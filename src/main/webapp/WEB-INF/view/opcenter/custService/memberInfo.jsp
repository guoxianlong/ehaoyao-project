<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 加载CSS样式文件 -->
<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-select.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-theme.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
<style type="text/css">
	.table thead tr th{font-size:14px;background-color: #f9f9f9;}
	input.item_input {height: 30px;}
	.item_title {text-align: right;width: auto;padding-top: 5px;margin-left: 15px;}
	.sear_btn{margin-left: 20px; padding: 0 12px; height: 30px;}
	.select_set + div button.selectpicker{height:30px;padding-top:0;padding-bottom:0;}
	
	.item_title4 {width: 70px;font-weight:normal;text-align: right;margin-left: 5px;padding-top: 5px;}
	.item_title5 {width: 25px;font-weight:normal;text-align: right;padding-top: 5px;}
	.msg{
	 	z-index: 9999;
	    position: fixed;
	    left: 80px;
	    top: 300px;
	    width: 120px;
	    min-height:40px;
		background-color: #03C440;
	    opacity: .8;
	    border-radius: 5px;
	    text-align: center;
	    color: #fff;
	    font-size: 14px;
	    border:1px solid #47A447;
	    padding:10px;
    }
    
    a,img {
		border: 0;
	}
	
	/* select */
	.select {
		padding: 5px 10px;
		border: #ddd 1px solid;
		border-radius: 4px;
		font-size: 12px
	}
	
	.select li {
		list-style: none;
		padding: 10px 0 5px 100px
	}
	
	.select .select-list {
		border-bottom: #eee 1px dashed
	}
	
	.select dl {
		zoom: 1;
		position: relative;
		line-height: 24px;
	}
	
	.select dl:after {
		content: " ";
		display: block;
		clear: both;
		height: 0;
		overflow: hidden
	}
	
	.select dt {
		width: 100px;
		margin-bottom: 5px;
		position: absolute;
		top: 0;
		left: -100px;
		text-align: right;
		color: #666;
		height: 24px;
		line-height: 24px
	}
	
	.select dd {
		float: left;
		display: inline;
		margin: 0 0 5px 5px;
	}
	
	.select a {
		display: inline-block;
		white-space: nowrap;
		height: 24px;
		padding: 0 10px;
		text-decoration: none;
		color: #039;
		border-radius: 2px;
		padding-top: 4px;
	}
	
	.select a:hover {
		color: #f60;
		background-color: #f3edc2
	}
	
	.select .selected a {
		color: #fff;
		background-color: #f60
	}
	
	.select-result dt {
		font-weight: bold
	}
	
	.select-no {
		color: #999
	}
	
	.select .select-result a {
		padding-right: 20px;
		background: #f60 right 9px no-repeat
	}
	
	.select .select-result a:hover {
		background-position: right -15px
	}
</style>

<!-- 加载javascript文件 -->
<script>
    $(document).ready(function(){
    	if ($(".select-result dd").length > 1) {
	       $(".select-no").hide();
	   } else {
		   $(".select-no").show();
	   }
    });
</script>

<div>
   <%--  <c:if test="${member != null}"> --%>
	<%-- <div class="nav-tabs" style="padding-bottom: 15px;">
		<h4 style="font-weight: bold;">${member.memberName}的个人信息</h4>
	</div> --%>
	<div style="float:left;width:60%;">
		<div class="right_inline">
			<div class="right_input item_title4">用户名：</div>
			<div class="right_input" style="width:200px">
				<input id="memberName" value="${member.memberName}" type="text" readonly class="form-control item_input"/>
			</div>
			<div class="right_input item_title4">性别：</div>
			<div class="right_input" style="width:130px">
				<c:choose> 
					<c:when test="${member.sex=='1'}">   
						<input id="sex" name="sex" value="男" type="text" readonly class="form-control item_input"/>
					</c:when> 
					<c:when test="${member.sex=='2'}">   
						<input id="sex" name="sex" value="女" type="text" readonly class="form-control item_input"/>
					</c:when> 
					<c:when test="${member.sex=='0'}">   
						<input id="sex" name="sex" value="未知" type="text" readonly class="form-control item_input"/>
					</c:when> 
					<c:otherwise>   
						<input id="sex" name="sex"  type="text" readonly class="form-control item_input"/>
					</c:otherwise> 
				</c:choose> 
			</div>
			<div style="clear:both;"></div>
		</div>
		<div class="right_inline">
			<div class="right_input item_title4">昵称：</div>
			<div class="right_input" style="width: 200px">
				<input id="nickName" value="${member.nickName}" type="text" readonly class="form-control item_input"/>
			</div>
			<div class="right_input item_title4">年龄：</div>
			<div class="right_input" style="width: 100px">
				<input id="age" value="${member.age}" type="text" readonly class="form-control item_input"/>
			</div>
			<div class="right_input item_title5">岁</div>
			<div style="clear:both;"></div>
		</div>
		<div class="right_inline">
			<div class="right_input item_title4">真实姓名：</div>
			<div class="right_input" style="width: 200px">
				<input id="nickName" value="${member.realName}" type="text" readonly class="form-control item_input"/>
			</div>
			<div class="right_input item_title4">生日：</div>
			<div class="right_input" style="width: 130px">
				<input id="age" value="${member.birthday}" type="text" readonly class="form-control item_input"/>
			</div>
			<div style="clear:both;"></div>
		</div>
		<div class="right_inline">
			<div class="right_input item_title4"> 居住于：</div>
			<div class="right_input" style="width:125px">
				<input id="userName" name="userName" value="${member.province}" type="text" readonly class="form-control item_input"/>
			</div>
			<div class="right_input" style="margin-left:15px; width:125px">
				<input id="userName" name="userName" value="${member.city}" type="text" readonly class="form-control item_input"/>
			</div>
			<div class="right_input" style="margin-left:15px; width:125px">
				<input id="userName" name="userName" value="${member.distrct}" type="text" readonly class="form-control item_input"/>
			</div>
			<div style="clear:both;"></div>
		</div>
		<div class="right_inline">
			<div class="right_input item_title4">会员等级：</div>
			<div class="right_input" style="width:125px">
				<input id="userName" name="userName" value="${memberGrade}" type="text" readonly class="form-control item_input"/>
			</div>
			<div style="clear:both;"></div>
		</div>
			<div class="right_inline">
			<div class="right_input item_title4">手机号码：</div>
			<div class="right_input" style="width: 125px">
				<input id="nickName" value="${member.tel}" type="text" readonly class="form-control item_input"/>
			</div>
			<div class="right_input item_title4">邮箱：</div>
			<div class="right_input" style="width: 300px">
				<input id="age" value="${member.email}" type="text" readonly class="form-control item_input"/>
			</div>
			<div style="clear:both;"></div>
		</div>
	</div>
	<div style="float:left;width:20%;">
		<img class="img-thumbnail" src="${member.headImageUrl!=null && member.headImageUrl!='' && member.headImageUrl!='null'?member.headImageUrl:'http://design.ehaoyao.com/static/images/default.jpg'}"
			onerror="this.src='http://design.ehaoyao.com/static/images/default.jpg';this.onerror=null;" alt="头像"
			style="width: 120px; height: 120px;" />
	</div>
	<div style="clear:both;"></div>
	<hr>
	<!-- <div class="right_inline">
		<div class="right_input item_title">健康信息：</div>
	</div> -->
	<div style="height: 30px;">
		<div class="right_inline">
			<div class="right_input item_title">健康信息：</div>
		</div> 
	</div>
	<p>
	<ul class="select">
		<li class="select-result">
			<dl>
				<dt>已选条件：</dt>
				<dd class="select-no">暂时没有选择过滤条件</dd>
				<c:forEach items="${tempDetailList}" var="temp">
				    <dd id="view${temp.id}" class="selected" onclick='removeSelect("${temp.id}")'>
						<a id="item1">${temp.keyword}</a>
				    </dd>
				</c:forEach>
			</dl>
		</li>
		<c:forEach items="${healthList}" var="item">
			<li class="select-list">
				<dl id="select1">
					<dt>${item.className}</dt>
					<c:forEach items="${item.healthDetail}" var="items">
						<dd id="${items.id}">
							<a id="item${items.id}">${items.keyword}</a>
						</dd>
					</c:forEach>
				</dl>
			</li>
		</c:forEach>
	</ul>
	<%-- <div class="right_inline">
		<div class="right_input item_title">优惠券：</div>
	</div>
	<table id="buy_table" class="table table-bordered" style="margin-left: 40px; width: 95%;">
		<thead>
			<tr>
				<th>序号</th>
				<th>优惠券名称</th>
				<th>说明</th>
				<th>号码</th>
				<th>截止日期</th>
			</tr>
		</thead>
		 <tbody id="tb_body">
			<c:forEach items="${bLs}" varStatus="status" var="buy">
			<tr>
				<td>${status.count}</td>
				<td>${buy.orderTime }</td>
				<td>${buy.orderNum }</td>
				<td>${buy.goodsArea }</td>
				<td>${buy.goodsArea }</td>
			</tr>
			</c:forEach>
			<c:if test="${bLs==null}">
				<tr>
					<td colspan="5" style="height:80px;text-align: center;vertical-align: middle;font-size:14px;font-weight: bold;">暂无数据！</td>
				</tr>
			</c:if>
		</tbody>
	</table> --%>
	<div style="clear:both;"></div>
	
	<%-- <div class="form-horizontal col-sm-4">
	    <div class="form-group">
	        <label class="col-sm-4 control-label">用户名：</label>
	        <p class="col-sm-7 form-control-static" id="tel">${member.memberName}</p>
	    </div>
	    <div class="form-group">
	        <label class="col-sm-4 control-label">昵称：</label>
	        <div class="col-sm-6">
	            <input type="text" class="form-control" disabled value="${member.nickName}" />
	        </div>
	    </div>
	    <div class="form-group">
	        <label class="col-sm-4 control-label">性别：</label>
			<div class="col-sm-8">
				<label class="radio-inline"> 
				    <input type="radio" name="inlineRadioOptions" disabled id="inlineRadio1" value="1" ${"1"==member.sex?'checked="checked"':''} >男
				</label> 
				<label class="radio-inline">
				    <input type="radio" name="inlineRadioOptions" disabled id="inlineRadio2" value="2" ${"2"==member.sex?'checked="checked"':''}>女
				</label>
			</div>
		</div>
	    <div class="form-group">
	        <label class="col-sm-4 control-label">年龄：</label>
	        <div class="col-sm-6">
	            <input type="text" class="form-control" disabled value="${member.age}" />
	        </div>
	        <p class="form-control-static">岁</p>
	    </div>
	    <div class="form-group">
	        <label class="col-sm-4 control-label">邮箱：</label>
	        <p class="col-sm-7 form-control-static" id="tel">${member.email}</p>
	    </div>
	    <div class="form-group">
	        <label class="col-sm-4 control-label">手机：</label>
	        <p class="col-sm-7 form-control-static" id="tel">${member.tel}</p>
	    </div>
	</div>
	<div class="form-horizontal col-sm-3" style="padding-top: 15px;padding-bottom: 15px;">
		<div style="padding-top: 15px;">
		    <c:if test="${member.headImageUrl != 'null'}">
			    <img alt="" src="${member.headImageUrl}">
		    </c:if>
		    <c:if test="${member.headImageUrl == 'null'}">
			    <img alt="" src="<%=request.getContextPath()%>/img/head.jpg">
		    </c:if>			
		</div>
	</div>
	<div class="form-horizontal col-sm-4" style="padding-top: 15px;padding-bottom: 15px;">
	    <div class="panel-body" style="border: 1px solid #d2eaf1;background: #d2eaf1;color: #4fc1e9;">
	        <h3>绑定手机后</h3>
	        <p style="text-indent: 2em;">我们将你通过京东好药师健康商城, 天猫好药师大药房旗舰店, 1号店好药师健康馆所下的订单打通. 以便为你提供专业的药师建议及用药咨询等服务</p>
	        <p style="text-indent: 2em;">绑定手机同时可将官网同微信服务号好药师打通, 通过微信查看官网购物车,快递信息及用药提醒</p>
	        <p style="text-indent: 2em;">我们的隐私条款将保护你的隐私不被泄露和第三方使用</p>
	    </div>
	    <p />
	    <div class="panel-body" style="border: 1px solid #d2eaf1;background: #d2eaf1;color: #4fc1e9;">
	        <p style="text-indent: 2em;">询问你(包括你所许可的家人)的健康信息, 仅仅为了向你推荐更合适的医药及健康产品,推荐更适合你的健康资讯. 并在你进行药师咨询时提供若干必要的辅助信息</p>	    
	    </div>
	</div> --%>
	<%-- </c:if> --%>
	<%-- <c:if test="${member==null}">
	    <div style="height:100px;text-align: center;vertical-align: middle;font-size:14px;font-weight: bold;">暂无数据！</div>
	</c:if> --%>
</div>