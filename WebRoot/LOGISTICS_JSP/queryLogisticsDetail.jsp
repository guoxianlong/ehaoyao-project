<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'queryLogisticsDetail.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   <div align="center" style="width: 50%;height: 50%; margin-left: 25%;margin-top: 15%;">
   <form action="my/logistics!queryDetail" method="post">
  快递单号：<input type="text" name="trackNumber"/> 
  快递公司：<select name="source">
  <option value="shunfeng">顺丰快递</option>
  <option value="yunda">韵达快递</option> 
  <option value="ems">EMS</option>
   <!--<option value="jd">京东快递</option>
  <option value="huitongkuaidi">汇通快递</option>
  --></select>
  <input type="submit" value="查询"/>
   </form>
   </div>
  </body>
</html>
