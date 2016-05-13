
//页面上的全选函数
function checkAll(firstCheckName,checkName){
	  var flag = 0;	 
	  if(checkName!=null)
	  {
		  if(firstCheckName.checked==true)
		  {
			  for(; flag < checkName.length; flag++){
				   checkName[flag].checked = true;
			  }
			  if(flag==0) checkName.checked = true;
		  }
		  else
		  {
			for(; flag < checkName.length; flag++){
				   checkName[flag].checked = false;
			  }
			  if(flag==0) checkName.checked = false;
		  }
		}	
	}


// 检查是否只选一项更新
function checkUpdate(checkName)
	{
		// 判断有没有数据
		if(checkName==null)
		{
			alert("没有可更新的数据！");
			return false;
		}
		else
		{
			// 判断是否只得一条记录
			if(checkName.length==null)
			{
				if(checkName.checked==true)
				{
					// alert("success");
					return true;
				}
				else
				{
					alert("请选择要更新的记录!");
					return false;
				}
			}
			else
			{
			// 如为否则有多条记录
				i=0;
				t=0;
				// 检查选了多小项
				for(;i<checkName.length;i++)
				{
					if(checkName[i].checked==true)
					{
						t++;
					}
				}
				// alert("t="+t);
				// 多于一项
				if(t>1)
				{
					alert("只可以选择一项记录！");
					return false;
				}
				// 没有选
				if(t==0)
				{
					alert("请选择要更新的记录");
					return false;
				}
				// 选一项
				if(t==1)
				{
					// alert("success");
					return true;
				}
			}
		}
	}
// 用于更新时返回已选择的值
	function returnValue(checkName)
	{
		if(checkName.length==null)
		{
			return checkName.value;
		}
		else
		{
			for(var i=0;i<checkName.length;i++)
			{
				if(checkName[i].checked==true)
				{
					return checkName[i].value;
				}
			}
		}
	}	
// 检查删除
function checkDelete(checkName)
{	
 		var i = 0;
      	var flag = false;
		if( checkName == null)
	    {
			alert('没有要删除的数据!');
			return false;
        }
		if( checkName.length == null ){
	           	if(checkName.checked == true){
	           		flag = true;
        	   	}
		}
		else
		{
		      for(; i < checkName.length; i++)
		      {
		           	if( checkName[i].checked == true)
		           	{
	        	   		flag = true;
	           			break;
        	   		}
          		}
		}


          if( flag == false )
          {
			alert('请选择要删除的数据!');
			return false;
          }
	return confirm('您确定需要删除吗?');
}
// 多选检查
function checkMulti(checkName)
{	
		
 		var i = 0;
      	var flag = false;
		if( checkName == null)
	    {
			alert('没有选中的数据!');
			return false;
        }
		if( checkName.length == null )
		{
			
	           	if(checkName.checked == true)
	           	{
	           		flag = true;
        	   	}
		}
		else
		{
			
		      for(; i < checkName.length; i++)
		      {
		           	if( checkName[i].checked == true)
		           	{
	        	   		flag = true;
	           			break;
        	   		}
          		}
		}


          if( flag == false )
          {
			alert('请选择要操作的数据!');
			return false;
          }
          return true;
}
function toUrlOpen(url,w,h)
	{
		// window.open(url,'','width=width,
		// height=height,top='+(screen.height-height)/2+',left='+(screen.width-width)/2+',toolbar=no,
		// menubar=no, scrollbars=no, resizable=no,status=no');
		var top = (screen.availHeight-h)/2;
		var left = (screen.availWidth-w)/2;
		var options = "width=" + w + ",height=" + h + ",";
	    options += "scrollbars=no,location=no,menubar=no,toolbar=no,directories=no,top="+top+",left="+left;
	    var date=new Date();
	    n=date.getSeconds();
	    n=n+"ab";
	    var newWin=window.open(url,n, options);
	    newWin.focus();
	    // return newWin;
	}
// 在页面加载时使用(body的onload事件调用),让文本框来遮住选择框.
// sel是选择框的id值
// txt是文本框的id值
function setcss(sel,txt){
	var sel=document.getElementById(sel);
	var txt=document.getElementById(txt);
	var txtw=100;
	var selw=txtw+18;
	with(txt.style){
		position="absolute";
		width=txtw;
		left=null;
		top=null
	};
	with(sel.style){
		position="absolute";
		width=selw;
		left=null;
		top=null;
		clip="rect(0 "+selw+" 20 "+(selw-20)+")";
	};
}
// 在选择框之后文本框中使用(文本框的onkeyUp事件调用),智能输入文本框的值.
// sel是选择框的id值
// txt是文本框的id值
function showMsg(sel,txt){
	var sel=document.getElementById(sel);
	var txt=document.getElementById(txt);
	var txtValue=txt.value;
	var txtl=txtValue.length;	
	var l=sel.options.length;
	
	for(var i=0;i<l;i++){
		var optext = sel.options[i].text
		if(txtValue==optext.substring(0,txtl)){		
			txt.value=optext;
			// sel.value=optext;
			sel.selectedIndex = i;
			sel.value=sel.options[i].value;
			txt.focus();
			break;
		}
	}
}

function pjChage(sel,txt){
	var sel=document.getElementById(sel);
	var txt=document.getElementById(txt);
	var i=sel.selectedIndex;	
	txt.value=sel.options[i].text;
	txt.focus()
}
function checkToAdd(checkName)
{
	var flag = false;
	var i=0;
	if(checkName.length==null)
	{
		if(checkName.checked == true)
		{
			flag = true;
		}
	}
	for(; i < checkName.length; i++)
	{
       	if( checkName[i].checked == true)
       	{
	   		flag = true;
   			break;
   		}
        
    }
	if( flag == false )
	{
		alert('请选择增加项');
		return false;
	}
	var sure=window.confirm("确定要增加？");
	return sure;
}


/**
 * 检测是否有被选中
 * 
 * @param {Object}
 *            objNam CheckBox的id属性
 */
function chkCheckBoxChs(objNam){ // 檢測是否有選擇多选框的至少一项
    var obj = document.getElementsByName(objNam); // 獲取多選框數組
    var objLen = obj.length; // 獲取數據長度
    var objYN; // 是否有選擇
    var i;
    objYN = false;
    for (i = 0; i < objLen; i++) {
        if (obj[i].checked == true) {
            objYN = true;
            break;
        }
    }
    return objYN;
}
/**
 * 全选
 * 
 * @param {Object}
 *            a
 */
 function selAll(a){ 
  o=document.getElementsByName(a) ;
  for(var i=0;i<o.length;i++) 
  o[i].checked=event.srcElement.checked ;
 }
 /**
  * 全选方法,兼容firefox,推荐使用新方法老的将废弃
  * 
  * @param {Object}
  *            a
  */ 
function selAlls(a,event){ 
	  o=document.getElementsByName(a) ;
	  var e=window.event||event; 
	  var element =e.srcElement ? e.srcElement:e.target  ;
	  var resT = element.checked ;
	  for(var i=0;i<o.length;i++){
		  o[i].checked= resT ;
	  } 
}