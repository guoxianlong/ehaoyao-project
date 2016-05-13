<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>订单记录</title>
    
    <!-- 加载CSS样式文件 -->
    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-theme.css" rel="stylesheet">
    
</head>
<body>
	<div class="panel-body">
		<table width="98%" border="0" class="table table-striped table-bordered">
			<tr>
				<td>订单状态</td>
				<td>订单号/下单时间</td>
				<td>平台</td>
				<td>商品内容</td>
				<td>物流状态</td>
				<td>订单操作</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>
				    <a href="#">取消</a>
				    <a href="#">详情</a>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>