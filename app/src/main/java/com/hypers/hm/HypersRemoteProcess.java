package com.hypers.hm;

import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.hypers.hm.server.IRemoteProcess;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

public class HypersRemoteProcess extends Process {

    private final IRemoteProcess remote;
    private OutputStream stdin;
    private InputStream  stdout;
    private InputStream  stderr;

    public HypersRemoteProcess(IRemoteProcess remote) {
        this.remote = remote;
    }

    @Override
    public OutputStream getOutputStream() {
        if (stdin == null) {
            try {
                ParcelFileDescriptor pfd = remote.getOutputStream();
                stdin = new FileOutputStream(pfd.getFileDescriptor());
            } catch (RemoteException e) { throw new RuntimeException(e); }
        }
        return stdin;
    }

    @Override
    public InputStream getInputStream() {
        if (stdout == null) {
            try {
                ParcelFileDescriptor pfd = remote.getInputStream();
                stdout = new FileInputStream(pfd.getFileDescriptor());
            } catch (RemoteException e) { throw new RuntimeException(e); }
        }
        return stdout;
    }

    @Override
    public InputStream getErrorStream() {
        if (stderr == null) {
            try {
                ParcelFileDescriptor pfd = remote.getErrorStream();
                stderr = new FileInputStream(pfd.getFileDescriptor());
            } catch (RemoteException e) { throw new RuntimeException(e); }
        }
        return stderr;
    }

    @Override
    public int waitFor() throws InterruptedException {
        try { return remote.waitFor(); }
        catch (RemoteException e) { throw new RuntimeException(e); }
    }

    @Override
    public boolean waitFor(long timeout, TimeUnit unit) throws InterruptedException {
        try { return remote.waitForTimeout(timeout, unit.name()); }
        catch (RemoteException e) { throw new RuntimeException(e); }
    }

    @Override
    public int exitValue() {
        try { return remote.exitValue(); }
        catch (RemoteException e) { throw new IllegalThreadStateException(e.getMessage()); }
    }

    @Override
    public void destroy() {
        try { remote.destroy(); }
        catch (RemoteException e) { throw new RuntimeException(e); }
    }

    @Override
    public Process destroyForcibly() { destroy(); return this; }

    public boolean isAlive() {
        try { return remote.alive(); }
        catch (RemoteException e) { return false; }
    }

    public String exec(String command) throws RemoteException {
        return remote.exec(command);
    }
}
