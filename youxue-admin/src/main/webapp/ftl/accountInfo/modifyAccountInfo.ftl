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
                <h3 class="text-center"><b>修改宽带账号信息</b></h3>
                <h5 class="text-center" style="color:red">${msg!''}</h5>
                <hr>
                <div class="col-lg-offset-2 col-lg-8">
                    <form class="form-horizontal" id="Form" method="POST" action="/doModifyAccountInfo.html" data-validate="true">
                        <div class="form-group">
                            <label class="col-lg-offset-2 col-lg-2 control-label">账户名：</label>
                            <div class="col-lg-6">
                                <input type="text" class="form-control" name="accountId" readOnly="true"
                                        data-bv-notempty="true" value="${accountInfo.accountId!''}" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-offset-2 col-lg-2 control-label">状态：</label>
                            <div class="col-lg-6">
								<select class="form-control" name="status" id="status"  disabled="disabled">
									<#list statusMap?keys as t>
									  	 <#if accountInfo.status==t?eval >
									       <option value='${t}' selected='selected'> ${statusMap[t]}</option>
									     <#else>
									       <option value='${t}'>${statusMap[t]}</option>
									     </#if>
									</#list>
								</select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-offset-2 col-lg-2 control-label">姓名：</label>
                            <div class=" col-lg-6">
                                <input class="form-control" name="name" 
                                        type="text" value="${accountInfo.name!''}" <#if userType==2>readOnly="true"</#if>/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-offset-2 col-lg-2 control-label">小区：</label>
                            <div class=" col-lg-6">
                                <input class="form-control" name="area" 
                                        type="text" value="${accountInfo.area!''}" <#if userType==2>readOnly="true"</#if>/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-offset-2 col-lg-2 control-label">楼号：</label>
                            <div class="col-lg-6">
                                <input class="form-control" name="buildingNo" 
                                        type="text" value="${accountInfo.buildingNo!''}" <#if userType==2>readOnly="true"</#if>/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-offset-2 col-lg-2 control-label">单元号：</label>
                            <div class="col-lg-6">
                                <input class="form-control" name="unitNo" 
                                        type="text" value="${accountInfo.unitNo!''}" <#if userType==2>readOnly="true"</#if>/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-offset-2 col-lg-2 control-label">到期时间：</label>
                            <div class="col-lg-6">
                                <input class="form-control form_datetime" name="endTimeStr" id="${accountInfo.initialDate!''}"
                                        type="text" value="${accountInfo.endTimeStr!''}" data-picker-position="top-right"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-offset-2 col-lg-2 control-label">备注：</label>
                            <div class="col-lg-6">
                                <input class="form-control" name="remark" 
                                        type="text" value="${accountInfo.remark!''}" />
                            </div>
                        </div>
                        <!--
                        <div class="form-group">
                            <label class="col-lg-offset-2 col-lg-2 control-label">修改历史：</label>
                            <div class="col-lg-6">
                                <textarea class="form-control" name="changeLog" readOnly="true">${accountInfo.changeLog!''}</textarea>
                            </div>
                        </div>
                        -->
                        <div class="form-group">
                            <div class="col-lg-offset-6 col-lg-8">
                                <button type="submit" class="btn btn-primary">提交</button>
                            </div>
                       </div>
                    </form>
                </div>
            </div><!-- /.row-->
    </div><!-- /#wrapper -->
<@docFoot />
</#compress>