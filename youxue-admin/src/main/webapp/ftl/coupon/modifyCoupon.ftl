<#compress>
<#-- 嵌入基础依赖模块 -->
<#include "../common/core.ftl">
<#setting date_format="yyyy-MM-dd">
<#-- html文档头部 -->
<@docHead title="修改优惠券"/>
<#-- 正文 -->
    <div id="wrapper">
    	<#-- 页头和导航 文件地址："../common/header.ftl"-->
		<@pagehead />
		<#-- 主体内容 -->
				<!-- /.col-lg-12 -->
			<div class="row">
                <h4 class="text-center">修改优惠券</h4>
                <h5 class="text-center" style="color:red">${msg!''}</h5>
                <div class="col-lg-offset-3 col-lg-8">
                    <table class="table table-striped table-bordered table-hover" id="dataTables-example1">
                     	<input type="hidden" name="codeId" value="${coupon.codeId}" id="codeId">
                        <tr><th>优惠券名称 *:</th><td><input type="text" class="form-control" name="codeName" id="codeName" value="${coupon.codeName}"/></td></tr>
						<tr>
							<th>适用国家分类:</th><td>
								<#list localeCategoryList as category>
								<#if (coupon.categoryIds!'')?split(',')?seq_contains(category.categoryId) >
										<label class="checkbox-inline" >
												<input type="checkbox" name="categoryIds" value="${category.categoryId}" checked 
													data-bv-notempty="true" data-bv-notempty-message="" />${category.categoryName}
										</label>
								<#else>
									<label class="checkbox-inline" >
										<input type="checkbox" name="categoryIds" value="${category.categoryId}" />${category.categoryName}
									</label>
								</#if>
								</#list>	
							</td>
						</tr>
						<tr>
							<th>适用主题分类:</th>
							<td>
								<#list subjectCategoryList as category>
									<#if (coupon.categoryIds!'')?split(',')?seq_contains(category.categoryId) >
										<label class="checkbox-inline" >
												<input type="checkbox" name="categoryIds" value="${category.categoryId}" checked 
													data-bv-notempty="true" data-bv-notempty-message="" />${category.categoryName}
										</label>
								<#else>
									<label class="checkbox-inline" >
										<input type="checkbox" name="categoryIds" value="${category.categoryId}" />${category.categoryName}
									</label>
								</#if>
								</#list>	
							</td>
						</tr>
						<tr><th>优惠券编码:</th><td><input type="text" class="form-control" name="codeValue" id="codeValue" value="${coupon.codeValue}"/></td></tr>
						<tr><th>优惠券金额:</th><td><input type="number" class="form-control" name="codeAmount" id="codeAmount" value="${(coupon.codeAmount?c)!''}"/></td></tr>
						<tr><th>有效开始时间:</th><td><input class="form-control form_datetime" name="startTime" id="startTime"
                                        type="text"  data-picker-position="top-right" value="${coupon.startTime?date}"/></td></tr>
                         <tr><th>有效截止时间:</th>               
											<td><input class="form-control form_datetime" name="endTime" id="endTime"
                                        type="text"  data-picker-position="top-right" value="${coupon.endTime?date}"/></td></tr>
						<tr>
                              <th></th>  <td><button type="submit" class="btn btn-primary" onclick="modify()">提交</button></td>
						</tr>
                        </table>
                </div>
            </div><!-- /.row-->
    </div><!-- /#wrapper -->
<script>
	function modify(form)
	{
		 var codeName = document.getElementById("codeName").value;
		 var codeId = document.getElementById("codeId").value;
		 var categoryIds='';
		 var obj=document.getElementsByName('categoryIds');
		 for(var i=0; i<obj.length; i++){ 
			if(obj[i].checked) 
				categoryIds+=obj[i].value+',';
		 } 
		var codeValue=document.getElementById("codeValue").value;
		var codeAmount=document.getElementById("codeAmount").value;
		var startTime=document.getElementById("startTime").value;
		var endTime=document.getElementById("endTime").value;
		 if(codeName==null || ""==codeName)
		 {
		 	alert("名称不能为空");
		 }else{
			$.getJSON("doModifyCoupon.do", 
						{codeName:codeName,
						codeId:codeId,
						categoryIds:categoryIds,
						codeValue:codeValue,
						codeAmount:codeAmount,
						startTime:startTime,
						endTime:endTime }
				, function(data){
					  alert(JSON.parse(data).resultDesc);
					});
		 }  
	}
	
</script>
<@docFoot />
</#compress>