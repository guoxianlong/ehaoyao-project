package com.ehaoyao.opertioncenter.reportForm.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehaoyao.opertioncenter.common.EntityUtil;
import com.ehaoyao.opertioncenter.common.ExcelUtil;
import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.reportForm.dao.IThirdOrderAuditReportDao;
import com.ehaoyao.opertioncenter.reportForm.service.IThirdOrderAuditReportService;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderDetail;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.OrderMainInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.ThirdOrderAuditVO;

@Service
public class ThirdOrderAuditReportServiceImpl implements IThirdOrderAuditReportService {

	@Autowired
	private IThirdOrderAuditReportDao iThirdOrderAuditReportDao;
	
	/**
	 * 订单查询
	 * @throws Exception 
	 */
	public PageModel<OrderMainInfo> getOrderInfos(PageModel<OrderMainInfo> pm,ThirdOrderAuditVO vo) throws Exception{
		if(pm.getPageSize()>0){
			int count = iThirdOrderAuditReportDao.getCountOrderInfos(vo);
			pm.setTotalRecords(count);
		}
		List<OrderMainInfo> ls = iThirdOrderAuditReportDao.getOrderInfos(pm,vo);
		pm.setList(ls);
		return pm;
	}
	
	/**
	 * 查询订单明细
	 */
	public List<OrderDetail> getOrderDetails(ThirdOrderAuditVO vo) throws Exception{
		return iThirdOrderAuditReportDao.getOrderDetails(vo);
	}

