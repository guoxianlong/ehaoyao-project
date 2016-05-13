<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<%=request.getContextPath()%>/media/css/bootstrap.min.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/style.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/jquery.tooltip.css"
	type="text/css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.tooltip.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>机构详细信息</title>
</head>
<body>
	<form action="<%=request.getContextPath()%>/org.do?method=update"
		method="post">
		<div class="right_box" id="ll"></div>
		<div class="right_h right_bg">机构详细信息</div>
		<div class="right_box_l"></div>
		<input type="hidden" name="id" id="id" value="${org.id }" />
		<div class="right_inline">
			<div class="right_title">机构代码</div>
			<div class="right_input" style="width:300px;">
				<input name="code"  type="text" value="${org.code }" disabled class="form-control input-sm">
			</div>
		</div>

		<div class="right_inline">
			<div class="right_title">机构名称</div>
			<div class="right_input" style="width:300px;">
				<input name="name"  type="text" value="${org.name }" disabled  class="form-control input-sm">
			</div>
		</div>
		
		<div class="right_inline">
			<div class="right_title">负责人</div>
			<div class="right_input" style="width:300px;">
				<input name="manager"  type="text" value="${org.manager }" disabled  class="form-control input-sm">
			</div>
		</div>

		<div class="right_inline">
			<div class="right_title">备注</div>
			<div class="right_input" style="width:300px;">
				<textarea name="remark" rows="3" class="form-control" disabled >${org.remark }</textarea>
			</div>
		</div>

		<div class="form-group">
				<div class="right_title">&nbsp;</div>
				<button class="btn btn-default" type="button" onclick="window.location.href='<%=request.getContextPath()%>/org.do?method=organization'">返回</button>
  			</div>
	</form>
</body>