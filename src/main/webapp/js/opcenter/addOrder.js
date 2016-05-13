var provinceId = "";
var cityId = "";
var districtId = "";

var pageIndex = 1; //商品页索引 
var pageIndex_user = 1;//下单用户页索引
//表单校验参数
var optionts = {
		lang : 'zh_CN',
		showCloseIcon : true,
		position : {
			x : 'right',
			y : 'top'
		}, 
		showErrMsgSpeed : 'normal',//'fast', 'normal', 'slow' 
		validateOn : 'blur',//'change', 'blur', 'keyup'
		classNamePrefix : 'bvalidator_red_'//bvalidator_red_,bvalidator_orange_,bvalidator_gray2_,bvalidator_postit_
}; 
$(document).ready(function(){	
	$('.selectpicker').selectpicker();
	jQuery('#form1').bValidator(optionts,'formInstance'); 
	$.getScript("http://design.ehaoyao.com/mall/js/commonArea.js",function(){
		setProvince();
	});
	var hospitalId_txt = $("#addOrder input[name=hospitalId]").val();
	if(hospitalId_txt > 0){
		loadDoctor(hospitalId_txt,true);
	}
	$("#first").click(function() { 
		pageIndex = 1; 
		$("#lblCurent").text(1); 
		search_product(); 
	}); 
	//上一页按钮click事件 
	$("#previou").click(function() { 
		if (pageIndex != 1) { 
			pageIndex--; 
			$("#lblCurent").text(pageIndex); 
		} 
		search_product(); 
	}); 
	//下一页按钮click事件 
	$("#next").click(function() { 
		var pageCount = parseInt($("#lblPageCount").text()); 
		if (pageIndex != pageCount) { 
			pageIndex++; 
			$("#lblCurent").text(pageIndex); 
		} 
		search_product(); 
	}); 
	//最后一页按钮click事件 
	$("#last").click(function() { 
		var pageCount = parseInt($("#lblPageCount").text()); 
		pageIndex = pageCount; 
		$("#lblCurent").text(pageIndex);
		search_product(); 
	});
	
	$("#first_user").click(function() { 
		pageIndex_user = 1; 
		$("#lblCurent_user").text(1); 
		search_user(); 
	}); 
	//上一页按钮click事件 
	$("#previous_user").click(function() { 
		if (pageIndex_user != 1) { 
			pageIndex_user--; 
			$("#lblCurent_user").text(pageIndex_user); 
		} 
		search_user(); 
	}); 
	//下一页按钮click事件 
	$("#next_user").click(function() { 
		var pageCount = parseInt($("#lblPageCount_user").text()); 
		if (pageIndex_user != pageCount) { 
			pageIndex_user++; 
			$("#lblCurent").text(pageIndex_user); 
		} 
		search_user(); 
	}); 
	//最后一页按钮click事件 
	$("#last_user").click(function() { 
		var pageCount = parseInt($("#lblPageCount_user").text()); 
		pageIndex_user = pageCount; 
		$("#lblCurent").text(pageIndex_user);
		search_user(); 
	});
});
/**
 * 加载某个医院里的医生
 */
function loadDoctor(hospitalId,isInit){
	if(hospitalId == null || hospitalId == ''){
		hospitalId = $("#hospitalName").val();
	}
	var type = $("#orderOperationType").val().trim();
	var url = "";
	if(null != type && type == "updateOrder"){
		url = $("#addOrder input[name=outAction]").val()+"?method=getDoctorByHospitalId";
	} else if(null != type && type == "addOrder"){
	    url = $("#addOrder input[name=actionName]").val()+"?method=getDoctorByHospitalId";
	}
	$.ajax({
		type : "POST",  
		dataType: "json",//返回json格式的数据
		data : {hospitalId: hospitalId},
		url: url,
		success: function(jsonData){
			var ops = "<option value=''>--请选择--</option>";
			for(var i = 0; i < jsonData.length; i++){
				ops += "<option value='"+jsonData[i].id+"'>"+jsonData[i].doctorName;+"</option>";
			}
			$("#doctorName").html(ops);
			if(isInit){
				$('#doctorName').selectpicker('val', $("#addOrder input[name=doctorId]").val());
				$('#doctorName').selectpicker('render');
			}
			$("#doctorName").selectpicker("refresh");
		}
	});
}
/**
 * 选择医生的时候把医院ID和医生ID放到session中
 */
function doctorChange(){
	var hospitalId = $("#hospitalName").val();;
	var doctorId = $("#doctorName").val();;
	var dateString = $("#addOrder input[name=dateString]").val();
	var type = $("#orderOperationType").val().trim();
	var url = "";
	if(null != type && type == "updateOrder"){
		url = $("#addOrder input[name=outAction]").val()+"?method=doctorChange";
	} else if(null != type && type == "addOrder"){
	    url = $("#addOrder input[name=actionName]").val()+"?method=doctorChange";
	}
	$.ajax({
		type : "POST",  
		dataType: "text",
		data : {
			type:type,
			hospitalId: hospitalId,
			doctorId:doctorId,
			dateString:dateString
		},
		url:url,
        success: function(data){
        	if(data != null && data.result != null && data.result.trim() == "ok"){
        		$("#addOrder input[name=hospitalId]").val(data.hospitalId);
        		$("#addOrder input[name=doctorId]").val(data.doctorId);
        	}
        }
	});	
}
/**
 * 省份
 */
function setProvince(){
	var options = getOption("","所有省份",provinceId);
	$.each(areaAllJson,function(index,area){
		if(area.parentId=='0'){
			options += getOption(area.areaCode,area.areaName,provinceId);
		}
	});
	$('#provinceId').html(options);
	setCity(cityId);
	setDistrict(districtId);
}
/**
 * 市
 */
function setCity(selectCityId){
	var options = getOption("","所有城市",selectCityId);
	var selectId = $("#provinceId").val();
	if(selectId!=""){
		$.each(areaAllJson,function(index,area){
			if(area.parentId==selectId){
				options += getOption(area.areaCode,area.areaName,selectCityId);
			}
		});
	}
	$('#cityId').html(options);
	$('#province').val($("#provinceId").find("option:selected").text());
}

/**
 * 区
 */
function setDistrict(selectDistrictId){
	var options = getOption("","所有区域",selectDistrictId);
	var selectId = $("#cityId").val();
	if(selectId!=""){
		$.each(areaAllJson,function(index,area){
			if(area.parentId==selectId){
				options += getOption(area.areaCode,area.areaName,selectDistrictId);
			}
		});
	}
	$('#districtId').html(options);
	$('#city').val($("#cityId").find("option:selected").text());
}
/**
 * 省市区下拉菜单拼接
 */
function getOption(id,name,selectId){
	var s = "";
	if(id==selectId){
		s += 'selected="selected"';
	}
	return '<option '+s+' value="'+id+'">'+name+'</option>';
}
/**
 * 收货人地址区域发生改变时触发函数
 */
function changeDistrict(){
	$('#district').val($("#districtId").find("option:selected").text());
}
/**
 * 数量减少
 */
function minus(obj,prescriptionType){
	var rowIndex = obj.parentElement.parentElement.parentElement.rowIndex;
	var cellIndex = obj.parentElement.parentElement.cellIndex;
	var price = 0;
	/* if(null != prescriptionType && prescriptionType == 3){
		price = document.getElementById("product_table").rows[rowIndex].cells[cellIndex+1].getElementsByTagName("input")[0].value;
	} else {
	    price = document.getElementById("product_table").rows[rowIndex].cells[cellIndex+1].innerHTML;
	} */
	price = document.getElementById("product_table").rows[rowIndex].cells[cellIndex+1].innerHTML;
	var qty = obj.parentElement.getElementsByTagName("input")[0].value;
	if(qty == 1){
		obj.parentElement.getElementsByTagName("input")[0].value = 1;
	}else{
		obj.parentElement.getElementsByTagName("input")[0].value = --qty;
	}
	var secondID = document.getElementById("product_table").rows[rowIndex].cells[cellIndex-5].innerHTML;
	var productID = document.getElementById("product_table").rows[rowIndex].cells[cellIndex+3].innerHTML;
	document.getElementById("product_table").rows[rowIndex].cells[cellIndex+2].innerHTML = (price * qty).toFixed(2);
	updateSession(secondID.trim(),productID.trim(),price,qty,(price * qty).toFixed(2));
}
/**
 * 数量增加
 */
