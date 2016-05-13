<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/checkBox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/sorttable.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<link href="<%=request.getContextPath()%>/media/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.3.js"></script>
<title>无标题文档</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<script type="text/javascript">

	//查询分页跳转
	function gotoPage(pageno)
	{
		document.getElementById('pageno').value = pageno;
		document.form.submit();
	}
	
	 //设置页面显示的条数
       function gotoPageSize(pageSize)
      {
        document.getElementById('pageSize').value = pageSize;
        document.form.submit();
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
				url:"<%=request.getContextPath()%>/front.do?method=getTreeLister&id="+yij,
				success: function(data){
					$.each(data,function(i,result){  
						item +="<option value="+result['id']+">"+result['className']+"</option>";
					});  
					$('#B').append(item);  
				}
			});
	}
	
	function findNextb(){
		var item="<option value=0>请选择</option>";
			document.getElementById("C").length=0;
			var erj=document.getElementById("B").value;
			$.ajax({
					type : "POST",  
					dataType: "json",//返回json格式的数据
					url:"<%=request.getContextPath()%>/front.do?method=getTreeListerb&idb="+erj,
					success: function(data){
						$.each(data,function(i,result){  
						item +="<option value="+result['id']+">"+result['className']+"</option>";
					});
					$('#C').append(item);
					}
				});
	}
	
	function chang(){
		var name=document.getElementById("name").value;
		var id=document.getElementById("Aa").value;//选中的一级商品
		document.form.action = "front.do?method=guan&className="+name+"&id="+id;
		document.form.submit();
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
	
	#Aa {
		width: 150px;
		border: 1px solid gray;
	}
</style>
<body onload="result();">
	<div class="right_h"><h3>前端分类管理二级（<c:if test="${frontClass.className == null}">二级所有信息</c:if>${frontClass.className }）</h3></div>
	<input type="hidden" id="result" value="${result}" />
	<form action="front.do?method=guan&id=${parentId}" name="form" method="post">
      <div class="right_inline" style="padding-top:15px;">
		<input type="hidden" id="pageno" name="pageno" value="${pageno}" />
		<input type="hidden" id="pageSize" name="pageSize" value="${pageSize}" />
		


		<div class="col-xs-10" style="margin-top: 15px;">
		<sec:authorize ifAnyGranted="/front.do?method=addNext,/front.do" >
			<a class="btn btn-primary btn-xs" href="<%=request.getContextPath()%>/front.do?method=addNext&pId=${parentId}">增加</a>
		</sec:authorize>
			<a class="btn btn-success btn-xs" href="<%=request.getContextPath()%>/front.do?method=load">返回</a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span class="form-title" style="margin-left: 80px;">一级 </span>
			
			<select id="Aa" name="classNameA">
				<option value="0">请选择</option>
				<c:forEach items="${list}" var="item">
					<c:choose>
						<c:when test="${item.id == fuck}">
								<option selected="selected" value="${item.id}">${item.className }</option>
						</c:when>
						
						<c:otherwise>
								<option value="${item.id}">${item.className }</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
			
			名称：<input type="text" name="name" id="name" value="${name}">
			<button type="button" onclick="chang()" class="btn btn-success btn-xs">搜索</button>
		</div>
		<table width="100%" border="0" class="table table-bordered">
				<tr>
					<th width="50">id</th>
					<th width="100">名称</th>
					<th width="100">编码</th>
					<th width="100">描述</th>
					<th width="70">层级</th>
					<th width="70">父节点</th>
					<th width="200">操作</th>
				</tr>
				<c:forEach items="${yhuserList}" var="item">
					<tr>
						<td>${item.id }</td>
						<td>${item.className }</td>
						<td>${item.classCode }</td>
						<td>${item.classDesc }</td>
						<td>${item.classLevel }</td>
						<c:forEach items="${all}" var="all">
							<c:if test="${item.parentId ==all.id }">
								<td>${all.className }</td>
								<input type="hidden" id="hahaha" name="hahaha" value="${item.parentId}" />
							</c:if>
						</c:forEach>
						<td>
							<c:if test="${item.classLevel != 3}">
								<sec:authorize ifAnyGranted="/front.do?method=addChildb,/front.do" >
									<a class="btn btn-primary btn-xs" href="<%=request.getContextPath()%>/front.do?method=addChildb&id=${item.id}">增加子节点</a>
								</sec:authorize>
							</c:if>
							<sec:authorize ifAnyGranted="/front.do?method=del,/front.do" >
								<a class="btn btn-info btn-xs" href="<%=request.getContextPath() %>/front.do?method=del&id=${item.id}&url=guan&parentId=${parentId}" onclick="if(confirm('确定删除?')==false)return false;">删除</a>
							</sec:authorize>
							<sec:authorize ifAnyGranted="/front.do?method=editEr,/front.do" >
								<a class="btn btn-success btn-xs" href="<%=request.getContextPath() %>/front.do?method=editEr&id=${item.id}">修改</a>
							</sec:authorize>
							<c:if test="${item.classLevel != 3}">
								<sec:authorize ifAnyGranted="/front.do?method=guan1,/front.do" >
									<a class="btn btn-warning btn-xs" href="<%=request.getContextPath() %>/front.do?method=guan1&id=${item.id}">管理</a>
								</sec:authorize>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
			<ul class="pager">
				<%@ include file="/WEB-INF/view/common/page.jspf"%>
			</ul>
		</div>
	</form>
	<script>
		function result() {
			var obj = document.getElementById("result");
			if (obj.value == "1") {
				alert("删除成功");
			} else if (obj.value == "2") {
				alert("删除失败,它有子节点！");
			}else if (obj.value == "3") {
				alert("删除失败,该商品和其他商品关联！");
			}
			obj.value = "0";
		}
	</script>
</body>
</html>
