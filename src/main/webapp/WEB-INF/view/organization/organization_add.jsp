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
		$("#orgForm").bValidator(options,"formInstance");

	});
	
	function checkName( flag ){
		var name = document.getElementById("name").value;
		var namediv = document.getElementById("namediv");
		var reg = /^[\u4E00-\u9FA5A-Za-z0-9_]+$/;
		if( $.trim(name) == "" ){
			namediv.className = namediv.className + " form-group has-error ";
			return;
		}else if (!reg.test($.trim(name))) {
			namediv.className = namediv.className + " form-group has-error ";
			document.getElementById("flag").value = "false";
			$('#name').data('bValidators').nameInst.showMsg($('#name'), '机构名称含有非法字符');
			return;
		}
		name = encodeURI(name);
		name = encodeURI(name);
		$.ajax( {   
		     type : "POST",   
		     url : "<%=request.getContextPath()%>/org.do?method=checkName&name=" + name,   
		     dataType: "json",   
		     async : flag,
		     success : function(data) {   
		    	 if(data.result == "false"){   
		        	 namediv.className = namediv.className + " form-group has-error";
		        	 document.getElementById("flag").value = "false";
		        	 $('#name').data("bValidators").nameInst.showMsg($('#name'),'机构名称已经存在');
		        	 return;
		         }else if( data.result == "true" ){
		    		 document.getElementById("flag").value = "true";
		    		 namediv.className = namediv.className + " form-group has-success";
		    	 }
		     } 
		 });
	}
	
	
	function fromSubmit(){
		checkName(false);
		if( $("#flag").val() == "false" ){
			return;
		}
		var result = jQuery("#orgForm").data("bValidators").formInstance.validate();
		if( result ){
			$("#orgForm").submit();	
		}
	}
	
	function removeCss( id ){
		document.getElementById(id).className = " right_inline ";
	}
	
</script>
<title>添加机构</title>
</head>
<body>
<form id="orgForm" action="<%=request.getContextPath()%>/org.do?method=save" method="post">
<div class="right_box" id="ll"></div>
		<div class="right_h right_bg">创建机构信息</div>
		<div class="right_box_l"></div>
		<input type="hidden" id="flag" value="false"/>
		<div id="code" class="right_inline">
			<div class="right_title">机构代码</div>
			<div class="right_input" style="width:300px;">
				<input name="code"  readonly value="${code }"   type="text" class="form-control input-sm">
			</div>
		</div>

		<div id="namediv" class="right_inline">
			<div class="right_title">机构名称</div>
			<div class="right_input" style="width:300px;">
				<input name="name" onfocus="removeCss('namediv')" data-bvalidator="maxlength[10],required" id="name"  onblur="checkName(true)" type="text"  class="form-control input-sm">
			</div>
		</div>
		
		<div class="right_inline">
			<div class="right_title">负责人</div>
			<div class="right_input" style="width:300px;">
				<input name="manager"   type="text" maxlength="20"  class="form-control input-sm">
			</div>
		</div>

		<div class="right_inline">
			<div class="right_title">备注</div>
			<div class="right_input" style="width:300px;">
				<textarea name="remark"  maxlength="100" rows="3" lang="" class="form-control" ></textarea>
			</div>
		</div>

		<div class="form-group">
				<div class="right_title">&nbsp;</div>
	    		<button class="btn btn-default" type="button" onclick="fromSubmit()" >提交</button>
				<button class="btn btn-default" type="button" onclick="window.location.href='<%=request.getContextPath()%>/org.do?method=organization'">返回</button>
  			</div>
</form>
</body>