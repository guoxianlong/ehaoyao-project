<%@ page contentType="text/html;charset=UTF-8" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
	<meta charset="utf-8" />
	<title>套餐查询</title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	
	<link href="<%=request.getContextPath()%>/media/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/checkBox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/sorttable.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.3.js"></script>
<script src="<%=request.getContextPath()%>/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.blockUI.js"></script>
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
    	var seachCheckbo=document.getElementById("seachCheckbo").value;
    	if( $("#seachCheckbo").prop("checked")==true){
         seachCheckbo="1";
         }else{
         seachCheckbo="";
         }
    	//var seachGroupName=document.getElementById("seachGroupName").value;
    	//var seachGroupStatus=document.getElementById("seachGroupStatus").value;
    	//document.form.action = "goodsGroup.do?seachGroupName="+seachGroupName+"&seachGroupStatus="+seachGroupStatus;
    	document.form.action = "goodsGroup.do?method=load&ba="+ba+"&seachCheckbo="+seachCheckbo;
		document.form.submit();
    }
  //高级搜索
    function gaojichang(ba){
      var seachCheckbo=document.getElementById("seachCheckbo").value;
    	if( $("#seachCheckbo").prop("checked")==true){
         seachCheckbo="1";
         }else{
         seachCheckbo="";
         }
	  var starjia=document.getElementById("starjia").value;//售价开始值
	  var endjia=document.getElementById("endjia").value;//售价最大值
	  if(parseInt(endjia)<parseInt(starjia)){
		  alert("售价最大值不能小于售价开始值");
		  return;
	  }
	  document.form.action = "goodsGroup.do?method=load&ba="+ba+"&seachCheckbo="+seachCheckbo;
	  document.form.submit();
	  
    }
  function shengh(){
	  var shzt=document.getElementById("shzt").value;
  }
