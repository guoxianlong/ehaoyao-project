<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<script type="text/javascript">
	//查询分页跳转
	function gotoPage(pageno)
	{
	  	document.getElementById('pageno').value = pageno;
	  	document.form.submit();
	}
	//设置页面显示的条数
	function gotoPageSize(pageSize){
	  	document.getElementById('pageSize').value = pageSize;
	  	document.form.submit();
	} 
	function warrant(roleId,userId){
		$.ajax( {   
		     type : "POST",  
		     data:{
		    	 roleId:roleId,
		    	 userId:userId
		     },
		     url : "<%=request.getContextPath()%>/user.do?method=warrant",   
		     dataType: "json",   
		     success : function(data) {   
		    	 if( data.result == "true" ){
		    		$("#btn"+roleId).removeAttr("onclick");
		    		$("#btn"+roleId).attr("onclick","notWarrant("+roleId+","+userId+")");
		    		$("#btn"+roleId).removeClass("btn btn-success btn-xs");
		    		$("#btn"+roleId).addClass("btn btn-danger btn-xs");
		    		$("#btn"+roleId).text("取消");
		    	 }else{
		    		 alert("取消授权失败，请联系管理员！");
		    	 }
		     } 
		 });
	}
	function notWarrant(roleId,userId){
		$.ajax( {   
		     type : "POST",  
		     data:{
		    	 roleId:roleId,
		    	 userId:userId
		     },
		     url : "<%=request.getContextPath()%>/user.do?method=notWarrant",   
		     dataType: "json",   
		     success : function(data) {   
		    	 if( data.result == "true" ){
		    		$("#btn"+roleId).removeAttr("onclick");
		    		$("#btn"+roleId).attr("onclick","warrant("+roleId+","+userId+")");
		    		$("#btn"+roleId).removeClass("btn btn-danger btn-xs");
		    		$("#btn"+roleId).addClass("btn btn-success btn-xs");
		    		$("#btn"+roleId).text("授权");
		    	 }else{
		    		 alert("取消授权失败，请联系管理员！");
		    	 }
		     } 
		 });
	}
	function warrantChange(){
    	var txt = $("#warrantSel").val();
    	$("#isWarrant").val(txt);
    }
</script>
<title>用户分配角色</title>
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
<body>
	<form action="user.do?method=detail" name="form" method="post">
		<div class="right_box" id="ll"></div>
		<div class="right_h right_bg">分配角色</div>
		<div class="right_box_l"></div>
		<div class="panel-body">
			<input type="hidden" id="pageno" name="pageno" value="${pageno}" />
			<input type="hidden" id="pageSize" name="pageSize" value="${pageSize}" />
			<input type="hidden" id="userId" name="userId" value="${vo.userId }" />
			<br>
			<table class="table table-bordered" >
				<tr>
					<th>用户</th>
					<th>邮箱</th>
					<th>状态</th>
					<th>创建时间</th>
				</tr>
				<tr>
					<td>${user.userName }</td>
					<td>${user.email }</td>
					<td>${user.lockStatus == 0 ? "启用" : "锁定" }</td>
					<td>${user.createTime }</td>
				</tr>
			</table>
			
			<div class="right_inline">
				<div class="right_input" style="padding-top: 5px">&nbsp;&nbsp;&nbsp;&nbsp;<B>角色查询:</B></div>
				<div class="right_input" style="width:200px;">
				<input  id="name" name="name" value="${vo.name}" type="text" class="form-control input-sm"   ></div>
				<div class="right_input" style="padding-top: 5px">&nbsp;&nbsp;&nbsp;&nbsp;<B>所属平台:</B></div>
				<div class="right_input" style="width:200px;">
					<select name="platformId" style="height: 30px;" id="platformId" class="selectpicker form-control select_set">
						<option value="" selected>--请选择--</option>
						<c:forEach items="${apfls}" var="item" varStatus="status">
							<option value="${item.id}"} ${item.id==vo.platformId?'selected="selected"':''}>${item.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="right_input" style="padding-top: 5px">&nbsp;&nbsp;&nbsp;&nbsp;<B>是否授权:</B></div>
				<input type="hidden" id="isWarrant" name="isWarrant" value="${vo.isWarrant}" />
				<div class="right_input" style="width:200px;" >
					<select  style="height: 30px;" id="warrantSel" onchange="warrantChange();" class="selectpicker form-control select_set">
						<option value="0" selected>--请选择--</option>
						<option value="1" ${vo.isWarrant==1?'selected="selected"':''}>未授权</option>
						<option value="2" ${vo.isWarrant==2?'selected="selected"':''}>已授权</option>
					</select>
				</div>
				<div class="right_input" style="padding-top: -2px;">
					<div class="right_input" style="width:15px;">&nbsp;&nbsp;&nbsp;&nbsp;</div>
					<button type="button" onclick="gotoPage(1)" class="btn btn-info sear_btn" style="height:30px;">搜索</button>
					<button class="btn btn-default" type="button" style="height:30px;" onclick="window.location.href='<%=request.getContextPath()%>/user.do?method=user'">返回</button>
				</div>
			</div>
			
			<table class="table table-bordered">
				<tr>
					<th>角色</th>
					<th>备注</th>
					<th>所属平台</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${userRoleList}" var="role">
					<tr>
						<td>${role.name}</td>
						<td>${role.remark}</td>
						<td>${role.paltformNm}</td>
						<td>
							<c:choose>  
								<c:when test="${role.userRoleId == null|| role.userRoleId <= 0}"> 
									<sec:authorize ifAnyGranted="/role.do?method=detail,/role.do" >
										<a class="btn btn-success btn-xs" id="btn${role.id}"
										href="#" onclick="warrant(${role.id},${vo.userId});">授权</a>
									</sec:authorize>
								</c:when>  
								<c:otherwise>  
									<sec:authorize ifAnyGranted="/role.do?method=detail,/role.do" >
										<a class="btn btn-danger btn-xs" id="btn${role.id}"
										href="#" onclick="notWarrant(${role.id},${vo.userId});">取消</a>
									</sec:authorize>
								</c:otherwise>  
							</c:choose>  
						</td>
					</tr>
				</c:forEach>
			</table>
			<ul class="pager">
			<%@ include file="/WEB-INF/view/common/page.jspf"%>
		</ul>
		</div>
	</form>
</body>
</html>
