<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1 maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>中药代订单管理</title>
		
		<!--
        	作者：158822436@qq.com
        	时间：2016-01-13
        	描述：引入css样式
        -->
        <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-theme.css" rel="stylesheet">
	    <link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
	    <link href="<%=request.getContextPath()%>/css/bootstrap/jquery-bootstrap-select.css" rel="stylesheet">
		<link href="<%=request.getContextPath()%>/css/bootstrap/jquery.mCustomScrollbar.css" rel="stylesheet" />
		<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-lightbox.min.css" rel="stylesheet">
		<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
		<link href="<%=request.getContextPath()%>/css/opcenter/thirdOrderAudit/global.css" rel="stylesheet">
		<link href="<%=request.getContextPath()%>/css/opcenter/thirdOrderAudit/thirdOrderAudit.css" rel="stylesheet">
		<link href="<%=request.getContextPath()%>/css/opcenter/thirdOrderAudit/table.css" rel="stylesheet">
		
		<!--
        	作者：longshanw
        	时间：2016-01-13
        	描述：引入js脚本文件
        -->
		<script src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"></script>
	    <script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap.min.js"></script>
	    <script src="<%=request.getContextPath()%>/js/bootstrap/jquery-bootstrap-select.js"></script>
	    <script src="<%=request.getContextPath()%>/js/bootstrap/jquery.bootstrap.js"></script>
		<script src="<%=request.getContextPath()%>/bvalidator/js/jquery.bvalidator.js"></script>
		<script src="<%=request.getContextPath()%>/js/WdatePicker/WdatePicker.js"></script>
		<script src="<%=request.getContextPath()%>/js/bootstrap/jquery.mCustomScrollbar.concat.min.js" ></script>
		<script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap-lightbox.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/opcenter/CMReplaceOrder/CMReplaceOrder.js"></script>
		
	</head>


