/**
 * 去电沟通受理结果
 */
function getAcceptResults() {
	var types = [ {
		"typeCode" : "101",
		"typeName" : "产品咨询",
		"typeLs" : [ {
			"typeCode" : "10101",
			"typeName" : "产品咨询"
		}, {
			"typeCode" : "10102",
			"typeName" : "需求等级"
		}, {
			"typeCode" : "10103",
			"typeName" : "客户回拨"
		}, {
			"typeCode" : "10104",
			"typeName" : "售后回拨"
		}, {
			"typeCode" : "10105",
			"typeName" : "其他"
		} ]
	},{
		"typeCode" : "102",
		"typeName" : "客户维护",
		"typeLs" : [ {
			"typeCode" : "10201",
			"typeName" : "处方药"
		}, {
			"typeCode" : "10202",
			"typeName" : "OTC"
		}, {
			"typeCode" : "10203",
			"typeName" : "保健品"
		}, {
			"typeCode" : "10204",
			"typeName" : "食品"
		}, {
			"typeCode" : "10205",
			"typeName" : "医疗器械"
		}, {
			"typeCode" : "10206",
			"typeName" : "美妆"
		}, {
			"typeCode" : "10207",
			"typeName" : "日用百货"
		} ]
	}, {
		"typeCode" : "103",
		"typeName" : "无效电话",
		"typeLs" : [{
			"typeCode" : "10301",
			"typeName" : "电话关机"
		},{
			"typeCode" : "10302",
			"typeName" : "无法接通"
		},{
			"typeCode" : "10303",
			"typeName" : "错误号码"
		}]
	} ];
	return types;
}

/**
 * 来电沟通类型
 */
//function getCallerTypes() {
//	var types = [ {
//		"typeCode" : "201",
//		"typeName" : "产品咨询",
//		"typeLs" : [ {
//			"typeCode" : "20101",
//			"typeName" : "处方药"
//		}, {
//			"typeCode" : "20102",
//			"typeName" : "OTC"
//		}, {
//			"typeCode" : "20103",
//			"typeName" : "保健品"
//		}, {
//			"typeCode" : "20104",
//			"typeName" : "食品"
//		}, {
//			"typeCode" : "20105",
//			"typeName" : "医疗器械"
//		}, {
//			"typeCode" : "20106",
//			"typeName" : "美妆"
//		}, {
//			"typeCode" : "20107",
//			"typeName" : "日用百货"
//		} ]
//	}, {
//		"typeCode" : "203",
//		"typeName" : "投诉建议",
//		"typeLs" : [ {
//			"typeCode" : "20301",
//			"typeName" : "产品质量"
//		}, {
//			"typeCode" : "20302",
//			"typeName" : "客服服务态度"
//		}, {
//			"typeCode" : "20303",
//			"typeName" : "物流"
//		}, {
//			"typeCode" : "20304",
//			"typeName" : "页面投诉"
//		} ]
//	}, {
//		"typeCode" : "204",
//		"typeName" : "物流查询",
//		"typeLs" : []
//	}, {
//		"typeCode" : "205",
//		"typeName" : "无效电话",
//		"typeLs" : []
//	}, {
//		"typeCode" : "206",
//		"typeName" : "其他问题",
//		"typeLs" : []
//	} ];
//	return types;
//}
function getCallerTypes() {
	var types = [ {
		"typeCode" : "201",
		"typeName" : "咨询",
		"typeLs" : [ {
			"typeCode" : "20101",
			"typeName" : "产品咨询"			
		}, {
			"typeCode" : "20102",
			"typeName" : "在线咨询"
		},{
			"typeCode" : "20109",
			"typeName" : "新品咨询"
		},
		{
			"typeCode" : "20103",
			"typeName" : "QQ咨询"
		}, {
			"typeCode" : "20104",
			"typeName" : "微信咨询"
		}, {
			"typeCode" : "20105",
			"typeName" : "短信咨询"
		}, {
			"typeCode" : "20106",
			"typeName" : "售后问题咨询"	
		}, {
			"typeCode" : "20107",
			"typeName" : "加盟投诉"	
		}, {
			"typeCode" : "20108",
			"typeName" : "其它"	
		} ]
	}, {
		"typeCode" : "202",
		"typeName" : "修改",
		"typeLs" : [ {
			"typeCode" : "20201",
			"typeName" : "修改客户资料"
		}, {
			"typeCode" : "20202",
			"typeName" : "修改订单信息"
		}, {
			"typeCode" : "20203",
			"typeName" : "修改订单状态"
		}, {
			"typeCode" : "20204",
			"typeName" : "其它"
		} ]
	}, {
		"typeCode" : "203",
		"typeName" : "查询",
		"typeLs" : [ {
			"typeCode" : "20301",
			"typeName" : "账户信息"
		}, {
			"typeCode" : "20302",
			"typeName" : "快递信息"
		}, {
			"typeCode" : "20303",
			"typeName" : "退换货进度"
		}, {
			"typeCode" : "20304",
			"typeName" : "退款进度"
		}, {
			"typeCode" : "20305",
			"typeName" : "补/换发票"
		}, {
			"typeCode" : "20306",
			"typeName" : "其它"
		} ]
	}, {
		"typeCode" : "204",
		"typeName" : "业务受理",
		"typeLs" : [ {
			"typeCode" : "20401",
			"typeName" : "退款申请"
		}, {
			"typeCode" : "20402",
			"typeName" : "补/换发票"
		}, {
			"typeCode" : "20403",
			"typeName" : "拒收"
		}, {
			"typeCode" : "20404",
			"typeName" : "退换补货申请"
		}, {
			"typeCode" : "20405",
			"typeName" : "其它"
		} ]
	}, {
		"typeCode" : "205",
		"typeName" : "退换补货",
		"typeLs" : [ {
			"typeCode" : "20501",
			"typeName" : "商品质量问题"
		}, {
			"typeCode" : "20502",
			"typeName" : "客服操作问题"
		}, {
			"typeCode" : "20503",
			"typeName" : "页面与实物不符"
		}, {
			"typeCode" : "20504",
			"typeName" : "其它"
		} ]
	}, {
		"typeCode" : "206",
		"typeName" : "投诉业务",
		"typeLs" : [ {
			"typeCode" : "20601",
			"typeName" : "服务态度"
		}, {
			"typeCode" : "20602",
			"typeName" : "商品质量问题"
		}, {
			"typeCode" : "20603",
			"typeName" : "商品近效期问题"
		}, {
			"typeCode" : "20604",
			"typeName" : "商品过效期问题"
		}, {
			"typeCode" : "20605",
			"typeName" : "商品价格问题"
		}, {
			"typeCode" : "20606",
			"typeName" : "页面与实物不符"
		}, {
			"typeCode" : "20607",
			"typeName" : "发货超时问题"
		}, {
			"typeCode" : "20608",
			"typeName" : "其它"
		} ]
	}, {
		"typeCode" : "207",
		"typeName" : "建议&感谢",
		"typeLs" : [ {
			"typeCode" : "20701",
			"typeName" : "建议"
		}, {
			"typeCode" : "20702",
			"typeName" : "感谢"
		}]
	} ];
	return types;
}

