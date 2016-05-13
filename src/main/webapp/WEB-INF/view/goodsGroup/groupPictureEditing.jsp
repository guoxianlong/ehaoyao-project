<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
   
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8" />
	<title>无标题文档</title>
	<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet">
    <script src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap.min.js" type="text/javascript"></script>
	<style type="text/css">
		.form-inline{margin:10px 0;}
		.form-inline .form-title{display:block; float:left; padding:0 5px; text-align:right; line-height:34px;}
		.rightbox{width:700px;}
		#formSquare, #formRectangle {position:absolute; top:0; left:-1000px;}
	</style>
	
</head>
<body>

	<div class="right_box">
    	<div class="right_h right_bg" >套餐图片编辑</div>
        <div id="selectBox" class="right_box2">
        	<!-- 
				相关参数说明：
				picid：图片标示，用于跟下面的做匹配，如此空间内没有图片，则为null
				s：图片状态
					main：主商品图
					sub：其他商品图
				w：图片规格
					single：为1x1的规格
					double：为2x1的规格 ${goodsID } <img src="${imgSrc }" >

				redirect回来后，所有参数picid，s，w都要填充好，不然选择操作会出错！
			-->
    		<div id="mainimage" class="editpic_0 border_gray" w="single" s="main" picid="${tuID }"><c:if test="${not empty tuID}"><img src="${imgSrc }" ></c:if></div>
   			<div class="editpic_box">
    			<div id="futuimage1" class="editpic_1 border_gray" w="single" s="sub" picid="${fuID1 }"><c:if test="${not empty fuID1}"><img src="${imgSrcf1 }" ></c:if></div>
