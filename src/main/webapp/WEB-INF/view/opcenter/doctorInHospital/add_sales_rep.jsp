<%@ page language="java" contentType="text/html; charset=utf-8"   pageEncoding="utf-8"%>
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
<title>添加销售代表和医院</title>
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

/* 使输入框中的data-bvalidator属性  起作用          */
$(document).ready(function(){
	jQuery('#salesCenter').bValidator(options,'salesCenterInst');
	jQuery('#hospitalName').bValidator(options,'hospitalNameInst');
	jQuery('#salesRepName').bValidator(options,'salesRepNameInst');
	jQuery('#office').bValidator(options,'officeInst');
	jQuery('#provincialCompany').bValidator(options,'provincialCompanyInst');
});
function removeCss( id ){
	document.getElementById(id).className = " right_inline ";
}
/* 删除左右两端的空格 */
function trim(str){ 
    return str.replace(/(^\s*)|(\s*$)/g, "");
}

/* 验证输入框       如果需要验证输入框内容为特殊格式， 需要参照以下代码*/
 	//验证销售中心
/* function isSalesCenter(flag) {
	var namediv = document.getElementById("namediv");
	var str_salesCenter=trim($("#salesCenter").val());
	var strRegex = "[^\s]{1,}";
	var re = new RegExp(strRegex);
	if(!re.test(str_salesCenter)){
		document.getElementById("flag").value = "false";
		namediv.className = namediv.className + " form-group has-error"; 
		$("#salesCenter").data("bValidators").salesCenterInst.showMsg($("#salesCenter"),"销售中心不能为空！");
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
*/

/* 添加医药销售代表 */
function saveSalesRep(){
	//验证销售中心
	var str_salesCenter=trim($("#salesCenter").val());
	if(str_salesCenter==null||""==str_salesCenter){
		$("#salesCenter").data("bValidators").salesCenterInst.showMsg($("#salesCenter"),"销售中心不能为空！");
		return;
	}
	//验证医院名称
	var str_hospitalName=trim($("#hospitalName").val());
	if(str_hospitalName==null||""==str_hospitalName){
		$("#hospitalName").data("bValidators").hospitalNameInst.showMsg($("#hospitalName"),"医院名称不能为空！");
		return;
	}
	//验证代表名称
	var str_salesRepName=trim($("#salesRepName").val());
	if(str_salesRepName==null||""==str_salesRepName){
		$("#salesRepName").data("bValidators").salesRepNameInst.showMsg($("#salesRepName"),"销售代表不能为空！");
		return;
	}	
	//验证办事处
	var str_office=trim($("#office").val());
	if(str_office==null||""==str_office){
		$("#office").data("bValidators").officeInst.showMsg($("#office"),"办事处不能为空！");
		return;
	}	
	//验证省公司
	var str_provincialCompany=trim($("#provincialCompany").val());
	if(str_provincialCompany==null||""==str_provincialCompany){
		$("#provincialCompany").data("bValidators").provincialCompanyInst.showMsg($("#provincialCompany"),"办事处不能为空！");
		return;
	}	
	$("#userForm").submit();
}
</script>
</head>
<body>
<form id="userForm" action="<%=request.getContextPath()%>/doctorInHospital.do?method=saveSalesRep" method="post" >
<div class="right_box" id="ll" ></div>
		<div class="right_h right_bg" >创建销售代表和医院</div>
		<div class="right_box_l"></div>
		<input type="hidden" id="flag" value="false"/>		
			<div id="salesCenterdiv" class="right_inline">
				<div class="right_title">销售中心</div>
				<div class="right_input" style="width:300px;">
				<input name="salesCenter" id="salesCenter"  data-bvalidator="maxlength[20],required"  type="text"  class="form-control input-sm" onfocus="removeCss('salesCenterdiv')" ></div>
			</div>			
			<div id="hospitalNamediv" class="right_inline">
				<div class="right_title">医院名称</div>
				<div class="right_input" style="width:300px;"><input  id="hospitalName" name="hospitalName"  type="text" data-bvalidator="maxlength[20],required" onfocus="removeCss('hospitalNamediv')" class="form-control input-sm" ></div>
			</div>
			<div id="salesRepNamediv" class="right_inline">
				<div class="right_title">代表名称</div>
				<div class="right_input" style="width:300px;"><input  id="salesRepName" name="salesRepName"  type="text" data-bvalidator="maxlength[20],required"  class="form-control input-sm" ></div>
			</div>
			<div id="officediv" class="right_inline">
				<div class="right_title">办事处</div>
				<div class="right_input" style="width:300px;"><input  id="office" name="office"  type="text" data-bvalidator="maxlength[50],required"   class="form-control input-sm" ></div>
			</div>
				<div id="provincialCompanydiv" class="right_inline">
				<div class="right_title">省公司</div>
				<div class="right_input" style="width:300px;"><input   id="provincialCompany" name="provincialCompany"  type="text" data-bvalidator="maxlength[50],required"   class="form-control input-sm" ></div>
			</div>
		
			<div class="form-group">
				<div class="right_title">&nbsp;</div>
	    		<button class="btn btn-default" onclick="saveSalesRep()" type="button" >提交</button>
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