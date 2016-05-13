<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
<!-- 加载CSS样式文件 -->  
<style type="text/css">
	a,img {
		border: 0;
	}
	
	/* select */
	.select {
		padding: 5px 10px;
		border: #ddd 1px solid;
		border-radius: 4px;
		font-size: 12px
	}
	
	.select li {
		list-style: none;
		padding: 10px 0 5px 100px
	}
	
	.select .select-list {
		border-bottom: #eee 1px dashed
	}
	
	.select dl {
		zoom: 1;
		position: relative;
		line-height: 24px;
	}
	
	.select dl:after {
		content: " ";
		display: block;
		clear: both;
		height: 0;
		overflow: hidden
	}
	
	.select dt {
		width: 100px;
		margin-bottom: 5px;
		position: absolute;
		top: 0;
		left: -100px;
		text-align: right;
		color: #666;
		height: 24px;
		line-height: 24px
	}
	
	.select dd {
		float: left;
		display: inline;
		margin: 0 0 5px 5px;
	}
	
	.select a {
		display: inline-block;
		white-space: nowrap;
		height: 24px;
		padding: 0 10px;
		text-decoration: none;
		color: #039;
		border-radius: 2px;
	}
	
	.select a:hover {
		color: #f60;
		background-color: #f3edc2
	}
	
	.select .selected a {
		color: #fff;
		background-color: #f60
	}
	
	.select-result dt {
		font-weight: bold
	}
	
	.select-no {
		color: #999
	}
	
	.select .select-result a {
		padding-right: 20px;
		background: #f60 url("http://localhost:8080/operation_center/img/close.gif") right 9px no-repeat
	}
	
	.select .select-result a:hover {
		background-position: right -15px
	}
</style>

<!-- 加载javascript文件 -->
<script type="text/javascript">
   $(document).ready(function(){
	   
	   if ($(".select-result dd").length > 1) {
	       $(".select-no").hide();
	   } else {
		   $(".select-no").show();
	   }
	   
       $("#select1 dd").click(function () {
    	   var ids = $(this).attr("id");
    	   var detailVal = $("#detailId").val();
    	   if(detailVal != null && detailVal !="" && detailVal != undefined){
    		   var arr=new Array();
    	       arr=detailVal.split(',');
    	       for(var i=0;i<arr.length;i++){
    	    	   if(arr[i] != ids){
    	    		   continue;
    	    	   } else {
    	    		   alert("标签已存在");
    	    		   return;
    	    	   }
    	       }        	   
		       detailVal+= ",";
    	   }
    	   $("#detailId").val(detailVal+ids);
    	   $(".select-result dl").append("<dd id='view"+ids+"' class='selected' onclick='removeSelect("+ids+")'>"+$(this).html()+"</dd>");
       });
       
	   $(".select dd").bind("click", function () {
	       if ($(".select-result dd").length > 1) {
		       $(".select-no").hide();
		   } else {
			   $(".select-no").show();
		   }
	   });

    });
   
    function removeSelect(obj){
        var detailIds = $("#detailId").val();
        var arr=new Array();
        arr=detailIds.split(',');
        var tt = '';
        for(var i=0;i<arr.length;i++){
        	if(arr[i]!=obj){
        		tt+=arr[i];
        		tt+=',';
        	}
        }
        $("#detailId").val(tt);
        $("#view"+obj).remove();
    }
    
    function saveHealth(){
    	var data = {};
    	data.detailId = $("#detailId").val();
    	data.tel = $("#health_tel").val();
    	var url = "${healAction}?method=saveHealthRecords";
    	$.ajax({
    		type: "POST",
            url: url,
            dataType: "text",
            data: data,
            success: function(message){
            	alert("健康关键字保存成功");            		
            		if(message == "1"){
            	}
            },error:function(){
           	    alert("健康关键字保存失败");
            }
		});
    }
  </script>

<div class="panel-body">
    <div style="height: 30px;">
		<input type="hidden" id="detailId" value="${pid}" name="detailId">
		<input type="hidden" id="health_tel" value="${tel}" name="tel">
		<div style="float:right">
		    <button class="btn btn-info" onclick="saveHealth();">保存</button>		
		</div>
	</div>
	<p>
	<ul class="select">
		<li class="select-result">
			<dl>
				<dt>已选条件：</dt>
				<dd class="select-no">暂时没有选择过滤条件</dd>
				<c:forEach items="${tempDetailList}" var="temp">
				    <dd id="view${temp.id}" class="selected" onclick='removeSelect("${temp.id}")'>
						<a href="#" id="item1">${temp.keyword}</a>
				    </dd>
				</c:forEach>
			</dl>
		</li>
		<c:forEach items="${healthList}" var="item">
			<li class="select-list">
				<dl id="select1">
					<dt>${item.className}</dt>
					<c:forEach items="${item.healthDetail}" var="items">
						<dd id="${items.id}">
							<a href="#" id="item${items.id}">${items.keyword}</a>
						</dd>
					</c:forEach>
				</dl>
			</li>
		</c:forEach>
	</ul>
</div>