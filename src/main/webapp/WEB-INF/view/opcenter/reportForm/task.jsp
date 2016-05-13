<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
     <meta name="viewport" content="width=device-width, initial-scale=1 maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>今日任务</title>
    
    <!-- 加载CSS样式文件 -->
    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-theme.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-select.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
    
     <!-- 加载javascript文件 -->
    <script src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap-select.min.js"></script>
    
	<style type="text/css">
		#task_table thead tr th{font-size:14px;background-color: #f9f9f9;}
		input.item_input {height: 30px;}
		.item_title {text-align: right;width: 70px;padding-top: 5px;margin-left: 10px;}
		.sear_btn{margin-left: 20px; padding: 0 12px; height: 30px;}
		.sear_btn2{padding: 0 12px; height: 30px;}
	</style>

<script type="text/javascript">
String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
$(document).ready(function (){
	$('.selectpicker').selectpicker();
});
function gotoPageSize(pageSize){
	$("#curPageSize").val(pageSize);
	gotoPage($("#curPage").val());
}
/**
 * 查询
 */
function gotoPage(pageno){
	//var flag = $("#flag").val();
	$("[name=pageno]").val(pageno);
	$("#dataForm").attr("action","<%=request.getContextPath()%>/nowTaskReport${endStr}.do?method=getTaskList");
	$("#dataForm").submit();
	<%-- var custName = $("#custName").val().trim();
	var tel = $("#tel").val().trim();
	var custServiceNo = "";
	var custServiceNm = "";
	var flag = $("#flag").val();
	if(flag != null && flag != "undefined" && flag.trim() == "1"){
		custServiceNo = $("#custServiceNo").val().trim();
		custServiceNm = $("#custServiceNm").val().trim();
	}
	var pageSize = $("#curPageSize").val().trim();
	$.ajax({
		type:"post",
		dataType : "html",
		url: "<%=request.getContextPath()%>/nowTaskReport${endStr}.do?method=getTaskList",
		data : {
			custName : custName,
			phoneNo : tel,
			custServCd : custServiceNo,
			custServName : custServiceNm,
			pageno : pageno,
			pageSize : pageSize,
			flag : flag
		},
		success : function(data){
			$("#reservaDiv").html(data);
		}
	}); --%>
}
/**
 * 导出
 */
 function toExcel(){
	document.form.action = "nowTaskReport.do?method=getExcel";
 	document.form.submit();
}
//完成按钮点击
function call_complete(id){
	if(confirm("此预约回访已完成？")){
		$.ajax({
			type:"post",
			dataType : "text",
			url: "<%=request.getContextPath()%>/nowTaskReport${endStr}.do?method=complete",
			data : {
				id : id
			},
			success : function(data){
				gotoPage(1);
			}
		});
	}
}
/**
 * 详情
 */
