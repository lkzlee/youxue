 <!-- Navigation -->
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
			<span class="sr-only">Toggle navigation</span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
		</button>
		<a class="navbar-brand" href="/index.do">Camplink后台管理系统管理系统</a>
	</div>

	<ul class="nav navbar-top-links navbar-right">
		<li class="dropdown">
			<a href="#" class="dropdown-toggle" data-toggle="dropdown" >
				<i class="fa fa-user fa-fw"></i> ${currentAdminUserName!""}  <i class="fa fa-caret-down"></i>
			</a>
			<ul class="dropdown-menu">
				<li>
					<a class="text-center" href="/outlogin.do">退出登录</a>
				</li>						
			</ul>
		</li>
	</ul>
	<#if siderbar >
		<#include "sidebar.ftl">
	</#if>
	
</nav>