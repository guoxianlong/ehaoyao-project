<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
   
    <!-- 加载javascript文件 -->
 	<script type="text/javascript" src="<%=request.getContextPath()%>/js/opcenter/commuType.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.3.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap-select.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/WdatePicker/WdatePicker.js"></script>
    <script src="<%=request.getContextPath()%>/js/city.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/respond.min.js"></script>
    <style type="text/css">
	#buy_table thead tr th{font-size:14px;background-color: #f9f9f9;}
	input.item_input {height: 30px;}
	.input_custSerNo {width:100px;}
	.item_title {text-align: right;width: auto;padding-top: 5px;margin-left: 15px;}
	.item_title2 {width: 80px;font-weight: bold;text-align: right;padding-top: 10px;margin-left: 15px;}
	.sear_btn{margin-left: 30px; padding: 0 12px; height: 30px;}
	.exp_btn{margin-lett: 30px; padding: 0 12px; height: 30px;}
	.select_set + div button.selectpicker{width:95px;height:30px;padding-top:0;padding-bottom:0;}
	.select_set_is + div button.selectpicker{width:70px;height:30px;padding-top:0;padding-bottom:0;}
	.conn_Select_set{}
	.accept_Select_set{}
	.isOrder_space{margin-left: 5px}
	</style>
    <script type="text/javascript">
		
  	  var initHtml="<option value=''>全部</option>";
		String.prototype.trim = function(){
			return this.replace(/(^\s*)|(\s*$)/g, "");
		};
		
		$(document).ready(function(){
			$("img[title='去电弹屏']").click(
				 function outScreen(){
					$("tr").removeClass("success");
					$(this).parent().parent().addClass("success");
					var tel=$(this).attr("alt");
			    	$("#telForm").attr("action","outScreen2.do?method=getInfo&phoneNo="+tel);
			    	$("#telForm").submit();
			    }
			);
/* 			commuType("0","#acceptResult"); */
			$('.selectpicker').selectpicker();
			getNextSelect(1);
			con_tel_show();
		});
		//翻页
		function gotoPage(pageno){
			var startDate=$("#startDate").val().trim();
			var endDate=$("#endDate").val().trim();
			var name = $("#custServName").val().trim();
			var connGroup = $("#connGroup").val().trim();
			var custSource = $("#custSource").val().trim();
			var acceptResult= $("#acceptResult").val().trim();
			var secondType= $("#secondType").val().trim();
			var proCategory=$("#proCategory").val().trim();
			var depCategory = $("#depCategory").val().trim();
			var diseaseCategory = $("#diseaseCategory").val().trim();
			var isPlaceOrder = $("#isPlaceOrder").val().trim();
			var isOrder = $("#isOrder").val().trim();
			var isTrack = $("#isTrack").val().trim();
			var isNewUser=$("#isNewUser").val().trim();
			var todayTrack=$("#todayTrack").val().trim();
			var pageSize = $("#curPageSize").val().trim();
			var proSkus=$("#proSkus").val().trim();
			var	proKeywords=$("#proKeywords").val().trim(); 
			$.ajax({
				type: "POST",
				url: "<%=request.getContextPath()%>/communicateReport.do?method=getCommunicationList&flag=1",
		        dataType: "html",
		        data: {startDate:startDate,endDate:endDate,name:name,connGroup:connGroup,custSource:custSource,acceptResult:acceptResult,
		        	secondType:secondType,proCategory:proCategory,depCategory:depCategory,diseaseCategory:diseaseCategory,
		        	isPlaceOrder:isPlaceOrder,isOrder:isOrder,isTrack:isTrack,isNewUser:isNewUser,todayTrack:todayTrack,pageno:pageno,
		        	pageSize:pageSize,proSkus:proSkus,proKeywords:proKeywords},
		        success: function(data){
		       		$("#commuLsDiv").html(data);
		       		con_tel_show();
		        },
		        error: function(){
		        	$("#commuLsDiv").html("");
	            }
			});	
		}
		function gotoPageSize(pageSize){
			$("#curPageSize").val(pageSize);
			gotoPage($("#curPage").val());
		}
		
	
		//获取下一级列表数据
		function getNextSelect(level){
			var parentName;
			switch(level){
				case(1):{
					parentName="null";
					break;
				};
				case(2):{
					parentName=$("#proCategory").find("option:selected").text();
					break;
				};
				case(3):{
					parentName=$("#depCategory").find("option:selected").text();
					break;
				};
			};
			if(parentName!="全部"){
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
							$("#proCategory").empty();
							var proHtml=initHtml;
							for(i=0;i<arr.length;i++){
								proHtml+=("<option value='"+arr[i]+"'>"+arr[i]+"</option>");
							}
							$("#proCategory").html(proHtml);
							$("#depCategory").html(initHtml);
							$("#diseaseCategory").html(initHtml);
							$('.selectpicker').selectpicker('refresh');
							break;
					};
					case(2):{
							$("#depCategory").empty();
							$("#depCategory").append("<option value=''>全部</option>");
							for(i=0;i<arr.length;i++){
									$("#depCategory").append("<option value='"+arr[i]+"'>"+arr[i]+"</option>");
								}
							$("#diseaseCategory").html(initHtml);
							$('.selectpicker').selectpicker('refresh');
							break;
					};
					case(3):{
							$("#diseaseCategory").empty();
							$("#diseaseCategory").append("<option value=''>全部</option>");
							for(i=0;i<arr.length;i++){
								$("#diseaseCategory").append("<option value='"+arr[i]+"'>"+arr[i]+"</option>");
							}
							$('.selectpicker').selectpicker('refresh');
							break;
					};
		       	 };
		        }
			});
			}
		}
		
		//将查询结果导出报表
		function exportComm(){
			document.form.action = "communicateReport.do?method=getExcel";
	    	document.form.submit();
		}
		//更多条件
		$(function () { $('#collapseFour').collapse({
		      toggle: false
		   })});
		
		/**选择客户来源时，加载沟通类型（一级）*/
		function changeAcceptResult(){
			//如果客户来源为  来电  或  在线    则加载对应的沟通记录
			var custSource=$("#custSource").val();
			if(custSource=="TEL_IN"||custSource=="ZX"){
				commuType("0","#acceptResult");
				//重置沟通类型（二级）
				changeSecType();
				return;
			 //客户来源为去电
			}
			if(custSource=="TEL_OUT"){
				commuType("1","#acceptResult");
				//重置沟通类型（二级）
				changeSecType();
				return;
			}
			$("#acceptResult").html("<option value=''>--请选择--</option>");
			$("#acceptResult").selectpicker("refresh");
			$("#secondType").html("<option value=''>--请选择--</option>");
			$("#secondType").selectpicker("refresh");
			//客户来源为需求登记，不做处理
			return;
		}
		/**选择沟通类型（一级）加载沟通类型（二级）*/
		function changeSecType(){
			var custSource = $("#custSource").val();
			//来电或在线客服
			if(custSource=="TEL_IN"||custSource=="ZX"){
				selFirstType("0","#acceptResult",'#secondType','#thirdType');
				return;
			}
			//去电
			if(custSource=="TEL_OUT"){
				selFirstType("1","#acceptResult",'#secondType','#thirdType');
				return;
			}
			$("#secondType").html("<option value=''>--请选择--</option>");
			$("#secondType").selectpicker("refresh");
		}
	</script>
