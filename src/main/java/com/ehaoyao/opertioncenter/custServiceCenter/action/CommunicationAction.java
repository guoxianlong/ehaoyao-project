package com.ehaoyao.opertioncenter.custServiceCenter.action;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.custServiceCenter.model.Communication;
import com.ehaoyao.opertioncenter.custServiceCenter.model.TrackInfo;
import com.ehaoyao.opertioncenter.custServiceCenter.service.CommuService;
import com.ehaoyao.opertioncenter.custServiceCenter.service.TelGoodsService;
import com.ehaoyao.opertioncenter.custServiceCenter.util.PropertiesUtil;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.CommuInfo;
import com.haoyao.goods.action.BaseAction;
import com.haoyao.goods.model.User;
import com.haoyao.goods.service.UserServiceImpl;

/**
 * 
 * Title: CommunicationAction.java
 * 
 * Description: 沟通记录
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年11月20日 下午1:53:19
 */
// commu.do呼叫中心访问，commu2.do运营中心后台访问
@Controller
@RequestMapping({"/commu.do","/commu2.do"})
public class CommunicationAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(CommunicationAction.class);
	//电销商品
	@Autowired
	private TelGoodsService telGoodsService;
	//沟通记录
	@Autowired
	private CommuService commuService;
	//用户信息
	@Autowired
	private UserServiceImpl userService;
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
	//产品分类列表 url
	private String frontClassUrl = PropertiesUtil.getProperties("extend.properties", "frontClassUrl");
	//产品分类信息
	public static String frontClass = null;
	public static Date frontClassTime = null;
	
	public void setTelGoodsService(TelGoodsService telGoodsService) {
		this.telGoodsService = telGoodsService;
	}
	
	public void setCommuService(CommuService commuService) {
		this.commuService = commuService;
	}
	
	/**
	 * 来电弹屏--沟通记录
	 */
	@RequestMapping(params = ("method=commuMain"))
	public String commuMain(HttpServletRequest request, ModelMap modelMap, CommuInfo commuVO) {
		String uri = request.getRequestURI();
		String ac = uri.substring(uri.lastIndexOf("/")+1);
		modelMap.put("commuAction", ac);
		String isTask = request.getParameter("isTask");
		modelMap.put("isTask", isTask);
		if("commu.do".equals(ac)){//呼叫中心访问
			modelMap.put("outAction", "outScreen.do");
		}else{//运营中心后台访问
			modelMap.put("outAction", "outScreen2.do");
		}
		//客户来源。TEL_OUT：呼入电话，TEL_OUT：老客维护，ZX：在线客服，XQ：需求登记
		if("TEL_OUT".equals(commuVO.getCustSource()) && !"1".equals(isTask)){//去电（老客维护），且不是今日任务，默认查询产品咨询
			commuVO.setAcceptResult("产品咨询");
		}else{//呼入电话、在线客服、需求订单，默认查询“咨询”
			commuVO.setAcceptResult("咨询");
		}
		modelMap.put("custServCode",request.getParameter("custServCodeVO"));
		this.getCommuLs(request, modelMap, commuVO);
		return "opcenter/commu/commuMain";
	}
	
	//查询沟通记录
	@SuppressWarnings("unchecked")
	@RequestMapping(params = ("method=getCommuLs"))
	public String getCommuLs(HttpServletRequest request, ModelMap modelMap, CommuInfo commuVO) {
		String type = commuVO.getAcceptResult();
		//沟通记录查询
		String tel = commuVO.getTel();
		if(tel!=null && tel.trim().length()>0){
			try {
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
				
				PageModel<CommuInfo> pm = new PageModel<CommuInfo>();
				pm.setPageSize(this.pageSize);
				pm.setPageNo(this.pageno);
				pm = commuService.getCommuLs(pm, commuVO);
				modelMap.put("pageno", pm.getPageNo());
				modelMap.put("pageTotal", pm.getTotalPages());
				modelMap.put("pageSize", pm.getPageSize());
				modelMap.put("recTotal", pm.getTotalRecords());
				
				List<CommuInfo> ls= (List<CommuInfo>)pm.getList();
				if(ls!=null && ls.size()>0 && ("咨询".equals(type) || "产品咨询".equals(type) || "客户维护".equals(type))){
					String isTask = request.getParameter("isTask");
					this.isEnable(ls,commuVO.getCustSource(),isTask);
				}
				modelMap.put("commuLs", ls);
				modelMap.put("curDate", df1.format(new Date()));
			} catch (Exception e) {
				modelMap.put("message", "1");
				logger.error("沟通记录查询失败！",e);
			}
		}
		String res = "opcenter/commu/proConsult";
		if("咨询".equals(type) || "产品咨询".equals(type) || "客户维护".equals(type)){//咨询、产品咨询、客户维护
			res =  "opcenter/commu/proConsult";
		}else{
			res =  "opcenter/commu/commuOther";
		}
		modelMap.put("commuSource", commuVO.getCustSource());
		return res;
	}
	
	/**
	 * 
	 * 描述 判断沟通记录是否可修改
	 * @param ls 沟通记录列表
	 * @param commuSource 沟通记录来源
	 * @param isTask 是否今日任务
	 */
	private void isEnable(List<CommuInfo> ls,String commuSource,String isTask){
		try {
			if(ls==null || ls.size()==0){
				return;
			}
			Calendar ca1 = Calendar.getInstance();
			Calendar ca2 = Calendar.getInstance();
			String t = null;
			Date d = null;
			String po = null;
			//今日任务
			if("1".equals(isTask)){
				for(CommuInfo c:ls){
					try {
						c.setIsEnable("0");//不可追加
						po = c.getIsPlaceOrder();
						//未成单
						if(!"1".equals(po)){
							c.setIsEnable("1");
						}
					} catch (Exception e) {
						c.setIsEnable("0");
						continue;
					}
				}
			}else if("TEL_OUT".equals(commuSource) && !"1".equals(isTask)){
				/* 去电组：来电新增，4-6天，属于去电组；去电新增，3天内可跟踪*/
				ca1.add(Calendar.DAY_OF_MONTH, -6);
				String sTime1 = df1.format(ca1.getTime());
				ca1.setTime(df1.parse(sTime1));
				ca2.add(Calendar.DAY_OF_MONTH, -4);
				String eTime1 = df1.format(ca2.getTime())+" 23:59:59";
				ca2.setTime(df.parse(eTime1));
				
				//去电沟通记录：去电后3天内
				Calendar ca3 = Calendar.getInstance();
				ca3.add(Calendar.DAY_OF_MONTH, -3);
				String sTime2 = df1.format(ca3.getTime());
				ca3.setTime(df1.parse(sTime2));
				Calendar ca4 = Calendar.getInstance();
				String eTime2 = df1.format(ca4.getTime())+" 23:59:59";
				ca4.setTime(df.parse(eTime2));
				
				String custSource = null;
				for(CommuInfo c:ls){
					c.setIsEnable("0");
					//1:已成单，不可追加跟踪信息
					po = c.getIsPlaceOrder();
					if(!"1".equals(po)){
						t = c.getCreateTime();
						d = df.parse(t);
						custSource = c.getCustSource();
						//去电沟通记录
						if("TEL_OUT".equals(custSource)){
							if(d.compareTo(ca3.getTime())>=0 && d.compareTo(ca4.getTime())<=0){
								c.setIsEnable("1");//1：可追加跟踪记录
							}
						}else{//来电
							if(d.compareTo(ca1.getTime())>=0 && d.compareTo(ca2.getTime())<=0){
								c.setIsEnable("1");
							}
						}
					}
				}
			}else{
//				//来电组，来电后从第二天开始数3天内，共4天可跟踪
//				ca1.add(Calendar.DAY_OF_MONTH, -3);
//				String sTime1 = df1.format(ca1.getTime());
//				ca1.setTime(df1.parse(sTime1));
//				String eTime1 = df1.format(ca2.getTime())+" 23:59:59";
//				ca2.setTime(df.parse(eTime1));
				String custSource = null;
				for(CommuInfo c:ls){
					//1:已成单，不可追加跟踪信息
					po = c.getIsPlaceOrder();
//					c.setIsEnable("0");
//					t = c.getCreateTime();
//					d = df.parse(t);
//					if("产品咨询".equals(c.getAcceptResult()) && !"1".equals(po) && 
//							d.compareTo(ca1.getTime())>=0 && d.compareTo(ca2.getTime())<=0){
//						c.setIsEnable("1");
//					}
					custSource = c.getCustSource();
					//1：成单，不可追加；去电组的沟通记录不可追加
					if("1".equals(po) || "TEL_OUT".equals(custSource)){
						c.setIsEnable("0");
					}else{
						c.setIsEnable("1");
					}
				}
			}
		} catch (Exception e) {
		}
	}
	
	//查询沟通记录详细信息
	@SuppressWarnings("unchecked")
	@RequestMapping(params = ("method=getCommuDetail"))
	@ResponseBody
	public String getCommuDetail(HttpServletRequest request, ModelMap modelMap, CommuInfo commuVO) {
		JSONObject json = new JSONObject();
		try {
			if (commuVO != null && commuVO.getCommuId() != null
					&& commuVO.getCommuId().trim().length() > 0) {
				PageModel<CommuInfo> pm = new PageModel<CommuInfo>();
				pm.setPageSize(0);
				//按ID查询沟通记录详细信息
				pm = commuService.getCommuLs(pm, commuVO);
				List<CommuInfo> ls = pm.getList();
				if (ls != null && ls.size() > 0) {
					if (ls.get(0) != null) {
						CommuInfo commu = (CommuInfo) ls.get(0);
						if("咨询".equals(commu.getAcceptResult()) || "产品咨询".equals(commu.getAcceptResult()) || "客户维护".equals(commu.getAcceptResult())){
							String isTask = request.getParameter("isTask");
							//判断沟通记录是否可修改
							this.isEnable(ls, commuVO.getCustSource(),isTask);
							
							Long id = Long.parseLong(commuVO.getCommuId());
							//按沟通记录ID查询跟踪信息
							List<TrackInfo> trackInfoLs = commuService.getTrackLsByCommuId(id);
							if (trackInfoLs != null && trackInfoLs.size() > 0) {
								json.put("trackLs", trackInfoLs);
							}
						}else{
							commu.setIsEnable("1");
						}
						json.put("commu", commu);
					}else{
						json.put("mesg", "记录不存在！");
					}
				}
			}
		} catch (Exception e) {
			json.put("mesg", "error");
			logger.error("查询沟通记录异常");
		}
		return json.toString();
	}
	/**
	 * 来电弹屏-保存沟通记录
	 */
	@RequestMapping(params = ("method=saveCallCommu"))
	@ResponseBody
	public Map<String,String> saveCallCommu(HttpServletRequest request, HttpSession session, ModelMap modelMap, CommuInfo commuVO) {
		Map<String,String> map = new HashMap<String,String>();
		try {
			if(commuVO!=null){
				String uri = request.getRequestURI();
				String ac = uri.substring(uri.lastIndexOf("/")+1);
				//呼叫中心访问
				if("commu.do".equals(ac)){
					//客服工号
					Object code = commuVO.getCustServCode();
					commuVO.setCustServCode(code!=null?code.toString():null);
				}else{//运营中心后台访问
					//当前登录用户
					User user = getCurrentUser();
					commuVO.setCustServCode(user!=null?user.getUserName():null);
				}
				String keywords = commuVO.getProKeywords();
				String words1 = "";
				String words2 = "";
				//手动输入各关键词去首尾空格
				if(keywords!=null && (keywords = keywords.trim()).length()>0){
					int index = keywords.indexOf("{;}");
					if(index>=0){
						//{;}后的部分是手动输入的，用“;”分隔；{;}前的部分是选择的商品
						words2 = keywords.substring(index+3);
						if(words2.length()>0){
							String[] split = words2.split(";");
							words2 = "";
							for(String word:split){
								word = word.trim();
								if(!"".equals(word)){
									words2 += word+";";
								}
							}
						}
						//搜索选择的关键词
						words1 = keywords.substring(0,index).trim();
					}else{
						words1 = keywords;
					}
				}
				if(words1.length()==0 && words2.length()==0){
					commuVO.setProKeywords(null);
				}else{
					commuVO.setProKeywords(words1+"{;}"+words2);
				}
				Communication commu = commuService.saveCommu(commuVO);
				map.put("commuId", commu.getId()+"");
				map.put("mesg", "success");
				return map;
			}
		} catch (Exception e) {
			logger.error("保存沟通记录异常！"+e.getMessage());
		}
		map.put("mesg", "error");
		return map;
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
	 * 获取所有产品分类
	 */
	@RequestMapping(params = ("method=getAllCatagory"))
	@ResponseBody
	public String getAllCatagory(HttpServletRequest request) {
		String updateFlag = request.getParameter("updateFlag");
		//updateFlag=1	立即更新产品分类
		if(!"1".equals(updateFlag)){
			if(frontClass!=null && frontClass.trim().length()>0){
				//分类信息每小时更新一次
				if(frontClassTime!=null && (System.currentTimeMillis()-frontClassTime.getTime()<60*60*1000)){
					//不需要更新
					return frontClass;
				}
			}
		}
		frontClassTime = new Date();
		try {
			SAXReader reader = new SAXReader();
			URL url = new URL(frontClassUrl);
			Document doc = reader.read(url);
			Element rootElt = doc.getRootElement();
			String responseCode = rootElt.elementTextTrim("response_code");
			if (!"1".equals(responseCode)) {
				logger.error("获取商品分类列表失败！responseCode:" + responseCode);
				return null;
			}
			Element data = rootElt.element("data");
			Element classItem = null;
			String className = null;
			JSONArray jArr = new JSONArray();
			JSONObject json = null;
			Iterator<?> iter = data.elementIterator("classs");
			// 遍历classs节点
			while (iter.hasNext()) {
				classItem = (Element) iter.next();
				className = classItem.elementTextTrim("className");
				if (className != null && !className.endsWith("_de")) {
					json = new JSONObject();
					json.put("id", classItem.elementTextTrim("id"));
					json.put("className", className);
					json.put("classCode",classItem.elementTextTrim("classCode"));
					json.put("classLevel",classItem.elementTextTrim("classLevel"));
					json.put("parentId", classItem.elementTextTrim("parentId"));
					json.put("parentName",classItem.elementTextTrim("parentName"));
					json.put("classDesc",classItem.elementTextTrim("classDesc"));
					jArr.add(json);
				}
			}
			frontClass = jArr.toString();
			return frontClass;
		} catch (Exception e) {
			logger.error("获取商品分类信息失败！",e);
			return null;
		}
	}
}
