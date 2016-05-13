<html>
	<script type="text/javascript">
	function gotoLogin(){
		window.open('<%=request.getContextPath()%>/login.jsp','_top');
	}
	</script>
	
	<body onload="gotoLogin()" ></body>	
</html>