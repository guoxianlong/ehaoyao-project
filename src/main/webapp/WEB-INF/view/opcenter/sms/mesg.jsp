<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  
  <body>
  <form action="rule/sendMesg.do" method="post">
	   手机号：<input type="text" name="phoneNumber" value=""/> <br>
	   短信内容： <br>
	   <textarea name="mesg" style="width:400px;height:100px;"></textarea> <br>
	   <input type="submit" value="发送"  />
  </form>
  <!-- <s:property value="res"/> -->
  
  </body>
</html>
