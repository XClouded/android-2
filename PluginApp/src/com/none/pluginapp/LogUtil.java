package com.none.pluginapp;

import android.util.Log;

public class LogUtil {
//	public static void debug(String tag,String message,Throwable throwable){
//		Log.d(tag, message, throwable);
//	}
	public static void debug(String tag,String format,Object... args){
		format=String.format(format, args);
		Log.d(tag, format);
	}
	
}
