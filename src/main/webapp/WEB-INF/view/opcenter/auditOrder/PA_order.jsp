<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1 maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>平安健康订单管理</title>
    
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
		.lab{text-align:left;margin-left: 1px;}
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
	
</head>
<body>
	<!-- 方法 -->
	<script type="text/javascript">
	/* 隐藏商品明细 */
	  $(document).ready(function(){
	   /*  	$.getScript("http://design.ehaoyao.com/mall/js/commonArea.js",function(){
	    		setProvince();
	    	});
	    	$('.selectpicker').selectpicker(); */
	    	$("#orderDetail").hide();
	    });
	/* 查询事件 */
	function gotoPage(pageNo){
   		var orderType = $("#orderType").val().trim();
   		var pageSize = $("#curPageSize").val().trim();
   		var startDate = $("#startDate").val().trim();
   		var endDate = $("#endDate").val().trim();
    	$.ajax({
    		type : "POST",
    		url : "<%=request.getContextPath()%>/${auditAction}?method=getPAOrder",
    		dataType: "html",
    		data : {startTime:startDate,
    			    endTime:endDate,
    			    orderType:orderType,
    			    pageIndex:pageNo,
    			    pageSize:pageSize},
    		success: function(data){
    			$("#base").parent().html(data);
           		//$("#maskDiv").unmask();
    		},
    		error: function() {
    			$("#base").parent().html("");
            	//$("#maskDiv").unmask();
    		}
    	});
    }
	/* 更改显示的记录数 */
	function gotoPageSize(pageSize){
    	$("#curPageSize").val(pageSize);
    	$("#curPage").val(1);
    	gotoPage(1);
    }
	
	   /**获取商品明细*/
    function showGoodsDetail(bizOrderId){
		$("#orderDetail").show();
		$("#orderDetail").children().hide();
    	$("#orderGoods"+bizOrderId).show();
    }
	 /** 审核订单显示 */
    function auditOrderView(orderSn,addTime,goodsAmount,receiveName,tel,address,sex,age){
		$('#auditOrderModal').modal('show');
/* 		document.getElementById("add_reserve").style.display="none"; */
 		//订单号
		$("#auditOrderSn").text(orderSn);
		//订单创建时间
		$("#auditAddTime").text(addTime);
		//客户姓名
		$("#auditCustomerName").text(receiveName);
		//客户手机号
		$("#auditCustomerMobile").text(tel);
		//客户地址
		$("#auditCustomerAddress").text(address);
		//客户性别
		if(sex=="1"){
			$("#auditCustomerSex").text("男");			
		}else if(sex=="0"){
			$("#auditCustomerSex").text("女");
		}else{
			$("#auditCustomerSex").text("未知");
		}
		//客户年龄
		$("#auditCustomerAge").text(age);
/* 		$("#memberName_win").val(receiveName);
		$("#tel_win").val(tel); */
/* 		$("#custServiceNo_win").val(createAdminUser); */
		//订单总金额
		$("#auditGoodsAmount").text(goodsAmount+"元");
		$("#auditOrderGoods tr:gt(0)").remove();
	 	var OrderDetailDOListJson='${OrderDetailDOListJson}';
		//解析订单集合json字符串
		var json = eval('('+OrderDetailDOListJson+')'); 
		//遍历订单集合
		for(var i=0; i<json.length; i++){
		//获得订单详情对象
			var bizOrderDetailDO=json[i].bizOrderDetailDO;
			//通过订单详情对象获取订单id
			var OrderId=bizOrderDetailDO.bizOrderId;
			/* 获取需要审核的订单对象，得到审核订单的药品清单集合 */
			if(orderSn==OrderId){
				var data=bizOrderDetailDO.drugDOList;
				if(data==null){
					alert("获取订单中无药品清单！,请重新选择新的订单");
	       			$('#auditOrderModal').modal('hide');
				}
			    var item;
				$.each(data, function(i, result){
				    item = "<tr>"+
			                    "<td>"+result.itemId+"</td>"+
			                    "<td>"+result.referId+"</td>"+
			                    /* "<td>"+result.itemImage+"</td>"+ */
			                    "<td style='word-break: break-all; word-wrap:break-word;'>"+result.itemName+"</td>"+
			                    "<td>"+result.standard+"</td>"+
			                    "<td>"+result.buyAmount+"</td>"+
			         "</tr>";
				    $("#auditOrderGoods").append(item);
				});
				break;
			}
		}		
    }
    /** 审核订单*/
    function auditOrder(type,orderCode){
    	/* var orderSn;
    	if(type == 2){
    		orderSn = orderCode;
    	} else {
    		orderSn = $("#auditOrderSn").text();
    	} */
/*     	var operatorAccount = "${custServCode}"; */
    	var orderSn= $("#auditOrderSn").text();
    	var auditRemark = $("#auditRemark").val();
    	if(auditRemark == ""||auditRemark==null){
    		alert("请填写好审批说明！！");
    		return;
    	}
    	if(auditRemark.length>200){
    		alert("字数超过200,不符合要求！");
    		return;
    	}
    	$.ajax({
    		type : "POST",
    		url : "${auditAction}?method=auditOrder",
    		dataType: "html",
    		data : {type:type,
    			    orderSn:orderSn,
    			    /* operatorAccount:operatorAccount */
    			    auditRemark:auditRemark,
    			    remark:""
    			   },
    		success: function(data){
    			if(data == "success"){
    				if(type == 2){
        				alert("订单审核不通过成功");
        			}
    				if(type == 1){
    					alert("订单审核通过成功");
    				}
    				//重新查询当前页订单
    		    	var pageNo= $("#curPage").val().trim();
    		    	var totalRecords=$("#totalRecords").val().trim();
    		    	var curPageSize=$("#curPageSize").val().trim();
    		    	 if(totalRecords%curPageSize==1){
    		    		pageNo=pageNo-1;  
    		    	}
    		    	if(pageNo<1){
    					pageNo=1;
    				}
    		    	gotoPage(pageNo);
    			} else {
    				alert(data);
    			}
    		},
    		error: function() {
    			if(type == 2){
    				alert("订单审核不通过失败");
    			}
				if(type == 1){
					alert("订单审核通过失败");			
				}
    		}
    	});
    	//隐藏订单审核界面
    	$('#auditOrderModal').modal('hide');   		
    }
	</script>
		
    <div id="base" style="height: 100%;">
	    <div class="panel panel-default">
	        <div class="panel-heading">
                <h3 class="panel-title" style="color:#31849b;">平安健康订单管理</h3>
                <input id="mobile" name="tel" type="hidden" value="" />
            </div>
			<div class="panel-body">
				<input type="hidden" id="curPage" name="pageno" value="${pageno}"/>
				<input type="hidden" id="curPageSize" name="pageSize" value="${pageSize}"/>
				<input type="hidden" id="totalRecords" name="totalRecords" value="${recTotal}"/>
		        <form class="well" method="post" id="dataForm">				
					<div class="tr_inline" style="margin-bottom:1px;">
						<div class="right_input item_title">订单日期：</div>
						<div class="right_input" style="width: 120px;">
							<input id="startDate" name="startDate" type="text" value="${startTime}" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'});" class="form-control item_input"/>
						</div>
						<div class="right_input">&nbsp;_&nbsp;</div>
						<div class="right_input" style="width: 120px;">
							<input id="endDate" name="endDate" type="text" value="${endTime}" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" class="form-control item_input"/>
						</div>
						<div class="right_input item_title" style="display: none;">订单类型：</div>
						<div class="right_input" style="width: 180px;display: none;">
							<select id="orderType" name="orderType" class="selectpicker form-control">
							    <%-- <option value="2" ${""==orderType || "default"== orderType?'selected="selected"':''}>--请选择--</option> --%>
								<%-- <option value="0" ${"0"==orderType?'selected="selected"':''}>全部订单</option> --%>
								<%-- <option value="1" ${"1"==orderType?'selected="selected"':''}>普通订单</option> --%>
								<option value="2" ${"2"==orderType || ""==orderType?'selected="selected"':''}>未审核处方药订单</option>
							<%-- 	<option value="3" ${"3"==orderType?'selected="selected"':''}>审核通过处方药订单</option>
								<option value="4" ${"4"==orderType?'selected="selected"':''}>审核未通过处方药订单</option> --%>
							</select>
						</div>
						<button id="queryButton" type="button" class="btn btn-primary" onclick="gotoPage(1);" style="text-align: left;">查询</button>					
					</div>					
				</form>				
				<div style="clear:both;"></div>
				<div class="table_head">
					<span style="float:left;margin:5px 0 0 19px;font-size:13px;color:white">平安健康订单管理</span>
				</div>
				<div style="overflow:auto;">
					<table class="table table-striped table-bordered" id="orderInfo">					
						<tr>
							<th class="th">序号</th>
							<th class="th">订单号</th>
							<th class="th">订单日期</th>			
							<th class="th">姓名</th>
							<!-- <th class="th">电话</th> -->
							<th class="th">地址</th>
							<th class="th">订单金额(元)</th>
							<th class="th">处方药</th>
							<th class="th">付款状态</th>
							<th class="th">付款金额(元)</th>
							<!-- <th class="th">审核状态</th>
							<th class="th">审核人</th> -->
							<th>操作</th>
						</tr>
						<c:if test="${OrderDetailDOList != null && fn:length(OrderDetailDOList)>0}">
						<c:forEach items="${OrderDetailDOList}" var="items" varStatus="status">
							<tr>
								<td class="th" style="vertical-align: middle;">
									<input type="radio" name="checkOrderId" id="id" class="iCheckRadio"  onclick="showGoodsDetail('${items.bizOrderDetailDO.bizOrderId}');"/>
								    ${status.index + 1}
								</td>
								<!-- 订单号 -->
								<td class="th" style="vertical-align: middle;">${items.bizOrderDetailDO.bizOrderId}</td>
								<!-- 订单日期 -->
								<td class="th" style="vertical-align: middle;">${items.bizOrderDetailDO.gmtCreated}</td>
								<!-- 购买者姓名 -->
								<td class="th" style="vertical-align: middle;">${items.lgOrderInfoDO.fullName}</td>
								<!-- 购买者手机号 -->
								<%-- <td class="th" style="vertical-align: middle;">${items.lgOrderInfoDO.mobilePhone}</td> --%>
								<!-- 购买者地址 -->
								<td class="th" style="vertical-align: middle;">${items.lgOrderInfoDO.prov}${items.lgOrderInfoDO.city}${items.lgOrderInfoDO.area}${items.lgOrderInfoDO.address}</td>
								<!-- 订单金额=实付金额+折扣金额 -->
								<td class="th" style="text-align: right;vertical-align: middle;">${items.bizOrderDetailDO.orderPriceDO.orderPrice+items.bizOrderDetailDO.orderPriceDO.discountFee}</td>
								<td class="th" style="vertical-align: middle;">
								    <c:if test="${items.bizOrderDetailDO.isPrescribed == 0}">否</c:if>
								    <c:if test="${items.bizOrderDetailDO.isPrescribed == 1}">是</c:if>
								</td>
								<td class="th" style="vertical-align: middle;">
								   <%--  <c:if test="${items.bizOrderDetailDO.payStatus == '0'}">未支付</c:if> --%>
								    <c:if test="${items.bizOrderDetailDO.payStatus == '2'}">已支付</c:if>
								</td>
								<td class="th" style="text-align: right;vertical-align: middle;">
								  <%--   <c:if test="${items.payStatus == '0'}">0</c:if>
								    <c:if test="${items.payStatus == '1'}">${items. }</c:if> --%>
								    <c:if test="${items.bizOrderDetailDO.payStatus == '2'}">${items.bizOrderDetailDO.orderPriceDO.orderPrice}</c:if>
								</td>
								<%-- <td>
								    <c:if test="${orderType == '2'}">未审核</c:if>
								    <c:if test="${orderType == '3'}">已审核</c:if>
								    <c:if test="${orderType == '4'}">审核未通过</c:if>
								</td> --%>
								<td>
								   <div class="btn-group-vertical">
								   		<c:if test="${items.bizOrderDetailDO.isPrescribed == '1'}">
								   			 <sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_AuditOrder">
								   			 		<!-- 参数   订单id，订单创建日期，订单总金额（实付金额+折扣金额），收货人姓名，电话 ,地址,性别,年龄。-->
									   			<button type="button" class="btn btn-primary"  onclick="auditOrderView('${items.bizOrderDetailDO.bizOrderId}','${items.bizOrderDetailDO.gmtCreated}','${items.bizOrderDetailDO.orderPriceDO.orderPrice+items.bizOrderDetailDO.orderPriceDO.discountFee}','${items.lgOrderInfoDO.fullName}','${items.lgOrderInfoDO.mobilePhone}','${items.snOrderDetailDO.address}','${items.snOrderDetailDO.sex}','${items.snOrderDetailDO.age}');" >审核</button>
								   			 </sec:authorize>
								   			<!--  <button type="button" class="btn btn-primary">备用1</button>
								   			 <button type="button" class="btn btn-primary">备用2</button> -->		
								   		</c:if>	
									<%-- 	<button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown">
										        操作
										    <span class="caret"></span>
										</button>
										<ul class="dropdown-menu" style="min-width:75px;" >
											<c:if test="${items.bizOrderDetailDO.isPrescribed == '1'}">							    
											    <sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_AuditOrder">
											    	<!-- 参数   订单id，订单创建日期，订单金额，收货人姓名，电话 ,地址,性别,年龄。-->
											    	<li><a href="#" onclick="auditOrderView('${items.bizOrderDetailDO.bizOrderId}','${items.bizOrderDetailDO.gmtCreated}','${items.bizOrderDetailDO.orderPriceDO.orderPrice}','${items.lgOrderInfoDO.fullName}','${items.lgOrderInfoDO.mobilePhone}','${items.snOrderDetailDO.address}','${items.snOrderDetailDO.sex}','${items.snOrderDetailDO.age}');" >审核</a></li>
												</sec:authorize >
												<li><a href="#"  onclick="updateOrderView('${items.bizOrderDetailDO.bizOrderId}');">修改</a></li>
											</c:if>
											<li><a href="#" onclick="auditOrder(2,'${items.bizOrderDetailDO.bizOrderId}');">取消</a></li>
										</ul> --%>
									</div>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</table>
				<c:if test="${OrderDetailDOList == null or fn:length(OrderDetailDOList)<=0}">
				    <c:choose>
			           <c:when test='${orderMessage != null && fn:length(orderMessage)>0}'>
			               ${orderMessage}
			           </c:when>
			           <c:otherwise>
			                              暂无数据！
			           </c:otherwise>
			        </c:choose>
				</c:if>
				<c:if test="${OrderDetailDOList != null && fn:length(OrderDetailDOList)>0}">
				<ul class="pager">
					<%@ include file="/WEB-INF/view/common/page.jspf" %>
				</ul>
				</c:if>
			</div>
			<div><p>&nbsp;</div>
			<!-- <h4 align="left">商品明细</h4> -->
			<div style="overflow:auto;">
			<div id="orderDetail">
				<c:forEach items="${OrderDetailDOList}" var="items" varStatus="status">  
				    <table id="orderGoods${items.bizOrderDetailDO.bizOrderId}" class="table table-striped table-bordered" style="display: none"  >
				    	<tr>
					    	<th>商品编码</th>
							<th>商品名称</th>
							<th>规格</th>
							<th>药品许可证号</th>
							<th>制药公司</th>
							<th>数量</th>
							<th>单价</th>
							<th>药品总金额</th>
							<th>是否处方药</th>
						</tr>			    
						<c:forEach items="${items.bizOrderDetailDO.drugDOList}" var="item" >
							<tr>
						    	<th>${item.referId}</th>
								<th>${item.itemName}</th>
								<th>${item.standard}</th>
								<th>${item.licence}</th>
								<th>${item.factoryName}</th>
								<th>${item.buyAmount}</th>
								<th>${item.price}</th>
								<th>${item.price*item.buyAmount}</th>
								<th>
									<c:if test="${item.isPrescribed == 0}">否</c:if>
									<c:if test="${item.isPrescribed == 1}">是</c:if>
								</th>
							</tr>		
						</c:forEach>									
				    </table>
				</c:forEach>
			</div>
			</div>	
		</div>
	</div>
	
