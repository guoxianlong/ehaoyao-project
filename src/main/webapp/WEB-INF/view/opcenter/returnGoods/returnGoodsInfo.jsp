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
	<link href="<%=request.getContextPath()%>/js/bootstrap/iCheck/skins/flat/blue.css" rel="stylesheet">
	
    <style type="text/css" mce_bogus="1">
		th {
			text-align: center;
		}
		table th  
        {  
            white-space: nowrap; 
             
        } 
		input.item_input {height: 30px;}
		.item_title {text-align: right;width: 80px;padding-top: 5px;margin-left: -10px;}
		.item_win {text-align: left;padding-top: 7px;font-weight: bold;}
		.item_win_text {text-align: left;padding-top: 7px;}
		.sear_btn{margin-left: 5px; padding: 0 5px; height: 30px;}
		.select_set + div button.selectpicker{height:30px;padding-top:0;padding-bottom:0;}
		.tr_inline{width:100%;margin-bottom:7px;}
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
	<script src="<%=request.getContextPath()%>/js/bootstrap/iCheck/icheck.min.js"></script>
    
    <script>
    	var initHtml="<option value=''>--请选择--</option>";
    	String.prototype.trim = function(){
			return this.replace(/(^\s*)|(\s*$)/g, "");
		};
	    $(document).ready(function (){
	    	$("#orderDetail").hide();
	    	$("input.iCheckRadio").iCheck({
	    	    radioClass: 'iradio_flat-blue'
	    	});
	    	
	    	$("#orderInfo input[name='checkOrderId']").on('ifChecked', function(){
	    		var orNum = $.trim($(this).parent().parent().parent().find("td:eq(1)").text());
	    		getGoodsDetail(orNum);
	    	});
	    });
	    //查询按钮
	    function search(){
	    	gotoPage(1);
	    }
	    //翻页
	    function gotoPage(pageno){
	    	$("[name=pageno]").val(pageno);
	    	$("#dataForm").attr("action","<%=request.getContextPath()%>/returnGoods.do?method=getReturnGoodsInfo");
	    	$("#dataForm").submit();
	    }
	    function gotoPageSize(pageSize){
	    	$("#curPageSize").val(pageSize);
	    	gotoPage($("#curPage").val());
	    }
	    //查询明细数据
	    function getGoodsDetail(orderNumber){
	    	$("#orderDetail").show();
	    	$("#detailInfo").empty();
	    	if(orderNumber == null || orderNumber == ''){
	    		alert("所选订单的订单编号为空!");
	    		return;
	    	}
	    	$.ajax({
				type: "POST",
	            url: "returnGoods.do?method=getDetail",
	            dataType: "json",
	            data: {orderNumber:orderNumber},
	            success: function(data){
	    			var item = "";
	    			if(data != null && data.detailLs != null && data.detailLs.length > 0){
	    				$.each(data.detailLs,function(i,result){ 
		    			    item = "<tr>"+
		 		                       "<td>"+result.productId+"</td>"+
		 		                       "<td>"+result.productName+"</td>"+
		 		                       "<td style='text-align: right;'>"+result.count+"</td>"+
		 		                       "<td style='text-align: right;'>"+result.price+"</td>"+
		 		                       "<td style='text-align: right;'>"+result.totalPrice+"</td></tr>";
		    			});
	    			}else{
	    				item = "<td colspan='5' style='height:60px;text-align: center;vertical-align: middle;font-size:14px;font-weight: bold;'>暂无数据！</td>";
	    			}
    			    $("#detailInfo").append(item);
	    			
	    		},   
	    		error : function(){
	    			alert("获取退货订单明细加载失败,请重新选择订单");
	    		}
	    	});
	    }
	    //审核功能
	    function review(){
	    	var orderNumber = $("#returnOrderNumber").text();
	    	var amount = $("#returnOrderAmount").text();
	    	var reason = $("#reason").val();
	    	var reasonTxt = $("#reason").find("option:selected").text();
	    	var remark = $("#remark").val();
	    	if(orderNumber == null || orderNumber == ''){
	    		alert("所选订单的订单编号为空!");
	    		return;
	    	}
	    	if(remark == null || remark.trim() == ""){
	    		remark = reasonTxt;
	    	}
	    	$.ajax({
				type: "POST",
	            url: "returnGoods.do?method=review",
	            dataType: "text",
	            data: {orderNumber:orderNumber,amount:amount,reason:reason,remark:remark},
	            success: function(data){
	    			if(data != null && data.trim() != ""){
	    				alert(data);
	    			}else{
	    				$('#model').modal('hide');
	    				gotoPage(1);
	    			}
	    		},   
	    		error : function(){
	    			alert("获取退货订单明细加载失败,请重新选择订单");
	    		}
	    	});
	    }
	    //打开审核对话框
	    function openWin(orderNumber,amount){
	    	$("#returnOrderNumber").text(orderNumber);
	    	$("#returnOrderAmount").text(amount);
	    	$("#reason").val("");
	    	$("#remark").val("");
	    	$('#model').modal('show');
	    }
	    //打开驳回对话框
	    function openRejectWin(orderNumber,amount){
	    	$("#returnOrderNumber1").text(orderNumber);
	    	$("#returnOrderAmount1").text(amount);
	    	$("#reason1").val("");
	    	$("#remark1").val("");
	    	$('#rejectModel').modal('show');
	    }	
	    //驳回功能
	    function reject(){
	    	var orderNumber = $("#returnOrderNumber1").text();
	    	var amount = $("#returnOrderAmount1").text();
	    	var reason = $("#reason1").val();
	    	var remark = $("#remark1").val();
	    	if(orderNumber == null || orderNumber == ''){
	    		alert("所选订单的订单编号为空!");
	    		return;
	    	}
	    	$.ajax({
				type: "POST",
	            url: "returnGoods.do?method=reject",
	            dataType: "json",
	            data: {orderNumber:orderNumber,amount:amount,reason:reason,remark:remark},
	            success: function(data){
	    			alert("驳回成功！");
	    			$('#rejectModel').modal('hide');
	    			gotoPage(1);
	    		},   
	    		error : function(){
	    			alert("获取退货订单明细加载失败,请重新选择订单");
	    		}
	    	});
	    }
    </script>
