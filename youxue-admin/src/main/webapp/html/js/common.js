$(function() {
	//表单校验
	$('form[data-validate=true]').bootstrapValidator({
		excluded: [':disabled']
	});

	//日期组件
	$('.form_datetime').each(function(i, elem) {
		var $input = $(elem),
			format = $input.data('format') || 'yyyy-mm-dd',
			initialDate = $input.attr('id'),
			minView = format === 'yyyy-mm-dd hh:ii:ss' ? 'month' : 'month';
		   
		$input.change(function() {//解决【因日期未输入导致校验失败，然后输入日期但input框状态不改变】的问题
			$input.trigger('input').trigger('keyup');

		}).datetimepicker({
			language:  'zh-CN',
			weekStart: 1,
			todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
		    format: format,
		    minView: minView,
		    isInline: true,
		    initialDate:initialDate
		});
	});
	//日期组件
	$('.form_datetimestamp').each(function(i, elem) {
		var $input = $(elem),
			format = $input.data('format') || 'yyyy-mm-dd hh:ii:ss',
			initialDate = $input.attr('id'),
			minView = format === 'yyyy-mm-dd hh:ii:ss' ? 'month' : 'month';
		   
		$input.change(function() {//解决【因日期未输入导致校验失败，然后输入日期但input框状态不改变】的问题
			$input.trigger('input').trigger('keyup');

		}).datetimepicker({
			language:  'zh-CN',
			weekStart: 1,
			todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
		    format: format,
		    minView: minView,
		    isInline: true,
		    initialDate:initialDate
		});
	});
	/*=============================文件上传==============================*/
	//注册事件
	$(document)
		.delegate('.auto_upload_file', 'change', upload) //上传文件
		.delegate('.upload-preview', 'click', function() { //查看已上传文件详情
			window.open($(this).data('url'));

		}).delegate('.preview-del', 'click', function(e) { //删除已上传文件
			var $del = $(this),
				$wrapper = $del.parents('.upload-wrapper'),
				$autoUpload = $wrapper.find('.auto_upload_file');

			//显示上传按钮(删除一个之后必然小于最大数) - 这步要先做, 因为删除预览之后删除按钮也不复存在
			$wrapper.find('.upload-trigger').show();
			
			//删除对应的input域
			$($del.data('hidden')).remove();
			//删除预览图
			$del.parents('.upload-preview').remove();
				
			//阻止冒泡
			return false; 
		});
	
	//上传组件非空校验
	$('.auto_upload_file[data-notempty=true]').each(function() {
		var $autoUpload = $(this);
		$autoUpload.parents('form').submit($.proxy(validateUpload, $autoUpload));
	});
	function validateUpload() { 
		var $autoUpload = $(this),
			msg = $autoUpload.data('notempty-message'),
			$uploadWrapper = $autoUpload.parents('.upload-wrapper'),
			$uploadPreview = $uploadWrapper.find('.upload-preview'),
			$uploadErr = $uploadWrapper.find('.upload-err'),
			$formGroup = $uploadWrapper.parents('.form-group');

		if(!$uploadPreview.length) {
			$uploadErr.html(msg).show();
			$formGroup.addClass('has-error');
			return false;//阻止提交

		} else {
			$uploadErr.empty().hide();
			$formGroup.removeClass('has-error');
		}
	}

	//上传处理
	function upload(){
		var $fileInput = $(this),
			$wrapper = $fileInput.parents('.upload-wrapper'),
			$uploadErr = $wrapper.find('.upload-err'),
			$form = $wrapper.parents('form'),
			$formGroup = $wrapper.parents('.form-group'),
			$trigger = $fileInput.parents('.upload-trigger'),
			allowedType = $wrapper.data('ext'), //允许上传的文件类型
			maxNum = +$wrapper.data('maxnum') || 1, //最多上传文件数
			maxSize = +$wrapper.data('maxsize') || 1048576, //单个文件的最大字节数
			inputName = $wrapper.data('name'),  //表单域的名称（用于与后端通信）
			fileObj = $fileInput[0].files[0],
			ext = fileObj.name.replace(/.+\.([^\.\/\\]+)$/, "$1").toLowerCase(),
			path = $wrapper.data('path') || '',
			url = '/img/'+ (path ? (path+'/') : '') +'uploadImg.do',
			formData = new FormData();
		
		$uploadErr.empty().hide();
		$formGroup.removeClass('has-error');

		if (!window.FormData) {
			alert("您的浏览器不支持FormData，请更换chrome/firefox等浏览器");
			return;
		}
		if (allowedType && allowedType.indexOf(ext) < 0) {
			alert("请上传 " + allowedType +" 类型的文件");
			return;
		}
		if (fileObj.size >= maxSize) {
			alert("请上传小于 " + maxSize +" 字节的文件");
			return;
		}

		//开始上传
		formData.append("imgFile", fileObj);
		$trigger.addClass('uploading');
		$.ajax({
			url: url, 
			dataType: 'json', //响应的数据格式
			type: 'POST',
			data: formData,
			/**   
			 * 必须false才会避开jQuery对 formdata 的默认处理
			 * XMLHttpRequest会对 formdata 进行正确的处理
			 */
			processData: false,
			/**   
			 *必须false才会自动加上正确的Content-Type
			 */
			contentType: false,
			success: function(ret) {
				$trigger.removeClass('uploading');
				ret=JSON.parse(ret);
				if(ret.retCode === 200) {
					//保存对应的隐藏表单域的Id, 用于删除
					var hiddenInputId = 'uploadHidden' + (+new Date());
					
					//插入预览图
					$trigger.before(generateHTML('preview', {
						ext: ext,
						url: ret.url,
						hiddenInputId: hiddenInputId
					})).before(generateHTML('hidden', {
						name: inputName,
						value: ret.url, 
						id: hiddenInputId
					}));
					
					//若已到达上传上限, 则隐藏上传按钮
					if($wrapper.find('.upload-preview').length >= maxNum) {
						$trigger.hide();
					}

					//去掉BootstrapValidator设置的disabled
					$form.find('[type=submit]').removeAttr('disabled');

				} else {
					alert('上传失败: ' + ret.msg);
				}
			},
			error: function(ret) {
				$trigger.removeClass('uploading');
				console.log(ret);
				alert('网络错误或后端返回的不是json, 错误信息请F12查看控制台');
			}
		});
	}

	//创建dom节点
	function generateHTML(type, data) {
		switch(type) {
			case 'hidden':
				return '<input type="hidden" id="'+ data.id +'" name="'+ data.name +'" value="'+ data.value +'" />';

			case 'preview': 
				var str = '<div class="upload-preview" data-url="'+ data.url +'">' +
							'<div class="preview-del" title="删除" data-hidden="#'+ data.hiddenInputId +'">X</div>';

				if('png,jpg,jpeg,gif'.indexOf(data.ext.toLowerCase()) > -1) {
					str += '<img src="'+ data.url +'" title="点击查看大图"/>';

				} else {
					str += '<p class="preview-ext" title="">'+ data.ext.toUpperCase() +'</p>';
				}
				str += '</div>'; 
				return str;
		}
	}
	/*=============================文件上传(完)==============================*/

	
	/*=============================ajax数据刷新==============================*/
	$(document).delegate('[data-flush]', 'click', function() {
		var $btn = $(this),
			sendRequest = function() {
				var url = $.trim($btn.data('flush-url')),
					rels = $.trim($btn.data('flush-para')), //ajax请求参数相关节点
					para = null,
					type = +$btn.data('flush-type'),
					defaultHandler = function(data) {
						alert(data.retCode + ' : ' + data.msg);
					};

				if ( rels ) {
					rels = rels.split(/\s+/);
					for ( var i = 0, len = rels.length; i < len; i++ ) {
						if( $('[name='+ rels[i] +']').length ) {
							para = para || {};
							para[rels[i]] = $.trim($('[name='+ rels[i] +']').val());
						}
					}
				}
				console.log(url);
				$.get(url, para, function(data) {
					switch(type) {
						case 1:
							if ( +data.retCode === 1 ) {
								window.location.reload();
							} else {
								defaultHandler(data);
							}
							break;

						default:
							defaultHandler(data);
					}
				}, 'json');
			};

		if( $btn.hasClass('btn-validate') && $btn.parents('form[data-validate=true]').length ) {
			 var validator = $btn.parents('form[data-validate=true]').data('bootstrapValidator');
			 //校验，若有错误则显示错误信息
			 validator.validate();
			 validator.isValid() && sendRequest();

		} else {
			sendRequest();
		}

	});
	/*=============================ajax数据刷新(完)==========================*/


	/*===================选择radio联动显示输入框组件=========================*/
    //在控制的radio 父层容器上的 data-toggle ='radio-tab',表示需要激活radio选择联动
    //要求该父层容器只能包含同一个name的radio
    //控制的radio的标签中加上data-target ="#id" 设置被控制模块的选择器,有多个时用","分开，例如：data-target ="#id1,#id2"
    function radioTab(){
        var $radioTab = $("[data-toggle ='radio-tab']"); 
        $radioTab.delegate("input:radio", 'click', function(event) {     
            var ev =$(this);
            var evName = $(this).attr("name");
            var noChected = $radioTab.find(":radio[name="+evName+"]:not(:checked)");            
            noChected.each(function(){
                var dom = $(this);
                $(dom.attr("data-target")).addClass("hide");             
            });     
            $(ev.attr("data-target")).removeClass("hide");
            
        });
    } 
    radioTab();

    /*===================选择radio联动显示输入框组件 (完)====================*/

    /*=======================全选、全不选====================================*/
		$(document).delegate('input[data-toggle = "checked-all"]', 'click', function(event) {
			 var $ev =$(this);
			 var subName=  $ev.attr("data-target")|| $ev.attr("name");
			 var subBox = 'input[name='+subName+']:not([data-toggle])';			
             $(subBox).prop("checked",this.checked);          
         
            $(subBox).click(function(){
               $ev.prop("checked",$(subBox).length == $(subBox+":checked").length ? true : false);
            });			 
		});

    /*=======================全选、全不选(完)================================*/


    /*=======================select框级联控制================================*/    	
		$(document).delegate('select[data-toggle = "select-tab"]', 'change', function(event) {
			 var $ev =$(this);			 
			 var indexTargetName =  $ev.find('option:selected').attr("data-target")|| $ev.val();
			 var parentName = "[data-parent='"+$ev.attr('name')+"']";
			 var allTargetDom =parentName+"[data-selected-target]";
			 var indexTarget= parentName+"[data-selected-target="+indexTargetName+"]";
			 $(allTargetDom).addClass('hide');
			 $(indexTarget).removeClass('hide');
		});

    /*=======================select框级联控制(完)=============================*/

});