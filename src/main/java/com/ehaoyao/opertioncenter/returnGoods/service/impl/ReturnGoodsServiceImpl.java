package com.ehaoyao.opertioncenter.returnGoods.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.returnGoods.dao.ReturnGoodsDao;
import com.ehaoyao.opertioncenter.returnGoods.model.GoodsDetailInfo;
import com.ehaoyao.opertioncenter.returnGoods.model.GoodsInfo;
import com.ehaoyao.opertioncenter.returnGoods.model.ReturnGoodsHistory;
import com.ehaoyao.opertioncenter.returnGoods.service.ReturnGoodsService;
import com.ehaoyao.opertioncenter.returnGoods.vo.GoodsInfoVO;
import com.ibm.icu.text.SimpleDateFormat;
import com.pajk.openapi.codec.client.RequestEncoder;
import com.pajk.openapi.codec.client.RequestEntity;
import com.pajk.openapi.codec.client.ResponseDecoder;

/**
 * Title: ReturnGoodsService.java
 * Description: 退货审核流程
 * @author zhang
 */
@Service
public class ReturnGoodsServiceImpl implements ReturnGoodsService {
	
	@Autowired
	private ReturnGoodsDao returnGoodsDao;
	
	private static final Logger logger = Logger.getLogger(ReturnGoodsServiceImpl.class);
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private RestTemplate restTemplate = new RestTemplate();

	/**
	 * 查询订单
	 */
	@Override
	public PageModel<GoodsInfo> getReturnGoodsInfo(PageModel<GoodsInfo> pm,GoodsInfoVO vo) {
		if(pm.getPageSize()>0){
			int count = returnGoodsDao.getRetuanOrderCount(vo);
			pm.setTotalRecords(count);
		}
		List<GoodsInfo> ls = returnGoodsDao.getReturnGoodsInfo(pm,vo);
		pm.setList(ls);
		return pm;
	}

	/**
	 * 查询退货订单明细
	 */
	@Override
	public List<GoodsDetailInfo> getGoodsDetails(GoodsInfoVO vo) {
		return returnGoodsDao.getGoodsDetails(vo);
	}
	/**
	 * 更新退货单状态
	 */
	@Override
	public void updateStatus(GoodsInfoVO vo) {
		returnGoodsDao.updateStatus(vo);
	}
	/**
	 * 审核
	 */
	@Override
	public String insertReview(ReturnGoodsHistory bean) {
		String msg = null;
		try{
			String partnerId = "haoyaoshi_01";
			// 请求/响应报⽂文使⽤用 
			String key = "8aaa9339547fb66351e4a66972cfe359"; 
			// 测试环境apiId 
			String apiId = "03938caf10b6fb93758029f13ae00b90#PROD"; 
			String apiName = "B2cOrderRefundApplication"; 
			String apiGroup = "shennongLogistics";
			Long userVenderId = new Long(989810607);
			// 开始请求报⽂文编码 
			RequestEncoder encoder = new RequestEncoder(partnerId, key, apiId);
			// 填充API参数 
			encoder.addParameter(userVenderId);
			encoder.addParameter(bean.getOrderNumber());
			encoder.addParameter(Math.round(bean.getAmount()));
			encoder.addParameter(bean.getReason());
			encoder.addParameter(bean.getRemark());
			// 结束请求报⽂文编码,作为HTTP POST BODY 
			RequestEntity re = encoder.encode();
			String pastData = re.getFormParams();
			String url = "http://openapi.jk.cn/api/v1/"+apiGroup+"/"+apiName + "?";
			url += re.getQueryParams();
			url += "&" + pastData;
			// 客户端发送HTTP POST请求
			// 获得HTTP相应结果(JSON格式) 
			String text = restTemplate.postForObject(url, null, String.class);
			// 从response中提取key=object的value 
			Map obj = JSON.parseObject(text,Map.class);
			// 响应结果解码 
			ResponseDecoder decoder = new ResponseDecoder(key);
			boolean decoded = decoder.decode(obj.get("object").toString()); 
			if(decoded){    
				// 当解码成功时，获得上层业务数据    
				String data = decoder.getData();
				if(data != null && !"".equals(data)){
					data = data.substring(1,data.length()-1);
					data = data.replaceAll("\\\\","");
					JSONObject json = JSONObject.fromObject(data);
					boolean rs = json.getBoolean("success");
					if(rs){
						//插入历史记录，并更新订单中心订单状态
						Long orderNo = json.getLong("bizOrderId");
						bean.setOrderNumber(orderNo);
						bean.setOperateFlag("退货审核");
						writeStatus(bean, "s13");
					}else{
						msg = json.getString("resultMsg");
					}
				}else{
					msg = "获取data的值为" + data;
				}
			}else{
				msg = "接口秘钥配置错误，请联系管理员！";
				logger.info("接口秘钥配置错误，url：" + url);
			}
		}catch(Exception e){
			msg = e.getMessage();
			logger.error("退货单审核失败，退货单号为：" + bean.getOrderNumber(), e);
		}
		return msg;
	}
	/**
	 * 驳回
	 */
	@Override
	public void insertReject(ReturnGoodsHistory bean) {
		try{
			bean.setOperateFlag("退货驳回");
			writeStatus(bean, "s14");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("更新订单退款驳回信息失败：", e);
		}
	}
	/**
	 * 插入历史记录，并更新订单中心订单状态
	 * @param bean
	 * @param status
	 */
	private void writeStatus(ReturnGoodsHistory bean,String status){
		String date = df.format(new Date());
		bean.setOperateTime(date);
		bean.setCustServiceNo(getCurrentUser());
		returnGoodsDao.insertHistory(bean);
		GoodsInfoVO vo = new GoodsInfoVO();
		vo.setOrderNumber(bean.getOrderNumber());
		vo.setOrderStatus(status);
		returnGoodsDao.updateStatus(vo);
	}
	/**
	 * 获取当前用户
	 * @return User 
	 */
	private String getCurrentUser(){
		//用户信息
		Authentication aut = SecurityContextHolder.getContext().getAuthentication();
		String userName = null;
		if(aut!=null){
			Object principal = aut.getPrincipal();
			if (principal instanceof UserDetails) {
				userName = ((UserDetails) principal).getUsername();
			}
		}
		return userName;
	}
}
