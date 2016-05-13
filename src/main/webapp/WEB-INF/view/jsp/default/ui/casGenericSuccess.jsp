
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.Map.Entry" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1 maximum-scale=1, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>CAS &#8211; Central Authentication Service</title>
<link href="<%=request.getContextPath()%>/css/revise/reset.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/revise/base.css" rel="stylesheet">
</head>
<body>
	<div class="container bgWathet">
		<div class="sysHeader bgdeepBlue cf">
			<h1>好药师后台数据管理系统</h1>
			<p class="loginInfo">
				<%
				String str = request.getAttribute("credential").toString();
				String username = "";
				if(str != null){
					String[] arr = str.toString().split("\\+");
					if(arr != null && arr.length > 1){
						username = arr[0];
					}
				}
				request.setAttribute("username", username);
				%>
				<a href="#" class="userinfo">欢迎您<em>${username}</em></a>
				<!-- <a href="#">修改密码</a> -->
				<a href="<%=request.getContextPath()%>/logout" class="last">退出</a>
			</p>
		</div>
		<div class="sysBody">
			<!-- 登陆成功--> 
			<div class="successInfo">
				<h3>您已经成功登录系统。</h3>
				<p>出于安全考虑，一旦您访问过那些需要你提供凭证信息的应用时，请操作完成之后关闭浏览器。</p>
			</div>
			<div class="platformEnter">
				<a href="http://219.139.241.75:8082/hys-report" target="_blank" class="reportCenter">报表中心</a>
				<a href="http://localhost:8080/operation_center/" target="_self" class="operationCenter">运营中心</a>
				<a href="#" class="productCenter last">商品中心</a>
			</div>
		</div>
		<div class="sysFooter">
			<p>Copyright @ 2000-2015 北京好药师大药房连锁有限公司-好药师网上药店版权所有 </p>
		</div>
	</div>
	<script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" >

	</script>
</body>
</html>