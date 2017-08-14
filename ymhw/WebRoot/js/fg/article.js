$(function() {
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
});
	
var share = function() {
	var contentHtml = UE.getEditor('contentContainer').getContent();
	$("#content").attr("value", contentHtml);

	var typeList = "";
	$("input[name='type']:checked").each(function() {
		typeList += $(this).val() + ",";
	});
	if(!isNullOrEmpty(typeList)) {
		$("#types").attr("value", "");
		$("#types").attr("value", typeList.substr(0, typeList.length - 1));
	}

	$("#articlePublishForm").submit();
}