//复选框审核
  function shengheall(){
  	if(chkCheckBoxChs('checkgroup')== false){
  		alert('请至少选择一项！');
  		return;
  	}		
  	if(confirm("确定要审核选中的信息吗？")){
  		form.action="goodsGroup.do?method=allqc";
  		form.submit();
  	}else{
  		return false;
  	}
  }
  //全部审核
  function shengheplall(){
  	if(confirm("确定要全部审核吗？时间有点长")){
  	 $.blockUI({ message: "处理中，请稍候...", css: {color:'#fff',border:'3px solid #aaa',backgroundColor:'#CC3300'}});
  		form.action="goodsGroup.do?method=allplqc";
  		form.submit();
  	}else{
  		return false;
  	}
  }
  
  
  $(document).ready(function(){
      var qyijindex=window.document.getElementById("seachqyij").value;//前端选中第一级
   	  var qerjindex=window.document.getElementById("seachqerj").value;//前端选中第二级
   	  var qsanjindex=window.document.getElementById("seachqsanj").value;//前端选中第三级
   	  var yijindex=window.document.getElementById("yij").value;//药监选中第一级
 	  var erjindex=window.document.getElementById("erj").value;//药监选中第二级
 	  var sanjindex=window.document.getElementById("sanj").value;//药监选中第三级
      qerjindex=document.getElementById("qerjid").value;
      qsanjindex=document.getElementById("qsanjid").value;
      erjindex=document.getElementById("erjid").value;
      sanjindex=document.getElementById("sanjid").value;
 
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
	  
	  if('${seachCheckbo}'=='1'){
	   $("[name = seachCheckbo]:checkbox").prop("checked", true);
	   }else{
	   $("[name = seachCheckbo]:checkbox").prop("checked", false);
	   }
	  
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
  			url:"<%=request.getContextPath()%>/goodsGroup.do?method=getTreeLister&ida="+yij,
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
  			url:"<%=request.getContextPath()%>/goodsGroup.do?method=getTreeListsan&idc="+erj,
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
  			url:"<%=request.getContextPath()%>/goodsGroup.do?method=getTreesan&sanj="+sanj,
  			success: function(data){
  	           
  			}
  		});
  }
  //前端商品点击第一级时出发的事件
  function wqyiji(qyij,qerjindex){
  	  var item="<option value=0 >请选择</option>";
  	  document.getElementById("seachqerj").length=0;
  	  document.getElementById("seachqsanj").length=0;
  	  $.ajax({
  			type : "POST",  
  			dataType: "json",//返回json格式的数据
  			url:"<%=request.getContextPath()%>/goodsGroup.do?method=getTreeListerq&ida="+qyij,
  			success: function(data){
  	             $.each(data,function(i,result){  
  	            	 item +="<option value="+result['id']+">"+result['className']+"</option>";
  	             });  
  	             $('#seachqerj').append(item);
  	             $("#seachqerj option").each(function(){
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
  	  document.getElementById("seachqsanj").length=0;
  	  $.ajax({
  			type : "POST",  
  			dataType: "json",//返回json格式的数据
  			url:"<%=request.getContextPath()%>/goodsGroup.do?method=getTreeListsanq&idc="+qerj,
  			success: function(data){
  	             $.each(data,function(i,result){  
  	            	  item +="<option value="+result['id']+">"+result['className']+"</option>";
  	             });  
  	             $('#seachqsanj').append(item);  
  	             $("#seachqsanj option").each(function(){
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
  	  $.ajax({
  			type : "POST",  
  			dataType: "json",//返回json格式的数据
  			url:"<%=request.getContextPath()%>/goodsGroup.do?method=getTreesanq&sanj="+qsanj,
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
			url:"<%=request.getContextPath()%>/goodsGroup.do?method=getTreeLister&ida="+yij,
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
			url:"<%=request.getContextPath()%>/goodsGroup.do?method=getTreeListsan&idc="+erj,
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
			url:"<%=request.getContextPath()%>/goodsGroup.do?method=getTreesan&sanj="+sanj,
			success: function(data){
	           
			}
		});
  }
//前端商品点击第一级时出发的事件
  function qyiji(){
	  var item="<option value=0 >请选择</option>";
	  var item3="<option value=0 >请选择</option>";
	  document.getElementById("seachqerj").length=0;
	  document.getElementById("seachqsanj").length=0;
	  $('#seachqsanj').append(item3);  
	  var  qyij=document.getElementById("seachqyij").value;
	  $.ajax({
			type : "POST",  
			dataType: "json",//返回json格式的数据
			url:"<%=request.getContextPath()%>/goodsGroup.do?method=getTreeListerq&ida="+qyij,
			success: function(data){
	             $.each(data,function(i,result){  
	            	 item +="<option value="+result['id']+">"+result['className']+"</option>";
	             });  
	             $('#seachqerj').append(item);  
			}
		});
  }
  //前端商品第二级点击时触发的事件
  function qerji(){
	  var item="<option value=0>请选择</option>";
	  document.getElementById("seachqsanj").length=0;
	  var  qerj=document.getElementById("seachqerj").value;
	  $.ajax({
			type : "POST",  
			dataType: "json",//返回json格式的数据
			url:"<%=request.getContextPath()%>/goodsGroup.do?method=getTreeListsanq&idc="+qerj,
			success: function(data){
	             $.each(data,function(i,result){  
	            	  item +="<option value="+result['id']+">"+result['className']+"</option>";
	             });  
	             $('#seachqsanj').append(item);  
			}
		});
  }
  //前端商品点击第三级时出发的事件
  function qsanji(){
	  var qsanj=document.getElementById("seachqsanj").value;
	  $.ajax({
			type : "POST",  
			dataType: "json",//返回json格式的数据
			url:"<%=request.getContextPath()%>/goodsGroup.do?method=getTreesanq&sanj="+qsanj,
			success: function(data){
	           
			}
		});
  }
  //全部生成套餐副本
  function alltcscfbSave(){
  if(confirm("你确定全部套餐生成副本吗？时间有点长请您等待？")){
  $.blockUI({ message: "处理中，请稍候...", css: {color:'#fff',border:'3px solid #aaa',backgroundColor:'#CC3300'}});
  var salesPlatform=document.getElementById("salesPlatform").value;
   $.ajax({
    			type : "POST",  
    			dataType: "json",//返回json格式的数据
    			url:"<%=request.getContextPath()%>/goodsGroup.do?method=alltcscfbSave&salesPlatform="+salesPlatform,
    			success: function(data){
    			
    	          $.each(data,function(i,result){  
	            	alert(result);
	            	
	            	document.form.action = "goodsGroup.do?method=load";
	                document.form.submit();
	             }); 
    			}
    		});
  }
  }
  
  //
  //批量套餐生成副本
  function pltcscfbSave(){
  
  	if(chkCheckBoxChs('checkgroup')== false){
  		alert('请至少选择一项！');
  		return;
  	}		
  	if(confirm("确定要选中商品生成套餐的吗？")){
  	
  var goodcheck=document.getElementsByName('checkgroup');
  var gcheck="";
  for(var i=0;i<goodcheck.length;i++){
            if(goodcheck[i].checked==true){
            gcheck+=goodcheck[i].value+",";
            }
        }
  	 $.blockUI({ message: "处理中，请稍候...", css: {color:'#fff',border:'3px solid #aaa',backgroundColor:'#CC3300'}});
  	 var salesPlatform=document.getElementById("salesPlatform").value;
  	 
  	 $.ajax({
    			type : "POST",  
    			dataType: "json",//返回json格式的数据
    			url:"<%=request.getContextPath()%>/goodsGroup.do?method=pltcscfbSave&gcheck="+gcheck+"&salesPlatform="+salesPlatform,
    			success: function(data){
    			
    	          $.each(data,function(i,result){  
	            	alert(result);
	            	
	            	document.form.action = "goodsGroup.do?method=load";
	                document.form.submit();
	             }); 
    			}
    		});
  	}else{
  		return false;
  	}
  }
  function delGoodsGroup(id){
   if(confirm("确定删除套餐吗？")){
  $.blockUI({ message: "处理中，请稍候...", css: {color:'#fff',border:'3px solid #aaa',backgroundColor:'#CC3300'}});
   $.ajax({
    		type : "POST",  
    			dataType: "json",//返回json格式的数据
    			url:"<%=request.getContextPath()%>/goodsGroup.do?method=delGoodsGroup&id="+id,
    			success: function(data){
    	          $.each(data,function(i,result){  
	            	alert(result);
	            	document.form.action = "goodsGroup.do?method=load";
	                document.form.submit();
	             }); 
    			}
    		});
    }else{
    return false;
    }
  }
  
  //编辑跳转
  function tomodify(id,goodsId){
	  document.form.action = "goodsGroup.do?method=toupdate&id="+id+"&maingoodsid="+goodsId;
	  document.form.submit();
  }
  //套餐生成副本跳转
  function tomodifyfb(id,goodsId){
	  document.form.action = "goodsGroup.do?method=totcscfb&id="+id+"&maingoodsid="+goodsId;
	  document.form.submit();
  }
//图片跳转
  function totupian(id){
	  document.form.action = "goodsImage.do?method=tcload&id="+id+"&wtype=5";
	  document.form.submit();
  }
  //审核跳转
  function toshenghe(id,goodsId){
	  document.form.action = "goodsGroup.do?method=toqi&id="+id+"&maingoodsid="+goodsId;
	  document.form.submit();
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
  //查看详情
  function seedetailed(id,maingoodsid){
	  jQuery.ligerDialog.open({ 
			isDrag:true,//是否拖动
	        showMax:true,//是否显示最大化按钮
			height: 500, 
			width:1000,
			top:1,
			slide:true,
			title:'套餐详情', 
			url: '<%=request.getContextPath()%>/goodsGroup.do?method=toupdetailed&id='+id+'&maingoodsid='+maingoodsid
			
		}); 
	 // document.form.action = "goodsGroup.do?method=toupdetailed&id="+id;
	  //.form.submit();
  }
  
  
 function seachCheckboaa(){
  
 if( $("#seachCheckbo").prop("checked")==true){
 alert("true");
 document.getElementById("seachCheckbo").value="1";
 }else{
 alert("false");
 document.getElementById("seachCheckbo").value="0";
 alert(document.getElementById("seachCheckbo").value);
 }
  
  }
  
  
  
  
  //拷贝主商品保存
  function copymaintotc(id,goodsId){
  if(confirm("确定要拷贝吗？")){
   $.blockUI({ message: "处理中，请稍候...", css: {color:'#fff',border:'3px solid #aaa',backgroundColor:'#CC3300'}});
  		$.ajax({
    			type : "POST",  
    			dataType: "json",//返回json格式的数据
    			url:"<%=request.getContextPath()%>/goodsp.do?method=toCopyTheGoodsToSetmeal&id="+id+"&goodsId="+goodsId,
    			success: function(data){
    	          $.each(data,function(i,result){  
	            	alert(result);
	            	document.form.action = "goodsGroup.do?method=load";
	                document.form.submit();
	             }); 
    			}
    		});
  	}else{
  		return false;
  	}
  }
</script>

<body>
<form action="goodsGroup.do?method=load" name="form" method="post">
		<input type="hidden" id="pageno" name="pageno" value="${pageno}"/>
		<input type="hidden" id="pageSize" name="pageSize" value="${pageSize}"/>
		<input type="hidden" id="qerjid" name="qerjid" value="${qerjid}"/>
		<input type="hidden" id="qsanjid" name="qsanjid" value="${qsanjid}"/>
		<input type="hidden" id="erjid" name="erjid" value="${erjid}"/>
		<input type="hidden" id="sanjid" name="sanjid" value="${sanjid}"/>
		
		<input type="hidden" id="ba" name="ba" value="${ba}"/>
		
<div class="panel panel-default">
  <div class="panel-heading">
    <h5 class="panel-title" style="color:#31849b;">套餐查询</h5>
  </div>
  <div class="panel-body">
  	<div class="col-xs-3" style="margin-top: 7px;">
	    <label>商品编码或套餐名称:</label>
	</div>
	<div class="col-xs-2" style="margin-left: -110px;">
	    <input id="seachGroupName" style="width: 120%;" name="seachGroupName" type="text" value="${seachGroupName }" class="form-control input-sm"  placeholder="请输入套餐名或商品编码.... ">
	</div>
	<div class="col-xs-2" style="margin-left: 12px;margin-top: 7px;">
	    <input type="checkbox" id="seachCheckbo" name="seachCheckbo"   value="${seachCheckbo }" />只搜主商品
	</div>
	<div class="col-xs-2" style="margin-left: -80px;">
	    <input id="seachGroupId" name="seachGroupId" type="text" class="form-control input-sm" placeholder="请输入套餐id..."   value="${seachGroupId }"/>
	</div>
	
	<div class="col-xs-4" style="margin-top: 7px;">
	   <button type="button" class="btn btn-info   btn-xs margin3"  style="width:70px;" onclick="chang('1')" >搜索</button>
 	   <button type="button" class="btn btn-info   btn-xs margin3"  id="productBtn"  style="width:70px;">高级</button>
	</div>
  </div>
</div>		
		
<div class="right_box">


  


  <div class="right_box2"  id="productPanel" style="display:none;width: 100%">

  <div class="right_inline">
  <div class="right_title"><strong>套餐</strong></div>
   <div class="right_title_min">商品类型</div>
  <div class="right_input"  style="width:90px; ">
    <select id="seachGoodsType" name="seachGoodsType" class="form-control input-sm" >
    <option value="" >不限</option>
    <c:forEach items="${listgoods }" var="item">
    <option value="${item.id }" ${seachGoodsType == item.id? 'selected=selected':'' }>${item.attname }</option>
    </c:forEach>
    </select>
  </div>
  <div class="right_title_min">套餐类型</div>
  <div class="right_input" style="width:90px; ">
    <select id="seachGroupType" name="seachGroupType" class="form-control input-sm" >
     <option value="">不限</option>
     <option value="1" ${"1"==seachGroupType?'selected="selected"':''}>单商品套餐</option>
     <option value="2" ${"2"==seachGroupType?'selected="selected"':''}>多商品套餐</option>
    </select>
  </div>
  <div class="right_title_min">是否可销售</div>
  <div class="right_input" style="width:90px; ">
    <select id="seachSalesStatus" name="seachSalesStatus" class="form-control input-sm" >
     <option value="">全部</option>
     <option value="1" ${"1"==seachSalesStatus?'selected="selected"':''}>可销售</option>
     <option value="2" ${"2"==seachSalesStatus?'selected="selected"':''}>不可销售</option>
    </select>
  </div>
  <div class="right_title_min">质检审核</div>
   <div class="col-xs-3">
	     <select id="seachGroupStatus" name="seachGroupStatus" class="form-control input-sm" >
		     <option value="">全部</option>
		     <option value="0" ${"0"==seachGroupStatus?'selected="selected"':''}>未质检</option>
		     <option value="1" ${"1"==seachGroupStatus?'selected="selected"':''}>质检通过</option>
		     <option value="2" ${"2"==seachGroupStatus?'selected="selected"':''}>质检未通过</option>
    	 </select>
	</div>
</div>



<div class="right_inline">
 <div class="right_title"><strong>通用</strong></div>
<div class="right_input" style="width:110px">
<input id="seachGroupNamet" name="seachGroupNamet" type="text" class="form-control input-sm" placeholder="套餐名包含"   value="${seachGroupNamet }"/>
</div>
<div class="right_input" style="width:110px"><input name="seachMainGoodsName" type="text" class="form-control input-sm" value="${seachMainGoodsName }"  placeholder="商品名包含" /></div>
<div class="right_input" style="width:110px"><input name="seachInteriorCode" type="text" class="form-control input-sm" value="${seachInteriorCode }" placeholder="编码"/></div>
<div class="right_input" style="width:110px">
<input name="seachLabel" type="text" class="form-control input-sm" value="${seachLabel }" placeholder="分类标签"/>
</div>
<div class="right_input" style="width:110px">
<input name="seachBrand" type="text" class="form-control input-sm"  value="${seachBrand }" placeholder="品牌包含"/>
</div>
<div class="right_title_min">(多规格套餐)</div>
 <div class="right_input" >
    <select name="select" class="form-control input-sm"  style="width:100px;" >
      <option>所有套餐</option>
      <option>母套餐（含多个子套餐）</option>
      <option>单品母套餐（含同模版）</option>
      
    </select>
  </div>
 </div>
<div class="right_inline">
 <div class="right_title"><strong>销售用</strong></div>
 <div class="right_title_min">套餐生成时间</div>
<div class="right_input" style="width:150px;">
    <select name="seachDate" class="form-control input-sm" >
      <option>全部</option>
      <option value="1" ${"1"==seachDate?'selected="selected"':''}>24小时内</option>
      <option value="2" ${"2"==seachDate?'selected="selected"':''}>72小时内</option>
      <option value="3" ${"3"==seachDate?'selected="selected"':''}>一周内</option>
      <option value="4" ${"4"==seachDate?'selected="selected"':''}>30天内</option>
    </select>
  </div>
  <div class="right_title_min">套餐售价范围</div>
<div class="right_input" style="width:45px">
<input type="text" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" id="starjia" name="starjia" value="${starjia }" class="form-control input-sm"   />
</div>
 <div class="right_title_min">到</div>
<div class="right_input" style="width:45px">
<input type="text" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" id="endjia" name="endjia" value="${endjia }" class="form-control input-sm" />
</div>
 <div class="right_title_min">是否有图片</div>
<div class="right_input" style="width:100px;">
    <select name="seachincludeImages" class="form-control input-sm" >
     <option value="">全部</option>
     <option value="0" ${"0"==seachincludeImages?'selected="selected"':''}>无图片</option>
     <option value="1" ${"1"==seachincludeImages?'selected="selected"':''}>有图片</option>
    </select>
  </div>
 
 </div>
 <div class="right_inline">
<div class="right_title">&nbsp;</div>
<div id="qiandfl">
						<div class="right_title_min">前端分类一级</div>
		<div class="right_input" style="width:150px; ">
<select id="seachqyij" name="seachqyij" onChange="qyiji()" class="form-control input-sm" >
<option value="0">请选择</option>
   <c:forEach items="${listq}" var="item">
   <option value="${item.id }" ${seachqyij eq item.id? 'selected=selected':'' }>${item.className }</option>
   </c:forEach>
</select></div>
<div class="right_title_min">二级</div>
<div class="right_input" style="width:150px;">
<select id="seachqerj" name="seachqerj"  onchange="qerji()" class="form-control input-sm" >
  <option value="0">请选择</option>
</select>
</div>
<div class="right_title_min" >三级</div><div class="right_input input-sm" style="width:150px;">
<select id="seachqsanj" name="seachqsanj" onChange="qsanji()" class="form-control input-sm" >
  <option value="0">请选择</option>
</select></div></div>
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
 <div class="right_inline">
 <div class="right_title"><strong>审核用</strong></div>
 <div class="right_title_min" style="width:82px;">处方类型 </div>
 <div class="right_input" style="width:150px;">
    <select name="seachPrescriptionType" class="form-control input-sm" >
    <option value="">请选择</option>
    <c:forEach items="${listchu }" var="item">
    <option value="${item.id }" ${seachPrescriptionType == item.id? 'selected=selected':'' }>${item.attname}</option>
    </c:forEach>   
      
    </select>
  </div>
  <div class="right_title_min">是否含麻</div>
  <div class="right_input" style="width:125px;">
    <select name="seachSfHanma" class="form-control input-sm" >
     <option value="">全部</option>
     <option value="1" ${"1"==seachSfHanma?'selected="selected"':''}>是</option>
     <option value="2" ${"2"==seachSfHanma?'selected="selected"':''}>否</option>
    </select>
  </div>
  <div class="right_title_min">是否打胎</div>
  <div class="right_input" style="width:123px;">
    <select name="seachSfDatai" class="form-control input-sm" >
     <option value="">全部</option>
     <option value="1" ${"1"==seachSfDatai?'selected="selected"':''}>是</option>
     <option value="2" ${"2"==seachSfDatai?'selected="selected"':''}>否</option>
    </select>
  </div>
  </div><div class="right_inline" >
 <div class="right_title">&nbsp;</div>
<div id="zhijfl">
						<div class="right_title_min">质检分类一级</div>
		<div class="right_input" style="width:150px; ">
<select id="yij" name="yij" onChange="yiji()"  class="form-control input-sm" >
  <option value="0">请选择</option>
  <c:forEach items="${lista}" var="item">
  <option value="${item.id }" ${yij eq item.id? 'selected=selected':'' }>${item.className }</option>
  </c:forEach>
</select></div>
<div class="right_title_min">二级</div>
<div class="right_input" style="width:150px; ">
<select id="erj" name="erj"  onchange="erji()" class="form-control input-sm" >
 <option value="0">请选择</option>
</select>
</div>
<div class="right_title_min">三级</div><div class="right_input" style="width:150px; ">
<select id="sanj" name="sanj" onChange="sanji()" class="form-control input-sm" >
  <option value="0">请选择</option>
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
   <div class="right_inline"><button type="button" onClick="gaojichang('2')" class="btn btn-info  ">高级搜索</button></div>
   <hr style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=10 />
</div>
</div>
 <div class="right_box">
 <div class="right_box2">
<div class="right_title"></div>
	<div class="right_title_min"></div>
	
 </div>
 <div class="right_inline" >
 <div class="right_input" style="float:right">
 <sec:authorize ifAnyGranted="/goodsGroup.do?method=pltcscfbSave,/goodsGroup.do" >
   <button type="button" onclick="pltcscfbSave()" class="btn btn-info   btn-xs margin3" >批量套餐生成副本(质检通过)</button>
 </sec:authorize>
 <sec:authorize ifAnyGranted="/goodsGroup.do?method=alltcscfbSave,/goodsGroup.do" >
  <button type="button" onclick="alltcscfbSave()" class="btn btn-info   btn-xs margin3" >全部套餐生成副本(质检通过)</button>
 </sec:authorize>
平台
 <select id="salesPlatform" name="salesPlatform"   >
  <c:forEach items="${listspform }" var="item">
    <option value="${item.id }" ${salesPlatform == item.id? 'selected=selected':'' }>${item.platformName }</option>
    </c:forEach>
</select>
<!--  
 <button type="button" onclick="shengheplall()" class="btn btn-info   btn-xs margin3" >全部质检通过</button>
 <button type="button" onclick="shengheall()" class="btn btn-info   btn-xs margin3" >批量质检通过</button>
 -->
 <div class="right_title_min"  ><label class="checkbox-inline" >
 <!-- 
 <input type="checkbox" id="selAll" onClick="selectAll();"  style="margin-top:9px;" >
   全选
   -->
</label>
   </div>
   <div class="right_title_min"  ><label class="checkbox-inline" >
   <!--
  <input type="checkbox" id="inlineCheckbox1" value="option1" style="margin-top:9px;" >
   所有页全选
   -->
</label>
   </div>
   
  
 </div><div class="right_inline"  >
 <table width="98%" border="0"  class="table table-striped table-bordered table-hover"  style="text-align:center"> 
  <tr>
    <td width="9%" ><label class="checkbox-inline">
    <input type="checkbox" name="checkAll" onClick="selAlls('checkgroup',event)" onClick="selectAll();"  style="vertical-align:middle; margin:-1px 0 0 0;">全选
    <!--  
 <input type="checkbox" name="checkAll" id="checkAll" onClick="setSelectAll();"/> 
-->
</label></td>
    <td width="4%">套餐ID</td>
    <td width="5%">套餐名</td>
    <td width="8%">含商品名</td>
    <td width="8%">主商品处方类型</td>
    <td width="8%">主商品规格</td>
    <td width="12%">主商品生产厂家</td>
    <td width="8%">质检状态</td>
    <td width="45%">操作</td>
    </tr>
    
    <c:forEach items="${grouplist}" var="item">
  <tr>
    <td><label class="checkbox-inline">
 <input type="checkbox" name="checkgroup" value="${item.id}">
</label></td>
    <td>${item.id }</td>
    <td>${item.groupName }</td>
    <td>${item.mainGoodsName }</td>
    <td>${item.attname }</td>
    <td>${item.specifications }</td>
    <td>${item.manufacturer }</td>
    <td>
    <c:if test="${item.groupStatus==0 }">
              未质检
    </c:if>
     <c:if test="${item.groupStatus==1 }">
              质检通过
    </c:if>
     <c:if test="${item.groupStatus==2 }">
              质检未通过
    </c:if>
    
    </td>
    <td style="text-align: left;width:45%;">
    <sec:authorize ifAnyGranted="/goodsGroup.do?method=toupdetailed,/goodsGroup.do" >
    	<button type="button" onClick="seedetailed('${item.id }','${item.goodsId }')" class="btn btn-success   btn-xs"   >查看</button>
    </sec:authorize>
    <sec:authorize ifAnyGranted="/goodsGroup.do?method=toupdate,/goodsGroup.do" >
    	<button type="button" onClick="tomodify('${item.id }','${item.goodsId }')" class="btn btn-success   btn-xs"   >编辑</button>
    </sec:authorize>
    <!--  
    <c:if test="${item.groupStatus==0 }">
              <button type="button" onClick="toshenghe('${item.id }','${item.goodsId }')" class="btn btn-success   btn-xs"   >质检</button>
    </c:if>
     <c:if test="${item.groupStatus==2 }">
             <button type="button" onClick="toshenghe('${item.id }','${item.goodsId }')" class="btn btn-success   btn-xs"   >质检</button>
    </c:if>
    -->
     <c:if test="${item.groupStatus==1 }">
     	<sec:authorize ifAnyGranted="/goodsGroup.do?method=totcscfb,/goodsGroup.do" >
    		<button type="button" onclick="tomodifyfb('${item.id }','${item.goodsId }')"  class="btn btn-success   btn-xs"   >套餐生成副本</button>
    	</sec:authorize>
     </c:if>
     <sec:authorize ifAnyGranted="/goodsImage.do?method=tcload,/goodsImage.do" >
      	<button type="button" onclick="totupian('${item.id }')" class="btn btn-primary  btn-xs"  >编辑图片</button>
      </sec:authorize>
     
      <sec:authorize ifAnyGranted="/goodsp.do?method=toCopyTheGoodsToSetmeal,/goodsp.do" >
      	<button type="button" onclick="copymaintotc('${item.id }','${item.goodsId }')" class="btn btn-primary  btn-xs"  >拷贝主商品</button>
      </sec:authorize>
      
      <sec:authorize ifAnyGranted="/goodsGroup.do?method=delGoodsGroup,/goodsGroup.do" >
      	 <button type="button" onClick="delGoodsGroup('${item.id }')" class="btn btn-success   btn-xs"   >删除</button>
      </sec:authorize>
      <!--  
      <a href="#">&nbsp;&nbsp;同宗管理</a></td>
      -->
      
  </tr>
  </c:forEach>
</table>
<ul class="pager">
<%@ include file="/WEB-INF/view/common/page.jspf" %>
</ul>
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
