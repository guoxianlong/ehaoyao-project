
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
<script type="text/javascript" src="<%=request.getContextPath()%>/media/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/bvalidator/js/jquery.bvalidator.js"></script>
<link href="<%=request.getContextPath()%>/bvalidator/css/bvalidator.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/bvalidator/css/bvalidator.theme.red.css" rel="stylesheet" type="text/css" />
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
    function dogongs(){
     var imgPath=document.getElementById("bpath").value;//第一张图片
     var bpath1=document.getElementById("bpath1").value;//第二张图片
     var bpath2=document.getElementById("bpath2").value;//第三张图片
     var bpath3=document.getElementById("bpath3").value;//第四张图片
     var bpath4=document.getElementById("bpath4").value;//第五张图片
     var bpath5=document.getElementById("bpath5").value;//第六张图片
     alert("imgPath"+imgPath);
     alert("bpath1"+bpath1);
     alert("bpath2"+bpath2);
 	 alert("bpath3"+bpath3);
 	 alert("bpath4"+bpath4);
 	 alert("bpath5"+bpath5);
     if(imgPath!=""||bpath1!=""||bpath2!=""||bpath3!=""||bpath4!=""||bpath5!=""){
    	 document.form.action="goodsp.do?method=upload&imgPath="+imgPath+"&bpath1="+bpath1+"&bpath2="+bpath2+"&bpath3="+bpath3+"&bpath4="+bpath4+"&bpath5="+bpath5;
       	 document.form.submit();
     }else{ 
    	 alert("请选择一张图片");
     }
    }
    //上传第一张
function openBrowse(){ 
    	
    	document.getElementById("imgPath").click(); 
    	document.getElementById("filepath").src=document.getElementById("imgPath").value;
    	document.getElementById("bpath").value=document.getElementById("imgPath").value;
    	} 
//上传第二张
function openBrowsea(){ 
    	
    	document.getElementById("imgPatha").click(); 
    	document.getElementById("filepatha").src=document.getElementById("imgPatha").value;
    	document.getElementById("bpath1").value=document.getElementById("imgPatha").value;
    	} 
//上传第三张
function openBrowseb(){ 
	
	document.getElementById("imgPathb").click(); 
	document.getElementById("filepathb").src=document.getElementById("imgPathb").value;
	document.getElementById("bpath2").value=document.getElementById("imgPathb").value;
	} 
//上传第四张
function openBrowsec(){ 
	
	document.getElementById("imgPathc").click(); 
	document.getElementById("filepathc").src=document.getElementById("imgPathc").value;
	document.getElementById("bpath3").value=document.getElementById("imgPathc").value;
	} 
//上传第五张
function openBrowsed(){ 
	
	document.getElementById("imgPathd").click(); 
	document.getElementById("filepathd").src=document.getElementById("imgPathd").value;
	document.getElementById("bpath4").value=document.getElementById("imgPathd").value;
	} 
//上传第六张
function openBrowsee(){ 
	
	document.getElementById("imgPathe").click(); 
	document.getElementById("filepathe").src=document.getElementById("imgPathe").value;
	document.getElementById("bpath5").value=document.getElementById("imgPathe").value;
	} 
	
//表单校验参数
var options = {
		lang : 'zh_CN',
		showCloseIcon : false,
		position : {
			x : 'right',
			y : 'top'
		}, 
		showErrMsgSpeed : 'normal',//'fast', 'normal', 'slow' 
		validateOn : 'blur',//'change', 'blur', 'keyup'
		classNamePrefix : 'bvalidator_red_'//bvalidator_red_,bvalidator_orange_,bvalidator_gray2_,bvalidator_postit_

}; 
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
	 
	 
	 
	  
	  $(".pimg").click(function(){
		
			var _this = $(this);
			imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);
		});
	  jQuery('#interiorCode').bValidator(options,'zidingyInst');
	  jQuery('#form').bValidator(options,'formInstance'); 
	
});

