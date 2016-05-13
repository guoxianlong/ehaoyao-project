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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap/bootstrap-typeahead.js"></script>
<link href="<%=request.getContextPath()%>/bvalidator/css/bvalidator.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/bvalidator/css/bvalidator.theme.red.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加医生url</title>
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
	jQuery('#orgName').bValidator(options,'orgNameInst');
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
	var  doctorListJson = '${doctorListJson}';
	var  doctorList=JSON.parse(doctorListJson);
	var  inputId=$("#orgId").val();
	for (var i = 0; i < doctorList.length; i++) {
		if(doctorList[i].id==inputId){
			$("#userForm").submit();
			return;
		}
	}
	$("#orgName").data("bValidators").orgNameInst.showMsg($("#orgName"),"医生姓名不正确！请输入部分关键字后,选取医生列表中的医生才能添加！");
	return;			
} 	 
function removeCss( id ){
	document.getElementById(id).className = " right_inline ";
}
</script>
</head> 
<body>
<form id="userForm" action="<%=request.getContextPath()%>/doctorInHospital.do?method=saveDoctorUrl" method="post" >
<div class="right_box" id="ll"></div>
		<div class="right_h right_bg" >添加医生url</div>
		<div class="right_box_l"></div>
		<input type="hidden" id="flag" value="false"/>				
		<div id="urldiv" class="right_inline"> 
			<div class="right_title">医生药品url</div>
			<div class="right_input" style="width:300px;"><input  id="url" name="url"  type="text" data-bvalidator="maxlength[100],required" onfocus="removeCss('urldiv')" onblur="isURL(true)" class="form-control input-sm" ></div>
		</div>
		<div id="namediv" class="right_inline">
			<div class="right_title">医生名称</div>
			<div class="right_input" style="width:300px;">
		<%-- 	<select id="orgId" name="orgId"  class="form-control input-sm"  >
				<c:forEach items="${doctorList }" var="org" >
					<option value="${org.id }" >${org.doctorName }</option>
				</c:forEach>
				</select> --%>
				<input  id="orgName" name="orgName" onchange="cleanOrgId()" type="text" data-provide="typeahead" class="form-control input-sm" >
				<input type="hidden" id="orgId" name="orgId" >
			</div>
		</div>
		
		
		<div class="form-group">
			<div class="right_title">&nbsp;</div>
		  		<button class="btn btn-default" onclick="fromSubmit()" type="button" >提交</button>
		<!-- 	<button class="btn btn-default" type="button" onclick="window.location.href='<%=request.getContextPath()%>/doctorInHospital.do?method=user'">返回</button>  -->
			</div>
		<div style="height: 500px"></div>
</form>
</body>
<script type="text/javascript">
	$(function(){
		setTimeout(function(){
			$("#password").attr('type','password') ;
			$("#passwordt").attr('type','password') ;
		}, 10) ;
	}) ;
	/* 自动补全 医生姓名的方法 */
	$(function(){
	    var mySource = '${doctorListJson}';
	    mySource=JSON.parse(mySource);
        $('#orgName').typeahead({
            source: mySource,
            display: "doctorName",   // 默认的对象属性名称为 name 属性
            val: "id",           // 默认的标识属性名称为 id 
            items: 8,
            itemSelected: function (item, val, text) {      // 当选中一个项目的时候，回调函数
                console.info(item);
                // console.info($("#product_search").val());
                $("#orgName").val(text);
               	$("#orgId").val(val);
            }
        });
	}) ;
	//清空orgId的value值
	function cleanOrgId(){
		$("#orgId").val("");
	}
</script>