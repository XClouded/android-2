package com.none.pluginhost;

import com.none.plugininterface.ContextProxy;
import com.none.plugininterface.PluginInterface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class PluginInterfaceImpl extends PluginInterfaceWrapper {
	PluginInterface mPluginInterface;

	PluginInterfaceImpl(PluginInterface pluginInterface) {
		mPluginInterface = pluginInterface;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mPluginInterface._onCreate(savedInstanceState);
	}

	@Override
	public void onStart() {
		mPluginInterface._onStart();
	}

	@Override
	public boolean init(ContextProxy<Activity> contextProxy) {
		return mPluginInterface.init(contextProxy);
	}

	@Override
	public void onStop() {
		mPluginInterface._onStop();
	}

	@Override
	public void onResume() {
		mPluginInterface._onResume();
	}

	@Override
	public void onRestart() {
		mPluginInterface._onRestart();
	}

	@Override
	public void onPause() {
		mPluginInterface._onPause();
	}

	@Override
	public void onDestroy() {
		mPluginInterface._onDestroy();
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		mPluginInterface._onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public void onNewIntent(Intent intent) {
		mPluginInterface._onNewIntent(intent);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		mPluginInterface._onSaveInstanceState(outState);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		mPluginInterface._onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onBackPressed() {
		mPluginInterface._onBackPressed();
	}
	

}
