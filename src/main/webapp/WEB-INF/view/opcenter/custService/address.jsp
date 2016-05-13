<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
<style type="text/css">
#buy_table thead tr th{font-size:14px;background-color: #f9f9f9;}
input.item_input {height: 30px;}
</style>	
<script type="text/javascript">
function gotoPage(pageno){
	var pageSize = $("#curPageSize").val().trim();
	var totalRecords = $("#totalRecords").val().trim();
	var actionName = $("#actionName").val().trim();
	var userId = $("#memberCode").val();
	$.ajax({
		type: "POST",
		url:"<%=request.getContextPath()%>/"+actionName+"?method=getUserAddressInfo",
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
	<form id="dataForm" name="dataForm" action="" class="form-horizontal" role="form" method="post">
		<input type="hidden" id="curPage" name="pageno" value="${pageno}"/>
		<input type="hidden" id="curPageSize" name="pageSize" value="${pageSize}"/>
		<input type="hidden" id="totalRecords" name="totalRecords" value="${recTotal}"/>
		<input type="hidden" id="actionName" name="actionName" value="${actionName}"/>
		<div style="clear:both;"></div>
	</form>
	<table id="buy_table" class="table table-bordered">
		<%-- <caption><span style="font-size: 17px;font-weight: bold;">消费记录</span></caption> --%>
		<thead>
			<tr>
				<th>是否默认地址</th>
				<th>地址类型</th>
				<th>省</th>
				<th>市</th>
				<th>区</th>
				<th>详细地址</th>
				<th>邮编</th>
				<th>收件人</th>
				<th>手机</th>
			</tr>
		</thead>
		<tbody id="tb_body">
			<c:forEach items="${als}" varStatus="status" var="address">
			<tr>
				<td>${address.isDefault=='1'?'是':'否'}</td>
				<td>${address.name }</td>
				<td>${address.province }</td>
				<td>${address.city}</td>
				<td>${address.district }</td>
				<td>${address.fullAddress }</td>
				<td>${address.postcode }</td>
				<td>${address.contacts}</td>
				<td>${address.contactNumber }</td>
			</tr>
			</c:forEach>
			<c:if test="${als==null || fn:length(als)<=0}">
				<tr>
					<td colspan="8" style="height:80px;text-align: center;vertical-align: middle;font-size:14px;font-weight: bold;">暂无数据！</td>
				</tr>
			</c:if>
		</tbody>
	</table>
	<ul class="pager">
		<%@ include file="/WEB-INF/view/common/page.jspf" %>
	</ul>
</div>