//药监页面第一次加载第一级时出发的事件
function wyiji(yij,erjindex){
	//alert(yij);
	  var item="<option value=0 >请选择</option>";
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
	  var item="<option value=0>请选择</option>";
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
	  var item="<option value=0 >请选择</option>";
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
	  var item="<option value=0>请选择</option>";
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
	  var item="<option value=0 >请选择</option>";
	  var item3="<option value=0 >请选择</option>";
	  document.getElementById("erj").length=0;
	  document.getElementById("sanj").length=0;
	  $('#sanj').append(item3);  
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
	  var item="<option value=0>请选择</option>";
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
	  var item="<option value=0 >请选择</option>";
	  var item3="<option value=0 >请选择</option>";
	  document.getElementById("qerj").length=0;
	  document.getElementById("qsanj").length=0;
	  $('#qsanj').append(item3);
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
	  var item="<option value=0>请选择</option>";
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
	var ids="";
	$.each($('#keywordPanel').find('.select-items'), function(i){
		var id =$(this).attr("value");
		//alert(id);
		ids+=","+id+",";
	});
	var flone=document.getElementById("onej").value;//id值
	var fltwo=document.getElementById("twoj").value;//id值
	var flthree=document.getElementById("threej").value;//id值
	var flfour=document.getElementById("fourj").value;//id值
	 
	if(flone!=""){
		if(fltwo==""&&flthree==""&&flfour==""){
			//alert(ids);
			if(ids.indexOf(","+flone+",")< 0 )
			{
				   var onename=jQuery("#onej  option:selected").text();
					addItem(flone, onename);
			}else{
				 alert('标签已经存在,不能重复添加!');
			}
			
			
		}
		if(fltwo!=""&&flthree==""&&flfour==""){
			if(ids.indexOf(","+fltwo+",")< 0 )
			{
				//alert("two"+fltwo);
				var twoname=jQuery("#twoj  option:selected").text();
				//$('#flid').append(twoname);
				addItem(fltwo, twoname);
			}else{
				 alert('标签已经存在,不能重复添加!');
			}
			
			
		}
		if(fltwo!=""&&flthree!=""&&flfour==""){
			if(ids.indexOf(","+flthree+",")< 0 )
			{
				//alert("three"+flthree);
				var threename=jQuery("#threej  option:selected").text();
				//$('#flid').append(threename);
				addItem(flthree, threename);
			}else{
				 alert('标签已经存在,不能重复添加!');
			}
			
		}
		if(fltwo!=""&&flthree!=""&&flfour!=""){
			if(ids.indexOf(","+flfour+",")< 0 )
			{
				//alert("four"+flfour);
				var fourname=jQuery("#fourj  option:selected").text();
				addItem(flfour, fourname);
				//$('#flid').append(fourname);
			}else{
				 alert('标签已经存在,不能重复添加!');
			}
			
		}
	}
}
function add(){

	var ids="";
	$.each($('#keywordPanel').find('.select-items'), function(i){
		var id =$(this).attr("value");
		//alert(id);
		ids+=id+",";
	});
	if(confirm('你确定要提交吗？')){
		
		var	interiorCode=document.getElementById("interiorCode").value;
		var result=jQuery('#form').data("bValidators").formInstance.validate(); 
		if(result){
		  $.ajax({
				type : "POST",  
				dataType: "json",//返回json格式的数据
				url:"<%=request.getContextPath()%>/goodsp.do?method=getinteriorCode&interiorCode="+interiorCode,
				success: function(data){
		             
					if(data==false){
						 document.form.action = "goodsp.do?method=zuploadSave&ids="+ids;
						 document.form.submit();
					}else{
						//alert("编码要唯一");
						jQuery('#interiorCode').data('bValidators').zidingyInst.showMsg(jQuery('#interiorCode'), '内部编码要唯一');
						if(confirm('内部编码要唯一,要跳到此编码的页面吗？')){
							 document.form.action = "goodsp.do?method=toload&interiorCode="+interiorCode;
							 document.form.submit();
							
						}else{
					         return false;
					    }
						$('body,html').animate({scrollTop:0},500);
					}
				}
			});
		  
		}
	
	 }else{
         return false;
     }
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
	
	 document.form.action = "goodsp.do?method=getzhijiansList";
	 document.form.submit();
}
function yulang(){
 var	picTextDetailsHtml=document.getElementById("picTextDetailsHtml").value;
	document.getElementById("tvjs").innerHTML=picTextDetailsHtml;//id值
	
}
function yulangj(){
	 var	goodsDetailsHtml=document.getElementById("goodsDetailsHtml").value;
		document.getElementById("jianj").innerHTML=goodsDetailsHtml;//id值
		
	}
    </script>
</head>
<body class="table table-striped table-bordered table-condensed">
<form   name="form" id="form" method="post" enctype="multipart/form-data">
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
<div class="right_h"><h3> 商品编辑模板</h3></div>
  <div class="right_inline"><span style="float:right"><a href="#">查看对应套餐

