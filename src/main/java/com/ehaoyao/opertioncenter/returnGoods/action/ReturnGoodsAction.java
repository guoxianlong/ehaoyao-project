package com.ehaoyao.opertioncenter.returnGoods.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.returnGoods.model.GoodsDetailInfo;
import com.ehaoyao.opertioncenter.returnGoods.model.GoodsInfo;
import com.ehaoyao.opertioncenter.returnGoods.model.ReturnGoodsHistory;
import com.ehaoyao.opertioncenter.returnGoods.service.ReturnGoodsService;
import com.ehaoyao.opertioncenter.returnGoods.vo.GoodsInfoVO;
import com.haoyao.goods.action.BaseAction;

/**
 * Title: ReturnGoodsAction.java
 * Description: 退货审核流程
 * @author zhang
 */
@Controller
@RequestMapping("/returnGoods.do")
public class ReturnGoodsAction extends BaseAction{
	private static final Logger logger = Logger.getLogger(ReturnGoodsAction.class);
	
	/**极速达订单发货接口*/
	//private String JSD_FH_URL = PropertiesUtil.getProperties("extend.properties", "JSD_FH_URL");
	/**极速达订单驳回接口*/
	//private String JSD_BH_URL = PropertiesUtil.getProperties("extend.properties", "JSD_BH_URL");
	
	//订单信息
	@Autowired
	private ReturnGoodsService returnGoodsService; 

	/**
	 * 查询订单相关信息
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = ("method=getReturnGoodsInfo"))
	public String getInfo(HttpServletRequest request, ModelMap modelMap,GoodsInfoVO vo) {
		if(vo==null){
			vo = new GoodsInfoVO();
		}
		//开始时间
		String startDate = vo.getStartDate();
		if(startDate!=null && startDate.trim().length()>0){
			vo.setStartDate(startDate.trim()+" 00:00:00");
		}
		//截止时间
		String endDate = vo.getEndDate();
		if(endDate!=null && endDate.trim().length()>0){
			vo.setEndDate(endDate.trim()+" 23:59:59");
		}
		String pageno = request.getParameter("pageno");
		String pageSize = request.getParameter("pageSize");
		try {
			if (pageno == null || "".equals(pageno)) {
				this.setPageno(1);
			} else {
				this.setPageno(Integer.parseInt(pageno));
				if (this.getPageno() < 1) {
					this.setPageno(1);
				}
			}
			if (pageSize == null || "".equals(pageSize)) {
				this.setPageSize(5);
			} else {
				this.setPageSize(Integer.parseInt(pageSize));
			}
		} catch (Exception e) {
			this.setPageno(1);
			this.setPageSize(5);
		}
		PageModel<GoodsInfo> pm = new PageModel<GoodsInfo>();
		pm.setPageSize(this.getPageSize());
		pm.setPageNo(this.getPageno());
		try {
			//订单查询
			pm = returnGoodsService.getReturnGoodsInfo(pm,vo);
			List<GoodsInfo> goodsInfoLs = pm.getList();
			if (goodsInfoLs != null && goodsInfoLs.size() > 0) {
				modelMap.put("goodsInfoLs", goodsInfoLs);
			}
			modelMap.put("pageno", pm.getPageNo());
			modelMap.put("pageTotal", pm.getTotalPages());
			modelMap.put("pageSize", pm.getPageSize());
			modelMap.put("recTotal", pm.getTotalRecords());
		} catch (Exception e) {
			logger.error("退货订单查询异常！orderNumber:",e);
		}
		vo.setStartDate(startDate);
		vo.setEndDate(endDate);
		modelMap.put("vo", vo);
		return "opcenter/returnGoods/returnGoodsInfo";
	}
	
	/**
	 * 查询订单相关信息
	 */
	@RequestMapping(params = ("method=getDetail"))
	public void getDetail(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap,GoodsInfoVO vo) {
		response.setContentType("text/html;charset=UTF-8");
		if(vo!=null && vo.getOrderNumber()!=null && vo.getOrderNumber()>0){
			try {
				//订单详情
				PrintWriter writer= response.getWriter();
				List<GoodsDetailInfo> ls = returnGoodsService.getGoodsDetails(vo);
				JSONArray ja = JSONArray.fromObject(ls);
				JSONObject object = new JSONObject();
				object.put("detailLs", ja.toString());
				modelMap.put("detailLs", ja.toString());
				writer.println(object);
				writer.flush(); 
				writer.close();
			} catch (Exception e) {
				logger.error("退货订单明细查询出错！orderNumber:"+vo.getOrderNumber(),e);
			}
		}
	}
	
	/**
	 * 退货审核
	 */
	@RequestMapping(params = ("method=review"))
	public void review(HttpServletResponse response,ReturnGoodsHistory bean, ModelMap modelMap) {
		if(bean!=null && bean.getOrderNumber()!=null && bean.getOrderNumber()>0){
			String msg = null;
			try {
				//订单详情
				PrintWriter writer= response.getWriter();
				msg = returnGoodsService.insertReview(bean);
				writer.println(msg);
				writer.flush(); 
				writer.close();
			} catch (Exception e) {
				logger.error("退货审核出错！orderNumber:"+bean.getOrderNumber()+",错误信息为" + msg);
			}
		}
	}
	/**
	 * 退货驳回
	 */
	@RequestMapping(params = ("method=reject"))
	public void reject(HttpServletResponse response,ReturnGoodsHistory bean, ModelMap modelMap) {
		returnGoodsService.insertReject(bean);
	}
}
