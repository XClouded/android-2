package com.qiqi.binder;
import com.qiqi.binder.ICallback;
interface IRemoteService{
	void registerCallback(ICallback callback);
	void unregisterCallback(ICallback callback);
}