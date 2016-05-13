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
		<title>订单查询</title>
		
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
        	作者：158822436@qq.com
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
		<script src="<%=request.getContextPath()%>/js/opcenter/payOrder/payOrder.js"></script>
		<style type="text/css">
         .fieldset ul.dropdown-menu{max-height:200px !important;}
        </style>
	</head>
	
	<body style="min-height: 680px;">
		<h3 class="h3">订单查询</h3>
		<form id="orderOprForm"  method="post" role="form">
		<div id="conditionDiv" >
			<input type="hidden" id="curPage" name="pageno" value="${pageno}"/>
			<input type="hidden" id="curPageSize" name="pageSize" value="${pageSize}"/>
			<input type="hidden" id="totalRecords" name="totalRecords" value="${recTotal}"/>
			<input type="hidden" id="orderBy" name="orderBy" value="${vo.orderBy}"/>
			<input type="hidden" id="sort" name="sort" value="${vo.sort}"/>
			<input type="hidden" id="orderFlagHidden" name="orderFlagHidden" value="${vo.orderFlag}"/>
			<input type="hidden" name="actionName" value="${actionName}"/>
			<input type="hidden" name="contextPath" value="<%=request.getRequestURL()%>"/>
			<fieldset class="fieldset" >
				<legend class="legend"><span>查询条件</span></legend>
				<label><span>订单日期:</span></label>
				<input type="text" class="input" style="width: 100px;" id="startDate" name="startDate" value="${vo.startDate}" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'});" />
				<span>-</span>
				<input type="text" class="input" style="width: 100px;" id="endDate" name="endDate" value="${vo.endDate}" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" />
				
				<label><span>订单号：</span></label>
				<input type="text" id="orderNumber" class="input" name="orderNumber" value="${vo.orderNumber}" />
				
				<label><span>渠道:</span></label>
				<select id="orderFlagSelect" name="orderFlag" class="selectpicker" data-hide-disabled="false" data-live-search="true"> 		     
			          <option value="-1" >请选择</option>
				  </select>
				<br>
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
		 					<th><p id="selAll" name="selAll">序号</p></th>
		 					<th class="sortTh" id="order_number">订单号</th>
		 					<th class="sortTh" id="order_flag">渠道</th>
		 					<th class="sortTh" id="start_time">订单日期</th>
				 			<th class="sortTh" id="receiver">姓名</th>
				 			<th class="sortTh" id="telephone">电话</th>
				 			<th>地址</th>
				 			<th class="sortTh" id="order_price">订单金额(元)</th>
				 			<th class="sortTh" id="order_status">订单状态</th>
				 			<th class="sortTh" id="pay_type">支付类型</th>
				 			<th class="sortTh" id="price">付款金额(元)</th>
				 			
				 			<th class="sortTh" id="to_erp">进入ERP</th>
				 			<th class="sortTh" id="to_erp_time">进入ERP时间</th>
				 			<th class="sortTh" id="kf_account">客服工号</th>
				 			<th>订单备注</th>
		 				</tr>
		 			</thead>
					<tbody>
						<c:if test="${orderList != null && fn:length(orderList)>0}">
							<c:forEach items="${orderList}" var="items"
								varStatus="orderNumber">
								<tr class="orderInfoTr">
									<td style="vertical-align: middle;" >
										<p name="selItem" value="${items.orderNumber},${items.orderFlag}">
										${(pageno-1)*pageSize+orderNumber.count}</p>
									</td>
									<td style="vertical-align: middle;">${items.orderNumber}</td>
									<td style="display: none;">${items.orderFlag}</td>
                                    <td style="vertical-align: middle;">${items.orderFlag}</td>
									<td style="vertical-align: middle;">${items.orderTime}</td>
									<td style="vertical-align: middle;">${items.receiver}</td>
								    <td style="vertical-align: middle;" class="phoneCls">${(items.telephone==""||items.telephone==null)?items.mobile:items.telephone}</td>
									<td style="text-align: left; vertical-align: middle;">${items.province}${items.city}${items.area}${items.addressDetail}</td>
									<td style="text-align: right; vertical-align: middle;">
										<fmt:formatNumber value="${items.orderPrice!=null?items.orderPrice:0.00}" pattern="##.##" minFractionDigits="2"></fmt:formatNumber>
									</td>
						
									<td style="vertical-align: middle;">
										<c:if test="${items.orderStatus == 's00'}">订单初始化</c:if>
										<c:if test="${items.orderStatus == 's01'}">出货成功(已有运单号)</c:if>
										<c:if test="${items.orderStatus == 's02'}">运单信息已推送</c:if>
										<c:if test="${items.orderStatus == 's03'}">交易完成</c:if>
										<c:if test="${items.orderStatus == 's04'}">订单取消</c:if>
										<c:if test="${items.orderStatus == 's06'}">锁定状态(京东)</c:if>
										<c:if test="${items.orderStatus == 's07'}">处方药(客服代下单)</c:if>
										<c:if test="${items.orderStatus == 's09'}">退款订单</c:if>
										<c:if test="${items.orderStatus == 's10'}">退款信息已获取</c:if>
										<c:if test="${items.orderStatus == 's11'}">退款成功</c:if>
										<c:if test="${items.orderStatus == 's12'}">退款失败</c:if>
										<c:if test="${items.orderStatus == 's50'}">极速达</c:if>
										<c:if test="${items.orderStatus == 's20'}">去买药取消单子已获取</c:if>
										<c:if test="${items.orderStatus == 's21'}">取消单子回写平台成功</c:if>
									</td>
									<td style="vertical-align: middle;">
										<c:if test="${items.payType == '在线支付'}">在线支付</c:if>
										<c:if test="${items.payType == '货到支付'}">货到支付</c:if>
									</td>
									
									<td style="text-align: right;vertical-align: middle;">
										<fmt:formatNumber value="${items.price!=null?items.price:0.00}" pattern="##.##" minFractionDigits="2"></fmt:formatNumber>
									</td>														
			                       
			                       <td style="vertical-align: middle;">
										<c:if test="${items.toErp == '0'}">初始状态未同步</c:if>
										<c:if test="${items.toErp == '1'}">初始状态已同步</c:if>
										<c:if test="${items.toErp == '2'}">取消状态已同步</c:if>
										<c:if test="${items.toErp == '3'}">锁定状态已同步</c:if>
										<c:if test="${items.toErp == '4'}">物流回写已同步</c:if>
									    <c:if test="${items.toErp == '9'}">退款订单已同步</c:if>
									</td>

			                        <td style="vertical-align: middle;">${items.toErpTime}</td>
			                        <td style="vertical-align: middle;">${items.kfAccount}</td>
									<td style="vertical-align: middle;">${items.remark}</td>					
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
						<th>商品名称</th>
						<th>单价</th>
						<th>数量</th>
						<th>金额</th>
						<th>优惠后总金额</th>	
					</tr>
			    </table>
			</div>
			</div>
	</body>
</html>
