
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<%=request.getContextPath()%>/media/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">

<link rel="stylesheet" href="<%=request.getContextPath()%>/media/css/jquery.tooltip.css" type="text/css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.tooltip.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/media/js/bootstrap.min.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/bvalidator/js/jquery.bvalidator.js"></script>
<link href="<%=request.getContextPath()%>/bvalidator/css/bvalidator.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/bvalidator/css/bvalidator.theme.red.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.form-inline{margin:10px 0;}
.form-inline .form-title{display:block; float:left; padding:0 5px;  text-
align:right; line-height:34px;}
.rightbox{width:700px;}
	.select-box{height:70px; border:1px solid #ccc; overflow:hidden; overflow-y:scroll;}
	.select-box .select-items{position:relative; float:left; margin:5px 0 0 5px; padding:5px 10px; border:1px solid #ccc; cursor:pointer;}
	.select-box .select-items span{color:red; font-weight:bold;}
	.select-box .select-items i{display:none; position:absolute; top:0; right:0; width:10px; height:10px; line-height:10px; text-align:center; font-style:normal; background:#427fed; color:#FFFFFF;}
	.select-box .select-items-hover{border-color:#427fed;}
	.select-box .select-items-hover i{display:block;}
	.form-panel{overflow:hidden;}
	.form-panel .pull-left{margin-right:20px;}
</style>
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
	 
	 jQuery('#form').bValidator(options,'formInstance'); 
	  
});
function updateshoujia(){
 var id=document.getElementById("id").value;
 var jiage=document.getElementById("jiage").value;
  var resulta=jQuery('#form').data("bValidators").formInstance.validate(); 
  if(resulta){
  $.ajax({
    			type : "POST",  
    			dataType: "json",//返回json格式的数据
    			url:"<%=request.getContextPath()%>/goodsGroup.do?method=updateRetailPrice&id="+id+"&jiage="+jiage,
    			success: function(data){
    			
    	          $.each(data,function(i,result){  
	            	alert(result);
	            	 window.parent.location.reload();
	           // document.form.action = "goodsGroup.do?method=goodsGroupCopyList";
	               // document.form.submit();
	                
	            	//parent.$(".l-dialog,.l-window-mask").remove(); //关闭弹出层
	            	
	             }); 
    			}
    		});
    		}
  }
</script>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>套餐编辑</title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />


<body>
<form   name="form" id="form" method="post" enctype="multipart/form-data">

<input type="hidden" name="id" id="id" value="${id }"/>
<br>
<br>

销售价格:<input type="text" name="jiage" id="jiage" data-bvalidator="number" value="${jiage }"/>
<br>
<button type="button" onclick="updateshoujia()" class="btn btn-info btn-sm" style="margin:15px 15px 0 120px;" >保存</button>
</form>
</body>
	
     
	
</html>