<body style="min-height: 680px;">
	<h3 class="h3">中药代订单管理</h3>
	<form id="orderOprForm"  method="post" role="form">
		<div id="conditionDiv" >
		<input type="hidden" id="curPage" name="pageno" value="${pageno}"/>
		<input type="hidden" id="curPageSize" name="pageSize" value="${pageSize}"/>
		<input type="hidden" id="totalRecords" name="totalRecords" value="${recTotal}"/>
		<input type="hidden" id="orderBy" name="orderBy" value="add_time"/>
		<input type="hidden" id="sort" name="sort" value="2"/>
		<input type="hidden" name="actionName" value="${actionName}"/>
			<fieldset class="fieldset">
				<legend class="legend"><span>查询条件</span></legend>
				<label><span>订单日期:</span></label>
				<input type="text" class="input" id="startDate" name="startDate" value="${vo.startDate}" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'});" />
				<span>-</span>
				<input type="text" class="input" id="endDate" name="endDate" value="${vo.endDate}" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" />
				
				 <label><span>订单号：</span></label>
				 <input type="text" id="orderNumber" class="input" name="orderNumber" value="${vo.orderNumber}" />
				 <label><span>渠道:</span></label>
				<select id="orderFlagSelect" name="orderFlag" class="selectpicker" data-hide-disabled="true" data-live-search="false">
				      <option value="-1" ${vo.orderFlag==''?"selected":''}  >请选择</option>
				      <option value="TMZYG" ${vo.orderFlag=='TMZYG'?"selected":''}>天猫中药馆</option>
				      <option value="GWZYG" ${vo.orderFlag=='GWZYG'?"selected":''}>官网中药馆</option>
				  </select>
				  
				 <label><span>审核状态:</span></label>
				 <select id="auditStatusSelect" name="auditStatus" class="selectpicker" data-hide-disabled="true" data-live-search="false">
				    <!-- <optgroup label=">审核状态"> -->
				      <option value="-1" ${vo.auditStatus==''?"selected":''}  >请选择</option>
				      <option value="WAIT" ${vo.auditStatus=='WAIT'?"selected":''}>等待审核</option>
				      <option value="SUCC" ${vo.auditStatus=='SUCC'?"selected":''} >审核通过</option>
				      <option value="RETURN" ${vo.auditStatus=='RETURN'?"selected":''} >审核驳回</option>
				    <!-- </optgroup> -->
				  </select>
				  
				<%--  <label><span>订单状态：</span></label>
				 <select id="orderStatus" name="orderStatus" class="selectpicker"  data-hide-disabled="true" data-live-search="true" >
				    <option value="-1" ${""==vo.orderStatus || "-1"== vo.orderStatus?'selected="selected"':''}>--请选择--</option>
					<option value="UNTREATED" ${"UNTREATED"==vo.orderStatus?'selected="selected"':''}>未处理</option>
					<option value="FINISH" ${"FINISH"==vo.orderStatus?'selected="selected"':''}>已完成</option>
				 </select> --%>
					<button type="button" class="button" id="queryBtnId" style="text-align: left;">查询</button>
			</fieldset>
		</div>
	</form>
		 <div id="contentDiv" class="contentDiv">
		 	<div id="tableDiv">
		 		<table class="bordered"  id="orderInfo">
		 		<caption>✦✦✦✦✦点击表格行可查看订单药品明细✦✦✦✦✦</caption>
		 		<thead>
				<tr>
					<th class="th">序号</th>
					<th class="th">订单号</th>
					<th class="th">渠道</th>
					<th class="th">订单日期</th>
					<th class="th">姓名</th>
					<th class="th">电话</th>
					<th class="th">地址</th>
					<th class="th">患者姓名</th>
					<th class="th">性别</th>
					<th class="th">年龄</th>
					<th class="th">是否孕妇</th>
					<th class="th">是否待煎</th>
					<th class="th">剂量</th>
					<th class="th">用药说明</th>
					<th class="th">订单金额</th>
					<th class="th">支付方式</th>
					<th class="th">付款状态</th>
					<th class="th">订单状态</th>
					<th class="th">客服工号</th>
					<th class="th">审核状态</th>
					<th class="th">审核人</th>
					<th class="th">处方单</th>
					<th>操作</th>
				</tr>
				</thead>
				<tbody>
					<c:if test="${orderList != null && fn:length(orderList)>0}">
					<c:forEach items="${orderList}" var="items" varStatus="orderNumber">
					<tr class="orderInfoTr">
						<td class="th" style="vertical-align: middle;">
						    ${(pageno-1)*pageSize+orderNumber.count}
						</td>
						<td class="th" style="vertical-align: middle;">${items.orderNumber}</td>
						<td style="display: none;">${items.orderFlag}</td>
						<td class="th" style="vertical-align: middle;">
							<c:if test="${items.orderFlag=='TMZYG'}">天猫中药馆</c:if>
							<c:if test="${items.orderFlag=='GWZYG'}">官网中药馆</c:if>
						</td>
						<td class="th" style="vertical-align: middle;">${items.orderTime}</td>
						<td class="th" style="vertical-align: middle;">${items.receiver}</td>
						<td class="th" style="vertical-align: middle;">${items.telephone}</td>
						<td class="th" style="vertical-align: middle;">${items.addressDetail} </td>
						<td class="th" style="vertical-align: middle;">${items.patientName}</td>
						<td class="th" style="vertical-align: middle;">${items.sex}</td>
						<td class="th" style="vertical-align: middle;">${items.age}</td>
						<td class="th" style="vertical-align: middle;">
						${items.pregnantFlag == '0'?'否':'是'}
						</td>
						<td class="th" style="vertical-align: middle;">${items.decoctFlag=="0"?否:'是'}</td>
						<td class="th" style="vertical-align: middle;">${items.dose}</td>
						<td class="th" style="vertical-align: middle;">${items.instructions}</td>
						<td class="th" style="text-align: right;vertical-align: middle;">
						<fmt:formatNumber value="${items.orderPrice!=null?items.orderPrice:0.00}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>
						</td>
						<td class="th" style="vertical-align: middle;">
							<c:if test="${items.payType == 'COD'}">货到付款</c:if>
							<c:if test="${items.payType == 'ONLINEPAY'}">网上支付</c:if>
						</td>
						<td class="th" style="vertical-align: middle;">
						    <c:if test="${items.payStatus == 'NOPAY'}">未支付</c:if>
						    <c:if test="${items.payStatus == 'PAID'}">已支付</c:if>
						    <c:if test="${items.payStatus == 'REFUND'}">已退款</c:if>
						</td>
						<td class="th" style="vertical-align: middle;">
						    <c:if test="${items.orderStatus == 'UNTREATED'}">未处理</c:if>
						    <c:if test="${items.orderStatus == 'FINISH'}">已完成</c:if>
						</td>
						<td class="th" style="vertical-align: middle;">${items.kfAccount}</td>
						<td class="th" style="vertical-align: middle;">
						    <c:if test="${items.auditStatus == 'WAIT'}">未审核</c:if>
						    <c:if test="${items.auditStatus == 'SUCC'}">审核通过</c:if>
						    <c:if test="${items.auditStatus == 'RETURN'}">驳回</c:if>
						</td>
						<td class="th" style="vertical-align: middle;">${items.kfAccount}</td>
						<td class="th detailTd" style="vertical-align: middle;">
						   <div class="btn-group-vertical">
						   			<img class="img-thumbnail" src="images/search.png" onclick="orderDetailsPageView('${items.orderNumber}','${items.orderFlag}');"
						    	 onerror="this.src='images/search.png';this.onerror=null;"
							     title="详情" alt="详情" style="width: 27px; height: 27px; cursor: pointer"/>
							</div>
						</td>
						<td class="oprBtn">
	 						<button id="oprBtn" onclick="orderDetailsPageView('${items.orderNumber}','${items.orderFlag}');" class="button">${items.auditStatus!='WAIT'?"查看":"审核"}</button>
						</td>
					</tr>
					</c:forEach>
					</c:if>
					<c:if test="${orderList == null or fn:length(orderList)<=0}">
					    <tr><th colspan="23" style="background-color:#ccc;font-size: 14px;color: #fa0;">暂无数据...</th></tr>
					</c:if>
				</tbody>
			</table>
			<div class="page">
			 	<c:if test="${orderList != null && fn:length(orderList)>0}">
					<ul class="pager page">
						<%@ include file="/WEB-INF/view/common/page.jspf" %>
					</ul>
				</c:if>
			</div>
		</div>
		
		<div id="orderDetailDiv" class="orderDetailDiv">
		    <table id="orderDetailsTable" class="bordered">
	        <caption>商品明细：</caption>
			<tr>
				<th>商品编码</th>
				<th>商品名称</th>
				<th>规格</th>
				<th>品牌</th>
				<th>数量</th>
				<th>单价</th>
				<th>金额</th>
				<th>是否赠品</th>
				<th>处方类型</th>
			</tr>
	    </table>
	</div>
