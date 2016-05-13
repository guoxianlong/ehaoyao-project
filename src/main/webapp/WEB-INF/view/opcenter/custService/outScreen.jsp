<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1 maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>
    	<c:choose>
    		<c:when test='${commuSource=="XQ"}'>需求订单</c:when>
    		<c:otherwise>去电弹屏 </c:otherwise>
    	</c:choose>
    </title>
    <!-- 加载CSS样式文件 -->
    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-theme.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-select.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
     
    <!-- 加载javascript文件 -->
    <script src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap-select.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/WdatePicker/WdatePicker.js"></script>
	<script src="<%=request.getContextPath()%>/js/mask.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/respond.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/sitedata_bas.js"></script>
    
    <style type="text/css">
		#os_tablist li a{font-size:14px; font-weight: bold;}
		input.item_input {height: 30px;}
		.input_div {width:130px;}
		.item_title {text-align: right;width: auto;padding-top: 5px;margin-left: 15px;}
		.item_title2 {width: 60px;font-weight: bold;text-align: right;padding-top: 10px;margin-left: 15px;}
		.item_title3 {width: 45px;font-weight: bold;text-align: right;padding-top: 10px;margin-left: 10px;}
		.select_set + div button.selectpicker{height:30px;padding-top:0;padding-bottom:0;}
		.tr_inline{width:100%;margin-bottom:7px;}
		.td_item {float:left;}
