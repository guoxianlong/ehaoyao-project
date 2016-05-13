package com.ehaoyao.opertioncenter.custServiceCenter.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ehaoyao.admin.ice.business.CartClient;
import com.ehaoyao.admin.ice.business.MealClient;
import com.ehaoyao.admin.ice.business.MealSkusClient;
import com.ehaoyao.admin.ice.business.OrderClient;
import com.ehaoyao.admin.ice.business.UserClient;
import com.ehaoyao.commandice.model.ResponseMessage;
import com.ehaoyao.ice.common.bean.CpsMerchantBean;
import com.ehaoyao.ice.common.bean.MallUserBean;
import com.ehaoyao.ice.common.bean.Meal;
import com.ehaoyao.ice.common.bean.MealQueryParam;
import com.ehaoyao.ice.common.bean.MealSku;
import com.ehaoyao.ice.common.bean.OrderPromotionBean;
import com.ehaoyao.ice.common.bean.PageList;
import com.ehaoyao.ice.common.bean.PageTurn;
import com.ehaoyao.ice.common.bean.PaymentMethod;
import com.ehaoyao.ice.common.bean.ProxyOrderInfoBean;
import com.ehaoyao.ice.common.bean.ProxyOrderInfoBean.AddGoodsBean;
import com.ehaoyao.ice.common.bean.ShipTimeBean;
import com.ehaoyao.ice.common.bean.ShowPostVO;
import com.ehaoyao.ice.common.bean.StoreShipFeeBean;
import com.ehaoyao.ice.common.bean.UserAddressBean;
import com.ehaoyao.opertioncenter.common.DateUtil;
import com.ehaoyao.opertioncenter.custServiceCenter.model.Communication;
import com.ehaoyao.opertioncenter.custServiceCenter.model.Product;
import com.ehaoyao.opertioncenter.custServiceCenter.model.Reservation;
import com.ehaoyao.opertioncenter.custServiceCenter.service.CommuService;
import com.ehaoyao.opertioncenter.custServiceCenter.service.ReservationService;
import com.ehaoyao.opertioncenter.custServiceCenter.service.TelGoodsService;
import com.ehaoyao.opertioncenter.custServiceCenter.util.PropertiesUtil;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.BuyRecordVO;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.CardProductInfo;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.CartInfos;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.CommunicationVO;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.MealVo;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.OrderInfoVO;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.ProductVO;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.ReservationVO;
import com.ehaoyao.opertioncenter.doctorInHospital.model.DoctorModel;
import com.ehaoyao.opertioncenter.doctorInHospital.model.DoctorUrlModel;
import com.ehaoyao.opertioncenter.doctorInHospital.model.SalesRepModel;
import com.ehaoyao.opertioncenter.doctorInHospital.service.DoctorService;
import com.ehaoyao.opertioncenter.doctorInHospital.service.DoctorUrlService;
import com.ehaoyao.opertioncenter.doctorInHospital.service.HosDoctorUrlService;
import com.ehaoyao.opertioncenter.doctorInHospital.service.SalesRepService;
import com.ehaoyao.opertioncenter.doctorInHospital.vo.HosDoctorUrlVO;
import com.ehaoyao.opertioncenter.member.model.HealthKeywordDetail;
import com.ehaoyao.opertioncenter.member.model.HealthKeywordHead;
import com.ehaoyao.opertioncenter.member.model.Member;
import com.ehaoyao.opertioncenter.member.service.HealthKeywordDetailService;
import com.ehaoyao.opertioncenter.member.service.HealthKeywordHeadService;
import com.ehaoyao.opertioncenter.member.service.MemberService;
import com.ehaoyao.opertioncenter.member.vo.HealthkeywordVO;
import com.ehaoyao.opertioncenter.member.vo.MemberVO;
import com.ehaoyao.system.util.MealRelateTypeEnum;
import com.haoyao.goods.action.BaseAction;
import com.haoyao.goods.model.User;
import com.haoyao.goods.service.UserServiceImpl;
import com.haoyao.goods.util.MD5Utils;
import com.haoyao.goods.util.SignUtils;

/**
 * 
 * Title: OutScreenAction.java
 * 
 * Description: 去电弹屏
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年8月15日 下午2:18:42
 */
