$(document).ready(function(){
	$('#na_list li a').click(function(){
		$(this).parent().parent().find("li").each(function(){
			if($(this).hasClass("active")){
				$(this).removeClass("active");
			}
		});
		$(this).parent().addClass("active");
		$("#startDate").val("");
		$("#endDate").val("");
		getCommuLs();
	});
	/*来电/去电咨询页切换，来电咨询切换到去电咨询，但添加按钮将禁用，去电切换来电同理。*/
/*	$('#consult_tablist li a').click(function(){
		$(this).parent().parent().find("li").each(function(){
			if($(this).hasClass("active")){
				$(this).removeClass("active");
			}
		});
		$(this).parent().addClass("active");
		var data = {};
		var url="";
		if($(this).text()=="来电咨询"){
			if($("#commuSource").val()=="TEL_OUT"){
				data.custSource="TEL_IN";
				url="commu.do?method=commuMain&tel=13001151490";				
			}				
			doPost(url,data);
		}		
	});*/
	
	$("#proKeywords").blur(function(){
		if ($.trim($(this).val()) == '') {
			var keywordsTip = $.trim($("#keywordsTip").val());
			$(this).val(keywordsTip);
			$(this).addClass('tipText');
		}
	});
	$("#proKeywords").focus(function(){
		var keywordsTip = $.trim($("#keywordsTip").val());
		if ($.trim($(this).val()) == keywordsTip) {
			$(this).val("");
			$(this).removeClass('tipText');
		}
	});
	//是否订购
	$('#isOrderDiv input').on('ifChecked', function(event){
		var val = $(this).val();
		if(val=="1"){//订购
			//隐藏未订购原因
//			$("#noOrderCause").parent().parent().hide();
			$("#noOrderCause").prop("disabled",true);
			$("#noOrderCause").selectpicker('refresh');
			var orderPerm = false;
    		$('#os_tablist li a').each(function(){
    			if($(this).text()=="新增订单"){
    				$(this).parent().addClass("active");
    				orderPerm = true;
    				return false;
    			}
    		});
    		if(orderPerm==true){
    			$("#submitBtn").text("保存并新增订单");
    		}else{
    			$("#submitBtn").text("保存并添加任务");
    		}
//			//不跟踪
//			$("#isTrack2").iCheck("check");
//			$('#isTrackDiv input').iCheck('disable');
		}else if(val=="0"){//不订购
//			$("#noOrderCause").parent().parent().show();
			$("#noOrderCause").prop("disabled",false);
			$("#noOrderCause").selectpicker('refresh');
//			$('#isTrackDiv input').iCheck('enable');
			$("#submitBtn").text("保存并添加任务");
		}
	});
	//是否跟踪
	$('#isTrackDiv input').on('ifChecked', function(event){
		var val = $(this).val();
		if(val=="1"){//跟踪
			//回访日期
//			$("#visitDate").parent().parent().show();
			$("#visitDate").prop("disabled",false);
		}else if(val=="0"){//不跟踪
//			$("#visitDate").parent().parent().hide();
			$("#visitDate").prop("disabled",true);
		}
	});
	
	//首页
	$("#first").click(function() {
		$("#lblCurent").text(1);
		$("#inputCurent").val(1);
		search_product(1); 
	}); 
	//上一页按钮click事件 
	$("#previous").click(function() { 
		var pageIndex = parseInt($("#lblCurent").text());
		if(pageIndex > 1){ 
			pageIndex--; 
		}else if(pageIndex==0){
			pageIndex=1;
		}
		$("#lblCurent").text(pageIndex); 
		$("#inputCurent").val(pageIndex); 
		search_product(pageIndex);
	}); 
	//下一页按钮click事件 
	$("#next").click(function() { 
		var pageCount = parseInt($("#lblPageCount").text());
		var pageIndex = parseInt($("#lblCurent").text());
		if (pageIndex < pageCount) {
			pageIndex++; 
		}
		$("#lblCurent").text(pageIndex);
		$("#inputCurent").val(pageIndex);
		search_product(pageIndex);
	}); 
	//最后一页按钮click事件 
	$("#last").click(function() { 
		var pageCount = parseInt($("#lblPageCount").text()); 
		$("#lblCurent").text(pageCount);
		$("#inputCurent").val(pageCount);
		search_product(pageCount); 
	});
	
	//商品查询页面跳转click事件 
	$("#gopage").click(function() {
		var pageCount = parseInt($("#lblPageCount").text());
		var str = $("#inputCurent").val();
		if (str != null && str.length > 0) {
			str = str.replace(/^(0)+/, '');//去除左侧多余的0
			str = (str.length == 0 ? 0 : str);
			$("#inputCurent").val(str);
			var reg = /^(0|([1-9][0-9]*))$/;
			if (reg.test(str)) {
				var inputCurent = parseInt(str);
				if (inputCurent > 0 && inputCurent <= pageCount) {
					$("#lblCurent").text(inputCurent);
					search_product(inputCurent);
				} else {
					alert("超出页数范围！");
				}
			}
		}
	});
	
	//载入产品分类
	loadCategory("proCategory","depCategory","diseaseCategory");
});

//沟通记录跳页
function gotoPage(pageno){
	var data = {};
	data.pageno = pageno;
	data.pageSize = $("#curPageSize").val();
	$('#na_list li').each(function(){
		if($(this).hasClass("active")){
			data.acceptResult=$.trim($(this).text());
		}
	});
	data.tel = $("#mobile").val();
	data.startDate = $("#startDate").val();
	data.endDate = $("#endDate").val();
	data.custSource = $("#commuSource").val();
	data.isTask=$("#isNewTask").val();
	var url= $("#commuDiv input[name=commuAction]").val()+"?method=getCommuLs";
	if(url!=""){
		$.ajax({
			 type: "POST",
             url: url,
             dataType: "html",
             data: data,
             success: function(data){
            	 $("#commLs").html(data);
             },
             error:function(){
            	 $("#commLs").html("请求处理失败！");
             }
		});
	}else{
		$("#commLs").html("");
	}
}
function gotoPageSize(pageSize){
	$("#curPageSize").val(pageSize);
	gotoPage(1);
}
//查询沟通记录
function getCommuLs(){
	gotoPage(1);
}

