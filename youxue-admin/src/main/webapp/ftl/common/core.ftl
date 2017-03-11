<#compress>
<#-- 本系统使用
	bootstrap(v3.3.4)框架、sb-admin-2(1.0.7)主题

	默认加载组件
	dataTables 数据表格

	可选组件：
	timeline(时间轴)、Morris charts（图表）
	使用只需在<@docHead>方法的 hasModules参数配置即可
-->

<#-- docHead html文档头部 -->
<#-- 参数说明 --------------------
	title		string	页面标题	可选	默认值'后台管理系统'
	description	string	页面描述	可选	默认值'后台管理系统'
	keywords	string	页面关键字	可选	默认值'后台管理系统'	
	css			list	页面自定义样式表	可选	默认值 空	css=['myself1.css','myself2.css']
	hasModules  string	页面包含的功能模块	可选	
		默认 空	可选值 timeline(时间轴)、charts（图表），如包含多个模块用都逗号分开
		示例： 一个：hasModules="timeline"，多个值 hasModules="timeline,charts"
-->
<#--  用法说明 --------------------
用法一： (适用于：一切都默认设置都满足需求啦)
<@docHead/>

用法二： (适用于：自定义页面标题、描述、关键性、声明页面包括的功能模块、 添加定义样式 的情况)
<@docHead title="我要自定义" description="我要自定义" keywords="我要自定义" hasModules="charts" css=['my.css']/>

用法三：(适用于：不满足上述自定义的内容，还需要再head标签内加入其他内容的情况)
<@docHead title="我要自定义"	description="我要自定义"	keywords="我要自定义"	hasModules="charts"	css=['css/my.css']>
	这里写代码
</@docHead >
-->
<#macro docHead  title="后台管理系统" description="后台管理系统" keywords="后台管理系统" hasModules="" css=["bower_components/thumb/thumb.css"]>
<#-- 全局声明页面模块类型 -->
<#assign hasModules = hasModules/>
<#-- html文档正式开始 -->
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="${description}">
	<meta name="keywords" content="${keywords}">
	<title>${title}</title>
	<#-- bootstrap 核心 css -->
	<link href="/html/bower_components/bootstrap/dist/css/bootstrap.css" rel="stylesheet">
	<link href="/html/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

	<#-- 各种表格 DataTables CSS 、DataTables Responsive CSS  -->
    <link href="/html/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">
    <link href="/html/bower_components/datatables-responsive/css/dataTables.responsive.css" rel="stylesheet">
   
    <#-- Datepicker CSS-->
    <link href="/html/css/bootstrap-datetimepicker.min.css" rel="stylesheet">

	<#-- 时间轴(Timeline) CSS -->
	<#if hasModules?contains("timeline")>
	<link href="/html/css/timeline.css" rel="stylesheet">
	</#if>

	<#-- 主题样式 -->
	<link href="/html/css/sb-admin-2.css" rel="stylesheet">

	<#-- 图标类(Morris Charts) CSS -->
	<#if hasModules?contains("charts")>    
	<link href="/html/bower_components/morrisjs/morris.css" rel="stylesheet">
	</#if>

	<#-- 主题包含的图形字体 -->
	<link href="/html/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet">

	<!-- 页面公共的css -->
	<link href="/html/css/common.css" rel="stylesheet">

	<#-- 页面自定义样式表 -->
	<#if css?size gt 0>
	<#list css as list>
	<link href="/html/${list}" rel="stylesheet">
	</#list>
	</#if>

	<#-- 插入内容占位符 -->
	<#nested>
</head>
<body>
	<!--[if lt IE 10]>
		<div class="alert alert-danger">
			本系统不支持 <strong>ie6 ~ ie9</strong> 等落后的浏览器。请使用
			<a href="http://www.google.cn/chrome/browser/desktop/index.html">Chrome</a>、
			<a href="http://www.firefox.com.cn/download/">Firefox</a>、以及其他webkit内核浏览器。
		</div>
	<![endif]-->
</#macro>


<#-- docFoot html文档尾部  -->
<#-- 注意:该方法依赖<@docHead/>方法存在 
	 已经默认加载了jquery.js、bootstrap.js、metisMenu.js、sb-admin-2.js
-->

<#-- 参数说明 --------------------
	js	list	页面自定义js文件路径	可选	默认值 空	js=['myself1.js','myself2.js']	
-->
<#--  用法说明 --------------------
用法一： (适用于：一切都默认设置都满足需求啦)
<@docfoot/>

用法二： (适用于：页面有私用js要引用)
<@docfoot js=['my.js']/>

用法三：(适用于：不满足上述自定义的内容，还想在引入js文件后放些代码)
<@docfoot js=['js/my.js']>
	这里写代码
