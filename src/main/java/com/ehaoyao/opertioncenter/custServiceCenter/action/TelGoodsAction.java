package com.ehaoyao.opertioncenter.custServiceCenter.action;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ehaoyao.admin.ice.business.MealClient;
import com.ehaoyao.ice.common.bean.Meal;
import com.ehaoyao.ice.common.bean.MealQueryParam;
import com.ehaoyao.ice.common.bean.PageList;
import com.ehaoyao.ice.common.bean.PageTurn;
import com.ehaoyao.opertioncenter.custServiceCenter.model.Product;
import com.ehaoyao.opertioncenter.custServiceCenter.service.TelGoodsService;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.ProductVO;
import com.haoyao.goods.action.BaseAction;
import com.haoyao.goods.model.User;
import com.haoyao.goods.service.UserServiceImpl;

/**
 * 
 * Title: TelGoodsAction.java
 * 
 * Description: 电销商品
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年8月15日 下午2:33:22
 */
@Controller
@RequestMapping("/telGoods.do")
public class TelGoodsAction extends BaseAction{
	private static final Logger logger = Logger.getLogger(TelGoodsAction.class);
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//电销商品
	@Autowired
	private TelGoodsService telGoodsService;
	//用户信息
	@Autowired
	private UserServiceImpl userService;
	
	public void setTelGoodsService(TelGoodsService telGoodsService) {
		this.telGoodsService = telGoodsService;
	}
	
	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

	/**
	 * 
	 * 电销商品
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = ("method=getTelGoodsLs"))
	public String getTelGoodsLs(HttpServletRequest request, ModelMap modelMap,ProductVO pvo) {
		String pageno = request.getParameter("pageno");
		String pageSize = request.getParameter("pageSize");
		if( pageno == null || "".equals(pageno) ){
			this.setPageno(1);
		}else{
			this.setPageno(Integer.parseInt(pageno));
			if( this.getPageno() < 1 ){
				this.setPageno(1);
			}
		}
		if( pageSize == null || "".equals(pageSize) || "".equals(pageSize) ){
			this.setPageSize(20);
		}else{
			this.setPageSize(Integer.parseInt(pageSize));
		}
		if (pvo != null) {
			pvo.setProductName((pvo.getProductName() != null && pvo.getProductName().trim().length() > 0) ? pvo.getProductName().trim() : null);
			pvo.setProductCode((pvo.getProductCode() != null && pvo.getProductCode().trim().length() > 0) ? pvo.getProductCode().trim() : null);
			pvo.setSecondName((pvo.getSecondName() != null && pvo.getSecondName().trim().length() > 0) ? pvo.getSecondName().trim() : null);
			pvo.setStatus((pvo.getStatus() != null && pvo.getStatus().trim().length() > 0) ? pvo.getStatus().trim() : null);
			pvo.setIsCf((pvo.getIsCf() != null && pvo.getIsCf().trim().length() > 0) ? pvo.getIsCf().trim() : null);
		}
		recTotal = telGoodsService.queryProductCount(pvo);
		pageTotal = recTotal / this.getPageSize();
		pageTotal = recTotal % this.getPageSize() == 0 ? pageTotal : pageTotal + 1;
		if( this.getPageno() > pageTotal ){
			this.setPageno(1);
		}
		List<Product> productList = telGoodsService.queryProductList((this.getPageno() - 1) * this.getPageSize() , this.getPageSize(), pvo);
		modelMap.put("pList", productList);
		modelMap.put("pageno", this.getPageno());
		modelMap.put("pageTotal", pageTotal);
		modelMap.put("pageSize", this.getPageSize() );
		modelMap.put("recTotal", recTotal);
		modelMap.put("product",pvo);
		
		return "opcenter/custService/electricPins_goods";
	}
	
	/**
	 * 
	 * 删除商品
	 */
//	@RequestMapping(params = ("method=delGoods"))
//	public String delGoods(HttpServletRequest request, ModelMap modelMap,ProductVO pvo) {
//		String pageno = request.getParameter("pageno");
//		String pageSize = request.getParameter("pageSize");
//		modelMap.put("pageno", (pageno!=null?Integer.parseInt(pageno):1));
//		modelMap.put("pageSize", (pageSize!=null?Integer.parseInt(pageSize):20));
//		if(pvo!=null && pvo.getSecondID()!=null && pvo.getSecondID().trim().length()>0){
//			String ids = pvo.getSecondID().trim();
//			String[] idArr = ids.split(",");
//			List<String> idList = Arrays.asList(idArr);
//				telGoodsService.delGoods(idList);
//		}
//		return "forward:telGoods.do?method=getTelGoodsLs";
//	}
	
	/**
	 * 设置主推状态
	 */
	@RequestMapping(params = ("method=updateStatus"))
	public String updateStatus(HttpServletRequest request, ModelMap modelMap,ProductVO pvo) {
		String pageno = request.getParameter("pageno");
		String pageSize = request.getParameter("pageSize");
		modelMap.put("pageno", (pageno!=null?Integer.parseInt(pageno):1));
		modelMap.put("pageSize", (pageSize!=null?Integer.parseInt(pageSize):20));
		if(pvo!=null && pvo.getSecondID()!=null && pvo.getSecondID().trim().length()>0){
			String ids = pvo.getSecondID().trim();
			String[] idArr = ids.split(",");
			List<String> idList = Arrays.asList(idArr);
			if(pvo.getGoodsSta()!=null && ("0".equals(pvo.getGoodsSta().trim()) || "1".equals(pvo.getGoodsSta().trim()))){
				telGoodsService.updateStatus(idList,pvo.getGoodsSta());
			}
		}
		return "forward:telGoods.do?method=getTelGoodsLs";
	}
	
