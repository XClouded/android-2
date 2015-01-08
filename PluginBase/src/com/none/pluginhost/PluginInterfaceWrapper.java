package com.none.pluginhost;

import android.os.Bundle;

import com.none.plugin.PluginInterface;

public abstract class  PluginInterfaceWrapper extends PluginInterface{
	public abstract void  onCreate(Bundle savedInstanceState);
	public abstract void  onStart();
	public abstract void onStop();
}
