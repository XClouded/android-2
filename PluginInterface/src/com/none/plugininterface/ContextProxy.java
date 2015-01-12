package com.none.plugininterface;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.view.ContextThemeWrapper;


/**
 * <p>Copyright: Copyright (c) 2013</p>
 * 
 * <p>Company: 呱呱视频社区<a href="www.guagua.cn">www.guagua.cn</a></p>
 *
 * @description baseProxy
 *
 *
 * @author jintao
 * @modify 
 * @version 1.0.0 
*/
	
public interface ContextProxy<T>{
	Context getHostContext();
	AssetManager getProxyAssetManager();
	Resources getProxyResource();
	T getProxy();
}