</@docfoot >
-->
<#macro docFoot js=["bower_components/thumb/thumb.js"]>
<#-- js  -->
	<#-- jQuery -->
	<script src="/html/bower_components/jquery/dist/jquery.min.js"></script>

	<#-- Bootstrap Core JavaScript -->
	<script src="/html/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<#-- Metis Menu Plugin JavaScript -->
	<script src="/html/bower_components/metisMenu/dist/metisMenu.min.js"></script>
	
	<#-- 图表类(Morris Charts) JavaScript -->
	<#if hasModules?contains("charts")> 
	<script src="/html/bower_components/raphael/raphael-min.js"></script>
	<script src="/html/bower_components/morrisjs/morris.min.js"></script>
	<#-- <script src="/js/morris-data.js"></script> -->
	</#if>

	<#-- DataTables JavaScript -->
    <script src="/html/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="/html/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
	
	<#-- Form Validation JavaScript-->
    <script src="/html/js/bootstrapValidator.min.js"></script>

    <#-- Datepicker JavaScript-->
    <script src="/html/js/bootstrap-datetimepicker.min.js"></script>
    <script src="/html/js/bootstrap-datetimepicker.zh-CN.js"></script>

	<#-- Custom Theme JavaScript -->
	<script src="/html/js/sb-admin-2.js"></script>

	<#-- Common Function JavaScript -->
	<script src="/html/js/common.js"></script>


	<#-- 页面自定义JavaScript -->
	<#if js?size gt 0>
		<#list js as list>
			<script src="/html/${list}"></script>	
		</#list>
	</#if>

	<#-- 插入内容占位符 -->
	<#nested>
</body>
</html>
</#macro>

<#-- 页面头部和导航 -->
<#macro pagehead>
<#assign siderbar = siderbar!true />
	<#include "header.ftl">
</#macro>

<#-- 文件上传  -->
<#-- 
    参数说明：
        value {List<String>} 已上传的文件url数组，若无则不用提供此值
        maxnum {Integer} 最大上传的文件数,默认值1
        maxsize {Integer} 单个文件的最大字节数, 默认值1073741824（1G）
        name {String} 后端接收的字段名
        ext {String} 允许上传的文件类型，以逗号分隔，如'png,jpg,gif',若不传则不限制类型
        path {String} 文件上传时对应的action路径，
                如/duobao/award/duobaoAdminUpLoadFiles.html中的award即为path值，
                若不提供此值则默认上传路径为/duobao/duobaoAdminUpLoadFiles.html

    使用方法：
        
        1.新建数据时
        <@fileUpload 
            path="ad"
            name="adImg"
            ext="png,jpg,jpeg,gif"
            maxnum="3"
            maxsize="1024" />
         
        2.修改数据时：
        <@fileUpload
            path="ad"
            name="adImg"
            ext="png,jpg,jpeg,gif"
            maxnum="3"
            maxsize="1024"
            value=['http://offlintab.firefoxchina.cn/static/img/search/baidu_web.png','https://www.baidu.com/img/baidu_jgylogo3.gif'] />

        注：其中各配置项请依项目实际需要填写。
-->
<#macro fileUpload 
		notEmpty="false" 
		notEmptyMsg=""
		value=[] 
		maxnum=1 
		maxsize=1073741824 
		name="" 
		ext="" 
		path=""
		group="">
    <div class="upload-wrapper" 
            data-maxnum="${maxnum}"
            data-maxsize="${maxsize}"
            data-ext="${ext}" 
            data-name="${name}" 
            data-path="${path}">
        
        <#local previewSize = 0 />
        <#list value as url>
            <#if url?length gt 0>
            	<#local previewSize = previewSize+1 />
	            <#local dotArr = url?split('.') />
	            <#local ext = dotArr[dotArr?size - 1] />
	            <div class="upload-preview" data-url="${url}">
	                <div title="删除" class="preview-del" data-hidden="#${group}_uploadHidden${url_index}">X</div>
	        		<#if ext == 'jpg' || ext == 'jpeg' || ext == 'png' || ext == 'gif'>
	                	<img title="点击查看大图" src="${url}" />
	       		 	<#else>
	                	<p title="点击查看文件" class="preview-ext">${ext?upper_case}</p>
	        		</#if>
	            </div>
	            <input type="hidden" id="${group}_uploadHidden${url_index}" value="${url}" name="${name}" />
            </#if>
        </#list>

        <div class="upload-trigger" <#if previewSize gte maxnum?number>style="display: none;"</#if>>
            <p class="trigger-desc"></p>
            <#if notEmpty=="true">
            	<input type="file" class="auto_upload_file"
            		data-notempty="true"
            		data-notempty-message="${notEmptyMsg}"/>
			<#else>
				<input type="file" class="auto_upload_file"/>
			</#if>
        </div>
        <#if notEmpty=="true">
        	<div><small class="help-block upload-err"></small></div>
        </#if>

    </div>
</#macro>

<#-- 
	ajax请求发送按钮

	参数说明:
		btnText: <必填>按钮上显示的文本,默认值为空串
		url: <必填>数据接口, 若有固定参数, 可拼在url后面；若参数来自其它表单节点，请配置para参数
		cls: [可选]按钮上的自定义class,
		para：[可选]参数节点的name属性值
		type: [可选]响应方式. 可选值 1 | 0, 默认值0.
			i. type为0时，一律弹窗显示结果
			ii. type为1且retCode为1时，刷新页面; 否则同i.

	要求:
		接口的返回值必须为固定格式的json,如下所示
		{
			"retCode": 200, //状态码, int类型, 成功时为200
			"msg": "响应信息"
		}
-->
<#macro flushBtn btnText="" url="" para="" type="" cls="">
	<#if btnText?length gt 0 && url?length gt 0>
		<button class="btn btn-primary ${cls}" 
			data-flush="true" 
			data-flush-url="${url}"
			data-flush-para="${para}"
			data-flush-type="${type}">${btnText}</button>
	</#if>
</#macro>

<#-- 工具方法入口 -------------------------------->
<#-- 分页方法组件 -->
<#include "paging.ftl">
</#compress>