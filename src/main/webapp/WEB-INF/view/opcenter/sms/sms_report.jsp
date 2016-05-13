<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>平台短信统计</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">

	<!-- 加载CSS样式文件 -->
	<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-theme.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
	
	<!-- 加载javascript文件 -->
	<script src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/WdatePicker/WdatePicker.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap/respond.min.js"></script>

<style type="text/css">
#sms_tb th{background-color: #f9f9f9;}
.item_input{height:30px;}
.item_title{line-height:30px;margin-left:10px;text-align:right;}
</style>
<script type="text/javascript">
function queryCount(){
	$("#smsForm").attr("action","smsReport.do");
	$("#smsForm").submit();
}
function exportData(){
	if(confirm("您确定要导出统计报表吗？")){
		$("#smsForm").attr("action","exportData.do");
		$("#smsForm").submit();
	}
}

</script>
</head>
<body>
	<div class="right_box">
		<h4>平台短信统计</h4>
		<form id="smsForm" name="smsForm" action="smsReport.do" style="margin-bottom:10px;">
			<div class="pull-left item_title">发送日期：</div>
			<div class="pull-left" style="width: 150px;">
				<input id="startDate" name="startDate" value="${startDate }" type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'});" class="Wdate form-control item_input"/>
			</div>
			<div class="pull-left">&nbsp;&nbsp;__&nbsp;&nbsp;</div>
			<div class="pull-left" style="width: 150px;">
				<input id="endDate" name="endDate" value="${endDate }" type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" class="Wdate form-control item_input"/>
			</div>
			<button type="button" class="btn btn-info btn-sm" onclick="queryCount()" style="margin:0 15px;">查询</button>
			<button type="button" class="btn btn-info btn-sm" onclick="exportData()" style="margin:0 15px;">导出</button>
			<div style="clear:both;"></div>
		</form>
		<table id="sms_tb" class="table table-bordered" style="width:600px;">
			<thead>
				<tr>
					<th>序号</th>
					<th>店铺名称</th>
					<th>短信触发量（条）</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${smsList }" var="item" varStatus="status">
					<tr>
						<td>${status.index+1 }</td>
						<td >${item.shopName }</td>
						<td style="text-align:right;">${item.num }</td>
					</tr>
				</c:forEach>
				<tr style="font-weight:bold;">
					<td>&nbsp;</td>
					<td >合计</td>
					<td style="text-align:right;">${count }</td>
				</tr>
			</tbody>
		</table>
		<div style="width:600px;">
			<div style="font-weight: bold;">说明:</div>
			<p>短息触发量：本报表仅统计因生成订单触发的几类短息，包括：已下单、配送中、已发货、已签收、已拆单、运单已复核、已退款。<br />
			条数：同一个订单触发的短息字数如果超出65字符，则系统发送短信时会自动拆分，所以统计数据时同样按照每满65字符，将超出部分按新增一条短息计算，以此类推。</p>
		</div>
	</div>
	<div class="clearfix"></div>
</body>
</html>