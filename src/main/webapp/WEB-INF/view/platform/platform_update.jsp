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
<title>平台修改</title>
</head>

<script type="text/javascript">
	function check() {
		var pname = document.form.platformName.value.replace(/\s+/g, "");//整体为如果以一个或多个空格开始，替换全部空格为空
		var pcode = document.form.platformCode.value.replace(/\s+/g, "");
		if (pcode.length == 0) {
			alert("编码不能为空");
			return false;
		}
		if (pname.length == 0) {
			alert("名称不能为空");
			return false;
		}
		
		if (pname.length >12||pname.length <2) {
			alert("名称必须是2-12位");
			return false;
		}
		
		var re=/^(\w){2,12}$/;
		if (!re.test(pcode)) {
			alert("编码只能由数字、字母、下划线组成，并且长度只能为2-12位");
			return false;
		}
	}
</script>

<body>
	<form method="post" name="form" action="<%=request.getContextPath()%>/platform.do?method=update&i=${salesPlatform.id}">;
       
		<div class="right_box" id="ll">
		<div class="right_h right_bg" >平台的修改</div>
		<div class="right_box_l">
			<div class="right_inline">
				<div class="right_title">名称</div>
				<div class="right_input" style="width:300px;"><input id="platformName" name="platformName" type="text"  class="form-control input-sm" value="${salesPlatform.platformName}"></div>
			</div>
			
			<div class="right_inline">
				<div class="right_title">编码</div>
				<div class="right_input" style="width:300px;"><input id="platformCode" name="platformCode" type="text" class="form-control input-sm" value="${salesPlatform.platformCode}"></div>
			</div>
			
			<div>
				<input class="btn btn-warning btn-xs" type="submit" value="提交" onclick="return check();" id="tj">
				<input class="btn btn-warning btn-xs" type="button" onclick="javascript:history.go(-1);" value="返回">
			</div>
		</div>
	</div>
	</form>
</body>
</html>
