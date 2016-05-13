<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.3.js"></script> 
<link href="<%=request.getContextPath()%>/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/ligerUI/js/plugins/ligerTab.js" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/ligerUI/js/plugins/ligerMenu.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
<script type="text/javascript">
        var navtab = null;
        $(function (){
            $("#tab1").ligerTab({
	            onAfterSelectTabItem: function (tabid){
	                 navtab.reload(navtab.getSelectedTabItemID());
	            } ,
	            contextmenu :false
            });
            navtab = $("#tab1").ligerGetTabManager();
        });
        function toTabItem(tabid){
        	navtab.selectTabItem (tabid);
        }
    </script>
</head>
<body>
<div id="tab1" style="width: 100%">
  
    <iframe style="width: 100%;height: 500px;" src="<%=request.getContextPath()%>/goodsp.do?method=getGoodsList"></iframe>
  
</div>
</body>
</html>