function plus(obj,prescriptionType){
	var rowIndex = obj.parentElement.parentElement.parentElement.rowIndex;
	var cellIndex = obj.parentElement.parentElement.cellIndex;
	var secondID = document.getElementById("product_table").rows[rowIndex].cells[cellIndex-5].innerHTML;
	var productID = document.getElementById("product_table").rows[rowIndex].cells[cellIndex+3].innerHTML;
	var price = 0;
	/* if(null != prescriptionType && prescriptionType == 3){
		price = document.getElementById("product_table").rows[rowIndex].cells[cellIndex+1].getElementsByTagName("input")[0].value;
	} else {
        price = document.getElementById("product_table").rows[rowIndex].cells[cellIndex+1].innerHTML;
	} */
    price = document.getElementById("product_table").rows[rowIndex].cells[cellIndex+1].innerHTML;
	var qty = obj.parentElement.getElementsByTagName("input")[0].value;
	obj.parentElement.getElementsByTagName("input")[0].value = ++qty;
	document.getElementById("product_table").rows[rowIndex].cells[cellIndex+2].innerHTML = (price * qty).toFixed(2);
	updateSession(secondID.trim(),productID.trim(),price,qty,(price * qty).toFixed(2));
}
/**
 * 修改session
 */
function updateSession(secondId,productID,price,qty,amount){
	var expressId = $('input[name="expressInfo"]:checked').val();
	//var dateString = '${dateString}';
	var dateString = $("#addOrder input[name=dateString]").val();
	var type = $("#orderOperationType").val().trim();
	var url = "";
	if(null != type && type == "updateOrder"){
		url = $("#addOrder input[name=outAction]").val()+"?method=updateSession";
		//url = "${outAction}?method=updateSession";
	} else if(null != type && type == "addOrder"){
	    //url = "${actionName}?method=updateSession";
	    url = $("#addOrder input[name=actionName]").val()+"?method=updateSession";
	}
	$.ajax({
		type : "POST",  
		dataType: "json",//返回json格式的数据
		data : {
			type:type,
			mealId: secondId,
			productId:productID,
			price: price,
			buyCount : qty,
			amount : amount,
			expressId:expressId,
			dateString:dateString
		},
		url:url,
        success: function(data){
        	if(data != null && data.result != null && data.result.trim() == "yes"){
        		inputText(data);
        	}
        }
	});	
}
/**
 * 数量文本框输入事件
 */
function text_change(obj,prescriptionType){
	var rowIndex = obj.parentElement.parentElement.parentElement.rowIndex;
	var cellIndex = obj.parentElement.parentElement.cellIndex;
	var price = 0;
	/* if(null != prescriptionType && prescriptionType == 3){
		price = document.getElementById("product_table").rows[rowIndex].cells[cellIndex+1].getElementsByTagName('input')[0].value;
	} else {
	    price = document.getElementById("product_table").rows[rowIndex].cells[cellIndex+1].innerHTML;
	} */
	price = document.getElementById("product_table").rows[rowIndex].cells[cellIndex+1].innerHTML;
	var qty = obj.parentElement.getElementsByTagName("input")[0].value;
	document.getElementById("product_table").rows[rowIndex].cells[cellIndex+2].innerHTML = (price * qty).toFixed(2);
	var secondID = document.getElementById("product_table").rows[rowIndex].cells[cellIndex-5].innerHTML;
	var productID = document.getElementById("product_table").rows[rowIndex].cells[cellIndex+3].innerHTML;
	updateSession(secondID.trim(),productID.trim(),price,qty,(price * qty).toFixed(2));
}
/**
 * 修改单价
 */
function text_price(obj){
	var rowIndex = obj.parentElement.parentElement.rowIndex;
	var cellIndex = obj.parentElement.cellIndex;
	var qty = document.getElementById("product_table").rows[rowIndex].cells[cellIndex-1].getElementsByTagName('input')[0].value;
	var price = obj.parentElement.getElementsByTagName("input")[0].value;
	document.getElementById("product_table").rows[rowIndex].cells[cellIndex+1].innerHTML = (price * qty).toFixed(2);
	var secondID = document.getElementById("product_table").rows[rowIndex].cells[cellIndex-5].innerHTML;
	var productID = document.getElementById("product_table").rows[rowIndex].cells[cellIndex+2].innerHTML;
	updateSession(secondID.trim(),productID.trim(),price,qty,(price * qty).toFixed(2));
}
/**
 * 删除一行,同时删除session中的此商品
 */
function removeRow(obj){
	var expressId = $('input[name="expressInfo"]:checked').val();
	var rowIndex = obj.parentElement.parentElement.rowIndex;
	var secondId = document.getElementById("product_table").rows[rowIndex].cells[1].innerHTML;
	var productId = document.getElementById("product_table").rows[rowIndex].cells[9].innerHTML;
	var dateString = $("#addOrder input[name=dateString]").val();
	var type = $("#orderOperationType").val().trim();
	var url = "";
	if(null != type && type == "updateOrder"){
		//url = "${outAction}?method=deleteSessionRow";
		url = $("#addOrder input[name=outAction]").val()+"?method=deleteSessionRow";
	}
	if(null != type && type == "addOrder"){
	    //url = "${actionName}?method=deleteSessionRow";
	    url = $("#addOrder input[name=actionName]").val()+"?method=deleteSessionRow";
	}
	$.ajax({
		type : "POST",  
		dataType: "json",//返回json格式的数据
		data : {
			type:type,
			secondID: secondId,
			productId:productId,
			expressId:expressId,
			dateString:dateString
		},
		url:url,
        success: function(data){
        	if(data != null && data.result.trim() == "yes"){
				document.getElementById("product_table").deleteRow(rowIndex);
				inputText(data);
        	}
        }
	});	
}
function inputText(data){
	$("#cheapInfo").text(data.cheapInfo);	
	$("#postageInfo").text(data.postageInfo);
	$("#totalAmtInfo").text(data.totalAmtInfo);	
	$("#totalAmount").text(data.totalAmount);
	$("#cheapAmt").text(data.cheapAmt);
	$("#postage").text(data.postage);
	$("#orderAmt").text(data.orderAmt);
}
/**
 * 下一步
 */