/* 		.td_context{padding-top:10px;font-size:14px;} */
	</style>
	    
	<script type="text/javascript">
		String.prototype.trim = function(){
			return this.replace(/(^\s*)|(\s*$)/g, "");
		};
		
		$(document).ready(function(){
			$('.selectpicker').selectpicker();
			$(".bootstrap-select").css("margin-bottom","0");
			
			//会员地址   省
			showProvince("${member.province}","${member.city}","${member.county}");
			
			$('#os_tablist li a').click(function(){
				$(this).parent().parent().find("li").each(function(){
					if($(this).hasClass("active")){
						$(this).removeClass("active");
					}
				});
				var data = {};
				var url="";
				var li = $(this).parent();
				li.addClass("active");
				if(li.index()==0){
					url = "${actionName}?method=getOfficalMember";//会员管理
					data.memberName = $("#custName").val();
					data.tel = $("#mobile").val();
				}else if(li.index()==1){
					url = "<%=request.getContextPath()%>/${actionName}?method=getGoodsLs&dateString=${dateString}";//主推商品
				}else if(li.index()==2){
					url = "${healAction}?method=getHealthKeywordHean&tel=${member.tel}";//健康档案
				}else if(li.index()==3){
					//url = "${actionName}?method=getCommunication&tel=${member.tel}&screenType=1";//沟通记录
					data.custSource = $("#commuSource").val();
					data.isTask=$("#isNewTask").val();
					data.custServCodeVO="${custServCode}";
					url = "${commuAction}?method=commuMain&tel=${member.tel}";//沟通记录
				}else if(li.index()==4){
					url = "<%=request.getContextPath()%>/${actionName}?method=addOrder&dateString=${dateString}&tel=${member.tel}";//新增订单
				}else if(li.index()==5){
					data.phoneNo = "${member.tel}";
					url = "${callerAction}?method=getOfficialOrder";//官网订单
				}else if(li.index()==6){
					$("body").mask("Loading , please waite ...");
					url = "<%=request.getContextPath()%>/${actionName}?method=getBuyRecords";//购买记录
					data.userName = encodeURI($("#custName").val());
					data.telephoneNo = "${member.tel}";
					data.timeNum = "3";
				}
				if(url!=""){
					$.ajax({
						 type: "POST",
			             url: url,
			             dataType: "html",
			             data: data,
			             success: function(data){
			            	 $("#tabDiv").html(data);
			            	 $("body").unmask();
			             },
			             error:function(){
			            	 $("#tabDiv").html("请求处理失败！");
			            	 $("body").unmask();
			             }
					});
				}else{
					$("#tabDiv").html("");
					$("body").unmask();
				}
			});
			//默认加载客户档案
			$('#os_tablist li a:first').click();
		});
		
		function saveMemberInfo(){
			var data = {};
			data.memberName = $("#custName").val();
			data.sex = $("#custCall").val();
			data.tel = $("#mobile").val();
			data.attitude = $("#attitude").val();
			data.comment = $("#remark").val();
			data.province = $("#province").val();
			data.city = $("#city").val();
			data.county = $("#district").val();
			data.address = $("#address").val();
			data.email = $("#email").val().trim();
			data.screenType="1";
			$.ajax({
				 type: "POST",
	             url: "${memAction}?method=addMember",
	             dataType: "json",
	             data: data,
	             success: function(data){
	            	if(data == 1){
	            		alert("保存客户信息成功");
	            	} else {
	            		alert("保存客户信息失败");
	            	}
	             }
			});
		}
		
		//官网会员注册
		function openRegMem(){
			var mobile = $.trim($("#mobile").val());
			var nickName = $.trim($("#custName").val());
// 			var memEmail = $.trim($("#email").val());
			$("#memPhone").val(mobile);
			$("#nickName").val(nickName);
// 			$("#memEmail").val(memEmail);
			$("#regMemModal").modal("show");
		}
		function registerMember(){
			var memPhone = $.trim($("#memPhone").val());
			if(memPhone==null || memPhone=="" || memPhone=="请输入手机号查询"){
				$("#memPhone").focus();
				alert("请填写手机号！");
				return;
			}
			var password = $.trim($("#password").val());
			var nickName = $.trim($("#nickName").val());
// 			var email = $.trim($("#memEmail").val());
			var email = "";
			$.ajax({
				 type: "POST",
	             url: "${memAction}?method=registerMember",
	             dataType: "json",
	             data: {memberName:memPhone,password:password,nickName:nickName,email:email},
	             success: function(result){
	            	 if(result!=null && result.code == 1){
	            		 var mobile = $.trim($("#mobile").val());
	            		 if(memPhone==mobile){
		            		 $("#memberGrade").text("普通用户");
	            		 }
	            		 alert("注册官网会员成功");
	            		 $("#regMemModal").modal("hide");
	            	 } else{
	            		 if(result!=null && result.tip!=null && result.tip.length>0){
	            			 alert(result.tip);
	            		 }else{
		            		alert("注册官网会员失败");
	            		 }
	            	 }
	             }
			});
		}
		
		//已选中省
		var selProJson = null;
		//已选中城市
		var selCityJson = null;
		
		//显示省列表
		function showProvince(province,city,district){
			var proOptions = "<option value=''>--请选择--</option>";
			var proName="";
			var selProName="";
			for(var i=0;i<arrCity.length;i++){
				proName = arrCity[i].name;
				if(proName!=null && proName.indexOf("请选择")<0){
					if(province!=null && province!="" && (proName.indexOf(province)>=0 || province.indexOf(proName)>=0)){
						selProJson = arrCity[i];
						selProName = arrCity[i].name;
						proOptions += "<option selected='selected' value='"+proName+"'>"+proName+"</option>";
					}else{
						proOptions += "<option value='"+proName+"'>"+proName+"</option>";
					}
				}
			}
			//显示省列表
			var selProEle = $("#province").parent().children('.selectpicker');
			selProEle.html(proOptions);
			$("#province").parent().html(selProEle);
			selProEle.selectpicker({size:10});
			$("#province").parent().find(".bootstrap-select").css("margin-bottom","0");
			if(selProName==null || selProName==""){//未选择
				selProJson=null;
				selProEle.selectpicker('val',"");
			}
			showCity(selProJson,city,district);
		}
		
		//显示城市列表
		function showCity(provinceJson,city,district){
			var selCityEle = $("#city").parent().children('.selectpicker');
			if(provinceJson==null){
				selCityEle.html("<option selected='selected' value=''>--请选择--</option>");
				$("#city").parent().html(selCityEle);
				selCityEle.selectpicker({size:10});
				$("#city").parent().find(".bootstrap-select").css("margin-bottom","0");
				selCityJson=null;
				showDistrict(selCityJson,"");
				return;
			}
			
			//市 数组
			var citys = provinceJson.sub;
			var cityOptions = "<option value=''>--请选择--</option>";
			if(city==null || city==""){
				selCityJson=null;
			}
			//获取城市列表
			if(citys!=null && citys.length>0){
				var cityName = "";
				for(var i=0;i<citys.length;i++){
					cityName = citys[i].name;
					if(cityName!=null && cityName.length>0 && cityName!="请选择"){
						//会员地址 所在城市
						if(city!=null && city.length>0 && (city.indexOf(cityName)>=0||cityName.indexOf(city)>=0)){
							selCityJson = citys[i];
							cityOptions += "<option selected='selected' value='"+cityName+"'>"+cityName+"</option>";
						}else{
							cityOptions += "<option value='"+cityName+"'>"+cityName+"</option>";
						}
					}
				}
			}else{
				selCityJson=null;
			}
			//显示城市列表
			selCityEle.html(cityOptions);
			$("#city").parent().html(selCityEle);
			selCityEle.selectpicker({size:10});
			$("#city").parent().find(".bootstrap-select").css("margin-bottom","0");
			if(selCityJson==null){//未选中 ，显示"请选择"
				selCityEle.selectpicker('val',"");
			}
			//显示地区列表
			showDistrict(selCityJson,district);
		}
		
		//显示地区
		function showDistrict(cityJson,district){
			//显示地区列表
			var selDisEle = $("#district").parent().children('.selectpicker');
			if(cityJson==null){
				selDisEle.html("<option selected='selected' value=''>--请选择--</option>");
				$("#district").parent().html(selDisEle);
				selDisEle.selectpicker({size:10});
				$("#district").parent().find(".bootstrap-select").css("margin-bottom","0");
				return;
			}
			//地区 数组
			var districts = cityJson.sub;
			var disOptions = "<option value=''>--请选择--</option>";
			//获取地区列表
			if(districts!=null && districts.length>0){
				var districtName = "";
				var selCityJson = null;
				for(var i=0;i<districts.length;i++){
					districtName = districts[i].name;
					if(districtName!=null && districtName.length>0 && districtName!="请选择"){
						//会员地址 所在地区
						if(district!=null && district.length>0 && (district.indexOf(districtName)>=0||districtName.indexOf(district)>=0)){
							selCityJson = districts[i];
							disOptions += "<option selected='selected' value='"+districtName+"'>"+districtName+"</option>";
						}else{
							disOptions += "<option value='"+districtName+"'>"+districtName+"</option>";
						}
					}
				}
				//显示地区列表
				selDisEle.html(disOptions);
				$("#district").parent().html(selDisEle);
				selDisEle.selectpicker({size:10});
				$("#district").parent().find(".bootstrap-select").css("margin-bottom","0");
				if(selCityJson==null){//未选中 ，显示"请选择"
					selDisEle.selectpicker('val',"");
				}
			}
		}

		//选择省
		function changeProvince(){
			var pro = $("#province").parent().children('.selectpicker').val();
			var proName="";
			if(pro==null || pro==""){
				selProJson=null;
			}else{
				for(var i=0;i<arrCity.length;i++){
					proName = arrCity[i].name;
					if(proName!=null && proName.indexOf("请选择")<0){
						if(proName==pro){
							selProJson = arrCity[i];
							break;
						}
					}
				}
			}
			//显示城市
			showCity(selProJson,"","");
		}
		
		//选择城市
		function changeCity(){
			selCityJson = null;
			var city = $("#city").parent().children('.selectpicker').val();
			if(selProJson!=null){
				var cityName="";
				var citys = selProJson.sub;
				for(var i=0;i<citys.length;i++){
					cityName = citys[i].name;
					if(cityName!=null && cityName.indexOf("请选择")<0){
						if(cityName==city){
							selCityJson = citys[i];
							break;
						}
					}
				}
			}
			//显示地区
			showDistrict(selCityJson,"");
		}
		//打开新增预约窗口
		function openReserva(){
			$('#myModal').modal('show');
			$("#reservaTime").val("");
			$("#comment").val("");
			/* var tel = $("#mobile").val().trim();
			var custServiceNo = "${custServCode}";
			$.ajax({
				 type: "POST",
	             url: "${actionName}?method=getReservaMessage",
	             dataType: "text",
	             data: {tel:tel,custServiceNo:custServiceNo},
	             success: function(data){
	            	 if(data != null && data.trim() != ""){
	            		 if(confirm(data)){
	            			 $('#myModal').modal('show');
	            		 }
	            	 }else{
		            	 $('#myModal').modal('show');
	            	 }
	            	 if(data != null ){
	            		alert("2222"); 
	            	 }else{
	            		 $('#myModal').modal('show');
	            	 } 
	             }
			}); */
		}
		//保存预约信息
		function savaReservat(){
			var custName = $("#memberName_win").val().trim();
			var tel = $("#tel_win").val().trim();
			//var custServiceNo = "${custServCode}";
			var custServiceNo = "${custServCode}";
			var reserveTime = $("#reservaTime").val().trim();
			var comment = $("#comment").val().trim();
			var orderType="去电";
			if(reserveTime == null || reserveTime == ""){
				alert("预约时间不能为空！");
				return;
			}
			$.ajax({
				 type: "POST",
	             url: "${actionName}?method=savaReservat",
	             dataType: "json",
	             data: {custName:custName,tel:tel,custServiceNo:custServiceNo,reserveTime:reserveTime,comment:comment,orderType:orderType},
	             success: function(data){
	            	 $('#myModal').modal('hide');
	            	 alert("保存成功！");
	             }
			});
		}
	</script>
