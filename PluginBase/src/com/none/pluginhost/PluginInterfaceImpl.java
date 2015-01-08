package com.none.pluginhost;

import android.app.Activity;
import android.os.Bundle;

import com.none.plugin.ContextProxy;
import com.none.plugin.PluginInterface;

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

}
