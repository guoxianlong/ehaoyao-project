/***** 页面公用js*****/
var $dtb,resultDataTable;
var arrPath = [];
	arrPath[0]=['stock','product','caiwu'];
	arrPath[1]=['stock1','product1','caiwu1'];


/*
菜单二级菜单
*/
$('.slideMenu').find('li').hover(function(){
	$(this).addClass('hover');
	$(this).children('.subCategory').css('display','block');
},function(){
	$(this).removeClass('hover');
	$(this).children('.subCategory').css('display','none');
});

$(".subCategory a").click(function(){
	$(this).addClass('sel').siblings().removeClass('sel');
	$(this).parent('.subCategory').parent('li').addClass('active').siblings().removeClass('active');
	var i = $(this).parent('.subCategory').parent('li').index(),
		j = $(this).index(),
	    url = 'model/'+arrPath[i][j]+'.html';

	$("#pageFlag").val(arrPath[i][j]);
	$(".reminder").removeClass("hidden");

	$.get(url, function(result){
	    $("#resultCont").html(result);
	},'html');
});

/*隐藏菜单*/
var flag= 0;
$(".toggleBtn").click(function(){
	if(flag == 0){
		flag = 1;
		$(".leftCont").css('left','-130px');
		$(".rightCont").css("margin-left","15px");
	}else{
		flag = 0;
		$(".leftCont").css('left','0px');
		$(".rightCont").css("margin-left","145px");
	}
});

/*复选框*/
$('body').delegate('.checkBox label','click',function(){
	var flag = $(this).find('span').is(':hidden');
	if(flag){
		//$(this).siblings("input[type=checkbox]").attr('checked','true');
		$(this).find('span').css('display','block');
	}else{
		//$(this).siblings("input[type=checkbox]").removeAttr("checked");
		$(this).find('span').css('display','none');
	}
});

/*下拉框*/
/*
$(".selBox").click(function(){
	$(this).find('ul').toggleClass('show');
});

$(".selBox ul").hover(function(){
	$(this).addClass('show');
},function(){
	$(this).removeClass('show');
});
$(".selBox li").click(function(){
	var con = $(this).text();
	$(this).parent().siblings('span').text(con);
});
*/
$('body').delegate('.selBox','click',function(){
	$(this).find('ul').toggleClass('show');
});

$('body').delegate('.selBox ul','hover',function(){
	$(this).toggleClass('show');
});
$('body').delegate('.selBox li','click',function(){
	var con = $(this).text();
	$(this).parent().siblings('span').text(con);
});



$(function(){
	//点击查询
	$("#resultCont").delegate('#queryBtn','click',function(){
		//hide reminder info
		$(".reminder").addClass("hidden");
		$(".resultInfo").removeClass("hidden");


		var flag = $("#pageFlag").val(),
			objId,data;
			
		if(flag == 'stock'){
			objId = ".dataTable";
		}	
		if(flag == 'product'){
			objId = ".dataTable";
		}
		initDtb(objId,flag,data);
	});
})

function initDtb(id,flag,data){
	id = id ? id : '.dataTable';
	$dtb = $(id);
	
	if(flag == 'stock'){
		resultDataTable = $dtb.dataTable({
			destroy: true,
			"ajax": "data/arrays.txt",
			"aoColumns": [  
	            { "sTitle": "name" },  
	            { "sTitle": "sex" },  
	            { "sTitle": "birthday" },  
	            { "sTitle": "mobilePhone" },  
	            { "sTitle": "diploma" },  
	            { "sTitle": "workYears" }
	        ]
		});
	}
	/*
	resultDataTable = $dtb.dataTable({
		destroy: true,
		"bServerSide": true,  
        "sServerMethod": "POST",  
        "sAjaxSource": data.url,    
        "fnServerParams": function (aoData) {  
            aoData.push(data.param);  
        }
	})；
	*/
}