</head>

<body style="padding-top: 10px;padding-bottom:20px;">
	<div class="right_box">
		<div>
		<div style="float: left;width: 120px; height: 120px;">
			<img class="img-thumbnail" src="<%=request.getContextPath()%>/images/u36.png"
				onerror="this.src='<%=request.getContextPath()%>/images/u36.png';this.onerror=null;" alt="头像"/>
		</div>
		<div style="float:left;width:415px;margin:4px 0 0 15px;">
		    <input type="hidden" id="password" name="" value = "000000" />
		    <input type="hidden" id="dateString" name="dateString" value="${dateString}" />
		    <input type="hidden" id="commuSource" name="commuSource" value="${commuSource}" />
		    <input type="hidden" id="curCommuId" name="curCommuId" value='' />
		    <input type="hidden" id="isNewTask" name="isNewTask" value='${isNewTask }' />
			<div class="tr_inline">
				<div class="td_item item_title2">姓名：</div>
				<div class="td_item input_div">
					<input id="custName" name="memberName" type="text" class="form-control" value="${member.memberName}"/>
				</div>
				<div class="td_item item_title2">称呼：</div>
				<div class="td_item input_div">
					<select id="custCall" name="sex" class="selectpicker form-control">
						<option value="">--请选择--</option>
						<option value="1" ${"1"==member.sex?'selected="selected"':''}>先生</option>
						<option value="2" ${"2"==member.sex?'selected="selected"':''}>女士</option>
					</select>
				</div>
				<div style="clear:both;"></div>
			</div>
			<div class="tr_inline">
				<div class="td_item item_title2">电话：</div>
				<div class="td_item input_div">
					<input id="mobile" name="tel" type="text" readonly="readonly" class="form-control" value="${member.tel}" />
				</div>
				<div class="td_item item_title2">态度：</div>
				<div class="td_item input_div">
					<select name="attitude" id="attitude" class="selectpicker form-control">
						<option value="4" ${"4"==member.attitude?'selected="selected"':''}>积极</option>
						<option value="1" ${"1"==member.attitude?'selected="selected"':''}>拒访</option>
						<option value="2" ${"2"==member.attitude?'selected="selected"':''}>接收短信</option>
						<option value="3" ${"3"==member.attitude?'selected="selected"':''}>接收邮件</option>
					</select>
				</div>
				<div style="clear:both;"></div>
			</div>
			<div class="tr_inline">
				<div class="td_item item_title2">Email：</div>
				<div class="td_item" style="width:335px;">
					<input id="email" name="email" type="text" class="form-control" value="${member.email}" />
				</div>
				<div style="clear:both;"></div>
			</div>
			<div class="tr_inline">
				<div class="td_item item_title2">说明：</div>
				<div class="td_item" style="width: 335px;">
					<textarea id="remark" name="comment" class="form-control" style="height:75px;">${member.comment}</textarea>
				</div>
				<div style="clear:both;"></div>
			</div>
		</div>
		<div style="float:left; width:490px;margin:4px 0 0 5px;">
			<div class="tr_inline">
				<div class="td_item item_title3">省：</div>
				<div class="td_item" style="width:105px;">
					<select id="province" name="province" onchange="changeProvince();" class="selectpicker form-control">
						<option value=''>--请选择--</option>
					</select>
				</div>
				<div class="td_item item_title3">市：</div>
				<div class="td_item" style="width:105px;">
					<select id="city" name="city" onchange="changeCity()" class="selectpicker form-control">
						<option value="">--请选择--</option>
					</select>
				</div>
				<div class="td_item item_title3">区：</div>
				<div class="td_item" style="width:105px;">
					<select id="district" name="county" class="selectpicker form-control">
						<option value="">--请选择--</option>
					</select>
				</div>
				<div style="clear:both;"></div>
			</div>
			<div class="tr_inline" style="margin:4px 0 7px 0;">
				<div class="td_item item_title3">地址：</div>
				<div class="td_item" style="width: 425px;">
					<textarea id="address" name="address" class="form-control" style="height:75px;">${member.address}</textarea>
				</div>
				<div style="clear:both;"></div>
			</div>
			<div style="margin:25px 0 0 55px;">
