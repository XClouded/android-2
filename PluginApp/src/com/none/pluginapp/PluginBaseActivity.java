package com.none.pluginapp;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.none.plugin.ContextProxy;
import com.none.plugin.PluginInterface;


public  class PluginBaseActivity  extends PluginInterface{
	ContextProxy<Activity> mContextProxy;
	public static final String TAG="PluginBaseActivity";
	public static final boolean DEBUG=true;
	
	private boolean isProxyed;
	private LayoutInflater mInflater;
	
	public PluginBaseActivity() {
		super();
		isProxyed=false;
	}

	@Override
	public final boolean init(ContextProxy<Activity> contextProxy) {
		this.mContextProxy=contextProxy;
		isProxyed=true;
		mInflater=LayoutInflater.from(mContextProxy.getHostContext());
		return false;
	}
	
	
	public void log(String message,Object... args){
		message=String.format(message, args);
		Log.d(TAG,message);
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		if(isProxyed){
			return;
		}
		super.onStart();
	}
	@Override
	protected void onCreate(Bundle savedInstanceSBundltate) {
		if(isProxyed){
			if(DEBUG){
				log("mContextProxy context : %s",mContextProxy.getHostContext().toString());
				log("layoutInflater context : %s ",mInflater.getContext().toString());
			}
			return;
		}
		super.onCreate(savedInstanceSBundltate);
	}
	@Override
	public void setContentView(int layoutResID) {
		if(isProxyed){
			XmlResourceParser parser=mContextProxy.getProxyResource().getLayout(layoutResID);
			View view=mInflater.inflate(parser, null);
			setContentView(view);
			return;
		}
		super.setContentView(layoutResID);
		
	}
	@Override
	public void setContentView(View view) {
		// TODO Auto-generated method stub
		if(isProxyed){
			mContextProxy.getProxy().setContentView(view);
			return;
		}
		super.setContentView(view);
	}
	
	@Override
	protected void onStop() {
		if(isProxyed){
			return;
		}
		super.onStop();
	}
	
	@Override
	public View findViewById(int id) {
		// TODO Auto-generated method stub
		if(isProxyed){
			return mContextProxy.getProxy().findViewById(id);
		}
		return super.findViewById(id);
	}
	@Override
	public Context getBaseContext() {
		// TODO Auto-generated method stub
		if(isProxyed){
			return mContextProxy.getProxy().getBaseContext();
		}
		return super.getBaseContext();
	}
	@Override
	public AssetManager getAssets() {
		// TODO Auto-generated method stub
		if(isProxyed){
			return mContextProxy.getProxyAssetManager();
		}
		return super.getAssets();
	}
	@Override
	public Resources getResources() {
		// TODO Auto-generated method stub
		if(isProxyed){
			return mContextProxy.getProxyResource();
		}
		return super.getResources();
	}
	
}
