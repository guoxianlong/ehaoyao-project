<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
<style type="text/css">
	.table thead tr th{font-size:14px;background-color: #f9f9f9;}
	input.item_input {height: 30px;}
	.item_title {text-align: right;width: auto;padding-top: 5px;margin-left: 15px;}
	.item_title4 {width: 70px;text-align: right;padding-top: 5px;}
	.item_title6 {text-align: left;margin-left:10px;padpadding-top: 5px;font-size: 16px;font-weight: bold;}
	.sear_btn{margin-left: 20px; padding: 0 12px; height: 30px;}
	.select_set + div button.selectpicker{height:30px;padding-top:0;padding-bottom:0;}
</style>
<script type="text/javascript">
$(document).ready(function(){
	$('#na_list li a').click(function(){
		$(this).parent().parent().find("li").each(function(){
			if($(this).hasClass("active")){
				$(this).removeClass("active");
			}
		});
		var data = {};
		var url="";
		var li = $(this).parent();
		li.addClass("active");
		if(li.index()==0){
			$("#itemTitle").text("个人信息");
			url = "<%=request.getContextPath()%>/${actionName}?method=getMemberInfo";//个人信息
			data.memberName = $("#custName").val();
			data.tel = $("#mobile").val();
		}else if(li.index()==1){
			$("#itemTitle").text("个人资产");
			url = "<%=request.getContextPath()%>/${actionName}?method=getMemberAssets";//个人资产
			data.userId = $("#memberCode").val();
		}else if(li.index()==2){
			$("#itemTitle").text("送货地址");
			url = "<%=request.getContextPath()%>/${actionName}?method=getUserAddressInfo";//收货地址
			data.userId = $("#memberCode").val();
		}else if(li.index()==3){
			$("#itemTitle").text("家庭成员");
			url = "<%=request.getContextPath()%>/${actionName}?method=getFamilysInfo";//家庭成员
			data.userId = $("#memberCode").val();
		}else if(li.index()==4){
			$("#itemTitle").text("优惠券信息");
			url = "<%=request.getContextPath()%>/${actionName}?method=getCouponInfo";//优惠券
			data.userId = ${member.memberCode};
		}else if(li.index()==5){
			$("#itemTitle").text("浏览记录");
			url = "<%=request.getContextPath()%>/${actionName}?method=getBrowseRecord";//浏览记录
			data.userId = ${member.memberCode};
		}else if(li.index()==6){
			$("#itemTitle").text("用户收藏");
			url = "<%=request.getContextPath()%>/${actionName}?method=getUserCollection";//用户收藏
			data.userId = ${member.memberCode};
		}
		if(url!=""){
			$.ajax({
				 type: "POST",
	             url: url,
	             dataType: "html",
	             data: data,
	             success: function(data){
	            	 $("#memberDiv").html(data);
	             },
	             error:function(){
	            	 $("#memberDiv").html("请求处理失败！");
	             }
			});
		}else{
			$("#memberDiv").html("");
		}
	});
});
</script>
<div style="min-width: 1000px;">
	<input type="hidden" id="memberCode" value="${member.memberCode}">
	<div>
		<div style="float:left;width:8%;">
			<ul class="nav nav-pills nav-stacked" id="na_list">
			   <li class="active"><a href="#">个人信息</a></li>
			   <li><a href="#">个人资产</a></li>
			   <li><a href="#">送货地址</a></li>
			   <li><a href="#">家庭成员</a></li>
			   <li><a href="#">优惠券</a></li>
			   <li><a href="#">浏览记录</a></li>
			   <li><a href="#">用户收藏</a></li>
			</ul>
		</div>
		<div style="float:left;width:50%;">
			<div class="right_inline">
				<div class="right_input item_title6"></div>
				<div class="right_input item_title6">${member.nickName}的<span id="itemTitle">个人信息</span></div>
			</div> 
		</div>
		<div id="memberDiv" style="float:left;width:91%;min-height:300px;margin-left:10px;padding:10px;border:1px solid #DDD;">
			<jsp:include page="memberInfo.jsp"/>
			<%-- <jsp:include page="member_info.jsp"/>--%>
		</div>
	</div>
	<div style="clear:both;"></div>
</div>