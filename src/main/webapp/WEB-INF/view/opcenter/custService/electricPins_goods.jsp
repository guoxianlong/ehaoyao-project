<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>电销商品</title>
    
    <!-- 加载CSS样式文件 -->
    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-theme.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-select.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
    
     <!-- 加载javascript文件 -->
    <script src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap-select.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap/respond.min.js"></script>
    
<style type="text/css">
#goods_table thead tr th{font-size:14px;background-color: #f9f9f9;}
input.item_input {height: 30px;}
.div_cont{width:150px;}
.item_title {text-align: right;width: 80px;padding-top: 5px;margin-left: 15px;}
.sear_btn{margin-left: 20px; padding: 0 12px; height: 30px;}
.sear_btn2{padding: 0 12px; height: 30px;}
.select_set + div button.selectpicker{height:30px;padding-top:0;padding-bottom:0;}
</style>

<script type="text/javascript">

$(document).ready(function(){
	$('.selectpicker').selectpicker();
});

function gotoPage(pageno){
	$("[name=pageno]").val(pageno);
	$("#dataForm").attr("action","<%=request.getContextPath()%>/telGoods.do?method=getTelGoodsLs");
	$("#dataForm").submit();
}
function gotoPageSize(pageSize){
	$("[name=pageSize]").val(pageSize);
	$("#dataForm").attr("action","<%=request.getContextPath()%>/telGoods.do?method=getTelGoodsLs");
	$("#dataForm").submit();
}
//全选
function selectAll(){
	var check = document.getElementById("selAll").checked;
	var items = document.getElementsByName("selItem");
	for(var i=0;i<items.length;i++){
		items[i].checked=check;
	}
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

//删除商品
function delGoods(){
	var ids = onSel();
	if(ids!=null && ids!=""){
		$("#dataForm").attr("action","<%=request.getContextPath()%>/telGoods.do?method=delGoods");
		$("#secondID").val(ids);
		$("#dataForm").submit();
	}else{
		alert("请选择商品！");
	}
}

//主推设置
function updateStatus(status){
	var ids = onSel();
	if(ids!=null && ids!=""){
		$("#dataForm").attr("action","<%=request.getContextPath()%>/telGoods.do?method=updateStatus");
		$("#secondID").val(ids);
		$("#goodsSta").val(status);
		$("#dataForm").submit();
	}else{
		alert("请选择商品！");
	}
}

//导入商品
function importPro(){
	if(confirm("该操作将立即从官网导入商品，您确定吗？")){
		$.ajax({
			type: "POST",
            url: "<%=request.getContextPath()%>/telGoods.do?method=importProduct",
            dataType: "text",
            success: function(data){
           		alert("导入商品完成！");
            },
            error:function(){
           		alert("导入商品失败！");
            }
		});
	}
}
</script>
</head>
<body style="padding-top: 15px;">
    <div class="right_box">
		<form id="dataForm" name="dataForm" action="" method="post">
			<input type="hidden" name="pageno" value="${pageno}"/>
			<input type="hidden" name="pageSize" value="${pageSize}"/>
			<input type="hidden" id="secondID" name="secondID" value=""/>
			<input type="hidden" id="goodsSta" name="goodsSta" value=""/>
			<div class="right_inline">
				<div class="right_input item_title">副本名称：</div>
				<div class="right_input div_cont">
					<input id="secondName" name="secondName" value="${product.secondName}" type="text" class="form-control item_input"/>
				</div>
				<div class="right_input item_title">主商品编码：</div>
				<div class="right_input div_cont">
					<input id="productCode" name="productCode" value="${product.productCode}" type="text" class="form-control item_input"/>
				</div>
				<div class="right_input item_title">主商品名称：</div>
				<div class="right_input div_cont">
					<input id="productName" name="productName" value="${product.productName}" type="text" class="form-control item_input"/>
				</div>
				<div style="clear:both;"></div>
			</div>
			<div class="right_inline">
				<div class="right_input item_title">主推状态：</div>
				<div class="right_input div_cont">
					<select id="status" name="status" class="selectpicker form-control">
						<option value="">全部</option>
						<option value="1" ${product.status=="1"?'selected="selected"':''}>主推</option>
						<option value="0" ${product.status=="0"?'selected="selected"':''}>电销</option>
					</select>
				</div>
				<div class="right_input item_title">处方类型：</div>
				<div class="right_input div_cont">
					<select id="isCf" name="isCf" class="selectpicker form-control">
						<option value="">全部</option>
						<option value="1" ${product.isCf=="1"?'selected="selected"':''}>非药品</option>
						<option value="2" ${product.isCf=="2"?'selected="selected"':''}>非处方药</option>
						<option value="3" ${product.isCf=="3"?'selected="selected"':''}>单轨处方药</option>
						<option value="4" ${product.isCf=="4"?'selected="selected"':''}>双轨处方药</option>
					</select>
				</div>
				<button type="button" onclick="gotoPage(1)" class="btn btn-info sear_btn">查询</button>
		        <div style="float:right;margin-right: 10px;">
		<!--             <button type="button" class="btn btn-info" onclick="delGoods()">删除</button> -->
		            <button type="button" class="btn btn-info sear_btn2" onclick="updateStatus('1')">主推</button>
		            <button type="button" class="btn btn-info sear_btn2" onclick="updateStatus('0')">取消主推</button>
		            <button type="button" class="btn btn-info sear_btn2" onclick="importPro();">导入</button>
		        </div>
				<div style="clear:both;"></div>
			</div>
		</form>
		
        <table id="goods_table" border="0" class="table table-bordered">
			<thead>
			<tr>
				<th><input id="selAll" onclick="selectAll()" type="checkbox"/> 全选 </th>
				<th>状态</th>
				<th>副本ID</th>
				<th>副本名</th>
				<th>主商品编码</th>
				<th>主商品名称</th>
				<th>主商品规格</th>
				<th>市场价</th>
				<th>销售价</th>
				<th>处方类型</th>
				<th>前端分类</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${pList}" varStatus="status" var="product">
				<tr>
					<td><input type="checkbox" name="selItem" value="${product.secondID}"/>&nbsp;${status.count}</td>
					<td>${product.status=="1"?"主推":"电销"}</td>
					<td>${product.secondID}</td>
					<td>${product.secondName}</td>
					<td>${product.productCode}</td>
					<td>${product.productName}</td>
					<td>${product.specifications}</td>
					<td style="text-align: right;">${product.marketPrice}</td>
					<td style="text-align: right;">${product.price}</td>
					<td><c:choose>
						<c:when test="${product.isCf=='1'}">非药品</c:when>
						<c:when test="${product.isCf=='2'}">非处方药</c:when>
						<c:when test="${product.isCf=='3'}">单轨处方药</c:when>
						<c:when test="${product.isCf=='4'}">双轨处方药</c:when>
					</c:choose></td>
					<td>${product.frontType}</td>
				</tr>
				</c:forEach>
				<c:if test="${pList==null || fn:length(pList)<=0}">
					<td colspan="11" style="height:100px;text-align: center;vertical-align: middle;font-size:14px;font-weight: bold;">暂无数据！</td>
				</c:if>
			</tbody>
		</table>
		<ul class="pager">
			<%@ include file="/WEB-INF/view/common/page.jspf" %>
		</ul>
	</div>
	<div style="clear:both;"></div>
</body>
</html>