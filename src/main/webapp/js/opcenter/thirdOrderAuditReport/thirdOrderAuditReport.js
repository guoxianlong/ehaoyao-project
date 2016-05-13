/**
 *	@author				longshanw
 *	@date				2016-01-13
 *	@description		三方平台订单审核脚本处理文件
 */


//页面元素加载完毕后执行
$(document).ready(function() {
	
	$("#orderDetailDiv").hide();
	
	//查询
	$('#queryBtn').click(function() {
		gotoPage(1);
	});
	
	//导出
	$('#exportBtn').click(function() {
		exportFunct();
	});
	
	
	$("#detailPageTbDiv").mCustomScrollbar({
		setWidth:1000,
		setHeight:150,
		axis:"yx",
		scrollButtons:{enable:true},
		//theme:"3d",
		//scrollbarPosition:"outside"
		theme:"minimal-dark"
     });
	
	//为th增加点击事件
	thSortClick();
	
	//为td增加触发事件 排除oprStatusTd样式列的表格
	tdClick();
	
	//隐藏手机号中间四位
	phoneHideFunc();
});


/**
 * 隐藏手机号中间四位
 */
function phoneHideFunc(){
	$(".phoneCls").each(function(i){
		var phone = $(this).text();
		console.log(phone);
		if(phone!=""&&phone!=null&&phone.length>4){
			$(this).text(phone.substring(0,3)+"****"+phone.substring(8,11));
		}
	});
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
	
	//为td增加触发事件 排除oprStatusTd样式列的表格
	var TdArr = $(".orderInfoTr");
	for(var i=0;i<TdArr.length;i++){
		TdArr[i].onclick = function(event){
			if(event.currentTarget.cells.length>2){
				var orderNumber = event.currentTarget.cells[1].innerHTML;
				var orderFlag = event.currentTarget.cells[2].innerHTML;
//				console.log("orderNumber："+orderNumber+",orderFlag="+orderFlag);
				getMainOrderDetails(orderNumber,orderFlag);
			}
		};
	}
}

/**
 * 查询订单
 * @param pageNo
 */
function gotoPage(pageNo){
	var pageSize = $("#curPageSize").val().trim();
	var orderBy = $("#orderBy").val().trim();
	var sort = $("#sort").val().trim();
	var startDate = $("#startDate").val().trim();
	var endDate = $("#endDate").val().trim();
	var url = $("#conditionDiv input[name=actionName]").val()+"?method=showOrderInfos";
	url += ("&orderBy="+orderBy+"&sort="+sort+"&pageno="+pageNo+"&pageSize="+pageSize);
	$("#orderOprForm").attr("action",url);
	$("#orderOprForm").submit();
	/*$.ajax({
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
 * 导出Excel
 */
function exportFunct(){
	orderOprForm.action = $("#conditionDiv input[name=actionName]").val()+"?method=exportExcel";
	orderOprForm.submit();
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
	getOrderDetails(orderNumber,orderFlag,"orderDetailsTable");
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




