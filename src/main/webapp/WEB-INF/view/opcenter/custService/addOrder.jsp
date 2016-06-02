<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-select.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-select.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-switch.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/bvalidator/css/bvalidator.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/bvalidator/css/bvalidator.theme.red.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/bvalidator/js/jquery.bvalidator.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/opcenter/addOrder.js"></script>
<script>
/**
 * 加载某个医院里的医生
 */
<%-- function loadDoctor(){
	var hospitalId = $("#hospitalName").val();
	var basePath = '<%=request.getContextPath()%>';
	$.ajax({
		type : "POST",  
		dataType: "json",//返回json格式的数据
		data : {hospitalId: hospitalId},
		url: basePath + "/outScreen2.do?method=getDoctorByHospitalId&id=" + hospitalId,
		success: function(jsonData){
			var doctorSel=document.getElementById("doctorName");
			doctorSel.options.length = 0;
			for(var i = 0; i < jsonData.length; i++){
				var newOption = document.createElement("OPTION");
				newOption.value= jsonData[i].id;
				newOption.text=jsonData[i].doctorName;
				doctorSel.options.add(newOption);
			}
			for(i=0;i<doctorSel.length;i++){
			  if(doctorSel[i].value == "${doctorId}")
				  doctorSel[i].selected = true;
			}
		}
	});
} --%>

/**
 * 提交医生表单
 */
 /* function submitDoctorForm(){
 	var doctorId = $("#doctorName").val();
 	if(null != doctorId && parseInt(doctorId) > 0){
 		$("#doctorId").val(parseInt(doctorId))
 		$("#doctorForm").submit();
 	}else{
 		alert("亲~请选择医生姓名！");
 	}
 } */
</script>
<style type="text/css">
	#buy_table thead tr th{font-size:14px;background-color: #f9f9f9;}
	input.item_input {height: 30px;}
	.item_title {text-align: right;width: auto;padding-top: 5px;margin-left: 15px;}
	.sear_btn{margin-left: 20px; padding: 0 12px; height: 30px;}
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
	.tc-text{
	    width:60px;
	}
