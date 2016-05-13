<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<!-- 导入插件样式 -->
<link rel="stylesheet"	
href="<%=request.getContextPath()%>/js/imgareaselect/css/imgareaselect-animated.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/jquery.tooltip.css" type="text/css"/>
<link href="<%=request.getContextPath()%>/js/jquery.loadmask.css" rel="stylesheet" type="text/css" />

<!-- 设置图片操作DIV的样式 -->
<style type="text/css">

#imgDiv{

	margin-left: 3px; margin-top: 5px; width: 800px; height: 700px;
	overflow: auto;
	scrollbar-3dlight-color:#595959; 
	scrollbar-arrow-color:#FFFFFF; 
	scrollbar-base-color:#CFCFCF; 
	scrollbar-darkshadow-color:#FFFFFF; 
	scrollbar-face-color:#CFCFCF; 
	scrollbar-highlight-color:#FFFFFF; 
	scrollbar-shadow-color:#595959;
}

</style>
<!-- 导入jQuery各种插件 -->
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/imgareaselect/jquery.imgareaselect.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.tooltip.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.loadmask.js"></script>

	<script type="text/javascript">

	$(document).ready(function() {
		
		//获取拖拽比例
		var proportion = $('#proportion').val();
        
		//unescape可对通过escape()编码的字符串进行解码
		var imagePath = unescape($('#img').attr('src'));

		//图片的url设置给隐藏表单originPath，准备提交给Action
		$('input[name=originPath]').val(imagePath);
		
		//通过图片的url取出服务器域名全路径并设置好访问Action的url
		var imgPath = $('#img').attr('src');
		
		var newAction = imgPath.substring(0, imgPath.indexOf('/images')) +"imageScissorAction.do?method=toScissor";
		
		//设置表单的action属性为Action的rul
		$('#scissorForm').attr('action',newAction);
		
		//为id为img的图片设置图片剪切插件
		var imgArea = $('#img').imgAreaSelect({
			
			fadeSpeed: 400,
			handles: true,
	        		instance: true,
	       	 	aspectRatio: proportion, //设置拖拽比例
			onSelectEnd : function(img, selection) {
				$('#areaHeight').html((selection.y2 - selection.y1)+" 像素");
				$('#areaWidth').html((selection.x2 - selection.x1)+" 像素");
				$('input[name=x1]').val(selection.x1);
				$('input[name=y1]').val(selection.y1);
				$('input[name=x2]').val(selection.x2);
				$('input[name=y2]').val(selection.y2);
				$('#warning').hide();
			}
		});
		
		//提交按钮的单击事件处理
		$('#submitBtn').click(function(){
			if(confirm("是否提交？")){
				$('#scissorForm').submit();
				
			}
			
		});

		//设置表单ajax异步提交
	    $('#scissorForm').submit(function() {
		    		
			        $(this).ajaxSubmit({ 
				        	
			        		beforeSubmit:  function(){//提交前的处理
								
			        			//提交表单处理期间，屏蔽整个窗口        					
			        			$('#content').mask("正在提交数据，请稍候。");
			        			//关闭提交按钮
			        			$('input[name=submit]').attr("disabled", true);
	        				},
			        		dataType:  'json', 
				        	success: function showResponse(responseText, statusText, xhr, $form){ 

					        	//取消窗口屏蔽
	        					$('#content').unmask();
			        			$('#warning').show();

			        			//打开提交按钮
			        			$('input[name=submit]').attr("disabled", false);
			        			
			        			imgArea.cancelSelection();
				        		var imgPath = $('#img').attr('src')+'?'+new Date().getTime();
			        			$('#img').attr('src',imgPath);
			        			
								//重置参数
			        			$('input[name=scaleHeight]').val("");
			        			$('input[name=scaleWidth]').val("");
			        			$('#areaHeight').html("");
			        			$('#areaWidth').html("");
			        			$('input[name=scaleWidtha]').val("");
			        			$('input[name=scaleHeightb]').val("");
			        			
			        			alert(responseText);
			        			document.scissorForm.reset();
					        }
		        	}); 
		        	
			        return false; 
	    }); 

		//返回上一页
	    $('#back').click(function(){
	    	var  bpath=    document.getElementById("lujing").value;//第一张图片
	    	var  bpath1=  document.getElementById("lujing1").value;//第二张图片
	    	var  bpath2=  document.getElementById("lujing2").value;//第三张图片
	    	var  bpath3=  document.getElementById("lujing3").value;//第四张图片
	    	var  bpath4=  document.getElementById("lujing4").value;//第五张图片
	    	var  bpath5=  document.getElementById("lujing5").value;//第六张图片
	    	window.location.href="<%=request.getContextPath() %>/goodsp.do?bpath="+bpath+"&bpath1="+bpath1+"&bpath2="+bpath2+"&bpath3="+bpath3"&bpath4="+bpath4"&bpath5="+bpath5;   
	    	
		});
		
	});
