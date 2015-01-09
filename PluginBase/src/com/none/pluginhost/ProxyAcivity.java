package com.none.pluginhost;

import java.io.File;
import java.lang.reflect.Method;

import com.none.plugin.ContextProxy;
import com.none.plugin.PluginInterface;

import dalvik.system.DexClassLoader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;

public class ProxyAcivity extends Activity {

	public static final String TAG="ProxyAcivity";
	public static final boolean DEBUG = true;
	public static final String PROXY="proxy_obj";
	
	
	
	PluginInterfaceWrapper mPluginInterface;
	private Class mProxyClass;
	private ContextProxy<Activity> mContextProxy;
	private  ProxyInfo mProxyInfo;
	APKClassHandler<PluginInterface> mClassHandler;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		ProxyInfo proxyInfo=intent.getParcelableExtra(PROXY);
		
		if(proxyInfo==null){
			proxyInfo=savedInstanceState.getParcelable(PROXY);
			if(proxyInfo==null){
				if(DEBUG){
					Log.d(TAG,"no proxyInfo finish activity");
				}
			}
		}
		
		mProxyInfo=proxyInfo;
		if (DEBUG) {
			mProxyInfo=new ProxyInfo();
			mProxyInfo.mApkName="pluginApp";
			mProxyInfo.mApkPath="/plugin/pluginApp.apk";
			mProxyInfo.mProxyClassName="com.none.pluginapp.HomeActivity";
		}
		
		laodApk();
		loadClass();
		init();
		mPluginInterface.onCreate(savedInstanceState);
	}
	
	
	
	@Override
	protected void onStart() {
		mPluginInterface.onStart();
		super.onStart();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putParcelable(PROXY, mProxyInfo);
		
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
	}
	
	@Override
	protected void onStop() {
		mPluginInterface.onStop();
		super.onStop();
	}
	void init(){
		AssetManager assetManager = createAssetManager();
		Resources resources = createResource(assetManager, getResources());
		mContextProxy=new ContextProxyImpl(this, assetManager, resources);
		mPluginInterface.init(mContextProxy);
	}
	
	public boolean laodApk() {
		try {
			String dexPath = Environment.getExternalStorageDirectory() + mApkPath;
			File optimizedDirectory = getDir(mApkName + "_odex", Context.MODE_PRIVATE);
			mDexClassLoader = new DexClassLoader(dexPath, optimizedDirectory.getAbsolutePath(), null, getClassLoader());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	public boolean loadClass(){
		try {
			mProxyClass=mDexClassLoader.loadClass(mProxyClassName);
			PluginInterface pluginInterface=(PluginInterface) mProxyClass.newInstance();
			mPluginInterface=new PluginInterfaceImpl(pluginInterface);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	public AssetManager createAssetManager() {
		AssetManager assetManager = null;
		try {
			assetManager = AssetManager.class.newInstance();
			Method method = AssetManager.class.getMethod("addAssetPath", String.class);
			if (mApkPath != null) {
				method.invoke(assetManager, Environment.getExternalStorageDirectory() + mApkPath);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return assetManager;
	}

	public Resources createResource(AssetManager assetManager, Resources hostResource) {
		Resources resources = null;
		try {
			Configuration configuration = hostResource.getConfiguration();
			DisplayMetrics displayMetrics = hostResource.getDisplayMetrics();
			resources = new Resources(assetManager, displayMetrics, configuration);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return resources;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//不用对象了..
	public static class ProxyInfo implements Parcelable{
		public String mProxyClassName;
		public String mApkPath;
		public String mApkName;
		
		@Override
		public int describeContents() {
			return 0;
		}
		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeString(mProxyClassName);
			dest.writeString(mApkPath);
			dest.writeString(mApkName);
		}
		public static class CREATOR implements Parcelable.Creator<ProxyInfo>{

			@Override
			public ProxyInfo createFromParcel(Parcel source) {
				ProxyInfo proxyInfo=new ProxyInfo();
				proxyInfo.mProxyClassName=source.readString();
				proxyInfo.mApkPath=source.readString();
				proxyInfo.mApkName=source.readString();
				return proxyInfo;
			}
			@Override
			public ProxyInfo[] newArray(int size) {
				return null;
			}
			
		}
	}
}
