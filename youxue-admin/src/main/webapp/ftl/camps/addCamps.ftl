<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "../common/core.ftl">
<#setting date_format="yyyy-MM-dd">
<#-- html文档头部 -->
<@docHead title="新增营地"/>
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
                <h4 class="text-center">新增营地</h4>
                <h5 class="text-center" style="color:red">${msg!''}</h5>
                <div class="col-lg-offset-2 col-lg-10">
                    <form class="form-horizontal" id="Form" method="POST" action="doAddCamps.do" data-validate="true">
                        <div class="form-group">
                            <label class="col-lg-2 control-label">营地名称：</label>
                            <div class="col-lg-8">
                                <input type="text" class="form-control" name="campsName"
                                        data-bv-notempty="true" />
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地标题：</label>
                            <div class="col-lg-8">
                                <input type="text" class="form-control" name="campsTitle"
                                        data-bv-notempty="true" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">面向对象：</label>
                            <div class="col-lg-8">
                                <input class="form-control" name="orientedPeople" 
                                 		data-bv-notempty="true"
                                        type="text" />
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">上架状态：</label>
							<div class="col-lg-8">
						      <select class="form-control" name="status" >
									  <#list campsStatusMap?keys as t>
										<option value='${t}'>${campsStatusMap[t]}</option>
									  </#list>
								</select>
							</div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地目的地分类：</label>
                            <div class="col-lg-8">
                                <select class="form-control" name="campsLocaleId">
									  <#list localeCategoryList as t>
										<option value='${t.categoryId}'>${t.categoryName}</option>
									  </#list>
								</select>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地主题分类：</label>
                            <div class="col-lg-8">
                                <select class="form-control" name="campsSubjectId">
									  <#list subjectCategoryList as t>
										<option value='${t.categoryId}'>${t.categoryName}</option>
									  </#list>
								</select>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地时间周期分类：</label>
                            <div class="col-lg-8">
                                <select class="form-control" name="campsDurationId">
									  <#list durationCategoryList as t>
										<option value='${t.categoryId}'>${t.categoryName}</option>
									  </#list>
								</select>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地价格档位分类：</label>
                            <div class="col-lg-8">
                                <select class="form-control" name="campsPriceId" >
									  <#list priceCategoryList as t>
										<option value='${t.categoryId}'>${t.categoryName}</option>
									  </#list>
								</select>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地出发时间分类：</label>
                            <div class="col-lg-8">
                                <select class="form-control" name="campsDepartureId">
									  <#list depatureCategoryList as t>
										<option value='${t.categoryId}'>${t.categoryName}</option>
									  </#list>
								</select>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">是否热门：</label>
                            <div class="col-lg-8">
                                <label class="radio-inline">
									<input type="radio" name="ifHot" value="0"/>否
								</label>
								<label class="radio-inline">
									<input type="radio" name="ifHot" value="1"/>是
								</label>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">是否特价：</label>
                            <div class="col-lg-8">
                                <label class="radio-inline">
									<input type="radio" name="ifPrice" value="0"/>否
								</label>
								<label class="radio-inline">
									<input type="radio" name="ifPrice" value="1"/>是
								</label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">自定义字段：</label>
                            <div class="col-lg-8">
                            	<a href="javascript:void(0)">+</a>
                                <div>
                                    	行程开始时间<input class="detailStartDates form-control form_datetime" name="detailStartDates"
                                        type="text"  data-picker-position="top-right" data-bv-notempty="true" style="display:inline-block;width:90%"/>
                                    	<br>	
                                    	行程短称<input class="form-control" name="detailNames"  type="text"   data-bv-notempty="true"/>
                                    	行程价格<input class="form-control" name="detailPrices"  type="text"   data-bv-notempty="true"/>
                                    	行程持续天数<input class="form-control" name="durations"  type="text"   data-bv-notempty="true"/>
                                    	<hr>
                                </div>
                            </div>
                        </div>
                        <!--
						<div class="form-group">
                            <label class="col-lg-2 control-label">持续天数：</label>
                            <div class="col-lg-8">
                                <input class="form-control" name="durationTime" 
                                 		data-bv-notempty="true"
                                        type="text" />
                            </div>
                        </div>
                        -->
						<div class="form-group">
                            <label class="col-lg-2 control-label">报名截止时间：</label>
                            <div class="col-lg-8">
                                <input class="form-control form_datetime" name="deadlineDateStr"
                                        type="text"  data-picker-position="top-right" data-bv-notempty="true"/>
                            </div>
                        </div>
                  		
                  		<!--
                        <div class="form-group">
                            <label class="col-lg-2 control-label">产品金额：</label>
                            <div class="col-lg-8">
                                <input class="form-control" name="totalPrice"  data-bv-notempty="true"
                                        type="number"/>
                            </div>
                        </div>
                        -->
                        
						<div class="form-group">
                            <label class="col-lg-2 control-label">产品特色：</label>
                            <div class="col-lg-8">
                                <input class="form-control" name="feature"
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
                                    maxnum="10" />
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地介绍：</label>
                            <div class="col-lg-8">
                            	<script id="container1" name="campsDesc" type="text/plain">
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
                                    maxnum="10" />
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地住宿描述：</label>
                            <div class="col-lg-8">
                              <script id="container5" name="campsHotelDesc" type="text/plain">
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
                                    maxnum="10" />
                            </div>
                        </div>
						<!--
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地行程标题<br>(用#_#分割,行程标题、描述、图片数请保持一致)：</label>
                            <div class="col-lg-8">
                                <textarea class="form-control" name="traceTitle" ></textarea>
                            </div>
                        </div> 
						-->
                        <div class="form-group">
                            <label class="col-lg-2 control-label">营地行程：</label>
                            <div class="col-lg-8">
								<script id="container16" name="traceDesc" type="text/plain">
								</script>
								<!-- 实例化编辑器 -->
								<script type="text/javascript">
									var ue = UE.getEditor('container16');
								</script>
                            </div>
                        </div>
						<!--
						<div class="form-group">
                            <label class="col-lg-2 control-label">营地行程图片<br>(行程标题、描述、图片数请保持一致)：</label>
                            <div class="col-lg-8 clearfix">
                                <@fileUpload
                                	notEmpty="false"
                                    notEmptyMsg="图片不能为空"
                                    path="campsTrace"
                                    name="tracePhotos"
                                    ext="png,jpg,jpeg,gif"
                                    maxnum="10" />
                            </div>
                        </div>
						-->
						<div class="form-group">
                            <label class="col-lg-2 control-label">费用详情：</label>
                            <div class="col-lg-8">
                              	<script id="container6" name="feeDesc" type="text/plain">
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
								<script id="container" name="questions" type="text/plain">
								</script>
        
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