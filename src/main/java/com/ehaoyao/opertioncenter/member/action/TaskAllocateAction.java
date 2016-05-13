package com.ehaoyao.opertioncenter.member.action;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ehaoyao.opertioncenter.custServiceCenter.service.ReservationService;
import com.ehaoyao.opertioncenter.member.model.Member;
import com.ehaoyao.opertioncenter.member.service.MemberService;
import com.ehaoyao.opertioncenter.member.service.TaskAllocateService;
import com.ehaoyao.opertioncenter.member.vo.MemberVO;
import com.ehaoyao.opertioncenter.member.vo.OrderHouseVO;
import com.haoyao.goods.action.BaseAction;
import com.haoyao.goods.model.User;
import com.haoyao.goods.service.UserServiceImpl;

@Controller
@RequestMapping("/taskAllocate.do")
public class TaskAllocateAction extends BaseAction{
	@Autowired
	private TaskAllocateService taskAllocateService;
	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private ReservationService reservationService;
	
	//private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 打开
	 */
	@RequestMapping(params = ("method=task"))
	public String task(HttpServletRequest request){
		return "opcenter/memberCenter/task_allocate";
	}
	/**
	 * 查询客户信息
	 * @param modelMap
	 * @param request
	 * @param vo
	 * @param isCheck
	 * @return
	 */
	@RequestMapping(params = ("method=getInfo"))
	public String getInfo( ModelMap modelMap ,HttpServletRequest request,OrderHouseVO vo,String isCheck){
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
		recTotal = taskAllocateService.getOrderCount(vo);
		pageTotal = recTotal / this.getPageSize();
		pageTotal = recTotal % this.getPageSize() == 0 ? pageTotal : pageTotal + 1;
		if( this.getPageno() > pageTotal ){
			this.setPageno(1);
		}
		List<OrderHouseVO> taskList = taskAllocateService.getOrderList((this.getPageno() - 1) * this.getPageSize() , this.getPageSize(),vo);
		//request.getSession().setAttribute("vo", vo);
		modelMap.put("taskList", taskList);
		modelMap.put("pageno", this.getPageno());
		modelMap.put("pageTotal", pageTotal);
		modelMap.put("pageSize", this.getPageSize() );
		modelMap.put("recTotal", recTotal);
		modelMap.put("isCheck", isCheck);
		modelMap.put("vo",vo);
		return "opcenter/memberCenter/task_allocate";
	}
	/**
	 * 进入选择客服页面
	 */
	@RequestMapping(params = ("method=getUserList"))
	public void getUserList(HttpServletRequest request,HttpServletResponse response,User user) {
		try{
			PrintWriter out = response.getWriter();
			JSONObject object = new JSONObject();
			
			StringBuilder hqlString = new StringBuilder(100);
			if( user.getUserName() != null && !"".equals(user.getUserName().trim()) ){
				hqlString.append(" and (userName like '%" + user.getUserName().trim() + "%' or name like '%" + user.getUserName().trim() + "%') ");
			}
			user.setLockStatus(0);
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
			recTotal = userService.findUserCount(hqlString.toString());
			pageTotal = recTotal / this.getPageSize();
			pageTotal = recTotal % this.getPageSize() == 0 ? pageTotal : pageTotal + 1;
			if( this.getPageno() > pageTotal ){
				this.setPageno(1);
			}
			List<User> userList = userService.findUser((this.getPageno() - 1) * this.getPageSize(),recTotal,hqlString.toString());
			JSONArray userArray = JSONArray.fromObject(userList);
			object.put("userList", userArray);
			out.println(object.toString());
			out.flush(); 
		    out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 分配客服
	 */
	@RequestMapping(params = ("method=allotService"))
	public void allotService(HttpServletRequest request,HttpServletResponse response,String serIds,String tels) {
		try{
			PrintWriter out = response.getWriter();
			JSONObject object = new JSONObject();
			String message = "";
			try{
				if(serIds == null || "".equals(serIds.trim())){
					message = "选择的客服有误，请检查后继续！";
					return;
				}
				if(tels == null || "".equals(tels.trim())){
					message = "选择的会员有误，请检查后继续！";
					return;
				}
				String[] serIdArr = serIds.split("\\{&,");
				String[] telArr = tels.split("\\{&,");
				if(serIdArr == null || serIdArr.length <= 0){
					message = "选择的客服有误，请检查后继续！";
					return;
				}
				if(telArr == null || telArr.length <= 0){
					message = "选择的会员有误，请检查后继续！";
					return;
				}
				//计算需要分配多少次
				int telLen = telArr.length;
				int serLen = serIdArr.length;
				int count = telLen % serLen == 0 ? telLen / serLen : telLen / serLen + 1;
				//转换成List
				List<String> serList = Arrays.asList(serIdArr);
				List<String> telList = Arrays.asList(telArr);
				//调用shuffle方法 随机打乱list顺序
				Collections.shuffle(serList);
				taskAllocateService.updateUserName(serList, telList, count);
			}catch(Exception e){
				message = e.getMessage();
				if(message == null || "".equals(message)){
					message = "分配客服失败，请联系管理员！";
				}
				e.printStackTrace();
			}finally{
				object.put("message", message);
				out.println(object.toString());
				out.flush(); 
				out.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 批量分配客服
	 */
	@RequestMapping(params = ("method=batchAllotService"))
	public void batchAllotService(HttpServletRequest request,HttpServletResponse response,String serIds,MemberVO member) {
		try{
			PrintWriter out = response.getWriter();
			JSONObject object = new JSONObject();
			String message = "";
			try{
				if(serIds == null || "".equals(serIds.trim())){
					message = "选择的客服有误，请检查后继续！";
					return;
				}
				String[] serIdArr = serIds.split("\\{&,");
				if(serIdArr == null || serIdArr.length <= 0){
					message = "选择的客服有误，请检查后继续！";
					return;
				}
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
				recTotal = memberService.getMemberCount(hqlString);
				int pageSize = 6000;
				int page = recTotal % pageSize == 0 ? recTotal / pageSize:recTotal / pageSize + 1;
				List<String> serList = Arrays.asList(serIdArr);
				int count = 0;
				int memLen = 0;
				int serLen = 0;
				List<Member> memberList = null;
				for(int n = 0;n<page;n++){
					memberList = memberService.queryMemberList(n * pageSize , pageSize,hqlString);
					if(memberList == null || memberList.size() == 0){
						continue;
					}
					memLen = memberList.size();
					serLen = serIdArr.length;
					count = memLen % serLen == 0 ? memLen / serLen : memLen / serLen + 1;
					//调用shuffle方法 随机打乱list顺序
					Collections.shuffle(serList);
					taskAllocateService.updateBatchUserName(serList, memberList, count);
					/*for(int i=0;i<count;i++){
						//memberService.updateMemberByTel(serList, memberList, i);
						for(int j=1;j<=serList.size() && i * serList.size() + j<=memLen;j++){
							String tel = memberList.get(i * serList.size() + j-1).getTel();
							String cus = serList.get(j-1);
							memberService.updateMemberByTel(tel, cus);
						}
					}*/
				}
			}catch(Exception e){
				message = e.getMessage();
				if(message == null || "".equals(message)){
					message = "分配客服失败，请联系管理员！";
				}
				e.printStackTrace();
			}finally{
				object.put("message", message);
				out.println(object.toString());
				out.flush(); 
				out.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 分配任务
	 */
	@RequestMapping(params = ("method=allotTask"))
	public synchronized void allotTask(HttpServletRequest request,HttpServletResponse response,String serIds,String customers) {
		try{
			PrintWriter out = response.getWriter();
			JSONObject object = new JSONObject();
			String message = "";
			try{
				if(serIds == null || "".equals(serIds.trim())){
					message = "选择的客服有误，请检查后继续！";
					return;
				}
				if(customers == null || "".equals(customers.trim())){
					message = "选择的会员有误，请检查后继续！";
					return;
				}
				String[] serIdArr = serIds.split("\\{&,");
				String[] cusArr = customers.split("\\{&,");
				if(serIdArr == null || serIdArr.length <= 0){
					message = "选择的客服有误，请检查后继续！";
					return;
				}
				if(cusArr == null || cusArr.length <= 0){
					message = "选择的会员有误，请检查后继续！";
					return;
				}
				String taskDate = request.getParameter("taskDate");
				String ask = request.getParameter("ask");
				taskAllocateService.addTask(serIdArr, cusArr, ask, taskDate);
				/*int cusLen = 0;;
				int serLen = serIdArr.length;
				//计算需要分配多少次
				int count = 0;
				//转换成List
				List<String> serList = Arrays.asList(serIdArr);
				//List<Member> alltList = new ArrayList<Member>();//已分配过客服的
				List<String> noAlltList = new ArrayList<String>();//未分配过客服的
				//List<Member> newCusList = new ArrayList<Member>();//新客户
				Member m = null;
				Reservation re = null;
				for(int i=0;i<cusArr.length;i++){
					m = new Member();
					String [] arr = cusArr[i].split("}&");
					if(arr.length != 7){
						continue;
					}
					if("".equals(convertStr(arr[0]))){
						continue;
					}
					//是否新用户
					if("是".equals(convertStr(arr[5]))){
						m.setTel(convertStr(arr[0]));
						m.setMemberName(convertStr(arr[1]));
						m.setProvince(convertStr(arr[2]));
						m.setCity(convertStr(arr[3]));
						m.setCounty(convertStr(arr[4]));
						saveMember(m);
					}
					//是否已经分配过客服
					if(!"".equals(convertStr(arr[6]))){
						re = new Reservation();
						re.setComment(ask);//备注
						re.setCustServiceNo(convertStr(arr[6]));//客服编码
						re.setReserveTime(taskDate);//预约回访日期
						re.setTel(convertStr(arr[0]));//手机号
						re.setAcceptResult("客户维护");//沟通类型
						re.setCustSource("老客维护");//客户来源
						reservationService.savaReservat(re);
					}else{
						noAlltList.add(convertStr(arr[0]));
					}
				}
				//调用shuffle方法 随机打乱list顺序
				cusLen = noAlltList.size(); 
				count = cusLen % serLen == 0 ? cusLen / serLen : cusLen / serLen + 1;
				Collections.shuffle(serList);
				for(int i=0;i<count;i++){
					for(int j=1;j<=serList.size() && i*serList.size()+j<=noAlltList.size();j++){
						String tel = noAlltList.get(i * serList.size() + j-1);
						String cus = serList.get(j-1);
						memberService.updateMemberByTel(tel, cus);
						re = new Reservation();
						re.setComment(ask);//备注
						re.setCustServiceNo(cus);//客服编码
						re.setReserveTime(taskDate);//预约回访日期
						re.setTel(tel);//手机号
						re.setAcceptResult("客户维护");//沟通类型
						re.setCustSource("老客维护");//客户来源
						reservationService.savaReservat(re);
					}
				}
				//方案1
				//1.已经分配过客服的继续分配给原客服
				//2.记录分配给原客服今日任务的条数
				//3.如果已经分过的客服用未分配的平均数-2
				//存在问题：旗下客户数不平均
				//方案2
				//1.已经分配过客服的继续分配给原客服
				//2.把未分配过客服的客户平均分配给客服
				//存在问题：今日任务量不同，可能某个客服今日任务量比较大*/			
			}catch(Exception e){
				message = e.getMessage();
				if(message == null || "".equals(message)){
					message = "任务分配失败，请联系管理员！";
				}
				e.printStackTrace();
			}finally{
				object.put("message", message);
				out.println(object.toString());
				out.flush(); 
				out.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 批量分配任务
	 */
	@RequestMapping(params = ("method=batchAllotTask"))
	public synchronized void batchAllotTask(HttpServletRequest request,HttpServletResponse response,String serIds,OrderHouseVO vo) {
		try{
			PrintWriter out = response.getWriter();
			JSONObject object = new JSONObject();
			String message = "";
			try{
				if(serIds == null || "".equals(serIds.trim())){
					message = "选择的客服有误，请检查后继续！";
					return;
				}
				String[] serIdArr = serIds.split("\\{&,");
				if(serIdArr == null || serIdArr.length <= 0){
					message = "选择的客服有误，请检查后继续！";
					return;
				}
				List<String> serList = Arrays.asList(serIdArr);
				/*int serLen = serIdArr.length;
				int cusLen = 0;*/
				String taskDate = request.getParameter("taskDate");
				String ask = request.getParameter("ask");
				int pageSize = 6000;
				System.out.println("查询条数" + "");
				recTotal = taskAllocateService.getOrderCount(vo);
				int page = recTotal % pageSize == 0 ? recTotal / pageSize:recTotal / pageSize + 1;
				
				if( this.getPageno() > pageTotal ){
					this.setPageno(1);
				}
				taskAllocateService.addBatchTask(serList, ask, taskDate, page, pageSize, vo);
				
				
				/*List<OrderHouseVO> taskList = null;
				Member m = null;
				Reservation re = null;
				int count = 0;
				List<String> noAlltList = null;//未分配过客服的
				for(int n = 0;n<page;n++){
					taskList = taskAllocateService.getOrderList(n * pageSize , pageSize,vo);
					if(taskList == null || taskList.size() == 0){
						continue;
					}
					
					for(OrderHouseVO o:taskList){
						noAlltList = new ArrayList<String>();
						if(o == null){
							continue;
						}
						if(o.getTel() == null || "".equals(o.getTel().trim())){
							continue;
						}
						//新用户
						if(o.getIsExist() == null || "".equals(o.getIsExist().trim()) ){
							m = new Member();
							m.setTel(o.getTel());
							m.setMemberName(o.getName());
							m.setProvince(o.getProvince());
							m.setCity(o.getCity());
							m.setCounty(o.getCountry());
							saveMember(m);
						}
						//是否分配过客服
						if(o.getUserName() != null && !"".equals(o.getUserName().trim())){
							//已经分配过客服
							re = new Reservation();
							re.setComment(ask);//备注
							re.setCustServiceNo(o.getUserName());//客服编码
							re.setReserveTime(taskDate);//预约回访日期
							re.setTel(o.getTel());//手机号
							re.setAcceptResult("客户维护");//沟通类型
							re.setCustSource("老客维护");//客户来源
							reservationService.savaReservat(re);
						}else{
							//未分配客服
							noAlltList.add(o.getTel());
						}
						//调用shuffle方法 随机打乱list顺序
						Collections.shuffle(serList);
						cusLen = noAlltList.size();
						count = cusLen % serLen == 0 ? cusLen / serLen : cusLen / serLen + 1;
						for(int i=0;i<count;i++){
							for(int j=1;j<=serList.size() && i*serList.size()+j<=noAlltList.size();j++){
								String tel = noAlltList.get(i * serList.size() + j-1);
								String cus = serList.get(j-1);
								memberService.updateMemberByTel(tel, cus);
								//保存今日任务
								re = new Reservation();
								re.setComment(ask);//备注
								re.setCustServiceNo(cus);//客服编码
								re.setReserveTime(taskDate);//预约回访日期
								re.setTel(tel);//手机号
								re.setAcceptResult("客户维护");//沟通类型
								re.setCustSource("老客维护");//客户来源
								reservationService.savaReservat(re);
							}
						}
					}
				}*/
			}catch(Exception e){
				message = e.getMessage();
				if(message == null || "".equals(message.trim())){
					message = "分配任务失败，请联系管理员！";
				}
				e.printStackTrace();
			}finally{
				object.put("message", message);
				out.println(object.toString());
				out.flush(); 
				out.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
