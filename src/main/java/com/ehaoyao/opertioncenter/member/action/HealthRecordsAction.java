package com.ehaoyao.opertioncenter.member.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehaoyao.opertioncenter.member.model.HealthKeywordDetail;
import com.ehaoyao.opertioncenter.member.model.HealthKeywordHead;
import com.ehaoyao.opertioncenter.member.model.MemberHealthKeyword;
import com.ehaoyao.opertioncenter.member.service.HealthKeywordDetailService;
import com.ehaoyao.opertioncenter.member.service.HealthKeywordHeadService;
import com.ehaoyao.opertioncenter.member.service.MemberHealthKeywordService;
import com.ehaoyao.opertioncenter.member.vo.HealthkeywordVO;
import com.haoyao.goods.action.BaseAction;

/**
 * 健康档案Action类
 * @author kxr
 *
 */
@Controller
@RequestMapping({"/healthRecords.do","/healthRecords2.do"})
public class HealthRecordsAction extends BaseAction {
	
	@Autowired
	private HealthKeywordHeadService healthKeywordHeadService;
	
	@Autowired
	private HealthKeywordDetailService healthKeywordDetailService;
	
	@Autowired
	private MemberHealthKeywordService memberHealthKeywordService;

	/**
	 * @param healthKeywordHeadService the healthKeywordHeadService to set
	 */
	public void setHealthKeywordHeadService(
			HealthKeywordHeadService healthKeywordHeadService) {
		this.healthKeywordHeadService = healthKeywordHeadService;
	}

	/**
	 * @param healthKeywordDetailService the healthKeywordDetailService to set
	 */
	public void setHealthKeywordDetailService(
			HealthKeywordDetailService healthKeywordDetailService) {
		this.healthKeywordDetailService = healthKeywordDetailService;
	}
	
	/**
	 * 查询健康档案列表
	 * @param modelMap
	 * @param tel
	 * @return
	 */
	@RequestMapping(params = ("method=getHealthKeywordHean"))
	public String getHealthKeywordHean(HttpServletRequest request,ModelMap modelMap ,String tel){	
		String uri = request.getRequestURI();
		String ac = uri.substring(uri.lastIndexOf("/")+1);
		modelMap.put("healAction", ac);
		
		List<HealthkeywordVO> healthHeadList = new ArrayList<HealthkeywordVO>();
		List<HealthKeywordDetail> healthDetail = new ArrayList<HealthKeywordDetail>();
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
		if(null != tel && !"".equals(tel)){
			List<HealthKeywordDetail> tempDetailList = healthKeywordDetailService.getMemberHealthKeywordByTel(tel);
			if(null != tempDetailList && tempDetailList.size()>0){
				StringBuffer pid = new StringBuffer();
				for (int i = 0; i < tempDetailList.size(); i++) {
					pid.append(tempDetailList.get(i).getId());
					if(i != (tempDetailList.size()-1)){
						pid.append(",");
					}
				}
				modelMap.put("tempDetailList", tempDetailList);
				modelMap.put("pid", pid);
			}
		}
		modelMap.put("healthList", healthHeadList);
		modelMap.put("tel", tel);
		return "opcenter/memberCenter/memberArchives/health_status";
	}
	
	/**
	 * 保存会员健康关键字
	 * @param request
	 * @return
	 */
	@RequestMapping(params= ("method=saveHealthRecords"))
	@ResponseBody
	public String saveHealthRecords(HttpServletRequest request){
		String pid = request.getParameter("detailId");
		String tel = request.getParameter("tel");
		String detailId[] = pid.split(",");
		MemberHealthKeyword memberHealth = null;
		memberHealthKeywordService.delMemberHealthKeyword(tel);
		for (int i = 0; i < detailId.length; i++) {
			memberHealth = new MemberHealthKeyword();
			memberHealth.setId(i);
			memberHealth.setPid(detailId[i]);
			memberHealth.setMid(tel);			
			memberHealthKeywordService.saveMemberHealthKeyword(memberHealth);
		}
		//return "forward:member.do?method=getMemberById&tel="+tel;
		return "1";
	}
	
}
