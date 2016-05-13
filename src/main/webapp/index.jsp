<%@ page language="java" import="com.haoyao.goods.model.Permission,java.util.List" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=9;">
<title>运营中心后台</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/media/css/style.css" />
<link href="<%=request.getContextPath()%>/media/css/bootstrap.min.css" rel="stylesheet" />
<script src="<%=request.getContextPath()%>/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/media/js/jquery.accordion.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap/bootstrap.min.js"></script>




<style type="text/css">
.tree-on{display:none;float:left;font-size:18px;width:10px;line-height:60px;border-top:1px solid #ccc;background-color:#006699;cursor:pointer;color:#fff;}
.tree-off{float:right;font-size:18px;padding:0 10px;font-weight:normal;cursor:pointer;}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#acc1").accordion({
			alwaysOpen: false,
			autoheight: false,
			header: 'a.acc1',
			clearStyle: true,
			active: false
		});
		
		$("#tree-off").click(function(){
			$("#treeDiv").hide();
			$("#treeDiv").parent().css("width","15px");
			$("#tree-on").show();
			$("#r_content").css("margin-left","15px");
		});
		$("#tree-on").click(function(){
			$("#treeDiv").show();
			$("#treeDiv").parent().css("width","204px");
			$("#tree-on").hide();
			$("#r_content").css("margin-left","210px");
		});
		 //$('.dropdown-toggle').dropdown();
		 $('#menu').dropdown('toggle'); 
	});
</script>
</head>