//打开沟通记录添加框
function openAdd(){
	var isNewTask = $("#isNewTask").val();
	if($("#commuSource").val()=="TEL_OUT" && isNewTask!="1"){//去电，且不属于今日任务
		//类型 0：来电， 1：去电
		commuType("1","#aResult");
	}else{
		commuType("0","#aResult");
	}
	$("#commuId").val("");
	//不可查看历史记录
	$("#hisLi").addClass("disabled");
	//沟通记录可编辑
	$("#commuLi").removeClass("disabled");
	$("#commuLi a").tab('show');
	
	var tel = $.trim($("#mobile").val());
	var sou = $("#commuSource").val();
	if(sou!="TEL_OUT" && sou!="XQ"){
		var telTip = $("#telTip").val().trim();
		if(tel==telTip){
			alert("手机号为空！");
			return false;
		}
	}
	if(tel.length==0){
		alert("手机号为空！");
		return false;
	}
	
	//是否新用户，默认不选
	$("input[name='isNewUser']").iCheck('uncheck');
	
	$("#remark_lb").text("备注");
	
	//当前类型
	var ty = $('#na_list li.active').find("a").text();
	$('#aResult').selectpicker("val",ty);//触发change事件
	/* 重置数据 */
	//沟通类型可选择
	$('#aResult').prop('disabled',false);
    $('#aResult').selectpicker('refresh');
	$('#sType').prop('disabled',false);
    $('#sType').selectpicker('refresh');
    $('#thirdType').prop('disabled',false);
    $('#thirdType').selectpicker('refresh');
	
	$("#proKeywords").val("");//产品关键字
	$("#proKeywords").blur();
	$("#selKeywords").empty().hide();
	
	$('#proCategory').selectpicker("val","");
	$('#proCategory').prop('disabled',false);
	$('#proCategory').selectpicker('refresh');
	$('#depCategory').prop('disabled',false);
    $('#depCategory').selectpicker('refresh');
	$('#diseaseCategory').prop('disabled',false);
    $('#diseaseCategory').selectpicker('refresh');
	
	$('#noOrderCause').selectpicker("val","");
	$("#visitDate").val("");//回访日期
	$("#com_remark").val("");//备注
	
	//默认显示沟通记录
	$('#commuTab a:first').tab("show");
	$("#commuModal .modal-dialog").css("width","530px");
	//显示添加窗口
	$("#commuModal").modal("show");
}

