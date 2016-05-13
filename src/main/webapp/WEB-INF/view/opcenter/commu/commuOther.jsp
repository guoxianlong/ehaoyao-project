<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style type="text/css">
#co_table .th_1{width:150px;}
</style>

<table id="co_table" class="table table-bordered">
	<thead>
		<tr>
			<th class="th_1">操作</th>
			<th class="th_1">时间</th>
			<th>备注</th>
			<th class="th_1">客服工号</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
		<c:when test="${commuLs!=null && fn:length(commuLs)>0 }">
			<c:forEach items="${commuLs }" varStatus="status" var="item">
			<tr>
				<td>
					<a href="javascript:void(0);" onclick="openEdit(${item.commuId})" class="btn btn-success btn-xs">修改</a>
				</td>
				<td>${item.consultDate }</td>
				<td>${item.remark }</td>
				<td>${item.custServCode }</td>
			</tr>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<tr>
				<td colspan="4" style="height:100px;text-align: center;vertical-align: middle;font-size:14px;font-weight: bold;">暂无数据！</td>
			</tr>
		</c:otherwise>
		</c:choose>
	</tbody>
</table>
<ul class="pager">
	<%@ include file="/WEB-INF/view/common/page.jspf" %>
</ul>