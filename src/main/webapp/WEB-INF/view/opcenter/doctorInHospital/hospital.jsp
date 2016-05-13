<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/checkBox.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/sorttable.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/common.js"></script>
<link href="<%=request.getContextPath()%>/media/css/bootstrap.min.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/style.css"
	rel="stylesheet">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.8.3.js"></script>
<title>用户角色</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<style>
table {
	TABLE-LAYOUT: fixed;
}

th {
	text-align: center;
}

td {
	text-align: center;
	WORD-WRAP: break-word;
}
</style>
<script type="text/javascript">
	
	$(document).ready(function(){
		jQuery('#userName').bValidator(options,'nameInst');
	});
//搜索

	function chang(){
		var keyword = $("#hospitalName").val();
		var url="<%=request.getContextPath()%>/doctorInHospital.do?method=showAllSalesRep&searchName="
				+ keyword;
		document.form.action = url;
		document.form.submit();
	}

	//查询分页跳转
	function gotoPage(pageno) {
		document.getElementById('pageno').value = pageno;
		document.form.submit();
	}

	//设置页面显示的条数
	function gotoPageSize(pageSize) {
		document.getElementById('pageSize').value = pageSize;
		document.form.submit();
	}
</script>
<body>
	<form action="doctorInHospital.do?method=showAllSalesRep" name="form" method="post">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h4 class="panel-title">医院信息</h4>
			</div>
			<div class="panel-body">
				<input type="hidden" id="pageno" name="pageno" value="${pageno}" />
				<input type="hidden" id="pageSize" name="pageSize"
					value="${pageSize}" />
				<div class="right_inline">
					<div class="right_input" style="padding-top: 5px">
						&nbsp;&nbsp;&nbsp;&nbsp;<B>医院查询:</B>
					</div>
					<div class="right_input" style="width: 350px;">
						<input id="hospitalName" name="hospitalName" type="text"
							class="form-control input-sm">
					</div>
					<div class="right_input" style="width: 10px;">&nbsp;&nbsp;&nbsp;&nbsp;</div>
					<button type="button" onclick="chang()"
						class="btn btn-success btn-xs">搜索</button>
					<input type="button" name="button" value="添加"
						onclick="window.location.href='<%=request.getContextPath()%>/doctorInHospital.do?method=addSalesRep'" />
				</div>

				<table class="table table-bordered table-hover">
					<tr>
						<th>序号</th>
						<th>医院名称</th>
						<th>销售中心</th>
						<th>省公司</th>
						<th>办事处</th>
						<th>代表名称</th>
<!-- 						<th><span>操作</span></th> -->
					</tr>
					<c:forEach items="${salesRepList}" var="hospital">
						<tr>
							<td>${hospital.id }</td>
							<td>${hospital.hospitalName }</td>
							<td>${hospital.salesCenter}</td>
							<td>${hospital.provincialCompany}</td>
							<td>${hospital.office }</td>
							<td>${hospital.salesRepName }</td>
<!-- 							<td><a class="btn btn-info btn-xs" -->
<!-- 								onclick="return confirm('是否删除?')" -->
<%-- 								href="<%=request.getContextPath() %>/user.do?method=del&id=${user.id }">删除</a> --%>
<!-- 								<a class="btn btn-success btn-xs" href="#">修改</a></td> -->
						</tr>
					</c:forEach>
				</table>
				<ul class="pager">
					<%@ include file="/WEB-INF/view/common/page.jspf"%>
				</ul>

			</div>
		</div>
	</form>
</body>
</html>