</head>
<body>
    <div id="base" style="height: 100%;">
	    <div class="panel panel-default">
	        <div class="panel-heading">
                <h5 class="panel-title" style="color:#31849b;">退货审核</h5>
            </div>
			<div class="panel-body">
		        <form class="well" method="post" id="dataForm">
					<input type="hidden" id="curPage" name="pageno" value="${pageno}"/>
					<input type="hidden" id="curPageSize" name="pageSize" value="${pageSize}"/>
					<input type="hidden" id="totalRecords" name="totalRecords" value="${recTotal}"/>
					<div class="tr_inline" style="margin-bottom:1px;">
						<div class="right_input item_title">订单日期：</div>
						<div class="right_input" style="width: 120px;">
							<input id="startDate" name="startDate" type="text" value="${vo.startDate}" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'});" class="form-control item_input"/>
						</div>
						<div class="right_input">&nbsp;_&nbsp;</div>
						<div class="right_input" style="width: 120px;">
							<input id="endDate" name="endDate" type="text" value="${vo.endDate}" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" class="form-control item_input"/>
						</div>
						<div class="right_input item_title">订单号：</div>
						<div class="right_input" style="width: 180px;">
							<input type="text" class="form-control item_input" id="orderNumber" name="orderNumber" value="${vo.orderNumber}" />
						</div>
						<div class="right_input item_title">手机号：</div>
						<div class="right_input" style="width: 140px;">
							<input type="text" class="form-control item_input" id="mobile" name="mobile" value="${vo.mobile}" />
						</div>
						<button class="btn btn-primary sear_btn" id="btn_search" onclick="search()" style="margin-right: 340px;">&nbsp;&nbsp;&nbsp;&nbsp;查询&nbsp;&nbsp;&nbsp;&nbsp;</button>
						<div style="clear:both;"></div>
					</div>
				</form>
				<div class="table_head">
					<span style="float:left;margin:5px 0 0 19px;font-size:13px;color:white">退货订单</span>
				</div>
				<div>
					<table class="table table-striped table-bordered" id="orderInfo">
						<tr>
							<th class="th">序号</th>
							<th class="th">订单号</th>
							<th class="th">订单日期</th>
							<!-- <th class="th">订单类型</th> -->					
							<th class="th">姓名</th>
							<th class="th">电话</th>
							<th class="th">地址</th>
							<th class="th">订单金额</th>
							<th class="th">备注</th>
							<th class="th">支付方式</th>
							<!-- <th class="th">订单状态</th> -->
							<!-- <th class="th">处方药</th> -->
							<th class="th">付款状态</th>
							<th class="th">付款金额</th>
							<!-- <th class="th">客服工号</th>
							<th class="th">审核状态</th>
							<th class="th">审核人</th> -->
							<th>操作</th>
						</tr>
						<c:if test="${goodsInfoLs != null && fn:length(goodsInfoLs)>0}">
						<c:forEach items="${goodsInfoLs}" var="items" varStatus="status">
							<tr>
								<td class="th" style="vertical-align: middle;">
									<input type="radio" name="checkOrderId" id="id" class="iCheckRadio" />
								    ${status.index + 1}
								</td>
								<td class="th" style="vertical-align: middle;">${items.orderNumber}</td>
								<td class="th" style="vertical-align: middle;">${items.startTime}</td>
								<td class="th" style="vertical-align: middle;">${items.receiver}</td>
								<td class="th" style="vertical-align: middle;">${items.mobile}</td>
								<td class="th" style="text-align: left;vertical-align: middle;">${items.address}</td>
								<td class="th" style="text-align: right;vertical-align: middle;">${items.price}</td>
								<td class="th" style="vertical-align: middle;">${items.remark}</td>
								<td class="th" style="text-align: middle;vertical-align: middle;">${items.payType}</td>
								<td class="th" style="vertical-align: middle;">
								    <c:if test="${items.payType == '货到付款'}">未支付</c:if>
								    <c:if test="${items.payType == '在线支付'}">已支付</c:if>
								</td>
								<td class="th" style="text-align: right;vertical-align: middle;">
								    <c:if test="${items.payType == '货到付款'}">0</c:if>
								    <c:if test="${items.payType == '在线支付'}">${items.orderPrice}</c:if>
								</td>
								<td>
								    <div class="btn-group">
										<button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown">
										        操作
										    <span class="caret"></span>
										</button>
										<ul class="dropdown-menu" style="min-width:75px;" >
										    <sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_AuditOrder">
										    	<li><a href="#" onclick="openWin('${items.orderNumber}','${items.price}');" 
										    	style="padding-left:8px;padding-right: 0px;" data-toggle="modal">审核</a></li>
												<li><a href="#" style="padding-left:8px;padding-right: 0px;" data-toggle="modal" 
												onclick="openRejectWin('${items.orderNumber}','${items.price}');">驳回</a></li>
											</sec:authorize >
										</ul>
									</div>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</table>
				<c:if test="${goodsInfoLs == null or fn:length(goodsInfoLs)<=0}">
				    <div style="height:100px;text-align: center;vertical-align: middle;font-size:14px;font-weight: bold;">
				    	暂无数据！
				    </div>
				</c:if>
				<c:if test="${goodsInfoLs != null && fn:length(goodsInfoLs)>0}">
					<ul class="pager">
						<%@ include file="/WEB-INF/view/common/page.jspf" %>
					</ul>
				</c:if>
				</div>
				<div><p>&nbsp;</div>
				<div id="orderDetail" style="width: 80%;">
					<div class="table_head">
						<span style="float:left;margin:5px 0 0 19px;font-size:13px;color:white">退货订单明细</span>
					</div>
				    <table id="orderGoods" class="table table-striped table-bordered">
				    	<thead>
						<tr>
							<th>商品编码</th>
							<th>商品名称</th>
							<!-- <th>规格</th>
							<th>品牌</th> -->
							<th>数量</th>
							<th>单价</th>
							<th>金额</th>
							<!-- <th>是否赠品</th>
							<th>处方类型</th> -->
						</tr>
						</thead>
						<tbody id="detailInfo">
						</tbody>
				    </table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<div class="modal fade" id="model" tabindex="-1" role="dialog" aria-labelledby="deliverModalLabel" aria-hidden="true" data-backdrop='static'>
	<div class="modal-dialog" style="width:480px;">
		<div class="modal-content" >
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">退货审核</h4>
			</div>
			<div class="modal-body">
				<div class="tr_inline">
					<div class="right_input item_win" style="padding-top:0;">订单号码：</div>
					<div class="right_input item_win_text" style="padding-top:0;" id="returnOrderNumber"></div>
					<div style="clear: both;"></div> 
				</div>
				<div class="tr_inline">
					<div class="right_input item_win">退款金额：</div>
					<div class="right_input item_win_text" id="returnOrderAmount"></div>
					<div style="clear: both;"></div> 
				</div>
				<div class="tr_inline">
					<div class="right_input item_win">退货原因：</div>
					<div class="right_input" style="width: 200px;margin-bottom: 1px;">
						<select name="platformId" style="height: 33px;" id="reason" data-bvalidator="required,required" placeholder="所属应用平台" class="selectpicker form-control select_set">
							<option value="OUTER_PACKAGE_BROKEN"}>外包装破损</option>
							<option value="INNER_PACKAGE_BROKEN"}>内包装破损</option>
							<option value="DELIVERY_TIMEOUT"}>送货超时</option>
							<option value="DELIVERY_MISTAKE"}>发错货</option>
							<option value="DELIVERY_MISSING"}>漏发货</option>
							<option value="ADVERSE_REACTION"}>服用后有不良反应</option>
							<option value="OVERSOLD"}>超卖</option>
							<option value="OTHER_REASON"}>其它原因</option>
						</select>
					</div>
					<div style="clear: both;"></div> 
				</div>
				<div class="tr_inline">
					<div class="right_input item_win">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;备注：</div>
					<div class="right_input" style="width: 200px;margin-bottom: 1px;">
						<textarea class="right_input" style="width: 350px;" id="remark" rows="4"></textarea>
					</div>
					<div style="clear: both;"></div> 
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	       			<button type="button" class="btn btn-primary" onclick="review()">确定</button>  
				</div>
			</div>
		</div>
	</div> 
