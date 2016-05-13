<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1 maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>
    	<c:choose>
    		<c:when test='${opFlag=="1"}'>在线工单</c:when>
    		<c:otherwise>来电弹屏</c:otherwise>
    	</c:choose>
    </title>
    
    <!-- 加载CSS样式文件 -->
    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-theme.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-select.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
    
    <!-- 加载javascript文件 -->
    <script src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap-select.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/respond.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/js/WdatePicker/WdatePicker.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/js/mask.js" type="text/javascript"></script>
    
    <style type="text/css">
		#os_tablist li a{font-size:14px; font-weight: bold;}
		input.item_input {height: 30px;}
		.input_div {width:130px;}
		.item_title {text-align: right;width: auto;padding-top: 5px;margin-left: 15px;}
		.item_title2 {width: 60px;font-weight: bold;text-align: right;padding-top: 10px;margin-left: 15px;}
		.select_set + div button.selectpicker{height:30px;padding-top:0;padding-bottom:0;}
		.tr_inline{width:100%;margin-bottom:7px;}
		.td_item {float:left;}
		.tipText{color: #999999;}
	</style>
	    
	<script type="text/javascript">
		String.prototype.trim = function(){
			return this.replace(/(^\s*)|(\s*$)/g, "");
		};
		
		$(document).ready(function(){
			$('.selectpicker').selectpicker();
			$(".bootstrap-select").css("margin-bottom","0");
// 			var datas = {};
// 			//datas.phoneNo = "${member.tel}";
// 			datas.tel = "${member.tel}";
// 			var urls = "${callerAction}?method=getOfficalMember";
// 			doPost(urls,datas);
			
			$('#os_tablist li a').click(function(){
				/*if($("#mobile").val()==null || $("#mobile").val().trim()=="" 
						|| $("#mobile").val()=="输入手机号查询"){
					alert("用户手机号为空！");
					return;
				}*/
				$(this).parent().parent().find("li").each(function(){
					if($(this).hasClass("active")){
						$(this).removeClass("active");
					}
				});
				var data = {};
				var url="";
				var li = $(this).parent();
				li.addClass("active");
				var tel = $("#mobile").val().trim();
				var telTip = $("#telTip").val().trim();
				if(tel==telTip){
					tel="";
				}
				var itemname=li.find("a").text();
// 				if(li.index()==0){
				if(itemname=="会员档案"){
					url = "${callerAction}?method=getOfficalMember";//会员档案
					data.memberName = $("#custName").val();
					data.tel = $("#mobile").val();
				}else if(itemname=="健康档案"){
					url = "${healAction}?method=getHealthKeywordHean&tel="+tel;//健康档案
				}else if(itemname=="沟通记录"){
					data.custSource = "${commuSource}";
					data.custServCodeVO="${custServCode}";
					url = "${commuAction}?method=commuMain&tel="+tel;//沟通记录
				}else if(itemname=="新增订单"){
					url = "${outAction}?method=addOrder&dateString=${dateString}&tel="+tel;//新增订单
				}else if(itemname=="官网订单"){
					data.phoneNo = tel;
					url = "${callerAction}?method=getOfficialOrder";//官网订单
				}else if(itemname=="购买记录"){
					$("body").mask("Loading , please waite ...");
					url = "${outAction}?method=getBuyRecords";//购买记录
					data.userName = encodeURI($("#custName").val());
					data.telephoneNo = tel;
					data.timeNum = "3";
				}
				if(url !=""){
					doPost(url,data);
				}else{
					$("#tabDiv").html("");
					$("body").unmask();
				}
			});
			//默认加载第一项
			$('#os_tablist li a:first').click();
			
		});
		
		function doPost(url,data){
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
		}
		
		function saveMemberInfo(){
			var data = {};
			data.memberName = $("#custName").val();
			data.sex = $("#custCall").val();
			var mobile = $("#mobile").val().trim();
			$("#mobile").val(mobile);
			var telTip = $("#telTip").val().trim();
			if(mobile==null || mobile=="" || mobile==telTip){
				$("#mobile").focus();
				alert("请填写手机号！");
				return;
			}
			data.tel = mobile;
			data.attitude = $("#attitude").val();
// 			data.comment = $("#remark").val().trim();
			data.email = $("#email").val().trim();
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
			var telTip = $("#telTip").val().trim();
			if(memPhone==null || memPhone=="" || memPhone==telTip){
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
		
		//查询会员信息
		function queryMember(){
			//清空当前沟通记录
			$("#curCommuId").val("");
			var tel = $("#mobile").val().trim();
			$.ajax({
				 type: "POST",
	             url: "${memAction}?method=queryMember",
	             dataType: "json",
	             data: {tel:tel},
	             success: function(data){
	            	 if(data!=null){
		            	 $("#custName").val(data.memberName);
		            	 var mSex = data.sex;
		            	 if(mSex!="1" && mSex!="2"){
		            		 mSex="";
		            	 }
		            	 $('#custCall').parent().children('.selectpicker').selectpicker('val',mSex);
		            	 var mStat = data.attitude;
		            	 if(mStat!="1" && mStat!="2" && mStat!="3" && mStat!="4"){
		            		 mStat="4";
		            	 }
		            	 $("#attitude").parent().children('.selectpicker').selectpicker('val',mStat);
// 		            	 $("#remark").val(data.comment);
		            	 $("#email").val(data.email);
		     			 $("#memberGrade").text(data.memberGrade);
	            	 }else{
		            	 $("#custName").val("");
		            	 $("#custCall").parent().children('.selectpicker').selectpicker('val',"");
		            	 $("#attitude").parent().children('.selectpicker').selectpicker('val',"4");
// 		            	 $("#remark").val("");
		            	 $("#email").val("");
		     			 $("#memberGrade").text("");
	            	 }
	             }
			});
		}
		
		function dialNumber(){
			var tel = $("#mobile").val().trim();
			var telTip = $("#telTip").val().trim();
			if(tel==null || tel.length==0 || tel==telTip){
				alert("电话号码为空！");
				return;
			}
			if(confirm("您确定要拨打电话吗？")){
				var blank = window.open("http://www.callout4ehaoyao.com?outboundnbr="+tel,"_blank");
				//10秒后关闭
				setTimeout(function(){
					blank.close();
				}, 10000);
			}
		}
	</script>
</head>
<body style="padding-top: 10px;padding-bottom:20px;">
	<div class="right_box">
		<div style="margin-bottom:10px;">
			<div style="float: left;width: 120px; height: 120px;">
				<img alt="头像" class="img-thumbnail" src="<%=request.getContextPath()%>/images/u36.png"
					onerror="this.src='<%=request.getContextPath()%>/images/u36.png';this.onerror=null;" />
			</div>
			<div style="float:left;width:415px;margin:4px 0 0 15px;">
			    <input type="hidden" id="password" name="" value = "000000" />
			    <input type="hidden" id="dateString" name="dateString" value="${dateString}" />
			    <input type="hidden" id="commuSource" name="commuSource" value='${commuSource}' />
			    <input type="hidden" id="curCommuId" name="curCommuId" value='' />
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
						<input id="telTip" type="hidden" value="输入手机号查询"/>
						<%-- 在线工单 --%>
						<c:if test='${opFlag=="1" }'>
						<input id="mobile" name="tel" type="text" onchange="queryMember();" 
							onfocus="if(this.value.trim()=='输入手机号查询'){this.value='';$(this).removeClass('tipText');}"
							onblur="if(this.value.trim()==''){this.value='输入手机号查询';$(this).addClass('tipText');}" 
							value="输入手机号查询" class="form-control tipText"  />
						</c:if>
						<%-- 来电弹屏 --%>
						<c:if test='${opFlag!="1" }'>
						<input id="mobile" name="tel" type="text" readonly="readonly" value="${member.tel}" class="form-control" />
						</c:if>
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
					<%-- <div class="td_item item_title2">说明：</div>
					<div class="td_item" style="width: 335px;">
						<textarea id="remark" name="comment" class="form-control" style="height:75px;">${member.comment}</textarea>
					</div> --%>
					<div class="td_item item_title2">回拨：</div>
					<div class="td_item" style="width: 30px; height: 30px;">
						<img class="img-thumbnail" title="回拨" alt="回拨" src="<%=request.getContextPath()%>/images/call.png"
					    	onerror="this.onerror=null;this.src='<%=request.getContextPath()%>/images/dial.png';"
					    	onclick="dialNumber();" style=" cursor: pointer" />
					</div>
					<div style="clear:both;"></div>
				</div>
			</div>
			<div style="float:left;width:310px;margin:4px 0 0 5px;">
				<div class="tr_inline">
					<div class="td_item item_title2">Email：</div>
					<div class="td_item" style="width:230px;">
						<input id="email" name="email" type="text" class="form-control" value="${member.email}" />
					</div>
					<div style="clear:both;"></div>
				</div>
				<div class="tr_inline">
					<div class="td_item item_title2">级别：</div>
					<div id="memberGrade" class="td_item" style="padding-top:10px;font-size:14px;">${memberGrade}</div>
					<div style="clear:both;"></div>
				</div>
				<div style="margin:15px 0 0 75px;">
					<button type="button" onclick="openRegMem();" class="btn btn-primary">注册官网会员</button>
					<button type="button" class="btn btn-primary" onclick="saveMemberInfo();" style="margin-left:8px;">保存客户信息</button>
				</div>
			</div>
			<div style="clear:both;"></div>
		</div>
		<hr style="margin:0 0 10px 0;"/>
		<div>
			<ul id="os_tablist" class="nav nav-tabs" role="tablist">
				<c:choose>
					<%-- 运营中心后台 --%>
					<c:when test='${callerAction=="callerScreen2.do" }'>
						<sec:authorize ifAnyGranted="/callerScreen2.do?method=getOfficalMember">
							<li class="active"><a href="javascript:void(0);">会员档案</a></li>
						</sec:authorize>
						<sec:authorize ifAnyGranted="/healthRecords.do?method=getHealthKeywordHean">
							<li><a href="javascript:void(0);">健康档案</a></li>
						</sec:authorize>
						<sec:authorize ifAnyGranted="/commu2.do?method=commuMain">
							<li><a href="javascript:void(0);">沟通记录</a></li>
						</sec:authorize>
						<sec:authorize ifAnyGranted="/outScreen2.do?method=addOrder">
							<li><a href="javascript:void(0);">新增订单</a></li>
						</sec:authorize>
						<sec:authorize ifAnyGranted="/callerScreen2.do?method=getOfficialOrder">
							<li><a href="javascript:void(0);">官网订单</a></li>
						</sec:authorize>
						<sec:authorize ifAnyGranted="/outScreen2.do?method=getBuyRecords">
							<li><a href="javascript:void(0);">购买记录</a></li>
						</sec:authorize>
					</c:when>
					<%-- 呼叫中心 --%>
					<c:otherwise>
						<li class="active"><a href="javascript:void(0);">会员档案</a></li>
						<li><a href="javascript:void(0);">健康档案</a></li>
						<li><a href="javascript:void(0);">沟通记录</a></li>
						<li><a href="javascript:void(0);">新增订单</a></li>
						<li><a href="javascript:void(0);">官网订单</a></li>
						<li><a href="javascript:void(0);">购买记录</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
			<div style="clear:both;"></div>
			<div style="border:1px solid #DDD;border-top:none;padding:10px;min-height: 200px;">
				<div id="tabDiv" style="overflow: auto;">
					<%-- <jsp:include page="official_order.jsp"/> --%>
				</div>
			</div>
		</div>
		<div style="margin-top:15px;">
			<div class="tr_inline">
			    <div class="td_item item_title">受理客服：</div>
				<div class="td_item item_title" id="callerCustServCode">${custServCode}</div>
			</div>
		</div>
	</div>
	<div style="clear: both;"></div> 
	
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
</body>
</html>