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


$(document).ready(function(){
	  var qerjindex=document.getElementById("qerjid").value;//前端选中第二级
	  
	  
	  wqyiji(qerjindex);//前端
	 
});

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
		document.getElementById("Bb").length=0;
		var yij=document.getElementById("Aa").value;
		$.ajax({
				type : "POST",  
				dataType: "json",//返回json格式的数据
				url:"<%=request.getContextPath()%>/cfda.do?method=getTreeLister&id="+yij,
				success: function(data){
					$.each(data,function(i,result){  
						item +="<option value="+result['id']+">"+result['className']+"</option>";
					});  
					$('#Bb').append(item);  
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
					url:"<%=request.getContextPath()%>/cfda.do?method=getTreeListerb&idb="+erj,
					success: function(data){
						$.each(data,function(i,result){
							alert(i);
						item +="<option value="+result['id']+">"+result['className']+"</option>";
					});
					$('#C').append(item);
					}
				});
	}
	function chang(){
		var name=document.getElementById("name").value;
		var id=document.getElementById("Bb").value;
		document.form.action = "cfda.do?method=guan1&className="+name+"&id="+id;
		document.form.submit();
	}
	
	function wqyiji(qerjindex){
  	  var item="<option value=0 >请选择</option>";
  	  document.getElementById("Bb").length=0;
  	  var yij=document.getElementById("Aa").value;
  	  //var  qyij=document.getElementById("qyij").value;
  	  $.ajax({
				type : "POST",  
				dataType: "json",//返回json格式的数据
				url:"<%=request.getContextPath()%>/cfda.do?method=getTreeLister&id="+yij,
				success: function(data){
					$.each(data,function(i,result){  
						item +="<option value="+result['id']+">"+result['className']+"</option>";
					});  
					$('#Bb').append(item);
					$("#Bb option").each(function(){
  	                 //alert($(this).val());
  	                 if($(this).val() == qerjindex){
  	                 	$(this).attr("selected",true);
  	                 }
  	             });   
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
#Aa{
	width:150px;
	border: 1px solid gray;
}
#Bb {
		width: 150px;
		border: 1px solid gray;
	}
</style>
<body onload="result();">
	<div class="right_h"><h3>质检分类管理三级（<span><c:if test="${cfdaClass.className == null}">三级所有信息</c:if>${cfdaClass.className}</span>）</h3></div>
	<input type="hidden" id="result" value="${result}" />
	<form action="cfda.do?method=guan1&id=${parentId}" name="form" method="post">
      <div class="right_inline" style="padding-top:15px;">
		<input type="hidden" id="qerjid" name="qerjid" value="${parentId}"/>
		<input type="hidden" id="pageno" name="pageno" value="${pageno}" />
		<input type="hidden" id="pageSize" name="pageSize" value="${pageSize}" />


		<div class="col-xs-10" style="margin-top: 15px;">
			<sec:authorize ifAnyGranted="/cfda.do?method=addNextb,/cfda.do">
				<a class="btn btn-primary btn-xs" href="<%=request.getContextPath()%>/cfda.do?method=addNextb&pId=${parentId}">增加</a>
			</sec:authorize>
			<a class="btn btn-success btn-xs" href="<%=request.getContextPath() %>/cfda.do?method=guan&id=${hahaha}">返回</a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span class="form-title" style="margin-left: 80px;">一级 </span>
			<select id="Aa" name="classNameA" onchange="findNext()">
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
			<span class="form-title">二级</span> <span class="form-title"></span>
				<select id="Bb" name="classNameB">
				<option value="0">请选择</option>
				<c:if test="${fuck2 != 0 }">
					<c:forEach items="${all}" var="item">
						<c:choose>
							<c:when test="${item.id == fuck2}">
									<option selected="selected" value="${item.id}">${item.className }</option>
							</c:when>
						</c:choose>
					</c:forEach>
				</c:if>
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
					</c:if>
				</c:forEach>
				<td>
					<sec:authorize ifAnyGranted="/cfda.do?method=delb,/cfda.do" >
						<a class="btn btn-info btn-xs" href="<%=request.getContextPath() %>/cfda.do?method=delb&id=${item.id}&url=guan&parentId=${parentId}" onclick="if(confirm('确定删除?')==false)return false;">删除</a>
					</sec:authorize>
					<sec:authorize  ifAnyGranted="/cfda.do?method=editSan,/cfda.do" >
						<a class="btn btn-success btn-xs" href="<%=request.getContextPath() %>/cfda.do?method=editSan&id=${item.id}">修改</a>
					</sec:authorize>
				</td>
			</tr>
		</c:forEach>
	</table>
   </div>
  </form>
	<ul class="pager">
		<%@ include file="/WEB-INF/view/common/page.jspf" %>
	</ul>
</body>
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
</html>
