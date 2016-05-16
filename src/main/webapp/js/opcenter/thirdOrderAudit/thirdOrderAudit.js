/**
 *	@author				longshanw
 *	@date				2016-01-13
 *	@description		三方平台订单审核脚本处理文件
 */


//页面元素加载完毕后执行
$(document).ready(function() {
	
	$("#orderDetailDiv").hide();
	$("#doctorFieldSet").hide();
	
	//查询
	$('#queryBtn').click(function() {
		gotoPage(1);
	});
	
	$("#kfInvoiceStatusDetailPage").change(
		function(){
			invoiceStatusFunc(this);
		}
	);
	
	$("#detailPageTbDiv").mCustomScrollbar({
		setWidth:900,
		setHeight:120,
		axis:"yx",
		scrollButtons:{enable:true},
		//theme:"3d",
		//scrollbarPosition:"outside"
		theme:"minimal-dark"
     });
	
	//为th增加点击事件
	thClick();
	
	//为td增加触发事件 排除oprStatusTd样式列的表格
	tdClick();
	
	
	//批量审核通过
	$('#batchAuditBtn').click(function() {
		batchAuditPassFunc();
	});
	
	//隐藏手机号中间四位
	//phoneHideFunc();
});


/**
 * 隐藏手机号中间四位
 */
function phoneHideFunc(){
	$(".phoneCls").each(function(i){
		var phone = $(this).text();
		if(phone!=""&&phone!=null&&phone.length>4){
			$(this).text(phone.substring(0,3)+"****"+phone.substring(8,11));
		}
	});
}


/**
 * 为th增加点击事件,全选/取消
 */
function thClick(){
	//点击复选框，全选/取消
	$('#selAll').click(function(){
		selectAll();
	});
	//点击th排序
	thSortClick();
}

//全选
function selectAll(){
	var check = $("#selAll")[0].checked;
	var items = $("[name='selItem']:checkbox");
	for(var i=0;i<items.length;i++){
		items[i].checked=check;
	}
	$("#orderDetailDiv").hide();
}

//获取选中项
function onSel(){
	var selId = "";
	var items = document.getElementsByName("selItem");
	for(var i=0;i<items.length;i++){
		if(items[i].checked){
			if(selId!="") {
				selId+=",";
			}
			selId += items[i].value;
		}
	}
	return selId;
}


/**
 * 为th增加点击事件,全选/取消
 */
function thSortClick(){
	var urlArr = $(":input[name='contextPath']")[0].value.split("//");
	var contextPath = urlArr[0]+"//"+urlArr[1].split("/")[0]+"/"+urlArr[1].split("/")[1];
	var sortThArr = $(".sortTh");
	
	var sort = $("#sort").val();
	var orderBy = $("#orderBy").val();
	if(orderBy.length>0&&sort.length>0){
		if(sort == "asc"){
			$("#"+orderBy).html("<div nowrap>"+$("#"+orderBy).html()+"<img style='width: 20px;height: 18px;margin-left:8px;' src='"+(contextPath+"/images/asc.png")+"'/>"+"</div>");
		}
		if(sort == "desc"){
			$("#"+orderBy).html("<div nowrap>"+$("#"+orderBy).html()+"<img style='width: 20px;height: 18px;margin-left:8px;' src='"+(contextPath+"/images/desc.png")+"'/>"+"</div>");
		}
	}
	
	//为每项th增加点击触发事件
	for(var i=0;i<sortThArr.length;i++){
		sortThArr[i].onclick = function(event){
			var sort = $("#sort").val();
			var field = $(this).attr("id");
			if(sort=="" || sort == 'desc'){
				sort = "asc";
			}else{
				sort = "desc";
			}
			$("#orderBy").val(field);
			$("#sort").val(sort);
			gotoPage($("#curPage").val());
//			console.log(sort+"--"+field);
		};
	}
}

/**
 * 为td增加触发事件 排除oprStatusTd样式列的表格
 */
