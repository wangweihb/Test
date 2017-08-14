$(function()
{
	$(':input').labelauty();
	
	$('.datepick_ws').datetimepicker(
		{
			  lang:"ch",           //语言选择中文
		      format:"Y-m-d",      //格式化日期
		      timepicker:false,    //关闭时间选项
		      yearStart:2000,     //设置最小年份
		      yearEnd:2080,        //设置最大年份
		      todayButton:false    //关闭选择今天按钮
		}
	);
	
	//活动类型的异步加载(暂时只能加载数据，前端显示的属性有问题)
//	$.getJSON("/activity/portActTypes", function(data) {
//		var objStr = JSON.stringify(data);
//		var obj = jQuery.parseJSON(objStr);
//		$("#actType").empty();
//		$.each(obj, function(i, item) {
//			var inner =  '<li><input type="checkbox" name="acttype" value="'+item.id+'" data-labelauty="'+item.name+'"></li>';
//			$("#actType").append(inner);
//		});
//	});
	
	
	
	/**
	 * 处理file input加载的图片文件
	 * 判断浏览器是否有FileReader接口
	 */
    if(typeof FileReader =='undefined')
    {
       $("#destination").css({'background':'none'}).html('您的浏览器还不支持HTML5的FileReader接口,无法使用图片本地预览,请更新浏览器获得最好体验');
       
       //如果浏览器是ie
        if($.browser.msie===true)
        {
            //ie6直接用file input的value值本地预览
            if($.browser.version==6)
            {
                $("#imgUpload").change(function(event){                        
                      //ie6下怎么做图片格式判断?
                      var src = event.target.value;
                      //var src = document.selection.createRange().text;        //选中后 selection对象就产生了 这个对象只适合ie
                      var img = '<img id="actPic" name="actPic" src="'+src+'" width="350px" height="250px" />';
                      $("#destination").empty().append(img);
                      
                      $("#pic").attr("value","");
					  $("#pic").attr("value",src);
                  });
            }
            //ie7,8使用滤镜本地预览
            else if($.browser.version==7 || $.browser.version==8)
            {
                $("#imgUpload").change(function(event)
                {
                      $(event.target).select();
                      var src = document.selection.createRange().text;
                      var dom = document.getElementById('destination');
                      //使用滤镜 成功率高
                      dom.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src= src;
                      dom.innerHTML = '';
                      //使用和ie6相同的方式 设置src为绝对路径的方式 有些图片无法显示 效果没有使用滤镜好
                      /*var img = '<img src="'+src+'" width="300px" height="300px" />';
                      $("#destination").empty().append(img);*/
                     
                      $("#pic").attr("value","");
					  $("#pic").attr("value",src);
                 });
            }
        }
        //如果是不支持FileReader接口的低版本firefox 可以用getAsDataURL接口
        else if($.browser.mozilla===true)
        {
            $("#imgUpload").change(function(event)
            {
                //firefox2.0没有event.target.files这个属性 就像ie6那样使用value值 但是firefox2.0不支持绝对路径嵌入图片 放弃firefox2.0
                //firefox3.0开始具备event.target.files这个属性 并且开始支持getAsDataURL()这个接口 一直到firefox7.0结束 不过以后都可以用HTML5的FileReader接口了
                if(event.target.files)
                {
                  //console.log(event.target.files);
                  for(var i=0;i<event.target.files.length;i++)
                  {    
                        var img = '<img id="actPic" name="actPic"  src="'+event.target.files.item(i).getAsDataURL()+'" width="350px" height="250px"/>';
                      $("#destination").empty().append(img);
                      
                      $("#pic").attr("value","");
					  $("#pic").attr("value",event.target.files.item(i).getAsDataURL());
                  }
                }
                else
                {
                    //console.log(event.target.value);
                    //$("#imgPreview").attr({'src':event.target.value});
                }
            });
        }
    }
    else
    {
          //多图上传 input file控件里指定multiple属性 e.target是dom类型
           $("#imgUpload").change(function(e)
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
	                    var img = '<img id="actPic" name="actPic"  src="'+e.target.result+'" width="350px" height="250px"/>';
	                    $("#destination").empty().append(img);
	                    
	                    $("#pic").attr("value","");
					    $("#pic").attr("value",e.target.result);
	                }
           		}
            });
               
          //处理图片拖拽的代码
          var destDom = document.getElementById('destination');
          destDom.addEventListener('dragover',function(event)
          {
	          event.stopPropagation();
	          event.preventDefault();
          },false);
              
          destDom.addEventListener('drop',function(event)
          {
              event.stopPropagation();
              event.preventDefault();
              var img_file = event.dataTransfer.files.item(0);                //获取拖拽过来的文件信息 暂时取一个
              //console.log(event.dataTransfer.files.item(0).type);
              if(!(/^image\/.*$/.test(img_file.type)))
              {
                  alert('您还未拖拽任何图片过来,或者您拖拽的不是图片文件');
                  return false;
              }
              fReader = new FileReader();
              fReader.readAsDataURL(img_file);
              fReader.onload = function(event){
                  destDom.innerHTML='';
                  destDom.innerHTML = '<img id="actPic" name="actPic"  src="'+event.target.result+'" width="350px" height="250px"/>';    
                  };
          },false);
    }
    
    
    /**
     * 活动强度级别选择
     */
    var oStar = document.getElementById("star");
	var aLi = oStar.getElementsByTagName("li");
	var oUl = oStar.getElementsByTagName("ul")[0];
	var oSpan = oStar.getElementsByTagName("span")[1];
	var oP = oStar.getElementsByTagName("p")[0];
	var i = iScore = iStar = 0;
	var aMsg = [
		"很轻|所有人都可参与",
		"较轻|无重要身体疾病都可参与",
		"一般|一般人都可参与",
		"较强|建议经常进行此项活动的人参与",
		"很强|建议比较专业的人参与"
	]
	for (i = 1; i <= aLi.length; i++)
	{
		aLi[i - 1].index = i;
		//鼠标移过显示分数
		aLi[i - 1].onmouseover = function() 
		{
			fnPoint(this.index);
			//浮动层显示
			oP.style.display = "block";
			//计算浮动层位置
			oP.style.left = oUl.offsetLeft + this.index * this.offsetWidth - 104 + "px";
			//匹配浮动层文字内容
			oP.innerHTML = "<em><b>" + this.index + "</b> 级 " + aMsg[this.index - 1].match(/(.+)\|/)[1] + "</em>" + aMsg[this.index - 1].match(/\|(.+)/)[1]
		};
		
		//鼠标离开后恢复上次评分
		aLi[i - 1].onmouseout = function() {
			fnPoint();
			//关闭浮动层
			oP.style.display = "none"
		};
		
		//点击后进行评分处理
		aLi[i - 1].onclick = function() 
		{
			iStar = this.index;
			oP.style.display = "none";
			$("#intensityLevel").attr("value",iStar);
			
			//oSpan.innerHTML = "<strong>" + (this.index) + " 分</strong> (" + aMsg[this.index - 1].match(/\|(.+)/)[1] + ")"
		}
	}
	
	//评分处理
	function fnPoint(iArg) 
	{
		//分数赋值
		iScore = iArg || iStar;
		for (i = 0; i < aLi.length; i++)
		{
			aLi[i].className = i < iScore ? "on" : "";
		}
	}
	
	//领队数据的异步加载
	$.getJSON("/user/portAllLeaders", function(data) {
		var objStr = JSON.stringify(data);
		var obj = jQuery.parseJSON(objStr);
		$("#leader1").empty();
		$("#leader2").empty();
		$("#leader2").append('<option value="NULL"></option>');
		$.each(obj, function(i, item) {
			var inner =  '<option value="' + item.id +'">'+ item.account +'</option>';
			$("#leader1").append(inner);
			$("#leader2").append(inner);
		});
	});
});

