package com.qiqi.binder;
import com.qiqi.binder.ILocalService;
/*
*	 此处引入的接口必须是aidl中声明的接口。
*	
*
*/
interface ILocalService2{
	void start(ILocalService i);
}