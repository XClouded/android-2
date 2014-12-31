package com.qiqi.matrix;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public interface IAction {
	public void action(Canvas canvas,Paint paint,Bitmap bitmap,int timeDifference);
}
