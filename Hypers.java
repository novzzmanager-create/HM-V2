package com.hypers.hm;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP_PREFIX;
import static com.hypers.hm.api.HypersApiConstants.ATTACH_APPLICATION_API_VERSION;
import static com.hypers.hm.api.HypersApiConstants.ATTACH_APPLICATION_PACKAGE_NAME;
import static com.hypers.hm.api.HypersApiConstants.BIND_APPLICATION_PERMISSION_GRANTED;
import static com.hypers.hm.api.HypersApiConstants.BIND_APPLICATION_SERVER_PATCH_VERSION;
import static com.hypers.hm.api.HypersApiConstants.BIND_APPLICATION_SERVER_SECONTEXT;
import static com.hypers.hm.api.HypersApiConstants.BIND_APPLICATION_SERVER_UID;
import static com.hypers.hm.api.HypersApiConstants.BIND_APPLICATION_SERVER_VERSION;
import static com.hypers.hm.api.HypersApiConstants.BIND_APPLICATION_SHOULD_SHOW_REQUEST_PERMISSION_RATIONALE;
import static com.hypers.hm.api.HypersApiConstants.REQUEST_PERMISSION_REPLY_ALLOWED;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.hypers.hm.api.HypersApiConstants;
import com.hypers.hm.server.IHypersApplication;
import com.hypers.hm.server.IHypersService;
import com.hypers.hm.server.IRemoteProcess;

/**
 * Hypers Bridge – main entry point.
 *
 * <p>Package: com.hypers.hm</p>
 *
 * <h3>Usage</h3>
 * <ol>
 *   <li>Declare {@link HypersProvider} in your AndroidManifest.xml</li>
 *   <li>Register listeners with {@link #addBinderReceivedListenerSticky}</li>
 *   <li>Request permission with {@link #requestPermission}</li>
 *   <li>Use APIs after {@link OnBinderReceivedListener#onBinderReceived()} fires</li>
 * </ol>
 */
public class Hypers {

    private static final String TAG = "Hypers";

    private static IBinder binder;
    private static IHypersService service;

    private static int serverUid           = -1;
    private static int serverApiVersion    = -1;
    private static int serverPatchVersion  = -1;
    private static String serverContext    = null;
    private static boolean permissionGranted = false;
    private static boolean shouldShowRationale = false;
    private static boolean preV11          = false;
    private static boolean binderReady     = false;

    // ------------------------------------------------------------------ //
    //  Internal IHypersApplication stub received by the Hypers server     //
    // ------------------------------------------------------------------ //

    private static final IHypersApplication HYPERS_APPLICATION = new IHypersApplication.Stub() {

        @Override
        public void bindApplication(Bundle data) {
            serverUid           = data.getInt(HypersApiConstants.BIND_APPLICATION_SERVER_UID, -1);
            serverApiVersion    = data.getInt(HypersApiConstants.BIND_APPLICATION_SERVER_VERSION, -1);
            serverPatchVersion  = data.getInt(HypersApiConstants.BIND_APPLICATION_SERVER_PATCH_VERSION, -1);
            serverContext       = data.getString(HypersApiConstants.BIND_APPLICATION_SERVER_SECONTEXT);
            permissionGranted   = data.getBoolean(HypersApiConstants.BIND_APPLICATION_PERMISSION_GRANTED, false);
            shouldShowRationale = data.getBoolean(
                    HypersApiConstants.BIND_APPLICATION_SHOULD_SHOW_REQUEST_PERMISSION_RATIONALE, false);

            scheduleBinderReceivedListeners();
        }

        @Override
        public void dispatchRequestPermissionResult(int requestCode, Bundle data) {
            boolean allowed = data.getBoolean(HypersApiConstants.REQUEST_PERMISSION_REPLY_ALLOWED, false);
            scheduleRequestPermissionResultListeners(requestCode,
                    allowed ? PackageManager.PERMISSION_GRANTED : PackageManager.PERMISSION_DENIED);
        }

        @Override
        public void showPermissionConfirmation(int requestUid, int requestPid,
                                               String requestPackageName, int requestCode) {
            // Handled by manager UI, not app
        }
    };

    // ------------------------------------------------------------------ //
    //  Binder death handler                                                //
    // ------------------------------------------------------------------ //

    private static final IBinder.DeathRecipient DEATH_RECIPIENT = () -> {
        binderReady = false;
        onBinderReceived(null, null);
    };