function next_step(){
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
	var adminDiscount = $("#adminDiscount").val();
	var totalAmount = $("#totalAmtInfo").text();
	var totalAmountPercent = (parseFloat(totalAmount)/2);
	if(adminDiscount >= totalAmountPercent){
		if(!confirm("亲，你是在给自己下单吗？优惠好多啊!")){
			return;
		}
	}
	//var dateString = '${dateString}';
	var dateString = $("#addOrder input[name=dateString]").val();
	var type = $("#orderOperationType").val();
	var previous = $("#previous").val();
	$.ajax({
		type : "POST",
		dataType: "json",//返回json格式的数据
		data : {
			userId: userId,
			type:type,
			dateString:dateString
		},
		//url:"${actionName}?method=nextStep",
		url:$("#addOrder input[name=actionName]").val()+"?method=nextStep",
        success: function(data){
        	if(previous == 0){
	        	var item1 = "";
	        	$("#default_address").empty();
	        	if(data.address != ""){
	        		item1 = "<input type='radio' name='order_address' value='"+data.addressId+"' checked='checked'>" +data.addUsername+"&nbsp;&nbsp;&nbsp;&nbsp;"+data.address+"&nbsp;&nbsp;&nbsp;&nbsp;"+data.addPhone;
	        	}else{
	        		item1 = "<input type='radio' name='order_address'  checked='checked'>添加新地址";
	        	}
	        	$("#default_address").append(item1);
	        	var item2 = "";
	        	$("#pay_method").empty();
	        	for(var i=0;i<data.payMethod.length;i++){
	        		if(data.payMethod[i].method == 0){
		            	item2 = "<div class='right_inline'>"+
									"<div class='right_input item_title'>"+
										"<input type='radio' name='payMethod' value='"+data.payMethod[i].paymentMethodId+"' checked>"+data.payMethod[i].name.trim()+
									"</div>"+
								"</div>";
	        		}else{
	        			item2 = "<div class='right_inline'>"+
									"<div class='right_input item_title'>"+
										"<input type='radio' name='payMethod' value='"+data.payMethod[i].paymentMethodId+"'>"+data.payMethod[i].name.trim()+
									"</div>"+
								"</div>";
	        		}
	        		$("#pay_method").append(item2);
	            }
	        	
	        	var item4 = "";
	        	$("#ship_time").empty();
	        	for(var h=0;h<data.shipTime.length;h++){  
	        		if(data.shipTime[h].shipTimeId == 8){
		        		item4 = "<div class='right_inline'>"+
									"<div class='right_input item_title'>"+
										"<input type='radio' name='shipTime' id='shipTime"+data.shipTime[h].shipTimeId+"' value='"+data.shipTime[h].shipTimeId+"' checked>"+data.shipTime[h].title+
									"</div>"+
								"</div>";
	        		}else{
	        			item4 = "<div class='right_inline'>"+
									"<div class='right_input item_title'>"+
										"<input type='radio' name='shipTime' id='shipTime"+data.shipTime[h].shipTimeId+"' value='"+data.shipTime[h].shipTimeId+"'>"+data.shipTime[h].title+
									"</div>"+
								"</div>";
	        		}
	        		$("#ship_time").append(item4);
	            }
	        	//发票信息
	        	document.getElementById("if_bill").checked=false;
	        	document.getElementById("billInfo").style.display="none";
	        	$("#invoiceTitle").val("");
	        	$(':radio[name="invoiceType"]').attr("checked",false);
	        	//备注
	        	$("#requireNote").val("");
        	}
        	/** 订单商品列表 */
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
        	var cheapAmt = $("#adminDiscount").val();
        	$("#cheapAmt").text(cheapAmt);
        }
	});
	document.getElementById("tep1").style.display="none";
	document.getElementById("tep2").style.display="";
}
/**
 * 订单创建成功
 */
function prev_step(){	
	$.ajax({
		type : "POST",
		dataType: "html",//返回json格式的数据
		//url:"${actionName}?method=clearSession",
		url:$("#addOrder input[name=actionName]").val()+"?method=clearSession",
        success: function(data){
        	$("#addOrder").parent().html(data);
        }
	});	
}

/**
 * 订单类型选择
 */
function OrderTypeChange(orderType){
	var id = orderType.value;
	//var dateString = '${dateString}';
	var dateString = $("#addOrder input[name=dateString]").val();
	var type = $("#orderOperationType").val();
	var url = "";
	if(null != type && type == "updateOrder"){
		//url = "${outAction}?method=setOrderTypeSession";
		url = $("#addOrder input[name=outAction]").val()+"?method=setOrderTypeSession";
	}
	if(null != type && type == "addOrder"){
	    //url = "${actionName}?method=setOrderTypeSession";
	    url = $("#addOrder input[name=actionName]").val()+"?method=setOrderTypeSession";
	}
	$.ajax({
		type : "POST",  
		dataType: "text",//返回json格式的数据
		data : {id: id,type:type,dateString:dateString},
		url:url,
        success: function(data){
        	
        }
	});	
}
/**
 * 查询用户信息
 */
function search_user(){
	var userId = $("#userId").val().trim();
	var userAccount = $("#userAccount").val().trim();
	var tel = $("#tel").val().trim();
	var pageCount = 0;
	var url = "";
	var type = $("#orderOperationType").val();
	if(null != type && type == "updateOrder"){
		//url = "${outAction}?method=searchUser";
		url = $("#addOrder input[name=outAction]").val()+"?method=searchUser";
	}
	if(null != type && type == "addOrder"){
	    //url = "${actionName}?method=searchUser";
	    url = $("#addOrder input[name=actionName]").val()+"?method=searchUser";
	}
	$.ajax({
		type : "POST",
		dataType: "json",//返回json格式的数据
		data : {
			uid: userId,
			uc : userAccount,
			tel : tel,
			pn : pageIndex_user
		},
		url:url,
        success: function(data){
        	var errorMessage = data.errorMessage;
        	if(errorMessage != null && errorMessage.trim() != ""){
        		$("#tel").val("");
        		var t = document.getElementById("user_body"); //获取展示数据的表格 
        		while (t.rows.length != 0) { 
        			t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除 
        		} 
        		$("#lblPageCount_user").text("0"); 
        		$("#lblToatl_user").text("0"); 
        		document.getElementById("lblCurent_user").innerHTML = "0"; 
        		alert(errorMessage);
        		return;
        	}
        	pageCount = data.pageInfo.pageCount;
        	$("#lblPageCount_user").text(data.pageInfo.pageCount); 
			$("#lblToatl_user").text(data.pageInfo.rowCount); 
			if (pageCount == 0) { 
				document.getElementById("lblCurent_user").innerHTML = "0"; 
			}else {
				if (pageIndex_user > pageCount) { 
					$("#lblCurent_user").text(1);
				}else { 
					$("#lblCurent_user").text(pageIndex_user); //当前页 
				} 
			} 
			document.getElementById("first_user").disabled = (pageIndex_user == 1 || $("#lblCurent_user").text() == "0") ? true : false; 
			document.getElementById("previous_user").disabled = (pageIndex_user <= 1 || $("#lblCurent_user").text() == "0") ? true : false; 
			document.getElementById("next_user").disabled = (pageIndex_user >= pageCount) ? true : false; 
			document.getElementById("last_user").disabled = (pageIndex_user == pageCount || $("#lblCurent_user").text() == "0") ? true : false;
			
			var t = document.getElementById("user_body"); //获取展示数据的表格 
			while (t.rows.length != 0) { 
				t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除 
			} 
        	var item = "";
            $.each(data.userList,function(i,result){  
            	 item += "<tr>" +
		 			 		"<td align='center' width='10%'>" +
		 			 			"<button type='button' class='btn btn-info sear_btn' onclick='choice_user("+result.id+",&quot;"+result.phone+"&quot;"+",&quot;"+result.accountName+"&quot;);' style='padding: 0 12px; height: 21px;'>选择</button>"+
            	 			"</td>" +
		 				    "<td width='10%'>"+result.id+"</td>" +
		 			        "<td width='20%'>"+result.accountName+"</td>" +	    			        
		 			        "<td width='15%'>"+result.realName+"</td>" +
		 			        "<td width='19%'>"+result.phone+"</td>" +
		 			        "<td width='28%'>"+result.email+"</td>" +
 			   			"</tr>";
             });  
   			$("#user_body").append(item);
        },
        error: function() {
        	var t = document.getElementById("user_body"); //获取展示数据的表格 
			while (t.rows.length != 0) { 
				t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除 
			} 
			$("#lblPageCount_user").text("0"); 
			$("#lblToatl_user").text("0"); 
			document.getElementById("lblCurent_user").innerHTML = "0"; 
        } 
	});	
}
/**
 * 选择用户
 */
