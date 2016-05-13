<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div>
	<input type="hidden" id="curPage" name="pageno" value="${pageno}"/>
	<input type="hidden" id="curPageSize" name="pageSize" value="${pageSize}"/>
	<div class="right_inline">
		<div class="right_input item_title">日期：</div>
		<div class="right_input" style="width: 150px;">
			<input id="startDate" name="startDate" value="${cvo.startDate}" type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'});" class="form-control item_input"/>
		</div>
		<div class="right_input">&nbsp;__&nbsp;</div>
		<div class="right_input" style="width: 150px;">
			<input id="endDate" name="endDate" value="${cvo.endDate}" type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" class="form-control item_input"/>
		</div>
		<button type="button" onclick="gotoPage(1)" class="btn btn-info sear_btn">查询</button>
		<div style="clear:both;"></div>
	</div>
	<div style="clear:both;"></div>
</div>
<table class="table table-bordered" style="margin-top: 5px;">
	<thead>
		<tr>
			<th style="width:15%;">
				<c:choose>
					<c:when test='${screenType=="1"}'>受理结果</c:when>
					<c:otherwise>类型</c:otherwise>
				</c:choose>
			</th>
			<th style="width:55%;">备注</th>
			<th style="width:20%;">时间</th>
			<th style="width:10%;">客服工号</th>
		</tr>
	</thead>
	<tbody id="tb_body">
		<c:forEach items="${commuList}" varStatus="status" var="commu">
		<tr>
			<td>${commu.acceptResult}
<%-- 			<c:choose> --%>
<%-- 				<c:when test="${commu.acceptResult=='1'}">订购</c:when> --%>
<%-- 				<c:when test="${commu.acceptResult=='2'}">考虑</c:when> --%>
<%-- 				<c:when test="${commu.acceptResult=='3'}">反感</c:when> --%>
<%-- 				<c:when test="${commu.acceptResult=='4'}">关机</c:when> --%>
<%-- 				<c:when test="${commu.acceptResult=='5'}">空号</c:when> --%>
<%-- 				<c:when test="${commu.acceptResult=='6'}">无应答</c:when> --%>
<%-- 				<c:when test="${commu.acceptResult=='7'}">非处方药产品订购</c:when> --%>
<%-- 				<c:when test="${commu.acceptResult=='8'}">处方药产品订购</c:when> --%>
<%-- 				<c:when test="${commu.acceptResult=='9'}">订单查询</c:when> --%>
<%-- 				<c:when test="${commu.acceptResult=='10'}">退换货</c:when> --%>
<%-- 				<c:when test="${commu.acceptResult=='11'}">投诉</c:when> --%>
<%-- 			</c:choose> --%>
			</td>
			<td style="word-wrap: break-word; word-break:break-all;">${commu.remark}</td>
			<td>${commu.createTime}</td>
			<td>${commu.createUser}</td>
		</tr>
		</c:forEach>
		<c:if test="${commuList==null || fn:length(commuList)<=0}">
			<tr>
				<td colspan="4" style="height:100px;text-align: center;vertical-align: middle;font-size:14px;font-weight: bold;">暂无数据！</td>
			</tr>
		</c:if>
	</tbody>
</table>
<ul class="pager">
	<%@ include file="/WEB-INF/view/common/page.jspf" %>
</ul>