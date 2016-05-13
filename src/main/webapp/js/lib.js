(function ( $ ) {
 
    $.init = function( options ) {
    	var settings = $.extend( {}, options ),
    		_aside = $( '#aside' ),
        	_asideList = _aside.find( '.aside-link' ),
        	_asideBtn = $( '#asideBtn' ),
        	_main = $( '#main' );

        loadHeader();
        loadAside();
        initialization();

        $( window ).resize(function() {
        	_asideList.css({
        		height: 'auto'
        	});
        	initialization();
        });

        function initialization(){
        	var _h = $( window ).height(),
        		_padding = parseInt( _main.css('padding-left').replace(/[^\d]/g,'') ) * 2;

	        if( _asideList.height() < (_h - 52) ){
	        	if( _asideList.height() < _main.height() && _main.height() > (_h - 52) ){
	        		_asideList.height( _main.height() + _padding );
	        	}else{
	        		_asideList.height( _h - 52 );
	        	};
	        }else{
	        	if( _asideList.height() < _main.height() ){
	        		_asideList.height( _main.height() + _padding );
	        	};
	        };

	        _asideBtn.off().click(function () {
	        	_asideList.toggle();
	        });
        };

        function loadHeader(){
        	$.get( '../common/header.html', function( data ){
        		$( '#header' ).html( data );
        	});
        };

        function loadAside(){
        	$.get( settings.asideLink, function( data ){
        		$( '#asideLink' ).html( data );
        		var _asideTitle = _aside.find( 'h4' );

        		if( _asideTitle.length > 0 ){
		        	_asideTitle.off().click(function () {
		        		var __container = $( this ).siblings( '.sub' );

		        		$( this ).find('.glyphicon').toggleClass( 'glyphicon-minus' );
		        		__container.toggle();
		        	});
		        };
        	});
        };
    };
 
}( jQuery ));