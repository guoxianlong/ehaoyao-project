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
	.sear_btn{margin-left: 120px; padding: 0 12px; height: 30px;}
	.exp_btn{margin-left: 0px; padding: 0 12px; height: 30px;}
	.select_set + div button.selectpicker{height:30px;padding-top:0;padding-bottom:0;}
	.space{margin-left: 180px}
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
		
		//导出
		function exportComm(){
			
		}
	</script>
</head>
<body style="padding-top: 10px;" id="commuLsDiv">
	<div class="right_box">
	    <form name="queryForm" action="" class="form-horizontal" role="form" method="post" style="width:auto;">
			<input type="hidden" id="curPage" name="pageno" value="${pageno}"/>
			<input type="hidden" id="curPageSize" name="pageSize" value="${pageSize}"/>
			
			
			<div class="right_inline">
				<!-- 咨询日期 -->
				<div class="right_input item_title">咨询日期：</div>
				<div class="right_input" style="width: 110px;">
					<input id="startDate" name="startDate" value="${cvo.startDate}" type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'});" class="form-control item_input"/>
				</div>
				<div class="right_input">&nbsp;__&nbsp;</div>
				<div class="right_input" style="width: 110px;">
					<input id="endDate" name="endDate" value="${cvo.endDate}" type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" class="form-control item_input"/>
				</div>
				<!-- 客服工号 -->
				<div class="right_input item_title">客服工号：</div>
				<div class="right_input" style="width: 100px">
					<input id="workNo" name="workNo" type="text" value="${cvo.workNo}" class="form-control item_input"/>
				</div>
				<!-- 联系分组 -->
				<div class="right_input item_title">联系分组：</div>
				<div class="right_input">
					<select name="attitude" id="attitude" class="selectpicker form-control select_set">
							<option value="1" ${"1"==member.attitude?'selected="selected"':''}>全部</option>
							<option value="2" ${"2"==member.attitude?'selected="selected"':''}>呼入组</option>
							<option value="3" ${"3"==member.attitude?'selected="selected"':''}>呼出组</option>
					</select>
				</div>
					<button type="button" onclick="gotoPage(1)" class="btn btn-info sear_btn">查询</button>
				<div style="clear:both;"></div>
			</div>
			
			
			<div class="right_inline">
				<!-- 科组类别 -->
				<div class="right_input item_title">科组类别：</div>
				<div class="right_input">
					<select name="attitude" id="attitude" class="selectpicker form-control select_set">
							<option value="1" ${"1"==member.attitude?'selected="selected"':''}>全部</option>
							<option value="2" ${"2"==member.attitude?'selected="selected"':''}>肝病科</option>
							<option value="3" ${"3"==member.attitude?'selected="selected"':''}>心脏病科</option>
							<option value="4" ${"4"==member.attitude?'selected="selected"':''}>外科</option>
							<option value="5" ${"5"==member.attitude?'selected="selected"':''}>神经科</option>
							<option value="6" ${"6"==member.attitude?'selected="selected"':''}>其他</option>
					</select>
				</div>
				<!-- 病种类别-->
				<div class="right_input item_title">病种类别：</div>
				<div class="right_input">
					<select name="attitude" id="attitude" class="selectpicker form-control select_set">
							<option value="1" ${"1"==member.attitude?'selected="selected"':''}>全部</option>
							<option value="2" ${"2"==member.attitude?'selected="selected"':''}>乙肝</option>
							<option value="3" ${"3"==member.attitude?'selected="selected"':''}>帕金森</option>
							<option value="4" ${"4"==member.attitude?'selected="selected"':''}>高血压</option>
							<option value="5" ${"5"==member.attitude?'selected="selected"':''}>糖尿病</option>
							<option value="6" ${"6"==member.attitude?'selected="selected"':''}>其他</option>
					</select>
				</div>
				<!-- 是否成单 -->
				<div class="right_input item_title">是否成单：</div>
				<div class="right_input">
					<select name="attitude" id="attitude" class="selectpicker form-control select_set">
							<option value="1" ${"1"==member.attitude?'selected="selected"':''}>全部</option>
							<option value="2" ${"2"==member.attitude?'selected="selected"':''}>是</option>
							<option value="2" ${"2"==member.attitude?'selected="selected"':''}>否</option>
					</select>
				</div>
				<!-- 今日跟踪 -->
				<div class="right_input item_title space">今日跟踪：</div>
				<div class="right_input">
					<select name="attitude" id="attitude" class="selectpicker form-control select_set">
							<option value="1" ${"1"==member.attitude?'selected="selected"':''}>全部</option>
							<option value="2" ${"2"==member.attitude?'selected="selected"':''}>是</option>
							<option value="2" ${"2"==member.attitude?'selected="selected"':''}>否</option>
					</select>
				</div>
				<button type="button" onclick="exportComm()" class="btn btn-info exp_btn">导出</button>
			</div>
			<div style="clear:both;"></div>
		</form>
		<table class="table table-bordered" style="margin-top: 5px;">
			<thead>
				<tr>
					<th style="width:5%;">操作</th>
					<th style="width:5%;">客服工号</th>
					<th style="width:8%;">客户编号</th>
					<th style="width:5%;">客户来源</th>
					<th style="width:8%;">产品编码</th>
					<th style="width:6%;">咨询日期</th>
					<th style="width:6%;">成单日期</th>
					<th style="width:4%;">是否成单</th>
					<th style="width:10%;">产品关键词</th>
					<th style="width:5%;">科组类别</th>
					<th style="width:5%;">病种类别</th>
					<th style="width:4%;">订单数量</th>
					<th style="width:6%;">订单金额</th>
					<th style="width:4%;">是否跟踪</th>
					<th style="width:4%;">今日跟踪</th>
					<th style="width:10%;">跟踪信息</th>
				</tr>
			</thead>
			<tbody id="tb_body">
				<tr>
					<td colspan="16" style="height:100px;text-align: center;vertical-align: middle;font-size:14px;font-weight: bold;">暂无数据！</td>
				</tr>
			</tbody>
		</table>
		<ul class="pager">
			<%@ include file="/WEB-INF/view/common/page.jspf" %>
		</ul>
	</div>
</body>
</html>