</a></span>
    <div class="right_title">商品类型</div>
	<div class="right_input" style="width:100px;"><select name="goodsType" id="goodsType" class="form-control input-sm">
	<option value="" >不限</option>
    <c:forEach items="${listgoods }" var="item">
    <option value="${item.id }" ${goods.goodsType eq item.id? 'selected=selected':'' }>${item.attname }</option>
    </c:forEach>
</select></div>
</div>
 <div class="right_h right_bg" > 通用商品信息</div>
 <div class="right_box2"><div class="right_box_l">
  <div class="right_inline">
  <div class="right_title">内部ID</div>
  <div class="right_input" style="width:300px;"><input name="interiorId" id="interiorId" data-bvalidator="maxlength[200]" value="" type="text" class="form-control input-sm"  placeholder=" "></div>
  </div>
  <div class="right_inline">
  <div class="right_title">内部编码</div>
  <div class="right_input" style="width:300px;">
  <input name="interiorCode" id="interiorCode" data-bvalidator="maxlength[200],required"  value="" type="text" class="form-control input-sm" placeholder=" "/>

  </div>
  </div>
  <div class="right_inline">
  <div class="right_title">条形码</div>
  <div class="right_input" style="width:300px;"><input name="barCode" id="barCode" data-bvalidator="maxlength[200]" value="" type="text" class="form-control input-sm"  placeholder=" "></div>
  </div>
  <div class="right_inline">
  <div class="right_title">通用名</div>
  <div class="right_input" style="width:300px;"><input name="commonName" id="commonName" data-bvalidator="maxlength[200]" value="" type="text" class="form-control input-sm"  placeholder="普通商品的商品名"></div>
  </div>
  <div class="right_inline">
  <div class="right_title">商品名</div>
  <div class="right_input" style="width:300px;"><input name="goodsName" id="goodsName" data-bvalidator="maxlength[200]" value="" type="text" class="form-control input-sm"  placeholder=" "></div>
  </div>
  <div class="right_inline">
  <div class="right_title">英文或拼音</div>
  <div class="right_input" style="width:300px;"><input name="englishOrPinyin" id="englishOrPinyin" data-bvalidator="maxlength[200]" value="" type="text" class="form-control input-sm"  placeholder=" "></div>
  </div>
  <div class="right_inline">
  <div class="right_title">拼音编码</div>
  <div class="right_input" style="width:300px;"><input name="pinyinCode" id="pinyinCode" data-bvalidator="maxlength[200]" value="" type="text" class="form-control input-sm"  placeholder=" "></div>
  </div>
  </div>
  
  
  <div class="right_box_pic " style="margin-left:50px;" >
 <div id="outerdiv" style="position:fixed;top:-900px;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;">
 <div id="innerdiv" style="position:absolute;"><img id="bigimg" style="border:5px solid #fff;" src="" /></div></div>
 <div class="right_inline">
 <div class="right_pic_lg"> <img class="pimg" src="${bpath}"   id="filepath" name="filepath"  value="${bpath}"   width="90" height="70"/></div>
 <div class="right_pic_sm">
 <div class="right_pic"> <img class="pimg" src="${bpath1}"  id="filepatha" name="filepatha"   value="${bpath1}"  width="90" height="70"/></div>
 <div class="right_pic"> <img class="pimg" src="${bpath2}"  id="filepathb"  name="filepathb"   value="${bpath2}"  width="90" height="70"/></div>
 <div class="right_pic"> <img class="pimg" src="${bpath3}"  id="filepathc"  name="filepathc"  value="${bpath3}"  width="90" height="70"/></div>
 <div class="right_pic"> <img class="pimg" src="${bpath4}"  id="filepathd"  name="filepathd"  value="${bpath4}"  width="90" height="70"/></div>
 </div>
 </div> 
 <div class="right_inline"><div class="right_pic_long"><img class="pimg" src="${bpath5}"  id="filepathe"  name="filepathe"  value="${bpath5}"  width="90" height="70"/></div>
 <span style="float:right; margin:40px 120px 0 0"><button type="button" class="btn btn-info btn-sm pull-right" >编辑图片</button></span>
 </div>
 
 
 
 
 
  
 
  
  </div>
  
  
  </div>
  
  <div class="right_inline"><div class="right_title">前端分类一级</div>
  
  <div class="right_input" style="width:150px;">
<select id="qyij" name="qyij" onChange="qyiji()" class="form-control input-sm" >
<option value="0">请选择</option>
   <c:forEach items="${listq}" var="item">
   <option value="${item.id }" ${fxingyi eq item.id? 'selected=selected':'' } >${item.className }</option>
   </c:forEach>
