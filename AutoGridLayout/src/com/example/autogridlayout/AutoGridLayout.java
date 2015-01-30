package com.example.autogridlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.ViewDebug.HierarchyTraceType;

public class AutoGridLayout extends ViewGroup {

	private static final String TAG="AutoGridLayout";
	private static final boolean DEBUG=true;
	
	private static int ROWPX_UNIT = 200;//px;

	private static int VIEW_STATUS_MASK = 0x00;

	private static int MEASURE_ROW = 0x01;

	private int mViewStatus;

	public AutoGridLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mViewStatus = 0x00;
	}

	/* (non-Javadoc)
	 * @see android.view.View#onMeasure(int, int)
	 * 自己测量应该怎样设置每行每列个数。
	 * 由于是流失布局所以他所给予子视图的宽度都是确定的。也就是都是MeasureSpec.Exactly模式。
	 * 但是高度则不是MeasureSpec.Exactly模式
	 * 目前不考虑UNSPECIFIED情况。不是用margin。
	 * 目前所实现的版本是记录没行最大高度。进行设置。宽度都是确定的
	 */
	private int mRowNumber;
	private int mRowAVGWidth;
	
	
	//why? onMeasure method is called by twice?
	@Override
	protected final void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		if(DEBUG){
			final int heightMode=MeasureSpec.getMode(heightMeasureSpec);
			switch (heightMode) {
				case MeasureSpec.AT_MOST:
					log("height mode at_most");
					break;
				case MeasureSpec.EXACTLY:
					log("height mode EXACTLY");
					
					break;
				case MeasureSpec.UNSPECIFIED:
					log("height mode UNSPECIFIED");
				default:
					break;
			}
		}
		//进行宽度测量。atMost 和 Exactly 都是用widthSize。
		int realWidthContent = widthSize - getPaddingLeft() - getPaddingRight();
		measureRowNumber(realWidthContent, ROWPX_UNIT);
		
		final int childCount = getChildCount();

		//measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed)

		final int childWidthSpec = MeasureSpec.makeMeasureSpec(mRowAVGWidth, MeasureSpec.EXACTLY);

		int heightTotal = 0;
		
		for (int i = 0; i < childCount;) {
			int maxHeight=0;
			for(int j=0;j<mRowNumber;j++){
				final View child = getChildAt(i*mRowNumber+j);
				if(child==null){
					break;
				}
				ViewGroup.LayoutParams params = child.getLayoutParams();
				child.measure(childWidthSpec, ViewGroup.getChildMeasureSpec(heightMeasureSpec, getPaddingBottom() + getPaddingTop(), params.height));
				final int childMeasureHeigth = child.getMeasuredHeight();
				int childSize = Math.max(childMeasureHeigth, getSuggestedMinimumHeight());
				maxHeight=maxHeight>childSize?maxHeight:childSize;
			}
			heightTotal += maxHeight;
			for(int j=0;j<mRowNumber;j++){
				final View child = getChildAt(i*mRowNumber+j);
				if(child==null){
					break;
				}
				ViewGroup.LayoutParams params = child.getLayoutParams();
				child.measure(childWidthSpec, ViewGroup.getChildMeasureSpec(heightMeasureSpec, getPaddingBottom() + getPaddingTop(),maxHeight));
			}
			i+=mRowNumber;
		}
		int specMode = MeasureSpec.getMode(heightMeasureSpec);
		int specSize = MeasureSpec.getSize(heightMeasureSpec);
		int resultHeight = 0;
		switch (specMode) {
			case MeasureSpec.UNSPECIFIED:
				resultHeight = heightTotal;
				break;
			case MeasureSpec.AT_MOST:
				if (specSize < heightTotal) {
					resultHeight = specSize | MEASURED_STATE_TOO_SMALL;
				}
				else {
					resultHeight = heightTotal;
				}
				break;
			case MeasureSpec.EXACTLY:
				resultHeight = specSize;
				break;
		}
		setMeasuredDimension(widthSize, resultHeight);
	}

	/**
	 * @author: jintao
	 * @param width
	 * @param unit
	 * @return: void
	 * @date: 2015-1-26 下午3:19:05
	 * 测量每行数量函数，必须要在onMeasure调用
	*/

	private void measureRowNumber(int width, int unit) {
		mViewStatus |= MEASURE_ROW;
		mRowNumber = width / unit == 0 ? 1 : width / unit;
		final double widthAvg = (width * 1.0) / mRowNumber;
		mRowAVGWidth = (int) (widthAvg + 1);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		final int widthChild=mRowAVGWidth;
		final int rowNumber=mRowNumber;
		final int childCount=getChildCount();
		final int columnNumber=childCount/rowNumber+(childCount%rowNumber==0?0:1);
		if(DEBUG){
			log("gridlayout rowNumber %d columnNumber %d",mRowNumber,columnNumber);
		}
		int row=0;
		int column=0;
		for(int index=0;index<childCount;index++){
			final View child=getChildAt(index);
//			child.layout(column, childCount, row, columnNumber);
			final int measureHeight=child.getMeasuredHeight();
			final int measureWidth=child.getMeasuredWidth();
			l=l+measureWidth*row;
			t=t+measureHeight*column;
			if((index+1)%mRowNumber==0){
				row=0;
				column+=column;
			}else{
				row+=1;
			}
			child.layout(l, t, l+measureWidth, t+measureHeight);
			if(DEBUG){
				log("child %d left %d top %d right %d bottom %d",index,l,t,l+measureWidth,t+measureHeight);
			}
			//child.layout(l+measureHeight, measureHeight, measureWidth, columnNumber)
//			log("measure Heigth %d", measureHeight);
//			log("measure width %d", measureWidth);
		}
	}
	
	public void log(String message,Object... args){
		message=String.format(message, args);
		Log.d(TAG, message);
	}
}
