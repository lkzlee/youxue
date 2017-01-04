<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "common/core.ftl">

<#-- html文档头部 -->
<@docHead title="后台管理系统-修改个人密码信息"/>
<#-- 正文 -->
    <div id="wrapper">
    	<#-- 页头和导航 文件地址："common/header.ftl"-->
		<@pagehead />
		<#-- 主体内容 -->
        <div id="page-wrapper" class="form-wrapper">
            <hr>
            <div class="row">
                <h4 class="text-center">修改个人信息</h4>
                <div class="col-lg-offset-2 col-lg-8">
                    <form class="form-horizontal" id="form" method="POST"
                        action="/changePassword.html" data-validate="true">
                        <div class="form-group">
                            <label class="col-lg-2 control-label">用户名：</label>
                            <div class="col-lg-6">
                                <input type="text" class="form-control" name="username"  disabled="true"
                                        data-bv-notempty="true"  value="${username}" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">当前密码：</label>
                            <div class="col-lg-6">
                                <input class="form-control" name="password" 
                                 		data-bv-notempty="true" type="password"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">修改密码：</label>
                            <div class="col-lg-6">
                                <input class="form-control" name="changedPassword" 
                                 		data-bv-notempty="true" type="password"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">确认密码：</label>
                            <div class="col-lg-6">
                                <input class="form-control" name="confirmPassword" 
                                 		data-bv-notempty="true" type="password"/>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <div class="col-lg-offset-4 col-lg-8">
                                <button type="submit" class="btn btn-primary">修改密码</button>
                            </div>
                        </div>
                    </form>
                    <hr/>
                    <span><#if msg??>${msg}</#if></span>
                </div>
            </div><!-- /.row-->
            <hr>
        </div>
    </div><!-- /#wrapper -->
<@docFoot />
</#compress>