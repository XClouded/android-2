package com.qiqi.binder;

import java.util.Random;

import com.qiqi.binder.IRemoteService.Stub;
import com.qiqi.util.BaseAndroidLogTemp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

public class ServiceLocal extends Service {

	private RemoteService remoteService;
	private RemoteCallbackList<ICallback> remoteCallbackList;
	public static final char[] chars = "1234567890ABCDEFGHIGKLMNOPQRSTUVWXYZabcdefghigklmnopqrstuvwxyz".toCharArray();

	public static final int TOKEN_REFRESH_TIME = 3000;
	public static final int mTokenLength=5;
	public static final int DISPATCH_TOKEN=1;
	//在另外的县城里处理。
	private HandlerThread handlerThread;
	private Handler handler;
	private Random random;
	private String mToken;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		remoteCallbackList = new RemoteCallbackList<>();
		remoteService = new RemoteService();
		BaseAndroidLogTemp.debug("remote service :"+remoteService.toString());
		handlerThread = new HandlerThread("remoteService");
		random = new Random(System.currentTimeMillis());
		handlerThread.start();
		handler = new Handler(handlerThread.getLooper()) {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				mToken=newToken();
				BaseAndroidLogTemp.debug("remote service token :"+mToken);
				triggerCallback();
				handler.sendEmptyMessageDelayed(DISPATCH_TOKEN, TOKEN_REFRESH_TIME);
			}

		};
		
		handler.sendEmptyMessage(DISPATCH_TOKEN);
	}

	private String newToken(){
		StringBuffer buffer=new StringBuffer();
		for(int i=0;i<mTokenLength;i++){
			buffer.append(chars[random.nextInt(chars.length)]);
		}
		return buffer.toString();
	}

	private void triggerCallback() {
		int index = remoteCallbackList.beginBroadcast();
		while (index > 0) {
			index--;
			try {
				remoteCallbackList.getBroadcastItem(index).syncToken(mToken);
			}
			catch (RemoteException e) {
				e.printStackTrace();
				break;
			}
		}
		remoteCallbackList.finishBroadcast();
	}
	  int  index=0;
	
	@Override
	public IBinder onBind(Intent intent) {
		//此处不用volatile,防止输出信息有误。
		synchronized (this) {
			index++;
			BaseAndroidLogTemp.debug("connect remote server: client number is "+index);
		}
		return remoteService;
	}

	public class RemoteService extends Stub {
		public RemoteService() {
			// TODO Auto-generated constructor stub
			super();
		}

		@Override
		public void registerCallback(ICallback callback) throws RemoteException {
			remoteCallbackList.register(callback);
		}

		@Override
		public void unregisterCallback(ICallback callback) throws RemoteException {
			// TODO Auto-generated method stub
			remoteCallbackList.unregister(callback);
		}

	}
}