<!-- 				<button type="button" onclick="registerMember();" class="btn btn-primary">注册官网会员</button> -->
				<button type="button" onclick="openRegMem();" class="btn btn-primary">注册官网会员</button>
				<button type="button" class="btn btn-primary" onclick="saveMemberInfo();" style="margin-left:15px;">保存客户信息</button>
				<button type="button" class="btn btn-primary" onclick="openReserva();" style="margin-left:15px;">新增预约信息</button>
			</div>
		</div>
		<div style="clear:both;"></div>
		</div>
		<hr style="margin:10px 0;"/>
		<div>
			<ul id="os_tablist" class="nav nav-tabs" role="tablist">
				<li class="active"><a href="javascript:void(0);">会员档案</a></li>
				<li><a href="javascript:void(0);">主推商品</a></li>
				<li><a href="javascript:void(0);">健康档案</a></li>
				<li><a href="javascript:void(0);">沟通记录</a></li>
				<li><a href="javascript:void(0);">新增订单</a></li>
				<li><a href="javascript:void(0);">官网订单</a></li>
				<li><a href="javascript:void(0);">购买记录</a></li>
			</ul>
			<div style="clear:both;"></div>
			<div style="border:1px solid #DDD;border-top:none;padding:10px;min-height: 200px;">
				<div id="tabDiv" style="overflow: auto;">
