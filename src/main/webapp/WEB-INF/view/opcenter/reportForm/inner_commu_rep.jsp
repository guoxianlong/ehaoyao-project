<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style type="text/css">
	table{
     empty-cells:show; 
     border-collapse: collapse;
     margin:0 auto;
    }
	th {
		text-align: center;
	}
	.th {
         white-space: nowrap;
    }

    
</style>
<script type="text/javascript">
	$("img[title='去电弹屏']").click(
		 function outScreen(){
			$("tr").removeClass("success");
			$(this).parent().parent().addClass("success");
			var tel=$(this).attr("alt");
	    	$("#telForm").attr("action","outScreen2.do?method=getInfo&phoneNo="+tel);
	    	$("#telForm").submit();
	    }
	);
	
	function con_tel_show(){
		var tel_show = "${tel_show}";
		if(!tel_show || tel_show=="false"){
			$("#tel_th").css('display','none');
			$(".tel_th_val").css('display','none'); 
		}else{
			$("#tel_th").css('display','block');
			$(".tel_th_val").css('display','block'); 
		}
	}
	
</script>
<table class="table table-bordered" style="margin-top: 5px;">
	<thead>
				<tr>
					<th  class="th">操作</th>
					<th  id="tel_th"  class="th">手机号码</th>
					<th  class="th">客服姓名</th>
					<th  class="th">沟通类型（一级）</th>
					<th  class="th">沟通类型（二级）</th>
					<th  class="th">客户来源</th>
					<th  class="th">产品编码</th>
					<th  class="th">科组类别</th>
					<th  class="th">病种类别</th>
					<th  class="th">&emsp;咨询日期&emsp;</th>
					<th  class="th">最近操作日期</th>
					<th  class="th">是否订购</th>
					<th  class="th">&emsp;成单日期&emsp;</th>
					<th  class="th">是否成单</th>
					<th  class="th">&emsp;&emsp;未订购原因&emsp;&emsp;</th>
					<th  class="th">&emsp;&emsp;&emsp;产品关键词&emsp;&emsp;&emsp;</th>
					<th  class="th">订单金额</th>
					<th  class="th">是否跟踪</th>
					<th  class="th">今日跟踪</th>
					<th  class="th">&emsp;&emsp;跟踪信息&emsp;&emsp;</th>
					<th  class="th">是否新用户</th>
					<!-- <th  class="th">客户编号</th>
					<th  class="th">订单数量</th> -->
				</tr>
			</thead>
			<tbody id="tb_body">
				<c:forEach items="${commuList}" var="commu" varStatus="status">
					<tr>
						<td title=""><img class="img-thumbnail"
							style="width: 27px; height: 27px; cursor: pointer" alt="${commu.tel}"
							title="去电弹屏"
							onerror="this.src='/operation_center/images/call.png';this.onerror=null;"
							src="/operation_center/images/call.png"></td>
						<td class="tel_th_val" style="border-bottom-style:none;border-right-style:none;border-left-style:none;" title="${commu.tel}">${commu.tel}</td>
						<td title="${commu.name}">${commu.name}</td>
						<td title="${commu.acceptResult}">${commu.acceptResult}</td>
						<td title="${commu.secondType}">${commu.secondType}</td>
						<c:choose>
							<c:when test='${commu.custSource=="ZX"}'><td title="在线客服">在线客服</td></c:when>
							<c:when test='${commu.custSource=="XQ"}'><td title="需求登记">需求登记</td></c:when>
							<c:when test='${commu.custSource=="TEL_IN"}'><td title="呼入电话">呼入电话</td></c:when>
							<c:when test='${commu.custSource=="TEL_OUT"}'><td title="老客维护">老客维护</td></c:when>
							<c:otherwise><td>&nbsp;</td></c:otherwise>
						</c:choose>
						<td title="${fn:replace(commu.proSkus,',', ', ')}">
							${fn:replace(commu.proSkus, ",", ", ")}
						</td>
						<td title="${commu.depCategory}">${commu.depCategory}</td>
						<td title="${commu.diseaseCategory}">${commu.diseaseCategory}</td>
						<td title="${commu.createTime}">${commu.createTime}</td>
						<td title="${commu.consultDate}">${commu.consultDate}</td>
						<td>
							<c:if test="${commu.isOrder=='1'}">是</c:if>
							<c:if test="${commu.isOrder=='0'}">否</c:if>
						</td>
						<td title="${commu.placeOrderDate}">${commu.placeOrderDate}</td>
						<td title="${commu.isPlaceOrder=='1'?'是':'否'}">
							<c:if test="${commu.isPlaceOrder=='1'}">是</c:if>
							<c:if test="${commu.isPlaceOrder ne '1'}">否</c:if>
						</td>
						<td title="${commu.noOrderCause}">${commu.noOrderCause}</td>
						<td title="${fn:replace(fn:replace(commu.proKeywords, '{;}', ''),'{,}','; ')}">
							${fn:replace(fn:replace(commu.proKeywords, "{;}", ""),"{,}","; ")}
						</td>
						<td title="${commu.orderTotalPrice}">${commu.orderTotalPrice}</td>
						<td>
							<c:if test="${commu.isTrack=='1'}">是</c:if>
							<c:if test="${commu.isTrack=='0'}">否</c:if>
						</td>
						<td>
							<c:if test="${commu.todayTrackValue=='1'}">已跟踪</c:if>
							<c:if test="${commu.todayTrackValue=='0'}">未跟踪</c:if>
						</td>
						<td title="${commu.trackInfo}">${commu.trackInfo}</td>
						<td>
							<c:if test="${commu.isNewUser=='1'}">是</c:if>
							<c:if test="${commu.isNewUser=='0'}">否</c:if>
						</td>
				<%-- 		<td title="${commu.custNo}">${commu.custNo}</td>
						<td title="${commu.orderQuantity}">${commu.orderQuantity}</td> --%>
					</tr>
				</c:forEach>
		<c:if test="${commuList==null || fn:length(commuList)<=0 }">
			<tr>
				<td colspan="17"
					style="height: 100px; text-align: center; vertical-align: middle; font-size: 14px; font-weight: bold;">暂无数据！</td>
			</tr>
		</c:if>
	</tbody>
</table>
<form  method="post" action="" name="telForm"	id="telForm" target="_blank"></form>
<ul class="pager">
	<%@ include file="/WEB-INF/view/common/page.jspf"%>
</ul>