</select>
  </div>
  <div class="right_title_min">二级</div>
  <div class="right_input" style="width:150px;">
<select id="qerj" name="qerj"  onchange="qerji()" class="form-control input-sm" >
  <option value="0">请选择</option>
</select>
  </div>
  <div class="right_title_min">三级</div>
  <div class="right_input" style="width:150px;">
<select name="qsanj" id="qsanj" onChange="qsanji()" class="form-control input-sm" >
  <option>请选择</option>
</select>
  </div></div>

  
  <div class="right_inline">
  <div class="right_title">关键词</div>
  
  <div class="right_input" style="width:500px;"><input name="keyWord" id="keyWord" data-bvalidator="maxlength[200]" value="" type="text" class="form-control input-sm"  placeholder=" "></div>
  </div>
  <div class="right_inline">
  <div class="right_title">规格型号</div>
  
  <div class="right_input" style="width:300px;"><input name="specifications" id="specifications" data-bvalidator="maxlength[200]" value="" type="text" class="form-control input-sm"  placeholder="纸铝复合膜包装；6包/盒、8包/盒、10包/盒

、12包/盒、14包/盒、20包/盒。 "></div>
  <div class="right_title textright">品牌</div>

 <div class="right_input" style="width:300px;"><input name="brand" id="brand" data-bvalidator="maxlength[200]" value="" type="text" 

class="form-control input-sm"  placeholder=" 江中"></div>
  </div>
  
  <div class="right_inline">
  <div class="right_title">生产企业</div>
  
  <div class="right_input" style="width:300px;"><input name="manufacturer" id="manufacturer" data-bvalidator="maxlength[200]" value="" type="text" 

class="form-control input-sm"  placeholder="纸铝复合膜包装；6包/盒、8包/盒、10包/盒

、12包/盒、14包/盒、20包/盒。 "></div>
  <div class="right_title textright">生产地址</div>

 <div class="right_input" style="width:300px;"><input name="productionAddress" id="productionAddress" data-bvalidator="maxlength[200]" value="" type="text" 

class="form-control input-sm"  placeholder=" 江中"></div>
  </div>
  <div class="right_box2">  <div class="right_box_l">
   
        <div class="right_inline">简介</div>
		<div class="right_inline"><textarea name="brief"  id="brief" data-bvalidator="maxlength[200]" value="" rows="4" 

class="form-control input-sm" style="width:360px;"></textarea></div>
  </div>
  
  <div class="right_box_l">
   
        <div class="right_inline">详细介绍纯文本</div>
		<div class="right_inline"><textarea name="goodsDetails" id="goodsDetails" data-bvalidator="maxlength[200]" value="" class="form-control input-sm" 

rows="4" style="width:360px;"></textarea></div>
      
    
  </div>
  <div class="right_box_l">
   
        <div class="right_inline"><span class="pull-right" style="padding-right:35px" data-toggle="modal" data-target="#xxjs" onclick="yulangj()">预览</span>详细介绍（html）</div>
		<div id="xxjs" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
	    <div class="modal-dialog">
		    <div class="modal-content">
		        <div class="modal-header" style="background:#f7f8fa; border-bottom:#4fc1e9 1px solid; border-radius:4px 4px 0 0;">
		        	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		        	<h4 class="modal-title"><i class="glyphicon glyphicon-list-alt" style="margin:0 10px 0 0; color:#4fc1e9 "></i>详细介绍预览</h4>
		        </div>
	          <div class="modal-body" id="jianj">
	          </div>
	    </div>
		
	</div>
	    </div>
		<div class="right_inline"><textarea name="goodsDetailsHtml" id="goodsDetailsHtml" data-bvalidator="maxlength[200]" value="" class="form-control input-sm" 

rows="4" style="width:360px;"></textarea></div>
      
    
  </div>
  <div class="right_box_l">
   
        <div class="right_inline"><span class="pull-right" style="padding-right:35px" data-toggle="modal" data-target="#yulan" onclick="yulang()">预览</span>图文介绍（html）</div>
		<div id="yulan" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
	    <div class="modal-dialog">
		    <div class="modal-content">
		        <div class="modal-header" style="background:#f7f8fa; border-bottom:#4fc1e9 1px solid; border-radius:4px 4px 0 0;">
		        	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		        	<h4 class="modal-title"><i class="glyphicon glyphicon-list-alt" style="margin:0 10px 0 0; color:#4fc1e9 "></i>图文介绍预览</h4>
		        </div>
	          <div class="modal-body" id="tvjs"></div>
	    </div>
		
	</div>
	    </div>
		<div class="right_inline"><textarea name="picTextDetailsHtml" id="picTextDetailsHtml" data-bvalidator="maxlength[200]" value="" class="form-control input-sm" 

