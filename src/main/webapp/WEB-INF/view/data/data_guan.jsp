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
				url:"<%=request.getContextPath()%>/data.do?method=getTreeLister&id="+yij,
				success: function(data){
					$.each(data,function(i,result){  
						item +="<option value="+result['id']+">"+result['attName']+"</option>";
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
					url:"<%=request.getContextPath()%>/data.do?method=getTreeListerb&idb="+erj,
					success: function(data){
						$.each(data,function(i,result){  
						item +="<option value="+result['id']+">"+result['attName']+"</option>";
					});
					$('#C').append(item);
					}
				});
	}
	
	function chang(){
		var name=document.getElementById("name").value;
		var id=document.getElementById("Aa").value;
		document.form.action = "data.do?method=guan&attName="+name+"&id="+id;
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

	<div class="right_h"><h3>数据字典管理二级（<span><c:if test="${dataDictionary.attName == null}">二级所有信息</c:if>${dataDictionary.attName}</span>）</h3></div>
	<input type="hidden" id="result" value="${result}" />
	<form action="data.do?method=guan&id=${parentId}" name="form" method="post">
     <div class="right_inline" style="padding-top:15px;">
		<input type="hidden" id="pageno" name="pageno" value="${pageno}" />
		<input type="hidden" id="pageSize" name="pageSize" value="${pageSize}" />
		


		<div class="col-xs-10" style="margin-top: 15px;">
			<sec:authorize ifAnyGranted="/data.do?method=addNextb,/data.do" >
				<a class="btn btn-primary btn-xs" href="<%=request.getContextPath()%>/data.do?method=addNextb&pId=${parentId}">增加</a>
			</sec:authorize>
			<a class="btn btn-success btn-xs" href="<%=request.getContextPath()%>/data.do?method=load">返回</a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span class="form-title" style="margin-left: 80px;">一级 </span>
			
			<select id="Aa" name="classNameA">
				<option value="0">请选择</option>
				<c:forEach items="${all}" var="item">
					<c:choose>
						<c:when test="${item.id == fuck}">
								<option selected="selected" value="${item.id}">${item.attName }</option>
						</c:when>
						
						<c:otherwise>
								<option value="${item.id}">${item.attName }</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
			
			名称：<input type="text" name="name" id="name" value="${name}">
			<button type="button" onclick="chang()" class="btn btn-success btn-xs">搜索</button>
			
		</div>
			<table width="100%" border="0" class="table table-bordered">
				<tr>
					<th>id</th>
					<th>编码</th>
					<th>层级</th>
					<th>名称</th>
					<th>父节点</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${yhuserList}" var="item">
					<tr>
						<td>${item.id }</td>
						<td>${item.attCode }</td>
						<td>${item.attLevel }</td>
						<td>${item.attName }</td>
						<c:forEach items="${all}" var="all">
							<c:if test="${item.parentId ==all.id }">
								<td>${all.attName }</td>
							</c:if>
						</c:forEach>
						<td>
							<sec:authorize ifAnyGranted="/data.do,/data.do?method=del" >
								<a class="btn btn-info btn-xs" href="<%=request.getContextPath() %>/data.do?method=del&id=${item.id}&url=guan&parentId=${parentId}" onclick="if(confirm('确定删除?')==false)return false;">删除</a>
							</sec:authorize>
							<sec:authorize ifAnyGranted="/data.do,/data.do?method=editEr" >
								<a class="btn btn-success btn-xs" href="<%=request.getContextPath() %>/data.do?method=editEr&id=${item.id}">修改</a>
							</sec:authorize>
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
			} else if (obj.value == "2") {
				alert("删除失败,它有子节点！");
			}else if (obj.value == "3") {
				alert("删除失败,该标签被商品所使用！");
			}
			obj.value = "0";
		}
	</script>
</body>
</html>
