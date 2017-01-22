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
                <h4 class="text-center">订单管理</h4>
                <h5 class="text-center" style="color:red">${msg!''}</h5>
                <div class="col-lg-12">
                    <form class="form-horizontal" id="form" action="/admin/userorder.do" data-validate="true">
                    <table class="table table-striped table-bordered table-hover" id="dataTables-example1">
						<tr>
							<th>订单编号:</th><td><input type="text" class="form-control" name="orderId" /></td>
							<th>会员手机号:</th><td><input type="text" class="form-control" name="accountId"/></td>
						</tr>
						<tr>
							<th>营地名称:</th><td><input type="text" class="form-control" name="campsName" /></td>
							<th>订单状态:</th><td>
												<select class="form-control" name="orderType" id="orderType">
													<option value='-1'>全部订单</option>
													<#list orderStatusMap?keys as t>
													       <option value='${t}'>${orderStatusMap[t]}</option>
													</#list>
												</select>
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
							订单列表列表
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover" id="dataTables-example1">
									<thead>
										<tr>
											<th>编号</th>
											<th>订单编号</th>
											<th>营地名称</th>
											<th>营地数量</th>
											<th>付款金额</th>
											<th>参与优惠</th>
											<th>支付方式</th>
											<th>会员手机号</th>
											<th>下单时间</th>
										    <th>订单状态</th>  
											<th class="text-center" colspan=2>操作</th>
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
											<td style='background-color:${adItem.statusColor!""};'>${adItem.campsName!""}</td>
											<td>${adItem.totalCount!""}</td>
											<td>${adItem.payPrice!""}</td>
											<td>${adItem.codeName!"无"}</td>
											<td>${payTypeMap[adItem.payType?string("0")]}</td>
											<td>${adItem.accountId!""}</td>
											<td>${adItem.creatTime?string("yyyy-MM-dd HH:mm:ss")}</td>
											<td>${orderStatusMap[adItem.status?string("0")]}</td>
											<td>
											<#if adItem.status=1>
											<a href="/admin/auditOrder.do?type=pass&orderId=${adItem.orderId}"><span>审核通过(待出行)</span></a> | 
											<a href="/admin/auditOrder.do?type=fail&orderId=${adItem.orderId}"><span>拒绝(申请退款)</span></a>
											<#elseif adItem.status=2> 
											<a href="/admin/auditOrder.do?type=pass&orderId=${adItem.orderId}"><span>审核通过(已完成)</span></a> | 
											<a href="/admin/auditOrder.do?type=fail&orderId=${adItem.orderId}"><span>拒绝(申请退款)</span></a>
											<#elseif adItem.status=6>
											<a href="/admin/auditOrder.do?type=pass&orderId=${adItem.orderId}"><span>审核通过(已取消，已退款)</span></a> | 
											<a href="/admin/auditOrder.do?type=fail&orderId=${adItem.orderId}"><span>拒绝(退款失败，不通过)</span></a>
											<#elseif adItem.status=7>
											<a href="/admin/auditOrder.do?type=pass&orderId=${adItem.orderId}"><span>审核通过(申请退款)</span></a>  
											</#if>
											</td>
										</tr>
										</#list>
									</tbody>
								</table>
								
								<@paging maxRecordNum=page.totalCount 
										totalPage=page.totalPage 
										currentPage=page.pageNo 
										urlTmpl="?pageNo={p}&orderId=${orderId!''}&accountId=${accountId!''}&campsName=${campsName!''}&orderType=${orderType!''}&payType=${payType!''}"/>
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