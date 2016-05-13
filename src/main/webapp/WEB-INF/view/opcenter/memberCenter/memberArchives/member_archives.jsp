<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1 maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>会员档案</title>
    
    <!-- 加载CSS样式文件 -->
	<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-select.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-theme.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
    
    <!-- 加载javascript文件 -->
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap/bootstrap-select.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/WdatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/citySelect.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/mask.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap/respond.min.js"></script>
    
    <script>
	    $(document).ready(function(){
	    	//alert("33");
	    	/** select下拉框事件 */
	    	$('.selectpicker').selectpicker();
	    	/** 初始化选项卡 */
	    	//var _city = $("#city_hi").val(); 
	    	//set_city(document.getElementById('province'),document.getElementById('city'));
	    	//$('#city').selectpicker('val', _city);
	    	tabsClick("tabs-1");
	    });
	
	    /** 选项卡事件 */
	    function tabsClick(id){
	    	$('ul li').removeClass("active");
	    	var url = "";
	    	var data = {};
	    	if(id == "tabs-1"){
	    		url = "outScreen2.do?method=getCommunication";
	    		data.tel="${member.tel}";
	    	}
	    	if(id == "tabs-2"){
	    		$("body").mask("Loading , please waite ...");
	    		url = "outScreen2.do?method=getBuyRecords";
	    		data.userName=encodeURI("${member.memberName}");
	    		data.telephoneNo="${member.tel}";
	    		data.timeNum = "3";
	    	}
	    	if(id == "tabs-3"){
	    		url = "healthRecords.do?method=getHealthKeywordHean";
	    		data.tel = $("#tel").html();
	    	}
	    	if(id == "tabs-4"){
	    		url = "outScreen.do?method=";
	    	}
	    	$.ajax({
	    		type: 'post',
	    		url: url, 
	    		dataType: 'html', 
	    		data : data,
	    		success:function(res){
	    			$("#"+id).addClass("active");
	    			$("#tab-pane").html(res);
	    			$("body").unmask();
	    		},
	    		error:function(){
	    			$("body").unmask();
	    		}
	    	});
	    }
    </script>
    