<%-- 					<jsp:include page="commodity.jsp"/> --%>
				</div>
			</div>
		</div>
		<div style="margin-top:15px;">
			<div class="tr_inline">
				<div class="td_item item_title">受理客服：${custServCode}</div>
			</div>
		</div>
	</div>
	<div style="clear: both;"></div> 
</body>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:480px;">
		<div class="modal-content" >
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">新增预约</h4>
			</div>
			<div id="ruleInfo" class="modal-body">
				<div class="right_inline">
					<div class="right_input item_title">客户名称：</div>
					<div class="right_input" style="width:130px">
						<input id="memberName_win" name="memberName_win" value="${member.memberName}" type="text" readonly class="form-control item_input"/>
					</div>
					<div class="right_input item_title">&nbsp;&nbsp;&nbsp;&nbsp;手机号：</div>
					<div class="right_input" style="width:130px">
						<input id="tel_win" name="tel_win" value="${member.tel}" type="text" readonly class="form-control item_input"/>
					</div>
				</div>
				<div style="clear: both;"></div> 
				<div class="right_inline">
					<div class="right_input item_title">客服工号：</div>
					<div class="right_input" style="width:130px">
						<input id="custServiceNo" name="custServiceNo" value="${custServCode}" type="text" readonly class="form-control item_input"/>
					</div>
					<div class="right_input item_title">预约时间：</div>
					<div class="right_input" style="width:130px">
						<input id="reservaTime" name="reservaTime" style="height: 34px;" type="text" onfocus="WdatePicker({minDate:'%y-%M-%d'});" class="form-control item_input"/>
					</div>
				</div>
				<div class="tr_inline">
					<div class="right_input item_title">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;说明：</div>
					<div class="right_input" style="width: 340px;">
						<textarea id="comment" name="comment" class="form-control" style="height:60px;"></textarea>
					</div>
				</div>
				<div style="clear: both;"></div> 
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal" style="margin-right: 10px;">取消</button>
       			<button type="button" id="submitBtn" class="btn btn-primary" onclick="savaReservat();" style="margin-right: 20px;">保存</button>  
			</div>
		</div>
	</div> 