//打开沟通记录修改框
function openEdit(comId){
	$("#commuId").val(comId);
	$("#selKeywords").empty().hide();
	$("#track_tb tbody").empty();
	//是否新用户，默认不选
	$("input[name='isNewUser']").iCheck('uncheck');
	
	var data = {};
	data.commuId=comId;
	data.custSource = $("#commuSource").val();
	data.isTask=$("#isNewTask").val();
	var url= $("#commuDiv input[name=commuAction]").val()+"?method=getCommuDetail";
	$.ajax({
		type: "POST",
        url: url,
        async : false,
        dataType: "json",
        data: data,
        success: function(res){
        	if(res.mesg=="error"){
        		alert("查询沟通记录异常！");
        	}else if(res.mesg!=null && res.mesg.length>0){
        		alert(res.mesg);
        	}else{
        		var commu = res.commu;
        		if(commu.isPlaceOrder=="1" || commu.isEnable!="1"){
        			//显示历史记录
        			$("#hisLi a").tab("show");
        			$("#commuModal .modal-dialog").css("width","800px");
        			//沟通记录不可编辑
        			$("#commuLi").addClass("disabled");
             	}else{
             		//沟通记录可编辑
             		$("#commuLi").removeClass("disabled");
             		$("#commuLi a").tab("show");
             		$("#commuModal .modal-dialog").css("width","530px");
             	}
	        	//当前沟通类型
	        	if(commu.acceptResult!=null && commu.acceptResult.length>0){
	        		$('#aResult').html("<option value='"+commu.acceptResult+"'>"+commu.acceptResult+"</option>");
	        		$('#aResult').selectpicker("refresh");
	        		$('#aResult').selectpicker("val",commu.acceptResult);
	        	}else{
	        		$('#aResult').selectpicker("val","");
	        	}
	        	if(commu.secondType!=null && commu.secondType.length>0){
	        		$('#sType').html("<option value='"+commu.secondType+"'>"+commu.secondType+"</option>");
	        		$('#sType').selectpicker("refresh");
	        	}else{
	        		$('#sType').selectpicker("val","");
	        	}
	        	if(commu.thirdType!=null && commu.thirdType.length>0){
	        		$('#thirdType').html("<option value='"+commu.thirdType+"'>"+commu.thirdType+"</option>");
	        		$('#thirdType').selectpicker("refresh");
	        	}else{
	        		$('#thirdType').selectpicker("val","");
	        	}
	        	//沟通类型不可修改
	        	$('#aResult').prop('disabled',true);
	            $('#aResult').selectpicker('refresh');
	        	$('#sType').prop('disabled',true);
	            $('#sType').selectpicker('refresh');
	            $('#thirdType').prop('disabled',true);
	            $('#thirdType').selectpicker('refresh');
	        	if(commu.acceptResult=="咨询" ||commu.acceptResult=="产品咨询" || commu.acceptResult=="客户维护"){
	        		//可查看历史记录
	        		$("#hisLi").removeClass("disabled");
	        		$("#remark_lb").text("跟踪信息");
	        		var wArr = null;
	        		var keywords = commu.proKeywords;
	        		if(keywords!=null && keywords.length>0){
	        			var index = keywords.indexOf("{;}");
	        			if(index>=0){
	        				var arr = keywords.split("{;}");
	        				var words1 = arr[0];
	        				if(words1.length>0){
	        					wArr = words1.split("{,}");
	        					var idArr = null;
	        					if(commu.proMealIds!=null && commu.proMealIds.length>0){
	        						idArr = commu.proMealIds.split(",");
	        					}
	        					var skuArr = null;
	        					if(commu.proSkus!=null && commu.proSkus.length>0){
	        						skuArr = commu.proSkus.split(",");
	        					}
	        					var w1 = "",w2 = "",proDiv = "";
	        					for(var i=0;i<wArr.length;i++){
	        						if(wArr[i]!=null && wArr[i].length>0){
	        							w1 = wArr[i];
	        							w2 = w1.length>8?(w1.substring(0,7)+"..."):w1;
	        							proDiv += "<div class='div_inline'>"+
	        									  "<input type='hidden' name='itemName' value='"+w1+"' /></span>";
	        							if(idArr!=null && idArr.length>i){
	        								proDiv += "<input type='hidden' name='itemId' value='"+idArr[i]+"' /></span>";
	        							}
	        							proDiv += "<input type='hidden' name='itemSkuId' value='' /></span>";
	        							if(skuArr!=null && skuArr.length>i){
	        								proDiv += "<input type='hidden' name='itemSku' value='"+skuArr[i]+"' /></span>";
	        							}
		        						proDiv += "<span class='span_text' title='["+idArr[i]+"],"+w1+"'>"+w2+"</span>" +
			        							  "<span class='span_del' title='删除'>&times;</span>" +
			        							  "</div>";
	        						}
	        					}
	        					proDiv += "<div class='clearfix'></div>";
	        					$("#selKeywords").empty().append(proDiv);
	        					$("#selKeywords").show();
	        					//关键词删除事件
	        					$("#selKeywords .span_del").click(delKeywords);
	        				}
	        				//手动输入关键词
	        				$("#proKeywords").val(arr[1]);
	        			}
	        		}
		            $("#proKeywords").removeClass('tipText');
		            $("#proKeywords").blur();
		            
		            var fc="",sc="";
		            //显示指定分类
		            if(commu.proCategory!=null && commu.proCategory!=""){
		            	$('#proCategory').selectpicker("val",commu.proCategory);
		            	fc=$('#proCategory').val();
		            	if(fc!=null && fc.length>0 && commu.depCategory!=null && commu.depCategory!=""){
		            		$('#depCategory').selectpicker("val",commu.depCategory);
		            		sc=$('#depCategory').val();
		            		if(sc!=null && sc.length>0 && commu.diseaseCategory!=null && commu.diseaseCategory!=""){
		            			$('#diseaseCategory').selectpicker("val",commu.diseaseCategory);
		            		}
		            	}
		            }

		            //修改沟通记录，产品分类不可修改
		            $('#proCategory').prop('disabled',true);
		            $('#depCategory').prop('disabled',true);
		            $('#diseaseCategory').prop('disabled',true);
	        	    $('#proCategory').selectpicker('refresh');
	        	    $('#depCategory').selectpicker('refresh');
	        	    $('#diseaseCategory').selectpicker('refresh');

	        	    //清空跟踪信息
		        	$('#noOrderCause').selectpicker("val","");
		        	$("#visitDate").val("");
		        	$("#com_remark").val("");
		        	var tr = "";
		        	var trackLs = res.trackLs;
		        	if(trackLs!=null && trackLs.length>0){
		        		for(var i=0;i<trackLs.length;i++){
		        			tr += "<tr>";
		        			tr += "<td>"+trackLs[i].consultDate+"</td>";
		        			
		        			var wor1 = trackLs[i].proKeywords;
		        			if(wor1!=null && wor1.length>0){
		        				wor1 = wor1.replace(/\{;\}/g, "");
		        				wor1 = wor1.replace(/\{,\}/g, "; ");
		        			}
		        			var wor2 = wor1;
		        			if(wor1.length>25){
		        				wor2 = wor1.substring(0,25)+"...";
		        				tr += "<td>" +
		        						"<span class='word_det1' style='display:none;'>"+wor1+"</span>" +
		        						"<span class='word_det2'>" + wor2 +"</span>" +
		        						"<a class='a_detail' href='javascript:void(0);'>[详情]</a>" +
		        					  "</td>";
		        			}else{
		        				tr += "<td>" + wor1 + "</td>";
		        			}
		        			
		        			var isOrder = "";
		        			if(trackLs[i].isOrder=="0"){
		        				isOrder = "否";
		        			}else if(trackLs[i].isOrder=="1"){
		        				isOrder = "是";
		        			}
		        			tr += "<td>"+isOrder+"</td>";
		        			tr += "<td>"+trackLs[i].noOrderCause+"</td>";
		        			tr += "<td>"+trackLs[i].visitDate+"</td>";
		        			tr += "<td>"+trackLs[i].createUserName+"</td>";
		        			tr += "<td>"+trackLs[i].createUser+"</td>";
		        			tr += "<td>"+trackLs[i].trackInfo+"</td>";
		        			tr += "</tr>";
		        		}
		        		$("#track_tb tbody").append(tr);
		        		//查看详细信息事件
		        		$("#track_tb tbody tr").find(".a_detail").click(function(){
		        			if($(this).text()=="[详情]"){
		        				$(this).parent().find(".word_det2").hide();
		        				$(this).parent().find(".word_det1").show();
		        				$(this).text("[收起]");
		        			}else{
		        				$(this).parent().find(".word_det1").hide();
		        				$(this).parent().find(".word_det2").show();
		        				$(this).text("[详情]");
		        			}
		        		});
		        	}
	        	}else{
	        		//不可查看历史记录
	        		$("#hisLi").addClass("disabled");
	        		$("#remark_lb").text("备注");
	        		$("#com_remark").val(commu.remark);
	        	}
	        	$("#commuModal").modal("show");
        	}
        },
        error:function(){
        	alert("查询沟通记录失败！");
        }
	});
}

