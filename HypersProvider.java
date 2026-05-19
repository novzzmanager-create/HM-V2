package com.hypers.hm;

import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hypers.hm.api.BinderContainer;
import com.hypers.hm.api.HypersApiConstants;

/**
 * ContentProvider that receives the Hypers server binder and optionally
 * shares it across processes of the same app.
 *
 * <p>Declare in AndroidManifest.xml:</p>
 * <pre>{@code
 * <provider
 *     android:name="com.hypers.hm.HypersProvider"
 *     android:authorities="${applicationId}.hypers"
 *     android:exported="true"
 *     android:multiprocess="false"
 *     android:permission="android.permission.INTERACT_ACROSS_USERS_FULL" />
 * }</pre>
 *
 * <p>
 * {@code android:permission} must be a permission granted to Shell (com.android.shell)
 * but not to normal apps – e.g. {@code android.permission.INTERACT_ACROSS_USERS_FULL}.
 * </p>
 *
 * Package: com.hypers.hm
 */
public class HypersProvider extends ContentProvider {

    private static final String TAG = "HypersProvider";

    private static boolean enableMultiProcess = false;
    private static boolean isProviderProcess  = false;

    /**
     * Must be called as early as possible (e.g., Application.onCreate static block)
     * when the app has multiple processes.
     *
     * @param isProviderProcess {@code true} for the process that hosts this provider.
     */
    public static void enableMultiProcessSupport(boolean isProviderProcess) {
        Log.d(TAG, "enableMultiProcessSupport: providerProcess=" + isProviderProcess);
        HypersProvider.isProviderProcess  = isProviderProcess;
        HypersProvider.enableMultiProcess = true;
    }

    public static void setIsProviderProcess(boolean value) {
        HypersProvider.isProviderProcess = value;
    }

    /**
     * Call from non-provider processes to pull the binder from the provider process.
     * Requires {@link #enableMultiProcessSupport(boolean)} to have been called first.
     */
    public static void requestBinderForNonProviderProcess(@NonNull Context context) {
        if (isProviderProcess) return;

        Log.d(TAG, "requestBinderForNonProviderProcess");

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent intent) {
                BinderContainer container = intent.getParcelableExtra(HypersApiConstants.EXTRA_BINDER);
                if (container != null && container.binder != null) {
                    Log.i(TAG, "binder received from broadcast");
                    Hypers.onBinderReceived(container.binder, ctx.getPackageName());
                }
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(receiver,
                    new IntentFilter(HypersApiConstants.ACTION_BINDER_RECEIVED),
                    Context.RECEIVER_NOT_EXPORTED);
        } else {
            context.registerReceiver(receiver,
                    new IntentFilter(HypersApiConstants.ACTION_BINDER_RECEIVED));
        }

        Bundle reply;
        try {
            Uri uri = Uri.parse("content://" + context.getPackageName()
                    + HypersApiConstants.PROVIDER_AUTHORITY_SUFFIX);
            reply = context.getContentResolver().call(uri,
                    HypersApiConstants.METHOD_GET_BINDER, null, new Bundle());
        } catch (Throwable t) {
            reply = null;
        }

        if (reply != null) {
            reply.setClassLoader(BinderContainer.class.getClassLoader());
            BinderContainer container = reply.getParcelable(HypersApiConstants.EXTRA_BINDER);
            if (container != null && container.binder != null) {
                Log.i(TAG, "binder received from provider process");
                Hypers.onBinderReceived(container.binder, context.getPackageName());
            }
        }
    }

    // ------------------------------------------------------------------ //
    //  ContentProvider lifecycle                                            //
    // ------------------------------------------------------------------ //

    @Override
    public void attachInfo(Context context, ProviderInfo info) {
        super.attachInfo(context, info);
        if (info.multiprocess) {
            throw new IllegalStateException("HypersProvider: android:multiprocess must be false");
        }
        if (!info.exported) {
            throw new IllegalStateException("HypersProvider: android:exported must be true");
        }
        isProviderProcess = true;
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Bundle call(@NonNull String method, @Nullable String arg, @Nullable Bundle extras) {
        if (extras == null) return null;

        extras.setClassLoader(BinderContainer.class.getClassLoader());

        Bundle reply = new Bundle();
        switch (method) {
            case HypersApiConstants.METHOD_SEND_BINDER:
                handleSendBinder(extras);
                break;
            case HypersApiConstants.METHOD_GET_BINDER:
                if (!handleGetBinder(reply)) return null;
                break;
        }
        return reply;
    }

    private void handleSendBinder(@NonNull Bundle extras) {
        if (Hypers.pingBinder()) {
            Log.d(TAG, "sendBinder: already have a living binder");
            return;
        }

        BinderContainer container = extras.getParcelable(HypersApiConstants.EXTRA_BINDER);
        if (container != null && container.binder != null) {
            Log.d(TAG, "sendBinder: binder received");
            Hypers.onBinderReceived(container.binder, getContext().getPackageName());

            if (enableMultiProcess) {
                Log.d(TAG, "sendBinder: broadcasting to other processes");
                Intent intent = new Intent(HypersApiConstants.ACTION_BINDER_RECEIVED)
                        .putExtra(HypersApiConstants.EXTRA_BINDER, container)
                        .setPackage(getContext().getPackageName());
                getContext().sendBroadcast(intent);
            }
        }
    }

    private boolean handleGetBinder(@NonNull Bundle reply) {
        IBinder binder = Hypers.getBinder();
        if (binder == null || !binder.pingBinder()) return false;
        reply.putParcelable(HypersApiConstants.EXTRA_BINDER, new BinderContainer(binder));
        return true;
    }

    // ------------------------------------------------------------------ //
    //  Unused ContentProvider methods                                       //
    // ------------------------------------------------------------------ //

    @Nullable @Override
    public Cursor query(@NonNull Uri u, @Nullable String[] p, @Nullable String s,
                        @Nullable String[] sa, @Nullable String so) { return null; }

    @Nullable @Override
    public String getType(@NonNull Uri uri) { return null; }

    @Nullable @Override
    public Uri insert(@NonNull Uri u, @Nullable ContentValues v) { return null; }

    @Override
    public int delete(@NonNull Uri u, @Nullable String s, @Nullable String[] sa) { return 0; }

    @Override
    public int update(@NonNull Uri u, @Nullable ContentValues v,
                      @Nullable String s, @Nullable String[] sa) { return 0; }
}
