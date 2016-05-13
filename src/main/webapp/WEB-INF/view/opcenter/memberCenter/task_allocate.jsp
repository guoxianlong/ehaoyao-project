<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1 maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>任务分配</title>
    
    <!-- 加载CSS样式文件 -->
    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-theme.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-select.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-switch.min.css" rel="stylesheet" />
	<link href="<%=request.getContextPath()%>/css/city.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
    <style type="text/css" mce_bogus="1">
		th {
			text-align: center;
		}
		table th  
        {  
            white-space: nowrap; 
             
        } 
        #cusTab td  
        {  
            /* white-space: nowrap;  */
            word-wrap: break-word
        }  
        #cusTab{
        	table-layout:fixed;word-wrap:break-word;word-break:break-all
        }
		input.item_input {height: 30px;}
		.item_title {text-align: right;width: 80px;padding-top: 5px;margin-left: -10px;}
		.item_title1 {text-align: left;width: 280px;padding-top: 5px;}
		.sear_btn{margin-left: 5px; padding: 0 5px; height: 30px;}
		.select_set + div button.selectpicker{height:30px;padding-top:0;padding-bottom:0;}
		.tr_inline{width:100%;margin-bottom:7px;}
		#choiceId{float:left; width:250px;height:405px;min-height:75px;max-height: 405px;overflow-y: auto; margin-left:8px;padding: 5px;border: 1px solid #DDD;border-radius:4px;}
		.div_inline{float:left;width:auto;padding:2px 5px;margin:3px;border-radius:4px;border:1px solid #D2E0F0;background-color:#D2F0FF;}
		.span_text{color:#3179A0;}
		.span_del{color:#FF0404;cursor: pointer;margin-left:5px;}
		#choiceId1{float:left; width:280px;height:80px;padding: 5px;border: 1px solid #DDD;border-radius:4px;}
		.table_head{width: 100%;height: 27px;background-color: #51ABD9;border-top-left-radius:0.5em;border-top-right-radius:0.5em;}
	</style>
    <!-- 加载javascript文件 -->
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.3.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap-select.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap-switch.min.js"></script>
    
    <script src="<%=request.getContextPath()%>/js/bootstrap/respond.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/WdatePicker/WdatePicker.js"></script>
    <script src="<%=request.getContextPath()%>/js/mask.js"></script>
    
    
    <script>
    	var initHtml="<option value=''>--请选择--</option>";
    	String.prototype.trim = function(){
			return this.replace(/(^\s*)|(\s*$)/g, "");
		};
	    $(document).ready(function (){
			getNextSelect(1);
			$('.selectpicker').selectpicker();
	    });
	    //获取下一级列表数据
		function getNextSelect(level){
			var parentName;
			switch(level){
				case(1):{
					parentName="null";
					break;
				};
				case(2):{
					parentName=$("#oneLevel").find("option:selected").text();
					break;
				};
				case(3):{
					parentName=$("#twoLevel").find("option:selected").text();
					break;
				};
			};
			if(parentName!="--请选择--"){
				//发送请求
				$.ajax({
				type: "POST",
				url: "<%=request.getContextPath()%>/communicateReport.do?method=getSelectList",
		        dataType: "text",
		        data: {parentName:parentName},
		        success: function(data){
		        	var arr= new Array();
					arr=data.split(",");
		        	switch(level){
					case(1):{
							$("#oneLevel").empty();
							var proHtml=initHtml;
							for(i=0;i<arr.length;i++){
								proHtml+=("<option value='"+arr[i]+"' name='${"+arr[i]+"== vo.oneLevel}'>"+arr[i]+"</option>");
							}
							$("#oneLevel").html(proHtml);
							$("#twoLevel").html(initHtml);
							$("#threeLevel").html(initHtml);
							$("#oneLevel").selectpicker('val','${vo.oneLevel}');
							$("#oneLevel").selectpicker('refresh');
							$('.selectpicker').selectpicker('refresh');
							break;
					};
					case(2):{
							$("#twoLevel").empty();
							$("#twoLevel").append("<option value=''>--请选择--</option>");
							for(i=0;i<arr.length;i++){
									$("#twoLevel").append("<option value='"+arr[i]+"'>"+arr[i]+"</option>");
								}
							$("#threeLevel").html(initHtml);
							$("#twoLevel").selectpicker('val','${vo.twoLevel}');
							$("#twoLevel").selectpicker('refresh');
							$('.selectpicker').selectpicker('refresh');
							break;
					};
					case(3):{
							$("#threeLevel").empty();
							$("#threeLevel").append("<option value=''>--请选择--</option>");
							for(i=0;i<arr.length;i++){
								$("#threeLevel").append("<option value='"+arr[i]+"'>"+arr[i]+"</option>");
							}
							$("#threeLevel").selectpicker('val','${vo.threeLevel}');
							$("#threeLevel").selectpicker('refresh');
							$('.selectpicker').selectpicker('refresh');
							break;
					};
		       	 };
		        }
			});
			}
		}
	    //查询按钮
	    function search(){
	    	var startDate = $("#startDate").val().trim();
	    	var endDate = $("#endDate").val().trim();
	    	var oneLevel = $("#oneLevel").val().trim();
	    	one_level = oneLevel;
	    	if(startDate== ''){
	    		alert("请选择开始日期！");
	    		//$("#startDate").attr("value",startDate);
	    		return;
	    	}
	    	if(endDate == ''){
	    		alert("请选择结束日期！");
	    		return;
	    	}
	    	$("#allRecord").attr("checked",false);
	    	gotoPage(1);
	    }
	    //翻页
	    function gotoPage(pageno){
	    	var check = document.getElementById("allRecord").checked;
	    	$("[name=pageno]").val(pageno);
	    	$("#dataForm").attr("action","<%=request.getContextPath()%>/taskAllocate.do?method=getInfo&isCheck="+check);
	    	$("#dataForm").submit();
	    	<%-- var pageSize = $("#curPageSize").val().trim();
	    	var totalRecords = $("#totalRecords").val().trim();
	    	var startDate = $("#startDate").val().trim();
	    	var endDate = $("#endDate").val().trim();
	    	var oneLevel = $("#oneLevel").val().trim();
	    	var twoLevel = $("#twoLevel").val().trim();
	    	var threeLevel = $("#threeLevel").val().trim();
	    	$.ajax({
	    		type: "POST",
	            url: "<%=request.getContextPath()%>/taskAllocate.do?method=getInfo",
	            dataType: "html",
	            data: {pageno:pageno,pageSize:pageSize,recTotal:totalRecords,startDate:startDate,endDate:endDate,
	            	oneLevel:oneLevel,twoLevel:twoLevel,threeLevel:threeLevel,isCheck:check},
	            success: function(data){
	           		$("#base").parent().html(data);
	            },
	            error: function(){
	            	$("#base").parent().html("");
	            }
	    	});	 --%>
	    }
	    function gotoPageSize(pageSize){
	    	$("#curPageSize").val(pageSize);
	    	gotoPage($("#curPage").val());
	    }
	    //查询
	    function resetPageNo(){
	    	$("#curPage").val(1);
	    }
	    function openUserWin(){
	    	var tels = onTelSel();
	    	if(tels == ""){
	    		alert("请选择需要分配客服专员的客户！");
	    		return;
	    	}
	    	queue = document.getElementById("allRecord").checked;
	    	allot();
	    	$("#choiceId .div_inline").remove();
	    	$("#serSelAll").attr("checked",false);
	    	$('#myModal').modal('show');
	    }
	    //查询客服
	    function allot(){
	    	var userName =  $("#userName").val().trim();
	    	$.ajax({
	    		type: "POST",
	            url: "<%=request.getContextPath()%>/taskAllocate.do?method=getUserList",
	            dataType: "json",
	            data: {userName:userName},
	            success: function(data){
	            	var item = "";
	       			var t = document.getElementById("kf_body"); //获取展示数据的表格 
	       			while (t.rows.length != 0) { 
	       				t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除 style='display:none'
	       			} 
	                $.each(data.userList,function(i,result){  
              			var isExist = false;
	    	    		$("#choiceId .div_inline").each(function(){
	    	    			var idEle = $(this).find("input[name='itemId']");
	    	    			if(idEle.val()==result.userName){
	    	    				isExist = true;
	    	    				return false;
	    	    			}
	    	    		});
	                	item += "<tr>" + "<td align='center' width='10%'>";
	                	if(isExist==true){
	                		item += "<input type='checkbox' name='checkGroup' value='"+result.name+"' checked='checked'/></td>";
	        			}else{
	        				item += "<input type='checkbox' name='checkGroup' value='"+result.name+"' /></td>";
	        			}
	 			 		item += "<td width='40%'>"+result.userName+"</td>" +
	 				    "<td width='50%'>"+result.name+"</td></tr>";
	                });
	       			$("#kf_body").append(item);
	       			//重新绑定事件，复选框选中商品
	       			$("#kf_body input[name='checkGroup']").click(function(){
	       				userCheck_click($(this));
	       			});
	       			/* var tabHeight = $('#kf_body').outerHeight() + 75;
	    	    	$("#choiceId").css("height",tabHeight); */
	            },
	            error: function(){
	            	alert("出现异常，请联系管理员！");
	            }
	    	});
	    }
	  //点击复选框，选择客服
	  	function userCheck_click(thisobj){
	    	var thistr = thisobj.parent("td").parent("tr");
	    	var uid = thistr.find("td:eq(1)").text();
	    	var isCheck = thisobj.prop("checked");
	    	if(isCheck==true){
	    		var uname = thistr.find("td:eq(2)").text();
	    		var isExist = false;
	    		$("#choiceId .div_inline").each(function(){
	    			var idEle = $(this).find("input[name='itemId']");
	    			if(idEle.val()==uid){
	    				isExist = true;
	    				return false;
	    			}
	    		});
	    		if(!isExist){//不存在，添加
	    			//显示选中商品
	    			var proDiv = "<div class='div_inline'>" +
	    					"<input type='hidden' name='itemId' value='"+uid+"' />" +
	    					"<input type='hidden' name='itemName' value='"+uname+"' />" +
	    					"<span class='span_text' title='["+uid+"],"+uname+"'>"+uname+"</span>" +
	    					"<span class='span_del' title='删除'>&times;</span>" +
	    					"</div>";
	    			$("#choiceId .clearfix").before(proDiv);
	    			//关键词删除事件
	    			$("#choiceId .span_del:last").click(delKeywords);
	    		}
	    	}else{
		    	$("#serSelAll").attr("checked",false);
	    		$("#choiceId .div_inline").each(function(){
	    			var idEle = $(this).find("input[name='itemId']");
	    			if(idEle.val()==uid){
	    				//取消
	    				$(this).remove();
	    				return false;
	    			}
	    		});
	    	}
	    }
	    //删除关键词
	    function delKeywords(e){
	    	var uid = $(e.target).parent().find("input[name=itemId]").val();
	    	$(e.target).parent().remove();
	    	$("#kf_body tr").each(function(){
	    		var pid = $(this).find("td:eq(1)").text();
	    		if(pid==uid){
	    			$(this).find("td:eq(0)").find("input").prop('checked',false);
	    			return false;
	    		}
	    	});
	    }
	    //客服全选
	    function serSelAll(){
	    	var check = document.getElementById("serSelAll").checked;
	    	//var items = document.getElementsByName("checkGroup");
	    	var items = $("#kf_body input[name='checkGroup']");
	    	for(var i=0;i<items.length;i++){
	    		items[i].checked=check;
	    	}
	    	if(check==true){
	    		$("#kf_body tr").each(function(){
		    		var uid = $(this).find("td:eq(1)").text();
		    		var uname = $(this).find("td:eq(2)").text();
		    		var isExist = false;
		    		$("#choiceId .div_inline").each(function(){
		    			var idEle = $(this).find("input[name='itemId']");
		    			if(idEle.val()==uid){
		    				isExist = true;
		    				return false;
		    			}
		    		});
		    		if(!isExist){//不存在，添加
		    			//显示选中商品
		    			var proDiv = "<div class='div_inline'>" +
		    					"<input type='hidden' name='itemId' value='"+uid+"' />" +
		    					"<input type='hidden' name='itemName' value='"+uname+"' />" +
		    					"<span class='span_text' title='["+uid+"],"+uname+"'>"+uname+"</span>" +
		    					"<span class='span_del' title='删除'>&times;</span>" +
		    					"</div>";
		    			$("#choiceId .clearfix").before(proDiv);
		    			//关键词删除事件
		    			$("#choiceId .span_del:last").click(delKeywords);
		    		}
		    	});
	    	}else{
	    		$("#kf_body tr").each(function(){
		    		var uid = $(this).find("td:eq(1)").text();
		    		$("#choiceId .div_inline").each(function(){
		    			var idEle = $(this).find("input[name='itemId']");
		    			if(idEle.val()==uid){
		    				//取消
		    				$(this).remove();
		    				return false;
		    			}
		    		});
	    		});
	    	}
	    }
	    //选中会员查询结果的所有记录
	    function allRecordSel(){
	    	var check = document.getElementById("allRecord").checked;
	    	document.getElementById("cusSelAll").checked = check;
	    	var items = document.getElementsByName("cusSel");
	    	for(var i=0;i<items.length;i++){
	    		items[i].checked=check;
	    	}
	    }
	    //会员信息本页全选
	    function cusSelAll(){
	    	var check = document.getElementById("cusSelAll").checked;
	    	var items = document.getElementsByName("cusSel");
	    	for(var i=0;i<items.length;i++){
	    		items[i].checked=check;
	    	}
	    }
	    //获取选中的手机号
		function onTelSel(){
			var tels = "";
			$("#kh_body tr").each(function(){
				if($(this).find("td:eq(0)").find("input[name='cusSel']").prop("checked")){
					var tel = $(this).find("td:eq(0)").find("input[name='cusSel']").val();
		    		var name = $(this).find("td:eq(1)").text();
		    		var province = $(this).find("td:eq(2)").text();
		    		var city = $(this).find("td:eq(3)").text();
		    		var country = $(this).find("td:eq(4)").text();
		    		var newCus = $(this).find("td:eq(6)").text();
		    		var service = $(this).find("td:eq(7)").text();
		    		tels += $.trim(tel) + "}&{" + $.trim(name) + "}&{" + $.trim(province)+ "}&{" + $.trim(city)+ "}&{" + $.trim(country) + "}&{" + $.trim(newCus) + "}&{" + $.trim(service)+ "{&, ";
				}
    		});
			return tels;
		}
	    //获取选中的客服
	    function onCusSel(){
	    	var cusIds = "";
	    	$("#choiceId").children(":gt(0)").each(function(){
	      		var mid = $(this).find("input[name='itemId']").val();
	      		if(mid!=null && mid.length>0){
	      			cusIds += $.trim(mid)+"{&,";
	      		}
	      	});
	    	return cusIds;
	    }
	    //会员信息全选
	    function cusSelAll(){
	    	var check = document.getElementById("cusSelAll").checked;
	    	var items = document.getElementsByName("cusSel");
	    	for(var i=0;i<items.length;i++){
	    		items[i].checked=check;
	    	}
	    }
	    //提交按钮点击
	    var queue = false;
	    function commit(){
	    	var taskDate = $("#taskDate").val().trim();
	    	if(taskDate == null || taskDate == ""){
	    		alert("请选择执行任务日期！");
	    		return;
	    	}
	    	var serIds = onCusSel();
	    	var cusIds =  onTelSel();
	    	if(serIds == ""){
	    		alert("请选择客服人员！");
	    		return;
	    	}
	    	$("body").mask("Loading , please waite...");
	    	var ask = $("#ask").val().trim();
	    	if(!queue){
	    		$.ajax({
		    		type: "POST",
		            url: "<%=request.getContextPath()%>/taskAllocate.do?method=allotTask",
		            dataType: "json",
		            data: {customers:cusIds,serIds:serIds,taskDate:taskDate,ask:ask},
		            success: function(data){
		            	if(data.message != null && data.message != ""){
		            		alert(data.message);
		            	}else{
		            		alert("分配客服成功！");
		            		gotoPage(1);
		            	}
		            	$("#body").unmask();
		            	$('#myModal').modal('hide');
		            },error: function(){
		            	$("#body").unmask();
		            	alert("分配任务失败，请联系管理员！");
		            }
		    	});
	    	}else{
	    		batch(serIds,taskDate,ask);
	    	}
	    }
	    //批量分配任务
	    function batch(serIds,taskDate,ask){
	    	var startDate = $("#startDate").val().trim();
	    	var endDate = $("#endDate").val().trim();
	    	$.ajax({
	    		type: "POST",
	            url: "<%=request.getContextPath()%>/taskAllocate.do?method=batchAllotTask",
	            dataType: "json",
	            data: {startDate:startDate,endDate:endDate,serIds:serIds,taskDate:taskDate,ask:ask},
	            success: function(data){
	            	if(data.message != null && data.message != ""){
	            		alert(data.message);
	            	}else{
	            		alert("分配客服成功！");
	            		gotoPage(1);
	            	}
	            	$("#body").unmask();
	            	$('#myModal').modal('hide');
	            },
	            error: function(){
	            	$("#body").unmask();
	            	alert("分配任务失败，请联系管理员！");
	            }
	    	});
	    }
    </script>
</head>
<body>
    <div id="base" style="height: 100%;">
	    <div class="panel panel-default">
	        <div class="panel-heading">
                <h5 class="panel-title" style="color:#31849b;">任务分配</h5>
            </div>
            <div class="panel-body">
		        <form class="well" method="post" id="dataForm">
					<input type="hidden" id="curPage" name="pageno" value="${pageno}"/>
					<input type="hidden" id="curPageSize" name="pageSize" value="${pageSize}"/>
					<input type="hidden" id="totalRecords" name="totalRecords" value="${recTotal}"/>
					<div class="tr_inline" style="margin-bottom:1px;">
						<div class="right_input item_title">订单日期：</div>
						<div class="right_input" style="width: 120px;">
							<input id="startDate" name="startDate" type="text" value="${vo.startDate}" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}',minDate:'#F{$dp.$D(\'endDate\',{M:-3,d:1})}'});" class="form-control item_input"/>
						</div>
						<div class="right_input">&nbsp;_&nbsp;</div>
						<div class="right_input" style="width: 120px;">
							<input id="endDate" name="endDate" type="text" value="${vo.endDate}" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'#F{$dp.$D(\'startDate\',{M:3,d:-1})}',})" class="form-control item_input"/>
						</div>
						<div class="right_input item_title">前端分类：</div>
						<div class="right_input" style="width: 120px;">
							<select id="oneLevel" name="oneLevel" class="selectpicker form-control" onchange="getNextSelect(2)";>
								<option value="">--请选择--</option>
							</select>
						</div>
						<div class="right_input">&nbsp;&nbsp;</div>
						<div class="right_input" style="width: 120px;">
							<select id="twoLevel" name="twoLevel" class="selectpicker form-control" onchange="getNextSelect(3)";>
								<option value="">--请选择--</option>
							</select>
						</div>
						<div class="right_input">&nbsp;&nbsp;</div>
						<div class="right_input" style="width: 120px;">
							<select id="threeLevel" name="threeLevel" class="selectpicker form-control">
								<option value="">--请选择--</option>
							</select>
						</div>
						
						<div style="clear:both;"></div>
					</div>
					<div class="tr_inline" style="margin-bottom:1px;">
						<div class="right_input item_title">商品名称：</div>
						<div class="right_input" style="width: 200px;">
							<input id="goodName" name="goodName" type="text" value="${vo.goodName}" class="form-control item_input"/>
						</div>
						<div class="right_input item_title">订单标识：</div>
						<div class="right_input" style="width: 150px;">
							<input id="orderFlag" name="orderFlag" type="text" value="${vo.orderFlag}" class="form-control item_input"/>
						</div>
						<button class="btn btn-primary sear_btn" id="btn_search" onclick="search()">&nbsp;&nbsp;&nbsp;&nbsp;查询&nbsp;&nbsp;&nbsp;&nbsp;</button>
						<button class="btn btn-success sear_btn" type="reset" id="btn_allot" onclick="openUserWin();" style="margin-right: 350px;">分配任务</button>
						<!-- <button class="btn btn-success sear_btn" type="reset" id="btn_allot" onclick="allot();">更多选项</button> -->
						<div style="clear:both;"></div>
					</div>
				</form>
				<%-- <div id="tabDiv" style="overflow-x:auto;">
					<jsp:include page="task_allocate_detail.jsp"></jsp:include>
				</div> --%>
				<div class="table_head">
					<input style="float:left;margin:7px 0 0 19px;" id="allRecord" type="checkbox" onclick="allRecordSel();" 
					<c:if test="${isCheck}">checked="checked"</c:if>/>
					<span style="float:left;margin-top:5px;font-size:13px;color:white">所有记录</span>
				</div>
				<table border="0" id="cusTab" class="table table-striped table-bordered" width="100%;" >
				   	<thead>
						<tr>
							<th width="40px;">
								<input id="cusSelAll" name="cusSelAll" type="checkbox" onclick="cusSelAll();" <c:if test="${isCheck}">checked="checked"</c:if>/>
							</th>
							<th width="80px;">姓名</th>
							<th width="80px;">省</th>
							<th width="80px;">市</th>
							<!-- <th width="80px;">渠道名称</th> -->
							<th width="80px;">区</th>
							<th width="60px;">订单标识</th>
							<th width="70px;">是否新客户</th>
							<th width="70px;">专属客服</th>
							<th width="70px;">一级分类</th>
							<th width="70px;">二级分类</th>
							<th width="70px;">三级分类</th>
							<th width="150px;">商品名称</th>
						</tr>
					</thead>
					<tbody id="kh_body">
						<c:forEach items="${taskList}" var="item" varStatus="status">
							<tr>
								<td>
									<input name="cusSel" type="checkbox" value="${item.tel}" <c:if test="${isCheck}">checked="checked"</c:if>/>
								</td>
								<td>${item.name}</td>
								<td>${item.province}</td>
								<%-- <td>
						            <c:if test="${item.sex==1 }">男</c:if>
						            <c:if test="${item.sex==2 }">女</c:if> 
								</td> --%>
								<td>${item.city}</td>
								<td>${item.country}</td>
								<td>${item.orderFlag}</td>
								<td>
									<c:choose>
										<c:when test="${item.isExist !=null && item.isExist !=''}">否</c:when>
										<c:otherwise>是</c:otherwise>
									</c:choose>
								</td>
								<td>${item.userName}</td>
								<td>${item.oneLevel}</td>
								<td>${item.twoLevel}</td>
								<td>${item.threeLevel}</td>
								<td align="left">${item.goodName}</td>
							</tr>
						</c:forEach>
						<c:if test="${taskList==null || fn:length(taskList)<=0 }">
							<tr>
								<td colspan="12"
									style="height: 100px; text-align: center; vertical-align: middle; font-size: 14px; font-weight: bold;">暂无数据！</td>
							</tr>
						</c:if>
					</tbody>
				</table>
				<ul class="pager">
					<%@ include file="/WEB-INF/view/common/page.jspf" %>
				</ul>
			</div>
		</div>
	</div>
	<div class="modal fade" id="myModal" tabindex="-1" data-backdrop="false">
		<div class="modal-dialog" style="width:900px;">
			<div class="modal-content" >
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<!-- <h4 class="modal-title" id="myModalLabel">分配</h4> -->
				</div>
				<div id="info" class="modal-body">
					<div style="float:left; width:300px;">
						<div class="tr_inline" style="margin-bottom:10px;">
							<div id="choiceId1" style="text-align: left;">
								<!-- <table width="300px;" style="border-collapse:separate; border-spacing:10px;white-space: nowrap;">
									<tr>
										<td width="15%" align="right">选中客户数：</td>
										<td width="35%" align="left">2000人</td>
										<td width="15%" align="right">已分配客服：</td>
										<td width="35%" align="left">2000人</td>
									</tr>
									<tr>
										<td width="15%" align="right">新客户：</td>
										<td width="35%" align="left">300人</td>
										<td width="15%" align="right">未分配客服：</td>
										<td width="35%" align="left">300人</td>
									</tr>
								</table> -->
								请选择执行任务日期，提交后所需时间可能比较长，请耐心等待！
							</div>
						</div>
						<div class="right_inline" >
							<div class="right_input item_title1">第二步：选择执行任务日期</div>
						</div>
						<div class="right_inline" style="width: 150px;">
							<input id="taskDate" name="taskDate" type="text" onfocus="WdatePicker({minDate:'%y-%M-%d'});" class="form-control item_input1"/>
						</div>
						<div class="right_inline">
							<div class="right_input item_title1">第三步：填写任务要求</div>
						</div>
						<div class="right_inline">
							<textarea class="right_input item_title1" style="width: 280px;" id="ask" rows="9"></textarea>
						</div>
					</div>
					<div style="float:left; width:300px;">
						<div class="right_inline"style="margin-top: -6px;">
							<div class="right_input item_title1">第四步：新客户是否加入客户档案列表</div>
						</div>
						<div class="right_inline" style="width: 250px;">
							<input class="right_input" type="radio" id="isCheck1" name="isTrack" checked="checked" value="1">
							<span class="right_input">是</span>
							<input class="right_input" style="margin-left: 20px;" type="radio" id="isCheck2" name="isTrack" value="0" disabled="disabled">
							<span class="right_input">否</span>
						</div>
						<div class="right_inline">
							<div class="right_input item_title1">第五步：为未分配客服的客户选择客户专员</div>
						</div>
						<div class="tr_inline" style="margin-bottom:10px;">
							<div class="right_input" style="width: 220px;">
								<input type="text" id="userName" name="userName" class="form-control" placeholder="请输入客服ID或姓名"/>
							</div>
							<button class="btn btn-info sear_btn" onclick="allot();">&nbsp;&nbsp;查询&nbsp;&nbsp;</button>
						</div>
						<div style="height:275px;max-height: 275px;overflow-y: auto;">
							<table class="table table-striped table-bordered" id="kf_tab">
								<thead>
								<tr>
									<th align="center"><input id="serSelAll" onclick="serSelAll()" type="checkbox"/></th>
									<th align="center">客服ID</th>
									<th align="center">客服姓名</th>
								</tr>
								</thead>
								<tbody id="kf_body"></tbody>
							</table>
						</div>
					</div>
					<div id="choiceId">
						<div style="float:left;margin:7px 0 0 5px;">已选客服：</div>
						<div class="clearfix"></div>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="modal-footer" style="margin-top: 1px;">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	       			<button type="button" class="btn btn-primary" onclick="commit();">提交</button>  
				</div>
			</div>
		</div> 
	</div>	
	</body>
</html>