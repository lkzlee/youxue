<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "../common/core.ftl">
<#setting date_format="yyyy-MM-dd">
<#-- html文档头部 -->
<@docHead title="关于我们"/>
<script>
	window.UEDITOR_HOME_URL = "/ueditor/";
</script>
<script type="text/javascript" src="../../ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="../../ueditor/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8" src="../../ueditor/lang/zh-cn/zh-cn.js"></script>
<#-- 正文 -->
    <div id="wrapper">
    	<#-- 页头和导航 文件地址："../common/header.ftl"-->
		<@pagehead />
		<#-- 主体内容 -->
				<!-- /.col-lg-12 -->
			<div class="row">
                <h5 class="text-center" style="color:red">${msg!''}</h5>
                <div class="col-lg-offset-2 col-lg-8">
                	<h4 class="text-center">关于我们</h4>
                    <form class="form-horizontal" id="Form" method="POST" action="doModifyAboutUs.do" data-validate="true">
						<div class="form-group row">
                            <label class="col-lg-2 control-label">上传海报：</label>
                            <div class="col-lg-6 clearfix">
                                <@fileUpload
                                	notEmpty="false"
                                    notEmptyMsg="图片不能为空"
                                    path="aboutUs"
                                    name="imgurl"
                                    ext="png,jpg,jpeg,gif"
                                    maxnum="10"
									group="1"
                                    value=(aboutUs.imgurl!'')?split(',') />
                            </div>
                        </div>
						<div class="form-group row">
                            <label class="col-lg-2 control-label">关于我们描述：</label>
                            <div class="col-lg-6">
                            	<input type="hidden"  name="type" data-bv-notempty="true"  value="aboutUs"/>
								<script id="container1" name="descs" type="text/plain">
									${aboutUs.descs!""}
								</script>
								
                          		<!-- 实例化编辑器 -->
								<script type="text/javascript">
									var ue = UE.getEditor('container1');
									UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;  
									UE.Editor.prototype.getActionUrl = function(action) {  
										if (action == 'uploadimage' || action == 'uploadfile') {  
											return '/img/uploadUEDitorImage.do?action=uploadImage';  
										} else {  
											return this._bkGetActionUrl.call(this, action);  
										}  
									};
								</script>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-lg-offset-4 col-lg-8">
                                <button type="submit" class="btn btn-primary">提交</button>
                            </div>
                       </div>
                    </form>
                </div>
                <hr>
                <div class="col-lg-offset-2 col-lg-8">
                	<h4 class="text-center">青古内容</h4>
                    <form class="form-horizontal" id="Form" method="POST" action="doModifyAboutUs.do" data-validate="true">
						<div class="form-group">
                            <label class="col-lg-2 control-label">上传海报：</label>
                            <div class="col-lg-6 clearfix">
                                <@fileUpload
                                	notEmpty="false"
                                    notEmptyMsg="图片不能为空"
                                    path="aboutUs"
                                    name="imgurl"
                                    ext="png,jpg,jpeg,gif"
                                    maxnum="10" 
									group="2"
                                    value=(qinggu.imgurl!'')?split(',') />
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">青古内容描述：</label>
                            <div class="col-lg-6">
                            	<input type="hidden"  name="type" data-bv-notempty="true"  value="qinggu"/>
								<script id="container2" name="descs" type="text/plain">
									${qinggu.descs!""}
								</script>
                               <!-- 实例化编辑器 -->
								<script type="text/javascript">
									var ue = UE.getEditor('container2');
									UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;  
									UE.Editor.prototype.getActionUrl = function(action) {  
										if (action == 'uploadimage' || action == 'uploadfile') {  
											return '/img/uploadUEDitorImage.do?action=uploadImage';  
										} else {  
											return this._bkGetActionUrl.call(this, action);  
										}  
									};
								</script>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-lg-offset-4 col-lg-8">
                                <button type="submit" class="btn btn-primary">提交</button>
                            </div>
                       </div>
                    </form>
                </div>
                 <hr>
             
                <div class="col-lg-offset-2 col-lg-8">
                   <h4 class="text-center">合作伙伴</h4>
                    <form class="form-horizontal" id="Form" method="POST" action="doModifyAboutUs.do" data-validate="true">
						<div class="form-group">
                            <label class="col-lg-2 control-label">上传海报：</label>
                            <div class="col-lg-6 clearfix">
                                <@fileUpload
                                	notEmpty="false"
                                    notEmptyMsg="图片不能为空"
                                    path="aboutUs"
                                    name="imgurl"
                                    ext="png,jpg,jpeg,gif"
                                    maxnum="10" 
									group="3"
                                    value=(creator.imgurl!'')?split(',')/>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">合作伙伴描述：</label>
                            <div class="col-lg-6">
                            	<input type="hidden"  name="type" data-bv-notempty="true"  value="creator"/>
								<script id="container3" name="descs" type="text/plain">
									${creator.descs!""}
								</script>
                                <!-- 实例化编辑器 -->
								<script type="text/javascript">
									var ue = UE.getEditor('container3');
									UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;  
									UE.Editor.prototype.getActionUrl = function(action) {  
										if (action == 'uploadimage' || action == 'uploadfile') {  
											return '/img/uploadUEDitorImage.do?action=uploadImage';  
										} else {  
											return this._bkGetActionUrl.call(this, action);  
										}  
									};
								</script>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-lg-offset-4 col-lg-8">
                                <button type="submit" class="btn btn-primary">提交</button>
                            </div>
                       </div>
                    </form>
                </div>
                 <hr>
              
                 <div class="col-lg-offset-2 col-lg-8">
                   <h4 class="text-center">常见问题</h4>
                    <form class="form-horizontal" id="Form" method="POST" action="doModifyAboutUs.do" data-validate="true">
						<div class="form-group">
                            <label class="col-lg-2 control-label">上传海报：</label>
                            <div class="col-lg-6 clearfix">
                                <@fileUpload
                                	notEmpty="false"
                                    notEmptyMsg="图片不能为空"
                                    path="aboutUs"
                                    name="imgurl"
                                    ext="png,jpg,jpeg,gif"
                                    maxnum="10"
									group="4"
                                    value=(mainProduct.imgurl!'')?split(',') />
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">常见问题描述：</label>
                            <div class="col-lg-6">
                            	<input type="hidden"  name="type" data-bv-notempty="true"  value="mainProduct"/>
								<script id="container4" name="descs" type="text/plain">
									${mainProduct.descs!""}
								</script>
                                <!-- 实例化编辑器 -->
								<script type="text/javascript">
									var ue = UE.getEditor('container4');
									UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;  
									UE.Editor.prototype.getActionUrl = function(action) {  
										if (action == 'uploadimage' || action == 'uploadfile') {  
											return '/img/uploadUEDitorImage.do?action=uploadImage';  
										} else {  
											return this._bkGetActionUrl.call(this, action);  
										}  
									};
								</script>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-lg-offset-4 col-lg-8">
                                <button type="submit" class="btn btn-primary">提交</button>
                            </div>
                       </div>
                    </form>
                </div>
            </div><!-- /.row-->
    </div><!-- /#wrapper -->
<@docFoot />
</#compress>