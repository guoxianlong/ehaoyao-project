<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="<%=request.getContextPath()%>/js/bootstrap/iCheck/skins/flat/blue.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">

<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap/iCheck/icheck.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/opcenter/commuType.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/opcenter/commuMain.js"></script>

<style type="text/css">
#commuDiv table:not(.table-striped) th{background-color: #f9f9f9;}
.td_title{width: 70px;text-align: right;padding-top: 5px;margin-left:15px;margin-right:20px;}
.td_title2{width: 70px;text-align: right;padding-top: 5px;margin-left:15px;margin-right:20px;}
.td_context{width:140px;}
.td_context2{width:160px;}
.lab_normal{margin-left:10px;font-weight: normal;}
.comm_textarea{width:310px;}
.pro_h{height:50px;border-bottom:1px solid #DDD;line-height: 50px;text-align: center;}
.pro_ct{min-height:200px;padding:5px;border-bottom:1px solid #DDD;}
.tipText{color:#999;}
#selPros{padding:5px;border: 1px solid #DDD;border-radius:4px;}
.div_inline{float:left;width:auto;padding:2px 5px;margin:3px;border-radius:4px;border:1px solid #D2E0F0;background-color:#D2F0FF;}
.span_text{color:#3179A0;}
.span_del{color:#FF0404;cursor: pointer;margin-left:5px;}
#commuTab li a{font-size:14px;}
</style>

<script type="text/javascript">
$(document).ready(function(){
	$('#commuModal .selectpicker').selectpicker();
	$("#commuModal .bootstrap-select").css("margin-bottom","0");
	
	$("input.iCheckRadio").iCheck({
	    radioClass: 'iradio_flat-blue'
	});
	$('#commuTab a').click(function(e){
		e.preventDefault();
		if($(this).parent().hasClass("disabled")){
			return;
		}
		$(this).tab('show');
		var li = $(this).parent();
		if(li.index()==0){
			$("#commuModal .modal-dialog").css("width","530px");
		}else if(li.index()==1){
			$("#commuModal .modal-dialog").css("width","800px");
		}else if(li.index()==2){
			$("#commuModal .modal-dialog").css("width","650px");
		}
	});
});
</script>

<div style="min-width: 1000px;" id="commuDiv">
	<input type="hidden" name="commuAction" value="${commuAction}"/>
	<input type="hidden" name="outAction" value="${outAction}"/>
	<input type="hidden" id="commuSource" value="${commuSource}"/>
	<input type="hidden" id="custServCode" value="${custServCode}"/>
<%-- 	<div>
		<ul id="consult_tablist" class="nav nav-pills" role="tablist">
			<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
			<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
			<li ${commuSource=="TEL_IN"?'class="active"':''}><a href="javascript:void(0);" >来电咨询</a></li>
			<li ${commuSource=="TEL_OUT" && isTask!="1"?'class="active"':''}><a href="javascript:void(0);">去电咨询</a></li>
		</ul>
	<div> --%>
	<div>
		<div style="float:left;">
			<ul class="nav nav-pills nav-stacked" id="na_list">
			<c:choose>
			   <c:when test='${commuSource=="TEL_OUT" && isTask!="1"}'>
				   <li class="active"><a href="javascript:void(0);">产品咨询</a></li>
				   <li><a href="javascript:void(0);">客户维护</a></li>
				   <li><a href="javascript:void(0);">无效电话</a></li>
			   </c:when>
			   <c:otherwise>
				   <li class="active"><a href="javascript:void(0);">咨询</a></li>
				   <li><a href="javascript:void(0);">修改</a></li>
				   <li><a href="javascript:void(0);">查询</a></li>
				   <li><a href="javascript:void(0);">业务受理</a></li>
				   <li><a href="javascript:void(0);">退换补货</a></li>
				   <li><a href="javascript:void(0);">投诉业务</a></li>
				   <li><a href="javascript:void(0);">建议&感谢</a></li>
			   </c:otherwise>
			</c:choose>
			</ul>
		</div>
		<div style="float:left;width:91%;min-height: 300px;margin-left:10px;padding:10px;border:1px solid #DDD;">
			<input type="hidden" id="curPageSize" name="pageSize" value="${pageSize}"/>
			<div class='tr_inline'>
				<div class="td_item item_title">来电日期：</div>
				<div class="td_item" style="width: 150px;">
					<input id="startDate" name="startDate" value="" type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'});" class="Wdate form-control item_input"/>
				</div>
				<div class="td_item">&nbsp;&nbsp;__&nbsp;&nbsp;</div>
				<div class="td_item" style="width: 150px;">
					<input id="endDate" name="endDate" value="" type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" class="Wdate form-control item_input"/>
				</div>
				<button type="button" onclick="getCommuLs();" class="btn btn-info btn-sm" style="margin:0 15px;">查询</button>
				<button class="btn btn-default btn-sm" onclick="openAdd()">添加</button>
				<div style="clear:both;"></div>
			</div>
			<div id="commLs">
				<jsp:include page="proConsult.jsp"></jsp:include>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
	
	<div class="modal fade" id="commuModal" tabindex="-1" role="dialog" aria-labelledby="commuModalLabel" aria-hidden="true" data-backdrop='static'>
		<div class="modal-dialog" style="width:530px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="commuModalLabel">沟通记录</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" id="commuId" name="commuId" />
					<ul id="commuTab" class="nav nav-tabs">
						<li id="commuLi"><a href="#commuTabPane">沟通记录</a></li>
						<li class="disabled" id="hisLi"><a href="#trackLs">历史记录</a></li>
						<li class="disabled" id="healthLi"><a href="#healthDoc">健康档案</a></li>
					</ul>
					<div id="commuTabContent" class="tab-content" style="border:1px solid #DDD;border-top:none;padding:10px;min-height: 200px;">
						<div id="commuTabPane" class="tab-pane active">
						  	<div id="commuType" class="tr_inline">
								<label class="td_item td_title" for="aResult">沟通类型</label>
								<div class="td_item td_context">
									<select id="aResult" name="acceptResult" onchange="changeType()" class="selectpicker form-control" >
										<option value="">--请选择--</option>
									</select>
								</div>
								<div class="td_item td_context" style="margin-left:30px;">
									<select id="sType" name="secondType" onchange="changeSecType()" class="selectpicker form-control">
										<option value="">--请选择--</option>
									</select>
								</div>
								<div class="clearfix"></div>
								<label class="td_item td_title" for="thirdType"></label>
								<div class="td_item td_context" style="margin-top:7px;display:none">
									<select id="thirdType" name="thirdType" class="selectpicker form-control">
										<option value="">--请选择--</option>
									</select>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="tr_inline">
								<label class="td_item td_title">产品关键词</label>
								<div class="td_item">
									<input type="hidden" id="keywordsTip" value="多个关键词请用英文分号“;”分隔"/>
									<textarea id="proKeywords" name="proKeywords" class="form-control comm_textarea" style="height:60px;" ></textarea>
								</div>
								<div class="td_item">
									<img alt="查询关键词" title="查询关键词" src="images/search.png" onerror="this.onerror=null;this.src='images/search.png';" 
									  class="img-thumbnail" style="margin-left:10px;width: 25px; height: 25px; cursor: pointer" 
									  data-toggle="modal" data-target="#proModal" onclick="openProLs();"/>
								</div>
								<div class="clearfix"></div>
							</div>
							<div id="selKeywords" class="" style="display:none;width:420px;margin-top:5px;margin-left:30px;"></div>
							<div class="tr_inline">
								<label class="td_item td_title">品类类别</label>
								<div class="td_item td_context">
									<select id="proCategory" name="proCategory" class="selectpicker form-control">
										<option value="">--请选择--</option>
									</select>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="tr_inline">
								<label class="td_item td_title">科组类别</label>
								<div class="td_item td_context">
									<select id="depCategory" name="depCategory" class="selectpicker form-control">
										<option value="">--请选择--</option>
									</select>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="tr_inline">
								<label class="td_item td_title">病种类别</label>
								<div class="td_item td_context">
									<select id="diseaseCategory" name="diseaseCategory" class="selectpicker form-control">
										<option value="">--请选择--</option>
									</select>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="tr_inline">
								<label class="td_item td_title">是否订购</label>
								<div id="isOrderDiv" class="td_item" style="padding-top:3px;">
									<input type="radio" id="isOrder1" name="isOrder" checked="checked" value="1" class="iCheckRadio">
									<label class="lab_normal" for="isOrder1" style="margin-right: 20px;">是</label>
									<input type="radio" id="isOrder2" name="isOrder" value="0" class="iCheckRadio">
									<label class="lab_normal" for="isOrder2">否</label>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="tr_inline">
								<label class="td_item td_title">未订购原因</label>
								<div class="td_item td_context">
									<select id="noOrderCause" name="noOrderCause" data-size="7" class="selectpicker form-control">
										<option value="">--请选择--</option>
										<option value="价格高">价格高</option>
										<option value="商品咨询">商品咨询</option>
										<option value="配送时间长">配送时间长</option>
										<option value="支付不成功">支付不成功</option>
										<option value="商品缺货">商品缺货</option>
										<option value="联系不上">联系不上</option>
										<option value="产品限销">产品限销</option>
										<option value="重复信息">重复信息</option>
										<option value="催单">催单</option>
										<option value="已购买">已购买</option>
										<option value="其它">其它</option>
									</select>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="tr_inline">
								<label class="td_item td_title">是否跟踪</label>
								<div id="isTrackDiv" class="td_item" style="padding-top:3px;">
									<input type="radio" id="isTrack1" name="isTrack" checked="checked" value="1" class="iCheckRadio">
									<label class="lab_normal" for="isTrack1" style="margin-right: 20px;">是</label>
									<input type="radio" id="isTrack2" name="isTrack" value="0" class="iCheckRadio">
									<label class="lab_normal" for="isTrack2">否</label>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="tr_inline" >
								<label class="td_item td_title">预约回访</label>
								<div class="td_item td_context">
									<input id="visitDate" name="visitDate" type="text" onfocus="WdatePicker({minDate:'%y-%M-%d'})" class="Wdate form-control item_input"/>
								</div>
								<div class="clearfix"></div>
							</div>
							<div id="remarkDiv" class="tr_inline" style="width:450px;">
								<label id="remark_lb" class="td_item td_title">备注</label>
								<div class="td_item">
									<textarea id="com_remark" name="remark" class="form-control comm_textarea" style="height:80px;"></textarea>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="tr_inline">
								<label class="td_item td_title">新用户</label>
								<div id="isNewUserDiv" class="td_item" style="padding-top:3px;">
									<input type="radio" id="isNewUser1" name="isNewUser" value="1" class="iCheckRadio">
									<label class="lab_normal" for="isNewUser1" style="margin-right: 20px;">是</label>
									<input type="radio" id="isNewUser2" name="isNewUser" value="0" class="iCheckRadio">
									<label class="lab_normal" for="isNewUser2">否</label>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
								<button type="button" id="submitBtn" onclick="saveCommu()" class="btn btn-primary">保存并添加任务</button>
							</div>
						</div>
						<div id="trackLs" class="tab-pane">
							<table id="track_tb" class="table table-bordered">
								<thead>
									<tr>
										<th style="width:80px;">沟通时间</th>
										<th>产品关键词</th>
										<th style="width:70px;">是否订购</th>
										<th style="width:90px;">未订购原因</th>
										<th style="width:100px;">下次沟通时间</th>
										<th style="width:70px;">客服姓名</th>
										<th style="width:70px;">客服ID</th>
										<th>跟踪信息</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</div>
						<div id="healthDoc" class="tab-pane">
							<h5 style="margin-left:10px;">使用人信息</h5>
							<div class="tr_inline">
								<div class="td_item td_title2">称谓</div>
								<div class="td_item">
									<input id="" name="" type="text" value="" class="form-control" style="float:left;width:120px;"/>
									<img class="img-thumbnail" alt="查询关键词" title="查询关键词" src="images/search.png" 
										onerror="this.onerror=null;this.src='images/search.png';" 
									  	style="float:left;margin-left:10px;width: 30px; height: 30px; cursor: pointer"/>
									<div class="clearfix"></div>
								</div>
								<div class="td_item td_title2">使用人姓名</div>
								<div class="td_item td_context2">
									<input id="" name="" type="text" value=""  class="form-control"/>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="tr_inline">
								<div class="td_item td_title2">联系电话</div>
								<div class="td_item td_context2">
									<input id="" name="" type="text" value=""  class="form-control"/>
								</div>
								<div class="td_item td_title2">性别</div>
								<div id="" class="td_item td_context2">
									<select id="" name="" class="selectpicker form-control">
										<option value="">--请选择--</option>
										<option value="1">男</option>
										<option value="2">女</option>
									</select>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="tr_inline">
								<div class="td_item td_title2">出生地</div>
								<div class="td_item td_context2">
									<input id="" name="" type="text" value=""  class="form-control"/>
								</div>
								<div class="td_item td_title2">籍贯</div>
								<div class="td_item td_context2">
									<input id="" name="" type="text" value=""  class="form-control"/>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="tr_inline">
								<div class="td_item td_title2">民族</div>
								<div class="td_item td_context2">
									<input id="" name="" type="text" value=""  class="form-control"/>
								</div>
								<div class="td_item td_title2">婚姻</div>
								<div class="td_item td_context2">
									<select id="" name="" class="selectpicker form-control">
										<option value="">--请选择--</option>
										<option value="1">已婚</option>
										<option value="2">未婚</option>
									</select>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="tr_inline">
								<div class="td_item td_title2">医保类型</div>
								<div id="" class="td_item td_context2">
									<select id="" name="" class="selectpicker form-control">
										<option value="">--请选择--</option>
										<option value="1">自费</option>
										<option value="2">省医保</option>
										<option value="3">市医保</option>
									</select>
								</div>
								<div class="td_item td_title2">医保卡号</div>
								<div id="" class="td_item td_context2">
									<input id="" name="" type="text" value=""  class="form-control"/>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="tr_inline">
								<div class="td_item td_title2">单位</div>
								<div id="" class="td_item td_context2">
									<input id="" name="" type="text" value=""  class="form-control"/>
								</div>
								<div class="td_item td_title2">职业</div>
								<div class="td_item td_context2">
									<input id="" name="" type="text" value=""  class="form-control"/>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="tr_inline">
								<div class="td_item td_title2">家族史</div>
								<div id="" class="td_item td_context2">
									<input id="" name="" type="text" value=""  class="form-control"/>
								</div>
								<div class="td_item td_title2">过敏史</div>
								<div class="td_item td_context2">
									<input id="" name="" type="text" value=""  class="form-control"/>
								</div>
								<div class="clearfix"></div>
							</div>
							<div>
								<button class="btn btn-primary" style="float:right;margin-right:60px;">保存</button>
								<div class="clearfix"></div>
							</div>
							<hr/>
							<h5 style="margin-left:10px;">就诊信息</h5>
							<div class="tr_inline">
								<div class="td_item td_title2">患病描述</div>
								<div id="" class="td_item td_context2">
									<input id="" name="" type="text" value="" class="form-control"/>
								</div>
								<div class="td_item td_title2">处方编号</div>
								<div class="td_item td_context2">
									<input id="" name="" type="text" value="" class="form-control"/>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="tr_inline">
								<div class="td_item td_title2">就诊医院</div>
								<div id="" class="td_item td_context2">
									<input id="" name="" type="text" value="" class="form-control"/>
								</div>
								<div class="td_item td_title2">处方医生</div>
								<div class="td_item td_context2">
									<input id="" name="" type="text" value="" class="form-control"/>
								</div>
								<div class="clearfix"></div>
							</div>
							<div>
								<button class="btn btn-primary" style="float:right;margin-right:60px;">保存</button>
								<div class="clearfix"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="proModal" tabindex="-1" role="dialog" aria-labelledby="proModalLabel" aria-hidden="true" data-backdrop='static'>
		<div class="modal-dialog" style="width:1000px;">
			<div class="modal-content" >
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="proModalLabel">选择产品关键词</h4>
				</div>
				<div class="modal-body">
					<div style="float:left;">
						<div id="selPros" style="width:740px;margin-bottom:10px;">
							<div style="float:left;margin:7px 0 0 5px;">已选关键词：</div>
							<div class="clearfix"></div>
						</div>
						<table>
							<tr>
								<td align="left" style="width: 45px;">套餐ID:</td>
								<td align="left" style="width: 160px;">
								    <input type="text" id="prodId" name="prodId" value="" class="form-control" placeholder="请输入套餐ID"/>
								</td>
								<td style="width: 10px;">&nbsp;&nbsp;</td>
								<td style="width: 60px;">套餐名称:</td>
								<td style="width: 160px;">
								    <input type="text" id="prodNm" name="prodNm" value="" class="form-control" placeholder="请输入套餐名称" />
								</td>
								<td style="width: 10px;">&nbsp;&nbsp;</td>
								<td style="width: 60px;">商品编码:</td>
								<td style="width: 160px;">
								    <input type="text" id="prodNo" name="prodNo" value="" class="form-control" placeholder="请输入商品编码"/>
								</td>
								<td style="width: 10px;">&nbsp;&nbsp;</td>
								<td>
								    <input type="button" value="查询" onclick="search_product();" class="btn btn-primary" />
								</td>
							</tr>
						</table>
						<table id="pro_tb" style="width:740px;margin-top:10px;" class="table table-striped table-bordered">
							<thead>
								<tr>
									<th style="width:50px;">选择</th>
									<th style="width:60px;">套餐ID</th>
									<th>套餐名称</th>
									<th style="width:120px;">主商品编码</th>
									<th style="width:90px;">主商品规格</th>
									<th style="width:60px;">单价</th>
									<th style="width:60px;">库存量</th>
									<th style="width:85px;">处方药类型</th>
								</tr>
							</thead>
							<tbody id="product_body"></tbody>
						</table>
						<div> 
							<div style="text-align: right;padding-right:15px;"> 
								共<label id="lblToatl">0</label>条数据&nbsp;&nbsp;第[<label id="lblCurent">0</label>]页/共[<label id="lblPageCount">0</label>]页 
								&nbsp;&nbsp;<a id="first" href="#">首页</a>&nbsp;&nbsp;<a id="previous" href="#">上一页</a>&nbsp;&nbsp;<a id="next" href="#">下一页</a>
								&nbsp;&nbsp;<a id="last" href="#">末页</a>&nbsp;&nbsp;
								转到第<input id="inputCurent" type="text" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" style="width:50px;"/>页 
								&nbsp;&nbsp;<a id="gopage" href="#">跳转</a> 
							</div> 
						</div>
						<div id="relate_meal" style="display:none;">
							<h4>父子套餐</h4>
							<table id="relate_meal_tb" style="width:740px;margin-top:10px;" class="table table-striped table-bordered">
								<thead>
									<tr>
										<th style="width:50px;">选择</th>
										<th style="width:60px;">套餐ID</th>
										<th>套餐名称</th>
										<th style="width:120px;">主商品编码</th>
										<th style="width:90px;">主商品规格</th>
										<th style="width:60px;">单价</th>
										<th style="width:60px;">库存量</th>
										<th style="width:85px;">处方药类型</th>
									</tr>
								</thead>
								<tbody id="relate_meal_body"></tbody>
							</table>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			       			<button type="button" class="btn btn-primary" onclick="selPros()">确定</button>  
						</div>
					</div>
					<div style="float:left;width:200px;margin-left:15px;border:1px solid #DDD;">
						<div class="pro_h">市场信息</div>
						<div class="pro_ct"></div>
						<div class="pro_h">推销技巧（商品话术）</div>
						<div class="pro_ct"></div>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div> 
	</div>
	
	<div class="modal fade" id="skuModal" tabindex="-1" role="dialog" aria-labelledby="skuModalLabel" aria-hidden="true" data-backdrop='static'>
		<div class="modal-dialog">
			<div class="modal-content" >
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="skuModalLabel">选择商品</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" id="opsku" value=""/>
					<table id="sku_tb" class="table table-striped table-bordered">
						<thead>
							<tr>
								<th style="width:50px;">选择</th>
								<th style="width:60px;">套餐ID</th>
								<th style="width:120px;">主商品编码</th>
								<th style="width:180px;">主商品规格</th>
								<th style="width:60px;">单价</th>
								<th style="width:60px;">库存量</th>
							</tr>
						</thead>
						<tbody id="sku_body"></tbody>
					</table>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
		       			<button type="button" class="btn btn-primary" onclick="slcedSku()">确定</button>  
					</div>
				</div>
			</div>
		</div> 
	</div>
</div>
