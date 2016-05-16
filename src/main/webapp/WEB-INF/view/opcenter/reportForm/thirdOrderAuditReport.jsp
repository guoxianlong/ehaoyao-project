<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1 maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>处方审核报表</title>
		
		<!--
        	作者：158822436@qq.com
        	时间：2016-01-13
        	描述：引入css样式
        -->
        <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-theme.css" rel="stylesheet">
	    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
	    <link href="<%=request.getContextPath()%>/css/bootstrap/jquery-bootstrap-select.css" rel="stylesheet">
		<link href="<%=request.getContextPath()%>/css/bootstrap/jquery.mCustomScrollbar.css" rel="stylesheet" />
		<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-lightbox.min.css" rel="stylesheet">
		<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
		<link href="<%=request.getContextPath()%>/css/opcenter/thirdOrderAudit/global.css" rel="stylesheet">
		<link href="<%=request.getContextPath()%>/css/opcenter/thirdOrderAudit/thirdOrderAudit.css" rel="stylesheet">
		<link href="<%=request.getContextPath()%>/css/opcenter/thirdOrderAudit/table.css" rel="stylesheet">
		
		<!--
        	作者：longshanw
        	时间：2016-01-13
        	描述：引入js脚本文件
        -->
		<script src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"></script>
	    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap.min.js"></script>
	    <script src="<%=request.getContextPath()%>/js/bootstrap/jquery-bootstrap-select.js"></script>
	    <script src="<%=request.getContextPath()%>/js/bootstrap/jquery.bootstrap.js"></script>
		<script src="<%=request.getContextPath()%>/bvalidator/js/jquery.bvalidator.js"></script>
		<script src="<%=request.getContextPath()%>/js/WdatePicker/WdatePicker.js"></script>
		<script src="<%=request.getContextPath()%>/js/bootstrap/jquery.mCustomScrollbar.concat.min.js" ></script>
		<script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap-lightbox.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/opcenter/thirdOrderAuditReport/thirdOrderAuditReport.js"></script>
		
	</head>
	
	<body style="min-height: 680px;">
		<h3 class="h3">处方审核报表</h3>
		<form id="orderOprForm"  method="post" role="form">
		<div id="conditionDiv" >
			<input type="hidden" id="curPage" name="pageno" value="${pageno}"/>
			<input type="hidden" id="curPageSize" name="pageSize" value="${pageSize}"/>
			<input type="hidden" id="totalRecords" name="totalRecords" value="${recTotal}"/>
			<input type="hidden" id="orderBy" name="orderBy" value="${vo.orderBy}"/>
			<input type="hidden" id="sort" name="sort" value="${vo.sort}"/>
			<input type="hidden" name="actionName" value="${actionName}"/>
			<input type="hidden" name="contextPath" value="<%=request.getRequestURL()%>"/>
			<fieldset class="fieldset">
				<legend class="legend"><span>查询条件</span></legend>
				<label><span>订单日期:</span></label>
				<input type="text" class="input" style="width: 100px;" id="startDate" name="startDate" value="${vo.startDate}" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'});" />
				<span>-</span>
				<input type="text" class="input" style="width: 100px;" id="endDate" name="endDate" value="${vo.endDate}" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" />
				
				<label><span>订单号：</span></label>
				<input type="text" id="orderNumber" class="input" name="orderNumber" value="${vo.orderNumber}" />
				 
				<label><span>渠道:</span></label>
				<select id="orderFlagSelect" name="orderFlag" class="selectpicker" data-hide-disabled="true" data-live-search="false">
				      <option value="-1" ${vo.orderFlag==''?"selected":''}  >请选择</option>
				      <option value="TMCFY" ${vo.orderFlag=='TMCFY'?"selected":''}>天猫处方药</option>
				      <option value="PACFY" ${vo.orderFlag=='PACFY'?"selected":''}>平安处方药</option>
				      <option value="yhdcfy" ${vo.orderFlag=='yhdcfy'?"selected":''}>1号店处方药</option>
				      <option value="KYW" ${vo.orderFlag=='KYW'?"selected":''}>快药处方药</option>
				      <option value="ZSTY" ${vo.orderFlag=='ZSTY'?"selected":''}>掌上糖医</option>
				      <option value="SLLCFY" ${vo.orderFlag=='SLLCFY'?"selected":''}>360健康处方药</option>
				  </select>
				  
				<label><span>审核状态:</span></label>
				<select id="auditStatusSelect" name="auditStatus" class="selectpicker" data-hide-disabled="true" data-live-search="false">
				    <!-- <optgroup label=">审核状态"> -->
				      <option value="-1" ${vo.auditStatus==''?"selected":''}  >请选择</option>
				      <option value="WAIT" ${vo.auditStatus=='WAIT'?"selected":''}>等待客服审核</option>
				      <option value="PRERETURN" ${vo.auditStatus=='PRERETURN'?"selected":''} >客服审核驳回</option>
				      <option value="PRESUCC" ${vo.auditStatus=='PRESUCC'?"selected":''} >等待药师审核</option>
				      <option value="SUCC" ${vo.auditStatus=='SUCC'?"selected":''} >药师审核通过</option>
				      <option value="RETURN" ${vo.auditStatus=='RETURN'?"selected":''} >药师审核驳回</option>
				    <!-- </optgroup> -->
				  </select>
				<br>
				<button id="exportBtn" class="button" type="button" >导出</button>
				<button id="queryBtn" class="button" type="button" >查询</button>
			</fieldset>
		</div>
		</form>
		 <div id="contentDiv" class="contentDiv">
		 	<div id="tableDiv">
		 		<table class="bordered">
		 			<caption>✦✦✦✦✦点击表格行可查看订单药品明细,点击标题可排序✦✦✦✦✦</caption>
		 			<thead>
		 				<tr>
		 					<th>序号</th>
		 					<th class="sortTh" id="order_number">订单号</th>
		 					<th class="sortTh" id="order_flag">渠道</th>
		 					<th class="sortTh" id="order_time">订单日期</th>
				 			<th class="sortTh" id="receiver">姓名</th>
				 			<th class="sortTh" id="telephone">电话</th>
				 			<th>地址</th>
				 			<th class="sortTh" id="order_price">订单金额(元)</th>
				 			<th class="sortTh" id="prescription_type">处方药</th>
				 			<th class="sortTh" id="pay_status">付款状态</th>
				 			<th class="sortTh" id="audit_status">审核状态</th>
				 			<th class="sortTh" id="price">付款金额(元)</th>
				 			<th class="sortTh" id="invoice_status">是否开票</th>
				 			<th class="sortTh" id="invoice_type">发票类型</th>
				 			<th>发票抬头</th>
				 			<th>发票内容</th>
				 			<th>备注</th>
				 			<th class="sortTh" id="kf_audit_user">一级审核人</th>
				 			<th class="sortTh" id="kf_audit_time">一级审核时间</th>
				 			<th>一级审核说明</th>
				 			<th class="sortTh" id="doctor_audit_user">二级审核人</th>
				 			<th class="sortTh" id="doctor_audit_time">二级审核时间</th>
				 			<th>二级审核说明</th>
		 				</tr>
		 			</thead>
					<tbody>
						<c:if test="${orderList != null && fn:length(orderList)>0}">
							<c:forEach items="${orderList}" var="items"
								varStatus="orderNumber">
								<tr class="orderInfoTr">
									<td style="vertical-align: middle;">
										${(pageno-1)*pageSize+orderNumber.count}
									</td>
									<td style="vertical-align: middle;">${items.orderNumber}</td>
									<td style="display: none;">${items.orderFlag}</td>
									<td style="vertical-align: middle;">
										<c:if test="${items.orderFlag=='TMCFY'}">天猫处方药</c:if>
										<c:if test="${items.orderFlag=='PACFY'}">平安处方药</c:if>
										<c:if test="${items.orderFlag=='yhdcfy'}">1号店处方药</c:if>
										<c:if test="${items.orderFlag=='KYW'}">快药处方药</c:if>
										<c:if test="${items.orderFlag=='ZSTY'}">掌上糖医</c:if>
										<c:if test="${items.orderFlag=='SLLCFY'}">360健康处方药</c:if>  
									</td>
									<td style="vertical-align: middle;">${items.orderTime}</td>
									<td style="vertical-align: middle;">${items.receiver}</td>
									<td style="vertical-align: middle;" class="phoneCls">${(items.telephone==""||items.telephone==null)?items.mobile:items.telephone}</td>
									<td style="vertical-align: middle;">${items.province}${items.city}${items.area}${items.addressDetail}</td>
									<td style="text-align: right; vertical-align: middle;">
										<fmt:formatNumber value="${items.orderPrice!=null?items.orderPrice:0.00}" pattern="##.##" minFractionDigits="2"></fmt:formatNumber>
									</td>
									<td style="vertical-align: middle;">${items.pregnantFlag == '0'?'否':'是'}</td>
									<td style="vertical-align: middle;">
										<c:if test="${items.payStatus == 'NOPAY'}">未支付</c:if>
										<c:if test="${items.payStatus == 'PAID'}">已支付</c:if>
										<c:if test="${items.payStatus == 'REFUND'}">已退款</c:if>
									</td>
									<td style="vertical-align: middle;">
										<c:if test="${items.auditStatus == 'WAIT'}">等待客服审核</c:if>
										<c:if test="${items.auditStatus == 'PRERETURN'}">客服审核驳回</c:if> 
										<c:if test="${items.auditStatus == 'PRESUCC'}">等待药师审核</c:if>
										<c:if test="${items.auditStatus == 'SUCC'}">药师审核通过</c:if> 
										<c:if test="${items.auditStatus == 'RETURN'}">药师审核驳回</c:if>
									</td>
									<td style="vertical-align: middle;">
										<fmt:formatNumber value="${items.price!=null?items.price:0.00}" pattern="##.##" minFractionDigits="2"></fmt:formatNumber>
									</td>
									<td style="vertical-align: middle;">${items.invoiceStatus=="1"?"是":"否"}</td>
									<td style="vertical-align: middle;">
										<c:if test="${items.invoiceType == 'PLAIN'}">普通发票</c:if>
										<c:if test="${items.invoiceType == 'ELECTRONIC'}">电子发票</c:if>
										<c:if test="${items.invoiceType == 'VAT'}">增值税发票</c:if>
									</td>
									<td style="vertical-align: middle;">${items.invoiceTitle}</td>
									<td style="vertical-align: middle;">${items.invoiceContent}</td>
									<td style="vertical-align: middle;">${items.remark}</td>
									<td style="vertical-align: middle;">${items.kfAuditUser}</td>
									<td style="vertical-align: middle;">${items.kfAuditTime}</td>
									<td style="vertical-align: middle;">${items.kfAuditDescription}</td>
									<td style="vertical-align: middle;">${items.doctorAuditUser}</td>
									<td style="vertical-align: middle;">${items.doctorAuditTime}</td>
									<td style="vertical-align: middle;">${items.doctorAuditDescription}</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${orderList == null or fn:length(orderList)<=0}">
						    <tr><th colspan="23" style="background-color:#ccc;font-size: 14px;color: #fa0;">暂无数据...</th></tr>
						</c:if>
					</tbody>
		 		</table>
				 	<div class="page">
					 	<c:if test="${orderList != null && fn:length(orderList)>0}">
							<ul class="pager page">
								<%@ include file="/WEB-INF/view/common/page.jspf" %>
							</ul>
						</c:if>
					</div>
		 	</div>
			<div id="orderDetailDiv" class="orderDetailDiv">
			    <table id="orderDetailsTable" class="bordered">
			    <caption>商品明细：</caption>
					<tr>
						<th>商品编码</th>
						<th>商品名称</th>
						<th>规格</th>
						<th>品牌</th>
						<th>药品许可证号</th>
						<th>制药公司</th>
						<th>单价</th>
						<th>数量</th>
						<th>金额</th>
						<th>是否赠品</th>
						<th>是否处方药</th>
					</tr>
			    </table>
			</div>
		 </div>
	

	</body>
</html>
