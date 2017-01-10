<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "common/core.ftl">
<#-- html文档头部 -->
<@docHead/>
<#-- 正文 -->
	<div id="wrapper">
		<#-- 页头和导航 文件地址："common/header.ftl" -->
		<#-- 关闭左侧导航 -->
		<#assign siderbar = false />
		<@pagehead/>
		
		<#-- 主体内容 -->
		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row col-lg-12" style=" padding-top:20px">
				<div class="panel panel-danger ">
					<div class="panel-heading">页面访问错误</div>
					<div class="panel-body">
						${errorMessage!""}
					</div>
				</div>
			</div>
			<!-- /.row -->
			<div class="row">
			  
			</div>
			
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->

<#-- html文档尾部 -->
<@docFoot/>
</#compress>