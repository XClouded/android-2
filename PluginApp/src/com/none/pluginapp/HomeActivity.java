package com.none.pluginapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends PluginBaseActivity implements View.OnClickListener{
	Button button;
	@Override
	protected void onCreate(Bundle savedInstanceSBundltate) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceSBundltate);
		setContentView(R.layout.activity_main_proxy);
		button=(Button) findViewById(R.id.btn);
		button.setOnClickListener(this);
	}
	
	
	
	@Override
	public void onClick(View v) {
		Toast.makeText(getBaseContext(),"回调",Toast.LENGTH_LONG).show();
	}
}
