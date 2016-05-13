package com.ehaoyao.opertioncenter.doctorInHospital.action;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import oracle.jdbc.driver.DMSFactory;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ehaoyao.opertioncenter.custServiceCenter.util.Trans2PinYinUtil;
import com.ehaoyao.opertioncenter.doctorInHospital.model.DoctorModel;
import com.ehaoyao.opertioncenter.doctorInHospital.model.DoctorUrlModel;
import com.ehaoyao.opertioncenter.doctorInHospital.model.SalesRepModel;
import com.ehaoyao.opertioncenter.doctorInHospital.service.DoctorService;
import com.ehaoyao.opertioncenter.doctorInHospital.service.DoctorUrlService;
import com.ehaoyao.opertioncenter.doctorInHospital.service.SalesRepService;
import com.ehaoyao.opertioncenter.doctorInHospital.util.QuickResponseCodeUtil;
import com.haoyao.goods.action.BaseAction;

@Controller
@RequestMapping("/doctorInHospital.do")
public class DoctorInHospitalAction extends BaseAction{
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private DoctorUrlService doctorUrlService;

	@Autowired
	private SalesRepService salesRepService;
	
	
	private static final Logger logger = Logger.getLogger(DoctorInHospitalAction.class);
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//------------------------------------------------------销售代表后台------------------------------------------------------
	/**
	 * 添加销售代表页面跳转
	 * @return 
	 * 添加销售代表页面
	 */
	@RequestMapping(params = ("method=addSalesRep"))
	public String addSalesRep(){
		return "opcenter/doctorInHospital/add_sales_rep";
	}
	
	/**
	 * 保存销售代表信息
	 * @param salesRepModel
	 * @return 
	 * 展示所有销售代表
	 */
	@RequestMapping(params = ("method=saveSalesRep"))
	public String saveSalesRep(SalesRepModel salesRepModel,HttpServletRequest request,ModelMap modelMap){
		logger.info("-------保存销售代表信息-----------");
		salesRepModel.setCreateTime(format.format(new Date()));
		
		String hospitalName = "";
		if(null != salesRepModel){
			hospitalName = salesRepModel.getHospitalName();
		}
		//添加医院名称的时候存医院名称对应的拼音全拼
		salesRepModel.setHospitalNamePinYin(Trans2PinYinUtil.getInstance().convertAll(hospitalName));
		salesRepService.save(salesRepModel);
		return showAllSalesRep(request,modelMap,salesRepModel);
	}
	
