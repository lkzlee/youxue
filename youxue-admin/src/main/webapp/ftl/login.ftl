<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "common/core.ftl">
<#-- html文档头部 -->
<@docHead/>
<#-- 正文 -->
    <div id="wrapper">
    	<#-- 页头和导航 文件地址："common/header.ftl"-->
		<@pagehead />
		<#-- 主体内容 -->
        <div id="page-wrapper" class="row">
            <div class="row">
                <form action="/doLogin.do" method="post" class="form-horizontal">
                    <div class="jumbotron text-center">
                        <h2><i class="fa fa-check " style="color:#5cb85c"></i> 请用您的管理员账号登录</h2>
                    </div>
                    <div class="form-group">
                            <label class="col-lg-2 control-label">用户名：</label>
                            <div class="col-lg-6">
                                <input type="text" class="form-control" name="loginName" data-bv-notempty="true" />
                            </div>
                    </div>
                    <div class="form-group">
                            <label class="col-lg-2 control-label">密码：</label>
                            <div class="col-lg-6">
                                <input class="form-control" name="loginPwd" data-bv-notempty="true" type="password"/>
                            </div>
                     </div>
                    <div class="form-group">
                    	 <div class="col-lg-6">
							<button id="loginBtn" class="btn btn-main btn-login col-lg-6 col-lg-offset-6" tabindex="6" type="submit">登&nbsp;&nbsp;录</button>
						</div>
                    </div>
                  </form>
             </div>
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->
<#-- html文档尾部 -->
<@docFoot/>
</#compress>