package com.hypers.hm.bridge;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.hypers.hm.Hypers;
import com.hypers.hm.HypersRemoteProcess;
import com.hypers.hm.api.HypersApiConstants;
import com.hypers.hm.service.HypersPairService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Manages Hypers server pairing and startup modes.
 */
public class HypersPairingManager {

    private static final String TAG = "HypersPairingManager";

    // =========================================================
    // START PAIRING
    // =========================================================

    public static void startPairing(@NonNull Context context) {

        Log.i(TAG, "startPairing");

        // START NOTIFICATION PAIR SERVICE
        try {

            Intent serviceIntent =
                    new Intent(
                            context,
                            HypersPairService.class
                    );

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                context.startForegroundService(
                        serviceIntent
                );

            } else {

                context.startService(
                        serviceIntent
                );
            }

        } catch (Exception e) {

            Log.e(
                    TAG,
                    "Failed start pair service",
                    e
            );
        }

        // OPTIONAL BROADCAST KE MANAGER
        try {

            Intent intent =
                    new Intent(
                            HypersApiConstants.CMD_START_PAIRING
                    );

            intent.setPackage(
                    "com.hypers.hm.manager"
            );

            context.sendBroadcast(intent);

        } catch (Exception e) {

            Log.e(
                    TAG,
                    "Broadcast failed",
                    e
            );
        }
    }

    // =========================================================
    // ROOT
    // =========================================================

    @NonNull
    public static RemoteShellSession startOnRoot() {

        Log.i(TAG, "startOnRoot");

        return new RemoteShellSession(
                new String[]{"su"},
                null,
                null
        );
    }

    // =========================================================
    // ADB
    // =========================================================

    @NonNull
    public static RemoteShellSession startOnAdb() {

        Log.i(TAG, "startOnAdb");

        return new RemoteShellSession(
                new String[]{"sh"},
                null,
                null
        );
    }

    // =========================================================
    // COMPUTER
    // =========================================================

    public static void startOnComputer(@NonNull Context context) {

        Log.i(TAG, "startOnComputer");

        Intent intent =
                new Intent(
                        HypersApiConstants.CMD_START_COMPUTER
                );

        intent.setPackage(
                "com.hypers.hm.manager"
        );

        intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
        );

        context.startActivity(intent);
    }

    // =========================================================
    // STOP SERVICE
    // =========================================================

    public static void stopService(@NonNull Context context) {

        Log.i(TAG, "stopService");

        if (Hypers.pingBinder()) {

            Hypers.exit();

        } else {

            Intent intent =
                    new Intent(
                            HypersApiConstants.CMD_STOP_SERVICE
                    );

            intent.setPackage(
                    "com.hypers.hm.manager"
            );

            context.sendBroadcast(intent);
        }
    }

    // =========================================================
    // REMOTE SHELL
    // =========================================================

    public static class RemoteShellSession {

        private final String[] cmd;
        private final String[] env;
        private final String dir;

        RemoteShellSession(
                String[] cmd,
                String[] env,
                String dir
        ) {

            this.cmd = cmd;
            this.env = env;
            this.dir = dir;
        }

        // =====================================================
        // EXECUTE SYNC
        // =====================================================

        @NonNull
        public String execute(@NonNull String command) {

            String[] fullCmd =
                    new String[]{
                            "sh",
                            "-c",
                            command
                    };

            try {

                HypersRemoteProcess process =
                        newProcess(
                                fullCmd,
                                env,
                                dir
                        );

                StringBuilder sb =
                        new StringBuilder();

                try (

                        BufferedReader reader =
                                new BufferedReader(
                                        new InputStreamReader(
                                                process.getInputStream()
                                        )
                                )

                ) {

                    String line;

                    while ((line = reader.readLine()) != null) {

                        sb.append(line)
                                .append('\n');
                    }
                }

                process.waitFor();

                return sb.toString().trim();

            } catch (Exception e) {

                Log.e(
                        TAG,
                        "execute failed: " + command,
                        e
                );

                return "";
            }
        }

        // =====================================================
        // EXECUTE ASYNC
        // =====================================================

        public void execute(
                @NonNull String command,
                @NonNull Callback callback
        ) {

            new Thread(() -> {

                String result =
                        execute(command);

                callback.onResult(result);

            }, "hypers-shell").start();
        }

        // =====================================================
        // CREATE PROCESS
        // =====================================================

        private HypersRemoteProcess newProcess(
                String[] cmd,
                String[] env,
                String dir
        ) {

            try {

                return new HypersRemoteProcess(
                        Hypers.requireService()
                                .newProcess(
                                        cmd,
                                        env,
                                        dir
                                )
                );

            } catch (Exception e) {

                throw new RuntimeException(e);
            }
        }

        // =====================================================
        // CALLBACK
        // =====================================================

        public interface Callback {

            void onResult(String output);
        }
    }
}