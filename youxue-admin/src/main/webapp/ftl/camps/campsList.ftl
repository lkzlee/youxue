<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "../common/core.ftl">

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
							<td><input type="text" class="form-control" name="campsNameKey" /></td>
							
							<th>上架状态:</th>
							<td>
								<select class="form-control" name="status" id="status">
					                  <option value=''>--</option>
									  <#list campsStatusMap?keys as t>
										<option value='${t}'  <#if status==t?eval> selected="selected"</#if>>${campsStatusMap[t]}</option>
									  </#list>
								</select>
							</td>
						</tr>
						<tr>
							<th>营地分组:</th>
							<td>
								<select class="form-control" name="categoryId" id="categoryId">
					                  <option value=''>--</option>
					                   <#list categoryList![] as t>
										<option value='${t.categoryId}' <#if categoryId==t.categoryId> selected="selected"</#if>>${t.categoryName}</option>
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
											<td>${adItem.statusStr!""}</td>
											<td>${adItem.totalPrice}</td>
											<td>${adItem.doneCount}</td>
											<td>${campsStatusMap[adItem.status]}</td>
											<td>${adItem.hotOrPrice!""}</td>
											<td><a href="/modifyAccountInfo.html?accountId=${adItem.accountId}"><span>修改</span></a></td>
											<#if userType == 0><td><a href="/doDeleteAccountInfo.html?accountId=${adItem.accountId}" onclick="return confirm('您确认删除账号“${adItem.accountId}”吗?')"><span>下架</span></a></td></#if>
										</tr>
										</#list>
									</tbody>
								</table>
								<@paging maxRecordNum=accountList.totalCount 
										totalPage=accountList.totalPage 
										currentPage=accountList.pageNo 
										urlTmpl="?pageNo={p}&accountId=${accountId}&area=${area}&buildingNo=${buildingNo}&status=${status}&unitNo=${unitNo}"/>
							</div>                            
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<hr>
				<!-- /.col-lg-12 -->
				<#if userType != 2>
				 <div class="row">
                <h4 class="text-center">添加宽带账户</h4>
                <h5 class="text-center" style="color:red">${addMsg!''}</h5>
                <div class="col-lg-12">
                    <form class="form-horizontal" id="addForm" method="POST" action="/doAddAccountInfo.html" data-validate="true">
                    <table class="table table-striped table-bordered table-hover" id="dataTables-example1">
                        <tr>
							<th>账户名 *:</th><td><input type="text" class="form-control" name="accountId" /></td>
							<th>小区:</th><td><input type="text" class="form-control" name="area" /></td>
							<th>楼号:</th><td><input type="text" class="form-control" name="buildingNo" /></td>
						</tr>
						<tr>
							<th>单元号:</th><td><input type="text" class="form-control" name="unitNo" /></td>
							<th>使用人姓名(可不填):</th><td><input type="text" class="form-control" name="name" /></td>
							<th>备注:</th><td><input type="text" class="form-control" name="remark" /></td>
						</tr>
                        <tr>
							<td align=center colspan='6'>
								<p style="text-align:center"><button type="submit" class="btn btn-primary">添加</button></p>
							</td>
						</tr>
					</table>
                    </form>
                </div>
            </div><!-- /.row-->
            </#if>
            <hr>
		</div>
    </div><!-- /#wrapper -->
<@docFoot />
</#compress>