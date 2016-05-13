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
<title>修改角色</title>
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
		$('#remark').bValidator(options,'remarkInst');
		$("#roleForm").bValidator(options,"formInstance");

	});
	
	
	
	function checkName( flag ){
		var name = document.getElementById("name").value;
		var namediv = document.getElementById("namediv");
		var id = document.getElementById("id").value;
		var reg = /^[a-zA-Z][a-zA-Z0-9_]*$/;
		if( $.trim(name) == "" ){
			namediv.className = namediv.className + " form-group has-error ";
			return;
		}else if( name.substring(0,5) != "ROLE_" ){
			namediv.className = namediv.className + " form-group has-error ";
			document.getElementById("flag").value = "false";
			$('#name').data('bValidators').nameInst.showMsg($('#name'), '角色名称为"ROLE_"开头');
			return;
		}else if (!reg.test($.trim(name))) {
			namediv.className = namediv.className + " form-group has-error ";
			document.getElementById("flag").value = "false";
			$('#name').data('bValidators').nameInst.showMsg($('#name'), '角色名称只能为数字或者英文');
			return;
		}
		$.ajax( {   
		     type : "POST",   
		     url : "<%=request.getContextPath()%>/role.do?method=checkNameById&name=" + name + "&id=" + id,   
		     dataType: "json",   
		     async : flag,
		     success : function(data) {
		    	 if(data.result == "false"){   
		        	 namediv.className = namediv.className + " form-group has-error";
		        	 document.getElementById("flag").value = "false";
		        	 $("#name").data("bValidators").nameInst.showMsg($("#name"),"角色名称已经存在");
		        	 return;
		         }else if( data.result == "true" ){
		    		 document.getElementById("flag").value = "true";
		    		 namediv.className = namediv.className + " form-group has-success";
		    	 } 
		     } 
		 });
	}
	
	function checkReamrk(){
		var remark = document.getElementById("remark").value;
		var remarkdiv = document.getElementById("remarkdiv");
		if( $.trim(remark) == "" ){
			remarkdiv.className = remarkdiv.className + " form-group has-error";
			return;
		}else{
			document.getElementById("flag").value = "true";
			remarkdiv.className = remarkdiv.className + " form-group has-success";
		}
	}
	
	function fromSubmit(){
		checkName(false);
		if( $("#flag").val() == "false" ){
			return;
		}
		//$("#remark").blur();
		checkReamrk();
		if( $("#flag").val() == "false" ){
			return;
		}
		var result = jQuery("#roleForm").data("bValidators").formInstance.validate();
		if( result ){
			$("#roleForm").submit();	
		}
	}
	
	function removeCss( id ){
		document.getElementById(id).className = " right_inline ";
	}
	
</script>
</head>
<body>
	<form id="roleForm" action="<%=request.getContextPath()%>/role.do?method=update"
		method="post">
		<input type="hidden" id="id" name="id" value="${role.id }" />
		<div class="right_box" id="ll"></div>
		<div class="right_h right_bg">修改角色信息</div>
		<div class="right_box_l"></div>
		<input type="hidden" id="flag" value="false"/>
		<div id="namediv" class="right_inline">
			<div class="right_title">名称</div>
			<div class="right_input" style="width:300px;">
				<input id="name" name="name" onfocus="removeCss('namediv')"  data-bvalidator="minlength[7],maxlength[50],required" onblur="checkName(true)" value="${role.name }" type="text"
					class="form-control input-sm">
			</div>
		</div>
		<div id="platformdiv" class="right_inline">
			<div class="right_title">应用</div>
			<div class="right_input" style="width:300px;">
				<select name="platformId" style="height: 30px;" id="platformId" data-bvalidator="required,required" placeholder="所属应用平台" class="selectpicker form-control select_set">
					<c:forEach items="${apfls}" var="item" varStatus="status">
						<option value="${item.id}"}>${item.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div id="remarkdiv" class="right_inline">
			<div class="right_title">备注</div>
			<div class="right_input" style="width:300px;">
				<input id="remark" name="remark"  data-bvalidator="maxlength[100],required" onblur="checkReamrk()" class="form-control input-sm" value="${role.remark }" />
			</div>
		</div>
		
		<div class="form-group">
				<div class="right_title">&nbsp;</div>
	    		<button class="btn btn-default" type="button" onclick="fromSubmit()" >提交</button>
				<button class="btn btn-default" type="button" onclick="window.location.href='<%=request.getContextPath()%>/role.do?method=role'">返回</button>
  			</div>
	</form>
</body>