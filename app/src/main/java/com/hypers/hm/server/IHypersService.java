// FILE INI ADALAH STUB MANUAL DARI IHypersService.aidl
// Sketchware Pro tidak bisa proses .aidl otomatis, jadi ini versi Java-nya
// Diletakkan di: src/com/hypers/hm/server/IHypersService.java

package com.hypers.hm.server;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.content.Intent;

public interface IHypersService extends IInterface {

    int getVersion() throws RemoteException;
    int getUid() throws RemoteException;
    int checkPermission(String permission) throws RemoteException;
    IRemoteProcess newProcess(String[] cmd, String[] env, String dir) throws RemoteException;
    String getSELinuxContext() throws RemoteException;
    String getSystemProperty(String name, String defaultValue) throws RemoteException;
    void setSystemProperty(String name, String value) throws RemoteException;
    int addUserService(IHypersServiceConnection conn, Bundle args) throws RemoteException;
    int removeUserService(IHypersServiceConnection conn, Bundle args) throws RemoteException;
    void requestPermission(int requestCode) throws RemoteException;
    boolean checkSelfPermission() throws RemoteException;
    boolean shouldShowRequestPermissionRationale() throws RemoteException;
    void attachApplication(IHypersApplication application, Bundle args) throws RemoteException;
    void exit() throws RemoteException;
    void attachUserService(IBinder binder, Bundle options) throws RemoteException;
    void dispatchPackageChanged(Intent intent) throws RemoteException;
    boolean isHidden(int uid) throws RemoteException;
    void dispatchPermissionConfirmationResult(int requestUid, int requestPid, int requestCode, Bundle data) throws RemoteException;
    int getFlagsForUid(int uid, int mask) throws RemoteException;
    void updateFlagsForUid(int uid, int mask, int value) throws RemoteException;

    abstract class Stub extends Binder implements IHypersService {

        private static final String DESCRIPTOR = "com.hypers.hm.server.IHypersService";

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IHypersService asInterface(IBinder obj) {
            if (obj == null) return null;
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin instanceof IHypersService) return (IHypersService) iin;
            return new Proxy(obj);
        }