</head>
<body style="padding-top: 10px;">
	<div class="right_box">
	    <form name="queryForm" action="" class="form-horizontal" role="form" method="post" style="width:auto;">
			<input type="hidden" id="curPage" name="pageno" value="${pageno}"/>
			<input type="hidden" id="curPageSize" name="pageSize" value="${pageSize}"/>
			<input type="hidden" id="tel_show" name="tel_show" value="${tel_show}"/>
			
			
			<div class="right_inline">
				<!-- 咨询日期 -->
				<div class="right_input item_title">咨询日期：</div>
				<div class="right_input" style="width: 110px;">
					<input id="startDate" name="startDate" value="${ci.startDate}" type="text" onfocus="WdatePicker({maxDate:'%y-%M-%d||#F{$dp.$D(\'endDate\')}'});" class="form-control item_input"/>
				</div>
				<div class="right_input">&nbsp;__&nbsp;</div>
				<div class="right_input" style="width: 110px;">
					<input id="endDate" name="endDate" value="${ci.endDate}" type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d'})" class="form-control item_input"/>
				</div>
				<!-- 客服工号 -->
				<div class="right_input item_title">客服姓名：</div>
				<div class="right_input" style="width: 100px">
					<input id="custServName" name="custServName" type="text" value="${ci.name}" class="form-control item_input input_custSerNo"/>
				</div>
				<!-- 联系分组 -->
				<div class="right_input item_title conn_Select_set">联系分组：</div>
				<div class="right_input">
					<select name="connGroup" id="connGroup" class="selectpicker form-control select_set">
							<option value="">全部</option>
							<option value="2" ${"2"==ci.connGroup?'selected="selected"':''}>呼入组</option>
							<option value="3" ${"3"==ci.connGroup?'selected="selected"':''}>呼出组</option>
					</select>
				</div>
				
				<div class="right_input sear_btn">
					<button type="button" onclick="gotoPage(1)" class="btn btn-info">查询</button>
				</div>
				<sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_ExportExcel,/communicateReport.do?method=getExcel">
					<div class="right_input exp_btn">
						<button type="button" onclick="exportComm();" class="btn btn-info">导出</button>
					</div>
				</sec:authorize>
				<div style="clear:both;"></div>
			</div>
			<div class="right_inline">
				<div class="right_input item_title">客户来源：</div>
				<div class="right_input">
					<select name="custSource" id="custSource" onchange="changeAcceptResult()" class="selectpicker form-control select_set">
							<option value="">--请选择--</option>
							<option value="TEL_IN" ${"TEL_IN"==ci.custSource?'selected="selected"':''}>来电弹屏</option>
							<option value="TEL_OUT" ${"TEL_OUT"==ci.custSource?'selected="selected"':''}>去电弹屏</option>
							<option value="ZX" ${"ZX"==ci.custSource?'selected="selected"':''}>在线客服</option>
							<option value="XQ" ${"XQ"==ci.custSource?'selected="selected"':''}>需求登记</option>
					</select>
				</div>
				<!-- 沟通类型（一级） -->
				<div class="right_input item_title">沟通类型：</div>
				<div class="right_input">
					<select name="acceptResult" id="acceptResult"  onchange="changeSecType()" class="selectpicker form-control select_set">
						<option value="">--请选择--</option>
					</select>
				</div>
				<!-- 沟通类型（二级） -->
				<div class="right_input">
					<select name="secondType" id="secondType" class="selectpicker form-control select_set">
						<option value="">--请选择--</option>
					</select>
				</div>
				<!-- 沟通类型（三级） -->
				<div class="right_input" style="display:none">
					<select name="thirdType" id="thirdType" class="selectpicker form-control select_set">
						<option value="">--请选择--</option>
					</select>
				</div>
			</div>
			<div class="right_inline">				
				<div class="right_input item_title">产品编码(<font style="color:red;">请输入完整的,否则将有误差</font>)：</div>
				<div class="right_input" style="width: 200px">
					<input id="proSkus" name="proSkus" type="text" value="${ci.proSkus}" class="form-control item_input" />
				</div>
				<div class="right_input item_title">产品关键词：</div>
				<div class="right_input" style="width: 150px">
					<input id="proKeywords" name="proKeywords" type="text" value="${ci.proKeywords}" class="form-control item_input"/>
				</div>
				<div class="right_input item_title isOrder_space">
					 <a data-toggle="collapse" data-parent="#accordion"  href="#collapseFour" style="color: blue;">
	             	 	更多条件...
	         		 </a>
         		</div>
			</div>
	     	 <div id="collapseFour" class="panel-collapse collapse">
		         <div class="panel-body">
		         	<div class="right_inline">
			         	<!-- 品类类别 -->
						<div class="right_input item_title">品类类别：</div>
						<div class="right_input">
							<select name="proCategory" id="proCategory" class="selectpicker form-control select_set" onchange="getNextSelect(2);">
									<option value="">全部</option>
							</select>
						</div>
						<!-- 科组类别 -->
						<div class="right_input item_title">科组类别：</div>
						<div class="right_input">
							<select name="depCategory" id="depCategory" class="selectpicker form-control select_set" onchange="getNextSelect(3);">
									<option value="">全部</option>
							</select>
						</div>
						<!-- 病种类别-->
						<div class="right_input item_title">病种类别：</div>
						<div class="right_input">
							<select name="diseaseCategory" id="diseaseCategory" class="selectpicker form-control select_set">
									<option value="">全部</option>
							</select>
						</div>
						<!-- 是否成单 -->
						<div class="right_input item_title">是否成单：</div>
						<div class="right_input">
							<select name="isPlaceOrder" id="isPlaceOrder" class="selectpicker form-control select_set_is">
									<option value="">全部</option>
									<option value="1" ${"1"==ci.isPlaceOrder?'selected="selected"':''}>是</option>
									<option value="0" ${"0"==ci.isPlaceOrder?'selected="selected"':''}>否</option>
							</select>
						</div>
						<!-- 是否订购 -->
						<div class="right_input item_title isOrder_space">是否订购：</div>
						<div class="right_input">
							<select name="isOrder" id="isOrder" class="selectpicker form-control select_set_is">
									<option value="">全部</option>
									<option value="1" ${"1"==ci.isOrder?'selected="selected"':''}>是</option>
									<option value="0" ${"0"==ci.isOrder?'selected="selected"':''}>否</option>
							</select>
						</div>
						<!-- 是否跟踪 -->
						<div class="right_input item_title isOrder_space">是否跟踪：</div>
						<div class="right_input">
							<select name="isTrack" id="isTrack" class="selectpicker form-control select_set_is">
									<option value="">全部</option>
									<option value="1" ${"1"==ci.isTrack?'selected="selected"':''}>是</option>
									<option value="0" ${"0"==ci.isTrack?'selected="selected"':''}>否</option>
							</select>
						</div>
					</div>
					<div class="right_inline">
			            <!-- 是否新用户 -->
						<div class="right_input item_title isOrder_space">是否新用户：</div>
						<div class="right_input">
							<select name="isNewUser" id="isNewUser" class="selectpicker form-control select_set_is">
									<option value="">全部</option>
									<option value="1" ${"1"==ci.isNewUser?'selected="selected"':''}>是</option>
									<option value="0" ${"0"==ci.isNewUser?'selected="selected"':''}>否</option>
							</select>
						</div>
						<!-- 今日跟踪 -->
						<div class="right_input item_title isOrder_space">今日跟踪：</div>
						<div class="right_input">
							<select name="todayTrack" id="todayTrack" class="selectpicker form-control select_set">
									<option value="">全部</option>
									<option value="1" ${"1"==ci.todayTrack?'selected="selected"':''}>已跟踪</option>
									<option value="0" ${"0"==ci.todayTrack?'selected="selected"':''}>未跟踪</option>
							</select>
						</div>
			         </div>
		     	 </div>
		     </div>
			<div style="clear:both;"></div>
		</form>
		<div id="commuLsDiv" style="overflow-x:auto;">
			<jsp:include page="inner_commu_rep.jsp"></jsp:include>
		</div>
	</div>
	<form  method="post" action="" name="form"	id="form"></form>
</body>
</html>