</div>
	
	
	<!-- 订单详情 -->
    <div class="modal fade" id="orderDetailPageModal" tabindex="-1" role="dialog" aria-labelledby="detailsModalLabel" style="margin-top: 0px;" aria-hidden="true">
		<div class="modal-dialog" style="width: 950px;height: 300px;">
			<div class="modal-content">
				<div class="modal-header" style="padding-top:5px;padding-bottom:0px;margin-bottom:0px;height:35px;background-color: #51ABD9;">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
					</button>
					<h5 class="modal-title" style="color:white;text-align: center;" id="detailsModalLabel">
		               	订单详情
		            </h5>
				</div>
				<div class="modal-body" style="margin-top:0px;margin-bottom: 0px;padding-bottom: 10px;padding-top: 0px;">
					<ul id="CMTabUL" class="nav nav-tabs" >
						<li class="active"><a href="#drupSelected" data-toggle="tab" style="height: 30px;padding-top: 3px;" >药品明细</a></li>
						<li><a href="#DrupPicture" data-toggle="tab" style="padding-top: 3px;">处方单</a></li>
					</ul>

					<div id="CMTabContent" class="tab-content" style="margin-top: 5px;">
						<div class="tab-pane fade" id="DrupPicture">
						<a data-toggle="lightbox" href="#demoLightbox">
							<img title="点击放大" id="smallPicLink" class="img-rounded"  alt="处方单">
						</a>
						</div>
						<div class="tab-pane fade in active" id="drupSelected" >
							<div class="modal-body" style="margin-bottom: 0px;padding-bottom: 0px;padding-top: 5px;" >
								<fieldset class="fieldset-small">
									<legend class="legend-small">订单信息</legend>
									<label class="label" style="display: none;">订单编号：<span id="orderNumberDetailPage"></span></label>
									<label class="label" style="display: none;">渠道：<span id="orderFlagDetailPage"></span></label>
									<label>用户ID：<span id="userId"></span></label>
									<label>编号：<span id="userCode"></span></label>
									<label>提交日期：<span id="orderTime"></span></label>
									<label>合计金额：<span id="orderPrice"></span></label>
									<br>
									<label>是否代煎：<span id="decoctFlag"></span></label>
									<label>是否孕妇：<span id="pregnantFlag"></span></label>
									<label>患者姓名：<span id="patientName"></span></label>
									<label>患者性别：<span id="sex"></span></label>
									<label>患者年龄：<span id="age"></span></label>
									<label>剂量：<span id="dose"></span></label>
								</fieldset>
								<label style="margin-top: 10px;font-weight: bold;color: yellowgreen;font-size: 13px;">购买商品：</label>
								<div id="detailPageTbDiv" >
									<table id="detailsPageTable" class="bordered detailsPageTable" style="margin: 5px 0px 5px;width: 870px;">
										<tr>
											<th>商品编码</th>
											<th>商品名称</th>
											<th>规格</th>
											<th>品牌</th>
											<th>数量</th>
											<th>单价</th>
											<th>金额</th>
											<th>是否赠品</th>
											<th>处方类型</th>
											<th>删除</th>
										</tr>
									</table>
								</div>
								<!-- 按钮触发模态框 -->
								<button class="btn btn-primary btn-sm" id="addBtn" style="margin-top: 5px;" data-toggle="modal" data-target="#DrugFastModal">
									增加
								</button>
								<div class="form-group">
									<span style="color: #66CC99;">✦✦✦✦✦用药说明✦✦✦✦✦</span>
									<textarea class="form-control" style="margin-top: 5px;" disabled="disabled" id="instructions" name="instructions" placeholder="请输入用药说明..." rows="2"></textarea>
								</div>
								
								<div class="form-group">
									<span style="color: #66CC99;">✦✦✦✦✦审核说明✦✦✦✦✦</span>
									<textarea class="form-control" style="margin-top: 10px;" disabled="disabled" id="auditDescription" name="auditDescription" placeholder="请输入审核说明..." rows="2"></textarea>
								</div>
							</div>

							<div class="modal-footer" style="height: 35px;padding-top: 8px;">
								<label style="font-size: 13px;float: left;display: none;" id="auditUserLabel">执行医师：<span id="auditUser" style="font-weight: bold;font-size: 13px;color: #428BCA;"></span></label>

								<button type="button" id="succBtn" class="btn btn-primary btn-sm" onclick="auditFunc('SUCC')"> 通过 </button>
								<button type="button" id="retnBtn" class="btn btn-primary btn-sm" onclick="auditFunc('RETURN')"> 驳回 </button>
								<button type="button" id="closeBtn" class="btn btn-primary btn-sm" data-dismiss="modal"> 取消 </button>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
		
	<!-- 弹出处方单图片 -->
	<div id="demoLightbox" class="lightbox fade"  tabindex="-1" style="index-z:1" role="dialog" aria-hidden="true">
		<div class='lightbox-dialog'>
			<div class='lightbox-header'>
				<button type="button" class="close" data-dismiss="lightbox" aria-hidden="true" >
				</button>
			</div>
			<div class='lightbox-content' id="imgContentDiv" style="padding: 5px;">
				<img id="bigPicLink"  class="img-rounded" >
				<div class="lightbox-caption"><p>处方单</p></div>
			</div>
		</div>
	</div>


	<!-- 快速抓药模态框（Modal） -->
		<div class="modal fade" id="DrugFastModal" tabindex="-1" role="dialog" aria-labelledby="drugFastLabel" aria-hidden="true" data-backdrop="true">
			<div class="modal-dialog" style="width: 350px;height: 450px;">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #51ABD9;height: 35px;margin-top: 0px;padding-top: 6px;">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
						</button>
						<h5 class="modal-title" style="color:white;text-align:center;" id="drugFastLabel">
			               	药材选择：
			            </h5>
					</div>
					<div class="modal-body" style="margin-left: 30px;margin-right:20px;padding: 0px;">
						<br />
						<form class="form-horizontal" role="form">
							<div class="form-group">
								<div class="col-sm-9">
									<input type="text" class="form-control" id="drupName" style="width: 210px;" placeholder="请输入中文/拼音或者↑↓选择...">
								</div>
								<button type="button" class="btn btn-default">搜索</button>
							</div>
						</form>
						<ul class="list-group" id="ulId" style="height:300px;padding-right: 10px;margin-bottom: 10px;">
							<li class="list-group-item">免费域名注册<a href="#" style="float: right;"onclick="hiddenFastModal(this);">添加</a></li>
							<li class="list-group-item">免费 Window 空间托管<a href="#" style="float: right;" onclick="hiddenFastModal(this);">添加</a></li>
							<li class="list-group-item">图像的数量<a href="#" style="float: right;" onclick="hiddenFastModal(this);">添加</a></li>
							<li class="list-group-item">
								<a href="#" style="float: right;" onclick="hiddenFastModal(this);">添加</a>
								<span class="badge">新</span> 24*7 支持
							</li>
							<li class="list-group-item">每年更新成本<a href="#" style="float: right;" onclick="hiddenFastModal(this);">添加</a></li>
							<li class="list-group-item">每年更新成本<a href="#" style="float: right;" onclick="hiddenFastModal(this);">添加</a></li>
							<li class="list-group-item">每年更新成本<a href="#" style="float: right;" onclick="hiddenFastModal(this);">添加</a></li>
							<li class="list-group-item">每年更新成本<a href="#" style="float: right;" onclick="hiddenFastModal(this);">添加</a></li>
							<li class="list-group-item">每年更新成本<a href="#" style="float: right;" onclick="hiddenFastModal(this);">添加</a></li>
							<li class="list-group-item">
								<a href="#" style="float: right;" onclick="hiddenFastModal(this);">添加</a>
								<span class="badge">新</span> 折扣优惠
							</li>
						</ul>
					</div>
					<div class="modal-footer" style="height: 35px;padding-top: 2px;margin-top: 0px;">
						<button type="button" class="btn btn-primary btn-sm" data-dismiss="modal">
							关闭
						</button>
						<!--<button type="button" class="btn btn-primary">
							提交更改
						</button>-->
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
</body>