//删除关键词
function delKeywords(e){
	var prosDiv = $(e.target).parent().parent();
	var mid = $(e.target).parent().find("input[name=itemId]").val();
	$(e.target).parent().remove();
	$("#product_body tr").each(function(){
		var pid = $(this).find("td:eq(1)").text();
		if(pid==mid){
			$(this).find("td:eq(0)").find("input").prop('checked',false);
			return false;
		}
	});
	$("#relate_meal_body tr").each(function(){
		var pid = $(this).find("td:eq(1)").text();
		if(pid==mid){
			$(this).find("td:eq(0)").find("input").prop('checked',false);
			return false;
		}
	});
	var comId = $("#commuId").val();
	if(comId==null || $.trim(comId).length==0){//新增
		//显示第一个商品的分类
		firstProCat(prosDiv);
	}
}

//显示第一个商品的分类
function firstProCat(prosDiv){
	var firstPro = prosDiv.find(".div_inline:first");
	var fName = "",sName="",tName="";
	if(firstPro!=null && firstPro.length>0){
		//商品ID
		var firstId = firstPro.find("input[name=itemId]").val();
		if(firstId!=null && firstId.length>0){
			var params = {mealId:firstId, pn:1};
			var proData = getProductLs(params);
			if(proData!=null && proData.prodList!=null && proData.prodList.length>0){
				var product = proData.prodList[0];
				//显示已有商品分类，不可修改
				if(product.firstCatName!=null && product.firstCatName!=""){
					$('#proCategory').selectpicker("val",product.firstCatName);
					fName = $('#proCategory').val();
					if(product.secondCatName!=null && product.secondCatName!=""){
						$('#depCategory').selectpicker("val",product.secondCatName);
						sName = $('#depCategory').val();
						if(product.thirdCatName!=null && product.thirdCatName!=""){
							$('#diseaseCategory').selectpicker("val",product.thirdCatName);
							tName = $('#diseaseCategory').val();
						}
					}
				}
			}
		}
	}
	if(fName!=null && fName.length>0){
		$('#proCategory').prop('disabled',true);
	}else{
		$('#proCategory').selectpicker("val","");
		$('#proCategory').prop('disabled',false);
	}
	if(sName!=null && sName.length>0){
		$('#depCategory').prop('disabled',true);
	}else{
		$('#depCategory').selectpicker("val","");
		$('#depCategory').prop('disabled',false);
	}
	if(tName!=null && tName.length>0){
		$('#diseaseCategory').prop('disabled',true);
	}else{
		$('#diseaseCategory').selectpicker("val","");
		$('#diseaseCategory').prop('disabled',false);
	}
	$('#proCategory').selectpicker('refresh');
	$('#depCategory').selectpicker('refresh');
	$('#diseaseCategory').selectpicker('refresh');
}

//选择沟通类型
function changeType(obj){
	var isNewTask = $("#isNewTask").val();
	var sou = $("#commuSource").val();
	if("TEL_OUT"==sou && isNewTask!="1"){//去电，且不属于今日任务
		selFirstType("1","#aResult",'#sType','#thirdType');
	}else{//来电、需求订单沟通分类
		selFirstType("0","#aResult",'#sType','#thirdType');
	}
	if($("#aResult").val()=="咨询" || $("#aResult").val()=="产品咨询" || $("#aResult").val()=="客户维护" || $("#aResult").val()==""){
		$("#commuModal .modal-body .tr_inline").show();
		$("#selKeywords").show();//显示已选关键词
		//默认不订购
		$("#isOrder2").iCheck('check');
		$("#isTrack1").iCheck('check');//默认跟踪
		$("#submitBtn").text("保存并添加任务");
	}else{
		$("#commuModal .modal-body .tr_inline").hide();
		$("#selKeywords").hide();//隐藏已选关键词
		$("#remarkDiv").show();//备注
		$("#commuType").show();//沟通类型
		$("#submitBtn").text("保存");
	}
}
//选择二级沟通类型
function changeSecType(){
	selSecondType('#sType','#thirdType');
}

//展开商品查询页
function openProLs(){
	var selProsEle = $("#selKeywords .div_inline").clone(true);
	$("#selPros .div_inline").remove();
	$("#selPros .clearfix").before(selProsEle);
	search_product(1);
}

