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

	function chang(){
// 		var keyword = $("#doctorName").val();
<%-- 		var urll="<%=request.getContextPath()%>/doctorInHospital.do?method=showAllDoctor&searchName="+ keyword; --%>
// 		document.form.action = urll;
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
	//更改医生状态  "0"为失效  ,"1"为有效
	function changeStatus(status,id) {
	 	$.ajax({
    		type : "POST",
    		url : "<%=request.getContextPath()%>/doctorInHospital.do?method=updateStatusDoctor",
    		dataType: "html",
    		data : {status:status,
    				id:id},
    		success: function(data){
    				alert(data);
    				document.form.submit();
    		},
    		error: function() {
    			if(status == 0){
    				alert("更新无效状态失败！请联系开发人员");
    			}
				if(status == 1){
					alert("更新有效状态失败！请联系开发人员");			
				}				
    		}
    	});
	}
</script>
<body>
	<form action="doctorInHospital.do?method=showAllDoctor" name="form"
		method="post">		 
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h4 class="panel-title">医生信息</h4>
			</div>
			<div class="panel-body">
				 <input type="hidden" id="pageno" name="pageno" value="${pageno}" />
				<input type="hidden" id="pageSize" name="pageSize" value="${pageSize}" />
				<div class="right_inline">
					<div class="right_input" style="padding-top: 5px">
						&nbsp;&nbsp;&nbsp;&nbsp;<B>医生查询:</B>
					</div>
					<div class="right_input" style="width: 350px;">
						<input id="searchName" name="searchName" type="text"
							class="form-control input-sm">
					</div>
					<div class="right_input" style="width: 10px;">&nbsp;&nbsp;&nbsp;&nbsp;</div>
					<button type="button" onclick="chang()" class="btn btn-primary ">搜索</button>
					<button type="button" onclick="window.location.href='<%=request.getContextPath()%>/doctorInHospital.do?method=addDoctor'" 
					class="btn btn-primary ">添加</button>
					<%-- <input type="button" name="button" value="添加"
						onclick="window.location.href='<%=request.getContextPath()%>/doctorInHospital.do?method=addDoctor'" /> --%>
				</div>
			<table class="table table-bordered table-hover">
				<tr>
					<th>序号</th>
					<th>医生姓名</th>
					<th>医生邮箱</th>
					<th>所在医院（如空白,则医院为失效）</th>
					<th>创建时间</th>
					<th>更新时间</th>
					<th>是否有效</th>
					<th><span>操作</span></th>

				</tr>
				<c:forEach items="${doctorRepList}" var="doctor">
					<tr>
						<td>${doctor.id }</td>
						<td>${doctor.doctorName }</td>
						<td>${doctor.email}</td>
						<td>
						<c:forEach items="${salesRepList}" var="salesRepList">
							<c:if test="${doctor.salesRepId==salesRepList.id}">
							${salesRepList.hospitalName}
							</c:if>
						</c:forEach>
						</td>
						<td>${doctor.createTime }</td>
						<td>${doctor.updateTime }</td>
						<td>
							<c:if test="${doctor.status == 0}">否</c:if>
							<c:if test="${doctor.status == 1}">是</c:if>
						</td>
						<td>
							<button type="button" class="btn btn-danger btn-xs"  onclick="changeStatus(0,${doctor.id});">失效</button>
							<button type="button" class="btn btn-success btn-xs" onclick="changeStatus(1,${doctor.id});">恢复</button>
							<%-- <a class="btn btn-success btn-xs" href="<%=request.getContextPath() %>/doctorInHospital.do?method=updateStatusDoctor&id=${doctor.id}&status=0&pageno=${pageno}&pageSize=${pageSize}">失效</a>
							<a class="btn btn-success btn-xs" href="<%=request.getContextPath() %>/doctorInHospital.do?method=updateStatusDoctor&id=${doctor.id}&status=1&pageno=${pageno}&pageSize=${pageSize}">恢复</a>
							<a class="btn btn-info btn-xs" onclick="return confirm('是否失效?')" href="<%=request.getContextPath() %>/user.do?method=del&id=${user.id }">删除</a>
							<a class="btn btn-info btn-xs" onclick="return confirm('是否失效?')" href="<%=request.getContextPath() %>/user.do?method=del&id=${user.id }">删除</a> --%>
						</td>
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
