<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>极速达订单查询</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<!-- 加载CSS样式文件 -->
	<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-theme.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-select.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/js/bootstrap/iCheck/skins/flat/blue.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
	
	<!-- 加载javascript文件 -->
	<script src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap-select.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap/iCheck/icheck.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/WdatePicker/WdatePicker.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap/respond.min.js"></script>
	
<style type="text/css">
	table thead{background-color: #f9f9f9;}
	.tr_inline{width:100%;margin-bottom:7px;}
	.td_item {float:left;}
	.item_title {text-align: right;width: 90px;padding-top: 5px;margin-left: 15px;}
	.item_title2 {text-align: right;width: auto;padding-top: 5px;margin-left: 15px;font-size:14px;}
	.div_cont{width:200px;padding-left:10px;}
	.div_cont2{width:150px;}
</style>
<script type="text/javascript">
$(document).ready(function(){
	$("input.iCheckRadio").iCheck({
// 	    checkboxClass: 'icheckbox_flat-blue',
	    radioClass: 'iradio_flat-blue'
	});
	
	$('.selectpicker').selectpicker();
	$(".bootstrap-select").css("margin-bottom","0");
	
	$("#orderInfoTb input[name='orderRad']").on('ifChecked', function(){
		var orNum = $.trim($(this).parent().parent().parent().find("td:eq(1)").text());
		$.ajax({
			type: "POST",
            url: "orderInfo.do?method=getDetail",
            dataType: "json",
            data: {orderNumber:orNum},
            success: function(res){
            	var trs = "";
            	if(res!=null && res.length>0){
            		var det = null;
            		for(var i=0;i<res.length;i++){
            			det = res[i];
		            	trs +="<tr>"+
		            	"<td>"+det.orderNumber+"</td>"+
						"<td>"+det.productName+"</td>"+
						"<td>"+det.productId+"</td>"+
						"<td style='text-align: right;'>"+det.price+"</td>"+
						"<td style='text-align: right;'>"+det.count+"</td>"+
						"<td style='text-align: right;'>"+det.totalPrice+"</td>"+
						"</tr>";
            		}
            	}
           		$("#detTb tbody").html(trs);
            },
            error:function(){
            	$("#detTb tbody").html("请求处理失败！");
            }
		});
	});
});

//沟通记录跳页
function gotoPage(pageno){
	var num = $.trim($("#orderNumber").val());
	$("#orderNumber").val(num);
	$("#curPageno").val(pageno);
	$("#orderQueryForm").submit();
}
function gotoPageSize(pageSize){
	$("#curPageSize").val(pageSize);
	gotoPage(1);
}

//查询订单
function orderQuery(){
	gotoPage(1);
}

function openDeliv(thisObj){
	var num = $.trim($(thisObj).parent().parent().find("td:eq(1)").text());
	if(num==null || num.length==0){
		alert("未找到订单，请确认订单号后重新查询！");
	}else{
		$("#delivOrderNum").text(num);
		var country = $.trim($(thisObj).parent().parent().find("td:eq(6)").find("span[name='country']").text());
		$("#district").text(country);
		$("#deliverModal").modal("show");
	}
}

//发货
function deliver(){
	var num = $("#delivOrderNum").text();;
	if(num==null || num.length==0){
		alert("未找到订单，请确认订单号后重新查询！");
		return;
	}
	
	var expNo = $.trim($("#expNo").val());
	if(expNo==null || expNo.length==0){
		alert("请填写运单号！");
		return;
	}
	var district = $.trim($("#district").text());
	if(confirm("您确定要通知订单"+num+"的运单"+expNo+"发货吗？")){
		$.ajax({
			type:'post',
			url:'orderInfo.do?method=deliver',
			dataType:'json',
			data:{orderNumber:num,expressNo:expNo,district:district},
			success: function(result){
				if(result!=null && result.mesg!=null && result.mesg.length>0){
					var mesg = result.mesg;
					if(mesg=="success"){
						alert("发货通知成功");
					}else{
						alert(mesg);
					}
				}
				orderQuery();
			},
			error: function(){
				alert("发货通知处理失败！");
			}
		});
	}
}
//驳回
function reject(thisObj){
	var num = $.trim($(thisObj).parent().parent().find("td:eq(1)").text());
	if(num==null || num.length==0){
		alert("未找到订单，请确认订单号后重新查询！");
		return;
	}
	if(confirm("您确定要驳回订单"+num+"吗？")){
		$.ajax({
			type:'post',
			url:'orderInfo.do?method=reject',
			dataType:'json',
			data:{orderNumber:num},
			success: function(result){
				if(result!=null && result.mesg!=null && result.mesg.length>0){
					var mesg = result.mesg;
					if(mesg=="success"){
						alert("订单"+num+"驳回成功！");
					}else{
						alert(mesg);
					}
				}
				orderQuery();
			},
			error: function(){
				alert("订单"+num+"驳回失败！");
			}
		});
	}
}

</script>
</head>

<body>
	<div style="margin:0 15px;">
		<div>
			<h3>极速达订单查询</h3>
		</div>
		<div style="margin-top:20px;">
			<form id="orderQueryForm" action="orderInfo.do?method=getInfo" method="post">
				<input type="hidden" id="curPageSize" name="pageSize" value="${pageSize }"/>
				<input type="hidden" id="curPageno" name="pageno" value="${pageno }"/>
				<label class="td_item item_title2">订单日期：</label>
				<div class="td_item">
					<input id="startDate" name="startDate" value="${orderData.startDate }" type="text" 
						onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'});" 
						class="Wdate form-control item_input" style='height:30px;width:120px;float:left;'/>
					<span style="float:left;padding-top:5px;">&nbsp;&nbsp;—&nbsp;&nbsp;</span>
					<input id="endDate" name="endDate" value="${orderData.endDate }" type="text" 
						onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" 
						class="Wdate form-control item_input" style='height:30px;width:120px;float:left'/>
					<div class="clearfix"></div>
				</div>
				<label class="td_item item_title2">订单号：</label>
				<div class="td_item div_cont2">
					<input type="text" id="orderNumber" name="orderNumber" value="${orderData.orderNumber }" class="form-control input-sm"/>
				</div>
				<label class="td_item item_title2">联系电话：</label>
				<div class="td_item">
					<input type="text" id="tel" name="tel" value="${orderData.tel }" class="form-control input-sm" style="width:120px;"/>
				</div>
				<label class="td_item item_title2">订单状态：</label>
				<div class="td_item div_cont2">
					<select name="jdsOrderStatus" id="jdsOrderStatus" class="selectpicker form-control">
						<option value="">--请选择--</option>
						<option value="0" ${orderData.jdsOrderStatus=='0'?"selected='selected'":""}>初始化</option>
						<option value="1" ${orderData.jdsOrderStatus=='1'?"selected='selected'":""}>不在配送范围</option>
						<option value="2" ${orderData.jdsOrderStatus=='2'?"selected='selected'":""}>不在配送时间内</option>
						<option value="3" ${orderData.jdsOrderStatus=='3'?"selected='selected'":""}>已发货</option>
						<option value="4" ${orderData.jdsOrderStatus=='4'?"selected='selected'":""}>已驳回</option>
					</select>
				</div>
				<button type="button" onclick="orderQuery()" class="btn btn-primary btn-sm td_item" style="margin-left:15px;">查询</button>
				<div class="clearfix"></div>
			</form>
		</div>
		<div style="margin-top:5px;">
			<table id="orderInfoTb" class="table table-bordered">
				<thead>
					<th style="width:50px;">选择</th>
					<th style="width:120px;">订单编号</th>
					<th style="width:135px;">订单时间</th>
					<th style="width:70px;">订单金额</th>
					<th style="width:100px;">联系电话</th>
					<th style="width:70px;">收货人</th>
					<th>收货地址</th>
					<th style="width:70px;">状态</th>
					<th style="width:100px;">操作</th>
				</thead>
				<tbody>
					<c:forEach items="${orderInfoLs }" varStatus="status" var="orderInfo">
						<tr>
							<td><input type="radio" name="orderRad" class="iCheckRadio" /></td>
							<td>${orderInfo.orderNumber }</td>
							<td>${orderInfo.startTime }</td>
							<td style="text-align: right;">${orderInfo.orderPrice }</td>
							<td>${orderInfo.mobile }</td>
							<td>${orderInfo.receiver }</td>
							<td><span>${orderInfo.addressDetail }</span><span name="country">${orderInfo.country }</span></td>
							<td>
							<c:choose>
								<c:when test='${orderInfo.jdsOrderStatus=="0" }'>初始化</c:when>
								<c:when test='${orderInfo.jdsOrderStatus=="1" }'>不在配送范围 </c:when>
								<c:when test='${orderInfo.jdsOrderStatus=="2" }'>不在配送时间内 </c:when>
								<c:when test='${orderInfo.jdsOrderStatus=="3" }'>已发货</c:when>
								<c:when test='${orderInfo.jdsOrderStatus=="4" }'>已驳回</c:when>
							</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test='${orderInfo!=null && (orderInfo.jdsOrderStatus=="3" || orderInfo.jdsOrderStatus=="4") }'>
									<button type="button" onclick="openDeliv(this)" disabled="disabled" class="btn btn-success btn-xs">发货</button>
									<button type="button" onclick="reject(this)" disabled="disabled" class="btn btn-danger btn-xs">驳回</button>
									</c:when>
									<c:otherwise>
									<button type="button" onclick="openDeliv(this)" class="btn btn-success btn-xs">发货</button>
									<button type="button" onclick="reject(this)" class="btn btn-danger btn-xs">驳回</button>
									</c:otherwise>
								</c:choose>
								<div class="clearfix"></div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<ul class="pager">
				<%@ include file="/WEB-INF/view/common/page.jspf" %>
			</ul>
		</div>
		<div style="mrgin-top:15px;">
			<h4>订单详情</h4>
			<table id="detTb" class="table table-bordered">
				<thead>
					<tr>
						<th>订单编号</th>
						<th>商品名称</th>
						<th>商品编码</th>
						<th>商品单价</th>
						<th>商品数量</th>
						<th>商品总价</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${detailLs }" varStatus="status" var="item">
						<tr>
							<td>${item.orderNumber }</td>
							<td>${item.productName }</td>
							<td>${item.productId }</td>
							<td style="text-align: right;">${item.price }</td>
							<td style="text-align: right;">${item.count }</td>
							<td style="text-align: right;">${item.totalPrice }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div style="mrgin-top:15px;">
			<h4>药店信息</h4>
			<table class="table table-bordered" style="width:700px;">
				<thead>
					<tr>
						<th style="width:130px;">药店名称</th>
						<th style="width:250px;">药店地址</th>
						<th style="width:70px;">联系人</th>
						<th style="width:150px;">联系电话</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>好药师大药房马连道店</td>
						<td>西城区马连道路2-3</td>
						<td>李晓转</td>
						<td>13331038607 / 63468994</td>
					</tr>
					<tr>
						<td>好药师大药房东花市店</td>
						<td>东城区东花市大街2号院-3-2</td>
						<td>陈宏云</td>
						<td>13021928969 / 51005299</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div class="clearfix"></div>
	
	<div class="modal fade" id="deliverModal" tabindex="-1" role="dialog" aria-labelledby="deliverModalLabel" aria-hidden="true" data-backdrop='static'>
		<div class="modal-dialog" style="width:400px;">
			<div class="modal-content" >
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="deliverModalLabel">发货信息</h4>
				</div>
				<div class="modal-body">
					<div class="tr_inline">
						<div class="td_item item_title" style="padding-top:0;">订单号：</div>
						<div class="td_item div_cont" id="delivOrderNum"></div>
						<div style="clear: both;"></div> 
					</div>
					<div class="tr_inline">
						<div class="td_item item_title">运单号：</div>
						<div class="td_item div_cont">
							<input type="text" id="expNo" name="expNo" value="" class="form-control"/>
						</div>
						<div style="clear: both;"></div> 
					</div>
					<div class="tr_inline">
						<div class="td_item item_title" style="padding-top:0;">发货区域：</div>
						<div id="district" class="td_item div_cont"></div>
						<div style="clear: both;"></div> 
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
		       			<button type="button" class="btn btn-primary" onclick="deliver()">确定</button>  
					</div>
				</div>
			</div>
		</div> 
	</div>
</body>
</html>