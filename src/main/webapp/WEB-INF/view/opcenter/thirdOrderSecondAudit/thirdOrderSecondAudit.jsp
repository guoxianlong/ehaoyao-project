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
		<title>药师二级审核</title>
		
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
		<script src="<%=request.getContextPath()%>/js/opcenter/thirdOrderSecondAudit/thirdOrderSecondAudit.js"></script>
		
	</head>
	
	<body style="min-height: 680px;">
		<!-- 遮盖 -->
		<div id='loading' style="display:none;">
			<div class='loaded'>
				<div class='load'></div>
			</div>
		</div>
		<h3 class="h3">药师二级审核</h3>
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
				      <option value="PRESUCC" ${vo.auditStatus=='PRESUCC'?"selected":''} >等待审核</option>
				      <option value="SUCC" ${vo.auditStatus=='SUCC'?"selected":''} >审核通过</option>
				      <option value="RETURN" ${vo.auditStatus=='RETURN'?"selected":''} >审核驳回</option>
				    <!-- </optgroup> -->
				  </select>
				<br>
				<button id="batchAuditBtn" class="button" type="button" >批量审核通过</button>
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
		 					<th><input type="checkbox" id="selAll" name="selAll">序号</th>
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
				 			<th>订单备注</th>
				 			<th>操作</th>
		 				</tr>
		 			</thead>
					<tbody>
						<c:if test="${orderList != null && fn:length(orderList)>0}">
							<c:forEach items="${orderList}" var="items"
								varStatus="orderNumber">
								<tr class="orderInfoTr">
									<td style="vertical-align: middle;" >
										<input type="checkbox" name="selItem" value="${items.orderNumber},${items.orderFlag}" ${items.auditStatus!="PRESUCC"?"disabled":""} >
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
										<c:if test="${items.auditStatus == 'PRESUCC'}">等待审核</c:if> 
										<c:if test="${items.auditStatus == 'SUCC'}">审核通过</c:if>
										<c:if test="${items.auditStatus == 'RETURN'}">审核驳回</c:if>
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
									<td class="oprBtn">
				 						<button id="oprBtn" onclick="orderDetailsPageView('${items.orderNumber}','${items.orderFlag}');" class="button">${items.auditStatus!='PRESUCC'?"查看":"审核"}</button>
									</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${orderList == null or fn:length(orderList)<=0}">
						    <tr><th colspan="18" style="background-color:#ccc;font-size: 14px;color: #fa0;">暂无数据...</th></tr>
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
	 <!-- 订单详情 -->
    <div class="modal fade" id="orderDetailPageModal" tabindex="-1" role="dialog" aria-labelledby="detailsModalLabel" style="margin-top: 0px;" aria-hidden="true">
		<div class="modal-dialog" style="width: 950px;height: 300px;">
			<div class="modal-content">
				<div class="modal-header" style="padding-top:5px;padding-bottom:0px;margin-bottom:0px;height:35px;background-color: #51ABD9;">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
					</button>
					<h5 class="modal-title" style="color:white;text-align: center;" id="detailsModalLabel">
		               	订单详情
		            </h5>
				</div>
					<div class="modal-body" style="10px">
							<label class="label">订单号：<span id="orderNumberDetailPage"></span></label>
							<label class="label" style="display: none;">渠道：<span id="orderFlagDetailPage"></span></label>
							<label class="label">提交日期：<span id="orderTimeDetailPage"></span></label>
							<label class="label">订单总金额：<span id="orderPriceDetailPage"></span></label>
						<fieldset class="fieldset-small">
							<legend class="legend-small">客户信息</legend>
							<label>姓名：<span id="receiverDetailPage"></span></label>
							<label>年龄：<span id="ageDetailPage"></span></label>
							<label>性别：<span id="sexDetailPage"></span></label>
							<label>手机号：<span id="telephoneDetailPage" class="phoneCls"></span></label>
							<br>
							<label>地址：<span id="addressDetailDetailPage"></span></label>
						</fieldset>
						<label style="margin-top: 10px;font-weight: bold;color: yellowgreen;font-size: 13px;">购买商品：</label>
						<div id="detailPageTbDiv" >
							<table id="detailsPageTable" class="bordered detailsPageTable" style="margin: 5px 0px 5px;width: 900px;">
								<tr>
									<th>商品编码</th>
									<th>商品名称</th>
									<th>规格</th>
									<th>品牌</th>
									<th style="width: 92px;">药品许可证号</th>
									<th>制药公司</th>
									<th>单价</th>
									<th>数量</th>
									<th>金额</th>
									<th>是否赠品</th>
									<th>是否处方药</th>
								</tr>
							</table>
						</div>
						
						<fieldset class="fieldset-small" id="kfFieldSet">
							<legend class="legend-small">客服审核信息</legend>
							<fieldset class="fieldset-small" id="kfInvoiceFieldSet">
								<legend class="legend-small">发票信息</legend>
									<label><font>是否开票：</font>
										<span>
											<select id="kfInvoiceStatusDetailPage">
												<option value="0">否</option>
												<option value="1">是</option>
											</select>
										</span>
									</label>
									<label id="kfInvoiceTypeLabel"><font>发票类型：</font>
										<span>
											<select id="kfInvoiceTypeDetailPage">
												<option value="PLAIN">普通发票</option>
												<option value="ELECTRONIC">电子发票</option>
												<option value="VAT">增值税发票</option>
											</select>
										</span>
									</label>
									<label id="kfInvoiceTitleLabel"><font>抬头：</font>
										<span>
											<input type="text" id="kfInvoiceTitleDetailPage" class="input" style="height: 20px;width: 250px;" /><br>
										</span>
									</label>
									<label id="kfInvoiceContentLabel"><font>发票内容：</font>
										<span>
											<input type="text" id="kfInvoiceContentDetailPage" class="input" style="height: 20px;width: 250px;" />
										</span>
									</label>
							</fieldset>
							<div class="form-group" style="margin:10px 0px 0px 3px;clear: both;" id="kfRemarkDiv">
								<span style="color: #66CC99;">✦✦✦✦✦客户订单备注✦✦✦✦✦</span>
								<textarea class="form-control" style="margin-top: 5px;" id="kfRemarkDetailPage" name="kfRemark" placeholder="录入并更改用户订单备注..." rows="1"></textarea>
							</div>
							<div class="form-group" style="margin:10px 0px 0px 3px;clear: both;" >
								<span style="color: #66CC99;">✦✦✦✦✦客服审核说明✦✦✦✦✦</span>
								<textarea class="form-control" style="margin-top: 5px;" id="kfAuditDescriptionDetailPage" name="auditDescription" placeholder="请输入审核说明..." rows="2"></textarea>
							</div>
						</fieldset>
						
						<fieldset class="fieldset-small" id="doctorFieldSet">
							<legend class="legend-small">药师审核信息</legend>
							<div class="form-group" style="margin:10px 0px 0px 3px;clear: both;">
								<span style="color: #66CC99;">✦✦✦✦✦药师审核说明✦✦✦✦✦</span>
								<textarea class="form-control" style="margin-top: 5px;" id="doctorAuditDescriptionDetailPage" name="doctorAuditDescriptionDetailPage" placeholder="请输入审核说明..." rows="2"></textarea>
							</div>
						</fieldset>
					</div>

					<div class="modal-footer" style="height: 35px;padding-top: 3px;">
						<label style="font-size: 13px;float: left;display: none;" id="auditUserLabel">执行药师：<span id="auditUserDetailPage" style="font-weight: bold;font-size: 13px;color: #428BCA;"></span></label>

						<button type="button" id="succBtn" class="btn btn-primary btn-sm" onclick="auditFunc('SUCC')"> 通过 </button>
						<button type="button" id="retnBtn" class="btn btn-primary btn-sm" onclick="auditFunc('RETURN')"> 驳回 </button>
						<button type="button" id="closeBtn" class="btn btn-primary btn-sm" data-dismiss="modal"> 取消 </button>
					</div>
			</div>
		</div>
	</div>
	
	
	

	</body>
</html>
