<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "../common/core.ftl">

<#-- html文档头部 -->
<@docHead title="后台管理系统-上传账户信息"/>
<#-- 正文 -->
    <div id="wrapper">
    	<#-- 页头和导航 文件地址："../common/header.ftl"-->
		<@pagehead />
		<#-- 主体内容 -->
				<!-- /.col-lg-12 -->
			<div class="row">
                <h3 class="text-center"><b>上传宽带账号信息</b></h3>
                <h5 class="text-center" style="color:red">${msg!''}</h5>
                <hr>
                <div class="col-lg-offset-2 col-lg-8">
                    <form class="form-horizontal" id="Form" method="POST" action="/doUploadAccountInfo.html" 
                    	enctype="multipart/form-data">
                        <div class="form-group">
                            <label class="col-lg-offset-2 col-lg-2 control-label">文件：</label>
                            <div class="col-lg-6">
						         <input type="file"  data-notempty="true" name="file"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-lg-offset-6 col-lg-8">
                                <button type="submit" class="btn btn-primary">提交</button>
                            </div>
                       </div>
                    </form>
                </div>
            </div><!-- /.row-->
    </div><!-- /#wrapper -->
<@docFoot />
</#compress>