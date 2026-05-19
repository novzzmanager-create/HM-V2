package com.hypers.hm.api;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Parcelable wrapper for an IBinder, used to pass the Hypers binder
 * through ContentProvider calls and Broadcasts.
 *
 * Package: com.hypers.hm
 */
public class BinderContainer implements Parcelable {

    public IBinder binder;

    public BinderContainer(IBinder binder) {
        this.binder = binder;
    }

    protected BinderContainer(Parcel in) {
        this.binder = in.readStrongBinder();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongBinder(this.binder);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BinderContainer> CREATOR = new Creator<BinderContainer>() {
        @Override
        public BinderContainer createFromParcel(Parcel source) {
            return new BinderContainer(source);
        }

        @Override
        public BinderContainer[] newArray(int size) {
            return new BinderContainer[size];
        }
    };
}