	@Override
	public HSSFWorkbook exportExcel(ThirdOrderAuditVO vo, HSSFWorkbook workbook2003) throws Exception {
		HSSFSheet sheet = workbook2003.createSheet("三方订单审核报表");
		//冻结
		sheet.createFreezePane(0, 4);
		//获取标题样式
		HSSFCellStyle titleStyle = ExcelUtil.getTitleCellStyle(workbook2003);
		//获取内容样式
		HSSFCellStyle contentStyle = ExcelUtil.getContentCellStyle(workbook2003);
		
		//合并单元格
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 31));

		HSSFRow row = null;
		
		//标题
		drawTitle(sheet,row,titleStyle);
		
		//查询条件
		drawCondition(sheet,row,titleStyle,vo);
		
		//订单列表
		drawContentTitleCell(sheet,row,titleStyle);
		
		
		//订单列表设置标题名称
		drawContentTitles(sheet,row,titleStyle);
		
		//订单内容
		drawContents(sheet,row,contentStyle,vo);
		
		return workbook2003;
	}
	
	
	@SuppressWarnings("rawtypes")
	private void drawContents(HSSFSheet sheet, HSSFRow row, HSSFCellStyle contentStyle, ThirdOrderAuditVO vo) throws Exception{
		List orderMainInfoList = iThirdOrderAuditReportDao.getOrderMainInfosbyConditions(vo);
		OrderMainInfo orderMainInfo = null;
		OrderDetail orderDetail = null;
		List<OrderDetail> orderDetailList = null;
		String phone = "";
		int k=4;
		if(orderMainInfoList.size()>0){
			for(int i=0;i<orderMainInfoList.size();i++){
				orderMainInfo = (OrderMainInfo) orderMainInfoList.get(i);
				vo.setOrderNumber(orderMainInfo.getOrderNumber());
				vo.setOrderFlag(orderMainInfo.getOrderFlag());
				orderDetailList = getOrderDetails(vo);
				for(int j=0;j<orderDetailList.size();j++){
					orderDetail = orderDetailList.get(j);
					row = sheet.createRow(k);
					row.setHeight((short)400);//目的是想把行高设置成400px
					int t=0;
					row.createCell(t++).setCellValue(orderMainInfo.getOrderNumber());
					row.createCell(t++).setCellValue(EntityUtil.getChannelName(orderMainInfo.getOrderFlag()));
					row.createCell(t++).setCellValue(orderMainInfo.getReceiver());
					
					if(orderMainInfo.getTelephone()!=null&&orderMainInfo.getTelephone().length()>4){
						phone = orderMainInfo.getTelephone().substring(0,3)+"****"+orderMainInfo.getTelephone().substring(8, 11);
					}else {
						if (orderMainInfo.getMobile()!=null&&orderMainInfo.getMobile().length()>4){
							phone = orderMainInfo.getMobile().substring(0,3)+"****"+orderMainInfo.getMobile().substring(8, 11);
						}
					}
					row.createCell(t++).setCellValue(phone);
					row.createCell(t++).setCellValue(orderMainInfo.getOrderTime());
					row.createCell(t++).setCellValue((orderDetail.getProductId()!=null&&orderDetail.getProductId().trim().length()>0)?orderDetail.getProductId():orderDetail.getMerchantId());
					row.createCell(t++).setCellValue(orderDetail.getProductName());
					row.createCell(t++).setCellValue(orderDetail.getProductSpec());
					row.createCell(t++).setCellValue(orderDetail.getProductBrand());
					row.createCell(t++).setCellValue(orderDetail.getProductLicenseNo());
					row.createCell(t++).setCellValue(orderDetail.getPharmacyCompany());
					row.createCell(t++).setCellValue(orderDetail.getCount());
					row.createCell(t++).setCellValue(orderDetail.getPrice()!=null?(orderDetail.getPrice().setScale(2,BigDecimal.ROUND_HALF_UP)+""):"");
					row.createCell(t++).setCellValue(orderDetail.getTotalPrice()!=null?(orderDetail.getTotalPrice().setScale(2,BigDecimal.ROUND_HALF_UP)+""):"");
					row.createCell(t++).setCellValue(orderMainInfo.getOrderPrice()!=null?(orderMainInfo.getOrderPrice().setScale(2,BigDecimal.ROUND_HALF_UP)+""):"");
					row.createCell(t++).setCellValue(vo.getPrescriptionTypeDesc(orderMainInfo.getPrescriptionType()+""));
					row.createCell(t++).setCellValue(vo.getPayStatusDesc(orderMainInfo.getPayStatus()));
					row.createCell(t++).setCellValue(vo.getAuditStatusDesc(orderMainInfo.getAuditStatus()));
					row.createCell(t++).setCellValue(orderMainInfo.getPrice()!=null?(orderMainInfo.getPrice().setScale(2,BigDecimal.ROUND_HALF_UP)+""):"");
					row.createCell(t++).setCellValue(orderMainInfo.getInvoiceStatusDesc());
					row.createCell(t++).setCellValue(orderMainInfo.getInvoiceTypeDesc());
					row.createCell(t++).setCellValue(orderMainInfo.getInvoiceTitle());
					row.createCell(t++).setCellValue(orderMainInfo.getInvoiceContent());
					row.createCell(t++).setCellValue(orderMainInfo.getRemark());
					row.createCell(t++).setCellValue(orderMainInfo.getKfAuditUser());
					row.createCell(t++).setCellValue(orderMainInfo.getKfAuditTime());
					row.createCell(t++).setCellValue(vo.getRejectTypeDesc(orderMainInfo.getKfRejectType()));
					row.createCell(t++).setCellValue(orderMainInfo.getKfAuditDescription());
					row.createCell(t++).setCellValue(orderMainInfo.getDoctorAuditUser());
					row.createCell(t++).setCellValue(orderMainInfo.getDoctorAuditTime());
					row.createCell(t++).setCellValue(vo.getRejectTypeDesc(orderMainInfo.getDoctorRejectType()));
					row.createCell(t++).setCellValue(orderMainInfo.getDoctorAuditDescription());
					k += 1;
					//订单内容设置样式
					for(int n=0;n<t;n++){
						row.getCell(n).setCellStyle(contentStyle);
					}
				}
			}
		}
	}

	private void drawContentTitles(HSSFSheet sheet, HSSFRow row, HSSFCellStyle titleStyle) {
		row = sheet.createRow(3);
		row.setHeight((short)400);//目的是想把行高设置成400px
		int i=0;
		row.createCell(i++).setCellValue("订单号");
		row.createCell(i++).setCellValue("渠道");
		row.createCell(i++).setCellValue("姓名");
		row.createCell(i++).setCellValue("电话");
		row.createCell(i++).setCellValue("订单日期");
		row.createCell(i++).setCellValue("商品编码");
		row.createCell(i++).setCellValue("商品名称");
		row.createCell(i++).setCellValue("规格");
		row.createCell(i++).setCellValue("品牌");
		row.createCell(i++).setCellValue("药品许可证号");
		row.createCell(i++).setCellValue("制药公司");
		row.createCell(i++).setCellValue("数量");
		row.createCell(i++).setCellValue("单价");
		row.createCell(i++).setCellValue("金额");
		row.createCell(i++).setCellValue("订单金额");
		row.createCell(i++).setCellValue("处方类型");
		row.createCell(i++).setCellValue("付款状态");
		row.createCell(i++).setCellValue("审核状态");
		row.createCell(i++).setCellValue("付款金额");
		row.createCell(i++).setCellValue("是否开票");
		row.createCell(i++).setCellValue("发票类型");
		row.createCell(i++).setCellValue("抬头信息");
		row.createCell(i++).setCellValue("发票内容");
		row.createCell(i++).setCellValue("备注");
		row.createCell(i++).setCellValue("一级审核人");
		row.createCell(i++).setCellValue("一级审核时间");
		row.createCell(i++).setCellValue("一级审核驳回类型");
		row.createCell(i++).setCellValue("一级审核说明");
		row.createCell(i++).setCellValue("二级审核人");
		row.createCell(i++).setCellValue("二级审核时间");
		row.createCell(i++).setCellValue("二级审核驳回类型");
		row.createCell(i++).setCellValue("二级审核说明");
		//订单列表标题设置样式
		for(int j=0;j<i;j++){
			row.getCell(j).setCellStyle(titleStyle);
		}
	}

	private void drawContentTitleCell(HSSFSheet sheet, HSSFRow row, HSSFCellStyle titleStyle) {
		HSSFCell contentTitleCell = null;
		row = sheet.createRow(2);
		row.setHeight((short)500);//目的是想把行高设置成500px
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 31));//合并单元格
		for(int i=0;i<32;i++){
			contentTitleCell = row.createCell(i);
			if(i==0){
				contentTitleCell.setCellValue("订单列表");
			}
			contentTitleCell.setCellStyle(titleStyle);
		}
	}

	private void drawTitle(HSSFSheet sheet,HSSFRow row, HSSFCellStyle titleStyle) {
		HSSFCell titleCell = null;
		row = sheet.createRow(0);
		row.setHeight((short)500);//目的是想把行高设置成500px
		for(int i=0;i<32;i++){
			sheet.setColumnWidth(i, 20*256);
			titleCell = row.createCell(i);
			if(i==0){
				titleCell.setCellValue("三方订单审核报表");
			}
			titleCell.setCellStyle(titleStyle);
		}
	}

	public void drawCondition(HSSFSheet sheet,HSSFRow row,HSSFCellStyle titleStyle,ThirdOrderAuditVO vo){
		row = sheet.createRow(1);
		row.setHeight((short)400);//目的是想把行高设置成400px
		
		HSSFCell conditionCell000 = row.createCell(0);
		conditionCell000.setCellValue("订单日期：");
		conditionCell000.setCellStyle(titleStyle);
		HSSFCell conditionCell001 = row.createCell(1);
		String date = "";
		if((vo.getStartDate()==null||vo.getStartDate().length()<=0) && (vo.getEndDate()==null||vo.getEndDate().length()<=0)){
			date = "全部";
		}
		if((vo.getStartDate()!=null&&vo.getStartDate().length()>0) && (vo.getEndDate()!=null&&vo.getEndDate().length()>0)){
			date = vo.getStartDate()+"至"+vo.getEndDate();
		}
		if((vo.getStartDate()==null||vo.getStartDate().length()<=0) && (vo.getEndDate()!=null&&vo.getEndDate().length()>0)){
			date = vo.getEndDate()+"之前";
		}
		if((vo.getStartDate()!=null&&vo.getStartDate().length()>0) && (vo.getEndDate()==null||vo.getEndDate().length()<=0)){
			date = vo.getStartDate()+"之后";
		}
		conditionCell001.setCellValue(date);
		conditionCell001.setCellStyle(titleStyle);
		
		HSSFCell conditionCell00 = row.createCell(2);
		conditionCell00.setCellValue("订单号：");
		conditionCell00.setCellStyle(titleStyle);
		HSSFCell conditionCell01 = row.createCell(3);
		conditionCell01.setCellValue(vo.getOrderNumber());
		conditionCell01.setCellStyle(titleStyle);
		
		HSSFCell conditionCell100 = row.createCell(4);
		conditionCell100.setCellValue("渠道：");
		conditionCell100.setCellStyle(titleStyle);
		HSSFCell conditionCell101 = row.createCell(5);
		conditionCell101.setCellValue(vo.getOrderFlagDesc(vo.getOrderFlag()));
		conditionCell101.setCellStyle(titleStyle);
		
		HSSFCell conditionCell200 = row.createCell(6);
		conditionCell200.setCellValue("审核状态：");
		conditionCell200.setCellStyle(titleStyle);
		HSSFCell conditionCell201 = row.createCell(7);
		conditionCell201.setCellValue(vo.getAuditStatusDesc(vo.getAuditStatus()));
		conditionCell201.setCellStyle(titleStyle);
		
		HSSFCell conditionCell300 = row.createCell(8);
		conditionCell300.setCellValue("驳回类型：");
		conditionCell300.setCellStyle(titleStyle);
		HSSFCell conditionCell301 = row.createCell(9);
		conditionCell301.setCellValue(vo.getRejectTypeDesc(vo.getRejectType()));
		conditionCell301.setCellStyle(titleStyle);
	}

}
