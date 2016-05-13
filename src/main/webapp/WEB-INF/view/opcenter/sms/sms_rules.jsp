<%@page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" />
<meta content="telephone=no" name="format-detection" />
<meta content="email=no" name="format-detection" />

<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-select.min.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-switch.min.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/css/sms/order-sms-setup.css" rel="stylesheet" />

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap/bootstrap-switch.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap/bootstrap-select.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/sms/sms_rules.js"></script>

<style type="text/css">
.channel{float:left;width:auto;padding:3px 5px;margin:5px;cursor: pointer;border-radius:4px;background-color:#A4D3EE;}
.c_text{color:#000055;}
.c_icon{color:#FF0404;}
#useChannel,#unuseChannel{width:745px;min-height:50px;padding:5px;border:1px solid #DDD;border-radius:4px;overflow: hidden;}
</style>
</head>
<body style="min-height: 600px;">
	<div style="margin: 20px auto; padding: 0 15px; max-width: 1150px;">
		<form id="queryForm" name="queryForm" action="<%=request.getContextPath()%>/rule/setup.do" method="post" style="display:none;">
			<input type="hidden" id="orderStatus" name="orderStatus" />
			<input type="hidden" id="con_channel" name="conChannel" />
			<input type="hidden" id="pageNo" name="pageNo" />
		</form>
		<div id="main" class="main">
			<h4 style="text-align: left;">订单(快递)状态短信群发设置</h4>
			<div class="condition">
				<div class="pull-left">
					<label style="padding-left:15px;">渠道：</label>
					<select class="selectpicker" data-width="auto" data-size="10" id="q_channel">
						<option value="all">全部</option>
						<c:if test="${!empty channelMap }">
						<c:forEach items="${channelMap }" var="item" >
							<option value="${item.key }" ${item.key==conChannel?'selected="selected"':'' }>${item.value }</option>
						</c:forEach>
						</c:if>
					</select>
					<label style="padding-left:15px;">订单状态：</label>
					<select class="selectpicker" data-width="auto" id="condition">
						<option value="all" ${orderStatus=="all"?'selected="selected"':'' } >全部</option>
						<option value="1" ${orderStatus=="1"?'selected="selected"':'' } >已下单</option>
						<option value="2" ${orderStatus=="2"?'selected="selected"':'' } >配货中</option>
						<option value="3" ${orderStatus=="3"?'selected="selected"':'' } >已发货</option>
						<option value="4" ${orderStatus=="4"?'selected="selected"':'' } >已签收</option>
						<option value="5" ${orderStatus=="5"?'selected="selected"':'' } >已拆单</option>
						<option value="6" ${orderStatus=="6"?'selected="selected"':'' } >运单已复核</option>
						<option value="7" ${orderStatus=="7"?'selected="selected"':'' } >已退款</option>
					</select>
				</div>
				<div class="pull-right">
<%-- 					<button type="button" class="btn btn-default" id="run">查询短信</button>
					<button type="button" class="btn btn-default" id="send">发送短信</button> --%>
					<button type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal" id="addRuleBtn" onclick="onAdd();">添加规则</button>
				</div>
				<div class="clearfix"></div>
			</div>
			<table id="ruleList" class="table table-bordered">
				<thead>
					<tr>
						<th class="w80">规则编号</th>
						<th class="w80">规则名称</th>
						<th class="w80">订单状态</th>
						<th class="w80">其它条件</th>
						<th>发送内容</th>
						<th class="w80">字数预估</th>
						<th class="w100">发送时段</th>
						<th class="w240">操作</th>
					</tr>
				</thead>
				<tbody id="tb_body">
					<c:forEach items="${ruleVO.pageModel.list}" varStatus="status" var="rule">
		          	<tr>
						<td>${rule.id}</td>
	   			        <td>${rule.ruleName}</td>
	   				    <td>${rule.orderStatus_CN}</td>
	   			        <td>${rule.cashDelivery_CN}</td>
	   			        <td>${rule.content}</td>
	   			        <td>${rule.wordCount}</td>
	   			        <td>
	   			        	<div>${rule.startHour}至${rule.endHour}时</div>
	   			        	<div>${rule.outTimeFlag=="0"?"时间外顺延":"时间外跳过"}</div>
	   			        </td>
	   			        <td>
							<div class='switch pull-left' data-on='success'>
								<input type="hidden" class="ruleId" value="${rule.id}" />
								<input type='checkbox' ${rule.enable=="1"?"checked":""} />
							</div>
							<div class='btn-group'>
							    <button type='button' onclick='onEdit(${rule.id})' class='btn btn-default' data-toggle='modal' data-target='#myModal'>编辑</button>
							    <button type='button' onclick='onDel(${rule.id})' class='btn btn-danger'>删除</button>
							</div>
						</td>
			        </tr>
		         	</c:forEach>
				</tbody>
			</table>
			<div class="navigation" style="height:50px;">
				<div class="pull-right">
					共<label id="lblToatl">${ruleVO.pageModel.totalRecords}</label>条数据 第[<label id="lblCurent">${ruleVO.pageModel.pageNo}</label>]页/共[<label
						id="lblPageCount">${ruleVO.pageModel.totalPages}</label>]页 <a id="first" href="#">首页</a> <a
						id="previous" href="#">上一页</a> <a id="next" href="#"> 下一页</a> <a
						id="last" href="#">末页</a>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
		<form id="editForm" class="form-horizontal" role="form">
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">短信群发设置</h4>
						</div>
						<input type="hidden" id="rid" />
						<div id="ruleInfo" class="modal-body">
							<div class="form-group">
								<label class="col-sm-2 control-label">规则名称</label>
								<div class="col-sm-3">
									<input id="ruleName" name="ruleName" maxlength="40" class="form-control"/>
								</div>
								<label class="col-sm-2 control-label">订单状态</label>
								<div class="col-sm-2">
									<select class="selectpicker" data-width="auto" id="orderType">
										<option value="1">已下单</option>
										<option value="2">配货中</option>
										<option value="3">已发货</option>
										<option value="4">已签收</option>
										<option value="5">已拆单</option>
										<option value="6">运单已复核</option>
										<option value="7">已退款</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">其它条件</label>
								<div class="col-sm-10">
									<label class="checkbox-inline">
										<input type="checkbox" id="cod" />仅针对货到付款
									</label>
								</div>
							</div>
							<div class="form-group">
								<input type="hidden" id="orderFlag" name="orderFlag" />
								<label class="col-sm-2 control-label">渠道</label>
								<div class="col-sm-10">
									<div id="useChannel"></div>
									<div style="margin-top:5px;height:25px;">
										<a id="sel_a" href="javascript:void(0);" onclick="viewChannel();">展开选择渠道...</a>
									</div>
									<div id="unuseChannel" style="display: none;">
										<div style="float:left;margin:10px 5px 0 5px;">可选渠道：</div>
										<c:if test="${!empty channelMap }">
										<c:forEach items="${channelMap }" var="item" >
											<div class="channel"><span class="c_text" title="${item.key }">${item.value }</span></div>
										</c:forEach>
										</c:if>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">短信内容</label>
								<div class="col-sm-10">
									<textarea id="sms_content" class="form-control" style="float: left; margin-right: 20px; width: 600px; height:165px;"></textarea>
									<div class="btn-group-vertical pull-left" id="insertBtn">
										<button type="button" id="btn1" class="btn btn-default">插入用户姓名</button>
										<button type="button" id="btn3" class="btn btn-default">插入订单号</button>
										<button type="button" id="btn4" class="btn btn-default">插入快递公司名</button>
										<button type="button" id="btn5" class="btn btn-default">插入快递单号</button>
										<button type="button" id="btn6" class="btn btn-default">插入退款金额</button>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">预计字数</label>
								<div class="col-sm-10">
									<input type="text" id="word" class="form-control pull-left w50" readonly>
									<button type="button" id="wordCnt" class="btn btn-default pull-left" style="margin: 0 10px;">清算字数</button>
									<span class="pull-left text-danger" style="height: 34px; line-height: 34px;">不建议超过67字</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">发送时间段</label>
								<div class="col-sm-10">
									<select class="selectpicker" data-width="auto" id="startTime">
										<option value="6">6</option>
										<option value="7">7</option>
										<option value="8" selected="selected">8</option>
										<option value="9">9</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
									</select> <span>至</span> <select class="selectpicker" data-width="auto"
										id="endTime">
										<option value="17">17</option>
										<option value="18">18</option>
										<option value="19">19</option>
										<option value="20" selected="selected">20</option>
										<option value="21">21</option>
										<option value="22">22</option>
									</select>
									<select class="selectpicker" data-width="auto" id="outTimeFlag">
										<option value="0">时间外自动顺延</option>
										<option value="1">时间外跳过不发</option>
									</select>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
								<button type="button" id="submitBtn" class="btn btn-primary">保存</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>