<div id="futuimage2" class="editpic_1 border_gray" w="single" s="sub" picid="${fuID2 }"><c:if test="${not empty fuID2}"><img src="${imgSrcf2 }" ></c:if></div>
<div id="futuimage3" class="editpic_1 border_gray" w="single" s="sub" picid="${fuID3 }"><c:if test="${not empty fuID3}"><img src="${imgSrcf3 }" ></c:if></div>
<div id="futuimage4" class="editpic_1 border_gray" w="single" s="sub" picid="${fuID4 }"><c:if test="${not empty fuID4}"><img src="${imgSrcf4 }" ></c:if></div>
<div id="futuimage5" class="editpic_2 border_gray" w="double" s="sub" picid="${cfuID1 }"><c:if test="${not empty cfuID1}"><img src="${cimgSrcf1 }" ></c:if></div>
   			</div> 
  		</div>
  		<div id="picBox" class="right_box2">
    		<div class="editpic_box2">
    			<!-- 
    				相关参数说明：
    				picid：图片标示，用于跟上面的做匹配
    				static： 图片状态
    					0：未被选中的图片
    					1：被选中的主商品图
    					2：被选中的其他商品图

    				redirect回来后，所有参数picid，static都要填充好，不然选择操作会出错！
    			-->
    			<!--  
   				<div picid="1" static="0" class="editpic_1 border_black"><img src="D:\dzsw\haoyao-goodsCenter\src\main\webapp\upload\44032\\402881e945e978b00145e986b3ac0001.jpg" ></div>
   				<div picid="2" static="0" class="editpic_1 border_black"><img src="D:\dzsw\haoyao-goodsCenter\src\main\webapp\upload\44032\\402881e945e978b00145e986b3ac0001.jpg" ></div>
   				<div picid="3" static="0" class="editpic_1 border_black"><img src="D:\dzsw\haoyao-goodsCenter\src\main\webapp\upload\44032\\402881e945e978b00145e986b3ac0001.jpg" ></div>
   				<div picid="4" static="0" class="editpic_1 border_black"><img src="D:\dzsw\haoyao-goodsCenter\src\main\webapp\upload\44032\\402881e945e978b00145e986b3ac0001.jpg" ></div>
   				<div picid="5" static="0" class="editpic_1 border_black"><img src="D:\dzsw\haoyao-goodsCenter\src\main\webapp\upload\44032\\402881e945e978b00145e986b3ac0001.jpg" ></div>
   				<div picid="6" static="0" class="editpic_1 border_black"><img src="D:\dzsw\haoyao-goodsCenter\src\main\webapp\upload\44032\\402881e945e978b00145e986b3ac0001.jpg" ></div>
   				-->
                <c:forEach items="${goodsImagelist }" var="item" varStatus="status">
                
                <c:if test="${(item.imgType==1) and (item.shape==1)}">
                <div name="picids" picid="${item.id }" static="1" class="editpic_1 border_red"><img src="${item.imgSrc }" ></div>
                </c:if>
                <c:if test="${item.imgType==0 and (item.shape==1)}">
   				<div name="picids" picid="${item.id }" static="0" class="editpic_1 border_black"><img src="${item.imgSrc }" ></div>
   				</c:if>
   				<c:if test="${item.imgType==2 and (item.shape==1)}">
   				<div name="picids" picid="${item.id }" static="2" class="editpic_1 border_green"><img src="${item.imgSrc }" ></div>
   				</c:if>
   				
   				<c:if test="${item.imgType==0 and (item.shape==2)}">
   				<div name="picids" picid="${item.id }" static="0" class="editpic_2 border_black"><img src="${item.imgSrc }" ></div>
   				</c:if>
   				<c:if test="${item.imgType==2 and (item.shape==2)}">
   				<div name="picids" picid="${item.id }" static="2" class="editpic_2 border_green"><img src="${item.imgSrc }" ></div>
   				</c:if>
   				</c:forEach>

   				
   				
   				
   				
   				
 			</div>
 			<div class="editpic_box4"><span id="prevImg"></span></div>
 			<div class="right_inline">
 				<button id="uploadSquare" type="button" class="btn btn-info btn-sm" style="margin:15px 15px 0 0;">上传正方形</button>
 				<button id="uploadRectangle" type="button" class="btn btn-info btn-sm" style="margin:15px 15px 0 0;">上传长方形</button>
 				<form id="shezhiform" name="shezhiform" method="post">
 				<input type="hidden" id="id" name="id" value="${id }"/>
 				<c:if test="${wtype==6 }">
 				<button type="button" onclick="saveshezhi()" class="btn btn-info btn-sm" style="margin:15px 15px 0 120px;" >保存设置</button>
 				</c:if>
 				<c:if test="${wtype==5 }">
 				<button type="button" onclick="saveshezhia()" class="btn btn-info btn-sm" style="margin:15px 15px 0 120px;" >保存设置</button>
 				</c:if>
 				</form>
 				<!-- action填写上传正方形图片的api -->
 				<form id="formSquare" name="formSquare"  enctype="multipart/form-data" method="post">
 				<input type="hidden" id="id" name="id" value="${id }"/>
 				<input type="hidden" id="wtype" name="wtype" value="${wtype }"/>
 					<input name="square" id="square" type="file" multiple="true"  style="opacity:0;">
 				</form>
 				<!-- action填写上传长方形图片的api -->
 				<form id="formRectangle" name="formRectangle"  enctype="multipart/form-data" method="post">
 				<input type="hidden" id="id" name="id" value="${id }"/>
 				<input type="hidden" id="wtype" name="wtype" value="${wtype }"/>
 					<input name="rectangle" type="file" multiple="true" style="opacity:0;">
 				</form>
 			</div>
  		</div>
	</div>

	<script type="text/javascript">
	 $(document).ready(function(){
	 var maid=$("#mainimage").attr("picid");//主图
	 if(maid!=""){
		 $("#mainimage").removeClass("editpic_0 border_gray");
		 $("#mainimage").addClass("editpic_0 border_red");
	 }
	 var futuimage1=$("#futuimage1").attr("picid");//副图1
	 var futuimage2=$("#futuimage2").attr("picid");//副图2
	 var futuimage3=$("#futuimage3").attr("picid");//副图3
	 var futuimage4=$("#futuimage4").attr("picid");//副图4
	 var futuimage5=$("#futuimage5").attr("picid");//副图5
	 if(futuimage1!=""){
		 $("#futuimage1").removeClass("editpic_1 border_gray");
		 $("#futuimage1").addClass("editpic_1 border_green");
	 }
     if(futuimage2!=""){
    	 $("#futuimage2").removeClass("editpic_1 border_gray");
		 $("#futuimage2").addClass("editpic_1 border_green");
	 }
     if(futuimage3!=""){
    	 $("#futuimage3").removeClass("editpic_1 border_gray");
		 $("#futuimage3").addClass("editpic_1 border_green");
	 }
     if(futuimage4!=""){
    	 $("#futuimage4").removeClass("editpic_1 border_gray");
		 $("#futuimage4").addClass("editpic_1 border_green");
     }
     if(futuimage5!=""){
    	 $("#futuimage5").removeClass("editpic_2 border_gray");
		 $("#futuimage5").addClass("editpic_2 border_green");
     }});
