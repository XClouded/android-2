package com.none.pluginapp;


import com.none.plugininterface.PluginBaseActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends PluginBaseActivity implements View.OnClickListener{
	Button button;
	Button buttonLocalActivity;
	Button buttonHostActivity;
	Button buttonForResultActivity;
	public static final int REQUEST_CODE=1;
	public static final String TAG="HomeActivity";
	@Override
	protected void onCreate(Bundle savedInstanceSBundltate) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceSBundltate);
		setContentView(R.layout.activity_main_proxy);
		button=(Button) findViewById(R.id.btn);
		button.setOnClickListener(this);
		buttonLocalActivity=(Button) findViewById(R.id.btnLocalActivity);
		buttonLocalActivity.setOnClickListener(this);
		buttonHostActivity=(Button) findViewById(R.id.btnHostActivity);
		buttonHostActivity.setOnClickListener(this);
		buttonForResultActivity=(Button) findViewById(R.id.btnLocalForResultActivity);
		buttonForResultActivity.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn:
				Toast.makeText(getBaseContext(),"回调",Toast.LENGTH_LONG).show();
				break;
			case R.id.btnHostActivity:
				Intent intent=new Intent();
				intent.putExtra("data", false);
				ComponentName componentName=new ComponentName(this, "com.none.pluginhost.ShowActivity");
				intent.setComponent(componentName);
				startActivity(intent);
				break;
			case R.id.btnLocalActivity:
				 intent=new Intent();
				intent.putExtra("data", false);
				intent.setClass(this,ShowActivity.class);
				startActivity(intent);
				break;
			case R.id.btnLocalForResultActivity:
				intent=new Intent();
				intent.setClass(this, ForResultActivity.class);
				startActivityForResult(intent, REQUEST_CODE);
				break;
			default:
				break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case REQUEST_CODE:
				if(resultCode==ForResultActivity.RESULT_CODE){
					LogUtil.debug(TAG,"返回结果为%s",data.getStringExtra("key"));
				}
				break;

			default:
				break;
		}
	}
	
}
