var cff=new Array();
cff[0]=new Array('北京','BEIJING','BJ');
cff[1]=new Array('重庆','CHONGQING','CQ');
cff[2]=new Array('河北省','HEBEI','HB');
cff[3]=new Array('河南省','HENAN','HN');
cff[4]=new Array('云南省','YUNNAN','YN');
cff[5]=new Array('辽宁省','LIAONING','LN');
cff[6]=new Array('黑龙江省','HEILONGJIANG','HLJ');
cff[7]=new Array('湖南省','HUNAN','HN');
cff[8]=new Array('安徽省','ANHUI','AH');
cff[9]=new Array('山东省','SHANDONG','SD');
cff[10]=new Array('新疆','XINJIANG','XJ');
cff[11]=new Array('江苏省','JIANGSU','JS');
cff[12]=new Array('浙江省','ZHEJIANG','ZJ');
cff[13]=new Array('江西省','JIANGXI','JX');
cff[14]=new Array('湖北省','HUBEISHENG','HB');
cff[15]=new Array('广西','GUANGXI','GX');
cff[16]=new Array('甘肃省','GANSU','GS');
cff[17]=new Array('山西省','SHANXI','SX');
cff[18]=new Array('内蒙古','NEIMENGGU','NMG');
cff[19]=new Array('陕西省','SHANXI','SX');
cff[20]=new Array('吉林省','JILIN','JL');
cff[21]=new Array('福建省','FUJIAN','FJ');
cff[22]=new Array('贵州省','GUIZHOU','GZ');
cff[23]=new Array('广东省','GUANGDONG','GD');
cff[24]=new Array('青海省','QINGHAI','QH');
cff[25]=new Array('西藏','XIZANG','XZ');
cff[26]=new Array('四川省','SICHUAN','SC');
cff[27]=new Array('宁夏','NINGXIA','NX');
cff[28]=new Array('海南省','HAINAN','HN');
cff[29]=new Array('台湾','TAIWAN','TW');
cff[30]=new Array('香港','XIANGGANG','XG');
cff[31]=new Array('澳门','AOMEN','AM');
cff[32]=new Array('天津','TIANJIN','TJ');
cff[33]=new Array('上海','SHANGHAI','SH');
var flightcitys = cff;
function citytab(a) { 
	var c = document.getElementById("cityhead").getElementsByTagName("li"); 
	if (c) { 
		for (var b = 0; b < c.length; b++) 
			c[b].className = "search_li01"; 
		if (b = document.getElementById("li" + a)) 
			b.className = "search_li02" 
	} 
	if (c = document.getElementById("city_box").getElementsByTagName("div")) { 
		for (b = 1; b < c.length; b++) 
			c[b].className = "list_main unshow"; 
		if (b = document.getElementById("city" + a)) 
			b.className = "list_main" 
	} 
	document.getElementById("top_getiframe").style.height = document.getElementById("city_box").offsetHeight + 2 + "px"; 
}
String.prototype.trim=function(){return this.replace(/(^\s+)|(\s+$)/g,"")};String.prototype.format=function(){var a=arguments;return this.replace(/\{(\d+)\}/g,function(c,b){return a[b]})};function StringBuilder(){this.arr=[]}StringBuilder.prototype.append=function(a){this.arr.push(a)};StringBuilder.prototype.appendFormat=function(){for(var a=arguments[0],c=0;c<arguments.length-1;c++)a=a.replace(new RegExp("\\{"+c+"\\}"),arguments[c+1]);this.arr.push(a)};StringBuilder.prototype.toString=function(){return this.arr.join("")};
function citytab(a){var c=document.getElementById("cityhead").getElementsByTagName("li");if(c){for(var b=0;b<c.length;b++)c[b].className="search_li01";if(b=document.getElementById("li"+a))b.className="search_li02"}if(c=document.getElementById("city_box").getElementsByTagName("div")){for(b=1;b<c.length;b++)c[b].className="list_main unshow";if(b=document.getElementById("city"+a))b.className="list_main"}document.getElementById("top_getiframe").style.height=document.getElementById("city_box").offsetHeight+2+"px";}
function prefixTab(a){
//alert(a);
    var c=document.getElementById("cityall").getElementsByTagName("ul");
    if(c)
    {
	    for(var b=1;b<c.length;b++)
	    c[b].className="city_sugg unshow";
	    var city_sugg=document.getElementById("ul"+a)
	    if(city_sugg)
	    city_sugg.className="city_sugg";
	 }
    var d=document.getElementById("firstul").getElementsByTagName("a");
    if(d){
		for(var b=0;b<d.length;b++){ //alert(d[b].id);
			d[b].className="off";
		}
		var e =document.getElementById("a_"+a);
		 //alert(e.id);
		if(e)
			e.className="on";
	}
    document.getElementById("top_getiframe").style.height=document.getElementById("city_box").offsetHeight+2+"px";
};

function replaceHtml(el, html) {
    var oldEl = typeof el == "string" ? document.getElementById(el) : el;
    var newEl = oldEl.cloneNode(false);
    newEl.innerHTML = html;
    oldEl.parentNode.replaceChild(newEl, oldEl);
    return newEl;
};

