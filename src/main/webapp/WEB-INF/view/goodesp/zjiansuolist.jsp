<%@ page contentType="text/html;charset=UTF-8" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
	<meta charset="utf-8" />
	<title>商品质检查询</title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	
	<link href="<%=request.getContextPath()%>/media/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/checkBox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/sorttable.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.blockUI.js"></script>
<script src="<%=request.getContextPath()%>/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script language="javascript">
  //查询分页跳转
      function gotoPage(pageno)
      {
        document.getElementById('pageno').value = pageno;
        document.form.submit();
      }
       function gotoPageSize(pageSize)
      {
        document.getElementById('pageSize').value = pageSize;
        document.form.submit();
      }
  //搜索
    function chang(ba){
    	document.getElementById('pageno').value ="1";
         //var seachshzt=document.getElementById("seachshzt").value;
    	
    	//var seachgoodsName=document.getElementById("seachgoodsName").value;
    	
    	document.form.action = "goodsp.do?method=getzhijiansList&ba="+ba;
		document.form.submit();
    }
  //高级搜索
    function gaojichang(ba){
      //yiji();
     // qyiji();
      document.getElementById('pageno').value ="1";
      var yijid=document.getElementById("yij").value;//药监第一级选中的值
   	  var erj=document.getElementById("erj").value;//药监选中第二级
   	  var sanj=document.getElementById("sanj").value;//药监选中第三级
      var qyijid=document.getElementById("qyij").value;//前端第一级选中的值
      var qerj=document.getElementById("qerj").value;//前端第一级选中的值
      var qsanj=document.getElementById("qsanj").value;//前端第一级选中的值
	  //var shangpin=document.getElementById("shangpin").value;//商品名包含
	  //var seachinteriorCode=document.getElementById("seachinteriorCode").value;//编号包含
	  //var seachbrand=document.getElementById("seachbrand").value;//品牌包含
	  var starjia=document.getElementById("starjia").value;//售价开始值
	  var endjia=document.getElementById("endjia").value;//售价最大值
	  //var includeImages=document.getElementById("includeImages").value;//是否有图片
	  //var seachmanufacturer=document.getElementById("seachmanufacturer").value;//生产企业
	  if(parseInt(endjia)<parseInt(starjia)){
		  alert("售价最大值不能小于售价开始值");
		  return;
	  }
	  document.form.action = "goodsp.do?method=getzhijiansList&starjia="+starjia+"&endjia="+endjia+"&yijid="+yijid+"&erj="+erj+"&sanj="+sanj+"&qyijid="+qyijid+"&qerj="+qerj+"&qsanj="+qsanj+"&ba="+ba;
	  document.form.submit();
	  
    }
    function toadd(){
  	  document.form.action = "goodsp.do?method=ztoadd";
  	  document.form.submit();
    }
    $(document).ready(function(){
      var qyijindex=window.document.getElementById("qyij").selectedIndex;//前端选中第一级
   	  var qerjindex=window.document.getElementById("qerj").selectedIndex;//前端选中第二级
   	  var qsanjindex=window.document.getElementById("qsanj").selectedIndex;//前端选中第三级
   	  
   	  var yijindex=window.document.getElementById("yij").selectedIndex;//药监选中第一级
   	  var erjindex=window.document.getElementById("erj").selectedIndex;//药监选中第二级
   	  var sanjindex=window.document.getElementById("sanj").selectedIndex;//药监选中第三级
   	  qyijindex=document.getElementById("qyijid").value;//前端
	  qerjindex=document.getElementById("qerjid").value;//前端
	  qsanjindex=document.getElementById("qsanjid").value;//前端
	  
	  yijindex=document.getElementById("yijid").value;//药监
	  erjindex=document.getElementById("erjid").value;//药监
	  sanjindex=document.getElementById("sanjid").value;//药监
	  wqyiji(qyijindex,qerjindex);//前端
	  wqerji(qerjindex,qsanjindex);//前端
	  wqsanji(qsanjindex);//前端
	  
	  
	  wyiji(yijindex,erjindex);//药监
	  werji(erjindex,sanjindex);//药监
	  wsanji(sanjindex);//药监
	   var radiozt='${zjradio}';
	  if(radiozt=='1'){
	  document.getElementById("zhijfl").style.display="block";
	  }
	  if(radiozt=='0'){
	  document.getElementById("zhijfl").style.display="none";
	  }
	  var qdradio='${qdradio}';
	  if(qdradio=='1'){
	  document.getElementById("qiandfl").style.display="block";
	  }
	  if(qdradio=='0'){
	  document.getElementById("qiandfl").style.display="none";
	  }
	  //提交按钮的单击事件处理
		$('#submitBtn').click(function(){
			if(confirm("你确定要商品全部生成单品套餐吗？时间有点长请您等待？")){
			 $.blockUI({ message: "处理中，请稍候...", css: {color:'#fff',border:'3px solid #aaa',backgroundColor:'#CC3300'}});
			
			 $.ajax({
    			type : "POST",  
    			dataType: "json",//返回json格式的数据
    			url:"<%=request.getContextPath()%>/goodsp.do?method=allsctaocan",
    			success: function(data){
    			
    	          $.each(data,function(i,result){  
	            	alert(result);
	            	
	            	document.form.action = "goodsp.do?method=getGoodsList";
	                document.form.submit();
	             }); 
    			}
    		});
			}
		});
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
        
  function shengh(){
	  var shzt=document.getElementById("shzt").value;
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
  
  //编辑跳转
  function tomodify(id,starus){
	  document.form.action = "goodsp.do?method=zload&id="+id+"&starus="+starus;
	  document.form.submit();
  }
  //审核跳转
  function toshenghe(id){
	  document.form.action = "goodsp.do?method=zshenghe&id="+id;
	  document.form.submit();
  }
//复选框审核
  function shengheall(){
  	if(chkCheckBoxChs('goodsdto.goodcheck')== false){
  		alert('请至少选择一项！');
  		return;
  	}		
  	if(confirm("确定要审核选中的信息吗？")){
  	 var goodcheck=document.getElementsByName('goodsdto.goodcheck');
  var gcheck="";
  for(var i=0;i<goodcheck.length;i++){
            if(goodcheck[i].checked==true){
            gcheck+=goodcheck[i].value+",";
            }
        }
         $.blockUI({ message: "处理中，请稍候...", css: {color:'#fff',border:'3px solid #aaa',backgroundColor:'#CC3300'}});
          $.ajax({
    			type : "POST",  
    			dataType: "json",//返回json格式的数据
    			url:"<%=request.getContextPath()%>/goodsp.do?method=zplsheng&gcheck="+gcheck,
    			success: function(data){
    			
    	          $.each(data,function(i,result){  
	            	alert(result);
	            	
	            	document.form.action = "goodsp.do?method=getzhijiansList";
	                document.form.submit();
	             }); 
    			}
    		});
  		
  	}else{
  		return false;
  	}
  }
  
  
//复选框商品生成套餐
  function spsctcall(){
  	if(chkCheckBoxChs('goodsdto.goodcheck')== false){
  		alert('请至少选择一项！');
  		return;
  	}		
  	if(confirm("确定要选中商品生成套餐的信息吗？")){
  	
  	 var goodcheck=document.getElementsByName('goodsdto.goodcheck');
  var gcheck="";
  for(var i=0;i<goodcheck.length;i++){
            if(goodcheck[i].checked==true){
            gcheck+=goodcheck[i].value+",";
            }
        }
  	 $.blockUI({ message: "处理中，请稍候...", css: {color:'#fff',border:'3px solid #aaa',backgroundColor:'#CC3300'}});
  	 $.ajax({
    			type : "POST",  
    			dataType: "json",//返回json格式的数据
    			url:"<%=request.getContextPath()%>/goodsp.do?method=plshengtaocan&gcheck="+gcheck,
    			success: function(data){
    			
    	          $.each(data,function(i,result){  
	            	alert(result);
	            	
	            	document.form.action = "goodsp.do?method=getGoodsList";
	                document.form.submit();
	             }); 
    			}
    		});
  	}else{
  		return false;
  	}
  }
  
   //质检全部审核
  function allzplsheng(){	
  	if(confirm("确定要质检全部审核吗？全部生成时间会很长")){
  	 $.blockUI({ message: "处理中，请稍候...", css: {color:'#fff',border:'3px solid #aaa',backgroundColor:'#CC3300'}});
  	  $.ajax({
    			type : "POST",  
    			dataType: "json",//返回json格式的数据
    			url:"<%=request.getContextPath()%>/goodsp.do?method=allzplsheng",
    			success: function(data){
    			
    	          $.each(data,function(i,result){  
	            	alert(result);
	            	
	            	document.form.action = "goodsp.do?method=getzhijiansList";
	                document.form.submit();
	             }); 
    			}
    		});
	           
  	}else{
  		return false;
  	}
  }
//查看详情
  function seedetailed(id){

	  jQuery.ligerDialog.open({ 
			isDrag:true,//是否拖动
	        showMax:true,//是否显示最大化按钮
			height: 500, 
			width:1000,
			top:1,
			slide:true,
			title:'质检商品详情', 
			url: '<%=request.getContextPath()%>/goodsp.do?method=shendetailed&id='+id
		}); 
  }
  function zhejhidden(){
  document.getElementById("zhijfl").style.display="none";
  }
  
  function zhejshow(){
  document.getElementById("zhijfl").style.display="block";
  }
  function qdhidden(){
  document.getElementById("qiandfl").style.display="none";
  }
  
  function qdshow(){
  document.getElementById("qiandfl").style.display="block";
  }
</script>
<body >
<form action="goodsp.do?method=getzhijiansList" name="form" method="post">
		<input type="hidden" id="pageno" name="pageno" value="${pageno}"/>
		<input type="hidden" id="pageSize" name="pageSize" value="${pageSize}"/>
		
		<input type="hidden" id="yijid" name="yijid" value="${yijid}"/>
		<input type="hidden" id="erjid" name="erjid" value="${erjid}"/>
		<input type="hidden" id="sanjid" name="sanjid" value="${sanjid}"/>
		
		<input type="hidden" id="qyijid" name="qyijid" value="${qyijid}"/>
		<input type="hidden" id="qerjid" name="qerjid" value="${qerjid}"/>
		<input type="hidden" id="qsanjid" name="qsanjid" value="${qsanjid}"/>
		<input type="hidden" id="ba" name="ba" value="${ba}"/>
<!-- class="right_box" -->
<div>
<div class="panel panel-default">
  <div class="panel-heading">
    <h5 class="panel-title" style="color:#31849b;">商品质检查询</h5>
  </div>
  <div class="panel-body">
  	<div class="row" style="vertical-align: middle;">
	  <div class="col-xs-3" style="margin-top: 5px;">
	    <label>商品编码或商品名称:</label>
	  </div>
	  <div class="col-xs-3" style="margin-left: -10%;">
	    <input  id="seachgoodsName" name="seachgoodsName" value="${seachgoodsName}" type="text" class="form-control input-sm"placeholder="请输入商品编码或商品名称...">
	  </div>
	 
	  <div class="col-xs-6" style="margin-top: 5px;">
 		<button type="button" onClick="chang('1')" class="btn btn-info  btn-xs margin3 " style="width: 70px;">搜索</button>
 		<button type="button" class="btn btn-info  btn-xs margin3 " id="productBtn" style="width: 70px;">高级</button>
	  </div>
	  
	</div>
  </div>
</div>

<!-- <div class="right_h"><h3>商品质检查询</h3></div> style="width:950px"-->

 <div class="right_box2" style="display:none;width: 100%" id="productPanel">
<div class="right_inline">
      <div class="right_title"><strong>套餐</strong></div>
						<div class="right_title_min">是否医药类</div>
						<div class="right_input" >
						 <select class="form-control input-sm" >
  <option>不限</option>
  <option>未审核</option>
  <option>3</option>
  <option>4</option>
  <option>5</option>
</select></div>
<div class="right_title_min">商品件数</div>
 <div class="right_input" >
						 <select class="form-control input-sm" >
  <option>不限</option>
  <option>未审核</option>
  <option>3</option>
  <option>4</option>
  <option>5</option>
</select>
</div>
<div class="right_title_min">是否可销售
</div>
 <div class="right_input" >
						 <select class="form-control input-sm" >
  <option>不限</option>
  <option>未审核</option>
  <option>3</option>
  <option>4</option>
  <option>5</option>
</select></div>

    
  
</div>













<div class="right_inline">
	<div class="right_title"><strong>质检通用</strong></div>
	<div class="right_input" style="width:40px;padding-top: 5px">商品名</div>	
	<div class="right_input" style="width:110px"><input  id="shangpin" name="shangpin" type="text" class="form-control input-sm"  value="${shangpin }"></div>
	<div class="right_input" style="width:40px;padding-top: 5px;padding-left: 9px">编码</div>
	<div class="right_input" style="width:110px"><input  id="seachinteriorCode" name="seachinteriorCode" type="text" class="form-control input-sm"    value="${seachinteriorCode }"> </div>
	<div class="right_input" style="width:40px;padding-top: 5px;padding-left: 9px">品牌</div>
	<div class="right_input" style="width:110px"><input  id="seachbrand" name="seachbrand" type="text" class="form-control  input-sm"  value="${seachbrand }"></div>
						<!--  
						<input name="text3" type="text" class="form-control" style="width:20%;">
						-->
						<div class="right_title_min">是否有图片
</div>
 <div class="right_input" >
						 <select id="includeImages" name="includeImages" class="form-control input-sm" >
        <option value="">全部</option>
        <option value="0" ${"0"==includeImages?'selected="selected"':''}>无图片</option>
        <option value="1" ${"1"==includeImages?'selected="selected"':''}>有图片</option>
</select></div>
<div class="right_title_min">生产厂家
</div>
 <div class="right_input" >
					<input  id="seachmanufacturer" name="seachmanufacturer" type="text" class="form-control  input-sm"   value="${seachmanufacturer }">
   </div>
   </div>



<div class="right_inline"> <div class="right_title"><strong>销售用</strong></div>
						<div class="right_title_min">商品生成时间</div>
						<div class="right_input" >
						<select name="seachDate" class="form-control input-sm" >
      <option>全部</option>
      <option value="1" ${"1"==seachDate?'selected="selected"':''}>24小时内</option>
      <option value="2" ${"2"==seachDate?'selected="selected"':''}>72小时内</option>
      <option value="3" ${"3"==seachDate?'selected="selected"':''}>一周内</option>
      <option value="4" ${"4"==seachDate?'selected="selected"':''}>30天内</option>
    </select></div>
<div class="right_title_min">基本价格范围</div>

<div class="right_input" style="width:60px;"><input  type="text" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" id="starjia" name="starjia" value="${starjia }" class="form-control input-sm"  style="width:60px;" ></div>
<div class="right_title_min">到</div>
<div class="right_input" style="width:60px"><span class="right_input" style="width:60px">
  <input  type="text" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" id="endjia" name="endjia" value="${endjia }" class="form-control input-sm" style="width:60px;" >
</span></div>

</div>
<div class="right_title_min" style="width:82px;">处方类型 </div>
 <div class="right_input" style="width:150px;">
    <select name="seachPrescriptionType" class="form-control input-sm" >
    <option value="">请选择</option>
    <c:forEach items="${listchu }" var="item">
    <option value="${item.id }" ${seachPrescriptionType == item.id? 'selected=selected':'' }>${item.attname}</option>
    </c:forEach>   
      
    </select>
  </div>
   <div class="col-xs-2" style="width: 14%">
	    <label>美编审核:</label>
	  </div>
	  <div class="col-xs-5" style="width: 14%;margin-left: -5%;margin-top: -5px;" >
	    
		<select id="seacheditStatus" name="seacheditStatus" onChange="shengh()" class="form-control input-sm">
        	<option value="">全部</option>
	      	<option value="0" ${"0"==seacheditStatus?'selected="selected"':''}>未审核</option>
	        <option value="1" ${"1"==seacheditStatus?'selected="selected"':''}>审核通过</option>
	        <option value="2" ${"2"==seacheditStatus?'selected="selected"':''}>审核未通过</option>
		</select>
	  </div>
	  <div class="col-xs-2" style="width: 14%">
	    <label>质检审核:</label>
	  </div>
	  <div class="col-xs-4" style="width: 14%;margin-top: -5px;margin-left: -8%">
	     
		  <select id="seachshzt" name="seachshzt" class="form-control input-sm">
			<option value="">全部</option>
			<option value="0" ${"0"==seachshzt?'selected="selected"':''}>未审核</option>
			<option value="1" ${"1"==seachshzt?'selected="selected"':''}>审核通过</option>
			<option value="2" ${"2"==seachshzt?'selected="selected"':''}>审核未通过</option>
		 </select>
	  </div>
<div class="right_inline" >
 <div class="right_title">&nbsp;</div>
<div id="zhijfl">
						<div class="right_title_min">质检分类一级</div>
		<div class="right_input">
<select id="yij" onChange="yiji()"  class="form-control input-sm" >
  <option value="0">请选择</option>
  <c:forEach items="${lista}" var="item">
  <option value="${item.id }" ${yijid eq item.id? 'selected=selected':'' }>${item.className }</option>
  </c:forEach>
</select></div>
<div class="right_title_min">二级</div>
<div class="right_input">
<select id="erj"  onchange="erji()" class="form-control input-sm" >
 <option value="0">请选择</option>
</select>
</div>
<div class="right_title_min">三级</div><div class="right_input">
<select id="sanj" onChange="sanji()" class="form-control input-sm" >
  <option>请选择</option>
</select></div>
</div>
<div class="right_title_min">有质检
</div>
 <div class="right_input" >
<input   id="zjradio" name="zjradio" onclick="zhejshow()" type="radio" value="1" ${zjradio=='1'?'checked':''}>
   </div>
<div class="right_title_min">无质检
</div>
 <div class="right_input" >
<input   id="zjradio" name="zjradio" onclick="zhejhidden()" type="radio" value="0" ${zjradio=='0'?'checked':''}>
   </div>
</div>


<div class="right_inline">
<div class="right_title">&nbsp;</div>
<div id="qiandfl">
						<div class="right_title_min">前端分类一级</div>
		<div class="right_input">
<select id="qyij" onChange="qyiji()" class="form-control input-sm" >
<option value="0">请选择</option>
   <c:forEach items="${listq}" var="item">
   <option value="${item.id }" ${qyijid eq item.id? 'selected=selected':'' }>${item.className }</option>
   </c:forEach>
</select></div>
<div class="right_title_min">二级</div>
<div class="right_input">
<select id="qerj"  onchange="qerji()" class="form-control input-sm" >
  <option value="0">请选择</option>
</select>
</div>
<div class="right_title_min">三级</div><div class="right_input input-sm">
<select id="qsanj" onChange="qsanji()" class="form-control input-sm" >
  <option value="0">请选择</option>
</select></div>
</div>
<div class="right_title_min">有前端
</div>
 <div class="right_input" >
<input   id="qdradio" name="qdradio" onclick="qdshow()" type="radio" value="1" ${qdradio=='1'?'checked':''}>
   </div>
<div class="right_title_min">无前端
</div>
 <div class="right_input" >
<input   id="qdradio" name="qdradio" onclick="qdhidden()" type="radio" value="0" ${qdradio=='0'?'checked':''}>
   </div>

</div>
<div class="right_inline"><button type="button" onClick="gaojichang('2')" class="btn btn-info  ">高级搜索</button></div>
<hr style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=10 />
</div>



<div class="right_inline" >

 
 







<div class="right_inline" >
<div class="form-inline">
	
		<!-- 			
						 <select class="form-control pull-right">
  <option>商品A-Z</option>
  <option>未审核</option>
  <option>3</option>
  <option>4</option>
  <option>5</option>
</select>
 <select class="form-control pull-right">
  <option>每页50项</option>
  <option>未审核</option>
  <option>3</option>
  <option>4</option>
  <option>5</option>
</select>
 -->	
</div>

</div>

<div class="right_inline" style="padding-top:15px;">
 <div class="right_title_min"  >
<label class="checkbox-inline">
<!--  
  <input type="checkbox" id="inlineCheckbox1" value="option1" style="margin-top:9px;"> 全选
-->
</label></div>
 <div class="right_title_min"  >
<label class="checkbox-inline">
<!--  
  <input type="checkbox" id="inlineCheckbox2" value="option2" style="margin-top:9px;">所有页全选
-->
</label></div>
 <div class="right_input" style="float:right">
 <sec:authorize ifAnyGranted="/goodsp.do?method=allzplsheng,/goodsp.do" >
 	<button type="button" class="btn btn-info  btn-sm margin3 "  onclick="allzplsheng()" >全部质检审核</button>
 </sec:authorize>
 <sec:authorize ifAnyGranted="/goodsp.do?method=zplsheng,/goodsp.do" >
 	<button type="button" class="btn btn-info  btn-sm margin3 "  onclick="shengheall()" >批量质检审核</button>
 </sec:authorize>
 <!-- 
		<button type="button" class="btn btn-info btn-sm margin3 " onclick="spsctcall()" >批量生成单品套餐(限质检已审)</button>
 		<button type="button" id="submitBtn" name="submitBtn"  class="btn btn-info btn-sm margin3 " >全部生成单品套餐</button>
  		<button type="button" onclick="toadd()" class="btn btn-info btn-sm margin3 " >添加</button>
		<button type="button" class="btn btn-info btn-sm margin3 " >批量下架</button>
 -->
</div>



<div class="right_inline"  >

<table width="950" border="0"  class="table table-bordered"> 
  <tr>
    <td width="70"><label class="checkbox-inline">
    <input type="checkbox" name="checkAll" onClick="selAlls('goodsdto.goodcheck',event)" onClick="selectAll();"  style="margin-top:9px;"> 全选
    <!--  
  <input type="checkbox" id="inlineCheckbox1" value="option1"> 
  -->

</label></td>
                                        <th width="50">内部编码</th>
										<th width="50">商品名</th>
										<th width="50">通用名</th>
										<th width="60">处方类型</th>
										<th width="90">质检审核状态</th>
										<th width="90">美编审核状态</th>
										<th width="60">基本价格</th>
										<th width="70">生产厂家</th>
										<th width="90">操作</th>
    </tr>
      <c:forEach items="${yhuserList}" var="item">
  <tr>
    <td><label class="checkbox-inline">
    <!--  
    <input type="checkbox" id="inlineCheckbox1" value="option1"> 
    -->
    <input type="checkbox" name="goodsdto.goodcheck" value="${item.id}">
     </label></td>
       <td>${item.interiorCode }</td>
	   <td>${item.goodsName }</td>
	   <td>${item.commonName }</td>
	   <td>${item.attname }</td>
	   <td><c:if test="${item.examineStatus==0 }">未审核</c:if>
	         <c:if test="${item.examineStatus==1 }">审核通过</c:if>
	         <c:if test="${item.examineStatus==2 }">审核未通过</c:if>         
	   </td>
	   <td><c:if test="${item.editStatus==0 }">未审核</c:if>
	         <c:if test="${item.editStatus==1 }">审核通过</c:if>
	         <c:if test="${item.editStatus==2 }">审核未通过</c:if>         
	   </td>
	   <td>${item.basicPricing }</td>
	   <td>${item.manufacturer }</td>
    <td>
    	
        <c:if test="${item.editStatus==1 and (item.examineStatus==0 or item.examineStatus==2)}">
        	<sec:authorize ifAnyGranted="/goodsp.do?method=zshenghe,/goodsp.do" >
	      		<button type="button" onClick="toshenghe('${item.id }')" class="btn btn-success   btn-xs"  >审核</button>
      		</sec:authorize>
        </c:if>
        <sec:authorize ifAnyGranted="/goodsp.do?method=shendetailed,/goodsp.do" >
        	<button type="button" onclick="seedetailed('${item.id }')" class="btn btn-success   btn-xs" >查看</button>
        </sec:authorize>
        
        
        
        
        
        <!--<button type="button" onClick="tomodify('${item.id }','1')" class="btn btn-success   btn-xs"   >编辑</button>-->
      <!--<button type="button" class="btn btn-primary  btn-xs"  >编辑图片</button>-->
        
      </td>
  </tr>
  </c:forEach>
</table>
<ul class="pager">
<%@ include file="/WEB-INF/view/common/page.jspf" %>
</ul>
</div>
</div>
</div>

</div>
</form>
</body>
<script>
		$(document).ready(function(){
		var ba= '${ba}';
		 if(ba==2){
		 $('#productPanel').attr("style","");
		 }
			$('#productBtn').click(function(){
				$('#productPanel').toggle();
			});
		});
		

			</script>
</html>
