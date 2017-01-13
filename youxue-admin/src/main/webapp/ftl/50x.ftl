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
		<#assign siderbar = false />
		<#-- 主体内容 -->
		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row col-lg-12" style=" padding-top:20px">
				<div class="panel panel-danger ">
					<div class="panel-heading">系统错误</div>
					<div class="panel-body">
						<#-- 段落示例 -->
						<p></p>
						<p>页面出错的小概率事件出现了...</p>
						<p></p>
						<p></p>
						<p></p>
						<p></p>
						<#-- 列表示例 -->
						<ul>
							<li>错误信息：${err!''}</li>
							 <li>建议您联系技术人员</li> 
						</ul>

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