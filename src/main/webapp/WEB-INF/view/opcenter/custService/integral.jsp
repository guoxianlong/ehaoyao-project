<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
<style type="text/css">
#buy_table thead tr th{font-size:14px;background-color: #f9f9f9;}
</style>
<script type="text/javascript">
function gotoPage(pageno){
	var pageSize = $("#curPageSize").val().trim();
	var totalRecords = $("#totalRecords").val().trim();
	var actionName = $("#actionName").val().trim();
	var userId = $("#memberCode").val();
	$.ajax({
		type: "POST",
		url:"<%=request.getContextPath()%>/"+actionName+"?method=getIntegralInfo",
		dataType: "html",
        data: {pageno:pageno,pageSize:pageSize,recTotal:totalRecords,userId:userId},
        success: function(data){
       		$("#cardDiv").parent().html(data);
        },
        error: function(){
        	$("#cardDiv").parent().html("");
        }
	});	
}
function gotoPageSize(pageSize){
	$("#curPageSize").val(pageSize);
	gotoPage($("#curPage").val());
}
//查询
function queryBuy(){
	gotoPage(1);
}
</script>
<div id="cardDiv" style="min-width: 700px;">
	<!-- <div class="nav-tabs" style="padding-bottom: 15px;">
		<h4 style="font-weight: bold;">消费记录</h4>
	</div> -->
	<form id="dataForm" name="dataForm" action="" class="form-horizontal" role="form" method="post">
		<input type="hidden" id="curPage" name="pageno" value="${pageno}"/>
		<input type="hidden" id="curPageSize" name="pageSize" value="${pageSize}"/>
		<input type="hidden" id="totalRecords" name="totalRecords" value="${recTotal}"/>
		<input type="hidden" id="actionName" name="actionName" value="${actionName}"/>
		<div class="right_inline">
			<span style="font-size: 15px;font-weight: bold;color: #766967;">消费记录：</span>
			<div style="float:right;margin-right: 60px;">
				<div class="right_input">现金账户余额：<span style="font-size: 18px;color: #FCC89A">${integralBalance}</span></div>
			</div>
			<div style="clear:both;"></div>
		</div>
		<div style="clear:both;"></div>
	</form>
	<table id="buy_table" class="table table-bordered">
		<%-- <caption><span style="font-size: 17px;font-weight: bold;">消费记录</span></caption> --%>
		<thead>
			<tr>
				<th>时间</th>
				<th>数量</th>
				<th>消费内容</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody id="tb_body">
			<c:forEach items="${integralLs}" varStatus="status" var="itr">
			<tr>
				<td>${itr.consumeTime}</td>
				<td>${itr.integralNum }</td>
				<td>${itr.expandCode }</td>
				<td>${itr.remark }</td>
			</tr>
			</c:forEach>
			<c:if test="${integralLs==null || fn:length(integralLs)<=0}">
				<tr>
					<td colspan="4" style="height:80px;text-align: center;vertical-align: middle;font-size:14px;font-weight: bold;">暂无积分消费记录！</td>
				</tr>
			</c:if>
		</tbody>
	</table>
	<ul class="pager">
		<%@ include file="/WEB-INF/view/common/page.jspf" %>
	</ul>
</div>
