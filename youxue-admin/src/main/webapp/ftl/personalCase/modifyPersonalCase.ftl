<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "../common/core.ftl">
<#setting date_format="yyyy-MM-dd">
<#-- html文档头部 -->
<@docHead title="修改定制案例"/>
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
                <h4 class="text-center">修改定制案例</h4>
                <h5 class="text-center" style="color:red">${msg!''}</h5>
                <div class="col-lg-12">
                    <form class="form-horizontal" id="form" action="/doModifyPersonalCase.do" data-validate="true">
                    <table class="table table-striped table-bordered table-hover" id="dataTables-example1">
						<div class="form-group">
                            <label class="col-lg-2 control-label">案例标题：</label>
                            <div class="col-lg-6">
                                <input type="text" class="form-control" name="caseName"
                                        data-bv-notempty="true" value="${personalCase.caseName!""}"/>
                                 <input type="hidden"  name="caseId"
                                        data-bv-notempty="true"  value="${personalCase.caseId}"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">案例內容：</label>
                            <div class="col-lg-8">
								<script id="container" name="caseContent" type="text/plain">
									${personalCase.caseContent}
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
                            <div class="col-lg-offset-4 col-lg-8">
                                <button type="submit" class="btn btn-primary">提交</button>
                            </div>
                       </div>
					</table>
                    </form>
                </div>
            </div><!-- /.row -->
				
    </div><!-- /#wrapper -->
<@docFoot />
</#compress>