rows="4" style="width:360px;"></textarea></div>
      
    
  </div>
  </div>

  

<div class="right_h right_bg"><span id="productBtn" style="margin-right:20px; float:right; font-size:12px;">收起</span>药品专属信息</div>
<div id="productPanel" class="right_box2" >
   <div class="right_inline">
  <div class="right_title">质检分类一级</div>
  
  <div class="right_input" style="width:180px;">
    <select name="yij" id="yij" onChange="yiji()"  class="form-control input-sm" >
     <option value="0">请选择</option>
      <c:forEach items="${lista}" var="item">
      <option value="${item.id }" ${yxingyi eq item.id? 'selected=selected':'' } >${item.className }</option>
      </c:forEach>
    </select>
  </div>
  <div class="right_title_min">二级</div>
  <div class="right_input" style="width:180px;">
    <select id="erj"  onchange="erji()" name="erj" class="form-control input-sm" >
     <option value="0">请选择</option>
    </select>
  </div>
  <div class="right_title_min">三级</div>
  <div class="right_input" style="width:180px;">
    <select id="sanj" onChange="sanji()" name="sanj" class="form-control input-sm" >
      <option>请选择</option>
    </select>
  </div>
   </div>
  <div class="right_inline">
  <div class="right_title">批准文号</div>
  
  <div class="right_input" style="width:150px;"><span class="right_input" 

style="width:150px;">
    <input name="approvalDocument" id="approvalDocument" data-bvalidator="maxlength[200]" value="" type="text" class="form-control"  placeholder=" ">
  </span></div>
  <div class="right_title_min">执行标准</div>
  <div class="right_input" style="width:150px;"><input id="standard" value="" name="standard" data-bvalidator="maxlength[200]" type="text" 

class="form-control input-sm"  placeholder=" "></div>
  <div class="right_title_min">卫生许可证号</div>
  <div class="right_input" style="width:150px;"><input id="hygieneLicense" value="" name="hygieneLicense" data-bvalidator="maxlength[200]" type="text" 

class="form-control input-sm"  placeholder=" "></div>
  </div>
  
  <div class="right_inline">
  <div class="right_title">处方类型</div>
  
  <div class="right_input"  style="width:120px;">
    <select name="prescriptionType" id="prescriptionType"  class="form-control input-sm" >
     <option value="">请选择</option>
    <c:forEach items="${listchu }" var="item">
    <option value="${item.id }" ${goods.prescriptionType eq item.id? 'selected=selected':'' }>${item.attname}</option>
    </c:forEach>   
    </select>
  </div>
   <div class="right_title_min"><label class="checkbox-inline">
  <input type="checkbox"  name="gmp"  id="gmp" value="1">
   GMP已认证
</label>
   </div>
  <div class="right_title_min">是否医保</div>
  <div class="right_input"  style="width:120px;">
    <select name="healthCare" id="healthCare" class="form-control input-sm" >
    <option value="0"${goods.healthCare=='0'? 'selected=selected':'' }>非医保</option>
    <option value="1"${goods.healthCare=='1'? 'selected=selected':'' }>医保</option>
    </select>
  </div>
  </div>
  <div class="right_inline">
  <div class="right_title">有效期 </div>
  
  <div class="right_input" style="width:190px;">
    <input name="valid" id="valid" data-bvalidator="maxlength[200]" value="" type="text" class="form-control input-sm"  placeholder=" 

">
  </div>
  <div class="right_title_min">存储条件</div>
  <div class="right_input" style="width:190px;">
    <input name="storage" id="storage" data-bvalidator="maxlength[200]" value="" type="text" class="form-control input-sm"  placeholder=" 

">
 </div>
  </div>
  <div class="right_inline">
   <div class="right_box_3">
   <div class="right_title2"><nobr title="主要成分及含量[全]">主要成分及含量[全]</nobr></div>
   <div class="right_inline"><textarea id="ingredients" name="ingredients" data-bvalidator="maxlength[200]"  class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   
   
   <div class="right_box_3" >
   <div class="right_title2"><nobr title="性状[药品,食品保健品,化妆品,日用品]">性状[药品,食品保健品,化妆品,日用品]</nobr></div>
   <div class="right_inline"><textarea id="tcac" name="tcac" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4" 

