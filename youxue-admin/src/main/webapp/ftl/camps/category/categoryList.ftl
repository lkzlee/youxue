<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "../../common/core.ftl">

<#-- html文档头部 -->
<@docHead title="后台管理系统-修改个人密码信息"/>
<#-- 正文 -->
    <div id="wrapper">
    	<#-- 页头和导航 文件地址："../../common/header.ftl"-->
		<@pagehead />
		<#-- 主体内容 -->
        <div id="page-wrapper" class="form-wrapper">
            <div class="row">
                <h4 class="text-center">营地分组</h4>
                <h5 class="text-center" style="color:red">${msg!''}</h5>
                <div class="col-lg-12">
                    <form class="form-horizontal" id="form" action="/campsCategoryListIndex.do" data-validate="true">
                    <table class="table table-striped table-bordered table-hover" id="dataTables-example1">
						<tr>
							<th>分类类型:</th>
							<td>
								<select class="form-control" name="categoryType" id="categoryType">
                                	<option value=''>--</option>
									<#list categoryTypeMap?keys as t>
									       <option value='${t}' <#if categoryType==t?eval> selected="selected"</#if> > ${categoryTypeMap[t]}</option>
									</#list>
								</select>
							</td>
							<td align=center colspan='6'>
								<p style="text-align:center">
									<button type="submit" class="btn btn-primary">查询</button> &nbsp;&nbsp;&nbsp;&nbsp;
								</p>
							</td>
							<td align=center colspan='6'>
								<p style="text-align:center">
									<a type="button" class="btn btn-primary" href="/addCategoryIndex.do">新增</a> &nbsp;&nbsp;&nbsp;&nbsp;
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
							分类列表
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover" id="dataTables-example1">
									<thead>
										<tr>
											<th>分类id</th>
											<th>分类名称</th>
											<th>分类图片地址</th>
											<th>分类权重</th>
											<th class="text-center" colspan=2>操作</th>
										</tr>
									</thead>
									<tbody>
										<#list categoryList![] as adItem>
										<tr class="<#if adItem_index%2 == 0>odd<#else>even</#if>">
											<td>${adItem.categoryId!""}</td>
											<td>${adItem.categoryName!""}</td>
											<td>${adItem.categoryUrl!""}</td>
											<td>${adItem.categoryWeight}</td>
											<td><a href="/modifyCategoryIndex.do?categoryId=${adItem.categoryId}"><span>修改</span></a></td>
											<td><a href="javascript:void(0)" onclick="deleteCate('${adItem.categoryId}')" ><span>删除</span></a></td>
										</tr>
										</#list>
									</tbody>
								</table>
							</div>                            
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<hr>
            <hr>
		</div>
    </div><!-- /#wrapper -->
    
 <script>
    function deleteCate(categoryId)
    {
    	if(confirm('您确认删除分类'+categoryId+'吗?'))
    	{
    		$.getJSON("/doDeleteCategory.do", 
						{categoryId:categoryId}, 
						function(data){
					  	alert(JSON.parse(data).resultDesc);
					  	 location.reload();
					});
    	}
    }
	</script>
<@docFoot />
</#compress>