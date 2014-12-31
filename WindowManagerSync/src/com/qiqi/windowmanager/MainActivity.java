package com.qiqi.windowmanager;

import java.lang.reflect.Method;

import com.qiqi.util.BaseAndroidLogTemp;

import android.app.Activity;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		reflect();
//		WindowManager manager=getWindowManager();
//		BaseAndroidLogTemp.debug(manager.toString());
//		manager=(WindowManager) getApplicationContext().getSystemService(getApplication().WINDOW_SERVICE);
//		BaseAndroidLogTemp.debug(manager.toString());
//		ImageView imageView=new ImageView(this);
//		imageView.setBackgroundResource(R.drawable.ic_launcher);
//		WindowManager.LayoutParams layoutParams=new WindowManager.LayoutParams();
//		layoutParams.width=ViewGroup.LayoutParams.WRAP_CONTENT;
//		layoutParams.height=ViewGroup.LayoutParams.WRAP_CONTENT;
//		layoutParams.gravity=Gravity.LEFT|Gravity.CENTER_VERTICAL;
//		layoutParams.type= WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
//		layoutParams.x=30;
//		manager.addView(imageView, layoutParams);
	}
	public Object reflect(){
		MyBinder binder=new MyBinder();
		Object o=null;
		try {
			Class c=Class.forName("android.os.ServiceManager");
//			Method method=c.getDeclaredMethod("addService",String.class,IBinder.class,boolean.class);
			Method method=c.getDeclaredMethod("getIServiceManager");
			Object ob=method.invoke(null, null);
//			o=c.newInstance();
//			method.invoke(null, new Object[]{"yujintao",binder,true});
		}
		catch (Exception e) {
			e.printStackTrace();
//			BaseAndroidLogTemp.debug(e.getMessage());
		}
		
		return null;
	}
	public static class MyBinder extends Binder{
		public MyBinder(){
			super();
		}
	}
}
