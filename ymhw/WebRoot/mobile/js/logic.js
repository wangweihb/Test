var choose_cate = ['_cate_all', '_cate_creat', '_cate_site', '_cate_inspire', '_cate_article', '_cate_app'],
	choose_sort = ['sort_re', 'sort_recom', 'sort_fa', 'sort_vi', 'sort_com'];
$('.choose_cate li').each(function(i) {});
$('.choose_sort li').each(function(i) {
	$(this).find('.s').addClass(choose_sort[i]);
});

$('#sort td').bind('click', function() {
	$('#sort_content').addClass('show');
	$('.asort').eq($(this).index()).addClass('show');
});
$('.asort .hd .fr').bind('click', function() {
	$('#sort_content').removeClass('show');
	var _this = $(this);
	setTimeout(function() {
		_this.parent().parent().parent().removeClass('show');
	}, 300);
});

$('.ct li').click(function() {
	if($(this).hasClass('a_selected') == false) {
		$(this).siblings().removeClass('a_selected');
		$(this).addClass('a_selected');
	} else {
		$(this).siblings().removeClass('a_selected');
		$(this).removeClass('a_selected');
	}
});