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
<script language="javascript">
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
  //搜索
    function chang(){
        var name=document.getElementById("name").value;
    	document.form.action = "front.do?method=load&className="+name;
		document.form.submit();
    }
  //高级搜索
    function gaojichang(ba){
      yiji();
      qyiji();
      var yijid=document.getElementById("yij").value;//药监第一级选中的值
      var qyijid=document.getElementById("qyij").value;//药监第一级选中的值
	  var shangpin=document.getElementById("goodsName").value;//商品名包含
	  var interiorCode=document.getElementById("interiorCode").value;//编号包含
	  var brand=document.getElementById("brand").value;//品牌包含
	  var starjia=document.getElementById("starjia").value;//售价开始值
	  var endjia=document.getElementById("endjia").value;//售价最大值
	  document.form.action = "goodsp.do?method=getGoodsList&shangpin="+shangpin+"&interiorCode="+interiorCode+"&brand="+brand+"&starjia="+starjia+"&endjia="+endjia+"&yijid="+yijid+"&qyijid="+qyijid+"&ba="+ba;
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
  <!-- Default panel contents -->
  <div class="panel-heading">前端分类管理一级</div>
  <div class="panel-body">
    <p>
		<input type="hidden" id="result" value="${result}" />
	<form action="front.do?method=load" name="form" method="post">
	 <div class="right_inline" style="padding-top:15px;">
		<input type="hidden" id="pageno" name="pageno" value="${pageno}"/>
		<input type="hidden" id="pageSize" name="pageSize" value="${pageSize}"/>
		<div class="col-xs-10" style="margin-top: 15px;">
			<sec:authorize ifAnyGranted="/front.do?method=add,/front.do" >
				<a class="btn btn-primary btn-xs" href="<%=request.getContextPath()%>/front.do?method=add&url=add">增加</a>
			</sec:authorize>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			名称：<input type="text" name="name" id="name" value="${name}">
			<button type="button" onclick="chang()" class="btn btn-success btn-xs">搜索</button>
		</div>
	
		<table width="950" class="table table-bordered table-hover" id="tab">
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
						<td >${item.className }</td>
						<td>${item.classCode }</td>
						<td>${item.classDesc }</td>
						<td>${item.classLevel }</td>
						<td>系统根节点</td>
						<td>
							<sec:authorize ifAnyGranted="/front.do?method=addChild,/front.do" >
								<a class="btn btn-primary btn-xs" href="<%=request.getContextPath()%>/front.do?method=addChild&id=${item.id}">增加子节点</a>
							</sec:authorize>
							<sec:authorize ifAnyGranted="/front.do?method=del,/front.do" >
								<a class="btn btn-info btn-xs" href="<%=request.getContextPath() %>/front.do?method=del&id=${item.id}&url=front" onclick="if(confirm('确定删除?')==false)return false;">删除</a>
							</sec:authorize>
							<sec:authorize ifAnyGranted="/front.do?method=edit,/front.do" >
								<a class="btn btn-success btn-xs" href="<%=request.getContextPath() %>/front.do?method=edit&id=${item.id}">修改</a>
							</sec:authorize>
							<sec:authorize ifAnyGranted="/front.do?method=guan,/front.do" >
								<a class="btn btn-warning btn-xs" href="<%=request.getContextPath() %>/front.do?method=guan&id=${item.id}">管理</a>
							</sec:authorize>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
    </form>
	</p>
  </div>
		<ul class="pager">
			<%@ include file="/WEB-INF/view/common/page.jspf"%>
		</ul>
</div>

	

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
			obj= "0";
		}
	</script>
  
</body>
</html>