</body>
</html>

<!-- 审核订单 -->
	<div class="modal fade" id="auditOrderModal" tabindex="-1">
	    <div class="modal-dialog" style="width:800px;">
			<div class="modal-content">
				<div class="modal-header" style="background-color:#51ABD9;" >
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title" id="myModalLabel" style="color:white">处方药审核</h5>
				</div>
				
				<div id="ruleInfo" class="modal-body">
				    <div style="padding: 1%;margin-left: -18px;">
				    	<div class="right_inline" style="margin-top: -10px;">
							<div class="right_input item_title">订单号：</div>
							<div class="right_input item_title lab" style="width: 110px;" id="auditOrderSn"></div>
							<div class="right_input item_title">下单日期：</div>
							<div class="right_input item_title lab" style="width: 150px;" id="auditAddTime"></div>
							<div class="right_input item_title">订单总金额：</div>
							<div class="right_input item_title lab" style="width: 80px;" id="auditGoodsAmount"></div>
						</div>
				    	<div style="clear:both"></div>
			        </div>
						<div style="margin-left: -670px">客户详细信息:</div>
			        <div class="panel panel-default" style="width:735px;margin-left: 8px;">
						<div class="right_inline">
							<div class="right_input item_title">姓名：</div>
							<div class="right_input item_title lab" style="width: 90px;" id="auditCustomerName"></div>
							<div class="right_input item_title">年龄：</div>
							<div class="right_input item_title lab" style="width: 50px;" id="auditCustomerAge"></div>
							<div class="right_input item_title">性别：</div>
							<div class="right_input item_title lab" style="width: 50px;" id="auditCustomerSex"></div>
							<div class="right_input item_title">手机号：</div>
							<div class="right_input item_title lab" style="width: 100px;" id="auditCustomerMobile"></div>
						</div>
						<div class="right_inline">
							<div class="right_input item_title">地址：</div>
							<div class="right_input item_title lab" style="width: 600px;" id="auditCustomerAddress"></div>
						</div>
					<!-- 	<div class="right_inline">
							<div class="right_input" style="width:680px;margin-left: 25px;">
								<textarea  class="form-control" style="height: 80px;">此处预定保存处方药文字信息</textarea>
							</div>
						</div> -->
				        <div style="clear:both"></div>
					</div>
				    <div style="padding: 1%;">
				        <div style="margin-left: -690px;">购买商品:</div>
				        <div>
				            <table style='width:735px;' class="table table-striped table-bordered" id="auditOrderGoods">
								<tr>
									<td style='width:10%;'>ID</td>
									<td style='width:15%;'>商品编码</td>
									<td style='width:40%;'>名称</td>
									<td style='width:25%;'>规格</td>
									<td style='width:10%;'>数量</td>
								</tr>
							</table>
				        </div>
				    </div>
			    	<div class="right_inline">
						<div class="right_input item_title">审核说明：</div>
						<div class="right_input" style="width:735px;margin-left: 8px;">
							<textarea id="auditRemark" name="comment" class="form-control" style="height: 60px;"></textarea>
						</div>
					</div>
					<div class="right_inline">
						<div class="right_input item_title">执业药师：</div>
						<div class="right_input item_title lab">${custServCode}</div>
						<div class="right_input" style="float: right;">
				        	<button type="button" class="btn btn-primary" style="text-align: left;" onclick="auditOrder(1,'');">审核通过</button>	
						    <button type="button" class="btn btn-primary" style="text-align: left;margin-right: 25px;" onclick="auditOrder(2,'');">不通过</button>
						</div>
					</div>
					<div style="clear:both"></div>
				</div>
			</div>
		</div>
	</div>		

