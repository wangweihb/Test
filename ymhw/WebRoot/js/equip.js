$(function(){
	
	//省市区联动的设置 
	var $distpicker = $('#distpicker');
  	$distpicker.distpicker({
    	province: '湖北省',
    	city: '恩施土家族苗族自治州',
    	district: '恩施市'
  	});
  	
   //头像上传
   $("#equipUpload").change(function(e)
   {
       for(var i=0;i<e.target.files.length;i++)
       {
           var file = e.target.files.item(i);
            //允许文件MIME类型 也可以在input标签中指定accept属性
            //console.log(/^image\/.*$/i.test(file.type));
            if(!(/^image\/.*$/i.test(file.type)))
            {
                continue;            //不是图片 就跳出这一次循环
            }
            
            //实例化FileReader API
            var freader = new FileReader();
            freader.readAsDataURL(file);
            freader.onload=function(e)
            {
                $("#equipPic").attr("src",e.target.result);
//      		$("#headShot").attr("value",e.target.result);
            }
   		}
    });
  
});

var show = function(id)
{
	$.getJSON("/equip/portDisplay",{"equipId":id},function(data) {
		var objStr = JSON.stringify(data);
		var obj = jQuery.parseJSON(objStr);
		$("#name").empty();
		$("#name").append(obj.name);
		$("#equipPic").attr("src",obj.equipPic);
		
		$("#showPrice").empty();
		$("#showPrice").append(obj.showPrice);
		
		$("#region").empty();
		$("#region").append(obj.provice + obj.city);
		
		$("#sendWay").empty();
		if(obj.sendWay == 1){
			$("#sendWay").append("自取");
		}else if(obj.sendWay == 2){
			$("#sendWay").append("可送");
		}
		
		$("#rentWay").empty();
		if(obj.sendWay == 1){
			$("#rentWay").append("只租");
		}else if(obj.rentWay == 2){
			$("#rentWay").append("可卖");
		}
		
		$("#supplyTension").empty();
		if(obj.supplyTension == 1){
			$("#supplyTension").append("有货");
		}else if(obj.supplyTension == 2){
			$("#supplyTension").append("紧缺");
		}
		
		$("#damage").empty();
		$("#damage").append(obj.damage);
		
		$("#qq").empty();
		$("#qq").append(obj.qq);
		
		$("#fixnumber").empty();
		$("#fixnumber").append(obj.fixnumber);
		
		$("#telphone").empty();
		$("#telphone").append(obj.telephone);
		
	});
	$(".equipment-parinforma").css('display','block');
	$(".parinforma-body").css('display','block');
}

var clo = function()
{
	$(".equipment-parinforma").css('display','none');
	$(".parinforma-body").css('display','none');
}

var upload = function()
{
	var name = $("#name").val();
	var deposit =  $("#deposit").val();
	var rent = $("#rent").val();
	var directions = $("#directions").val();
	var attention =  $("#attention").val();
	

	if (!isNullOrEmpty(name) && isFloat(deposit) && isFloat(rent)
				             &&!isNullOrEmpty(directions) && !isNullOrEmpty(attention)) {
		document.getElementById('equipForm').submit();
		return true;
	} else{
		alert("信息填写不合法！");
		return false;
	}
}

var updateEquip = function()
{
	$("#equipForm").attr("action","/equip/editEquip");
	$("#equipForm").submit();
}

var checkEquip = function()
{
	$("#equipForm").attr("enctype","");
	$("#equipForm").attr("action","/equip/check");
	$("#equipForm").submit();
}

var updateEquipBg = function()
{
	$("#equipForm").attr("action","/manage_equip/editEquip");
	$("#equipForm").submit();
}

var checkEquipBg = function()
{
	$("#equipForm").attr("enctype","");
	$("#equipForm").attr("action","/manage_equip/check");
	$("#equipForm").submit();
}


var getEquipInfo = function()
{
	var equipId = "";
	$.getJSON("/equip/portDisplay",{"equipId":equipId},function(data) {
		var objStr = JSON.stringify(data);
		var obj = jQuery.parseJSON(objStr);
		
	});
}

var goToDisplay = function(id)
{
	window.location = "/activity/display?activityId="+id;
}
