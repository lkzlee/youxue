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
                <h4 class="text-center">新增资讯</h4>
                <h5 class="text-center" style="color:red">${msg!''}</h5>
                <div class="col-lg-offset-2 col-lg-8">
                    <form class="form-horizontal" id="Form" method="POST" action="doAddNews.do" data-validate="true">
                        <div class="form-group">
                            <label class="col-lg-2 control-label">标题：</label>
                            <div class="col-lg-8">
                                <input type="text" class="form-control" name="newsTitle"
                                        data-bv-notempty="true" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">作者：</label>
                            <div class="col-lg-8">
                                <input class="form-control" name="author" 
                                 		data-bv-notempty="true"
                                        type="text" />
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-lg-2 control-label">文章内容：</label>
                            <div class="col-lg-8">
								<script id="container" name="newsContent" type="text/plain">
									
								</script>
        
								<!-- 实例化编辑器 -->
								<script type="text/javascript">
									var ue = UE.getEditor('container');
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