	/*
	 * 手动导入电销商品
	 */
	@RequestMapping(params = ("method=importProduct"))
	public void importProduct(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) {
		excuImportPro();
	}
	
	/*
	 * 电销商品导入定时任务
	 */
	public void excuImportPro(){
		Date st = new Date();
		logger.info("导入电销商开始>>>>>>>>" + df.format(st));
		try {
			//用户信息
			User user = getCurrentUser(); 
			String userName = user!=null?user.getUserName():null;
			
			//设置查询参数
			MealQueryParam param = new MealQueryParam();
			param.setMealTag("1");//1：电销
			int pageNo = 1;
			param.setPn(pageNo+"");//当前页
			param.setPz("100");//每页记录条数
			
			//总页数
			int pageCount = 0;
			PageTurn pt = null;
			//官网商品列表
			List<Meal> mealLs = null;
			Meal meal = null;//官网商品
			Product pro = null;//电销商品
			PageList<Meal> mealPage=null;
			int impCount=0;
			int rowCount=0;
			do{
				try {
					//查询官网电销商品
					mealPage = MealClient.getList(param);
					if(mealPage==null || mealPage.getDataList()==null || mealPage.getDataList().size()<=0){
						continue;
					}
				} catch (Exception e1) {
					logger.error("查询官网电销商品失败："+e1.getMessage());
					break;
				}
				
				mealLs = mealPage.getDataList();
//				for(Meal meal:mealLs){
				for(int i=0;i<mealLs.size();i++){
					meal = mealLs.get(i);
					if(meal==null){
						continue;
					}
					if(meal.getMealId()!=null){
						try {
							pro = new Product();
							//副本ID - 商品套餐id
							pro.setSecondID(meal.getMealId()+"");
							//副本名称 - 套餐名称
							pro.setSecondName(meal.getMealName());
							//主商品编码 - 主商品SKU
							pro.setProductCode(meal.getMainSku());
							//产品ID
							pro.setMainProductId(meal.getMainProductId()!=null?(meal.getMainProductId()+""):null);
//							//主商品名称
//							pro.setProductName("");
							//主商品规格
							pro.setSpecifications(meal.getMealNormName());
							//市场价 - 套餐的市场价格
							pro.setMarketPrice(meal.getMealMarketPrice()!=null?meal.getMealMarketPrice()+"":null);
							//销售价  - 套餐价格（实际售价）
							pro.setPrice(meal.getMealPrice()!=null?meal.getMealPrice()+"":null);
							//前端类型
							pro.setFrontType(meal.getFirstCatName());
							//处方类型
							if(meal.getPrescriptionType()==1 || meal.getPrescriptionType()==2){
								//2:非处方药
								pro.setIsCf("2");
							}else if(meal.getPrescriptionType()==3 && meal.getDrugType()==93){
								//3:单轨处方药
								pro.setIsCf("3");
							}else if(meal.getPrescriptionType()==3 && meal.getDrugType()==3){
								//4:双轨处方药
								pro.setIsCf("4");
							}else{
								//1:非药品
								pro.setIsCf("1");
							}
							
							pro.setCreateUser(userName);
							pro.setCreateTime(df.format(new Date()));
							pro.setUpdateUser(userName);
							pro.setUpdateTime(df.format(new Date()));
							//保存商品信息
							telGoodsService.saveOrUpdateProduct(pro);
							impCount++;
						} catch (Exception e) {
							logger.error("商品信息保存失败！商品编码:"+meal.getMainSku()+" "+e.getMessage());
						}
					} else{
						logger.info("商品套餐ID为空，无法保存。商品编码:"+meal.getMainSku());
					}
				}
				
				pt = mealPage.getPageTurn();
				if(pt!=null){
					//总页数
					pageCount = pt.getPageCount()!=null?pt.getPageCount():0;
					rowCount = pt.getRowCount();
				}
				pageNo++;
				//当前页
				param.setPn(pageNo+"");
			}while(pageNo<=pageCount);
			logger.info("查询电销商品："+rowCount+"个 ，成功导入电销商品："+impCount+"个");
		} catch (Exception e) {
			logger.error("电销商品导入失败！",e);
		}
		
		Date et = new Date();
		logger.info("导入电销商结束！"+df.format(et)+" ，总耗时"+(et.getTime()-st.getTime())+"ms");
	}
	
	/**
	 * 获取当前用户
	 * @return User 
	 */
	private User getCurrentUser(){
		//用户信息
		Authentication aut = SecurityContextHolder.getContext().getAuthentication();
		if(aut!=null){
			Object principal = aut.getPrincipal();      
			if (principal instanceof UserDetails) {      
				String userName = ((UserDetails) principal).getUsername();
				User user = userService.checkByUserName(userName);
				return user;
			}
		}
		return null;
	}
	
}
