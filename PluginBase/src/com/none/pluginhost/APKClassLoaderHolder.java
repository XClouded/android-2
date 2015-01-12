package com.none.pluginhost;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import dalvik.system.DexClassLoader;

public class APKClassLoaderHolder {
	public static final byte[] LOCK=new byte[1];
	//用odex路径最为classloader路径
	private HashMap<String,Reference<DexClassLoader>> holder;
	private static APKClassLoaderHolder INSTANCE;
	private ReentrantReadWriteLock readWriteLock;
	private APKClassLoaderHolder(){
		holder=new HashMap<String, Reference<DexClassLoader>>();
		readWriteLock=new ReentrantReadWriteLock(false);
	}
	
	public DexClassLoader getAPKClassLoader(String apkPath){
		DexClassLoader classLoader=null;
		readWriteLock.readLock().lock();
		try{
			Reference<DexClassLoader> ref=holder.get(apkPath);
			if(ref!=null){
				classLoader=ref.get();
				if(classLoader==null){
					/*readWriteLock.readLock().unlock();
					readWriteLock.writeLock().lock();
					holder.remove(apkPath);
					readWriteLock.writeLock().unlock();
					readWriteLock.readLock().lock();*/
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			readWriteLock.readLock().unlock();
		}
		return classLoader;
	}
	public boolean putAPKClassLoader(String apkPath,DexClassLoader classLoader){
		boolean putCmdSuccessed=false;
		if(apkPath==null||classLoader==null)
			return putCmdSuccessed;
		readWriteLock.writeLock().lock();
		try{
			holder.put(apkPath, new WeakReference<DexClassLoader>(classLoader));
		}catch(Exception e){
			e.printStackTrace();
			putCmdSuccessed=false;
		}finally{
			readWriteLock.writeLock().unlock();
		}
		return putCmdSuccessed;
	}
	
	
	
	
	
	public static APKClassLoaderHolder getInstance(){
		if(INSTANCE==null){
			synchronized (LOCK) {
				if(INSTANCE==null){
					INSTANCE=new APKClassLoaderHolder();
				}
			}
		}
		return INSTANCE;
	}
}
