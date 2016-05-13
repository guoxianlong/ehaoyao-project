package com.ehaoyao.opertioncenter.callerScreen.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.ehaoyao.admin.ice.business.MealClient;
import com.ehaoyao.admin.ice.business.OrderClient;
import com.ehaoyao.ice.common.bean.CpsMerchantBean;
import com.ehaoyao.ice.common.bean.ExpressDetail;
import com.ehaoyao.ice.common.bean.Meal;
import com.ehaoyao.ice.common.bean.MealQueryParam;
import com.ehaoyao.ice.common.bean.OrderBean;
import com.ehaoyao.ice.common.bean.OrderGoodsBean;
import com.ehaoyao.ice.common.bean.OrderLogBean;
import com.ehaoyao.ice.common.bean.OrderQueryParam;
import com.ehaoyao.ice.common.bean.PageList;
import com.ehaoyao.ice.common.bean.PageTurn;
import com.ehaoyao.ice.common.bean.ProxyOrderInfoBean;
import com.ehaoyao.ice.common.bean.ProxyOrderInfoBean.AddGoodsBean;
import com.ehaoyao.ice.common.bean.StoreShipFeeBean;
import com.ehaoyao.ice.common.bean.UpdateProxyOrderInfoBean;
import com.ehaoyao.opertioncenter.callerScreen.service.CallerScreenService;
import com.ehaoyao.opertioncenter.common.DateUtil;
import com.ehaoyao.opertioncenter.custServiceCenter.action.OutScreenAction;
import com.ehaoyao.opertioncenter.custServiceCenter.util.PropertiesUtil;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.MealVo;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.OrderInfoVO;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.ProductVO;
import com.ehaoyao.opertioncenter.member.model.Member;
import com.ehaoyao.opertioncenter.member.service.HealthKeywordDetailService;
import com.ehaoyao.opertioncenter.member.service.HealthKeywordHeadService;
import com.ehaoyao.opertioncenter.member.service.MemberService;
import com.haoyao.goods.action.BaseAction;
import com.haoyao.goods.model.User;
import com.haoyao.goods.service.UserServiceImpl;
import com.haoyao.goods.util.SignUtils;

/**
 * 
 * Title: CallerScreenAction.java
 * 
 * Description: 来电弹屏
 * 
 * @author kangxr
 * @version 1.0
 * @created 2014年9月17日 下午2:18:42
 */
