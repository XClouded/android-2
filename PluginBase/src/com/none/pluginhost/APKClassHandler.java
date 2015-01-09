package com.none.pluginhost;

import android.content.Context;

import com.none.pluginhost.ProxyAcivity.ProxyInfo;

import dalvik.system.DexClassLoader;

public class APKClassHandler<R extends Object> implements IAPKClassHandler<R>{
	private Context mContext;
	public APKClassHandler(Context context){
		mContext=context;
	}
	
	@Override
	public R reflectClass(ProxyInfo proxyInfo) throws RuntimeException{
		
		
		
		return null;
	}
	
	/*package*/ DexClassLoader 
	
}
