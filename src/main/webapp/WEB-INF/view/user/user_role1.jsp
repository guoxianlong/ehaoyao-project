<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<script type="text/javascript">
	/** 
	fromid:源list的id. 
	toid:目标list的id. 
	isAll参数(true或者false):是否全部移动或添加 
	 */
	function listToList(fromid, toid, isAll) {
			if (isAll == true) { //全部移动 
				$("#" + fromid + " option").each(
						function() {
							if( fromid == "unroles" )
								$('<option ondblclick=listToList("roles","unroles",false)  value="' + this.value + '" >' + this.text + '</option>').appendTo($("#" + toid + ":not(:has(option[value="+ $(this).val() + "]))"));
							else if ( fromid == "roles" )
								$('<option ondblclick=listToList("unroles","roles",false)  value="' + this.value + '" >' + this.text + '</option>').appendTo($("#" + toid + ":not(:has(option[value="+ $(this).val() + "]))"));
						});
				$("#" + fromid).empty(); //清空源list
			} else if (isAll == false) {
				$("#" + fromid + " option:selected").each(
						function() {
							//将源list中的option添加到目标list,当目标list中已有该option时不做任何操作.
							if( fromid == "unroles" )
								$('<option ondblclick=listToList("roles","unroles",false)  value="' + this.value + '" >' + this.text + '</option>').appendTo($("#" + toid + ":not(:has(option[value="+ $(this).val() + "]))"));
							else if ( fromid == "roles" )
								$('<option ondblclick=listToList("unroles","roles",false)  value="' + this.value + '" >' + this.text + '</option>').appendTo($("#" + toid + ":not(:has(option[value="+ $(this).val() + "]))"));
							//目标list中已经存在的option并没有移动,仍旧在源list中,将其清空. 
							if ($("#" + fromid + " option[value="+ $(this).val() + "]").length > 0) {
								$("#" + fromid).get(0).removeChild($("#" + fromid + " option[value="+ $(this).val() + "]").get(0));
							}
						});
			}
	}
	
	function fromSubmit(){
		var userId = $("#id").val();
	  	//获取所有授权的角色
	    var roles = new Array(); 
	    $("#roles option").each(function(){ 
	        var value = $(this).val();
	        roles.push(value); 
	    });
		
		$.ajax( {   
		     type : "POST",   
		     url : "<%=request.getContextPath()%>/user.do?method=saveRoles&roles=" + roles + "&userId=" + userId,   
		     dataType: "json",   
		     success : function(data) {   
		    	 if( data.result == "true" ){
		    		 window.location.href = "<%=request.getContextPath()%>/user.do?method=user";
		    	 }
		     } 
		 });
	}
</script>
<title>用户分配角色</title>
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
<body>
	<div class="right_inline" style="padding-top:15px;">
		<div class="right_h">
			<h3>分配角色</h3>
		</div>
		<form action="<%=request.getContextPath()%>/user.do?method=saveRoles" method="post" >
			<input type="hidden" id="pageno" name="pageno" value="${pageno}" />
			<input type="hidden" id="pageSize" name="pageSize" value="${pageSize}" />
			<input type="hidden" id="id" name="id" value="${user.id }" />
			<br>
			<table class="table table-bordered" >
				<tr>
					<th>用户</th>
					<th>邮箱</th>
					<th>状态</th>
					<th>创建时间</th>
				</tr>
				<tr>
					<td>${user.userName }</td>
					<td>${user.email }</td>
					<td>${user.lockStatus == 0 ? "启用" : "锁定" }</td>
					<td>${user.createTime }</td>
				</tr>
			</table>
			
			<table class="table table-bordered">
				<tr>
					<th>未授权角色</th>
					<th>操作</th>
					<th>已授权角色</th>
				</tr>
				<tr>
					<td>
						<select multiple id="unroles" name="unroles"
							style="width:300px;height:160px;">
							<c:forEach items="${unroleList }" var="unrole">
								<option ondblclick="listToList('unroles','roles',false)" value="${unrole.id }"   >${unrole.remark }</option>
							</c:forEach>
						</select>
					</td>
					<td>
					<br>
						<input class="btn btn-default" type="button" onclick="listToList('unroles','roles',false)" value="选中添加到右边&gt;&gt;" />
						<br>
						<input class="btn btn-default" type="button" onclick="listToList('unroles','roles',true)" value="全部添加到右边&gt;&gt;" />
						<br>
						<input class="btn btn-default" type="button" onclick="listToList('roles','unroles',false)" value="&lt;&lt;选中添加到左边" />
						<br>
						<input class="btn btn-default" type="button" onclick="listToList('roles','unroles',true)" value="&lt;&lt;全部添加到左边" />
					</td>
					<td>
						<select multiple id="roles" name="roles" 
							style="width: 300px;height:160px;">
							<c:forEach items="${roleList }" var="role">
								<option ondblclick="listToList('roles','unroles',false)"  value="${role.id }" >${role.remark }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
			<div class="form-group">
			    <div class="col-sm-offset-4 ">
			      <button class="btn btn-default" onclick="fromSubmit()" type="button" >提交</button>
			      <button class="btn btn-default" type="button" onclick="window.location.href='<%=request.getContextPath()%>/user.do?method=user'">返回</button>
			    </div>
			</div>
		</form>
	</div>
</body>
</html>
