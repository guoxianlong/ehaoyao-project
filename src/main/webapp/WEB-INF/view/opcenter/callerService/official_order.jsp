<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- 加载CSS样式文件 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/bvalidator/js/jquery.bvalidator.js"></script>
<script src="<%=request.getContextPath()%>/js/WdatePicker/WdatePicker.js"></script>
<script src="<%=request.getContextPath()%>/js/mask.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap/respond.min.js"></script>

<style type="text/css">
	th {
		text-align: center;
	}
    table{
     empty-cells:show; 
     border-collapse: collapse;
     margin:0 auto;
    }
    
    .th {
         white-space: nowrap;
    }
    
    .tc-amount {
	    position: relative;
	}
	.tc-amount .minus {
	    margin-right: 3px;
	    background-position: 2px -219px;
	}
	.tc-amount .minus, .tc-amount .plus {
	    display: inline-block;
	    vertical-align: top;
	    margin-top: 2px;
	    cursor: pointer;
	    border: 1px solid #CCC;
	    width: 18px;
	    height: 18px;
	    overflow: hidden;
	    background-color: #FFF;
	    text-align:center;
	    background-position: 50% 50%;
	}
	.expressInfo {
	    background: #f2fcf5;   
	    border: 1px solid #dcf7ed;
		line-height: 24px;
		padding: 6px 0 6px 10px;
		font-size: 14px;
	}
	.expressInfo .div {
	    text-align: left;
	}
</style>

