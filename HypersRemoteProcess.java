package com.hypers.hm;

import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

import com.hypers.hm.server.IRemoteProcess;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

/**
 * Wraps a remote {@link IRemoteProcess} binder with a convenient Java API
 * similar to {@link Process}.
 *
 * Package: com.hypers.hm
 */
public class HypersRemoteProcess {

    private final IRemoteProcess remote;

    public HypersRemoteProcess(IRemoteProcess remote) {
        this.remote = remote;
    }

    /** Returns an OutputStream connected to the stdin of the remote process. */
    public OutputStream getOutputStream() throws RemoteException {
        ParcelFileDescriptor pfd = remote.getOutputStream();
        return new FileOutputStream(pfd.getFileDescriptor());
    }

    /** Returns an InputStream connected to the stdout of the remote process. */
    public InputStream getInputStream() throws RemoteException {
        ParcelFileDescriptor pfd = remote.getInputStream();
        return new FileInputStream(pfd.getFileDescriptor());
    }

    /** Returns an InputStream connected to the stderr of the remote process. */
    public InputStream getErrorStream() throws RemoteException {
        ParcelFileDescriptor pfd = remote.getErrorStream();
        return new FileInputStream(pfd.getFileDescriptor());
    }

    /** Blocks until the remote process exits, then returns its exit code. */
    public int waitFor() throws RemoteException {
        return remote.waitFor();
    }

    /** Returns the exit code without blocking. Throws if the process is still running. */
    public int exitValue() throws RemoteException {
        return remote.exitValue();
    }

    /** Returns {@code true} if the remote process is still running. */
    public boolean isAlive() throws RemoteException {
        return remote.alive();
    }

    /**
     * Blocks until the remote process exits or the timeout elapses.
     *
     * @param timeout timeout value
     * @param unit    timeout unit
     * @return {@code true} if process exited within the timeout
     */
    public boolean waitFor(long timeout, TimeUnit unit) throws RemoteException {
        return remote.waitForTimeout(timeout, unit.name());
    }

    /** Terminates the remote process. */
    public void destroy() throws RemoteException {
        remote.destroy();
    }
    
    public String exec(String command) throws RemoteException {
        return remote.exec(command);
    }
}
