package com.none.pluginhost;

import java.io.File;
import java.lang.reflect.Method;

import com.none.plugininterface.ContextProxy;
import com.none.plugininterface.PluginInterface;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Environment;
import android.util.DisplayMetrics;


import dalvik.system.DexClassLoader;

public class APKClassHandler {
	String mAbsDexPath;
	String mApkOdexPath;
	ProxyInfo mProxyInfo;
	Context mContext;
	DexClassLoader mDexClassLoader;

	public APKClassHandler(ProxyInfo proxyInfo, Context context) {
		mProxyInfo = proxyInfo;
		mContext = context;
		mAbsDexPath = Environment.getExternalStorageDirectory() + proxyInfo.mApkPath;
		final File optimizedDirectory = context.getDir(proxyInfo.mApkName + "_odex", Context.MODE_PRIVATE);
		mApkOdexPath = optimizedDirectory.getAbsolutePath();

	}

	private void fireEx(Exception e, String message) throws Exception {
		Exception exception = new Exception(message, e);
		throw exception;
	}

	public Object reflectClass() throws Exception {
		reflectClassLoader();
		 Class c=null;
		 Object obj=null;
		try{
			c=mDexClassLoader.loadClass(mProxyInfo.mProxyClassName);
			obj=c.newInstance();
		}catch(Exception e){
			e.printStackTrace();
			fireEx(e,"反射class :"+mProxyInfo.mProxyClassName +" 失败");
		}
		return obj;
	}

	
	
	public ClassLoader reflectClassLoader() throws Exception {
		DexClassLoader dexClassLoader = null;
		Exception ex=null;
		if(mDexClassLoader!=null){
			return mDexClassLoader;
		}
		try {
			dexClassLoader = APKClassLoaderHolder.getInstance().getAPKClassLoader(mApkOdexPath);
			if (dexClassLoader == null) {
				dexClassLoader = new DexClassLoader(mAbsDexPath, mApkOdexPath, null, mContext.getClassLoader());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			ex=e;
		}
		if(dexClassLoader!=null){
			mDexClassLoader=dexClassLoader;
			APKClassLoaderHolder.getInstance().putAPKClassLoader(mApkOdexPath, mDexClassLoader);
		}
		if(ex!=null){
			fireEx(ex,"反射dexClassLoader类加载器  "+mApkOdexPath+" 失败...");
		}
		return mDexClassLoader;
	}

	public PluginInterfaceWrapper reflectPluginInterface() throws Exception {
		
		PluginInterfaceWrapper pluginInterfaceWrapper=null;
		try{
			pluginInterfaceWrapper=new PluginInterfaceImpl( (PluginInterface) reflectClass());
			initActivityPlugin(pluginInterfaceWrapper);
		}catch(Exception e){
			e.printStackTrace();
			fireEx(e, "反射pluginInterface 失败");
		}
		return pluginInterfaceWrapper;
	}
	
	private void initActivityPlugin(final PluginInterface pluginInterface)throws Exception{
		//生成assetmanager
		final AssetManager assetManager = AssetManager.class.newInstance();
		Method method = AssetManager.class.getMethod("addAssetPath", String.class);
		method.invoke(assetManager,mAbsDexPath);
		//生成resource
		Configuration configuration = mContext.getResources().getConfiguration();
		DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
		final Resources resources = new Resources(assetManager, displayMetrics, configuration);
		//生成theme
		ContextProxy<Activity> contextProxy=new ContextProxyImpl((Activity) mContext, assetManager, resources);
		pluginInterface.init(contextProxy);
	}
}
