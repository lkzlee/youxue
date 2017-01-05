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
				<!-- /.col-lg-12 -->
			<div class="row">
                <h4 class="text-center">修改管理员信息</h4>
                <h5 class="text-center" style="color:red">${msg!''}</h5>
                <div class="col-lg-offset-2 col-lg-8">
                    <form class="form-horizontal" id="Form" method="POST" action="/doModifyUserInfo.html" data-validate="true">
                        <div class="form-group">
                            <label class="col-lg-2 control-label">用户名：</label>
                            <div class="col-lg-6">
                                <input type="text" class="form-control" name="username" readOnly="true"
                                        data-bv-notempty="true" value="${username!''}" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">密码：</label>
                            <div class="col-lg-6">
                                <input class="form-control" name="password" 
                                 		data-bv-notempty="true"
                                        type="text" value="${password!''}" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">姓名：</label>
                            <div class="col-lg-6">
                                <input class="form-control" name="name" 
                                        type="text" value="${name!''}"/>
                            </div>
                        </div>
                        <#if area??>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">小区：</label>
                            <div class="col-lg-6">
                                <input class="form-control" name="area" 
                                 		data-bv-notempty="true"
                                        type="text" value="${area!''}"/>
                            </div>
                            (多个小区用空格分隔)
                        </div>
                        </#if>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">备注：</label>
                            <div class="col-lg-6">
                                <input class="form-control" name="remark" 
                                        type="text" value="${remark!''}"/>
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
    </div><!-- /#wrapper -->
<@docFoot />
</#compress>