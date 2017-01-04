<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "../common/core.ftl">

<#-- html文档头部 -->
<@docHead title="后台管理系统-修改个人密码信息"/>
<#-- 正文 -->
    <div id="wrapper">
    	<#-- 页头和导航 文件地址："../common/header.ftl"-->
		<@pagehead />
		<#-- 主体内容 -->
        <div id="page-wrapper" class="form-wrapper">
            <div class="row">
                <h4 class="text-center">查询小区宽带账号信息</h4>
                <h5 class="text-center" style="color:red">${msg!''}</h5>
                <div class="col-lg-12">
                    <form class="form-horizontal" id="form" action="/queryAccountInfo.html" data-validate="true">
                    <table class="table table-striped table-bordered table-hover" id="dataTables-example1">
						<tr>
							<th>账户名:</th><td><input type="text" class="form-control" name="accountId" /></td>
							<th>小区:</th><td><input type="text" class="form-control" name="area"/></td>
						</tr>
						<tr>
							<th>楼号:</th><td><input type="text" class="form-control" name="buildingNo" /></td>
							<th>单元号:</th><td><input type="text" class="form-control" name="unitNo" /></td>
						</tr>
						<tr>
							<th>账号状态:</th>
							<td>
								<select class="form-control" name="status" id="status">
                                	<option value=''>--</option>
									<#list statusMap?keys as t>
									       <option value='${t}'>${statusMap[t]}</option>
									</#list>
								</select>
							</td>
							<td align=center colspan='6'>
								<p style="text-align:center">
									<button type="submit" class="btn btn-primary">查询</button> &nbsp;&nbsp;&nbsp;&nbsp;
									<a href="/queryAccountInfo.html" class="btn btn-primary"><span>查询全部</span></a>
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
							小区宽带账号列表
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover" id="dataTables-example1">
									<thead>
										<tr>
											<th>序号</th>
											<th>账号</th>
											<th>状态</th>
											<th>小区</th>
											<th>楼号</th>
											<th>单元号</th>
											<th>过期时间</th>
											<th>备注</th>
										<!--	<th>修改历史</th> -->
											<th class="text-center" colspan=2>操作</th>
										</tr>
									</thead>
									<tbody>
										<#list accountList.result![] as adItem>
										<tr class="<#if adItem_index%2 == 0>odd<#else>even</#if>">
											<td>${adItem_index + 1}</td>
											<td style=''>
												<p>
													${adItem.accountId!""}
												</p>
											</td>
											<td style='background-color:${adItem.statusColor!""};'>${adItem.statusStr!""}</td>
											<td>${adItem.area!""}</td>
											<td>${adItem.buildingNo!""}</td>
											<td>${adItem.unitNo!""}</td>
											<td>${adItem.endTimeStr!""}</td>
											<td>${adItem.remark!""}</td>
											<!-- <td>${adItem.changeLog!""}</td> -->
											<td><a href="/modifyAccountInfo.html?accountId=${adItem.accountId}"><span>修改</span></a></td>
											<#if userType == 0><td><a href="/doDeleteAccountInfo.html?accountId=${adItem.accountId}" onclick="return confirm('您确认删除账号“${adItem.accountId}”吗?')"><span>删除</span></a></td></#if>
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