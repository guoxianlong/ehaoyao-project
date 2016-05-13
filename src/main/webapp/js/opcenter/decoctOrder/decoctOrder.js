String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
};

//页面元素加载完毕后执行
$(document).ready(function() {
	
	$('.selectpicker').selectpicker();
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
	
	//批量发货
	$('#batchDeliverBtnId').click(function() {
		batchDeliverFunc();
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
     
		
	tdClick();
	
	//处理Ctrl/Shift多选
	doCtrlShiftKeyPressed();
});

/**
 * 
 */
function tdClick(){
	//为td增加触发事件 排除oprStatusTd样式列的表格
	var TdArr = $(".orderInfoTr td:not('.oprStatusTd'):not('.detailTd')");
	for(var i=0;i<TdArr.length;i++){
		TdArr[i].onclick = function(event){
			if(event.currentTarget.parentElement.cells.length>2){
				var orderNumber = event.currentTarget.parentElement.cells[1].innerHTML;
				var orderFlag = event.currentTarget.parentElement.cells[2].innerHTML;
				//console.log("orderNumber："+orderNumber+",orderFlag="+orderFlag);
				getMainOrderDetails(orderNumber,orderFlag);
			}
		};
	}
}

function doCtrlShiftKeyPressed() {
	var start = null;
	$(".orderInfoTr").click(function(e){
		e = e || event;
		if(e.shiftKey){
			$(".orderInfoTr").css("user-select","none");
			var si = $(start).index(),ti = $(this).index();
			var sel = $(".orderInfoTr").slice(Math.min(si,ti),Math.max(si,ti)+1);		
			sel.addClass("selectTr"); 
			$(".orderInfoTr").not(sel).removeClass("selectTr");
			return;
		}else if(e.ctrlKey){
			if($(this).hasClass("selectTr")){
				$(this).removeClass("selectTr");
			}else{
				$(this).addClass("selectTr");
			}
			return;
		}
		$(".orderInfoTr").css("user-select","");
		start = this;
		$(this).addClass("selectTr"); 
		$(".orderInfoTr").not($(this)).removeClass("selectTr");
	});
}

/*function doCtrlShiftKeyPressed() {
	var start = null;
	$(".orderInfoTr").click(function(e){
		e = e || event;
		if(e.shiftKey){
			$(".orderInfoTr").css("user-select","none");
			var si = $(start).index(),ti = $(this).index();
			var sel = $(".orderInfoTr").slice(Math.min(si,ti),Math.max(si,ti)+1);		
			sel.attr("sel","sel"); 
			$(".orderInfoTr").not(sel).removeAttr("sel");
			return;
		}else if(e.ctrlKey){
			if($(this).attr("sel")){
				$(this).removeAttr("sel");
			}else{
				$(this).attr("sel","sel");
			}
			return;
		}
		$(".orderInfoTr").css("user-select","");
		start = this;
		$(this).attr("sel","sel"); 
		$(".orderInfoTr").not($(this)).removeAttr("sel");
		
	});
}*/


function doShiftKeyPressed(event) {
	var trTarget = event.currentTarget.parentElement;
	var backgroundColor = trTarget.style.backgroundColor;
	if (event.shiftKey == 1) {
		if(backgroundColor=="rgb(102, 204, 102)"){
			backgroundColor="";
		}else{
			var TdArr = $(".orderInfoTr");
			var ti = $(this).index();
			backgroundColor="rgb(102, 204, 102)";
		}
		trTarget.style.backgroundColor = backgroundColor;
	}
}


	
// 查询事件
function gotoPage(pageNo){
	var orderNumber = $("#orderNumber").val().trim();
	var orderStatus = $("#orderStatus").val().trim();
	var pageSize = $("#curPageSize").val().trim();
	var startDate = $("#startDate").val().trim();
	var endDate = $("#endDate").val().trim();
	var orderBy = $("#orderBy").val().trim();
	var sort = $("#sort").val().trim();
	var url = $("#conditionDiv input[name=actionName]").val()+"?method=showOrderInfos";
	url += ("&orderBy="+orderBy+"&sort="+sort+"&pageno="+pageNo+"&pageSize="+pageSize);
	$("#orderOprForm").attr("action",url);
	$("#orderOprForm").submit();
	/*$.ajax({
		type : "POST",
		url : $("#conditionDiv input[name=actionName]").val()+"?method=getOrderInfos",
		dataType: "html",
		data : {
			    startDate:startDate,
			    endDate:endDate,
			    orderNumber:orderNumber,
			    orderStatus:orderStatus,
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
	});*/
}

//翻页
function gotoPageSize(pageSize){
	$("#curPageSize").val(pageSize);
	$("#curPage").val(1);
	gotoPage(1);
}

 
//订单状态保存
function saveFunc(){
	 var k=0;
	 var oprStatus = $("select[name='oprStatus']").find("option:selected");
	 var pageno = $(":input[name='pageno']")[0].value;
	 var arr = new Array();
	 var orderNumStr = "";
	 $(oprStatus).each(function(i){
		 if(oprStatus[i].value!='UNTREATED' && (oprStatus[i].parentElement.disabled == false)){
			 var cellsArr = oprStatus[i].parentElement.parentElement.parentElement.parentElement;
			 var orderNumber = cellsArr.cells[1].innerHTML;
			 var orderFlag = cellsArr.cells[2].innerHTML;
			 var orderStatus = oprStatus[i].value;
			 arr[k] = orderNumber+orderFlag+","+orderStatus;
			 orderNumStr +=cellsArr.cells[1].innerHTML+"、";
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
	var orderNumberFlags = '';
	var orderNum = '';
	var selTr = $(".orderInfoTr.selectTr");
	if(selTr.length>0){
		for(var i=0;i<selTr.length;i++){
			if(i==selTr.length-1){
				orderNum += selTr[i].cells[1].innerHTML;
				orderNumberFlags += (selTr[i].cells[1].innerHTML+selTr[i].cells[2].innerHTML);	
			}else{
				orderNum += selTr[i].cells[1].innerHTML+"、";
				orderNumberFlags += (selTr[i].cells[1].innerHTML+selTr[i].cells[2].innerHTML)	+ ",";
			}
		}
	}
	if(orderNumberFlags.length<=0){
		$.messager.alert("验证结果", "请选择要打印的订单！");
		return;
	}
	$.messager.confirm("确认框", "确定要批量打印订单："+orderNum.substring(0, orderNum.length-1)+" 吗？", function() {
		printOrders(orderNumberFlags);
	});
}
  
//打印订单信息，可打印多单
function printOrders(orderNumberFlags){
	var orderInfo;
	var orderdetails;
	var patientName;
	var orderNumber;
	var orderFlag;
	var age;
	var sex;
	var pregnantFlag;
	var instructions;
	var productId;
	var productName;
	$.ajax({
		type : "POST",
		url : $("#conditionDiv input[name=actionName]").val()+"?method=printOrders",
		dataType: "json",
		data : {
			orderNumber:orderNumberFlags
		   },
		success: function(data){
			showPrint(data);
		},
		error: function() {
			$.messager.alert("处理结果", "打印订单信息失败，请联系管理员！");
		}
	});
}

/**
 * 展示打印内容
 * @param data
 */
function showPrint(data) {
/*	var urlArr = $(":input[name='contextPath']")[0].baseURI.split("//");
	var contextPath = urlArr[0]+"//"+urlArr[1].split("/")[0]+"/"+urlArr[1].split("/")[1];
	console.log(contextPath);*/
	
	var head = "<!DOCTYPE html><html><head><title>煎药订单打印</title>" +
			"<style>" +
			" * {margin: 0;padding: 0;font-size: 13px;} " +
			" body {width: 100%;align: center;} " +
			" label {margin:10px 30px;} " +
			" span {margin: 0 5px;color: #66CC99;} " +
			" hr {margin: 10px 0px;} " +
			" .orderDivStyle { page-break-after: always;margin:0px } " +
			"</style>" +
			"</head><body>";//头部
	var foot = "</body></html>";//尾部
	var printContent = "";//内容
	for(var i=0;i<data.length;i++){
		var orderInfoContent = ""
		var orderDetailsContent = ""
		var liContent = "";
		orderInfo = data[i].orderInfo;
		orderdetails = data[i].details;
		orderInfoContent += getOrderInfoContent(orderInfo);
		for(var j=0;j<orderdetails.length;j++){
			if((j+1)%3==0){
				orderDetailsContent += ("<ul style='list-style:none;clear: both;'>"+(liContent+getOrderDetailsContent(orderdetails[j]))+"</ul>");
				liContent = "";
			}else{
				liContent += getOrderDetailsContent(orderdetails[j]);
			}
		}
		if(liContent!=""){
			orderDetailsContent += ("<ul style='list-style:none;clear: both;'>"+liContent+"</ul>");
		}
		printContent += getPrintContent(orderInfoContent+orderDetailsContent+"<div style='clear: both;'></div>");
	}
	//新开页打印
	printNewPage(head+printContent+foot);
//  printPage(pageno);
}

function printNewPage(printContent){
	var printWin = window.open("煎药订单打印", "_blank");
	printWin.document.write(printContent);
	printWin.document.close();
	printWin.print();
//	printWin.close();
}

function printPage(printDiv){   
    //var newstr = document.all.item(printDiv).innerHTML;
    var newstr = document.getElementById(printDiv).innerHTML;
    var oldstr = document.body.innerHTML;
    document.body.innerHTML = newstr;
    window.print();
    document.body.innerHTML = oldstr;
    return false;
    }

function getPrintContent(content){
	return " <div class='orderDivStyle' >"+content+"</div> ";
}

function getOrderInfoContent(orderInfo){
	var urlArr = $(":input[name='contextPath']")[0].value.split("//");
	var contextPath = urlArr[0]+"//"+urlArr[1].split("/")[0]+"/"+urlArr[1].split("/")[1];
	//console.log(contextPath);
	return  " <div style='float: left;'><img style='width: 200px;height: 150px;' src='"+(contextPath+"/img/jiuzhoutong.jpg")+"' alt='九州通医药集团'/></div>"
	+ "		<div style='float: left;'><img style='width: 200px;height: 150px;' src='"+(contextPath+"/barcode?msg="+orderInfo.orderNumber+"&barType=CODE39")+"' alt='订单条形码'></div>" 
	+ "		<div style='float: right;'>" +
			"<label>内部处方编号：<span>1234567</span></label><br />"
	+ "		<label>客户处方编号：<span>1234567</span></label><br />"
	+ "		<label>客户处方序号：<span>1234567</span></label> " +
			"</div>"
	+ "		<br style='clear: both;' />"
	+ "		<hr style='height:10px;border:none;border-top:10px groove skyblue;' />"
	+ "		<label>患者姓名：<span id='patientName'>"+orderInfo.patientName+"</span></label>"
	+ "		<label>性别：<span id='sex'>"+(orderInfo.sex=='MALE'?'男':'女')+"</span></label>"
	+ "		<label>年龄：<span id='age'>"+orderInfo.age+"</span></label>"
	+ "		<label>是否妊娠：<span id='pregnantFlag'>"+(orderInfo.pregnantFlag==1?'是':'否')+"</span></label>"
	+ "		<br />"
	+ "		<label>调配要求：<span id='decoctFlag'>"+(orderInfo.decoctFlag=='1'?'是':'否')+"</span></label>"
	+ "		<label>处方类型：<span id='prescriptionType'>"+(orderInfo.prescriptionType=='1'?'处方药':'非处方药')+"</span></label>"
	+ "		<label>医院处方日期：<span id='hospitalPrescDate'>"+orderInfo.hospitalPrescDate+"</span></label>"
	+ "		<br />"
	+ "		<label>备注：<span id='remark'>"+orderInfo.remark+"</span></label>"
	+ "		"
	+ "		<hr style='height:5px;border:none;border-top:5px ridge green;' />"
	+ "		<label>药味：<span>（单位：g）</span></label>" 
	+ "		<hr style='height:5px;border:none;border-top:5px ridge green;' />";
}
function getOrderDetailsContent(orderdetail){
	return "<li style='width:200px;margin:20px 10px;float:left;'>"+orderdetail.productName+orderdetail.count+orderdetail.unit+"</li>" ;
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
	                       "<td>"+result.productId+"</td>"+
	                       "<td>"+result.productName+"</td>"+
	                       "<td>"+result.productSpec+"</td>"+
	                       "<td>"+result.productBrand+"</td>"+
	                       "<td>"+result.count+"</td>"+
	                       "<td>"+result.price.toFixed(2)+"</td>"+
	                       "<td>"+result.totalPrice.toFixed(2)+"</td>"+
	                       "<td>"+(result!=''&&result.giftFlag=='1'?'是':'否')+"</td>"+
	                      "<td>"+(result!=''&&result.prescriptionType=='1'?'处方药':'非处方药')+"</td>"+
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
	                       "<td>"+result.productId+"</td>"+
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


function batchDeliverFunc(){
	
}

