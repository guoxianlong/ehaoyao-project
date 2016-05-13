String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
};

//页面元素加载完毕后执行
$(document).ready(function() {
	
	$("#orderDetailDiv").hide();
	
	//查询
	$('#queryBtnId').click(function() {
		gotoPage(1);
	});
	
	//保存
	$('#saveBtnId').click(function() {
		saveFunc();
	});
	
	//打印
	$('#printBtnId').click(function() {
		printFunc();
	});
	
	$("#DrupPicture").mCustomScrollbar({
		setHeight:400,
		setWidth:600,
		axis:"yx",
		scrollButtons:{enable:true},
		//theme:"3d",
		theme:"minimal-dark",
		//scrollbarPosition:"outside"
	});
	
     $("#detailPageTbDiv").mCustomScrollbar({
		setHeight:150,
		theme:"minimal-dark"
     });
     
     $("#ulId").mCustomScrollbar({
 		setHeight:300,
 		theme:"minimal-dark"
      });
     
     
   //为td增加触发事件 排除oprStatusTd样式列的表格
 	tdClick();
		
	
});

/**
 * 为td增加触发事件 排除oprStatusTd样式列的表格
 */
function tdClick(){
	//为td增加触发事件 排除oprStatusTd样式列的表格
	var TdArr = $(".orderInfoTr td:not('.oprStatusTd'):not('.detailTd')");
	for(var i=0;i<TdArr.length;i++){
		TdArr[i].onclick = function(event){
			if(event.currentTarget.parentElement.cells.length>0){
//				console.log("orderNumber："+event.currentTarget.parentElement.cells[1].innerHTML);
				var orderFlag = event.currentTarget.parentElement.cells[2].innerHTML;
				getMainOrderDetails(event.currentTarget.parentElement.cells[1].innerHTML,orderFlag);
			}
		};
	}
}

	
//查询事件
function gotoPage(pageNo){
	var pageSize = $("#curPageSize").val().trim();
	var orderBy = $("#orderBy").val().trim();
	var sort = $("#sort").val().trim();
	var url = $("#conditionDiv input[name=actionName]").val()+"?method=showOrderInfos";
	url += ("&orderBy="+orderBy+"&sort="+sort+"&pageno="+pageNo+"&pageSize="+pageSize);
	$("#orderOprForm").attr("action",url);
	$("#orderOprForm").submit();
	
	/*
	var orderNumber = $("#orderNumber").val().trim();
	//	var orderStatus = $("#orderStatus").val().trim();
	var auditStatus = $("#auditStatusSelect").val().trim();
	var startDate = $("#startDate").val().trim();
	var endDate = $("#endDate").val().trim();
	 $.ajax({
		type : "POST",
		url : $("#conditionDiv input[name=actionName]").val()+"?method=getOrderInfos",
		dataType: "html",
		data : {
			    startDate:startDate,
			    endDate:endDate,
			    orderNumber:orderNumber,
			    auditStatus:auditStatus,
			    orderBy:orderBy,
			    sort:sort,
			    pageno:pageNo,
			    pageSize:pageSize},
		success: function(data){
			$("#conditionDiv").parent().html(data);
		},
		error: function() {
			$("#conditionDiv").parent().html("");
		}
	});
	*/
}

//翻页
function gotoPageSize(pageSize){
	$("#curPageSize").val(pageSize);
	$("#curPage").val(1);
	gotoPage(1);
}

 
//订单状态保存
function saveFunc(){
	var pageno = $(":input[name='pageno']")[0].value;
	
	 var k=0;
	 var oprStatus = $("select[name='oprStatus']").find("option:selected");
	 var arr = new Array();
	 var orderNumStr = "";
	 $(oprStatus).each(function(i){
		 if(oprStatus[i].value!='UNTREATED' && (oprStatus[i].parentElement.disabled == false)){
			 arr[k] = oprStatus[i].parentElement.parentElement.parentElement.parentElement.cells[1].innerHTML+","+oprStatus[i].value;
			 orderNumStr +=oprStatus[i].parentElement.parentElement.parentElement.parentElement.cells[1].innerHTML+"、";
			 k++;
		 }
	 });
	 if(arr.length<=0){
		 $.messager.alert("错误提示", "请先选择订单状态！");
		 return;
	 }
	$.messager.confirm("确认框", "确定要完成订单："+orderNumStr.substring(0, orderNumStr.length-1)+" 吗？", function() {
		 $.ajax({
				type: "POST",
		        url: $("#conditionDiv input[name=actionName]").val()+"?method=updOrderStatus",
		        dataType: "text",
		        traditional:true,  //默认false:深度序列化  true：阻止其深度序列化
		        data: {arrParam : arr},
		        async:true,//true 默认异步请求  false:同步请求 将锁定浏览器其他请求需等待处理完
		        success: function(data){
		        	alert(data);
//		        	$.messager.alert("处理结果", data);
		        	gotoPage(pageno);
				},   
				error : function(){
					$.messager.alert("处理结果", "更新订单状态失败！");
		    		}
		    	});
	      });
}
 

//打印
function printFunc(){
	 window.print();
	 $.messager.alert("打印。。");
}
  

//获取订单主页商品明细
function getMainOrderDetails(orderNumber,orderFlag){
	$("#orderDetailDiv").show();
	$("#orderDetailsTable tr:gt(0)").remove();
	getOrderDetails(orderNumber,orderFlag,"orderDetailsTable");
}
 
//订单详情页商品明细
function orderDetailsPageView(orderNumber,orderFlag){
	$('#orderDetailPageModal').modal('show');
	$("#detailsPageTable tr:gt(0)").remove();
	getOrderDetailPage(orderNumber,orderFlag);
//	getOrderDetails(orderNumber,"detailsPageTable");
}

