<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "../common/core.ftl">
<#assign str="">
<#-- html文档头部 -->
<@docHead title="营地管理"/>
<#-- 正文 -->
    <div id="wrapper">
    	<#-- 页头和导航 文件地址："../common/header.ftl"-->
		<@pagehead />
		<#-- 主体内容 -->
        <div id="page-wrapper" class="form-wrapper">
            <div class="row">
                <h4 class="text-center">营地管理</h4>
                <h5 class="text-center" style="color:red">${msg!''}</h5>
                <div class="col-lg-12">
                    <form class="form-horizontal" id="form" action="/campsList.do" data-validate="true">
                    <table class="table table-striped table-bordered table-hover" id="dataTables-example1">
						<tr>
							<th>营地名称:</th>
							<td><input type="text" class="form-control" name="campsNameKey" value="${campsNameKey}"/></td>
							
							<th>上架状态:</th>
							<td>
								<select class="form-control" name="status" id="status">
					                  <option value=''>--</option>
									  <#list campsStatusMap?keys as t>
										<option value='${t}'  <#if status==t> selected="selected"</#if>>${campsStatusMap[t]}</option>
									  </#list>
								</select>
							</td>
						</tr>
						<tr>
							<th>目的地分类:</th>
							<td>
								<#list localeCategoryList as category>
								<#if (localeCategoryIds!'')?split(',')?seq_contains(category.categoryId) >
										<label class="checkbox-inline" >
												<input type="checkbox" name="localeCategoryIds" value="${category.categoryId}" checked 
													 data-bv-notempty-message="" />${category.categoryName}
										</label>
								<#else>
									<label class="checkbox-inline" >
										<input type="checkbox" name="localeCategoryIds" value="${category.categoryId}" />${category.categoryName}
									</label>
								</#if>
								</#list>
							</td>
							<th>主题分类:</th>
							<td>
								<#list subjectCategoryList as category>
								<#if (subjectCategoryIds!'')?split(',')?seq_contains(category.categoryId) >
										<label class="checkbox-inline" >
												<input type="checkbox" name="subjectCategoryIds" value="${category.categoryId}" checked 
													data-bv-notempty-message="" />${category.categoryName}
										</label>
								<#else>
									<label class="checkbox-inline" >
										<input type="checkbox" name="subjectCategoryIds" value="${category.categoryId}" />${category.categoryName}
									</label>
								</#if>
								</#list>
							</td>
						</tr>
						<tr>
							<th>价格档位分类:</th>
							<td>
								<#list priceCategoryList as category>
								<#if (priceCategoryIds!'')?split(',')?seq_contains(category.categoryId) >
										<label class="checkbox-inline" >
												<input type="checkbox" name="priceCategoryIds" value="${category.categoryId}" checked 
													 data-bv-notempty-message="" />${category.categoryName}
										</label>
								<#else>
									<label class="checkbox-inline" >
										<input type="checkbox" name="priceCategoryIds" value="${category.categoryId}" />${category.categoryName}
									</label>
								</#if>
								</#list>
							</td>
							<th>出发时间分类:</th>
							<td>
								<#list depatureCategoryList as category>
								<#if (depatureCategoryIds!'')?split(',')?seq_contains(category.categoryId) >
										<label class="checkbox-inline" >
												<input type="checkbox" name="depatureCategoryIds" value="${category.categoryId}" checked 
													data-bv-notempty-message="" />${category.categoryName}
										</label>
								<#else>
									<label class="checkbox-inline" >
										<input type="checkbox" name="depatureCategoryIds" value="${category.categoryId}" />${category.categoryName}
									</label>
								</#if>
								</#list>
							</td>
						</tr>
						
					
						<tr>
							<th>时间周期分类:</th>
							<td>
								<#list durationCategoryList as category>
								<#if (durationCategoryIds!'')?split(',')?seq_contains(category.categoryId) >
										<label class="checkbox-inline" >
												<input type="checkbox" name="durationCategoryIds" value="${category.categoryId}" checked 
													 data-bv-notempty-message="" />${category.categoryName}
										</label>
								<#else>
									<label class="checkbox-inline" >
										<input type="checkbox" name="durationCategoryIds" value="${category.categoryId}" />${category.categoryName}
									</label>
								</#if>
								</#list>
							</td>
							<td align=center >
								<p style="text-align:center">
									<button type="submit" class="btn btn-primary">查询</button> &nbsp;&nbsp;&nbsp;&nbsp;
								</p>
							</td>
							<td align=center >
								<p style="text-align:center">
									<button type="reset" class="btn btn-primary">重置</button> &nbsp;&nbsp;&nbsp;&nbsp;
								</p>
							</td>
							<td align=center>
								<p style="text-align:center">
									<a type="button" class="btn btn-primary" href="addCampsIndex.do">新增</a>
								</p>
							</td>
						</tr>
					</table>
                    </form>
                </div>
            </div><!-- /.row -->
            
            <hr />
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading text-center">
							营地列表
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover" id="dataTables-example1">
									<thead>
										<tr>
											<th>营地名称</th>
											<th>分组类别</th>
											<th>订单价</th>
											<th>已售数量</th>
											<th>状态</th>
											<th>标签</th>
											<th class="text-center" colspan=2>操作</th>
										</tr>
									</thead>
									<tbody>
										<#list campsList.resultList![] as adItem>
										<tr class="<#if adItem_index%2 == 0>odd<#else>even</#if>">
											<td>${adItem.campsName!""}</td>
											<td>${adItem.categoryStrs!""}</td>
											<td>${adItem.totalPrice}</td>
											<td>${adItem.doneCount}</td>
											<td>${campsStatusMap[str+adItem.status]}</td>
											<td>${adItem.hotOrPrice!""}</td>
											<td><a href="/modifyCampsIndex.do?campsId=${adItem.campsId}"><span>修改</span></a></td>
											<td>
												<#if adItem.status == 0>
													<a href="/upOrDownCamps.do?campsId=${adItem.campsId}&status=1" onclick="return confirm('您确认上架营地“${adItem.campsId}”吗?')"><span>上架</span></a>
												<#else>
													<a href="/upOrDownCamps.do?campsId=${adItem.campsId}&status=0" onclick="return confirm('您确认下架营地“${adItem.campsId}”吗?')"><span>下架</span></a>
												</#if>
											</td>
										</tr>
										</#list>
									</tbody>
								</table>
								<@paging maxRecordNum=campsList.totalCount 
										totalPage=campsList.totalPage 
										currentPage=campsList.pageNo 
										urlTmpl="?pageNo={p}&localeCategoryIds=${localeCategoryIds}&subjectCategoryIds=${subjectCategoryIds}&durationCategoryIds=${durationCategoryIds}&depatureCategoryIds=${depatureCategoryIds}&priceCategoryIds=${priceCategoryIds}&status=${status}&campsNameKey=${campsNameKey}"/>
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