function tdClick(){
	
	var TdArr = $(".orderInfoTr td:not('.oprBtn')");
	for(var i=0;i<TdArr.length;i++){
		TdArr[i].onclick = function(event){
			if(event.currentTarget.parentElement.cells.length>0){
//				console.log("orderNumber："+event.currentTarget.parentElement.cells[1].innerHTML);
				//控制点击表格时，复选框选中/取消
				var checkBoxValue = event.currentTarget.parentElement.cells[0].childNodes[1].checked;
				if(event.target.nodeName == 'TD'){
					if(checkBoxValue){
						checkBoxValue = false;
					}else{
						checkBoxValue = true;
					}
				}
				event.currentTarget.parentElement.cells[0].childNodes[1].checked = checkBoxValue;
				var orderNumber = event.currentTarget.parentElement.cells[1].innerHTML; 
				var orderFlag = event.currentTarget.parentElement.cells[2].innerHTML; 
				getMainOrderDetails(orderNumber,orderFlag);
				
			}
		};
	}
}

/**
 * 客服审核-更改发票信息
 */
function invoiceStatusFunc(obj){
	$("#kfInvoiceTitleDetailPage").val("");
	if(obj.value == "0"){
		$("#kfInvoiceTypeLabel").hide();
		$("#kfInvoiceTitleLabel").hide();
		$("#kfInvoiceContentLabel").hide();
	}else{
		$("#kfInvoiceTypeLabel").show();
		$("#kfInvoiceTitleLabel").show();
		$("#kfInvoiceContentLabel").show();
	}
	$("#kfInvoiceTitleDetailPage").focus();
}



/**
 * 查询订单
 * @param pageNo
 */
function gotoPage(pageNo){
	var pageSize = $("#curPageSize").val().trim();
	var orderBy = $("#orderBy").val().trim();
	var sort = $("#sort").val().trim();
	var url = $("#conditionDiv input[name=actionName]").val()+"?method=showOrderInfos";
	url += ("&orderBy="+orderBy+"&sort="+sort+"&pageno="+pageNo+"&pageSize="+pageSize);
	$("#orderOprForm").attr("action",url);
	$("#orderOprForm").submit();
	/*
	 var startDate = $("#startDate").val().trim();
	 var endDate = $("#endDate").val().trim(); 
	 $.ajax({
		type : "POST",
		url : $("#conditionDiv input[name=actionName]").val()+"?method=showThirdOrderAudit",
		dataType: "html",
		data : {
			    startDate:startDate,
			    endDate:endDate,
			    orderBy:orderBy,
			    sort:sort,
			    pageno:pageNo,
			    pageSize:pageSize
			   },
		success: function(data){
			$("#contentDiv").parent().html(data);
		},
		error: function() {
			$("#contentDiv").parent().html("");
		}
	});*/
}

/**
 * 翻页
 * @param pageSize
 */
function gotoPageSize(pageSize){
	$("#curPageSize").val(pageSize);
	$("#curPage").val(1);
	gotoPage(1);
}


/**
 * 获取订单主页商品明细
 */
function getMainOrderDetails(orderNumber,orderFlag){
	$("#orderDetailDiv").show();
	$("#orderDetailsTable tr:gt(0)").remove();
	$("#kfAuditDescriptionDetailPage").val("");
	getOrderDetails(orderNumber,orderFlag,"orderDetailsTable");
}

/**
 * 订单详情页商品明细
 * @param orderNumber
 */
function orderDetailsPageView(orderNumber,orderFlag){
	$('#orderDetailPageModal').modal('show');
	$("#detailsPageTable tr:gt(0)").remove();
	getOrderDetailPage(orderNumber,orderFlag);
}


/**
 * 根据订单编码获取订单详情页信息
 * @param orderNumber
 */