@Controller
@RequestMapping({"/outScreen.do","/outScreen2.do"})
public class OutScreenAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(OutScreenAction.class);
	//会员档案
	@Autowired
	private MemberService memberService;
	//电销商品
	@Autowired
	private TelGoodsService telGoodsService;
	//沟通记录
	@Autowired
	private CommuService commuService;
	
	//用户信息
	@Autowired
	private UserServiceImpl userService;
	//健康档案
	@Autowired
	private HealthKeywordDetailService healthKeywordDetailService;
	@Autowired
	private HealthKeywordHeadService healthKeywordHeadService;
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private SalesRepService salesRepService;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private HosDoctorUrlService hosDoctorUrlService;
	@Autowired
	private DoctorUrlService doctorUrlService;
	
	private RestTemplate restTemplate = new RestTemplate();
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//从erp获取购买记录时的请求
	private String buyUrl = PropertiesUtil.getProperties("extend.properties", "buyUrl");
	//从erp获取购买记录时的请求加密标志
	private String buySign = PropertiesUtil.getProperties("extend.properties", "buySign");
	//电销客服访问时用的请求加密标志
	private String outScreenSign = PropertiesUtil.getProperties("extend.properties", "outScreenSign");
	//官网地址
	private String officialUrl = PropertiesUtil.getProperties("extend.properties", "officialUrl");
	private String officialUrl2 = PropertiesUtil.getProperties("extend.properties", "officialUrl2");
	private String officialUrl3 = PropertiesUtil.getProperties("extend.properties", "officialUrl3");
	private String officialUrl4 = PropertiesUtil.getProperties("extend.properties", "officialUrl4");
	private String accountToken = PropertiesUtil.getProperties("extend.properties", "accountToken");
	private String accountUUID = PropertiesUtil.getProperties("extend.properties", "accountUUID");
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setTelGoodsService(TelGoodsService telGoodsService) {
		this.telGoodsService = telGoodsService;
	}
	
	public void setCommuService(CommuService commuService) {
		this.commuService = commuService;
	}
	
	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}
	/**
	 * @param healthKeywordDetailService the healthKeywordDetailService to set
	 */
	public void setHealthKeywordDetailService(
			HealthKeywordDetailService healthKeywordDetailService) {
		this.healthKeywordDetailService = healthKeywordDetailService;
	}
	/**
	 * @param healthKeywordHeadService the healthKeywordHeadService to set
	 */
	public void setHealthKeywordHeadService(
			HealthKeywordHeadService healthKeywordHeadService) {
		this.healthKeywordHeadService = healthKeywordHeadService;
	}
	/**
	 * @param reservationService the reservationService to set
	 */
	public void setReservationService(ReservationService reservationService) {
		this.reservationService = reservationService;
	}
	
	
	public void setSalesRepService(SalesRepService salesRepService) {
		this.salesRepService = salesRepService;
	}
	
	public void setDoctorService(DoctorService doctorService) {
		this.doctorService = doctorService;
	}
	/**
	 * @return the doctorUrlService
	 */
	public DoctorUrlService getDoctorUrlService() {
		return doctorUrlService;
	}

	/**
	 * @param doctorUrlService the doctorUrlService to set
	 */
	public void setDoctorUrlService(DoctorUrlService doctorUrlService) {
		this.doctorUrlService = doctorUrlService;
	}
	/**
	 * 去电弹屏
	 */
	@RequestMapping(params = ("method=getInfo"))
	public String getInfo(HttpServletRequest request,HttpSession session, ModelMap modelMap) {
		String uri = request.getRequestURI();
		String ac = uri.substring(uri.lastIndexOf("/")+1);
		modelMap.put("actionName", ac);
		
		String type = request.getParameter("type");
		//type弹屏类型，QD：去电弹屏，XQ：需求订单，RW：今日任务
		//commuSource沟通记录来源，即客户来源。TEL_OUT：呼入电话，TEL_OUT：老客维护，ZX：在线客服，XQ：需求登记
		if("RW".equals(type)){
			modelMap.put("isNewTask", "1");
			modelMap.put("commuSource", "TEL_OUT");
		}else if("XQ".equals(type)){
			modelMap.put("commuSource", "XQ");
		}else{//去电弹屏（老客维护），默认
			type="QD";
			modelMap.put("commuSource", "TEL_OUT");
		}
		modelMap.put("type", type);
		try {
			//会员手机号
			String phoneNo = request.getParameter("phoneNo");
			//客服编号
			String custServCode = request.getParameter("custServCode");
			//校验码
			String reqSign = request.getParameter("sign");
			//二维码id
			String twoDimensionCodeId = request.getParameter("twoDimensionCodeId");
			//医生id
			//String doctorId = request.getParameter("doctorId");
			//电销客服访问
			if("outScreen.do".equals(ac)){
				modelMap.put("memAction", "member2.do");
				modelMap.put("healAction", "healthRecords2.do");
				modelMap.put("callerAction", "callerScreen.do");
				modelMap.put("commuAction", "commu.do");//沟通记录
				
				if(custServCode==null || custServCode.trim().length()<=0){
					modelMap.put("mesg", "客服编号为空！");
					return "opcenter/custService/permissionTip";
				}
				
				/**对请求加密*/
				Map<String, String> map = new HashMap<String, String>();
				//type，QD：去电弹屏，RW：今日任务，XQ：需求订单
				//去电弹屏没有type参数，不需验证
				if("RW".equals(type) || "XQ".equals(type)){
					map.put("type", type);
				}
				map.put("phoneNo",phoneNo);
				map.put("custServCode",custServCode);
				
				HashMap<String, String> signMap = SignUtils.sign(map, outScreenSign);
				String sign = signMap.get("appSign");
				
				if(!sign.equals(reqSign)){
					modelMap.put("mesg", "参数签名验证失败！");
					return "opcenter/custService/permissionTip";
				}
			}else{
				//运营中心访问
				modelMap.put("memAction", "member.do");
				modelMap.put("healAction", "healthRecords.do");
				modelMap.put("callerAction", "callerScreen2.do");
				modelMap.put("commuAction", "commu2.do");//沟通记录
			}
			
			if (phoneNo!=null) {
				//查询会员信息
				Member member = this.getMember(phoneNo);
				if(member==null){
					member = new Member();
					member.setTel(phoneNo);
				}
				modelMap.put("member",member);
				
				try {
					//查询官网会员等级
					JSONObject jsonResponse = this.getOfficalMemberInfo(phoneNo);
					int code = Integer.parseInt(jsonResponse.getString("code"));
					if(code == 1){
						String userBaseAccountInfo = jsonResponse.getString("userBaseAccountInfo");
						if(null != userBaseAccountInfo && !"".equals(userBaseAccountInfo.trim())){
							JSONObject jsonUserBaseAccountInfo = JSONObject.fromObject(userBaseAccountInfo);
							String userType = jsonUserBaseAccountInfo.getString("userType");
							if (userType != null && !"".equals(userType = userType.trim())) {
								String memberGrade = null;
								if ("10000".equals(userType)) {
									memberGrade = "普通用户";
								} else if (userType.startsWith("2000") && userType.length() == 5) {
									memberGrade = "VIP" + userType.substring(userType.length() - 1);
								}
								modelMap.put("memberGrade", memberGrade);
							}
						}
					}
				} catch (Exception e) {
				}
			}
			
			if(custServCode==null || "".equals(custServCode.trim())){
				User user = this.getCurrentUser();
				custServCode = user!=null?user.getUserName():null;
			}
			session.setAttribute("custServCode", custServCode);
			
			modelMap.put("user", this.getCurrentUser());
			
			
			List<ProductVO> newList = new ArrayList<ProductVO>();
			session.setAttribute("prodList", newList);
			
			session.setAttribute("orderUserId", "");
			session.setAttribute("orderUserAccount", "");
			session.setAttribute("orderType", "1");
			String dateString = DateUtil.getDateToString();
			modelMap.put("dateString", dateString);
			session.setAttribute("twoDimensionCodeId" + dateString, twoDimensionCodeId);
		} catch (Exception e) {
			logger.error("访问去电弹屏失败",e);
		}
		return "opcenter/custService/outScreen";
	}
	
	/**
	 * 主推商品
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = ("method=getGoodsLs"))
	public String getGoodsLs(HttpServletRequest request, ModelMap modelMap,ProductVO pvo,String dateString) {
		String uri = request.getRequestURI();
		String ac = uri.substring(uri.lastIndexOf("/")+1);
		modelMap.put("actionName", ac);
		pvo.setStatus("1");//主推商品
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
		if( pageSize == null || "".equals(pageSize)){
			this.setPageSize(5);
		}else{
			this.setPageSize(Integer.parseInt(pageSize));
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
		modelMap.put("pageSize", this.getPageSize());
		modelMap.put("recTotal", recTotal);
		modelMap.put("pvo",pvo);
		modelMap.put("dateString", dateString);
		return "opcenter/custService/commodity";
	}
	
	/**
	 * 购买记录
	 */
	@RequestMapping(params = ("method=getBuyRecords"))
	public String getBuyRecords(HttpServletRequest request, ModelMap modelMap, BuyRecordVO buyVO) {
		try {
			String uri = request.getRequestURI();
			String ac = uri.substring(uri.lastIndexOf("/")+1);//获取请求的名字
			modelMap.put("actionName",ac);
			
			if(buyVO!=null){
				//用户名
				String userName = buyVO.getUserName();
				if(userName!=null && (userName=userName.trim()).length()>0){
					try {
						userName = URLDecoder.decode(userName, "UTF-8");
						//页面参数
						buyVO.setUserName(userName);
					} catch (Exception e) {
					}
				}
				
				try {
					if(buyVO.getTelephoneNo()!=null && buyVO.getTelephoneNo().trim().length()>0){
						//手机号
						buyVO.setTelephoneNo(buyVO.getTelephoneNo()!=null?buyVO.getTelephoneNo().trim():"");
						//期间 0:全部
						buyVO.setTimeNum(buyVO.getTimeNum()!=null&&buyVO.getTimeNum().trim().length()>0 ? buyVO.getTimeNum().trim():"0");
						//商品编码
						buyVO.setProductNo(buyVO.getProductNo()!=null?buyVO.getProductNo().trim():"");
						
						String pageno = request.getParameter("pageno");
						String pageSize = request.getParameter("pageSize");
						String total = request.getParameter("recTotal");
						
						if( pageno == null || "".equals(pageno) ){
							this.setPageno(1);
						}else{
							this.setPageno(Integer.parseInt(pageno));
							if( this.getPageno() < 1 ){
								this.setPageno(1);
							}
						}
						
						if( pageSize == null || "".equals(pageSize)){
							this.setPageSize(20);
						}else{
							this.setPageSize(Integer.parseInt(pageSize));
						}
						
						if(total != null && !"".equals(total.trim())){
							int t = Integer.parseInt(total);
							int tPage = t / this.getPageSize();
							tPage = t % this.getPageSize() == 0 ? tPage : (tPage + 1);
							if( this.getPageno() > tPage ){
								this.setPageno(tPage);
							}
						}
						
						/**对请求加密*/
						Map<String, String> map = new HashMap<String, String>();
						//参与签名的参数
						map.put("userName",userName);
						map.put("telephoneNo", buyVO.getTelephoneNo());
						map.put("timeNum", buyVO.getTimeNum());
						map.put("productNo", buyVO.getProductNo());
						map.put("pageNo", this.getPageno()+"");
						map.put("pageSize", this.getPageSize()+"");
						HashMap<String, String> signMap = SignUtils.sign(map, buySign);
						String sign = signMap.get("appSign");
						
						/**设定参数*/
						Map<String, String> Stringiables = new HashMap<String, String>(); 
						Stringiables.put("userName",userName);
						Stringiables.put("telephoneNo", buyVO.getTelephoneNo());
						Stringiables.put("timeNum", buyVO.getTimeNum());
						Stringiables.put("productNo", buyVO.getProductNo());
						Stringiables.put("pageNo", this.getPageno()+"");
						Stringiables.put("pageSize", this.getPageSize()+"");
						Stringiables.put("sign", sign);
						
						try {
							//查询购买记录
							String res = "[]";
							res = restTemplate.getForObject(buyUrl, String.class, Stringiables);
							//res="{\"userName\":\"杨雨晨\",\"telephoneNo\":\"13001151490\",\"timeNum\":\"0\",\"productNo\":\"\",\"pageNo\":\"1\",\"pageSize\":\"20\",\"totalRecords\":4,\"orderList\":[{\"chanel\":\"SOP\",\"count\":1,\"formart\":\"平光\",\"goodsArea\":\"北京\",\"goodsName\":\"凯达隐形眼镜伴侣盒8009\",\"goodsNum\":\"PYL001503\",\"goodsSpid\":\"SPH50020104\",\"ifzp\":\"\",\"orderNum\":\"1856298859\",\"orderTime\":\"2014-09-13\",\"otherSeller\":\"\",\"price\":0,\"totalPrice\":0,\"unit\":\"\"},{\"chanel\":\"SOP\",\"count\":1,\"formart\":\"120ml/瓶\",\"goodsArea\":\"北京\",\"goodsName\":\"海昌海俪恩除蛋白 120ml\",\"goodsNum\":\"PYLSOP01864\",\"goodsSpid\":\"SPH50087739\",\"ifzp\":\"\",\"orderNum\":\"1856298859\",\"orderTime\":\"2014-09-13\",\"otherSeller\":\"\",\"price\":0,\"totalPrice\":0,\"unit\":\"\"},{\"chanel\":\"SOP\",\"count\":2,\"formart\":\"-6.5\",\"goodsArea\":\"北京\",\"goodsName\":\"海俪恩恋爱魔镜金棕半年抛1片\",\"goodsNum\":\"PYLSOP04512\",\"goodsSpid\":\"SPH50116297\",\"ifzp\":\"\",\"orderNum\":\"1856298859\",\"orderTime\":\"2014-09-13\",\"otherSeller\":\"\",\"price\":25,\"totalPrice\":0,\"unit\":\"\"},{\"chanel\":\"SOP\",\"count\":1,\"formart\":\"1片\",\"goodsArea\":\"北京\",\"goodsName\":\"昕薇女孩玫瑰香氛润泽面膜1片装\",\"goodsNum\":\"KYLSOP06233\",\"goodsSpid\":\"SPH50131990\",\"ifzp\":\"\",\"orderNum\":\"1856298859\",\"orderTime\":\"2014-09-13\",\"otherSeller\":\"\",\"price\":0,\"totalPrice\":0,\"unit\":\"\"}]}";
							if(res!=null && res.trim().length()>0){
								JSONObject json = JSONObject.fromObject(res);
								Object ordersObj = json.get("orderList");
								if(ordersObj!=null){
									String s = ordersObj.toString();
									if(s.trim().length()>0){
										JSONArray orderList = JSONArray.fromObject(s);
										modelMap.put("bLs",orderList);
									}
								}
								//记录总数
								recTotal = json.getInt("totalRecords");
								pageTotal = recTotal / this.getPageSize();
								pageTotal = recTotal % this.getPageSize() == 0 ? pageTotal : pageTotal + 1;
								if( this.getPageno() > pageTotal ){
									this.setPageno(1);
								}
								modelMap.put("pageno", this.getPageno());
								modelMap.put("pageTotal", pageTotal);
								modelMap.put("pageSize", this.getPageSize());
								modelMap.put("recTotal", recTotal);
							}
						} catch (Exception e) {
							logger.error("获取购买记录失败：", e);
						}
					}
				} catch (Exception e) {
					logger.error("获取购买记录失败：", e);
				}
				modelMap.put("bvo",buyVO);
			}
		} catch (Exception e) {
			logger.error("获取购买记录失败：", e);
		}
		
		return "opcenter/custService/buyRecord";
	}
	
	/**
	 * 沟通记录查询
	 */
	@RequestMapping(params = ("method=getCommunication"))
	public String getCommunication(HttpServletRequest request, ModelMap modelMap) {
		String uri = request.getRequestURI();
		String ac = uri.substring(uri.lastIndexOf("/")+1);//获取请求的名字
		modelMap.put("actionName",ac);
		
		try {
			String tel = request.getParameter("tel");
			String screenType = request.getParameter("screenType");
			modelMap.put("screenType", screenType);
			//查询会员信息
			Member member = this.getMember(tel);
			if(member==null){
				member = new Member();
				member.setTel(tel);
			}
			modelMap.put("member",member);
			modelMap.put("tel", tel);
			
			
			//用户信息
//			modelMap.put("user", getCurrentUser());
			
			CommunicationVO cvo = new CommunicationVO();
			cvo.setTel(tel);
			//沟通记录列表
			getCommuLs(request, modelMap, cvo);
		} catch (Exception e) {
			logger.error("获取沟通记录失败",e);
		}
		
		return "opcenter/custService/communication";
	}
	
	/**
	 * 查询沟通记录
	 */
	@RequestMapping(params = ("method=getCommuLs"))
	public String getCommuLs(HttpServletRequest request, ModelMap modelMap, CommunicationVO cvo){
		if(cvo!=null && cvo.getTel()!=null && cvo.getTel().trim().length()>0){
			String endDate=cvo.getEndDate();
			if(cvo.getEndDate()!=null && cvo.getEndDate().trim().length()>0){
				cvo.setEndDate(cvo.getEndDate().trim()+" 23:59:59");
			}
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
				this.setPageSize(10);
			}else{
				this.setPageSize(Integer.parseInt(pageSize));
			}
			
			recTotal = commuService.queryCommuCount(cvo);
			pageTotal = recTotal / this.getPageSize();
			pageTotal = recTotal % this.getPageSize() == 0 ? pageTotal : pageTotal + 1;
			if( this.getPageno() > pageTotal ){
				this.setPageno(1);
			}
			List<Communication> commuList = commuService.queryCommuList((this.getPageno()-1)*this.getPageSize(), this.getPageSize(), cvo);
			modelMap.put("commuList", commuList);
			modelMap.put("pageno", this.getPageno());
			modelMap.put("pageTotal", pageTotal);
			modelMap.put("pageSize", this.getPageSize() );
			modelMap.put("recTotal", recTotal);
			
			cvo.setEndDate(endDate);
			modelMap.put("cvo",cvo);
		}
		return "opcenter/custService/commu_ls";
	}
	
	
	/**
	 * 保存沟通记录
	 */
	@RequestMapping(params = ("method=saveCommunicat"), method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public String saveCommunicat(HttpServletRequest request,HttpSession session, Communication cm){
		if(cm!=null && cm.getTel()!=null && cm.getTel().trim().length()>0){
			try {
//				Member member = this.getMember(cm.getTel().trim());
//				if(member!=null){
//					User user = this.getCurrentUser();
//					cm.setCreateUser(user!=null?user.getUserName():null);
////					Object objCode = request.getSession().getAttribute("custServCode");
////					cm.setCreateUser(objCode!=null?objCode.toString():null);
//					cm.setCreateTime(df.format(new Date()));
//					//新增或修改
//					commuService.saveOrUpdate(cm);
//					return "{\"code\":\"0\",\"cmId\":\""+cm.getId()+"\"}";
//				}else{//会员信息不存在
//					return "{\"code\":\"1\"}";
//				}
				//客服工号
				Object code = session.getAttribute("custServCode");
				if(code!=null){
					cm.setCreateUser(code.toString());
				}else{
					User user = this.getCurrentUser();
					cm.setCreateUser(user!=null?user.getUserName():null);
				}
				cm.setCreateTime(df.format(new Date()));
				//新增或修改
				commuService.saveOrUpdate(cm);
				return "{\"code\":\"0\",\"cmId\":\""+cm.getId()+"\"}";
			} catch (Exception e) {
				return "{\"code\":\"3\"}";
			}
		}else{//手机号为空
			return "{\"code\":\"2\"}";
		}
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
	 * 进入官网下单页面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = ("method=addOfficOrder"))
	public void addOfficOrder(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		try{
			PrintWriter out = response.getWriter();
			String msg = "";
			JSONObject object = new JSONObject();
			String dateString = request.getParameter("dateString");
			List<MealVo> prodList = (List<MealVo>) session.getAttribute("prodList"+dateString);
			//用户id
			String buyerUserId = request.getParameter("buyerUserId");
			String createAdminUser = request.getParameter("createAdminUser");
			if(buyerUserId == null || "".equals(buyerUserId.trim())){
				msg = "用户ID不能为空！";
			}
			if(createAdminUser == null || "".equals(createAdminUser.trim())){
				msg = "客服ID不能为空！";
			}
			ResponseMessage message = null;
			if("".equals(msg.trim()) && prodList != null && prodList.size() > 0){
				List<CartInfos> list = new ArrayList<CartInfos>();
				CartInfos bean = null;
				for(MealVo vo:prodList){
					/*if(vo.getPrescriptionType() != null && "3".equals(vo.getPrescriptionType())){
						msg = "官网模式下单不允许选择处方药类型商品！";
						break;
					}*/
					bean = new CartInfos();
					//bean.setUid(buyerUserId);
					bean.setSelected("1");
					bean.setAssignSingleFaId("-1");
					bean.setAssignOtherFaId("-1");
					bean.setSkuId(vo.getProductId());//产品id
					bean.setQuantity(vo.getBuyCount());
					bean.setMealId(vo.getMealId());//套餐ID
					list.add(bean);
				}
				if("".equals(msg.trim())){
					CardProductInfo cp = new CardProductInfo();
					cp.setCartInfos(list);
					JSONObject json = JSONObject.fromObject(cp);
					message = CartClient.batchAddCart(Integer.parseInt(buyerUserId),json.toString());
					String data = message.data;
					JSONObject str = JSONObject.fromObject(data);
					String goodListStr = str.getString("execpGoodList");
					if(goodListStr != null && !"".equals(goodListStr.trim())){
						JSONArray arr = JSONArray.fromObject(goodListStr);
						StringBuffer goodsMsg = new StringBuffer();
						for(int i=0;i<arr.size();i++){
							JSONObject obj = arr.getJSONObject(i);
							goodsMsg.append("商品：" + obj.getString("mname") + "，");
							goodsMsg.append(obj.getString("msg") + "\n");
						}
						//String goodsStr = goodsMsg.substring(0,goodsMsg.lastIndexOf("\n"));
						object.put("goodsMsg", goodsMsg.toString());
					}
					logger.info("添加购物车：" + message);
					if(message != null && message.ifSuc == 1 && "200".equals(message.code)){
						message = CartClient.batchAddSnapShot(Integer.parseInt(buyerUserId),json.toString());
					}else{
						msg = message.msg;
					}
					logger.info("添加购物车：" + message);
					String url = null;
					if(message != null && message.ifSuc == 1 && "200".equals(message.code)){
						String kefuId = createAdminUser;
						String key = "mall_admin";
						String kefuv = MD5Utils.MD5(kefuId + key);
						url = "http://admin.mall.ehaoyao.com/admin/customerService/login?kefuv="+ kefuv + "&kefuId="+kefuId +"&userId=" + buyerUserId;
						object.put("url", url);
					}else{
						msg = message.msg;
					}
				}
			}else{
				String kefuId = createAdminUser;
				String key = "mall_admin";
				String kefuv = MD5Utils.MD5(kefuId + key);
				String url = "http://admin.mall.ehaoyao.com/admin/customerService/login?kefuv="+ kefuv + "&kefuId="+kefuId +"&userId=" + buyerUserId;
				object.put("url", url);
			}
			logger.info("进入官网下单页面Info:"+msg);
			object.put("msg", msg);
			out.println(object);
			out.flush(); 
		    out.close();
		}catch(Exception e){
			logger.error("进入官网下单页面失败！",e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 沟通记录到代下单
	 * @deprecated
	 */
	@RequestMapping(params = ("method=addAdvisoryOrder"))
	public String addAdvisoryOrder(HttpServletResponse response,HttpServletRequest request,HttpSession session, ModelMap modelMap,String mealIds) {
		String type = request.getParameter("type");
		String dateString = request.getParameter("dateString");
		List<ProductVO> newList = new ArrayList<ProductVO>();
		session.setAttribute("prodList"+dateString, newList);
		if(mealIds == null || "".equals(mealIds.trim())){
			return addOrder(request, session, modelMap, null);
		}
		/**设定参数*/
		String restUri =officialUrl3 + "/v1/meal/partialList.jsonp?token={token}&uuid={uuid}&ids={ids}";
		Map<String, String> variables = new HashMap<String, String>();
		variables.put("token", "ucenter_)$02");
		variables.put("uuid", "ucenter");
		variables.put("ids", mealIds);
		//个人现金消费信息
		String res = "[]";
		res = restTemplate.getForObject(restUri, String.class, variables);
		if(res!=null && res.trim().length()>0){
			JSONObject json = JSONObject.fromObject(res);
			String jsonStr = json.getString("response");
			JSONObject resultJson = JSONObject.fromObject(jsonStr);
			int code = Integer.parseInt(resultJson.getString("code"));
			if(code == 0){
				//modelMap.put("mesg", resultJson.get("tip"));
				logger.info("代下单查询商品出错：" + resultJson.get("tip"));
				return addOrder(request, session, modelMap, null);
			}
			Object goodsObj = resultJson.get("list");
			if(goodsObj!=null){
				String s = goodsObj.toString();
				if(s.trim().length()>0){
					JSONArray goodsLs = JSONArray.fromObject(s);
					if(goodsLs != null){
						MealVo mealVo = null;
						for(int i=0;i<goodsLs.size();i++){
							JSONObject obj = goodsLs.getJSONObject(i);
							mealVo = new MealVo();
							mealVo.setMealId(obj.getString("mealId"));//套餐ID
							mealVo.setMealName(obj.getString("mealName"));//套餐名称
							mealVo.setMainSku(obj.getString("mainSku"));//主商品编码
							mealVo.setMealNormName(obj.getString("mealNormName"));//主商品规格
							mealVo.setPrice(obj.getString("mealPrice"));//单价
							mealVo.setProductId(obj.getString("mainProductId"));//主商品id
							mealVo.setPrescriptionType(obj.getString("prescriptionType"));
							isSetSession(type, dateString, session, mealVo);
						}
					}
				}
			}
		}
		return addOrder(request, session, modelMap, null);
	}
	
	/**
	 * 多sku，父子套餐代下单
	 */
	@RequestMapping(params = ("method=addOrderSku"))
	public String addOrderSku(HttpServletResponse response,HttpServletRequest request,HttpSession session, ModelMap modelMap,String products,String suits) {
		String type = request.getParameter("type");
		String dateString = request.getParameter("dateString");
		/*List<ProductVO> newList = new ArrayList<ProductVO>();
		session.setAttribute("prodList"+dateString, newList);*/
		/*if(products == null || "".equals(products.trim())){
			return addOrder(request, session, modelMap, null);
		}*/
		/*if(suits != null && !"".equals(suits.trim())){
			*//**设定参数*//*
			String restUri =officialUrl3 + "/v1/meal/partialList.jsonp?token={token}&uuid={uuid}&ids={ids}";
			Map<String, String> variables = new HashMap<String, String>();
			variables.put("token", "ucenter_)$02");
			variables.put("uuid", "ucenter");
			variables.put("ids", suits);
			//个人现金消费信息
			String res = "[]";
			res = restTemplate.getForObject(restUri, String.class, variables);
			if(res!=null && res.trim().length()>0){
				JSONObject json = JSONObject.fromObject(res);
				String jsonStr = json.getString("response");
				JSONObject resultJson = JSONObject.fromObject(jsonStr);
				int code = Integer.parseInt(resultJson.getString("code"));
				if(code == 0){
					logger.info("代下单查询商品出错：" + resultJson.get("tip"));
					return addOrder(request, session, modelMap, null);
				}
				Object goodsObj = resultJson.get("list");
				if(goodsObj!=null){
					String s = goodsObj.toString();
					if(s.trim().length()>0){
						JSONArray goodsLs = JSONArray.fromObject(s);
						if(goodsLs != null){
							MealVo mealVo = null;
							for(int i=0;i<goodsLs.size();i++){
								JSONObject obj = goodsLs.getJSONObject(i);
								mealVo = new MealVo();
								mealVo.setMealId(obj.getString("mealId"));//套餐ID
								mealVo.setMealName(obj.getString("mealName"));//套餐名称
								mealVo.setMainSku(obj.getString("mainSku"));//主商品编码
								mealVo.setMealNormName(obj.getString("mealNormName"));//主商品规格
								mealVo.setPrice(obj.getString("mealPrice"));//单价
								mealVo.setProductId(obj.getString("mainProductId"));//主商品id
								mealVo.setPrescriptionType(obj.getString("prescriptionType"));
								isSetSession(type, dateString, session, mealVo);
							}
						}
					}
				}
			}
		}*/
		if(products != null && !"".equals(products.trim())){
			if(products.endsWith("|")){
				products = products.substring(0, products.length()-1);
			}
			List<MealSku> skuList = MealSkusClient.getMealSkuByMealIdsAndSkuIds(products);
			MealVo mealVo = null;
			if(skuList != null){
				for(MealSku sku : skuList){
					mealVo = new MealVo();
					mealVo.setMealId(String.valueOf(sku.getMealId()));//套餐ID
					mealVo.setMealName(sku.getMealName());//套餐名称
					mealVo.setMainSku(sku.getSku());//主商品编码
					mealVo.setMealNormName(sku.getSpecName());//主商品规格
					mealVo.setPrice(String.valueOf(sku.getPrice()));//单价
					mealVo.setProductId(String.valueOf(sku.getSkuId()));//主商品id
					mealVo.setStockCount(String.valueOf(sku.getStock()));
					mealVo.setPrescriptionType(String.valueOf(sku.getPrescriptionType()));
					isSetSession(type, dateString, session, mealVo);
				}
			}
		}
		return addOrder(request, session, modelMap, null);
	}
	/**
	 * 多sku，父子套餐代修改订单
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = ("method=updateOrderSku"))
	public void updateOrderSku(HttpServletResponse response,HttpServletRequest request,HttpSession session, ModelMap modelMap,String products) {
		try {
			PrintWriter out = response.getWriter();
			String type = request.getParameter("type");
			String dateString = request.getParameter("dateString");
			String expressCode = request.getParameter("expressCode");
			if(expressCode == null || "".equals(expressCode.trim())){
				expressCode = "4";
			}
			List<MealVo> rsList = new ArrayList<MealVo>();
			if(products != null && !"".equals(products.trim())){
				if(products.endsWith("|")){
					products = products.substring(0, products.length()-1);
				}
				List<MealSku> skuList = MealSkusClient.getMealSkuByMealIdsAndSkuIds(products);
				MealVo mealVo = null;
				if(skuList != null){
					for(MealSku sku : skuList){
						mealVo = new MealVo();
						mealVo.setMealId(String.valueOf(sku.getMealId()));//套餐ID
						mealVo.setMealName(sku.getMealName());//套餐名称
						mealVo.setMainSku(sku.getSku());//主商品编码
						mealVo.setMealNormName(sku.getSpecName());//主商品规格
						mealVo.setPrice(String.valueOf(sku.getPrice()));//单价
						mealVo.setProductId(String.valueOf(sku.getSkuId()));//主商品id
						mealVo.setStockCount(String.valueOf(sku.getStock()));
						mealVo.setPrescriptionType(String.valueOf(sku.getPrescriptionType()));
						String rs = isSetSession(type, dateString, session, mealVo);
						if(rs != null && "yes".equals(rs)){
							rsList.add(mealVo);
						}
					}
				}
			}
			String productList = "";
			if(null != type  && !"".equals(type)){
				if(type.equals("addOrder")){
					productList = "prodList"+dateString;
				}
				if(type.equals("updateOrder")){
					productList = "updateProdList"+dateString;
				}
			}
			List<MealVo> newList = (List<MealVo>) session.getAttribute(productList);
			if(newList == null){
				newList = new ArrayList<MealVo>();
			}
			OrderInfoVO orderInfo = getOrderInfo(newList,expressCode);
			JSONObject orderInfoObj = JSONObject.fromObject(orderInfo);
			//computeAmt(response, session, expressCode, request);
			JSONArray jarr = JSONArray.fromObject(rsList);
			JSONObject object = new JSONObject();
			object.put("rsList", jarr);
			object.put("orderInfoObj", orderInfoObj);
			out.print(object.toString());
			out.flush(); 
		    out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 代下单
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = ("method=addOrder"))
	public String addOrder(HttpServletRequest request,HttpSession session, ModelMap modelMap,MealVo mealVo) {
		String uri = request.getRequestURI();
		String ac = uri.substring(uri.lastIndexOf("/")+1);
		modelMap.put("actionName", ac);
		String dateString = request.getParameter("dateString");
		try {
			/*设定下单用户,先从session中取，如果取不到，则设定为当前电话对应的用户*/
			String orderUserId = (String) session.getAttribute("orderUserId"+dateString);
			String orderUserAccount = (String) session.getAttribute("orderUserAccount"+dateString);
			String orderUserPhone = (String) session.getAttribute("orderPhone"+dateString);			
			String twoDimensionCodeId = (String) session.getAttribute("twoDimensionCodeId"+dateString);
			String doctorId = (String) session.getAttribute("doctorId"+dateString);
			String hospitalId = (String) session.getAttribute("hospitalId"+dateString);
			if(twoDimensionCodeId != null && !"".equals(twoDimensionCodeId)){
				modelMap.put("doctorLock", true);
			}
			modelMap.put("twoDimensionCodeId", twoDimensionCodeId);
			//判断是否选择过医院和医生，如果有，则直接从session中取
			if(doctorId == null || "".equals(doctorId.trim())){
				HosDoctorUrlVO hdu = getUrlInfo(twoDimensionCodeId);
				if(hdu != null){
					modelMap.put("doctorId", hdu.getDoctorId());
					modelMap.put("hospitalId", hdu.getSalesRepId());
					session.setAttribute("doctorId"+dateString, hdu.getDoctorId());
					session.setAttribute("hospitalId"+dateString, hdu.getSalesRepId());
				}
			}else{
				modelMap.put("doctorId", doctorId);
				modelMap.put("hospitalId", hospitalId);
			}
			//判断session中是否有orderUserId,如果有，直接从session中取，如果没有，则需要根据手机号调用官网接口查询
			if(orderUserId == null || "".equals(orderUserId.trim())){
				String tel = request.getParameter("tel");
				logger.info("用户手机号tel:"+tel);
				if(tel != null && !"".equals(tel.trim())){
					try {
						PageList<MallUserBean> users = UserClient.getUserList(null,null,tel.trim(),"1","10");
						List<MallUserBean> userList = new ArrayList<MallUserBean>();
						if(users != null){
							userList = users.getDataList();
							if(userList != null && userList.size() > 0){
								modelMap.put("orderUserId", userList.get(0).getId());
								modelMap.put("orderUserAccount", userList.get(0).getAccountName());
								modelMap.put("orderUserPhone", userList.get(0).getPhone());
								session.setAttribute("orderUserId"+dateString, String.valueOf(userList.get(0).getId()));
								session.setAttribute("orderUserAccount"+dateString, String.valueOf(userList.get(0).getAccountName()));
								session.setAttribute("orderPhone"+dateString, String.valueOf(userList.get(0).getPhone()));
							}
						}
					} catch (Exception e) {
						logger.error("从官网获取用户信息失败："+e.getMessage());
					}
				}
			}else{
				modelMap.put("orderUserId", orderUserId);
				modelMap.put("orderUserAccount", orderUserAccount);
				modelMap.put("orderUserPhone", orderUserPhone);
			}
			//查询session中是否有商品列表
			List<MealVo> prodList = (List<MealVo>) session.getAttribute("prodList"+dateString);
			String orderType = (String) session.getAttribute("addOrderType"+dateString);
			if(prodList == null){
				prodList = new ArrayList<MealVo>();
			}
			/*判断是否是从主推商品页过来的新增商品*/
			if(mealVo != null && mealVo.getMealId() != null && !"".equals(mealVo.getMealId().trim())){
				boolean isExist = false;
				for(MealVo bean:prodList){
					if(bean.getMealId() == mealVo.getMealId() || bean.getMealId().equals(mealVo.getMealId())){
						isExist = true;
						break;
					}
				}
				if(!isExist){
					mealVo.setAmount(mealVo.getPrice());
					mealVo.setBuyCount("1");
					prodList.add(mealVo);
					session.setAttribute("prodList"+dateString, prodList);
				}
			}
			OrderInfoVO orderInfo = new OrderInfoVO();
			if(prodList.size() > 0){
				logger.info("开始计算金额！");
				orderInfo = getOrderInfo(prodList,null);
			}
			//订单来源标识
			List<CpsMerchantBean> cpsLaiyuanList = OrderClient.getCpsMerchantList();
			JSONArray laiYuanArray = JSONArray.fromObject(cpsLaiyuanList);
			
			//医院信息
			List<SalesRepModel> salesRepList = salesRepService.queryAll();
			if(null == salesRepList){
				salesRepList = new ArrayList<SalesRepModel>();
			}
			//查询运费信息
			List<StoreShipFeeBean> expressList = OrderClient.getCfyShipFeeList();
			JSONArray shipArray = JSONArray.fromObject(expressList);
			session.setAttribute("prodList", prodList);
			modelMap.put("prodList", prodList);
			modelMap.put("orderInfo", orderInfo);
			modelMap.put("shipArray",shipArray);
			modelMap.put("orderType", orderType);
			modelMap.put("dateString", dateString);
			//订单来源标识
			modelMap.put("cpsLaiyuan", laiYuanArray);
			//医院信息
			modelMap.put("salesRepList", salesRepList);
			//订单操作类型
			modelMap.put("orderOperationType", "addOrder");
			//电销客服代下单
			if("outScreen.do".equals(ac)){
				//客服编号
				Object custServCode = session.getAttribute("custServCode");
				modelMap.put("adminUser", custServCode!=null?custServCode.toString():"");
				//客服姓名
				User user = userService.loadUserByUserName(custServCode.toString());
				modelMap.put("adminUserName", user.getName());
			}else{//运营中心后台管理人员代下单
				User user = this.getCurrentUser();
				modelMap.put("adminUser", user!=null?user.getUserName():"");
				//客服姓名
				modelMap.put("adminUserName", user.getName());
			}
			
		} catch (Exception e) {
			logger.error("进入代下单失败",e);
		}
		return "opcenter/custService/addOrder";
	}
	/**
	 * 根据二维码ID获取基本信息
	 */
	private HosDoctorUrlVO getUrlInfo(String id){
		HosDoctorUrlVO model = null;
		if(id != null && !"".equals(id)){
			model = hosDoctorUrlService.getHosDoctorUrlId(Integer.parseInt(id));
		}
		return model;
	}
	/**
	 * 查询下单用户
	 */
	@RequestMapping(params = "method=searchUser")
	public void searchUser(HttpServletResponse response,HttpServletRequest request,String uid,String uc,String tel,String pn){
		try{
			PrintWriter out = response.getWriter();
			PageList<MallUserBean> user = null;
			String errorMessage = null;
			try{
				user = UserClient.getUserList(uid,uc,tel,pn,"10");
			}catch(Exception e){
				errorMessage =  e.getMessage();
				logger.error("查询下单用户出错：" + e.getMessage());
			}
			List<MallUserBean> userList = new ArrayList<MallUserBean>();
			PageTurn pageTurn = new PageTurn(); 
			if(user != null){
				userList = user.getDataList();
				pageTurn = user.getPageTurn();
			}
			request.setAttribute("userList", userList);
			JSONArray jsonArray = JSONArray.fromObject(userList);  
			JSONObject pageInfo = JSONObject.fromObject(pageTurn);
			JSONObject object = new JSONObject();
			object.put("userList",jsonArray);
			object.put("pageInfo", pageInfo);
			object.put("errorMessage", errorMessage);
			out.println(object);
			out.flush(); 
		    out.close();
		}catch(Exception e){
			logger.error("查询下单用户出错：" + e.getMessage());
		}
	}
	/**
	 * 代下单添加商品获取商品一览
	 * @param request
	 * @param modelMap
	 * @param bvo
	 * @return
	 */
	@RequestMapping(params = "method=getProductList")
	public void getProductList(HttpServletResponse response,HttpServletRequest request,MealVo mealVo){
		try{
			PrintWriter out = response.getWriter();
			String mealId = mealVo.getMealId();
			String mealName = mealVo.getMealName();
			String mainSku = mealVo.getMainSku();
			String pn = "0";
			if(mealVo.getPn() != null && !"".equals(mealVo.getPn())){
				pn = mealVo.getPn();
			}
			//PageList<Meal> meals = MealClient.getList(mealId, mealName, mainSku, null, pn, "10");
			MealQueryParam mealQueryParam = new MealQueryParam();
			mealQueryParam.setMealId(mealId);
			mealQueryParam.setMealName(mealName);
			mealQueryParam.setMainSku(mainSku);
			mealQueryParam.setPn(pn);
			mealQueryParam.setPz("10");
			if("updateOrder".equals(mealVo.getType())){
				mealQueryParam.setOrderSn(mealVo.getOrderSn());			
			}
			PageList<Meal> meals = MealClient.getList(mealQueryParam);
			List<Meal> prodList = new ArrayList<Meal>();
			PageTurn pageTurn = new PageTurn(); 
			if(meals != null){
				prodList = meals.getDataList();
				pageTurn = meals.getPageTurn();
			}
			String products = request.getParameter("products");
			if(prodList != null && prodList.size() > 0 && products != null && !"".equals(products.trim())){
				String[] prodArr = products.split("\\|");
				if(prodArr != null && prodArr.length > 0){
					for(String str:prodArr){
						if(str == null || "".equals(str.trim())){
							continue;
						}
						for(Meal param:prodList){
							String [] arr = str.split(",");
							if(param.getMealId() != Integer.parseInt(arr[0])){
								continue;
							}
							param.setBrandId(0);
							if(arr == null || arr.length != 2){
								param.setBrandId(1);
								break;
							}
							List<MealSku> skuList = MealSkusClient.getMealSkuByMealIdsAndSkuIds(str);
							if(skuList != null && skuList.size() > 0){
								MealSku sku = skuList.get(0);
								param.setMainSku(sku.getSku());
								param.setMealNormName(sku.getSpecName());
								BigDecimal b = new BigDecimal(String.valueOf(sku.getPrice()));
								double d = b.doubleValue();
								param.setMealPrice(d);
								param.setMinStock(sku.getStock());
								param.setMainProductId(sku.getSkuId());
								param.setBrandId(2);
								break;
							}
						}
					}
				}
			}
			request.setAttribute("prodList",prodList);
			JSONArray jsonArray = JSONArray.fromObject(prodList);
			JSONObject pageInfo = JSONObject.fromObject(pageTurn);
			JSONObject object = new JSONObject();
			object.put("prodList",jsonArray);
			object.put("pageInfo", pageInfo);
			out.println(object);
			out.flush(); 
		    out.close();
		}catch(Exception e){
			logger.error("代下单添加商品获取商品一览出错：" + e.getMessage());
		}
	}
	/**
	 * 代下单获取商品sku一览
	 * @param response
	 * @param request
	 * @param mealId
	 */
	@RequestMapping(params = "method=getSkuList")
	public void getSkuList(HttpServletResponse response,HttpServletRequest request,int mealId){
		try{
			PrintWriter out = response.getWriter();
			if(mealId <= 0){
				return;
			}
			List<MealSku> mealSkuList = MealSkusClient.getMealSkusByMealId(mealId);
			JSONArray jsonArray = JSONArray.fromObject(mealSkuList);
			JSONObject object = new JSONObject();
			object.put("skuList",jsonArray);
			out.println(object);
			out.flush(); 
		    out.close();
		}catch(Exception e){
			logger.error("代下单添加商品获取商品sku一览出错：" + e.getMessage());
		}
	}
	/**
	 * 代下单添加商品获取父子套餐一览
	 * @param response
	 * @param request
	 * @param mealId
	 */
	@RequestMapping(params = "method=getSuitList")
	public void getSuitList(HttpServletResponse response,HttpServletRequest request,String mealId){
		try{
			PrintWriter out = response.getWriter();
			if(mealId == null || "".equals(mealId.trim())){
				return;
			}
			List<Meal> relateMealList = MealClient.getRelateMeal(mealId, MealRelateTypeEnum.FATHER_AND_SON);
			/*String products = request.getParameter("products");
			if(relateMealList != null && relateMealList.size() > 0 && products != null && !"".equals(products.trim())){
				String[] prodArr = products.split("\\|");
				if(prodArr != null && prodArr.length > 0){
					for(Meal param:relateMealList){
						param.setBrandId(0);
						for(String str:prodArr){
							if(str == null || "".equals(str.trim())){
								continue;
							}
							String [] arr = str.split(",");
							if(arr == null || arr.length != 2){
								continue;
							}
							if(param.getMealId() != Integer.parseInt(arr[0])){
								continue;
							}
							List<MealSku> skuList = MealSkusClient.getMealSkuByMealIdsAndSkuIds(str);
							if(skuList != null && skuList.size() > 0){
								MealSku sku = skuList.get(0);
								param.setMainSku(sku.getSku());
								param.setMealNormName(sku.getSpecName());
								BigDecimal b = new BigDecimal(String.valueOf(sku.getPrice()));
								double d = b.doubleValue();
								param.setMealPrice(d);
								param.setMinStock(sku.getStock());
								param.setMainProductId(sku.getSkuId());
								param.setBrandId(1);
							}
						}
					}
				}
			}*/
			//List<Meal> relateMealList = MealClient.getRelateMeal(mealId, MealRelateTypeEnum.FATHER_AND_SON);
			JSONArray jsonArray = JSONArray.fromObject(relateMealList);
			JSONObject object = new JSONObject();
			object.put("relateMealList",jsonArray);
			out.println(object);
			out.flush(); 
		    out.close();
		}catch(Exception e){
			logger.error("代下单添加商品获取父子套餐一览出错：" + e.getMessage());
		}
	}
	/*@RequestMapping(params = "method=choiceSuit")
	public void choiceSuit(HttpServletResponse response,HttpServletRequest request,String mealIds,int flag){
		try{
			PrintWriter out = response.getWriter();
			if(mealIds == null || "".equals(mealIds.trim())){
				return;
			}
			mealIds = mealIds.substring(0,mealIds.length()-1);
			
			//List<Meal> relateMealList = MealClient.getRelateMeal("19457", MealRelateTypeEnum.FATHER_AND_SON);
			List<Meal> relateMealList = MealClient.getRelateMeal(mealIds, MealRelateTypeEnum.FATHER_AND_SON);
			JSONArray jsonArray = JSONArray.fromObject(relateMealList);
			JSONObject object = new JSONObject();
			object.put("relateMealList",jsonArray);
			out.println(object);
			out.flush(); 
		    out.close();
		}catch(Exception e){
			logger.error("代下单添加商品获取父子套餐一览出错：" + e.getMessage());
		}
	}*/
	/**
	 * 下一步按钮点击
	 * @param response
	 * @param request
	 * @param modelMap
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(params = "method=nextStep")
	public void nextStep(HttpServletResponse response,HttpServletRequest request,HttpSession session,String userId,String usaid,String type){
		/**默认收货地址*/
		try{
			PrintWriter out = response.getWriter();
			/**默认地址*/
			UserAddressBean addressBean = null;
			if(null !=type && !"".equals(type)){
				if(type.equals("updateOrder")){
					addressBean = UserClient.getUserAddressById(usaid, userId);
				}
				if(type.equals("addOrder")){
					addressBean = UserClient.getUserDefaultAddress(userId);
				}
			}	
			String address = "";
			int addressId = 0;
			String addUsername = "";
			String addPhone = "";
			if(addressBean != null){
				address = addressBean.getFullAddress();
				addressId = addressBean.getId();
				addUsername = addressBean.getContacts();
				addPhone = addressBean.getContactNumber();
			}
			/**支付方式*/
			List<PaymentMethod> payMethodList = OrderClient.getPayMethod();
			JSONArray payArray = JSONArray.fromObject(payMethodList);
			/**运费ID*/
			List<StoreShipFeeBean> expressList = OrderClient.getCfyShipFeeList();
			JSONArray shipArray = JSONArray.fromObject(expressList);
			/**送货时间*/
			List<ShipTimeBean> timeList = OrderClient.getShipTime();
			JSONArray timeArray = JSONArray.fromObject(timeList);
			/**购买商品一览*/
			List<MealVo> prodList = new ArrayList<MealVo>();
			String dateString = request.getParameter("dateString");
			if(null !=type && !"".equals(type)){
				if(type.equals("updateOrder")){
					prodList = (List<MealVo>) session.getAttribute("updateProdList"+dateString);
				}
				if(type.equals("addOrder")){
					prodList = (List<MealVo>) session.getAttribute("prodList"+dateString);
				}
			}
			JSONArray prodArray = JSONArray.fromObject(prodList);
			JSONObject object = new JSONObject();
			object.put("address", address);
			object.put("addressId", addressId);
			object.put("addPhone", addPhone);
			object.put("addUsername", addUsername);			
			object.put("payMethod", payArray.toString());
			object.put("shipTime", timeArray.toString());
			object.put("productList", prodList);
			out.println(object.toString());
			out.flush(); 
		    out.close();
		}catch(Exception e){
			logger.error("下一步按钮点击出错：" + e.getMessage());
		}
	}
	/**
	 * 判断所选商品中有没有医生对应的商品
	 */
	private int getDoctorMid(List<MealVo> prodList,String doctorId){
		StringBuilder hqlString = new StringBuilder(100);
		hqlString.append("FROM DoctorUrlModel dm");
		if(null!=doctorId  && !"".equals(doctorId)){
			hqlString.append(" WHERE dm.doctorId = '"+doctorId+"'");
		}
		List<DoctorUrlModel> list = doctorUrlService.queryHql(hqlString.toString(), null);
		int doctorUrlId = 0;
		if(list != null && list.size() > 0){
			OK:
			for(MealVo vo:prodList){
				for(DoctorUrlModel model:list){
					if(vo.getMealId().equals(getMid(model.getUrl()))){
						doctorUrlId = model.getId(); 
						break OK;
					}
				}
			}
		}
		return doctorUrlId;
	}
	/**
	 * 截取URL中的套餐ID
	 * @param url
	 * @return
	 */
	private String getMid(String url){
		String mid = null;
		int startMid = url.indexOf("mid=");
		int startm = url.indexOf("m=");
		if(startMid != -1){
			int end = url.indexOf("&",startMid);
			if(end == -1){
				mid = url.substring(startMid+4 ,url.length());
			}else{
				mid = url.substring(startMid+4,end);
			}
		}else if(startm != -1){
			int end = url.indexOf("&",startm);
			if(end == -1){
				mid = url.substring(startm+2 ,url.length());
			}else{
				mid = url.substring(startm+2,end);
			}
		}
		return mid;
	}
	/**
	 * 选择订单类型后保存到session中
	 * @param response
	 * @param request
	 * @param session
	 * @param pvo
	 */
	@RequestMapping(params = "method=setOrderTypeSession")
	public void setOrderTypeSession(HttpServletResponse response,HttpSession session,String id,String type,String dateString){
		try{
			PrintWriter out = response.getWriter();
			if(null != type && type == "addOrder"){
				session.setAttribute("addOrderType"+dateString, id);
			}
            if(null != type && type == "updateOrder"){
            	session.setAttribute("updateOrderType"+dateString, id);
			}
			out.println("yes");
			out.flush(); 
		    out.close();
		}catch(Exception e){
			logger.error("选择订单类型后保存到session中出错：" + e.getMessage());
		}
	}
	/**
	 * 选择下单用户后保存到session中
	 * @param response
	 * @param request
	 * @param session
	 * @param pvo
	 */
	@RequestMapping(params = "method=setUserSession")
	public void setUserSession(HttpServletResponse response,HttpSession session,String id,String account,String phone,String dateString){
		try{
			PrintWriter out = response.getWriter();
			session.setAttribute("orderUserId"+dateString, id);
			session.setAttribute("orderUserAccount"+dateString, account);
			session.setAttribute("orderPhone"+dateString, phone);
			out.println("yes");
			out.flush(); 
		    out.close();
		}catch(Exception e){
			logger.error("选择下单用户后保存到session中出错：" + e.getMessage());
		}
	}
	/**
	 * 新增商品时保存到session中
	 * @param response
	 * @param request
	 * @param session
	 * @param pvo
	 */
	@RequestMapping(params = "method=setSession")
	public void setSession(HttpServletResponse response,HttpSession session,MealVo mealVo,HttpServletRequest request){
		try{
			PrintWriter out = response.getWriter();
			String type = request.getParameter("type");
			String dateString = request.getParameter("dateString");
			String result = isSetSession(type, dateString, session, mealVo);
			/*String productList = "";
			if(null != type  && !"".equals(type)){
				if(type.equals("addOrder")){
					productList = "prodList"+dateString;
				}
				if(type.equals("updateOrder")){
					productList = "updateProdList"+dateString;
				}
			}
			List<MealVo> newList = (List<MealVo>) session.getAttribute(productList);
			if(newList == null){
				newList = new ArrayList<MealVo>();
			}
			for(MealVo bean:newList){
				if(bean.getMealId() == mealVo.getMealId() || bean.getMealId().equals(mealVo.getMealId())){
					out.println("no");
					out.flush(); 
				    out.close();
				    return;
				}
			}
			mealVo.setAmount(mealVo.getPrice());
			mealVo.setBuyCount("1");
			newList.add(mealVo);
			session.setAttribute(productList, newList);
			out.println("yes");*/
			out.print(result);
			out.flush(); 
		    out.close();
		}catch(Exception e){
			logger.error("新增商品时保存到session中出错：" + e.getMessage());
		}
	}
	@SuppressWarnings("unchecked")
	private String isSetSession(String type,String dateString,HttpSession session,MealVo mealVo){
		String productList = "";
		if(null != type  && !"".equals(type)){
			if(type.equals("addOrder")){
				productList = "prodList"+dateString;
			}
			if(type.equals("updateOrder")){
				productList = "updateProdList"+dateString;
			}
		}
		List<MealVo> newList = (List<MealVo>) session.getAttribute(productList);
		if(newList == null){
			newList = new ArrayList<MealVo>();
		}
		for(MealVo bean:newList){
			if((bean.getMealId() == mealVo.getMealId() || bean.getMealId().equals(mealVo.getMealId())) && (bean.getProductId() == mealVo.getProductId() || bean.getProductId().equals(mealVo.getProductId()))){
			    return "no";
			}
		}
		mealVo.setAmount(mealVo.getPrice());
		mealVo.setBuyCount("1");
		newList.add(mealVo);
		session.setAttribute(productList, newList);
		return "yes";
	}
	/**
	 * 新增商品完成后计算优惠政策和邮费
	 * @param response
	 * @param request
	 * @param session
	 * @param pvo
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=computeAmt")
	public void computeAmt(HttpServletResponse response,HttpSession session,String expressCode,HttpServletRequest request){
		try{
			PrintWriter out = response.getWriter();
			String type = request.getParameter("type");
			String dateString = request.getParameter("dateString");
			String productList = "";
			if(null != type  && !"".equals(type)){
				if(type.equals("addOrder")){
					productList = "prodList"+dateString;
				}
				if(type.equals("updateOrder")){
					productList = "updateProdList"+dateString;
				}
			}
			List<MealVo> newList = (List<MealVo>) session.getAttribute(productList);
			if(newList == null){
				newList = new ArrayList<MealVo>();
			}
			OrderInfoVO orderInfo = getOrderInfo(newList,expressCode);
			JSONObject obj = JSONObject.fromObject(orderInfo);
			out.println(obj);
			out.flush();
		    out.close();
		}catch(Exception e){
			logger.error("新增商品完成后计算优惠政策和邮费出错：" + e.getMessage());
		}
	}
	/**
	 * 修改session中的数量和金额
	 * @param response
	 * @param request
	 * @param session
	 * @param pvo
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=updateSession")
	public void updateSession(HttpServletResponse response,HttpServletRequest request,HttpSession session,MealVo mealVo){
		try{
			PrintWriter out = response.getWriter();
			String type = request.getParameter("type");
			String dateString = request.getParameter("dateString");
			String productList = "";
			if(null != type && !"".equals(type)){
				if(type.equals("addOrder")){
					productList = "prodList"+dateString;
				}
				if(type.equals("updateOrder")){
					productList = "updateProdList"+dateString;
				}
			}
			List<MealVo> newList = (List<MealVo>) session.getAttribute(productList);
			if(newList == null){
				newList = new ArrayList<MealVo>();
			}
			for(MealVo bean:newList){
				if((bean.getMealId() == mealVo.getMealId() || bean.getMealId().equals(mealVo.getMealId())) && (bean.getProductId() == mealVo.getProductId() || bean.getProductId().equals(mealVo.getProductId()))){
					bean.setPrice(mealVo.getPrice());
					bean.setBuyCount(mealVo.getBuyCount());
					bean.setAmount(mealVo.getAmount());
				}
			}
			String expressId = request.getParameter("expressId");			
			session.setAttribute(productList, newList);
			OrderInfoVO orderInfo = getOrderInfo(newList,expressId);
			JSONObject obj = JSONObject.fromObject(orderInfo);
			obj.put("result", "yes");
			out.println(obj);
			out.flush(); 
		    out.close();
		}catch(Exception e){
			logger.error("修改session中的数量和金额：" + e.getMessage());
		}
	}
	/**
	 * 计算商品总价和邮费
	 * @param prodList
	 * @return
	 */
	public OrderInfoVO getOrderInfo(List<MealVo> prodList,String expressId){
		OrderInfoVO orderInfo = new OrderInfoVO();
		try{
			BigDecimal totalAmount = new BigDecimal(0);//商品总价
			BigDecimal cheapAmt = new BigDecimal(0);//优惠金额
			BigDecimal postageAmt = new BigDecimal(0);//快递费用
			BigDecimal orderAmt = new BigDecimal(0);//订单总价
			String cheapInfo = "无";//优惠信息
			String postageInfo = "0.00";//邮费
			String totalAmt = "0.00";//总金额
			if(prodList != null && prodList.size() > 0){
				/*logger.info("prodList的长度=" + prodList.size());*/
				BigDecimal amount = new BigDecimal(0);
				for(int i=0;i<prodList.size();i++){
					String amountStr = prodList.get(i).getAmount();
					if(amountStr != null && !"".equals(amountStr.trim())){
						amount = new BigDecimal(Double.parseDouble(amountStr)).setScale(2,BigDecimal.ROUND_HALF_UP);
						totalAmount = totalAmount.add(amount).setScale(2,BigDecimal.ROUND_HALF_UP);
					}
				}
			}else{
				logger.info("prodList为null或者prodList的长度为0！");
			}
			logger.info("商品总价（计算邮费前）的金额为" + totalAmount);
			if(totalAmount.compareTo(BigDecimal.ZERO) != 1){
				return new OrderInfoVO();
			}
			/**促销信息*/
			OrderPromotionBean opBean = OrderClient.getOrderPromotion(String.valueOf(totalAmount));
			/**邮费*/
			StoreShipFeeBean storeBean = new StoreShipFeeBean();
			if(null != expressId && !"".equals(expressId)){
				int shipFeeId = Integer.parseInt(expressId);				
				storeBean = OrderClient.getStoreShipFee(shipFeeId);
			} else {
				storeBean.setPrice(8.00);
				storeBean.setExpressCode("PTKD");
			}
			if(opBean != null){
				/**设定促销信息*/				
				/**是否包邮标志*/
				ShowPostVO fab1 = opBean.getShipActivity();
				BigDecimal absValue = new BigDecimal(0);
				if(fab1 != null && fab1.getIfMeet() != 0){
					absValue = new BigDecimal(fab1.getIfMeet()).setScale(2,BigDecimal.ROUND_HALF_UP);
				}
				/**邮费*/				
				BigDecimal postage = new BigDecimal(0);
				if(storeBean != null && storeBean.getPrice() != null){
					postage = new BigDecimal(storeBean.getPrice()).setScale(2,BigDecimal.ROUND_HALF_UP);
				}
				/** 判断订单属于那种促销活动 */
				if(absValue.compareTo(BigDecimal.ZERO) >= 0 && !"SFKD".equals(storeBean.getExpressCode())){
					//符合满足包邮 
					cheapInfo = "包邮";
					postageInfo = "0.00";//邮费
					totalAmt = "" + totalAmount;
					orderAmt = totalAmount;//订单总价
				}else {
					//不符合满减不符合包邮
					cheapInfo = "无";
					postageInfo = "" + postage;
					totalAmt = ""+ (totalAmount.add(postage));
					postageAmt = postage;//快递费用
					orderAmt = totalAmount.add(postage);//订单总价
				}				
				orderInfo.setCheapInfo(cheapInfo);
				orderInfo.setPostageInfo(postageInfo);
				orderInfo.setTotalAmtInfo(totalAmt);
				orderInfo.setTotalAmount(totalAmount);
				orderInfo.setCheapAmt(cheapAmt);
				orderInfo.setOrderAmt(orderAmt);
				orderInfo.setPostage(postageAmt);
			}
		}catch(Exception e){
			logger.error("计算金额出错，错误信息为：" ,e);
		}
		return orderInfo;
	}
	
	/**
	 * 按ID删除session中的某个商品
	 * @param response
	 * @param request
	 * @param session
	 * @param secondID
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=deleteSessionRow")
	public void deleteSessionRow(HttpServletResponse response,HttpServletRequest request,HttpSession session,String secondID,String productId){
		try{
			PrintWriter out = response.getWriter();
			String dateString = request.getParameter("dateString");
			String deleteFlag = "no";
			String type = request.getParameter("type");
			String productList = "";
			if(null != type && !"".equals(type)){
				if(type.equals("addOrder")){
					productList = "prodList"+dateString;
				}
				if(type.equals("updateOrder")){
					productList = "updateProdList"+dateString;
				}
			}
			List<MealVo> prodList = (List<MealVo>) session.getAttribute(productList);
			List<MealVo> deleteList = new ArrayList<MealVo>();
			for(MealVo bean:prodList){
				if((bean.getMealId() == secondID || bean.getMealId().equals(secondID)) && (bean.getProductId() == productId || bean.getProductId().equals(productId))){
					deleteList.add(bean);
				}
			}
			if(deleteList.size() > 0){
				prodList.removeAll(deleteList);
				deleteFlag = "yes";
			}
			String expressId = request.getParameter("expressId");
			session.setAttribute(productList, prodList);
			OrderInfoVO orderInfo = getOrderInfo(prodList,expressId);
			JSONObject obj = JSONObject.fromObject(orderInfo);
			obj.put("result", deleteFlag);
			out.println(obj);
			out.flush();
		    out.close();
		}catch(Exception e){
			logger.error("按ID删除session中的某个商品出错：" + e.getMessage());
		}
	}
	/**
	 * 更改地址
	 * @param response
	 * @param request
	 * @param modelMap
	 */
	@RequestMapping(params = "method=updateAddress")
	public void updateAddress(HttpServletResponse response,HttpServletRequest request,String userId){
		try{
			PrintWriter out = response.getWriter();
			PageList<UserAddressBean> addressBean = UserClient.getAddressList(userId, "1", "100");
			if(addressBean != null){
				request.setAttribute("addressList", addressBean.getDataList());
				JSONArray jsonArray = JSONArray.fromObject(addressBean.getDataList());  
				out.println(jsonArray.toString());
				out.flush(); 
			    out.close();
			}
		}catch(Exception e){
			logger.error("更改地址出错：" + e.getMessage());
		}
	}
	/**
	 * 新增用户地址
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=saveAddress")
	public void saveAddress(HttpServletResponse response,HttpServletRequest request){
		try {
			PrintWriter out = response.getWriter();
			String userId = request.getParameter("userId");//用户ID
			String province = request.getParameter("province");//省
			String city = request.getParameter("city");//城市
			String district = request.getParameter("district");//地区
			String street = request.getParameter("street");//街道
			String aid = request.getParameter("aid");//地区ID
			String contactNumber = request.getParameter("contactNumber");//手机
			String postcode = request.getParameter("postcode");//邮编
			String contacts = request.getParameter("contacts");//联系人 
			/** 新增送货地址*/
			UserAddressBean bean = UserClient.addAddress(userId, province, city, district, street, aid, contactNumber, postcode, contacts);
			//UserAddressBean bean = UserClient.addAddress(userId, province, city, district, street, aid, "", "010-88888888", postcode, contacts);
			JSONObject jsonObj = JSONObject.fromObject(bean); 
			out.println(jsonObj);
			out.flush(); 
		    out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("新增用户地址出错：" + e.getMessage());
		}
	}
	/**
	 * 编辑用户地址时根据用户地址ID获取用户地址信息
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=getAddressInfo")
	public void getAddressInfo(HttpServletResponse response,HttpServletRequest request,String address_val,String userId){
		try {
			PrintWriter out = response.getWriter();
			/** 新增送货地址*/
			UserAddressBean bean = UserClient.getUserAddressById(address_val, userId);
			JSONObject jsonObj = JSONObject.fromObject(bean); 
			out.println(jsonObj);
			out.flush(); 
		    out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("编辑用户地址时根据用户地址ID获取用户地址信息出错：" + e.getMessage());
		}
	}
	/**
	 * 编辑用户地址后保存用户地址
	 * @param response
	 * @param request
	 * @param address_val
	 * @param userId
	 */
	@RequestMapping(params = "method=editAddress")
	public void editAddress(HttpServletResponse response,HttpServletRequest request){
		try {
			PrintWriter out = response.getWriter();
			String usaid = request.getParameter("usaid");
			String userId = request.getParameter("userId");//用户ID
			String province = request.getParameter("province");//省
			String city = request.getParameter("city");//城市
			String district = request.getParameter("district");//地区
			String street = request.getParameter("street");//街道
			String aid = request.getParameter("aid");//地区ID
			String contactNumber = request.getParameter("contactNumber");//手机
			String postcode = request.getParameter("postcode");//邮编
			String contacts = request.getParameter("contacts");//联系人 
			/** 新增送货地址*/
			String result = UserClient.editAddress(usaid, userId, province, city, district, street, aid, contactNumber, postcode, contacts);
			if(result == null){
				UserAddressBean bean = UserClient.getUserAddressById(usaid, userId);
				JSONObject jsonObj = JSONObject.fromObject(bean); 
				out.println(jsonObj);
				out.flush(); 
			    out.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("编辑用户地址后保存用户地址出错：" + e.getMessage());
		}
	}
	/**
	 * 下单完成后清理session
	 * @param response
	 * @param request
	 */
	@RequestMapping(params = "method=clearSession")
	public String clearSession(HttpServletResponse response,HttpServletRequest request,HttpSession session,ModelMap modelMap){
		//ProxyOrderInfoBean proxyOrderInfoBean = new ProxyOrderInfoBean();
		try{
			List<ProductVO> newList = new ArrayList<ProductVO>();
			session.setAttribute("prodList", newList);
			session.setAttribute("orderUserId", "");
			session.setAttribute("orderUserAccount", "");
			session.setAttribute("orderType", "1");
			String uri = request.getRequestURI();
			String ac = uri.substring(uri.lastIndexOf("/")+1);
			modelMap.put("actionName", ac);
			OrderInfoVO orderInfo = new OrderInfoVO();
			modelMap.put("prodList", newList);
			modelMap.put("orderInfo", orderInfo);
			modelMap.put("orderType", "1");
		}catch(Exception e){
			logger.error("清理session失败：" + e.getMessage());
		}
		return "opcenter/custService/addOrder";
	}
	/**
	 * 下单
	 * @param response
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=orderSubmit")
	public void orderSubmit(HttpServletResponse response,HttpServletRequest request,PrintWriter out,HttpSession session){
		//String result = null;
		JSONObject result = new JSONObject();
		try{
			ProxyOrderInfoBean proxyOrderInfoBean = new ProxyOrderInfoBean();
			//订单类型（1、为代下单，2、为处方药，3、惠氏订单。4、金斯利安）
			String orderType = request.getParameter("orderType");
			//用户id
			String buyerUserId = request.getParameter("buyerUserId");
			//用户帐号
			String buyerLoginName = request.getParameter("buyerLoginName");
			//用户手机号
			String buyerUserPhone = request.getParameter("buyerUserPhone");
			//下单客服帐号
			String createAdminUser = request.getParameter("createAdminUser");
			//用户地址id
			String userAddressId = request.getParameter("userAddressId");
			//支付方式id
			String paymentMethodId = request.getParameter("paymentMethodId");
			//送货方式id
			String expressId = request.getParameter("expressId");
			//送货时间id
			String shipTimeId = request.getParameter("shipTimeId");
			//发票类型（0为不需要发票，1为个人发票）
			String invoiceType = request.getParameter("invoiceType");
			//发票抬头
			String invoiceTitle = request.getParameter("invoiceTitle");
			//备注
			String remark = request.getParameter("remark");
			//订单来源标识
			String cpsLaiyuan = request.getParameter("cpsLaiyuan");
			//订单来源名称
			String orderSource = request.getParameter("orderSource");
			//优惠金额
			String adminDiscount = request.getParameter("adminDiscount");
			//订单总金额（含快递费）
			String orderAmount = request.getParameter("orderAmount");
			//二维码ID
			String twoDimensionCodeId = request.getParameter("twoDimensionCodeId");
			//随机数
			String dateString = request.getParameter("dateString");
			//订单类型（1、为代下单，2、为处方药，3、惠氏订单。4、金斯利安）
			proxyOrderInfoBean.setOrderType(Integer.parseInt(orderType));
			//用户id
			proxyOrderInfoBean.setBuyerUserId(Integer.parseInt(buyerUserId));
			//用户帐号
			proxyOrderInfoBean.setBuyerLoginName(buyerLoginName);
			//用户手机号
			proxyOrderInfoBean.setAskerContact(buyerUserPhone);
			//下单客服帐号
			proxyOrderInfoBean.setCreateAdminUser(createAdminUser);
			//用户地址id
			proxyOrderInfoBean.setUserAddressId(Integer.parseInt(userAddressId));
			//支付方式id
			proxyOrderInfoBean.setPaymentMethodId(Integer.parseInt(paymentMethodId));
			//送货方式id
			proxyOrderInfoBean.setStoreShipFeeId(Integer.parseInt(expressId));
			//送货时间id
			proxyOrderInfoBean.setShipTimeId(Integer.parseInt(shipTimeId));
			//发票类型（0为不需要发票，1为个人发票）
			proxyOrderInfoBean.setInvoiceType(Integer.parseInt(invoiceType));
			//发票抬头
			proxyOrderInfoBean.setInvoiceTitle(invoiceTitle);
			//备注
			proxyOrderInfoBean.setRequireNote(remark);
			//获取所选商品列表
			List<MealVo> prodList = (List<MealVo>) session.getAttribute("prodList"+dateString);
			if(prodList == null){
				prodList = new ArrayList<MealVo>();
			}
			if(twoDimensionCodeId == null || "".equals(twoDimensionCodeId.trim())){
				twoDimensionCodeId = "0";
				String doctorId = request.getParameter("doctorId");
				if(doctorId != null && !"".equals(doctorId)){
					int doctorUrlId = getDoctorMid(prodList, doctorId);
					if(doctorUrlId <= 0){
						result.put("orderCode", 501);
						result.put("Message", "所选商品中没有医生推荐的药品，请重新选择！");
					    return;
					}
					String doctorUrlId_old = (String) session.getAttribute("twoDimensionCodeId"+dateString);
					if(doctorUrlId_old == null || "".equals(doctorUrlId_old.trim())){
						twoDimensionCodeId = String.valueOf(doctorUrlId);
					}
				}
			}
			proxyOrderInfoBean.setTwoDimensionCodeId(Integer.parseInt(twoDimensionCodeId));
			//调用方ip
			String ip = request.getHeader("x-forwarded-for");
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
				ip = request.getHeader("Proxy-Client-IP");  
			}  
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
				ip = request.getHeader("WL-Proxy-Client-IP");
			}  
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
				ip = request.getRemoteAddr();  
			}
			//调用方ip
			proxyOrderInfoBean.setCreateUserIp(ip);
			//订单来源标识
			proxyOrderInfoBean.setCpsLaiyuan(cpsLaiyuan);
			//优惠金额
			BigDecimal discountsBig = null;
			//订单总金额（含快递费）
			BigDecimal amountBig = new BigDecimal(orderAmount);
			double temp = amountBig.doubleValue();
			//判断是否优惠
			if(null != adminDiscount && !"".equals(adminDiscount)){
				discountsBig = new BigDecimal(adminDiscount);
				proxyOrderInfoBean.setAdminDiscount(discountsBig.doubleValue());
				//优惠后的订单总金额
				temp = amountBig.subtract(discountsBig).doubleValue();
			}
			proxyOrderInfoBean.setOrderAmount(temp);
			AddGoodsBean goodBean = null;
			List<AddGoodsBean> goodList = new ArrayList<ProxyOrderInfoBean.AddGoodsBean>();
			String mealIds = "";
			String skus = "";
			String mealNames = "";
			for(MealVo bean:prodList){
				goodBean = new AddGoodsBean();
				if(bean.getBuyCount() != null && !"".equals(bean.getBuyCount().trim())){
					goodBean.setBuyCount(Integer.parseInt(bean.getBuyCount()));
				}
				if(bean.getMealId() != null && !"".equals(bean.getMealId().trim())){
					goodBean.setMealId(Integer.parseInt(bean.getMealId()));
				}
				if(bean.getProductId() != null && !"".equals(bean.getProductId().trim())){
					goodBean.setProductId(Integer.parseInt(bean.getProductId()));
				}
				goodList.add(goodBean);
				mealIds += (bean.getMealId() + ",");
				skus += (bean.getMainSku() + ",");
				mealNames += (bean.getMealName() + "{,}");
			}
			proxyOrderInfoBean.setAddGoodsList(goodList);
			JSONObject jsonObj1 = JSONObject.fromObject(proxyOrderInfoBean);  
			logger.info("下单信息为：---------" + jsonObj1.toString());
			String resonse = OrderClient.createProxyOrder(proxyOrderInfoBean);
			
			result.put("orderCode", 1);
			result.put("Message", resonse);
			if(resonse != null || resonse != ""){
				//保存沟通记录订单信息
				String commuId = request.getParameter("commuId");
				try {
					if(commuId!=null && commuId.trim().length()>0){
						Communication comm = commuService.getCommuById(Long.parseLong(commuId.trim()));
						if(comm!=null){
							comm.setIsPlaceOrder("1");//成单
							comm.setOrderNumber(resonse);//订单号
							comm.setOrderTel(buyerUserPhone);//下单手机号
							comm.setOrderMealIds(mealIds);//商品套餐ID
							comm.setProductCode(skus);//商品sku
							comm.setProductName(mealNames);//套餐名称
							if(cpsLaiyuan!=null && cpsLaiyuan.trim().length()>0){
								comm.setOrderSourceFlag(cpsLaiyuan);//订单来源标志
								comm.setOrderSource(orderSource);//订单来源
							}
							comm.setOrderTotalPrice(orderAmount);//订单总金额
							comm.setPlaceOrderDate(df.format(new Date()));//下单时间
							commuService.updateCommu(comm);
						}
					}
				} catch (Exception e) {
					logger.error("保存沟通记录下单信息异常！commuId:"+commuId,e);
				}
				
				//清除Session中商品列表
				session.removeAttribute("prodList"+dateString);
				//清除Session中下单用户ID
				session.removeAttribute("orderUserId"+dateString);
				//清除Session中下单用户账号
				session.removeAttribute("orderUserAccount"+dateString);
				//清除Session中下单用户手机
				session.removeAttribute("orderPhone"+dateString);
			}
		}catch(Exception e){
			logger.error("提交下单出错：" , e);
			result.put("orderCode", 500);
			result.put("Message", e.getMessage());
		} finally {
			out.println(result);
			out.flush();
		    out.close();
		}
	}
	
	/**
	 * 官网会员
	 */
	@RequestMapping(params = ("method=getOfficalMember"))
	public String getOfficalMember(HttpServletRequest request, ModelMap modelMap,String tel) {
		try{
			String uri = request.getRequestURI();
			String ac = uri.substring(uri.lastIndexOf("/")+1);
			modelMap.put("actionName", ac);
			String result = getMemberInfo(request, modelMap,tel);
			if("opcenter/custService/error".equals(result)){
				return "opcenter/custService/error";
			}
		}catch(Exception e){
			logger.error("查看官网会员出错",e);
		}
		return "opcenter/custService/official_member";
	}
	/**
	 * 个人信息
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = ("method=getMemberInfo"))
	public String getMemberInfo(HttpServletRequest request, ModelMap modelMap,String tel) {
		try{
			String uri = request.getRequestURI();
			String ac = uri.substring(uri.lastIndexOf("/")+1);
			modelMap.put("actionName", ac);
			/**查询个人信息 */
			if(tel == null || "".equals(tel.trim()) || "输入手机号查询".equals(tel.trim())){
				modelMap.put("mesg", "请您先填写手机号码，再查看会员档案。");
				return "opcenter/custService/error";
			}
			
			/**解析返回结果 */
			JSONObject jsonResponse = this.getOfficalMemberInfo(tel);
			int code = Integer.parseInt(jsonResponse.getString("code"));
			if(code == 0){
				modelMap.put("mesg", jsonResponse.get("tip"));
				return "opcenter/custService/error";
			}
			MemberVO member = new MemberVO();
			List<HealthKeywordDetail> tempDetailList = new ArrayList<HealthKeywordDetail>();
			List<HealthkeywordVO> healthHeadList = new ArrayList<HealthkeywordVO>();
			List<HealthKeywordDetail> healthDetail = new ArrayList<HealthKeywordDetail>();
			/**查询健康信息资料 */
			List<HealthKeywordHead> healthKeywordHeadList = healthKeywordHeadService.getHealthKeywordHead();
			for (int i = 0; i < healthKeywordHeadList.size(); i++) {
				HealthKeywordHead healthHead = healthKeywordHeadList.get(i);
				HealthkeywordVO healthKeywordVO = new HealthkeywordVO();
				healthKeywordVO.setId(healthHead.getId());
				healthKeywordVO.setClassName(healthHead.getClassName());
				healthKeywordVO.setCreateUser(healthHead.getClassName());
				healthKeywordVO.setCreateTime(healthHead.getCreateTime());
				healthKeywordVO.setUpdateUser(healthHead.getUpdateUser());
				healthKeywordVO.setUpdateTime(healthHead.getUpdateTime());
				healthDetail = healthKeywordDetailService.getHealthKeywordDetailByPid(healthHead.getId());
				healthKeywordVO.setHealthDetail(healthDetail);
				healthHeadList.add(healthKeywordVO);
			}
			if(code == 1){
				String userBaseAccountInfo = jsonResponse.getString("info");
				if(null != userBaseAccountInfo && !"".equals(userBaseAccountInfo.trim())){
					JSONObject jsonUserBaseAccountInfo = JSONObject.fromObject(userBaseAccountInfo);
					/**设定个人基本信息 */
					member.setMemberCode(jsonUserBaseAccountInfo.getString("id"));
					member.setMemberName(isNULL(jsonUserBaseAccountInfo.getString("accountName")));
					member.setNickName(isNULL(jsonUserBaseAccountInfo.getString("nickName")));
					member.setSex(isNULL(jsonUserBaseAccountInfo.getString("sex")));
					member.setAge(isNULL(jsonUserBaseAccountInfo.getString("age")));
					member.setEmail(isNULL(jsonUserBaseAccountInfo.getString("email")));
					member.setTel(isNULL(jsonUserBaseAccountInfo.getString("phone")));
					member.setHeadImageUrl(isNULL(jsonUserBaseAccountInfo.getString("headImageUrl")));
					member.setRealName(isNULL(jsonUserBaseAccountInfo.getString("realName")));
					member.setBirthday(isNULL(jsonUserBaseAccountInfo.getString("birthday")));
					member.setProvince(isNULL(jsonUserBaseAccountInfo.getString("defaultProvince")));
					member.setCity(isNULL(jsonUserBaseAccountInfo.getString("defaultCity")));
					member.setDistrct(isNULL(jsonUserBaseAccountInfo.getString("defaultDistrct")));
					
					/**设定个人健康档案信息 */
					Object tagStr = jsonUserBaseAccountInfo.get("tags");
					if(null != tagStr && !"".equals(tagStr.toString().trim()) && !"null".equals(tagStr.toString().trim().toLowerCase())){
						JSONObject tagArr = JSONObject.fromObject(tagStr);
						Iterator<String> it = tagArr.keys();  
						HealthKeywordDetail hkd = null;
			            while (it.hasNext()) {  
			            	hkd = new HealthKeywordDetail();
			                String key = it.next();  
			                String value = tagArr.getString(String.valueOf(key)); 
			                hkd.setId(Integer.parseInt(key));
			                hkd.setKeyword(value);
			                tempDetailList.add(hkd);
			            }  
					}
					String userType = jsonUserBaseAccountInfo.getString("userType");
					if(userType!=null && !"".equals(userType=userType.trim())){
						String memberGrade = null;
						if("10000".equals(userType)){
							memberGrade = "普通用户";
						}else if(userType.startsWith("2000") && userType.length()==5){
							memberGrade = "VIP" + userType.substring(userType.length()-1);
						}
						modelMap.put("memberGrade", memberGrade);
					}
				} 
			} 
			modelMap.put("member", member);
			modelMap.put("healthList", healthHeadList);
			modelMap.put("tempDetailList", tempDetailList);
		}catch(Exception e){
			logger.error("查看官网会员->个人信息出错",e);
			modelMap.put("mesg", "获取个人信息失败：" + e.getMessage());
			return "opcenter/custService/error";
		}
		return "opcenter/custService/memberInfo";
	}
	/**
	 * 转换null串
	 * @param str
	 * @return
	 */
	private String isNULL(String str){
		if(str == null || "null".equals(str.trim())){
			return "";
		}
		return str;
	}
	
	/** 获取官网会员信息
	 */
	private JSONObject getOfficalMemberInfo(String tel){
		RestTemplate restTemplate = new RestTemplate();
        String restUri =officialUrl + "/v1/operation/user/baseinfo/show.jsonp?token={token}&uuid={uuid}&phone={aname}";
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
		return jsonResponse;
	}
	
	/**
	 * 个人资产
	 */
	@RequestMapping(params = ("method=getMemberAssets"))
	public String getMemberAssets(HttpServletRequest request, ModelMap modelMap,String userId) {
		try{
			String uri = request.getRequestURI();
			String ac = uri.substring(uri.lastIndexOf("/")+1);
			modelMap.put("actionName", ac);
			if(userId == null || "".equals(userId.trim())){
				logger.info("获取个人现金消费信息失败：userId字段为空");
				return "opcenter/custService/cash";
			}
			String result = getCashInfo(request, modelMap, userId);
			if("opcenter/custService/error".equals(result)){
				return result;
			}
		}catch(Exception e){
			logger.error("查看官网会员->个人资产出错",e);
			modelMap.put("mesg", "获取个人资产失败：" + e.getMessage());
			return "opcenter/custService/error";
		}
		return "opcenter/custService/memberAssets";
	}
	/**
	 * 获取账户余额和积分余额信息
	 * @param userId
	 * @param modelMap
	 * @return
	 */
	private String getBalance(String userId,ModelMap modelMap){
		String restUri =officialUrl + "/v1/operation/property/show.jsonp?token={token}&uuid={uuid}&uid={uid}";
		Map<String, String> variables = new HashMap<String, String>();
		variables.put("token", accountToken);
		variables.put("uuid", accountUUID);
		variables.put("uid", userId);
		//个人现金消费信息
		String res = "[]";
		res = restTemplate.getForObject(restUri, String.class, variables);
		if(res!=null && res.trim().length()>0){
			JSONObject json = JSONObject.fromObject(res);
			String jsonStr = json.getString("response");
			JSONObject resultJson = JSONObject.fromObject(jsonStr);
			int code = Integer.parseInt(resultJson.getString("code"));
			if(code == 0){
				modelMap.put("mesg", resultJson.get("tip"));
				return "opcenter/custService/error";
			}
			Object moneyBalance = resultJson.getString("moneyBalance");
			if(moneyBalance!=null){
				modelMap.put("moneyBalance",moneyBalance.toString());
			}
			Object integralBalance = resultJson.getString("integralBalance");
			if(integralBalance!=null){
				modelMap.put("integralBalance",integralBalance.toString());
			}
		}
		return null;
	}
	/**
	 * 查看现金账户信息
	 */
	@RequestMapping(params = ("method=getCashInfo"))
	public String getCashInfo(HttpServletRequest request,ModelMap modelMap,String userId) {
		try {
			String uri = request.getRequestURI();
			String ac = uri.substring(uri.lastIndexOf("/")+1);
			modelMap.put("actionName", ac);
			if(userId == null || "".equals(userId.trim())){
				modelMap.put("mesg", "获取个人现金消费信息失败：userId字段为空");
				return "opcenter/custService/error";
			}
			String pageno = request.getParameter("pageno");
			String pageSize = request.getParameter("pageSize");
			String total = request.getParameter("recTotal");
			
			if( pageno == null || "".equals(pageno) ){
				this.setPageno(1);
			}else{
				this.setPageno(Integer.parseInt(pageno));
				if( this.getPageno() < 1 ){
					this.setPageno(1);
				}
			}
			if( pageSize == null || "".equals(pageSize)){
				this.setPageSize(10);
			}else{
				this.setPageSize(Integer.parseInt(pageSize));
			}
			
			if(total != null && !"".equals(total.trim())){
				int t = Integer.parseInt(total);
				int tPage = t / this.getPageSize();
				tPage = t % this.getPageSize() == 0 ? tPage : (tPage + 1);
				if( this.getPageno() > tPage ){
					this.setPageno(tPage);
				}
			}
			/**设定参数*/
			String restUri =officialUrl + "/v1/kefu/user/consume/list.jsonp?token={token}&uuid={uuid}&uid={uid}&pn={pn}&pz={pz}";
			Map<String, String> variables = new HashMap<String, String>();
			variables.put("token", accountToken);
			variables.put("uuid", accountUUID);
			variables.put("uid", userId);
			variables.put("pn", this.getPageno()+"");
			variables.put("pz", this.getPageSize()+"");
			//个人现金消费信息
			String res = "[]";
			res = restTemplate.getForObject(restUri, String.class, variables);
			if(res!=null && res.trim().length()>0){
				JSONObject json = JSONObject.fromObject(res);
				String jsonStr = json.getString("response");
				JSONObject resultJson = JSONObject.fromObject(jsonStr);
				int code = Integer.parseInt(resultJson.getString("code"));
				if(code == 0){
					modelMap.put("mesg", resultJson.get("tip"));
					return "opcenter/custService/error";
				}
				Object cashObj = resultJson.get("list");
				if(cashObj!=null){
					String s = cashObj.toString();
					if(s.trim().length()>0){
						JSONArray cashList = JSONArray.fromObject(s);
						if(cashList != null && cashList.size() > 0){
							for (int i = 0; i < cashList.size(); i++) {
								JSONObject jo = (JSONObject) cashList.get(i);
								String value = jo.getString("consumeTime");
								value = convertToDate(value);
								jo.remove("consumeTime");
								jo.put("consumeTime", value);
							}
						}
						modelMap.put("cls",cashList);
					}
				}
				//获得分页数据信息
				Object pageObj = resultJson.get("pageTurn");
				if(pageObj != null && !"".equals(pageObj.toString().trim()) && !"null".equals(pageObj.toString().trim())){
					JSONObject pageTurn = JSONObject.fromObject(pageObj.toString().trim());
					recTotal = pageTurn.getInt("rowCount");
					pageTotal = pageTurn.getInt("pageCount");
					if( this.getPageno() > pageTotal ){
						this.setPageno(1);
					}
					modelMap.put("pageno", this.getPageno());
					modelMap.put("pageTotal", pageTotal);
					modelMap.put("pageSize", this.getPageSize());
					modelMap.put("recTotal", recTotal);
				}
			}
			String rs = getBalance(userId, modelMap);
			if(rs != null && !"".equals(rs)){
				return rs;
			}
		} catch (Exception e) {
			logger.error("查看官网会员->个人资产->现金账户出错", e);
			modelMap.put("mesg", "获取现金账户失败：" + e.getMessage());
			return "opcenter/custService/error";
		}
		return "opcenter/custService/cash";
	}
	/**
	 * 时间戳转换成时间格式
	 * @param str
	 * @return
	 */
	private String convertToDate(String str){
		String sd = "";
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sd = sdf.format(new Date(Long.parseLong(str)));
		}catch(Exception e){
			e.printStackTrace();
		}
		return sd;
	}
	/**
	 * 查看积分账户信息
	 */
	@RequestMapping(params = ("method=getIntegralInfo"))
	public String getIntegralInfo(HttpServletRequest request, ModelMap modelMap,String userId) {
		try{
			String uri = request.getRequestURI();
			String ac = uri.substring(uri.lastIndexOf("/")+1);
			modelMap.put("actionName", ac);
			
			if(userId == null || "".equals(userId.trim())){
				modelMap.put("mesg", "获取个人积分消费信息失败：userId字段为空");
				return "opcenter/custService/error";
			}
			String pageno = request.getParameter("pageno");
			String pageSize = request.getParameter("pageSize");
			String total = request.getParameter("recTotal");
			
			if( pageno == null || "".equals(pageno) ){
				this.setPageno(1);
			}else{
				this.setPageno(Integer.parseInt(pageno));
				if( this.getPageno() < 1 ){
					this.setPageno(1);
				}
			}
			if( pageSize == null || "".equals(pageSize)){
				this.setPageSize(10);
			}else{
				this.setPageSize(Integer.parseInt(pageSize));
			}
			
			if(total != null && !"".equals(total.trim())){
				int t = Integer.parseInt(total);
				int tPage = t / this.getPageSize();
				tPage = t % this.getPageSize() == 0 ? tPage : (tPage + 1);
				if( this.getPageno() > tPage ){
					this.setPageno(tPage);
				}
			}
			/**设定参数*/
			String restUri =officialUrl + "/v1/operation/integral/consume/list.jsonp?token={token}&uuid={uuid}&uid={uid}&pn={pn}&pz={pz}";
			Map<String, String> variables = new HashMap<String, String>();
			variables.put("token", accountToken);
			variables.put("uuid", accountUUID);
			variables.put("uid", userId);
			variables.put("pn", this.getPageno()+"");
			variables.put("pz", this.getPageSize()+"");
			//个人现金消费信息
			String res = "[]";
			res = restTemplate.getForObject(restUri, String.class, variables);
			if(res!=null && res.trim().length()>0){
				JSONObject json = JSONObject.fromObject(res);
				String jsonStr = json.getString("response");
				JSONObject resultJson = JSONObject.fromObject(jsonStr);
				int code = Integer.parseInt(resultJson.getString("code"));
				if(code == 0){
					modelMap.put("mesg", resultJson.get("tip"));
					return "opcenter/custService/error";
				}
				Object integralObj = resultJson.get("list");
				if(integralObj!=null){
					String s = integralObj.toString();
					if(s.trim().length()>0){
						JSONArray integralList = JSONArray.fromObject(s);
						if(integralList != null && integralList.size() > 0){
							for (int i = 0; i < integralList.size(); i++) {
								JSONObject jo = (JSONObject) integralList.get(i);
								String value = jo.getString("consumeTime");
								value = convertToDate(value);
								jo.remove("consumeTime");
								jo.put("consumeTime", value);
							}
						}
						modelMap.put("integralLs",integralList);
					}
				}
				//获得分页数据信息
				Object pageObj = resultJson.get("pageTurn");
				if(pageObj != null && !"".equals(pageObj.toString().trim()) && !"null".equals(pageObj.toString().trim())){
					JSONObject pageTurn = JSONObject.fromObject(pageObj.toString().trim());
					recTotal = pageTurn.getInt("rowCount");
					pageTotal = pageTurn.getInt("pageCount");
					if( this.getPageno() > pageTotal ){
						this.setPageno(1);
					}
					modelMap.put("pageno", this.getPageno());
					modelMap.put("pageTotal", pageTotal);
					modelMap.put("pageSize", this.getPageSize());
					modelMap.put("recTotal", recTotal);
				}
			}
			String rs = getBalance(userId, modelMap);
			if(rs != null && !"".equals(rs)){
				return rs;
			}
		} catch (Exception e) {
			logger.error("查看官网会员->个人资产->积分账户出错", e);
			modelMap.put("mesg", "获取积分账户失败：" + e.getMessage());
			return "opcenter/custService/error";
		}
		return "opcenter/custService/integral";
	}
	/**
	 * 查看优惠券信息
	 */
	@RequestMapping(params = ("method=getCouponInfo"))
	public String getCouponInfo(HttpServletRequest request,String userId, ModelMap modelMap) {
		try{
			String uri = request.getRequestURI();
			String ac = uri.substring(uri.lastIndexOf("/")+1);
			modelMap.put("actionName", ac);
			if(userId == null || "".equals(userId.trim())){
				modelMap.put("mesg", "获取优惠券信息失败：userId字段为空");
				return "opcenter/custService/error";
			}
			String result = getEnableCoupon(request, modelMap,userId);
			if("opcenter/custService/error".equals(result)){
				return result;
			}
		}catch(Exception e){
			logger.error("查看官网会员->优惠券信息出错",e);
			modelMap.put("mesg", "获取优惠券信息失败：" + e.getMessage());
			return "opcenter/custService/error";
		}
		return "opcenter/custService/coupon";
	}
	/**
	 * 可用优惠券
	 */
	@RequestMapping(params = ("method=getEnableCoupon"))
	public String getEnableCoupon(HttpServletRequest request,ModelMap modelMap,String userId) {
		return coupon(request, userId, modelMap, "1", "opcenter/custService/enableCoupon");
	}
	/**
	 * 已使用优惠券
	 */
	@RequestMapping(params = ("method=getDisableCoupon"))
	public String getDisableCoupon(HttpServletRequest request,String userId, ModelMap modelMap) {
		return coupon(request, userId, modelMap, "4", "opcenter/custService/disableCoupon");
	}
	/**
	 * 已过期优惠券
	 */
	@RequestMapping(params = ("method=getOverdueCoupon"))
	public String getOverdueCoupon(HttpServletRequest request,String userId, ModelMap modelMap) {
		return coupon(request, userId, modelMap, "6", "opcenter/custService/overdueCoupon");
	}
	/**
	 * 优惠券
	 */
	private String coupon(HttpServletRequest request,String userId, ModelMap modelMap,String apid,String url) {
		try{
			String uri = request.getRequestURI();
			String ac = uri.substring(uri.lastIndexOf("/")+1);
			modelMap.put("actionName", ac);
			
			if(userId == null || "".equals(userId.trim())){
				modelMap.put("mesg", "获取可用优惠券信息失败：userId字段为空");
				return "opcenter/custService/error";
			}
			String pageno = request.getParameter("pageno");
			String pageSize = request.getParameter("pageSize");
			String total = request.getParameter("recTotal");
			
			if( pageno == null || "".equals(pageno) ){
				this.setPageno(1);
			}else{
				this.setPageno(Integer.parseInt(pageno));
				if( this.getPageno() < 1 ){
					this.setPageno(1);
				}
			}
			if( pageSize == null || "".equals(pageSize)){
				this.setPageSize(10);
			}else{
				this.setPageSize(Integer.parseInt(pageSize));
			}
			
			if(total != null && !"".equals(total.trim())){
				int t = Integer.parseInt(total);
				int tPage = t / this.getPageSize();
				tPage = t % this.getPageSize() == 0 ? tPage : (tPage + 1);
				if( this.getPageno() > tPage ){
					this.setPageno(tPage);
				}
			}
			/**设定参数*/
			String restUri =officialUrl4 + "/api/card/ucenter/getUserCouponNumber.jsonp?token={token}&uuid={uuid}&uid={uid}&cs={cs}&apid={apid}&pn={pn}&pz={pz}";
			Map<String, String> variables = new HashMap<String, String>();
			variables.put("token", accountToken);
			variables.put("uuid", accountUUID);
			variables.put("uid", userId);
			variables.put("cs", apid);
			variables.put("apid", "3");
			variables.put("pn", this.getPageno()+"");
			variables.put("pz", this.getPageSize()+"");
			//个人现金消费信息
			String res = "[]";
			res = restTemplate.getForObject(restUri, String.class, variables);
			if(res!=null && res.trim().length()>0){
				JSONObject json = JSONObject.fromObject(res);
				String jsonStr = json.getString("response");
				JSONObject resultJson = JSONObject.fromObject(jsonStr);
				int code = Integer.parseInt(resultJson.getString("code"));
				if(code == 0){
					modelMap.put("mesg", resultJson.get("tip"));
					return "opcenter/custService/error";
				}
				Object couponObj = resultJson.get("list");
				if(couponObj!=null){
					String s = couponObj.toString();
					if(s.trim().length()>0){
						JSONArray couponLs = JSONArray.fromObject(s);
						if(couponLs != null && couponLs.size() > 0){
							for (int i = 0; i < couponLs.size(); i++) {
								JSONObject jo = (JSONObject) couponLs.get(i);
								String beginTime = jo.getString("beginTime");
								beginTime = convertToDate(beginTime);
								jo.remove("beginTime");
								jo.put("beginTime", beginTime);
								String endTime = jo.getString("endTime");
								endTime = convertToDate(endTime);
								jo.remove("endTime");
								jo.put("endTime", endTime);
							}
						}
						modelMap.put("couponLs",couponLs);
					}
				}
				//获得分页数据信息
				Object pageObj = resultJson.get("pageTurn");
				if(pageObj != null && !"".equals(pageObj.toString().trim()) && !"null".equals(pageObj.toString().trim())){
					JSONObject pageTurn = JSONObject.fromObject(pageObj.toString().trim());
					recTotal = pageTurn.getInt("rowCount");
					pageTotal = pageTurn.getInt("pageCount");
					if( this.getPageno() > pageTotal ){
						this.setPageno(1);
					}
					modelMap.put("pageno", this.getPageno());
					modelMap.put("pageTotal", pageTotal);
					modelMap.put("pageSize", this.getPageSize());
					modelMap.put("recTotal", recTotal);
				}
			}
		} catch (Exception e) {
			logger.error("查看官网会员->已有优惠券出错", e);
			modelMap.put("mesg", "获取优惠券信息失败：" + e.getMessage());
			return "opcenter/custService/error";
		}
		return url;
	}
	
	/**
	 * 查看送货地址信息
	 */
	@RequestMapping(params = ("method=getUserAddressInfo"))
	public String getUserAddressInfo(HttpServletRequest request,String userId, ModelMap modelMap) {
		try{
			String uri = request.getRequestURI();
			String ac = uri.substring(uri.lastIndexOf("/")+1);
			modelMap.put("actionName", ac);
			
			if(userId == null || "".equals(userId.trim())){
				modelMap.put("mesg", "获取用户地址信息失败：userId字段为空");
				return "opcenter/custService/error";
			}
			String pageno = request.getParameter("pageno");
			String pageSize = request.getParameter("pageSize");
			String total = request.getParameter("recTotal");
			
			if( pageno == null || "".equals(pageno) ){
				this.setPageno(1);
			}else{
				this.setPageno(Integer.parseInt(pageno));
				if( this.getPageno() < 1 ){
					this.setPageno(1);
				}
			}
			if( pageSize == null || "".equals(pageSize)){
				this.setPageSize(10);
			}else{
				this.setPageSize(Integer.parseInt(pageSize));
			}
			
			if(total != null && !"".equals(total.trim())){
				int t = Integer.parseInt(total);
				int tPage = t / this.getPageSize();
				tPage = t % this.getPageSize() == 0 ? tPage : (tPage + 1);
				if( this.getPageno() > tPage ){
					this.setPageno(tPage);
				}
			}
			/**设定参数*/
			String restUri =officialUrl + "/v1/operation/shipping/address/list.jsonp?token={token}&uuid={uuid}&uid={uid}&pn={pn}&pz={pz}";
			Map<String, String> variables = new HashMap<String, String>();
			variables.put("token", accountToken);
			variables.put("uuid", accountUUID);
			variables.put("uid", userId);
			variables.put("pn", this.getPageno()+"");
			variables.put("pz", this.getPageSize()+"");
			//个人现金消费信息
			String res = "[]";
			res = restTemplate.getForObject(restUri, String.class, variables);
			if(res!=null && res.trim().length()>0){
				JSONObject json = JSONObject.fromObject(res);
				String jsonStr = json.getString("response");
				JSONObject resultJson = JSONObject.fromObject(jsonStr);
				int code = Integer.parseInt(resultJson.getString("code"));
				if(code == 0){
					modelMap.put("mesg", resultJson.get("tip"));
					return "opcenter/custService/error";
				}
				Object addressObj = resultJson.get("list");
				if(addressObj!=null){
					String s = addressObj.toString();
					if(s.trim().length()>0){
						JSONArray als = JSONArray.fromObject(s);
						modelMap.put("als",als);
					}
				}
				//获得分页数据信息
				Object pageObj = resultJson.get("pageTurn");
				if(pageObj != null && !"".equals(pageObj.toString().trim()) && !"null".equals(pageObj.toString().trim())){
					JSONObject pageTurn = JSONObject.fromObject(pageObj.toString().trim());
					recTotal = pageTurn.getInt("rowCount");
					pageTotal = pageTurn.getInt("pageCount");
					if( this.getPageno() > pageTotal ){
						this.setPageno(1);
					}
					modelMap.put("pageno", this.getPageno());
					modelMap.put("pageTotal", pageTotal);
					modelMap.put("pageSize", this.getPageSize());
					modelMap.put("recTotal", recTotal);
				}
			}
		} catch (Exception e) {
			logger.error("查看官网会员->送货地址信息出错", e);
			modelMap.put("mesg", "获取送货地址信息失败：" + e.getMessage());
			return "opcenter/custService/error";
		}
		return "opcenter/custService/address";
	}
	/**
	 * 查看家庭成员
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = ("method=getFamilysInfo"))
	public String getFamilysInfo(HttpServletRequest request,String userId, ModelMap modelMap) {
		try{
			String uri = request.getRequestURI();
			String ac = uri.substring(uri.lastIndexOf("/")+1);
			modelMap.put("actionName", ac);
			
			if(userId == null || "".equals(userId.trim())){
				modelMap.put("mesg", "获取家庭成员信息失败：userId字段为空");
				return "opcenter/custService/error";
			}
			String pageno = request.getParameter("pageno");
			String pageSize = request.getParameter("pageSize");
			String total = request.getParameter("recTotal");
			
			if( pageno == null || "".equals(pageno) ){
				this.setPageno(1);
			}else{
				this.setPageno(Integer.parseInt(pageno));
				if( this.getPageno() < 1 ){
					this.setPageno(1);
				}
			}
			if( pageSize == null || "".equals(pageSize)){
				this.setPageSize(10);
			}else{
				this.setPageSize(Integer.parseInt(pageSize));
			}
			
			if(total != null && !"".equals(total.trim())){
				int t = Integer.parseInt(total);
				int tPage = t / this.getPageSize();
				tPage = t % this.getPageSize() == 0 ? tPage : (tPage + 1);
				if( this.getPageno() > tPage ){
					this.setPageno(tPage);
				}
			}
			/**设定参数*/
			String restUri =officialUrl + "/v1/operation/user/family/list.jsonp?token={token}&uuid={uuid}&uid={uid}&pn={pn}&pz={pz}";
			Map<String, String> variables = new HashMap<String, String>();
			variables.put("token", accountToken);
			variables.put("uuid", accountUUID);
			variables.put("uid", userId);
			variables.put("pn", this.getPageno()+"");
			variables.put("pz", this.getPageSize()+"");
			//个人现金消费信息
			String res = "[]";
			res = restTemplate.getForObject(restUri, String.class, variables);
			if(res!=null && res.trim().length()>0){
				JSONObject json = JSONObject.fromObject(res);
				String jsonStr = json.getString("response");
				JSONObject resultJson = JSONObject.fromObject(jsonStr);
				int code = Integer.parseInt(resultJson.getString("code"));
				if(code == 0){
					modelMap.put("mesg", resultJson.get("tip"));
					return "opcenter/custService/error";
				}
				Object familyObj = resultJson.get("list");
				if(familyObj!=null){
					String s = familyObj.toString();
					if(s.trim().length()>0){
						JSONArray familyList = JSONArray.fromObject(s);
						/**设定个人健康档案信息 */
						if(familyList != null && familyList.size() > 0){
							for (int i = 0; i < familyList.size(); i++) {
								JSONObject jo = (JSONObject) familyList.get(i);
								String tagStr = jo.getString("diseaseTag");
								String keyWord = "";
								if(null != tagStr && !"".equals(tagStr.toString().trim()) && !"null".equals(tagStr.toString().trim().toLowerCase())){
									JSONObject tagArr = JSONObject.fromObject(tagStr);
									Iterator<String> it = tagArr.keys();  
						            while (it.hasNext()) {  
						                String key = it.next();  
						                String value = tagArr.getString(String.valueOf(key)); 
						                keyWord += value + ",";
						            } 
						            if(keyWord.length() > 0){
						            	keyWord = keyWord.substring(0,keyWord.length() - 1);
						            }
								}
								jo.remove("diseaseTag");
								jo.put("diseaseTag", keyWord);
							}
						}
						modelMap.put("familyLs",familyList);
						
					}
				}
				//获得分页数据信息
				Object pageObj = resultJson.get("pageTurn");
				if(pageObj != null && !"".equals(pageObj.toString().trim()) && !"null".equals(pageObj.toString().trim())){
					JSONObject pageTurn = JSONObject.fromObject(pageObj.toString().trim());
					recTotal = pageTurn.getInt("rowCount");
					pageTotal = pageTurn.getInt("pageCount");
					if( this.getPageno() > pageTotal ){
						this.setPageno(1);
					}
					modelMap.put("pageno", this.getPageno());
					modelMap.put("pageTotal", pageTotal);
					modelMap.put("pageSize", this.getPageSize());
					modelMap.put("recTotal", recTotal);
				}
			}
		} catch (Exception e) {
			logger.error("查看官网会员->查看家庭成员出错", e);
			modelMap.put("mesg", "获取家庭成员信息失败：" + e.getMessage());
			return "opcenter/custService/error";
		}
		return "opcenter/custService/family";
	}
	/**
	 * 查看浏览记录
	 */
	@RequestMapping(params = ("method=getBrowseRecord"))
	public String getBrowseRecord(HttpServletRequest request,String userId, ModelMap modelMap) {
		try{
			String uri = request.getRequestURI();
			String ac = uri.substring(uri.lastIndexOf("/")+1);
			modelMap.put("actionName", ac);
			
			if(userId == null || "".equals(userId.trim())){
				modelMap.put("mesg", "获取用户浏览记录信息失败：userId字段为空");
				return "opcenter/custService/error";
			}
			String pageno = request.getParameter("pageno");
			String pageSize = request.getParameter("pageSize");
			String total = request.getParameter("recTotal");
			
			if( pageno == null || "".equals(pageno) ){
				this.setPageno(1);
			}else{
				this.setPageno(Integer.parseInt(pageno));
				if( this.getPageno() < 1 ){
					this.setPageno(1);
				}
			}
			if( pageSize == null || "".equals(pageSize)){
				this.setPageSize(10);
			}else{
				this.setPageSize(Integer.parseInt(pageSize));
			}
			
			if(total != null && !"".equals(total.trim())){
				int t = Integer.parseInt(total);
				int tPage = t / this.getPageSize();
				tPage = t % this.getPageSize() == 0 ? tPage : (tPage + 1);
				if( this.getPageno() > tPage ){
					this.setPageno(tPage);
				}
			}
			/**设定参数*/
			String restUri =officialUrl2 + "/v1/user/userHistory/getByUid.jsonp?token={token}&uuid={uuid}&uid={uid}&pn={pn}&pz={pz}";
			Map<String, String> variables = new HashMap<String, String>();
			variables.put("token", accountToken);
			variables.put("uuid", accountUUID);
			variables.put("uid", userId);
			variables.put("pn", this.getPageno()+"");
			variables.put("pz", this.getPageSize()+"");
			//个人现金消费信息
			String res = "[]";
			res = restTemplate.getForObject(restUri, String.class, variables);
			if(res!=null && res.trim().length()>0){
				JSONObject json = JSONObject.fromObject(res);
				String jsonStr = json.getString("response");
				JSONObject resultJson = JSONObject.fromObject(jsonStr);
				int code = Integer.parseInt(resultJson.getString("code"));
				if(code == 0){
					modelMap.put("mesg", resultJson.get("tip"));
					return "opcenter/custService/error";
				}
				Object brObj = resultJson.get("list");
				if(brObj!=null){
					String s = brObj.toString();
					if(s.trim().length()>0){
						JSONArray brls = JSONArray.fromObject(s);
						if(brls != null && brls.size() > 0){
							for (int i = 0; i < brls.size(); i++) {
								JSONObject jo = (JSONObject) brls.get(i);
								String mealId = jo.getString("mealId");
								if(mealId != null && !"".equals(mealId)){
									JSONObject obj = getGoodsInfo(mealId);
									if(obj != null){
										jo.put("mealName", obj.getString("mealName"));//商品名称
										jo.put("mealPrice", obj.getString("mealPrice"));//商品名称
									}else{
										brls.remove(jo);
									}
								}
								String value = jo.getString("visitDay");
								value = convertToDate(value);
								jo.remove("visitDay");
								jo.put("visitDay", value);
							}
						}
						modelMap.put("brls",brls);
					}
				}
				//获得分页数据信息
				Object pageObj = resultJson.get("pageTurn");
				if(pageObj != null && !"".equals(pageObj.toString().trim()) && !"null".equals(pageObj.toString().trim())){
					JSONObject pageTurn = JSONObject.fromObject(pageObj.toString().trim());
					recTotal = pageTurn.getInt("rowCount");
					pageTotal = pageTurn.getInt("pageCount");
					if( this.getPageno() > pageTotal ){
						this.setPageno(1);
					}
					modelMap.put("pageno", this.getPageno());
					modelMap.put("pageTotal", pageTotal);
					modelMap.put("pageSize", this.getPageSize());
					modelMap.put("recTotal", recTotal);
				}
			}
		} catch (Exception e) {
			logger.error("查看官网会员->查询用户浏览记录信息出错", e);
			modelMap.put("mesg", "获取用户浏览记录信息失败：" + e.getMessage());
			return "opcenter/custService/error";
		}
		return "opcenter/custService/browseRecord";
	}
	private JSONObject getGoodsInfo(String mealId){
		JSONObject obj = null;
		/**设定参数*/
		String restUri =officialUrl3 + "/v1/meal/partialList.jsonp?token={token}&uuid={uuid}&ids={ids}";
		Map<String, String> variables = new HashMap<String, String>();
		variables.put("token", accountToken);
		variables.put("uuid", accountUUID);
		variables.put("ids", mealId);
		//个人现金消费信息
		String res = "[]";
		res = restTemplate.getForObject(restUri, String.class, variables);
		if(res!=null && res.trim().length()>0){
			JSONObject json = JSONObject.fromObject(res);
			String jsonStr = json.getString("response");
			JSONObject resultJson = JSONObject.fromObject(jsonStr);
			int code = Integer.parseInt(resultJson.getString("code"));
			if(code == 0){
				return null;
			}
			Object goodsObj = resultJson.get("list");
			if(goodsObj!=null){
				String s = goodsObj.toString();
				if(s.trim().length()>0){
					JSONArray goodsLs = JSONArray.fromObject(s);
					if(goodsLs != null && goodsLs.size() > 0){
						obj = goodsLs.getJSONObject(0);
					}
				}
			}
		}
		return obj;
	}
	/**
	 * 查看用户收藏
	 */
	@RequestMapping(params = ("method=getUserCollection"))
	public String getUserCollection(HttpServletRequest request,String userId, ModelMap modelMap) {
		try{
			String uri = request.getRequestURI();
			String ac = uri.substring(uri.lastIndexOf("/")+1);
			modelMap.put("actionName", ac);
			
			if(userId == null || "".equals(userId.trim())){
				modelMap.put("mesg", "获取用户收藏信息失败：userId字段为空");
				return "opcenter/custService/error";
			}
			String pageno = request.getParameter("pageno");
			String pageSize = request.getParameter("pageSize");
			String total = request.getParameter("recTotal");
			
			if( pageno == null || "".equals(pageno) ){
				this.setPageno(1);
			}else{
				this.setPageno(Integer.parseInt(pageno));
				if( this.getPageno() < 1 ){
					this.setPageno(1);
				}
			}
			if( pageSize == null || "".equals(pageSize)){
				this.setPageSize(10);
			}else{
				this.setPageSize(Integer.parseInt(pageSize));
			}
			
			if(total != null && !"".equals(total.trim())){
				int t = Integer.parseInt(total);
				int tPage = t / this.getPageSize();
				tPage = t % this.getPageSize() == 0 ? tPage : (tPage + 1);
				if( this.getPageno() > tPage ){
					this.setPageno(tPage);
				}
			}
			/**设定参数*/
			String restUri =officialUrl + "/v1/operation/goods/favorite/list.jsonp?token={token}&uuid={uuid}&uid={uid}&pn={pn}&pz={pz}";
			Map<String, String> variables = new HashMap<String, String>();
			variables.put("token", accountToken);
			variables.put("uuid", accountUUID);
			variables.put("uid", userId);
			variables.put("pn", this.getPageno()+"");
			variables.put("pz", this.getPageSize()+"");
			//个人现金消费信息
			String res = "[]";
			res = restTemplate.getForObject(restUri, String.class, variables);
			if(res!=null && res.trim().length()>0){
				JSONObject json = JSONObject.fromObject(res);
				String jsonStr = json.getString("response");
				JSONObject resultJson = JSONObject.fromObject(jsonStr);
				int code = Integer.parseInt(resultJson.getString("code"));
				if(code == 0){
					modelMap.put("mesg", resultJson.get("tip"));
					return "opcenter/custService/error";
				}
				Object ucObj = resultJson.get("list");
				if(ucObj!=null){
					String s = ucObj.toString();
					if(s.trim().length()>0){
						JSONArray ucls = JSONArray.fromObject(s);
						if(ucls != null && ucls.size() > 0){
							for (int i = 0; i < ucls.size(); i++) {
								JSONObject jo = (JSONObject) ucls.get(i);
								String mealId = jo.getString("mealId");
								if(mealId != null && !"".equals(mealId)){
									JSONObject obj = getGoodsInfo(mealId);
									if(obj != null){
										jo.put("mealName", obj.getString("mealName"));//套餐名称
										jo.put("mainSku", obj.getString("mainSku"));//主商品编码
										jo.put("title", obj.getString("title"));//主商品名称
										jo.put("mealNormName", obj.getString("mealNormName"));//规格
										jo.put("mealPrice", obj.getString("mealPrice"));//售价
										jo.put("factory", obj.getString("factory"));//商品名称
									}else{
										ucls.remove(jo);
									}
								}
							}
						}
						modelMap.put("ucls",ucls);
					}
				}
				//获得分页数据信息
				Object pageObj = resultJson.get("pageTurn");
				if(pageObj != null && !"".equals(pageObj.toString().trim()) && !"null".equals(pageObj.toString().trim())){
					JSONObject pageTurn = JSONObject.fromObject(pageObj.toString().trim());
					recTotal = pageTurn.getInt("rowCount");
					pageTotal = pageTurn.getInt("pageCount");
					if( this.getPageno() > pageTotal ){
						this.setPageno(1);
					}
					modelMap.put("pageno", this.getPageno());
					modelMap.put("pageTotal", pageTotal);
					modelMap.put("pageSize", this.getPageSize());
					modelMap.put("recTotal", recTotal);
				}
			}
		} catch (Exception e) {
			logger.error("查看官网会员->查询用户收藏信息出错", e);
			modelMap.put("mesg", "获取用户收藏信息失败：" + e.getMessage());
			return "opcenter/custService/error";
		}
		return "opcenter/custService/userCollection";
	}
	
	/**
	 * 上一步
	 */
	@RequestMapping(params= ("method=previous"))
	public String previous(HttpServletRequest request,HttpSession session, ModelMap modelMap){
		try{
			String dateString = request.getParameter("dateString");
			String type = request.getParameter("type");
			String productList = "";
			String orderType = "";
			String uri = request.getRequestURI();
			String ac = uri.substring(uri.lastIndexOf("/")+1);			
			if(null != type && !"".equals(type)){
				if(type.equals("addOrder")){
					productList = "prodList"+dateString;
					orderType = "addOrderType"+dateString;
					modelMap.put("actionName", ac);
				}
				if(type.equals("updateOrder")){
					productList = "updateProdList"+dateString;
					orderType = "updateOrderType"+dateString;
					modelMap.put("outAction", ac);
				}
			}
			List<ProductVO> newList = new ArrayList<ProductVO>();
			session.setAttribute(productList, newList);
			session.setAttribute("orderUserId", "");
			session.setAttribute("orderUserAccount", "");
			session.setAttribute(orderType, "1");			
			OrderInfoVO orderInfo = new OrderInfoVO();
			modelMap.put("prodList", newList);
			modelMap.put("orderInfo", orderInfo);
			modelMap.put("orderType", "1");
		}catch(Exception e){
			logger.error("清理session失败：" + e.getMessage());
		}
		return "opcenter/custService/addOrder";
	}
	
	/**
	 * 查看预约List
	 */
	@RequestMapping(params = ("method=getReservaMessage"))
	public void getReservaMessage(HttpServletResponse response,HttpServletRequest request,ReservationVO re){
		try {
			PrintWriter out = response.getWriter();
			if(re == null || re.getTel() == null || "".equals(re.getTel().trim())){
				return;
			}
			/** 新增送货地址*/
			Reservation rt = reservationService.getReservaInfo(re);
			String message = null;
			if(rt != null && rt.getReserveTime() != null && !"".equals(rt.getReserveTime())){
				message = "此用户在" + rt.getReserveTime() + "已经有预约，新增预约可能覆盖之前预约，是否继续？";
			}
			out.println(message);
			out.flush(); 
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			logger.error("预约提醒：" + e.getMessage());
		}
		
	}
	/**
	 * 新增预约信息
	 */
	@RequestMapping(params = ("method=savaReservat"))
	public void savaReservat(HttpServletResponse response,HttpServletRequest request,Reservation re){
		try {
			PrintWriter out = response.getWriter();
			/** 新增送货地址*/
			reservationService.savaReservat(re);
			JSONObject object = new JSONObject();
			object.put("result","ok");
			out.println(object);
			out.flush(); 
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			logger.error("新增预约信息出错：" + e.getMessage());
		}
		
	}
	
	//今日任务拨号弹屏
	@RequestMapping(params = ("method=getScreenUrl"))
	@ResponseBody
	public String getScreenUrl(HttpServletResponse response,HttpServletRequest request,HttpSession session){
		JSONObject json = new JSONObject();
		
		String tel = request.getParameter("tel");
		String type = request.getParameter("type");
		
		if(tel==null || tel.trim().length()==0){
			json.put("errorMsg", "电话号码为空！");
			return json.toString();
		}
		
		//去电弹屏链接
		String url = request.getRequestURL()+"?method=getInfo";
		//今日任务
		if("RW".equals(type)){
			url += "&type=RW";
		}
		url += "&phoneNo="+tel;
		
		String uri = request.getRequestURI();
		String ac = uri.substring(uri.lastIndexOf("/")+1);
		//电销客服访问
		if("outScreen.do".equals(ac)){
			//客服工号
			Object code = session.getAttribute("custServCode");
			String custServCode="";
			if(code!=null){
				custServCode = code.toString();
				url += "&custServCode="+custServCode;
			}
			
			//生成加密验证码
			Map<String, String> map = new HashMap<String, String>();
			if("RW".equals(type)){
				map.put("type", type);
			}
			map.put("phoneNo",tel);
			map.put("custServCode",custServCode);
			HashMap<String, String> signMap = SignUtils.sign(map, outScreenSign);
			String sign = signMap.get("appSign");
			
			url+="&sign="+sign;
		}
		json.put("url", url);
		return json.toString();
	}
	
	/**
	 * @Title getDoctorByHospitalId
	 * @Description 根据医院ID查询医院下的医生
	 * @author 刘超
	 * @date 2015年9月22日
	 * @param response
	 * @param request
	 * @param out
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params="method=getDoctorByHospitalId")
	public void getDoctorByHospitalId(HttpServletResponse response,HttpServletRequest request, PrintWriter out){
		String hospitalId = request.getParameter("hospitalId");
		if(StringUtils.isEmpty(hospitalId)){
			hospitalId = "0";
		}
		//医生信息
		List<DoctorModel> doctorList = doctorService.queryHql(" from DoctorModel dm where salesRepId=" + hospitalId, null);
		if(null == doctorList){
			doctorList = new ArrayList<DoctorModel>();
		}
		String retResponseJson = JSONArray.fromObject(doctorList).toString();
		logger.info(retResponseJson);
		out.print(retResponseJson);
		out.flush();
		out.close();
	}
	/**
	 * 选择医生的时候把医生和医院ID保存到session中
	 * @param response
	 * @param request
	 * @param out
	 */
	@RequestMapping(params="method=doctorChange")
	public void doctorChange(HttpServletResponse response,HttpSession session,HttpServletRequest request){
		try {
			PrintWriter out = response.getWriter();
			//String type = request.getParameter("type");
			String dateString = request.getParameter("dateString");
			String hospitalId = request.getParameter("hospitalId");
			String doctorId = request.getParameter("doctorId");
			if(StringUtils.isEmpty(doctorId)){
				return;
			}
			session.setAttribute("hospitalId" + dateString, hospitalId);
			session.setAttribute("doctorId" + dateString, doctorId);
			JSONObject object = new JSONObject();
			object.put("result","ok");
			object.put("hospitalId",hospitalId);
			object.put("doctorId",doctorId);
			out.print(object);
			out.flush(); 
		    out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
