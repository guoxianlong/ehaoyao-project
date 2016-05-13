<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
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
	$("body").mask("Loading , please waite...");
	var pageSize = $("#curPageSize").val().trim();
	var totalRecords = $("#totalRecords").val().trim();
	var userName = $("#userName").val().trim();
	var telephoneNo = $("#telephoneNo").val().trim();
	var timeNum = $("#timeNum").val().trim();
	var productNo = $("#productNo").val().trim();
	var actionName = $("#actionName").val().trim();
	$.ajax({
		type: "POST",
		url:"<%=request.getContextPath()%>/"+actionName+"?method=getBuyRecords",
        dataType: "html",
        data: {pageno:pageno,pageSize:pageSize,recTotal:totalRecords,userName:encodeURI(userName),telephoneNo:telephoneNo,
        	timeNum:timeNum,productNo:productNo},
        success: function(data){
       		$("#cardDiv").parent().html(data);
       		$("body").unmask();
        },
        error: function(){
        	$("#cardDiv").parent().html("");
        	$("body").unmask();
        }
	});	
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
<div id="cardDiv" style="min-width: 1000px;">
	<form id="dataForm" name="dataForm" action="" class="form-horizontal" role="form" method="post">
		<input type="hidden" id="curPage" name="pageno" value="${pageno}"/>
		<input type="hidden" id="curPageSize" name="pageSize" value="${pageSize}"/>
		<input type="hidden" id="totalRecords" name="totalRecords" value="${recTotal}"/>
		<input type="hidden" id="actionName" name="actionName" value="${actionName}"/>
		<div class="right_inline">
			<div class="right_input item_title">姓名：</div>
			<div class="right_input" style="width:100px">
				<input id="userName" name="userName" value="${bvo.userName}" type="text" readonly class="form-control item_input"/>
			</div>
			<div class="right_input item_title">手机：</div>
			<div class="right_input" style="width: 150px">
				<input id="telephoneNo" name="telephoneNo" value="${bvo.telephoneNo}" type="text" readonly class="form-control item_input"/>
			</div>
			<div class="right_input item_title">期间：</div>
			<div class="right_input" style="width: 100px;">
				<select id="timeNum" name="timeNum" class="selectpicker form-control select_set">
					<option value="0" ${"0"==bvo.timeNum?'selected="selected"':''}>全部</option>
					<option value="3" ${"3"==bvo.timeNum?'selected="selected"':''}>三个月</option>
					<option value="6" ${"6"==bvo.timeNum?'selected="selected"':''}>六个月</option>
					<option value="12" ${"12"==bvo.timeNum?'selected="selected"':''}>十二个月</option>
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
			<c:if test="${bLs==null || fn:length(bLs)<=0}">
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
