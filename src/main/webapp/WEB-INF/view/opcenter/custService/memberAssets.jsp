<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <!-- 加载CSS样式文件 -->
    <link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
    
    <style type="text/css">
		#recordlist li a{font-size:14px; font-weight: bold;}
	</style>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#recordlist li a').click(function(){
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
					url = "<%=request.getContextPath()%>/${actionName}?method=getCashInfo";//现金账户
					data.userId = $("#memberCode").val();
				}else if(li.index()==1){
					url = "<%=request.getContextPath()%>/${actionName}?method=getIntegralInfo";//积分账户
					data.userId = $("#memberCode").val();
				}
				if(url!=""){
					$.ajax({
						 type: "POST",
			             url: url,
			             dataType: "html",
			             data: data,
			             success: function(data){
			            	 $("#recordDiv").html(data);
			             },
			             error:function(){
			            	 $("#recordDiv").html("请求处理失败！");
			             }
					});
				}else{
					$("#recordDiv").html("");
				}
			});
		});
	</script>
</head>

<body style="padding-top: 10px;padding-bottom:20px;">
	 <div class="right_box">
		<ul id="recordlist" class="nav nav-tabs" >
			<li class="active"><a href="javascript:void(0);">现金账户</a></li>
			<li><a href="javascript:void(0);">积分账户</a></li>
		</ul>
		<div style="clear:both;"></div>
		<div style="border:1px solid #DDD;border-top:none;padding:10px;min-height: 200px;">
			<div id="recordDiv" style="overflow: auto;">
				<jsp:include page="cash.jsp"/>
			</div>
		</div>
	</div>
	<div style="clear: both;"></div> 
</body>
</html>