<%@ page language="java" contentType="text/html; charset=utf-8"   pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>修改医生url</title>
<script type="text/javascript">

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
	jQuery('#url').bValidator(options,'urlInst');

});

function isURL(flag) {// 验证url
	var urldiv = document.getElementById("urldiv");
	var str_url=$("#url").val();
	var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
	+ "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp的user@
	+ "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
	+ "|" // 允许IP和DOMAIN（域名）
	+ "([0-9a-z_!~*'()-]+\.)*" // 域名- www.
	+ "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
	+ "[a-z]{2,6})" // first level domain- .com or .museum
	+ "(:[0-9]{1,4})?" // 端口- :80
	+ "((/?)|" // a slash isn't required if there is no file name
	+ "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
	var re = new RegExp(strRegex);
	if(!re.test(str_url)){
		document.getElementById("flag").value = "false";
		urldiv.className = urldiv.className + " form-group has-error";
		$("#url").data("bValidators").urlInst.showMsg($("#url"),"url地址不合法");
		return;
		
	}else{
		document.getElementById("flag").value = "true";
	}
	} 
	
function fromSubmit(){
	
	$("#url").blur();
	if( $("#flag").val() == "false" ){
		return;
	}
	else{
		$("#userForm").submit();	
	}
	
	
} 	 
function removeCss( id ){
	document.getElementById(id).className = " right_inline ";
} 	
</script>
</head>
<body>
<form id="userForm" action="<%=request.getContextPath()%>/doctorInHospital.do?method=saveUpdateDoctorUrl" method="post" >
<div class="right_box" id="ll" ></div>
		<div class="right_h right_bg" >修改医生url</div>
		<div class="right_box_l"></div>
		<input type="hidden" id="flag" value="false"/>		
		<input id="id" name="id" type="hidden" value="${doctorUrlModel.id} " />
		<input id="createTime" name="createTime" type="hidden" value="${doctorUrlModel.createTime} " />
			<div id="urldiv" class="right_inline">
				<div class="right_title">医生药品url</div>
				<div class="right_input" style="width:300px;"><input  id="url" name="url"  value="${doctorUrlModel.url}" type="text" data-bvalidator="maxlength[100],required"  onfocus="removeCss('urldiv')" onblur="isURL(true)" class="form-control input-sm" ></div>
			</div>
			<div id="checkpswddiv" class="right_inline">
				<div class="right_title">医生名称</div>
				<div class="right_input" style="width:300px;">
				<select id="doctorId" name="doctorId"  class="form-control input-sm" >
					<c:forEach items="${doctorList }" var="org" >
						<c:choose>
						<c:when test="${doctorUrlModel.doctorId==org.id}" >
						<option value="${org.id }" selected="selected" >${org.doctorName }</option>
						</c:when>
						<c:otherwise>
             		  <option value="${org.id }"  >${org.doctorName }</option>
        		   </c:otherwise>
						</c:choose>
						
					</c:forEach>
					
					</select>
					</div>
			</div>
	
		
			<div class="form-group">
				<div class="right_title">&nbsp;</div>
	    		<button class="btn btn-default" onclick="fromSubmit()" type="button" >提交</button>
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