function getOrderDetailPage(orderNumber,orderFlag){
	$.ajax({
		type : "POST",
		url : $("#conditionDiv input[name=actionName]").val()+"?method=getOrderDetailPage",
		dataType: "json",
		data : {
		    orderNumber:orderNumber,
		    orderFlag : orderFlag
		   },
		success: function(data){
			var orderMainInfo = jQuery.parseJSON(data.orderMainInfo); 
			var orderDetails = jQuery.parseJSON(data.orderDetails); 
			if(orderMainInfo.auditStatus=='WAIT' || orderMainInfo.auditStatus=='RETURN'){
				$("#auditUserLabel").hide();
				$("#succBtn").attr("disabled",false); 
				$("#retnBtn").attr("disabled",false); 
				$("#kfInvoiceStatusDetailPage").attr("disabled",false); 
				$("#kfInvoiceTypeDetailPage").attr("disabled",false);
				$("#kfInvoiceTitleDetailPage").attr("disabled",false); 
				$("#kfInvoiceContentDetailPage").attr("disabled",false);
				$("#kfRemarkDetailPage").attr("disabled",false);
				$("#kfAuditDescriptionDetailPage").attr("disabled",false); 
			}else{
				$("#auditUserLabel").show();
				$("#succBtn").attr("disabled",true);
				$("#retnBtn").attr("disabled",true);
				$("#kfInvoiceStatusDetailPage").attr("disabled",true);
				$("#kfInvoiceTypeDetailPage").attr("disabled",true);
				$("#kfInvoiceTitleDetailPage").attr("disabled",true);
				$("#kfInvoiceContentDetailPage").attr("disabled",true);
				$("#kfRemarkDetailPage").attr("disabled",true);
				$("#kfAuditDescriptionDetailPage").attr("disabled",true);
			}
			
			//判断是否需要展示药师审核信息
			if(orderMainInfo.auditStatus=='RETURN' || orderMainInfo.auditStatus=='SUCC'){
				$("#doctorFieldSet").show();
			}
			//灰化药师审核信息
			$("#doctorAuditDescriptionDetailPage").attr("disabled",true); 
			
			//若渠道不是天猫处方药，则暂时关闭修改发票信息功能
			if(orderMainInfo.orderFlag!="TMCFY" && orderMainInfo.orderFlag!="yhdcfy" && orderMainInfo.orderFlag!="SLLCFY"){
				$("#kfInvoiceFieldSet").hide();
				$("#kfRemarkDiv").hide();
			}
			
			
			
			$("#receiverDetailPage")[0].innerHTML = (orderMainInfo.receiver==undefined?'':orderMainInfo.receiver);
			$("#orderNumberDetailPage")[0].innerHTML = (orderMainInfo.orderNumber==undefined?'':orderMainInfo.orderNumber);
			$("#orderFlagDetailPage")[0].innerHTML = (orderMainInfo.orderFlag==undefined?'':orderMainInfo.orderFlag);
			$("#addressDetailDetailPage")[0].innerHTML = ((orderMainInfo.province!=undefined?orderMainInfo.province:"")+(orderMainInfo.city!=undefined?orderMainInfo.city:"")+(orderMainInfo.area!=undefined?orderMainInfo.area:"")+(orderMainInfo.addressDetail!=undefined?orderMainInfo.addressDetail:""));
			$("#orderTimeDetailPage")[0].innerHTML = (orderMainInfo.orderTime==undefined?'':orderMainInfo.orderTime);
			$("#telephoneDetailPage")[0].innerHTML = ((orderMainInfo.telephone==""||orderMainInfo.telephone==null||orderMainInfo.telephone==undefined)?(orderMainInfo.mobile==""||orderMainInfo.mobile==null||orderMainInfo.mobile==undefined?"":orderMainInfo.mobile):orderMainInfo.telephone);
			/*var phone = $("#telephoneDetailPage").text();
			if(phone!=null &&  phone != undefined && phone.length>4){
				$("#telephoneDetailPage").text(phone.substring(0,3)+"****"+phone.substring(8,11));
			}*/
			$("#orderPriceDetailPage")[0].innerHTML = (orderMainInfo.orderPrice==undefined?'':orderMainInfo.orderPrice);
			$("#sexDetailPage")[0].innerHTML = (orderMainInfo.sex==undefined?'':(orderMainInfo.sex=="MALE"?"男":"女"));
			$("#ageDetailPage")[0].innerHTML = (orderMainInfo.age==undefined||orderMainInfo.age=="0"?'':orderMainInfo.age);
			$("#auditUserDetailPage")[0].innerHTML = (orderMainInfo.kfAuditUser==undefined?'':orderMainInfo.kfAuditUser);
			$("#kfAuditDescriptionDetailPage")[0].innerHTML = (orderMainInfo.kfAuditDescription==undefined?'':orderMainInfo.kfAuditDescription);
			$("#doctorAuditDescriptionDetailPage")[0].innerHTML = (orderMainInfo.doctorAuditDescription==undefined?'':orderMainInfo.doctorAuditDescription);
			$("#kfRemarkDetailPage").val(orderMainInfo.remark==undefined?'':orderMainInfo.remark);
			
			$("#kfInvoiceStatusDetailPage").find("option[value='"+orderMainInfo.invoiceStatus+"']").attr("selected",true);
			$("#kfInvoiceTypeDetailPage option[value='"+orderMainInfo.invoiceType+"']").attr("selected",true);
			$("#kfInvoiceTitleDetailPage").attr("value",orderMainInfo.invoiceTitle);
			$("#kfInvoiceContentDetailPage").attr("value",orderMainInfo.invoiceContent);
			$("#kfRemarkDetailPage").attr("value",orderMainInfo.remark);
			
			if(orderMainInfo.invoiceStatus=="1"){
				$("#kfInvoiceTypeLabel").show();
				$("#kfInvoiceTitleLabel").show();
				$("#kfInvoiceContentLabel").show();
			}else{
				$("#kfInvoiceTypeLabel").hide();
				$("#kfInvoiceTitleLabel").hide();
				$("#kfInvoiceContentLabel").hide();
			}
			
			
			var orderDetailItem = "";
			$.each(orderDetails, function(i, result){
				orderDetailItem = "<tr class='detailsTr'>"+
	                       "<td style='width: 100px;'>"+((result.productId!=null&&result.productId!=''&&result.productId.length>0&&result.productId!=undefined)?result.productId:result.merchantId)+"</td>"+
	                       "<td style='width: 100px;'>"+result.productName+"</td>"+
	                       "<td style='width: 100px;'>"+result.productSpec+"</td>"+
		                   	"<td style='width: 100px;'>"+result.productBrand+"</td>"+
		                   	"<td style='width: 100px;'>"+result.productLicenseNo+"</td>"+
		        			"<td style='width: 100px;'>"+result.pharmacyCompany+"</td>"+
		        			"<td style='width: 60px;'>"+result.price.toFixed(2)+"</td>"+
		        			"<td style='width: 60px;'>"+result.count+"</td>"+
		        			"<td style='width: 50px;'>"+result.totalPrice.toFixed(2)+"</td>"+
		        			"<td style='width: 50px;'>"+(result.giftFlag=="1"?'是':'否')+"</td>"+
	                      "<td style='width: 50px;'>"+(result!=''&&result.prescriptionType=='1'?'处方药':(result.prescriptionType=='0'?'非处方药':''))+"</td>"+
        	           "</tr>";
			    	$("#detailsPageTable").append(orderDetailItem);
			    	var tdArr = $(".detailsPageTable").find("tr").find("td");
			    	for(var i=0;i<tdArr.length;i++){
			    		tdArr[i].title =tdArr[i].innerHTML; 
			    	}
			});
		},
		error: function() {
			$.messager.alert("处理结果", "获取订单明细信息失败！");
		}
	});
}