//设置保存
 function saveshezhi(){
     var id=document.getElementById("id").value;
	 var maid=$("#mainimage").attr("picid");//主图
	 var futuimage1=$("#futuimage1").attr("picid");//副图1
	 var futuimage2=$("#futuimage2").attr("picid");//副图2
	 var futuimage3=$("#futuimage3").attr("picid");//副图3
	 var futuimage4=$("#futuimage4").attr("picid");//副图4
	 var futuimage5=$("#futuimage5").attr("picid");//副图5
	 if(maid==""){
		 alert("必须设置主图!");
		 return;
	 }
	 $.ajax({
    			type : "POST",  
    			dataType: "json",//返回json格式的数据
    			url:"<%=request.getContextPath()%>/goodsImage.do?method=saveimagetcshezhi&maid="+maid+"&futuimage1="+futuimage1+"&futuimage2="+futuimage2+"&futuimage3="+futuimage3+"&futuimage4="+futuimage4+"&futuimage5="+futuimage5+"&id="+id,
    			success: function(data){
    			
    	          $.each(data,function(i,result){  
	            	alert(result);
	            	document.shezhiform.action = "goodsImage.do?method=tcload&wtype=6";
	                document.shezhiform.submit();
	                 if(maid!=""){
	                 window.parent.document.getElementById("filepatha").style.display="block";
	                 window.parent.document.getElementById("filepatha").src=$("#mainimage").find("img").attr("src");
	                 }else{
	                 window.parent.document.getElementById("filepatha").style.display="none";
	                 }
	                 if(futuimage5!=""){
	                 window.parent.document.getElementById("filepathe").style.display="block";
	                 window.parent.document.getElementById("filepathe").src=$("#futuimage5").find("img").attr("src");
	                 }else{
	                 window.parent.document.getElementById("filepathe").style.display="none";
	                 }
	                 parent.$(".l-dialog,.l-window-mask").remove(); //关闭弹出层
	             }); 
    			}
    		});
	 //alert("maid="+maid+" "+"futuimage1="+futuimage1+" "+"futuimage2="+futuimage2+" "+"futuimage3="+futuimage3+" "+"futuimage4="+futuimage4+" "+"futuimage5="+futuimage5);
	 //document.shezhiform.action="goodsImage.do?method=saveimagetcshezhi&maid="+maid+"&futuimage1="+futuimage1+"&futuimage2="+futuimage2+"&futuimage3="+futuimage3+"&futuimage4="+futuimage4+"&futuimage5="+futuimage5;
	 //document.shezhiform.submit();
 }
 
 //设置保存
 function saveshezhia(){
     var id=document.getElementById("id").value;
	 var maid=$("#mainimage").attr("picid");//主图
	 var futuimage1=$("#futuimage1").attr("picid");//副图1
	 var futuimage2=$("#futuimage2").attr("picid");//副图2
	 var futuimage3=$("#futuimage3").attr("picid");//副图3
	 var futuimage4=$("#futuimage4").attr("picid");//副图4
	 var futuimage5=$("#futuimage5").attr("picid");//副图5
	 if(maid==""){
		 alert("必须设置主图!");
		 return;
	 }
	 $.ajax({
    			type : "POST",  
    			dataType: "json",//返回json格式的数据
    			url:"<%=request.getContextPath()%>/goodsImage.do?method=saveimagetcshezhi&maid="+maid+"&futuimage1="+futuimage1+"&futuimage2="+futuimage2+"&futuimage3="+futuimage3+"&futuimage4="+futuimage4+"&futuimage5="+futuimage5+"&id="+id,
    			success: function(data){
    			
    	          $.each(data,function(i,result){  
	            	alert(result);
	            	document.shezhiform.action = "goodsImage.do?method=tcload&wtype=5";
	                document.shezhiform.submit();
	                
	             }); 
    			}
    		});
	 //alert("maid="+maid+" "+"futuimage1="+futuimage1+" "+"futuimage2="+futuimage2+" "+"futuimage3="+futuimage3+" "+"futuimage4="+futuimage4+" "+"futuimage5="+futuimage5);
	// document.shezhiform.action="goodsImage.do?method=saveimagetcshezhi&maid="+maid+"&futuimage1="+futuimage1+"&futuimage2="+futuimage2+"&futuimage3="+futuimage3+"&futuimage4="+futuimage4+"&futuimage5="+futuimage5;
	 //document.shezhiform.submit();
 }
 
 
	 $(window).scroll(function(){
			var _top = $(this).scrollTop();

			if( _top > 338 ){
				$('#prevImg').css({
					position: 'fixed',
					top: '25px'
				});
			}else{
				$('#prevImg').css({
					position: 'static'
				});
			};
		});
 
	 
	 
	 
		editPic();

		$('#uploadSquare').click(function(){
			$('[name=square]').click();
			$('[name=square]').change(function(){
			
				document.formSquare.action="goodsImage.do?method=saveimageztc";
				console.log($(this).val());
				$('#formSquare').submit();
			});
		});

		$('#uploadRectangle').click(function(){
			$('[name=rectangle]').click();
			$('[name=rectangle]').change(function(){
				document.formRectangle.action="goodsImage.do?method=saveimagectc";
				console.log($(this).val());
				$('#formRectangle').submit();
			});
		});

		function editPic(){
			
			var _pic = $('#picBox').find('[picid]');
           
			_pic.off().on({
				mouseenter: function(){
					var __src = $(this).find('img').attr('src');
                   
					$('#prevImg').html('<img src="'+ __src +'" />');
				},
				mouseleave: function(){
					$('#prevImg').html('');
				},
				click: function(){
				
					var __pic = $(this),
						__picid = __pic.attr('picid'),
						__picstatic = __pic.attr('static'),
						__picsrc = __pic.find('img').attr('src'),
						__picw = __pic.hasClass('editpic_2'),
						__target = $('#selectBox').find('[picid='+ __picid +']'),
						__select,
						__selectclass,
						__selectstatic;
					

					switch( __picstatic ){
					
						case '0':
							
							if( $('#selectBox').find('[picid=""]').length > 0 ){
								if( __picw ){
									if( $('#selectBox').find('[w=double]').eq(0).attr('picid') == "" ){
										__select = $('#selectBox').find('[w=double]').eq(0);
										
									}else{
										alert('位置已满，请先空出图片位置再进行操作！');
										return;
									};
								}else{
									if( $('#selectBox').find('[w=single][picid=""]').length > 0 ){
										__select = $('#selectBox').find('[w=single][picid=""]').eq(0);
										
									}else{
										alert('位置已满，请先空出图片位置再进行操作！');
										return;
									};
								};
								if( __select.attr('s') == 'main' ){
									__selectclass = 'border_red';
									__selectstatic = '1';
								}else{
									__selectclass = 'border_green';
									__selectstatic = '2';
								};
							}else{
								alert('位置已满，请先空出图片位置再进行操作！');
								return;
							};
							__pic.removeClass('border_black').
							      addClass(__selectclass).
							      attr('static', __selectstatic);
							__select.removeClass('border_gray').
						             addClass(__selectclass).
						             attr('picid', __picid).
								     html('<img src="'+ __picsrc +'" >');
							break;
						case '1':
							
							__pic.removeClass('border_red').
							      addClass('border_black').
							      attr('static', '0');
							__target.removeClass('border_red').
									 addClass('border_gray').
									 attr('picid', '').
									 html('');
							break;
						case '2':
							
							__pic.removeClass('border_green').
							      addClass('border_black').
							      attr('static', '0');
							__target.removeClass('border_green').
									 addClass('border_gray').
									 attr('picid', '').
									 html('');
							break;
					};
					
					
					
				}
				
				
				
			});
		};
	</script>
</body>
</html>
