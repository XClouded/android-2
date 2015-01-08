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
import android.util.DisplayMetrics;

public class ProxyAcivity extends Activity {

	public static final boolean DEBUG = true;

	PluginInterfaceWrapper mPluginInterface;
	private String mProxyClassName;
	private String mApkPath;
	private String mApkPathName;
	private String mApkName;
	private Class mProxyClass;
	private DexClassLoader mDexClassLoader;
	private ContextProxy<Activity> mContextProxy;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
//			获取
//			mProxyClassName=intent.getStringExtra("ClassName");
		if (DEBUG) {
			mProxyClassName = "com.none.pluginapp.HomeActivity";
			mApkPath = "/plugin/pluginApp.apk";
			mApkName = "pluginApp";
			
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
}