</div>

<div class="modal fade" id="regMemModal" tabindex="-1" role="dialog" aria-labelledby="regMemModalLabel" aria-hidden="true" data-backdrop='static'>
	<div class="modal-dialog" style="width:400px;">
		<div class="modal-content" >
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="regMemModalLabel">会员信息</h4>
			</div>
			<div class="modal-body">
				<div class="tr_inline">
					<div class="td_item item_title" style="width:80px;">会员昵称：</div>
					<div class="td_item" style="width:200px;margin-left:5px;">
						<input type="text" id="nickName" name="nickName" value="" class="form-control"/>
					</div>
					<div style="clear: both;"></div> 
				</div>
				<div class="tr_inline">
					<div class="td_item item_title" style="width:80px;">会员手机号：</div>
					<div class="td_item" style="width:200px;margin-left:5px;">
						<input type="text" id="memPhone" name="memPhone" value="" class="form-control"/>
					</div>
					<div style="clear: both;"></div> 
				</div>
				<!-- <div class="tr_inline">
					<div class="td_item item_title" style="width:80px;">邮箱：</div>
					<div class="td_item" style="width:200px;margin-left:5px;">
						<input type="text" id="memEmail" name="memEmail" value="" class="form-control"/>
					</div>
					<div style="clear: both;"></div> 
				</div> -->
			</div>
			<div class="modal-footer" style="margin-top:0;">
				<button type="button" class="btn btn-default" data-dismiss="modal" style="margin-right: 10px;">取消</button>
       			<button type="button" id="submitBtn" class="btn btn-primary" onclick="registerMember()" style="margin-right: 20px;">注册</button>  
			</div>
		</div>
	</div> 
</div>

</html>