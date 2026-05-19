package com.hypers.hm.server;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

public interface IRemoteProcess extends IInterface {

    ParcelFileDescriptor getOutputStream() throws RemoteException;

    ParcelFileDescriptor getInputStream() throws RemoteException;

    ParcelFileDescriptor getErrorStream() throws RemoteException;

    int waitFor() throws RemoteException;

    int exitValue() throws RemoteException;

    void destroy() throws RemoteException;

    boolean alive() throws RemoteException;

    boolean waitForTimeout(long timeout, String unit)
            throws RemoteException;
            
    String exec(String command) throws RemoteException;

    abstract class Stub extends Binder implements IRemoteProcess {

        private static final String DESCRIPTOR =
                "com.hypers.hm.server.IRemoteProcess";

        static final int TRANSACTION_getOutputStream = 1;
        static final int TRANSACTION_getInputStream = 2;
        static final int TRANSACTION_getErrorStream = 3;
        static final int TRANSACTION_waitFor = 4;
        static final int TRANSACTION_exitValue = 5;
        static final int TRANSACTION_destroy = 6;
        static final int TRANSACTION_alive = 7;
        static final int TRANSACTION_waitForTimeout = 8;
        static final int TRANSACTION_exec = 9;

        public Stub() {

            attachInterface(this, DESCRIPTOR);

        }

        public static IRemoteProcess asInterface(IBinder obj) {

            if (obj == null)
                return null;

            IInterface iin =
                    obj.queryLocalInterface(DESCRIPTOR);

            if (iin instanceof IRemoteProcess)
                return (IRemoteProcess) iin;

            return new Proxy(obj);
        }

        @Override
        public IBinder asBinder() {

            return this;

        }

        @Override
        protected boolean onTransact(
                int code,
                Parcel data,
                Parcel reply,
                int flags
        ) throws RemoteException {

            switch (code) {

                case INTERFACE_TRANSACTION: {

                    reply.writeString(DESCRIPTOR);

                    return true;
                }

                case TRANSACTION_getOutputStream: {

                    data.enforceInterface(DESCRIPTOR);

                    ParcelFileDescriptor result =
                            this.getOutputStream();

                    reply.writeNoException();

                    if (result != null) {

                        reply.writeInt(1);

                        result.writeToParcel(
                                reply,
                                android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE
                        );

                    } else {

                        reply.writeInt(0);

                    }

                    return true;
                }

                case TRANSACTION_getInputStream: {

                    data.enforceInterface(DESCRIPTOR);

                    ParcelFileDescriptor result =
                            this.getInputStream();

                    reply.writeNoException();

                    if (result != null) {

                        reply.writeInt(1);

                        result.writeToParcel(
                                reply,
                                android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE
                        );

                    } else {

                        reply.writeInt(0);

                    }

                    return true;
                }

                case TRANSACTION_getErrorStream: {

                    data.enforceInterface(DESCRIPTOR);

                    ParcelFileDescriptor result =
                            this.getErrorStream();

                    reply.writeNoException();

                    if (result != null) {

                        reply.writeInt(1);

                        result.writeToParcel(
                                reply,
                                android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE
                        );

                    } else {

                        reply.writeInt(0);

                    }

                    return true;
                }

                case TRANSACTION_waitFor: {

                    data.enforceInterface(DESCRIPTOR);

                    int result = this.waitFor();

                    reply.writeNoException();
                    reply.writeInt(result);

                    return true;
                }

                case TRANSACTION_exitValue: {

                    data.enforceInterface(DESCRIPTOR);

                    int result = this.exitValue();

                    reply.writeNoException();
                    reply.writeInt(result);

                    return true;
                }

                case TRANSACTION_destroy: {

                    data.enforceInterface(DESCRIPTOR);

                    this.destroy();

                    reply.writeNoException();

                    return true;
                }
                
                case TRANSACTION_alive: {

                    data.enforceInterface(DESCRIPTOR);

                    boolean result = this.alive();

                    reply.writeNoException();
                    reply.writeInt(result ? 1 : 0);

                    return true;
                }

                case TRANSACTION_waitForTimeout: {

                    data.enforceInterface(DESCRIPTOR);

                    long timeout = data.readLong();
                    String unit = data.readString();

                    boolean result =
                            this.waitForTimeout(timeout, unit);

                    reply.writeNoException();
                    reply.writeInt(result ? 1 : 0);

                    return true;
                }

                case TRANSACTION_exec: {

                    data.enforceInterface(DESCRIPTOR);

                    String command = data.readString();

                    String result = this.exec(command);

                    reply.writeNoException();
                    reply.writeString(result);

                    return true;
                }

            }

            return super.onTransact(
                    code,
                    data,
                    reply,
                    flags
            );
        }

