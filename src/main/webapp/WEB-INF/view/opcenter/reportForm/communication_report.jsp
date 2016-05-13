<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1 maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>会员查询</title>
    
    <!-- 加载CSS样式文件 -->
    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-theme.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-select.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/city.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
   
    <!-- 加载javascript文件 -->
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.3.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap-select.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/WdatePicker/WdatePicker.js"></script>
    <script src="<%=request.getContextPath()%>/js/city.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/respond.min.js"></script>
    <style type="text/css">
	#buy_table thead tr th{font-size:14px;background-color: #f9f9f9;}
	input.item_input {height: 30px;}
	.item_title {text-align: right;width: auto;padding-top: 5px;margin-left: 15px;}
	.item_title2 {width: 80px;font-weight: bold;text-align: right;padding-top: 10px;margin-left: 15px;}
	.sear_btn{margin-right: 350px; padding: 0 12px; height: 30px;}
	.select_set + div button.selectpicker{height:30px;padding-top:0;padding-bottom:0;}
	</style>
    <script type="text/javascript">
		String.prototype.trim = function(){
			return this.replace(/(^\s*)|(\s*$)/g, "");
		};
		
		$(document).ready(function(){
			$('.selectpicker').selectpicker();
		});
		//翻页
		function gotoPage(pageno){
			var tel = $("#tel").val().trim();
			var memberName = $("#memberName").val().trim();
			var workNo = $("#workNo").val().trim();
			var pageSize = $("#curPageSize").val().trim();
			var startDate = $("#startDate").val().trim();
			var endDate = $("#endDate").val().trim();
			$.ajax({
				type: "POST",
				url: "<%=request.getContextPath()%>/communicateReport.do?method=getCommunicationList",
		        dataType: "html",
		        data: {tel:tel,memberName:memberName,workNo:workNo,pageno:pageno,pageSize:pageSize,startDate:startDate,endDate:endDate},
		        success: function(data){
		       		$("#commuLsDiv").html(data);
		        }
			});	
		}
		function gotoPageSize(pageSize){
			$("#curPageSize").val(pageSize);
			gotoPage($("#curPage").val());
		}
	</script>
</head>
<body style="padding-top: 10px;" id="commuLsDiv">
	<div class="right_box">
	    <form name="queryForm" action="" class="form-horizontal" role="form" method="post" style="width:auto;">
			<input type="hidden" id="curPage" name="pageno" value="${pageno}"/>
			<input type="hidden" id="curPageSize" name="pageSize" value="${pageSize}"/>
			<div class="right_inline">
				<div class="right_input item_title">姓名：</div>
				<div class="right_input" style="width: 200px">
					<input id="memberName" name="memberName" type="text" value="${cvo.memberName}" class="form-control item_input"/>
				</div>
				<div class="right_input item_title">手机：</div>
				<div class="right_input" style="width: 200px">
					<input id="tel" name="tel" type="text" value="${cvo.tel}" class="form-control item_input"/>
				</div>
				<div style="clear:both;"></div>
			</div>
			<div class="right_inline">
				<div class="right_input item_title">工号：</div>
				<div class="right_input" style="width: 200px">
					<input id="workNo" name="workNo" type="text" value="${cvo.workNo}" class="form-control item_input"/>
				</div>
				<div class="right_input item_title">日期：</div>
				<div class="right_input" style="width: 150px;">
					<input id="startDate" name="startDate" value="${cvo.startDate}" type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'});" class="form-control item_input"/>
				</div>
				<div class="right_input">&nbsp;__&nbsp;</div>
				<div class="right_input" style="width: 150px;">
					<input id="endDate" name="endDate" value="${cvo.endDate}" type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" class="form-control item_input"/>
				</div>
				<button type="button" onclick="gotoPage(1)" class="btn btn-info sear_btn">查询</button>
				<div style="clear:both;"></div>
			</div>
			<div style="clear:both;"></div>
		</form>
		<table class="table table-bordered" style="margin-top: 5px;">
			<thead>
				<tr>
					<th style="width:8%;">客服工号</th>
					<th style="width:10%;">姓名</th>
					<th style="width:10%;">手机</th>
					<th style="width:20%;">购买商品</th>
					<th style="width:10%;">结果</th>
					<th style="width:30%;">备注</th>
					<th style="width:12%;">时间</th>
				</tr>
			</thead>
			<tbody id="tb_body">
				<c:forEach items="${commuList}" varStatus="status" var="commu">
				<tr>
					<td title="${commu.createUser}">${commu.createUser}</td>
					<td title="${commu.member.memberName}">${commu.member.memberName}</td>
					<td title="${commu.tel}">${commu.tel}</td>
					<td title="${commu.productName}" style="text-align: left">${commu.productName}</td>
					<td>${commu.acceptResult}
<%-- 					<c:choose> --%>
<%-- 						<c:when test="${commu.acceptResult=='1'}">订购</c:when> --%>
<%-- 						<c:when test="${commu.acceptResult=='2'}">考虑</c:when> --%>
<%-- 						<c:when test="${commu.acceptResult=='3'}">反感</c:when> --%>
<%-- 						<c:when test="${commu.acceptResult=='4'}">关机</c:when> --%>
<%-- 						<c:when test="${commu.acceptResult=='5'}">空号</c:when> --%>
<%-- 						<c:when test="${commu.acceptResult=='6'}">无应答</c:when> --%>
<%-- 						<c:when test="${commu.acceptResult=='7'}">非处方药订购</c:when> --%>
<%-- 						<c:when test="${commu.acceptResult=='8'}">处方药订购</c:when> --%>
<%-- 						<c:when test="${commu.acceptResult=='9'}">订单查询</c:when> --%>
<%-- 						<c:when test="${commu.acceptResult=='10'}">退换货</c:when> --%>
<%-- 						<c:when test="${commu.acceptResult=='11'}">投诉</c:when> --%>
<%-- 						<c:otherwise>其它</c:otherwise> --%>
<%-- 					</c:choose> --%>
					</td>
					<td style="word-wrap: break-word; word-break:break-all;text-align: left">${commu.remark}</td>
					<td>${commu.createTime}</td>
				</tr>
				</c:forEach>
				<c:if test="${commuList==null || fn:length(commuList)<=0}">
					<tr>
						<td colspan="7" style="height:100px;text-align: center;vertical-align: middle;font-size:14px;font-weight: bold;">暂无数据！</td>
					</tr>
				</c:if>
			</tbody>
		</table>
		<ul class="pager">
			<%@ include file="/WEB-INF/view/common/page.jspf" %>
		</ul>
	</div>
</body>
</html>