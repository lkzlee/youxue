<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "../common/core.ftl">
<#setting date_format="yyyy-MM-dd">

<@docHead title="新增营地"/>
<!-- 编辑器源码文件 -->
<script>
window.UEDITOR_HOME_URL = "/ueditor/";
</script>
<script type="text/javascript" src="../../ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="../../ueditor/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8" src="../../ueditor/lang/zh-cn/zh-cn.js"></script>
<!-- 默认样式 -->
<link type="text/css" href="./../ueditor/themes/default/css/ueditor.css" rel="stylesheet" />
<#-- 正文 -->
    <div id="wrapper">
    	<#-- 页头和导航 文件地址："../common/header.ftl"-->
		<@pagehead />
		<#-- 主体内容 -->
				<!-- /.col-lg-12 -->
			<div class="row">
                <h4 class="text-center">签证服务</h4>
                <h5 class="text-center" style="color:red">${msg1!''}</h5>
                <div class="col-lg-offset-2 col-lg-8">
                    <form class="form-horizontal" id="Form" method="POST" action="/admin/addProduct.do" data-validate="true">
                       
                        
						<div class="form-group">
						 	<input type="hidden" class="form-control" name="productId"
                                        data-bv-notempty="false" value="${product1.productId!''}"/>
                            <input type="hidden" class="form-control" name="type"
                                        data-bv-notempty="false" value="1"/>
                            <label class="col-lg-2 control-label">服务内容：</label>
                            <div class="col-lg-8">
								<script id="container" name="productDesc" type="text/plain">
									${product1.productDesc!''}
								</script>
        
								<!-- 实例化编辑器 -->
								<script type="text/javascript">
									var ue = UE.getEditor('container');
									UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;  
									UE.Editor.prototype.getActionUrl = function(action) {  
										if (action == 'uploadimage' || action == 'uploadfile') {  
											var id = $('#carInfoId').val();  
											return '/img/uploadUEDitorImage.do?action=uploadImage';  
										} else {  
											return this._bkGetActionUrl.call(this, action);  
										}  
									};
								</script>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">费用：</label>
                            <div class="col-lg-6">
                                <input type="text" class="form-control" name="productPrice"
                                        data-bv-notempty="true" value="${product1.productPrice!''}" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">上传微信二维码：</label>
                            <div class="col-lg-6 clearfix">
                                <@fileUpload
                                	notEmpty="false"
                                    notEmptyMsg="图片不能为空"
                                    path="wxQrCodePay"
                                    name="weixinQrcodeUrl"
                                    ext="png,jpg,jpeg,gif"
                                    maxnum="10"
                                    value="(product1.weixinQrcodeUrl!'')?split(',')"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">上传支付宝二维码：</label>
                            <div class="col-lg-6 clearfix">
                                <@fileUpload
                                	notEmpty="false"
                                    notEmptyMsg="图片不能为空"
                                    path="aliQrCodePay"
                                    name="alipayQrcodeUrl"
                                    ext="png,jpg,jpeg,gif"
                                    maxnum="10"
                                    value="(product1.alipayQrcodeUrl!'')?split(',')" />
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
            <hr/>
            <div class="row">
                <h4 class="text-center">接送机服务</h4>
                <h5 class="text-center" style="color:red">${msg2!''}</h5>
                <div class="col-lg-offset-2 col-lg-8">
                    <form class="form-horizontal" id="Form" method="POST" action="/admin/addProduct.do" data-validate="true">
                       
                        
						<div class="form-group">
						 	<input type="hidden" class="form-control" name="productId"
                                        data-bv-notempty="false" value="${product2.productId!''}"/>
                            <input type="hidden" class="form-control" name="type"
                                        data-bv-notempty="false" value="2"/>
                            <label class="col-lg-2 control-label">服务内容：</label>
                            <div class="col-lg-8">
								<script id="container1" name="productDesc" type="text/plain">
										${product2.productDesc!''}
								</script>
        
								<!-- 实例化编辑器 -->
								<script type="text/javascript">
									var ue = UE.getEditor('container1');
									UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;  
									UE.Editor.prototype.getActionUrl = function(action) {  
										if (action == 'uploadimage' || action == 'uploadfile') {  
											var id = $('#carInfoId').val();  
											return '/img/uploadUEDitorImage.do?action=uploadImage';  
										} else {  
											return this._bkGetActionUrl.call(this, action);  
										}  
									};
								</script>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">费用：</label>
                            <div class="col-lg-6">
                                <input type="text" class="form-control" name="productPrice"
                                        data-bv-notempty="true" value="${product2.productPrice!''}" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">上传微信二维码：</label>
                            <div class="col-lg-6 clearfix">
                                <@fileUpload
                                	notEmpty="false"
                                    notEmptyMsg="图片不能为空"
                                    path="wxQrCodePay"
                                    name="weixinQrcodeUrl"
                                    ext="png,jpg,jpeg,gif"
                                    maxnum="10"
                                    value="(product2.weixinQrcodeUrl!'')?split(',')"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">上传支付宝二维码：</label>
                            <div class="col-lg-6 clearfix">
                                <@fileUpload
                                	notEmpty="false"
                                    notEmptyMsg="图片不能为空"
                                    path="aliQrCodePay"
                                    name="alipayQrcodeUrl"
                                    ext="png,jpg,jpeg,gif"
                                    maxnum="10"
                                    value="(product2.alipayQrcodeUrl!'')?split(',')" />
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