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
										<option value='${t.categoryId}' <#if (camps.campsLocaleId!'')==t.categoryId> selected="selected"</#if>>${t.categoryName}</option>
									  </#list>
								</select>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地主题分类：</label>
                            <div class="col-lg-8">
                                <select class="form-control" name="campsSubjectId">
									  <#list subjectCategoryList as t>
										<option value='${t.categoryId}' <#if (camps.campsSubjectId!'')==t.categoryId> selected="selected"</#if>>${t.categoryName}</option>
									  </#list>
								</select>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地时间周期分类：</label>
                            <div class="col-lg-8">
                                <select class="form-control" name="campsDurationId">
									  <#list durationCategoryList as t>
										<option value='${t.categoryId}' <#if (camps.campsDurationId!'')==t.categoryId> selected="selected"</#if>>${t.categoryName}</option>
									  </#list>
								</select>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地价格档位分类：</label>
                            <div class="col-lg-8">
                                <select class="form-control" name="campsPriceId" >
									  <#list priceCategoryList as t>
										<option value='${t.categoryId}' <#if (camps.campsPriceId!'')==t.categoryId> selected="selected"</#if>>${t.categoryName}</option>
									  </#list>
								</select>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地出发时间分类：</label>
                            <div class="col-lg-8">
                                <select class="form-control" name="campsDepartureId">
									  <#list depatureCategoryList as t>
										<option value='${t.categoryId}' <#if (camps.campsDepartureId!'')==t.categoryId> selected="selected"</#if>>${t.categoryName}</option>
									  </#list>
								</select>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">是否热门：</label>
                            <div class="col-lg-8">
                                <label class="radio-inline">
									<input type="radio" name="ifHot" value="0" <#if camps.ifHot== 0> checked</#if>/>否
								</label>
								<label class="radio-inline">
									<input type="radio" name="ifHot" value="1" <#if camps.ifHot== 1> checked</#if>/>是
								</label>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">是否特价：</label>
                            <div class="col-lg-8">
                                <label class="radio-inline">
									<input type="radio" name="ifPrice" value="0" <#if camps.ifPrice== 0> checked</#if>/>否
								</label>
								<label class="radio-inline">
									<input type="radio" name="ifPrice" value="1" <#if camps.ifPrice== 1> checked</#if>/>是
								</label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">报名截止时间：</label>
                            <div class="col-lg-8">
                                <input class="deadlineDateStr form-control form_datetime" name="deadlineDateStr" value="${camps.deadlineDateStr!""}"
                                        type="text" data-picker-position="top-right"/>
                                <small data-bv-validator="callback" data-bv-validator-for="startDateStr" class="help-block" style="color: #a94442;"></small>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">自定义字段：</label>
                            <div class="col-lg-8 zdy_div">
                                <#list camps.campsDetailList as t>
                                <div class="zdy_child">
                                    <a href="javascript:void(0)">+</a>
                                    <div><span>行程开始时间</span><input class="width90 detailStartDates form-control form_datetime" name="detailStartDates" type="text"  data-picker-position="top-right" value="${t.detailStartTime?date}"/></div>
                                    <div><span>行程短称</span><input class="empty_input width90 form-control" name="detailNames"  type="text"   value="${t.detailName!""}"/></div>
                                    <div><span>行程价格</span><input class="number_input width90 form-control" name="detailPrices"  type="text"   value="${(t.detailPrice?c)!""}"/></div>
                                    <div><span>行程持续天数</span><input class="number_input width90 form-control" name="durations"  type="text"   value="${(t.duration?c)!""}"/></div>
                                    <input class="form-control" type="hidden" name="detailIds"  type="text" value="${t.detailId!""}"/>
                                    <small data-bv-validator="callback" data-bv-validator-for="startDateStr" class="help-block" style="color: #a94442;"></small>
                                    <hr>
                                </div>
                                </#list>
                                <#if (camps.campsDetailList?size<5)>
                                <#list 1..(5 - camps.campsDetailList?size) as t>
                                    <div class="zdy_child" style="display:none;">
                                        <a href="javascript:void(0)">+</a>
                                        <div><span>行程开始时间</span><input class="width90 detailStartDates form-control form_datetime" name="detailStartDates" type="text"  data-picker-position="top-right" value=""/></div>
                                        <div><span>行程短称</span><input class="empty_input width90 form-control" name="detailNames"  type="text"   value=""/></div>
                                        <div><span>行程价格</span><input class="number_input width90 form-control" name="detailPrices"  type="text"   value=""/></div>
                                        <div><span>行程持续天数</span><input class="number_input width90 form-control" name="durations"  type="text"   value=""/></div>
                                        <input class="form-control" type="hidden" name="detailIds"  type="text" value=""/>
                                        <small data-bv-validator="callback" data-bv-validator-for="startDateStr" class="help-block" style="color: #a94442;"></small>
                                        <hr>
                                    </div>
                                </#list>
                                </#if>
                            </div>
                        </div>
                        <!--
						<div class="form-group">
                            <label class="col-lg-2 control-label">持续天数：</label>
                            <div class="col-lg-8">
                                <input class="form-control" name="durationTime"  value="${camps.durationTime!""}"
                                 		data-bv-notempty="true"
                                        type="text" />
                            </div>
                        </div>
                        -->
                  		<!--
                        <div class="form-group">
                            <label class="col-lg-2 control-label">产品金额：</label>
                            <div class="col-lg-8">
                                <input class="form-control" name="totalPrice"  data-bv-notempty="true" value="${(camps.totalPrice?c)!''}"
                                        type="number"/>
                            </div>
                        </div>
                        -->
                        <div class="form-group">
                            <label class="col-lg-2 control-label">显示已售数量：</label>
                            <div class="col-lg-8">
                                <input class="form-control" name="fadeDoneCount"  data-bv-notempty="true" value="${(camps.fadeDoneCount?c)!''}"
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
									group="1"
									value=(camps.campsImages!'')?split(',')/>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地介绍：</label>
                            <div class="col-lg-8">
                            	<script id="container1" name="campsDesc" type="text/plain">
                            		${camps.campsDesc!""}
								</script>
								<!-- 实例化编辑器 -->
								<script type="text/javascript">
									var ue = UE.getEditor('container1');
								</script>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地课程内容：</label>
                            <div class="col-lg-8">
                            	<script id="container2" name="courseDesc" type="text/plain">
                            		${camps.courseDesc!""}
								</script>
								<!-- 实例化编辑器 -->
								<script type="text/javascript">
									var ue = UE.getEditor('container2');
								</script>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地活动内容：</label>
                            <div class="col-lg-8">
                            	<script id="container3" name="activityDesc" type="text/plain">
                            		${camps.activityDesc!""}
								</script>
								<!-- 实例化编辑器 -->
								<script type="text/javascript">
									var ue = UE.getEditor('container3');
								</script>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地伙食描述：</label>
                            <div class="col-lg-8">
                            	<script id="container4" name="campsFoodDesc" type="text/plain">
                            		${camps.campsFoodDesc!""}
								</script>
								<!-- 实例化编辑器 -->
								<script type="text/javascript">
									var ue = UE.getEditor('container4');
								</script>
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
									group="2"
									value=(camps.campsFoodsPhotos!'')?split(',')/>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地住宿描述：</label>
                            <div class="col-lg-8">
                            	<script id="container5" name="campsHotelDesc" type="text/plain">
                            		${camps.campsHotelDesc!""}
								</script>
								<!-- 实例化编辑器 -->
								<script type="text/javascript">
									var ue = UE.getEditor('container5');
								</script>
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
									group="3"
									value=(camps.campsHotelPhotos!'')?split(',')/>
                            </div>
                        </div>
						<!--
                        <div class="form-group">
                            <label class="col-lg-2 control-label">营地行程标题<br>(用#_#分割,行程标题、描述、图片数请保持一致)：</label>
                            <div class="col-lg-8">
                               <textarea class="form-control" name="traceTitle" >${camps.traceTitle!""}</textarea>
                            </div>
                        </div>
						-->
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地行程：</label>
                            <div class="col-lg-8">
								<script id="container16" name="traceDesc" type="text/plain">
                            		${camps.traceDesc!""}
								</script>
								<!-- 实例化编辑器 -->
								<script type="text/javascript">
									var ue = UE.getEditor('container16');
								</script>
                             
                            </div>
                        </div>
						<!--
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地行程图片(行程标题、描述、图片数请保持一致)：</label>
                            <div class="col-lg-8 clearfix">
                                <@fileUpload
                                	notEmpty="false"
                                    notEmptyMsg="图片不能为空"
                                    path="campsTrace"
                                    name="tracePhotos"
                                    ext="png,jpg,jpeg,gif"
                                    maxnum="10" 
									group="4"
									value=(camps.tracePhotos!'')?split(',')/>
                            </div>
                        </div>
						-->
						<div class="form-group">
                            <label class="col-lg-2 control-label">费用详情：</label>
                            <div class="col-lg-8">
                            	<script id="container6" name="feeDesc" type="text/plain">
                            		${camps.feeDesc!""}
								</script>
								<!-- 实例化编辑器 -->
								<script type="text/javascript">
									var ue = UE.getEditor('container6');
								</script>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">常见问题：</label>
                            <div class="col-lg-8">
								<script id="container7" name="questions" type="text/plain">
									${camps.questions!""}
								</script>
                                <!-- 实例化编辑器 -->
								<script type="text/javascript">
									var ue = UE.getEditor('container7');
								</script>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-lg-offset-4 col-lg-10">
                                <button type="submit" class="btn btn-primary addSubmit">提交</button>
                            </div>
                       </div>
                    </form>
                </div>
            </div><!-- /.row-->
    </div><!-- /#wrapper -->
<@docFoot />
</#compress>