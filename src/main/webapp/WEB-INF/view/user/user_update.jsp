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
<title>修改用户</title>
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
		jQuery('#userName').bValidator(options,'nameInst');
		jQuery('#password').bValidator(options,'pswdInst');
		jQuery('#email').bValidator(options,'emailInst');
		jQuery("#userForm").bValidator(options,"formInstance");

	});
	
	function checkName( flag ){
		document.getElementById("flag").value = "false";
		var userName = document.getElementById("userName").value;
		var userId = document.getElementById("id").value;
		var userDiv = document.getElementById("namediv");
		var reg = /^[A-Za-z0-9]+$/;
		if( $.trim(userName) == "" ){
			return;
		}
		if( $.trim(userName).length != userName.length ){
			userDiv.className = userDiv.className + " form-group has-error ";
			document.getElementById("flag").value = "false";
			jQuery('#userName').data('bValidators').nameInst.showMsg(jQuery('#userName'), '你输入的用户名含有空格');
			return;
		}
		if (!reg.test($.trim(userName))) {
			userDiv.className = userDiv.className + " form-group has-error ";
			document.getElementById("flag").value = "false";
			jQuery('#userName').data('bValidators').nameInst.showMsg(jQuery('#userName'), '用户名称只能为数字或字母');
			return;
		}
		$.ajax( {   
		     type : "POST",   
		     url : "<%=request.getContextPath()%>/user.do?method=checkUserNameById&userName=" + userName + "&id=" + userId,   
		     dataType: "json",   
		     async : flag,
		     success : function(data) {   
		    	 if( data.result == "true" ){
		    		 document.getElementById("flag").value = "true";
		    		 userDiv.className = userDiv.className + " form-group has-success";
		    	 }else if(data.result == "false"){   
		        	 userDiv.className = userDiv.className + " form-group has-error";
		        	 document.getElementById("flag").value = "false";
		        	 $("#userName").data("bValidators").nameInst.showMsg($("#userName"),"用户名已经存在");
		        	 return;
		         } 
		     } 
		 });
	}
	
	function checkpassword(){
		document.getElementById("flag").value = "false";
		var password = document.getElementById("password").value;
		var userDiv = document.getElementById("pswddiv");
		var reg = /^([a-zA-Z]+[0-9]+[!@#$%^&*]+)|([a-zA-Z]+[!@#$%^&*]+[0-9]+)|([0-9]+[!@#$%^&*]+[a-zA-Z]+)|([0-9]+[a-zA-Z]+[!@#$%^&*]+)|([!@#$%^&*]+[a-zA-Z]+[0-9]+)|([!@#$%^&*]+[0-9]+[a-zA-Z]+)$/;
		if( $.trim(password) == "" ){
			$("#password").data("bValidators").pswdInst.showMsg($("#password"),"此字段为必填");
			return;
		}else if (!reg.test($.trim(password))) {
			userDiv.className = userDiv.className + " form-group has-error";
			document.getElementById("flag").value = "false";
			$("#password").data("bValidators").pswdInst.showMsg($("#password"),"密码必须要包含字母、数字和!@#$%^&* 这几个特殊字符并且必须为8-20位之间");
			return;
/* 		}else if( password.length < 6 ){
			userDiv.className = userDiv.className + " form-group has-error";
			document.getElementById("flag").value = "false";
			$("#password").data("bValidators").pswdInst.showMsg($("#password"),"密码长度低于6位");
			return; */
		}else{
			document.getElementById("flag").value = "true";
			userDiv.className = userDiv.className + " form-group has-success";
		}
	}
	
	
	function checkEmail(){
		document.getElementById("flag").value = "false";
		var email = document.getElementById("email").value;
		var userDiv = document.getElementById("emaildiv");
		var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-]))+(\.+[com|cn]{2,3})+$/;
		if( $.trim(email) == "" ){
			return;
		}else if ( !reg.test(email) ){
			document.getElementById("flag").value = "false";
			userDiv.className = userDiv.className + " form-group has-error";
			$("#email").data("bValidators").emailInst.showMsg($("#email"),"请输入正确的邮箱");
			return;
		}else{
			userDiv.className = userDiv.className + " form-group has-success";
			document.getElementById("flag").value = "true";
		}
	}
	
	function fromSubmit(){
		checkName(false);
		if( $("#flag").val() == "false" ){
			return;
		}
		$("#password").blur();
		if( $("#flag").val() == "false" ){
			return;
		}
		$("#email").blur();
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
	
</script>
</head>
<body>
<form id="userForm" action="<%=request.getContextPath()%>/user.do?method=update" method="post" >
<input type="hidden" id="id" name="id" value="${id }" />
<div class="right_box" id="ll" ></div>
		<div class="right_h right_bg" >修改用户信息</div>
		<div class="right_box_l"></div>
		<input type="hidden" id="flag" value="false"/>
			<div id="namediv" class="right_inline">
				<div class="right_title">用户名</div>
				<div class="right_input" style="width:300px;"><input onfocus="removeCss('namediv')" id="userName"    onblur="checkName(true)"  data-bvalidator="minlength[3],maxlength[20],required"  name="userName" value="${userName }"  type="text"  class="form-control input-sm"></div>
			</div>
			
			<div id="namediv" class="right_inline">
				<div class="right_title">姓名</div>
				<div class="right_input" style="width:300px;"><input onfocus="removeCss('namediv')" id="name"    onblur="checkName(true)"  data-bvalidator="minlength[2],maxlength[20],required"  name="name" value="${name }"  type="text"  class="form-control input-sm"></div>
			</div>
		
			<div id="pswddiv" class="right_inline">
				<div class="right_title">密码</div>
				<div class="right_input" style="width:300px;"><input onfocus="removeCss('pswddiv')" id="password"  onblur="checkpassword()" data-bvalidator="required" name="passWord"  type="text" value="${oldPswd }"  class="form-control input-sm" ></div>
			</div>
			
			<input name="createTime" value="${createTime }" type="hidden" >
			
			<div id="emaildiv" class="right_inline">
				<div class="right_title">邮箱</div>
				<div class="right_input" style="width:300px;"><input onfocus="removeCss('emaildiv')" id="email"  onblur="checkEmail()"  data-bvalidator="minlength[6],maxlength[50],required" name="email" value="${email }" type="text"  class="form-control input-sm" ></div>
			</div>
			
			<div  class="right_inline">
				<div class="right_title">机构</div>
				<div class="right_input" style="width:300px;" >
					<select class="form-control" id="orgId" name="orgId">
							<c:forEach items="${orgList }" var="org">
								<c:choose>
									<c:when test="${orgId == org.id }">
										<option value="${org.id }" selected>${org.name }</option>	
									</c:when>
									<c:otherwise>
										<option value="${org.id }" >${org.name }</option>	
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
				</div>
			</div>
			
			<div class="right_inline">
				<div class="right_title">状态</div>
				<div class="right_input" style="width:300px;">
				<div class="right_input" style="width:300px;">
				<c:choose>
					<c:when test="${lockStatus == 0 }">
					<input name="lockStatus" value="0" type="radio" checked="checked"   />启用
					&nbsp;
					<input name="lockStatus" value="1" type="radio"  />锁定
					</c:when>
					<c:otherwise>
						<input name="lockStatus" value="0" type="radio"   >启用
						&nbsp;
					<input name="lockStatus" value="1"  type="radio" checked="checked"  >锁定
					</c:otherwise>
				</c:choose>
				
			</div>
			
			<div class="form-group">
				<div class="right_title">&nbsp;</div>
	    		<button class="btn btn-default" type="button" onclick="fromSubmit()" >提交</button>
				<button class="btn btn-default" type="button" onclick="window.location.href='<%=request.getContextPath()%>/user.do?method=user'">返回</button>
  			</div>
		</div>
	</div>
</form>
</body>
<script type="text/javascript">
	$(function(){
		setTimeout(function(){
			$("#password").attr('type','password') ;
		}, 10) ;
	}) ;
</script>