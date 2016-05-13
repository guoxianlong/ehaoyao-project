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
		$('#name').bValidator(options,'nameInst');
		$("#permForm").bValidator(options,"formInstance");

	});
	
	
	
	function checkName( flag ){
		var name = document.getElementById("name").value;
		var id = document.getElementById("id").value;
		var namediv = document.getElementById("namediv");
		var reg = /^[\u4E00-\u9FA5A-Za-z0-9_]+$/;
		if( $.trim(name) == "" ){
			namediv.className = namediv.className + " form-group has-error ";
			return;
		}else if (!reg.test($.trim(name))) {
			namediv.className = namediv.className + " form-group has-error ";
			document.getElementById("flag").value = "false";
			$('#name').data('bValidators').nameInst.showMsg($('#name'), '应用平台名称含有非法字符');
			return;
		}
		name = encodeURI(name);
		name = encodeURI(name);
		$.ajax( {   
		     type : "POST",   
		     url : "<%=request.getContextPath()%>/applyPlatform.do?method=checkNameById&name=" + name + "&id=" + id,   
		     dataType: "json",   
		     async : flag,
		     success : function(data) {   
		    	 if( data.result == "true" ){
		    		 document.getElementById("flag").value = "true";
		    		 namediv.className = namediv.className + " form-group has-success";
		    	 }else if(data.result == "false"){   
		        	 namediv.className = namediv.className + " form-group has-error";
		        	 document.getElementById("flag").value = "false";
		        	 $('#name').data('bValidators').nameInst.showMsg($('#name'),'应用平台名称已经存在');
		        	 return;
		         } 
		     } 
		 });
	}
	
	function permFormSubmit(){
		checkName(false);
		if( $("#flag").val() == "false" ){
			return;
		}
		var result = jQuery("#permForm").data("bValidators").formInstance.validate();
		if( result ){
			$("#permForm").submit();	
		}
	}
	
	function removeCss( id ){
		document.getElementById(id).className = " right_inline ";
	}
	
</script>
<title>修改应用平台信息</title>
</head>
<body>
	<form id="permForm" action="<%=request.getContextPath()%>/applyPlatform.do?method=update"
		method="post">
		<input id="id" type="hidden" name="id" data-bvalidator="maxlength[200],required" value="${apf.id }" />
		<div class="right_box" id="ll"></div>
		<div class="right_h right_bg">修改应用平台信息</div>
		<div class="right_box_l"></div>
		<input type="hidden" id="flag" value="false" />
		<div id="namediv" class="right_inline">
			<div class="right_title">名称</div>
			<div class="right_input" style="width:300px;">
				<input id="name" name="name" onfocus="removeCss('namediv')" onblur="checkName(true)"  data-bvalidator="maxlength[50],required" value="${apf.name }" type="text"
					class="form-control input-sm">
			</div>
		</div>
		
		<div id="urldiv" class="right_inline">
			<div class="right_title">url</div>
			<div class="right_input" style="width:300px;">
				<input id="url" name="url" onfocus="removeCss('urldiv')" onblur="checkUrl(true)"  class="form-control input-sm" value="${apf.url }" />
			</div>
		</div>

		<div id="urldiv" class="right_inline">
			<div class="right_title">说明</div>
			<div class="right_input" style="width:300px;">
				<input id="comment" name="comment" class="form-control input-sm" value="${apf.comment }" />
			</div>
		</div>

		<div class="form-group">
				<div class="right_title">&nbsp;</div>
	    		<button class="btn btn-default" type="button" onclick="permFormSubmit()" >提交</button>
				<button class="btn btn-default" type="button" onclick="window.location.href='<%=request.getContextPath()%>/applyPlatform.do?method=getApplyPlatformList'">返回</button>
  			</div>
	</form>
</body>