> </textarea></div>
   </div>
   
   
   <div class="right_box_3">
   <div class="right_title2"><nobr title="适应症[药品] 适用范围[器械,化妆品,日用品]">适应症[药品] 适用范围[器械,化妆品,日用品]</nobr></div>
   <div class="right_inline"><textarea id="indication" name="indication" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4" 
></textarea></div>
   </div>
   
   <div class="right_box_3">
   <div class="right_title2"><nobr title="用法用量[药品,食品保健品,化妆品,日用品]">用法用量[药品,食品保健品,化妆品,日用品]</nobr></div>
   <div class="right_inline"><textarea id="usageAndDosage" name="usageAndDosage" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="不良反应[药品,器械,日用品]">不良反应[药品,器械,日用品]</nobr></div>
   <div class="right_inline"><textarea id="adverseDrugReactions" name="adverseDrugReactions" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="禁忌[药品,食品保健品,化妆品,日用品]">禁忌[药品,食品保健品,化妆品,日用品]</nobr></div>
   <div class="right_inline"><textarea id="taboo" name="taboo" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="功能主治[药品]">功能主治[药品]</nobr></div>
   <div class="right_inline"><textarea id="treatment" name="treatment" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="药理作用[药品]">药理作用[药品]</nobr></div>
   <div class="right_inline"><textarea id="pharmacological" name="pharmacological" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="药物相互作用[药品]">药物相互作用[药品]</nobr></div>
   <div class="right_inline"><textarea id="interactions" name="interactions" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="药代动力学[药品]">药代动力学[药品]</nobr></div>
   <div class="right_inline"><textarea id="pharmacokinetics" name="pharmacokinetics" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="特殊人群用药[药品]">特殊人群用药[药品]</nobr></div>
   <div class="right_inline"><textarea id="tsrqyy" name="tsrqyy" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="药物过量[药品]">药物过量[药品]</nobr></div>
   <div class="right_inline"><textarea id="overdose" name="overdose" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="性味归经[中药]">性味归经[中药]</nobr></div>
   <div class="right_inline"><textarea id="xwgj" name="xwgj" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="药理毒理[药品]">药理毒理[药品]</nobr></div>
   <div class="right_inline"><textarea id="yldl" name="yldl" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="说明书核准日期[药品,食品保健品,化妆品]">说明书核准日期[药品,食品保健品,化妆品]</nobr></div>
   <div class="right_inline"><textarea id="smshzrq" name="smshzrq" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="作用类别[药品,日用品,化妆品]">作用类别[药品,日用品,化妆品]</nobr></div>
   <div class="right_inline"><textarea id="categories" name="categories" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="作用用途[化妆品,日用品] 保健作用[保健品]">作用用途[化妆品,日用品] 保健作用[保健品]</nobr></div>
   <div class="right_inline"><textarea id="purpose"  name="purpose" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="生产许可证号[化妆品]">生产许可证号[化妆品]</nobr></div>
   <div class="right_inline"><textarea id="license" name="license" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="适宜人群[器械,食品保健品,化妆品,日用品]">适宜人群[器械,食品保健品,化妆品,日用品]</nobr></div>
   <div class="right_inline"><textarea id="syrq" name="syrq" data-bvalidator="maxlength[200]"  class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="不适宜人群[保健品,化妆品]">不适宜人群[保健品,化妆品]</nobr></div>
   <div class="right_inline"><textarea id="bsyrq" name="bsyrq" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="适合年龄[日用品]">适合年龄[日用品]</nobr></div>
   <div class="right_inline"><textarea id="shnl" name="shnl" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="注意事项[全]">注意事项[全]</nobr></div>
   <div class="right_inline"><textarea id="notes" name="notes" data-bvalidator="maxlength[200]"  class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="企业介绍[全]">企业介绍[全]</nobr></div>
   <div class="right_inline"><textarea id="introduction"  name="introduction" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
     <div class="right_box_3">
   <div class="right_title2"><nobr title="使用方法[器械]">使用方法[器械]</nobr></div>
   <div class="right_inline"><textarea id="methodOfUse" name="methodOfUse" data-bvalidator="maxlength[200]"  class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="适用范围[器械,化妆品,日用品]">适用范围[器械,化妆品,日用品]</nobr></div>
   <div class="right_inline"><textarea id="scopeOfApplication" name="scopeOfApplication" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="性能结构及组成[器械]">性能结构及组成[器械]</nobr></div>
   <div class="right_inline"><textarea id="xnjgjzc" name="xnjgjzc" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="临床试验[药品,器械]">临床试验[药品,器械]</nobr></div>
   <div class="right_inline"><textarea id="clinicalTrials" name="clinicalTrials" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="注册证[器械]">注册证[器械]</nobr></div>
   <div class="right_inline"><textarea id="registration" name="registration" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4" 

