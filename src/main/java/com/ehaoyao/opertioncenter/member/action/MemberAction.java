package com.ehaoyao.opertioncenter.member.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.ehaoyao.opertioncenter.custServiceCenter.util.PropertiesUtil;
import com.ehaoyao.opertioncenter.member.model.Member;
import com.ehaoyao.opertioncenter.member.service.MemberService;
import com.ehaoyao.opertioncenter.member.vo.MemberVO;
import com.haoyao.goods.action.BaseAction;
import com.haoyao.goods.model.User;
import com.haoyao.goods.service.UserServiceImpl;

/**
 * @author Administrator
 * 会员信息
 */
@Controller
@RequestMapping({"/member.do","/member2.do"})
public class MemberAction extends BaseAction{

	private static final Logger logger = Logger.getLogger(MemberAction.class);
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//官网地址
	private String officialUrl = PropertiesUtil.getProperties("extend.properties", "officialUrl");
	//获取官网会员信息接口标识
	private String accountToken = PropertiesUtil.getProperties("extend.properties", "accountToken");
	private String accountUUID = PropertiesUtil.getProperties("extend.properties", "accountUUID");
	
	@Autowired
	private MemberService memberService;
	
	//用户信息
	@Autowired
	private UserServiceImpl userService;

	/**
	 * @param memberService the memberService to set
	 */
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

	/**
	 * 条件查询会员信息
	 * @param modelMap
	 * @param request
	 * @param member
	 * @return
	 */
	@RequestMapping(params = ("method=getMemberList"))
	public String getMemberList( ModelMap modelMap ,HttpServletRequest request,MemberVO member){
		String hqlString = "";
		if(member.getMemberName() != null && !"".equals(member.getMemberName().trim())){
			hqlString += " and member_name = '" + member.getMemberName().trim() + "'";
		}
		if(member.getTel() != null && !"".equals(member.getTel().trim())){
			hqlString += " and tel = '" + member.getTel().trim() + "'";
		}
		if(member.getSex() != null && !"".equals(member.getSex().trim())){
			hqlString += " and sex = '" + member.getSex().trim() + "'";
		}
		if(member.getStartAge() != null && !"".equals(member.getStartAge().trim())){
			hqlString += " and age >= " + member.getStartAge().trim();
		}
		if(member.getEndAge() != null && !"".equals(member.getEndAge().trim())){
			hqlString += " and age <= " + member.getEndAge().trim();
		}
		if(member.getHealth() != null && !"".equals(member.getHealth().trim())){
			hqlString += " and health = '" + member.getHealth().trim() + "'";
		}
		if(member.getProvince() != null && !"".equals(member.getProvince().trim())){
			String province = member.getProvince().trim();
			if(member.getProvince().endsWith("省")){
				province = member.getProvince().trim().substring(0, member.getProvince().trim().length()-1);
			}
			hqlString += " and province like '" + province + "%'";
		}
		if(member.getMemberStatus() != null && !"".equals(member.getMemberStatus().trim())){
			hqlString += " and member_status = '" + member.getMemberStatus().trim() + "'";
		}
		if(member.getAttitude() != null && !"".equals(member.getAttitude().trim())){
			hqlString += " and attitude = '" + member.getAttitude().trim() + "'";
		}
		if(member.getIsAllot() == 1){
			hqlString += " and userName <> '' and userName is not null ";
		}else if(member.getIsAllot() == 2){
			hqlString += " and (userName = '' or userName is null) ";
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
			this.setPageSize(20);
		}else{
			this.setPageSize(Integer.parseInt(pageSize));
		}
		recTotal = memberService.getMemberCount(hqlString);
		pageTotal = recTotal / this.getPageSize();
		pageTotal = recTotal % this.getPageSize() == 0 ? pageTotal : pageTotal + 1;
		if( this.getPageno() > pageTotal ){
			this.setPageno(1);
		}
		List<Member> memberList = memberService.queryMemberList((this.getPageno() - 1) * this.getPageSize() , this.getPageSize(),hqlString);
		modelMap.put("memberList", memberList);
		modelMap.put("pageno", this.getPageno());
		modelMap.put("pageTotal", pageTotal);
		modelMap.put("pageSize", this.getPageSize() );
		modelMap.put("recTotal", recTotal);
		modelMap.put("member",member);
		//request.setAttribute("memberList", memberList);
		return "opcenter/memberCenter/member_query";
	}
	
