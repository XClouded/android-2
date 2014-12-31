package com.qiqi.clientbinder;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;

import com.qiqi.binder.ICallback;
import com.qiqi.binder.IRemoteService;
import com.qiqi.util.BaseAndroidLogTemp;

public class MainActivity extends Activity {
	
	TextView textView;
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			
		};
	};
	private MyCallback callback;
	IRemoteService iRemoteService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView=(TextView) findViewById(R.id.show);
		callback=new MyCallback();
	}
	public void onClick(View view){
		switch (view.getId()) {
			case R.id.bind:
				
				 if(!bindService(new Intent("com.qiqi.binder.REMOTESERVICE"), new MyConnection(), BIND_AUTO_CREATE))
					 BaseAndroidLogTemp.debug("not bind");
				break;
			default:
				break;
		}
	}
	public class MyConnection implements ServiceConnection{

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			IRemoteService iRemoteService=IRemoteService.Stub.asInterface(service);
			BaseAndroidLogTemp.debug("client: rRemoteService  "+iRemoteService.toString());
			BaseAndroidLogTemp.debug("client: ibinder "+service);
			try {
				iRemoteService.registerCallback(callback);
			}catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			if(iRemoteService!=null){
				try {
					iRemoteService.unregisterCallback(callback);
				}catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public class MyCallback extends ICallback.Stub{
		@Override
		public void syncToken(String token) throws RemoteException {
			final String mToken=token;
			handler.post(new Runnable() {
				@Override
				public void run() {
					textView.setText(mToken);
				}
			});
		}
		
	}
}
