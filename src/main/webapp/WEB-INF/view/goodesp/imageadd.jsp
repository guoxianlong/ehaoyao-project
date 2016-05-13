<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
function a(){
	document.form.action ="goodsImage.do?method=saveimage";
	document.form.submit();
}
</script>
<body>
<form name="form" id="form" method="post" enctype="multipart/form-data">
<input type="file" name="imgPath" id="imgPath" />
<button type="button" name="button" onclick="a()" >保存</button>
</form>
</body>
</html>