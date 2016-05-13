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
	    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-theme.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/bootstrap.min.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/style.css"
	rel="stylesheet">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.8.3.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap-select.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/respond.min.js"></script>
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
	
	function deleteRole(obj){
		if( confirm("是否删除? ")){
			$.ajax( {   
			     type : "POST",   
			     url : "<%=request.getContextPath()%>/role.do?method=del&id=" + obj.id,   
			     dataType: "json",   
			     success : function(data) {   
			    	 if( data.result == "true" ){
			    		 window.location.href="<%=request.getContextPath()%>/role.do?method=role";
			    	 }else if(data.result == "false"){   
			        	 alert("该角色已被使用，不能删除");
			        	 return;
			         } 
			     } 
			 });
		}
	}
	
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
	function openPermission(){
		$('#myModal').modal('show');
	}
</script>
<body>
<form action="role.do?method=role" name="form" method="post">

<div class="panel panel-primary">
  <div class="panel-heading">
    <h3 class="panel-title">角色管理</h3>
  </div>
  <div class="panel-body">
  	<input type="hidden" id="pageno" name="pageno" value="${pageno}" /> <input
			type="hidden" id="pageSize" name="pageSize" value="${pageSize}" />
		
		<div class="right_inline">
		<div class="right_title">
		<sec:authorize ifAnyGranted="/role.do?method=add,/role.do" >
			<a class="btn btn-primary btn-xs"	href="<%=request.getContextPath()%>/role.do?method=add">添加角色</a>
		</sec:authorize>
		</div>
		<div class="right_input" style="padding-top: 5px">&nbsp;&nbsp;&nbsp;&nbsp;<B>角色查询:</B></div>
		<div class="right_input" style="width:350px;">
		<input  id="name" name="name" value="${role.name}" type="text" class="form-control input-sm"   ></div>
		<div class="right_input" style="padding-top: 5px">&nbsp;&nbsp;&nbsp;&nbsp;<B>所属平台:</B></div>
		<div class="right_input" style="width:200px;">
			<select name="platformId" style="height: 30px;" id="platformId" class="selectpicker form-control select_set">
				<option value="" selected>--请选择--</option>
				<c:forEach items="${apfls}" var="item" varStatus="status">
					<option value="${item.id}"} ${item.id==role.platformId?'selected="selected"':''}>${item.name}</option>
				</c:forEach>
			</select>
		</div>
		<div class="right_input"  style="padding-top: 5px">
		<div class="right_input" style="width:10px;">&nbsp;&nbsp;&nbsp;&nbsp;</div>
			<button type="button" onclick="chang()" class="btn btn-success btn-xs">搜索</button>
		</div>
</div>
		
		<table class="table table-bordered table-hover">
			<tr>
				<th>角色</th>
				<th>备注</th>
				<th>所属平台</th>
				<th><span>操作</span> 
			</th>
			</tr>
			<c:forEach items="${roleList}" var="role">
				<tr>
					<td style="text-align: left">${role.name }</td>
					<td>${role.remark }</td>
					<td>${role.paltformNm }</td>
					<td>
					<sec:authorize ifAnyGranted="/role.do?method=del,/role.do" >
						<a class="btn btn-info btn-xs" id="${role.id }"
							onclick='deleteRole(this)'
							href="javascript:void(0)">删除</a>
					</sec:authorize>
					<sec:authorize ifAnyGranted="/role.do?method=data,/role.do" >
						<a class="btn btn-success btn-xs"
							href="<%=request.getContextPath() %>/role.do?method=data&id=${role.id }">修改</a>
					</sec:authorize>
					<sec:authorize ifAnyGranted="/role.do?method=detail,/role.do" >
						<a class="btn btn-warning btn-xs"
						href="<%=request.getContextPath() %>/role.do?method=detail&roleId=${role.id }&platformId=${role.platformId}" >授权</a>
					</sec:authorize>
					<!--  -->
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

<!-- 授权窗口 -->
<div class="modal " style="display: none; " id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:900px;">
		<div class="modal-content" >
			<div class="modal-header">
				<button type="button" class="close">&times;</button>
				<h4 class="modal-title" id="myModalLabel">资源一览</h4>
			</div>
			<div id="permissionInfo" class="modal-body">
				<table style="width: 850px;">
					<tr>
						<td align="left" style="width: 45px;">资源名称:</td>
						<td align="left" style="width: 160px;">
							<input  id="name" name="name" value="${permission.name}" type="text" class="form-control input-sm"   ></div>
						</td>
						<td style="width: 10px;">&nbsp;&nbsp;</td>
						<td style="width: 60px;">资源url:</td>
						<td style="width: 160px;">
						    <input  id="url" name="url" value="${permission.url}" type="text" class="form-control input-sm"   ></div>
						</td>
						<td style="width: 10px;">&nbsp;&nbsp;</td>
						<td style="width: 60px;">所属平台:</td>
						<td style="width: 160px;">
						    <select name="platformId" style="height: 30px;" id="platformId" class="selectpicker form-control select_set">
								<option value="" selected>--请选择--</option>
								<c:forEach items="${apfls}" var="item" varStatus="status">
									<option value="${item.id}"} ${item.id==permission.platformId?'selected="selected"':''}>${item.name}</option>
								</c:forEach>
							</select>
						</td>
						<td style="width: 10px;">&nbsp;&nbsp;</td>
						<td width="5%">
						    <input type="button" value="查询" onclick="search_product();" class="btn btn-primary" />
						</td>
					</tr>
				</table>
				<br>
				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<th>资源名称</th>
							<th>资源url</th>
							<th>所属平台</th>
							<th><span>操作</span></th>
						</tr>
					</thead>
					<tbody id="permission_body">
						
					</tbody>
				</table>
				<div class="navigation"> 
					<div style="text-align: right; float: right;"> 
						共<label id="lblToatl"></label>条数据 第[<label id="lblCurent"></label>]页/共[<label id="lblPageCount">0</label>]页 
						<a id="first" href="#">首页</a> <a id="previous" href="#">上一页</a> <a id="next" href="#"> 
						下一页</a> <a id="last" href="#">末页</a> 
					</div> 
				</div> 
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal" style="margin-left: 500px;">取消</button>
       			<button type="button" id="submitBtn" class="btn btn-primary" onclick="choice_prod();">确定</button>  
			</div>
		</div>
	</div> 
</div>

</body>
</html>