</style>
<div id="addOrder" style="min-width: 1000px;">
<input type="hidden" id="orderOperationType" value="${orderOperationType}" />
<input type="hidden" id="previous" value="0" />
<input type="hidden" name="outAction" value="${outAction}"/>
<input type="hidden" name="actionName" value="${actionName}"/>
<input type="hidden" name="hospitalId" value="${hospitalId}"/>
<input type="hidden" name="doctorId" value="${doctorId}"/>
<input type="hidden" id="twoDimensionCodeId" value="${twoDimensionCodeId}"/>
<input type="hidden" name="dateString" value="${dateString}"/>
<div style="min-width: 1000px;" id="tep1">
	<form action="" class="form-horizontal" method="post">
	    <div class="right_inline" id="updateOrderCode" style="display: none;">
	        <div class="right_input item_title">订单编号：</div>
	        <div class="right_input">
				<p class="form-control-static" id="orderCode" style="margin-top:-2px;"></p>
			</div>
	    </div>
		<div class="right_inline">
		    <div class="right_input item_title">订单来源：</div>
			<div class="right_input input_div" id="cpsLaiyuanInfo" style="width: 185px;" >
				<select name="memberStatus" id="cpsLaiyuan" class="selectpicker form-control select_set" data-size="10">
					<option value="">--请选择--</option>
					<c:forEach items="${cpsLaiyuan}" var="cpsLaiyuanlist">
					    <option value="${cpsLaiyuanlist.cpsFlag}">${cpsLaiyuanlist.merchantName}</option>
					</c:forEach>
				</select>
			</div>
			<div id="updateLaiyuan" class="right_input input_div" style="width: 185px;"></div>
			<div class="right_input item_title">订单类型：</div>
			<div class="right_input input_div" >
				<select name="memberStatus" id="orderType" class="selectpicker form-control select_set" onchange="OrderTypeChange(this);">
					<option value="1" ${orderType=='1'?'selected="selected"':''}>代下单</option>
					<option value="2" ${orderType=='2'?'selected="selected"':''}>处方药</option>
					<option value="3" ${orderType=='3'?'selected="selected"':''}>惠氏订单</option>
					<option value="4" ${orderType=='4'?'selected="selected"':''}>金斯利安</option>
				</select>
			</div>
			<div class="right_input item_title" style="width: 74px;padding-left: 0px;">下单客服ID：</div>
			<div class="right_input" style="width: 140px;">
			    <input type="text" id="adminUser" value="${adminUser}" class="form-control item_input" readonly="readonly" />
			</div>
			<div class="right_input item_title">客服姓名：</div>
			<div class="right_input" style="width: 140px;">
			    <input type="text" id="adminUserName" value="${adminUserName }" class="form-control item_input" readonly="readonly" />			    
			</div>			
		</div>
		<div class="right_inline">
		    <div class="right_input item_title">下单用户：</div>
			<div class="right_input" style="width: 120px">
				<input id="order_user" name="order_user" type="text" class="form-control item_input" value="${orderUserId}" readonly="readonly"/>				
			</div>
			<div class="right_input">
			<img id="updateImg" class="img-thumbnail" src="<%=request.getContextPath()%>/images/search.png" data-toggle="modal" data-target="#myModal1" class="btn btn-default"
									     onclick="search_user();" onerror="this.src='<%=request.getContextPath()%>/images/search.png';this.onerror=null;"
									     title="查询用户" alt="查询用户" style="width: 30px; height: 30px; cursor: pointer" />
			</div>
			<div class="right_input item_title">用户账号：</div>
			<div class="right_input" style="width: 150px">
			    <input type="text" id="order_account" name="order_account" value="${orderUserAccount}" class="form-control item_input" readonly="readonly" />
			</div>
		    <div class="right_input item_title">用户手机号：</div>
			<div class="right_input" style="width: 130px;">
			    <input type="text" id="orderPhone" value="${orderUserPhone}" class="form-control item_input" readonly="readonly" />
			</div>		    
			<div style="clear:both;"></div>
		</div>
		<div class="right_inline">
		    <%-- <div class="right_input item_title">医院：</div>
			<div class="right_input input_div" id="cpsLaiyuanInfo" style="width: 185px;" >
				<select name="memberStatus" id="cpsLaiyuan" class="selectpicker form-control select_set" data-size="10">
					<option value="">--请选择--</option>
					<c:forEach items="${cpsLaiyuan}" var="cpsLaiyuanlist">
					    <option value="${cpsLaiyuanlist.cpsFlag}">${cpsLaiyuanlist.merchantName}</option>
					</c:forEach>
				</select>
			</div>
			<div id="updateLaiyuan" class="right_input input_div" style="width: 185px;"></div>
			<div class="right_input item_title">医生：</div>
			<div class="right_input input_div" >
				<select name="memberStatus" id="orderType" class="selectpicker form-control select_set" onchange="OrderTypeChange(this);">
					<option value="1" ${orderType=='1'?'selected="selected"':''}>代下单</option>
					<option value="2" ${orderType=='2'?'selected="selected"':''}>处方药</option>
					<option value="3" ${orderType=='3'?'selected="selected"':''}>惠氏订单</option>
					<option value="4" ${orderType=='4'?'selected="selected"':''}>金斯利安</option>
				</select>
			</div> --%>
			<div class="right_input item_title">医院：</div>
			<div class="right_input input_div" id="cpsLaiyuanInfo" style="width: 185px;">
				<select name="memberStatus" id="hospitalName" ${doctorLock==true?'disabled="true"':''} class="selectpicker form-control select_set" data-size="8" onchange="loadDoctor('',false);">
					<option value="">--请选择--</option>
					<c:forEach items="${salesRepList}" var="salesRep">
					    <option value="${salesRep.id}" ${hospitalId==salesRep.id?'selected="selected"':''}>${salesRep.hospitalName}</option>
					</c:forEach>
				</select>
			</div>
			<div id="updateLaiyuan" class="right_input input_div" style="width: 185px;"></div>
			<div class="right_input item_title">医生：</div>
			<div class="right_input input_div" >
				<select id="doctorName" ${doctorLock==true?'disabled="true"':''} class="selectpicker form-control select_set" data-size="8" onchange="doctorChange();">
					<option value="">--请选择--</option>
				</select>
			</div>
			<div class="right_input" style="width: 150px">
				<div class="right_input item_title" >添加商品：</div>
				<img class="img-thumbnail" src="<%=request.getContextPath()%>/images/addProd.png" 
									     onerror="this.src='<%=request.getContextPath()%>/images/addProd.png';this.onerror=null;"
									     title="添加商品" alt="添加商品" style="width: 30px; height: 30px; cursor: pointer" onclick="openProductWin();" />
			</div>
			<div class="right_input" style="width: 120px;margin-left: -10px;" >
				<button type="button" class="btn btn-info sear_btn" onclick="add_office_order();">官网模式下单</button>
			</div>
		</div>
		<div style="clear:both;"></div>
	</form>
	<%-- <form id="doctorForm" action="<%=request.getContextPath()%>/doctorInHospital.do?method=addDoctorUrl" method="post">
		<input type="text" id="doctorId" name="doctorId" />
	</form> --%>
	<div style="width:1000px;padding-left: 15px;">
		<table id="product_table" class="table table-bordered">
			<thead>
				<tr>
					<th width="40px;">操作</th>
					<th width="60px;">套餐ID</th>
					<th width="250px;">套餐名称</th>
					<th width="100px;">主商品编码</th>
					<th width="100px;">主商品规格</th>
					<th width="70px;">库存</th>
					<th width="130px;">数量</th>
					<th width="70px;">单价</th>
					<th width="80px;">金额</th>
				</tr>
			</thead>
			<tbody id="tb_body">
				<c:forEach items="${prodList}" var="item">
					<tr>
						<td align='center'><img class="img-thumbnail"
							src="<%=request.getContextPath()%>/images/delete.png"
							onclick="removeRow(this);"
							onerror="this.src='<%=request.getContextPath()%>/images/delete.png';this.onerror=null;"
							title="删除" alt="删除"
							style="width: 25px; height: 25px; cursor: pointer" /></td>
						<td>${item.mealId}</td>
						<td>${item.mealName}</td>
						<td>${item.mainSku}</td>
						<td>${item.mealNormName}</td>
						<td>${item.stockCount}</td>
						<td>
							<div class="tc-amount">
								<span class="minus" onclick="minus(this,'${item.prescriptionType}');">-</span>
								<input class="tc-text amount" type="text" value="${item.buyCount}" onkeyup="text_change(this,'${item.prescriptionType}');" /> 
								<span class="plus" onclick="plus(this,'${item.prescriptionType}');">+</span>
							</div>
						</td>
						<%-- <td>
						    <c:choose>
								<c:when test='${item.prescriptionType != null && item.prescriptionType == 3}'>
									<input class="tc-text amount" type="text" value="${item.price}" onkeyup="text_price(this)" />
								</c:when>
								<c:otherwise>
		                            ${item.price}
		                        </c:otherwise>
							</c:choose>
						</td> --%>
						<td>${item.price}</td>
						<td>${item.amount}</td>
						<td style='display:none;'>${item.productId}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
		
	<div class="right_inline">
		<div class="right_input item_title">选择快递：</div>
		<c:forEach items="${shipArray}" var="ship" >
		    <c:choose>
		        <c:when test='${ship.expressCode != null && ship.expressCode != "STKD"}'>
		            <div class="right_input" style="width: 8%;">
						<input type='radio' onclick="getExpress('${ship.expressCode}')" name='expressInfo' ${ship.expressCode =='PTKD'?'checked':''} value="${ship.shipFeeId}">${ship.name}
				    </div>
		        </c:when>
		    </c:choose>
		</c:forEach>
		<div id="update_expressInfo"></div>
	</div>
	<div class="right_inline">
		<div class="right_input item_title">促销信息：</div>
		<div class="right_input">
			<p class="form-control-static" style="margin-top: 6px" id="cheapInfo">${orderInfo.cheapInfo}</p>
		</div>		
	</div>
	<div class="right_inline">
		<div class="right_input item_title">邮费：￥</div>
		<div class="right_input">
			<p class="form-control-static" style="margin-top: 6px" id="postageInfo">${orderInfo.postageInfo}</p>
		</div>
	</div>
	<div id="offerPrice">
	    <sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_OfferPrice">
			<div class="right_inline">
			    <div class="right_input item_title">优惠金额：</div>
			    <div class="right_input">
					<input type="text" id="adminDiscount" onkeyup="inputChecking();" value="" class="form-control">
				</div>
			</div>
		</sec:authorize>
	</div>
	<div class="right_inline">
		<div class="right_input item_title">商品总价（含运费）：￥</div>
		<div class="right_input">
			<p class="form-control-static" style="margin-top: 6px" id="totalAmtInfo">${orderInfo.totalAmtInfo}</p>
		</div>
	</div>		
	<div class="left_button" style="float:right">
		<button type="button" class="btn btn-info sear_btn" style="margin-right: 400px;" onclick="order_next()">下一步</button>
	</div>
	<div style="clear:both;"></div>
