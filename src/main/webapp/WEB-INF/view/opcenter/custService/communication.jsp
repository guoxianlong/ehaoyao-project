<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet"> --%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/opcenter/commuType.js"></script>
<style type="text/css">
.table thead tr th{font-size:14px;background-color: #f9f9f9;}
input.item_input {height: 30px;}
.item_title {text-align: right;width: auto;padding-top: 5px;margin-left: 15px;}
.item_title4 {width: 70px;text-align: right;padding-top: 5px;}
.sear_btn{margin-left: 20px; padding: 0 12px; height: 30px;}
.select_set + div button.selectpicker{height:30px;padding-top:0;padding-bottom:0;}

.msg{
 	z-index: 9999;
    position: fixed;
    left: 80px;
    top: 300px;
    width: 120px;
    min-height:40px;
	background-color: #03C440;
    opacity: .8;
    border-radius: 5px;
    text-align: center;
    color: #fff;
    font-size: 14px;
    border:1px solid #47A447;
    padding:10px;
    }
</style>
<script type="text/javascript">
String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
};

$(document).ready(function(){
	//类型
	commuType($("#screenType").val(),"#acceptResult");
	$('.selectpicker').selectpicker();
	$("#selectdiv .select_set").css("margin-bottom","0");
	
});

//翻页
function gotoPage(pageno){
	var ctel = $("#communicatTel").val().trim();
	var pageSize = $("#curPageSize").val().trim();
	var startDate = $("#startDate").val().trim();
	var endDate = $("#endDate").val().trim();
	var actionName = $("#actionName").val().trim();
	$.ajax({
		type: "POST",
		url: "<%=request.getContextPath()%>/"+
		actionName+"?method=getCommuLs",
        dataType: "html",
        data: {tel:ctel,pageno:pageno,pageSize:pageSize,startDate:startDate,endDate:endDate},
        success: function(data){
        	con_tel_show();
       		$("#commuLsDiv").html(data);
        }
	});	
}
function gotoPageSize(pageSize){
	$("#curPageSize").val(pageSize);
	gotoPage($("#curPage").val());
}

//保存沟通记录
function saveCom(){
	var param = {};
	param.id = $("#communicatId").val();
	var ctel = $("#communicatTel").val().trim();
	if(ctel==null || ctel==""){
		alert("手机号不能为空！");
		return;
	}
	param.tel = ctel;
	
	var aresult = $("#acceptResult").val();
	var screenType = $("#screenType").val();
	if(aresult==null || aresult==""){
		alert("请选择类型！");
		return;
	}
	param.acceptResult = aresult;
	if(screenType!="1"){//来电类型
		var stype = $("#secondType").val();
		param.secondType = stype;
	}
	
	var remark = $("#commRemark").val().trim();
	$("#commRemark").val(remark);
	if(remark.length>200){
		alert("备注内容不能超过200字符！");
		return;
	}
	param.remark = remark;
	
	var actionName = $("#actionName").val().trim();
	var url = "<%=request.getContextPath()%>/"+actionName+"?method=saveCommunicat";
	$.ajax({
		type: "POST",
		url: url,
        dataType: "json",
        data: param,
        success: function(data){
        	if(data.code=="0"){
	       		$("#communicatId").val(data.cmId);
	       		$("#mesg").html("保存成功！");
        	}else if(data.code=="1"){
	       		$("#mesg").html("会员不存在！"); 
        	}else if(data.code=="2"){
        		$("#mesg").html("手机号不能为空！"); 
        	}else{
        		$("#mesg").html("保存失败！"); 
        	}
        	$("#mesg").fadeIn(1000).delay(1000).fadeOut(1000); 
        },
        error: function(){
        	$("#mesg").html("保存失败！").fadeIn(1000).delay(1000).fadeOut(1000); 
        }
	});
	
}

</script>

<div style="min-width: 1000px;">
	<div style="float:left;width:22%;max-width: 255px;">
		<input type="hidden" id="screenType" value="${screenType}" />
		<form id="dateForm" name="dateForm" action="" method="post" style="width:240px;">
			<input type="hidden" id="communicatId" name="id"/>
			<input type="hidden" id="actionName" value="${actionName}"/> 
			<input type="hidden" id="communicatTel" value="${member.tel}" />
			<div class="right_inline">
				<div class="right_input item_title4">
				<c:choose>
					<c:when test='${screenType=="1"}'>受理结果：</c:when>
					<c:otherwise>类型：</c:otherwise>
				</c:choose>
				</div>
				<div id="selectdiv" class="right_input" style="width: 155px;">
					<div>
					<select id="acceptResult" name="acceptResult" ${screenType!="1"?"onchange=selType($(this),&#39;#secondType&#39;)":""}
						 class="selectpicker form-control select_set" >
						<%-- <option value="">--请选择--</option>
						<c:choose>
							<c:when test='${screenType=="1"}'>
								<option value="1">订购</option>
								<option value="2">考虑</option>
								<option value="3">反感</option>
								<option value="4">关机</option>
								<option value="5">空号</option>
								<option value="6">无应答</option>
							</c:when>
							<c:otherwise>
								<option value="7">非处方药产品订购</option>
								<option value="8">处方药产品订购</option>
								<option value="9">订单查询</option>
								<option value="10">退换货</option>
								<option value="11">投诉</option>
							</c:otherwise>
						</c:choose> --%>
					</select>
					</div>
					<c:if test='${screenType!="1"}'>
					<div style="margin-top:5px;">
						<select id="secondType" name="secondType" class="selectpicker form-control select_set">
							<option value="">--请选择--</option>
						</select>
					</div>
					</c:if>
				</div>
			</div>
			<div class="right_inline">
				<div class="right_input item_title4">备注：</div>
				<div class="right_input" style="width: 155px;">
					<textarea id="commRemark" name="remark" class="form-control" rows="6"></textarea>
				</div>
			</div>
			<div class="right_inline" style="padding-left:140px;">
				<button type="button" onclick="saveCom()" class="btn btn-success sear_btn">保存</button>
			</div>
			<div style="clear:both;"></div>
		</form>
	</div>
	<div id="commuLsDiv" style="float:left;width:77%;min-height:300px;margin-left:10px;padding:10px;border:1px solid #DDD;">
		<jsp:include page="commu_ls.jsp"/>
	</div>
	<div style="clear:both;"></div>
	<div id="mesg" class="msg" style="display: none;"></div>
</div>