function outScreen1(tel){
	if(tel==null && tel.length==0){
		alert("手机号为空！");
	}else{
		$.ajax({
	 		type:"post",
	 		dataType: "json",
	 		url: "outScreen${endStr}.do?method=getScreenUrl",
	 		data: {tel:tel,type:"RW"},//今日任务
	 		success: function(data){
	 			if(data!=null){
					if(data.errorMsg!=null && $.trim(data.errorMsg).length>0){
	 					alert(data.errorMsg);
	 				}
					//打开去电弹屏页面
					if(data.url!=null && data.url.length>0){
			 			var rand = Math.floor(Math.random()*10000);
			 			window.open(data.url,"newwindow"+rand,'fullscreen=1,menubar=1,toolbar=1,resizable=1,scrollbars=1');
	 				}
	 			}
	 		},
	 		error: function(){
	 			alert("进入任务失败！");
	 		}
		});
	}
}
function outScreen(tel){
	if(tel==null && tel.length==0){
		alert("手机号为空！");
	}else if(confirm("您确定要拨打该客户电话，并处理该任务吗？")){
		var ra = Math.floor(Math.random()*10000);
		var blank = window.open("http://www.callout4ehaoyao.com?outboundnbr="+tel,"_blank"+ra);
		//10秒后关闭窗口
		setTimeout(function(){
				blank.close();
			}, 10000);
		$.ajax({
	 		type:"post",
	 		dataType: "json",
	 		url: "outScreen${endStr}.do?method=getScreenUrl",
	 		data: {tel:tel,type:"RW"},//今日任务
	 		success: function(data){
	 			if(data!=null){
					if(data.errorMsg!=null && $.trim(data.errorMsg).length>0){
	 					alert(data.errorMsg);
	 				}
					//打开去电弹屏页面
					if(data.url!=null && data.url.length>0){
			 			var rand = Math.floor(Math.random()*10000);
			 			window.open(data.url,"newwindow"+rand,'fullscreen=1,menubar=1,toolbar=1,resizable=1,scrollbars=1');
	 				}
	 			}
	 		},
	 		error: function(){
	 			alert("进入任务失败！");
	 		}
		});
	}
}
</script>
</head>
<body style="padding-top: 15px;" id="reservaDiv">
    <div class="right_box">
		<form id="dataForm" name="dataForm" action="" method="post">
			<input type="hidden" id="curPage" name="pageno" value="${pageno}"/>
			<input type="hidden" id="curPageSize" name="pageSize" value="${pageSize}"/>
			<div class="right_inline" style="margin-bottom:0px;">
				<div class="right_input item_title">客户名称：</div>
				<div class="right_input" style="width: 150px;">
					<input id="custName" name="custName" value="${cvo.custName}" type="text" class="form-control item_input"/>
				</div>
				<sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_TASK_ADMIN">
					<div class="right_input item_title">客服工号：</div>
					<div class="right_input" style="width:150px;">
						<input id="custServiceNo" name="custServiceNo" value="${cvo.custServiceNo}" type="text" class="form-control item_input"/>
					</div>
					<input type="hidden" id="flag" name="flag" value="1"/>
				</sec:authorize>
				<div class="right_input item_title">是否完成：</div>
				<div class="right_input" style="width: 100px;">
					<select class="selectpicker" data-width="100%" data-size="20" name="status" id="status">
						<option value="0" ${"0"==cvo.status?'selected="selected"':''}>未完成</option>
						<option value="1" ${"1"==cvo.status?'selected="selected"':''}>已完成</option>
					</select>
				</div>
				<button type="button" onclick="gotoPage(1)" class="btn btn-info sear_btn">查询</button>
				<sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_ExportExcel,/nowTaskReport.do?method=getExcel">
					<button type="button" onclick="toExcel()" class="btn btn-info sear_btn">导出</button>
				</sec:authorize>
				<div style="clear:both;"></div>
			</div>
			<div class="right_inline" style="margin-bottom:10px;">
				<div class="right_input item_title">手机：</div>
				<div class="right_input" style="width: 150px;">
					<input id="tel" name="tel" value="${cvo.tel}" type="text" class="form-control item_input"/>
				</div>
				<sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_TASK_ADMIN">
					<div class="right_input item_title">健康顾问：</div>
					<div class="right_input" style="width:150px;">
						<input id="custServiceNm" name="custServiceNm" value="${cvo.custServiceNm}" type="text" class="form-control item_input"/>
					</div>
				</sec:authorize>
				<div class="right_input item_title">说明：</div>
				<div class="right_input" style="width: 250px;">
					<input id="comment" name="comment" value="${cvo.comment}" type="text" class="form-control item_input"/>
				</div>
				<div style="clear:both;"></div>
			</div>
		</form>
		
        <table id="task_table" border="0" class="table table-bordered">
			<thead>
			<tr>
				<th width="45px;">序号</th>
				<th width="110px;">操作</th>
				<th width="70px;">沟通类型</th>
				<th width="70px;">客户名称</th>
				<th width="75px;">客户来源</th>
				<th width="205px;">产品关键词</th>
				<th width="100px;">预约回访日期</th>
				<th width="120px;">上次沟通日期</th>
				<th width="70px;">客服工号</th>
				<th width="75px;">健康顾问</th>
				<th width="105px;">说明</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${taskList}" varStatus="status" var="task">
				<tr>
					<td>${status.count}</td>
					<td>
						<img class="img-thumbnail" src="images/call.png" onclick="outScreen(${task.tel})"
					    	 onerror="this.src='images/call.png';this.onerror=null;"
						     title="去电弹屏" alt="去电弹屏" style="width: 27px; height: 27px; cursor: pointer"/>
						<span>&nbsp;</span> 
						<img class="img-thumbnail" src="images/mark_ok.png" onclick="call_complete(${task.id})"
					    	 onerror="this.src='images/mark_ok.png';this.onerror=null;"
						     title="完成" alt="完成" style="width: 27px; height: 27px; cursor: pointer"/>
						<img class="img-thumbnail" src="images/search.png" onclick="outScreen1(${task.tel})"
					    	 onerror="this.src='images/search.png';this.onerror=null;"
						     title="详情" alt="详情" style="width: 27px; height: 27px; cursor: pointer"/>
					</td>
					<td>${task.acceptResult}</td>
					<td>${task.custName}</td>
					<c:choose>
						<c:when test='${task.custSource=="ZX"}'><td title="在线客服">在线客服</td></c:when>
						<c:when test='${task.custSource=="XQ"}'><td title="需求登记">需求登记</td></c:when>
						<c:when test='${task.custSource=="TEL_IN"}'><td title="呼入电话">呼入电话</td></c:when>
						<c:when test='${task.custSource=="TEL_OUT"}'><td title="老客维护">老客维护</td></c:when>
						<c:otherwise><td>&nbsp;</td></c:otherwise>
					</c:choose>
					<td>${fn:replace(fn:replace(task.proKeywords, "{;}", ""),"{,}","; ")}</td>
					<td>${task.reserveTime}</td>
					<td>${task.lastTime}</td>
					<td>${task.custServiceNo}</td>
					<td>${task.custServiceNm}</td>
					<td>${task.comment}</td>
				</tr>
				</c:forEach>
				<c:if test="${taskList==null || fn:length(taskList)<=0}">
					<td colspan="11" style="height:100px;text-align: center;vertical-align: middle;font-size:14px;font-weight: bold;">暂无数据！</td>
				</c:if>
			</tbody>
		</table>
		<ul class="pager">
			<%@ include file="/WEB-INF/view/common/page.jspf" %>
		</ul>
	</div>
	<form  method="post" action="" name="form"	id="form"></form>
</body>
</html>