/**
* 查询商品套餐并显示
*/
function search_product(pageNo){
	var t = document.getElementById("product_body"); //获取展示数据的表格 
	while (t.rows.length != 0) { 
		t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除
	}
	//隐藏父子套餐
	$("#relate_meal").hide();
	$("#relate_meal_body").html("");
	
	if(pageNo==null){
		pageNo=1;
	}
	var prodId = $("#prodId").val().trim();
	var prodNo = $("#prodNo").val().trim();
	var prodNm = $("#prodNm").val().trim();
	var params = {mealId:prodId, mainSku:prodNo, mealName:prodNm, pn:pageNo};
	var data = getProductLs(params);
	if(data!=null && data.prodList!=null && data.prodList.length>0){
		var pageCount = data.pageInfo.pageCount;
		$("#lblPageCount").text(data.pageInfo.pageCount); 
		$("#lblToatl").text(data.pageInfo.rowCount);
		if (pageCount == 0) {
			$("#lblCurent").text(0);
			$("#inputCurent").val(0);
		}else {
			$("#lblCurent").text(pageNo); //当前页 
			$("#inputCurent").val(pageNo); //当前页 
		}
		
		var proTr = "";
		$.each(data.prodList,function(i,result){
			var slcedSku = "";
			var slcedSkuId = "";
			var slcedSpecName = "";
			var slcedPrice = "";
			var slcedStock = "";
			//商品是否选中
			var exist = false;
			$("#selPros .div_inline").each(function(){
				var idEle = $(this).find("input[name='itemId']");
				if(idEle.val()==result.mealId){
					exist = true;
					slcedSku = $(this).find("input[name='itemSku']").val();
					var skus = querySkus({mealId:result.mealId});
					if(skus!=null && skus.length>0){
						for(var i=0;i<skus.length;i++){
							if(skus[i].sku==slcedSku){
								slcedSkuId = skus[i].skuId;
								slcedSpecName = skus[i].specName;
								slcedPrice = skus[i].price;
								slcedStock = skus[i].stock;
								break;
							}
						}
					}
					return false;
				}
			});
			proTr += "<tr><td align='center'>";
			if(exist==true){
				proTr += "<input type='checkbox' name='checkGroup' checked='checked'/>";
			}else{
				proTr += "<input type='checkbox' name='checkGroup'/>";
			}
			proTr += "</td>" +
					"<td>"+result.mealId+"</td>" +
					"<td>"+result.mealName+"</td>"+
					"<td class='mainSku' title='点击选择商品' style='cursor:pointer;color:#428bca;'>" +
					"<input type='hidden' name='skuId' value='"+slcedSkuId+"' />"+
					"<a href='javascript:void(0);'>"+(slcedSku!=""?slcedSku:"请选择商品")+"</a>"+
					"</td>" +
					"<td>"+slcedSpecName+"</td>" +
					"<td>"+slcedPrice+"</td>" +
					"<td>"+slcedStock+"</td>" +
					"<td>";
			if(null != result.prescriptionType && result.prescriptionType == 0){
				proTr += "<input type='hidden' value='0' />";
			}else if(null != result.prescriptionType && result.prescriptionType == 1){
				proTr += "<input type='hidden' value='1' />非处方药(甲类)";
			}else if(null != result.prescriptionType && result.prescriptionType == 2){
				proTr += "<input type='hidden' value='2' />非处方药(乙类)";
			}else if(null != result.prescriptionType && result.prescriptionType == 3){
				proTr += "<input type='hidden' value='3' />处方药";
			}else if(null != result.prescriptionType && result.prescriptionType == 4){
				proTr += "<input type='hidden' value='4' />保健食品";
			}	
			proTr += "</td></tr>";
		});
		$("#product_body").append(proTr);
		//重新绑定事件，复选框选中商品
		$("#product_body input[name='checkGroup']").click(function(){
			proCheck_click($(this));
		});
		
		//重新绑定事件,查询sku并选择
		$("#product_body .mainSku").click(function(){
			//查询sku
			$("#opsku").val("0");
			search_sku($(this).parent());
		});
		$("#product_body .mainSku").click();
	}else{
		$("#lblPageCount").text("0"); 
		$("#lblToatl").text("0"); 
		$("#lblCurent").text("0"); 
		$("#inputCurent").text("0"); 
	}
}

//点击复选框，选择商品
function proCheck_click(thisobj){
	var thistr = thisobj.parent("td").parent("tr");
	var mid = thistr.find("td:eq(1)").text();
	var isCheck = thisobj.prop("checked");
	if(isCheck==true){
		var mname = thistr.find("td:eq(2)").text();
		var mname2 = (mname!=null&&mname.length>9)?(mname.substring(0,8)+"..."):mname;
		var skuId = thistr.find("td:eq(3)").find("input").val();
		var sku = thistr.find("td:eq(3)").text();
		if(skuId==null || skuId==""){
			//选择商品，查询sku
			$("#opsku").val("1");
			search_sku(thistr);
			sku = thistr.find("td:eq(3)").text();
			skuId = thistr.find("td:eq(3)").find("input").val();
			if(skuId==null || skuId==""){
				thisobj.prop("checked",false);
				return;
			}
		}
		var isExist = false;
		$("#selPros .div_inline").each(function(){
			var idEle = $(this).find("input[name='itemId']");
			if(idEle.val()==mid){
				isExist = true;
				return false;
			}
		});
		if(!isExist){//不存在，添加
			//显示选中商品
			var proDiv = "<div class='div_inline'>" +
					"<input type='hidden' name='itemId' value='"+mid+"' />" +
					"<input type='hidden' name='itemName' value='"+mname+"' />" +
					"<input type='hidden' name='itemSkuId' value='"+skuId+"' />" +
					"<input type='hidden' name='itemSku' value='"+sku+"' />" +
					"<span class='span_text' title='["+mid+"],"+mname+"'>"+mname2+"</span>" +
					"<span class='span_del' title='删除'>&times;</span>" +
					"</div>";
			$("#selPros .clearfix").before(proDiv);
			//关键词删除事件
			$("#selPros .span_del:last").click(delKeywords);
		}
		//选中主商品套餐，加载父子套餐
		if(thistr.parent().attr('id')=='product_body'){
			loadRelateMeal(mid);
		}
	}else{
		$("#selPros .div_inline").each(function(){
			var idEle = $(this).find("input[name='itemId']");
			if(idEle.val()==mid){
				//取消
				$(this).remove();
				return false;
			}
		});
//		//原套餐取消时，隐藏父子套餐
//		if(thistr.parent().attr('id')=='product_body'){
//			$("#relate_meal").hide();
//			$("#relate_meal_body").html("");
//		}
	}
}

