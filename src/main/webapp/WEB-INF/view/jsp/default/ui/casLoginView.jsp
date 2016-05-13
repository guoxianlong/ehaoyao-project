
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1 maximum-scale=1, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>CAS &#8211; Central Authentication Service</title>
<link href="<%=request.getContextPath()%>/css/revise/reset.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/revise/base.css" rel="stylesheet">
</head>
<body class="loginPage">
	<div class="container bgWathet">
		<div class="sysHeader bgdeepBlue cf">
			<h1>好药师后台数据管理系统</h1>
		</div>
		<div class="sysBody">
			<!-- 登陆窗口--> 
			<div class="lgnBox">
				<form>
			  		<fieldset>
		    			<legend>用户登录</legend>
						<div>
							<form:form method="post" id="fm1" commandName="${commandName}" htmlEscape="true">
								<%-- <form:errors path="*" id="msg" cssClass="errors" element="div" htmlEscape="false" /> --%>
			   					<p><input type="text" id="username" name="username" class="inputBox lgnname"  placeholder="用户名"/></p>
								<p><input type="password" id="password" name="password" class="inputBox lgnpw"  placeholder="密码"/></p>
								<p><input type="submit" value="登&nbsp;录" class="lgnBtn"></p>
								<p class="agreeInfo checkBox">
									<input id="checkboxOneInput" type="checkbox" name="agree" >
									<label for="checkboxOneInput"><span></span></label>
									<span>转向其他站点前<em>提示我。</em></span>
								</p>
								<input type="hidden" name="lt" value="${loginTicket}" />
							    <input type="hidden" name="execution" value="${flowExecutionKey}" />
							    <input type="hidden" name="_eventId" value="submit" />
							</form:form>
						</div>
		  			</fieldset>
				</form>
			</div>
		</div>
		<div class="sysFooter">
			<p>Copyright @ 2000-2015 北京好药师大药房连锁有限公司-好药师网上药店版权所有 </p>
		</div>
	</div>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/revise/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/revise/common.js"></script>
	<script type="text/javascript" >
	</script>
</body>
</html>