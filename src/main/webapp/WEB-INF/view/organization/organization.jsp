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
<title>组织机构</title>
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
	function findorg(){
		name = $("#name").val();
		name = encodeURI(name);
		name = encodeURI(name);
		window.location.href="<%=request.getContextPath()%>/org.do?method=findByName&name=" + name;
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
	
    function deleteOrg(obj){
		if( confirm("是否删除? ")){
			$.ajax( {   
			     type : "POST",   
			     url : "<%=request.getContextPath()%>/org.do?method=del&id=" + obj.id,   
			     dataType: "json",   
			     success : function(data) {   
			    	 if( data.result == "true" ){
			    		 window.location.href="<%=request.getContextPath()%>/org.do?method=organization";
			    	 }else if(data.result == "false"){   
			        	 alert("该机构已被使用，不能删除");
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
<form action="org.do?method=organization" name="form" method="post" >

	<div class="panel panel-primary">
	  <div class="panel-heading">
	    <h3 class="panel-title">机构管理</h3>
	  </div>
	  <div class="panel-body">
	  	<input type="hidden" id="pageno" name="pageno" value="${pageno}" /> <input
				type="hidden" id="pageSize" name="pageSize" value="${pageSize}" />
				
			<div class="right_inline">
			<div class="right_title">
			<sec:authorize ifAnyGranted="/org.do?method=add,/org.do" >
				<a class="btn btn-primary btn-xs"	href="<%=request.getContextPath()%>/org.do?method=add">添加机构</a>
			</sec:authorize>
			</div>
				<div class="right_input" style="padding-top: 5px">&nbsp;&nbsp;&nbsp;&nbsp;<b>机构代码:</b></div>
				<div class="right_input" style="width:200px;">
				<input  id="code" name="code" value="${org.code}" type="text" class="form-control input-sm"   ></div>
				<div class="right_input" style="padding-top: 5px">&nbsp;&nbsp;&nbsp;&nbsp;<b>机构名称:</b></div>
			<div class="right_input" style="width:200px;">
			<input  id="name" name="name" value="${org.name}" type="text" class="form-control input-sm"   ></div>
			<div class="right_input"  style="padding-top: 5px">
				<div class="right_input" style="width:10px;">&nbsp;&nbsp;&nbsp;&nbsp;</div>
				<button type="button" onclick="chang()" class="btn btn-success btn-xs">搜索</button>
			</div>
			</div>
			<table class="table table-bordered table-hover">
				<tr>
					<th>机构代码</th>
					<th>机构名称</th>
					<th>负责人</th>
					<th><span>操作</span> 
				</th>
				</tr>
				<c:forEach items="${orgList}" var="organization">
					<tr>
						<td>${organization.code }</td>
						<td>${organization.name }</td>
						<td>${organization.manager }</td>
						<td>
						<sec:authorize ifAnyGranted="/org.do?method=del,/org.do" >
							<a class="btn btn-info btn-xs" id="${organization.id }"
								onclick="deleteOrg(this)"
								href="javascript:void(0)">删除</a>
						</sec:authorize>
						<sec:authorize ifAnyGranted="/org.do?method=data,/org.do" >
							<a class="btn btn-success btn-xs"
								href="<%=request.getContextPath() %>/org.do?method=data&id=${organization.id }">修改</a>
						</sec:authorize>
						<sec:authorize ifAnyGranted="/org.do?method=detail,/org.do" >
							<a class="btn btn-warning btn-xs"
							href="<%=request.getContextPath() %>/org.do?method=detail&id=${organization.id }">详细</a>
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
