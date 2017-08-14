/**
 * 工具函数
 * @author oswin
 */

/**
 * 判空
 * @param {Object} strVal
 */
function isNullOrEmpty(strVal) 
{
	if (strVal == '' || strVal == null || strVal == undefined) 
	{
		return true;
	} 
	else 
	{
		return false;
	}
}

/**
 * 检验是不是手机号
 * @param {Object} mobile
 */
function isMobileNumber(mobile)
{
 	var pattern = /^1[34578]\d{9}$/; 
 	return pattern.test(mobile); 
}
 
/**
 * 验证是不是邮箱
 * @param {Object} str
 */
function isEmail(str){
	
	var patrn = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if(patrn.test(str))
	{
		
		return true;
	}
	else
	{
		return false;
	}
}

/**
 * 验证是不是数字 
 * @param {Object} str
 */
function isNum(str)
{
    var reg = /^[0-9]*[1-9][0-9]*$/;
    return reg.test(str);
    
}

/**
 * 验证是否是浮点数
 * @param {Object} str
 */
function isFloat(str)
{
	if(!isNaN(str) && parseFloat(str) !=  "NaN")
	{
		return true;
	}
	return false;
}
/**
 * 为某个元素设置html值
 * @param {Object} idSelector
 * @param {Object} htmlText
 */
function setValue(idSelector,htmlText)
{
	$("#"+idSelector).html("");
	$("#"+idSelector).html(htmlText);
}

/**
 * 检查字符串的长度范围
 * @param {Object} str
 * @param {Object} start
 * @param {Object} end
 */
function checklength(str,start,end)
{
	if(str.length>=start && str.length<=end)
	{
		return true;
	}
	else
	{
		return false;
	}
}

/**
 * 选择省一级 
 */
