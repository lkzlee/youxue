<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "../common/core.ftl">
<#setting date_format="yyyy-MM-dd hh:mm:ss">
<#-- html文档头部 -->
<@docHead title="后台管理系统-周边产品订单"/>
<#-- 正文 -->
    <div id="wrapper">
    	<#-- 页头和导航 文件地址："../common/header.ftl"-->
		<@pagehead />
		<#-- 主体内容 -->
        <div id="page-wrapper" class="form-wrapper">
            <div class="row">
                <h4 class="text-center">周边产品订单</h4>
                <h5 class="text-center" style="color:red">${msg!''}</h5>
                <div class="col-lg-12">
                    <form class="form-horizontal" id="form" action="/admin/productorder.do" data-validate="true">
                    <table class="table table-striped table-bordered table-hover" id="dataTables-example1">
						<tr>
							<th>订单编号:</th><td><input type="text" class="form-control" name="orderId" value="${orderId!''}"/></td>
							<th>会员手机号:</th><td><input type="text" class="form-control" name="accountId" value="${accountId!''}"/></td>
						</tr>
						<tr>
							<th>购买起始时间:</th><td><input class="form-control form_datetimestamp" name="startTime"
                                        type="text"  data-picker-position="top-right" value="${startTime!''}"/></td>
							<th>购买结束时间:</th><td>
												<input class="form-control form_datetimestamp" name="endTime"
                                        type="text"  data-picker-position="top-right" value="${endTime!''}"/>
											</td>
						</tr>
						<tr>
							<th>支付方式:</th>
							<td>
								<select class="form-control" name="payType" id="payType">
									<#list payTypeMap?keys as t>
									       <option value='${t}'>${payTypeMap[t]}</option>
									</#list>
								</select>
							</td>
						</tr>
						<tr>
							<td align=center colspan='6'>
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
							周边产品订单列表
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover" id="dataTables-example1">
									<thead>
										<tr>
											<th>编号</th>
											<th>订单编号</th>
											<th>购买内容</th>
											<th>服务类型</th>
											<th>支付方式</th>
											<th>会员手机号</th>
											<th>购买时间</th>
										    <th>支付金额</th>  
											<th class="text-center" colspan=2>备注</th>
										</tr>
									</thead>
									<tbody>
										<#list page.resultList![] as adItem>
										<tr class="<#if adItem_index%2 == 0>odd<#else>even</#if>">
											<td>${adItem_index + 1}</td>
											<td style=''>
												<p>
													${adItem.orderId!""}
												</p>
											</td>
											<td>${adItem.content!""}</td>
											<td>${productTypeMap[adItem.buyType?string("0")]}</td>
											<td>${payTypeMap[adItem.payType?string("0")]}</td>
											<td>${adItem.accountId!""}</td>
											<td>${adItem.buyTime?string("yyyy-MM-dd HH:mm:ss")}</td>
											<td>${adItem.price!""}</td>
											<td>${adItem.remark!""}</td>
										</tr>
										</#list>
									</tbody>
								</table>
								
								<@paging maxRecordNum=page.totalCount 
										totalPage=page.totalPage 
										currentPage=page.pageNo 
										urlTmpl="?pageNo={p}&orderId=${orderId!''}&accountId=${accountId!''}&payType=${payType!''}&startTime=${startTime!''}&endTime=${endTime!''}"/>
							</div>                            
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
					<hr>
				<!-- /.col-lg-12 -->
				 <div class="row">
                <h4 class="text-center">添加周边服务</h4>
                <h5 class="text-center" style="color:red">${addMsg!''}</h5>
                <div class="col-lg-12">
                	<form class="form-horizontal" id="addForm" method="POST" action="/admin/addproductorder.do" data-validate="true">
                    <table class="table table-striped table-bordered table-hover" id="dataTables-example1">
                        <tr><th>购买内容 :</th><td><input type="text" class="form-control" name="content" id="content" /></td></tr>
						
						<tr><th>服务类型:</th>
							<td>
								<select class="form-control" name="buyType" id="buyType">
									<#list productTypeMap?keys as t>
									       <option value='${t}'>${productTypeMap[t]}</option>
									</#list>
								</select>
							</td>
						</tr>
						<tr><th>支付方式:</th>
							<td>
								<select class="form-control" name="payType" id="payType">
											<#list payTypeMap?keys as t>
											       <option value='${t}'>${payTypeMap[t]}</option>
											</#list>
								</select>
							</td>
						</tr>
						<tr><th>会员手机号:</th>
							<td><input class="form-control" name="accountId" id="accountId" type="text"/></td>
						</tr>
						<tr><th>购买时间:</th>
							<td><input class="form-control form_datetimestamp" name="buyTimeStr"
                                        type="text"  data-picker-position="top-right"/></td>
						</tr>
						<tr><th>支付金额:</th>
							<td><input class="form-control" name="price" id="price" type="text"/></td>
						</tr>
						<tr><th>备注:</th>
							<td><input class="form-control" name="remark" id="remark" type="text"/></td>
						</tr>
                        <tr>
							<td align=center colspan='6'>
								<p style="text-align:center"><input type="submit" class="btn btn-primary" value="添加" /></p>
							</td>
						</tr>
					</table>
					</form>
                </div>
		</div>
    </div><!-- /#wrapper -->
<@docFoot />
</#compress>