</div>

<div style="min-width: 1000px;display: none" id="tep2">
	<!-- <form action="" class="form-horizontal" role="form" method="post" id="form2"> -->
		<div id="address_step1">
			<div class="right_inline">
				<div class="right_input item_title">收货地址：</div>
				<button type="button" class="btn btn-info sear_btn" style="margin-left: 400px;" onclick="change_address()">更换地址</button>
			</div>
			<div class="right_inline">
				<div class="right_input item_title" id="default_address">
				</div>
			</div>
			<div style="clear:both"></div>
		</div>
		<div id="address_step2" style="display: none;color:black;border:2px solid red">
		<form id="form1" method="post">
			<div class="right_inline">
				<div class="right_input item_title">收货地址：</div>
				<button type="button" class="btn btn-info sear_btn" style="margin-left: 200px;" onclick="saveOrUpdateAddress();">保存</button>
				<button type="button" class="btn btn-info sear_btn" onclick="getAddressInfo();">修改</button>
				<button type="button" class="btn btn-info sear_btn" onclick="closeAddressWin();">关闭</button>
			</div>
			<div id="address_step3" >
			</div>
			<div id="address_step4" style="display: none;">
				<div class="right_inline">
					<div class="right_input item_title">&nbsp;&nbsp;&nbsp;&nbsp;收货人<span style="color: red;">*</span>：</div>
					<div class="right_input" style="width: 150px">
						<input id="consignee" name="consignee" type="text" class="form-control item_input" data-bvalidator="required,required"/>
					</div>
				</div>
				<div class="right_inline">
					<div class="right_input item_title">所在地区<span style="color: red;">*</span>：</div>
					<div class="right_input" >
						<select id="provinceId"  name="provinceId" onchange="setCity()" style="height: 30px;" data-bvalidator="required,required"></select>
					</div>
					<div class="right_input" >
						<select id="cityId"  name="cityId" onchange="setDistrict()" style="height: 30px;" data-bvalidator="required,required"></select>
					</div>
					<div class="right_input" >
						<select id="districtId" name="districtId"  onchange="changeDistrict()" style="height: 30px;" data-bvalidator="required,required"></select>
					</div>
					<div class="right_input" style="width: 300px">
						<input id="streetId" name="streetId" type="text" class="form-control item_input" data-bvalidator="required,required"/>
					</div>
				</div>
				<div class="right_inline">
					<div class="right_input item_title">手机号码<span style="color: red;">*</span>：</div>
					<div class="right_input" style="width: 150px">
						<input id="phoneNo" name="phoneNo" type="text" class="form-control item_input" data-bvalidator="digit,maxlength[11],minlength[11],required"/>
					</div>
					<div class="right_input item_title">邮编：</div>
					<div class="right_input" style="width: 150px">
						<input id="zipCode" name="zipCode" type="text" class="form-control item_input" data-bvalidator="minlength[6],maxlength[6]"/>
					</div>
				</div>
				<div class="right_inline">
					<div class="right_input">
						<button type="button" class="btn btn-info sear_btn" style="margin-left: 90px;" onclick="saveOrUpdateAddress();">保存收货人地址</button>
					</div>
				</div>
			</div>
			<div style="clear:both"></div>
			</form>
		</div>		
		<div class="right_inline">
			<br/>
			<div class="right_input item_title">支付方式：</div>
		</div>
		<div id="pay_method" >
		</div>
		<div class="right_inline">
			<br/>
			<div class="right_input item_title">送货时间：</div>
		</div>
		<div id="ship_time" >			
		</div>
		<div class="right_inline">
			<div class="right_input item_title">
			    <br/>
				<input type="checkbox" name="if_bill" id='if_bill' value="" onclick="boxClick();">需要发票(普通发票)
			</div>
		</div>
		<div class="right_inline" style="padding-left: 20px;" id="billInfo">
		 <div class="right_inline">
			<br/>
			<div class="right_input item_title">纸质：</div>
		 
			<div class="right_input item_title">
				<input type='radio' name='invoiceType' onclick="getInvoiceTyep(1);" value="1">个人 
			</div>
			<div class="right_input item_title">
				<input type='radio' name='invoiceType' onclick="getInvoiceTyep(2);" value="2">单位
			</div>
			<div class="right_input">
				<input type="text" style="display: none;" id="invoiceTitle" name="invoiceTitle" class="form-control item_input" value="" />
			</div>
		 </div>	
			<div class="right_inline">
			  <br/>
			  <div class="right_input item_title">电子：</div>
		    
			<div class="right_input item_title">
				<input type='radio' name='invoiceType' onclick="getInvoiceTyep(1);" value="11">电子发票个人 
			</div>
			<div class="right_input item_title">
				<input type='radio' name='invoiceType' onclick="getInvoiceTyep(3);" value="22">电子发票单位
			</div>
			<div class="right_input">
				<input type="text" style="display: none;" id="invoiceTitle2" name="invoiceTitle" class="form-control item_input" value="" />
			</div>
	     </div>
	
		</div>
		<div class="right_inline">
		    <br/>
		    <div class="right_input item_title">备注：</div>
		    <div class="right_input" style="width: 40%;">
		    	<textarea id="requireNote" name="requireNote" class="form-control" rows="2"></textarea>
		    </div>
		</div>
		<div class="right_inline">
			<br/>
			<div class="right_input item_title">订单详情：</div>
		</div>
		<div style="clear:both;"></div>
	    <div style="width:950px;padding-left: 15px;">
	        <table id="buy_table" class="table table-bordered">
				<thead>
					<tr>
						<th>套餐ID</th>
						<th>套餐名称</th>
						<th>主商品编码</th>
						<th>主商品规格</th>
						<th>数量</th>
						<th>单价</th>
						<th>金额</th>
					</tr>
				</thead>
				<tbody id="buy_body"></tbody>
			</table>
	    </div>
	<div class="right_inline">
		<div class="right_input item_title">商品总价：￥</div>
		<div class="right_input">
			<p class="form-control-static" style="margin-top: 6px" id="totalAmount">${orderInfo.totalAmount}</p>
		</div>
		<div class="right_input item_title">优惠金额：￥</div>
		<div class="right_input">
			<p class="form-control-static" style="margin-top: 6px" id="cheapAmt">${orderInfo.cheapAmt}</p>
		</div>
		<div class="right_input item_title">快递费用：￥</div>
		<div class="right_input">
			<p class="form-control-static" style="margin-top: 6px" id="postage">${orderInfo.postage}</p>
		</div>
		<div class="right_input item_title">订单总价：￥</div>
		<div class="right_input">
			<p class="form-control-static" style="margin-top: 6px" id="orderAmt">${orderInfo.orderAmt}</p>
		</div>
	</div>
	<!-- </form> -->
	<div class="left_button" style="float:right">
		<button type="button" class="btn btn-info sear_btn" onclick="previous();">上一步</button>
		<button type="button" id="orderSubmit" class="btn btn-info sear_btn" style="margin-right: 300px;" onclick="orderSubmit();">提交订单</button>
	</div>
	<div style="clear:both;"></div>
