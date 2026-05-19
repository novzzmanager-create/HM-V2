package com.hypers.hm.server;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IHypersApplication extends IInterface {

    void bindApplication(Bundle data) throws RemoteException;
    void dispatchRequestPermissionResult(int requestCode, Bundle data) throws RemoteException;
    void showPermissionConfirmation(int requestUid, int requestPid, String requestPackageName, int requestCode) throws RemoteException;

    abstract class Stub extends Binder implements IHypersApplication {

        private static final String DESCRIPTOR = "com.hypers.hm.server.IHypersApplication";

        public Stub() { attachInterface(this, DESCRIPTOR); }

        public static IHypersApplication asInterface(IBinder obj) {
            if (obj == null) return null;
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin instanceof IHypersApplication) return (IHypersApplication) iin;
            return new Proxy(obj);
        }

        @Override public IBinder asBinder() { return this; }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1: {
                    data.enforceInterface(DESCRIPTOR);
                    Bundle _arg0 = null;
                    if (data.readInt() != 0) { _arg0 = Bundle.CREATOR.createFromParcel(data); }
                    bindApplication(_arg0);
                    return true;
                }
                case 2: {
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0 = data.readInt();
                    Bundle _arg1 = null;
                    if (data.readInt() != 0) { _arg1 = Bundle.CREATOR.createFromParcel(data); }
                    dispatchRequestPermissionResult(_arg0, _arg1);
                    return true;
                }
                case 10000: {
                    data.enforceInterface(DESCRIPTOR);
                    int _a0 = data.readInt(); int _a1 = data.readInt();
                    String _a2 = data.readString(); int _a3 = data.readInt();
                    showPermissionConfirmation(_a0, _a1, _a2, _a3);
                    if (reply != null) { reply.writeNoException(); }
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }

        private static class Proxy implements IHypersApplication {
            private final IBinder mRemote;
            Proxy(IBinder remote) { mRemote = remote; }
            @Override public IBinder asBinder() { return mRemote; }
            @Override public void bindApplication(Bundle data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try { _data.writeInterfaceToken(DESCRIPTOR); if (data != null) { _data.writeInt(1); data.writeToParcel(_data, 0); } else { _data.writeInt(0); } mRemote.transact(1, _data, null, IBinder.FLAG_ONEWAY); }
                finally { _data.recycle(); }
            }
            @Override public void dispatchRequestPermissionResult(int requestCode, Bundle data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try { _data.writeInterfaceToken(DESCRIPTOR); _data.writeInt(requestCode); if (data != null) { _data.writeInt(1); data.writeToParcel(_data, 0); } else { _data.writeInt(0); } mRemote.transact(2, _data, null, IBinder.FLAG_ONEWAY); }
                finally { _data.recycle(); }
            }
            @Override public void showPermissionConfirmation(int requestUid, int requestPid, String requestPackageName, int requestCode) throws RemoteException {
                Parcel _data = Parcel.obtain(); Parcel _reply = Parcel.obtain();
                try { _data.writeInterfaceToken(DESCRIPTOR); _data.writeInt(requestUid); _data.writeInt(requestPid); _data.writeString(requestPackageName); _data.writeInt(requestCode); mRemote.transact(10000, _data, _reply, 0); _reply.readException(); }
                finally { _data.recycle(); _reply.recycle(); }
            }
        }
    }
}
