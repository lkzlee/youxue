<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "../common/core.ftl">
<#setting date_format="yyyy-MM-dd">
<#-- html文档头部 -->
<@docHead title="优惠券管理"/>
<#-- 正文 -->
    <div id="wrapper">
    	<#-- 页头和导航 文件地址："../common/header.ftl"-->
		<@pagehead />
		<#-- 主体内容 -->
        <div id="page-wrapper" class="form-wrapper">
            <div class="row">
                <h4 class="text-center">优惠券</h4>
                <h5 class="text-center" style="color:red">${msg!''}</h5>
                <div class="col-lg-12">
                    <form class="form-horizontal" id="form" action="/doGetCouponList.do" data-validate="true">
                    <table class="table table-striped table-bordered table-hover" id="dataTables-example1">
						<tr>
							<th>名称:</th><td><input type="text" class="form-control" name="couponName" /></td>
							<th >分类:</th>
							<td><select class="form-control" name="categoryId" id="categoryId">
                                	<option value=''>--</option>
									<#list categoryList![] as t>
									       <option value="${t.categoryId}">${t.categoryName}</option>
									</#list>
								</select>
							</td>
							<th>编码:</th><td><input type="text" class="form-control" name="couponValue" /></td>
						</tr>
						<tr>
							<th >有效期截止时间:</th>
							<td >
								<input class="form-control form_datetime" name="validStartTime" id="validStartTime"
                                        type="text"  data-picker-position="top-right"/>
                            </td>
                            <td >
                                <input class="form-control form_datetime" name="validEndTime" id="validEndTime"
                                        type="text"  data-picker-position="top-right"/>
							</td>
							<td align=center colspan='6'>
								<p style="text-align:center">
									<button type="submit" class="btn btn-primary">查询</button> 
								</p>
							</td>
						</tr>
						<tr>
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
							优惠券列表
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover" id="dataTables-example1">
									<thead>
										<tr>
											<th>名称</th>
											<th>分类</th>
											<th>金额</th>
											<th>编码</th>
											<th>有效时间</th>
											<th>创建时间</th>
											<th>创建人</th>
											<th class="text-center" colspan=2>操作</th>
										</tr>
									</thead>
									<tbody>
										<#list couponPage.resultList![] as adItem>
										<tr class="<#if adItem_index%2 == 0>odd<#else>even</#if>">
											<td>${adItem.codeName!""}</td>
											<td>${adItem.categorys!""}</td>
											<td>${adItem.codeAmount}</td>
											<td>${adItem.codeValue!""}</td>
											<td>${adItem.startTime?date} 至 ${adItem.endTime?date}</td>
											<td>${adItem.createTime?date}</td>
											<td>${adItem.creator!""}</td>
											<td><a href="/modifyCouponIndex.do?couponId=${adItem.codeId}"><span>修改</span></a></td>
											<td><a href="/downCoupon.do?couponId=${adItem.codeId}" onclick="return confirm('您确认下架“${adItem.codeName}”吗?')"><span>下架</span></a></td>
										</tr>
										</#list>
									</tbody>
								</table>
							</div>                            
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<hr>
				<!-- /.col-lg-12 -->
				 <div class="row">
                <h4 class="text-center">添加优惠券</h4>
                <h5 class="text-center" style="color:red">${addMsg!''}</h5>
                <div class="col-lg-12">
                    <form class="form-horizontal" id="addForm" method="POST" action="/doAddCoupon.do" data-validate="true">
                    <table class="table table-striped table-bordered table-hover" id="dataTables-example1">
                        <tr><th>优惠券名称 *:</th><td><input type="text" class="form-control" name="codeName" /></td></tr>
						<tr>
							<th>适用国家分类:</th><td>
								<#list localeCategoryList as category>
									<label class="checkbox-inline" >
										<input type="checkbox" name="categoryIds" value="${category.categoryId}" />${category.categoryName}
									</label>
								</#list>	
							</td>
						</tr>
						<tr>
							<th>适用主题分类:</th>
							<td>
								<#list subjectCategoryList as category>
									<label class="checkbox-inline" >
										<input type="checkbox" name="categoryIds" value="${category.categoryId}" />${category.categoryName}
									</label>
								</#list>	
							</td>
						</tr>
						<tr><th>优惠券编码:</th><td><input type="text" class="form-control" name="codeValue" /></td></tr>
						<tr><th>优惠券金额:</th><td><input type="text" class="form-control" name="codeAmount" /></td></tr>
						<tr><th>有效时间:</th><td><input class="form-control form_datetime" name="startTime" id="startTime"
                                        type="text"  data-picker-position="top-right"/></td>
											<td><input class="form-control form_datetime" name="endTime" id="endTime"
                                        type="text"  data-picker-position="top-right"/></td>
						</tr>
                        <tr>
							<td align=center colspan='6'>
								<p style="text-align:center"><button type="submit" class="btn btn-primary">添加</button></p>
							</td>
						</tr>
					</table>
                    </form>
                </div>
            </div><!-- /.row-->
            <hr>
		</div>
    </div><!-- /#wrapper -->
<@docFoot />
</#compress>