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
<title>添加医生</title>
<script type="text/javascript">
/* 删除左右两端的空格 */
function trim(str){ 
    return str.replace(/(^\s*)|(\s*$)/g, "");
}

var options = {
		lang : 'zh_CN',
		showCloseIcon : false,
		position : {
			x : 'right',
			y : 'top'
		}, 
		showErrMsgSpeed : 'normal',//'fast', 'normal', 'slow' 
		validateOn : 'blur',//'change', 'blur', 'keyup'
		classNamePrefix : 'bvalidator_red_'//bvalidator_red_,bvalidator_orange_,bvalidator_gray2_,bvalidator_postit_

}; 

$(document).ready(function(){
	jQuery('#email').bValidator(options,'emailInst');
	jQuery('#doctorName').bValidator(options,'doctorNameInst');

});

function removeCss( id ){
	document.getElementById(id).className = " right_inline ";
}
//验证医生姓名
function checkDoctorName(){
	var email = document.getElementById("doctorName").value;
	var userDiv = document.getElementById("doctorNamediv");
	/* var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-]))+(\.+[com|cn]{2,3})+$/; */
	if( $.trim(email) == "" ){
		userDiv.className = userDiv.className + " form-group has-error";
		$("#doctorName").data("bValidators").doctorNameInst.showMsg($("#doctorName"),"此字段为必填");
		return false;
/* 	}else if ( !reg.test(email) ){
		userDiv.className = userDiv.className + " form-group has-error";
		$("#email").data("bValidators").emailInst.showMsg($("#email"),"请输入正确的邮箱,比如xsx@163.com!");
		return false; */
	}else{
		userDiv.className = userDiv.className + " form-group has-success";
 		return true;
	}
}

//验证医生邮箱格式  
function checkEmail(){
	var email = document.getElementById("email").value;
	var userDiv = document.getElementById("emaildiv");
	var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-]))+(\.+[com|cn]{2,3})+$/;
	if( $.trim(email) == "" ){
		userDiv.className = userDiv.className + " form-group has-error";
		$("#email").data("bValidators").emailInst.showMsg($("#email"),"此字段为必填");
		return false;
	}else if ( !reg.test(email) ){
		userDiv.className = userDiv.className + " form-group has-error";
		$("#email").data("bValidators").emailInst.showMsg($("#email"),"请输入正确的邮箱,比如xsx@163.com!");
		return false;
	}else{
		userDiv.className = userDiv.className + " form-group has-success";
 		return true;
	}
}


/* 保存医生信息 */
function saveDoctor(){
	//验证医生名称
	if(!checkDoctorName()){
		return;
	}
	//验证邮箱
	if(!checkEmail()){
		return;
	}
	$("#userForm").submit();	
		
	
} 	 	
</script>
</head>
<body>
<form id="userForm" action="<%=request.getContextPath()%>/doctorInHospital.do?method=saveDoctor" method="post" >
<div class="right_box" id="ll" ></div>
		<div class="right_h right_bg" >创建医生</div>
		<div class="right_box_l"></div>
		<input type="hidden" id="flag" value="false"/>		
			<div id="doctorNamediv" class="right_inline">
				<div class="right_title">医生名称</div>
				<div class="right_input" style="width:300px;">
				<input name="doctorName" id="doctorName"  data-bvalidator="minlength[2],maxlength[20],required"  type="text"  onblur="checkDoctorName()"   class="form-control input-sm"></div>
			</div>
			
			<div id="emaildiv" class="right_inline">
				<div class="right_title">医生邮箱</div>
				<div class="right_input" style="width:300px;"><input onfocus="removeCss('emaildiv')"  id="email" name="email"  type="text" data-bvalidator="maxlength[50],required"  onblur="checkEmail()"  class="form-control input-sm" ></div>
			</div>
			<div id="checkpswddiv" class="right_inline">
				<div class="right_title">所在医院</div>
				<div class="right_input" style="width:300px;">
				<select id="orgId" name="orgId"  class="form-control input-sm">
					<c:forEach items="${salesRepList }" var="org" >
						<option value="${org.id }" >${org.hospitalName }</option>
					</c:forEach>
					</select>
				</div>
			</div>
	
		
			<div class="form-group">
				<div class="right_title">&nbsp;</div>
	    		<button class="btn btn-default" onclick="saveDoctor()" type="button" >提交</button>
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