//查询sku
function search_sku(thistr){
	var mid = thistr.find("td:eq(1)").text();
	var sku = thistr.find("td:eq(3)").text();
	//获取sku商品列表
	var skus = querySkus({mealId:mid});
	if(skus!=null && skus.length>0){
		if(skus.length==1){
			thistr.find("td:eq(3)").find("input").val(skus[0].skuId);
			thistr.find("td:eq(3)").find("a").text(skus[0].sku);
			thistr.find("td:eq(4)").text(skus[0].specName);
			thistr.find("td:eq(5)").text(skus[0].price);
			thistr.find("td:eq(6)").text(skus[0].stock);
			//修改选中项的sku
			$("#selPros .div_inline").each(function(){
				var idEle = $(this).find("input[name='itemId']");
				if(idEle.val()==mid){
					$(this).find("input[name='itemSku']").val(skus[0].sku);
					$(this).find("input[name='itemSkuId']").val(skus[0].skuId);
					return false;
				}
			});
		}else{
			var trs = "";
			for(var i=0;i<skus.length;i++){
				trs += "<tr>";
				if(skus[i].sku==sku){
					trs += "<td><input type='radio' name='skuRadio' class='skuRadio' checked='checked'/></td>";
				}else{
					trs += "<td><input type='radio' name='skuRadio' class='skuRadio'/></td>";
				}
				trs += "<td>"+skus[i].mealId+"</td>" +
				"<td><input type='hidden' value='"+skus[i].skuId+"' /><span>"+skus[i].sku+"</span></td>" +
				"<td>"+skus[i].specName+"</td>" +
				"<td>"+skus[i].price+"</td>" +
				"<td>"+skus[i].stock+"</td>" +
				"</tr>";
			}
			$("#sku_body").html(trs);
			$("#skuModal").modal("show");
		}
	}else{
		alert("未找到此商品的相关信息");
	}
}

/**
 * 获取商品套餐数据
 */
function getProductLs(params){
	var resData = null;
	var url = $("#commuDiv input[name=outAction]").val()+"?method=getProductList";
	$.ajax({
		type: "POST",
		async: false,
		dataType: "json",
		data: params,
		url:url,
		success: function(data){
			resData = data;
		},
		error: function(){
//			alert("加载数据失败"); 
		}
	});
	return resData;
}

//选择关键词确定
function selPros() {
	var selProsEle = $("#selPros").children(":gt(0)").clone(true);
	$("#selKeywords").empty().append(selProsEle).show();
	$('#proModal').modal('hide');
	var comId = $("#commuId").val();
	if(comId==null || $.trim(comId).length==0){//新增
		//显示第一个商品的分类
		firstProCat($("#selKeywords"));
	}
}

//获取sku数据
function querySkus(params){
	var skus = null;
	var url = $("#commuDiv input[name=outAction]").val()+"?method=getSkuList";
	$.ajax({
		type: "POST",
		async: false,
		dataType: "json",//返回json格式的数据
		data: params,
		url:url,
		success: function(res){
			if(res!=null && res.skuList!=null && res.skuList.length>0){
				skus = res.skuList;
			}
		}
	});
	return skus;
}

//选择sku，确定
function slcedSku(){
	var rad = $("#sku_body .skuRadio:checked");
	if(rad!=null && rad.length>0){
		var tr = rad.parent().parent();
		var mid = tr.find("td:eq(1)").text();
		var skuId = tr.find("td:eq(2)").find("input").val();
		var sku = tr.find("td:eq(2)").find("span").text();
		var specName = tr.find("td:eq(3)").text();
		var price = tr.find("td:eq(4)").text();
		var stock = tr.find("td:eq(5)").text();
		var ischecked = false;
		$("#product_body tr").each(function(){
			var pid = $(this).find("td:eq(1)").text();
			if(pid==mid){
				ischecked = $(this).find("td:eq(0)").find("input").prop("checked");
				$(this).find("td:eq(3)").find("input").val(skuId);
				$(this).find("td:eq(3)").find("a").text(sku);
				$(this).find("td:eq(4)").text(specName);
				$(this).find("td:eq(5)").text(price);
				$(this).find("td:eq(6)").text(stock);
				return false;
			}
		});
		if(ischecked==true){
			//修改选中项的sku
			$("#selPros .div_inline").each(function(){
				var idEle = $(this).find("input[name='itemId']");
				if(idEle.val()==mid){
					$(this).find("input[name='itemSku']").val(sku);
					$(this).find("input[name='itemSkuId']").val(skuId);
					return false;
				}
			});
		}
		$("#skuModal").modal("hide");
		var opsku = $("#opsku").val();
		if(opsku=="1"){//选择套餐时，查询sku
			$("#product_body tr").each(function(){
				var pid = $(this).find("td:eq(1)").text();
				if(pid==mid){
					$(this).find("td:eq(0) input[name='checkGroup']").click();
					return false;
				}
			});
		}
	}else{
		alert("您还未选择商品！");
	}
}

