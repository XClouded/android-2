package com.none.pluginhost;

import android.os.Parcel;
import android.os.Parcelable;

public  class ProxyInfo implements Parcelable,Cloneable {
	public static final Parcelable.Creator<ProxyInfo> CREATOR=new Creator<ProxyInfo>() {
		
		@Override
		public ProxyInfo[] newArray(int size) {
			return null;
		}
		
		@Override
		public ProxyInfo createFromParcel(Parcel source) {
			ProxyInfo proxyInfo = new ProxyInfo();
			proxyInfo.mProxyClassName = source.readString();
			proxyInfo.mApkPath = source.readString();
			proxyInfo.mApkName = source.readString();
			return proxyInfo;
		}
	};
	public String mProxyClassName;
	public String mApkPath;
	public String mApkName;
	public ProxyInfo(){}
	public ProxyInfo(ProxyInfo proxyInfo){
		mProxyClassName=proxyInfo.mProxyClassName;
		mApkPath=proxyInfo.mApkPath;
		mApkName=proxyInfo.mApkName;
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	protected Object clone() {
		return new ProxyInfo(this);
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mProxyClassName);
		dest.writeString(mApkPath);
		dest.writeString(mApkName);
	}

//	public static class CREATOR implements Parcelable.Creator<ProxyInfo> {
//
//		@Override
//		public ProxyInfo createFromParcel(Parcel source) {
//			ProxyInfo proxyInfo = new ProxyInfo();
//			proxyInfo.mProxyClassName = source.readString();
//			proxyInfo.mApkPath = source.readString();
//			proxyInfo.mApkName = source.readString();
//			return proxyInfo;
//		}
//
//		@Override
//		public ProxyInfo[] newArray(int size) {
//			return null;
//		}
//
//	}
}