/**
 * 加载沟通类型，一级分类
 */
function commuType(screenType,sId){
	var ops = "<option value=''>--请选择--</option>";
	var typeArr = [];
	if(screenType=="1"){//去电
		typeArr = getAcceptResults();
	}else{//来电
		typeArr = getCallerTypes();
	}
	for(var i=0;i<typeArr.length;i++){
		ops += "<option value='"+typeArr[i].typeName+"'>"+typeArr[i].typeName+"</option>";
	}
	$(sId).html(ops);
	$(sId).selectpicker("refresh");
}
//二级分类
var secTypes;
//选择沟通类型一级分类，加载二级分类
function selFirstType(screenType,firTypeId,secTypeId,thiTypeId){
	var ty = $(firTypeId).val();
	var ops = "<option value=''>--请选择--</option>";
	if(ty!=null && ty!=""){
		var typeArr = null;
		if("1"==screenType){
			typeArr = getAcceptResults();
		}else{
			typeArr = getCallerTypes();
		}
		for(var i=0;i<typeArr.length;i++){
			if(typeArr[i].typeName==ty){
				secTypes = typeArr[i].typeLs;
				break;
			}
		}
		if(secTypes!=null){
			for(var i=0;i<secTypes.length;i++){
				ops += "<option value='"+secTypes[i].typeName+"'>"+secTypes[i].typeName+"</option>";
			}
		}
	}
	$(secTypeId).html(ops);
	$(secTypeId).selectpicker("refresh");
	$(thiTypeId).html("<option value=''>--请选择--</option>");
	$(thiTypeId).selectpicker("refresh");
}
//选择沟通类型二级分类，加载三级分类
function selSecondType(secTypeId,thiTypeId){
	var ty = $(secTypeId).val();
	var ops = "<option value=''>--请选择--</option>";
	if(ty!=null && ty!=""){
		if(typeof(secTypes)!="undefined" && secTypes.length>0){
			var typeJson = null;
			for(var i=0;i<secTypes.length;i++){
				if(secTypes[i].typeName==ty){
					typeJson = secTypes[i].typeLs;
					break;
				}
			}
			if(typeJson!=null){
				for(var i=0;i<typeJson.length;i++){
					ops += "<option value='"+typeJson[i].typeName+"'>"+typeJson[i].typeName+"</option>";
				}
			}
		}
	}
	$(thiTypeId).html(ops);
	$(thiTypeId).selectpicker("refresh");
}