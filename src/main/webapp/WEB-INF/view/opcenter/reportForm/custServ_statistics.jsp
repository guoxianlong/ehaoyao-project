<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1 maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>客服统计</title>
    
    <!-- 加载CSS样式文件 -->
    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-theme.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-select.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/city.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
   
    <!-- 加载javascript文件 -->
 	<script type="text/javascript" src="<%=request.getContextPath()%>/js/opcenter/commuType.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.3.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap-select.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/WdatePicker/WdatePicker.js"></script>
    <script src="<%=request.getContextPath()%>/js/city.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/respond.min.js"></script>
    <style type="text/css">
	table thead tr th{font-size:14px;background-color: #f9f9f9;}
	input.item_input {height: 30px;}
	.item_title {text-align: right;width: auto;padding-top: 5px;margin-left: 15px;}
	.item_title2 {width: 80px;font-weight: bold;text-align: right;padding-top: 10px;margin-left: 15px;}
	.sear_btn{margin-left: 30px; padding: 0 12px; height: 30px;}
	.exp_btn{margin-lett: 30px; padding: 0 12px; height: 30px;}
	.select_set + div button.selectpicker{width:95px;height:30px;padding-top:0;padding-bottom:0;}
	.select_set_is + div button.selectpicker{width:70px;height:30px;padding-top:0;padding-bottom:0;}
	</style>
    <script type="text/javascript">
		String.prototype.trim = function(){
			return this.replace(/(^\s*)|(\s*$)/g, "");
		};
		
		$(document).ready(function(){
			commuType("0","#acceptResult");
			$('.selectpicker').selectpicker();
			getNextSelect(1);
			alert("123");
		});
		//翻页
		function gotoPage(pageno){
			var queryDate=$("#queryDate").val().trim();
			var pageSize = $("#curPageSize").val().trim();
			$.ajax({
				type: "POST",
				url: "<%=request.getContextPath()%>/custServStatistics.do?method=getStatisticsList&flag=1",
		        dataType: "html",
		        data: {consultDate:queryDate,pageno:pageno,pageSize:pageSize},
		        success: function(data){
		       		$("#statisticsLsDiv").html(data);
		        },
		        error: function(){
		        	$("#statisticsLsDiv").html("");
	            }
			});	
		}
		function gotoPageSize(pageSize){
			$("#curPageSize").val(pageSize);
			gotoPage($("#curPage").val());
		}
		//将查询结果导出报表
		function exportComm(){
			document.form.action="custServStatistics.do?method=getExcel";
			document.form.submit();
		}
	</script>
</head>
<body style="padding-top: 10px;">
	<div class="right_box">
	    <form name="queryForm" action="" class="form-horizontal" role="form" method="post" style="width:auto;">
			<input type="hidden" id="curPage" name="pageno" value="${pageno}"/>
			<input type="hidden" id="curPageSize" name="pageSize" value="${pageSize}"/>
			<div style="margin-bottom:10px;">
				<div class="right_input item_title">统计日期：</div>
				<div class="right_input" style="width: 110px;">
					<input id="queryDate" name="queryDate" value="${cs.consultDate}" type="text" onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" class="form-control item_input"/>
				</div>
				<div class="right_input" style="width: 150px;">
					<button type="button" onclick="gotoPage(1)" class="btn btn-info sear_btn">查询</button>
				</div>
				<sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_ExportExcel,/custServStatistics.do?method=getExcel">
					<div class="right_input">
						<button type="button" onclick="exportComm();" class="btn btn-info exp_btn">导出</button>
					</div>
				</sec:authorize>
				<div style="clear:both;"></div>
			</div>
		</form>
		<form  method="post" action="" name="form"	id="form">
		<div id="statisticsLsDiv" style="overflow-x:auto;">
			<jsp:include page="inner_custServ_statistics.jsp"></jsp:include>
		</div>
		</form>
	</div>
	
</body>
</html>