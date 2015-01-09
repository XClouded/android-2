package com.none.pluginhost;

import com.none.pluginhost.ProxyAcivity.ProxyInfo;

public interface IAPKClassHandler<T>{
	T reflectClass(ProxyInfo proxyInfo) throws RuntimeException;
}
