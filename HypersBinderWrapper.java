package com.hypers.hm;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.FileDescriptor;
import java.util.Objects;

import com.hypers.hm.api.HypersApiConstants;

/**
 * Wraps an {@link IBinder} so that all transact calls are forwarded through the
 * Hypers server via {@link Hypers#transactRemote}.
 *
 * <pre>
 * IPackageManager pm = IPackageManager.Stub.asInterface(
 *         new HypersBinderWrapper(SystemServiceHelper.getSystemService("package")));
 * pm.getInstalledPackages(0, 0);
 * </pre>
 *
 * Package: com.hypers.hm
 */
public class HypersBinderWrapper implements IBinder {

    private final IBinder original;

    public HypersBinderWrapper(@NonNull IBinder original) {
        this.original = Objects.requireNonNull(original);
    }

    @Override
    public boolean transact(int code, @NonNull Parcel data,
                             @Nullable Parcel reply, int flags) throws RemoteException {
        boolean atLeast13 = !Hypers.isPreV11() && Hypers.getVersion() >= 13;

        Parcel newData = Parcel.obtain();
        try {
            newData.writeInterfaceToken(HypersApiConstants.BINDER_DESCRIPTOR);
            newData.writeStrongBinder(original);
            newData.writeInt(code);
            if (atLeast13) {
                newData.writeInt(flags);
            }
            newData.appendFrom(data, 0, data.dataSize());

            if (atLeast13) {
                Hypers.transactRemote(newData, reply, 0);
            } else {
                Hypers.transactRemote(newData, reply, flags);
            }
        } finally {
            newData.recycle();
        }
        return true;
    }

    @Nullable
    @Override
    public String getInterfaceDescriptor() throws RemoteException {
        return original.getInterfaceDescriptor();
    }

    @Override
    public boolean pingBinder() {
        return original.pingBinder();
    }

    @Override
    public boolean isBinderAlive() {
        return original.isBinderAlive();
    }

    @Nullable
    @Override
    public IInterface queryLocalInterface(@NonNull String descriptor) {
        return null;
    }

    @Override
    public void dump(@NonNull FileDescriptor fd, @Nullable String[] args) throws RemoteException {
        original.dump(fd, args);
    }

    @Override
    public void dumpAsync(@NonNull FileDescriptor fd, @Nullable String[] args) throws RemoteException {
        original.dumpAsync(fd, args);
    }

    @Override
    public void linkToDeath(@NonNull DeathRecipient recipient, int flags) throws RemoteException {
        original.linkToDeath(recipient, flags);
    }

    @Override
    public boolean unlinkToDeath(@NonNull DeathRecipient recipient, int flags) {
        return original.unlinkToDeath(recipient, flags);
    }
}