        @Override
        public IBinder asBinder() { return this; }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }

        private static class Proxy implements IHypersService {
            private final IBinder mRemote;

            Proxy(IBinder remote) { mRemote = remote; }

            @Override public IBinder asBinder() { return mRemote; }

            @Override public int getVersion() throws RemoteException {
                Parcel data = Parcel.obtain(); Parcel reply = Parcel.obtain();
                try { data.writeInterfaceToken(DESCRIPTOR); mRemote.transact(2, data, reply, 0); reply.readException(); return reply.readInt(); }
                finally { data.recycle(); reply.recycle(); }
            }
            @Override public int getUid() throws RemoteException {
                Parcel data = Parcel.obtain(); Parcel reply = Parcel.obtain();
                try { data.writeInterfaceToken(DESCRIPTOR); mRemote.transact(3, data, reply, 0); reply.readException(); return reply.readInt(); }
                finally { data.recycle(); reply.recycle(); }
            }
            @Override public int checkPermission(String permission) throws RemoteException {
                Parcel data = Parcel.obtain(); Parcel reply = Parcel.obtain();
                try { data.writeInterfaceToken(DESCRIPTOR); data.writeString(permission); mRemote.transact(4, data, reply, 0); reply.readException(); return reply.readInt(); }
                finally { data.recycle(); reply.recycle(); }
            }
            @Override public IRemoteProcess newProcess(String[] cmd, String[] env, String dir) throws RemoteException {
                Parcel data = Parcel.obtain(); Parcel reply = Parcel.obtain();
                try { data.writeInterfaceToken(DESCRIPTOR); data.writeStringArray(cmd); data.writeStringArray(env); data.writeString(dir); mRemote.transact(7, data, reply, 0); reply.readException(); return IRemoteProcess.Stub.asInterface(reply.readStrongBinder()); }
                finally { data.recycle(); reply.recycle(); }
            }
            @Override public String getSELinuxContext() throws RemoteException {
                Parcel data = Parcel.obtain(); Parcel reply = Parcel.obtain();
                try { data.writeInterfaceToken(DESCRIPTOR); mRemote.transact(8, data, reply, 0); reply.readException(); return reply.readString(); }
                finally { data.recycle(); reply.recycle(); }
            }
            @Override public String getSystemProperty(String name, String defaultValue) throws RemoteException {
                Parcel data = Parcel.obtain(); Parcel reply = Parcel.obtain();
                try { data.writeInterfaceToken(DESCRIPTOR); data.writeString(name); data.writeString(defaultValue); mRemote.transact(9, data, reply, 0); reply.readException(); return reply.readString(); }
                finally { data.recycle(); reply.recycle(); }
            }
            @Override public void setSystemProperty(String name, String value) throws RemoteException {
                Parcel data = Parcel.obtain(); Parcel reply = Parcel.obtain();
                try { data.writeInterfaceToken(DESCRIPTOR); data.writeString(name); data.writeString(value); mRemote.transact(10, data, reply, 0); reply.readException(); }
                finally { data.recycle(); reply.recycle(); }
            }
            @Override public int addUserService(IHypersServiceConnection conn, Bundle args) throws RemoteException {
                Parcel data = Parcel.obtain(); Parcel reply = Parcel.obtain();
                try { data.writeInterfaceToken(DESCRIPTOR); data.writeStrongBinder(conn != null ? conn.asBinder() : null); if (args != null) { data.writeInt(1); args.writeToParcel(data, 0); } else { data.writeInt(0); } mRemote.transact(11, data, reply, 0); reply.readException(); return reply.readInt(); }
                finally { data.recycle(); reply.recycle(); }
            }
            @Override public int removeUserService(IHypersServiceConnection conn, Bundle args) throws RemoteException {
                Parcel data = Parcel.obtain(); Parcel reply = Parcel.obtain();
                try { data.writeInterfaceToken(DESCRIPTOR); data.writeStrongBinder(conn != null ? conn.asBinder() : null); if (args != null) { data.writeInt(1); args.writeToParcel(data, 0); } else { data.writeInt(0); } mRemote.transact(12, data, reply, 0); reply.readException(); return reply.readInt(); }
                finally { data.recycle(); reply.recycle(); }
            }
            @Override public void requestPermission(int requestCode) throws RemoteException {
                Parcel data = Parcel.obtain(); Parcel reply = Parcel.obtain();
                try { data.writeInterfaceToken(DESCRIPTOR); data.writeInt(requestCode); mRemote.transact(14, data, reply, 0); reply.readException(); }
                finally { data.recycle(); reply.recycle(); }
            }
            @Override public boolean checkSelfPermission() throws RemoteException {
                Parcel data = Parcel.obtain(); Parcel reply = Parcel.obtain();
                try { data.writeInterfaceToken(DESCRIPTOR); mRemote.transact(15, data, reply, 0); reply.readException(); return reply.readInt() != 0; }
                finally { data.recycle(); reply.recycle(); }
            }
            @Override public boolean shouldShowRequestPermissionRationale() throws RemoteException {
                Parcel data = Parcel.obtain(); Parcel reply = Parcel.obtain();
                try { data.writeInterfaceToken(DESCRIPTOR); mRemote.transact(16, data, reply, 0); reply.readException(); return reply.readInt() != 0; }
                finally { data.recycle(); reply.recycle(); }
            }
            @Override public void attachApplication(IHypersApplication application, Bundle args) throws RemoteException {
                Parcel data = Parcel.obtain(); Parcel reply = Parcel.obtain();
                try { data.writeInterfaceToken(DESCRIPTOR); data.writeStrongBinder(application != null ? application.asBinder() : null); if (args != null) { data.writeInt(1); args.writeToParcel(data, 0); } else { data.writeInt(0); } mRemote.transact(17, data, reply, 0); reply.readException(); }
                finally { data.recycle(); reply.recycle(); }
            }
            @Override public void exit() throws RemoteException {
                Parcel data = Parcel.obtain(); Parcel reply = Parcel.obtain();
                try { data.writeInterfaceToken(DESCRIPTOR); mRemote.transact(100, data, reply, 0); reply.readException(); }
                finally { data.recycle(); reply.recycle(); }
            }
            @Override public void attachUserService(IBinder binder, Bundle options) throws RemoteException {
                Parcel data = Parcel.obtain(); Parcel reply = Parcel.obtain();
                try { data.writeInterfaceToken(DESCRIPTOR); data.writeStrongBinder(binder); if (options != null) { data.writeInt(1); options.writeToParcel(data, 0); } else { data.writeInt(0); } mRemote.transact(101, data, reply, 0); reply.readException(); }
                finally { data.recycle(); reply.recycle(); }
            }
            @Override public void dispatchPackageChanged(Intent intent) throws RemoteException {
                Parcel data = Parcel.obtain();
                try { data.writeInterfaceToken(DESCRIPTOR); if (intent != null) { data.writeInt(1); intent.writeToParcel(data, 0); } else { data.writeInt(0); } mRemote.transact(102, data, null, IBinder.FLAG_ONEWAY); }
                finally { data.recycle(); }
            }
            @Override public boolean isHidden(int uid) throws RemoteException {
                Parcel data = Parcel.obtain(); Parcel reply = Parcel.obtain();
                try { data.writeInterfaceToken(DESCRIPTOR); data.writeInt(uid); mRemote.transact(103, data, reply, 0); reply.readException(); return reply.readInt() != 0; }
                finally { data.recycle(); reply.recycle(); }
            }
            @Override public void dispatchPermissionConfirmationResult(int requestUid, int requestPid, int requestCode, Bundle data2) throws RemoteException {
                Parcel data = Parcel.obtain();
                try { data.writeInterfaceToken(DESCRIPTOR); data.writeInt(requestUid); data.writeInt(requestPid); data.writeInt(requestCode); if (data2 != null) { data.writeInt(1); data2.writeToParcel(data, 0); } else { data.writeInt(0); } mRemote.transact(104, data, null, IBinder.FLAG_ONEWAY); }
                finally { data.recycle(); }
            }
            @Override public int getFlagsForUid(int uid, int mask) throws RemoteException {
                Parcel data = Parcel.obtain(); Parcel reply = Parcel.obtain();
                try { data.writeInterfaceToken(DESCRIPTOR); data.writeInt(uid); data.writeInt(mask); mRemote.transact(105, data, reply, 0); reply.readException(); return reply.readInt(); }
                finally { data.recycle(); reply.recycle(); }
            }
            @Override public void updateFlagsForUid(int uid, int mask, int value) throws RemoteException {
                Parcel data = Parcel.obtain(); Parcel reply = Parcel.obtain();
                try { data.writeInterfaceToken(DESCRIPTOR); data.writeInt(uid); data.writeInt(mask); data.writeInt(value); mRemote.transact(106, data, reply, 0); reply.readException(); }
                finally { data.recycle(); reply.recycle(); }
            }
        }
    }
}