<body>
	<form id="form" method="post">
		<div class="full">
			<!--页面内容开始-->
			<div class="top" style="height: 50px;">
				<h3 class="pull-left" style="margin-bottom:auto;">&nbsp;运营中心后台</h3>
				
				<div class="pull-right">
					<span style="line-height: 33px; height: 33px; vertical-align: middle">欢迎您
						<font color="#FF6600"><sec:authentication property="name" /></font>&nbsp;&nbsp;&nbsp;
						<sec:authorize ifAnyGranted="/main.jsp">
							<a href="<%=request.getContextPath()%>/main.jsp" target="right">首页</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
						</sec:authorize>
						<a href="<%=request.getContextPath()%>/password.do?method=data" target="right">修改密码</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
						<a href="<%=request.getContextPath()%>/logout">退出</a>
					</span>
				</div>
				<div class="pull-right" style="margin-right:20px;">
					<div class="dropdown" style="height:50px;">  
						<a id="drop1" href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">切换系统 <b class="caret"></b></a>  
						<ul class="dropdown-menu" role="menu" aria-labelledby="drop1">  
							<li><a tabindex="-1" href="http://219.139.241.75:8082/hys-report" target="_self">报表中心</a></li>  
							<li><a tabindex="-1" href="#">商品中心</a></li>  
						</ul>  
					</div>  
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="content_all">
				<!--主内容开始-->
				<div class="list_a">
					<div id="treeDiv" class="list_a_2">
						<div class="left_menu">
							<div class="caidandazi">
								<div style="float:left;margin-left:50px;;">运营中心</div>
								<div id="tree-off" class="tree-off" title="收缩">&laquo;</div>
							</div>
							<div class="clears"></div>
							<ul id="acc1" class="ui-accordion-container">
								<sec:authorize ifAnyGranted="/member.do,/member.do?method=getMemberList">
									<li><a href="#" class="ui-accordion-link acc1">客户管理</a>
										<div>
											<sec:authorize ifAnyGranted="/member.do,/member.do?method=getMemberList">
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/member.do?method=getMemberList" target="right">档案管理</a></div>
											</sec:authorize>
											<div class="clears"></div>
										</div>
									</li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="/auditPAOrder.do,/auditPAOrder.do?method=goPAOrder,/telGoods.do,/telGoods.do?method=getTelGoodsLs,/callerScreen2.do,/callerScreen2.do?method=goAddOrder,/callerScreen2.do?method=goOfficialOrder,/orderInfo.do,/orderInfo.do?method=getInfo,/taskAllocate.do,/taskAllocate.do?method=task,/payOrder.do,/payOrder.do?method=showOrderPay,/returnGoods.do,/returnGoods.do?method=getReturnGoodsInfo">
									<li><a href="#" class="ui-accordion-link acc1">客服业务</a>
										<div>
											<sec:authorize ifAnyGranted="/telGoods.do,/telGoods.do?method=getTelGoodsLs">
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/telGoods.do?method=getTelGoodsLs" target="right">电销商品</a></div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="/callerScreen2.do,/callerScreen2.do?method=goAddOrder">
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/callerScreen2.do?method=goAddOrder" target="right">在线工单</a></div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="/callerScreen2.do,/callerScreen2.do?method=goOfficialOrder">
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/callerScreen2.do?method=goOfficialOrder" target="right">官网订单管理</a></div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="/auditPAOrder.do,/auditPAOrder.do?method=goPAOrder">
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/auditPAOrder.do?method=goPAOrder" target="right">三方平台订单审核</a></div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="/orderInfo.do,/orderInfo.do?method=getInfo">
												<script type="text/javascript" src="<%=request.getContextPath()%>/js/opcenter/jsdMsg.js"></script>
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/orderInfo.do?method=getInfo" target="right">极速达订单</a></div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="/taskAllocate.do,/taskAllocate.do?method=task">
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/taskAllocate.do?method=task" target="right">任务分配</a></div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="/returnGoods.do,/returnGoods.do?method=getReturnGoodsInfo">
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/returnGoods.do?method=getReturnGoodsInfo" target="right">退货审批</a></div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="/thirdOrderAudit.do,/thirdOrderAudit.do?method=showOrderInfos">
												<div class="left_menu_a" ><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/thirdOrderAudit.do?method=showOrderInfos" target="right">客服一级审核</a></div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="/thirdOrderSecondAudit.do,/thirdOrderSecondAudit.do?method=showOrderInfos">
												<div class="left_menu_a" ><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/thirdOrderSecondAudit.do?method=showOrderInfos" target="right">药师二级审核</a></div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="/decoctOrder.do,/decoctOrder.do?method=showOrderInfos">
												<div class="left_menu_a" ><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/decoctOrder.do?method=showOrderInfos" target="right">煎药处订单管理</a></div>
											</sec:authorize>
											
											<sec:authorize ifAnyGranted="/payOrder.do,/payOrder.do?method=showOrderPay">
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/payOrder.do?method=showOrderPay" target="right">订单查询</a></div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="/CMOrderAudit.do,/CMOrderAudit.do?method=showOrderInfos">
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/CMOrderAudit.do?method=showOrderInfos" target="right">中药订单审核管理</a></div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="/CMReplaceOrder.do,/CMReplaceOrder.do?method=showOrderInfos">
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/CMReplaceOrder.do?method=showOrderInfos" target="right">中药代下单管理</a></div>
											</sec:authorize>
											<div class="clears"></div>
										</div>
									</li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="/doctorInHospital.do,/doctorInHospital.do?method=showAllSalesRep,/doctorInHospital.do,/doctorInHospital.do?method=showAllDoctor,/doctorInHospital.do,/doctorInHospital.do?method=showAllDoctorUrl">								
										<li><a href="#" class="ui-accordion-link acc1">医院管理</a>
										<div>
											<sec:authorize ifAnyGranted="/doctorInHospital.do,/doctorInHospital.do?method=showAllSalesRep">
												<script type="text/javascript" src="<%=request.getContextPath()%>/js/opcenter/jsdMsg.js"></script>
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span>  <a href="<%=request.getContextPath()%>/doctorInHospital.do?method=showAllSalesRep" target="right">医院信息</a></div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="/doctorInHospital.do,/doctorInHospital.do?method=showAllDoctor">
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/doctorInHospital.do?method=showAllDoctor" target="right">医生信息</a></div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="/doctorInHospital.do,/doctorInHospital.do?method=showAllDoctorUrl">
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span><a href="<%=request.getContextPath()%>/doctorInHospital.do?method=showAllDoctorUrl" target="right">医生二维码</a></div>
											</sec:authorize>
											<div class="clears"></div>
										</div>
									</li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="/communicateReport.do,/communicateReport.do?method=getCommunicationList,/tradeReport.do,/tradeReport.do?method=getTradeRecords,/nowTaskReport2.do,/nowTaskReport2.do?method=getTaskList">
									<li><a href="#" class="ui-accordion-link acc1">统计查询</a>
										<div>
											<sec:authorize ifAnyGranted="/communicateReport.do,/communicateReport.do?method=getCommunicationList">
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/communicateReport.do?method=getCommunicationList" target="right">沟通记录查询</a></div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="/nowTaskReport2.do,/nowTaskReport2.do?method=getTaskList">
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/nowTaskReport2.do?method=getTaskList" target="right">今日任务查询</a></div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="/tradeReport.do,/tradeReport.do?method=getTradeRecords">
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/tradeReport.do?method=getTradeRecords" target="right">购买记录查询</a></div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="/custServStatistics.do,/custServStatistics.do?method=getStatisticsList">
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/custServStatistics.do?method=getStatisticsList" target="right">产品咨询日报</a></div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="/thirdOrderAuditReport.do,/thirdOrderAuditReport.do?method=showOrderInfos">
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/thirdOrderAuditReport.do?method=showOrderInfos" target="right">处方审核报表</a></div>
											</sec:authorize>
											<div class="clears"></div>
										</div></li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="/rule/setup.do">
									<li><a href="#" class="ui-accordion-link acc1">短信管理</a>
										<div>
											<sec:authorize ifAnyGranted="/rule/setup.do">
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/rule/setup.do" target="right">短信设置</a></div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="/rule/smsReport.do?flag=1">
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/rule/smsReport.do?flag=1" target="right">短信统计</a></div>
											</sec:authorize>
											<div class="clears"></div>
										</div></li>
								</sec:authorize>

								<sec:authorize ifAnyGranted="/user.do,/role.do,/permission.do,/org.do,/applyPlatform.do,/user.do?method=user,/role.do?method=role,/permission.do?method=permission,/org.do?method=organization,/applyPlatform.do?method=getApplyPlatformList">
									<li><a href="#" class="ui-accordion-link acc1">系统管理</a>
										<div>
											<sec:authorize ifAnyGranted="/user.do,/user.do?method=user">
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/user.do?method=user" target="right">用户管理</a></div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="/role.do,/role.do?method=role">
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/role.do?method=role" target="right">角色管理</a></div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="/permission.do,/permission.do?method=permission">
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/permission.do?method=permission" target="right">资源管理</a></div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="/org.do,/org.do?method=organization">
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/org.do?method=organization" target="right">机构管理</a></div>
											</sec:authorize>
											<sec:authorize ifAnyGranted="/applyPlatform.do,/applyPlatform.do?method=getApplyPlatformList">
												<div class="left_menu_a"><span class="glyphicon glyphicon-play small"></span> <a href="<%=request.getContextPath()%>/applyPlatform.do?method=getApplyPlatformList" target="right">应用管理</a></div>
											</sec:authorize>
											<div class="clears"></div>
										</div></li>
								</sec:authorize>		
							</ul>
						</div>
						
					</div>
					<div id="tree-on" class="tree-on" title="展开">&raquo;</div>
				</div>
				<div id="r_content" class="right_a">
					<sec:authorize ifAnyGranted="/main.jsp">
						<iframe src="main.jsp" name="right" id="mainFrame" width="100%" frameborder="0" scrolling="no"></iframe>
					</sec:authorize>
					<sec:authorize ifNotGranted="/main.jsp" >
					  	<iframe  name="right" id="mainFrame" width="100%" frameborder="0" scrolling="no"></iframe>
					 </sec:authorize>
				</div>
				<!--主内容结束-->
			</div>
			<!--页面内容结束-->
			<div class="clears"></div>
		</div>
	</form>
	<script type="text/JavaScript">
		function reinitIframe() {
			var iframe = document.getElementById("mainFrame");
			try {
				var bHeight = iframe.contentWindow.document.body.scrollHeight;
				var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
				if (dHeight < 1000) {
					dHeight = 1000;
				}
				var height = Math.min(bHeight, dHeight);
				iframe.height = height;
			} catch (ex) {
			}
		}
		window.setInterval("reinitIframe()", 3000);
	</script>
</body>
</html>