function choice_user(id,phone,account){
	var type = $("#orderOperationType").val();
	//var dateString = '${dateString}';
	var dateString = $("#addOrder input[name=dateString]").val();
	var url = "";
	if(null != type && type == "updateOrder"){
		//url = "${outAction}?method=setUserSession";
		url = $("#addOrder input[name=outAction]").val()+"?method=setUserSession";
	}
	if(null != type && type == "addOrder"){
	    //url = "${actionName}?method=setUserSession";
	    url = $("#addOrder input[name=actionName]").val()+"?method=setUserSession";
	}
	$.ajax({
		type : "POST",  
		dataType: "text",//返回json格式的数据
		data : {
			dateString:dateString,
			id: id,
			account: account,
			phone:phone
		},
		url:url,
        success: function(data){
        	if(data != null && data.trim() == "yes"){
        		document.getElementById("order_user").value = id;
        		document.getElementById("order_account").value = account;
        		document.getElementById("orderPhone").value = phone;
        		$('#myModal1').modal('hide');
        	}
        },error:function(){
        	alert("数据加载失败");
        }
	});	
}

/**
 * 打开商品列表窗口
 */
 function openProductWin(){
	$("#prodId").val("");
	$("#prodNo").val("");
	$("#prodNm").val("");
	//$("#suits").val("");
	$("#products").val("");
	document.getElementById("plansInfo").style.display="none"; 
	search_product();
	$("#myModal2").modal(true);
 }
 /**
  * 商品查询按钮点击
  */
 function searchProductBtn(){
	pageIndex = 1;
	search_product();
 }
/**
 * 查询商品列表
 */
function search_product(){
	var prodId = $("#prodId").val().trim();
	var prodNo = $("#prodNo").val().trim();
	var prodNm = $("#prodNm").val().trim();
	var pageCount = 0;
	var type = $("#orderOperationType").val();
	var check_value = $("#products").val();//已经选择过的商品
	var url = "";
	var orderSn = $("#orderCode").text().trim();
	if(null != type && type == "updateOrder"){
		//url = "${outAction}?method=getProductList";
		url = $("#addOrder input[name=outAction]").val()+"?method=getProductList";
	}
	if(null != type && type == "addOrder"){
	    //url = "${actionName}?method=getProductList";
	    url = $("#addOrder input[name=actionName]").val()+"?method=getProductList";
	}
	$.ajax({
		type : "POST",
		dataType: "json",//返回json格式的数据
		data : {
			products:check_value,
			orderSn:orderSn,
			type:type,
			mealId: prodId,
			mainSku : prodNo,
			mealName : prodNm,
			pn : pageIndex
		},
		url:url,
        success: function(data){
        	pageCount = data.pageInfo.pageCount;
        	$("#lblPageCount").text(data.pageInfo.pageCount); 
			$("#lblToatl").text(data.pageInfo.rowCount); 
			if (pageCount == 0) {
				document.getElementById("lblCurent").innerHTML = "0"; 
			}else {
				if (pageIndex > pageCount) { 
					$("#lblCurent").text(1);
				}else { 
					$("#lblCurent").text(pageIndex); //当前页 
				}
			} 
			
        	var item = "";
   			var t = document.getElementById("product_body"); //获取展示数据的表格 
   			while (t.rows.length != 0) { 
   				t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除 style='display:none'
   			} 
   			//var value = $("#products").val();
            $.each(data.prodList,function(i,result){  
            	 item += "<tr>" +
		 			 		"<td align='center' width='5%'>";
		 			 		if(result.brandId == 1 || result.brandId == 2){
		 			 			item += "<input type='checkbox' onclick='getSuitInfo(this,"+result.mealId+","+i+");' name='checkGroup' value='"+result.mealId+"' checked /></td>";
		 			 		}else{
		 			 			item += "<input type='checkbox' onclick='getSuitInfo(this,"+result.mealId+","+i+");' name='checkGroup' value='"+result.mealId+"'/></td>";
		 			 		}
		 			 		item += "<td width='7%'>"+result.mealId+"</td>" +
		 				    "<td width='35%'>"+result.mealName+"</td>" +	
		 				    "<td><button type='button' class='btn btn-success' onclick='manySku("+result.mealId+")' style='padding: 0 12px; height: 25px;'>选择</button></td>";
		 				   if(result.brandId == 2){
		 					   item += "<td width='14%'>"+result.mainSku+"</td>" + //"+result.mainSku+"
			 			        "<td width='14%'>"+result.mealNormName+"</td>" + //"+result.mealNormName+"
			 			        "<td width='6%'>"+result.mealPrice+"</td>" + //"+result.mealPrice+"
			 			        "<td width='7%'>"+result.minStock+"</td>"; //"+result.minStock+"
		 			 		}else{
		 			 			item += "<td width='14%'></td>" + //"+result.mainSku+"
			 			        "<td width='14%'></td>" + //"+result.mealNormName+"
			 			        "<td width='6%'></td>" + //"+result.mealPrice+"
			 			        "<td width='7%'></td>"; //"+result.minStock+"
		 			 		}
		 			        switch(result.prescriptionType){
			 			        case 0:
			 			        	item += "<td width='10%'><input type='hidden' value='0' />未知</td>";
			 			        	break;
			 			        case 1:
			 			        	item += "<td width='10%'><input type='hidden' value='1' />非处方药(甲类)</td>";
			 			        	break;
			 			        case 2:
			 			        	item += "<td width='10%'><input type='hidden' value='2' />非处方药(乙类)</td>";
			 			        	break;
			 			        case 3:
			 			        	item += "<td width='10%'><input type='hidden' value='3' />处方药</td>";
			 			        	break;
			 			        case 4:
			 			        	item += "<td width='10%'><input type='hidden' value='4' />保健食品</td>";
			 			        	break;
			 			        default:
			 			        	item += "<td width='10%'><input type='hidden' value='0' />未知</td>";	
		 			        }
		            	   /*  if(null != result.prescriptionType && result.prescriptionType == 0){
					        	item += "<td width='10%'><input type='hidden' value='0' /></td>";
					        }else if(null != result.prescriptionType && result.prescriptionType == 1){
		 			        	item += "<td width='10%'><input type='hidden' value='1' />非处方药(甲类)</td>";
		 			        }else if(null != result.prescriptionType && result.prescriptionType == 2){
		 			        	item += "<td width='10%'><input type='hidden' value='2' />非处方药(乙类)</td>";
		 			        }else if(null != result.prescriptionType && result.prescriptionType == 3){
		 			        	item += "<td width='10%'><input type='hidden' value='3' />处方药</td>";
		 			        }else if(null != result.prescriptionType && result.prescriptionType == 4){
		 			        	item += "<td width='10%'><input type='hidden' value='4' />保健食品</td>";
		 			        }else{
		 			        	item += "<td width='10%'><input type='hidden' value='0' /></td>";
		 			        } */
		 			       if(result.brandId == 2){
		 			        	item += "<td style='display:none;'>"+result.mainProductId+"</td></tr>"; //"+result.mainProductId+"
		 			       }else{
			 			        item += "<td style='display:none;'></td></tr>"; //"+result.mainProductId+"
		 			    	   
		 			       }
             });
   			$("#product_body").append(item);
        },
        error: function() {
        	var t = document.getElementById("product_body"); //获取展示数据的表格 
   			while (t.rows.length != 0) { 
   				t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除 
   			} 
			$("#lblPageCount").text("0"); 
			$("#lblToatl").text("0"); 
			document.getElementById("lblCurent").innerHTML = "0"; 
        	alert("加载数据失败"); 
        } 
	});	
	document.getElementById("plansInfo").style.display="none";
}
/**
 * 多sku一览
 */
