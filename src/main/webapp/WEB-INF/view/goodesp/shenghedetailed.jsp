<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/jquery.tooltip.css" type="text/css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.tooltip.js"></script>
<style type="text/css">
.form-inline{margin:10px 0;}
.form-inline .form-title{display:block; float:left; padding:0 5px;  text-
align:right; line-height:34px;}
.rightbox{width:700px;}
	.select-box{height:70px; border:1px solid #ccc; overflow:hidden; overflow-y:scroll;}
		.select-box .select-items{position:relative; float:left; margin:5px 0 0 5px; padding:5px 10px; border:1px solid #ccc; cursor:pointer;}
		.select-box .select-items span{color:red; font-weight:bold;}
		.select-box .select-items i{display:none; position:absolute; top:0; right:0; width:10px; height:10px; line-height:10px; text-align:center; font-style:normal; background:#427fed; color:#FFFFFF;}
		.select-box .select-items-hover{border-color:#427fed;}
		.select-box .select-items-hover i{display:block;}
		.form-panel{overflow:hidden;}
		.form-panel .pull-left{margin-right:20px;}
<!--.addfileA{ 
   position:relative;   
    cursor:hand; 
	   text-decoration:none;   
	   background-image: url('images/goods_editor/u16.png'); 
	   width:50px;
	   height: 50px;
	   } 
		.addfileI {  
		 cursor:hand; 
		 position:relative; 
		left:0px; 
		width:50px; 
		height: 50px;
		background-color: blue;    
		opacity:0;    
		filter:alpha(opacity=0)}
		.addfileB{ 
   position:relative;   
    cursor:hand; 
	   text-decoration:none;   
	   background-image: url('images/goods_editor/u16.png'); 
	   width:50px;
	   height: 50px;
	   } 
		.addfileI1 {  
		 cursor:hand; 
		 position:relative; 
		left:0px; 
		width:50px;
		height: 50px;   
		background-color: blue;    
		opacity:0;    
		filter:alpha(opacity=0)}
		.addfileC{ 
   position:relative;   
    cursor:hand; 
	   text-decoration:none;   
	   background-image: url('images/goods_editor/u16.png'); 
	   width:50px;
	   height: 50px;
	   } 
		.addfileI2 {  
		 cursor:hand; 
		 position:relative; 
		left:0px; 
		width:50px;
		height: 50px;   
		background-color: blue;    
		opacity:0;    
		filter:alpha(opacity=0)}
		.addfileD{ 
   position:relative;   
    cursor:hand; 
	   text-decoration:none;   
	   background-image: url('images/goods_editor/u16.png'); 
	   width:50px;
	   height: 50px;
	   } 
		.addfileI3 {  
		 cursor:hand; 
		 position:relative; 
		left:0px; 
		width:50px;  
		height: 50px; 
		background-color: blue;    
		opacity:0;    
		filter:alpha(opacity=0)}
			.addfileE{ 
   position:relative;   
    cursor:hand; 
	   text-decoration:none;   
	   background-image: url('images/goods_editor/u16.png'); 
	   width:50px;
	   height: 50px;
	   } 
		.addfileI4 {  
		 cursor:hand; 
		 position:relative; 
		left:0px; 
		width:50px;  
		height: 50px; 
		background-color: blue;    
		opacity:0;    
		filter:alpha(opacity=0)}
			.addfileF{ 
   position:relative;   
    cursor:hand; 
	   text-decoration:none;   
	   background-image: url('images/goods_editor/u16.png'); 
	   width:50px;
	   height: 50px;
	   } 
		.addfileI5 {  
		 cursor:hand; 
		 position:relative; 
		left:0px; 
		width:50px;  
		height: 50px; 
		background-color: blue;    
		opacity:0;    
		filter:alpha(opacity=0)}
			 -->
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
 <script type="text/javascript">
   
	
	
$(document).ready(function(){
	  var qyijindex=window.document.getElementById("qyij").selectedIndex;//前端选中第一级
	  var qerjindex=window.document.getElementById("qerj").selectedIndex;//前端选中第二级
	  var qsanjindex=window.document.getElementById("qsanj").selectedIndex;//前端选中第三级
	  
	  var yijindex=window.document.getElementById("yij").selectedIndex;//药监选中第一级
	  var erjindex=window.document.getElementById("erj").selectedIndex;//药监选中第二级
	  var sanjindex=window.document.getElementById("sanj").selectedIndex;//药监选中第三级
	  //var val=window.document.getElementById("qyij").options[index].value;
	  qyijindex=document.getElementById("fxingyi").value;
	  qerjindex=document.getElementById("fxinger").value;
	  qsanjindex=document.getElementById("fxingsan").value;
	  
	  yijindex=document.getElementById("yxingyi").value;
	  erjindex=document.getElementById("yxinger").value;
	  sanjindex=document.getElementById("yxingsan").value;
	  //alert(qyijindex);
	 // alert(qerjindex);
	 // alert(qsanjindex);
	 // wyiji(qyijindex,qerjindex);//药监
	  //werji(erjindex);//药监
	 // wsanji(sanjindex);//药监
	  wqyiji(qyijindex,qerjindex);//前端
	  wqerji(qerjindex,qsanjindex);//前端
	  wqsanji(qsanjindex);//前端
	  
	  
	  wyiji(yijindex,erjindex);//药监
	  werji(erjindex,sanjindex);//药监
	  wsanji(sanjindex);//药监
	  <c:forEach items="${listlableid}" var="a"> 
	  addItem('${a.labelId}', '${a.labelName}');
	  </c:forEach>
	  
	 
	  $("#btnSubmit").click(function(){
		 	var val=$('input:radio[name="optionsRadios"]:checked').val();
			if(val==null){
				alert("请选中一个!");
				return false;
			}
			else{
				if(window.confirm('你确定要审核吗？')){
				var notPassCause=document.getElementById("notPassCause").value;
				
				 document.form.action = "goodsp.do?method=zshenghadd&val="+val+"&notPassCause="+notPassCause;
				 document.form.submit();
				}else{
					 return false;
				}
			}
		 });
	  
	  $(".pimg").click(function(){
			
			var _this = $(this);
			imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);
		});
});

//药监页面第一次加载第一级时出发的事件
function wyiji(yij,erjindex){
	//alert(yij);
	  var item="<option value=0 >请选择</option>"
	  document.getElementById("erj").length=0;
	  document.getElementById("sanj").length=0;
	 // var  yij=document.getElementById("yij").value;
	  $.ajax({
			type : "POST",  
			dataType: "json",//返回json格式的数据
			url:"<%=request.getContextPath()%>/goodsp.do?method=getTreeLister&ida="+yij,
			success: function(data){
	             $.each(data,function(i,result){  
	            	 item +="<option value="+result['id']+" >"+result['className']+"</option>";
	             });  
	             $('#erj').append(item);  
	             $("#erj option").each(function(){
	                // alert($(this).val());
	                 if($(this).val() == erjindex){
	                 $(this).attr("selected",true);
	                 }
	             });   
			}
		});
}
//药监页面第一次加载第二级点击时触发的事件
function werji(erj,sanjindex){
	  var item="<option value=0>请选择</option>"
	  document.getElementById("sanj").length=0;
	 // var  erj=document.getElementById("erj").value;
	  $.ajax({
			type : "POST",  
			dataType: "json",//返回json格式的数据
			url:"<%=request.getContextPath()%>/goodsp.do?method=getTreeListsan&idc="+erj,
			success: function(data){
	             $.each(data,function(i,result){  
	            	  item +="<option value="+result['id']+">"+result['className']+"</option>";
	             });  
	             $('#sanj').append(item);
	             $("#sanj option").each(function(){
	                // alert($(this).val());
	                 if($(this).val() == sanjindex){
	                 $(this).attr("selected",true);
	                 }
	             });   
			}
		});
}
//药监页面第一次加载点击第三级时出发的事件
function wsanji(sanj){
	 // var sanj=document.getElementById("sanj").value;
	  $.ajax({
			type : "POST",  
			dataType: "json",//返回json格式的数据
			url:"<%=request.getContextPath()%>/goodsp.do?method=getTreesan&sanj="+sanj,
			success: function(data){
	           
			}
		});
}
	
	
//前端商品点击第一级时出发的事件
function wqyiji(qyij,qerjindex){
	  var item="<option value=0 >请选择</option>"
	  document.getElementById("qerj").length=0;
	  document.getElementById("qsanj").length=0;
	  //var  qyij=document.getElementById("qyij").value;
	  $.ajax({
			type : "POST",  
			dataType: "json",//返回json格式的数据
			url:"<%=request.getContextPath()%>/goodsp.do?method=getTreeListerq&ida="+qyij,
			success: function(data){
	             $.each(data,function(i,result){  
	            	 item +="<option value="+result['id']+">"+result['className']+"</option>";
	             });  
	             $('#qerj').append(item);
	             $("#qerj option").each(function(){
	                 //alert($(this).val());
	                 if($(this).val() == qerjindex){
	                 $(this).attr("selected",true);
	                 }
	             });       
			}
		});
	
	
}
//前端商品第二级点击时触发的事件
function wqerji(qerj,qsanjindex){
	  var item="<option value=0>请选择</option>"
	  document.getElementById("qsanj").length=0;
	  //var  qerj=document.getElementById("qerj").value;
	  $.ajax({
			type : "POST",  
			dataType: "json",//返回json格式的数据
			url:"<%=request.getContextPath()%>/goodsp.do?method=getTreeListsanq&idc="+qerj,
			success: function(data){
	             $.each(data,function(i,result){  
	            	  item +="<option value="+result['id']+">"+result['className']+"</option>";
	             });  
	             $('#qsanj').append(item);  
	             $("#qsanj option").each(function(){
	                // alert($(this).val());
	                 if($(this).val() == qsanjindex){
	                 $(this).attr("selected",true);
	                 }
	             });      
			}
		});
}
//前端商品点击第三级时出发的事件
function wqsanji(qsanj){
	  //var qsanj=document.getElementById("qsanj").value;
	  $.ajax({
			type : "POST",  
			dataType: "json",//返回json格式的数据
			url:"<%=request.getContextPath()%>/goodsp.do?method=getTreesanq&sanj="+qsanj,
			success: function(data){
	           
			}
		});
}
    




//药监点击第一级时出发的事件
function yiji(){
	  var item="<option value=0 >请选择</option>"
	  document.getElementById("erj").length=0;
	  document.getElementById("sanj").length=0;
	  var  yij=document.getElementById("yij").value;
	  $.ajax({
			type : "POST",  
			dataType: "json",//返回json格式的数据
			url:"<%=request.getContextPath()%>/goodsp.do?method=getTreeLister&ida="+yij,
			success: function(data){
	             $.each(data,function(i,result){  
	            	 item +="<option value="+result['id']+">"+result['className']+"</option>";
	             });  
	             $('#erj').append(item);  
			}
		});
}
//药监第二级点击时触发的事件
function erji(){
	  var item="<option value=0>请选择</option>"
	  document.getElementById("sanj").length=0;
	  var  erj=document.getElementById("erj").value;
	  $.ajax({
			type : "POST",  
			dataType: "json",//返回json格式的数据
			url:"<%=request.getContextPath()%>/goodsp.do?method=getTreeListsan&idc="+erj,
			success: function(data){
	             $.each(data,function(i,result){  
	            	  item +="<option value="+result['id']+">"+result['className']+"</option>";
	             });  
	             $('#sanj').append(item);  
			}
		});
}
//药监点击第三级时出发的事件
function sanji(){
	  var sanj=document.getElementById("sanj").value;
	  $.ajax({
			type : "POST",  
			dataType: "json",//返回json格式的数据
			url:"<%=request.getContextPath()%>/goodsp.do?method=getTreesan&sanj="+sanj,
			success: function(data){
	           
			}
		});
}
	
	
//前端商品点击第一级时出发的事件
function qyiji(){
	  var item="<option value=0 >请选择</option>"
	  document.getElementById("qerj").length=0;
	  document.getElementById("qsanj").length=0;
	  var  qyij=document.getElementById("qyij").value;
	  $.ajax({
			type : "POST",  
			dataType: "json",//返回json格式的数据
			url:"<%=request.getContextPath()%>/goodsp.do?method=getTreeListerq&ida="+qyij,
			success: function(data){
	             $.each(data,function(i,result){  
	            	 item +="<option value="+result['id']+">"+result['className']+"</option>";
	             });  
	             $('#qerj').append(item);  
			}
		});
}
//前端商品第二级点击时触发的事件
function qerji(){
	  var item="<option value=0>请选择</option>"
	  document.getElementById("qsanj").length=0;
	  var  qerj=document.getElementById("qerj").value;
	  $.ajax({
			type : "POST",  
			dataType: "json",//返回json格式的数据
			url:"<%=request.getContextPath()%>/goodsp.do?method=getTreeListsanq&idc="+qerj,
			success: function(data){
	             $.each(data,function(i,result){  
	            	  item +="<option value="+result['id']+">"+result['className']+"</option>";
	             });  
	             $('#qsanj').append(item);  
			}
		});
}
//前端商品点击第三级时出发的事件
function qsanji(){
	  var qsanj=document.getElementById("qsanj").value;
	  $.ajax({
			type : "POST",  
			dataType: "json",//返回json格式的数据
			url:"<%=request.getContextPath()%>/goodsp.do?method=getTreesanq&sanj="+qsanj,
			success: function(data){
	           
			}
		});
}




//标签单机第一级时！得到第二级的数据
function oneji(){
	var item="";
	 document.getElementById("twoj").length=0;
	 document.getElementById("threej").length=0;
	 document.getElementById("fourj").length=0;
	 var onej=document.getElementById("onej").value;
	  $.ajax({
			type : "POST",  
			dataType: "json",//返回json格式的数据
			url:"<%=request.getContextPath()%>/goodsp.do?method=getTreeLabelers&fid="+onej,
			success: function(data){
	             $.each(data,function(i,result){  
	            	 item +="<option value="+result['id']+">"+result['labelName']+"</option>";
	             });  
	             $('#twoj').append(item);  
			}
		});
}
//标签单机第第二级时！得到第三级的数据
function twoji(){
	var item="";
	 document.getElementById("threej").length=0;
	 document.getElementById("fourj").length=0;
	 var twoj=document.getElementById("twoj").value;
	  $.ajax({
			type : "POST",  
			dataType: "json",//返回json格式的数据
			url:"<%=request.getContextPath()%>/goodsp.do?method=getTreeLabelsans&fid="+twoj,
			success: function(data){
	             $.each(data,function(i,result){  
	            	 item +="<option value="+result['id']+">"+result['labelName']+"</option>";
	             });  
	             $('#threej').append(item);  
			}
		});
}
function threeji(){
	var item="";
	 document.getElementById("fourj").length=0;
	 var threej=document.getElementById("threej").value;
	  $.ajax({
			type : "POST",  
			dataType: "json",//返回json格式的数据
			url:"<%=request.getContextPath()%>/goodsp.do?method=getTreeLabelsis&fid="+threej,
			success: function(data){
	             $.each(data,function(i,result){  
	            	 item +="<option value="+result['id']+">"+result['labelName']+"</option>";
	             });  
	             $('#fourj').append(item);  
			}
		});
}
function addItem(kw, text){
	var html = '<div class="select-items" id="kw" value='+kw+'><span>'+ text +'</span><i>x</i></div>';
	$('#keywordPanel').append(html).find('.select-items').off().on({
		mouseenter: function(){
			$(this).addClass('select-items-hover');
		},
		mouseleave: function(){
			$(this).removeClass('select-items-hover');
		},
		click: function(){
			
			$(this).detach();
		}
	});

	
}







function addfl(){
	
	var flone=document.getElementById("onej").value;//id值
	var fltwo=document.getElementById("twoj").value;//id值
	var flthree=document.getElementById("threej").value;//id值
	var flfour=document.getElementById("fourj").value;//id值
	
	if(flone!=""){
		if(fltwo==""&&flthree==""&&flfour==""){
			//alert("one"+flone);flid
		   var onename=jQuery("#onej  option:selected").text();
			addItem(flone, onename);
		   //$('#flid').append(onename);
		}
		if(fltwo!=""&&flthree==""&&flfour==""){
			//alert("two"+fltwo);
			var twoname=jQuery("#twoj  option:selected").text();
			//$('#flid').append(twoname);
			addItem(fltwo, twoname);
			
		}
		if(fltwo!=""&&flthree!=""&&flfour==""){
			//alert("three"+flthree);
			var threename=jQuery("#threej  option:selected").text();
			//$('#flid').append(threename);
			addItem(flthree, threename);
		}
		if(fltwo!=""&&flthree!=""&&flfour!=""){
			//alert("four"+flfour);
			var fourname=jQuery("#fourj  option:selected").text();
			addItem(flfour, fourname);
			//$('#flid').append(fourname);
		}
	}
}
function add(){
	var ids="";
	$.each($('#keywordPanel').find('.select-items'), function(i){
		var id =$(this).attr("value");
		alert(id);
		ids+=id+",";
	});
	
	//var imgPath=document.getElementById("bpath").value;//第一张图片
   // var bpath1=document.getElementById("bpath1").value;//第二张图片
    //var bpath2=document.getElementById("bpath2").value;//第三张图片
    //var bpath3=document.getElementById("bpath3").value;//第四张图片
    //var bpath4=document.getElementById("bpath4").value;//第五张图片
    //var bpath5=document.getElementById("bpath5").value;//第六张图片
   // alert("imgPath"+imgPath);
   // alert("bpath1"+bpath1);
   // alert("bpath2"+bpath2);
	//alert("bpath3"+bpath3);
	//alert("bpath4"+bpath4);
	//alert("bpath5"+bpath5);
	//document.form.action="goodsp.do?method=upload&imgPath="+imgPath+"&bpath1="+bpath1+"&bpath2="+bpath2+"&bpath3="+bpath3;	
	 document.form.action = "goodsp.do?method=upload&ids="+ids;
	 document.form.submit();
}



function imgShow(outerdiv, innerdiv, bigimg, _this){
	var src = _this.attr("src");//获取当前点击的pimg元素中的src属性
	$(bigimg).attr("src", src);//设置#bigimg元素的src属性
	   /*获取当前点击图片的真实大小，并显示弹出层及大图*/
	$("<img/>").attr("src", src).load(function(){
		var windowW = $(window).width();//获取当前窗口宽度
		var windowH = $(window).height();//获取当前窗口高度
		var realWidth = this.width;//获取图片真实宽度
		var realHeight = this.height;//获取图片真实高度
		var imgWidth, imgHeight;
		var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放
		
		if(realHeight>windowH*scale) {//判断图片高度
			imgHeight = windowH*scale;//如大于窗口高度，图片高度进行缩放
			imgWidth = imgHeight/realHeight*realWidth;//等比例缩放宽度
			if(imgWidth>windowW*scale) {//如宽度扔大于窗口宽度
				imgWidth = windowW*scale;//再对宽度进行缩放
			}
		} else if(realWidth>windowW*scale) {//如图片高度合适，判断图片宽度
			imgWidth = windowW*scale;//如大于窗口宽度，图片宽度进行缩放
			imgHeight = imgWidth/realWidth*realHeight;//等比例缩放高度
		} else {//如果图片真实高度和宽度都符合要求，高宽不变
			imgWidth = realWidth;
			imgHeight = realHeight;
		}
		$(bigimg).css("width",imgWidth);//以最终的宽度对图片缩放
		
		var w = (windowW-imgWidth)/2;//计算图片与窗口左边距
		var h = (windowH-imgHeight)/2;//计算图片与窗口上边距
		$(innerdiv).css({"top":h, "left":w});//设置#innerdiv的top和left属性
		$(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
	});
	
	$(outerdiv).click(function(){
		$(this).fadeOut("fast");
	});
}
function back(){
	 document.form.action = "goodsp.do?method=zback";
	 document.form.submit();
}


    </script>
</head>
<body class="table table-striped table-bordered table-condensed">
<form   name="form" method="post" enctype="multipart/form-data">
<input type="hidden" id="pageno" name="pageno" value="${pageno}"/>
		<input type="hidden" id="pageSize" name="pageSize" value="${pageSize}"/>
 <input type="hidden" id="patha" value="${patha}"/>
    <input type="hidden" id="proportion" value=""/>
    <input type="hidden" id="bpath" value="${bpath}" />
    <input type="hidden" id="bpath1" value="${bpath1}" />
    <input type="hidden" id="bpath2" value="${bpath2}" />
    <input type="hidden" id="bpath3" value="${bpath3}" />
    <input type="hidden" id="bpath4" value="${bpath4}" />
    <input type="hidden" id="bpath5" value="${bpath5}" />
    
    <input type="hidden" id="fxingyi" value="${fxingyi}" />
    <input type="hidden" id="fxinger" value="${fxinger}" />
    <input type="hidden" id="fxingsan" value="${fxingsan}" />
    
    <input type="hidden" id="yxingyi" value="${yxingyi}" />
    <input type="hidden" id="yxinger" value="${yxinger}" />
    <input type="hidden" id="yxingsan" value="${yxingsan}" />
     <input type="hidden" id="seachgoodsName" name="seachgoodsName" value="${seachgoodsName}" />
    <input type="hidden" id="ba" name="ba" value="${ba}" />
    <input type="hidden" id="shangpin" name="shangpin" value="${shangpin}" />
    <input type="hidden" id="seachinteriorCode" name="seachinteriorCode" value="${seachinteriorCode}" />
    <input type="hidden" id="seachbrand" name="seachbrand" value="${seachbrand}" />
    <input type="hidden" id="seachshzt" name="seachshzt" value="${seachshzt}" />
    <input type="hidden" id="starjia" name="starjia" value="${starjia}" />
    <input type="hidden" id="endjia" name="endjia" value="${endjia}" />
    
    <input type="hidden" id="yijid" name="yijid" value="${yijid}"/>
		<input type="hidden" id="erjid" name="erjid" value="${erjid}"/>
		<input type="hidden" id="sanjid" name="sanjid" value="${sanjid}"/>
		
		<input type="hidden" id="qyijid" name="qyijid" value="${qyijid}"/>
		<input type="hidden" id="qerjid" name="qerjid" value="${qerjid}"/>
		<input type="hidden" id="qsanjid" name="qsanjid" value="${qsanjid}"/>
		<input type="hidden" id="includeImages" name="includeImages" value="${includeImages}"/>
		<input type="hidden" id="seachmanufacturer" name="seachmanufacturer" value="${seachmanufacturer}"/>
<input type="hidden" name="id" id="id" value="${goods.id }"/>
<input type="hidden" name="listlableid" id="temps" value="${listlableid }"/>
<div class="right_box">
<div class="right_h"><h3> 商品详情</h3></div>
  
    <div class="right_title">商品类型</div>
	<div class="right_input" style="width:100px;"><select name="goodsType" id="goodsType" class="form-control input-sm" disabled="disabled">
    <option value="" >不限</option>
    <c:forEach items="${listgoods }" var="item">
    <option value="${item.id }" ${goods.goodsType == item.id? 'selected=selected':'' }>${item.attname }</option>
    </c:forEach>
    </select></div>
</div>
 <div class="right_h right_bg" > 通用商品信息</div>
 <div class="right_box2"><div class="right_box_l">
  <div class="right_inline">
  <div class="right_title">内部ID</div>
  <div class="right_input" style="width:300px;"><input disabled="disabled" name="interiorId" id="interiorId" value="${goods.interiorId }" type="text" class="form-control input-sm"  placeholder=" "></div>
  </div>
  <div class="right_inline">
  <div class="right_title">内部编码</div>
  <div class="right_input" style="width:300px;"><input disabled="disabled" name="interiorCode" id="interiorCode" value="${goods.interiorCode }" type="text" class="form-control input-sm" placeholder=" "></div>
  </div>
  <div class="right_inline">
  <div class="right_title">条形码</div>
  <div class="right_input" style="width:300px;"><input disabled="disabled" name="barCode" id="barCode" value="${goods.barCode }" type="text" class="form-control input-sm"  placeholder=" "></div>
  </div>
  <div class="right_inline">
  <div class="right_title">通用名</div>
  <div class="right_input" style="width:300px;"><input disabled="disabled" name="commonName" id="commonName" value="${goods.commonName }" type="text" class="form-control input-sm"  placeholder="普通商品的商品名"></div>
  </div>
  <div class="right_inline">
  <div class="right_title">商品名</div>
  <div class="right_input" style="width:300px;"><input disabled="disabled" name="goodsName" id="goodsName" value="${goods.goodsName }" type="text" class="form-control input-sm"  placeholder=" "></div>
  </div>
  <div class="right_inline">
  <div class="right_title">英文或拼音</div>
  <div class="right_input" style="width:300px;"><input disabled="disabled" name="englishOrPinyin" id="englishOrPinyin" value="${goods.englishOrPinyin }" type="text" class="form-control input-sm"  placeholder=" "></div>
  </div>
  <div class="right_inline">
  <div class="right_title">拼音编码</div>
  <div class="right_input" style="width:300px;"><input disabled="disabled" name="pinyinCode" id="pinyinCode" value="${goods.pinyinCode }" type="text" class="form-control input-sm"  placeholder=" "></div>
  </div>
  </div><div class="right_box_pic " style="margin-left:50px;">
  
    <div >
    <div id="outerdiv" style="position:fixed;top:-999px;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;"><div id="innerdiv" style="position:absolute;"><img id="bigimg" style="border:5px solid #fff;" src="" /></div></div>
	<div class="right_inline">
	<div class="right_pic_lg"> <img class="pimg" src="${bpath}"   id="filepath" name="filepath"  value="${bpath}"   width="90" height="70"/></div>
	<div class="right_pic_sm">
	   <div class="right_pic"><img class="pimg" src="${bpath1}"  id="filepatha" name="filepatha"   value="${bpath1}"  width="90" height="70"/></div>
	   <div class="right_pic"><img class="pimg" src="${bpath2}"  id="filepathb"  name="filepathb"   value="${bpath2}"  width="90" height="70"/></div>
	   <div class="right_pic"> <img class="pimg" src="${bpath3}"  id="filepathc"  name="filepathc"  value="${bpath3}"  width="90" height="70"/></div>
	   <div class="right_pic"> <img class="pimg" src="${bpath4}"  id="filepathd"  name="filepathd"  value="${bpath4}"  width="90" height="70"/></div>
	</div>
	</div>
	<div class="right_inline">
	<div class="right_pic_long"><img class="pimg" src="${bpath5}"  id="filepathe"  name="filepathe"  value="${bpath5}"  width="90" height="70"/></div>
	</div>
 
  
  
 
 
  
    </div>
     <!-- 
	<div class="right_pic" >
	<i class="glyphicon glyphicon-plus " >
    <A hideFocus class=addfileB id=aComposeAttach href="#">
    <input class=addfileI1 size=1 type="file"  name="imgPatha" id="imgPatha"    onchange="javascript:openBrowsea();"/>
    </A>
    </i>
    </div>
    <div class="right_pic" >
    <i class="glyphicon glyphicon-plus " >
    <A hideFocus class=addfileA id=aComposeAttach href="#">
    <input class=addfileI size=1 type="file"  name="imgPath" id="imgPath"   onchange="javascript:openBrowse();"/>
    </A>
	</i>
	</div>
   
	<div class="right_pic" >
	<i class="glyphicon glyphicon-plus " >
	<A hideFocus class=addfileD id=aComposeAttach href="#">
    <input class=addfileI3 size=1 type="file"  name="imgPathc" id="imgPathc"    onchange="javascript:openBrowsec();"/>
    </A>
	</i>
	</div>
	
	 <div class="right_pic" >
	<i class="glyphicon glyphicon-plus " >
	<A hideFocus class=addfileC id=aComposeAttach href="#">
    <input class=addfileI2 size=1 type="file"  name="imgPathb" id="imgPathb"    onchange="javascript:openBrowseb();"/>
    </A>
	</i>
	</div>
	  
	 <div class="right_pic" >
	<i class="glyphicon glyphicon-plus " >
	<A hideFocus class=addfileF id=aComposeAttach href="#">
    <input class=addfileI5 size=1 type="file"  name="imgPathe" id="imgPathe"    onchange="javascript:openBrowsee();"/>
    </A>
	</i>
	</div>
	
	
	 <div class="right_pic" >
	<i class="glyphicon glyphicon-plus " >
	<A hideFocus class=addfileE id=aComposeAttach href="#">
    <input class=addfileI4 size=1 type="file"  name="imgPathd" id="imgPathd"    onchange="javascript:openBrowsed();"/>
    </A>
	</i>
	</div>
	
	-->
	


   
	


  </div></div>
  
  
  <div class="right_inline">
  <div class="right_title">前端分类一级</div>
  
  <div class="right_input" style="width:150px;">
<select id="qyij" name="qyij" onChange="qyiji()" disabled="disabled" class="form-control input-sm" >
<option value="0">请选择</option>
   <c:forEach items="${listq}" var="item">
   <option value="${item.id }" ${fxingyi eq item.id? 'selected=selected':'' } >${item.className }</option>
   </c:forEach>
</select>
  </div>
  <div class="right_title_min">二级</div>
  <div class="right_input" style="width:150px;">
<select id="qerj" disabled="disabled" name="qerj"  onchange="qerji()" class="form-control input-sm" >
  <option value="0">请选择</option>
</select>
  </div>
  <div class="right_title_min">三级</div>
  <div class="right_input" style="width:150px;">
<select name="qsanj" disabled="disabled" id="qsanj" onChange="qsanji()" class="form-control input-sm" >
  <option>请选择</option>
</select>
  </div>
  </div>
  <div class="right_inline">
  <div class="right_title">关键词</div>
  
  <div class="right_input" style="width:500px;"><input disabled="disabled" name="keyWord" id="keyWord" value="${goods.keyWord }" type="text" class="form-control input-sm"  placeholder=" "></div>
  </div>
  <div class="right_inline">
  <div class="right_title">规格型号</div>
  
  <div class="right_input" style="width:300px;"><input disabled="disabled" name="specifications" id="specifications" value="${goods.specifications }" type="text" class="form-control input-sm"  placeholder="纸铝复合膜包装；6包/盒、8包/盒、10包/盒

、12包/盒、14包/盒、20包/盒。 "></div>
  <div class="right_title textright">品牌</div>

 <div class="right_input" style="width:300px;"><input disabled="disabled" name="brand" id="brand" value="${goods.brand }" type="text" 

class="form-control input-sm"  placeholder=" 江中"></div>
  </div>
  
  <div class="right_inline">
  <div class="right_title">生产企业</div>
  
  <div class="right_input" style="width:300px;"><input disabled="disabled" name="manufacturer" id="manufacturer" value="${goods.manufacturer }" type="text" 

class="form-control input-sm"  placeholder="纸铝复合膜包装；6包/盒、8包/盒、10包/盒

、12包/盒、14包/盒、20包/盒。 "></div>
  <div class="right_title textright">生产地址</div>

 <div class="right_input" style="width:300px;"><input disabled="disabled" name="productionAddress" id="productionAddress" value="${goods.productionAddress }" type="text" 

class="form-control input-sm"  placeholder=" 江中"></div>
  </div>
  
  <div class="right_box2"><div class="right_box_l">
   
        <div class="right_inline">简介</div>
		<div class="right_inline"><textarea name="brief" disabled="disabled" id="brief" value="${goods.brief }" rows="3" 

class="form-control input-sm" style="width:360px;"></textarea></div>
  </div>
  
  
  <div class="right_box_l">
   
        <div class="right_inline">详细介绍纯文本</div>
		<div class="right_inline"><textarea name="goodsDetails" disabled="disabled" id="goodsDetails" value="${goods.goodsDetails }" class="form-control input-sm" 

rows="3" style="width:360px;"></textarea></div>
      
    
  </div>
  <div class="right_box_l">
   
        <div class="right_inline">详细介绍（html）</div>
		<div class="right_inline"><textarea name="goodsDetailsHtml" disabled="disabled" id="goodsDetailsHtml" value="${goods.goodsDetailsHtml }" class="form-control input-sm" 

rows="3" style="width:360px;"></textarea></div>
      
    
  </div>
  <div class="right_box_l">
   
        <div class="right_inline">图文介绍（html）</div>
		<div class="right_inline"><textarea name="picTextDetailsHtml" disabled="disabled" id="picTextDetailsHtml" value="${goods.picTextDetailsHtml }" class="form-control input-sm" 

rows="3" style="width:360px;"></textarea></div>
      
    
  </div>
  </div>
  
  
  
  
  
 


<div class="right_h right_bg"><span id="productBtn" style="margin-right:20px; float:right; font-size:12px;">收起</span>药品专属信息</div>
   
   <div id="productPanel" class="right_box2" >
   <div class="right_inline">
  <div class="right_title">质检分类一级</div>
  
  <div class="right_input" style="width:180px;">
    <select name="yij" disabled="disabled" id="yij" onChange="yiji()"  class="form-control input-sm" >
     <option value="0">请选择</option>
      <c:forEach items="${lista}" var="item">
      <option value="${item.id }" ${yxingyi eq item.id? 'selected=selected':'' } >${item.className }</option>
      </c:forEach>
    </select>
  </div>
  <div class="right_title_min">二级</div>
  <div class="right_input" style="width:180px;">
    <select id="erj" disabled="disabled" onChange="erji()" name="erj" class="form-control input-sm" >
     <option value="0">请选择</option>
    </select>
  </div>
  <div class="right_title_min">三级</div>
  <div class="right_input"  style="width:180px;">
    <select id="sanj" disabled="disabled" onChange="sanji()" name="sanj" class="form-control input-sm" >
      <option>请选择</option>
    </select>
  </div>
   </div>
  <div class="right_inline">
  <div class="right_title">批准文号</div>
  
  <div class="right_input" style="width:150px;"><span class="right_input" 

style="width:150px;">
    <input name="approvalDocument" disabled="disabled" id="approvalDocument" value="${goods.approvalDocument }" type="text" class="form-control input-sm"  placeholder=" ">
  </span></div>
  <div class="right_title_min">执行标准</div>
  <div class="right_input" style="width:150px;"><input disabled="disabled" id="standard" value="${goods.standard }" name="standard" type="text" 

class="form-control input-sm"  placeholder=" "></div>
  <div class="right_title_min">卫生许可证号</div>
  <div class="right_input" style="width:150px;"><input disabled="disabled" id="hygieneLicense" value="${goods.hygieneLicense }" name="hygieneLicense" type="text" 

class="form-control input-sm"  placeholder=" "></div>
  </div>
  
  <div class="right_inline">
  <div class="right_title">处方类型</div>
  
  <div class="right_input"  style="width:120px;">
    <select disabled="disabled" name="prescriptionType" id="prescriptionType"  class="form-control input-sm" >
      <option value="${goods.prescriptionType}">${goods.prescriptionType}</option>
    </select>
  </div>
   <div class="right_title_min"><label class="checkbox-inline">
   <c:if test="${goods.gmp==1 }">
  <input type="checkbox" disabled="disabled"  checked="checked" name="gmp"  id="gmp" value="${goods.gmp }">
  </c:if>
   <c:if test="${goods.gmp==0 }">
  <input type="checkbox" disabled="disabled"  name="gmp"  id="gmp" value="${goods.gmp }">
  </c:if>
   GMP已认证
</label>
   </div>
  <div class="right_title_min">是否医保</div>
  <div class="right_input"  style="width:120px;">
    <select name="healthCare" disabled="disabled" id="healthCare" class="form-control input-sm" >
    <option value="0"${goods.healthCare=='0'? 'selected=selected':'' }>非医保</option>
    <option value="1"${goods.healthCare=='1'? 'selected=selected':'' }>医保</option>
    </select>
  </div>
  </div>
  <div class="right_inline">
  <div class="right_title">有效期 </div>
  
  <div class="right_input" style="width:190px;">
    <input name="valid" disabled="disabled" id="valid" value="${goods.valid }" type="text" class="form-control input-sm"  placeholder=" 

">
  </div>
  <div class="right_title_min">存储条件</div>
  <div class="right_input" style="width:190px;">
    <input name="storage" disabled="disabled" id="storage" value="${goods.storage }" type="text" class="form-control input-sm"  placeholder=" 

">
 </div>
  </div>
  <div class="right_inline">
   <div class="right_box_3">
   <div class="right_title2"><nobr title="主要成分及含量[全]">主要成分及含量[全]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="ingredients" name="ingredients"  class="form-control input-sm" rows="4" 

>${goods.ingredients }</textarea></div>
   </div>
   
   
   <div class="right_box_3" >
   <div class="right_title2"><nobr title="性状[药品,食品保健品,化妆品,日用品]">性状[药品,食品保健品,化妆品,日用品]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="tcac" name="tcac" class="form-control input-sm" rows="4" 

> ${goods.tcac }</textarea></div>
   </div>
   
   
   <div class="right_box_3">
   <div class="right_title2"><nobr title="适应症[药品] 适用范围[器械,化妆品,日用品]">适应症[药品] 适用范围[器械,化妆品,日用品]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="indication" name="indication" class="form-control input-sm" rows="4" 
>${goods.indication }</textarea></div>
   </div>
   
   <div class="right_box_3">
      <div class="right_title2"><nobr title="用法用量[药品,食品保健品,化妆品,日用品]">用法用量[药品,食品保健品,化妆品,日用品]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="usageAndDosage" name="usageAndDosage" class="form-control input-sm" rows="4" 

>${goods.usageAndDosage }</textarea></div>
   </div>
   <div class="right_box_3">
 <div class="right_title2"><nobr title="不良反应[药品,器械,日用品]">不良反应[药品,器械,日用品]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="adverseDrugReactions" name="adverseDrugReactions" class="form-control input-sm" rows="4" 

>${goods.adverseDrugReactions }</textarea></div>
   </div>
   <div class="right_box_3">
    <div class="right_title2"><nobr title="禁忌[药品,食品保健品,化妆品,日用品]">禁忌[药品,食品保健品,化妆品,日用品]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="taboo" name="taboo" class="form-control input-sm" rows="4" 

>${goods.taboo }</textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="功能主治[药品]">功能主治[药品]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="treatment" name="treatment" class="form-control input-sm" rows="4" 

>${goods.treatment }</textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="药理作用[药品]">药理作用[药品]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="pharmacological" name="pharmacological" class="form-control input-sm" rows="4" 

>${goods.pharmacological }</textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="药物相互作用[药品]">药物相互作用[药品]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="interactions" name="interactions" class="form-control input-sm" rows="4" 

>${goods.interactions }</textarea></div>
   </div>
   <div class="right_box_3">
    <div class="right_title2"><nobr title="药代动力学[药品]">药代动力学[药品]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="pharmacokinetics" name="pharmacokinetics" class="form-control input-sm" rows="4" 

>${goods.pharmacokinetics }</textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="特殊人群用药[药品]">特殊人群用药[药品]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="tsrqyy" name="tsrqyy" class="form-control input-sm" rows="4" 

>${goods.tsrqyy }</textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="药物过量[药品]">药物过量[药品]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="overdose" name="overdose" class="form-control input-sm" rows="4" 

>${goods.overdose }</textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="性味归经[中药]">性味归经[中药]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="xwgj" name="xwgj" class="form-control input-sm" rows="4" 

>${goods.xwgj }</textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="药理毒理[药品]">药理毒理[药品]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="yldl" name="yldl" class="form-control input-sm" rows="4" 

>${goods.yldl }</textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="说明书核准日期[药品,食品保健品,化妆品]">说明书核准日期[药品,食品保健品,化妆品]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="smshzrq" name="smshzrq" class="form-control input-sm" rows="4" 

>${goods.smshzrq }</textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="作用类别[药品,日用品,化妆品]">作用类别[药品,日用品,化妆品]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="categories" name="categories" class="form-control input-sm" rows="4" 

>${goods.categories }</textarea></div>
   </div>
   <div class="right_box_3">
     <div class="right_title2"><nobr title="作用用途[化妆品,日用品] 保健作用[保健品]">作用用途[化妆品,日用品] 保健作用[保健品]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="purpose"  name="purpose" class="form-control input-sm" rows="4" 

>${goods.purpose }</textarea></div>
   </div>
   <div class="right_box_3">
  <div class="right_title2"><nobr title="生产许可证号[化妆品]">生产许可证号[化妆品]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="license" name="license" class="form-control input-sm" rows="4" 

>${goods.license }</textarea></div>
   </div>
   <div class="right_box_3">
    <div class="right_title2"><nobr title="适宜人群[器械,食品保健品,化妆品,日用品]">适宜人群[器械,食品保健品,化妆品,日用品]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="syrq" name="syrq" class="form-control input-sm" rows="4" 

>${goods.syrq }</textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="不适宜人群[保健品,化妆品]">不适宜人群[保健品,化妆品]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="bsyrq" name="bsyrq" class="form-control input-sm" rows="4" 

>${goods.bsyrq }</textarea></div>
   </div>
   <div class="right_box_3">
  <div class="right_title2"><nobr title="适合年龄[日用品]">适合年龄[日用品]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="shnl" name="shnl" class="form-control input-sm" rows="4" 

>${goods.shnl }</textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="注意事项[全]">注意事项[全]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="notes" name="notes"  class="form-control input-sm" rows="4" 

>${goods.notes }</textarea></div>
   </div>
   <div class="right_box_3">
    <div class="right_title2"><nobr title="企业介绍[全]">企业介绍[全]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="introduction"  name="introduction" class="form-control input-sm" rows="4" 

>${goods.introduction }</textarea></div>
   </div>
     <div class="right_box_3">
   <div class="right_title2"><nobr title="使用方法[器械]">使用方法[器械]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="methodOfUse" name="methodOfUse"  class="form-control input-sm" rows="4" 

>${goods.methodOfUse }</textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="适用范围[器械,化妆品,日用品]">适用范围[器械,化妆品,日用品]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="scopeOfApplication" name="scopeOfApplication" class="form-control input-sm" rows="4" 

>${goods.scopeOfApplication }</textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="性能结构及组成[器械]">性能结构及组成[器械]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="xnjgjzc" name="xnjgjzc" class="form-control input-sm" rows="4" 

>${goods.xnjgjzc }</textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="临床试验[药品,器械]">临床试验[药品,器械]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="clinicalTrials" name="clinicalTrials" class="form-control input-sm" rows="4" 

>${goods.clinicalTrials }</textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="注册证[器械]">注册证[器械]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="registration" name="registration" class="form-control input-sm" rows="4" 

>${goods.registration }</textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="完整说明书文本(长文本]">完整说明书文本(长文本]</nobr></div>
   <div class="right_inline"><textarea disabled="disabled" id="allInstructions" name="allInstructions" class="form-control input-sm" rows="4" 

>${goods.allInstructions }</textarea></div>
   </div>
  </div>
  
  <div class="right_inline">
  <div class="right_title" >
    其他分类<br>
          属性关键词  </div>
  
  <div class="right_input" style="width:720px;">
<div id="keywordPanel" class="select-box"></div>
 
  </div>
  <div class="right_inline">
  <div class="right_title" style="height:10px;"></div>
  
  <div class="right_box_4">
  <select id="onej" disabled="disabled" onChange="oneji()" multiple class="form-control">
  <c:forEach items="${listlabel}" var="item">
  <option  value="${item.id }">${item.labelName }</option>
 </c:forEach>
</select>
  </div>
    <div class="right_box_4">
  <select id="twoj" disabled="disabled" name="twoj" onclick="twoji()" multiple class="form-control">
 
</select>
  </div>
    <div class="right_box_4">
  <select id="threej" disabled="disabled" name="threej" onChange="threeji()" multiple class="form-control">

</select>
  </div>
    <div class="right_box_4">
  <select id="fourj" disabled="disabled" name="fourj" multiple class="form-control">
 
</select>
  </div>
  
  </div>
  

  </div>
  
  <!----------------------->
  <div class="right_h2 right_bg" ><strong>包装属性 </strong></div>
   <div class="right_inline">
  <div class="right_title">小包单位</div>
  
  <div class="right_input" style="width:300px;"><input disabled="disabled" id="smallUnits" name="smallUnits" value="${goods.smallUnits }"  type="text" 

class="form-control input-sm"  

></div>
  <div class="right_title textright">最小单位</div>

 <div class="right_input" style="width:300px;"><input disabled="disabled" id="smallesUnit" name="smallesUnit" value="${goods.smallesUnit }"   type="text" 

class="form-control input-sm"  ></div>
  </div>
  <div class="right_inline">
  <div class="right_title">包装重量</div>
  
  <div class="right_input" style="width:300px;"><input disabled="disabled" id="tare" name="tare" value="${goods.tare }"  type="text" 

class="form-control input-sm"  

></div>
  <div class="right_title textright">小包数量</div>

 <div class="right_input" style="width:300px;"><input disabled="disabled" id="xbsl" name="xbsl"  value="${goods.xbsl }"  type="text" 

class="form-control input-sm"  ></div>
  </div>
  <div class="right_inline">
  <div class="right_title">包装尺寸</div>
  
  <div class="right_input" style="width:300px;"><input disabled="disabled" id="packingSize" name="packingSize" value="${goods.packingSize }"  type="text" 

class="form-control input-sm"  

></div>
  <div class="right_title textright">中包数量</div>

  <div class="right_input" style="width:300px;"><input disabled="disabled" id="zbsl" name="zbsl" value="${goods.zbsl }"  type="text" 

class="form-control input-sm"  ></div>
  </div>
  <div class="right_inline">
  <div class="right_title">净重</div>
  
  <div class="right_input" style="width:300px;"><input disabled="disabled" id="netWeight" name="netWeight" value="${goods.netWeight }"  type="text" 

class="form-control input-sm" ></div>
  <div class="right_title textright">大包数量</div>

 <div class="right_input" style="width:300px;"><input disabled="disabled" id="dbsl" name="dbsl" value="${goods.dbsl }"  type="text" 

class="form-control input-sm"  ></div>
  </div>
  <div class="right_inline">
    <div class="right_box_l">
	<div class="right_inline"><strong>基本定价属性</strong></div>
	<div class="right_inline">
  <div class="right_title">内成本粗估</div>
  <div class="right_input" style="width:300px;"><input disabled="disabled" id="costPrice" name="costPrice" value="${goods.costPrice }" type="text" 

class="form-control input-sm" placeholder=" "></div>
  </div>
  <div class="right_inline">
  <div class="right_title">建议零售价</div>
  <div class="right_input" style="width:300px;"><input disabled="disabled" id="retailPrice" name="retailPrice" value="${goods.retailPrice }"  type="text" 

class="form-control input-sm" placeholder=" "></div>
  </div>
  <div class="right_inline">
  <div class="right_title">基本定价</div>
  <div class="right_input" style="width:300px;"><input disabled="disabled" id="basicPricing" name="basicPricing" value="${goods.basicPricing }"  type="text" 

class="form-control input-sm" placeholder=" "></div>
  </div>

	
	</div>
	<div class="right_box_l">
	<div class="right_inline"><strong>销售状态</strong></div>
	
	<div class="right_inline">
  <div class="right_title">
    是否可售
  </div>
  <div class="right_input" style="width:300px;"><div class="right_input" 

style="width:100px;"><select id="salesStatus" name="goods.salesStatus" disabled="disabled" class="form-control input-sm">
    <option value="1"${goods.salesStatus=='1'? 'selected=selected':'' }>可销售</option>
    <option value="2"${goods.salesStatus=='2'? 'selected=selected':'' }>不可销售</option>
 
</select></div></div>
  </div>
  
  <div class="right_inline">
  <div class="right_title">库存数量</div>
  <div class="right_input" style="width:300px;"><input disabled="disabled"  type="text" 

class="form-control input-sm" placeholder=" "></div>
  </div>
 
	
	
	
	
	
	
	
	
	
	
	
	
	
	</div>

  <!-- 

  <div class="right_inline" style="padding-left:30px;">
        <div class="right_title"  style="width:80px"><label class="radio-inline" 

>
  <input type="radio" id="optionsRadios2" value="option1" style="margin-top:9px;" 

>
   通过质检

</label>
   </div>
   <div class="right_title"  style="width:110px"><label class="radio-inline" >
  <input type="radio" id="optionsRadios2" value="option1" style="margin-top:9px;" 

>
   未通过质检

</label>
   </div>
   <div class="right_input" style="width:130px;"><input  type="text" 

class="form-control input-sm" placeholder="未通过原因 "></div>
  </div>
 
 -->
 
  <div class="right_inline "><!--  <button type="button" class="btn btn-info   btn-sm 

pull-right" style="margin:5px 15px 0 0;">仅保存</button>-->

</div>
	</div></div>
    
	
	
	
  </div>
</div>

</form>
<script>
		$(document).ready(function(){
		
			$('#productName').blur(function(){
				if( $(this).val() != '' ){
					$('#jx').attr('href', 'http://www.jxdyf.com/search/'+ $(this).val() +'.html');
					$('#baidu').attr('href', 'http://www.baidu.com/#wd='+$(this).val());
				};
			});
			$('#productBtn').click(function(){
				$('#productPanel').hide();
			});
			$('#keyword1').find('option').on({
				click: function(){
					var db = ['21','22','23'],
						dbhtml = '';

					$.each(db, function(i){
						dbhtml += '<option kw="2级">'+ this +'</option>';
					});
					$('#keyword2').html(dbhtml).find('option').off().on({
						click: function(){
							var db1 = ['31','32','33'],
								dbhtml1 = '';

							$.each(db, function(i){
								dbhtml1 += '<option kw="3级">'+ this +'</option>';
							});
							$('#keyword3').html(dbhtml1).find('option').off().on({
								dblclick: function(){
									addItem($(this).attr('kw'), $(this).html());
								}
							});
						},
						dblclick: function(){
							addItem($(this).attr('kw'), $(this).html());
						}
					});
				},
				dblclick: function(){
					addItem($(this).attr('kw'), $(this).html());
				}
			});
			function addItem(kw, text){
				var html = '<div class="select-items">'+ kw +':<span>'+ text +'</span><i>x</i></div>';

				$('#keywordPanel').append(html).find('.select-items').off().on({
					mouseenter: function(){
						$(this).addClass('select-items-hover');
					},
					mouseleave: function(){
						$(this).removeClass('select-items-hover');
					},
					click: function(){
						$(this).detach();
					}
				});
			};
			$('#byCheck').click(function(){
				$('#checkText').attr('disabled', 'disabled');
			});
			$('#notByCheck').click(function(){
				$('#checkText').removeAttr('disabled');
			});
			$('#byQuality').click(function(){
				$('#qualityText').attr('disabled', 'disabled');
			});
			$('#notByQuality').click(function(){
				$('#qualityText').removeAttr('disabled');
			});
			$('#showLog').click(function(){
				$('#logtable').toggle();
			});
		});
	</script>
</body>
</html>