//根据订单编码获取订单详情页信息
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
			var orderInfo = jQuery.parseJSON(data.orderInfo); 
			var orderDetails = jQuery.parseJSON(data.orderDetails); 
			if(orderInfo.auditStatus!='WAIT'){
				$("#auditUserLabel")[0].style.display = "block";
				$("#auditDescription")[0].disabled = "disabled";
				$("#succBtn")[0].disabled = "disabled";
				$("#retnBtn")[0].disabled = "disabled";
				$("#addBtn")[0].disabled = "disabled";
			}else{
				$("#auditDescription")[0].disabled = "";
				$("#succBtn")[0].disabled = "";
				$("#retnBtn")[0].disabled = "";
				$("#addBtn")[0].disabled = "";
				$("#auditDescription")[0].value = "";
			}
			$("#orderNumberDetailPage")[0].innerHTML = (orderInfo.orderNumber==undefined?'':orderInfo.orderNumber);
			$("#orderFlagDetailPage")[0].innerHTML = (orderInfo.orderFlag==undefined?'':orderInfo.orderFlag);
			$("#smallPicLink")[0].src = (orderInfo.smallPicLink==undefined?'':orderInfo.smallPicLink);
			$("#bigPicLink")[0].src = (orderInfo.bigPicLink==undefined?'':orderInfo.bigPicLink);
			$("#userId")[0].innerHTML = (orderInfo.userId==undefined?'':orderInfo.userId);
			$("#userCode")[0].innerHTML =(orderInfo.userCode==undefined?'':orderInfo.userCode);
			$("#orderTime")[0].innerHTML = (orderInfo.orderTime==undefined?'':orderInfo.orderTime);
			$("#orderPrice")[0].innerHTML = (orderInfo.orderPrice==undefined?'':orderInfo.orderPrice);
			$("#decoctFlag")[0].innerHTML = (orderInfo.decoctFlag==undefined?'':(orderInfo.decoctFlag=="0"?"否":"是"));
			$("#pregnantFlag")[0].innerHTML = (orderInfo.pregnantFlag==undefined?'':(orderInfo.pregnantFlag=="0"?"否":"是"));
			$("#patientName")[0].innerHTML = (orderInfo.patientName==undefined?'':orderInfo.patientName);
			$("#sex")[0].innerHTML = (orderInfo.smallPicLink==undefined?'':(orderInfo.sex=="MALE"?"男":"女"));
			$("#age")[0].innerHTML = (orderInfo.age==undefined?'':orderInfo.age);
			$("#dose")[0].innerHTML = (orderInfo.dose==undefined?'':orderInfo.dose);
			$("#instructions")[0].innerHTML = (orderInfo.smallPicLink==undefined?'':orderInfo.smallPicLink);orderInfo.instructions;
			$("#auditDescription")[0].innerHTML = (orderInfo.auditDescription==undefined?'':orderInfo.auditDescription);
			$("#auditUser")[0].innerHTML = (orderInfo.auditUser==undefined?'':orderInfo.auditUser);

			var orderDetailItem = "";

			$.each(orderDetails, function(i, result){
				orderDetailItem = "<tr class='detailsTr'>"+
	                       "<td>"+((result.productId!=''&&result.productId.length>0&&result.productId!=undefined)?result.productId:result.merchantId)+"</td>"+
	                       "<td>"+result.productName+"</td>"+
	                       "<td>"+result.productSpec+"</td>"+
	                       "<td>"+result.productBrand+"</td>"+
	                       "<td>"+result.count+"</td>"+
	                       "<td>"+result.price.toFixed(2)+"</td>"+
	                       "<td>"+result.totalPrice.toFixed(2)+"</td>"+
	                       "<td>"+(result!=''&&result.giftFlag=='1'?'是':'否')+"</td>"+
	                      "<td>"+(result!=''&&result.prescriptionType=='1'?'处方药':'非处方药')+"</td>"+
	                      "<td><button type='button' class='btn btn-primary btn-sm' onclick='delFunc('"+result.productId+"')'> 删除 </button></td>"+
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



//获取商品明细
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
			var item;
			$.each(JSON.parse(data), function(i, result){
			    item = "<tr class='detailsTr'>"+
	                       "<td>"+((result.productId!=''&&result.productId.length>0&&result.productId!=undefined)?result.productId:result.merchantId)+"</td>"+
	                       "<td>"+result.productName+"</td>"+
	                       "<td>"+result.productSpec+"</td>"+
	                       "<td>"+result.productBrand+"</td>"+
	                       "<td>"+result.count+"</td>"+
	                       "<td>"+result.price.toFixed(2)+"</td>"+
	                       "<td>"+result.totalPrice.toFixed(2)+"</td>"+
	                       "<td>"+(result!=''&&result.giftFlag=='1'?'是':'否')+"</td>"+
	                      "<td>"+(result!=''&&result.prescriptionType=='1'?'处方药':'非处方药')+"</td>"+
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
		},   
		error : function(){
			alert("获取商品明细加载失败,请重新选择订单");
    		}
    	});
}

function auditFunc(flag){
	var orderNumber = $("#orderNumberDetailPage")[0].innerHTML;
	var orderFlag = $("#orderFlagDetailPage")[0].innerHTML;
	var pageno = $(":input[name='pageno']")[0].value;
	
	var auditStatusDesc = '通过';
	var auditDescription =  $("#auditDescription")[0].value;
	if(auditDescription.length>2000){
		$.messager.alert("错误提示", "审核说明太长，请保持在2000字符以内！");
		return;
	}
	if(flag=='RETURN'){
		auditStatusDesc = '驳回';
		if(auditDescription == ''){
			$.messager.alert("错误提示", "请输入驳回原因！");
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
	        	auditDescription:auditDescription
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