var select = function(p,c) 
{
	var x = [35];　　　　　　　
	x[0] = ",";　　　　　　　
	x[1] = "东城,西城,崇文,宣武,朝阳,丰台,石景山,海淀,门头沟,房山,通州,顺义,昌平,大兴,平谷,怀柔,密云,延庆";　　　　　　　
	x[2] = "黄浦,卢湾,徐汇,长宁,静安,普陀,闸北,虹口,杨浦,闵行,宝山,嘉定,浦东,金山,松江,青浦,南汇,奉贤,崇明";　　　　　　　
	x[3] = "和平,东丽,河东,西青,河西,津南,南开,北辰,河北,武清,红挢,塘沽,汉沽,大港,宁河,静海,宝坻,蓟县,大邱庄";　　　　　　　
	x[4] = "万州,涪陵,渝中,大渡口,江北,沙坪坝,九龙坡,南岸,北碚,万盛,双挢,渝北,巴南,黔江,长寿,綦江,潼南,铜梁,大足,荣昌,壁山,梁平,城口,丰都,垫江,武隆,忠县,开县,云阳,奉节,巫山,巫溪,石柱,秀山,酉阳,彭水,江津,合川,永川,南川";　　　　　　　
	x[5] = "石家庄,邯郸,邢台,保定,张家口,承德,廊坊,唐山,秦皇岛,沧州,衡水";　　　　　　　
	x[6] = "太原,大同,阳泉,长治,晋城,朔州,吕梁,忻州,晋中,临汾,运城";　　　　　　　
	x[7] = "呼和浩特,包头,乌海,赤峰,呼伦贝尔盟,阿拉善盟,哲里木盟,兴安盟,乌兰察布盟,锡林郭勒盟,巴彦淖尔盟,伊克昭盟";　　　　　　　
	x[8] = "沈阳,大连,鞍山,抚顺,本溪,丹东,锦州,营口,阜新,辽阳,盘锦,铁岭,朝阳,葫芦岛";　　　　　　　
	x[9] = "长春,吉林,四平,辽源,通化,白山,松原,白城,延边";　　　　　　　
	x[10] = "哈尔滨,齐齐哈尔,牡丹江,佳木斯,大庆,绥化,鹤岗,鸡西,黑河,双鸭山,伊春,七台河,大兴安岭";　　　　　　　
	x[11] = "南京,镇江,苏州,南通,扬州,盐城,徐州,连云港,常州,无锡,宿迁,泰州,淮安";　　　　　　　
	x[12] = "杭州,宁波,温州,嘉兴,湖州,绍兴,金华,衢州,舟山,台州,丽水";　　　　　　　
	x[13] = "合肥,芜湖,蚌埠,马鞍山,淮北,铜陵,安庆,黄山,滁州,宿州,池州,淮南,巢湖,阜阳,六安,宣城,亳州";　　　　　　　
	x[14] = "福州,厦门,莆田,三明,泉州,漳州,南平,龙岩,宁德";　　　　　　　
	x[15] = "南昌市,景德镇,九江,鹰潭,萍乡,新馀,赣州,吉安,宜春,抚州,上饶";　　　　　　　
	x[16] = "济南,青岛,淄博,枣庄,东营,烟台,潍坊,济宁,泰安,威海,日照,莱芜,临沂,德州,聊城,滨州,菏泽,博兴";　　　　　　　
	x[17] = "郑州,开封,洛阳,平顶山,安阳,鹤壁,新乡,焦作,濮阳,许昌,漯河,三门峡,南阳,商丘,信阳,周口,驻马店,济源";　　　　　　　
	x[18] = "武汉,宜昌,荆州,襄樊,黄石,荆门,黄冈,十堰,恩施,潜江,天门,仙桃,随州,咸宁,孝感,鄂州";　　　　　　　
	x[19] = "长沙,常德,株洲,湘潭,衡阳,岳阳,邵阳,益阳,娄底,怀化,郴州,永州,湘西,张家界";　　　　　　　
	x[20] = "广州,深圳,珠海,汕头,东莞,中山,佛山,韶关,江门,湛江,茂名,肇庆,惠州,梅州,汕尾,河源,阳江,清远,潮州,揭阳,云浮";　　　　　　　
	x[21] = "南宁,柳州,桂林,梧州,北海,防城港,钦州,贵港,玉林,南宁地区,柳州地区,贺州,百色,河池";　　　　　　　
	x[22] = "海口,三亚";　　　　　　　
	x[23] = "成都,绵阳,德阳,自贡,攀枝花,广元,内江,乐山,南充,宜宾,广安,达川,雅安,眉山,甘孜,凉山,泸州";　　　　　　　
	x[24] = "贵阳,六盘水,遵义,安顺,铜仁,黔西南,毕节,黔东南,黔南";　　　　　　　
	x[25] = "昆明,大理,曲靖,玉溪,昭通,楚雄,红河,文山,思茅,西双版纳,保山,德宏,丽江,怒江,迪庆,临沧";　　　　　　　
	x[26] = "拉萨,日喀则,山南,林芝,昌都,阿里,那曲";　　　　　　　
	x[27] = "西安,宝鸡,咸阳,铜川,渭南,延安,榆林,汉中,安康,商洛";　　　　　　　
	x[28] = "兰州,嘉峪关,金昌,白银,天水,酒泉,张掖,武威,定西,陇南,平凉,庆阳,临夏,甘南";　　　　　　　
	x[29] = "银川,石嘴山,吴忠,固原";　　　　　　　
	x[30] = "西宁,海东,海南,海北,黄南,玉树,果洛,海西";　　　　　　　
	x[31] = "乌鲁木齐,石河子,克拉玛依,伊犁,巴音郭勒,昌吉,克孜勒苏柯尔克孜,博 尔塔拉,吐鲁番,哈密,喀什,和田,阿克苏";　　　　　　　
	x[32] = "香港,";　　　　　　　
	x[33] = "澳门,";　　　　　　　
	x[34] = "台北,高雄,台中,台南,屏东,南投,云林,新竹,彰化,苗栗,嘉义,花莲,桃园,宜兰,基隆,台东,金门,马祖,澎湖";　　　　　　　
	var citys = x[p.value].split(",");
	c.options.length = 0;
	for (var i = 0; i < citys.length; i++)　　　　　　　 
		c.options[i] = new Option(citys[i], citys[i]);
}

/**
 * 选择市一级
 */
