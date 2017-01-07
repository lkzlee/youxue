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
                <h4 class="text-center">账户修改</h4>
                <h5 class="text-center" style="color:red">${msg!''}</h5>
                <div class="col-lg-8">
                    <form class="form-horizontal" id="form" action="/updateSysUser.do" data-validate="true">
                    <table class="table table-striped table-bordered table-hover" id="dataTables-example1">
						<tr>
							<th>账户:</th><td>
							<input type="hidden" class="form-control" name="userId" readonly="true" value="${sysUser.userId}"/>
							<input type="text" class="form-control" readonly="true" name="loginName" value="${sysUser.loginName}"/></td>
						</tr>
						<tr>	
							<th>原密码:</th><td><input type="password" class="form-control" name="loginPwd"/></td>
						</tr>
						<tr> 
						    <th>新密码:</th><td><input type="password" class="form-control" name="newPassword"/></td>
						</tr>
						<tr>
							<th>
							<button type="submit" class="btn btn-primary">修改</button> &nbsp;&nbsp;&nbsp;&nbsp;
							</th>		
						</tr>
					</table>
                    </form>
                </div>
            </div><!-- /.row -->
            
		</div>
    </div><!-- /#wrapper -->
<@docFoot />
</#compress>