//callerScreen.do 呼叫中心访问
//callerScreen2.do 运营中心后台访问
@Controller
@RequestMapping({"/callerScreen.do","/callerScreen2.do"})
public class CallerScreenAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(CallerScreenAction.class);
	
	//呼叫中心客服访问时使用的请求加密标志
	private String outScreenSign = PropertiesUtil.getProperties("extend.properties", "outScreenSign");
	//官网地址
	private String officialUrl = PropertiesUtil.getProperties("extend.properties", "officialUrl");
	//获取官网会员信息接口标识
	private String accountToken = PropertiesUtil.getProperties("extend.properties", "accountToken");
	private String accountUUID = PropertiesUtil.getProperties("extend.properties", "accountUUID");
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//去电弹屏
	@Autowired
	private OutScreenAction outScreenAction;
	//来电弹屏
	@Autowired
	private CallerScreenService callerScreenService;
	//会员档案
	@Autowired
	private MemberService memberService;
	//用户信息
	@Autowired
	private UserServiceImpl userService;
	//健康档案
	@Autowired
	private HealthKeywordDetailService healthKeywordDetailService;
	@Autowired
	private HealthKeywordHeadService healthKeywordHeadService;
	
	/**
	 * 来电弹屏
	 */
	@RequestMapping(params = ("method=getInfo"))
	public String getInfo(HttpServletRequest request,HttpSession session, ModelMap modelMap) {
		String uri = request.getRequestURI();
		String ac = uri.substring(uri.lastIndexOf("/")+1);
		modelMap.put("callerAction", ac);
		
		Object opFlag = request.getAttribute("opFlag");
		if(opFlag!=null && "1".equals(opFlag.toString())){//在线工单
			//沟通记录来源：在线客服
			modelMap.put("commuSource", "ZX");
		}else{
			opFlag = "0";//来电弹屏（呼入电话）
			modelMap.put("commuSource", "TEL_IN");
		}
		modelMap.put("opFlag", opFlag);
		
		//会员手机号
		String phoneNo = request.getParameter("phoneNo");
		
		//呼叫中心访问
		if("callerScreen.do".equals(ac)){
			modelMap.put("healAction", "healthRecords2.do");
			modelMap.put("memAction", "member2.do");
			modelMap.put("outAction", "outScreen.do");
			modelMap.put("commuAction", "commu.do");//沟通记录
			
			//客服编号
			String custServCode = request.getParameter("custServCode");
			//校验码
			String reqSign = request.getParameter("sign");		
			if(custServCode==null || custServCode.trim().length()<=0){
				modelMap.put("mesg", "客服编号为空！");
				return "opcenter/custService/permissionTip";
			}
			
			/**对请求加密*/
			Map<String, String> map = new HashMap<String, String>();
			//来电，需要验证手机号。在线工单没有电话，不用验证
			if(opFlag==null || !"1".equals(opFlag.toString())){
				map.put("phoneNo",phoneNo);
				
				//官网会员级别
				String memberGrade = getOfficalMemberGrade(phoneNo);
				//查询会员信息
				Member member = this.getMember(phoneNo);
				if(member==null){
					member = new Member();
					member.setTel(phoneNo);
					if("非官网会员".equals(memberGrade)){
						memberGrade = "非会员";
					}
				}
				modelMap.put("memberGrade", memberGrade);
				modelMap.put("member",member);
			}
			map.put("custServCode",custServCode);		
			HashMap<String, String> signMap = SignUtils.sign(map, outScreenSign);
			String sign = signMap.get("appSign");
			
			if(!sign.equals(reqSign)){
				modelMap.put("mesg", "参数签名验证失败！");
				return "opcenter/custService/permissionTip";
			}			
			session.setAttribute("custServCode", custServCode);
			//客服姓名
			User user = userService.loadUserByUserName(custServCode.toString());
			session.setAttribute("adminUserName", user.getName());
		}else{//运营中心后台访问   新增订单菜单
			modelMap.put("healAction", "healthRecords.do");
			modelMap.put("memAction", "member.do");
			modelMap.put("outAction", "outScreen2.do");
			modelMap.put("commuAction", "commu2.do");//沟通记录
			
			User user = this.getCurrentUser();
			modelMap.put("user", user);
			//当前用户名作为客服编号
			session.setAttribute("custServCode", user.getUserName());
			session.setAttribute("adminUserName", user.getName());
		}
		
		//时间标示
		String dateString = DateUtil.getDateToString();
		modelMap.put("dateString", dateString);
		List<ProductVO> newList = new ArrayList<ProductVO>();
		session.setAttribute("prodList", newList);
		return "opcenter/callerService/callerScreen";
	}
	
	/** 获取官网会员等级
	 */
	private String getOfficalMemberGrade(String tel){
		if(tel==null || "".equals(tel.trim())){
			return null;
		}
		String memberGrade = null;
		try {
			//查询官网会员等级
			RestTemplate restTemplate = new RestTemplate();
			String restUri =officialUrl + "/v1/kefu/user/account/detail.jsonp?token={token}&uuid={uuid}&phone={aname}";
			/**设定参数 */
			Map<String, String> variables = new HashMap<String, String>();
			variables.put("token", accountToken);
			variables.put("uuid", accountUUID);
			variables.put("aname", tel.trim());
			String response = restTemplate.getForObject(restUri,String.class,variables);
			/**解析返回结果 */
			JSONObject jsonObject = JSONObject.fromObject(response);
			String responseString = jsonObject.getString("response");
			JSONObject jsonResponse = JSONObject.fromObject(responseString);
			int code = Integer.parseInt(jsonResponse.getString("code"));
			if(code == 1){
				String userBaseAccountInfo = jsonResponse.getString("userBaseAccountInfo");
				if(null != userBaseAccountInfo && !"".equals(userBaseAccountInfo.trim())){
					JSONObject jsonUserBaseAccountInfo = JSONObject.fromObject(userBaseAccountInfo);
					String userType = jsonUserBaseAccountInfo.getString("userType");
					if (userType != null && !"".equals(userType = userType.trim())) {
						if ("10000".equals(userType)) {
							memberGrade = "普通用户";
						} else if (userType.startsWith("2000") && userType.length() == 5) {
							memberGrade = "VIP" + userType.substring(userType.length() - 1);
						}
					}
				}
			}else{
				memberGrade = "非官网会员";
			}
		} catch (Exception e) {
		}
		return memberGrade;
	}
	
	/**
	 * 菜单 ：新增订单（在线工单），进入新增订单页面。
	 * 以来电弹屏作为新增订单（在线工单）页面
	 */
	@RequestMapping(params = ("method=goAddOrder"))
	public String goAddOrder(HttpServletRequest request,HttpSession session, ModelMap modelMap,ProductVO pvo) {
		//操作类型标识，1：在线工单
		request.setAttribute("opFlag", "1");
		modelMap.put("opFlag", "1");
		String res = this.getInfo(request, session, modelMap);
		return res;
	}
	
	/**
	 * 根据手机号获取会员信息
	 * @param tel 会员手机号
	 * @return 会员信息
	 */
	private Member getMember(String tel){
		Member member = null;
		if(tel != null && !"".equals(tel.trim())){
			String hqlString = " and TRIM(tel) = '" + tel.trim() + "'";
			//查询会员信息
			List<Member> memberList = memberService.queryMemberList(0, 20, hqlString);
			if(memberList!=null && memberList.size()>0){
				member = memberList.get(0);
			}
		}
		return member;
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
	
	/**
	 * 菜单：官网订单
	 */
	@RequestMapping(params = ("method=goOfficialOrder"))
	public String goOfficialOrder(HttpServletRequest request,HttpSession session, ModelMap modelMap,String orderSn,String orderStatr,String orderType,String paymentType,String startTime,String endTime,String orderBy,String sort,String createAdmin){
		try {
			String uri = request.getRequestURI();
			String ac = uri.substring(uri.lastIndexOf("/")+1);
			//呼叫中心访问，需要加密验证
			if("callerScreen.do".equals(ac)){
				//客服编号
				String custServCode = request.getParameter("custServCode");
				//校验码
				String reqSign = request.getParameter("sign");
				
				if(custServCode==null || custServCode.trim().length()<=0){
					modelMap.put("mesg", "客服编号为空！");
					return "opcenter/custService/permissionTip";
				}
				
				/**对请求加密*/
				Map<String, String> map = new HashMap<String, String>();
				//参与签名的参数
				map.put("custServCode",custServCode);
				
				HashMap<String, String> signMap = SignUtils.sign(map, outScreenSign);
				String sign = signMap.get("appSign");
				
				if(!sign.equals(reqSign)){
					modelMap.put("mesg", "新增订单：参数签名验证失败！");
					return "opcenter/custService/permissionTip";
				}
				//验证通过后，将客服工号放到session中
				request.getSession().setAttribute("custServCode", custServCode);
				//客服姓名
				User user = userService.loadUserByUserName(custServCode.toString());
				request.getSession().setAttribute("adminUserName", user.getName());
			} else {
				User user = this.getCurrentUser();
				//客服编号
				request.getSession().setAttribute("custServCode", user != null ? user.getUserName():"");
				//客服姓名
				request.getSession().setAttribute("adminUserName", user.getName());
			}
			this.getOfficialOrder(request,session, modelMap,orderSn,orderStatr,orderType,paymentType,startTime,endTime,orderBy,sort,createAdmin);
		} catch (Exception e) {
			logger.error("官网订单访问失败！",e);
			modelMap.put("mesg", "官网订单访问失败！");
			return "opcenter/custService/permissionTip";
		}
		return "opcenter/callerService/official_order_main";
	}
	
	/**
	 * 官网订单
	 * @param orderSn  订单号
	 * @param status
	 *              订单状态（0为未付款、20：待发货、30：已发货、40：已完成（用户确认）、50：已取消。51 退换货（目前只有天猫数据，商城不提供功能操作。）,52 锁定状态（目前只针对京东，商城不提供功能操作。））
	 * @param phone  收货人手机
	 * @param paymentMethodType 支付方式 （0为线上支付，1为线下支付）
	 * @param pageIndex  第几页
	 * @param pageNumber 每页显示的条数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = ("method=getOfficialOrder"))
	public String getOfficialOrder(HttpServletRequest request,HttpSession session, ModelMap modelMap,String orderSn,String orderStatr,String orderType,String paymentType,String startTime,String endTime,String orderBy,String sort,String createAdmin){
		String uri = request.getRequestURI();
		String ac = uri.substring(uri.lastIndexOf("/")+1);
		modelMap.put("callAction", ac);
		//获取时间标示
		String dateString = DateUtil.getDateToString();
		modelMap.put("dateString", dateString);
		if("callerScreen.do".equals(ac)){
			modelMap.put("outAction","outScreen.do");
		}else{
			modelMap.put("outAction","outScreen2.do");
		}
		//获取当前用户角色
		/*Authentication autentication= SecurityContextHolder.getContext().getAuthentication();
		Collection<GrantedAuthority> list = autentication.getAuthorities();*/
		//订单开始日期
		String orderStartTime;
		if(null != startTime && !"".equals(startTime)){
			orderStartTime = startTime;
		} else {
			orderStartTime = null;
		}
		//订单结束日期
		String orderEndTime;
		if(null != endTime && !"".equals(endTime)){
			orderEndTime = endTime;
		} else {
			orderEndTime = null;
		}
		//收货人手机号
		String phone  = request.getParameter("phoneNo");
		//订单编号
		String orderCode;
		if(null != orderSn && !"".equals(orderSn)){
			orderCode = orderSn;
		} else {
			orderCode = null;
		}
		//订单状态
		String status;
		if(null != orderStatr && !"".equals(orderStatr) && !orderStatr.equals("default")){
			status = orderStatr;
		} else {
			status = null;
		}
		//支付方式
		String paymentMethodType;
		if(null != paymentType && !"".equals(paymentType) && !paymentType.equals("default")){
			paymentMethodType = paymentType;
		} else {
			paymentMethodType = null;
		}
		//处方药
		String adminType;
		if(null != orderType && !"".equals(orderType) && !orderType.equals("default")){
			adminType = orderType;
		} else {
			adminType = null;
		}
		//客服工号
		String createAdminUser;
		if(null != createAdmin && !"".equals(createAdmin) && !createAdmin.equals("default")){
			createAdminUser = createAdmin;
		} else {
			createAdminUser = null;
		}
		//第几页
		String pageIndex = request.getParameter("pageIndex");
		//每页显示的条数
		String pageSize = request.getParameter("pageSize");
		if( pageno == null || "".equals(pageno) ){
			this.setPageno(1);
		}else{
			if(null != pageIndex && !"".equals(pageIndex)){
				this.setPageno(Integer.parseInt(pageIndex));
			} else {
				this.setPageno(1);
			}
			if( this.getPageno() < 1 ){
				this.setPageno(1);
			}
			
		}
		if( pageSize == null || "".equals(pageSize) || "".equals(pageSize) ){
			this.setPageSize(5);
		}else{
			this.setPageSize(Integer.parseInt(pageSize));
		}
		String pageNumbers = this.getPageSize().toString();
		HashMap<String, Object> objectOrderBean = new HashMap<String, Object>();
		List<OrderBean> orderList = null;
		try {
			OrderQueryParam orderQueryParam = new OrderQueryParam();
			//订单开始日期
			orderQueryParam.setGtAddTime(orderStartTime);
			//订单结束日期
			orderQueryParam.setLtAddTime(orderEndTime);
			//订单号
			orderQueryParam.setOrderSn(orderCode);
			//收货人
			orderQueryParam.setMobile(phone);
			//订单状态
			orderQueryParam.setStatus(status);
			//支付方式
			orderQueryParam.setPaymentMethodType(paymentMethodType);
			//处方药
			orderQueryParam.setOrderType(adminType);
			//排序关键字
			orderQueryParam.setOrderby("add_time");
			//1:升序，2：降序
			orderQueryParam.setSort("2");
			//客服工号
			orderQueryParam.setCreateAdminUser(createAdminUser);
			//页数
			orderQueryParam.setPn(this.getPageno().toString());
			//条数
			orderQueryParam.setPz(pageNumbers);
			objectOrderBean = callerScreenService.getOfficialOrder(orderQueryParam);
			orderList = (List<OrderBean>) objectOrderBean.get("orderList");
		} catch (Exception e) {
			logger.error("来电弹屏调用官网订单列表接口失败"+e.getMessage());
			modelMap.put("orderMessage", "调用官网订单列表接口失败");
		}
		if(null != objectOrderBean){
			PageTurn pageInfo = (PageTurn) objectOrderBean.get("pageInfo");
			recTotal = pageInfo != null ? pageInfo.getRowCount() : 0;
			pageTotal = recTotal / this.getPageSize();
			pageTotal = recTotal % this.getPageSize() == 0 ? pageTotal : pageTotal + 1;
		    modelMap.put("orderList", orderList);
			modelMap.put("pageno", this.getPageno());
			modelMap.put("pageTotal", pageTotal);
			modelMap.put("pageSize", this.getPageSize());
			modelMap.put("recTotal", recTotal);
			modelMap.put("orderSn", orderSn);
			modelMap.put("orderStatr", orderStatr);
			modelMap.put("orderType", orderType);
			modelMap.put("paymentType", paymentType);
			modelMap.put("phoneNo",phone);
			modelMap.put("startTime", startTime);
			modelMap.put("endTime", endTime);	
			modelMap.put("createAdmin", createAdmin);
		}
		return "opcenter/callerService/official_order";
	}
	
	/**
	 * 根据订单号查找商品明细
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = ("method=getGoodsDetail"))
	public void getGoodsDetail(HttpServletRequest request,PrintWriter printWriter){
		String orderSn = request.getParameter("orderSn");
		List<OrderGoodsBean> orderGoodsList = callerScreenService.getGoodsDetail(orderSn);
		JSONArray jsonList= JSONArray.fromObject(orderGoodsList);
		String result = jsonList.toString();
		printWriter.write(result);	
		printWriter.flush();
		printWriter.close();
	}
	
	/**
	 * 审核订单、取消订单
	 * @param request
	 * @param printWriter
	 */
	@RequestMapping(params = ("method=auditOrder"))
	public void auditOrder(HttpServletRequest request,PrintWriter printWriter){
		/** 0:审核不通过,1:审核通过，2：订单取消 */
		String type = request.getParameter("type");
		/** 订单号 */
		String orderSn = request.getParameter("orderSn");
		/** 操作者帐号 */
		String operatorAccount = request.getParameter("operatorAccount");
		/** 理由 */
		String reason = request.getParameter("auditRemark");
		/** 操作者ip */
		String operatorIp = request.getHeader("x-forwarded-for");  
		if(operatorIp == null || operatorIp.length() == 0 || "unknown".equalsIgnoreCase(operatorIp)) {  
			operatorIp = request.getHeader("Proxy-Client-IP");  
		}  
		if(operatorIp == null || operatorIp.length() == 0 || "unknown".equalsIgnoreCase(operatorIp)) {  
			operatorIp = request.getHeader("WL-Proxy-Client-IP");  
		}  
		if(operatorIp == null || operatorIp.length() == 0 || "unknown".equalsIgnoreCase(operatorIp)) {  
			operatorIp = request.getRemoteAddr();  
		}		
		String updateProxyOrderStatus = null;
		try {
			updateProxyOrderStatus = OrderClient.updateProxyOrderStatus(type, orderSn, operatorIp, operatorAccount, reason);
		} catch (Exception e) {
			if(type.equals("0")){
				logger.error("调用审核不通过接口出错",e);
			}
			if(type.equals("1")){
				logger.error("调用审核通过接口出错",e);
			}
			if(type.equals("2")){
				logger.error("调用订单取消接口出错",e);
			}
		}
		if(updateProxyOrderStatus == null){
			updateProxyOrderStatus = "success";
		}
		printWriter.write(updateProxyOrderStatus);
		printWriter.flush();
		printWriter.close();
	}
	
	/**
	 * 修改未审核的订单
	 * @param request
	 * @param session
	 * @param printWriter
	 */
	@RequestMapping(params = ("method=updateOrder"))
	public void updateOrder(HttpServletRequest request,HttpSession session,PrintWriter printWriter){
		String orderSn = request.getParameter("orderSn");
		String dateString = request.getParameter("dateString");
		try {
			ProxyOrderInfoBean orderInfoBean= OrderClient.getProxyOrderInfoBean(orderSn);
			List<AddGoodsBean> goodsBeanList = orderInfoBean.getAddGoodsList();
			List<MealVo> prodList = new ArrayList<MealVo>();
			MealVo mealVO;
			for (int i = 0; i < goodsBeanList.size(); i++) {
				AddGoodsBean goodsBean = goodsBeanList.get(i);
				mealVO = new MealVo();
				//商品数量
				String number = String.valueOf(goodsBean.getBuyCount());
				mealVO.setBuyCount(number);
				//商品价格
				Double price = goodsBean.getPrice();
				mealVO.setPrice(price.toString());
				//商品总价
				BigDecimal buyCount = new BigDecimal(goodsBean.getBuyCount());
				BigDecimal bigPrice = new BigDecimal(String.valueOf(goodsBean.getPrice()));
				Double amount = buyCount.multiply(bigPrice).doubleValue();
				mealVO.setAmount(amount.toString());
			    int mealIds = goodsBean.getMealId();
			    MealQueryParam mealQueryParam = new MealQueryParam();
			    mealQueryParam.setMealId(String.valueOf(mealIds));
			    PageList<Meal> meals;
				try {
					meals = MealClient.getList(mealQueryParam);			
				    for (int j = 0; j < meals.getDataList().size(); j++) {
				    	Meal meal = meals.getDataList().get(j);
				    	//商品套餐id
				    	mealVO.setMealId(meal.getMealId().toString());
				    	//套餐名称
				    	mealVO.setMealName(meal.getMealName());
				    	//主商品SKU
				    	mealVO.setMainSku(meal.getMainSku());
				    	//套餐规格
				    	mealVO.setMealNormName(meal.getMealNormName());
				    	//是否处方药
				    	mealVO.setPrescriptionType(String.valueOf(meal.getPrescriptionType()));
				    	//产品ID
				    	mealVO.setProductId(String.valueOf(meal.getMainProductId()));
					}
				    prodList.add(mealVO);
				} catch (Exception e) {
					logger.error("获取订单商品列表错误", e);
				}
			}
			//获取快递信息
			logger.info("orderInfoBean"+orderInfoBean.getStoreShipFeeId());
			List<StoreShipFeeBean> expressList = OrderClient.getCfyShipFeeList();
			JSONArray shipArray = JSONArray.fromObject(expressList);
			logger.info("shipArray"+shipArray.toString());
			//将客服工号存到session
			String custServCode = (String) session.getAttribute("custServCode");
			String adminUserName = (String) session.getAttribute("adminUserName");
			//计算快递费用、总金额
			String expressId = String.valueOf(orderInfoBean.getStoreShipFeeId());
			OrderInfoVO orderInfo = outScreenAction.getOrderInfo(prodList,expressId);
			logger.info("orderInfoVo 快递费用"+orderInfo.getPostageInfo()+"促销信息"+"商品总价（含运费）"+"运费"+"优惠金额");
			//将商品列表存到session
			session.setAttribute("updateProdList"+dateString, prodList);
			//获取订单来源列表
			List<CpsMerchantBean> cpsLaiyuanList = OrderClient.getCpsMerchantList();
			JSONArray laiYuanArray = JSONArray.fromObject(cpsLaiyuanList);
			//将订单信息转换成jsonObject
			JSONObject jsonObjct = JSONObject.fromObject(orderInfo);
			//将商品类表装换成jsonArray
			JSONArray jsonArray = JSONArray.fromObject(prodList);
			//声明返回jsonObject
			JSONObject object = new JSONObject();
			object.put("orderInfoBean", orderInfoBean);
			object.put("shipArray",shipArray);
			object.put("productList", jsonArray);
			object.put("orderInfo", jsonObjct);
			object.put("custServCode", custServCode);
			object.put("adminUserName", adminUserName);
			object.put("cpsLaiyuan", laiYuanArray);
			//订单操作类型
			object.put("orderOperationType", "updateOrder");
			printWriter.write(object.toString());			
		} catch (Exception e) {
			logger.error("查询订单错误", e);
			printWriter.write("{\"message\":\"查询订单错误\"}");
		} finally{
			printWriter.flush();
			printWriter.close();
		}
	}
	
	/**
	 * 提交修改订单
	 * @param request
	 * @param printWriter
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = ("method=submitUpdateOrder"))
	public void submitUpdateOrder(HttpServletRequest request,PrintWriter printWriter,HttpSession session){
		String result = null;
		//时间标示
		String dateString = request.getParameter("dateString");
		//订单编号
		String orderSn = null;
		//后台操作者帐号
		String operatorAccount  = null;
		//收货地址id
		int userAddressId = 0;
		//支付方式id
		int paymentMethodId = 0;
		//送货方式id
		int shipMethodId = 0;
		//送货时间id
		int shipTimeId = 0;
		//备注
		String remark = null;
		//发票类型（0为不需要发票，1为个人发票,2为单位发票）
		int invoiceType = 0;
		//发票抬头。当发票类型为2，即单位发票时，需要填写
		String invoiceTitle = null;
		//后台操作者ip
		String operatorIp = null;
		//订单来源
		String cpsLaiyuan = null;
		//商品列表
		List<AddGoodsBean> addGoodsList = null ;
		//优惠金额
		String adminDiscount = null;
		//订单总额
		String orderAmount = null;
		try {
			//订单编号
			orderSn = request.getParameter("orderSn");
			//后台操作者帐号
			operatorAccount = request.getParameter("createAdminUser");
			//收货地址id
			userAddressId = Integer.parseInt(request.getParameter("userAddressId"));
			//支付方式id
			paymentMethodId = Integer.parseInt(request.getParameter("paymentMethodId"));
			//送货方式id
			shipMethodId = Integer.parseInt(request.getParameter("shipMethodId"));
			//送货时间id
			shipTimeId = Integer.parseInt(request.getParameter("shipTimeId"));
			//备注
			remark = request.getParameter("remark");
			//发票类型（0为不需要发票，1为个人发票,2为单位发票）
			invoiceType = Integer.parseInt(request.getParameter("invoiceType"));
			//发票抬头。当发票类型为2，即单位发票时，需要填写
			invoiceTitle = request.getParameter("invoiceTitle");
			//后台操作者ip
			operatorIp = request.getHeader("x-forwarded-for");
			if(operatorIp == null || operatorIp.length() == 0 || "unknown".equalsIgnoreCase(operatorIp)) {  
				operatorIp = request.getHeader("Proxy-Client-IP");  
			}  
			if(operatorIp == null || operatorIp.length() == 0 || "unknown".equalsIgnoreCase(operatorIp)) {  
				operatorIp = request.getHeader("WL-Proxy-Client-IP");  
			}  
			if(operatorIp == null || operatorIp.length() == 0 || "unknown".equalsIgnoreCase(operatorIp)) {  
				operatorIp = request.getRemoteAddr();  
			}
			//订单来源
			cpsLaiyuan = request.getParameter("cpsLaiyuan");
			//优惠金额
			adminDiscount = request.getParameter("adminDiscount");
			//订单总额
			orderAmount = request.getParameter("orderAmount");
			//从Session里获取商品列表
			List<MealVo> prodList = (List<MealVo>) session.getAttribute("updateProdList"+dateString);
			if(prodList == null){
				prodList = new ArrayList<MealVo>();
			}
			addGoodsList = new ArrayList<AddGoodsBean>();
			AddGoodsBean addGoodsBean = null;
			for(MealVo bean:prodList){
				addGoodsBean = new AddGoodsBean();			
				if(bean.getMealId() != null && !"".equals(bean.getMealId().trim())){
					addGoodsBean.setMealId(Integer.parseInt(bean.getMealId()));
				}			
				if(bean.getBuyCount() != null && !"".equals(bean.getBuyCount().trim())){
					addGoodsBean.setBuyCount(Integer.parseInt(bean.getBuyCount()));
				}
				if(bean.getProductId() != null && !"".equals(bean.getProductId().trim())){
					addGoodsBean.setProductId(Integer.parseInt(bean.getProductId()));
				}
				if(bean.getPrice() != null && !"".equals(bean.getPrice().trim())){
					addGoodsBean.setPrice(Double.parseDouble(bean.getPrice()));
				}
				addGoodsList.add(addGoodsBean);
			}
		} catch (NumberFormatException e) {
			logger.error("提交修改订单取前台空值", e);
			e.printStackTrace();
		}
		try {
			UpdateProxyOrderInfoBean updateProxyOrderInfoBean = new UpdateProxyOrderInfoBean();
			//商品列表
			updateProxyOrderInfoBean.setAddGoodsList(addGoodsList);
			//订单编号
			updateProxyOrderInfoBean.setOrderSn(orderSn);
			//后台操作者帐号
			updateProxyOrderInfoBean.setOperatorAccount(operatorAccount);
			//收货地址id
			updateProxyOrderInfoBean.setUserAddressId(userAddressId);
			//支付方式id
			updateProxyOrderInfoBean.setPaymentMethodId(paymentMethodId);
			//送货方式id
			updateProxyOrderInfoBean.setStoreShipFeeId(shipMethodId);
			//送货时间id
			updateProxyOrderInfoBean.setShipTimeId(shipTimeId);
			//发票类型（0为不需要发票，1为个人发票,2为单位发票）
			updateProxyOrderInfoBean.setInvoiceType(invoiceType);
			//发票抬头。当发票类型为2，即单位发票时，需要填写
			updateProxyOrderInfoBean.setInvoiceTitle(invoiceTitle);
			//后台操作者ip
			updateProxyOrderInfoBean.setOperatorIp(operatorIp);
			//备注
			updateProxyOrderInfoBean.setRequireNote(remark);
			//订单来源
			updateProxyOrderInfoBean.setCpsLaiyuan(cpsLaiyuan);
			//优惠金额
			BigDecimal discountsBig = null;
			//订单总额
			BigDecimal orderAmountBig = new BigDecimal(orderAmount);
			double orderAmountTemp = orderAmountBig.doubleValue();
			if(null != adminDiscount && !"".equals(adminDiscount)){
				discountsBig = new BigDecimal(adminDiscount);
				updateProxyOrderInfoBean.setAdminDiscount(discountsBig.doubleValue());
				orderAmountTemp = orderAmountBig.subtract(discountsBig).doubleValue();
			}
			//订单总额
			updateProxyOrderInfoBean.setOrderAmount(orderAmountTemp);		
			result = OrderClient.editProxyOrder(updateProxyOrderInfoBean);
		} catch (Exception e) {
			logger.error("调用修改订单接口错误",e);
		}
		if(result == null){
			result = "success";
		}
		printWriter.write(result);
		printWriter.flush();
		printWriter.close();
	}
	
	/**
	 * 获取物流信息
	 * @param printWriter
	 * @param orderCode
	 */
	@RequestMapping(params = ("method=getExpressInfo"))
	public void getExpressInfo(PrintWriter printWriter,String orderCode){
		JSONObject jsonObject = new JSONObject();
		try {
			ExpressDetail expressInfo = OrderClient.getExpressDetail(orderCode);
			jsonObject.put("expressInfo", expressInfo);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject = null;
		}
		printWriter.write(jsonObject.toString());
		printWriter.flush();
		printWriter.close();
	}
	
	/**
	 * 查看订单日志
	 */
	@RequestMapping(params = ("method=getOrderLogInfo"))
	public void getOrderLogInfo(PrintWriter printWriter,String orderCode){
		PageList<OrderLogBean> pageList;
		JSONObject jsonObject = new JSONObject();
		try {
			pageList = OrderClient.getOrderLog(orderCode);
			List<OrderLogBean> orderLogList = pageList.getDataList();
			if(null != orderLogList && orderLogList.size() > 0){
				JSONArray array = JSONArray.fromObject(orderLogList);
				JSONObject object = null;
				Date time = null;
				for (int i = 0; i < array.size(); i++) {
					 object = (JSONObject) array.get(i);
					 time = orderLogList.get(i).getActTime();
					 if(null != time && !"".equals(time)){
						 object.put("actTime",sdf.format(time));						 
					 } else {
						 object.put("actTime","");
					 }
				}
				jsonObject.put("orderLogInfo", array);
			} else {
				jsonObject.put("message", "订单日志空");
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		printWriter.write(jsonObject.toString());
		printWriter.flush();
		printWriter.close();
	}
	/**
	 * 官网会员
	 */
	@RequestMapping(params = ("method=getOfficalMember"))
	public String getOfficalMember(HttpServletRequest request, ModelMap modelMap,String tel) {
		return outScreenAction.getOfficalMember(request, modelMap, tel);
	}
	/**
	 * 个人信息
	 */
	@RequestMapping(params = ("method=getMemberInfo"))
	public String getMemberInfo(HttpServletRequest request, ModelMap modelMap,String tel) {
		return outScreenAction.getMemberInfo(request, modelMap, tel);
	}
	/**
	 * 个人资产
	 */
	@RequestMapping(params = ("method=getMemberAssets"))
	public String getMemberAssets(HttpServletRequest request, ModelMap modelMap,String userId) {
		return outScreenAction.getMemberAssets(request, modelMap, userId);
	}
	/**
	 * 查看现金账户信息
	 */
	@RequestMapping(params = ("method=getCashInfo"))
	public String getCashInfo(HttpServletRequest request,ModelMap modelMap,String userId) {
		return outScreenAction.getCashInfo(request, modelMap, userId);
	}
	/**
	 * 查看积分账户信息
	 */
	@RequestMapping(params = ("method=getIntegralInfo"))
	public String getIntegralInfo(HttpServletRequest request,ModelMap modelMap,String userId) {
		return outScreenAction.getIntegralInfo(request,modelMap,userId);
	}
	/**
	 * 查看优惠券信息
	 */
	@RequestMapping(params = ("method=getCouponInfo"))
	public String getCouponInfo(HttpServletRequest request,String userId, ModelMap modelMap) {
		return outScreenAction.getCouponInfo(request, userId, modelMap);
	}
	/**
	 * 可用优惠券
	 */
	@RequestMapping(params = ("method=getEnableCoupon"))
	public String getEnableCoupon(HttpServletRequest request,ModelMap modelMap,String userId) {
		return outScreenAction.getEnableCoupon(request, modelMap, userId);
	}
	/**
	 * 已使用优惠券
	 */
	@RequestMapping(params = ("method=getDisableCoupon"))
	public String getDisableCoupon(HttpServletRequest request,String userId, ModelMap modelMap) {
		return outScreenAction.getDisableCoupon(request, userId, modelMap);
	}
	/**
	 * 已过期优惠券
	 */
	@RequestMapping(params = ("method=getOverdueCoupon"))
	public String getOverdueCoupon(HttpServletRequest request,String userId, ModelMap modelMap) {
		return outScreenAction.getOverdueCoupon(request, userId, modelMap);
	}
	/**
	 * 查看送货地址信息
	 */
	@RequestMapping(params = ("method=getUserAddressInfo"))
	public String getUserAddressInfo(HttpServletRequest request,String userId, ModelMap modelMap) {
		
		return outScreenAction.getUserAddressInfo(request, userId, modelMap);
	}
	/**
	 * 查看家庭成员
	 */
	@RequestMapping(params = ("method=getFamilysInfo"))
	public String getFamilysInfo(HttpServletRequest request,String userId, ModelMap modelMap) {
		
		return outScreenAction.getFamilysInfo(request, userId, modelMap);
	}
	/**
	 * 查看浏览记录
	 */
	@RequestMapping(params = ("method=getBrowseRecord"))
	public String getBrowseRecord(HttpServletRequest request,String userId, ModelMap modelMap) {
		
		return outScreenAction.getBrowseRecord(request, userId, modelMap);
	}
	/**
	 * 查看用户收藏
	 */
	@RequestMapping(params = ("method=getUserCollection"))
	public String getUserCollection(HttpServletRequest request,String userId, ModelMap modelMap) {
		
		return outScreenAction.getUserCollection(request, userId, modelMap);
	}
	
	/**
	 * 沟通记录到代下单
	 */
	@RequestMapping(params = ("method=addAdvisoryOrder"))
	public String addAdvisoryOrder(HttpServletResponse response,HttpServletRequest request,HttpSession session,ModelMap modelMap,String mealIds) {
		
		return outScreenAction.addAdvisoryOrder(response, request, session, modelMap, mealIds);
	}
	/**
	 * 多sku，父子套餐代下单
	 */
	@RequestMapping(params = ("method=addOrderSku"))
	public String addOrderSku(HttpServletResponse response,HttpServletRequest request,HttpSession session, ModelMap modelMap,String products,String suits) {
		return outScreenAction.addOrderSku(response, request, session, modelMap, products, suits);
	}
}