	/**
	 * 根据id查询会员详情
	 * @param modelMap
	 * @param request
	 * @param member
	 * @return
	 */
	@RequestMapping(params = ("method=getMemberById"))
	public String getMemberById(HttpServletRequest request){
		String tel = request.getParameter("tel");
		Member member = new Member();
		if(tel != null && !"".equals(tel.trim())){
			member = memberService.queryMemberByTel(tel);
		}
		request.setAttribute("member", member);
		return "opcenter/memberCenter/memberArchives/member_archives";
	}
	
	/**
	 * 添加会员信息
	 * @param request
	 * @param member
	 * @return
	 */
	@RequestMapping(params = ("method=addMember"))
	public void addMember(HttpServletRequest request,Member member,PrintWriter printWriter){
		try {
			if(member!=null && member.getTel()!=null && member.getTel().trim().length()>0){
				String userName = null;
				String uri = request.getRequestURI();
				String ac = uri.substring(uri.lastIndexOf("/")+1);
				if("member.do".equals(ac)){//运营中心后台访问
					User user = this.getCurrentUser();
					if(user!=null){
						userName = user.getUserName();
					}
				}else{//呼叫中心访问
					Object obj = request.getSession().getAttribute("custServCode");
					if(obj!=null){
						userName = obj.toString();
					}
				}
				
				Member m = memberService.queryMemberByTel(member.getTel());
				if(m!=null){//修改
					m.setMemberName(member.getMemberName());
					m.setSex(member.getSex());
					m.setComment(member.getComment());
					//态度
					m.setAttitude(member.getAttitude());
					m.setEmail(member.getEmail());
//					m.setMemberGrade(member.getMemberGrade());
					String screenType = request.getParameter("screenType");
					if("1".equals(screenType)){//去电弹屏 ，修改地址信息
						m.setProvince(member.getProvince());
						m.setCity(member.getCity());
						m.setCounty(member.getCounty());
						m.setAddress(member.getAddress());
					}
					m.setUpdateTime(df.format(new Date()));
					m.setUpdateUser(userName);
					memberService.updateMember(m);
					printWriter.write("1");
				}else{
					member.setCreateTime(df.format(new Date()));
					member.setCreateUser(userName);
					Object result = memberService.addMember(member);
					if(result == null){
						printWriter.write("1");
					} else {
						printWriter.write("0");
					}		
				}
			}else{
				printWriter.write("0");
			}
		} catch (Exception e) {
			logger.error("会员信息保存失败：",e);
			printWriter.write("0");
		}
		printWriter.flush();
		printWriter.close();
	}
	
	/**
	 * 注册官网会员
	 * @param request
	 * @param member
	 * @return
	 */
	@RequestMapping(params=("method=registerMember"))
	public void registerMember(HttpServletRequest request,PrintWriter printWriter){
		String memberName = request.getParameter("memberName");
		String password = request.getParameter("password");
		String nickName = request.getParameter("nickName");
		String result = memberService.registerMember(nickName,memberName,password);
		printWriter.write(result);
		printWriter.flush();
		printWriter.close();
	}
	
	/**
	 * 获取官网会员信息
	 * @param modelMap
	 * @param request
	 * @return
	 */
	/*@RequestMapping(params=("method=getOfficalMember"))
	public String getOfficalMember(HttpServletRequest request,ModelMap modelMap,String memberName,String tel){
		String uri = request.getRequestURI();
		String ac = uri.substring(uri.lastIndexOf("/")+1);
		modelMap.put("memAction", ac);		
		Member member = memberService.getOfficialMember(memberName);
		if(null == member){
			member = memberService.getOfficialMember(tel);
		}
		modelMap.put("member", member);
		return "opcenter/custService/memberInfo";
	}*/
	
	//获取会员
	@RequestMapping(params = ("method=queryMember"))
	public void queryMember(HttpServletRequest request,HttpServletResponse response){
		String tel = request.getParameter("tel");
		Member member = new Member();
		String mesg=null;
		if(tel != null && !"".equals(tel.trim())){
			//官网会员级别
			String memberGrade = getOfficalMemberGrade(tel);
			member = memberService.queryMemberByTel(tel);
			JSONObject json = null;
			if(member!=null){
				json = JSONObject.fromObject(member);
			}else{
				if("非官网会员".equals(memberGrade)){
					memberGrade = "非会员";
				}
			}
			if(json==null){
				json = new JSONObject();
			}
			//会员级别
			json.put("memberGrade", memberGrade);
			mesg = json!=null?json.toString():null;
		}
		this.writeInfo(response, mesg);
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
	
	private void writeInfo(HttpServletResponse response,String mesg){
		//设置返回内容编码
        response.setContentType("text/text; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.println(mesg);
            out.close();
        } catch (Exception e) {
        }
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
