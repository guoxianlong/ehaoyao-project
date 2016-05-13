<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>购买记录</title>
    
    <!-- 加载CSS样式文件 -->
    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-theme.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-select.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
	
	<!-- 加载javascript文件 -->
    <script src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap-select.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/mask.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/respond.min.js"></script>
    
<style type="text/css">
#buy_table thead tr th{font-size:14px;background-color: #f9f9f9;}
input.item_input {height: 30px;}
.item_title {text-align: right;width: auto;padding-top: 5px;margin-left: 15px;}
.sear_btn{margin-left: 20px; padding: 0 12px; height: 30px;}
.select_set + div button.selectpicker{height:30px;padding-top:0;padding-bottom:0;}
</style>
<script type="text/javascript">
$(document).ready(function(){
	$('.selectpicker').selectpicker();
});

function gotoPage(pageno){
	$("#curPage").val(pageno);
	var pageSize = $("#curPageSize").val().trim();
	$("#curPageSize").val(pageSize);
	var totalRecords = $("#totalRecords").val().trim();
	$("#totalRecords").val(totalRecords);
	var userName = $("#userName").val().trim();
	$("#userName").val(userName);
	var telephoneNo = $("#telephoneNo").val().trim();
	$("#telephoneNo").val(telephoneNo);
	var productNo = $("#productNo").val().trim();
	$("#productNo").val(productNo);
	
	$("body").mask("Loading , please waite ...");
	$("#dataForm").submit();
	
}
function gotoPageSize(pageSize){
	$("#curPageSize").val(pageSize);
	gotoPage(1);
}
//查询
function queryBuy(){
	gotoPage(1);
}

</script>
</head>
<body>
<div class="panel panel-default">
	<div class="panel-heading">
		<h5 class="panel-title" style="color: #31849b;">购买记录查询</h5>
	</div>
	<div class="panel-body">		
<!-- <div class="right_box" style="margin-top:20px;"> -->
	<form id="dataForm" name="dataForm" action="<%=request.getContextPath()%>/tradeReport.do?method=getTradeRecords" class="form-horizontal" method="post">
		<input type="hidden" id="curPage" name="pageno" value="${pageno}"/>
		<input type="hidden" id="curPageSize" name="pageSize" value="${pageSize}"/>
		<input type="hidden" id="totalRecords" name="recTotal" value="${recTotal}"/>
		<input type="hidden" id="qFlag" name="qFlag" value="1"/>
		<div class="right_inline">
			<div class="right_input item_title">姓名：</div>
			<div class="right_input" style="width:100px">
				<input id="userName" name="userName" value="${bvo.userName}" type="text" class="form-control item_input"/>
			</div>
			<div class="right_input item_title">手机：</div>
			<div class="right_input" style="width: 150px">
				<input id="telephoneNo" name="telephoneNo" value="${bvo.telephoneNo}" type="text" class="form-control item_input"/>
			</div>
			<div class="right_input item_title">期间：</div>
			<div class="right_input" style="width: 100px;">
				<select id="timeNum" name="timeNum" class="selectpicker form-control select_set">
					<option value="3" ${"3"==bvo.timeNum?'selected="selected"':''}>三个月</option>
					<option value="6" ${"6"==bvo.timeNum?'selected="selected"':''}>六个月</option>
					<option value="12" ${"12"==bvo.timeNum?'selected="selected"':''}>十二个月</option>
					<option value="0" ${"0"==bvo.timeNum?'selected="selected"':''}>全部</option>
				</select>
			</div>
			<div class="right_input item_title">商品编号：</div>
			<div class="right_input" style="width: 150px">
				<input id="productNo" name="productNo" value="${bvo.productNo}" type="text" class="form-control item_input"/>
			</div>
			<button type="button" onclick="queryBuy()" class="btn btn-info sear_btn">查询</button>
			<div style="clear:both;"></div>
		</div>
		<div style="clear:both;"></div>
	</form>
	<table id="buy_table" class="table table-bordered">
		<thead>
			<tr>
				<th>序号</th>
				<th>订单日期</th>
				<th>订单编号</th>
				<th>发货仓库</th>
				<th>商品编码</th>
				<th>商品名称</th>
				<th>规格</th>
				<th>单位</th>
				<th>数量</th>
				<th>单价</th>
				<th>金额</th>
				<th>赠品</th>
<!-- 				<th>渠道类别</th> -->
<!-- 				<th>三方商家</th> -->
				<th>快递公司</th>
				<th>运单号</th>
			</tr>
		</thead>
		<tbody id="tb_body">
			<c:forEach items="${bLs}" varStatus="status" var="buy">
			<tr>
				<td>${status.count}</td>
				<td>${buy.orderTime }</td>
				<td>${buy.orderNum }</td>
				<td>${buy.goodsArea }</td>
				<td>${buy.goodsNum }</td>
				<td>${buy.goodsName }</td>
				<td>${buy.formart }</td>
				<td>${buy.unit}</td>
				<td style="text-align: right;">${buy.count }</td>
				<td style="text-align: right;">${buy.price }</td>
				<td style="text-align: right;">${buy.totalPrice }</td>
				<td>${buy.totalPrice gt 0.001 ? '否' : '是' }</td>
<%-- 				<td>${fn:indexOf(buy.goodsSpid, 'SPH5')==0?'三方':'自营'}</td> --%>
<%-- 				<td>${buy.otherSeller }</td> --%>
				<td>${buy.expressComName }</td>
				<td>${buy.expressCode }</td>
			</tr>
			</c:forEach>
			<c:if test="${qFlag=='0'}">
				<tr>
					<td colspan="14" style="height:100px;text-align: center;vertical-align: middle;font-size:14px;font-weight: bold;"></td>
				</tr>
			</c:if>
			<c:if test="${qFlag=='1' && (bLs==null || fn:length(bLs)<=0)}">
				<tr>
					<td colspan="14" style="height:100px;text-align: center;vertical-align: middle;font-size:14px;font-weight: bold;">暂无数据！</td>
				</tr>
			</c:if>
		</tbody>
	</table>
	<ul class="pager">
		<%@ include file="/WEB-INF/view/common/page.jspf" %>
	</ul>
</div>
</div>
</body>
</html>
