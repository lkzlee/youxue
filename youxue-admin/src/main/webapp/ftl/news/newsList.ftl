<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "../common/core.ftl">
<#setting date_format="yyyy-MM-dd">
<#assign str="">
<#-- html文档头部 -->
<@docHead title="营地管理"/>
<#-- 正文 -->
    <div id="wrapper">
    	<#-- 页头和导航 文件地址："../common/header.ftl"-->
		<@pagehead />
		<#-- 主体内容 -->
        <div id="page-wrapper" class="form-wrapper">
            <button type="button" class="btn btn-primary" onclick="modify()">新增</button>
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading text-center">
							文章列表
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover" id="dataTables-example1">
									<thead>
										<tr>
											<th>id</th>
											<th>标题</th>
											<th>时间</th>
											<th>作者</th>
											<th class="text-center" colspan=2>操作</th>
										</tr>
									</thead>
									
									<tbody>
										<#list newsList.resultList![] as adItem>
										<tr class="<#if adItem_index%2 == 0>odd<#else>even</#if>">
											<td>${adItem.newsId!""}</td>
											<td>${adItem.newsTitle!""}</td>
											<td>${adItem.createTime?date}</td>
											<td>${adItem.author!""}</td>
											<td><a href="/modifyNews.do?newsId=${adItem.newsId}"><span>修改</span></a></td>
											<td>
												<a href="/doDeleteNews.do?newsId=${adItem.newsId}" onclick="return confirm('您确认删除“${adItem.newsId}”吗?')"><span>删除</span></a>
											</td>
										</tr>
										</#list>
									</tbody>
								</table>
								<@paging maxRecordNum=newsList.totalCount 
										totalPage=newsList.totalPage 
										currentPage=newsList.pageNo 
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