/**
 * 获取商品明细
 * @param orderNumber
 * @param flag
 */
function getOrderDetails(orderNumber,orderFlag,flag){
	$.ajax({
		type: "POST",
        url: $("#conditionDiv input[name=actionName]").val()+"?method=getOrderDetails",
        dataType: "text",
        data: {
        	orderNumber : orderNumber,
        	orderFlag : orderFlag
        	},
        success: function(data){
        	if(data!=''){
        		var item;
        		$.each(JSON.parse(data), function(i, result){
        			item = "<tr>"+
        			"<td>"+((result.productId!=null&&result.productId!=''&&result.productId.length>0&&result.productId!=undefined)?result.productId:result.merchantId)+"</td>"+
        			"<td>"+result.productName+"</td>"+
        			"<td>"+result.productSpec+"</td>"+
        			"<td>"+result.productBrand+"</td>"+
        			"<td>"+result.productLicenseNo+"</td>"+
        			"<td>"+result.pharmacyCompany+"</td>"+
        			"<td>"+result.price.toFixed(2)+"</td>"+
        			"<td>"+result.count+"</td>"+
        			"<td>"+result.totalPrice.toFixed(2)+"</td>"+
        			"<td>"+(result.giftFlag=="1"?'是':'否')+"</td>"+
        			"<td>"+(result!=''&&result.prescriptionType=='1'?'处方药':(result.prescriptionType=='0'?'非处方药':''))+"</td>"+
        			"</tr>";
        			if(flag=="orderDetailsTable"){
        				$("#orderDetailsTable").append(item);
        			}
        			if(flag=="detailsPageTable"){
        				$("#detailsPageTable").append(item);
        				var tdArr = $(".detailsPageTable").find("tr").find("td");
        				for(var i=0;i<tdArr.length;i++){
        					tdArr[i].title =tdArr[i].innerHTML; 
        				}
        			}
        		});
        	}
		},   
		error : function(){
				$.messager.alert("错误提示", "获取商品明细加载失败,请重新选择订单或联系管理员！");
    		}
    	});
}