function manySku(mealId){
	var url = "";
	var type = $("#orderOperationType").val();
	if(null != type && type == "updateOrder"){
		//url = "${outAction}?method=getSkuList";
		url = $("#addOrder input[name=outAction]").val()+"?method=getSkuList";
	}
	if(null != type && type == "addOrder"){
	    //url = "${actionName}?method=getSkuList";
	    url = $("#addOrder input[name=actionName]").val()+"?method=getSkuList";
	}
	$.ajax({
		type : "POST",
		dataType: "json",//返回json格式的数据
		data : {
			mealId:mealId
		},
		url:url,
        success: function(data){
        	if(data.skuList != null && data.skuList.length == 1){
        		var t = document.getElementById("product_body"); //获取展示数据的表格 
        	   	for(var i=0;i<t.rows.length;i++){
        	   		if(t.rows[i].cells[1].innerHTML == mealId){
        	   			$.each(data.skuList,function(j,result){ 
	        	   			t.rows[i].cells[4].innerHTML=result.sku;
	        	   			t.rows[i].cells[5].innerHTML=result.specName;
	        	   			t.rows[i].cells[6].innerHTML=result.price;
	        	   			t.rows[i].cells[7].innerHTML=result.stock;
	        	   			t.rows[i].cells[9].innerHTML=result.skuId;
	        	   			spliceProducts(mealId,result.skuId,false);
	        	   		});
	       	   			break;
	        	   	}
        	   	}
        	}else{
        		var item = "";
       			var t = document.getElementById("sku_body"); //获取展示数据的表格 
       			while (t.rows.length != 0) { 
       				t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除 style='display:none'
       			} 
                $.each(data.skuList,function(i,result){  
                	 item += "<tr>" +
    		 			 		"<td align='center' width='5%'>" +
    		 			 			"<input type='radio' name='radioGroup' id='sku"+result.mealId+"'  value='"+result.mealId+"'/>"+
                	 			"</td>" +
    		 				    "<td>"+result.mealId+"</td>" +
    		 				    "<td>"+result.sku+"</td>" +
    		 			        "<td>"+result.specName+"</td>" +
    		 			        "<td>"+result.price+"</td>" +
    		 			        "<td>"+result.stock+"</td>" +
    		 				    "<td>"+result.skuId+"</td>"; 
                 });
       			$("#sku_body").append(item);
				$('#skuModel').modal('show');
        	}
        },error: function() {
        	var t = document.getElementById("sku_body"); //获取展示数据的表格 
   			while (t.rows.length != 0) { 
   				t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除 
   			} 
        	alert("加载数据失败"); 
        } 
	});
}
/**
 * 多sku选择
 */
function choice_sku(){
	var elements = $("input[name=radioGroup]:checked");
	if(elements.length <= 0){
		alert("请选择！");
		return;
	}
	var tr = elements[0].parentNode.parentNode;
    var tds = tr.cells;
    var mealId = tds[1].innerHTML;
    var t = document.getElementById("product_body"); //获取展示数据的表格 
   	for(var i=0;i<t.rows.length;i++){
   		if(t.rows[i].cells[1].innerHTML == mealId){
   			var skuId_old = t.rows[i].cells[9].innerHTML;
   			t.rows[i].cells[4].innerHTML=tds[2].innerHTML;
   			t.rows[i].cells[5].innerHTML=tds[3].innerHTML;
   			t.rows[i].cells[6].innerHTML=tds[4].innerHTML;
   			t.rows[i].cells[7].innerHTML=tds[5].innerHTML;
   			t.rows[i].cells[9].innerHTML=tds[6].innerHTML;
   			//spliceProducts(mealId,tds[6].innerHTML,false);
   			var value = $("#products").val();
   			if(value != null && value.trim() != ""){
   				var arr = value.split("|");
   				var mark = false;
   				for(var j=0;j<arr.length;j++){
   					if(arr[j] == mealId+"," + skuId_old){
   						value = value.replace(arr[j] + "|","");
   						mark = true;
   						break;
   					}
   				}
   			}
   			if(mark){
   				value += mealId + "," + tds[6].innerHTML + "|";
   			}
   			$("#products").val(value);
   			break;
   		}
   	}
   	document.getElementById("plansInfo").style.display="none"; 
	$('#skuModel').modal('hide');
}
/**
 * 拼接所选商品字符串
 */
function spliceProducts(mealId,skuId,flag){
	var value = $("#products").val();
	if(value != null && value.trim() != ""){
		var arr = value.split("|");
		var mark = false;
		for(var j=0;j<arr.length;j++){
			if(arr[j] == mealId+"," + skuId){
				value = value.replace(arr[j] + "|","");
				mark = true;
				break;
			}else if(arr[j] == mealId+","){
				value = value.replace(arr[j] + "|","");
				mark = true;
				break;
			}
		}
	}
	if(flag || mark){
		value += mealId + "," + skuId + "|";
	}
	$("#products").val(value);
}
/**
 * 删减所选商品字符串
 */
 function minusProducts(mealId,skuId){
	var value = $("#products").val();
	var arr = value.split("|");
	for(var i=0;i<arr.length;i++){
		if(arr[i] == mealId+"," + skuId){
			value = value.replace(arr[i] + "|","");
			break;
		}else if(arr[i] == mealId+","){
			value = value.replace(arr[i] + "|","");
			break;
		}
	}
	$("#products").val(value);
 }
/**
 * 父子套餐（套餐选择事件）
 */
function getSuitInfo(obj,mealId,index){
	var t = document.getElementById("product_body"); //获取展示数据的表格 
	var skuId = t.rows[index].cells[9].innerHTML;
	if(obj.checked==true){
		/* if(value != null && value.trim() != ""){
			var arr = value.split(",");
			for(var j=0;j<arr.length;j++){
				if(arr[j] == mealId+"|" + skuId){
					value = value.replace(arr[j] + ",","");
				}else if(arr[j] == mealId+"|"){
					value = value.replace(arr[j] + ",","");
				}
			}
		}
		value += mealId + "|" + skuId + ",";
		$("#products").val(value); */
		spliceProducts(mealId, skuId,true);
		var prods = $("#products").val();
		var url = "";
		var type = $("#orderOperationType").val();
		if(null != type && type == "updateOrder"){
			//url = "${outAction}?method=getSuitList";
			url = $("#addOrder input[name=outAction]").val()+"?method=getSuitList";
		}
		if(null != type && type == "addOrder"){
		    //url = "${actionName}?method=getSuitList";
		    url = $("#addOrder input[name=actionName]").val()+"?method=getSuitList";
		}
		$.ajax({
			type : "POST",
			dataType: "json",//返回json格式的数据
			data : {
				mealId:mealId,
				products:prods
			},
			url:url,
	        success: function(data){
	        	if(data.relateMealList != null && data.relateMealList.length > 0){
	        		document.getElementById("plansInfo").style.display=""; 
	        	}else{
	        		document.getElementById("plansInfo").style.display="none"; 
	        		return;
	        	}
	        	var item = "";
	   			var t = document.getElementById("product_plans_body"); //获取展示数据的表格 
	   			while (t.rows.length != 0) { 
	   				t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除 style='display:none'
	   			} 
	   			var skuId_old = null;
	   			if(skuId != ""){
	   				skuId_old = skuId;
	   			}
		   		$.each(data.relateMealList,function(i,result){  
	            	 item += "<tr>" +
			 			 		"<td align='center' width='5%'>" +
			 			 			"<input type='checkbox' onclick='checkSelect(this,"+mealId+","+skuId_old+","+result.mealId+","+i+");' name='checkGroup' value='"+result.mealId+"'/>"+
	            	 			"</td>" +
			 				    "<td width='7%'>"+result.mealId+"</td>" +
			 				    "<td width='35%'>"+result.mealName+"</td>" +	
			 				    "<td style='display:none;'></td>" + 
			 			        "<td width='14%'>"+result.mainSku+"</td>" +
			 			        "<td width='14%'>"+result.mealNormName+"</td>" +
			 			        "<td width='6%'>"+result.mealPrice+"</td>" +
			 			        "<td width='7%'>"+result.minStock+"</td>";
				            	switch(result.prescriptionType){
				 			        case 0:
				 			        	item += "<td width='10%'><input type='hidden' value='0' />未知</td>";
				 			        	break;
				 			        case 1:
				 			        	item += "<td width='10%'><input type='hidden' value='1' />非处方药(甲类)</td>";
				 			        	break;
				 			        case 2:
				 			        	item += "<td width='10%'><input type='hidden' value='2' />非处方药(乙类)</td>";
				 			        	break;
				 			        case 3:
				 			        	item += "<td width='10%'><input type='hidden' value='3' />处方药</td>";
				 			        	break;
				 			        case 4:
				 			        	item += "<td width='10%'><input type='hidden' value='4' />保健食品</td>";
				 			        	break;
				 			        default:
				 			        	item += "<td width='10%'><input type='hidden' value='0' />未知</td>";	
						        }
			 			        item += "<td style='display:none;'>"+result.mainProductId+"</td></tr>";
	             });
	   			$("#product_plans_body").append(item);
	        },error: function() {
	        	var t = document.getElementById("product_plans_body"); //获取展示数据的表格 
	   			while (t.rows.length != 0) { 
	   				t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除 
	   			} 
	   			document.getElementById("plansInfo").style.display="none"; 
	        	alert("加载数据失败"); 
	        } 
		});
	}else{
		/* var arr = value.split(",");
		for(var i=0;i<arr.length;i++){
			if(arr[i] == mealId+"|" + skuId){
				value = value.replace(arr[i] + ",","");
				break;
			}else if(arr[i] == mealId+"|"){
				value = value.replace(arr[i] + ",","");
			}
		} 
		$("#products").val(value);*/
		minusProducts(mealId,skuId);
		var t = document.getElementById("product_plans_body"); //获取展示数据的表格 
		while (t.rows.length != 0) { 
			t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除 
		} 
		document.getElementById("plansInfo").style.display="none"; 
	}
}
/**
 * 父子套餐选择事件
 */
