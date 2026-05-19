package com.hypers.hm.server;

import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class RemoteProcessHolder extends IRemoteProcess.Stub {

    private final Process process;

    public RemoteProcessHolder(Process process) {
        this.process = process;
    }

    @Override
    public ParcelFileDescriptor getOutputStream() {

        try {

            return ParcelFileDescriptor.dup(
                    ((FileOutputStream)
                            process.getOutputStream()).getFD()
            );

        } catch (Exception e) {

            return null;

        }
    }
    
    @Override
public String exec(String cmd) throws RemoteException {

    try {

        Process process = Runtime.getRuntime().exec(cmd);

        java.io.BufferedReader reader =
                new java.io.BufferedReader(
                        new java.io.InputStreamReader(
                                process.getInputStream()));

        StringBuilder output = new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        reader.close();

        return output.toString();

    } catch (Exception e) {

        return e.toString();
    }
}

    @Override
    public ParcelFileDescriptor getInputStream() {

        try {

            return ParcelFileDescriptor.dup(
                    ((FileInputStream)
                            process.getInputStream()).getFD()
            );

        } catch (Exception e) {

            return null;

        }
    }

    @Override
    public ParcelFileDescriptor getErrorStream() {

        try {

            return ParcelFileDescriptor.dup(
                    ((FileInputStream)
                            process.getErrorStream()).getFD()
            );

        } catch (Exception e) {

            return null;

        }
    }

    @Override
    public int waitFor() {

        try {

            return process.waitFor();

        } catch (Exception e) {

            return -1;

        }
    }

    @Override
    public int exitValue() {

        try {

            return process.exitValue();

        } catch (Exception e) {

            return -1;

        }
    }

    @Override
    public void destroy() {

        process.destroy();

    }

    @Override
    public boolean alive() {

        return process.isAlive();

    }

    @Override
    public boolean waitForTimeout(long timeout, String unit) {

        try {

            return process.waitFor(
                    timeout,
                    java.util.concurrent.TimeUnit.valueOf(unit)
            );

        } catch (Exception e) {

            return false;

        }
    }
}