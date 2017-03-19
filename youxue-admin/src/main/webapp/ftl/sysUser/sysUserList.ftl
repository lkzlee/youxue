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
                <h4 class="text-center">管理员管理</h4>
                <h5 class="text-center" style="color:red">${msg!''}</h5>
                <div class="col-lg-12">
                    <form class="form-horizontal" id="form" action="/sysUserList.do" data-validate="true">
                    <table class="table table-striped table-bordered table-hover" id="dataTables-example1">
						<tr>
							<th>管理员名称:</th><td><input type="text" class="form-control" name="userName" /></td>
							<th>管理员手机号:</th><td><input type="text" class="form-control" name="tel"/></td>
						 <!--	<th>创建时间:</th><td><input type="text" class="form-control" name="createTime"/></td>-->
							 <th>
										<button type="submit" class="btn btn-primary">搜索</button> &nbsp;&nbsp;&nbsp;&nbsp;
							 </th>		
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
							管理员列表
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover" id="dataTables-example1">
									<thead>
										<tr>
											<th>编号</th>
											<th>管理员名称</th>
											<th>管理员手机号</th>
											<th>管理员权限</th>
											<th>创建时间</th>
											<th class="text-center">操作</th>
										</tr>
									</thead>
									<tbody>
										<#list page.resultList![] as adItem>
										<tr class="<#if adItem_index%2 == 0>odd<#else>even</#if>">
											<td>${adItem_index + 1}</td>
											<td style=''>
												<p>
													${adItem.userName!""}
												</p>
											</td>
											<td>${adItem.tel!""}</td>
											<td>
												<#list (adItem.roleId!'')?split(',') as t>
													${roleMap[t]}&nbsp;
												</#list>
											</td>
											<td>${adItem.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
											<td>
											<#if adItem.status=0>
												<a href="javascript:void(0)" onclick="forbidOrStart(${adItem.userId},1)"><span>禁用</span></a>  
											<#elseif adItem.status=1>
												<a href="javascript:void(0)" onclick="forbidOrStart(${adItem.userId},0)"><span>恢复</span></a>
											</#if>
											<a href="javascript:void(0)" onclick="resetUser(${adItem.userId})"><span>重置密码</span></a>
											</td>
										</tr>
										</#list>
									</tbody>
								</table>
								
								<@paging maxRecordNum=page.totalCount 
										totalPage=page.totalPage 
										currentPage=page.pageNo 
										urlTmpl="?pageNo={p}&userName=${userName!''}&tel=${tel!''}"/>
							</div>                            
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				
				<hr>
				<!-- /.col-lg-12 -->
				 <div class="row">
                <h4 class="text-center">添加管理员账户</h4>
                <h5 class="text-center" style="color:red">${addMsg!''}</h5>
                <div class="col-lg-6">
                    <form class="form-horizontal" id="addForm" method="POST" action="/addSysUser.do" data-validate="true">
                    <table class="table table-striped table-bordered table-hover" id="dataTables-example1">
                    	<tr>
							<th class="text-center">管理员登录账户:</th><td><input type="text" class="form-control"  data-bv-notempty="true" name="loginName" /></td>
						</tr>
                        <tr>
							<th class="text-center">管理员名称 *:</th><td><input type="text" class="form-control"  data-bv-notempty="true" name="userName" /></td>
						</tr>
						<tr>
							<th class="text-center">管理员手机号:</th><td><input type="text" class="form-control"  data-bv-notempty="true" name="tel" /></td>
						</tr>
						<tr>
							<th class="text-center">管理员权限:</th>
							
							<td>
							<#list roleMap?keys as t>
							       <label class="radio-inline">
										<input type="checkbox" name="roleId" value="${t}" />${roleMap[t]}
									</label>
							</#list>
							</td>
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
            <hr>
		</div>
    </div><!-- /#wrapper -->
<script>
	function forbidOrStart(userId,type)
	{
		$.getJSON("/forbidOrStartSysUser.do", {userId:userId,type:type}
			, function(data){
				  alert(JSON.parse(data).resultDesc);
				});
	}
	function resetUser(userId)
	{
		$.getJSON("/resetSysUser.do", {userId:userId}
			, function(data){
				  alert(JSON.parse(data).resultDesc);
				});
	}
</script>
<@docFoot />
</#compress>