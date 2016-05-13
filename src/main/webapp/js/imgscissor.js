$(document).ready(function() {
	
        //获取JS文件当前路径并设置站点绝对路径
        var CurrentJsPath = (function (){
	        var k = document.getElementsByTagName("script");
	        srcStr = k[0].getAttribute("src");
	        //截取出站点路径
	        srcStr = srcStr.substring(0, srcStr.indexOf("/js/"));
	        return srcStr; 
        })();
	
	/*
	 * 创建表单，该表单访问ImageScissorAction并将两个参数传过去
	 * 参数：proportion	裁剪比例(一般的处理要求等比例裁剪)
	 * 参数：originPath	图片url路径
	 */
	$('body').append('<form id="toScissrorForm" action="' + CurrentJsPath + '/imageScissorAction.do" method="post">' + 
			'<input type="hidden" name="proportion" value="" />' + 
			'<input type="hidden" name="originPath" value="" /> </form>');
	$('#toScissrorForm').hide();
	
	//为样式为scissor的元素(img标签)加入处理
	$('.scissor').each(function() {
		
		//获取标签的src属性值(图片的url)并加入一个参数(当前时间)以防止缓存
		var imgPath = $(this).attr('src') + '?' + new Date().getTime();
		
		//将带时间参数的url再设置给图片的src属性
	    $(this).attr('src',imgPath);
	    
	    //设置鼠标移到图片上的提示文字
	    $(this).tooltip({
			showURL: false,
			bodyHandler: function() {
				return '双击裁剪图片';
			}
		});
	    
	    //设置图片的双击事件处理
	    $(this).dblclick(function(){
	    	
	    	//获取img标签的src值(url路径)
			var imageSrc = $(this).attr('src');
			
			//读取img的宽、高
			var width = $(this).attr("width");
			var height = $(this).attr("height");
			//设置拖拽比例(裁剪图片应该按比例裁剪)
			var proportion = width + ":" + height;
			//将值设置给表单
			$('#toScissrorForm input[name=originPath]').val(imageSrc);
			$('#toScissrorForm input[name=proportion]').val(proportion);
			$('#toScissrorForm').submit();//双击提交表单
		});
	    
	  });
	
});