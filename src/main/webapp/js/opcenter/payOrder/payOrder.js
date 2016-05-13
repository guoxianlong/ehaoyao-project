/**
 *	@author				xiaoyumeng
 *	@date				2016-04-20
 *	@description		订单查询脚本处理文件
 */


//页面元素加载完毕后执行
$(document).ready(function() {
	
	$("#orderDetailDiv").hide();
	
	//查询
	$('#queryBtn').click(function() {
		gotoPage(1);
	});
	
	
	/**
	 * 获取渠道
	 */
    $.ajax({
		type: "POST",
        url: $("#conditionDiv input[name=actionName]").val()+"?method=getOrderFlag",
        dataType: "text",
        success: function(data){
        	if(data!=''){
        		var options_str = "";
        		var orderFlagHidden = $("#orderFlagHidden").val();
        		$.each(JSON.parse(data), function(i, result){
        				if(orderFlagHidden==result.orderFlag){
        					options_str += "<option value=\"" + result.orderFlag + "\" selected>" + result.orderFlag + "</option>";
        				}else{
        					options_str += "<option value=\"" + result.orderFlag + "\" >" + result.orderFlag + "</option>";
        				}	  
                });
        		// console.log(options_str);
                     $("#orderFlagSelect").append(options_str);
                     $('#orderFlagSelect').selectpicker('refresh');
			}
		},   
		error : function(){
				$.messager.alert("错误提示", "获取渠道标识失败,请联系管理员！");
    		}
    	});
	
	
	//为th增加点击事件
	thClick();
	
	//为td增加触发事件 排除oprStatusTd样式列的表格
	tdClick();
	

});



/**
 * 为th增加点击事件,全选/取消
 */
function thClick(){

	//点击th排序
	thSortClick();
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
 * 查询订单
 * @param pageNo
 */
function gotoPage(pageNo){
	var pageSize = $("#curPageSize").val().trim();
	var orderBy = $("#orderBy").val().trim();
	var sort = $("#sort").val().trim();
	var url = $("#conditionDiv input[name=actionName]").val()+"?method=showOrderPay";
	url += ("&orderBy="+orderBy+"&sort="+sort+"&pageno="+pageNo+"&pageSize="+pageSize);
	$("#orderOprForm").attr("action",url);
	$("#orderOprForm").submit();
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
        			"<td>"+result.unit+"</td>"+
          			"<td>"+result.price.toFixed(2)+"</td>"+
        			"<td>"+result.count+"</td>"+
        			"<td>"+result.totalPrice.toFixed(2)+"</td>"+
        			"<td>"+result.discountAmount+"</td>"+
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


