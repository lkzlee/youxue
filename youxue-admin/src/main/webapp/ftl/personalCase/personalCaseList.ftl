<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "../common/core.ftl">
<#assign str="">
<#-- html文档头部 -->
<@docHead title="定制案例列表"/>
<script>
window.UEDITOR_HOME_URL = "/ueditor/";
</script>
<script type="text/javascript" src="../../ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="../../ueditor/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8" src="../../ueditor/lang/zh-cn/zh-cn.js"></script>
<!-- 默认样式 -->
<link type="text/css" href="./../ueditor/themes/default/css/ueditor.css" rel="stylesheet" />
<#-- 正文 -->
    <div id="wrapper">
    	<#-- 页头和导航 文件地址："../common/header.ftl"-->
		<@pagehead />
		<#-- 主体内容 -->
        <div id="page-wrapper" class="form-wrapper">
            
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading text-center">
							定制案例列表
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover" id="dataTables-example1">
									<thead>
										<tr>
											<th>营地id</th>
											<th>营地名称</th>
											<th class="text-center" colspan=2>操作</th>
										</tr>
									</thead>
									<tbody>
										<#list personalCaseList.resultList![] as adItem>
										<tr class="<#if adItem_index%2 == 0>odd<#else>even</#if>">
											<td>${adItem.caseId!""}</td>
											<td>${adItem.caseName!""}</td>
											<td><a href="/modifyPersonalCase.do?caseId=${adItem.caseId}"><span>修改</span></a></td>
											<td>
												<a href="/doDeletePersonalCase.do?caseId=${adItem.caseId}" onclick="return confirm('您确认刪除“${adItem.caseName}”吗?')"><span>刪除</span></a>
											</td>
										</tr>
										</#list>
									</tbody>
								</table>
								<@paging maxRecordNum=personalCaseList.totalCount 
										totalPage=personalCaseList.totalPage 
										currentPage=personalCaseList.pageNo 
										urlTmpl="?pageNo={p}"/>
							</div>                            
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
		</div>
		<hr>
		<div class="row">
                <h4 class="text-center">新增定制案例</h4>
                <h5 class="text-center" style="color:red">${msg!''}</h5>
                <div class="col-lg-12">
                    <form class="form-horizontal" id="form" action="/doAddPersonalCase.do" data-validate="true" method="POST">
                    <table class="table table-striped table-bordered table-hover" id="dataTables-example1">
						<div class="form-group">
                            <label class="col-lg-2 control-label">案例標題：</label>
                            <div class="col-lg-6">
                                <input type="text" class="form-control" name="caseName"
                                        data-bv-notempty="true" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">案例內容：</label>
                            <div class="col-lg-8">
								<script id="container" name="caseContent" type="text/plain">
									
								</script>
        
								<!-- 实例化编辑器 -->
								<script type="text/javascript">
									var ue = UE.getEditor('container');
								</script>
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