/**
 * 上传并浏览
 */
var cometoRight = function()
{
	
	var src = $("#destination img").attr("src");
	var img = '<img src="'+src+'" width="300px" height="300px"/>';
	$("#destination2").empty().append(img);
	$("#pic").attr("value","");
	$("#pic").attr("value",src);
}

/**
 * 切换到下一步
 */
var toNext = function(thisStep,nextStep)
{
	$("#step" + thisStep + "Content").attr("style","display:none");
	$("#step" + nextStep + "Content").attr("style","display:block");
	
	$("#step" + thisStep + "Contentli").attr("class","");
	$("#step" + nextStep + "Contentli").attr("class","active_step");
}

/**
 * 点击进去某一项
 */
var toStep = function(step)
{
	$("#step1Content").attr("style","display:none");
	$("#step1Contentli").attr("class","");
	
	$("#step2Content").attr("style","display:none");
	$("#step2Contentli").attr("class","");
	
	$("#step3Content").attr("style","display:none");
	$("#step3Contentli").attr("class","");
	
	$("#step4Content").attr("style","display:none");
	$("#step4Contentli").attr("class","");
	
	$("#step" + step + "Content").attr("style","display:block");
	$("#step" + step + "Contentli").attr("class","active_step");
}

/***
 * 富文本编辑的内容框切换
 */

