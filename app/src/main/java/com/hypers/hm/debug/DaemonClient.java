package com.hypers.hm.debug;

import java.io.*;
import java.net.*;

public class DaemonClient {

    private static final String HOST =
            "127.0.0.1";

    private static final int PORT =
            5050;

    public static String exec(String cmd) {

        try {

            Socket socket =
                    new Socket(
                            HOST,
                            PORT
                    );

            PrintWriter pw =
                    new PrintWriter(
                            socket.getOutputStream()
                    );

            pw.println(cmd);

            pw.flush();

            BufferedReader br =
                    new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream()
                            )
                    );

            StringBuilder result =
                    new StringBuilder();

            String line;

            while ((line = br.readLine()) != null) {

                result.append(line)
                        .append("\n");
            }

            socket.close();

            return result.toString();

        } catch (Exception e) {

            return e.toString();
        }
    }
}