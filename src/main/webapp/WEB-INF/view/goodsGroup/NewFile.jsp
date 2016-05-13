<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="<%=request.getContextPath()%>/media/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/jquery.tooltip.css" type="text/css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.3.js"></script> 
<script src="<%=request.getContextPath()%>/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script language="javascript">

//角色
function roleList() {

	jQuery.ligerDialog.open({ 
		isDrag:false,//是否拖动
        showMax:true,//是否显示最大化按钮
		height: 500, 
		width:1000,
		slide:true,
		title:'请分配对应的角色', 
		//url: '${basePath}/renyRoleYList.do?id=' + id 
		url: '<%=request.getContextPath()%>/goodsp.do?method=getGoodsLists' 
		
	});  
}
$(document).ready(function(){
	 $("#delbuton").click(function(){
		 alert();
	 });
});
function aa(){
	var intHot =document.getElementById("inputid").value;
	alert(intHot);
}
function getDel(k){
	var row=document.getElementById(k);
    document.getElementById("divid").removeChild(row);
    var userid=document.getElementById("userid").value;
    if(userid.indexOf(","+k+",")>= 0 ){
     var bb=userid.replace(","+k+",","");
     document.getElementById("userid").value=bb;
    }
	}

</script>
</head>
<body>
<input type="text" name="userid" id="userid" value=""/>
<input type="button" onclick="aa()" value="选中"/>
<input type="button" onclick="roleList()" value="提交"/>
<table border="1" cellspacing="0" cellpadding="0" class="table table-bordered pull-right table-striped" style=" width:480px; text-align:center; margin-top:5px; "  id="divid">
<tr>
<td>商品名(点击查看详情)</td>
<td>数量</td>
<td>是否赠品</td>
<td>库存</td>
<td>操作</td>
</tr>
${grouplist}
</table>
</body>
</html>