<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "../common/core.ftl">
<#setting date_format="yyyy-MM-dd">
<#-- html文档头部 -->
<@docHead title="修改定制案例"/>
<#-- 正文 -->
    <div id="wrapper">
    	<#-- 页头和导航 文件地址："../common/header.ftl"-->
		<@pagehead />
		<#-- 主体内容 -->
				<!-- /.col-lg-12 -->
				<div class="row">
                <h4 class="text-center">添加私人订制备注</h4>
                <h5 class="text-center" style="color:red">${msg!''}</h5>
                <div class="col-lg-12">
                    <form class="form-horizontal" id="form" action="/doModifyPersonalTailor.do" data-validate="true">
                    <table class="table table-striped table-bordered table-hover" id="dataTables-example1">
                        <div class="form-group">
                            <label class="col-lg-2 control-label">案例內容：</label>
                            <div class="col-lg-6">
                             <input type="hidden"  name="id"
                                        data-bv-notempty="true"  value="${personalTailor.id}"/>
                             <textarea class="form-control" name="remark" >${personalTailor.remark!""}</textarea>
                            </div>
                        </div>
                         <div class="form-group">
                            <div class="col-lg-offset-4 col-lg-8">
                                <button type="submit" class="btn btn-primary">提交</button>
                            </div>
                       </div>
					</table>
                    </form>
                </div>
            </div><!-- /.row -->
				
    </div><!-- /#wrapper -->
<@docFoot />
</#compress>