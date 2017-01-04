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
                <h4 class="text-center">查询管理员用户</h4>
                <h5 class="text-center" style="color:red">${msg!''}</h5>
                <div class="col-lg-offset-2 col-lg-8">
                    <form class="form-horizontal" id="userInfoForm" action="/queryUserInfo.html" data-validate="true">
                        <div class="form-group">
                            <label class="col-lg-2 control-label">用户名：</label>
                            <div class="col-lg-6">
                                <input type="text" class="form-control" name="username" />
                            </div>
                            <div class="col-lg-2">
                                 <button type="submit" class="btn btn-primary">查询</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div><!-- /.row -->
            <hr />
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading text-center">
							管理员用户列表
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover" id="dataTables-example1">
									<thead>
										<tr>
											<th>序号</th>
											<th>用户类型</th>
											<th>用户名</th>
											<th>密码</th>
											<th>姓名</th>
											<th>负责小区</th>
											<th>备注</th>
											<th class="text-center" colspan=2>操作</th>
										</tr>
									</thead>
									<tbody>
										<#list userList.result![] as adItem>
										<tr class="<#if adItem_index%2 == 0>odd<#else>even</#if>">
											<td>${adItem_index + 1}</td>
											<td>${adItem.userTypeStr!""}</td>
											<td>${adItem.username!""}</td>
											<td>${adItem.password!""}</td>
											<td>${adItem.name!""}</td>
											<td>${adItem.area!""}</td>
											<td>${adItem.remark!""}</td>
											<td><a href="/modifyUserInfo.html?username=${adItem.username}"><span>修改</span></a></td>
											<td><a href="/doDeleteUserInfo.html?username=${adItem.username}" onclick="return confirm('您确认删除管理员“${adItem.username}”吗?')"><span>删除</span></a></td>
										</tr>
										</#list>
									</tbody>
								</table>
								<@paging maxRecordNum=userList.totalCount 
										totalPage=userList.totalPage 
										currentPage=userList.pageNo 
										urlTmpl="?pageNo={p}"/>
							</div>                            
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<hr>
				
				<!-- /.col-lg-12 -->
				<div class="row">
				<#if userType == 1>
                	<h4 class="text-center">添加操作员</h4>
                </#if>
                <#if userType == 0>
                	<h4 class="text-center">添加普通管理员</h4>
                </#if>
                <h5 class="text-center" style="color:red">${addMsg!''}</h5>
                <div class="col-lg-offset-2 col-lg-8">
                    <form class="form-horizontal" id="sysConfigForm" method="POST" action="/doAddUserInfo.html" data-validate="true">
                        <div class="form-group">
                            <label class="col-lg-2 control-label">用户名：</label>
                            <div class="col-lg-6">
                                <input type="text" class="form-control" name="username"
                                        data-bv-notempty="true"
                                        data-bv-notempty-message="请输入用户名"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">密码：</label>
                            <div class="col-lg-6">
                                <input class="form-control" name="password" 
                                 		data-bv-notempty="true"
                                        data-bv-notempty-message="请输入密码"
                                        type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">姓名：</label>
                            <div class="col-lg-6">
                                <input class="form-control" name="name" 
                                        data-bv-notempty-message="请输入姓名"
                                        type="text"/>
                                <input type="hidden" name="userType" value="0" />
                            </div>
                        </div>
                        <#if userType == 1>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">小区：</label>
                            <div class="col-lg-6">
                                <input class="form-control" name="area" 
                                 		data-bv-notempty="true"
                                        data-bv-notempty-message="请输入小区名"
                                        type="text"/>
                            </div>
                            <p>(多个小区用空格分隔)</p>
                        </div>
                        </#if>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">备注：</label>
                            <div class="col-lg-6">
                                <input class="form-control" name="remark" 
                                        data-bv-notempty-message="请输入备注"
                                        type="text"/>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <div class="col-lg-offset-4 col-lg-8">
                                <button type="submit" class="btn btn-primary">提交</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div><!-- /.row-->
            <hr>
		</div>
    </div><!-- /#wrapper -->
<@docFoot />
</#compress>