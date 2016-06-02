<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/checkBox.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/sorttable.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/common.js"></script>
<link href="<%=request.getContextPath()%>/media/css/bootstrap.min.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/style.css"
	rel="stylesheet">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.8.3.js"></script>
<title>无标题文档</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<script language="javascript">
	//查询分页跳转
	function gotoPage(pageno){
		document.getElementById('pageno').value = pageno;
		document.form.submit();
	}
	
	 //设置页面显示的条数
       function gotoPageSize(pageSize)
      {
        document.getElementById('pageSize').value = pageSize;
        document.form.submit();
      }
	//搜索
	function chang(){
		var name=document.getElementById("name").value;
		document.form.action = "labels.do?method=load&className="+name;
		document.form.submit();
	}

	function shengh(){
		var shzt=document.getElementById("shzt").value;
	}

	function findNext(){
		var item="<option value=0 >请选择</option>";
		document.getElementById("B").length=0;
		document.getElementById("C").length=0;
		$('#C').append(item); 
		var yij=document.getElementById("A").value;
		$.ajax({
				type : "POST",  
				dataType: "json",//返回json格式的数据
				url:"<%=request.getContextPath()%>/labels.do?method=getTreeLister&id="+yij,
				success: function(data){
					$.each(data,function(i,result){
						item +="<option value="+result['id']+">"+result['className']+"</option>";
					});
					$('#B').append(item);
				}
			});
	}
	
	function findNextb(){
		var item="<option value=0>请选择</option>"
			document.getElementById("C").length=0;
			var erj=document.getElementById("B").value;
			$.ajax({
					type : "POST",  
					dataType: "json",//返回json格式的数据
					url:"<%=request.getContextPath()%>/labels.do?method=getTreeListerb&idb=" + erj,
					success : function(data) {
						$.each(data, function(i, result) {
							item += "<option value="+result['id']+">" + result['className'] + "</option>";
						});
						$('#C').append(item);
					}
				});
	}
</script>
<style>
table{
	TABLE-LAYOUT: fixed;
}

th {
	text-align: center;
}

td {
	text-align: center;
	WORD-WRAP: break-word;
}
</style>
<body onload="result();">
<div class="panel panel-info">
	<div class="panel-heading" style="height: 50px;"><h5>标签分类管理一级</h5></div>
	  <div class="panel-body">
	  	<input type="hidden" id="result" value="${result}" />
	<form action="labels.do?method=load" name="form" method="post">
      <div class="right_inline" style="padding-top:15px;">
		<input type="hidden" id="pageno" name="pageno" value="${pageno}" /> <input
			type="hidden" id="pageSize" name="pageSize" value="${pageSize}" />
		<div class="col-xs-10" style="margin-top: 15px;">
			<sec:authorize ifAnyGranted="/labels.do?method=add,/labels.do" >
				<a class="btn btn-primary btn-xs" href="<%=request.getContextPath()%>/labels.do?method=add&url=add">增加</a>
			</sec:authorize>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			名称：<input type="text" name="name" id="name" value="${name}">
			<button type="button" onclick="chang()"
				class="btn btn-success btn-xs">搜索</button>
		</div>
	
	<table width="100%" border="0" class="table table-bordered">
		<tr>
			<th width="50">id</th>
			<th width="100">编码</th>
			<th width="100">层级</th>
			<th width="100">名称</th>
			<th width="70">父节点</th>
			<th width="200">操作</th>
		</tr>
		<c:forEach items="${yhuserList}" var="item">
			<tr>
				<td>${item.id }</td>
				<td>${item.labelCode }</td>
				<td>${item.labelLevel }</td>
				<td>${item.labelName }</td>
				<td>系统根节点</td>
				<td>
				<sec:authorize ifAnyGranted="/labels.do?method=addChild,/labels.do" >
					<a class="btn btn-primary btn-xs" href="<%=request.getContextPath()%>/labels.do?method=addChild&id=${item.id}">增加子节点</a>
				</sec:authorize>
				<sec:authorize ifAnyGranted="/labels.do?method=del,/labels.do" >
					<a class="btn btn-info btn-xs" href="<%=request.getContextPath() %>/labels.do?method=del&id=${item.id}&url=labels" onclick="if(confirm('确定删除?')==false)return false;">删除</a>
				</sec:authorize>
				<sec:authorize ifAnyGranted="/labels.do?method=edit,/labels.do" >
					<a class="btn btn-success btn-xs" href="<%=request.getContextPath() %>/labels.do?method=edit&id=${item.id}">修改</a>
				</sec:authorize>
				<sec:authorize ifAnyGranted="/labels.do?method=guan,/labels.do" >
					<a class="btn btn-warning btn-xs" href="<%=request.getContextPath() %>/labels.do?method=guan&id=${item.id}">管理</a>
				</sec:authorize>
				</td>
			</tr>
		</c:forEach>
	 </table>
    </div>
   </form>
	<ul class="pager">
		<%@ include file="/WEB-INF/view/common/page.jspf"%>
	</ul>
	  </div>
</div>
	
	<!-- <div class="right_h">
		<h3>标签分类管理一级</h3>
	</div>
	 -->
	<script>
		function result() {
			var obj = document.getElementById("result");
			if (obj.value == "1") {
				alert("删除成功");
			} else if (obj.value == "2") {
				alert("删除失败,它有子节点！");
			} else if (obj.value == "3") {
				alert("删除失败,它有关联的节点！");
			}
			obj.value = "0";
		}
	</script>
</body>
</html>