package com.qiqi.matrix;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.View;

public class MatrixImage extends View{
	Bitmap bitmap;
	Paint paint;
	Matrix matrix;
	Matrix matrix2;
	ObjectAnimator animator;
	Handler handler;
	int p=10;
	public MatrixImage(Context context) {
		super(context);
		bitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
		paint=new Paint();
		matrix=new Matrix();
		matrix2=new Matrix();
//		animator=ObjectAnimator.
//		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//			@Override
//			public void onAnimationUpdate(ValueAnimator animation) {
//				animation.getAnimatedValue();
//			}
//		});
		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				matrix2.reset();
				p+=10;
				matrix2.postTranslate(p, p);
				invalidate();
				handler.sendEmptyMessageDelayed(1, 10);
			}
		};
		handler.sendEmptyMessageDelayed(1, 3000);
//		animator.start();
	}
	
	
	
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		int count=canvas.save();
		for(int i=0;i<10;i++){
//			canvas.drawBitmap(bitmap, i*30+100, 0, paint);
			matrix.reset();
			matrix.postConcat(matrix2);
//			matrix.postRotate(30+30*i);
			matrix.postTranslate(i*50, 0);
			canvas.drawBitmap(bitmap, matrix, paint);
		}
		canvas.restoreToCount(count);
		
	}
}
