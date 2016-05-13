<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
	<script type="text/javascript">
	function gotoLogin(){
		alert("session过期，请重新登录");
		window.open('<%=request.getContextPath()%>/login.jsp','_top');
	}
	</script>
	
	<body onload="gotoLogin()" ></body>	
</html>