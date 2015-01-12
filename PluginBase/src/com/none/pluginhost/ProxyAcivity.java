package com.none.pluginhost;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class ProxyAcivity extends Activity {

	public static final String TAG = "ProxyAcivity";
	public static final boolean DEBUG = false;
	public static final String PROXY = "proxy_obj";

	PluginInterfaceWrapper mPluginInterface;
	private ProxyInfo mProxyInfo;
	APKClassHandler mClassHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		ProxyInfo proxyInfo = intent.getParcelableExtra(PROXY);

		if (proxyInfo == null) {
			proxyInfo = savedInstanceState.getParcelable(PROXY);
			if (proxyInfo == null) {
				if (DEBUG) {
					Log.d(TAG, "no proxyInfo finish activity");
				}
			}
		}
		mProxyInfo = proxyInfo;
		if (DEBUG) {
			mProxyInfo = new ProxyInfo();
			mProxyInfo.mApkName = "pluginApp";
			mProxyInfo.mApkPath = "/plugin/pluginApp.apk";
			mProxyInfo.mProxyClassName = "com.none.pluginapp.HomeActivity";
		}
		mClassHandler = new APKClassHandler(mProxyInfo, this);
		try {
			mPluginInterface = mClassHandler.reflectPluginInterface();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (mPluginInterface != null) {
			mPluginInterface.onCreate(savedInstanceState);
		}
	}
	//重写activityforresult来实现代理跳转。
	@SuppressLint("Override")
	public void startActivityForResult(Intent intent, int requestCode) {
		
		//1.代理对象跳转到非代理对象的情况。替换packageName,通过manager进行检测
		PackageManager manager=getPackageManager();
		final ComponentName componentProxy=new ComponentName(getPackageName(), intent.getComponent().getClassName());
		final Intent intentTest=new Intent();
		intentTest.setComponent(componentProxy);
		final ResolveInfo resolveInfo=manager.resolveActivity(intentTest,PackageManager.GET_INTENT_FILTERS);
		if(resolveInfo!=null){
			super.startActivityForResult(intentTest, requestCode);
			return;
		}
		//2.代理对象跳转到被代理的apk自己的activity，不需要手动设置proxyInfo只需要指定className即可
		final Intent proxy=intent;
		ProxyInfo proxyInfo=intent.getParcelableExtra(PROXY);
		if(proxyInfo==null){
			proxyInfo=(ProxyInfo) mProxyInfo.clone();
			proxyInfo.mProxyClassName=proxy.getComponent().getClassName();
			proxy.putExtra(PROXY, proxyInfo);
			proxy.setClass(this,ProxyAcivity.class);
			super.startActivityForResult(proxy, requestCode);
			return;
		}
		//3.跳转到另外一个apk中的activity，暂时不实现
		
		super.startActivityForResult(proxy, requestCode);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(mPluginInterface!=null){
			mPluginInterface.onActivityResult(requestCode, resultCode, data);
		}
	}
	
	@Override
	protected void onStart() {
		if(mPluginInterface!=null){
			mPluginInterface.onStart();
		}
		super.onStart();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		if(mPluginInterface!=null){
			mPluginInterface.onSaveInstanceState(outState);
		}
		super.onSaveInstanceState(outState);
		outState.putParcelable(PROXY, mProxyInfo);
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if(mPluginInterface!=null){
			mPluginInterface.onRestoreInstanceState(savedInstanceState);
		}
		super.onRestoreInstanceState(savedInstanceState);
	}
	@Override
	protected void onNewIntent(Intent intent) {
		if(mPluginInterface!=null){
			mPluginInterface.onNewIntent(intent);
		}
	}
	@Override
	protected void onRestart() {
		if(mPluginInterface!=null){
			mPluginInterface.onRestart();
		}
		super.onRestart();
	}
	@Override
	protected void onResume() {
		if(mPluginInterface!=null){
			mPluginInterface.onResume();
		}
		super.onResume();
	}
	@Override
	protected void onPause() {
		if(mPluginInterface!=null){
			mPluginInterface.onPause();
		}
		super.onPause();
	}
	@Override
	protected void onStop() {
		if(mPluginInterface!=null)
			mPluginInterface.onStop();
		super.onStop();
	}
	@Override
	protected void onDestroy() {
		if(mPluginInterface!=null){
			mPluginInterface.onDestroy();
		}
		super.onDestroy();
	}
	//不能调用父类的onBackPressed，会引起问题。交给被代理对象调用。
	@Override
	public void onBackPressed() {
		if(mPluginInterface!=null){
			mPluginInterface.onBackPressed();
			return;
		}
		super.onBackPressed();
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}
}
