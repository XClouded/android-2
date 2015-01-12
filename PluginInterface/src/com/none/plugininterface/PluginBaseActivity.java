package com.none.plugininterface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.none.plugininterface.ContextProxy;
import com.none.plugininterface.PluginInterface;


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
	//由于setResult等方法是final类型不能覆盖，所以所有的子类不在调用Activity默认的setResult方法，调用setProxyOrRealResult;
	
	public final void setProxyOrRealResult(int resultCode){
		if(isProxyed){
			mContextProxy.getProxy().setResult(resultCode);
			return;
		}
		
		setResult(resultCode);
	}
	
	public final void setProxyOrRealResult(int resultCode ,Intent data){
		if(isProxyed){
			mContextProxy.getProxy().setResult(resultCode, data);
			return;
		}
		setResult(resultCode, data);
	}
	
	
	@Override
	public final boolean init(ContextProxy<Activity> contextProxy) {
		this.mContextProxy=contextProxy;
		isProxyed=true;
		mInflater=LayoutInflater.from(mContextProxy.getHostContext());
		attachBaseContext(contextProxy.getProxy().getBaseContext());
		return false;
	}
	
	
	public void log(String message,Object... args){
		message=String.format(message, args);
		Log.d(TAG,message);
	}
	
	@Override
	protected void onResume() {
		if(isProxyed){
			return;
		}
		super.onResume();
	}
	@Override
	protected void onPause() {
		if(isProxyed){
			return;
		}
		super.onPause();
	}
	@Override
	protected void onRestart() {
		if(isProxyed){
			return;
		}
		super.onRestart();
	}
	@Override
	protected void onStart() {
		if(isProxyed){
			return;
		}
		super.onStart();
	}
	@Override
	public void finish() {
		if(isProxyed){
			mContextProxy.getProxy().finish();
			return;
		}
		super.finish();
	}
	
//	@Override
//	public void finishActivity(int requestCode) {
//		if(isProxyed){
//			
//		}
//		super.finishActivity(requestCode);
//	}
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(isProxyed){
			return;
		}
	}
	
	@Override
	public View findViewById(int id) {
		if(isProxyed){
			return mContextProxy.getProxy().findViewById(id);
		}
		return super.findViewById(id);
	}
	/*@Override
	public Context getBaseContext() {
		if(isProxyed){
			return mContextProxy.getProxy().getBaseContext();
		}
		return super.getBaseContext();
	}*/
	@Override
	public AssetManager getAssets() {
		if(isProxyed){
			return mContextProxy.getProxyAssetManager();
		}
		return super.getAssets();
	}
	@Override
	public Resources getResources() {
		if(isProxyed){
			return mContextProxy.getProxyResource();
		}
		return super.getResources();
	}
	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		if(isProxyed){
			mContextProxy.getProxy().startActivityForResult(intent, requestCode);
			return;
		}
		super.startActivityForResult(intent, requestCode);
	}
	@Override
	public void startActivity(Intent intent) {
		if(isProxyed){
			startActivityForResult(intent, -1);
			return ;
		}
		super.startActivity(intent);
	}
	@Override
	protected void onNewIntent(Intent intent) {
		if(isProxyed){
			return;
		}
		super.onNewIntent(intent);
	}
	//因为onbackPressed事件比较特殊，所以目前是实现为最简单的关闭当前的proxyActivity
	@Override
	public void onBackPressed() {
		if(isProxyed){
			mContextProxy.getProxy().finish();
			return;
		}
		super.onBackPressed();
	}
	@Override
	protected void onDestroy() {
		if(isProxyed){
			return;
		}
		super.onDestroy();
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		if(isProxyed){
			return;
		}
		super.onSaveInstanceState(outState);
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if(isProxyed){
			return;
		}
		super.onRestoreInstanceState(savedInstanceState);
	}
}
