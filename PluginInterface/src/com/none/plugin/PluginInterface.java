package com.none.plugin;

import android.app.Activity;
import android.content.Context;
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
	
}
