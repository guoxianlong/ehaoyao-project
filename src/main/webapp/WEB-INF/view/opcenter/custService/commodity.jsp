<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">

<style type="text/css">
#commodity_table thead tr th{font-size:14px;background-color: #f9f9f9;}
.sear_btn{margin-left: 20px; padding: 0 12px; height: 30px;}
</style>
<script type="text/javascript">
function gotoPage(pageno){
	var pName = $("#pName").val().trim();
	var pageSize = $("#curPageSize").val().trim();
	$.ajax({
		type: "POST",
        url: "<%=request.getContextPath()%>/${actionName}?method=getGoodsLs",
        dataType: "html",
        data: {pageno:pageno,pageSize:pageSize,productName:pName},
        success: function(data){
       		$("#cardDiv").parent().html(data);
        }
	});	
}
function gotoPageSize(pageSize){
	$("#curPageSize").val(pageSize);
	gotoPage($("#curPage").val());
}

//购买商品,新增订单
function buyProduct(secondID){
	$('#os_tablist li').each(function(){
		if($(this).hasClass("active")){
			$(this).removeClass("active");
		}
	});
	var tr = secondID.parentNode.parentNode;  
    var tds = tr.cells;
    var mainProductId = tds[1].getElementsByTagName('input')[0].value;
    if(mainProductId == null || mainProductId == ''){
		$('#os_tablist li:eq(4)').addClass("active");
    	alert("该商品没有产品ID,请先设置产品ID");
    	return;
    }
    $('#os_tablist li:eq(4)').addClass("active");
    buyOne(tds[2].innerHTML,tds[4].innerHTML,tds[3].innerHTML,tds[6].innerHTML,tds[8].innerHTML,mainProductId);
}
function buyOne(secondId,productCode,productName,specifications,price,mainProductId){
	var tel = $("#mobile").val();
	var dateString = '${dateString}';
	$.ajax({
		type : "POST",
		dataType: "html",//返回json格式的数据
		data : {
			mealId: secondId,
			mainSku: productCode,
			mealName: productName,
			mealNormName: specifications,
			price: price,
			productId:mainProductId,
			dateString:dateString
		},
		url:"<%=request.getContextPath()%>/${actionName}?method=addOrder&tel="+tel,
        success: function(data){
        	$("#cardDiv").parent().html(data);
        }
	});	
}
</script>
<div id="cardDiv" style="min-width: 1000px;">
	<form action="" class="form-horizontal" role="form" method="post">
		<input type="hidden" id="curPage" name="pageno" value="${pageno}"/>
		<input type="hidden" id="curPageSize" name="pageSize" value="${pageSize}"/>
		<div class="tr_inline">
			<div class="td_item item_title">主商品名称：</div>
			<div class="td_item" style="width: 200px">
				<input id="pName" name="productName" type="text" value="${pvo.productName}" class="form-control item_input"/>
			</div>
			<button type="button" onclick="gotoPage(1)" class="btn btn-info sear_btn">定位</button>
		</div>
		<div style="clear:both;"></div>
	</form>
	<table id="commodity_table" class="table table-bordered" style="margin-top: 5px;">
		<thead>
			<tr>
				<th>序号</th>
				<th>操作</th>
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
		<tbody id="tb_body">
			<c:forEach items="${pList}" varStatus="status" var="product">
			<tr>
				<td>${status.count}</td>
				<td>
					<input type="hidden" name="mainProductId" value="${product.mainProductId }" />
					<button type="button" class="btn btn-success" onclick="buyProduct(this)" style="padding: 0 12px; height: 25px;">购买</button>
				</td>
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
					<c:when test="${product.isCf=='5'}">非处方药(甲类)</c:when>
					<c:when test="${product.isCf=='6'}">非处方药(乙类)</c:when>
					<c:when test="${product.isCf=='7'}">处方药</c:when>
					<c:when test="${product.isCf=='8'}">保健食品</c:when>
					<c:otherwise>其它</c:otherwise>
				</c:choose></td>
				<td>${product.frontType}</td>
			</tr>
			</c:forEach>
			<c:if test="${pList==null || fn:length(pList)<=0}">
				<tr>
					<td colspan="11" style="height:100px;text-align: center;vertical-align: middle;font-size:14px;font-weight: bold;">暂无数据！</td>
				</tr>
			</c:if>
		</tbody>
	</table>
	<ul class="pager">
		<%@ include file="/WEB-INF/view/common/page.jspf" %>
	</ul>
</div>
