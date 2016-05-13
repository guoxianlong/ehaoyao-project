<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<%=request.getContextPath()%>/media/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/jquery.tooltip.css" type="text/css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.tooltip.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生添加</title>
</head>

<script type="text/javascript">
	function check() {
		var className = document.form.className.value.replace(/\s+/g, "");
		var classCode = document.form.classCode.value.replace(/\s+/g, "");
		var classDesc = document.form.classDesc.value.replace(/\s+/g, "");

		if (className.length == 0) {
			alert("名称不能为空");
			return false;
		}
		if (className.length >15||className.length <2) {
			alert("名称的长度必须为2-15位");
			return false;
		}
		if (classCode.length == 0) {
			alert("编码不能为空");
			return false;
		}
		if (classCode.length >15||classCode.length<2) {
			alert("编码的长度必须为2-15位");
			return false;
		}
		if (classDesc.length == 0) {
			alert("表述不能为空");
			return false;
		}
		if (classDesc.length >20) {
			alert("表述不能超过20个字符");
			return false;
		}
}
</script>

<body>
	<form method="post" name="form" action="<%=request.getContextPath()%>/front.do?method=save1&id=${parentId}">

		<div class="right_box" id="ll">
		<div class="right_h right_bg" >前端分类子节点信息创建</div>
		<div class="right_box_l">
			<div class="right_inline">
				<div class="right_title">名称</div>
				<div class="right_input" style="width:300px;"><input id="className" name="className" type="text"  class="form-control input-sm"></div>
			</div>
			
			<div class="right_inline">
				<div class="right_title">编码</div>
				<div class="right_input" style="width:300px;"><input id="classCode" name="classCode" type="text"  class="form-control input-sm" onblur="checkClassCode(this.value)"></div>
			</div>
			
			<div class="right_inline">
				<div class="right_title">表述</div>
				<div class="right_input" style="width:300px;"><input id="classDesc" name="classDesc" type="text" class="form-control input-sm"></div>
			</div>
			
			<div class="right_inline">
				<div class="right_title">层级</div>
				<div class="right_input" style="width:300px;"><input id="classLevel" name="classLevel" type="text" value="${frontClass.classLevel + 1}" class="form-control input-sm" readonly="readonly"></div>
			</div>
			
			<div class="right_inline">
				<div class="right_title">父节点</div>
				<div class="right_input" style="width:300px;">
					<input type="hidden" name="parentId" value="${parentId}" />
					<input id="parentId" name="" type="text" value="${frontClass.className}" class="form-control input-sm" readonly="readonly">
				</div>
			</div>
			
			<div>
				<input class="btn btn-warning btn-xs" type="submit" value="提交" onclick="return check();" id="tj">
				<input class="btn btn-warning btn-xs" type="button" onclick="javascript:history.go(-1);" value="返回">
			</div>
		</div>
	</div>
	</form>
</body>
<script type="text/javascript">
function checkClassCode(val){
	$.ajax({ 
		type: "POST",
		url: "<%=request.getContextPath()%>/front.do?method=cc",
		data: "name="+val,
		context: document.body, 
		success: function(data){
			if(data=='1'){
				alert("编码已存在！");
				document.getElementById("tj").disabled=true;
			}else{
				document.getElementById("tj").disabled=false;
			}
      	}
	});
}
</script>
</html>
