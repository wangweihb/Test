/*
 * inform youxiake
 */
(function($){
	var $body = $('body'),
		k = '_inform';

	var inform = {
		title: '2017友鸣旅行网”春天花事“摄影大赛火热进行中！',
		link: 'http://www.youxiake.net/cths2016/'
	}

	var htmlStr = '<div class="newswarpten"><div class="newstopone"><div class="linktitle_4587"><a rel="nofollow" href="'+ inform.link +'" target="_blank">'+ inform.title +'</a></div><span class="close16_16"></span></div><div class="newsarrow"></div></div>',

		cssStr = '<style type="text/css">@charset "UTF-8";.newswarpten{position:absolute;position:fixed!important;bottom:0;display:none;left:50%;z-index:9999}.newstopone{height:46px;font-size:14px;background:url(http://static.youxiake.com/Public/assets/img/common/kxbg.png?1) 0 -1px no-repeat #fffff6;border:#e0d3be solid 1px;float:left;border-right:0 none}.newstopone .linktitle_4587{float:left;margin:12px 0 0 70px;display:inline}.newstopone .linktitle_4587 a{font-size:16px;color:#064361;text-decoration:none}.newstopone .linktitle_4587 a:hover{font-size:16px;color:#ff7e00;text-decoration:none}.newstopone .close16_16{width:16px;height:16px;float:left;cursor:pointer;background:url(http://static.youxiake.com/Public/assets/img/common/kxbgarrowclose.png) -47px -1px no-repeat;margin:16px 0 0 10px;display:inline}.newstopone .close16_16:hover{background:url(http://static.youxiake.com/Public/assets/img/common/kxbgarrowclose.png) -1px -1px no-repeat}.newsarrow{width:18px;height:48px;background:url(http://static.youxiake.com/Public/assets/img/common/kxbgarrow.png) left top no-repeat;float:left}</style>';

	var $informs = $(htmlStr + cssStr).appendTo($body);
	var $inform = $informs.eq(0);
	var oldTitle = $.cookie(k);


	$inform.css({'margin-left': - $inform.width() / 2})
	.on('click', '.linktitle_4587, .close16_16', function(){
		//关闭
		$.cookie(k, inform.title, {expires: 30, domain: 'youxiake.com', path: '/'});
		$informs.remove();
	});

	if(!oldTitle || inform.title !== oldTitle) {
		$inform.show()
	}

})(jQuery);
