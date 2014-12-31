package com.qiqi.binder;

import android.os.Binder;
import android.os.Parcel;
import android.os.RemoteException;

public class LocalBinder extends Binder {
	@Override
	protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
		// TODO Auto-generated method stub
		return super.onTransact(code, data, reply, flags);
	}
}
