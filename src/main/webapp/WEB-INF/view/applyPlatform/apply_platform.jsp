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
	
    function deletePerm(obj){
		if( confirm("是否删除? ")){
			$.ajax( {   
			     type : "POST",   
			     url : "<%=request.getContextPath()%>/applyPlatform.do?method=del&id=" + obj.id,   
			     dataType: "json",   
			     success : function(data) {   
			    	 if( data.result == "true" ){
			    		 window.location.href="<%=request.getContextPath()%>/applyPlatform.do?method=getApplyPlatformList";
			    	 }else if(data.result == "false"){   
			        	 alert("该资源已被使用，不能删除");
			        	 return;
			         } 
			     } 
			 });
		}
	}
    
    function chang(){
		document.form.submit();
	}
	
</script>
<body>
<form action="applyPlatform.do?method=getApplyPlatformList" name="form" method="post">
<div class="panel panel-primary">
  <div class="panel-heading">
    <h3 class="panel-title">应用平台管理</h3>
  </div>
  <div class="panel-body">
  	<input type="hidden" id="pageno" name="pageno" value="${pageno}" /> <input
			type="hidden" id="pageSize" name="pageSize" value="${pageSize}" />
			
		<div class="right_inline">
		<div class="right_title">
		<sec:authorize ifAnyGranted="/applyPlatform.do?method=add,/applyPlatform.do" >
			<a class="btn btn-primary btn-xs"	href="<%=request.getContextPath()%>/applyPlatform.do?method=add">添加应用</a>
		</sec:authorize>
		</div>
			<div class="right_input" style="padding-top: 5px">&nbsp;&nbsp;&nbsp;&nbsp;<B>应用名称:</B></div>
			<div class="right_input" style="width:200px;">
			<input  id="name" name="name" value="${vo.name}" type="text" class="form-control input-sm"   ></div>
			<div class="right_input" style="padding-top: 5px">&nbsp;&nbsp;&nbsp;&nbsp;<b>应用url:</b></div>
		<div class="right_input" style="width:200px;">
		<input  id="url" name="url" value="${vo.url}" type="text" class="form-control input-sm"   ></div>
		<div class="right_input"  style="padding-top: 5px">
			<div class="right_input" style="width:10px;">&nbsp;&nbsp;&nbsp;&nbsp;</div>
			<button type="button" onclick="chang()" class="btn btn-success btn-xs">搜索</button>
		</div>
		</div>

		
			
		<table class="table table-bordered table-hover">
			<tr>
				<th>序号</th>
				<th>名称</th>
				<th>地址</th>
				<th>说明</th>
				<th><span>操作</span></th>
			</tr>
			<c:forEach items="${platformList}" var="applyPlatform" varStatus="status">
				<tr>
					<td>${status.count}</td>
					<td>${applyPlatform.name }</td>
					<td>${applyPlatform.url }</td>
					<td>${applyPlatform.comment }</td>
					<td>
					<sec:authorize ifAnyGranted="/applyPlatform.do?method=del,/applyPlatform.do" >
						<a id="${applyPlatform.id }" class="btn btn-info btn-xs"
							onclick='deletePerm(this)'
							href="javascript:void(0)">删除</a>
					</sec:authorize>
					<sec:authorize ifAnyGranted="/applyPlatform.do?method=data,/applyPlatform.do" >
						<a class="btn btn-success btn-xs"
							href="<%=request.getContextPath() %>/applyPlatform.do?method=data&id=${applyPlatform.id }">修改</a>
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

