/**
 * 极速达订单新消息提示
 */
$(document).ready(function() {
	document.getElementById("mainFrame").onload = iframeload;
});
var jsdOrderTimer = null;
function iframeload() {
	var frameUrl = document.getElementById("mainFrame").contentWindow.location.href;
	// 清除消息提示
	msgRemind.clear();
	clearTimeout(jsdOrderTimer);
	// 极速达订单
	if (frameUrl.indexOf("orderInfo.do?method=getInfo") >= 0) {
		jsdNewOrder();
	}
}
var jsdOrderMesg = "";
// 定时查询新订单
function jsdNewOrder() {
	// 取消闪烁
	msgRemind.clear();
	jsdOrderTimer = setTimeout(jsdNewOrder, 300000);
	$.ajax({
		type : "post",
		url : "orderInfo.do?method=newOrder",
		dataType : "json",
		data : {},
		success : function(res) {
			if (res != null && res.count > 0) {
				jsdOrderMesg = "极速达" + res.count + "条";
				// 消息提示
				msgRemind.show();
			}
		}
	});
}

var msgRemind = {
	_step : 0,
	_title : document.title,
	_timer : null,
	// 显示新消息提示
	show : function() {
		msgRemind._timer = setTimeout(function() {
			msgRemind.show();
			msgRemind._step++;
			if (msgRemind._step == 3) {
				msgRemind._step = 1;
			}
			if (msgRemind._step == 1) {
				document.title = msgRemind._title;
			}
			if (msgRemind._step == 2) {
				document.title = msgRemind._title+"【" + jsdOrderMesg + "】";
			}
		}, 700);
		return [ msgRemind._timer, msgRemind._title ];
	},
	// 取消新消息提示
	clear : function() {
		clearTimeout(msgRemind._timer);
		document.title = msgRemind._title;
	}
};