//加载父子套餐
function loadRelateMeal(mid){
	var url = $("#commuDiv input[name=outAction]").val()+"?method=getSuitList";
	$.ajax({
		type: "POST",
		async: false,
		dataType: "json",//返回json格式的数据
		data: {mealId:mid},
		url:url,
		success: function(res){
			if(res!=null && res.relateMealList!=null && res.relateMealList.length>0){
				var relateMeal= res.relateMealList;
				var trs = "";
				for(var i=0;i<relateMeal.length;i++){
					var meal = relateMeal[i];
					//套餐是否被选中
					var isExistPro = false;
					$("#selPros .div_inline").each(function(){
						var idEle = $(this).find("input[name='itemId']");
						if(idEle.val()==meal.mealId){
							isExistPro = true;
							return false;
						}
					});
					trs += "<tr>" +
					"<td align='center'>";
					if(isExistPro){
						trs += "<input type='checkbox' name='mealCheck' checked='checked'/>" ;
					}else{
						trs += "<input type='checkbox' name='mealCheck'/>" ;
					}
					trs+="</td>" +
					"<td><input type='hidden' name='oldMealId' value='"+mid+"'>"+meal.mealId+"</td>" +
					"<td>"+meal.mealName+"</td>" +
					"<td class='mainSku'><input type='hidden' value='"+meal.mainProductId+"' />"+meal.mainSku+"</td>" +
					"<td>"+meal.mealNormName+"</td>" +
					"<td>"+meal.mealPrice+"</td>" +
					"<td>"+meal.minStock+"</td>" +
					"<td>";
					if(null != meal.prescriptionType && meal.prescriptionType == 0){
						trs += "<input type='hidden' value='0' />";
					}else if(null != meal.prescriptionType && meal.prescriptionType == 1){
						trs += "<input type='hidden' value='1' />非处方药(甲类)";
					}else if(null != meal.prescriptionType && meal.prescriptionType == 2){
						trs += "<input type='hidden' value='2' />非处方药(乙类)";
					}else if(null != meal.prescriptionType && meal.prescriptionType == 3){
						trs += "<input type='hidden' value='3' />处方药";
					}else if(null != meal.prescriptionType && meal.prescriptionType == 4){
						trs += "<input type='hidden' value='4' />保健食品";
					}	
					trs += "</td></tr>";
				}
				$("#relate_meal_body").html(trs);
				$("#relate_meal").show();
				$("#relate_meal_body input[name='mealCheck']").click(function(){
					var tr = $(this).parent().parent();
					var oldMealId = tr.find("td:eq(1) input[name='oldMealId']").val();
					var che = $(this).prop("checked");
					if(che==true){
						//选中父子套餐，取消原套餐
						$("#product_body tr").each(function(){
							var pid =$(this).find("td:eq(1)").text();
							if(pid==oldMealId){
								$(this).find("td:eq(0) input[name='checkGroup']").prop("checked",false);
								//删除关键词
								$("#selPros .div_inline").each(function(){
									var idEle = $(this).find("input[name='itemId']");
									if(idEle.val()==pid){
										//取消
										$(this).remove();
										return false;
									}
								});
								return false;
							}
						});
					}
					//关键词的显示与隐藏
					proCheck_click($(this));
				});
			}else{
				$("#relate_meal_body").html("");
				$("#relate_meal").hide();
			}
		}
	});
}

//保存沟通记录
function saveCommu(){
	var commuId = $.trim($("#commuId").val());
	var ty = $("#aResult").val();
	//新增
	if(commuId==null || commuId==""){
		if(ty==null || ty==""){
			alert("请选择沟通类型！");
			return;
		}
	}
	var params = {};
	params.tel = $.trim($("#mobile").val());
	params.userName = $.trim($("#custName").val());
	params.custSource = $.trim($("#commuSource").val());
	params.id = $.trim($("#commuId").val());
	params.acceptResult = ty;
	params.custServCode = $("#custServCode").val();
	var sType = $("#sType").val();
	if(sType!=null && sType!=""){
		params.secondType = $("#sType").val();
	}
	var thirdType = $("#thirdType").val();
	if(thirdType!=null && thirdType!=""){
		params.thirdType = $("#thirdType").val();
	}
	
	var orderParam = "";
	if(ty=="咨询" || ty=="产品咨询" || ty=="客户维护"){
		//已选产品关键词
		var words1 = "";
		var proMealIds = "";
		var proSkus = "";
		var orderParamArr = new Array();
      	$("#selKeywords .div_inline").each(function(){
      		var mid = $(this).find("input[name='itemId']").val();
      		var mname = $(this).find("input[name='itemName']").val();
      		var sku = $(this).find("input[name='itemSku']").val();
      		var skuId = $(this).find("input[name='itemSkuId']").val();
      		if(mid!=null && mid.length>0){
      			if(skuId==null || skuId==""){
          			//获取sku商品列表
        			var skus = querySkus({mealId:mid});
        			if(skus!=null && skus.length>0){
        				for(var i=0;i<skus.length;i++){
        					if(skus[i].sku==sku){
        						skuId = skus[i].skuId;
        						break;
        					}
        				}
        			}
          		}
      			
      			proMealIds += $.trim(mid)+",";
      			words1 += $.trim(mname)+"{,}";
      			proSkus += $.trim(sku)+",";
      			orderParamArr.push($.trim(mid)+","+skuId);
      		}
      	});
      	orderParam = orderParamArr.join("|");
      	
      	//手动输入关键词
      	var words2 = $.trim($("#proKeywords").val());
      	var keywordsTip = $.trim($("#keywordsTip").val());
      	if(words2==keywordsTip){
      		words2 = "";
      	}
		params.proKeywords = words1 + "{;}" + words2;
		if(params.proKeywords.length>400 || proSkus.length>400){
			alert("产品关键词过多！");
			return;
		}
		if(proMealIds.length>0){
			params.proMealIds = proMealIds;
		}
		if(proSkus.length>0){
			params.proSkus = proSkus;
		}
		//新增
		if(commuId==null || commuId.length==0){
			var fc = $("#proCategory").val();
			if(fc==null || fc.length==0){
				alert("请选择品类类别！");
				return;
			}
			var sc = $("#depCategory").val();
			if(sc==null || sc.length==0){
				alert("请选科组类别！");
				return;
			}
			var tc = $("#diseaseCategory").val();
	/*		if(tc==null || tc.length==0){
				alert("请选择病种类别！");
				return;
			}*/
		}
		params.proCategory = $("#proCategory").val();
		params.depCategory = $("#depCategory").val();
		params.diseaseCategory = $("#diseaseCategory").val();
		
		var isOrder = $("input[name=isOrder]:checked").val();
		params.isOrder = isOrder;
		if(isOrder=="0"){//不订购，保存并添加任务
			if($("#noOrderCause").val()==null || $("#noOrderCause").val()==""){
				alert("请选择未订购原因！");
				return;
			}
			params.noOrderCause = $("#noOrderCause").val();
		}
		
		var isTrack = $("input[name=isTrack]:checked").val();
		params.isTrack = isTrack;
		if(isTrack=="1"){//回访
			if($("#visitDate").val()==null || $("#visitDate").val()==""){
				alert("请选择回访日期！");
				return;
			}
			params.visitDate = $("#visitDate").val();
		}
		params.trackInfo = $.trim($("#com_remark").val());
		
		var isNewUser = $("input[name=isNewUser]:checked").val();
		if(isNewUser==null){
			alert("请选择是否新用户！");
			return;
		}
		params.isNewUser = isNewUser;
	}else{
		params.remark = $.trim($("#com_remark").val());
	}
	
	$('#commuModal').modal('hide');
	var url = $("#commuDiv input[name=commuAction]").val()+"?method=saveCallCommu";
	$.ajax({
		type: "POST",
        url: url,
        dataType: "json",
        data: params,
        success: function(result){
        	$("#curCommuId").val("");
        	if(result!=null && result.mesg=="success"){
        		//当前沟通记录ID
        		$("#curCommuId").val(result.commuId);
        		
        		var orderPerm = false;
        		$('#os_tablist li a').each(function(){
        			if($(this).text()=="新增订单"){
        				$(this).parent().addClass("active");
        				orderPerm = true;
        				return false;
        			}
        		});
        		//订购，跳转至新增订单页面
        		if((ty=="咨询" || ty=="产品咨询" || ty=="客户维护") && params.isOrder=="1" && orderPerm==true){
        			var orderData={};
        			orderData.tel = $("#mobile").val();
        			orderData.type= "addOrder";
        			orderData.dateString = $("#dateString").val();
        			orderData.products = orderParam;
        			//延时300毫秒加载下单页面，避免因当前页面被覆盖，无法完全关闭modal对话框造成的黑屏
        			setTimeout(function(){
        				addOrderSku(orderData);
        			}, 300);
        		}else{
        			getCommuLs();
        		}
        	}else{
        		alert("数据保存失败！");
        		getCommuLs();
        	}
        },
        error: function(){
        	$("#curCommuId").val("");
        	alert("保存失败！");
        }
	});
}

