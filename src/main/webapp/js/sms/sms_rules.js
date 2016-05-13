var allChannel;
$(document).ready(function() {
	$('.selectpicker').selectpicker();
	$('.bootstrap-select').css("margin-bottom", 0);

	$(".channel").click(function() {
		// 选择渠道
		selChannel($(this));
	});

	allChannel = $("#unuseChannel").clone(true);

	// 选择状态查询
	$("#condition,#q_channel").change(function() {
		goPage(1);
	});

	// 第一页按钮click事件
	$("#first").click(function() {
		goPage(1);
	});
	// 上一页按钮click事件
	$("#previous").click(function() {
		var pageIndex = $("#lblCurent").text();
		if (pageIndex != 1) {
			pageIndex--;
		}
		goPage(pageIndex);
	});
	// 下一页按钮click事件
	$("#next").click(function() {
		var pageCount = parseInt($("#lblPageCount").text());
		var pageIndex = $("#lblCurent").text();
		if (pageIndex != pageCount) {
			pageIndex++;
		}
		goPage(pageIndex);
	});
	// 最后一页按钮click事件
	$("#last").click(function() {
		var pageCount = parseInt($("#lblPageCount").text());
		goPage(pageCount);
	});

	$('.switch').on('switch-change', function(e, data) {
		var value = data.value;
		var ruleId = $(this).find(".ruleId").val();
		$.ajax({
			url : "../rule/onOrOffRule.do",
			type : "POST",
			dataType : "text",
			data : {
				id : ruleId,
				enable : value
			},
			error : function(msg) {
				alert("error!");
			}
		});
	});

	$("#insertBtn :button").click(function() {
		var value = $("#sms_content").val();
		$("#sms_content").focus();
		if ($(this).attr("id") == "btn1") {
			$("#sms_content").val(value + "[userName]");
		} else if ($(this).attr("id") == "btn2") {
			$("#sms_content").val(value + "[lastName]");
		} else if ($(this).attr("id") == "btn3") {
			$("#sms_content").val(value + "[orderNumber]");
		} else if ($(this).attr("id") == "btn4") {
			$("#sms_content").val(value + "[expressName]");
		} else if ($(this).attr("id") == "btn5") {
			$("#sms_content").val(value + "[expressNumber]");
		} else if ($(this).attr("id") == "btn6") {
			$("#sms_content").val(value + "[money]");
		}
	});

	/**
	 * 清算字数
	 */
	$("#wordCnt").click(function() {
		computeCount();
	});

	/**
	 * 保存事件触发验证
	 */
	$("#submitBtn").click(function() {
		//计算字数
		computeCount();
		//内容
		var content = $.trim($("#sms_content").val());
		if (content == "") {
			alert("短信内容不能为空!");
			return;
		}
		// 获取已选渠道
		var orderFlag = "";
		$("#useChannel .channel").each(function() {
			if (orderFlag.length > 0) {
				orderFlag += ",";
			}
			orderFlag += $(this).children('.c_text').attr('title');
		});
		if(orderFlag.length <= 0) {
			if(!confirm("尚未选择渠道，您确定要保存吗？")){
				return;
			}
		}
		var id = $("#rid").val();
		var ruleName = $.trim($("#ruleName").val());
		var orderType = $("#orderType").val();
		
		var cod = "0";
		if ($("#cod").is(":checked")) {
			cod = "1";
		}
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var outTimeFlag = $("#outTimeFlag").val();
		var wordCount = $("#word").val();
		var url = '';
		if (id != null && id != "") {// 修改
			url = "../rule/updateRuleByid.do";
		} else {
			url = "../rule/addRule.do";
		}
		$.ajax({
			url : url,
			type : 'POST',
			dataType : 'json',
			data : {
				ruleId : id,
				ruleName : ruleName,
				orderFlag : orderFlag,
				startHour : startTime,
				orderStatus : orderType,
				content : content,
				endHour : endTime,
				cashDelivery : cod,
				outTimeFlag : outTimeFlag,
				wordCount : wordCount
			},
			success : function(data) {
				if (data["error"] != null && data["error"] != "") {
					alert(data["error"]);
				} else {
					window.location.href = "../rule/setup.do";
				}
			}
		});
	});
	
	$("#run").click(function(){
		alert("run");
		$.ajax({
			url : "../rule/run.do",
			type : "POST",
			dataType : "text",
			success : function(msg) {
				alert("获取短信信息成功！");
			}
		});
	});
	$("#send").click(function(){
		$.ajax({
			url : "../rule/send.do",
			type : "POST",
			dataType : "text",
			success : function(msg) {
				alert("发送成功！");
			},
			error:function(msg) {
				alert("发送出错！");
			}
		});
	});

});

