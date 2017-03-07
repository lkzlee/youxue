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
                                    value=(aboutUs.imgurl!'')?split(',') />
                            </div>
                        </div>
						<div class="form-group row">
                            <label class="col-lg-2 control-label">关于我们描述：</label>
                            <div class="col-lg-6">
                               <textarea class="form-control" name="descs" >${aboutUs.descs!""}</textarea>
                                <input type="hidden"  name="type" data-bv-notempty="true"  value="aboutUs"/>
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
                                    value=(qinggu.imgurl!'')?split(',') />
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">青古内容描述：</label>
                            <div class="col-lg-6">
                               <textarea class="form-control" name="descs" >${qinggu.descs!""}</textarea>
                                <input type="hidden"  name="type" data-bv-notempty="true"  value="qinggu"/>
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
                                    value=(creator.imgurl!'')?split(',')/>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">合作伙伴描述：</label>
                            <div class="col-lg-6">
                               <textarea class="form-control" name="descs" >${creator.descs!""}</textarea>
                                <input type="hidden"  name="type" data-bv-notempty="true"  value="creator"/>
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
                                    value=(mainProduct.imgurl!'')?split(',') />
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">常见问题描述：</label>
                            <div class="col-lg-6">
                               <textarea class="form-control" name="descs" >${mainProduct.descs!""}</textarea>
                                <input type="hidden"  name="type" data-bv-notempty="true"  value="mainProduct"/>
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