/**
 * 审核订单
 * @param flag
 */
function auditFunc(flag){
	var pageno = $(":input[name='pageno']")[0].value;
	var orderNumber = $("#orderNumberDetailPage")[0].innerHTML;
	var orderFlag = $("#orderFlagDetailPage")[0].innerHTML;
	var auditStatusDesc = '通过';
	var auditDescription =  $("#kfAuditDescriptionDetailPage")[0].value;
	var invoiceStatus =  $("#kfInvoiceStatusDetailPage").val();
	var invoiceType =  $("#kfInvoiceTypeDetailPage").val();
	var invoiceTitle =  $("#kfInvoiceTitleDetailPage").val();
	var invoiceContent =  $("#kfInvoiceContentDetailPage").val();
	var remark =  $("#kfRemarkDetailPage").val();
	
	
	
	if(auditDescription.length>2000){
		$.messager.alert("错误提示", "审核说明太长，请保持在2000字符以内！");
		return;
	}
	if(flag=='PRERETURN'){
		auditStatusDesc = '驳回';
		if(auditDescription == ''){
			$.messager.alert("错误提示", "请输入驳回原因！");
			return;
		}
	}
	
	if(invoiceStatus=="1"){
		if(invoiceTitle.length==0){
			$.messager.alert("错误提示", "请输入抬头信息！");
			$("#kfInvoiceTitleDetailPage").focus(); 
			return;
		}
		if(invoiceContent.length==0){
			$.messager.alert("错误提示", "请输入发票内容信息！");
			$("#kfInvoiceContentDetailPage").focus();
			return;
		}
	}
	
	$.messager.confirm("确认框", "确定要审核"+auditStatusDesc+"该订单："+orderNumber+" 吗？", function() {
		$.ajax({
			type: "POST",
	        url: $("#conditionDiv input[name=actionName]").val()+"?method=auditOrder",
	        traditional:true,  //默认false:深度序列化  true：阻止其深度序列化
	        dataType: "text",
	        async:true,//true 默认异步请求  false:同步请求 将锁定浏览器其他请求需等待处理完
	        data: {
	        	auditStatus:flag,
	        	orderNumber:orderNumber,
	        	orderFlag:orderFlag,
	        	auditDescription:auditDescription,
	        	invoiceStatus:invoiceStatus,
	        	invoiceType:invoiceType,
	        	invoiceTitle:invoiceTitle,
	        	invoiceContent:invoiceContent,
	        	remark:remark
	        	},
	        success: function(data){
	        	alert(data);
//	        	$.messager.alert("处理结果", data);
				gotoPage(pageno);
			},   
			error : function(){
					$.messager.alert("错误提示", "审核操作失败，请联系管理员！");
	    		}
	    	});
	});
}


