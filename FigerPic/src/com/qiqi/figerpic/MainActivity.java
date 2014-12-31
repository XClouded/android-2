package com.qiqi.figerpic;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MainActivity extends Activity {

	MyPicture myPicture;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		getWindow().addFlags()
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		myPicture=(MyPicture) findViewById(R.id.picture);
	}
	public void onClick(View view){
		switch (view.getId()) {
			case R.id.eraser:
				myPicture.setEraserEffective(!myPicture.isEffective());
				break;
			default:
				break;
		}
	}
	
}