var select2 = function(p,c,place) 
{
	var provinces = "全国各地(不限),北京,上海,天津,重庆, 河北,山西,内蒙古,辽宁,吉林,黑龙江,江苏,浙江,安徽,福建,江西,山东,河南,湖北,湖南,广东,广西,海南,四川,贵州,云南,西藏,陕西,甘肃,宁夏,青海,新疆,香港,澳门,台湾";
	var province = provinces.split(",");
	var region = province[p.value]+""+c.value;
	
	$("#" + place).attr(region);
}


var getRegion = function(p,c)
{
	var provinces = "全国各地(不限),北京,上海,天津,重庆, 河北,山西,内蒙古,辽宁,吉林,黑龙江,江苏,浙江,安徽,福建,江西,山东,河南,湖北,湖南,广东,广西,海南,四川,贵州,云南,西藏,陕西,甘肃,宁夏,青海,新疆,香港,澳门,台湾";
	var province = provinces.split(",");
	var region = province[p.value]+""+c.value;
	
	return region;
}

var getSelectValue = function(s1,s2,p)
{
	var value1 = s1.value;
	var value2 = s2.value;
	if(value2 == "NULL"){
		$("#leaders").attr("value",value1);
	}
	else
	{
		$("#leaders").attr("value",value1 + "," + value2);
	}
}


//新建cookie。
//hours为空字符串时,cookie的生存期至浏览器会话结束。hours为数字0时,建立的是一个失效的cookie,这个cookie会覆盖已经建立过的同名、同path的cookie（如果这个cookie存在）。
function setCookie(name,value,hours,path){
    var name = escape(name);
    var value = escape(value);
    var expires = new Date();
    expires.setTime(expires.getTime() + hours*3600000);
    path = path == "" ? "" : ";path=" + path;
    _expires = (typeof hours) == "string" ? "" : ";expires=" + expires.toUTCString();
    document.cookie = name + "=" + value + _expires + path;
}

//获取cookie值
function getCookieValue(name){
    var name = escape(name);
    //读cookie属性，这将返回文档的所有cookie
    var allcookies = document.cookie;       
    //查找名为name的cookie的开始位置
    name += "=";
    var pos = allcookies.indexOf(name);    
    //如果找到了具有该名字的cookie，那么提取并使用它的值
    if (pos != -1){                                             //如果pos值为-1则说明搜索"version="失败
        var start = pos + name.length;                  //cookie值开始的位置
        var end = allcookies.indexOf(";",start);        //从cookie值开始的位置起搜索第一个";"的位置,即cookie值结尾的位置
        if (end == -1) end = allcookies.length;        //如果end值为-1说明cookie列表里只有一个cookie
        var value = allcookies.substring(start,end);  //提取cookie的值
        return unescape(value);                           //对它解码      
        }   
    else return "";                                             //搜索失败，返回空字符串
}

//删除cookie
function deleteCookie(name,path){
    var name = escape(name);
    var expires = new Date(0);
    path = path == "" ? "" : ";path=" + path;
    document.cookie = name + "="+ ";expires=" + expires.toUTCString() + path;
}

function getElementViewTop(element) {　　
	var actualTop = element.offsetTop;　　
	var current = element.offsetParent;　　
	while(current !== null) {
		actualTop += current.offsetTop;
		current = current.offsetParent;　　
	}
	var elementScrollTop = 0;　　
	if(document.compatMode == "BackCompat") {
		elementScrollTop = document.body.scrollTop;　　
	} else {
		elementScrollTop = document.documentElement.scrollTop;　　
	}
	return actualTop;　 //　return actualTop-elementScrollTop;
}

function getScroll() {
	var top, left, width, height;

	if(document.documentElement && document.documentElement.scrollTop) {
		top = document.documentElement.scrollTop;
		left = document.documentElement.scrollLeft;
		width = document.documentElement.scrollWidth;
		height = document.documentElement.scrollHeight;
	} else if(document.body) {
		top = document.body.scrollTop;
		left = document.body.scrollLeft;
		width = document.body.scrollWidth;
		height = document.body.scrollHeight;
	}
	return {
		'top': top,
		'left': left,
		'width': width,
		'height': height
	};
}