<!-- 加载javascript文件 -->
<script>    
    $(document).ready(function(){
    	//jQuery('#form1').bValidator(optionts,'formInstance');
    	$.getScript("http://design.ehaoyao.com/mall/js/commonArea.js",function(){
    		setProvince();
    	});
    	$('.selectpicker').selectpicker();
    	$("#orderDetail").hide();
    });
    
    /**查询事件*/
    function gotoPage(pageNo){
   		var orderNumber = $("#orderSn").val().trim();
   		var orderStatr = $("#orderStart").val().trim();
   		var paymentType = $("#paymentType").val().trim();
   		var orderType = $("#orderType").val().trim();
   		var pageSize = $("#curPageSize").val().trim();
   		var tel = $("#phone").val().trim();
   		var startDate = $("#startDate").val().trim();
   		var endDate = $("#endDate").val().trim();
   		var orderBy = $("#orderBy").val().trim();
   		var sort = $("#sort").val().trim();
   		var createAdmin=$("#createAdmin").val().trim();
    	$.ajax({
    		type : "POST",
    		url : "<%=request.getContextPath()%>/${callAction}?method=getOfficialOrder",
    		dataType: "html",
    		data : {phoneNo:tel,
    			    startTime:startDate,
    			    endTime:endDate,
    			    orderSn:orderNumber,
    			    orderStatr:orderStatr,
    			    paymentType:paymentType,
    			    createAdmin:createAdmin,
    			    orderType:orderType,
    			    orderBy:orderBy,
    			    sort:sort,
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
    
    function gotoPageSize(pageSize){
    	$("#curPageSize").val(pageSize);
    	$("#curPage").val(1);
    	gotoPage(1);
    }
    
    /**获取商品明细*/
    function getGoodsDetail(orderSn){
    	$("#orderDetail").show();
    	$("#orderGoods tr:gt(0)").remove();
    	$.ajax({
			type: "POST",
            url: "${callAction}?method=getGoodsDetail",
            dataType: "text",
            data: {orderSn:orderSn},
            success: function(data){
    			var item;
    			$.each(JSON.parse(data), function(i, result){
    			    item = "<tr>"+
 		                       "<td>"+result.sku+"</td>"+
 		                       "<td>"+result.goodsName+"</td>"+
 		                       "<td>"+result.mealNormName+"</td>"+
 		                       "<td>"+result.brandName+"</td>"+
 		                       "<td style='text-align: right;'>"+result.quantity+"</td>"+
 		                       "<td style='text-align: right;'>"+result.price+"</td>"+
 		                       "<td style='text-align: right;'>"+result.subTotalPrice+"</td>";
 		                       if(result.ifAfterSale == 0){
 		                    	   item += "<td>&nbsp;</td>";
 		                       } else {
 		                    	   item += "<td>是</td>";
 		                       }
 		                       if(null != result.prescriptionType && result.prescriptionType == 0){
 		                    	   item += "<td width='10%'></td></tr>";
						       }
			 			       if(null != result.prescriptionType && result.prescriptionType == 1){
			 			    	   item += "<td width='10%'>非处方药(甲类)</td></tr>";
			 			       }
			 			       if(null != result.prescriptionType && result.prescriptionType == 2){
			 			    	   item += "<td width='10%'>非处方药(乙类)</td></tr>";
			 			       }
			 			       if(null != result.prescriptionType && result.prescriptionType == 3){
			 			    	   if(null != result.drugType && result.drugType == 3){
			 			               item += "<td width='10%'>双轨处方药</td></tr>";
			 			    	   } else if(null != result.drugType && result.drugType == 93){
			 			    		  item += "<td width='10%'>单轨处方药</td></tr>";
			 			    	   } else {
			 			    		  item += "<td width='10%'></td></tr>";
			 			    	   }
			 			       }
			 			       if(null != result.prescriptionType && result.prescriptionType == 4){
			 			       	   item += "<td width='10%'>保健食品</td></tr>";
			 			       }
    			    $("#orderGoods").append(item);
    			});
    		},   
    		error : function(){
    			alert("获取商品明细加载失败,请重新选择订单");
    		}
    	});
    }
    
    /** 查看物流信息 */
    function getExpressInfo(orderCode){
    	$.ajax({
			type: "POST",
            url: "<%=request.getContextPath()%>/${callAction}?method=getExpressInfo",
            dataType: "json",
            data: {orderCode:orderCode},
            success: function(data){
            	if(null != data.expressInfo){
            		$('#expressInfoModal').modal('show');
	            	$("#expressOrderSn").text(data.expressInfo.orderSn);
	            	$("#expressReceiveName").text(data.expressInfo.receiveName);
	            	$("#expressMobile").text(data.expressInfo.mobile);
	            	$("#expressDetailAddr").text(data.expressInfo.detailAddr);
	            	$("#expressShipCorpName").text(data.expressInfo.shipCorpName);
	            	$("#expressShipNo").text(data.expressInfo.shipNo);
	            	var item = "";
	            	$("#expressInfos").empty();
	            	$.each(data.expressInfo.logisticsList, function(i, result){
	            		item = result.receiptTime + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp" + result.context;
	            	    $("#expressInfos").append("<li>" + item + "</li>");
	            	});
            	} else {
            		$('#expressInfoModal').modal('hide');
            		alert("返回物流信息空");
            	}
            },
            error : function(){
            	$('#expressInfoModal').modal('hide');
            	alert("查看物流信息失败");
            }
    	});
    }
    
    /** 查看订单日志 */
    function getOrderLog(orderSn){
    	if(null != orderSn){
    		$("#orderLogInfo tr:gt(0)").remove();
    	    $.ajax({
    	    	type: "POST",
                url: "<%=request.getContextPath()%>/${callAction}?method=getOrderLogInfo",
                dataType: "json",
                data: {orderCode:orderSn},
                success: function(data){
                	 var item = "";                	
                	 if(null != data.orderLogInfo){
                		 $('#orderLogModal').modal('show');
                		 $.each(data.orderLogInfo, function(i, result){
                			 item += "<tr>"+
                			             "<td>"+result.orderSn+"</td>"+
                			             "<td>"+result.opName+"</td>"+
                			             "<td>"+result.cmpCode+"</td>"+
                			             "<td>"+result.logText+"</td>"+
                			             "<td>"+result.actTime+"</td>"+
                			             "<td>"+result.adminLoginName+"</td>"+
                			         "</tr>";
                		 });
                		 $("#orderLogInfo").append(item);
                	 } else {
                		 $('#orderLogModal').modal('hide');
                		 alert("查看订单日志信息空");
                	 }
                },
                error: function(){
                	$('#orderLogModal').modal('hide');
                	alert("查看订单日志失败");
                }
    	    });
    	} else {
    		$('#orderLogModal').modal('hide');
    		alert("查看订单日志，订单编号不能为空");
    	}
    }
    
    /** 审核订单显示 */
    function auditOrderView(orderSn,addTime,goodsAmount,receiveName,tel,createAdminUser){
   	    $.ajax({
   			type: "POST",
               url: "${callAction}?method=getGoodsDetail",
               dataType: "text",
               data: {orderSn:orderSn},
               success: function(data){
                   $('#auditOrderModal').modal('show');
                   document.getElementById("add_reserve").style.display="none";
               	   $("#auditOrderSn").text(orderSn);
               	   $("#auditAddTime").text(addTime);
               	   $("#memberName_win").val(receiveName);
               	   $("#tel_win").val(tel);
               	   $("#custServiceNo_win").val(createAdminUser);
               	   $("#auditGoodsAmount").text(goodsAmount);
               	   $("#auditOrderGoods tr:gt(0)").remove();
               	var item;
       			$.each(JSON.parse(data), function(i, result){
       			    item = "<tr>"+
    		                       "<td>"+result.goodsId+"</td>"+
    		                       "<td>"+result.sku+"</td>"+
    		                       "<td style='word-break: break-all; word-wrap:break-word;'>"+result.goodsName+"</td>"+
    		                       "<td>"+result.mealNormName+"</td>"+
    		                       "<td>"+result.quantity+"</td>"+
   				           "</tr>";
       			    $("#auditOrderGoods").append(item);
       			});
       		},
       		error : function(){
       			alert("获取订单失败,请重新选择订单");
       			$('#auditOrderModal').modal('hide');
       		}
       	});
    }
    
    /** 审核订单、取消订单 */
    function auditOrder(type,orderCode){
    	var orderSn;
    	if(type == 2){
    		orderSn = orderCode;
    	} else {
    		orderSn = $("#auditOrderSn").text();
    	}
    	var operatorAccount = "${custServCode}";
    	var auditRemark = $("#auditRemark").val();
    	$.ajax({
    		type : "POST",
    		url : "${callAction}?method=auditOrder",
    		dataType: "html",
    		data : {type:type,
    			    orderSn:orderSn,
    			    operatorAccount:operatorAccount,
    			    auditRemark:auditRemark
    			   },
    		success: function(data){
    			if(data == "success"){
    				if(type == 0){
        				alert("订单审核不通过成功");
        			}
    				if(type == 1){
    					alert("订单审核通过成功");			
    				}
    				if(type == 2){
    					alert("取消订单成功");
    				}
    			} else {
    				alert(data);
    			}
    		},
    		error: function() {
    			if(type == 0){
    				alert("订单审核不通过失败");
    			}
				if(type == 1){
					alert("订单审核通过失败");			
				}
				if(type == 2){
					alert("取消订单失败");
				}
    		}
    	});
    	$('#auditOrderModal').modal('hide');
    }
   
    
    /** 修改订单start */
    
    /** 修改订单显示 */
    function updateOrderView(orderSn){
    	document.getElementById("tep1").style.display="";
		document.getElementById("tep2").style.display="none";
		$("#if_bill").prop("checked",true);
		$("#billInfo").attr("style","display:''");
		$("input[type=radio][name='invoiceType'][value='1']").prop("checked",true);
		$("#invoiceTitle").val("");
		$("#invoiceTitle").attr('style','display:none');
		$("#requireNote").val("");
		$("#orderSubmit").prop("disabled",false);
		var dateString = '${dateString}';
    	if(orderSn != null && orderSn != ""){
	   	    $.ajax({
	       		type : "POST",
	       		dataType: "json",//返回json格式的数据
	       		data : {orderSn: orderSn,dateString:dateString},
	       		url:"<%=request.getContextPath()%>/callerScreen.do?method=updateOrder",
	            success: function(data){
	            	if(data.message != null){
	            		alert(data.message);
	            		return;
	            	}
	            	$('#updateOrderModal').modal('show');
	            	//订单操作类型
	            	$("#orderOperationType").val(data.orderOperationType);
	            	//下单用户
	            	$("#updateImg").hide();
	            	//订单编号
	               	$("#orderCode").text(orderSn);
	            	document.getElementById("updateOrderCode").style.display="";	            	
	            	//订单来源标示
	            	$("#cpsLaiyuanInfo").hide();
	            	$("#updateLaiyuan").empty();
	            	var laiyuanTemp = "<select name='memberStatus' id='updateCpsLaiyuan' class='selectpicker form-control select_set'>"+
	            	                      "<option value=''>--请选择--</option>";
	            	$.each(data.cpsLaiyuan, function(i, result){
	            		laiyuanTemp += "<option value='"+result.cpsFlag+"'";
	            		if(data.orderInfoBean.cpsLaiyuan == result.cpsFlag){
	            			laiyuanTemp += " selected='selected'";
	            		}
	            		laiyuanTemp += ">"+result.merchantName+"</option>";
	            	});
	            	laiyuanTemp += "</select>";
	            	$("#updateLaiyuan").append(laiyuanTemp);
	            	$('.selectpicker').selectpicker();	            	
	            	//订单类型	            	
	            	$("#orderType").selectpicker("val",data.orderInfoBean.orderType);
	            	$('#orderType').selectpicker('render');
	            	//下单用户
	               	$("#order_user").val(data.orderInfoBean.buyerUserId);
	            	//用户账号
	               	$("#order_account").val(data.orderInfoBean.buyerLoginName);
	            	//用户手机号
	               	$("#orderPhone").val(data.orderInfoBean.askerContact);
	               	//客服ID
	               	$("#adminUser").val(data.custServCode);
	               	//客服姓名
	               	$("#adminUserName").val(data.adminUserName);
	               	//商品列表
	               	var t = document.getElementById("tb_body"); //获取展示数据的表格 
	        		while (t.rows.length != 0) { 
	        			t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除 
	        		}
	               	var item;
	               	$("#tb_body").empty();
	               	$.each(data.productList, function(i, result){
	       			    item = "<tr>"+
	       			               "<td align='center'>" +
				                       "<img class='img-thumbnail' src='<%=request.getContextPath()%>/images/delete.png'" +
		                               "onclick='removeRow(this);' title='删除' alt='删除' style='width: 25px; height: 25px; cursor: pointer' />"+
	 				               "</td>" +
								   "<td>"+result.mealId+"</td>" +
							       "<td>"+result.mealName+"</td>" +
							       "<td>"+result.mainSku+"</td>" +
							       "<td>"+result.mealNormName+"</td>" +
							       "<td>" +
					 			       "<div class='tc-amount'>"+
									       "<span class='minus' onclick='minus(this,"+'"'+result.prescriptionType+'"'+");'>-</span>"+
										   "<input class='tc-text amount' type='text' style='width: 71%;' value='"+result.buyCount+"' onkeyup='text_change(this,"+'"'+result.prescriptionType+'"'+");'/>"+
										   "<span class='plus' onclick='plus(this,"+'"'+result.prescriptionType+'"'+");'>+</span>"+
								       "</div>"+
								   "</td>"+
								   "<td>"+result.price+"</td>"+
								   "<td>"+result.amount+"</td>" +
								   "<td style='display:none;'>"+result.productId+"</td></tr>";
								   /* if(null != result.prescriptionType && result.prescriptionType == 3){
									    item += "<td><input class='tc-text amount' type='text' value='"+result.price+"' onkeyup='text_price(this);' /></td><td>"+
									    result.price+"</td></tr>";
								   } else {
										item += "<td>"+result.price+"</td><td>"+result.amount+"</td></tr>";
								   } */
	       			    $("#tb_body").append(item);
	       			});
	               	//快递信息
	               	var item1 ="";
	               	$.each(data.shipArray, function(i, result){
	               		if(result.expressCode != null && result.expressCode != "STKD"){
	               			item1 += "<div class='right_input' style='width: 8%;'>"+
	               			         "<input type='radio' onclick='getExpress()' name='expressInfo'";
	               			if(data.orderInfoBean.storeShipFeeId == result.shipFeeId){
	               				item1 += " checked ";
	               			}
	              		    item1 += "value="+"'"+result.shipFeeId+"'>"+result.name+"</div>";                   		        			
	              		}
	               	});	               	
	               	$("#update_expressInfo").empty();
					$("#update_expressInfo").append(item1);
					//促销信息
	               	$("#cheapInfo").text(data.orderInfo.cheapInfo);
					//邮费
	               	$("#postageInfo").text(data.orderInfo.postageInfo);
					//商品总价（含运费）
	               	$("#totalAmtInfo").text(data.orderInfo.totalAmtInfo);
	               	//优惠金额
	               	$("#adminDiscount").val(data.orderInfoBean.adminDiscount);
	               	//商品总价
	        		$("#totalAmount").text(data.orderInfo.totalAmount);
	        		//优惠金额
	        		$("#cheapAmt").text(data.orderInfo.cheapAmt);
	        		//快递费用
	        		$("#postage").text(data.orderInfo.postage);
	        		//订单总价
	        		$("#orderAmt").text(data.orderInfo.orderAmt);
	        		//用户用户地址id
	        		$("#updateUserAddressId").val(data.orderInfoBean.userAddressId);
	        		//支付方式id
	        		$("#updatePaymentMethodId").val(data.orderInfoBean.paymentMethodId);
	        		//送货时间id
	        		$("#updateShipTimeId").val(data.orderInfoBean.shipTimeId);
	        		//发票类型
	        		$("#updateInvoiceType").val(data.orderInfoBean.invoiceType);
	        		//发票抬头
	        		$("#updateInvoiceTitle").val(data.orderInfoBean.invoiceTitle);
	        		//订单备注
	        		$("#updateRequireNote").val(data.orderInfoBean.requireNote);
	            }
	       	});        	
	     } else {
	    	$('#updateOrderModal').modal('hide');
	   		alert("请先选择一个订单");
	 	 }
    }
	
	/** 选择商品start */

	/**
	 * 下一步
	 */
	function updateOrder_next(){
		var userId = $("#order_user").val().trim();
		if(userId == null || userId.trim() == ""){
			alert("请选择下单用户！");
			return;
		}
		var t = document.getElementById("tb_body"); //获取展示数据的表格 
		while (t.rows.length == 0) { 
			alert("请添加商品！");
			return;
		}
		var addressIdTemp = $("#updateUserAddressId").val();
		var dateString = '${dateString}';
		var type = $("#orderOperationType").val();
		var previous = $("#previous").val();
		if(previous == 0){
			$.ajax({
				type : "POST",  
				dataType: "json",//返回json格式的数据
				data : {
					userId: userId,
					usaid: addressIdTemp,
					type:type,
					dateString:dateString
				},
				url:"<%=request.getContextPath()%>/${outAction}?method=nextStep",
		        success: function(data){
		        	//送货地址
		        	var item1 = "";
		        	if(data.address != ""){
		        		item1 = "<input type='radio' name='order_address' id='userAddressId' value='"+data.addressId+"' checked='checked'>" +data.addUsername+"&nbsp;&nbsp;&nbsp;&nbsp;"+data.address+"&nbsp;&nbsp;&nbsp;&nbsp;"+data.addPhone;
		        	}else{
		        		item1 = "<input type='radio' name='order_address' id='userAddressId' value='null'  checked='checked'>添加新地址";
		        	}
		        	$("#default_address").empty();
		        	$("#default_address").append(item1);
		        	//支付方式
		        	var item2 = "";
	        		$("#pay_method").empty();
	        		var payMethodTemp = $("#updatePaymentMethodId").val();
		        	for(var i=0;i<data.payMethod.length;i++){
		        		if(data.payMethod[i].paymentMethodId == payMethodTemp){
			            	item2 = "<div class='right_inline'>"+
										"<div class='right_input item_title'>"+
											"<input type='radio' name='payMethod' value='"+data.payMethod[i].paymentMethodId+"' checked>"+data.payMethod[i].name.trim();
										"</div>"+
									"</div>";
		        		}else{
		        			item2 = "<div class='right_inline'>"+
										"<div class='right_input item_title'>"+
											"<input type='radio' name='payMethod' value='"+data.payMethod[i].paymentMethodId+"'>"+data.payMethod[i].name.trim();
										"</div>"+
									"</div>";
		        		}
		        		$("#pay_method").append(item2);
		            }
		        	//送货时间
		        	var item4 = "";
	        		$("#ship_time").empty();
	        		var shipTimeTemp = $("#updateShipTimeId").val();
		        	for(var h=0;h<data.shipTime.length;h++){
		        		if(data.shipTime[h].shipTimeId == shipTimeTemp){
			        		item4 = "<div class='right_inline'>"+
										"<div class='right_input item_title'>"+
											"<input type='radio' name='shipTime' id='shipTime"+data.shipTime[h].shipTimeId+"' value='"+data.shipTime[h].shipTimeId+"' checked>"+data.shipTime[h].title;
										"</div>"+
									"</div>";
		        		}else{
		        			item4 = "<div class='right_inline'>"+
										"<div class='right_input item_title'>"+
											"<input type='radio' name='shipTime' id='shipTime"+data.shipTime[h].shipTimeId+"' value='"+data.shipTime[h].shipTimeId+"'>"+data.shipTime[h].title;
										"</div>"+
									"</div>";
		        		}
		        		$("#ship_time").append(item4);
		            }
		        	//显示发票信息
		        	var invoiceTypetemp = $("#updateInvoiceType").val();
		        	var invoiceTitleTemp = $("#updateInvoiceTitle").val();
		        	if(null != invoiceTypetemp && invoiceTypetemp != 0){
		        		if(invoiceTypetemp == 1){
		        			$("input[type=radio][name='invoiceType'][value='1']").prop("checked",true);
		        			document.getElementById("invoiceTitle").style.display="none";
		        		}
		        		if(invoiceTypetemp == 2){
		        			$("input[type=radio][name='invoiceType'][value='2']").prop("checked",true);
		        			$("#invoiceTitle").val(invoiceTitleTemp);
		        			document.getElementById("invoiceTitle").style.display="";
		        		}
		        	} else {
		        		$("#if_bill").prop("checked",false);
		        		document.getElementById("billInfo").style.display="none";
		        	}
		        	//订单备注
		        	var requireNoteTemp = $("#updateRequireNote").val();
		        	$("#requireNote").val(requireNoteTemp);
		        	//购买的商品
		        	var item5 = "";
		        	$("#buy_body").empty();
		        	for(var f=0;f<data.productList.length;f++){
			        		item5 = "<tr>"+
					    				"<td>"+data.productList[f].mealId+"</td>"+
					    				"<td>"+data.productList[f].mealName+"</td>"+
					    				"<td>"+data.productList[f].mainSku+"</td>"+
					    				"<td>"+data.productList[f].mealNormName+"</td>"+
					    				"<td>"+data.productList[f].buyCount+"</td>"+
					    				"<td>"+data.productList[f].price+"</td>"+
					    				"<td>"+data.productList[f].amount+"</td>"+
					    			"</tr>";
		        		$("#buy_body").append(item5);
		            } 
		        }
			});
		}
		document.getElementById("tep1").style.display="none";
		document.getElementById("tep2").style.display="";
	}
	
	/**
	 * 提交订单
	 */
	function updateOrderSubmit(){
		if(confirm('你确定要下单吗？')){
			$("#orderSubmit").attr("disabled", "disabled");
			//订单编号
			var orderSn = $("#orderCode").text();
			//后台操作者帐号
			var createAdminUser = $("#adminUser").val();
			//用户手机号
			var buyerUserPhone = $("#orderPhone").val();
			//收货地址id
			var userAddressId = $('input[name="order_address"]:checked').val();
			if(null == userAddressId || userAddressId == 0){
				alert("请选择收货地址");
				return;
			}
			//支付方式id
			var paymentMethodId = $('input[name="payMethod"]:checked').val();
			if(paymentMethodId == null || paymentMethodId == 0){
				alert("请选择支付方式");
				return;
			}
			//送货方式id
			var shipMethodId = $('input[name="expressInfo"]:checked').val();
			if(shipMethodId == null || shipMethodId == 0){
				alert("请选择快递");
				return;
			}
			//送货时间id
			var shipTimeId = $('input[name="shipTime"]:checked').val();
			if(shipTimeId == null || shipTimeId == 0){
				alert("请选择送货时间");
				return;
			}
			//备注
			var remark = $("#requireNote").val();
			//发票类型（0为不需要发票，1为个人发票,2为单位发票）
			var invoiceType = 0;
			//发票抬头。当发票类型为2，即单位发票时，需要填写
			var invoiceTitle = $("#invoiceTitle").val();
			if($('#if_bill').is(':checked')){
				invoiceType = $('input[name="invoiceType"]:checked').val();
				if(invoiceType == 2){
					if(null != $("#invoiceTitle").val() && $("#invoiceTitle").val() != ""){
						invoiceTitle = $("#invoiceTitle").val();
					} else {
						alert("请输入发票抬头");
						return;
					}
				}
			} else {
				invoiceType = 0;
			}
			//订单来源
			var cpsLaiyuan = $("#updateCpsLaiyuan").val();
			//优惠金额
			var adminDiscount = $("#adminDiscount").val();
			//订单总额
			var orderAmount = $("#orderAmt").text();
			//随机数
			var dateString = '${dateString}';
			$.ajax({
				type : "POST",  
				dataType: "text",//返回json格式的数据
				data : {
					orderSn: orderSn,//订单编号
					createAdminUser: createAdminUser,//后台操作者帐号
					userAddressId: userAddressId,//收货地址id
					paymentMethodId: paymentMethodId,//支付方式id
					shipMethodId: shipMethodId,//送货方式id
					shipTimeId: shipTimeId,//送货时间id
					invoiceType: invoiceType,//发票类型
					invoiceTitle: invoiceTitle,//发票抬头
					remark:remark,//备注
					cpsLaiyuan:cpsLaiyuan,//订单来源
					buyerUserPhone:buyerUserPhone,//用户手机号
					adminDiscount:adminDiscount,//优惠金额
					orderAmount:orderAmount,//订单总额
					dateString:dateString
				},
				url:"<%=request.getContextPath()%>/${callAction}?method=submitUpdateOrder",
		        success: function(data){
		        	if(data == "success"){
		        		alert("下单成功！");
		        		$('#updateOrderModal').modal('hide');
		        	}else{
		        		$("#orderSubmit").attr("disabled", "");
		        		alert(data);
		        	}
		        },
		        error:function(){
		        	$("#orderSubmit").attr("disabled", "");
		        	alert("修改订单失败");
		        }
			});	
		}else{
			return false;
		}
	}
	/**
	 *打开新增预约信息面板
	 */
	function show_reserve(){
		$("#reservaTime").val("");
		$("#comment").val("");
		document.getElementById("add_reserve").style.display="";
	}
	//保存新增预约信息
	function saveReserve(){
		var custName = $("#memberName_win").val().trim();
		var tel = $("#tel_win").val().trim();
		//var custServiceNo = "${custServCode}";
		var custServiceNo = $("#custServiceNo_win").val().trim();
		var reserveTime = $("#reservaTime").val().trim();
		var comment = $("#comment").val().trim();
		var orderType="来电";
		if(reserveTime == null || reserveTime == ""){
			alert("预约时间不能为空！");
			return;
		}
		$.ajax({
			 type: "POST",
             url: "${outAction}?method=savaReservat",
             dataType: "json",
             data: {custName:custName,tel:tel,custServiceNo:custServiceNo,reserveTime:reserveTime,comment:comment,orderType:orderType},
             success: function(data){
		         document.getElementById("add_reserve").style.display="none";
            	 alert("保存成功！");
             }
		});
	}
	//取消新增预约信息
	function closeReserve(){
		document.getElementById("add_reserve").style.display="none";
	}
    /** 修改订单end */
</script>

<div id="base" class="container-fluid">
	<div class="panel-body">
		<input type="hidden" id="curPage" name="pageno" value="${pageno}"/>
		<input type="hidden" id="curPageSize" name="pageSize" value="${pageSize}"/>
		<input type="hidden" id="totalRecords" name="totalRecords" value="${recTotal}"/>
		<input type="hidden" id="orderBy" name="orderBy" value="add_time"/>
		<input type="hidden" id="sort" name="sort" value="2"/>
		<div class="form-horizontal">
			<div class="col-sm-12 form-group">
				<label style="float: left;text-align: left;padding-top: 8px;width:7%">订单日期：</label>
			    <div class="col-sm-1" style="padding-left: 10px;width: 12%;">
					<input id="startDate" name="startDate" style="height: 34px;" value="${startTime}" type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'});" class="form-control item_input"/>
				</div>
				<span class="control-label" style="float: left;padding-top: 0%;">&nbsp;__&nbsp;</span>
				<div class="col-sm-3" style="padding-left: 10px;width: 12%;">
					<input id="endDate" name="endDate" style="height: 34px;" value="${endTime}" type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" class="form-control item_input"/>
				</div>
				<label style="float: left;text-align: left;padding-top: 8px;width:7%">订单类型：</label>
				<div class="col-sm-8" style="padding-left: 0%;width: 11%;">
					<select id="orderType" name="orderType" class="selectpicker form-control">
					    <option value="default" ${""==orderType || "default"== orderType?'selected="selected"':''}>--请选择--</option>
						<option value="0" ${"0"==orderType?'selected="selected"':''}>普通类型</option>
						<option value="1" ${"1"==orderType?'selected="selected"':''}>代下单</option>
						<option value="2" ${"2"==orderType?'selected="selected"':''}>处方药</option>
						<option value="3" ${"3"==orderType?'selected="selected"':''}>惠氏订单</option>
						<option value="4" ${"4"==orderType?'selected="selected"':''}>金斯利安</option>
					</select>
				</div>
				<label style="float: left;text-align: left;padding-top: 8px;width:6%">订单号：</label>
				<div class="col-sm-3" style="padding-left: 10px;width: 20%;">
					<input type="text" class="form-control" id="orderSn" name="orderSn" value="${orderSn}" />
				</div>
				<label style="float: left;text-align: left;padding-top: 8px;width:7%">客服工号：</label>
				<div class="col-sm-3" style="padding-left: 10px;width: 12%;">
					<input type="text" class="form-control" id="createAdmin" name="createAdmin" value="${createAdmin}" />
				</div>
			</div>
			<div class="col-sm-12 form-group">
			    <label style="float: left;text-align: left;padding-top: 8px;width:7%">支付方式：</label>
				<div class="col-sm-2" style="padding-left: 10px;width: 27%;">
					<select id="paymentType" name="paymentType" class="selectpicker form-control">
					    <option value="default" ${""==paymentType || "default"== paymentType?'selected="selected"':''}>--请选择--</option>
						<option value="0" ${"0"==paymentType?'selected="selected"':''}>线上支付</option>
						<option value="1" ${"1"==paymentType?'selected="selected"':''}>货到付款</option>
					</select>
				</div>				    
				<label style="float: left;text-align: left;padding-top: 8px;width:7%">订单状态：</label>
				<div class="col-sm-2" style="padding-left: 0%;width: 21%;">
					<select id="orderStart" name="orderStatr" class="selectpicker form-control">
					    <option value="default" ${""==orderStatr || "default"== orderStatr?'selected="selected"':''}>--请选择--</option>
						<option value="0"  ${"0"==orderStatr?'selected="selected"':''}>未付款</option>
						<option value="10" ${"10"==orderStatr?'selected="selected"':''}>未审核</option>
						<option value="20" ${"20"==orderStatr?'selected="selected"':''}>待发货</option>
						<option value="30" ${"30"==orderStatr?'selected="selected"':''}>已发货</option>
						<option value="40" ${"40"==orderStatr?'selected="selected"':''}>已完成</option>
						<option value="50" ${"50"==orderStatr?'selected="selected"':''}>已取消</option>
					</select>
				</div>
				<label style="float: left;text-align: left;padding-top: 8px;width:6%">手机号：</label>
				<div class="col-sm-2" style="padding-left: 2px;width: 21%;">
					<input type="text" class="form-control" id="phone" name="phone" value="${phone}" />
				</div>
				<button type="submit" class="btn btn-primary" onclick="gotoPage(1);" style="text-align: left;">查询</button>					
			</div>
		</div>
		<div style="clear:both;height:0;"></div>
		<div>
			<table class="table table-striped table-bordered" id="orderInfo">
				<tr>
					<th class="th">序号</th>
					<th class="th">订单号</th>
					<th class="th">订单日期</th>
					<th class="th">订单类型</th>					
					<th class="th">姓名</th>
					<!-- <th class="th">电话</th> -->
					<th class="th">地址</th>
					<th class="th">订单金额</th>
					<th class="th">备注</th>
					<th class="th">支付方式</th>
					<th class="th">订单状态</th>
					<th class="th">处方药</th>
					<th class="th">付款状态</th>
					<th class="th">付款金额</th>
					<th class="th">客服工号</th>
					<th class="th">审核状态</th>
					<th class="th">审核人</th>
					<th>操作</th>
				</tr>
				<c:if test="${orderList != null && fn:length(orderList)>0}">
				<c:forEach items="${orderList}" var="items" varStatus="status">
				<tr>
					<td class="th" style="vertical-align: middle;">
					    <input type="radio" name="checkOrderId" id="id" onclick="getGoodsDetail('${items.orderSn}');" />
					    ${status.index + 1}
					</td>
					<td class="th" style="vertical-align: middle;">${items.orderSn}</td>
					<td class="th" style="vertical-align: middle;"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${items.addTime}"/></td>
					<td class="th" style="vertical-align: middle;">
					    <c:if test="${items.adminType==0}">普通类型</c:if>
					    <c:if test="${items.adminType==1}">代下单</c:if>
					    <c:if test="${items.adminType==2}">处方药</c:if>
					    <c:if test="${items.adminType==3}">惠氏订单</c:if>
					    <c:if test="${items.adminType==4}">金斯利安</c:if>
					</td>
					<td class="th" style="vertical-align: middle;">${items.receiveName}</td>
					<%-- <td class="th" style="vertical-align: middle;">${items.mobile}</td> --%>
					<td class="th" style="vertical-align: middle;">${items.provinceName}${items.districtName}</td>
					<td class="th" style="text-align: right;vertical-align: middle;">${items.orderAmount}</td>
					<td class="th" style="vertical-align: middle;">${items.requireNote}</td>
					<td class="th" style="vertical-align: middle;">
					    <c:if test="${items.paymentMethodType == '0'}">在线支付</c:if>
					    <c:if test="${items.paymentMethodType == '1'}">货到付款</c:if>
					</td>
					<td class="th" style="vertical-align: middle;">
					    <c:if test="${items.status == '0'}">未付款</c:if>
					    <c:if test="${items.status == '10'}">未审核</c:if>
					    <c:if test="${items.status == '20'}">待发货</c:if>
					    <c:if test="${items.status == '30'}">已发货</c:if>
					    <c:if test="${items.status == '40'}">已完成</c:if>
					    <c:if test="${items.status == '50'}">已取消</c:if>
					</td>
					<td class="th" style="vertical-align: middle;">
					    <c:if test="${items.ifPresc == 0}">否</c:if>
					    <c:if test="${items.ifPresc == 1}">是</c:if>
					</td>
					<td class="th" style="vertical-align: middle;">
					    <c:if test="${items.payStatus == '0'}">未支付</c:if>
					    <c:if test="${items.payStatus == '1'}">已支付</c:if>
					    <c:if test="${items.payStatus == '2'}">已退款</c:if>
					</td>
					<td class="th" style="text-align: right;vertical-align: middle;">
					    <c:if test="${items.payStatus == '0'}">0</c:if>
					    <c:if test="${items.payStatus == '1'}">${items.orderAmount}</c:if>
					    <c:if test="${items.payStatus == '2'}">${items.orderAmount}</c:if>
					</td>
					<td>${items.createAdminUser}</td>
					<td>
					    <c:if test="${items.auditStatus == '0'}">未审核</c:if>
					    <c:if test="${items.auditStatus == '1'}">已审核</c:if>
					    <c:if test="${items.auditStatus == '2'}">审核未通过</c:if>
					</td>
					<td>${items.auditUserName}</td>
					<td>
					    <div class="btn-group">
							<button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown">
							        操作
							    <span class="caret"></span>
							</button>
							<ul class="dropdown-menu" style="min-width:75px;" >
								<li><a href="#" style="padding-left:8px;padding-right: 0px;" data-toggle="modal" onclick="getExpressInfo('${items.orderSn}');">查看物流</a></li>
								<li><a href="#" style="padding-left:8px;padding-right: 0px;" data-toggle="modal" onclick="getOrderLog('${items.orderSn}');">订单日志</a></li>
								<c:if test="${items.adminType == 2}">								    
								    <sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_AuditOrder">
								    	<li><a href="#" onclick="auditOrderView('${items.orderSn}','<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${items.addTime}"></fmt:formatDate>','${items.goodsAmount}','${items.receiveName}','${items.mobile}','${items.createAdminUser}');" 
								    	style="padding-left:8px;padding-right: 0px;" data-toggle="modal">审核</a></li>
									</sec:authorize >
								</c:if>
								<li><a href="#" style="padding-left:8px;padding-right: 0px;" data-toggle="modal" onclick="updateOrderView('${items.orderSn}');">修改</a></li>
								<li><a href="#" style="padding-left:8px;padding-right: 0px;" onclick="auditOrder(2,'${items.orderSn}');">取消</a></li>
							</ul>
						</div>
					</td>
				</tr>
				</c:forEach>
				</c:if>
			</table>
			<c:if test="${orderList == null or fn:length(orderList)<=0}">
			    <div style="height:100px;text-align: center;vertical-align: middle;font-size:14px;font-weight: bold;">
			        <c:choose>
			           <c:when test='${orderMessage != null && fn:length(orderMessage)>0}'>
			               ${orderMessage}
			           </c:when>
			           <c:otherwise>
			                              暂无数据！
			           </c:otherwise>
			        </c:choose>
			    </div>
			</c:if>
			<c:if test="${orderList != null && fn:length(orderList)>0}">
				<ul class="pager">
					<%@ include file="/WEB-INF/view/common/page.jspf" %>
				</ul>
			</c:if>
		</div>
		<div><p>&nbsp;</div>
		<div id="orderDetail">
		        商品明细：
		    <table id="orderGoods" class="table table-striped table-bordered">
				<tr>
					<th>商品编码</th>
					<th>商品名称</th>
					<th>规格</th>
					<th>品牌</th>
					<th>数量</th>
					<th>单价</th>
					<th>金额</th>
					<th>是否赠品</th>
					<th>处方类型</th>
				</tr>
		    </table>
		</div>
	</div>
</div>
<!-- 审核订单 -->
<div class="modal fade" id="auditOrderModal" tabindex="-1">
    <div class="modal-dialog" style="width:800px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="myModalLabel">审核</h4>
			</div>
			<div id="ruleInfo" class="modal-body">
			    <div style="padding: 1%;">
			        <div style="display:inline-block;width: 8%;text-align: right;">订单号：</div>
			        <div style="display:inline-block;width: 20%;text-align: left;" id="auditOrderSn"></div>
			        <div style="display:inline-block;width: 10%;">下单日期：</div>
			        <div style="display:inline-block;width: 20%;" id="auditAddTime"></div>
			        <div style="display:inline-block;width: 10%;">订单总金额：</div>
			        <div style="display:inline-block;width: 25%;" id="auditGoodsAmount"></div>
		        </div>
			    <div style="padding: 1%;">
			        <div style="margin-left: 10px;">购买商品:</div>
			        <div>
			            <table style='width:725px;' class="table table-striped table-bordered" id="auditOrderGoods">
							<tr>
								<td style='width:15%;'>ID</td>
								<td style='width:10%;'>商品编码</td>
								<td style='width:50%;'>名称</td>
								<td style='width:10%;'>规格</td>
								<td style='width:15%;'>数量</td>
							</tr>
						</table>
			        </div>
			    </div>
		    	<div class="right_inline">
					<div class="right_input item_title">说明：</div>
					<div class="right_input" style="width:91%">
						<textarea id="auditRemark" name="comment" class="form-control" style="height: 60px;"></textarea>
					</div>
				</div>
		        <!-- <div style="display:inline-block;margin-left: 5px;">说明：</div>
		        <div style="display:inline-block;text-align: left;width: 92%;">
		            <textarea id="auditRemark" name="comment" class="form-control" style="height: 30px;"></textarea>
		        </div> -->
		     	<div class="right_inline">
					<div class="right_input item_title">预约回访：</div>
					<div class="right_input">
			        	<img class="img-thumbnail" src="<%=request.getContextPath()%>/images/addProd.png" 
									     onclick="show_reserve();" onerror="this.src='<%=request.getContextPath()%>/images/addProd.png';this.onerror=null;"
									     title="新增预约回访信息" alt="新增预约回访信息" style="width: 30px; height: 30px; cursor: pointer" />
					</div>
				</div>
				<div style="clear:both"></div>
		       	<div id="add_reserve" style="display: none;color:black;border:2px solid red">
					<div class="right_inline">
						<div class="right_input item_title">新增预约信息：</div>
						<button type="button" class="btn btn-info sear_btn" style="margin-left: 480px;" onclick="saveReserve();">保存</button>
						<button type="button" class="btn btn-info sear_btn" onclick="closeReserve();">取消</button>
						<div class="right_inline">
							<div class="right_input item_title">客户名称：</div>
							<div class="right_input" style="width:160px">
								<input id="memberName_win" name="memberName_win" type="text" readonly class="form-control item_input"/>
							</div>
							<div class="right_input item_title">&nbsp;&nbsp;&nbsp;&nbsp;手机号：</div>
							<div class="right_input" style="width:160px">
								<input id="tel_win" name="tel_win"  type="text" readonly class="form-control item_input"/>
							</div>
							<div class="right_input item_title">客服工号：</div>
							<div class="right_input" style="width:160px">
								<input id="custServiceNo_win" name="custServiceNo_win" type="text" readonly class="form-control item_input"/>
							</div>
						</div>
						<div class="right_inline">
							<div class="right_input item_title">预约时间：</div>
							<div class="right_input" style="width:160px">
								<input id="reservaTime" name="reservaTime" style="height: 30px;" type="text" onfocus="WdatePicker({minDate:'%y-%M-%d'});" class="form-control item_input"/>
							</div>
							<div class="right_input item_title">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;说明：</div>
							<div class="right_input" style="width: 400px;">
								<textarea id="comment" name="comment" class="form-control" style="height:30px;"></textarea>
							</div>
						</div>
					</div>
					<div style="clear:both"></div>
				</div>	
				<div class="right_inline">
					<div class="right_input item_title">执业药师：${custServCode}</div>
					<div class="right_input" style="float: right;">
			        	<button type="button" class="btn btn-primary" style="text-align: left;" onclick="auditOrder(1,'');">审核通过</button>	
					    <button type="button" class="btn btn-primary" style="text-align: left;margin-right: 10px;" onclick="auditOrder(0,'');">不通过</button>
					</div>
				</div>
				<div style="clear:both"></div>
			    <%-- <div style="padding: 2%;">
			        <div style="display:inline-block;">执业药师：</div>
			        <div style="display:inline-block;">${custServCode}</div>
			        <div style="display:inline-block;float:right;">
			            <button type="button" class="btn btn-primary" style="text-align: left;" onclick="auditOrder(1,'');">审核通过</button>	
					    <button type="button" class="btn btn-primary" style="text-align: left;" onclick="auditOrder(0,'');">不通过</button>	
			        </div>
			    </div> --%>
			</div>
		</div>
	</div>
</div>
<!-- 修改订单 -->
<div class="modal fade" id="updateOrderModal" tabindex="-1" data-backdrop="false">
    <input type="hidden" id="updateUserAddressId" value="" />
    <input type="hidden" id="updatePaymentMethodId" value="" />
    <input type="hidden" id="updateShipTimeId" value="" />
    <input type="hidden" id="updateInvoiceType" value="" />
    <input type="hidden" id="updateInvoiceTitle" value="" />
    <input type="hidden" id="updateRequireNote" value="" />
	<div class="modal-dialog" style="width: 1050px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" onclick="modalClose('updateOrderModal');">&times;</button>
				<h4 class="modal-title" id="myModalLabel">官网订单修改</h4>
			</div>
			<div id="ruleInfo" class="modal-body" >
			    <jsp:include page="/WEB-INF/view/opcenter/custService/addOrder.jsp"></jsp:include>
			</div>
			<div style="clear:both;"></div>
		</div>
	</div>
</div>

<!-- 查看物流信息 role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"-->
<div class="modal fade" id="expressInfoModal" tabindex="-1">
	<div class="modal-dialog" style="width:600px;">
		<div class="modal-content" >
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="myModalLabel">查看物流信息</h4>
			</div>
			<div id="ruleInfo" class="modal-body">
			    <div class="expressInfo">
			        <div>订 单 号：<div id="expressOrderSn" style="display: inline;"></div></div>			        
					<div>收 货 人：<div id="expressReceiveName" style="display: inline;"></div></div>
					<div>收货手机：<div id="expressMobile" style="display: inline;"></div></div>
					<div>收货地址：<div id="expressDetailAddr" style="display: inline;"></div></div>
					<div>快递公司：<div id="expressShipCorpName" style="display: inline;"></div></div>
					<div>运单号：<div id="expressShipNo" style="display: inline;"></div></div>
					<div>物流信息：<div  style="display: inline;">
						<ul id="expressInfos">
						
						</ul>
					</div></div>
			    </div>
			</div>
		</div>
	</div> 
</div>
<!-- 查看订单日志 -->
<div class="modal fade" id="orderLogModal" tabindex="-1">
	<div class="modal-dialog" style="width:600px;">
		<div class="modal-content" >
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="myModalLabel">查看订单日志</h4>
			</div>
			<div id="ruleInfo" class="modal-body">
			    <table id="orderLogInfo" class="table table-striped table-bordered">
		            <tr>
		                <td>订单号</td>
		                <td>快递公司</td>
		                <td>运单号</td>
		                <td>日志内容</td>
		                <td>操作时间</td>
		                <td>操作人</td>
		            </tr>
			    </table>
			</div>
		</div>
	</div> 
</div>
