
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<%=request.getContextPath()%>/media/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">

<link rel="stylesheet" href="<%=request.getContextPath()%>/media/css/jquery.tooltip.css" type="text/css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.tooltip.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/media/js/bootstrap.min.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/bvalidator/js/jquery.bvalidator.js"></script>
<link href="<%=request.getContextPath()%>/bvalidator/css/bvalidator.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/bvalidator/css/bvalidator.theme.red.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
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
</style>
<script type="text/javascript">
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
function goodList(id) {
	jQuery.ligerDialog.open({ 
		isDrag:true,//是否拖动
        showMax:true,//是否显示最大化按钮
		height: 500, 
		width:1000,
		top:1,
		slide:true,
		title:'商品详情', 
		url: '<%=request.getContextPath()%>/goodsp.do?method=detailed&id='+id 
		
	}); 
}
$(document).ready(function(){
	 var qyijindex=window.document.getElementById("qyij").value;//前端选中第一级
	 var qerjindex=window.document.getElementById("qerj").value;//前端选中第二级
	 var qsanjindex=window.document.getElementById("qsanj").value;//前端选中第三级
	 qyijindex=document.getElementById("fxingyi").value;
	 qerjindex=document.getElementById("fxinger").value;
	 qsanjindex=document.getElementById("fxingsan").value;
	 wqyiji(qyijindex,qerjindex);//前端
	 wqerji(qerjindex,qsanjindex);//前端
	 wqsanji(qsanjindex);//前端
	 <c:forEach items="${listlableid}" var="a"> 
	  addItem('${a.labelId}', '${a.labelName}');
	 </c:forEach> 
	 var items="";
	 <c:forEach items="${groupGoodslist}" var="item"> 
	   items+="<tr id='${item.goodsId}'><td><input type='hidden' id='inputid' name='inputid' value='${item.goodsId}' /> <a href='javascript:goodList(${item.goodsId})'>${item.goodsName}</a></td><td><input type='text' id='countid' name='${item.goodsId}' size='12' data-bvalidator='digit,min[1],required' value='${item.goodsCount}'/></td><td><c:if test='${item.sfGift==1 }'>非赠品<input type='checkbox' checked='checked'  id='isid' name='isid' value='${item.goodsId}' /></c:if><c:if test='${item.sfGift==0 }'>非赠品<input type='checkbox' id='isid' name='isid' value='${item.goodsId}' /></c:if></td><td>${item.repertoryCount}</td><td><input type='button' class='btn btn-success   btn-xs' value='移除' onclick='getDel(${item.goodsId})'/></td></tr>";
	 </c:forEach> 
	// alert(items);
	 $('#divid').append(items);  
	 jQuery('#form').bValidator(options,'formInstance'); 
	  $('#showLog').click(function(){
			$('#logtable').toggle();
		});
});
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
//添加标签
function addfl(){
	var ids="";
	$.each($('#keywordPanel').find('.select-items'), function(i){
		var id =$(this).attr("value");
		ids+=","+id+",";
	});
	var flone=document.getElementById("onej").value;//id值
	var fltwo=document.getElementById("twoj").value;//id值
	var flthree=document.getElementById("threej").value;//id值
	var flfour=document.getElementById("fourj").value;//id值
	if(flone!=""){
		if(fltwo==""&&flthree==""&&flfour==""){
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
				var twoname=jQuery("#twoj  option:selected").text();
				addItem(fltwo, twoname);
			}else{
				 alert('标签已经存在,不能重复添加!');
			}
		}
		if(fltwo!=""&&flthree!=""&&flfour==""){
			if(ids.indexOf(","+flthree+",")< 0 )
			{
				var threename=jQuery("#threej  option:selected").text();
				addItem(flthree, threename);
			}else{
				 alert('标签已经存在,不能重复添加!');
			}
		}
		if(fltwo!=""&&flthree!=""&&flfour!=""){
			if(ids.indexOf(","+flfour+",")< 0 )
			{
				var fourname=jQuery("#fourj  option:selected").text();
				addItem(flfour, fourname);
			}else{
				 alert('标签已经存在,不能重复添加!');
			}
		}
	}
}
function roleList() {
	jQuery.ligerDialog.open({ 
		isDrag:true,//是否拖动
        showMax:true,//是否显示最大化按钮
		height: 500, 
		width:1000,
		top:1,
		slide:true,
		title:'请选择商品', 
		url: '<%=request.getContextPath()%>/goodsp.do?method=getGoodsLists' 
		
	}); 
	
}
function kaobei(){
	var id =document.getElementById("inputid").value;
	 $.ajax({
			type : "POST",  
			dataType: "json",//返回json格式的数据
			url:"<%=request.getContextPath()%>/goodsGroup.do?method=getGoodsDetailed&id="+id,
			success: function(data){
	             $.each(data,function(i,result){
	            	 document.getElementById("mainGoodsName").value=result['mainGoodsName'];//商品名称拷贝
	            	 document.getElementById("pinyinCode").value=result['pinyinCode'];//拼音码拷贝
	            	 document.getElementById("keyWord").value=result['keyWord'];//关键词
	            	 document.getElementById("specifications").value=result['specifications'];//型号与规格
	            	 document.getElementById("filepatha").src=result['bpath'];
	            	 document.getElementById("filepathe").src=result['bpath5'];
	             	 document.getElementById("bpath").value=result['bpath'];
	            	 document.getElementById("bpath5").value=result['bpath5'];
	            	 document.getElementById("maingoodsid").value=result['maingoodsid'];
	            	 //alert(result['map']);
	            	 //document.getElementById("map").value=result['map'];
	            	 document.getElementById("costPrice").value=result['costPrice'];//成本粗估
	            	 document.getElementById("retailPrice").value=result['retailPrice'];//建议零售价
	            	 document.getElementById("basicPricing").value=result['basicPricing'];//基本定价
	            	 document.getElementById("repertoryCount").value=result['repertoryCount'];//库存数量
	            	 document.getElementById("brief").value=result['brief'];//简介 [自动同步主商品]
	            	 document.getElementById("goodsDetails").value=result['goodsDetails'];//详细介绍 [自动同步主商品]
	            	 document.getElementById("goodsDetailsHtml").value=result['goodsDetailsHtml'];//详细介绍html
	            	 document.getElementById("picTextDetailsHtml").value=result['picTextDetailsHtml'];//图文介绍
	            	 document.getElementById("sfHanma").value=result['sfHanma'];//是否含麻
	            	 document.getElementById("sfDatai").value=result['sfDatai'];//是否打胎
	            	 document.getElementById("dSBClassId").value=result['dSBClassId'];//药监id
	                 document.getElementById("salesStatus").value=result['salesStatus'];//是否可销售
	            	 document.getElementById("keywordPanel").innerHTML = "";	
	            	var data1= result['listlableid'];
	            	 $.each(data1,function(j,result){
	            		
	            		 addItem(result['labelId'], result['labelName']);
	            		 
	            	 }); 
	              var qyijindex=window.document.getElementById("qyij").selectedIndex;//前端选中第一级
	           	  var qerjindex=window.document.getElementById("qerj").selectedIndex;//前端选中第二级
	           	  var qsanjindex=window.document.getElementById("qsanj").selectedIndex;//前端选中第三级
	           	  qyijindex=result['fxingyi'];
	      	      qerjindex=result['fxinger'];
	      	      qsanjindex=result['fxingsan'];
	      	     
	      	    $("#qyij option").each(function(){
	                 //alert($(this).val());
	                 if($(this).val() == qyijindex){
	                 $(this).attr("selected",true);
	                 }
	             });       
	      	      wqyiji(qyijindex,qerjindex);//前端
	      	      wqerji(qerjindex,qsanjindex);//前端
	      	      wqsanji(qsanjindex);//前端
	             });  
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
function save(){
	var ids="";
	$.each($('#keywordPanel').find('.select-items'), function(i){
		var id =$(this).attr("value");
		//alert(id);
		ids+=id+",";
	});
	 var goodsids=document.getElementById("userid").value;
	 if(goodsids==""){
		 alert("你必须添加一个商品作为主商品!");
		 return;
	 }
	 var inputid =document.getElementById("inputid").value;
	 var result=jQuery('#form').data("bValidators").formInstance.validate(); 
	 if(result){
	 document.form.action = "goodsGroup.do?method=tcfbuptateSave&goodsids="+goodsids+"&ids="+ids+"&inputid="+inputid;
	 document.form.submit();
	 }
}
function back(){
	 document.form.action = "goodsGroup.do?method=goodsGroupCopyList";
	 document.form.submit();
}
//删除商品
function getDel(k){
	var tb=document.getElementById("divid");
	var row=document.getElementById(k);
	tb.deleteRow(row.rowIndex);
    var userid=document.getElementById("userid").value;
   // alert(userid);
    if(userid.indexOf(","+k+",")>= 0 ){
     var bb=userid.replace(","+k+",","");
    // alert(bb);
     document.getElementById("userid").value=bb;
     userid=bb;
    // alert(userid);
    }
    
	}
	//图片跳转
	  function totupian(id){
		  var id=document.getElementById("id").value;
		  jQuery.ligerDialog.open({ 
			isDrag:true,//是否拖动
	        showMax:true,//是否显示最大化按钮
			height: 500, 
			width:1100,
			top:1,
			slide:true,
			title:'图片设置', 
			url: '<%=request.getContextPath()%>/goodsImage.do?method=tcfbload&id='+id+'&wtype=6' 
			
		}); 
	
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
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>套餐编辑</title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />


<body>
<form   name="form" id="form" method="post" enctype="multipart/form-data">
<input type="hidden" name="userid" id="userid" value="${idm }"/>
<input type="hidden" name="id" id="id" value="${id }"/>
<input type="hidden" name="zjradio" id="zjradio" value="${zjradio }"/>
<input type="hidden" name="qdradio" id="qdradio" value="${qdradio }"/>
<input type="hidden" name="seachsalesPlatform" id="seachsalesPlatform" value="${seachsalesPlatform }"/>
<input type="hidden" id="pageno" name="pageno" value="${pageno}"/>
<input type="hidden" id="pageSize" name="pageSize" value="${pageSize}"/>
<input type="hidden" name="maingoodsid" id="maingoodsid" value=""/>
<input type="hidden" name="sfHanma" id="sfHanma" value="${goodsGroup.sfHanma }"/>
<input type="hidden" name="sfDatai" id="sfDatai" value="${goodsGroup.sfDatai }"/>
<input type="hidden" name="dSBClassId" id="dSBClassId" value="${goodsGroup.dSBClassId }"/>
<input type="hidden" name="fxingyi" id="fxingyi" value="${fxingyi }"/>
<input type="hidden" name="fxinger" id="fxinger" value="${fxinger }"/>
<input type="hidden" name="fxingsan" id="fxingsan" value="${fxingsan }"/>
<input type="hidden" name="bpath"  id="bpath"  value="${bpath }"/>
<input type="hidden" name="bpath1" id="bpath1" value="${bpath1 }"/>
<input type="hidden" name="bpath2" id="bpath2" value="${bpath2 }"/>
<input type="hidden" name="bpath3" id="bpath3" value="${bpath3 }"/>
<input type="hidden" name="bpath4" id="bpath4" value="${bpath4 }"/>
<input type="hidden" name="bpath5" id="bpath5" value="${bpath5 }"/>

<input type="hidden" name="type" id="type" value="${type }"/>
<input type="hidden" name="type1" id="type1" value="${type1 }"/>
<input type="hidden" name="type2" id="type2" value="${type2 }"/>
<input type="hidden" name="type3" id="type3" value="${type3 }"/>
<input type="hidden" name="type4" id="type4" value="${type4 }"/>
<input type="hidden" name="type5" id="type5" value="${type5 }"/>

<input type="hidden" name="imgSource" id="imgSource" value="${imgSource }"/>
<input type="hidden" name="imgSource1" id="imgSource1" value="${imgSource1 }"/>
<input type="hidden" name="imgSource2" id="imgSource2" value="${imgSource2 }"/>
<input type="hidden" name="imgSource3" id="imgSource3" value="${imgSource3 }"/>
<input type="hidden" name="imgSource4" id="imgSource4" value="${imgSource4 }"/>
<input type="hidden" name="imgSource5" id="imgSource5" value="${imgSource5 }"/>



<input type="hidden" name="seachGroupName" id="seachGroupName" value="${seachGroupName }"/>
<input type="hidden" name="seachGroupStatus" id="seachGroupStatus" value="${seachGroupStatus }"/>
<input type="hidden" id="ba" name="ba" value="${ba}"/>
<input type="hidden" id="seachGoodsType" name="seachGoodsType" value="${seachGoodsType}"/>
<input type="hidden" id="seachGroupType" name="seachGroupType" value="${seachGroupType}"/>
<input type="hidden" id="seachSalesStatus" name="seachSalesStatus" value="${seachSalesStatus}"/>
<input type="hidden" id="seachGroupNamet" name="seachGroupNamet" value="${seachGroupNamet}"/>
<input type="hidden" id="seachMainGoodsName" name="seachMainGoodsName" value="${seachMainGoodsName}"/>
<input type="hidden" id="seachInteriorCode" name="seachInteriorCode" value="${seachInteriorCode}"/>
<input type="hidden" id="seachLabel" name="seachLabel" value="${seachLabel}"/>
<input type="hidden" id="seachBrand" name="seachBrand" value="${seachBrand}"/>
<input type="hidden" id="seachDate" name="seachDate" value="${seachDate}"/>
<input type="hidden" id="starjia" name="starjia" value="${starjia}"/>
<input type="hidden" id="endjia" name="endjia" value="${endjia}"/>
<input type="hidden" id="seachincludeImages" name="seachincludeImages" value="${seachincludeImages}"/>
<input type="hidden" id="seachqyij" name="seachqyij" value="${seachqyij}"/>
<input type="hidden" id="seachqerj" name="seachqerj" value="${seachqerj}"/>
<input type="hidden" id="seachqsanj" name="seachqsanj" value="${seachqsanj}"/>
<input type="hidden" id="yij" name="yij" value="${yij}"/>
<input type="hidden" id="erj" name="erj" value="${erj}"/>
<input type="hidden" id="sanj" name="sanj" value="${sanj}"/>
<input type="hidden" id="seachPrescriptionType" name="seachPrescriptionType" value="${seachPrescriptionType}"/>
<input type="hidden" id="seachSfHanma" name="seachSfHanma" value="${seachSfHanma}"/>
<input type="hidden" id="seachSfDatai" name="seachSfDatai" value="${seachSfDatai}"/>
<input type="hidden" id="seachGroupId" name="seachGroupId" value="${seachGroupId}"/>
<input type="hidden" id="seachCheckbo" name="seachCheckbo" value="${seachCheckbo}"/>

<div class="right_box">
<div class="right_h">
 <div class="right_h_title"> 套餐副本编辑</div>
</div>
  <div class="right_h right_bg" > 套餐副本信息</div>
<div class="right_box2">
  <div class="right_box_l">
  
  <div class="right_inline"><div class="right_title">平台</div>
  <div class="right_input" style="width:150px;">
<select id="salesPlatform" name="salesPlatform"  class="form-control input-sm" >
  <c:forEach items="${listspform }" var="item">
    <option value="${item.id }" ${goodsGroup.salesPlatformId == item.id? 'selected=selected':'' }>${item.platformName }</option>
    </c:forEach>
</select>
  </div>
  </div>
  
  
  
  <div class="right_inline">
  <div class="right_title">套餐名</div>
  <div class="right_input" style="width:300px;"><input id="groupName" name="groupName" data-bvalidator="maxlength[200],required" value="${goodsGroup.groupName }" type="text" class="form-control input-sm"  placeholder=" "  ></div>
  </div>
  <div class="right_inline">
  <div class="right_title">主商品名</div>
  <div class="right_input" style="width:300px;"><input id="mainGoodsName" name="mainGoodsName" data-bvalidator="maxlength[200],required" value="${goodsGroup.mainGoodsName }" type="text" class="form-control input-sm" placeholder=" "></div>
  </div>
  <div class="right_inline">
  <div class="right_title">快速购买码</div>
  <div class="right_input" style="width:300px;"><input id="quickBuyCode" name="quickBuyCode" data-bvalidator="maxlength[200]" value="${goodsGroup.quickBuyCode }" type="text" class="form-control input-sm"  placeholder=" "></div>
  </div>
  <div class="right_inline">
  <div class="right_title">拼音码</div>
  <div class="right_input" style="width:300px;"><input id="pinyinCode" name="pinyinCode" data-bvalidator="maxlength[200]" value="${goodsGroup.pinyinCode }" type="text" class="form-control input-sm"  placeholder="普通商品的商品名"></div>
  </div>
  <div class="right_inline">
  <div class="right_title">关键词</div>
  <div class="right_input" style="width:300px;"><input id="keyWord" name="keyWord" data-bvalidator="maxlength[200]" value="${goodsGroup.keyWord }" type="text" class="form-control input-sm"  placeholder=" "></div>
  </div>
  <div class="right_inline">
  <div class="right_title">规格/型号</div>                    
  <div class="right_input" style="width:300px;"><input id="specifications" name="specifications" data-bvalidator="maxlength[200]" value="${goodsGroup.specifications }" type="text" class="form-control input-sm"  placeholder=" "></div>
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
  
  </div>
  
  <div class="right_inline">
  <div class="right_title" style="text-align:right">二级</div>
  <div class="right_input" style="width:150px;">
<select id="qerj" name="qerj"  onchange="qerji()" class="form-control input-sm" >
  <option value="0">请选择</option>
</select>
  </div>
  
  </div>
  <div class="right_inline">
  <div class="right_title" style="text-align:right">三级</div>
  <div class="right_input" style="width:150px;">
<select name="qsanj" id="qsanj" onChange="qsanji()" class="form-control input-sm" >
  <option value="0">请选择</option>
</select>
  </div>
  </div>
  </div>
  <div class="right_box_r " style="width:500px">
  <div class="right_input" style="float:right">
 <button type="button" class="btn btn-info btn-xs margin3 " onclick="kaobei()" >拷贝主商品信息</button>
 <button type="button" onclick="save()" class="btn btn-info  btn-xs margin3 "   >套餐信息保存</button>
 <button type="button" onclick="back()" class="btn btn-info  btn-xs margin3 "   >返回</button>
</div>
  <div class="right_inline">
    <table  border="1" cellspacing="0" cellpadding="0" class="table table-bordered pull-right table-striped" id="divid" style=" width:480px; text-align:center; margin-top:5px; "  >
      <tr>
        <td width="260" valign="middle">商品名（点击查看详情）</td>
        <td width="78">数量</td>
        <td width="100" valign="middle">是否赠品</td>
        <td width="75" valign="middle">库存</td>
        <td width="107" valign="middle">操作</td>
      </tr>
    </table>
	</div>
	
	<p><button type="button" onclick="roleList()" class="btn btn-info pull-right   btn-xs " >添加商品</button></p>
  </div>
  
  <div class="right_inline">
    <div class="right_box_3">
	<div class="right_inline">基本定价属性</div>
	<div class="right_inline"><div class="right_title">成本粗估</div><div class="right_input" style="width:130px;"><input id="costPrice" name="costPrice"  data-bvalidator="number"value="${goodsGroup.costPrice }" type="text" class="form-control input-sm"  placeholder=" "></div></div>
	<div class="right_inline"><div class="right_title">建议零售价</div><div class="right_input" style="width:130px;"><input id="retailPrice" name="retailPrice" data-bvalidator="number" value="${goodsGroup.retailPrice }" type="text" class="form-control input-sm"  placeholder=" "></div></div>
	<div class="right_inline"><div class="right_title">基本定价</div><div class="right_input" style="width:130px;"><input id="basicPricing" name="basicPricing" data-bvalidator="number" value="${goodsGroup.basicPricing }" type="text" class="form-control input-sm"  placeholder=" "></div></div>
	</div>
	
	<div class="right_box_3" style="padding-top:45px">
	  <div class="right_inline"><strong>销售状态</strong></div>
	<div class="right_inline">
	  <div class="right_title">是否可销售</div>
	  <div class="right_input" style="width:100px;"><select id="salesStatus" name="salesStatus" class="form-control input-sm">
    <option value="1"${goodsGroup.salesStatus=='1'? 'selected=selected':'' }>可销售</option>
    <option value="2"${goodsGroup.salesStatus=='2'? 'selected=selected':'' }>不可销售</option>
 
</select></div></div>
	<div class="right_inline">
	  <div class="right_title">库存数量</div>
	  <div class="right_input" style="width:130px;"><input id="repertoryCount" name="repertoryCount" data-bvalidator="digit" value="${goodsGroup.repertoryCount }" type="text" class="form-control input-sm"  placeholder=" "></div></div>
	</div>
	
	<div class="right_box_3b">
	<div class="right_inline" >
	<div class="right_pic"> <img class="pimg" src="${bpath }"  id="filepatha" name="filepatha"   value=""  width="600" height="600"/></div>
	<div class="right_pic_long2" ><img class="pimg" src="${bpath5 }"  id="filepathe"  name="filepathe"  value=""  width="600" height="600"/></div>
	
 <span style="float:right; margin:10px 105px 0 0"><button type="button" onclick="totupian()" class="btn btn-info btn-xs pull-right" >更换套餐图片，请先保存套餐</button></span>
 </div>
	</div>
  
  </div>
  <div class="right_box2">
  <div class="right_box_l">
   
        <div class="right_inline">简介 [自动同步主商品]</div>
		<div class="right_inline"><textarea id="brief" name="brief" rows="3" class="form-control input-sm" style="width:360px;">${goodsGroup.brief }</textarea></div>
  </div>
  
  <div class="right_box_l">
   
        <div class="right_inline">详细介绍 [自动同步主商品]</div>
		<div class="right_inline"><textarea id="goodsDetails" name="goodsDetails" class="form-control input-sm" rows="3" style="width:360px;">${goodsGroup.goodsDetails }</textarea></div>
      
    
  </div>
  <div class="right_box_l">
   
        <div class="right_inline"><a  class="pull-right" style="padding-right:35px" data-toggle="modal" data-target="#editAddress" onclick="yulangj()">预览</a>详细介绍(HTML)</div>
		<div id="editAddress" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
	    <div class="modal-dialog">
		    <div class="modal-content">
		        <div class="modal-header" style="background:#f7f8fa; border-bottom:#4fc1e9 1px solid; border-radius:4px 4px 0 0;">
		        	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		        	<h4 class="modal-title"><i class="glyphicon glyphicon-list-alt" style="margin:0 10px 0 0; color:#4fc1e9 "></i>详细介绍预览</h4>
		        </div>
	          <div class="modal-body" id="jianj"></div>
	    </div>
		
	</div>
	    </div>
		<div class="right_inline"><textarea id="goodsDetailsHtml" name="goodsDetailsHtml" class="form-control input-sm" rows="2" style="width:360px;">${goodsGroup.goodsDetailsHtml }</textarea></div>
      
    
  </div>
  <div class="right_box_l">
   
        <div class="right_inline"><a class="pull-right" style="padding-right:35px" data-toggle="modal" data-target="#yulan" onclick="yulang()">预览</a>图文介绍（html）</div>
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
		<div class="right_inline"><textarea id="picTextDetailsHtml" name="picTextDetailsHtml" class="form-control input-sm" rows="2" style="width:360px;">${goodsGroup.picTextDetailsHtml}</textarea></div>
      
    
  </div>
  
  </div>
  </div>
  
  <div class="right_h2 right_bg" >
    <strong>其它分类属性关键词</strong>
  </div>
  
  <div class="right_box2">
    <div class="right_input" style="width:720px;">
<div id="keywordPanel" class="select-box"></div>
 
  </div>
  <div class="right_inline">
    <div class="right_box_4">
  <select id="onej" onChange="oneji()" multiple class="form-control ">
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

btn-xs" style="margin:5px 50px 0 0; width:100px;" onClick="addfl()">添加</button></div>
  </div>
  <!-------------子套餐属性
<div class="right_h right_bg" ><button type="button" class="btn btn-info  btn-xs margin3 pull-right "    >子套餐保存</button><strong>子套餐属性 </strong></div>
<div class="right_box2">
<div class="right_inline"><div class="right_input" style="float:right">
<button type="button" class="btn btn-info btn-xs margin3 " >列出所有子套餐</button>
 <button type="button" class="btn btn-info  btn-xs margin3 "   >自动生成所有套餐</button>

</div><div class="right_title_tc" >子套餐数：15</div><div class="right_title_tc" >子套餐价格：18-28</div><div class="right_title_tc" >套餐基本价格：20</div></div>
<div class="right_inline">
<table width="950" border="0"  class="table table-bordered" > 
  <tr >
    <th width="65" align="center">颜色</th>
										<th width="82"  style="text-align:center">尺寸</th>
										<th width="127" style="text-align:center">选项3（无）</th>
										<th width="110" style="text-align:center">价格（可不填）</th>
										<th width="101" style="text-align:center">内部编码</th>
										<th width="265" style="text-align:center">描述（可不填）</th>
										<th width="168" style="text-align:center">操作</th>
    </tr>
      <c:forEach items="${yhuserList}" var="item">
  <tr>
    <td align="center"><select class="form-control input-sm" style="width:60px;">
  <option>黑</option>
  <option>白</option>
  <option>蓝</option>
 
</select></td>
	   <td align="center"><select class="form-control input-sm" style="width:80px;">
  <option>黑</option>
  <option>白</option>
  <option>蓝</option>
 
</select></td>
	   <td>&nbsp;</td>
	   <td align="center"><input name="text" type="text" class="form-control input-sm"  placeholder=" " style="width:80px;"></td>
	   <td align="center"><c:if test="${item.examineStatus==0 }">
	     <input name="text2" type="text" class="form-control input-sm"  placeholder=" " style="width:80px;">
	   </c:if></td>
	   <td align="center"><input name="text3" type="text" class="form-control input-sm"  placeholder=" " style="width:230px;"></td>
	   <td align="center"><a href="#">禁用</a> <a href="#">&nbsp;启用 </a>&nbsp;<a href="#">删除 </a></td>
  </tr>
  </c:forEach>
</table>

</div>
</div>

子套餐属性-->
</div>
<div id="tianjia" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
	    </div>
	    
	<div class="right_inline">
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
          <td align="center"><fmt:formatDate value="${item.operDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
          <td align="center">${item.operName }</td>
          <td align="center">
          <c:if test="${item.operType==1 }">
                                     创建信息
          </c:if>
           <c:if test="${item.operType==2 }">
                                     审核信息
           </c:if>
          <c:if test="${item.operType==3 }">
                                   修改信息
           </c:if>
           <c:if test="${item.operType==4 }">
                                     质检信息
           </c:if>
            <c:if test="${item.operType==5 }">
                                    修改销售价格
           </c:if>
           <c:if test="${item.operType==6 }">
                                    上架
           </c:if>
           <c:if test="${item.operType==7 }">
                                    下架
           </c:if>
            <c:if test="${item.operType==8 }">
                                    同步
           </c:if>
            <c:if test="${item.operType==9 }">
                                    图片设置
           </c:if>
          </td>
        </tr>
        </c:forEach>
      </table>
	</div>  
	</div></div>
	</div>
  </div>
     
	    
	    
	    
	    
</form>
</body>
	
     
	
</html>