// 页面跳转
function goPage(pageNo) {
	var orderStatus = $("#condition").val();
	$("#orderStatus").val(orderStatus);
	var channel = $("#q_channel").val();
	$("#con_channel").val(channel);
	$("#pageNo").val(pageNo);
	$("#queryForm").submit();
}

// 选择渠道
function selChannel(obj) {
	var item = obj.clone(true);
	if (obj.parent().attr("id") == 'unuseChannel') {
		var del = "<span class='c_icon' title='删除'>&nbsp;&times;</span>";
		$(item).append(del);
		$("#useChannel").append(item);
	} else {
		$(item).find('.c_icon').remove();
		$("#unuseChannel").append(item);
	}
	obj.remove();
}

// 渠道隐藏或显示
function viewChannel() {
	if ($("#unuseChannel").css("display") == "none") {
		$("#unuseChannel").show();
		$("#sel_a").html("隐藏渠道...");
	} else {
		$("#unuseChannel").hide();
		$("#sel_a").html("展开选择渠道...");
	}
}

/**
 * 新增规则事件
 */
function onAdd() {
	$("#useChannel .channel").remove();
	/*$("#unuseChannel").html(allChannel.children().clone(true)).show();更改为下一句。*/
	$("#unuseChannel").show();
	$("#sel_a").html("隐藏渠道...");
	$("#rid").val("");
	$("#ruleName").val("");
	$("#cod").prop('checked', false);
	$("#sms_content").val("");
	$("#word").attr('value', "");
	
	$('#orderType').selectpicker('val', '1');
	$('#startTime').selectpicker('val', '8');
	$('#endTime').selectpicker('val', '20');
	$('#outTimeFlag').selectpicker('val', '0');
}

// 修改规则
function onEdit(rid) {
	$("#rid").val(rid);
	$("#useChannel .channel").remove();
	/*$("#unuseChannel").html(allChannel.children().clone(true)).hide();更改为下一句。*/
	$("#unuseChannel").hide();
	$("#sel_a").html("展开选择渠道...");

	$.ajax({
		url : "../rule/getRuleByid.do",
		type : "POST",
		async : false,
		dataType : "json",
		data : {id : rid},
		success : function(json) {
			$("#ruleName").val(json.ruleName);
			$("#sms_content").val(json.content);
			$("#word").attr('value', json.wordCount);
			if (json.cashDelivery == "1") {
				$("#cod").prop("checked", true);
			} else {
				$("#cod").prop("checked", false);
			}

			var orderFlag = json.orderFlag;
			$('#orderFlag').val(orderFlag);
			if (orderFlag != null && orderFlag.length > 0) {
				var arr = orderFlag.split(",");
				for (var i = 0; i < arr.length; i++) {
					var channel = arr[i];
					$("#unuseChannel .channel").each(function() {
						if ($(this).children('.c_text').attr('title') == channel) {
							$(this).click();
						}
					});
				}
			}
			$('#orderType').selectpicker('val', json.orderStatus);
			$('#startTime').selectpicker('val', json.startHour);
			$('#endTime').selectpicker('val', json.endHour);
			$('#outTimeFlag').selectpicker('val', json.outTimeFlag);
		}
	});
}

/**
 * 删除事件触发
 */
function onDel(rid) {
	var _val = $("#condition").find('option:selected').val();
	if (confirm("您确定要删除这条数据吗?")) {
		$.ajax({
			url : "../rule/delRuleByid.do",
			type : "POST",
			dataType : "text",
			data : {
				id : rid
			},
			success : function(msg) {
				$('#condition').selectpicker('val', _val);// 触发change事件
			}
		});
	}
}

// 计算字数
function computeCount() {
	var str = $("#sms_content").val();
	var reg = /\[(.*?)\]/gi;
	var tmp = str.match(reg);
	var count = 0;
	if (tmp) {
		for (var i = 0; i < tmp.length; i++) {
			str = str.replace(tmp[i], "");
			if (tmp[i] == "[userName]") {
				count = count + 3;
			} else if (tmp[i] == "[lastName]") {
				count = count + 1;
			} else if (tmp[i] == "[orderNumber]") {
				count = count + 15;
			} else if (tmp[i] == "[expressName]") {
				count = count + 6;
			} else if (tmp[i] == "[expressNumber]") {
				count = count + 15;
			} else if (tmp[i] == "[money]") {
				count = count + 3;
			}
		}
	}
	count = count + str.length;
	$("#word").attr("value", count);// 填充内容
}

