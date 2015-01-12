package com.none.plugininterface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public abstract class  PluginInterface extends Activity{
	
	public  abstract boolean init(ContextProxy<Activity> contextProxy);
	public final void _onCreate(Bundle savedInstanceState){
		this.onCreate(savedInstanceState);
	}
	public final void _onStop(){
		this.onStop();
	}
	public final void _onStart(){
		this.onStart();
	}
	public final void _onResume(){
		this.onResume();
	}
	public final void _onRestart(){
		this.onRestart();
	}
	public final void _onPause(){
		this.onResume();
	}
	public final void _onDestroy(){
		this.onDestroy();
	}
	public final void _onRestoreInstanceState(Bundle savedInstanceState){
		this.onRestoreInstanceState(savedInstanceState);
	}
	public final void _onNewIntent(Intent intent){
		this.onNewIntent(intent);
	}
	public final void _onSaveInstanceState(Bundle outState){
		this.onSaveInstanceState(outState);
	}
	public final void _onActivityResult(int requestCode, int resultCode, Intent data){
		this.onActivityResult(requestCode, resultCode, data);
	}
	public final void _onBackPressed(){
		this.onBackPressed();
	}
}
