package com.example.jnitask;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	public static String TAG=MainActivity.class.getSimpleName();
	
	private EditText num1;
	private EditText num2;
	private EditText yunsuan;
	private TextView result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		num1=(EditText) findViewById(R.id.num1);
		num2=(EditText) findViewById(R.id.num2);
		yunsuan=(EditText) findViewById(R.id.yunsuanfu);
		result=(TextView) findViewById(R.id.result);
	}
	public void onClick(View view){
		switch (view.getId()) {
			case R.id.caculate:
				int number=Integer.parseInt(num1.getEditableText().toString());
				int number2=Integer.parseInt(num2.getEditableText().toString());
				String caculate=yunsuan.getEditableText().toString();
				result.setText(caculate(number, caculate, number2)+"");
				break;
			default:
				break;
		}
	}
	
	public native int caculate(int number1,String caculate,int number2);
	static{
		System.loadLibrary("JNITask");
	}
}
