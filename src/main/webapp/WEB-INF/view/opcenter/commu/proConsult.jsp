<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style type="text/css">
#co_table .th_1{width:70px;}
#co_table .th_2{width:85px;}
</style>

<script type="text/javascript">
$(document).ready(function(){
	$('.content-min').each(function(){
		var cont=$(this).parent().find(".content-all").text();
		$(this).popover({
			placement:'bottom',
			trigger:'hover',
			content:cont
		});
	});
});
</script>

<table id="co_table" class="table table-bordered">
	<thead>
		<tr>
			<th style="width:50px;">操作</th>
			<th class="th_2">咨询日期</th>
			<th class="th_2">上次沟通</th>
			<th class="th_1">上次客服工号</th>
			<th class="th_1">今日跟踪</th>
			<th class="th_1">沟通类型（一级）</th>
			<th class="th_1">沟通类型（二级）</th>
			<th class="th_1">品类类别</th>
			<th class="th_1">科组类别</th>
			<th class="th_1">病种类别</th>
			<th class="th_1">是否订购</th>
			<th style="width: 170px;">产品关键词</th>
			<th style="width:90px;">预约回访</th>
			<th style="min-width: 80px;">跟踪信息</th>
			<th style="width:90px;">未订购原因</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
		<c:when test="${commuLs!=null && fn:length(commuLs)>0 }">
			<c:forEach items="${commuLs }" varStatus="status" var="item">
			<tr>
				<td>
				<c:choose>
					<c:when test='${item.isPlaceOrder=="1" || item.isEnable!="1"}'>
					<a href="javascript:void(0);" onclick="openEdit(${item.commuId})" class="btn btn-success btn-xs">查看</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:void(0);" onclick="openEdit(${item.commuId})" class="btn btn-success btn-xs">追加</a>
					</c:otherwise>
				</c:choose>
				</td>
				<td>${item.createTime }</td>
				<td>${item.consultDate }</td>
				<td>${item.custServCode }</td>
				<td>
					<c:choose>
						<c:when test='${fn:substring(item.consultDate, 0, 10)==curDate && item.createTime<item.consultDate}'>已跟踪</c:when>
						<c:otherwise>未跟踪</c:otherwise>
					</c:choose>
				</td>
				<td>${item.acceptResult }</td>
				<td>${item.secondType }</td>
				<td>${item.proCategory }</td>
				<td>${item.depCategory }</td>
				<td>${item.diseaseCategory }</td>
				<td>
					<c:choose>
						<c:when test='${item.isOrder=="0"}'>否</c:when>
						<c:when test='${item.isOrder=="1"}'>是</c:when>
					</c:choose>
				</td>
				<td>
					<c:set var="words" value='${fn:replace(fn:replace(item.proKeywords, "{;}", ""),"{,}","; ")}' scope="request"></c:set>
					<span class="content-all" style="display:none;">${words}</span>
					<div class="content-min">
						<c:if test="${fn:length(words)>20}">${fn:substring(words,0,20)}&hellip;</c:if>
						<c:if test="${fn:length(words)<=20}">${words}</c:if>
					</div>
				</td>
				<td>${item.visitDate }</td>
				<td>
					<span class="content-all" style="display:none;">${item.trackInfo }</span>
					<div class="content-min">
						<c:if test="${fn:length(item.trackInfo)>12}">${fn:substring(item.trackInfo,0,12)}&hellip;</c:if>
						<c:if test="${fn:length(item.trackInfo)<=12}">${item.trackInfo}</c:if>
					</div>
				</td>
				<td>${item.noOrderCause }</td>
			</tr>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<tr>
				<td colspan="13" style="height:100px;text-align: center;vertical-align: middle;font-size:14px;font-weight: bold;">暂无数据！</td>
			</tr>
		</c:otherwise>
		</c:choose>
	</tbody>
</table>
<ul class="pager">
	<%@ include file="/WEB-INF/view/common/page.jspf" %>
</ul>