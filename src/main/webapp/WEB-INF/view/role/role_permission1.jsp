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
	function listTolist(fromid, toid, isAll) {
			if (isAll == true) { //全部移动 
				$("#" + fromid + " option").each(
						function() {
							//将源list中的option添加到目标list,当目标list中已有该option时不做任何操作. 
							if( fromid == "perms" )
								$('<option ondblclick=listTolist("unPerms","perms",false)  value="' + this.value + '" >' + this.text + '</option>').appendTo($("#" + toid + ":not(:has(option[value="+ $(this).val() + "]))"));
							else if( fromid == "unPerms" )
								$('<option ondblclick=listTolist("perms","unPerms",false)  value="' + this.value + '" >' + this.text + '</option>').appendTo($("#" + toid + ":not(:has(option[value="+ $(this).val() + "]))"));
						});
				$("#" + fromid).empty(); //清空源list
			} else if (isAll == false) {
				$("#" + fromid + " option:selected").each(
						function() {
							//将源list中的option添加到目标list,当目标list中已有该option时不做任何操作. 
							if( fromid == "perms" )
								$('<option ondblclick=listTolist("unPerms","perms",false)  value="' + this.value + '" >' + this.text + '</option>').appendTo($("#" + toid + ":not(:has(option[value="+ $(this).val() + "]))"));
							else if( fromid == "unPerms" )
								$('<option ondblclick=listTolist("perms","unPerms",false)  value="' + this.value + '" >' + this.text + '</option>').appendTo($("#" + toid + ":not(:has(option[value="+ $(this).val() + "]))"));
							//目标list中已经存在的option并没有移动,仍旧在源list中,将其清空. 
							if ($("#" + fromid + " option[value="+ $(this).val() + "]").length > 0) {
								$("#" + fromid).get(0).removeChild($("#" + fromid + " option[value="+ $(this).val() + "]").get(0));
							}
						});
			}
	};
	
	function fromSubmit(){
		var roleId = $("#id").val();
	  	//获取所有授权的角色
	    var perms = new Array(); 
	    $("#perms option").each(function(){ 
	        var value = $(this).val();
	        perms.push(value); 
	    });
		
		$.ajax( {   
		     type : "POST",   
		     url : "<%=request.getContextPath()%>/role.do?method=savePrems&perms=" + perms + "&roleId=" + roleId,   
		     dataType: "json",   
		     success : function(data) {   
		    	 if( data.result == "true" ){
		    		 window.location.href = "<%=request.getContextPath()%>/role.do?method=role";
		    	 }
		     } 
		 });
	}
	
</script>
<title>角色分配资源</title>
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
			<h3>分配资源</h3>
		</div>
		<form>
				<input type="hidden" id="id" name="id" value="${role.id }" />
			<br>
			<table class="table table-bordered">
				<tr>
					<th>角色</th>
					<th>备注</th>
				</tr>
				<tr>
					<td>${role.name }</td>
					<td>${role.remark }</td>
				</tr>
			</table>
			<table class="table table-bordered">
				<tr>
					<th>未授权资源</th>
					<th>操作</th>
					<th>已授权资源</th>
				</tr>
				<tr>
					<td>
						<select multiple id="unPerms" name="unPerms"
							style="width:300px;height:160px;">
							<c:forEach items="${unPermList }" var="unPerm">
								<option ondblclick="listTolist('unPerms','perms',false)" value="${unPerm.id }" >${unPerm.name }</option>
							</c:forEach>
						</select>
					</td>
					<td>
					<br>
						<input class="btn btn-default" type="button" onclick="listTolist('unPerms','perms',false)" value="选中添加到右边&gt;&gt;" />
						<br>
						<input class="btn btn-default" type="button" onclick="listTolist('unPerms','perms',true)" value="全部添加到右边&gt;&gt;" />
						<br>
						<input class="btn btn-default" type="button" onclick="listTolist('perms','unPerms',false)" value="&lt;&lt;选中添加到左边" />
						<br>
						<input class="btn btn-default" type="button" onclick="listTolist('perms','unPerms',true)" value="&lt;&lt;全部添加到左边" />
					</td>
					<td>
						<select multiple id="perms" name=perms
							style="width: 300px;height:160px;">
							<c:forEach items="${permList }" var="permission">
								<option ondblclick="listTolist('perms','unPerms',false)" value="${permission.id }"  >${permission.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
			<div class="form-group">
			    <div class="col-sm-offset-4">
			      <button class="btn btn-default" onclick="fromSubmit()" type="button" >提交</button>
			      <button class="btn btn-default" type="button" onclick="window.location.href='<%=request.getContextPath()%>/role.do?method=role'">返回</button>
			    </div>
			</div>
		</form>
	</div>
</body>
</html>
