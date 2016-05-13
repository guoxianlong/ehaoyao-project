<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<%=request.getContextPath()%>/media/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/jquery.tooltip.css" type="text/css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.tooltip.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<style>
	#ll{
		height:500px;
		border:1 solid black;
	}
</style>
<script type="text/javascript">
function check() {
	var className = document.form.labelName.value.replace(/\s+/g, "");
	var classCode = document.form.labelCode.value.replace(/\s+/g, "");

	if (classCode == "") {
		alert("编码不能为空");
		return false;
	}
	if (classCode.length >15||classCode.length<2) {
		alert("编码的长度必须为2-15位");
		return false;
	}
	if (className == "") {
		alert("名称不能为空");
		return false;
	}
	if (className.length >15||className.length <2) {
		alert("名称的长度必须为2-15位");
		return false;
	}
}
</script>
<body>
<form name="form" method="post" action="<%=request.getContextPath()%>/labels.do?method=update">
	<div class="right_box" id="ll">
		<div class="right_h right_bg" >标签分类信息修改</div>
		<div class="right_box_l">
			<div class="right_input" style="width:300px;"><input id="id" name="id" type="hidden" value="${labelsClass.id}" class="form-control input-sm"></div>
			
			<div class="right_inline">
				<div class="right_title">名称</div>
				<div class="right_input" style="width:300px;"><input id="labelName" name="labelName" type="text" value="${labelsClass.labelName}" class="form-control input-sm"></div>
			</div>
			
			<div class="right_inline">
				<div class="right_title">编码</div>
				<div class="right_input" style="width:300px;"><input id="labelCode" name="labelCode" type="text" value="${labelsClass.labelCode}" class="form-control input-sm" onblur="checkClassCode(this.value)"></div>
			</div>
			
			<div class="right_inline">
				<div class="right_title">层级</div>
				<div class="right_input" style="width:300px;"><input id="labelLevel" name="labelLevel" type="text" value="${labelsClass.labelLevel}" class="form-control input-sm" readonly="readonly"></div>
			</div>
			
			<div class="right_inline">
				<div class="right_title">父节点</div>
				<div class="right_input" style="width:300px;">
					
					<c:choose>
					
						<c:when test="${labelsClass.labelLevel==1}">
							<input type="hidden" id="parentId" name="parentId" value="0">
							<input type="text" value="系统根节点" class="form-control input-sm" readonly="readonly">
						</c:when>
						
						<c:otherwise>
							<select name="parentId" class="form-control input-sm">
								<c:forEach items="${list}" var="goods">
									<c:choose>
										<c:when test="${goods.id == labelsClass.parentId}">
												<option selected="selected" value="${goods.id}">${goods.labelName}</option>
										</c:when>
										
										<c:otherwise>
												<option value="${goods.id}">${goods.labelName}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</c:otherwise>
					
					</c:choose>
					
					
					
					
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
	var id = document.getElementById("id").value;
	$.ajax({ 
		type: "POST",
		url: "<%=request.getContextPath()%>/labels.do?method=cc&id="+id,
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