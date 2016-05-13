package com.ehaoyao.logistics.ws.client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis2.AxisFault;

import com.ehaoyao.logistics.ws.service.LogisticsServiceStub;
import com.ehaoyao.logistics.ws.service.LogisticsServiceStub.ElseExpress;
import com.ehaoyao.logistics.ws.service.LogisticsServiceStub.GetElseExpressesTrackNum;
import com.ehaoyao.logistics.ws.service.LogisticsServiceStub.GetElseLeakageOfSingle;
import com.ehaoyao.logistics.ws.service.LogisticsServiceStub.GetLeakageOfSingle;
import com.ehaoyao.logistics.ws.service.LogisticsServiceStub.GetTrackNumberBySource;
import com.ehaoyao.logistics.ws.service.LogisticsServiceStub.LogisticsDetail;
import com.ehaoyao.logistics.ws.service.LogisticsServiceStub.PutInfoToDB;
import com.ehaoyao.logistics.ws.service.LogisticsServiceStub.PutMemcached;
import com.ehaoyao.logistics.ws.service.LogisticsServiceStub.PutMemcachedResponse;
import com.ehaoyao.logistics.ws.service.LogisticsServiceStub.RemoveLastMaxId;

public class LogisticsServerClient {
	/**
	 * 按来源获取未完成状态的快递单号 
	 * @param source 快递来源公司编码
	 * @return String集合，包含快递单号
	 */
	public static List<String> getTrackNumberBySource(String source) {
		List<String> trackNum = new ArrayList<String>();
		try {
			LogisticsServiceStub stub = new LogisticsServiceStub();
			LogisticsServiceStub.GetTrackNumberBySource getTrackNum = new GetTrackNumberBySource();
			getTrackNum.setSource(source);

			LogisticsServiceStub.GetTrackNumberBySourceResponse trackResponse = stub
					.getTrackNumberBySource(getTrackNum);
			String[] str = trackResponse.get_return();
			if (null != str) {
				for (String st : str) {
					trackNum.add(st);
				}
			} else {
				return trackNum;
			}

		} catch (AxisFault e) {

			e.printStackTrace();
		} catch (RemoteException e) {

			e.printStackTrace();
		}
		return trackNum;
	}
	/**
	 * 获取指定渠道大于date到当前时间减N天内所有未完成的漏单快递单号
	 * @param date 日期时间
	 * @return 包含快递单号的List<String>集合
	 */
	public static List<String> getLeakageOfSingle(String source,String date){
		List<String> trackNum = new ArrayList<String>();
		try {
			LogisticsServiceStub stub = new LogisticsServiceStub();
			LogisticsServiceStub.GetLeakageOfSingle getTrackNum = new GetLeakageOfSingle();
			getTrackNum.setSource(source);
			getTrackNum.setDate(date);
			LogisticsServiceStub.GetLeakageOfSingleResponse trackResponse = stub
					.getLeakageOfSingle(getTrackNum);
			String[] str = trackResponse.get_return();
			if (null != str) {
				for (String st : str) {
					trackNum.add(st);
				}
			} else {
				return trackNum;
			}

		} catch (AxisFault e) {

			e.printStackTrace();
		} catch (RemoteException e) {

			e.printStackTrace();
		}
		return trackNum;
	}
	/**
	 * 获取没有接口和爬虫的快递公司单号及渠道
	 * @return  ElseExpress的对象集合，包含单号和来源
	 */
	public static List<ElseExpress> getElseExpressesTrackNum(){
		List<ElseExpress> elseExpList=new ArrayList<ElseExpress>();
		try {
			LogisticsServiceStub stub = new LogisticsServiceStub();
			LogisticsServiceStub.GetElseExpressesTrackNum getElseExp=new GetElseExpressesTrackNum();
			LogisticsServiceStub.GetElseExpressesTrackNumResponse getElseExpRes=stub.getElseExpressesTrackNum(getElseExp);
			ElseExpress[] elseExp=getElseExpRes.get_return();
			if(null!=elseExp&&elseExp.length>0){
			for(ElseExpress exp:elseExp){
				elseExpList.add(exp);
			}}
			return elseExpList;
		} catch (AxisFault e) {
			
			e.printStackTrace();
		} catch (RemoteException e) {
			
			e.printStackTrace();
		}
		return null;

	}
	/**
	 * 获取时间大于date小于当前时间减N天内没有接口和爬虫的漏单单号和来源
	 * @param date 日期时间
	 * @return ElseExpress的对象集合，包含单号和来源
	 */
	public static List<ElseExpress> getElseLeakageOfSingle(String date){
		List<ElseExpress> elseExpList=new ArrayList<ElseExpress>();
		try {
			LogisticsServiceStub stub = new LogisticsServiceStub();
			LogisticsServiceStub.GetElseLeakageOfSingle getElseExp=new GetElseLeakageOfSingle();
			getElseExp.setDate(date);
			LogisticsServiceStub.GetElseLeakageOfSingleResponse getElseExpRes=stub.getElseLeakageOfSingle(getElseExp);
			ElseExpress[] elseExp=getElseExpRes.get_return();
			if(null!=elseExp&&elseExp.length>0){
			for(ElseExpress exp:elseExp){
				elseExpList.add(exp);
			}}
			return elseExpList;
		} catch (AxisFault e) {
			
			e.printStackTrace();
		} catch (RemoteException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 将快递信息按“来源_单号”的Key形式存入memcached中
	 * @param source 快递来源公司编码
	 * @param trackNum 快递单号
	 * @param LogisticsDetail 存有快递信息的LogisticsDetail数组
	 * 
	 */
	public static PutMemcachedResponse putMemcached(String source, String trackNum,
			List<LogisticsDetail> LogisticsDetail) {
		try {
			LogisticsDetail[] detail = new LogisticsDetail[LogisticsDetail
					.size()];
			int i = 0;
			for (LogisticsDetail log : LogisticsDetail) {

				detail[i] = log;
				i++;
			}
			LogisticsServiceStub stub = new LogisticsServiceStub();
			LogisticsServiceStub.PutMemcached putMemcached = new PutMemcached();
			putMemcached.setTrackNum(trackNum);
			putMemcached.setSource(source);
			putMemcached.setLogisticsDetail(detail);
			return stub.putMemcached(putMemcached);
		} catch (AxisFault e) {

			e.printStackTrace();
		} catch (RemoteException e) {

			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 将已完成订单从Memcached中取出存入数据库，并从memcached中删除该条信息
	 * 
	 * @param source
	 *            快递来源公司编码
	 * @param trackNum
	 *            快递单号
	 * @return
	 */
	public static Integer putInfoToDB(String source, String trackNum) {
		try {
			LogisticsServiceStub stub = new LogisticsServiceStub();
			LogisticsServiceStub.PutInfoToDB putInfoToDb = new PutInfoToDB();
			putInfoToDb.setSource(source);
			putInfoToDb.setTrackNum(trackNum);
			LogisticsServiceStub.PutInfoToDBResponse toDBResponse = stub
					.putInfoToDB(putInfoToDb);
			return toDBResponse.get_return();
		} catch (AxisFault e) {

			e.printStackTrace();
		} catch (RemoteException e) {

			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 从缓存中移除指定渠道的LastMaxID信息
	 * 
	 * @param source
	 *            快递来源公司代码
	 * @return
	 */
	public static void removeLastMaxId(String source) {
		try {
			LogisticsServiceStub stub = new LogisticsServiceStub();
			LogisticsServiceStub.RemoveLastMaxId removeLastMaxId = new RemoveLastMaxId();
			removeLastMaxId.setSource(source);
			stub.removeLastMaxId(removeLastMaxId);
		} catch (AxisFault e) {

			e.printStackTrace();
		} catch (RemoteException e) {

			e.printStackTrace();
		}
	}
}
