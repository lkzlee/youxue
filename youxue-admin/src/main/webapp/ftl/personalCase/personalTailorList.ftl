<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "../common/core.ftl">
<#assign str="">
<#-- html文档头部 -->
<@docHead title="私人订制列表"/>
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
							私人订制列表
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover" id="dataTables-example1">
									<thead>
										<tr>
											<th>姓名</th>
											<th>联系电话</th>
											<th>邮箱</th>
											<th>期望目的地</th>
											<th>期望出行日期</th>
											<th>需求描述</th>
											<th class="text-center">添加备注</th>
										</tr>
									</thead>
									<tbody>
										<#list personalTailorList.resultList![] as adItem>
										<tr class="<#if adItem_index%2 == 0>odd<#else>even</#if>">
											<td>${adItem.name!""}</td>
											<td>${adItem.phone!""}</td>
											<td>${adItem.email!""}</td>
											<td>${adItem.destination!""}</td>
											<td>${adItem.departureTime?date}</td>
											<td>${adItem.descs!""}</td>
											<td><a href="/modifyPersonalTailor.do?id=${adItem.id}"><span>编辑</span></a></td>
										</tr>
										</#list>
									</tbody>
								</table>
								<@paging maxRecordNum=personalTailorList.totalCount 
										totalPage=personalTailorList.totalPage 
										currentPage=personalTailorList.pageNo 
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