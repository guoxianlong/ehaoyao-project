<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style type="text/css">
	table thead tr th{font-size:14px;background-color: #f9f9f9;text-align: center;}
</style>
<table class="table table-bordered" style="margin-top: 5px;">
	<thead>
		<tr>
			<th>客服姓名</th>
			<th>订单总额</th>
			<th>订单数</th>
			<th>客单价</th>
			<th>当日咨询量</th>
			<th>咨询转换率</th>
			<th>新增会员咨询量</th>
			<th>新增会员成单量</th>
			<th>新增会员转换率</th>
			<th>复购量</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${statisticsList!=null&&fn:length(statisticsList)>0}">
			<c:if test="${pageno==1}">
				<tr>
					<!-- 初始化值 -->
					<c:set value="${fn:replace(statisticSum.orderTotalPrice,',','')}" var="orderTotalPriceSum"></c:set>
					<c:set value="${statisticSum.orderAmount}" var="orderAmountSum"></c:set>
					<c:set value="${statisticSum.consultAmount}" var="consultAmountSum"></c:set>
					<c:set value="${statisticSum.newUserOrder}" var="newUserOrderSum"></c:set>
					<c:set value="${statisticSum.newUserConsult}" var="newUserConsultSum"></c:set>
					<c:set  var="transferSum" />
					<c:set  var="newTransferSum" />
					<c:set  var="perOrderPriceSum" />
					<!-- 计算合计咨询转换率 -->
					<c:choose>
						<c:when test="${consultAmountSum != null && consultAmountSum ne '0' && consultAmountSum ne 0}">
							<fmt:formatNumber  value="${fn:replace(100*orderAmountSum,',','')/fn:replace(consultAmountSum,',','')}" minFractionDigits="2" maxFractionDigits="2" var="transferSum"/>
						</c:when>
						<c:otherwise>
							<fmt:formatNumber  value="0" minFractionDigits="2" maxFractionDigits="2" var="transferSum"/>
						</c:otherwise>
					</c:choose>
					<!-- 计算合计新增会员咨询转换率 -->
					<c:choose>
						<c:when test="${newUserConsultSum != null && newUserConsultSum ne '0' && newUserConsultSum ne 0}">
							<fmt:formatNumber  value="${fn:replace(100*newUserOrderSum,',','')/fn:replace(newUserConsultSum,',','')}" minFractionDigits="2" maxFractionDigits="2" var="newTransferSum"/>
						</c:when>
						<c:otherwise>
							<fmt:formatNumber  value="0" minFractionDigits="2" maxFractionDigits="2" var="newTransferSum"/>
						</c:otherwise>
					</c:choose>
					<!-- 计算合计客单价 -->
					<c:choose>
						<c:when test="${orderAmountSum != null && orderAmountSum ne '0' && orderAmountSum ne 0}">
							<fmt:formatNumber  value="${fn:replace(orderTotalPriceSum,',','')/fn:replace(orderAmountSum,',','')}" minFractionDigits="2" maxFractionDigits="2" var="perOrderPriceSum"/>
						</c:when>
						<c:otherwise>
							<fmt:formatNumber  value="0" minFractionDigits="2" maxFractionDigits="2" var="perOrderPriceSum"/>
						</c:otherwise>
					</c:choose>
					<!-- 计算合计复购 -->
					<c:set value="${orderAmountSum-newUserOrderSum}" var="rePurchaseSum" />
					<td style="color:#CC3300">合计</td>
					<td>
						<fmt:formatNumber  value="${orderTotalPriceSum}" minFractionDigits="2" maxFractionDigits="2" var="orderTotalPriceSum"/>
						${orderTotalPriceSum}￥
					</td>
					<td>${orderAmountSum}</td>
					<td>${perOrderPriceSum}￥</td>
					<td>${consultAmountSum}</td>
					<td>${transferSum}%</td>
					<td>${newUserConsultSum}</td>
					<td>${newUserOrderSum}</td>
					<td>${newTransferSum}%</td>
					<td>${rePurchaseSum}</td>
				</tr>
			</c:if>
			
			<c:forEach items="${statisticsList}" varStatus="status" var="statistics">
				<!-- 计算咨询转换率 -->
				<c:choose>
					<c:when test="${statistics.consultAmount != null && statistics.consultAmount ne '0' && statistics.consultAmount ne 0}">
						<fmt:formatNumber  value="${fn:replace(100*statistics.orderAmount,',','')/fn:replace(statistics.consultAmount,',','')}" minFractionDigits="2" maxFractionDigits="2" var="transfer"/>
					</c:when>
					<c:otherwise>
						<fmt:formatNumber  value="0" minFractionDigits="2" maxFractionDigits="2" var="transfer"/>
					</c:otherwise>
				</c:choose>
				<!-- 计算新增会员咨询转换率 -->
				<c:choose>
					<c:when test="${statistics.newUserConsult != null && statistics.newUserConsult ne '0' && statistics.newUserConsult ne 0}">
						<fmt:formatNumber  value="${fn:replace(100*statistics.newUserOrder,',','')/fn:replace(statistics.newUserConsult,',','')}" minFractionDigits="2" maxFractionDigits="2" var="newUserTransfer"/>
					</c:when>
					<c:otherwise>
						<fmt:formatNumber  value="0" minFractionDigits="2" maxFractionDigits="2" var="newUserTransfer"/>
					</c:otherwise>
				</c:choose>
				<!-- 计算客单价 -->
				<c:choose>
					<c:when test="${statistics.orderAmount != null && statistics.orderAmount ne '0' && statistics.orderAmount ne 0}">
						<fmt:formatNumber  value="${fn:replace(statistics.orderTotalPrice,',','')/fn:replace(statistics.orderAmount,',','')}" minFractionDigits="2" maxFractionDigits="2" var="perOrderPrice"/>
					</c:when>
					<c:otherwise>
						<fmt:formatNumber  value="0" minFractionDigits="2" maxFractionDigits="2" var="perOrderPrice"/>
					</c:otherwise>
				</c:choose>
				<tr>
					<td style="color:#CC3300">${statistics.name}</td>
					<td>
						<fmt:formatNumber  value="${fn:replace(statistics.orderTotalPrice,',','')}" minFractionDigits="2" maxFractionDigits="2"/>￥
					</td>
					<td>${statistics.orderAmount}</td>
					<td>
						<fmt:formatNumber  value="${fn:replace(perOrderPrice,',','')}" minFractionDigits="2" maxFractionDigits="2"/>￥
					</td>
					<td>${statistics.consultAmount}</td>
					<td>${transfer}%</td>
					<td>${statistics.newUserConsult}</td>
					<td>${statistics.newUserOrder}</td>
					<td>${newUserTransfer}%</td>
					<td>${statistics.orderAmount-statistics.newUserOrder}</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${statisticsList==null || fn:length(statisticsList)<=0}">
			<td colspan="10" style="height:100px;text-align: center;vertical-align: middle;font-size:14px;font-weight: bold;">暂无数据！</td>
		</c:if>
	</tbody>
</table>

<!-- 合计 -->
<input type="hidden"  name="orderTotalPriceSum" value="${fn:replace(orderTotalPriceSum,',','')}"/>
<input type="hidden"  name="orderAmountSum" value="${fn:replace(orderAmountSum,',','')}"/>
<input type="hidden"  name="perOrderPriceSum" value="${fn:replace(perOrderPriceSum,',','')}"/>
<input type="hidden"  name="consultAmountSum" value="${fn:replace(consultAmountSum,',','')}"/>
<input type="hidden"  name="transferSum" value="${transferSum}"/>
<input type="hidden"  name="newUserConsultSum" value="${fn:replace(newUserConsultSum,',','')}"/>
<input type="hidden"  name="newUserOrderSum" value="${fn:replace(newUserOrderSum,',','')}"/>
<input type="hidden"  name="newTransferSum" value="${newTransferSum}"/>
<input type="hidden"  name="rePurchaseSum" value="${fn:replace(rePurchaseSum,',','')}"/>

<ul class="pager">
	<%@ include file="/WEB-INF/view/common/page.jspf"%>
</ul>