function checkSelect(obj,mealId_old,skuId_old,mealId,index){
	//var value = $("#products").val();
	var t = document.getElementById("product_plans_body"); //获取展示数据的表格 
	var skuId = t.rows[index].cells[9].innerHTML;
	if(obj.checked==true){
		spliceProducts(mealId, skuId,true);
		/* var flag = false;
		var arr = value.split(",");
		for(var i=0;i<arr.length;i++){
			if(arr[i] == mealId){
				flag = true;
				break;
			}
		}
		if(!flag){
			value += mealId + ",";
		} */
		var t = document.getElementById("product_body"); //获取展示数据的表格 
	   	for(var i=0;i<t.rows.length;i++){
	   		if(t.rows[i].cells[1].innerHTML == mealId_old){
	   			var products = $("#products").val();
	   			var prodArr = products.split("|");
	   			for(var j=0;j<prodArr.length;j++){
	   				var mid = prodArr[j].split(",")[0];
	   				var sid = prodArr[j].split(",")[1];
	   				if(mid == mealId_old && (sid == skuId_old || skuId_old == null)){
	   					products = products.replace(prodArr[j] + "|","");
	   					$("#products").val(products);
	   				}
	   			}
	   			t.rows[i].cells[0].innerHTML = "<input type='checkbox' onclick='getSuitInfo(this,"+mealId_old+","+i+");' name='checkGroup' value='"+mealId_old+"'/>";
	   		}
	   	}
	}else{
		/* var arr = value.split(",");
		for(var i=0;i<arr.length;i++){
			if(arr[i] == mealId){
				value = value.replace(arr[i] + ",","");
				break;
			}
		} */
		minusProducts(mealId,skuId);
	}
	//$("#suits").val(value);
}
/**
 * 选择商品
 */
function choice_prod(){
	//var suits = $("#suits").val();
	var products = $("#products").val();
	if(products == null || products.trim() == ""){
		alert("请选择商品!");
		return;
	}
	if(products != null && products.trim() != ""){
		var prodArr = products.split("|");
		var arr = null;
		for(var j=0;j<prodArr.length;j++){
			if(prodArr[j] != null && prodArr[j] != ""){
				arr = prodArr[j].split(",");
				if(arr != null && (arr.length != 2 || arr[1] == "")){
					alert("所选商品中存在未选sku的商品，请选择sku后继续！");
					return;
				}
			}
		}
	}
	var url = "";
	var dataType = "";
	var type = $("#orderOperationType").val();
	if(null != type && type == "updateOrder"){
		//url = "${outAction}?method=updateOrderSku";
		url = $("#addOrder input[name=outAction]").val()+"?method=updateOrderSku";
	}
	if(null != type && type == "addOrder"){
	    //url = "${actionName}?method=updateOrderSku";
	    url = $("#addOrder input[name=actionName]").val()+"?method=updateOrderSku";
	}
    dataType = "json";
	//var dateString = '${dateString}';
	var dateString = $("#addOrder input[name=dateString]").val();
	var expressCode = $('input[name="expressInfo"]:checked').val();
	$.ajax({
		type : "POST",
		dataType: dataType,//返回json格式的数据
		data : {
			expressCode:expressCode,
			products:products,
			tel : $("#mobile").val(),
			dateString : dateString,
			type:type
		},
		url:url,
        success: function(data){
       		 $.each(data.rsList,function(i,result){
       			 var item = "<tr>" +
	 				"<td align='center'>" +
			 			"<img class='img-thumbnail' src='images/delete.png'" +
					     "onclick='removeRow(this);'" +
						 "title='删除' alt='删除' style='width: 25px; height: 25px; cursor: pointer' />"+
   	 				"</td>" +
				    "<td>"+result.mealId+"</td>" +
			        "<td>"+result.mealName+"</td>" +
			        "<td>"+result.mainSku+"</td>" +	    			        
			        "<td>"+result.mealNormName+"</td>" +
					"<td>"+result.stockCount+"</td>"+
			        "<td>" +
	 			       	"<div class='tc-amount'>"+
						    "<span class='minus' onclick='minus(this,"+'"'+result.mealNormName+'"'+");'>-</span>"+
						    "<input class='tc-text amount' type='text' value='1' onkeyup='text_change(this,"+'"'+result.mealNormName+'"'+");'/>"+
						    "<span class='plus' onclick='plus(this,"+'"'+result.mealNormName+'"'+");'>+</span>"+
						"</div>"+
					"</td>"+
					"<td>"+result.price+"</td>"+
					"<td>"+result.price+"</td>"+
					"<td style='display:none;'>"+result.productId+"</td></tr>";
				$("#tb_body").append(item);
       		 });
       		inputText(data.orderInfoObj);
        	$('#myModal2').modal('hide');
        },error: function() {
        	alert("加载数据失败"); 
        } 
	});
}
/**
 * 新增商品完成后计算优惠政策和邮费
 */
function computeAmt(){
	var expressCode = $('input[name="expressInfo"]:checked').val();
	//var dateString = '${dateString}';
	var dateString = $("#addOrder input[name=dateString]").val();
	var type = $("#orderOperationType").val();
	var url = "";
	if(null != type && type == "updateOrder"){
		//url = "${outAction}?method=computeAmt";
		url = $("#addOrder input[name=outAction]").val()+"?method=computeAmt";
	}
	if(null != type && type == "addOrder"){
	    //url = "${actionName}?method=computeAmt";
	    url = $("#addOrder input[name=actionName]").val()+"?method=computeAmt";
	}
	$.ajax({
		type : "POST",
		dataType: "json",//返回json格式的数据
		data:{expressCode:expressCode,type:type,dateString:dateString},
		url:url,
        success: function(data){
        	if(data != null){
				inputText(data);
        	}
        }
	});	
}
/**
 * 修改地址
 */
