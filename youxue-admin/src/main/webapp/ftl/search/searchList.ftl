<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "../common/core.ftl">
<#assign str="">
<#-- html文档头部 -->
<@docHead title="搜索管理"/>
<#-- 正文 -->
    <div id="wrapper">
    	<#-- 页头和导航 文件地址："../common/header.ftl"-->
		<@pagehead />
		<#-- 主体内容 -->
        <div id="page-wrapper" class="form-wrapper">
            
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading text-center">
							搜索列表
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover" id="dataTables-example1">
									<thead>
										<tr>
											<th>搜索词条</th>
											<th>搜索次数</th>
											<th>最后一次搜索时间</th>
										</tr>
									</thead>
									<tbody>
										<#list searchList.resultList![] as adItem>
										<tr class="<#if adItem_index%2 == 0>odd<#else>even</#if>">
											<td>${adItem.word!""}</td>
											<td>${adItem.count}</td>
											<td>${adItem.lastSearchTime?datetime}</td>
										</tr>
										</#list>
									</tbody>
								</table>
								<@paging maxRecordNum=searchList.totalCount 
										totalPage=searchList.totalPage 
										currentPage=searchList.pageNo 
										urlTmpl="?pageNo={p}"/>
							</div>                            
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
		</div>
    </div><!-- /#wrapper -->
<@docFoot />
</#compress>