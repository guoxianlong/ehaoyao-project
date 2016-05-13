<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
		document.form.submit();
	}
	
	//查询分页跳转
    function gotoPage(pageno)
    {
      document.getElementById('pageno').value = pageno;
      document.form.submit();
    }
    
    //设置页面显示的条数
       function gotoPageSize(pageSize)
      {
        document.getElementById('pageSize').value = pageSize;
        document.form.submit();
      }
	
</script>
<body>
<form action="user.do?method=user" name="form" method="post">
<div class="panel panel-primary">
  <div class="panel-heading">
    <h4 class="panel-title">用户管理</h4>
  </div>
  <div class="panel-body">
  <input type="hidden" id="pageno" name="pageno" value="${pageno}" /> <input
			type="hidden" id="pageSize" name="pageSize" value="${pageSize}" />
		<div class="right_inline">
		<div class="right_title">
		<sec:authorize ifAnyGranted="/user.do?method=add,/user.do" >
			<a class="btn btn-primary btn-xs"	href="<%=request.getContextPath()%>/user.do?method=add">添加账号</a>
		</sec:authorize>
		</div>
		<div class="right_input" style="padding-top: 5px">&nbsp;&nbsp;&nbsp;&nbsp;<B>用户查询:</B></div>
		<div class="right_input" style="width:350px;">
		<input  id="userName" name="userName" value="${user.userName}" type="text" class="form-control input-sm"   ></div>
 <div class="right_input"  style="padding-top: 5px">&nbsp;&nbsp;&nbsp;&nbsp;<B>机构</B></div>
 <div class="right_input" style="width:110px;">
 <select id="orgId" name="orgId"  class="form-control input-sm">
 	 	<c:choose>
 	 		<c:when test="${user.orgId == '' || user.orgId == null }">
       			<option value="" selected >请选择</option>
       			<c:forEach items="${orgList }" var="org" >
       				<option value="${org.id }" >${org.name }</option>
       			</c:forEach>
       		</c:when>
       		<c:otherwise>
       			<option value="" >请选择</option>
       			<c:forEach items="${orgList }" var="org" >
       			<c:choose>
		 	 		<c:when test="${org.id == user.orgId }">
		       			<option value="${org.id }" selected >${org.name }</option>
	    	   		</c:when>
	       			<c:otherwise>
	       				<option value="${org.id }" >${org.name }</option>
	       			</c:otherwise>
	       		</c:choose>
	       		</c:forEach>
       		</c:otherwise>
       </c:choose>
</select></div>
<div class="right_input"  style="padding-top: 5px">&nbsp;&nbsp;&nbsp;&nbsp;<B>状态</B></div>
 <div class="right_input" style="width:110px;">
 <select id="lockStatus" name="lockStatus"  class="form-control input-sm">
 	<c:choose>
 		<c:when test="${user.lockStatus == null || user.lockStatus == '' }"> 
 			<option value="" selected >请选择</option>
       		<option value="0">启用</option>
       		<option value="1">锁定</option>
       </c:when>
       <c:when test="${user.lockStatus == 0 }">
       		<option value="" >请选择</option>
       		<option value="0" selected >启用</option>
       		<option value="1">锁定</option>
       </c:when>
       <c:otherwise>
       		<option value="" >请选择</option>
       		<option value="0">启用</option>
       		<option value="1" selected >锁定</option>
       </c:otherwise>
    </c:choose>
</select></div>
<div class="right_input"  style="padding-top: 5px">
	<div class="right_input" style="width:10px;">&nbsp;&nbsp;&nbsp;&nbsp;</div>
	<button type="button" onclick="chang()" class="btn btn-success btn-xs">搜索</button>
</div>


</div>

		<table class="table table-bordered table-hover">
			<tr>
				<th>用户账号</th>
				<th>姓名</th>
				<th>邮箱</th>
				<td>机构</td>
				<th>状态</th>
				<th>创建时间</th>
				<th><span>操作</span> 
			</th>
			</tr>
			<c:forEach items="${userList}" var="user">
				<tr>
					<td>${user.userName }</td>
					<td>${user.name}</td>
					<td>${user.email }</td>
					<c:forEach items="${orgList }" var="org" >
						<c:if test="${org.id == user.orgId }">
							<td>${org.name }</td>
						</c:if>
					</c:forEach>
					<td>${user.lockStatus == 0 ? "启用" : "锁定" }</td>
					<td>${user.createTime }</td>
					<td>
					<sec:authorize ifAnyGranted="/user.do?method=del,/user.do" >
						<a class="btn btn-info btn-xs"
							onclick="return confirm('是否删除?')"
							href="<%=request.getContextPath() %>/user.do?method=del&id=${user.id }">删除</a>
					</sec:authorize>
					<sec:authorize ifAnyGranted="/user.do?method=data,/user.do" >
						<a class="btn btn-success btn-xs"
							href="<%=request.getContextPath() %>/user.do?method=data&id=${user.id }">修改</a>
					</sec:authorize>
					<sec:authorize ifAnyGranted="/user.do?method=detail,/user.do" >
						<a class="btn btn-warning btn-xs"
						<%-- href="<%=request.getContextPath() %>/user.do?method=detail&id=${user.id }">授权</a> --%>
						href="<%=request.getContextPath() %>/user.do?method=detail&userId=${user.id }">授权</a>
					</sec:authorize>
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
