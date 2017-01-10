<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "../../common/core.ftl">

<#-- html文档头部 -->
<@docHead title="修改分类信息"/>
<#-- 正文 -->
    <div id="wrapper">
    	<#-- 页头和导航 文件地址："../../common/header.ftl"-->
		<@pagehead />
		<#-- 主体内容 -->
				<!-- /.col-lg-12 -->
			<div class="row">
                <h4 class="text-center">修改分类信息</h4>
                <h5 class="text-center" style="color:red">${msg!''}</h5>
                <div class="col-lg-offset-2 col-lg-8">
                    <form class="form-horizontal" id="Form" method="POST" action="/doModifyCategory.do" data-validate="true">
                        <div class="form-group">
                            <label class="col-lg-2 control-label">分类名称：</label>
                            <div class="col-lg-6">
                                <input type="text" class="form-control" name="categoryName"
                                        data-bv-notempty="true"   value="${category.categoryName}"/>
                                 <input type="hidden" name="categoryId" value="${category.categoryId}" id="categoryId">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">分类类别：</label>
                            <div class="col-lg-6">
                                <select class="form-control" name="categoryType" id="categoryType">
									<#list categoryTypeMap?keys as t>
									       <option value='${t}'  <#if category.categoryType==t?eval> selected="selected"</#if>> ${categoryTypeMap[t]}</option>
									</#list>
								</select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">分类权重<br>(范围0-999)：</label>
                            <div class="col-lg-6">
                                <input class="form-control" name="categoryWeight" 
                                        type="text" value="${category.categoryWeight}"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">分类图片：</label>
                            <div class="col-lg-10 clearfix">
                                <@fileUpload
                                	notEmpty="true"
                                    notEmptyMsg="图片不能为空"
                                    path="camps"
                                    name="categoryUrl"
                                    ext="png,jpg,jpeg,gif"
                                    maxnum="1"
                                    value=(category.categoryUrl!'')?split(',')/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-lg-offset-4 col-lg-8">
                                <button type="submit" class="btn btn-primary" >修改</button>
                            </div>
                       </div>
                    </form>
                </div>
            </div><!-- /.row-->
    </div><!-- /#wrapper -->
<@docFoot />
</#compress>