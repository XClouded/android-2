package com.none.pluginhost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{
	Button jump;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		jump=(Button) findViewById(R.id.jump);
		jump.setOnClickListener(this);
	}
	public void onClick(View view){
		switch (view.getId()) {
			case R.id.jump:
				ProxyInfo proxyInfo=new ProxyInfo();
				proxyInfo.mApkName="pluginApp";
				proxyInfo.mApkPath="/plugin/pluginApp.apk";
				proxyInfo.mProxyClassName="com.none.pluginapp.HomeActivity";
				Intent intent=new Intent(this,ProxyAcivity.class);
				intent.putExtra(ProxyAcivity.PROXY, proxyInfo);
				startActivity(intent);
				break;

			default:
				break;
		}
	}
}
