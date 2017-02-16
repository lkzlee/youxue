<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "../common/core.ftl">
<#setting date_format="yyyy-MM-dd">
<#-- html文档头部 -->
<@docHead title="修改营地"/>
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
                <h4 class="text-center">修改营地</h4>
                <h5 class="text-center" style="color:red">${msg!''}</h5>
                <div class="col-lg-offset-2 col-lg-10">
                    <form class="form-horizontal" id="Form" method="POST" action="doModifyCamps.do" data-validate="true">
                        <div class="form-group">
                            <label class="col-lg-2 control-label">营地名称：</label>
                            <div class="col-lg-8">
                                <input type="text" class="form-control" name="campsName"
                                        data-bv-notempty="true"  value="${camps.campsName!""}"/>
							     <input type="hidden"  name="campsId"
                                        data-bv-notempty="true"  value="${camps.campsId}"/>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地标题：</label>
                            <div class="col-lg-8">
                                <input type="text" class="form-control" name="campsTitle"
                                        data-bv-notempty="true" value="${camps.campsTitle!""}"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">面向对象：</label>
                            <div class="col-lg-8">
                                <input class="form-control" name="orientedPeople" 
                                 		data-bv-notempty="true" value="${camps.orientedPeople!""}"
                                        type="text" />
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">上架状态：</label>
							<div class="col-lg-8">
						      <select class="form-control" name="status" >
									  <#list campsStatusMap?keys as t>
										<option value='${t}' <#if camps.status==t?eval> selected="selected"</#if>>${campsStatusMap[t]}</option>
									  </#list>
								</select>
							</div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地目的地分类：</label>
                            <div class="col-lg-8">
                                <select class="form-control" name="campsLocaleId">
									  <#list localeCategoryList as t>
										<option value='${t.categoryId}' <#if camps.campsLocaleId==t.categoryId> selected="selected"</#if>>${t.categoryName}</option>
									  </#list>
								</select>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地主题分类：</label>
                            <div class="col-lg-8">
                                <select class="form-control" name="campsSubjectId">
									  <#list subjectCategoryList as t>
										<option value='${t.categoryId}' <#if camps.campsSubjectId==t.categoryId> selected="selected"</#if>>${t.categoryName}</option>
									  </#list>
								</select>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地时间周期分类：</label>
                            <div class="col-lg-8">
                                <select class="form-control" name="campsDurationId">
									  <#list durationCategoryList as t>
										<option value='${t.categoryId}' <#if camps.campsDurationId==t.categoryId> selected="selected"</#if>>${t.categoryName}</option>
									  </#list>
								</select>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地价格档位分类：</label>
                            <div class="col-lg-8">
                                <select class="form-control" name="campsPriceId" >
									  <#list priceCategoryList as t>
										<option value='${t.categoryId}' <#if camps.campsPriceId==t.categoryId> selected="selected"</#if>>${t.categoryName}</option>
									  </#list>
								</select>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地出发时间分类：</label>
                            <div class="col-lg-8">
                                <select class="form-control" name="campsDepartureId">
									  <#list depatureCategoryList as t>
										<option value='${t.categoryId}' <#if camps.campsDepartureId==t.categoryId> selected="selected"</#if>>${t.categoryName}</option>
									  </#list>
								</select>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">是否热门：</label>
                            <div class="col-lg-8">
                                <label class="radio-inline">
									<input type="radio" name="ifHot" value="0" <#if camps.ifHot== 0> selected="selected"</#if>/>否
								</label>
								<label class="radio-inline">
									<input type="radio" name="ifHot" value="1" <#if camps.ifHot== 1> selected="selected"</#if>/>是
								</label>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">是否特价：</label>
                            <div class="col-lg-8">
                                <label class="radio-inline">
									<input type="radio" name="ifPrice" value="0" <#if camps.ifPrice== 0> selected="selected"</#if>/>否
								</label>
								<label class="radio-inline">
									<input type="radio" name="ifPrice" value="1" <#if camps.ifPrice== 1> selected="selected"</#if>/>是
								</label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">行程开始时间：</label>
                            <div class="col-lg-8">
                               <input class="form-control" name="startDateStr" value="${camps.startDateStr!""}"
                                        type="date"/>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">持续天数：</label>
                            <div class="col-lg-8">
                                <input class="form-control" name="durationTime"  value="${camps.durationTime!""}"
                                 		data-bv-notempty="true"
                                        type="text" />
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">报名截止时间：</label>
                            <div class="col-lg-8">
                                <input class="form-control" name="deadlineDateStr" value="${camps.deadlineDateStr!""}"
                                        type="date"/>
                            </div>
                        </div>
                  
                        <div class="form-group">
                            <label class="col-lg-2 control-label">产品金额：</label>
                            <div class="col-lg-8">
                                <input class="form-control" name="totalPrice"  data-bv-notempty="true" value="${(camps.totalPrice?c)!''}"
                                        type="number"/>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">产品特色：</label>
                            <div class="col-lg-8">
                                <input class="form-control" name="feature" value="${camps.feature!""}"
                                        type="text"/>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地图片：</label>
                            <div class="col-lg-8 clearfix">
                                <@fileUpload
                                	notEmpty="false"
                                    notEmptyMsg="图片不能为空"
                                    path="camps"
                                    name="campsImages"
                                    ext="png,jpg,jpeg,gif"
                                    maxnum="10" 
									value=(camps.campsImages!'')?split(',')/>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地介绍：</label>
                            <div class="col-lg-8">
                               <textarea class="form-control" name="campsDesc" >${camps.campsDesc!""}</textarea>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地课程内容：</label>
                            <div class="col-lg-8">
                               <textarea class="form-control" name="courseDesc" >${camps.courseDesc!""}</textarea>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地活动内容：</label>
                            <div class="col-lg-8">
                               <textarea class="form-control" name="activityDesc" >${camps.activityDesc!""}</textarea>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地伙食描述：</label>
                            <div class="col-lg-8">
                               <textarea class="form-control" name="campsFoodDesc">${camps.campsFoodDesc!""}</textarea>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地伙食图片：</label>
                            <div class="col-lg-8 clearfix">
                                <@fileUpload
                                	notEmpty="false"
                                    notEmptyMsg="图片不能为空"
                                    path="campsFoods"
                                    name="campsFoodsPhotos"
                                    ext="png,jpg,jpeg,gif"
                                    maxnum="10" 
									value=(camps.campsFoodsPhotos!'')?split(',')/>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地住宿描述：</label>
                            <div class="col-lg-8">
                               <textarea class="form-control" name="campsHotelDesc" >${camps.campsHotelDesc!""}</textarea>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地住宿图片：</label>
                            <div class="col-lg-8 clearfix">
                                <@fileUpload
                                	notEmpty="false"
                                    notEmptyMsg="图片不能为空"
                                    path="campsHotel"
                                    name="campsHotelPhotos"
                                    ext="png,jpg,jpeg,gif"
                                    maxnum="10" 
									value=(camps.campsHotelPhotos!'')?split(',')/>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地行程描述<br>(每日营地用;分割)：</label>
                            <div class="col-lg-8">
                                <textarea class="form-control" name="traceDesc" >${camps.traceDesc!""}</textarea>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地行程图片：</label>
                            <div class="col-lg-8 clearfix">
                                <@fileUpload
                                	notEmpty="false"
                                    notEmptyMsg="图片不能为空"
                                    path="campsTrace"
                                    name="tracePhotos"
                                    ext="png,jpg,jpeg,gif"
                                    maxnum="10" 
									value=(camps.tracePhotos!'')?split(',')/>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">费用详情：</label>
                            <div class="col-lg-8">
                               <textarea class="form-control" name="feeDesc" >${camps.feeDesc}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">常见问题：</label>
                            <div class="col-lg-8">
								<script id="container" name="questions" type="text/plain">
									${camps.questions!""}
								</script>
                                <!-- 编辑器源码文件 -->
								<script type="text/javascript" src="../../ueditor/ueditor.config.js"></script>
								<script type="text/javascript" src="../../ueditor/ueditor.all.js"></script>
								<!-- 实例化编辑器 -->
								<script type="text/javascript">
									var ue = UE.getEditor('container');
								</script>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-lg-offset-4 col-lg-10">
                                <button type="submit" class="btn btn-primary">提交</button>
                            </div>
                       </div>
                    </form>
                </div>
            </div><!-- /.row-->
    </div><!-- /#wrapper -->
<@docFoot />
</#compress>