/* 
*  方法:Array.remove(dx) 通过遍历,重构数组 
*  功能:删除数组元素. 
*  参数:dx删除元素的下标. 
*/ 
Array.prototype.remove=function(dx) 
{ 
    if(isNaN(dx)||dx>this.length){return false;} 
    for(var i=0,n=0;i<this.length;i++) 
    { 
        if(this[i]!=this[dx]) 
        { 
            this[n++]=this[i] 
        } 
    } 
    this.length-=1 
};

/**
 * 批量审核通过
 */
function batchAuditPassFunc(){
	var orderNumFlags_arr = new Array();
	var orderNumStr = '';
	var orderNumFlagStr = '';
	var checkedArr = $("[name='selItem']:checkbox:checked");
	var tempArr = new Array();
	
	var pageno = $(":input[name='pageno']")[0].value;
	
	//只保留待审核的元素
	checkedArr.each(function(i){
		if(!$(this)[0].disabled){
			tempArr.push($(this)[0]);
		}
    });
	
	var invoiceStatus = false;
	var invoiceOrder = "";
	for(var i=0;i<tempArr.length;i++){
		var orderNumFlagArr = tempArr[i].value.split(",");
		orderNumStr += orderNumFlagArr[0]+"、";
		var orderRemark = tempArr[i].orderRemark;
		orderNumFlagStr = orderNumFlagArr[0]+orderNumFlagArr[1];
		orderNumFlags_arr.push(orderNumFlagStr);
		if(orderNumFlagArr[2]!=null&&orderNumFlagArr[2]!=""&&orderNumFlagArr[2]!=undefined&&(orderNumFlagArr[2].indexOf("发票")>0 || orderNumFlagArr[2].indexOf("个人")>0)){
			invoiceStatus = true;
			invoiceOrder = orderNumFlagArr[0];
		}
	}
	
	if(invoiceStatus){
		$.messager.alert("处理结果", "不允许批量审核通过订单，请单个审批并处理具有发票信息的订单！订单号："+invoiceOrder);
		return ;
	}
	
	if(tempArr.length==0){
		$.messager.alert("处理结果", "请先选择批量审批的待审核订单！");
		return ;
	}
	var urlArr = $(":input[name='contextPath']")[0].value.split("//");
	var contextPath = urlArr[0]+"//"+urlArr[1].split("/")[0]+"/"+urlArr[1].split("/")[1];
	$.messager.confirm("确认框", "确定要批量审核通过，待审核的订单："+orderNumStr.substring(0, orderNumStr.length-1)+" 吗？", function() {
		 $.ajax({
				type: "POST",
		        url: $("#conditionDiv input[name=actionName]").val()+"?method=updateAuditStatusBatch",
		        dataType: "text",
		        traditional:true,  //默认false:深度序列化  true：阻止其深度序列化
		        data: {arrParam : orderNumFlags_arr},
		        async:true,//true 默认异步请求  false:同步请求 将锁定浏览器其他请求需等待处理完
		        beforeSend:function(XMLHttpRequest){ 
		        	showLoading();
//		              $("#tableDiv").html("<img src='"+contextPath+"/images/loading.gif' />"); 
		         }, 
		        success: function(data){
		        	alert(data);
		        	gotoPage(pageno);
		        	hideLoading();
				},   
				error : function(){
					$.messager.alert("处理结果", "批量审核失败！");
					hideLoading();
		    		}
		    	});
	      });
}

function showLoading() {
	document.getElementById("loading").style.display = "block";
}

function hideLoading() {
	document.getElementById("loading").style.display = "none";
}