</div>
</div>

<div class="modal fade" id="myModal1" tabindex="-1" data-backdrop="false">
	<div class="modal-dialog" style="width:800px;">
		<div class="modal-content" >
			<div class="modal-header">
				<button type="button" class="close" onclick="modalClose('myModal1');">&times;</button>
				<h4 class="modal-title" id="myModalLabel">选择下单用户</h4>
			</div>
			<div id="ruleInfo" class="modal-body">
				<table>
					<tr>
						<td align="left" width="7%">用户ID:</td>
						<td align="left" width="30%"><input type="text" id="userId" name="userId" value="" class="form-control" placeholder="请输入ID，多个用户用','号分隔"/></td>
						<td width="10%" align="right">用户账号:</td>
						<td width="20%"><input type="text" id="userAccount" name="userAccount" value="" class="form-control" placeholder="请输入用户账号"/></td>
						<td width="8%" align="right">手机号:</td>
						<td width="20%"><input type="text" id="tel" name="tel" value="" class="form-control" placeholder="请输入手机号"/></td>
						<td width="8%" align="right"><input type="button" value="查询" onclick="search_user();" class="btn btn-primary" style="width: 50px"/></td>
					</tr>
				</table>
				<br>
				<table class="table table-striped table-bordered" id="user_table">
					<thead>
					<tr>
						<th align="center" width='10%'>选择</th>
						<th align="center"  width='10%'>ID</th>
						<th align="center"  width='20%'>账号</th>
						<th align="center" width='15%'>真实名称</th>
						<th align="center" width='19%'>手机</th>
						<th align="center" width='28%'>邮箱</th>
					</tr>
					</thead>
					<tbody id="user_body"></tbody>
				</table>
				<div class="navigation"> 
					<div style="text-align: right; float: right;"> 
						共<label id="lblToatl_user"></label>条数据 第[<label id="lblCurent_user"></label>]页/共[<label id="lblPageCount_user">0</label>]页 
						<a id="first_user" href="#">首页</a> <a id="previous_user" href="#">上一页</a> <a id="next_user" href="#"> 
						下一页</a> <a id="last_user" href="#">末页</a> 
					</div> 
				</div> 
			</div>
		</div>
	</div> 
