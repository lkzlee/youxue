<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "common/core.ftl">
<#-- html文档头部 -->
<@docHead/>
<#-- 正文 -->
    <div id="wrapper">
    	<#-- 页头和导航 文件地址："common/header.ftl"-->
		<@pagehead />
		<#-- 主体内容 -->
        <div id="page-wrapper">
        	
            
            <div class="row">
                <div class="col-lg-12 text-center">
                    <div class="jumbotron">
                        <h4><i class="fa fa-times" style="color:#5cb85c"></i>${errorMessage!""}</h4>
                    </div>
                </div>
            </div>
            
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

<#-- html文档尾部 -->
<@docFoot/>
</#compress>