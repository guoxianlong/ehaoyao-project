/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehaoyao.yhdjkg.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
 * @author wls
 */
public class VersionCachePool {
	/**
     * memcached客户端单例
     */
	public static MemCachedClient cacheMapObject = new MemCachedClient();
	
	static ResourceBundle properties = null;
	
	/**
	 * 初始化连接池
	 */
    static{
    	
    	try{
    		String proFilePath=System.getProperty("user.dir")+"/config/memcached.properties";
			//System.out.println(proFilePath);
			File outFile=new File(proFilePath);
			InputStream in=new BufferedInputStream(new FileInputStream(outFile));
			properties = new PropertyResourceBundle(in);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    	
    	//服务器列表,多个地址可以用","分开
		String[] servers = { properties.getString("servers")};
		//获取连接池的实例
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers( servers );
		pool.setFailover( true );
		//设置初始连接数、最小连接数、最大连接数
		pool.setInitConn( Integer.parseInt(properties.getString("initConn")) ); 
		pool.setMinConn( Integer.parseInt(properties.getString("minConn")) );
		pool.setMaxConn( Integer.parseInt(properties.getString("maxConn")) );
		//最大处理时间
//		pool.setMaxIdle( Integer.parseInt(properties.getString("maxIdle")) );
		//设置连接池守护线程的睡眠时间
		pool.setMaintSleep( Integer.parseInt(properties.getString("maintSleep")) );
		//设置TCP参数，连接超时
		pool.setNagle( false );
		pool.setSocketTO( Integer.parseInt(properties.getString("stocketTo")) );
		pool.setAliveCheck( true );
		//初始化并启动连接池
		pool.initialize();      
		//压缩设置，超过指定大小的都压缩
//		cacheMapObject.setCompressEnable(true);
//		cacheMapObject.setCompressThreshold(1024*1024);
		
		
    }
    /**
     * 存入方法，键值对存储,
     * value可存入对象，但是对象必须序列化
     * @param key
     * @param vos
     */
    public static void putObject(String key,Object vos){
            cacheMapObject.set(key, vos);
    }    
    /**
     * 根据key获取value，
     * 如果value是已经序列化的对象，
     * 可进行强转
     * @param key
     * @return
     */
    public static Object getObject(String key){
        return cacheMapObject.get(key);
    }
    /**
     * 删除缓存的全部内容
     */
    public static void remove(){
    	cacheMapObject.flushAll();
    }

}
