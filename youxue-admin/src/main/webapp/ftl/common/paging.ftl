<#--服务器端分页组件(paging)---->
<#-- 参数说明
	key				type	说明			
	maxRecordNum	int		最大记录数量
	currentPage		int		当前页码
	totalPage=1		int		最大页数
	urlTmpl			string	url链接模版
							"http://xxxxxx/xxx.html?page={p}" 
							{p}为为页面占位符不可修改，page这个参数名视具体需要修改。
-->
<#-- 使用方法
	<@paging 
		maxRecordNum=5 
		totalPage=2 
		currentPage=1 
		urlTmpl="http://baidu.com?page={p}"
	/>

-->

<#macro paging maxRecordNum=0 totalPage=1 currentPage=1 urlTmpl="?page={p}">
<#if totalPage gt 0>
<div class="col-sm-6"><div class="dataTables_info"> 第<strong>${currentPage}</strong>页 共<strong>${totalPage}</strong>页 总<strong>${maxRecordNum}</strong>条</div></div>
<div class="">	
	<ul class="pagination">
		<#if currentPage == 1 >
		<li class="disabled"><a href="javascript:void(0);" aria-label="Previous"><span aria-hidden="true">上一页</span></a></li>
		<#else>
		<li><a href="${urlTmpl?replace('{p}', (currentPage-1))}" aria-label="Previous">上一页</a></li>
		</#if>
		<#list 1..totalPage as k>
		<li <#if currentPage==k>class="active"</#if>><a  href="${urlTmpl?replace('{p}', k)}">${k}</a></li>
		</#list>
		<#if currentPage == totalPage>		
		<li class="disabled"><a href="javascript:void(0);" aria-label="Next"><span aria-hidden="true">下一页</span></a></li>
		<#else>
		<li><a href="${urlTmpl?replace('{p}', (currentPage+1))}" aria-label="Next">下一页</a></li>
		</#if>
	</ul>
</div>
</#if>
</#macro>