></textarea></div>
   </div>
   <div class="right_box_3">
   <div class="right_title2"><nobr title="完整说明书文本(长文本]">完整说明书文本(长文本]</nobr></div>
   <div class="right_inline"><textarea id="allInstructions" name="allInstructions" data-bvalidator="maxlength[200]" class="form-control input-sm" rows="4"  

></textarea></div>
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
  <select id="onej" onChange="oneji()" multiple class="form-control">
  <c:forEach items="${listlabel}" var="item">
  <option  value="${item.id }">${item.labelName }</option>
 </c:forEach>
</select>
  </div>
    <div class="right_box_4">
  <select id="twoj" name="twoj" onclick="twoji()" multiple class="form-control">
 
</select>
  </div>
    <div class="right_box_4">
  <select id="threej" name="threej" onChange="threeji()" multiple class="form-control">

</select>
  </div>
    <div class="right_box_4">
  <select id="fourj" name="fourj" multiple class="form-control">
 
</select>
  </div>
  
  </div>
  <div class="right_inline"><button type="button" class="btn btn-info pull-right  

btn-sm" style="margin:5px 50px 0 0; width:100px;" onClick="addfl()">添加</button></div>
  </div>
 
 
 </div>
 
  
  <!----------------------->
  <div class="right_h2 right_bg" ><strong>包装属性 </strong></div>
  
  <div class="right_box2"> <div class="right_inline">
  <div class="right_title">小包单位</div>
  
  <div class="right_input" style="width:300px;"><input id="smallUnits" name="smallUnits"data-bvalidator="maxlength[200]"  value=""  type="text" 

class="form-control input-sm"  ></div>
  <div class="right_title textright">最小单位</div>

 <div class="right_input" style="width:300px;"><input id="smallesUnit" name="smallesUnit" data-bvalidator="maxlength[200]" value=""   type="text" 

class="form-control input-sm"  ></div>
  </div>
  <div class="right_inline">
  <div class="right_title">包装重量</div>
  
  <div class="right_input" style="width:300px;"><input id="tare" name="tare" data-bvalidator="maxlength[200]" value=""  type="text" 

class="form-control input-sm"  ></div>
  <div class="right_title textright">小包数量</div>

 <div class="right_input" style="width:300px;"><input id="xbsl" name="xbsl" data-bvalidator="maxlength[200]"  value=""  type="text" 

class="form-control input-sm"  ></div>
  </div>
  <div class="right_inline">
  <div class="right_title">包装尺寸</div>
  
  <div class="right_input" style="width:300px;"><input id="packingSize" name="packingSize" data-bvalidator="maxlength[200]" value=""  type="text" 

class="form-control input-sm" ></div>
  <div class="right_title textright">中包数量</div>

  <div class="right_input" style="width:300px;"><input id="zbsl" name="zbsl" data-bvalidator="maxlength[200]" value=""  type="text" 

class="form-control input-sm"  ></div>
  </div>
  <div class="right_inline">
  <div class="right_title">净重</div>
  
  <div class="right_input" style="width:300px;"><input id="netWeight" name="netWeight" data-bvalidator="maxlength[200]" value=""  type="text" 

class="form-control input-sm"></div>
  <div class="right_title textright">大包数量</div>

 <div class="right_input" style="width:300px;"><input id="dbsl" name="dbsl" data-bvalidator="maxlength[200]" value="" type="text" 

class="form-control input-sm"  ></div>
  </div></div>
  
  <div class="right_box2">
    <div class="right_box_l">
	<div class="right_inline"><strong>基本定价属性</strong></div>
	<div class="right_inline">
  <div class="right_title">内成本粗估</div>
  <div class="right_input" style="width:300px;"><input id="costPrice" name="costPrice" data-bvalidator="decimal" value=""  type="text" 

class="form-control input-sm" placeholder=" "></div>
  </div>
  <div class="right_inline">
  <div class="right_title">建议零售价</div>
  <div class="right_input" style="width:300px;"><input id="retailPrice" name="retailPrice" data-bvalidator="decimal" value=""  type="text" 

