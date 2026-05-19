package com.hypers.hm.server;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IHypersServiceConnection extends IInterface {

    void connected(IBinder service) throws RemoteException;
    void died() throws RemoteException;

    abstract class Stub extends Binder implements IHypersServiceConnection {

        private static final String DESCRIPTOR = "com.hypers.hm.server.IHypersServiceConnection";

        public Stub() { attachInterface(this, DESCRIPTOR); }

        public static IHypersServiceConnection asInterface(IBinder obj) {
            if (obj == null) return null;
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin instanceof IHypersServiceConnection) return (IHypersServiceConnection) iin;
            return new Proxy(obj);
        }

        @Override public IBinder asBinder() { return this; }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case IBinder.FIRST_CALL_TRANSACTION: {
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg0 = data.readStrongBinder();
                    connected(_arg0);
                    return true;
                }
                case IBinder.FIRST_CALL_TRANSACTION + 1: {
                    data.enforceInterface(DESCRIPTOR);
                    died();
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }

        private static class Proxy implements IHypersServiceConnection {
            private final IBinder mRemote;
            Proxy(IBinder remote) { mRemote = remote; }
            @Override public IBinder asBinder() { return mRemote; }
            @Override public void connected(IBinder service) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try { _data.writeInterfaceToken(DESCRIPTOR); _data.writeStrongBinder(service); mRemote.transact(IBinder.FIRST_CALL_TRANSACTION, _data, null, IBinder.FLAG_ONEWAY); }
                finally { _data.recycle(); }
            }
            @Override public void died() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try { _data.writeInterfaceToken(DESCRIPTOR); mRemote.transact(IBinder.FIRST_CALL_TRANSACTION + 1, _data, null, IBinder.FLAG_ONEWAY); }
                finally { _data.recycle(); }
            }
        }
    }
}
