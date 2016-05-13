//自定义分页样式bootstrap
$(function(){
	/* Set the defaults for DataTables initialisation */
	$.extend( true, $.fn.dataTable.defaults, {
	    "sDom": '<"pageInfo"pi>rt<"clear">',
	    "sPaginationType": "bootstrap",
	    "oLanguage": {
	        "sZeroRecords": "没有找到符合条件的数据",  
			"sProcessing": "<img src=’./loading.gif’ />",  
			"sInfo": "共<em> _TOTAL_ </em>条跳转到",  
			"sInfoEmpty": "木有记录",  
			"sSearch": "搜索：",  
			"oPaginate": {  
			    "sFirst": "首页",  
			    "sPrevious": "&lt;",  
			    "sNext": "&gt;",  
			    "sLast": "尾页"  
			}
	    },
		"iDisplayLength":5,
		"scrollX": true
	} );
	 
	/* Default class modification */
	$.extend( $.fn.dataTableExt.oStdClasses, {
	    "sWrapper": "dataTables_wrapper form-inline"
	} );
	 
	/* API method to get paging information */
	$.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings ){
	    return {
	        "iStart":         oSettings._iDisplayStart,
	        "iEnd":           oSettings.fnDisplayEnd(),
	        "iLength":        oSettings._iDisplayLength,
	        "iTotal":         oSettings.fnRecordsTotal(),
	        "iFilteredTotal": oSettings.fnRecordsDisplay(),
	        "iPage":          Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),
	        "iTotalPages":    Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )
	    };
	};
	 
	/* Bootstrap style pagination control */
	$.extend( $.fn.dataTableExt.oPagination, {
	    "bootstrap": {
	        "fnInit": function( oSettings, nPaging, fnDraw ) {
	            var oLang = oSettings.oLanguage.oPaginate;
	            var oPaging = oSettings.oInstance.fnPagingInfo();
	            var fnClickHandler = function ( e ) {
	                e.preventDefault();
	                if ( oSettings.oApi._fnPageChange(oSettings, e.data.action) ) {
	                    fnDraw( oSettings );
	                }
	            };
	 
	            $(nPaging).addClass('pagination').append(
	                '<input type="text" name="pageNo" id="redirect" class="iptBox pageNo redirect"/>'+
	                '<b>'+oPaging.iPage+'</b><span>/'+oPaging.iTotalPages+'</span>' +
	                '<a href="#" class="paginate_button prev previous disabled">'+oLang.sPrevious+'</a>'+
	                '<a href="#" class="paginate_button next">'+oLang.sNext+'</a>'
	            );	
	 
	            //datatables分页跳转
	            $(nPaging).find(".redirect").keyup(function(e){
	                var ipage = parseInt($(this).val());
	                var oPaging = oSettings.oInstance.fnPagingInfo();
	                if(isNaN(ipage) || ipage<1){
	                    ipage = 1;
	                }else if(ipage>oPaging.iTotalPages){
	                    ipage=oPaging.iTotalPages;
	                }
	                $(this).val(ipage);
	                ipage--;
	                oSettings._iDisplayStart = ipage * oPaging.iLength;
	                fnDraw( oSettings );
	            });
	 
	            var els = $('a', nPaging);
	            $(els[0]).bind( 'click.DT', {
	                action: "previous"
	            }, fnClickHandler );
	            $(els[1]).bind( 'click.DT', {
	                action: "next"
	            }, fnClickHandler );
	        },
	        "fnUpdate": function ( oSettings, fnDraw ) {
	            var iListLength = 5;
	            var oPaging = oSettings.oInstance.fnPagingInfo();
	            var an = oSettings.aanFeatures.p;
	            var i, ien, j, sClass, iStart, iEnd, iHalf=Math.floor(iListLength/2);
	 
	            if ( oPaging.iTotalPages < iListLength) {
	                iStart = 1;
	                iEnd = oPaging.iTotalPages;
	            }
	            else if ( oPaging.iPage <= iHalf ) {
	                iStart = 1;
	                iEnd = iListLength;
	            } else if ( oPaging.iPage >= (oPaging.iTotalPages-iHalf) ) {
	                iStart = oPaging.iTotalPages - iListLength + 1;
	                iEnd = oPaging.iTotalPages;
	            } else {
	                iStart = oPaging.iPage - iHalf + 1;
	                iEnd = iStart + iListLength - 1;
	            }
	 
	      		//update the new current page and total page
	      		$('.previous').siblings('b').text(oPaging.iPage +1);
	      		$('.previous').siblings('span').text('/'+oPaging.iTotalPages);
	            
	            if ( oPaging.iPage === 0 ) {
                    $('.previous').addClass('disabled');
                } else {
                    $('.previous').removeClass('disabled');
                }
 
                if ( oPaging.iPage === oPaging.iTotalPages-1 || oPaging.iTotalPages === 0 ) {
                    $('.next').addClass('disabled');
                } else {
                    $('.next').removeClass('disabled');
                }
	        }
	    }
	} );
 });