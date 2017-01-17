<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "../common/core.ftl">
<#setting date_format="yyyy-MM-dd">
<#-- html文档头部 -->
<@docHead title="关于我们"/>
<#-- 正文 -->
    <div id="wrapper">
    	<#-- 页头和导航 文件地址："../common/header.ftl"-->
		<@pagehead />
		<#-- 主体内容 -->
				<!-- /.col-lg-12 -->
			<div class="row">
                <h4 class="text-center">关于我们</h4>
                <h5 class="text-center" style="color:red">${msg!''}</h5>
                <div class="col-lg-offset-2 col-lg-8">
                    <form class="form-horizontal" id="Form" method="POST" action="doModifyAboutUs.do" data-validate="true">
						<div class="form-group">
                            <label class="col-lg-2 control-label">上传海报：</label>
                            <div class="col-lg-6 clearfix">
                                <@fileUpload
                                	notEmpty="false"
                                    notEmptyMsg="图片不能为空"
                                    path="aboutUs"
                                    name="imgUrl"
                                    ext="png,jpg,jpeg,gif"
                                    maxnum="10" />
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">描述：</label>
                            <div class="col-lg-6">
                               <textarea class="form-control" name="desc" >输入关于我们内容描述</textarea>
                                <input type="hidden"  name="type" data-bv-notempty="true"  value="aboutUs"/>
                            </div>
                        </div>
                    </form>
                </div>
                <hr>
                <div class="col-lg-offset-2 col-lg-8">
                    <form class="form-horizontal" id="Form" method="POST" action="doModifyAboutUs.do" data-validate="true">
						<div class="form-group">
                            <label class="col-lg-2 control-label">上传海报：</label>
                            <div class="col-lg-6 clearfix">
                                <@fileUpload
                                	notEmpty="false"
                                    notEmptyMsg="图片不能为空"
                                    path="aboutUs"
                                    name="imgUrl"
                                    ext="png,jpg,jpeg,gif"
                                    maxnum="10" />
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">描述：</label>
                            <div class="col-lg-6">
                               <textarea class="form-control" name="desc" >输入关于青古内容描述</textarea>
                                <input type="hidden"  name="type" data-bv-notempty="true"  value="qinggu"/>
                            </div>
                        </div>
                    </form>
                </div>
                 <hr>
                <div class="col-lg-offset-2 col-lg-8">
                    <form class="form-horizontal" id="Form" method="POST" action="doModifyAboutUs.do" data-validate="true">
						<div class="form-group">
                            <label class="col-lg-2 control-label">上传海报：</label>
                            <div class="col-lg-6 clearfix">
                                <@fileUpload
                                	notEmpty="false"
                                    notEmptyMsg="图片不能为空"
                                    path="aboutUs"
                                    name="imgUrl"
                                    ext="png,jpg,jpeg,gif"
                                    maxnum="10" />
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">描述：</label>
                            <div class="col-lg-6">
                               <textarea class="form-control" name="desc" >输入关于创始人内容描述</textarea>
                                <input type="hidden"  name="type" data-bv-notempty="true"  value="creator"/>
                            </div>
                        </div>
                    </form>
                </div>
                 <hr>
                 <div class="col-lg-offset-2 col-lg-8">
                    <form class="form-horizontal" id="Form" method="POST" action="doModifyAboutUs.do" data-validate="true">
						<div class="form-group">
                            <label class="col-lg-2 control-label">上传海报：</label>
                            <div class="col-lg-6 clearfix">
                                <@fileUpload
                                	notEmpty="false"
                                    notEmptyMsg="图片不能为空"
                                    path="aboutUs"
                                    name="imgUrl"
                                    ext="png,jpg,jpeg,gif"
                                    maxnum="10" />
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">描述：</label>
                            <div class="col-lg-6">
                               <textarea class="form-control" name="desc" >输入关于主营产品内容描述</textarea>
                                <input type="hidden"  name="type" data-bv-notempty="true"  value="mainProduct"/>
                            </div>
                        </div>
                    </form>
                </div>
            </div><!-- /.row-->
    </div><!-- /#wrapper -->
<@docFoot />
</#compress>