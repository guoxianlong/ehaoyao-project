<%@ page language="java" contentType="text/html; charset=utf-8"   pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/jquery.tooltip.css" type="text/css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.tooltip.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/media/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/bvalidator/js/jquery.bvalidator.js"></script>
<link href="<%=request.getContextPath()%>/bvalidator/css/bvalidator.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/bvalidator/css/bvalidator.theme.red.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改销售代表和医院</title>
<script type="text/javascript">

function fromSubmit(){
		$("#userForm").submit();	
}
</script>
</head>
<body>
<form id="userForm" action="<%=request.getContextPath()%>/doctorInHospital.do?method=saveUpdateSalesRep" method="post" >
<div class="right_box" id="ll" ></div>
		<div class="right_h right_bg" >修改销售代表和医院</div>
		<div class="right_box_l"></div>
		<input type="hidden" id="flag" value="false"/>		
			<div id="namediv" class="right_inline">
				<div class="right_title">销售中心</div>
				<div class="right_input" style="width:300px;">
				<input name="salesCenter" id="salesCenter" value="${salesRepModel.salesCenter }" data-bvalidator="minlength[3],maxlength[20],required"  type="text"  class="form-control input-sm"></div>
			</div>
			<input id="id" name="id" type="hidden" value="${salesRepModel.id} " />
			<input id="createTime" name="createTime" type="hidden" value="${salesRepModel.createTime} " />
			<div id="checkpswddiv" class="right_inline">
				<div class="right_title">医院名称</div>
				<div class="right_input" style="width:300px;"><input  id="hospitalName" name="hospitalName" value="${salesRepModel.hospitalName }"  type="text" data-bvalidator="maxlength[20],required" onblur="checkpasswordt()" class="form-control input-sm" ></div>
			</div>
			<div id="checkpswddiv" class="right_inline">
				<div class="right_title">代表名称</div>
				<div class="right_input" style="width:300px;"><input  id="salesRepName" name="salesRepName" value="${salesRepModel.salesRepName }"  type="text" data-bvalidator="maxlength[20],required" onblur="checkpasswordt()" class="form-control input-sm" ></div>
			</div>
			<div id="emaildiv" class="right_inline">
				<div class="right_title">办事处</div>
				<div class="right_input" style="width:300px;"><input   id="office" name="office" value="${salesRepModel.office }"  type="text" data-bvalidator="maxlength[50],required"  onblur="checkEmail()"  class="form-control input-sm" ></div>
			</div>
				<div id="emaildiv" class="right_inline">
				<div class="right_title">省公司</div>
				<div class="right_input" style="width:300px;"><input   id="provincialCompany" name="provincialCompany" value="${salesRepModel.provincialCompany }"  type="text" data-bvalidator="maxlength[50],required"  onblur="checkEmail()"  class="form-control input-sm" ></div>
			</div>
		
			<div class="form-group">
				<div class="right_title">&nbsp;</div>
	    		<button class="btn btn-default" onclick="fromSubmit()" type="button" >提交</button>
			<!-- 	<button class="btn btn-default" type="button" onclick="window.location.href='<%=request.getContextPath()%>/doctorInHospital.do?method=user'">返回</button>  -->
  			</div>
</form>
</body>
<script type="text/javascript">
	$(function(){
		setTimeout(function(){
			$("#password").attr('type','password') ;
			$("#passwordt").attr('type','password') ;
		}, 10) ;
	}) ;
</script>