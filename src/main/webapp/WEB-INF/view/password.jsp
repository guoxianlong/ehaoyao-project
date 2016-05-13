<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
<title>修改密码</title>
</head>
<script type="text/javascript">
	//表单校验参数
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
		jQuery('#oldpassword').bValidator(options,'oldpswdInst');
		jQuery('#password').bValidator(options,'pswdInst');
		jQuery('#checkpassword').bValidator(options,'checkpasswordInst');
		jQuery("#userForm").bValidator(options,"formInstance");

	});
	
	function checkOldPassword( flag ){
		document.getElementById("flag").value = "false";
		var oldPassword = document.getElementById('oldpassword').value;
		var oldpassworddiv = document.getElementById('oldpassworddiv');
		var id = document.getElementById('id').value;
//		var reg = /^[A-Za-z0-9]+$/;
		if( $.trim(password) == "" ){
			oldpassworddiv.className = oldpassworddiv.className + " form-group has-error";
			return;
/*  		}else if (!reg.test($.trim(password))) {
			document.getElementById("flag").value = "false";
			oldpassworddiv.className = oldpassworddiv.className + " form-group has-error";
			$("#oldpassword").data("bValidators").oldpswdInst.showMsg($("#oldpassword"),"密码含有非法字符");
			return;
		}else if( password.length < 6 ){
			document.getElementById("flag").value = "false";
			oldpassworddiv.className = oldpassworddiv.className + " form-group has-error";
			$("#oldpassword").data("bValidators").oldpswdInst.showMsg($("#oldpassword"),"密码长度低于6位");
			return; */
		}else{
			$.ajax( {   
			     type : "POST",   
			     url : "<%=request.getContextPath()%>/password.do?method=checkOldPassword",   
			     dataType: "json",   
			     async : flag,
			     data:{oldPassword:oldPassword,id:id},
			     success : function(data) {   
			    	 if( data.result == "true" ){
			    		 document.getElementById("flag").value = "true";
			    		 oldpassworddiv.className = oldpassworddiv.className + " form-group has-success";
			    	 }else if(data.result == "false"){   
			        	 document.getElementById("flag").value = "false";
			        	 oldpassworddiv.className = oldpassworddiv.className + " form-group has-error";
			        	 $("#oldpassword").data("bValidators").oldpswdInst.showMsg($("#oldpassword"),"密码不正确");
			        	 return;
			         } 
			     } 
			 });
		}
	}
	
	
	function checkPassword(){
		document.getElementById("flag").value = "false";
		var password = document.getElementById('password').value;
		var passwordDiv = document.getElementById("passwordDiv");
		var reg = /^([a-zA-Z]+[0-9]+[!@#$%^&*]+)|([a-zA-Z]+[!@#$%^&*]+[0-9]+)|([0-9]+[!@#$%^&*]+[a-zA-Z]+)|([0-9]+[a-zA-Z]+[!@#$%^&*]+)|([!@#$%^&*]+[a-zA-Z]+[0-9]+)|([!@#$%^&*]+[0-9]+[a-zA-Z]+)$/;
		if( $.trim(password) == "" ){
			passwordDiv.className = passwordDiv.className + " form-group has-error";
			return;
		}else if (!reg.test($.trim(password))) {
			document.getElementById("flag").value = "false";
			$("#password").data("bValidators").pswdInst.showMsg($("#password"),"密码必须要包含字母、数字和!@#$%^&* 这几个特殊字符并且必须为8-20位之间");
			passwordDiv.className = passwordDiv.className + " form-group has-error";
			return;	
		}else{
			passwordDiv.className = passwordDiv.className + " form-group has-success";
			document.getElementById("flag").value = "true";
		}
	}
	
	function checkPasswordt(){
		document.getElementById("flag").value = "false";
		var password = document.getElementById("password").value;
		var checkpassword = document.getElementById("checkpassword").value;
		var checkpswddiv = document.getElementById("checkpswddiv");
		if ($.trim(password) == "") {
			document.getElementById("flag").value = "false";
			checkpswddiv.className = checkpswddiv.className + " form-group has-error";
			$("#passwordt").data("bValidators").checkpasswordInst.showMsg($("#passwordt"),"请先输入密码");
			return;
		}else if ( password != checkpassword ){
			document.getElementById("flag").value = "false";
			checkpswddiv.className = checkpswddiv.className + " form-group has-error";
			$("#checkpassword").data("bValidators").checkpasswordInst.showMsg($("#checkpassword"),"两次输入的密码不一致");
			return;
		}else{
			document.getElementById("flag").value = "true";
			checkpswddiv.className = checkpswddiv.className + " form-group has-success";
		}
	}
	
	
	function fromSubmit(){
		checkOldPassword(false);
		if( $("#flag").val() == "false" ){
			return;
		}
		$("#password").blur();
		if( $("#flag").val() == "false" ){
			return;
		}
		$("#checkpassword").blur();
		if( $("#flag").val() == "false" ){
			return;
		}
		var result = jQuery("#userForm").data("bValidators").formInstance.validate();
		if( result ){
			$("#userForm").submit();
		}
	}
	
	function removeCss( id ){
		document.getElementById(id).className = " right_inline ";
	}
	
	function result(){
		var result = document.getElementById("result").value;
		if( result == 0 ){
			alert("修改成功");
		}
	}
	
</script>
<body onload="result()" >
<form id="userForm" action="<%=request.getContextPath()%>/password.do?method=update" method="post" >
<input type="hidden" id="id" name="id" value="${user.id }" />
<input type="hidden" id="result" name="result" value="${result }" >
<div class="right_box" id="ll" ></div>
		<div class="right_h right_bg" >修改用户信息</div>
		<div class="right_box_l"></div>
		<input type="hidden" id="flag" value="false"/>
			<div id="namediv" class="right_inline">
				<div class="right_title">用户名</div>
				<div class="right_input" style="width:300px;">
				<input id="userName" readonly  name="userName" value="${user.userName }"  type="text"  class="form-control input-sm"></div>
			</div>
		
			<div id="oldpassworddiv" class="right_inline">
				<div class="right_title">原密码</div>
				<div class="right_input" style="width:300px;">
				<input id="oldpassword" onfocus="removeCss('oldpassworddiv')"  onblur="checkOldPassword(true)" name="oldPswd" data-bvalidator="required"  value="${user.oldPswd }"  type="password"   class="form-control input-sm" ></div>
			</div>
			
			<div id="passwordDiv" class="right_inline">
				<div class="right_title">新密码</div>
				<div class="right_input" style="width:300px;">
				<input id="password" onfocus="removeCss('passwordDiv')"  onblur="checkPassword()" data-bvalidator="required" name="passWord"  type="password"   class="form-control input-sm" ></div>
			</div>
			
			<div id="checkpswddiv" class="right_inline">
				<div class="right_title">确认密码</div>
				<div class="right_input" style="width:300px;">
				<input id="checkpassword" onfocus="removeCss('checkpswddiv')"  onblur="checkPasswordt()" data-bvalidator="required"  type="password"   class="form-control input-sm" ></div>
			</div>
			
			<input name="createTime" value="${user.createTime }" type="hidden" >
			<input name="name" value="${user.name }" type="hidden" >
			<input name="email" value="${user.email }" type="hidden" >
			<input name="orgId" value="${user.orgId }" type="hidden" >
			<input name="lockStatus" value="${user.lockStatus }" type="hidden" >
			
			<div class="right_inline">
			<div class="form-group">
				<div class="right_title">&nbsp;</div>
	    		<button class="btn btn-default" type="button" onclick="fromSubmit()" >提交</button>
				<button class="btn btn-default" type="button" onclick="javascript:history.go(-1)">返回</button>
  			</div>
		</div>
</form>
</body>