package com.none.pluginhost;

import com.none.plugininterface.PluginInterface;

import android.content.Intent;
import android.os.Bundle;


public abstract class  PluginInterfaceWrapper extends PluginInterface{
	public abstract void  onCreate(Bundle savedInstanceState);
	public abstract void  onStart();
	public abstract void  onStop();
	public abstract void onResume();
	public abstract void onRestart();
	public abstract void onPause();
	public abstract void onDestroy();
	public abstract void onRestoreInstanceState(Bundle savedInstanceState);
	public abstract void onNewIntent(Intent intent);
	public abstract void onSaveInstanceState(Bundle outState);
	public abstract void onActivityResult(int requestCode, int resultCode, Intent data);
	public abstract void onBackPressed();
	
}
