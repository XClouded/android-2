package com.none.pluginapp;


import com.none.plugininterface.PluginBaseActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForResultActivity extends PluginBaseActivity implements View.OnClickListener{
	Button button;
	public static final int RESULT_CODE=3;
	@Override
	protected void onCreate(Bundle savedInstanceSBundltate) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceSBundltate);
		setContentView(R.layout.activity_forresult);
		button=(Button) findViewById(R.id.btn_back);
		button.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_back:
				Intent data=new Intent();
				data.putExtra("key","value");
				setResult(Activity.RESULT_OK,data);
				finish();
				break;
				
			default:
				break;
		}
	}
}