	/**
	 * 展示所有销售代表信息
	 * @param request
	 * @param modelMap
	 * @param salesRepModel
	 * @return
	 */
	@RequestMapping(params = ("method=showAllSalesRep"))
	public String showAllSalesRep(HttpServletRequest request,ModelMap modelMap,SalesRepModel salesRepModel){
		logger.info("-------展示销售代表信息-----------");
		StringBuilder hqlString = new StringBuilder(100);
		hqlString.append("FROM SalesRepModel srm");
		String searchName=request.getParameter("searchName");
		if(null!=searchName  && !"".equals(searchName)){
			hqlString.append("  WHERE srm.hospitalName like '%"+searchName+"%'");
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
		Integer recTotal=salesRepService.queryAll().size();
		pageTotal = recTotal / this.getPageSize();
		pageTotal = recTotal % this.getPageSize() == 0 ? pageTotal : pageTotal + 1;
		if( this.getPageno() > pageTotal ){
			this.setPageno(1);
		}
		logger.info(hqlString.toString());
		List<SalesRepModel> salesRepList = salesRepService.queryHql(hqlString.toString(),null,(this.getPageno() - 1) * this.getPageSize(),this.getPageSize());
		modelMap.put("salesRepList", salesRepList);
		modelMap.put("pageno", this.getPageno());
		modelMap.put("pageTotal", pageTotal);
		modelMap.put("pageSize", this.getPageSize() );
		modelMap.put("recTotal", recTotal);
		modelMap.put("salesRepModel",salesRepModel );
		return "opcenter/doctorInHospital/showAllSalesRep";
	}
	
	/**
	 * 跳转修改医院
	 * @param request
	 * @param modelMap
	 * @param salesRepModel
	 * @return
	 */
	@RequestMapping(params="method=updateSalesRep")
	public String updateSalesRep(HttpServletRequest request,ModelMap modelMap,SalesRepModel salesRepModel){
		logger.info("-------修改医院信息-----------");
		String reqId=request.getParameter("reqId");
		salesRepModel=salesRepService.get(Integer.valueOf(reqId));
		modelMap.put("salesRepModel",salesRepModel);
		return "opcenter/doctorInHospital/update_sales_rep";
	}
	/**
	 * 修改医院信息
	 * @param request
	 * @param modelMap
	 * @param salesRepModel
	 * @return
	 */
	@RequestMapping(params="method=saveUpdateSalesRep")
	public String saveUpdateSalesRep(HttpServletRequest request,ModelMap modelMap,SalesRepModel salesRepModel){
		salesRepModel.setUpdateTime(format.format(new Date()));
		salesRepService.update(salesRepModel);
		return showAllSalesRep(request,modelMap,salesRepModel);
	}
	/**
	 * 
	* @Description:更新医院状态,更新前根据关联医生的状态，做相应处理。如（存在关联医生的状态有效,则医院不能更新为失效）
	* @param @param request
	* @param @param modelMap
	* @param @param salesRepModel
	* @param @return
	* @return String
	* @throws
	 */
	@RequestMapping(params="method=updateStatusSalesRep")
	public String updateStatusSalesRep(HttpServletRequest request,PrintWriter printWriter,ModelMap modelMap,SalesRepModel salesRepModel){
		logger.info("-------更新医院状态-----------");
		//获取传过来的id 
		int salesRepId = salesRepModel.getId();
		//通过id获取数据库中的对象
		SalesRepModel salesRepModelNew = salesRepService.get(salesRepId);
		//获取传过来的状态
		String status=salesRepModel.getStatus();
		//更新状态    0：失效    1 ：正常
		salesRepModelNew.setStatus(status);
		//更新时间
		salesRepModelNew.setUpdateTime(format.format(new Date()));
		//请求的反馈信息
		StringBuilder returnMsg=new StringBuilder();
		try {			
			//如果将医院信息状态更新为0，则看他关联的医生状态做判断，如果存在有效状态的关联医生,则将对医院状态不做更改,并将有效的医生id和名字记录
			//如果更新为1，则直接更新医院的状态为“1”。
			if("0".equals(status)){
				//获取状态为有效，关联此医院的医生集合
				StringBuilder hqlString = new StringBuilder(100);
				hqlString.append("FROM DoctorModel dm");
				hqlString.append(" WHERE dm.salesRepId="+salesRepId+" and dm.status=1");
				List<DoctorModel> doctorModelList = doctorService.queryHql(hqlString.toString(), null);
				if(doctorModelList.size()!=0){
					returnMsg.append("更新失效状态失败!\n原因：部分关联该医院的医生未失效!\n未失效的医生:\n");
					for (DoctorModel doctorModel : doctorModelList) {
						returnMsg.append("{id="+doctorModel.getId()+",名字="+doctorModel.getDoctorName()+"}\n");
					}
					//如果关联医院的医生均失效，则将该医院状态更新为无效
				}else{
					//将医院信息对象更新到数据
					salesRepService.update(salesRepModelNew);
					returnMsg.append("更新失效状态成功！");
				}			
			}else {
				//将医院信息对象更新到数据
				salesRepService.update(salesRepModelNew);
				returnMsg.append("更新有效状态成功！");
			}		
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.append("更新医院状态出错,请联系开发人员！");
			logger.error("更新医院状态出错", e);
		}
		printWriter.write(returnMsg.toString());
		printWriter.flush();
		printWriter.close();
		return null;
	}
	
	// ------------------------------------------------------医生后台------------------------------------------------------
	
	/**
	 * 添加医生页面跳转
	 * @return 
	 * 添加医生代表页面
	 */
	@RequestMapping(params = ("method=addDoctor"))
	public String addDoctor(ModelMap modelMap){
		//获取状态 status为  "1" 的医院信息集合
		String hql ="FROM SalesRepModel srm WHERE srm.status = 1";
		List<SalesRepModel> salesRepList=salesRepService.queryHql(hql,null);
		modelMap.put("salesRepList", salesRepList);
		return "opcenter/doctorInHospital/add_doctor";
	}
	
	
	
	/**
	 * 保存医生信息
	 * @param doctorModel
	 * @return 
	 * 展示所有医生
	 */
	@RequestMapping(params = ("method=saveDoctor"))
	public String saveDoctor(HttpServletRequest request,ModelMap modelMap,DoctorModel doctorModel){
		logger.info("-------保存医生信息-----------");
		doctorModel.setCreateTime(format.format(new Date()));
		String saleSepId=request.getParameter("orgId");
		logger.info(saleSepId);		
		String doctorName = "";
		if(null != doctorModel){
			doctorName = doctorModel.getDoctorName();
		}
		//添加医院名称的时候存医院名称对应的拼音全拼
		doctorModel.setDoctorNamePinYin(Trans2PinYinUtil.getInstance().convertAll(doctorName));
		doctorModel.setSalesRepId(saleSepId);
		doctorService.save(doctorModel);
		return showAllDoctor(request,modelMap,doctorModel);
	}
	
	/**
	 * 展示所有医生信息
	 * @param request
	 * @param modelMap
	 * @param doctorModel
	 * @return
	 */
	@RequestMapping(params = ("method=showAllDoctor"))
	public String showAllDoctor(HttpServletRequest request,ModelMap modelMap,DoctorModel doctorModel){
		logger.info("-------展示医生信息-----------");
		StringBuilder hqlString = new StringBuilder(100);
		hqlString.append("FROM DoctorModel dm");
		String searchName=request.getParameter("searchName");
		if(null!=searchName  && !"".equals(searchName)){
			hqlString.append(" WHERE dm.doctorName like '%"+searchName+"%'");
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
		Integer recTotal=doctorService.queryAll().size();
		pageTotal = recTotal / this.getPageSize();
		pageTotal = recTotal % this.getPageSize() == 0 ? pageTotal : pageTotal + 1;
		if( this.getPageno() > pageTotal ){
			this.setPageno(1);
		}
		logger.info(hqlString.toString());
		List<DoctorModel> doctorRepList = doctorService.queryHql(hqlString.toString(),null,(this.getPageno() - 1) * this.getPageSize(),this.getPageSize());
		//获取状态 status为  "1" 的医院信息集合
		String hql ="FROM SalesRepModel srm WHERE srm.status = 1";
		List<SalesRepModel> salesRepList=salesRepService.queryHql(hql,null);
		modelMap.put("salesRepList", salesRepList);
		modelMap.put("doctorRepList", doctorRepList);
		modelMap.put("pageno", this.getPageno());
		modelMap.put("pageTotal", pageTotal);
		modelMap.put("pageSize", this.getPageSize() );
		modelMap.put("recTotal", recTotal);
		modelMap.put("doctorModel",doctorModel );
		return "opcenter/doctorInHospital/doctor";
	}
	
	
	/**
	 * 修改医生跳转
	 * @param request
	 * @param modelMap
	 * @param doctorModel
	 * @return
	 */
	@RequestMapping(params="method=updateDoctor")
	public String updateDoctor(HttpServletRequest request,ModelMap modelMap,DoctorModel doctorModel){
		logger.info("-------修改医生信息-----------");
		String reqId=request.getParameter("reqId");
		doctorModel=doctorService.get(Integer.valueOf(reqId));
		List<SalesRepModel> salesRepList=salesRepService.queryAll();
		modelMap.put("salesRepList", salesRepList);
		modelMap.put("doctorModel",doctorModel);
		return "opcenter/doctorInHospital/update_doctor";
	}
	/**
	 * 修改医生信息
	 * @param request
	 * @param modelMap
	 * @param doctorModel
	 * @return
	 */
	@RequestMapping(params="method=saveUpdateDoctor")
	public String saveUpdateDoctor(HttpServletRequest request,ModelMap modelMap,DoctorModel doctorModel){
		doctorModel.setUpdateTime(format.format(new Date()));
		String saleSepId=request.getParameter("orgId");
		logger.info(saleSepId);
		SalesRepModel salesRepModel=salesRepService.get(Integer.valueOf(saleSepId));
		logger.info(salesRepModel.getId());
		logger.info(doctorModel.toString());
		doctorModel.setSalesRepId(saleSepId);
		logger.info(doctorModel.toString());
		doctorService.update(doctorModel);
		return showAllDoctor(request,modelMap,doctorModel);
	}
	/**
	 * 
	* @Description：根据关联的医院和医生url,来更新医生状态
	* @param @param request
	* @param @param modelMap
	* @param @param doctorModel
	* @param @return
	* @return String
	* @throws
	 */
	@RequestMapping(params="method=updateStatusDoctor")
	public String updateStatusDoctor(HttpServletRequest request,PrintWriter printWriter,ModelMap modelMap,DoctorModel doctorModel){
		//获取传过来的id 
		int doctorId =  doctorModel.getId();
		//通过id获取数据库中的医生对象
		DoctorModel  doctorModelNew = doctorService.get(doctorId);
		//获取传过来的状态
		String status=doctorModel.getStatus();
		//更新状态    0：失效    1 ：正常
		doctorModelNew.setStatus(status);
		//更新时间
		doctorModelNew.setUpdateTime(format.format(new Date()));
		//请求的反馈信息
		StringBuilder returnMsg=new StringBuilder();
		try {			
			//如果将医生信息状态更新为0，则看他关联的医生url做判断，如果存在关联的医生url,则将对医生状态不做更改,并将医生url的id记录。反之直接更改
			//如果更新为1，关联的医院为无效,则医生状态不做更改。如果关联医院为有效，则更改。
			if("0".equals(status)){
				//获取状态为有效，关联此医生的医生url集合
				StringBuilder hqlString1 = new StringBuilder(100);
				hqlString1.append("FROM DoctorUrlModel dum");
				hqlString1.append(" WHERE dum.doctorId="+doctorId);
				List<DoctorUrlModel> doctorUrlModelList = doctorUrlService.queryHql(hqlString1.toString(), null);
				if(doctorUrlModelList.size()!=0){
					returnMsg.append("更新失效状态失败!\n原因：存在关联该医生的url!需先删除\n存在的关联医生url:\n序号：");
					for (DoctorUrlModel doctorUrlModel : doctorUrlModelList) {
						returnMsg.append(doctorUrlModel.getId()+",");
					}
					//删除最后一个逗号
					returnMsg.deleteCharAt(returnMsg.length()-1);
					//如果关联医院的医生均失效，则将该医院状态更新为无效
				}else{
					//将医生状态更新
					doctorService.update(doctorModelNew);
					returnMsg.append("更新失效状态成功！");
				}			
			}
			//如果更新为1，关联的医院为无效,则医生状态不做更改。如果关联医院为有效，则更改。
			if("1".equals(status)){
				//获取状态为0，关联该医生的医院集合
				StringBuilder hqlString2 = new StringBuilder(100);
				hqlString2.append("FROM SalesRepModel srm");
				hqlString2.append(" WHERE id="+doctorModelNew.getSalesRepId()+" and status=0");
				List<SalesRepModel> salesRepModelList = salesRepService.queryHql(hqlString2.toString(), null);
				//如果关联医院无效
				if (salesRepModelList.size()==1) {
					SalesRepModel salesRep=salesRepModelList.get(1);
					returnMsg.append("更新有效状态失败!\n原因：关联该医生的医院状态为无效!需先使关联医院失效\n关联医院信息:\n");
					returnMsg.append("{序号="+salesRep.getId()+",医院名称="+salesRep.getHospitalName()+"}");
					//如果关联该医生的医院为有效，则将该医院状态更新为有效
				}else{
					doctorService.update(doctorModelNew);
					returnMsg.append("更新有效状态成功！");
				}
			}				
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.append("更新医生状态出错,请联系开发人员！");
			logger.error("更新医生状态出错", e);
		}
		//将消息传回jsp页面
		printWriter.write(returnMsg.toString());
		printWriter.flush();
		printWriter.close();
		return null;		
	}
	
	
	//------------------------------------------------------医生url后台------------------------------------------------------
	
	
	/**
	 * 添加医生url页面跳转
	 * @return 
	 * 添加医生url页面
	 */
	@RequestMapping(params = ("method=addDoctorUrl"))
	public String addDoctorUrl(ModelMap modelMap,HttpServletRequest request){
		//获取状态 status为  "1" 的医生集合
		String hql ="FROM DoctorModel dm WHERE dm.status = 1";
		List<DoctorModel> doctorList = doctorService.queryHql(hql, null);
		JSONArray doctorListJson = JSONArray.fromObject(doctorList);
		modelMap.put("doctorList", doctorList);
		modelMap.put("doctorListJson", doctorListJson.toString());
		return "opcenter/doctorInHospital/add_doctor_url";
	}
	
	 
	
	
	/**
	 * 保存医生url信息
	 * @param doctorUrlModel
	 * @return 
	 * 展示所有医生url
	 */
	@RequestMapping(params = ("method=saveDoctorUrl"))
	public String saveDoctorUrl(HttpServletRequest request,ModelMap modelMap,DoctorUrlModel doctorUrlModel){
		logger.info("-------保存医生Url信息-----------");
		doctorUrlModel.setCreateTime(format.format(new Date()));
		String doctorId=request.getParameter("orgId");
		logger.info(doctorId);
//		DoctorModel doctorModel=doctorService.get(Integer.valueOf(doctorId));
		doctorUrlModel.setDoctorId(doctorId);
		doctorUrlService.save(doctorUrlModel);
		return showAllDoctorUrl(request,modelMap,doctorUrlModel);
	}
	
	
	/**
	 * 展示所有医生Url信息
	 * @param request
	 * @param modelMap
	 * @param doctorUrlModel
	 * @return
	 */
	@RequestMapping(params = ("method=showAllDoctorUrl"))
	public String showAllDoctorUrl(HttpServletRequest request,ModelMap modelMap,DoctorUrlModel doctorUrlModel){
		logger.info("-------展示医生url信息-----------");
		StringBuilder hqlString = new StringBuilder(100);
		hqlString.append("FROM DoctorUrlModel dm");
		String searchName=request.getParameter("searchName");
		if(null!=searchName  && !"".equals(searchName)){
			hqlString.append(" WHERE dm.doctorModel.doctorName like '%"+searchName+"%'");
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
		Integer recTotal=doctorUrlService.queryAll().size();
		pageTotal = recTotal / this.getPageSize();
		pageTotal = recTotal % this.getPageSize() == 0 ? pageTotal : pageTotal + 1;
		if( this.getPageno() > pageTotal ){
			this.setPageno(1);
		}
		List<DoctorModel> doctorList=doctorService.queryAll();
		List<DoctorUrlModel> doctorUrlList = doctorUrlService.queryHql(hqlString.toString(),null,(this.getPageno() - 1) * this.getPageSize(),this.getPageSize());
		modelMap.put("doctorList", doctorList);
		modelMap.put("doctorUrlList", doctorUrlList);
		modelMap.put("pageno", this.getPageno());
		modelMap.put("pageTotal", pageTotal);
		modelMap.put("pageSize", this.getPageSize() );
		modelMap.put("recTotal", recTotal);
		modelMap.put("doctorUrlModel",doctorUrlModel );
		return "opcenter/doctorInHospital/doctorUrl";
	}
	
	
	/**
	 * 修改医生url跳转
	 * @param request
	 * @param modelMap
	 * @param doctorModel
	 * @return
	 */
	@RequestMapping(params="method=updateDoctorUrl")
	public String updateDoctorUrl(HttpServletRequest request,ModelMap modelMap,DoctorUrlModel doctorUrlModel){
		logger.info("-------修改医生信息-----------");
		String reqId=request.getParameter("reqId");
		doctorUrlModel=doctorUrlService.get(Integer.valueOf(reqId));
		String hql ="FROM DoctorModel dm WHERE dm.status = 1";
		List<DoctorModel> doctorList = doctorService.queryHql(hql, null);
		modelMap.put("doctorList", doctorList);
		modelMap.put("doctorUrlModel",doctorUrlModel);
		return "opcenter/doctorInHospital/update_doctor_url";
	}
	
	
	/**
	 * 修改医生Url信息
	 * @param request
	 * @param modelMap
	 * @param doctorUrlModel
	 * @return
	 */
	@RequestMapping(params="method=saveUpdateDoctorUrl")
	public String saveUpdateDoctorUrl(HttpServletRequest request,ModelMap modelMap,DoctorUrlModel doctorUrlModel){
		doctorUrlModel.setUpdateTime(format.format(new Date()));
		doctorUrlService.update(doctorUrlModel);
		return showAllDoctorUrl(request,modelMap,doctorUrlModel);
	}
	
	
	
	/**
	 * 生成二维码
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(params = ("method=createQuickCode"))
	public String createQuickResponseCode(HttpServletRequest request,ModelMap modelMap) throws Exception{
		logger.info("-------生成二维码-----------");
		String doctorUrlId=request.getParameter("doctorUrlId");
		DoctorUrlModel doctorUrlModel=doctorUrlService.get(Integer.valueOf(doctorUrlId));
		doctorUrlModel.setUpdateTime(format.format(new Date()));
		StringBuffer quickResponseCodeUrl=new StringBuffer();
		quickResponseCodeUrl.append(doctorUrlModel.getUrl());
		quickResponseCodeUrl.append("&qRCodeId=");
		quickResponseCodeUrl.append(doctorUrlModel.getId());
		String qrurl=QuickResponseCodeUtil.encodeUrl(quickResponseCodeUrl.toString(),request);
		doctorUrlModel.setQuickResponseCodeUrl(qrurl);
		doctorUrlService.update(doctorUrlModel);
		return showAllDoctorUrl(request,modelMap,doctorUrlModel);
	}
	/**
	 * 将医生url记录导出到excel中
	* @Description:(这里用一句话描述这个方法的作用)
	* @param @param request
	* @param @param response
	* @param @param modelMap
	* @param @return
	* @param @throws Exception
	* @return String
	* @throws
	 */
	@RequestMapping(params = ("method=exportXlsDoctorUrl"))
	public String exportXlsDoctorUrl(HttpServletRequest request,HttpServletResponse response ,ModelMap modelMap) throws Exception{
		logger.info("-------医生二维码导出到excel-----------");
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 在工作表中创建一个sheet页，名叫医生二维码
		HSSFSheet sheet = workbook.createSheet("医生二维码");
		//设置该页的第一列单元格宽度
		sheet.setColumnWidth(0, 7132);
		// 创建该页的标题行
		HSSFRow headRow = sheet.createRow(0);
		// 创建该标题行的单元格，并填充内容
		headRow.createCell(0).setCellValue("二维码");
		headRow.createCell(1).setCellValue("医院名称");
		headRow.createCell(2).setCellValue("医生名称");
		String ids = request.getParameter("ids");
		String[] sIds = ids.split(",");
		 //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
		HSSFPatriarch patri = sheet.createDrawingPatriarch();
		//遍历对应id的医生url，并将数据写入到工作表中
		for (int i = 0; i < sIds.length; i++) {
			int id = Integer.valueOf(sIds[i]);
			DoctorUrlModel doctorUrlModel = doctorUrlService.get(id);
			//向下建议一行
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			//设置该行的单元格高度
			dataRow.setHeight((short) (150 * 20));
			//添加该行的单元格，并填充内容
			//1、二维码图片地址
			/*dataRow.createCell(0).setCellValue(doctorUrlModel.getQuickResponseCodeUrl());*/
			String doctorId_str = doctorUrlModel.getDoctorId();
			int doctorId= Integer.valueOf(doctorId_str);
			//关联的医生对象
			DoctorModel doctorModel=null;
			try {
				doctorModel = doctorService.get(doctorId);
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("该医生url找不到关联医生对象", e);
			}
			SalesRepModel salesRepModel=null;
			try {
				String salesRepId_str = doctorModel.getSalesRepId();
				int salesRepId= Integer.valueOf(salesRepId_str);
				//关联的医院对象
				 salesRepModel = salesRepService.get(salesRepId);				
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("该医生url关联的医生对象找不到关联医院", e);
			}
			//1、医院名称
			dataRow.createCell(1).setCellValue(salesRepModel.getHospitalName());
			//2、医生名称
			dataRow.createCell(2).setCellValue(doctorModel.getDoctorName());
			//3、传二维码图片到excel
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			// 将二维码图片写入流中
			try {
				String  path=request.getSession().getServletContext().getRealPath("/");
				File file = new File(path+"images/doctor/"+doctorUrlModel.getQuickResponseCodeUrl());
				BufferedImage bufferImg = ImageIO.read(file);
				ImageIO.write(bufferImg, "JPG", outStream);
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("将二维码图片写入流时出错", e);
			}
			// 利用HSSFPatriarch将图片写入EXCEL， anchor主要用于设置图片的属性
			HSSFClientAnchor anchor = new HSSFClientAnchor(5, 5, 5, 5,(short) 0, i+1, (short)1, i+2);
			//插入二维码图片
			patri.createPicture(anchor, workbook.addPicture(
			outStream.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
		}
		// 清空response  
		response.reset();  
		// 设置response的Header  
		response.addHeader("Content-Disposition", "attachment;filename=QuickResponseCode.xls");  
		OutputStream out = new BufferedOutputStream(response.getOutputStream());  
		response.setContentType("application/octet-stream");
		//将excel对象写入到浏览器
		workbook.write(out);  
		out.flush();  
		out.close(); 
		return	null;
	}	
}