//应市场要求点击空白处也要填上城市值，故设定一个全局的变量，保存当前输入框的id
var global_id='';
var parentbject;
window.city_suggest = function(){
	this.object = '';
	this.id2 = '';
	this.taskid = 0;
	this.delaySec = 10; // 默认延迟多少毫秒出现提示框
	this.hot= [];
	this.letter = [];
	this.hotelcity ={};
	/**
	* 初始化类库
	*/
	this.init_zhaobussuggest=  function(){
		var objBody = document.getElementsByTagName("body").item(0);
		var objiFrame = document.createElement("iframe");
		var objplatform = document.createElement("div");
		objiFrame.setAttribute('id','top_getiframe');
		objiFrame.setAttribute("src","about:blank");
		objiFrame.style.zindex='100';
		objiFrame.style.border='0';
		objiFrame.style.position = 'absolute';
		objplatform.setAttribute('id','top_getplatform');
		objplatform.setAttribute('align','left');
		objplatform.style.position = 'absolute';
		objplatform.style.border = 'solid 1px #7f9db9';
		objplatform.style.background = '#ffffff';
		if(objBody){
		    objBody.appendChild(objiFrame);
		    if(objiFrame){
		        objiFrame.ownerDocument.body.appendChild(objplatform);
		    }
		}
		if(!document.all) {
			window.document.addEventListener("click",this.hidden_suggest,false);
		}else{
			window.document.attachEvent("onclick",this.hidden_suggest);
		}
	}

	/***************************************************fill_div()*********************************************/
	//函数功能：动态填充div的内容，该div显示所有的提示内容
	//函数参数：allplat 一个字符串数组，包含了所有可能的提示内容
	this.fill_div = function(allplat){
		
		var _html=new StringBuilder;
		_html.append('<div id="city_box" class="choose_frame">');
		_html.append('\t<div id="cityhead" class="list_head">');
		_html.append('\t\t<ul class="fleft">');
		_html.append('\t\t\t<li id="lihot" class="search_li02" onclick="citytab(\'hot\');document.getElementById(\'span_sort\').style.display=\'none\';">\u70ed\u95e8\u57ce\u5e02</li>');
		_html.append('\t\t\t<li id="liall" class="search_li01" onclick="citytab(\'all\');document.getElementById(\'span_sort\').style.display=\'block\';">\u66f4\u591a\u57ce\u5e02</li>');
		_html.append("\t\t</ul>");
		_html.append('\t\t<span id="span_sort" style="display:none;" class="fcenter">\uff08\u6309\u62fc\u97f3\u9996\u5b57\u6bcd\uff09</span>');
        _html.append('\t\t<a onclick="parentbject.hidden();" style="cursor:pointer;" class="fright" title="\u5173\u95ed"><img src="http://img.17u.cn/hotel/images/www_17u_cn/default/search_img04.gif" /></a>');
        _html.append("\t</div>");
        _html.append('\t<div id="cityhot" class="list_main ">');
        _html.append('\t\t<ul class="city_sugg">');
        for(var i=0;i<this.hot.length;i++)_html.appendFormat("\t\t\t<li><a href=\"javascript:void(0);\" onclick=\"parentbject.add_input_text('{0}','{1}');\">{2}</a></li>",this.hot[i],this.hot[i],this.hot[i]);
        _html.append("\t\t</ul>");_html.append('\t\t<span class="more_city link01"><a onclick="citytab(\'all\');" style="text-decoration:underline;cursor:pointer;">\u66f4\u591a\u57ce\u5e02</a></span>');
        _html.append("\t</div>");_html.append('\t<div id="cityall" class="list_main unshow">');
        _html.append('\t\t<ul id="firstul" class="city_list2 link01">');
        for(i=0;i<this.letter.length;i++){i==0?_html.appendFormat("\t\t\t<li><a href=\"javascript:void(0);\" style=\"cursor:pointer;\" id=\"a_{2}\" class=\"on\" onclick=\"prefixTab('{0}');\">{1}</a></li>",this.letter[i],this.letter[i],this.letter[i]):_html.appendFormat("\t\t\t<li><a href=\"javascript:void(0);\" style=\"cursor:pointer;\" id=\"a_{2}\" class=\"off\" onclick=\"prefixTab('{0}');\">{1}</a></li>",this.letter[i],this.letter[i],this.letter[i]);}
        _html.append("\t\t</ul>");
        for(i=0;i<this.letter.length;i++){i==0?_html.appendFormat('\t\t<ul id="ul{0}" class="city_sugg">',this.letter[i]):_html.appendFormat('\t\t<ul id="ul{0}" class="city_sugg unshow">',this.letter[i]);
        var c=this.hotelcity[this.letter[i]];if(c)for(var j=0;j<c.length;j++)_html.appendFormat("\t\t\t<li><a href=\"javascript:void(0);\" onclick=\"parentbject.add_input_text('{0}','{1}');\">{2}</a></li>",c[j],c[j],c[j]);
        _html.appendFormat("\t\t</ul>")}_html.append("\t</div>");_html.append("</div>");
        msgplat = _html.toString();
        
        var el = document.getElementById("top_getplatform");

        window.setTimeout(function(){
            replaceHtml(el, msgplat);
            document.getElementById("top_getiframe").style.width = document.getElementById("top_getplatform").clientWidth+2;
            document.getElementById("top_getiframe").style.height = document.getElementById("top_getplatform").clientHeight+2;
        },10);
		
		
	}

	/***************************************************fix_div_coordinate*********************************************/
	//函数功能：控制提示div的位置，使之刚好出现在文本输入框的下面
	this.fix_div_coordinate = function(){
		var leftpos=0;
		var toppos=0;
		
		var aTag = this.object;
		do {
			aTag = aTag.offsetParent;
			leftpos	+= aTag.offsetLeft;
			toppos += aTag.offsetTop;
		}while(aTag.tagName!="BODY"&&aTag.tagName!="HTML");
		document.getElementById("top_getiframe").style.width = this.object.offsetWidth+50 + 'px';
		if(document.layers){
			document.getElementById("top_getiframe").style.left = this.object.offsetLeft	+ leftpos + "px";
			document.getElementById("top_getiframe").style.top = this.object.offsetTop +	toppos + this.object.offsetHeight + 2 + "px";
		}else{
			document.getElementById("top_getiframe").style.left =this.object.offsetLeft	+ leftpos  +"px";
			document.getElementById("top_getiframe").style.top = this.object.offsetTop +	toppos + this.object.offsetHeight + 'px';
		}
		
		if(document.layers){
			document.getElementById("top_getplatform").style.left = this.object.offsetLeft	+ leftpos + "px";
			document.getElementById("top_getplatform").style.top = this.object.offsetTop +	toppos + this.object.offsetHeight + 2 + "px";
		}else{
			document.getElementById("top_getplatform").style.left =this.object.offsetLeft	+ leftpos  +"px";
			document.getElementById("top_getplatform").style.top = this.object.offsetTop +	toppos + this.object.offsetHeight + 'px';
		}

        ///如果框出屏幕外，向左 245px(城市弹出框宽度-文本框宽度)
	    if ((this.object.offsetLeft+leftpos+370)>screen.width)
	    {
            document.getElementById("top_getiframe").style.left = document.getElementById("top_getplatform").style.left = this.object.offsetLeft	+ leftpos -245 + "px";
	    }

	}

    /***************************************************hidden_suggest*********************************************/
	//函数功能：隐藏提示框
	this.hidden_suggest = function (event){
	//alert("beingdone");
		if (event.target) targ = event.target;  else if (event.srcElement) targ = event.srcElement;
		if(targ.tagName!='LI' && targ.tagName!='A'){	
		    document.getElementById("top_getiframe").style.visibility = "hidden";
		    document.getElementById("top_getplatform").style.visibility = "hidden";
		}
//		//应市场要求鼠标点击空白处，也要填上城市值的处理方法
//		var nodes = document.getElementById("top_getplatform").getElementsByTagName("li");
//		if(nodes!=null && typeof(nodes)!='undefined'){
//		    for(var i=0;i<nodes.length;i++){
//			    if(nodes[i].className == "ds_selected"){
//			        if(nodes[i].childNodes.length>1){
//			            if(document.getElementById(global_id))
//			            {
//			                document.getElementById(global_id).value=nodes[i].childNodes[1].innerHTML;
//			            }
//				    }
//			    }
//		    }
//		}
//		else{
//		    document.getElementById(global_id).value='';
//		}

	}
	
	this.hidden = function(){if(document.getElementById("top_getiframe")){document.getElementById("top_getiframe").style.visibility = "hidden";document.getElementById("top_getplatform").style.visibility = "hidden";}}

	/***************************************************show_suggest*********************************************/
	//函数功能：显示提示框
	this.show_suggest = function (){
		document.getElementById("top_getiframe").style.visibility = "visible";
		document.getElementById("top_getplatform").style.visibility = "visible";
	}

	this.is_showsuggest= function (){
		if(document.getElementById("top_getplatform").style.visibility == "visible") return true;else return false;
	}

	this.sleep = function(n){
		var start=new Date().getTime(); //for opera only
		while(true) if(new Date().getTime()-start>n) break;
	}

	this.ltrim = function (strtext){
		return strtext.replace(/[\$&\|\^*%#@! ]+/, '');
	}

    /***************************************************add_input_text*********************************************/
	//函数功能：当用户选中时填充相应的城市名字

	this.add_input_text = function (keys,szm){
		keys=this.ltrim(keys);
		if(this.object.id=='img_showcity')
		    CallDPCityPage(keys);
		else
		{
		    this.object.value = keys;
		    var id=this.object.id;
		    var id2 = this.id2;	
		    if(document.getElementById(this.id2)){
			    document.getElementById(this.id2).value = szm;
		    }
		    document.getElementById(id).style.color="#000000";
		    document.getElementById(id).value=keys;
		    document.getElementById("top_getiframe").style.visibility = "hidden";
		    document.getElementById("top_getplatform").style.visibility = "hidden";
		    //单击时设置自动跳到下一个输入框
		    if(id!=null && id=="C_SearchByPoly1_txt_orgcity")
		    {
		        if(document.getElementById("C_SearchByPoly1_txt_descity"))
		        {
		         Text_OnClick("C_SearchByPoly1_txt_descity");
		        }
		    }
		}
     }
     
     this.ajaxac_getkeycode = function (e){
		var code;
		if (!e) var e = window.event;
		if (e.keyCode) code = e.keyCode;
		else if (e.which) code = e.which;
		return code;
	}

    /***************************************************display*********************************************/
	//函数功能：入口函数，将提示层div显示出来
	//输入参数：object 当前输入所在的对象，如文本框
	//输入参数：e IE事件对象
	this.display = function (object,id2,e){
        if(object)
        {
            object.select();
        }
		this.id2 = id2;
		if(!document.getElementById("top_getplatform")) this.init_zhaobussuggest();
		if (!e) e = window.event;
		e.stopPropagation;
		e.cancelBubble = true;
		if (e.target) targ = e.target;  else if (e.srcElement) targ = e.srcElement;
		if (targ.nodeType == 3)  targ = targ.parentNode;

		this.object = object;
	
		if(window.opera) this.sleep(100);//延迟0.1秒
		parentbject = this;
		if(this.taskid) window.clearTimeout(this.taskid);
        this.taskid=setTimeout("parentbject.localtext();" , this.delaySec);
	}

	//函数功能：从本地js数组中获取要填充到提示层div中的文本内容
	this.localtext = function(){
		var id=this.object.id;
		parentbject.show_suggest();
		parentbject.fill_div('');
		parentbject.fix_div_coordinate();
	}
};

var letterH=['A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','W','X','Y','Z'];
var hotH=['北京','上海','广州','深圳','杭州','南京','成都','武汉','青岛','大连','苏州','三亚','厦门','海口','西安','长沙','昆明','沈阳','香港','澳门'];
var citysH={A:['阿坝','阿克苏','阿拉尔','阿拉善盟','阿里','定安县','安康','安庆','鞍山','安顺','安阳'],B:['白城','百色','白沙','白山','白银','保定','宝鸡','保山','保亭','包头','巴彦淖尔市','巴音郭楞','巴中','北海','北京','蚌埠','本溪','毕节','滨州','博尔塔拉','亳州'],C:['沧州','长白山','长春','常德','昌都','昌吉','昌江','长沙','长治','常州','巢湖','朝阳','潮州','承德','成都','澄迈县','郴州','赤峰','池州','重庆','楚雄','滁州','崇左'],D:['大理','大连','丹东','儋州','大庆','大同','大兴安岭','达州','德宏','德阳','德州','定西','迪庆','东方','东莞','东营'],E:['鄂尔多斯','恩施','鄂州'],F:['防城港','佛山','抚顺','阜新','阜阳','福州','抚州'],G:['甘南','赣州','甘孜','广安','广元','广州','贵港','桂林','贵阳','果洛','固原'],H:['海北','海东','海口','海南藏族','海西','哈密','邯郸','杭州','汉中','哈尔滨','鹤壁','河池','合肥','鹤岗','黑河','衡水','衡阳','和田','河源','菏泽','贺州','香港','红河','洪湖市','淮安','淮北','怀化','淮南','花莲','黄冈','黄南','黄山','黄石','呼和浩特','惠州','葫芦岛','呼伦贝尔','湖州'],J:['佳木斯','吉安','江门','焦作','嘉兴','嘉峪关','揭阳','吉林','基隆','济南','金昌','晋城','景德镇','荆门','荆州','金华','济宁','晋中','锦州','九江','酒泉','鸡西','济源'],K:['开封','喀什','克拉玛依','克孜勒苏柯尔克孜','昆明'],L:['来宾','莱芜','廊坊','兰州','拉萨','乐东','乐山','凉山','连云港','聊城','辽阳','辽源','丽江','临沧','临汾','临高县','陵水','临夏','临沂','林芝','丽水','六安','六盘水','柳州','陇南','龙岩','娄底','漯河','洛阳','泸州','吕梁'],M:['马鞍山','茂名','眉山','梅州','绵阳','牡丹江'],N:['南昌','南充','南京','南宁','南平','南通','南阳','那曲','内江','宁波','宁德','怒江'],P:['盘锦','攀枝花','平顶山','平凉','萍乡','普洱','莆田','濮阳'],Q:['黔东南','潜江','黔南','黔西南','青岛','庆阳','清远','秦皇岛','钦州','琼海','琼中','齐齐哈尔','七台河','泉州','曲靖','衢州'],R:['日喀则','日照'],S:['三门峡','三明','三亚','上海','商洛','商丘','上饶','山南','汕头','汕尾','韶关','绍兴','邵阳','神农架林区','沈阳','深圳','石家庄','石河子','十堰','石嘴山','双鸭山','绥化','遂宁','朔州','四平','松原','随州','宿迁','苏州','宿州'],T:['泰安','台东','台南','台北','太原','台州','泰州','唐山','天津','天门','天水','铁岭','铜川','通化','通辽','铜陵','铜仁','吐鲁番','图木舒克','屯昌县'],W:['万宁','潍坊','威海','渭南','文昌','文山','温州','乌海','武汉','芜湖','五家渠','乌兰察布市','乌鲁木齐','乌苏里江','武威','无锡','五指山','吴忠','梧州'],X:['厦门','西安','襄樊','湘潭','湘西','咸宁','仙桃','咸阳','孝感','锡林郭勒盟','兴安盟','兴城','邢台','西宁','新乡','信阳','新余','忻州','西双版纳','宣城','许昌','徐州'],Y:['雅安','延安','延边','盐城','阳江','阳泉','扬州','烟台','宜宾','宜昌','宜春','伊春','伊犁','银川','营口','鹰潭','益阳','永州','岳阳','玉林','榆林','运城','云浮','玉树','玉溪'],Z:['枣庄','张家界','张家口','张掖','漳州','湛江','肇庆','昭通','郑州','镇江','中山','周口','舟山','珠海','驻马店','株洲','淄博','自贡','资阳','遵义','中卫']};
var suggestH = new city_suggest();suggestH.letter = letterH;suggestH.hot = hotH;suggestH.hotelcity = citysH;

var letterF=['A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','S','T','W','X','Y','Z'];
var hotF=["北京","北京首都","北京南苑","上海","上海虹桥","上海浦东","深圳","杭州","广州","成都","南京","武汉","呼和浩特","重庆","长沙","昆明","西安","青岛","天津","宁波","厦门","太原","大连","济南"];
var citysF = { A: ['阿克苏', '阿勒泰', '安康', '安庆', '安顺', '鞍山'], B: ['百色', '蚌埠', '包头', '保山', '北海', '北京首都', '北京南苑'], C: ['昌都', '长春', '长海', '长沙', '长治', '常德', '常州', '朝阳', '成都', '赤峰', '重庆'], D: ['达县', '大理', '大连', '大庆', '大同', '丹东', '德宏', '迪庆', '东营', '敦煌'], E: ['鄂尔多斯', '恩施'], F: ['佛山', '福州', '阜阳', '富蕴'], G: ['赣州', '固原', '格尔木', '光化', '广汉', '广州', '贵阳', '桂林'], H: ['哈尔滨', '哈密', '海口', '海拉尔', '邯郸', '汉中', '杭州', '合肥', '和田', '黑河', '衡阳', '呼和浩特', '怀化', '淮安', '黄山', '黄岩', '徽州'], J: ['吉安', '吉林', '济南', '济宁', '佳木斯', '嘉峪关', '锦州', '晋江', '井冈山', '景德镇', '景洪', '九江', '九寨沟', '酒泉'], K: ['喀纳斯', '喀什', '克拉玛依', '库车', '库尔勒', '昆明'], L: ['拉萨', '兰州', '黎平', '丽江', '连城', '连云港', '梁平', '林西', '林芝', '临沧', '临沂', '柳州', '龙岩', '庐山', '泸州', '路桥', '洛阳'], M: ['满洲里', '芒市', '梅县', '绵阳', '漠河', '牡丹江'], N: ['那拉提', '南昌', '南充', '南京', '南宁', '南通', '南阳', '宁波'], P: ['攀枝花', '普洱'], Q: ['齐齐哈尔', '且末', '秦皇岛', '青岛', '庆阳', '衢州', '泉州'], S: ['三亚', '沙市', '汕头', '鄯善', '上海虹桥', '上海浦东', '深圳', '沈阳', '石家庄', '思茅'], T: ['塔城', '台州', '太原', '唐山', '天津', '通化', '通辽', '铜仁'], W: ['万州', '威海', '潍坊', '温州', '文山', '乌海', '无锡', '梧州', '武汉', '武夷山', '乌兰浩特', '乌鲁木齐'], X: ['西安', '西昌', '西宁', '西双版纳', '锡林浩特', '厦门', '香格里拉', '襄樊', '兴城', '兴宁', '兴义', '邢台', '徐州'], Y: ['烟台', '延安', '延吉', '盐城', '伊宁', '宜宾', '宜昌', '义乌', '银川', '永州零陵', '榆林', '元谋', '运城'], Z: ['湛江', '张家界', '昭通', '郑州', '芷江', '中甸', '舟山', '珠海', '遵义', '中卫'] };
var suggestF = new city_suggest();suggestF.letter = letterF;suggestF.hot = hotF;suggestF.hotelcity = citysF;

//兼容的onclick
function Text_OnClick(id)
{ 
   var ie=navigator.appName=="Microsoft Internet Explorer" ? true : false;
   if(ie)
   {
       document.getElementById(id).click();
   }
   else
   {
       var a=document.createEvent('MouseEvents');
       a.initEvent('click', true, true);
       document.getElementById(id).dispatchEvent(a);
   }
}
var item_parentbject;
window.item_city_suggest = function(){
	this.item_Remoreurl = ''; // 远程URL地址
	this.item_object = '';
	this.item_id2 = '';
	this.item_taskid = 0;
	this.item_delaySec = 100; // 默认延迟多少毫秒出现提示框
	this.item_lastkeys_val = 0;
	this.item_lastinputstr = '';
	this.item_citys = new Array();
	/**
	*赋值城市数组
	*/
	this.item_setArr_Citys = function(citys){
	    this.item_citys = citys;
	}
	/**
	* 初始化类库
	*/
	this.item_init_zhaobussuggest=  function(){
		var objBody = document.getElementsByTagName("body").item(0);
		var objiFrame = document.createElement("iframe");
		var objplatform = document.createElement("div");
		objiFrame.setAttribute('id','top_getiframe');
		objiFrame.style.zindex='100';
		objiFrame.style.border='0';
		objiFrame.style.position = 'absolute';
		objplatform.setAttribute('id','top_getplatform');
		objplatform.setAttribute('align','left');
		objplatform.style.position = 'absolute';
		objplatform.style.border = 'solid 1px #7f9db9';
		objplatform.style.background = '#ffffff';
		objplatform.style.padding = '0px 3px 3px 3px';
		objBody.appendChild(objiFrame);
		objiFrame.ownerDocument.body.appendChild(objplatform);
		if(!document.all) {
			window.document.addEventListener("click",this.item_hidden_suggest,false);
		}else{
			window.document.attachEvent("onclick",this.item_hidden_suggest);
		}
	}
    /**********************************************getPinYinByCity()*******************************************/
    //
    this.item_getPinYinByCity = function(cityName){
	    var pinYin = "";
	    for(var i = 0,len = this.item_citys.length;i<len;i++){
		    if(cityName == this.item_citys[i][0]){
			    pinYin = this.item_citys[i][1];
			    break;
		    }
	    }
	    return pinYin;
    }
	/***************************************************fill_div()*********************************************/
	//函数功能：动态填充div的内容，该div显示所有的提示内容
	//函数参数：allplat 一个字符串数组，包含了所有可能的提示内容
	this.item_fill_div = function(allplat){
		var msgplat = '';
		var all = '';
		var spell = '';
		var chinese = '';
		var platkeys = this.item_object.value;
        platkeys=this.ltrim(platkeys);
		if(!platkeys){
			msgplat += '<div class="ds_input_tips ds_input_tips_no">输入中文/拼音或&uarr;&darr;选择</div>';
			for(i=0;i<allplat.length;i++){
			    all=allplat[i].split(",");
				spell=all[0];
				chinese=all[1];
				szm=all[2];
			    //用于机票-城市附近机场
				if(szm != null && szm != "" && szm.indexOf("$") > -1){
				    msgplat += '<dd><div class="suggest-key" style="color:#0055aa">'+ spell +
			           '</div><div class="suggest-result" style="color:#0055aa">' + chinese + '</div><div style="display:none"></div></dd>';
				    var arr_szm = szm.split("$");//城市附近有机场城市
				    if(arr_szm.length>0){
				        msgplat += '<dl><dt>·该城市无机场</dt></dl>';
				        for(var j=1; j<arr_szm.length; j++){
				            msgplat += '<li onclick="item_parentbject.item_add_input_text(\'' + arr_szm[j].split('-')[0] + '\',\'\')">'
			                        +  '<div class="suggest-key" style="float:left;color:#414141;padding-left:2px">·邻近机场：</div>'
			                        +  '<div class="suggest-result" style="float:left;color:#414141;">' + arr_szm[j].split('-')[0] + '</div>'
			                        +  '<div style="float:left;color:#414141;">' + arr_szm[j].split('-')[1] + '公里</div></li>';
				        }
			        }
				}
				else{
				    msgplat += '<li onclick="item_parentbject.item_add_input_text(\'' + chinese + '\',\'' + szm + '\')"><div class="suggest-key">'+ spell +
				       '</div><div class="suggest-result">' + chinese + '</div><div style="display:none">' + szm + '</div></li>';
				}
			}
        }
		else {
			if(allplat.length < 1 || !allplat[0]){
				msgplat += '<div class="ds_input_tips ds_input_tips_no">对不起，找不到：'+platkeys+'</div>';
			}
			else{
			    if(allplat.length == 1){
			        msgplat += '<div class="ds_input_tips ds_input_tips_one">'+platkeys+'，按拼音排序</div>';
			    }
			    else{
			        msgplat += '<div class="ds_input_tips ds_input_tips_full">'+platkeys+'，按拼音排序</div>';
			    }
			    for(i=0;i<allplat.length;i++){
					all=allplat[i].split(",");
					spell=all[0];
					var firstchar = spell.substring(0,1);
					spell = firstchar.toUpperCase() + spell.substring(1,spell.length);
					chinese=all[1];
					szm=all[2];
					//用于机票-城市附近机场
				    if(szm != null && szm != "" && szm.indexOf("$") > -1){
				        msgplat += '<dd><div class="suggest-key" style="color:#0055aa">'+ spell +
				           '</div><div class="suggest-result" style="color:#0055aa">' + chinese + '</div><div style="display:none"></div></dd>';
				        var arr_szm = szm.split("$");//城市附近有机场城市
				        if(arr_szm.length>0){
				            msgplat += '<dl><dt>·该城市无机场</dt></dl>';
				            for(var j=1; j<arr_szm.length; j++){
				                msgplat += '<li onclick="item_parentbject.item_add_input_text(\'' + arr_szm[j].split('-')[0] + '\',\'\')">'
				                        +  '<div class="suggest-key" style="float:left;color:#414141;padding-left:2px">·邻近机场：</div>'
				                        +  '<div class="suggest-result" style="float:left;color:#414141;">' + arr_szm[j].split('-')[0] + '</div>'
				                        +  '<div style="float:left;color:#414141;">' + arr_szm[j].split('-')[1] + '公里</div></li>';

				            }
			            }
				    }
				    else{
				        msgplat += '<li onclick="item_parentbject.item_add_input_text(\'' + chinese + '\',\'' + szm + '\')"><div class="suggest-key">'+ spell +
				           '</div><div class="suggest-result">' + chinese + '</div><div style="display:none">' + szm + '</div></li>';
				    }
				}
			}
		}
		document.getElementById("top_getplatform").innerHTML =  '<div class="suggest-container">'+msgplat+'</div>';//城市结果列表呈现

		var nodes = document.getElementById("top_getplatform").childNodes;//列表外框<div>
		var nodes_li = document.getElementById("top_getplatform").getElementsByTagName("li");//城市列表节点
		nodes[0].className = "suggest-container";
		if(allplat.length >= 1 && allplat[0]){
			nodes_li[0].className = "ds_selected";
		}
		this.item_lastkeys_val = 0;
		for(var i=1;i<nodes_li.length;i++){
			nodes_li[i].onmouseover = function(){
				this.className = "top_mover";
			}

			nodes_li[i].onmouseout = function(){
				if(item_parentbject.lastkeys_val==(item_parentIndexOf(this)-1)){this.className = "ds_selected";}
				else{this.className = "top_mout";}
			}
		}
		document.getElementById("top_getiframe").style.width = document.getElementById("top_getplatform").clientWidth+2;
        document.getElementById("top_getiframe").style.height = document.getElementById("top_getplatform").clientHeight+2;
	}

	/***************************************************fix_div_coordinate*********************************************/
	//函数功能：控制提示div的位置，使之刚好出现在文本输入框的下面
	this.item_fix_div_coordinate = function(){
		var leftpos=0;
		var toppos=0;
		/*
		aTag = this.item_object;
		do {
			aTag = aTag.offsetParent;
			leftpos	+= aTag.offsetLeft;
			toppos += aTag.offsetTop;
		}while(aTag.tagName!="BODY");
		*/
		//linden.guo modify at 2009-04-23 11:35
		var aTag = this.item_object;
		do {
			aTag = aTag.offsetParent;
			leftpos	+= aTag.offsetLeft;
			toppos += aTag.offsetTop;
		}while(aTag.tagName!="BODY"&&aTag.tagName!="HTML");
		document.getElementById("top_getiframe").style.width = '175px';
		if(document.layers){
			document.getElementById("top_getiframe").style.left = this.item_object.offsetLeft	+ leftpos + "px";
			document.getElementById("top_getiframe").style.top = this.item_object.offsetTop +	toppos + this.item_object.offsetHeight + 2 + "px";
		}else{
			document.getElementById("top_getiframe").style.left =this.item_object.offsetLeft	+ leftpos  +"px";
			document.getElementById("top_getiframe").style.top = this.item_object.offsetTop +	toppos + this.item_object.offsetHeight + 'px';
		}
		if(document.layers){
			document.getElementById("top_getplatform").style.left = this.item_object.offsetLeft	+ leftpos + "px";
			document.getElementById("top_getplatform").style.top = this.item_object.offsetTop +	toppos + this.item_object.offsetHeight + 2 + "px";
		}else{
			document.getElementById("top_getplatform").style.left =this.item_object.offsetLeft	+ leftpos  +"px";
			document.getElementById("top_getplatform").style.top = this.item_object.offsetTop +	toppos + this.item_object.offsetHeight + 'px';
		}
	}

    /***************************************************hidden_suggest*********************************************/
	//函数功能：隐藏提示框
	this.item_hidden_suggest = function (){
		//this.item_lastkeys_val = 0;
		//当this.item_lastkeys_val有值时,不清零
		if(this.item_lastkeys_val == null || this.item_lastkeys_val < 0){
		    this.item_lastkeys_val = 0;
		}
		document.getElementById("top_getiframe").style.visibility = "hidden";
		document.getElementById("top_getplatform").style.visibility = "hidden";
	}

    this.item_onblur=function(object){
    //应市场要求鼠标点击空白处，也要填上城市值的处理方法
		var nodes = document.getElementById("top_getplatform").getElementsByTagName("li");
		if(nodes!=null && typeof(nodes)!='undefined'){
		    for(var i=0;i<nodes.length;i++){
			    if(nodes[i].className == "ds_selected"){
			        if(nodes[i].childNodes.length>1){
			            if(object)
			            {
			                object.value=nodes[i].childNodes[1].innerHTML;
			            }
				    }
			    }
		    }
		}
		else{
		    object.value='';
		}
    }
	/***************************************************show_suggest*********************************************/
	//函数功能：显示提示框
	this.item_show_suggest = function (){
		document.getElementById("top_getiframe").style.visibility = "visible";
		document.getElementById("top_getplatform").style.visibility = "visible";
	}

	this.is_showsuggest= function (){
		if(document.getElementById("top_getplatform").style.visibility == "visible") return true;else return false;
	}

	this.sleep = function(n){
		var start=new Date().getTime(); //for opera only
		while(true) if(new Date().getTime()-start>n) break;
	}

	this.ltrim = function (strtext){
		return strtext.replace(/[\$&\|\^*%#@! ]+/, '');
	}

    /***************************************************add_input_text*********************************************/
	//函数功能：当用户选中时填充相应的城市名字

	this.item_add_input_text = function (keys,szm){
		keys=this.ltrim(keys)
		this.item_object.value = keys;
		var id=this.item_object.id;
		var id2 = this.item_id2;
		if(document.id2){
			document.getElementById(this.item_id2).value = szm;
		}
		document.getElementById(id).style.color="#000000";
		document.getElementById(id).value=keys;
		//输入时设置自动跳到下一个输入框
		if(id!=null && id=="C_SearchByPoly1_txt_orgcity")
		{
		    if(document.getElementById("C_SearchByPoly1_txt_descity"))
		    {
		     Text_OnClick("C_SearchByPoly1_txt_descity");
		    }
		}
     }

	/***************************************************keys_handleup*********************************************/
	//函数功能：用于处理当用户用向上的方向键选择内容时的事件
	this.item_keys_handleup = function (){
		if(this.item_lastkeys_val > 0) this.item_lastkeys_val--;
		var nodes = document.getElementById("top_getplatform").getElementsByTagName("li");
		if(this.item_lastkeys_val < 0) this.item_lastkeys_val = nodes.length-1;
		var b = 0;
		for(var i=0;i<nodes.length;i++){
			if(b == this.item_lastkeys_val){
				nodes[i].className = "ds_selected";
				if(nodes[i].childNodes.length>1){
				    this.item_add_input_text(nodes[i].childNodes[1].innerHTML);
				}
			}else{
				nodes[i].className = "top_mout";
			}
			b++;
		}
	}

	/***************************************************keys_handledown*********************************************/
	//函数功能：用于处理当用户用向下的方向键选择内容时的事件
	this.item_keys_handledown = function (){
		this.item_lastkeys_val++;
		var nodes = document.getElementById("top_getplatform").getElementsByTagName("li");
		if(this.item_lastkeys_val >= nodes.length) {
			this.item_lastkeys_val--;
			return;
		}
		var b = 0;
		for(var i=0;i<nodes.length;i++){
			if(b == this.item_lastkeys_val){
				nodes[i].className = "ds_selected";
				if(nodes[i].childNodes.length>1){
				    this.item_add_input_text(nodes[i].childNodes[1].innerHTML);
				}
			}else{
				nodes[i].className = "top_mout";
			}
			b++;
		}
	}

	this.item_ajaxac_getkeycode = function (e)
	{
		var code;
		if (!e) var e = window.event;
		if (e.keyCode) code = e.keyCode;
		else if (e.which) code = e.which;
		return code;
	}

	/***************************************************keys_enter*********************************************/
	//函数功能：用于处理当用户回车键选择内容时的事件
	this.item_keys_enter = function (){
		var nodes = document.getElementById("top_getplatform").getElementsByTagName("li");
		for(var i=0;i<nodes.length;i++){
			if(nodes[i].className == "ds_selected"){
			    if(nodes[i].childNodes.length>1){
				    this.item_add_input_text(nodes[i].childNodes[1].innerHTML);
				}
			}
		}
		this.item_hidden_suggest();
	}

    /***************************************************display*********************************************/
	//函数功能：入口函数，将提示层div显示出来
	//输入参数：object 当前输入所在的对象，如文本框
	//输入参数：e IE事件对象
	this.item_display = function (object,id2,e,citys){
	    //this.item_setArr_Citys(citys);
	    /*第二次触发城市控件，默认全选。*/
		var content = object.value;
		global_id=object.id;

		var flag = this.item_getPinYinByCity(content)
		if(flag!="") 
		{
		    object.focus();
		    object.select();
		}
		this.item_id2 = id2;
		if(!document.getElementById("top_getplatform")) this.item_init_zhaobussuggest();
		if (!e) e = window.event;
		e.stopPropagation;
		e.cancelBubble = true;
		if (e.target) targ = e.target;  else if (e.srcElement) targ = e.srcElement;
		if (targ.nodeType == 3)  targ = targ.parentNode;

		var inputkeys = this.item_ajaxac_getkeycode(e);
		switch(inputkeys){
			case 38: //向上方向键
				this.item_keys_handleup(object.id);
			    return;break;
			case 40: //向下方向键
				if(this.is_showsuggest()) this.item_keys_handledown(object.id); else this.item_show_suggest();
			    return;break;
			case 39: //向右方向键
				return;break;
			case 37: //向左方向键
				return;break;
			case 13: //对应回车键
			    this.item_keys_enter();
			    return;break;
			case 18: //对应Alt键
				this.item_hidden_suggest();
			    return;break;
			case 27: //对应Esc键
				this.item_hidden_suggest();
			    return;break;
		}

		this.item_object = object;
		if(window.opera) this.sleep(100);//延迟0.1秒
		item_parentbject = this;
		if(this.item_taskid) window.clearTimeout(this.item_taskid);
        this.item_taskid=setTimeout("item_parentbject.item_localtext();" , this.item_delaySec)

	}

	//函数功能：从本地js数组中获取要填充到提示层div中的文本内容
	this.item_localtext = function(){
		var id=this.item_object.id;
        var suggestions="";
        suggestions=this.item_getSuggestionByName();
        if(suggestions==""){
	        item_parentbject.item_show_suggest();
	        item_parentbject.item_fill_div("");
	        item_parentbject.item_fix_div_coordinate();	
        }
        else{
	        suggestions=suggestions.substring(0,suggestions.length-1);
	        item_parentbject.item_show_suggest();
	        item_parentbject.item_fill_div(suggestions.split(';'));
	        item_parentbject.item_fix_div_coordinate();	
	    }
	}

	/***************************************************getSuggestionByName*********************************************/
	//函数功能：从本地js数组中获取要填充到提示层div中的城市名字
	this.item_getSuggestionByName = function(){
		platkeys = this.item_object.value;
		var str="";
        platkeys=this.ltrim(platkeys);
		if(!platkeys){
			return str;
        }
		else{
		   platkeys=platkeys.toUpperCase();
			for(i=0;i<this.item_citys.length;i++){
			    if((this.item_citys[i][0].toUpperCase().indexOf(platkeys)!=-1)||
				   this.item_getLeftStr(this.item_citys[i][1],platkeys.length).toUpperCase()==platkeys||
				   this.item_getLeftStr(this.item_citys[i][2],platkeys.length).toUpperCase()==platkeys)
					str+=this.item_citys[i][1]+","+this.item_citys[i][0]+","+this.item_citys[i][2]+";";
			}
			return str;
		}
	}

	/***************************************************getLeftStr************* *************************************/
    //函数功能：得到左边的字符串
    this.item_getLeftStr = function(str,len){

        if(isNaN(len)||len==null){
            len = str.length;
        }
        else{
            if(parseInt(len)<0||parseInt(len)>str.length){
                len = str.length;
             }
        }
        return str.substr(0,len);
    }

	/***************************************************parentIndexOf************* *************************************/
    //函数功能：得到子结点在父结点的位置
	function item_parentIndexOf(node){
	  for (var i=0; i<node.parentNode.childNodes.length; i++){
			if(node==node.parentNode.childNodes[i]){return i;}
	  }
   }
   
}
//首先自动加载城市
var item_suggest = new item_city_suggest();
item_suggest.item_citys = flightcitys;

function CallDPCityPage(orgcityname)
{
    alert("haha");
}