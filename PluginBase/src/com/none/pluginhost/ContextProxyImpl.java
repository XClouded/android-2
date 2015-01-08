package com.none.pluginhost;

import com.none.plugin.ContextProxy;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.view.ContextThemeWrapper;

public class ContextProxyImpl extends ContextThemeWrapper implements ContextProxy<Activity>{
	
	
	
	Activity mActivity;
	AssetManager mAssetManager;
	Resources mResources;
	public ContextProxyImpl(Activity activity,AssetManager assetManager,Resources resources){
//		super();
		super(activity.getBaseContext(),1);
		mActivity=activity;
		mAssetManager=assetManager;
		mResources=resources;
		
	}
	
	@Override
	public Context getHostContext() {
		return mActivity;
	}

	@Override
	public AssetManager getProxyAssetManager() {
		return mAssetManager;
	}

	@Override
	public Resources getProxyResource() {
		return mResources;
	}
	
	@Override
	public Activity getProxy() {
		return mActivity;
	}
	
}
