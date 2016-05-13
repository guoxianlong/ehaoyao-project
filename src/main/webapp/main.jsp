<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1 maximum-scale=1, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>首页</title>
<!-- 加载CSS样式文件 -->
<link type="text/css" href="<%=request.getContextPath()%>/css/bootstrap/bootstrap-theme.css" rel="stylesheet">
<link type="text/css" href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
<link type="text/css" href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">

<!-- 加载javascript文件 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap/respond.min.js"></script>

<style type="text/css">
	#todayTask{color:#555;font-size:18px;font-weight:bold;text-align:center; margin-bottom:0;}
	#version tbody{font-size:14px;}
	#version td:not(:last-child){text-align:center;}
	#version p{font-family:'宋体';margin-bottom:0;}
</style>
</head>

<body style="padding-top:10px;">
	<div class="right_box">
	    <sec:authorize ifAnyGranted="/nowTaskReport2.do?method=getTaskList">
	    	<script type="text/javascript">
	    		$(document).ready(function(){
	    			$.ajax({
	    				 type: "POST",
	    	             url: "nowTaskReport2.do?method=todayTask",
	    	             dataType: "json",
	    	             data: {},
	    	             success: function(res){
	    	            	 if(res!=null){
	    	            		 if(res.dataLs!=null && res.dataLs.length>0){
		    	            		 var tr = $("#todayTask tr:eq(1)");
		    	            		 tr.find("td:eq(1)").text(res.dataLs[0].count);
		    	            		 tr.find("td:eq(2)").text(res.dataLs[0].completeCount);
		    	            		 tr.find("td:eq(3)").text(res.dataLs[0].uncompleteCount);
		    	            		 if(res.length>1){
			    	            		 tr = $("#todayTask tr:eq(2)");
			    	            		 tr.find("td:eq(1)").text(res.dataLs[1].count);
			    	            		 tr.find("td:eq(2)").text(res.dataLs[1].completeCount);
			    	            		 tr.find("td:eq(3)").text(res.dataLs[1].uncompleteCount);
		    	            		 }
	    	            		 }
	    	            		 $("#queryTime").text(res.queryTime);
	    	            	 }
	    	             }
	    			});
	    			
	    		});
	    	</script>
			<div style="width:500px;border:1px solid #ddd; border-radius:4px;">
				<div style="background-color:#F2F2F2;border-bottom:1px solid #ddd;padding:5px;">今日任务</div>
				<div class="pull-left" style="width:420px;border-right:1px solid #ddd;padding:10px;">
					<table id="todayTask" class="table table-bordered">
						<tr>
							<td></td>
							<td>总数</td>
							<td>已完成</td>
							<td>未完成</td>
						</tr>
						<tr>
							<td>部门</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
						</tr>
						<tr>
							<td>个人</td>
							<td>0</td>
							<td style="color:green;">0</td>
							<td style="color:red;">0</td>
						</tr>
					</table>
				</div>
				<div class="pull-left" style="padding:10px;">
					<a href="nowTaskReport2.do?method=getTaskList">
						<button class="btn btn-primary btn-lg" style="width:50px;font-weight: bold;">执<br/>行<br/>任<br/>务</button>
					</a>
				</div>
				<div class="clearfix"></div>
			</div>
			<div style="margin-top:10px;">
				<span>查询时间：</span>
				<span id="queryTime" style="color:red;"></span>
			</div>
			<div style="margin-top:10px;">
				<h1 style="color:red;">为配合运营中心整理权限,增加用户密码强度,请各位用户必须在一周之内更改密码，否则进行冻结处理!!!</h1>
			</div>
		</sec:authorize>
		<div>
			<h3>运营中心版本更新日志</h3>
			<table id="version" class="table table-bordered">
				<thead>
					<tr style="background-color: #f9f9f9;">
						<td style="width:120px;">版本号</td>
						<td style="width:120px;">更新日期</td>
						<td>更新内容</td>
					</tr>
				</thead>
				<tbody>
				
			     	<tr>
						<td>V1.2.22</td>
						<td>2016-5-3</td>
						<td><p>
								1、增加订单信息查询功能<br/>
							</p>
						</td>
					</tr>
					<tr>
						<td>V1.2.21</td>
						<td>2016-04-10</td>
						<td><p>
								1、上线1号店处方药，同天猫处方药一样，需客服/药师审核。<br/>
							</p>
						</td>
					</tr>
					<tr>
						<td>V1.2.20</td>
						<td>2016-03-23</td>
						<td><p>
								1、在沟通记录查询增加 产品编码、产品关键字查询<br/>
								2、在沟通记录查询,隐藏部分不常用的查询条件、优化沟通记录显示<br/>
								3、修改密码、增加用户、修改用户界面,增强密码强度验证<br/>
								4、平安处方药/掌上糖医等三方渠道处方药订单，只需药师审核<br/>
								5、原《三方平台订单审核(平安处方药)》审核功能，移植至《药师二级审核》<br/>
							</p>
						</td>
					</tr>
					<tr>
						<td>V1.2.19</td>
						<td>2016-03-16</td>
						<td><p>
								1、三方订单增加二次审核功能<br/>
								2、增加客服可修改发票信息功能<br/>
								3、合并平安处方药审核功能<br/>
								4、增加掌上糖医渠道处方药订单审核功能<br/>
							</p>
						</td>
					</tr>
					<tr>
						<td>V1.2.18</td>
						<td>2016-03-02</td>
						<td><p>
								1、客服系统沟通记录优化<br/>
								2、添加沟通记录优化、选择产品关键字优化<br/>
								3、会员档案增加手机、邮箱信息<br/>
							</p>
						</td>
					</tr>
					<tr>
						<td>V1.2.17</td>
						<td>2016-02-22</td>
						<td><p>
								1、三方订单审核管理<br/>
								2、三方订单审核报表<br/>
							</p>
						</td>
					</tr>
					<tr>
						<td>V1.2.16</td>
						<td>2015-12-15</td>
						<td><p>
								1、医院管理二维码改造<br/>
								2、医生二维码增加导出功能<br/>
								3、添加医生姓名增加智能检索功能<br/>
							</p>
						</td>
					</tr>
					<tr>
						<td>V1.2.15</td>
						<td>2015-11-15</td>
						<td><p>
								1、平安处方药审核<br/>
							</p>
						</td>
					</tr>
					<tr>
						<td>V1.2.14</td>
						<td>2015-10-14</td>
						<td><p>
								1、医院管理：医院信息管理，医生信息管理，二维码的生成<br/>
								2、代下单功能针对二维码扫码需求的改造<br/>
							</p>
						</td>
					</tr>
					<tr>
						<td>V1.2.13</td>
						<td>2015-06-19</td>
						<td><p>
								1、档案管理：分配客服、批量分配客服<br/>
								2、任务分配功能<br/>
								3、系统切换的功能（运营中心可跳转到报表中心）
							</p>
						</td>
					</tr>
					<tr>
						<td>V1.2.12</td>
						<td>2015-04-27</td>
						<td><p>
								1、今日任务：导出、产品关键词、去掉手机号列<br/>
								2、首页权限控制
							</p>
						</td>
					</tr>
					<tr>
						<td>V1.2.11</td>
						<td>2015-04-22</td>
						<td><p>
								1.官网模式下单可以下处方药类型的商品<br/>
								2.从运营后台跳转到官网模式下单时验证商品是否有库存及是否赠品<br/>
								3.沟通记录不可追加时，可查看历史<br/>
								4.去电弹屏沟通记录新增“产品咨询”分类<br/>
								5.去电弹屏默认显示“会员档案”<br/>
								6.新增运营中心后台首页，显示版本信息，统计今日任务<br/>
								7.新增极速达新订单消息提示功能<br/>
								8.“留言弹屏”改为“需求订单”<br/>
								9.菜单列表的收起、展开功能<br>
								10.修改客户来源分类<br>
								11.修改来电弹屏、在线客服、需求订单中沟通记录分类<br>
								12.部分功能的优化<br>
								13.调整页面，解决IE8等浏览器中一些页面样式不兼容的问题<br>
								14.添加对在线工单子功能的权限控制
							</p>
						</td>
					</tr>
					<tr>
						<td>V1.2.10</td>
						<td>2015-03-13</td>
						<td><p>新增平台短信统计、报表导出功能</p></td>
					</tr>
					<tr>
						<td>V1.2.09</td>
						<td>2015-03-05</td>
						<td><p>官网后台代下单客服ID合法性的验证接口</p></td>
					</tr>
					<tr>
						<td>V1.2.08</td>
						<td>2015-03-05</td>
						<td><p>修改极速达订单查询功能</p></td>
					</tr>
					<tr>
						<td>V1.2.07</td>
						<td>2015-03-03</td>
						<td>
							<p> 1.修改今日任务追加沟通记录的限制条件<br/>
								2.沟通记录标记当日已跟踪<br/>
								3.修改会员注册功能<br/>
								4.代下单功能优化<br/>
								5.今日任务添加三个字段，包括：沟通类型、客户来源、上次沟通日期<br/>
								6.后台功能菜单名称修改
							</p>
						</td>
					</tr>
					<tr>
						<td>V1.2.06</td>
						<td>2015-02-10</td>
						<td>
							<p> 1.去电弹屏沟通记录<br/>
								2.今日任务权限设置<br/>
								3.解决资源修改出错的bug
							</p>
						</td>
					</tr>
					<tr>
						<td>V1.2.05</td>
						<td>2015-02-09</td>
						<td>
							<p> 1.代下单:多SKU商品、父子套餐<br/>
								2.沟通记录多SKU商品、父子套餐选择<br/>
								3.官网模式代下单
							</p>
						</td>
					</tr>
					<tr>
						<td>V1.2.04</td>
						<td>2015-02-04</td>
						<td><p>产品咨询日报</p></td>
					</tr>
					<tr>
						<td>V1.2.03</td>
						<td>2015-02-02</td>
						<td>
							<p>	1.沟通记录报表增加“是否新用户”条件<br/>
								2.极速达订单已发货、已驳回后，不允许再次操作
							</p>
						</td>
					</tr>
					<tr>
						<td>V1.2.02</td>
						<td>2015-01-30</td>
						<td>
							<p>	1.修改平台权限功能<br/>
								2.今日任务<br/>
								3.沟通记录订单信息回写
							</p>
						</td>
					</tr>
					<tr>
						<td>V1.2.01</td>
						<td>2015-01-28</td>
						<td>
							<p>	1.极速达订单<br/>
								2.沟通记录客户来源、报表数据导出权限
							</p>
						</td>
					</tr>
					<tr>
						<td>V1.1.01</td>
						<td>2014-10-14</td>
						<td>
							<p> 1.菜单：客服中心-新增订单（运营中心后台、呼叫中心）<br/>
								2.菜单：客服中心-官网订单（运营中心后台、呼叫中心）<br/>
								3.菜单：客服报表-购买记录（运营中心后台）<br/>
								4.添加电销商品查询条件<br/>
								5.修改沟通记录，来电类型、去电受理结果
							</p>
						</td>
					</tr>
					
					
				</tbody>
			</table>
		</div>
	</div>
	<div style="clear:both;"></div>
</body>
</html>