package com.hypers.hm.bridge;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hypers.hm.Hypers;
import com.hypers.hm.HypersBinderWrapper;
import com.hypers.hm.SystemServiceHelper;

/**
 * Bridge to other applications.
 *
 * <p>Other apps can expose a UserService (an AIDL Binder service run under
 * root/shell by the Hypers server). This class provides helpers to
 * bind to such services and to forward Binder calls through the Hypers
 * privileged channel.</p>
 *
 * <h3>Bridging a third-party app</h3>
 * <ol>
 *   <li>The target app must declare its UserService in its manifest and expose an AIDL interface.</li>
 *   <li>Call {@link #bindAppService} from your app after the Hypers binder is received.</li>
 *   <li>Cast the returned IBinder to the target app's AIDL stub interface.</li>
 * </ol>
 *
 * <h3>Bridging a system service</h3>
 * <p>Use {@link #getPrivilegedSystemService} to wrap a hidden system service binder
 * so that transact calls are executed in the Hypers elevated process.</p>
 *
 * Package: com.hypers.hm
 */
public class HypersAppBridge {

    private static final String TAG = "HypersAppBridge";

    // ------------------------------------------------------------------ //
    //  Bind to another app's UserService                                   //
    // ------------------------------------------------------------------ //

    /**
     * Binds to a UserService hosted by another application.
     *
     * @param context        caller context (used only for package info)
     * @param targetPackage  target app package name, e.g. {@code "com.example.app"}
     * @param targetClass    fully qualified UserService class name
     * @param versionCode    expected service version code
     * @param processSuffix  process name suffix used by the target service
     * @param tag            optional tag to disambiguate multiple services
     * @param daemon         {@code true} to keep the service alive beyond caller lifetime
     * @param conn           callback for connection events
     */
    public static void bindAppService(@NonNull Context context,
                                       @NonNull String targetPackage,
                                       @NonNull String targetClass,
                                       int versionCode,
                                       @NonNull String processSuffix,
                                       @Nullable String tag,
                                       boolean daemon,
                                       @NonNull ServiceConnection conn) {
        Log.i(TAG, "bindAppService: " + targetPackage + "/" + targetClass);

        Hypers.UserServiceArgs args = new Hypers.UserServiceArgs(
                new ComponentName(targetPackage, targetClass))
                .version(versionCode)
                .processNameSuffix(processSuffix)
                .daemon(daemon);

        if (tag != null) {
            args.tag(tag);
        }

        Hypers.bindUserService(args, conn);
    }

    /**
     * Unbinds (and optionally kills) a previously bound app service.
     *
     * @param targetPackage  target app package name
     * @param targetClass    fully qualified UserService class name
     * @param tag            same tag used during binding, or {@code null}
     * @param conn           same connection used during binding, or {@code null}
     * @param kill           {@code true} to kill the remote service process
     */
    public static void unbindAppService(@NonNull String targetPackage,
                                         @NonNull String targetClass,
                                         @Nullable String tag,
                                         @Nullable ServiceConnection conn,
                                         boolean kill) {
        Log.i(TAG, "unbindAppService: " + targetPackage + "/" + targetClass);

        Hypers.UserServiceArgs args = new Hypers.UserServiceArgs(
                new ComponentName(targetPackage, targetClass))
                .processNameSuffix("_bridge");

        if (tag != null) {
            args.tag(tag);
        }

        Hypers.unbindUserService(args, conn, kill);
    }

    // ------------------------------------------------------------------ //
    //  Privileged system service access                                    //
    // ------------------------------------------------------------------ //

    /**
     * Returns a wrapped IBinder for a hidden system service.
     * All transact calls will be forwarded through the Hypers elevated channel.
     *
     * <pre>
     * IBinder wrapped = HypersAppBridge.getPrivilegedSystemService("package");
     * IPackageManager pm = IPackageManager.Stub.asInterface(wrapped);
     * </pre>
     *
     * @param serviceName system service name, e.g. {@code "package"}, {@code "activity"}
     * @return wrapped IBinder, or {@code null} if the service is unavailable
     */
    @Nullable
    public static IBinder getPrivilegedSystemService(@NonNull String serviceName) {
        IBinder raw = SystemServiceHelper.getSystemService(serviceName);
        if (raw == null) {
            Log.w(TAG, "System service not found: " + serviceName);
            return null;
        }
        return new HypersBinderWrapper(raw);
    }

    // ------------------------------------------------------------------ //
    //  Permission check bridge                                             //
    // ------------------------------------------------------------------ //

    /**
     * Checks whether the Hypers server process holds the given permission.
     * Useful for verifying that elevated operations are actually available.
     *
     * @param permission Android permission string
     * @return {@code true} if the server has the permission
     */
    public static boolean serverHasPermission(@NonNull String permission) {
        return Hypers.checkRemotePermission(permission)
                == android.content.pm.PackageManager.PERMISSION_GRANTED;
    }
}
