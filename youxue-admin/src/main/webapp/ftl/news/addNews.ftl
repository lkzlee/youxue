<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "../common/core.ftl">
<#setting date_format="yyyy-MM-dd">
<#-- html文档头部 -->
<script src="../../jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.js"></script>
<script src="../../jquery-ui-1.10.4/js/jquery.ui.datepicker-zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="../../kindeditor/themes/default/default.css" />
<script type="text/javascript" src="../../kindeditor/kindeditor-all.js"></script>
<%--ue编辑器--%>
<script type="text/javascript" charset="utf-8" src="../../ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="../../ueditor/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8" src="../../common/webutils.js"></script>
<script type="text/javascript" src="../../common/article.js"></script>
<script type="text/javascript">
	$(function() {
		//实例化编辑器 UE编辑器
		initUEEditor("content",'580','400');
		//initKindEditor_addblog('content', 580, 400, 'articleContent', 'true');
		initSimpleImageUpload('imageFile', 'article', callback);
		
		$("#publishTime").datetimepicker({
			regional:"zh-CN",
	        changeMonth: true,
	        dateFormat:"yy-mm-dd",
	        timeFormat: "HH:mm:ss"
	    });
	});
</script>
<@docHead title="新增营地"/>
<#-- 正文 -->
    <div id="wrapper">
    	<#-- 页头和导航 文件地址："../common/header.ftl"-->
		<@pagehead />
		<#-- 主体内容 -->
				<!-- /.col-lg-12 -->
			<div class="row">
                <h4 class="text-center">新增营地</h4>
                <h5 class="text-center" style="color:red">${msg!''}</h5>
                <div class="col-lg-offset-2 col-lg-8">
                    <form class="form-horizontal" id="Form" method="POST" action="doAddNews.do" data-validate="true">
                        <div class="form-group">
                            <label class="col-lg-2 control-label">标题：</label>
                            <div class="col-lg-6">
                                <input type="text" class="form-control" name="newsTitle"
                                        data-bv-notempty="true" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">作者：</label>
                            <div class="col-lg-6">
                                <input class="form-control" name="orientedPeople" 
                                 		data-bv-notempty="true"
                                        type="text" />
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">文章内容：</label>
                            <div class="col-lg-6">
                              <textarea name="newsContent" id="content" data-rule="required;" style="background-image: none;margin-top:26px;"></textarea>
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