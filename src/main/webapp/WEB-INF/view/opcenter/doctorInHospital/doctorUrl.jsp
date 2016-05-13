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
<title>用户角色</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<style>
table {
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
	
	$(document).ready(function(){
		jQuery('#userName').bValidator(options,'nameInst');
	});
/**
 *  搜索
 */
	function chang(){
		var keyword = $("#doctorUrlName").val();
		
		var urll="<%=request.getContextPath()%>/doctorInHospital.do?method=showAllDoctorUrl&searchName="+ keyword;
		document.form.action = urll;
		document.form.submit();
	}
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
      
	//导出选中的医生url记录
	function exportXls() {
		var rows = $("input:checkbox[name='id']:checked");
		if (rows.length == 0) {
			//没有选中
			alert("请选择要导出的医生url记录！！");
		} else {
			//选中
			//获得选中记录的id串    如"1,2,3"
			var array = new Array();
			$(rows).each(function() {
						var isCheck = this.value;
						array.push(isCheck);
					});
			var ids = array.join(",");
			window.location.href = "${pageContext.request.contextPath}/doctorInHospital.do?method=exportXlsDoctorUrl&ids=" + ids;
		}
	}
</script>
<body>
<form action="doctorInHospital.do?method=showAllDoctorUrl" name="form" method="post">
<div class="panel panel-primary">
  <div class="panel-heading">
    <h4 class="panel-title">医生二维码</h4>
  </div>
  <div class="panel-body">
  <input type="hidden" id="pageno" name="pageno" value="${pageno}" /> <input
			type="hidden" id="pageSize" name="pageSize" value="${pageSize}" />
		<div class="right_inline">
<!-- 		<div class="right_input" style="padding-top: 5px">&nbsp;&nbsp;&nbsp;&nbsp;<B>医生姓名:</B></div> -->
		<div class="right_input" style="width:350px;">
<!-- 		<input  id="doctorUrlName" name="doctorUrlName" type="text" class="form-control input-sm"   ></div> -->
<!-- <div class="right_input"  style="padding-top: 5px"> -->
<!-- 	<div class="right_input" style="width:10px;">&nbsp;&nbsp;&nbsp;&nbsp;</div> -->
<!-- 	<button type="button" onclick="chang()" class="btn btn-success btn-xs">搜索</button> -->
	<button type="button" onclick="window.location.href='<%=request.getContextPath()%>/doctorInHospital.do?method=addDoctorUrl'" class="btn btn-primary ">添加</button>
	<button type="button" onclick="exportXls()" class="btn btn-primary ">导出到excel</button>
</div>


</div>

		<table class="table table-bordered table-hover">
			<tr>
				<th>序号</th>
				<th>药品所属医生</th>
				<th>医生药品url</th>
				<th>二维码图片地址</th>
				<td>创建时间 </td>
				<th>更新时间</th>			
				<th><span>操作</span> 
			</th>
			</tr>
			<c:forEach items="${doctorUrlList}" var="doctorUrl" varStatus="status">
				<tr>
					<td class="th" style="vertical-align: left;">
						<input type="checkbox" name="id" id="id" value="${doctorUrl.id }"/>
					    ${doctorUrl.id }
					</td>
					<td>
					<c:forEach items="${doctorList}" var="doctorList">
					<c:if test="${doctorUrl.doctorId==doctorList.id}">
					${doctorList.doctorName}
					</c:if>
					</c:forEach>
					</td>
					<td>${doctorUrl.url}</td>
					<td><img  src="<%=request.getContextPath()%>/images/doctor/${doctorUrl.quickResponseCodeUrl}" height="150" width="150" />
					</td>
					<td>${doctorUrl.createTime }</td>
					<td>${doctorUrl.updateTime }</td>
					<td><!-- <a class="btn btn-info btn-xs" onclick="return confirm('是否删除?')" -->
<%-- 							href="<%=request.getContextPath() %>/user.do?method=del&id=${user.id }">删除</a> --%>
							<a class="btn btn-info btn-xs" href="<%=request.getContextPath() %>/doctorInHospital.do?method=updateDoctorUrl&reqId=${doctorUrl.id }">修改</a>
							<a class="btn btn-success btn-xs" href="<%=request.getContextPath() %>/doctorInHospital.do?method=createQuickCode&doctorUrlId=${doctorUrl.id}&pageno=${pageno}&pageSize=${pageSize}">生成二维码</a>
				</tr>
			</c:forEach>
		</table>
		<ul class="pager">
			<%@ include file="/WEB-INF/view/common/page.jspf"%>
		</ul>
  
  </div>
</div>

</form>
</body>
</html>