var address_flag = false;
function change_address(){
	address_flag = true;
	document.getElementById("address_step1").style.display="none";
	document.getElementById("address_step2").style.display="";
	document.getElementById("address_step4").style.display="none";
	var userId = $("#order_user").val().trim();
	$("#address_step3").empty();
	var type = $("#orderOperationType").val();
	var url = "";
	if(null != type && type == "updateOrder"){
		//url = "${outAction}?method=updateAddress";
		url = $("#addOrder input[name=outAction]").val()+"?method=updateAddress";
	}
	if(null != type && type == "addOrder"){
	    //url = "${actionName}?method=updateAddress";
	    url = $("#addOrder input[name=actionName]").val()+"?method=updateAddress";
	}
	$.ajax({
		type : "POST",  
		dataType: "json",//返回json格式的数据
		data : {
			userId: userId
		},
		url:url,
        success: function(data){
        	var item = "";
            $.each(data,function(i,result){
            	 item += "<div class='right_inline'>"+
			 				"<div class='right_input item_title'>"+
								"<input type='radio' id='"+result.id+"' value='"+result.id+"' name='address' onclick='showWin(this);'><label for='"+result.id+"' onclick='showWin(this);'>"+result.contacts+"&nbsp;&nbsp;&nbsp;&nbsp;"+result.fullAddress+"&nbsp;&nbsp;&nbsp;&nbsp;"+result.contactNumber+"</label>"+
							"</div>"+
						"</div>";
             });
            item += "<div class='right_inline'>"+
							"<div class='right_input item_title'>"+
							"<input type='radio' id='newAddress' value='newAddress' name='address' onclick='showWin(this);'><label for='newAddress' onclick='showWin(document.getElememtById('newAddress'));'>使用新地址</label>"+
						"</div>"+
					"</div>"; 
					
   			$("#address_step3").append(item);
        }
	});	
}
function closeAddressWin(){
	document.getElementById("address_step1").style.display="";
	document.getElementById("address_step2").style.display="none";
	address_flag = false;
}
/**
 * 打开新增地址窗口
 */
function showWin(obj){
	saveOrUpdate = 1;
	if(obj.value == "newAddress"){
		document.getElementById("address_step4").style.display="";
		setProvince();
    	setCity("");
    	setDistrict("");
    	$("#streetId").val("");
		$("#phoneNo").val("");
		$("#zipCode").val("");
		$("#consignee").val("");
		jQuery('#form1').data("bValidators").formInstance.reset();
	}else{
		document.getElementById("address_step4").style.display="none";
	}
}
/**
 * 判断保存或者修改用户地址
 */
var saveOrUpdate = 0;
function saveOrUpdateAddress(){
	address_flag = false;
	var address_val = $("input[name='address']:checked").val();
	if(address_val == null || address_val== ""){
		alert("请选择！");
		return;
	}
	var result=jQuery('#form1').data("bValidators").formInstance.validate();
	if(result){
		if(saveOrUpdate == 1){
			saveAddress(address_val);
		}else if(saveOrUpdate == 2){
			editAddress(address_val);
		}
	}
}
/**
 * 保存收货地址
 */
function saveAddress(address_val){
	var address_text = $("input[name='address']:checked + label").text();
	$("#default_address").empty();
	var item = "";
	if(address_val != null && address_val.trim() =="newAddress"){
		var userId = $("#order_user").val().trim();
		var province = $("#provinceId option:selected").text().trim();
		var city = $("#cityId option:selected").text().trim();
		var district = $("#districtId option:selected").text().trim();
		var street = $("#streetId").val().trim();
		var aid = $("#districtId").val().trim();
		var contactNumber = $("#phoneNo").val().trim();
		var postcode = $("#zipCode").val().trim();
		var contacts = $("#consignee").val().trim();
		var type = $("#orderOperationType").val();
		var url = "";
		if(null != type && type == "updateOrder"){
			//url = "${outAction}?method=saveAddress";
			url = $("#addOrder input[name=outAction]").val()+"?method=saveAddress";
		}
		if(null != type && type == "addOrder"){
		    //url = "${actionName}?method=saveAddress";
		    url = $("#addOrder input[name=actionName]").val()+"?method=saveAddress";
		}
		$.ajax({
			type : "POST",
			dataType: "json",
			data : {
				userId : userId,
				province : province,
				city : city,
				district : district,
				street : street,
				aid : aid,
				contactNumber : contactNumber,
				postcode : postcode,
				contacts : contacts
			},
			url:url,
	        success: function(data){
	        	item = "<input type='radio' checked='checked' name='order_address' value='"+data.id+"'>" + data.contacts+"&nbsp;&nbsp;&nbsp;&nbsp;"+data.fullAddress+"&nbsp;&nbsp;&nbsp;&nbsp;"+data.contactNumber;
	        	$("#default_address").append(item);
	        	document.getElementById("address_step1").style.display="";
	        	document.getElementById("address_step2").style.display="none"; 
	        }
		});	
	}else{
		item = "<input type='radio' checked='checked' name='order_address' value='"+address_val+"'>" + address_text;
    	$("#default_address").append(item);
    	document.getElementById("address_step1").style.display="";
    	document.getElementById("address_step2").style.display="none"; 
	}
}
/**
 * 编辑收货地址
 */
function getAddressInfo(){
	var address_val = $("input[name='address']:checked").val();
	if(address_val == null || address_val== ""){
		alert("请选择！");
		return;
	}
	if(address_val.trim() =="newAddress"){
		alert("请选择正确的地址！");
		return;
	}
	var userId = $("#order_user").val().trim();
	var type = $("#orderOperationType").val();
	var url = "";
	if(null != type && type == "updateOrder"){
		//url = "${outAction}?method=getAddressInfo";
		url = $("#addOrder input[name=outAction]").val()+"?method=getAddressInfo";
	}
	if(null != type && type == "addOrder"){
	    //url = "${actionName}?method=getAddressInfo";
	    url = $("#addOrder input[name=actionName]").val()+"?method=getAddressInfo";
	}
	$.ajax({
		type : "POST",  
		dataType: "json",
		data : {
			address_val : address_val,
			userId : userId
		},
		url:url,
        success: function(data){
        	saveOrUpdate = 2;
        	document.getElementById("address_step4").style.display="";
        	$("#provinceId  option[value='"+data.provinceId+"'] ").attr("selected",true);
        	setCity(data.cityId);
        	setDistrict(data.districtId);
        	$("#streetId").val(data.street);
    		$("#phoneNo").val(data.contactNumber);
    		$("#zipCode").val(data.postcode);
    		$("#consignee").val(data.contacts);
    		jQuery('#form1').data("bValidators").formInstance.validate();
        }
	});	
}
/**
 * 编辑后保存收货地址
 */
function editAddress(address_val){
	$("#default_address").empty();
	var item = "";
	var userId = $("#order_user").val().trim();
	var province = $("#provinceId option:selected").text().trim();
	var city = $("#cityId option:selected").text().trim();
	var district = $("#districtId option:selected").text().trim();
	var street = $("#streetId").val().trim();
	var aid = $("#districtId").val().trim();
	var contactNumber = $("#phoneNo").val().trim();
	var postcode = $("#zipCode").val().trim();
	var contacts = $("#consignee").val().trim();
	var type = $("#orderOperationType").val();
	var url = "";
	if(null != type && type == "updateOrder"){
		//url = "${outAction}?method=editAddress";
		url = $("#addOrder input[name=outAction]").val()+"?method=editAddress";
	}
	if(null != type && type == "addOrder"){
	    //url = "${actionName}?method=editAddress";
	    url = $("#addOrder input[name=actionName]").val()+"?method=editAddress";
	}
	$.ajax({
		type : "POST",  
		dataType: "json",
		data : {
			usaid : address_val,
			userId : userId,
			province : province,
			city : city,
			district : district,
			street : street,
			aid : aid,
			contactNumber : contactNumber,
			postcode : postcode,
			contacts : contacts
		},
		url:url,
        success: function(data){
        	item = "<input type='radio' checked='checked' name='order_address' value='"+data.id+"'>" +data.contacts+"&nbsp;&nbsp;&nbsp;&nbsp;"+data.fullAddress+"&nbsp;&nbsp;&nbsp;&nbsp;"+data.contactNumber;
        	$("#default_address").append(item);
        	document.getElementById("address_step1").style.display="";
        	document.getElementById("address_step2").style.display="none"; 
        }
	});	
}
/**
 * 提交订单
 */
