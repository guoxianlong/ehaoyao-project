package com.ehaoyao.opertioncenter.member.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ehaoyao.opertioncenter.custServiceCenter.util.PropertiesUtil;
import com.ehaoyao.opertioncenter.member.dao.MemberDao;
import com.ehaoyao.opertioncenter.member.model.Member;
import com.ehaoyao.opertioncenter.member.service.MemberService;
import com.haoyao.goods.util.ExtendParamUtil;
import com.haoyao.goods.util.SignUtils;

/**
 * @author Administrator
 * 会员信息
 */
@Service
public class MemberServiceImpl implements MemberService{
	private static final Logger logger = Logger.getLogger(MemberServiceImpl.class);
	//官网地址
	private String officialUrl = PropertiesUtil.getProperties("extend.properties", "officialUrl");
	@Autowired
	private MemberDao memberDao;

	/**
	 * @param memberDao the memberDao to set
	 */
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public List<Member> queryMemberList(int firstResult, Integer pageSize,
			String hqlString) {
		return memberDao.queryMemberList(firstResult, pageSize,hqlString);
	}

	public Member queryMemberByTel(String tel) {
		// TODO Auto-generated method stub
		return memberDao.queryMemberByTel(tel);
	}
	/**
	 * 定时获取订单用户
	 */
	public void saveMemberList() {
		try{
			logger.info("定时获取订单用户启动" + new Date());
			RestTemplate restTemplate = new RestTemplate();
			/**设置接口地址*/
			String restUri =ExtendParamUtil.MEMBERURL;
			/**获取当前时间*/
			Date now = new Date();
//			DateFormat df = DateFormat.getDateTimeInstance(); 
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String dateTime = df.format(now); 
			/**设定参数*/
	    	int j=0;
	    	int pageCount = 0;
	    	int pageSize = 300;
	    	if(ExtendParamUtil.MEMPAGESIZE != null && !"".equals(ExtendParamUtil.MEMPAGESIZE.trim())){
	    		pageSize = Integer.parseInt(ExtendParamUtil.MEMPAGESIZE);
	    	}
	    	/**判断时候计算pageNo*/
	    	boolean compute_mark = true;
	    	do{
	    		/**对请求加密*/
	    		Map<String, String> map = new HashMap<String, String>();
	        	map.put("endTime", dateTime);
	        	map.put("timeNum", ExtendParamUtil.TIMENUM);
	        	map.put("pageNo", String.valueOf(j));
	    		map.put("pageSize", String.valueOf(pageSize));
	        	HashMap<String, String> signMap = SignUtils.sign(map, ExtendParamUtil.MEMBERKEY);
	        	String sign = signMap.get("appSign");
	        	
	    		Map<String, String> variables = new HashMap<String, String>(); 
	    		variables.put("endTime", dateTime);
	    		variables.put("timeNum", ExtendParamUtil.TIMENUM);
	    		variables.put("sign", sign);
	    		variables.put("pageNo",String.valueOf(j));
	    		variables.put("pageSize", String.valueOf(pageSize));
	    		JSONObject obj = null;
	    		/**调用接口*/
	    		obj = restTemplate.getForObject(restUri,
	    				JSONObject.class, variables);
	    		String endTime = obj.getString("endTime");
	    		String timeNum = obj.getString("timeNum");
	    		String totalRecords = obj.getString("totalRecords");
	    		logger.info("本次查询订单总数" + totalRecords);
	    		if(totalRecords != null && !"".equals(totalRecords) && compute_mark){
	    			pageCount = Integer.parseInt(totalRecords)%pageSize == 0 ? Integer.parseInt(totalRecords)/pageSize : Integer.parseInt(totalRecords)/pageSize + 1;
	    			compute_mark = false;
	    		}
	    		/**判断返回数据是否为请求的数据*/
	    		if(endTime != null && !dateTime.equals(endTime) && endTime != null && !timeNum.equals(timeNum)){
	    			return;
	    		}
	    		/**获得订单客户列表*/
	    	    String user = obj.getString("userList");
	    	    JSONArray userArr=JSONArray.fromObject(user);
	    	    Member member = null;
	    		JSONObject param = null;
	    		/**遍历数组，把每个订单用户进行持久化（有则更新，无则插入）*/
	    		for(int i=0 ; i < userArr.size();i++){
	    		    //获取每一个JsonObject对象
	    			param = (JSONObject)userArr.getJSONObject(i);
	    		    String memberName = param.getString("personName");
	    		    String tel = param.getString("tel");
	    		    if(tel == null || "".equals(tel.trim())){
	    		    	continue;
	    		    }
	    		    String address = param.getString("address");
	    		    String province = param.getString("province");
	    		    String city = param.getString("city");
	    		    String county = param.getString("county");
	    		    member = new Member();
	    		    member.setMemberName(memberName);
	    		    member.setTel(tel);
	    		    member.setAddress(address);
	    		    member.setProvince(province);
	    		    member.setCity(city);
	    		    member.setCounty(county);
	    		    memberDao.addMember(member);
	    		}
	    		j++;
	    	}while(j<pageCount);
	    	logger.info("定时获取订单用户结束" + new Date());
		}catch(Exception e){
			logger.error("定时获取订单用户信息失败：", e);
		}
	}
	/**
	 * 添加会员信息
	 */
	@Override
	public Object addMember(Member member) {
		return memberDao.addMember(member);
	}

	/**
	 * 注册官网会员
	 */
	@Override
	public String registerMember(String nickName,String memberName,String password) {
		RestTemplate restTemplate = new RestTemplate();
        String restUri =officialUrl + "/v1/user/account/register.jsonp?token={token}&uuid={uuid}&nkname={nkname}&username={username}&pswd={pswd}&cpswd={cpswd}&apid={apid}";
		Map<String, String> variables = new HashMap<String, String>();
		variables.put("token", "2014call15center08");//验证串
		variables.put("uuid", "callcenter");//分配给接入应用的唯一ID
		variables.put("nkname", nickName);//昵称
		variables.put("username", memberName);//用户账号
		variables.put("pswd", password);//密码
		variables.put("cpswd", password);//确认密码
		variables.put("apid", "4");//平台id
		/* {"response":{"tip":"请求处理失败：该手机号码已经被验证过","code":0}}
		 * code=1:成功
		 */
		String response = restTemplate.getForObject(restUri,String.class,variables);
		logger.info(memberName+", "+response);
		JSONObject jsonObject = JSONObject.fromObject(response);
		String result = jsonObject.getString("response");
		return result;
	}

	/**
	 * 获得会员总条数
	 */
	@Override
	public int getMemberCount(String hqlString) {
		return  memberDao.getMemberCount(hqlString);
	}

	@Override
	public void updateMember(Member member) {
		memberDao.updateMember(member);
	}
	/**
	 * 分配客服
	 */
	@Override
	public void updateMemberByTel(String tel,String userName) {
		memberDao.updateMemberByTel(tel, userName);
	}
	//@Override
	/*public void updateMemberByTel(List<String> cusList,List<Member> memberList,int i) {
		memberDao.updateMemberByTel(cusList, memberList,i);
	}*/
	
}
