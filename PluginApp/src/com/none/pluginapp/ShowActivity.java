package com.none.pluginapp;


import com.none.plugininterface.PluginBaseActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShowActivity extends PluginBaseActivity implements View.OnClickListener{
	Button button;
	@Override
	protected void onCreate(Bundle savedInstanceSBundltate) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceSBundltate);
		setContentView(R.layout.activity_show);
	}
	@Override
	public void onClick(View v) {
		
	}
}