//下订单
function addOrderSku(orderData){
	var addUrl = $("#commuDiv input[name=outAction]").val()+"?method=addOrderSku";
	$.ajax({
		type: "POST",
		url: addUrl,
		dataType: "html",
		data: orderData,
		success: function(res){
			$('#os_tablist li').each(function(){
				if($(this).hasClass("active")){
					$(this).removeClass("active");
					return false;
				}
			});
			$('#os_tablist li a').each(function(){
				if($(this).text()=="新增订单"){
					$(this).parent().addClass("active");
					return false;
				}
			});
			$("#tabDiv").html(res);
		},
		error: function(){
			alert("进入下单页面失败！");
			getCommuLs();
		}
	});
}

//载入产品分类
function loadCategory(firstId,secondId,thirdId){
	var firstEle = $("#"+firstId);
	var secondEle = $("#"+secondId);
	var thirdEle = $("#"+thirdId);
	//获取分类数据
	firCateArr = getCategoryJson("1","0");
	var options = "<option value=''>--请选择--</option>";
	if(firCateArr!=null && firCateArr.length>0){
		for(var i=0;i<firCateArr.length;i++){
			options += "<option value='"+firCateArr[i].className+"'>"+firCateArr[i].className+"</option>";
		}
	}
	firstEle.html(options);
	firstEle.selectpicker('refresh');
	
	//绑定change事件，加载二级分类
	firstEle.bind('change',function(){
		var secOptions = "<option value=''>--请选择--</option>";
		var fca = $(this).val();
		if(fca!=null && $.trim(fca).length>0){
			var pId = "";
			if(firCateArr!=null && firCateArr.length>0){
				for(var i=0;i<firCateArr.length;i++){
					if(fca == firCateArr[i].className){
						pId = firCateArr[i].id;
						break;
					}
				}
			}
			secCateArr = getCategoryJson("2",pId);
			if(secCateArr!=null && secCateArr.length>0){
				for(var i=0;i<secCateArr.length;i++){
					secOptions += "<option value='"+secCateArr[i].className+"'>"+secCateArr[i].className+"</option>";
				}
			}
		}
		secondEle.html(secOptions);
		secondEle.selectpicker('val','');
		secondEle.selectpicker('refresh');
	});
	
	//绑定change事件，加载三级分类
	secondEle.bind('change',function(){
		var thirdOptions = "<option value=''>--请选择--</option>";
		var tca = $(this).val();
		if(tca!=null && $.trim(tca).length>0){
			var pId = "";
			if(secCateArr!=null && secCateArr.length>0){
				for(var i=0;i<secCateArr.length;i++){
					if(tca == secCateArr[i].className){
						pId = secCateArr[i].id;
						break;
					}
				}
			}
			var thirdCate = getCategoryJson("3",pId);
			if(thirdCate!=null && thirdCate.length>0){
				for(var i=0;i<thirdCate.length;i++){
					thirdOptions += "<option value='"+thirdCate[i].className+"'>"+thirdCate[i].className+"</option>";
				}
			}
		}
		thirdEle.html(thirdOptions);
		thirdEle.selectpicker('refresh');
	});
}
//获取产品分类
function getCategoryJson(level,parentId){
	var caArr = new Array();
	//所有
	if(typeof(allCategory)=="undefined" || allCategory.length==0){
		allCategory=getAllCategory();
	}
	if(typeof(allCategory)!="undefined" && allCategory.length>0){
		if(level=="1"){
			parentId="0";
		}
		for(var i=0;i<allCategory.length;i++){
			if(allCategory[i].classLevel==level && allCategory[i].parentId==parentId 
					&& !/(_de)$/.test(allCategory[i].className)){
				caArr.push(allCategory[i]);
			}
		}
	}
	return caArr;
}
function getAllCategory(){
	var ca = null;
	var url= $("#commuDiv input[name=commuAction]").val()+"?method=getAllCatagory";
	$.ajax({
		type : 'post',
		url : url,
		async : false,
		dataType : "json",
		data : {},
		success : function(res) {
			ca = res;
		},
		error : function() {
			alert("获取分类失败！");
		}
	});
	return ca;
}
