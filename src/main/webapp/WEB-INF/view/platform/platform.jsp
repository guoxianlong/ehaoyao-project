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
<title>平台管理首页</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
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
<script type="text/javascript">
  //查询分页跳转
      function gotoPage(pageno)
      {
        document.getElementById('pageno').value = pageno;
        document.platform.submit();
      }
      //设置页面显示的条数
       function gotoPageSize(pageSize)
      {
        document.getElementById('pageSize').value = pageSize;
        document.platform.submit();
      }
      
  //搜索
    function chang(){
    	//var name=document.getElementById("name").value;
    	document.platform.action ="platform.do?method=queryAll";
		document.platform.submit();
    }
  
</script>

<body onload="result();">
<div class="panel panel-info">
	<div class="panel-heading" style="height: 50px;"><h5>平台管理</h5></div>
	  <div class="panel-body">
			<input type="hidden" id="result" value="${result}" />
	<form action="platform.do?method=queryAll" name="platform" method="post">
	 <div class="right_inline" style="padding-top:15px;">
	     	
		<input type="hidden" id="pageno" name="pageno" value="${pageno}"/>
		<input type="hidden" id="pageSize" name="pageSize" value="${pageSize}"/>
		<div class="col-xs-10" style="margin-top: 15px;">
		<sec:authorize ifAnyGranted="/platform.do?method=showAdd,/platform.do" >
			<a class="btn btn-primary btn-xs" href="<%=request.getContextPath()%>/platform.do?method=showAdd">增加</a>
		</sec:authorize>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			名称|编号：<input type="text" name="name" id="name" value="${name}">
			<button type="button" onclick="chang()" class="btn btn-success btn-xs">搜索</button>
		</div>

		<table  class="table table-bordered" id="tab">
				<tr>
					<th width="50">id</th>
					<th width="100">名称</th>
					<th width="100">编码</th>
					<th width="100">操作</th>
				</tr>
				 <c:forEach items="${list}" var="platform">
					<tr>
						<td>${platform.id }</td>
						<td >${platform.platformName}</td>
						<td>${platform.platformCode}</td>
						<td>
						<sec:authorize ifAnyGranted="/platform.do?method=del,/platform.do" >
							<a class="btn btn-info btn-xs" href="<%=request.getContextPath() %>/platform.do?method=del&id=${platform.id}" onclick="if(confirm('确定删除?')==false)return false;">删除</a>
						</sec:authorize>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<sec:authorize ifAnyGranted="/platform.do?method=showUpdate,/platform.do" >
							<a class="btn btn-success btn-xs" href="<%=request.getContextPath() %>/platform.do?method=showUpdate&id=${platform.id}">修改</a>
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


	<!-- <div class="right_h"><h3>平台管理</h3></div> -->
	
  <script>
		function result() {
			var obj = document.getElementById("result");
			if (obj.value == "1") {
				alert("删除成功");
			} else if (obj.value == "2") {
				alert("删除失败,它有关联的商品套餐！");
			}
			obj.value = "0";
		}
	</script>
</body>
</html>