class="form-control input-sm" placeholder=" "></div>
  </div>
  <div class="right_inline">
  <div class="right_title">基本定价</div>
  <div class="right_input" style="width:300px;"><input id="basicPricing" name="basicPricing" data-bvalidator="decimal" value=""  type="text" 

class="form-control input-sm" ></div>
  </div>

	
	</div>
	<div class="right_box_l">
	<div class="right_inline"><strong>销售状态</strong></div>
	
	<div class="right_inline">
  <div class="right_title">
    是否可售
  </div>
  <div class="right_input" style="width:300px;"><div class="right_input" 

style="width:100px;"><select id="" class="form-control input-sm">
  <option>可销售</option>
  <option>药品</option>
  <option>器械</option>
  <option>食品保健</option>
  <option>化妆品</option>
</select></div></div>
  </div>
  
  <div class="right_inline">
  <div class="right_title">库存数量</div>
  <div class="right_input" style="width:300px;"><input  type="text" 

class="form-control input-sm" placeholder=" "></div>
  </div>
  <div class="right_inline">
  <div class="right_title" style="line-height:60px;">生成套餐</div>
<div class="right_title"  style="width:100px"><label class="checkbox-inline" >
  <input type="checkbox" id="inlineCheckbox1" value="option1" style="margin-

top:9px;" >
   单品
</label>
   </div>
   <div class="right_title" style="width:140px"><label class="checkbox-inline" >
  <input type="checkbox" id="inlineCheckbox1" value="option1" style="margin-

top:9px;" >
   多品(作为主商品)

</label>
   </div>
   <div class="right_title"  style="width:100px"><label class="checkbox-inline" >
  <input type="checkbox" id="inlineCheckbox1" value="option1" style="margin-

top:9px;" >
   单品多件

</label>
   </div>
   <div class="right_title" style="width:140px"><label class="checkbox-inline" >
  <input type="checkbox" id="inlineCheckbox1" value="option1" style="margin-

top:9px;" >
   多品(不作为主商品)

</label>
   </div>
  </div>
	</div>
  </div>
  
  <div class="right_inline">
  <!--  
    <div class="right_box2">
	<div class="right_inline"><button type="button" class="btn btn-info   

btn-sm" style="margin:5px 15px 0 0;" id="showLog">日志列表</button></div>

<div id="logtable" class="portlet" style="display:none;">
					<div class="portlet-body">
	<div class="right_inline" >
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" 

class="table table-striped table-bordered">
        <tr>
          <td width="20%" align="center">时间</td>
          <td width="20%" align="center">操作者</td>
          <td width="60%" align="center">行为</td>
        </tr>
        <c:forEach items="${listlog }" var="item">
        <tr>
          <td align="center">${item.operDate }</td>
          <td align="center">${item.operName }</td>
          <td align="center">
          <c:if test="${item.operType==1 }">
                                     创建商品
          </c:if>
           <c:if test="${item.operType==2 }">
                                     审核
           </c:if>
          <c:if test="${item.operType==3 }">
                                   修改商品信息
           </c:if>
          </td>
        </tr>
        </c:forEach>
      </table>
	</div>  
	</div></div>
	</div>
 
  -->
  </div>
  <div class="right_box2" style="padding-left:30px;">
        <div class="right_inline pull-right"  style="width:180px">
<!-- 
  <input type="radio" id="optionsRadios2" value="option1" style="margin-top:9px;" 

>
 
   通过审核

</label>
   </div>
   <div class="right_title"  style="width:110px"><label class="radio-inline" >
  <input type="radio" id="optionsRadios2" value="option1" style="margin-top:9px;" 
 
>
   未通过审核

</label>
   </div>
   
   <div class="right_input" style="width:130px;"><input name="text" type="text" 

class="form-control input-sm" placeholder="未通过原因 "></div>
  </div>
  
 
  
  

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
   <div class="right_input" style="width:130px;"><input name="text" type="text" 

class="form-control input-sm" placeholder="未通过原因 "></div>
  </div>
  <div class="right_inline "><!--  <button type="button" class="btn btn-info   btn-sm 

pull-right" style="margin:5px 15px 0 0;">保存</button>-->


<button type="button" onClick="add()" class="btn btn-info   btn-sm" style="margin:5px 15px 0 0;">保存</button>
  <button type="button" onClick="back()" class="btn btn-info pull btn-sm" style="margin:5px 15px 0 0;">返回</button>
</div>
</div>
</div >
	
	

	
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