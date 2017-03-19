<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "../common/core.ftl">

<#-- html文档头部 -->
<@docHead title="后台管理系统-订单详情"/>
<#-- 正文 -->
    <div id="wrapper">
    	<#-- 页头和导航 文件地址："../common/header.ftl"-->
		<@pagehead />
		<#-- 主体内容 -->
        <div id="page-wrapper" class="form-wrapper">
            <div class="row">
                <h4 class="text-center">订单详情</h4>
                <h5 class="text-center" style="color:red">${errorMessage!''}</h5>
                <div class="col-lg-offset-3 col-lg-8">
                	 <table class="table table-striped table-bordered table-hover" id="dataTables-example1">
						<tr>
							<th>订单编号:</th><td>${orderDetail.orderId}</td>
						</tr>
						<tr>
							<th>营地名称:</th><td>${orderDetail.campsName}</td>
						</tr>
						<tr>
							<th>出行人数量:</th><td>${orderDetail.totalCount!""}</td>
						</tr>
						<tr>
							<th>付款金额:</th><td>${orderDetail.payPrice!""}</td>
						</tr>
						<tr>
							<th>订单总金额:</th><td>${orderDetail.totalPrice!""}</td>
						</tr>
						<tr>
							<th>参与优惠:</th><td>${orderDetail.codeName!"无"}</td>
						</tr>
						<tr>
							<th>支付方式:</th><td>${payTypeMap[orderDetail.payType?string("0")]}</td>
						</tr>
						<tr>
							<th>会员手机号:</th><td>${orderDetail.accountId!""}</td>
						</tr>
						<tr>
							<th>下单时间:</th><td>${orderDetail.creatTime?string("yyyy-MM-dd HH:mm:ss")}</td>
						</tr>
	                  </table>
	                   <hr/>
	                  <p>订单联系人信息：</p>
	                   <table class="table table-striped table-bordered table-hover" id="dataTables-example1">
	                 		<tr>
								<th>姓名:</th><td>${orderDetail.contactName!""}</td>
							</tr>
							<tr>
								<th>电话:</th><td>${orderDetail.contactPhone!""}</td>
							</tr>
							<tr>
								<th>邮箱:</th><td>${orderDetail.contactEmail!""}</td>
							</tr>
	                   </table>
	                  <hr/>
	                  <p>出行人信息：</p>
                	<#list orderDetail.orderPersonList![] as person>
	                    <table class="table table-striped table-bordered table-hover" id="dataTables-example1">
							<tr>
								<th>出行人姓名:</th><td>${person.personName}</td>
							</tr>
							<tr>
								<th>出行人性别:</th><td>
									<#if person.personSex =0>
									男
									<#else>
									女
									</#if>
								</td>
							</tr>
							<tr>
								<th>出行人年龄:</th><td>${person.personAge}</td>
							</tr>
							<tr>
								<th>联系电话:</th><td>${person.personPhone}</td>
							</tr>
							<tr>
								<th>身份证号:</th><td>${person.personIdno}</td>
							</tr>
							<tr>
								<th>联系他地址:</th><td>${person.personAddress}</td>
							</tr>
	                     </table>
	                     <p/>
                     </#list>	   
                </div>
            </div><!-- /.row -->	
		</div>
    </div><!-- /#wrapper -->
<@docFoot />
</#compress>