<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
	<script type="text/javascript">
	function gotoLogin(){
		alert("出现异常，请重新登录");
		window.open('<%=request.getContextPath()%>/logout','_top');
	}
	</script>
	
	<body onload="gotoLogin()" ></body>	
</html>