        private static class Proxy implements IRemoteProcess {

            private final IBinder mRemote;

            Proxy(IBinder remote) {

                mRemote = remote;

            }

            @Override
            public IBinder asBinder() {

                return mRemote;

            }

            @Override
            public ParcelFileDescriptor getOutputStream()
                    throws RemoteException {

                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();

                try {

                    data.writeInterfaceToken(DESCRIPTOR);

                    mRemote.transact(
                            TRANSACTION_getOutputStream,
                            data,
                            reply,
                            0
                    );

                    reply.readException();

                    return reply.readInt() != 0
                            ? ParcelFileDescriptor.CREATOR
                            .createFromParcel(reply)
                            : null;

                } finally {

                    data.recycle();
                    reply.recycle();

                }
            }

            @Override
            public ParcelFileDescriptor getInputStream()
                    throws RemoteException {

                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();

                try {

                    data.writeInterfaceToken(DESCRIPTOR);

                    mRemote.transact(
                            TRANSACTION_getInputStream,
                            data,
                            reply,
                            0
                    );

                    reply.readException();

                    return reply.readInt() != 0
                            ? ParcelFileDescriptor.CREATOR
                            .createFromParcel(reply)
                            : null;

                } finally {

                    data.recycle();
                    reply.recycle();

                }
            }

            @Override
            public ParcelFileDescriptor getErrorStream()
                    throws RemoteException {

                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();

                try {

                    data.writeInterfaceToken(DESCRIPTOR);

                    mRemote.transact(
                            TRANSACTION_getErrorStream,
                            data,
                            reply,
                            0
                    );

                    reply.readException();

                    return reply.readInt() != 0
                            ? ParcelFileDescriptor.CREATOR
                            .createFromParcel(reply)
                            : null;

                } finally {

                    data.recycle();
                    reply.recycle();

                }
            }

            @Override
            public int waitFor()
                    throws RemoteException {

                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();

                try {

                    data.writeInterfaceToken(DESCRIPTOR);

                    mRemote.transact(
                            TRANSACTION_waitFor,
                            data,
                            reply,
                            0
                    );

                    reply.readException();

                    return reply.readInt();

                } finally {

                    data.recycle();
                    reply.recycle();

                }
            }

            @Override
            public int exitValue()
                    throws RemoteException {

                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();

                try {

                    data.writeInterfaceToken(DESCRIPTOR);

                    mRemote.transact(
                            TRANSACTION_exitValue,
                            data,
                            reply,
                            0
                    );

                    reply.readException();

                    return reply.readInt();

                } finally {

                    data.recycle();
                    reply.recycle();

                }
            }

            @Override
            public void destroy()
                    throws RemoteException {

                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();

                try {

                    data.writeInterfaceToken(DESCRIPTOR);

                    mRemote.transact(
                            TRANSACTION_destroy,
                            data,
                            reply,
                            0
                    );

                    reply.readException();

                } finally {

                    data.recycle();
                    reply.recycle();

                }
            }

            @Override
            public boolean alive()
                    throws RemoteException {

                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();

                try {

                    data.writeInterfaceToken(DESCRIPTOR);

                    mRemote.transact(
                            TRANSACTION_alive,
                            data,
                            reply,
                            0
                    );

                    reply.readException();

                    return reply.readInt() != 0;

                } finally {

                    data.recycle();
                    reply.recycle();

                }
            }

            @Override
            public boolean waitForTimeout(
                    long timeout,
                    String unit
            ) throws RemoteException {

                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();

                try {

                    data.writeInterfaceToken(DESCRIPTOR);

                    data.writeLong(timeout);
                    data.writeString(unit);

                    mRemote.transact(
                            TRANSACTION_waitForTimeout,
                            data,
                            reply,
                            0
                    );

                    reply.readException();

                    return reply.readInt() != 0;

                } finally {

                    data.recycle();
                    reply.recycle();

                }
            }

            @Override
            public String exec(String command)
                    throws RemoteException {

                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();

                try {

                    data.writeInterfaceToken(DESCRIPTOR);

                    data.writeString(command);

                    mRemote.transact(
                            TRANSACTION_exec,
                            data,
                            reply,
                            0
                    );

                    reply.readException();

                    return reply.readString();

                } finally {

                    data.recycle();
                    reply.recycle();

                }
            }
        }
    }
}