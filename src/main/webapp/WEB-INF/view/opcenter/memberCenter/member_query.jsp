<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1 maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>会员查询</title>
    
    <!-- 加载CSS样式文件 -->
    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-theme.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-select.min.css" rel="stylesheet">
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
        table td  
        {  
            white-space: nowrap;  
        }  
        table{
         empty-cells:show; 
         border-collapse: collapse;
         margin:0 auto;
        }
		input.item_input {height: 30px;}
		.item_title {text-align: right;width: 80px;padding-top: 5px;margin-left: -10px;}
		.sear_btn{margin-left: 5px; padding: 0 12px; height: 30px;}
		.select_set + div button.selectpicker{height:30px;padding-top:0;padding-bottom:0;}
		.tr_inline{width:100%;margin-bottom:7px;}
		#choiceId{float:left; width:250px;height:100%;min-height:75px;max-height: 350px;overflow-y: auto; margin-left:8px;padding: 5px;border: 1px solid #DDD;border-radius:4px;}
		.div_inline{float:left;width:auto;padding:2px 5px;margin:3px;border-radius:4px;border:1px solid #D2E0F0;background-color:#D2F0FF;}
		.span_text{color:#3179A0;}
		.span_del{color:#FF0404;cursor: pointer;margin-left:5px;}
	</style>
    <!-- 加载javascript文件 -->
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.3.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap-select.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/city.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/respond.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/mask.js"></script>
    <script>
	    String.prototype.trim = function(){
			return this.replace(/(^\s*)|(\s*$)/g, "");
		};
	    $(document).ready(function (){
	    	$('.selectpicker').selectpicker();
	    	$("#btn_reset").click(function (){
	    		$("#memberName").attr("value","");
	    		$("#tel").attr("value","");
	    		$('#sex').selectpicker('val', '');
	    		$('#sex').selectpicker('refresh');
	    		$('#isAllot').selectpicker('val', '0');
	    		$("#isAllot").selectpicker('refresh'); 
	    		$("#province").attr("value","");
	    		//$("#health").attr("value","");
	    		$("#startAge").attr("value","");
	    		$("#endAge").attr("value","");
	    		//$('.selectpicker').selectpicker('refresh');
	    		//$("#memberStatus").attr("value","");
	    		//$("#attitude").attr("value","");
	    	});
	    });
		/**
		 * 查看会员明细信息
		 */
	    function searchDetail(tel){
			$("#dataForm").attr("action","<%=request.getContextPath()%>/member.do?method=getMemberById&tel="+tel);
	    	$("#dataForm").submit();
	    	/* document.form.action = "member.do?method=getMemberById&tel="+tel;
	    	document.form.submit(); */
	    }
	    /**
		 * 去电弹屏
		 */
	    function outScreen(tel){
	    	$("#dataForm").attr("action","<%=request.getContextPath()%>/outScreen2.do?method=getInfo&phoneNo="+tel);
	    	$("#dataForm").submit();
	    	/* document.form.action = "outScreen2.do?method=getInfo&phoneNo="+tel;
	    	document.form.submit(); */
	    }
	    
	    function gotoPage(pageno){
	    	$("[name=pageno]").val(pageno);
	    	$("#dataForm").attr("action","<%=request.getContextPath()%>/member.do?method=getMemberList");
	    	$("#dataForm").submit();
	    	<%--//$("body").mask("Loading , please waite...");
	    	var pageSize = $("#curPageSize").val().trim();
	    	var totalRecords = $("#totalRecords").val().trim();
	    	var memberName =  $("#memberName").val().trim();
	    	var tel =  $("#tel").val().trim();
	    	//var health =  $("#health").val().trim();
	    	var startAge =  $("#startAge").val().trim();
	    	var endAge =  $("#endAge").val().trim();
	    	var province =  $("#province").val().trim();
	    	var isAllot = $("#isAllot").val().trim();
	    	//var memberStatus =  $("#memberStatus").val().trim();
	    	//var attitude =  $("#attitude").val().trim();
	    	$.ajax({
	    		type: "POST",
	            url: "<%=request.getContextPath()%>/member.do?method=getMemberList",
	            dataType: "html",
	            data: {pageno:pageno,pageSize:pageSize,recTotal:totalRecords,memberName:encodeURI(memberName),tel:tel,
	            	sex:sex,province:province,startAge:startAge,endAge:endAge,isAllot:isAllot},
	            success: function(data){
	           		$("body").html(data);
	            },
	            error: function(){
	            	$("body").html("");
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
	    function openUserWin(seq){
	    	queue = seq;
	    	if(seq == 1){
		    	var tels = onTelSel();
		    	if(tels == ""){
		    		alert("请选择需要分配客服专员的客户！");
		    		return;
		    	}
	    	}
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
	       			var tabHeight = $('#kf_body').outerHeight() + 75;
	    	    	$("#choiceId").css("height",tabHeight);
	            },
	            error: function(){
	            	alert("error");
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
	    		//userCheck_click($(this));
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
	    //会员信息全选
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
			var items = document.getElementsByName("cusSel");
			for(var i=0;i<items.length;i++){
				if(items[i].checked){
					if(tels!="") {
						tels+="{&,";
					}
					tels += items[i].value;
				}
			}
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
	    //确定按钮点击(分配客服)
	    var queue = 0;
	    function allot_task(){
	    	$("body").mask("Loading , please waite...");
	    	var serIds = onCusSel();
	    	var tels =  onTelSel();
	    	if(serIds == ""){
	    		alert("请选择客服人员！");
	    		return;
	    	}
	    	if(queue == 1){
	    		$.ajax({
		    		type: "POST",
		            url: "<%=request.getContextPath()%>/taskAllocate.do?method=allotService",
		            dataType: "json",
		            data: {tels:tels,serIds:serIds,queue:queue},
		            success: function(data){
		            	if(data.message != null && data.message != ""){
		            		alert(data.message);
		            	}else{
		            		alert("分配客服成功！");
		            		gotoPage(1);
		            	}
	            		$('#myModal').modal('hide');
	            		$("#body").unmask();
		            },
		            error: function(){
		            	$("#body").unmask();
		            	alert("分配任务失败，请联系管理员！");
		            }
		    	});
	    	}else{
	    		batch(serIds);
	    	}
	    	
	    }
	    //批量分配客服
	    function batch(serIds){
	    	var memberName =  $("#memberName").val().trim();
	    	var tel =  $("#tel").val().trim();
	    	var sex =  $('#sex').val().trim();
	    	var province =  $("#province").val().trim();
	    	//var health =  $("#health").val().trim();
	    	var startAge =  $("#startAge").val().trim();
	    	var endAge =  $("#endAge").val().trim();
	    	var isAllot = $("#isAllot").val().trim();
	    	//var memberStatus =  $("#memberStatus").val().trim();
	    	//var attitude =  $("#attitude").val().trim();

	    	$.ajax({
	    		type: "POST",
	            url: "<%=request.getContextPath()%>/taskAllocate.do?method=batchAllotService",
	            dataType: "json",
	            data: {serIds:serIds,memberName:encodeURI(memberName),tel:tel,
	            	sex:sex,province:province,startAge:startAge,endAge:endAge,isAllot:isAllot},
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
    <div id="base" style="height: 600px;">
	    <div class="panel panel-default">
	        <div class="panel-heading">
                <h5 class="panel-title" style="color:#31849b;">会员信息查询</h5>
            </div>
            <div class="panel-body">
		        <form class="well" action="" method="post" id="dataForm">
					<input type="hidden" id="curPage" name="pageno" value="${pageno}"/>
					<input type="hidden" id="curPageSize" name="pageSize" value="${pageSize}"/>
					<input type="hidden" id="totalRecords" name="totalRecords" value="${recTotal}"/>
					<div class="tr_inline" style="margin-bottom:10px;">
						<div class="right_input item_title">姓名：</div>
						<div class="right_input" style="width: 150px;">
							<input type="text" class="form-control item_input" name="memberName" id="memberName" value="${member.memberName}">
						</div>
						<div class="right_input item_title">手机号：</div>
						<div class="right_input" style="width: 150px;">
							<input type="text" class="form-control item_input" name="tel" id="tel" value="${member.tel}">
						</div>
						<div class="right_input item_title">性别：</div>
						<div class="right_input" style="width: 150px;">
							<select class="selectpicker" data-width="100%" data-size="5" name="sex" id="sex">
								<option value="" ${""==member.sex || "0"== member.sex?'selected="selected"':''}>--请选择--</option>
								<option value="男" ${"男"==member.sex?'selected="selected"':''}>男</option>
								<option value="女" ${"女"==member.sex?'selected="selected"':''}>女</option>
							</select>
						</div>
						<div class="right_input item_title">年龄：</div>
						<div class="right_input" style="width: 70px;">
							<input type="text" class="form-control item_input" name="startAge" id="startAge" value="${member.startAge}">
						</div>
						<div class="right_input">-</div>
						<div class="right_input" style="width: 70px;">
							<input type="text" class="form-control item_input" name="endAge" id="endAge" value="${member.endAge}">
						</div>
						<button class="btn btn-default sear_btn" id="btn_reset">重置</button>
						<sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_ALLOT_SERVICE,/taskAllocate.do?method=allotService">
							<button class="btn btn-info sear_btn" type="reset" onclick="openUserWin(1);">&nbsp;&nbsp;&nbsp;&nbsp;分配客服&nbsp;&nbsp;&nbsp;&nbsp;</button>
						</sec:authorize>
						<!-- <button class="btn btn-default sear_btn"  id="btn_reset" onclick="resetPageNo();">重置</button> -->
						<div style="clear:both;"></div>
					</div>
					<div class="tr_inline">
						<div class="right_input item_title">省份：</div>
						<div class="right_input" style="width: 150px;">
							<input name="province" class="stext form-control" type="text"  id="province" onclick="item_suggest.item_display(this,'',event);" 
									onkeydown="item_suggest.item_display(this,'',event);" onblur="item_suggest.item_onblur(this);" value="${member.province}"/>
						</div>
						<!-- <div class="right_input item_title">所在区域：</div>
						<div class="right_input" style="width:132px;">
							<select id="province" name="province" onchange="changeProvince();" class="selectpicker form-control">
								<option value=''>--请选择省--</option>
							</select>
						</div>
						<div class="right_input" style="width:132px;">
							<select id="city" name="city" onchange="changeCity()" class="selectpicker form-control">
								<option value="">--请选择市--</option>
							</select>
						</div>
						<div class="right_input" style="width:132px;">
							<select id="district" name="county" class="selectpicker form-control">
								<option value="">--请选择区--</option>
							</select>
						</div> -->
						<div class="right_input item_title">活跃度：</div>
						<div class="right_input" style="width: 135px;">
							<select class="selectpicker" data-width="100%" data-size="5" name="memberStatus" id="memberStatus">
								<option value="" >--请选择--</option>
								<option value="1" >忠诚用户</option>
								<option value="2" >活跃用户</option>
								<option value="2" >普通用户</option>
								<option value="2" >边缘用户</option>
								<option value="2" >休眠用户</option>
							</select>
						</div>
						<div class="right_input item_title">专属客服：</div>
						<div class="right_input" style="width: 134px;">
							<select class="selectpicker" data-width="100%" data-size="5" name="isAllot" id="isAllot">
								<option value="0" ${""==member.isAllot || "0"== member.isAllot?'selected="selected"':''}>--请选择--</option>
								<option value="1" ${"1"==member.isAllot?'selected="selected"':''}>已分配</option>
								<option value="2" ${"2"==member.isAllot?'selected="selected"':''}>未分配</option>
							</select>
						</div>
						<button class="btn btn-default sear_btn" style="margin-left: 253px;" onclick="gotoPage(1);">查询</button>
						<sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_ALLOT_SERVICE,/taskAllocate.do?method=batchAllotService">
							<button class="btn btn-info sear_btn" type="reset" onclick="openUserWin(2);">批量分配客服</button>
						</sec:authorize>
					</div>
				</form>
			    <table border="0" class="table table-striped table-bordered" >
					<tr>
						<th><input id="cusSelAll" type="checkbox" onclick="cusSelAll()"/></th>
						<th>操作</th>
						<!-- <th>序号</th> -->
						<th>姓名</th>
						<!-- <th>手机号</th> -->
						<th>性别</th>
						<th>年龄</th>
						<th>会员编号</th>
						<!-- <th>注册日期</th>
						<th>生日</th> -->
						<!-- <th>状态</th>
						<th>态度</th>
						<th>有过订单</th> -->
						<th>省份</th>
						<th>市区</th>
						<!-- <th>QQ</th> -->
						<th>email</th>
						<!-- <th>上次访问日期</th> -->
						<!-- <th>活跃度</th> -->
						<th>专属客服</th>
						<!-- <th>说明</th> -->
					</tr>
					<c:forEach items="${memberList}" var="item" varStatus="status">
						<tr>
							<td><input name="cusSel" type="checkbox" value="${item.tel}"/></td>
							<td style="text-align: center">
							    <img class="img-thumbnail" src="<%=request.getContextPath()%>/images/search.png"
								     onclick="searchDetail('${item.tel}');" onerror="this.src='<%=request.getContextPath()%>/images/search.png';this.onerror=null;"
								     title="查看明细信息" alt="查看明细信息" style="width: 27px; height: 27px; cursor: pointer" />
								<span>&nbsp;&nbsp;</span> 
							    <img class="img-thumbnail" src="<%=request.getContextPath()%>/images/call.png"
								     onclick="outScreen('${item.tel}');" onerror="this.src='<%=request.getContextPath()%>/images/call.png';this.onerror=null;"
								     title="去电弹屏" alt="去电弹屏" style="width: 27px; height: 27px; cursor: pointer" />
							</td>
							<%-- <td>${status.index + 1}</td> --%>
								<td>${item.memberName}</td>
								<%-- <td>${item.tel}
								    <span>&nbsp;&nbsp;</span> 
								    <img class="img-thumbnail" src="<%=request.getContextPath()%>/images/call.png"
									     onclick="outScreen('${item.tel}');" onerror="this.src='<%=request.getContextPath()%>/images/call.png';this.onerror=null;"
									     title="去电弹屏" alt="去电弹屏" style="width: 27px; height: 27px; cursor: pointer" />
								</td> --%>
							<td>
					            <c:if test="${item.sex==1 }">男</c:if>
					            <c:if test="${item.sex==2 }">女</c:if> 
							</td>
							<td>${item.age}</td>
							<td>${item.memberCode}</td>
							<!-- <td>注册日期</td>
							<td>生日</td> -->
							<%-- <td>
								 <c:if test="${item.memberStatus==1 }">活跃</c:if>
					             <c:if test="${item.memberStatus==2 }">休眠</c:if> 
							</td>
							<td>
								<c:if test="${item.attitude==1 }">拒访</c:if>
					            <c:if test="${item.attitude==2 }">接收短信</c:if>
					            <c:if test="${item.attitude==3 }">接收邮件</c:if>
					            <c:if test="${item.attitude==4 }">积极</c:if>
							</td>
							<td>
								<c:if test="${item.isOrder==1 }">无订单信息</c:if>
					            <c:if test="${item.isOrder==2 }">有过订单</c:if>
							</td> --%>
							<td>${item.province}</td>
							<td>${item.city}</td>
							<%-- <td>${item.qq}</td> --%>
							<td>${item.email}</td>
							<!-- <td>上次访问日期</td> -->
							<!-- <td>活跃度</td> -->
							<td>${item.userName}</td>
							<%-- <td>${item.comment}</td> --%>
						</tr>
					</c:forEach>
				</table>
				<ul class="pager">
					<%@ include file="/WEB-INF/view/common/page.jspf" %>
				</ul>
			</div>
		</div>
	</div>
	<div class="modal fade" id="myModal" tabindex="-1" data-backdrop="false">
			<div class="modal-dialog" style="width:600px;">
				<div class="modal-content" >
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">选择客户专员</h4>
					</div>
					<div id="info" class="modal-body">
						<div style="margin-top:-11px;text-align: left;">将为所选会员重新分配专属客服！</div>
						<div style="float:left; width:300px;max-height: 350px;overflow-y: auto;">
							<div class="tr_inline" style="margin-bottom:10px;">
								<div class="right_input" style="width: 200px;">
									<input type="text" id="userName" name="userName" class="form-control" placeholder="请输入客服ID或姓名"/>
								</div>
								<button class="btn btn-info sear_btn" onclick="allot();">查询</button>
							</div>
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
						<div id="choiceId">
							<div style="float:left;margin:7px 0 0 5px;">已选客服：</div>
							<div class="clearfix"></div>
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="modal-footer" style="margin-top: 1px;">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
		       			<button type="button" class="btn btn-primary" onclick="allot_task();">确定</button>  
					</div>
				</div>
			</div> 
		</div>	
</body>
</html>