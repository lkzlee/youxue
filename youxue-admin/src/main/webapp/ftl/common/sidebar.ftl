<#compress>
<#-- 使用 -->
<div class="navbar-default sidebar" role="navigation">
	<div class="sidebar-nav navbar-collapse">			
		<@creatMemu menuList = menuList![]/>
	</div>		
</div>

<#-- 通过循环生成列表 -->
<#-- 功能： 用于生成多级导航， -->
<#-- 后台参数说明
	参数		类型		
	menuList	list(MenuList) 
	 
	
	MenuList
	参数		类型			说明		必有	备注
	powerName		string			导航名字	是		
	powerUrl			string			导航链接	否		当有下级列表时链接为空	
	sonList		list(MenuList)	下级列表	否		可为空或者空list
	icon		string			导航图标	否		具体图标可以在/ftl/demo/font-icon.ftl中挑选
-->
<#-- 其他参数说明
depth	int		标记导航深度，用于设置相应深度的class和id
-->

<#-- 使用方法 -->
<#macro creatMemu menuList = [] depth = 1>
<#if menuList?size lte 0><#return></#if>
<#-- 第一层加上 id="side-menu" -->
<ul class="nav ${navClass(depth)}" <#if depth == 1>id="side-menu"</#if>>
	<#list menuList![] as temp>
	<li>
		<a href="${temp.powerUrl!'#'}">
			<#if depth lt 3 ><i class="fa ${temp.icon!'fa-angle-double-right'} fa-fw"></i></#if>
			${temp.powerName!""}
			<#if (temp.sonList![])?size gt 0><span class="fa arrow"></span></#if>
		</a>
		<@creatMemu menuList = temp.sonList![] depth = depth+1/>
	</li>
	</#list>
</ul>
</#macro> 

<#-- 根据导航深度设置class -->
<#-- 设置每层ul的class	
	第二层 class 中增加 nav-second-level
	第三层 class 中增加 nav-third-level
 -->
<#function navClass depth>
	<#if depth gt 1>
	<#return depth?string?replace('2','nav-second-level')?replace('3','nav-third-level')>
	<#else>
	<#return "">
	</#if>
</#function>
</#compress>