var switchTab = function(id)
{
	$("#routeDescEx").attr("style","display:none;");
	$("#travelPlanEx").attr("style","display:none;");
	$("#requiredEquipEx").attr("style","display:none;");
	$("#noticeEx").attr("style","display:none;");
	$("#feeEx").attr("style","display:none;");
	$("#enrollEx").attr("style","display:none;");
	
	$("#" + id).attr("style","display:block;");
}


/**
 * 确认发布
 */
var publish = function()
{
	var mainLeader = $("#leader1").val();
	if(isNullOrEmpty(mainLeader)){
		alert("必须选择主领队！");
	}else{
		var routeDescHtml = UE.getEditor('routeDescContainer').getContent();
		var travelPlanHtml = UE.getEditor('travelPlanContainer').getContent();
		var requiredEquipHtml = UE.getEditor('requiredEquipContainer').getContent();
		var noticeHtml = UE.getEditor('noticeContainer').getContent();
		var feeHtml = UE.getEditor('feeContainer').getContent();
		var enrollHtml = UE.getEditor('enrollContainer').getContent();
		
		
		$("#routeDesc").attr("value",routeDescHtml);
		$("#travelPlan").attr("value",travelPlanHtml);
		$("#requiredEquip").attr("value",requiredEquipHtml);
		$("#notice").attr("value",noticeHtml);
		$("#fee").attr("value",feeHtml);
		$("#enroll").attr("value",enrollHtml);
		
		var contentList = "";
		$("input[name='content']:checked").each(function(){
			contentList += $(this).val() + ",";
		});
		if(!isNullOrEmpty(contentList))
		{
			$("#contentList").attr("value","");
			$("#contentList").attr("value",contentList.substr(0,contentList.length-1));
		}
		
		var typeList = "";
		$("input[name='acttype']:checked").each(function(){
			typeList += $(this).val() + ",";
		});
		if(!isNullOrEmpty(typeList))
		{
			$("#typeList").attr("value","");
			$("#typeList").attr("value",typeList.substr(0,typeList.length-1));
		}
		
		var region1 = getRegion(Province1,City1);
		var region2 = getRegion(Province2,City2);
		$("#departurePlace").attr("value",region1);
		$("#destination").attr("value",region2);
		
		getSelectValue(leader1,leader2,leaders);
		
		$("#activityPublishForm").submit();
	}
	
}

/**
 * 确定审核
 */
var check = function()
{
	$("#activityPublishForm").submit();
}

/**
 * 确认修改
 */
var update = function()
{
	var routeDescHtml = UE.getEditor('routeDescContainer').getContent();
	var travelPlanHtml = UE.getEditor('travelPlanContainer').getContent();
	var requiredEquipHtml = UE.getEditor('requiredEquipContainer').getContent();
	var noticeHtml = UE.getEditor('noticeContainer').getContent();
	var feeHtml = UE.getEditor('feeContainer').getContent();
	var enrollHtml = UE.getEditor('enrollContainer').getContent();
	
	
	$("#routeDesc").attr("value",routeDescHtml);
	$("#travelPlan").attr("value",travelPlanHtml);
	$("#requiredEquip").attr("value",requiredEquipHtml);
	$("#notice").attr("value",noticeHtml);
	$("#fee").attr("value",feeHtml);
	$("#enroll").attr("value",enrollHtml);
	
	var contentList = "";
	$("input[name='content']:checked").each(function(){
		contentList += $(this).val() + ",";
	});
	if(!isNullOrEmpty(contentList))
	{
		$("#contentList").attr("value","");
		$("#contentList").attr("value",contentList.substr(0,contentList.length-1));
	}
	
	var typeList = "";
	$("input[name='acttype']:checked").each(function(){
		typeList += $(this).val() + ",";
	});
	if(!isNullOrEmpty(typeList))
	{
		$("#typeList").attr("value","");
		$("#typeList").attr("value",typeList.substr(0,typeList.length-1));
	}
	
	$("#activityPublishForm").submit();
	
}

