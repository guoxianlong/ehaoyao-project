<%@ page contentType="text/html;charset=UTF-8" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">	
<link href="<%=request.getContextPath()%>/media/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.4.2.min.js"></script>

<title>无标题文档</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></head>
<script language="javascript">
  //查询分页跳转
      function gotoPage(pageno)
      {
        document.getElementById('pageno').value = pageno;
        document.form.submit();
      }
  //搜索
    function chang(ba){
    
        var seachshzt=document.getElementById("seachshzt").value;
    	
    	var seachgoodsName=document.getElementById("seachgoodsName").value;
    	
    	document.form.action = "goodsp.do?method=getGoodsList&seachgoodsName="+seachgoodsName+"&seachshzt="+seachshzt+"&ba="+ba;
		document.form.submit();
    }
  //高级搜索
    function gaojichang(ba){
      yiji();
      qyiji();
      var yijid=document.getElementById("yij").value;//药监第一级选中的值
      var qyijid=document.getElementById("qyij").value;//药监第一级选中的值
	  var shangpin=document.getElementById("shangpin").value;//商品名包含
	  var seachinteriorCode=document.getElementById("seachinteriorCode").value;//编号包含
	  var seachbrand=document.getElementById("seachbrand").value;//品牌包含
	  var starjia=document.getElementById("starjia").value;//售价开始值
	  var endjia=document.getElementById("endjia").value;//售价最大值
	  document.form.action = "goodsp.do?method=getGoodsList&shangpin="+shangpin+"&seachinteriorCode="+seachinteriorCode+"&seachbrand="+seachbrand+"&starjia="+starjia+"&endjia="+endjia+"&yijid="+yijid+"&qyijid="+qyijid+"&ba="+ba;
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
        
  function shengh(){
	  var shzt=document.getElementById("shzt").value;
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
  
  //编辑跳转
  function tomodify(id,starus){
	  document.form.action = "goodsp.do?id="+id+"&starus="+starus;
	  document.form.submit();
  }
  //审核跳转
  function toshenghe(id){
	  document.form.action = "goodsp.do?method=shenghe&id="+id;
	  document.form.submit();
  }
</script>
<body >
<form action="goodsp.do?method=getGoodsList" name="form" method="post">
		<input type="hidden" id="pageno" name="pageno" value="${pageno}"/>
		<input type="hidden" id="pageSize" name="pageSize" value="${pageSize}"/>
		
		<input type="hidden" id="yijid" name="yijid" value="${yijid}"/>
		<input type="hidden" id="erjid" name="erjid" value="${erjid}"/>
		<input type="hidden" id="sanjid" name="sanjid" value="${sanjid}"/>
		
		<input type="hidden" id="qyijid" name="qyijid" value="${qyijid}"/>
		<input type="hidden" id="qerjid" name="qerjid" value="${qerjid}"/>
		<input type="hidden" id="qsanjid" name="qsanjid" value="${qsanjid}"/>
		<input type="hidden" id="ba" name="ba" value="${ba}"/>
<div class="right_box" style="width:950px">


<div class="right_h"><h3>商品查询/编辑</h3></div>



<div class="right_inline">
						<div class="right_title">内部编码</div>
						<div class="right_input" style="width:350px;"><input  id="seachgoodsName" name="seachgoodsName" value="${seachgoodsName}" type="text" class="form-control input-sm"   ></div>

 <div class="right_input" style="width:110px;">
<select id="seachshzt" name="seachshzt" onChange="shengh()" class="form-control input-sm">
        <option value="">全部</option>
        <option value="0" ${"0"==seachshzt?'selected="selected"':''}>未审核</option>
        <option value="1" ${"1"==seachshzt?'selected="selected"':''}>已审核</option>
</select></div>
 <div class="right_input" style="width:110px;">
 <select class="form-control input-sm">
  <option value="">全部</option>
  <option >未质检</option>
  <option >未审核</option>
</select></div>

<button type="button" onClick="chang('1')" class="btn btn-info  btn-sm margin3 ">搜索</button>
 <button type="button" class="btn btn-info  btn-sm margin3 " id="productBtn">高级</button>

</div>
 <div class="right_box2"  id="productPanel">
<div class="right_inline">
      <div class="right_title"><strong>套餐</strong></div>
						<div class="right_title_min">是否医药类</div>
						<div class="right_input" >
						 <select class="form-control input-sm" >
  <option>不限</option>
  <option>医药类</option>
  <option>非医药类</option>
</select></div>
<div class="right_title_min">商品件数</div>
 <div class="right_input" >
						 <select class="form-control input-sm" >
  <option>不限</option>
  <option>1</option>
  <option>2</option>
  <option>3</option>
  <option>4</option>
  <option>5</option>
  <option>大于5件</option>
</select>
</div>
<div class="right_title_min">是否可销售
</div>
 <div class="right_input" >
						 <select class="form-control input-sm" >
  <option>不限</option>
  <option>可销售</option>
  <option>不可销售</option>
</select></div>
    <div class="right_title_min">平台</div>
  <div class="right_input" >
    <select name="select" class="form-control input-sm" >
      <option>全部</option>
      <option>官网</option>
      <option>微信</option>
      <option>天猫</option>
      <option>京东</option>
    </select>
  </div>
</div>

<div class="right_inline">
	<div class="right_title"><strong>通用</strong></div>	
	<div class="right_input" style="width:40px">商品名</div>
	<div class="right_input" style="width:110px"><input  id="shangpin" name="shangpin" type="text" class="form-control input-sm"      value="${shangpin }"></div>
	<div class="right_input" style="width:40px">编码</div>
	<div class="right_input" style="width:110px"><input  id="seachinteriorCode" name="seachinteriorCode" type="text" class="form-control input-sm"      value="${seachinteriorCode }"> </div>
	<div class="right_input" style="width:40px">品牌</div>
	<div class="right_input" style="width:110px"><input  id="seachbrand" name="seachbrand" type="text" class="form-control  input-sm"   value="${seachbrand }"></div>
						<!--  
						<input name="text3" type="text" class="form-control" style="width:20%;">
						-->
   </div>

<div class="right_inline"> <div class="right_title"><strong>销售用</strong></div>
						<div class="right_title_min">商品生成时间</div>
						<div class="right_input" >
						 <select class="form-control input-sm" >
  <option>不限</option>
  <option>3</option>
  <option>4</option>
  <option>5</option>
</select></div>
<div class="right_title_min">基本售价范围</div>

<div class="right_input" style="width:60px;"><input  type="text" id="starjia" name="starjia" value="${starjia }" class="form-control input-sm"  style="width:60px;" ></div>
<div class="right_title_min">到</div>
<div class="right_input" style="width:60px"><span class="right_input" style="width:60px">
  <input  type="text" id="endjia" name="endjia" value="${endjia }" class="form-control input-sm" style="width:60px;" >
</span></div>

</div>
<div class="right_inline" >
 <div class="right_title">&nbsp;</div>

						<div class="right_title_min">药监分类一级</div>
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
</div><div class="right_inline">
<div class="right_title">&nbsp;</div>
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
</select></div></div>
<div class="right_inline"><button type="button" onClick="gaojichang('2')" class="btn btn-info  ">高级搜索</button></div>

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
  <input type="checkbox"  id="selAll" onClick="selectAll();"  style="margin-top:9px;"> 全选

</label></div>
 <div class="right_title_min"  >
<label class="checkbox-inline">
  <input type="checkbox" id="inlineCheckbox2" value="option2" style="margin-top:9px;">所有页全选

</label></div>
 <div class="right_input" style="float:right">
<button type="button" class="btn btn-info btn-sm margin3 " >批量下架</button>
 <button type="button" class="btn btn-info  btn-sm margin3 "   >批量审核通过</button>
<button type="button" class="btn btn-info btn-sm margin3 "  >批量生成单品套餐(限已质检)</button>
</div>

</div>

<div class="right_inline"  >

<table width="950" border="0"  class="table table-bordered"> 
  <tr>
    <td width="29"><label class="checkbox-inline">
  <input type="checkbox"  name="checkAll" id="checkAll" onClick="setSelectAll();" value="option1"> 

</label></td>
                                        <th width="50">内部编码</th>
										<th width="100">商品名</th>
										<th width="100">通用名</th>
										<th width="100">处方类型</th>
										<th width="70">审核状态</th>
										<th width="70">基本价格</th>
										<th width="100">生产厂家</th>
										<th width="200">操作</th>
    </tr>
      <c:forEach items="${yhuserList}" var="item">
  <tr>
    <td><label class="checkbox-inline">
    <input type="checkbox"  name="checkAll" id="checkAll" onClick="setSelectAll();" value="option1"> 
     </label></td>
       <td>${item.interiorCode }</td>
	   <td>${item.goodsName }</td>
	   <td>${item.commonName }</td>
	   <td>${item.prescriptionType }</td>
	   <td><c:if test="${item.examineStatus==0 }">未审核</c:if>
	         <c:if test="${item.examineStatus==1 }">审核通过</c:if>
	         <c:if test="${item.examineStatus==2 }">审核未通过</c:if>         
	   </td>
	   <td>${item.retailPrice }</td>
	   <td>${item.manufacturer }</td>
    <td><button type="button" class="btn btn-info btn-xs" >查看</button>
        <button type="button" onClick="tomodify('${item.id }','1')" class="btn btn-success   btn-xs"   >编辑</button>
      <button type="button" class="btn btn-primary  btn-xs"  >编辑图片</button>
        <c:if test="${item.examineStatus==0 }">
      <button type="button" onClick="toshenghe('${item.id }')" class="btn btn-warning  btn-xs"  >审核</button>
            </c:if>
               <c:if test="${item.examineStatus==2 }">
      <button type="button" onClick="toshenghe('${item.id }')" class="btn btn-warning  btn-xs"  >审核</button>
            </c:if>
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
</form>
</body>
、

     
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
	
	<script language="javascript"> 

//选中全选按钮，下面的checkbox全部选中 
var selAll = document.getElementById("selAll"); 
function selectAll() 
{ 
  var obj = document.getElementsByName("checkAll"); 
  if(document.getElementById("selAll").checked == false) 
  { 
  for(var i=0; i<obj.length; i++) 
  { 
    obj[i].checked=false; 
  } 
  }else 
  { 
  for(var i=0; i<obj.length; i++) 
  {	  
    obj[i].checked=true; 
  }	
  } 
  
} 

//当选中所有的时候，全选按钮会勾上 
function setSelectAll() 
{ 
var obj=document.getElementsByName("checkAll"); 
var count = obj.length; 
var selectCount = 0; 

for(var i = 0; i < count; i++) 
{ 
if(obj[i].checked == true) 
{ 
selectCount++;	
} 
} 
if(count == selectCount) 
{	
document.all.selAll.checked = true; 
} 
else 
{ 
document.all.selAll.checked = false; 
} 
} 

//反选按钮 
function inverse() { 
var checkboxs=document.getElementsByName("checkAll"); 
for (var i=0;i<checkboxs.length;i++) { 
  var e=checkboxs[i]; 
  e.checked=!e.checked; 
  setSelectAll(); 
} 
} 

</script> 
</html>