function order_submit(){
	if(address_flag){
		alert("请选择收货地址！");
		return;
	}
	if(confirm('你确定要下单吗？')){
		$("#orderSubmit").attr("disabled", "disabled");
		//订单类型
		var orderType = $("#orderType").val();
		//用户id
		var buyerUserId = $("#order_user").val();
		//用户帐号
		var buyerLoginName = $("#order_account").val();
		//用户手机号
		var buyerUserPhone = $("#orderPhone").val();
		//下单客服帐号
		var createAdminUser = $("#adminUser").val();
		//下单客服帐号
		var userAddressId = $('input[name="order_address"]:checked').val();
		//支付方式id
		var paymentMethodId = $('input[name="payMethod"]:checked').val();
		//送货方式id
		var expressId = $('input[name="expressInfo"]:checked').val();
		//送货时间id
		var shipTimeId = $('input[name="shipTime"]:checked').val();
		//备注
		var remark = $("#requireNote").val();
		//随机数
		//var dateString = '${dateString}';
		var dateString = $("#addOrder input[name=dateString]").val();
		//订单来源标识
		var cpsLaiyuan = $("#cpsLaiyuan").val();
		var orderSource = $("#cpsLaiyuan option:checked").text();
		//优惠金额
		var adminDiscount = $("#adminDiscount").val();
		//订单总额
		var orderAmount = $("#orderAmt").text();
		//二维码ID
		var twoDimensionCodeId = $("#twoDimensionCodeId").val();
		//医生id
		var doctorId = $("#doctorName").val();
		//发票类型
		var invoiceType = 0;
		//发票抬头
		var invoiceTitle = null;		
		if($('#if_bill').is(':checked')){
			invoiceType = $('input[name="invoiceType"]:checked').val();
			if(invoiceType == 2){
				if(null != $("#invoiceTitle").val() && $("#invoiceTitle").val() != ""){
					invoiceTitle = $("#invoiceTitle").val();
				} else {
					$("#invoiceTitle:focus");
					alert("请输入发票抬头");
					return;
				}
			}
			if(invoiceType == 22){
				if(null != $("#invoiceTitle2").val() && $("#invoiceTitle2").val() != ""){
					invoiceTitle = $("#invoiceTitle2").val();
					
				} else {
					$("#invoiceTitle2:focus");
					alert("请输入发票抬头");
					return;
				}
			}
		} else {
			invoiceType = 0;
		}
		//当前沟通记录ID
		var commuId = $("#curCommuId").val();
		$.ajax({
			type : "POST",  
			dataType: "json",//返回json格式的数据
			data : {
				orderType: orderType,
				buyerUserId: buyerUserId,
				buyerLoginName: buyerLoginName,
				createAdminUser: createAdminUser,
				userAddressId: userAddressId,
				paymentMethodId: paymentMethodId,
				shipTimeId: shipTimeId,
				invoiceType: invoiceType,
				invoiceTitle:invoiceTitle,
				expressId:expressId,
				remark:remark,
				cpsLaiyuan:cpsLaiyuan,
				orderSource:orderSource,
				adminDiscount:adminDiscount,
				orderAmount:orderAmount,
				buyerUserPhone:buyerUserPhone,
				dateString:dateString,
				twoDimensionCodeId:twoDimensionCodeId,
				doctorId:doctorId,
				commuId:commuId
			},
			//url:"${actionName}?method=orderSubmit",
			url:$("#addOrder input[name=actionName]").val()+"?method=orderSubmit",
	        success: function(data){
	        	if(data.orderCode == 1) {
	        		prev_step();
	        		alert("订单编号："+data.Message);
	        	}
	        	if(data.orderCode == 500) {
	        		$("#orderSubmit").attr("disabled", false);
	        		alert("下单失败,错误："+data.Message);
	        	}
	        	if(data.orderCode == 501) {
	        		$("#orderSubmit").attr("disabled", false);
	        		alert("下单失败,错误："+data.Message);
	        	}
	        },
	        error:function(){
	        	$("#orderSubmit").attr("disabled", false);
	        }
		});	
	}else{
		return false;
	}
}

/** 选择快递信息改变邮费 */
function getExpress(){
	computeAmt();
}
function boxClick(){
	if($('#if_bill').is(':checked')){
		document.getElementById("billInfo").style.display="";
		$(':radio[name="invoiceType"]').eq(0).attr("checked",true); 
	}else{
		document.getElementById("billInfo").style.display="none"; 
	}
}
/** 选择发票类型 */
function getInvoiceTyep(type){
	if(type == 1){
		document.getElementById("invoiceTitle").style.display="none";
		document.getElementById("invoiceTitle2").style.display="none";
	} else  if(type == 2) {
		document.getElementById("invoiceTitle").style.display="";
	}else if(type == 3){
		document.getElementById("invoiceTitle2").style.display="";
	}
}

/** 手动关闭弹框 */
function modalClose(modalId){
	$("#"+modalId).modal('hide');
}

/** 下一步 */
function order_next(){
	var type = $("#orderOperationType").val();
	//修改订单
	if(null != type && type == "updateOrder"){
		updateOrder_next();
	}
	//新增订单
	if(null != type && type == "addOrder"){
		next_step();
	}
}


/**
 * 上一步
 */
function previous(){
	document.getElementById("tep1").style.display="";
	document.getElementById("tep2").style.display="none";
	$("#previous").val("1");
}

/** 提交订单 */
function orderSubmit(){
	var type = $("#orderOperationType").val();
	//修改订单
	if(null != type && type == "updateOrder"){
		updateOrderSubmit();
	}
	//新增订单
	if(null != type && type == "addOrder"){
		order_submit();
	}
}

/** 检验优惠金额*/
function inputChecking(){
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
    var val = $("#adminDiscount").val();
    if(val != null && val != ''){
    	 if(!/\d/.test(val)){
   	    	alert("优惠金额只能输入数字");
   	    	return;
   	    }
   	    if(!/^\d+\.?\d{0,2}$/.test(val)){
   	    	alert("请重新输入优惠金额，小数点只能输入两位数");
   	    	return;
   	    }
    } else {
    	$("#adminDiscount:focus");
    }   
}
/**
 * 按官网模式下单
 */
function add_office_order(){
	//var dateString = '${dateString}';
	var dateString = $("#addOrder input[name=dateString]").val();
	//用户id
	var buyerUserId = $("#order_user").val();
	//下单客服帐号
	var createAdminUser = $("#adminUser").val();
	if(buyerUserId == null || buyerUserId.trim() == "" ){
		alert("请选择下单用户！");
		return;
	}
	if(createAdminUser == null || createAdminUser.trim() == "" ){
		alert("下到哪客服ID不能为空！");
		return;
	}
	$.ajax({
		type : "POST",  
		dataType: "json",
		data : {
			dateString:dateString,
			buyerUserId:buyerUserId,
			createAdminUser:createAdminUser
		},
		url:"outScreen.do?method=addOfficOrder",
        success: function(data){
        	if(data.msg != null && data.msg != ""){
        		alert(data.msg);
        	}else{
        		if(data.goodsMsg != null && data.goodsMsg != ""){
        			alert(data.goodsMsg);
        		}
        		window.open(data.url);
        	};
        },
        error:function(){
        	alert("进入官网下单模式失败，请联系管理员！");
        }
	});	
}