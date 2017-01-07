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
                <h4 class="text-center">会员管理</h4>
                <h5 class="text-center" style="color:red">${msg!''}</h5>
                <div class="col-lg-12">
                    <form class="form-horizontal" id="form" action="/admin/userList.do" data-validate="true">
                    <table class="table table-striped table-bordered table-hover" id="dataTables-example1">
						<tr>
							
							<th>用户账户（手机号）:</th><td><input type="text" class="form-control" name="accountId" value="${accountId!''}"/></td>
							<th>用户昵称:</th><td><input type="text" class="form-control" name="nickName" value="${nickName!''}"/></td>
							<td align=center colspan='2'>
								<p style="text-align:center">
									<button type="submit" class="btn btn-primary">搜索</button> &nbsp;&nbsp;&nbsp;&nbsp;
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
							会员列表
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover" id="dataTables-example1">
									<thead>
										<tr>
											<th>编号</th>
											<th>用户账户（手机号）</th>
											<th>用户昵称</th>
											<th>消费总计</th>
											<th>会员积分</th>
											<th>喜爱城市</th>
											<th>注册时间</th>
										</tr>
									</thead>
									<tbody>
										<#list page.resultList![] as adItem>
										<tr class="<#if adItem_index%2 == 0>odd<#else>even</#if>">
											<td>${adItem_index + 1}</td>
											<td style=''>
												<p>
													${adItem.accountId!""}
												</p>
											</td>
											<td>${adItem.nickName!""}</td>
											<td>0</td>
											<td>${adItem.credit!""}</td>
											<td>${adItem.loveCity!"无"}</td>
											<td>${adItem.creatTime?string("yyyy-MM-dd HH:mm:ss")}</td>
										</tr>
										</#list>
									</tbody>
								</table>
								
								<@paging maxRecordNum=page.totalCount 
										totalPage=page.totalPage 
										currentPage=page.pageNo 
										urlTmpl="?pageNo={p}&accountId=${accountId!''}&nickName=${nickName!''}"/>
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