</div>

<div class="modal fade" id="myModal2" tabindex="-1" data-backdrop="false">
	<div class="modal-dialog" style="width:1000px;">
		<div class="modal-content" >
			<div class="modal-header">
				<button type="button" class="close" onclick="modalClose('myModal2');">&times;</button>
				<h4 class="modal-title" id="myModalLabel">选择商品</h4>
			</div>
			<div id="ruleInfo" class="modal-body">
				<input type="hidden" id="products" value="" />
				<table style="width: 850px;">
					<tr>
						<td align="left" style="width: 45px;">套餐ID:</td>
						<td align="left" style="width: 160px;">
						    <input type="text" id="prodId" name="prodId" value="" class="form-control" placeholder="请输入套餐ID"/>
						</td>
						<td style="width: 10px;">&nbsp;&nbsp;</td>
						<td style="width: 60px;">套餐名称:</td>
						<td style="width: 160px;">
						    <input type="text" id="prodNm" name="prodNm" value="" class="form-control" placeholder="请输入套餐名称" />
						</td>
						<td style="width: 10px;">&nbsp;&nbsp;</td>
						<td style="width: 60px;">商品编码:</td>
						<td style="width: 160px;">
						    <input type="text" id="prodNo" name="prodNo" value="" class="form-control" placeholder="请输入商品编码"/>
						</td>
						<td style="width: 10px;">&nbsp;&nbsp;</td>
						<td width="5%">
						    <input type="button" value="查询" onclick="searchProductBtn();" class="btn btn-primary" />
						</td>
					</tr>
				</table>
				<br>
				<table class="table table-striped table-bordered">
					<thead>
					<tr>
						<th align="center">选择</th>
						<th align="center" colspan="1">套餐ID</th>
						<th align="center">套餐名称</th>
						<th align="center">多sku选择</th>
						<th align="center">主商品编码</th>
						<th align="center">主商品规格</th>
						<th align="center">单价</th>
						<th align="center">库存量</th>
						<th align="center">处方药类型</th>											
					</tr>
					</thead>
					<tbody id="product_body"></tbody>
				</table>
				<div class="navigation"> 
					<div style="text-align: right; float: right;"> 
						共<label id="lblToatl"></label>条数据 第[<label id="lblCurent"></label>]页/共[<label id="lblPageCount">0</label>]页 
						<a id="first" href="#">首页</a> <a id="previou" href="#">上一页</a> <a id="next" href="#"> 
						下一页</a> <a id="last" href="#">末页</a> 
					</div> 
				</div> 
			</div>
			<div id="plansInfo" class="modal-body" style="display: none;">
				<table class="table table-striped table-bordered">
					<thead>
					<tr>
						<th align="center">选择</th>
						<th align="center" colspan="1">套餐ID</th>
						<th align="center">套餐名称</th>
						<th align="center">主商品编码</th>
						<th align="center">主商品规格</th>
						<th align="center">单价</th>
						<th align="center">库存量</th>
						<th align="center">处方药类型</th>											
					</tr>
					</thead>
					<tbody id="product_plans_body"></tbody>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal" style="margin-left: 500px;">取消</button>
       			<button type="button" id="submitBtn" class="btn btn-primary" onclick="choice_prod();">确定</button>  
			</div>
		</div>
	</div> 
</div>

<div class="modal fade" id="skuModel" tabindex="-1" data-backdrop="false">
	<div class="modal-dialog" style="width:600px;">
		<div class="modal-content" >
			<div class="modal-header">
				<button type="button" class="close" onclick="modalClose('skuModel');">&times;</button>
				<h4 class="modal-title" id="myModalLabel">选择商品sku</h4>
			</div>
			<div id="skuInfo" class="modal-body">
				<table class="table table-striped table-bordered">
					<thead>
					<tr>
						<th align="center">选择</th>
						<th align="center">套餐ID</th>
						<th align="center">主商品编码</th>
						<th align="center">规格名</th>
						<th align="center">单价</th>
						<th align="center">库存量</th>
						<th align="center">主商品ID</th>
					</tr>
					</thead>
					<tbody id="sku_body"></tbody>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal" style="margin-left: 200px;">取消</button>
       			<button type="button" id="skuBtn" class="btn btn-primary" onclick="choice_sku();">确定</button>  
			</div>
		</div>
	</div> 
</div>