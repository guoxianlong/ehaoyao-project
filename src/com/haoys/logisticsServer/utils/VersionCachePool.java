/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.haoys.logisticsServer.utils;


import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
 * @author liyang
 */
public class VersionCachePool {
	/**
     * memcached客户端单例
     */
	static Logger logger = Logger.getLogger(VersionCachePool.class);
	private static MemCachedClient cacheMapObject = new MemCachedClient();
	
	static ResourceBundle properties = null;
	static ResourceBundle bundle = ResourceBundle.getBundle("memcached");
	/**
	 * 初始化连接池
	 */
    static{
    	
    	//服务器列表,多个地址可以用","分开
		String[] servers = { bundle.getString("servers")};
		System.out.println( bundle.getString("servers") );
		//获取连接池的实例
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers( servers );
		pool.setFailover( true );
		//设置初始连接数、最小连接数、最大连接数
		pool.setInitConn( Integer.parseInt(bundle.getString("initConn")) ); 
		pool.setMinConn( Integer.parseInt(bundle.getString("minConn")) );
		pool.setMaxConn( Integer.parseInt(bundle.getString("maxConn")) );
		//最大处理时间
//		pool.setMaxIdle( Integer.parseInt(properties.getString("maxIdle")) );
		//设置连接池守护线程的睡眠时间
		pool.setMaintSleep( Integer.parseInt(bundle.getString("maintSleep")) );
		//设置TCP参数，连接超时
		pool.setNagle( false );
		pool.setSocketTO( Integer.parseInt(bundle.getString("stocketTo")) );
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
    	//logger.info("向缓存中添加Key为："+key+"的信息");
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
    	//logger.info("从缓存中获取Key为："+key+"的信息");
        return cacheMapObject.get(key);
    }
    /**
     * 删除缓存的全部内容
     */
    public static void remove(){
    	logger.info("清空缓存");
    	cacheMapObject.flushAll();
    }
public static void remove(String key){
	//logger.info("从缓存中移除Key为:"+key+"的信息");
	cacheMapObject.delete(key);
}

}