</div>

<div class="modal fade" id="rejectModel" tabindex="-1" role="dialog" aria-labelledby="deliverModalLabel" aria-hidden="true" data-backdrop='static'>
	<div class="modal-dialog" style="width:480px;">
		<div class="modal-content" >
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">退货驳回</h4>
			</div>
			<div class="modal-body">
				<div class="tr_inline">
					<div class="right_input item_win" style="padding-top:0;">订单号码：</div>
					<div class="right_input item_win_text" style="padding-top:0;" id="returnOrderNumber1"></div>
					<div style="clear: both;"></div> 
				</div>
				<div class="tr_inline">
					<div class="right_input item_win">退款金额：</div>
					<div class="right_input item_win_text" id="returnOrderAmount1"></div>
					<div style="clear: both;"></div> 
				</div>
				<div class="tr_inline">
					<div class="right_input item_win">驳回原因：</div>
					<div class="right_input" style="width: 200px;margin-bottom: 1px;">
						<input type="text" id="reason1" class="form-control"/>
					</div>
					<div style="clear: both;"></div> 
				</div>
				<div class="tr_inline">
					<div class="right_input item_win">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;备注：</div>
					<div class="right_input" style="width: 200px;margin-bottom: 1px;">
						<textarea class="right_input" style="width: 350px;" id="remark1" rows="4"></textarea>
					</div>
					<div style="clear: both;"></div> 
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	       			<button type="button" class="btn btn-primary" onclick="reject()">确定</button>  
				</div>
			</div>
		</div>
	</div> 
</div>