</head>
<body>
	<div id="base" class="container-fluid">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h5 class="panel-title" style="color: #31849b;">会员信息查询</h5>
			</div>
			<div class="panel-body">			    
			    <div class="form-horizontal">
			        <div class="col-sm-2 form-group">
						<div>
							<img alt="" src="<%=request.getContextPath()%>/img/head.jpg">
						</div>
					</div>
			        <div class="col-sm-10 form-group">
						<div class="col-sm-3">
							<label class="col-sm-4 control-label">姓名</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" value="${member.memberName}" readonly="readonly" />
							</div>
						</div>
						<div class="col-sm-3">
						    <label class="col-sm-5 control-label">手机号</label>
			                <p class="col-sm-7 form-control-static" id="tel">${member.tel}</p>
						</div>
						<div class="col-sm-6">
							<label class="control-label" style="padding-left: 5%;float: left;">QQ</label>
							<div class="col-sm-8" style="width: 80%;padding-left: 8%;">
								<input type="text" class="form-control" value="${member.qq}" readonly="readonly" />
							</div>
						</div>
					</div>
					<div class="col-sm-10 form-group">
			            <div class="col-sm-3">
							<label class="col-sm-4 control-label">性别</label>
							<div class="col-sm-8">
								<label class="radio-inline"> 
								    <input type="radio" name="inlineRadioOptions" id="inlineRadio1" value="1" ${"1"==member.sex?'checked="checked"':''}>男
								</label>
								<label class="radio-inline"> 
								    <input type="radio" name="inlineRadioOptions" id="inlineRadio2" value="2" ${"2"==member.sex?'checked="checked"':''}>女
								</label>
							</div>
						</div>
			            <div class="col-sm-3">
							<label class="col-sm-5 control-label">年龄</label>
							<div class="col-sm-6">
								<select class="selectpicker" data-width="auto" data-size="10"
									id="orderFlag">
									<option value="1"
										${member.age>=0 && member.age <= 10 ?'selected="selected"':''}>0-10岁</option>
									<option value="2"
										${member.age>=11 && member.age <= 20 ?'selected="selected"':''}>11-20岁</option>
									<option value="3"
										${member.age>=21 && member.age <= 30 ?'selected="selected"':''}>21-30岁</option>
									<option value="4"
										${member.age>=31 && member.age <= 40 ?'selected="selected"':''}>31-40岁</option>
									<option value="5"
										${member.age>=41 && member.age <= 50 ?'selected="selected"':''}>41-50岁</option>
									<option value="6"
										${member.age>=51 && member.age <= 60 ?'selected="selected"':''}>51-60岁</option>
									<option value="7"
										${member.age>=61 ?'selected="selected"':''}>60岁以上</option>
								</select>
							</div>
						</div>
			            <div class="col-sm-6">
							<label class="control-label" style="float: left;padding-left: 3%;">邮箱</label>
							<div class="col-sm-10" style="width: 80.1%;padding-left: 8%;">
								<input type="text" class="form-control" value="${member.email}" readonly="readonly" />
							</div>
						</div>
			        </div>
			        <div class="col-sm-10 form-group">
			            <div class="col-sm-3">
							<label class="col-sm-4 control-label">状态</label>
							<div class="col-sm-8">
								<select class="selectpicker" data-width="100%" data-size="5" id="orderFlag">
									<option value="1" ${member.memberStatus==1?'selected="selected"':''}>活跃</option>
									<option value="2" ${member.memberStatus==2?'selected="selected"':''}>休眠</option>
								</select>
							</div>
						</div>
			            <div class="col-sm-3">
							<label class="col-sm-5 control-label">态度</label>
							<div class="col-sm-6">
								<select class="selectpicker" data-width="auto" data-size="10"
									id="orderFlag">
									<option value="1" ${member.attitude==1?'selected="selected"':''}>拒访</option>
									<option value="2" ${member.attitude==2?'selected="selected"':''}>接收短信</option>
									<option value="3" ${member.attitude==3?'selected="selected"':''}>接收邮件</option>
									<option value="4" ${member.attitude==4?'selected="selected"':''}>积极</option>
								</select>
							</div>
						</div>
						<div class="col-sm-5">
							<label class="col-sm-2 control-label">省</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" value="${member.province}" readonly="readonly" />
								<%-- <SELECT name="sheng" id="province" onchange="set_city(this, document.getElementById('city')); WYL();" class="login_text_input selectpicker" data-width="auto" data-size="10"> 
									<option value=0>请选择</option> 
									<option ${member.province=='上海市'?'selected="selected"':''}value=上海 >上海</option> 
									<option ${member.province=='天津'?'selected="selected"':''}value=天津>天津</option> 
									<option ${member.province=='重庆'?'selected="selected"':''}value=重庆>重庆</option> 
									<option ${member.province=='河北省'?'selected="selected"':''}value=河北省>河北省</option> 
									<option ${member.province=='山西省'?'selected="selected"':''}value=山西省>山西省</option> 
									<option ${member.province=='辽宁省'?'selected="selected"':''}value=辽宁省>辽宁省</option> 
									<option ${member.province=='吉林省'?'selected="selected"':''}value=吉林省>吉林省</option> 
									<option ${member.province=='黑龙江省'?'selected="selected"':''}value=黑龙江省>黑龙江省</option> 
									<option ${member.province=='江苏省'?'selected="selected"':''}value=江苏省>江苏省</option> 
									<option ${member.province=='浙江省'?'selected="selected"':''}value=浙江省>浙江省</option> 
									<option ${member.province=='安徽省'?'selected="selected"':''}value=安徽省>安徽省</option> 
									<option ${member.province=='福建省'?'selected="selected"':''}value=福建省>福建省</option> 
									<option ${member.province=='江西省'?'selected="selected"':''}value=江西省>江西省</option> 
									<option ${member.province=='山东省'?'selected="selected"':''}value=山东省>山东省</option> 
									<option ${member.province=='河南省'?'selected="selected"':''}value=河南省>河南省</option> 
									<option ${member.province=='湖北省'?'selected="selected"':''}value=湖北省>湖北省</option> 
									<option ${member.province=='湖南省'?'selected="selected"':''}value=湖南省>湖南省</option> 
									<option ${member.province=='广东省'?'selected="selected"':''}value=广东省>广东省</option> 
									<option ${member.province=='海南省'?'selected="selected"':''}value=海南省>海南省</option> 
									<option ${member.province=='四川省'?'selected="selected"':''}value=四川省>四川省</option> 
									<option ${member.province=='贵州省'?'selected="selected"':''}value=贵州省>贵州省</option> 
									<option ${member.province=='云南省'?'selected="selected"':''}value=云南省>云南省</option> 
									<option ${member.province=='陕西省'?'selected="selected"':''}value=陕西省>陕西省</option> 
									<option ${member.province=='甘肃省'?'selected="selected"':''}value=甘肃省>甘肃省</option> 
									<option ${member.province=='青海省'?'selected="selected"':''}value=青海省>青海省</option> 
									<option ${member.province=='内蒙古'?'selected="selected"':''}value=内蒙古>内蒙古</option> 
									<option ${member.province=='广西'?'selected="selected"':''}value=广西>广西</option> 
									<option ${member.province=='西藏'?'selected="selected"':''}value=西藏>西藏</option> 
									<option ${member.province=='宁夏'?'selected="selected"':''}value=宁夏>宁夏</option> 
									<option ${member.province=='新疆'?'selected="selected"':''}value=新疆>新疆</option> 
									<option ${member.province=='香港'?'selected="selected"':''}value=香港>香港</option> 
									<option ${member.province=='澳门'?'selected="selected"':''}value=澳门>澳门</option> 
									<option ${member.province=='台湾'?'selected="selected"':''}value=台湾>台湾</option> 
								</SELECT> --%>
							</div>
							<label class="col-sm-2 control-label">市区</label>
							<div class="col-sm-4">
								<!-- <select id="city" class="selectpicker" data-width="auto" data-size="10"> 
									<option value=0>请选择</option> 
								</select>  -->
								<input type="text" class="form-control" value="${member.city}" readonly="readonly" />
								<input type="hidden" value="${member.city}" id="city_hi"/>
							</div>
						</div>
					</div>
					<div class="col-sm-2 form-group"></div>
			        <div class="col-sm-10 form-group">
			            <div style="width: 50%;">
							<label class="control-label" style="width:16.666667%;float: left;text-align: center;padding-left: 6%;">说明</label>
							<div class="col-sm-10" style="padding-left: 4%;">
								<textarea class="form-control" rows="2" readonly="readonly">${member.comment}</textarea>
							</div>
						</div>
			            <div class="col-sm-5" style="width: 45.2%; padding-left: 1%;">
							<label class="col-sm-2 control-label">地址</label>
							<div class="col-sm-10">
								<textarea class="form-control" rows="2" readonly="readonly">${member.address}</textarea>
							</div>
						</div>
			        </div>
			    </div>
			</div>
			<div class="panel-body">
				<div class="tabbable portlet-tabs" style="padding: 10px 10px;">
					<ul class="nav nav-tabs">
						<li class="active" id="tabs-1"><a href="javascript:tabsClick('tabs-1')">沟通历史</a></li>
						<li id="tabs-2"><a href="javascript:tabsClick('tabs-2')">订单记录</a></li>
						<li id="tabs-3"><a href="javascript:tabsClick('tabs-3')">健康状况</a></li>
<%--						<li id="tabs-4"><a href="javascript:tabsClick('tabs-4')">退款，投诉记录</a></li> --%>
					</ul>
					<div class="tab-content" style="border-left:1px solid #ddd;border-bottom:1px solid #ddd;border-right:1px solid #ddd;">
						<div class="panel-body tab-pane active" id="tab-pane"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>