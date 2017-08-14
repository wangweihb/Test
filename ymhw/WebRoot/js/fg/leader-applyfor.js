$(function()
{
	/**
	 * 处理file input加载的图片文件
	 * 判断浏览器是否有FileReader接口
	 */
    if(typeof FileReader =='undefined')
    {
       $("#imgUpload1Content").css({'background':'none'}).html('您的浏览器还不支持HTML5的FileReader接口,无法使用图片本地预览,请更新浏览器获得最好体验');
       
       //如果浏览器是ie
        if($.browser.msie===true)
        {
            //ie6直接用file input的value值本地预览
            if($.browser.version==6)
            {
                $("#imgUpload1").change(function(event){                        
                      //ie6下怎么做图片格式判断?
                      var src = event.target.value;
                      //var src = document.selection.createRange().text;        //选中后 selection对象就产生了 这个对象只适合ie
                      var img = '<img id="certificateFrontImg" name="certificateFrontImg" src="'+src+'" width="350px" height="200px" />';
                      $("#imgUpload1Content").empty().append(img);
                      $("#certificateFront").attr("value",src);
                  });
                  
                $("#imgUpload2").change(function(event){                        
                      //ie6下怎么做图片格式判断?
                      var src = event.target.value;
                      //var src = document.selection.createRange().text;        //选中后 selection对象就产生了 这个对象只适合ie
                      var img = '<img id="certificateBackImg" name="certificateBackImg" src="'+src+'" width="350px" height="200px" />';
                      $("#imgUpload2Content").empty().append(img);
                      $("#certificateBack").attr("value",src);
                });
                
                 $("#imgUpload3").change(function(event){                        
                      //ie6下怎么做图片格式判断?
                      var src = event.target.value;
                      //var src = document.selection.createRange().text;        //选中后 selection对象就产生了 这个对象只适合ie
                      var img = '<img id="certificateHanderImg" name="certificateHanderImg" src="'+src+'" width="350px" height="200px" />';
                      $("#imgUpload3Content").empty().append(img);
                      $("#certificateHander").attr("value",src);
                });
                
                $("#imgUpload4").change(function(event){                        
                      //ie6下怎么做图片格式判断?
                      var src = event.target.value;
                      //var src = document.selection.createRange().text;        //选中后 selection对象就产生了 这个对象只适合ie
                      var img = '<img id="otherCertificateImg" name="otherCertificateImg" src="'+src+'" width="350px" height="200px" />';
                      $("#imgUpload4Content").empty().append(img);
                      $("#otherCertificate").attr("value",src);
                });
                
                
                //图像上传
	             $("#headUpload").change(function(event)
	            {
	                $(event.target).select();
	                var src = document.selection.createRange().text;
	                $("#headImg").attr("src",src);
	                $("#headShot").attr("value",src);
	             });
            }
            //ie7,8使用滤镜本地预览
            else if($.browser.version==7 || $.browser.version==8)
            {
                $("#imgUpload1").change(function(event)
                {
                      $(event.target).select();
                      var src = document.selection.createRange().text;
                      var dom = document.getElementById('imgUpload1Content');
                      //使用滤镜 成功率高
                      dom.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src= src;
                      dom.innerHTML = '';
                      //使用和ie6相同的方式 设置src为绝对路径的方式 有些图片无法显示 效果没有使用滤镜好
                      /*var img = '<img src="'+src+'" width="300px" height="300px" />';
                      $("#destination").empty().append(img);*/
                 });
                 
                 $("#imgUpload2").change(function(event)
                {
                      $(event.target).select();
                      var src = document.selection.createRange().text;
                      var dom = document.getElementById('imgUpload2Content');
                      //使用滤镜 成功率高
                      dom.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src= src;
                      dom.innerHTML = '';
                      //使用和ie6相同的方式 设置src为绝对路径的方式 有些图片无法显示 效果没有使用滤镜好
                      /*var img = '<img src="'+src+'" width="300px" height="300px" />';
                      $("#destination").empty().append(img);*/
                 });
                 
                 $("#imgUpload3").change(function(event)
                {
                      $(event.target).select();
                      var src = document.selection.createRange().text;
                      var dom = document.getElementById('imgUpload3Content');
                      //使用滤镜 成功率高
                      dom.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src= src;
                      dom.innerHTML = '';
                      //使用和ie6相同的方式 设置src为绝对路径的方式 有些图片无法显示 效果没有使用滤镜好
                      /*var img = '<img src="'+src+'" width="300px" height="300px" />';
                      $("#destination").empty().append(img);*/
                 });
                 
                 $("#imgUpload4").change(function(event)
                {
                      $(event.target).select();
                      var src = document.selection.createRange().text;
                      var dom = document.getElementById('imgUpload4Content');
                      //使用滤镜 成功率高
                      dom.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src= src;
                      dom.innerHTML = '';
                      //使用和ie6相同的方式 设置src为绝对路径的方式 有些图片无法显示 效果没有使用滤镜好
                      /*var img = '<img src="'+src+'" width="300px" height="300px" />';
                      $("#destination").empty().append(img);*/
                 });
                 
                 //图像上传
	             $("#headUpload").change(function(event)
	            {
	                $(event.target).select();
	                var src = document.selection.createRange().text;
	                $("#headImg").attr("src",src);
	                $("#headShot").attr("value",src);
	             });
            }                                    
        }
        //如果是不支持FileReader接口的低版本firefox 可以用getAsDataURL接口
        else if($.browser.mozilla===true)
        {
            $("#imgUpload1").change(function(event)
            {
                //firefox2.0没有event.target.files这个属性 就像ie6那样使用value值 但是firefox2.0不支持绝对路径嵌入图片 放弃firefox2.0
                //firefox3.0开始具备event.target.files这个属性 并且开始支持getAsDataURL()这个接口 一直到firefox7.0结束 不过以后都可以用HTML5的FileReader接口了
                if(event.target.files)
                {
                  //console.log(event.target.files);
                  for(var i=0;i<event.target.files.length;i++)
                  {    
                        var img = '<img id="certificateFrontImg" name="certificateFrontImg"  src="'+event.target.files.item(i).getAsDataURL()+'" width="350px" height="200px"/>';
                      $("#imgUpload1Content").empty().append(img);
                       $("#certificateFront").attr("value",event.target.files.item(i).getAsDataURL());
                  }
                }
                else
                {
                    //console.log(event.target.value);
                    //$("#imgPreview").attr({'src':event.target.value});
                }
            });
            
             $("#imgUpload2").change(function(event)
            {
                //firefox2.0没有event.target.files这个属性 就像ie6那样使用value值 但是firefox2.0不支持绝对路径嵌入图片 放弃firefox2.0
                //firefox3.0开始具备event.target.files这个属性 并且开始支持getAsDataURL()这个接口 一直到firefox7.0结束 不过以后都可以用HTML5的FileReader接口了
                if(event.target.files)
                {
                  //console.log(event.target.files);
                  for(var i=0;i<event.target.files.length;i++)
                  {    
                        var img = '<img id="certificateBackImg" name="certificateBackImg"  src="'+event.target.files.item(i).getAsDataURL()+'" width="350px" height="200px"/>';
                      $("#imgUpload2Content").empty().append(img);
                      $("#certificateBack").attr("value",event.target.files.item(i).getAsDataURL());
                  }
                }
                else
                {
                    //console.log(event.target.value);
                    //$("#imgPreview").attr({'src':event.target.value});
                }
            });
            
             $("#imgUpload3").change(function(event)
            {
                //firefox2.0没有event.target.files这个属性 就像ie6那样使用value值 但是firefox2.0不支持绝对路径嵌入图片 放弃firefox2.0
                //firefox3.0开始具备event.target.files这个属性 并且开始支持getAsDataURL()这个接口 一直到firefox7.0结束 不过以后都可以用HTML5的FileReader接口了
                if(event.target.files)
                {
                  //console.log(event.target.files);
                  for(var i=0;i<event.target.files.length;i++)
                  {    
                        var img = '<img id="certificateHanderImg" name="certificateHanderImg"  src="'+event.target.files.item(i).getAsDataURL()+'" width="350px" height="200px"/>';
                      $("#imgUpload3Content").empty().append(img);
                      $("#certificateHander").attr("value",event.target.files.item(i).getAsDataURL());
                  }
                }
                else
                {
                    //console.log(event.target.value);
                    //$("#imgPreview").attr({'src':event.target.value});
                }
            });
            
             $("#imgUpload4").change(function(event)
            {
                //firefox2.0没有event.target.files这个属性 就像ie6那样使用value值 但是firefox2.0不支持绝对路径嵌入图片 放弃firefox2.0
                //firefox3.0开始具备event.target.files这个属性 并且开始支持getAsDataURL()这个接口 一直到firefox7.0结束 不过以后都可以用HTML5的FileReader接口了
                if(event.target.files)
                {
                  //console.log(event.target.files);
                  for(var i=0;i<event.target.files.length;i++)
                  {    
                        var img = '<img id="otherCertificateImg" name="otherCertificateImg"  src="'+event.target.files.item(i).getAsDataURL()+'" width="350px" height="200px"/>';
                      $("#imgUpload4Content").empty().append(img);
                      $("#otherCertificate").attr("value",event.target.files.item(i).getAsDataURL());
                  }
                }
                else
                {
                    //console.log(event.target.value);
                    //$("#imgPreview").attr({'src':event.target.value});
                }
            });
            
            //图像上传
             $("#headUpload").change(function(event)
            {
                $(event.target).select();
                var src = document.selection.createRange().text;
                alert("src1-->"+src);
                $("#headImg").attr("src",src);
                $("#headShot").attr("value",src);
             });
        }
    }
    else
    {
          //多图上传 input file控件里指定multiple属性 e.target是dom类型
           $("#imgUpload1").change(function(e)
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
	                    var img = '<img id="certificateFrontImg" name="certificateFrontImg"  src="'+e.target.result+'" width="350px" height="200px"/>';
	                    $("#imgUpload1Content").empty().append(img);
	                    $("#certificateFront").attr("value",e.target.result);
	                }
           		}
            });
               
          //处理图片拖拽的代码
          var destDom = document.getElementById('imgUpload1Content');
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
                  destDom.innerHTML = '<img id="certificateFrontImg" name="certificateFrontImg"  src="'+event.target.result+'" width="350px" height="200px"/>';    
                  };
          },false);
          
           //多图上传 input file控件里指定multiple属性 e.target是dom类型
           $("#imgUpload2").change(function(e)
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
	                    var img = '<img id="certificateBackImg" name="certificateBackImg"  src="'+e.target.result+'" width="350px" height="200px"/>';
	                    $("#imgUpload2Content").empty().append(img);
	                    $("#certificateBack").attr("value",e.target.result);
	                }
           		}
            });
               
          //处理图片拖拽的代码
          var destDom2 = document.getElementById('imgUpload2Content');
          destDom2.addEventListener('dragover',function(event)
          {
	          event.stopPropagation();
	          event.preventDefault();
          },false);
              
          destDom2.addEventListener('drop',function(event)
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
                  destDom.innerHTML = '<img id="certificateBackImg" name="certificateBackImg"  src="'+event.target.result+'" width="350px" height="200px"/>';    
                  };
          },false);
          
          //多图上传 input file控件里指定multiple属性 e.target是dom类型
           $("#imgUpload3").change(function(e)
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
	                    var img = '<img id="certificateHanderImg" name="certificateHanderImg"  src="'+e.target.result+'" width="350px" height="200px"/>';
	                    $("#imgUpload3Content").empty().append(img);
	                    $("#certificateHander").attr("value",e.target.result);
	                }
           		}
            });
               
          //处理图片拖拽的代码
          var destDom3 = document.getElementById('imgUpload3Content');
          destDom.addEventListener('dragover',function(event)
          {
	          event.stopPropagation();
	          event.preventDefault();
          },false);
              
          destDom3.addEventListener('drop',function(event)
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
                  destDom.innerHTML = '<img id="certificateHanderImg" name="certificateHanderImg"  src="'+event.target.result+'" width="350px" height="200px"/>';    
                  };
          },false);
          
           //多图上传 input file控件里指定multiple属性 e.target是dom类型
           $("#imgUpload4").change(function(e)
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
	                    var img = '<img id="otherCertificateImg" name="otherCertificateImg"  src="'+e.target.result+'" width="350px" height="200px"/>';
	                    $("#imgUpload4Content").empty().append(img);
	                    $("#otherCertificate").attr("value",e.target.result);
	                }
           		}
            });
               
          //处理图片拖拽的代码
          var destDom4 = document.getElementById('imgUpload4Content');
          destDom4.addEventListener('dragover',function(event)
          {
	          event.stopPropagation();
	          event.preventDefault();
          },false);
              
          destDom4.addEventListener('drop',function(event)
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
                  destDom.innerHTML = '<img id="otherCertificateImg" name="otherCertificateImg"  src="'+event.target.result+'" width="350px" height="200px"/>';    
                  };
          },false);
                          
         //头像上传
       $("#headUpload").change(function(e)
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
                    $("#headImg").attr("src",e.target.result);
	        		$("#headShot").attr("value",e.target.result);
                }
       		}
        });
    }
});



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

var check = function()
{
	$("#leaderForm").submit();
}

/**
 * 第一步中的确认
 */
var affrim = function()
{
	var isChecked = document.getElementById("readProcal").checked;
	if(isChecked){
		toNext(1,2);
	}else{
		alert("请阅读并确认友鸣官方领队管理条例");
	}
}