    // ------------------------------------------------------------------ //
    //  attachApplication – supports v11 and v13+ server protocols         //
    // ------------------------------------------------------------------ //

    private static boolean attachApplicationV13(IBinder binder, String packageName) throws RemoteException {
        Bundle args = new Bundle();
        args.putInt(HypersApiConstants.ATTACH_APPLICATION_API_VERSION, HypersApiConstants.SERVER_VERSION);
        args.putString(HypersApiConstants.ATTACH_APPLICATION_PACKAGE_NAME, packageName);

        Parcel data  = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInterfaceToken(HypersApiConstants.BINDER_DESCRIPTOR);
            data.writeStrongBinder(HYPERS_APPLICATION.asBinder());
            data.writeInt(1);
            args.writeToParcel(data, 0);
            boolean result = binder.transact(18, data, reply, 0);
            reply.readException();
            return result;
        } finally {
            reply.recycle();
            data.recycle();
        }
    }

    private static boolean attachApplicationV11(IBinder binder, String packageName) throws RemoteException {
        Parcel data  = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInterfaceToken(HypersApiConstants.BINDER_DESCRIPTOR);
            data.writeStrongBinder(HYPERS_APPLICATION.asBinder());
            data.writeString(packageName);
            boolean result = binder.transact(14, data, reply, 0);
            reply.readException();
            return result;
        } finally {
            reply.recycle();
            data.recycle();
        }
    }

    // ------------------------------------------------------------------ //
    //  Core binder lifecycle                                               //
    // ------------------------------------------------------------------ //

    /**
     * Called internally by {@link HypersProvider} when the server delivers
     * (or drops) the binder.
     */
    public static void onBinderReceived(@Nullable IBinder newBinder, String packageName) {
        if (binder == newBinder) return;

        if (newBinder == null) {
            binder           = null;
            service          = null;
            serverUid        = -1;
            serverApiVersion = -1;
            serverContext    = null;
            scheduleBinderDeadListeners();
        } else {
            if (binder != null) {
                binder.unlinkToDeath(DEATH_RECIPIENT, 0);
            }
            binder  = newBinder;
            service = IHypersService.Stub.asInterface(newBinder);

            try {
                binder.linkToDeath(DEATH_RECIPIENT, 0);
            } catch (Throwable e) {
                Log.w(TAG, "linkToDeath failed", e);
            }

            try {
                if (!attachApplicationV13(binder, packageName) && !attachApplicationV11(binder, packageName)) {
                    preV11 = true;
                }
            } catch (Throwable e) {
                Log.w(TAG, "attachApplication failed", e);
            }

            if (preV11) {
                binderReady = true;
                scheduleBinderReceivedListeners();
            }
        }
    }

    // ------------------------------------------------------------------ //
    //  Listener infrastructure                                             //
    // ------------------------------------------------------------------ //

    public interface OnBinderReceivedListener {
        void onBinderReceived();
    }

    public interface OnBinderDeadListener {
        void onBinderDead();
    }

    public interface OnRequestPermissionResultListener {
        /**
         * @param requestCode code passed to {@link #requestPermission(int)}
         * @param grantResult {@link PackageManager#PERMISSION_GRANTED} or
         *                    {@link PackageManager#PERMISSION_DENIED}
         */
        void onRequestPermissionResult(int requestCode, int grantResult);
    }

    private static class ListenerHolder<T> {
        final T       listener;
        final Handler handler;

        ListenerHolder(@NonNull T listener, @Nullable Handler handler) {
            this.listener = listener;
            this.handler  = handler;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ListenerHolder)) return false;
            ListenerHolder<?> that = (ListenerHolder<?>) o;
            return Objects.equals(listener, that.listener) && Objects.equals(handler, that.handler);
        }

        @Override
        public int hashCode() {
            return Objects.hash(listener, handler);
        }
    }

    private static final List<ListenerHolder<OnBinderReceivedListener>>        RECEIVED_LISTENERS   = new ArrayList<>();
    private static final List<ListenerHolder<OnBinderDeadListener>>            DEAD_LISTENERS       = new ArrayList<>();
    private static final List<ListenerHolder<OnRequestPermissionResultListener>> PERMISSION_LISTENERS = new ArrayList<>();
    private static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());

    /** Add listener called when Hypers binder is received (main thread). */
    public static void addBinderReceivedListener(@NonNull OnBinderReceivedListener listener) {
        addBinderReceivedListener(listener, null);
    }

    public static void addBinderReceivedListener(@NonNull OnBinderReceivedListener listener,
                                                  @Nullable Handler handler) {
        addBinderReceivedListenerInternal(Objects.requireNonNull(listener), false, handler);
    }

    /** Same as {@link #addBinderReceivedListener} but fires immediately if binder already available. */
    public static void addBinderReceivedListenerSticky(@NonNull OnBinderReceivedListener listener) {
        addBinderReceivedListenerSticky(listener, null);
    }

    public static void addBinderReceivedListenerSticky(@NonNull OnBinderReceivedListener listener,
                                                        @Nullable Handler handler) {
        addBinderReceivedListenerInternal(Objects.requireNonNull(listener), true, handler);
    }

    private static void addBinderReceivedListenerInternal(@NonNull OnBinderReceivedListener listener,
                                                           boolean sticky,
                                                           @Nullable Handler handler) {
        if (sticky && binderReady) {
            dispatch(handler, listener::onBinderReceived);
        }
        synchronized (RECEIVED_LISTENERS) {
            RECEIVED_LISTENERS.add(new ListenerHolder<>(listener, handler));
        }
    }

    public static boolean removeBinderReceivedListener(@NonNull OnBinderReceivedListener listener) {
        synchronized (RECEIVED_LISTENERS) {
            return RECEIVED_LISTENERS.removeIf(h -> h.listener == listener);
        }
    }

    public static void addBinderDeadListener(@NonNull OnBinderDeadListener listener) {
        addBinderDeadListener(listener, null);
    }

    public static void addBinderDeadListener(@NonNull OnBinderDeadListener listener,
                                              @Nullable Handler handler) {
        synchronized (DEAD_LISTENERS) {
            DEAD_LISTENERS.add(new ListenerHolder<>(listener, handler));
        }
    }

    public static boolean removeBinderDeadListener(@NonNull OnBinderDeadListener listener) {
        synchronized (DEAD_LISTENERS) {
            return DEAD_LISTENERS.removeIf(h -> h.listener == listener);
        }
    }

    public static void addRequestPermissionResultListener(@NonNull OnRequestPermissionResultListener listener) {
        addRequestPermissionResultListener(listener, null);
    }

    public static void addRequestPermissionResultListener(@NonNull OnRequestPermissionResultListener listener,
                                                           @Nullable Handler handler) {
        synchronized (PERMISSION_LISTENERS) {
            PERMISSION_LISTENERS.add(new ListenerHolder<>(listener, handler));
        }
    }

    public static boolean removeRequestPermissionResultListener(@NonNull OnRequestPermissionResultListener listener) {
        synchronized (PERMISSION_LISTENERS) {
            return PERMISSION_LISTENERS.removeIf(h -> h.listener == listener);
        }
    }

    private static void dispatch(@Nullable Handler handler, Runnable r) {
        if (handler != null) {
            handler.post(r);
        } else if (Looper.myLooper() == Looper.getMainLooper()) {
            r.run();
        } else {
            MAIN_HANDLER.post(r);
        }
    }

    private static void scheduleBinderReceivedListeners() {
        synchronized (RECEIVED_LISTENERS) {
            for (ListenerHolder<OnBinderReceivedListener> h : RECEIVED_LISTENERS) {
                dispatch(h.handler, h.listener::onBinderReceived);
            }
        }
        binderReady = true;
    }

    private static void scheduleBinderDeadListeners() {
        synchronized (DEAD_LISTENERS) {
            for (ListenerHolder<OnBinderDeadListener> h : DEAD_LISTENERS) {
                dispatch(h.handler, h.listener::onBinderDead);
            }
        }
    }

    private static void scheduleRequestPermissionResultListeners(int requestCode, int result) {
        synchronized (PERMISSION_LISTENERS) {
            for (ListenerHolder<OnRequestPermissionResultListener> h : PERMISSION_LISTENERS) {
                dispatch(h.handler, () -> h.listener.onRequestPermissionResult(requestCode, result));
            }
        }
    }

    // ------------------------------------------------------------------ //
    //  Public API helpers                                                   //
    // ------------------------------------------------------------------ //

    @NonNull
    public static IHypersService requireService() {
        if (service == null) {
            throw new IllegalStateException("Hypers binder has not been received yet.");
        }
        return service;
    }

    @Nullable
    public static IBinder getBinder() {
        return binder;
    }

    public static boolean pingBinder() {
        return binder != null && binder.pingBinder();
    }

    public static int getUid() {
        if (serverUid != -1) return serverUid;
        try {
            serverUid = requireService().getUid();
        } catch (RemoteException e) {
            throw rethrow(e);
        } catch (SecurityException e) {
            return -1;
        }
        return serverUid;
    }

    public static int getVersion() {
        if (serverApiVersion != -1) return serverApiVersion;
        try {
            serverApiVersion = requireService().getVersion();
        } catch (RemoteException e) {
            throw rethrow(e);
        } catch (SecurityException e) {
            return -1;
        }
        return serverApiVersion;
    }

    public static int getServerPatchVersion() {
        return serverPatchVersion;
    }

    public static boolean isPreV11() {
        return preV11;
    }

    public static int getLatestServiceVersion() {
        return HypersApiConstants.SERVER_VERSION;
    }

    @Nullable
    public static String getSELinuxContext() {
        if (serverContext != null) return serverContext;
        try {
            serverContext = requireService().getSELinuxContext();
        } catch (RemoteException e) {
            throw rethrow(e);
        } catch (SecurityException e) {
            return null;
        }
        return serverContext;
    }

    // ------------------------------------------------------------------ //
    //  Permission                                                           //
    // ------------------------------------------------------------------ //

    public static void requestPermission(int requestCode) {
        try {
            requireService().requestPermission(requestCode);
        } catch (RemoteException e) {
            throw rethrow(e);
        }
    }

    public static int checkSelfPermission() {
        if (permissionGranted) return PackageManager.PERMISSION_GRANTED;
        try {
            permissionGranted = requireService().checkSelfPermission();
        } catch (RemoteException e) {
            throw rethrow(e);
        }
        return permissionGranted ? PackageManager.PERMISSION_GRANTED : PackageManager.PERMISSION_DENIED;
    }

    public static boolean shouldShowRequestPermissionRationale() {
        if (permissionGranted) return false;
        if (shouldShowRationale) return true;
        try {
            shouldShowRationale = requireService().shouldShowRequestPermissionRationale();
        } catch (RemoteException e) {
            throw rethrow(e);
        }
        return shouldShowRationale;
    }

    public static int checkRemotePermission(String permission) {
        if (serverUid == 0) return PackageManager.PERMISSION_GRANTED;
        try {
            return requireService().checkPermission(permission);
        } catch (RemoteException e) {
            throw rethrow(e);
        }
    }

    // ------------------------------------------------------------------ //
    //  Binder transact                                                      //
    // ------------------------------------------------------------------ //

    public static void transactRemote(@NonNull Parcel data, @Nullable Parcel reply, int flags) {
        try {
            requireService().asBinder().transact(
                    android.os.IBinder.FIRST_CALL_TRANSACTION, data, reply, flags);
        } catch (RemoteException e) {
            throw rethrow(e);
        }
    }

    // ------------------------------------------------------------------ //
    //  UserService binding                                                  //
    // ------------------------------------------------------------------ //

    public static void bindUserService(@NonNull UserServiceArgs args,
                                        @NonNull ServiceConnection conn) {
        HypersServiceConnection connection = HypersServiceConnections.get(args);
        connection.addConnection(conn);
        try {
            requireService().addUserService(connection, args.forAdd());
        } catch (RemoteException e) {
            throw rethrow(e);
        }
    }

    public static int peekUserService(@NonNull UserServiceArgs args,
                                       @NonNull ServiceConnection conn) {
        HypersServiceConnection connection = HypersServiceConnections.get(args);
        connection.addConnection(conn);
        int result;
        try {
            Bundle bundle = args.forAdd();
            bundle.putBoolean(HypersApiConstants.USER_SERVICE_ARG_NO_CREATE, true);
            result = requireService().addUserService(connection, bundle);
        } catch (RemoteException e) {
            throw rethrow(e);
        }

        boolean atLeast13 = !preV11 && getVersion() >= 13;
        if (atLeast13) return result;
        return (result == 0) ? 0 : -1;
    }

    public static void unbindUserService(@NonNull UserServiceArgs args,
                                          @Nullable ServiceConnection conn,
                                          boolean remove) {
        if (remove) {
            try {
                requireService().removeUserService(null, args.forRemove(true));
            } catch (RemoteException e) {
                throw rethrow(e);
            }
        } else {
            HypersServiceConnection connection = HypersServiceConnections.get(args);
            int version      = getVersion();
            int patchVersion = getServerPatchVersion();
            if (version >= 14 || (version == 13 && patchVersion >= 4)) {
                try {
                    requireService().removeUserService(connection, args.forRemove(false));
                } catch (RemoteException e) {
                    throw rethrow(e);
                }
            }
            connection.clearConnections();
            HypersServiceConnections.remove(connection);
        }
    }

    // ------------------------------------------------------------------ //
    //  Internal / manager-only APIs                                         //
    // ------------------------------------------------------------------ //

    public static void exit() {
        try {
            requireService().exit();
        } catch (RemoteException e) {
            throw rethrow(e);
        }
    }

    public static void attachUserService(@NonNull IBinder binder, @NonNull Bundle options) {
        try {
            requireService().attachUserService(binder, options);
        } catch (RemoteException e) {
            throw rethrow(e);
        }
    }

    public static void dispatchPermissionConfirmationResult(int requestUid, int requestPid,
                                                             int requestCode, @NonNull Bundle data) {
        try {
            requireService().dispatchPermissionConfirmationResult(requestUid, requestPid, requestCode, data);
        } catch (RemoteException e) {
            throw rethrow(e);
        }
    }

    public static int getFlagsForUid(int uid, int mask) {
        try {
            return requireService().getFlagsForUid(uid, mask);
        } catch (RemoteException e) {
            throw rethrow(e);
        }
    }

    public static void updateFlagsForUid(int uid, int mask, int value) {
        try {
            requireService().updateFlagsForUid(uid, mask, value);
        } catch (RemoteException e) {
            throw rethrow(e);
        }
    }

    // ------------------------------------------------------------------ //
    //  UserServiceArgs                                                       //
    // ------------------------------------------------------------------ //

    public static class UserServiceArgs {

        final ComponentName componentName;
        int     versionCode         = 1;
        String  processName;
        String  tag;
        boolean debuggable          = false;
        boolean daemon              = true;
        boolean use32BitAppProcess  = false;

        public UserServiceArgs(@NonNull ComponentName componentName) {
            this.componentName = componentName;
        }

        public UserServiceArgs daemon(boolean daemon) {
            this.daemon = daemon;
            return this;
        }

        public UserServiceArgs tag(@NonNull String tag) {
            this.tag = tag;
            return this;
        }

        public UserServiceArgs version(int versionCode) {
            this.versionCode = versionCode;
            return this;
        }

        public UserServiceArgs debuggable(boolean debuggable) {
            this.debuggable = debuggable;
            return this;
        }

        public UserServiceArgs processNameSuffix(String processNameSuffix) {
            this.processName = processNameSuffix;
            return this;
        }

        Bundle forAdd() {
            Bundle options = new Bundle();
            options.putParcelable(HypersApiConstants.USER_SERVICE_ARG_COMPONENT, componentName);
            options.putBoolean(HypersApiConstants.USER_SERVICE_ARG_DEBUGGABLE, debuggable);
            options.putInt(HypersApiConstants.USER_SERVICE_ARG_VERSION_CODE, versionCode);
            options.putBoolean(HypersApiConstants.USER_SERVICE_ARG_DAEMON, daemon);
            options.putBoolean(HypersApiConstants.USER_SERVICE_ARG_USE_32_BIT_APP_PROCESS, use32BitAppProcess);
            options.putString(HypersApiConstants.USER_SERVICE_ARG_PROCESS_NAME,
                    Objects.requireNonNull(processName, "processNameSuffix must not be null"));
            if (tag != null) {
                options.putString(HypersApiConstants.USER_SERVICE_ARG_TAG, tag);
            }
            return options;
        }

        Bundle forRemove(boolean remove) {
            Bundle options = new Bundle();
            options.putParcelable(HypersApiConstants.USER_SERVICE_ARG_COMPONENT, componentName);
            if (tag != null) {
                options.putString(HypersApiConstants.USER_SERVICE_ARG_TAG, tag);
            }
            options.putBoolean(HypersApiConstants.USER_SERVICE_ARG_REMOVE, remove);
            return options;
        }
    }

    // ------------------------------------------------------------------ //
    //  Internal helpers                                                     //
    // ------------------------------------------------------------------ //

    private static RuntimeException rethrow(RemoteException e) {
        return new RuntimeException(e);
    }
    
    public static HypersRemoteProcess newProcess(String[] cmd) {

    try {

        IRemoteProcess process =
                requireService().newProcess(cmd, null, null);

        return new HypersRemoteProcess(process);

    } catch (Exception e) {

        e.printStackTrace();

        return null;
    }
}
}