</script>
</head>
<body>
<div id="content" >
<!-- 保存图片修改后参数的表单 -->
<form id="scissorForm" name="form" method="post">
<input type="hidden" id="lujing" name="lujing" value="${path }"/>
<input type="hidden" id="lujing1" name="lujing1" value="${filepatha }"/>
<input type="hidden" id="lujing2" name="lujing2" value="${filepathb }"/>
<input type="hidden" id="lujing3" name="lujing3" value="${filepathc }"/>
<input type="hidden" id="lujing4" name="lujing3" value="${filepathd }"/>
<input type="hidden" id="lujing5" name="lujing3" value="${filepathe }"/>
<input type="hidden" id="staus" name="staus" value="${staus }"/>
	剪切区域，高：<label id="areaHeight"></label>
	宽：<label id="areaWidth"></label>
	<br /><br />
	缩放宽：<input type="text" name="scaleWidth" id="scaleWidth" value="${scaleWidth }" style="width: 100px;" /> 
	缩放高：<input type="text" name="scaleHeight" id="scaleHeight"  value="${scaleHeight }" style="width: 100px;" />
	<br /><br />
	缩放宽：<input type="text" name="scaleWidtha" id="scaleWidtha" value="${scaleWidtha }" style="width: 100px;" /> 
	缩放高：<input type="text" name="scaleHeightb" id="scaleHeightb" value="${scaleHeightb }" style="width: 100px;" />
	<br /><br />
	<!-- 提示信息 -->
	<font color="red">[请先选取裁剪区域]&nbsp;[大尺寸图片可先缩放再剪切]</font>
	<br /><br />
	
	<input type="button" id="submitBtn" name="submitBtn" value="提交" />
	<input type="button" id="back"  value="返回" />
	
	<%-- 图片剪切区域 --%>
	<div id="imgDiv">
	    ${staus}
		<!-- 待处理的图片 -->
		<c:if test="${staus=='1' }">
		<img id="img" src="${path }" class="scissor" alt="点击裁剪图片">
		</c:if>
		<c:if test="${staus=='2' }">
		<img id="img" src="${filepatha }" class="scissor" alt="点击裁剪图片">
		</c:if>
		<c:if test="${staus=='3' }">
		<img id="img" src="${filepathb }" class="scissor" alt="点击裁剪图片">
		</c:if>
		<c:if test="${staus=='4' }">
		<img id="img" src="${filepathc }" class="scissor" alt="点击裁剪图片">
		</c:if>
		<c:if test="${staus=='5' }">
		<img id="img" src="${filepathd }" class="scissor" alt="点击裁剪图片">
		</c:if>
		<c:if test="${staus=='6' }">
		<img id="img" src="${filepathe }" class="scissor" alt="点击裁剪图片">
		</c:if>
	</div>
	
	<!-- 保存图片操作参数的隐藏表单 -->
	<input type="hidden" name="x1" id="x1" value="" /> 
	<input type="hidden" name="y1" id="y1"  value="" /> 
	<input type="hidden" name="x2" id="x2" value="" /> 
	<input type="hidden" name="y2" id="y2" value="" /> 
	<input type="hidden" name="originPath" value="" /> 
</form>
</div>

<!-- 保存缩放比的隐藏表单 -->
<input type="hidden"  name="proportion" 
value="${proportion}" />

</body>
</html>