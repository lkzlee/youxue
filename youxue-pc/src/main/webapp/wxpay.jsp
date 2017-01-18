<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>个人中心-我的消息_Camplink</title>
    <script src="/js/isLogin.js"></script>
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <![endif]-->
    <script src="'/js/prefixfree.min.js"></script>
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="/css/trip-calendar.css">
    <link rel="stylesheet" href="/css/user.css">
</head>
<body style="background:#eeeeee;">
<section class="weixin_head">
    <div class="logo">
        <img src="/img/weixin_logo.jpg" alt="">
    </div>
</section>
<%
//返回码 100 成功，其他状态未失败
	String result=request.getAttribute("result")+"";
//返回描述
	String resultDesc=request.getAttribute("resultDesc")+"";
	//支付url，需要转换为二维码
	String payUrl=request.getAttribute("payUrl")+"";
	// 订单号
	String logicOrderId=request.getAttribute("logicOrderId")+"";
	//交易金额
	String tradeAmount=request.getAttribute("tradeAmount")+"";
%>
<section class="weixin_cont">
    <p><img src="/img/weixin_erweima.jpg" alt="" width="257" height="257"></p>
    <p class="p2"><img src="/img/weixin1.jpg" alt="" width="262" height="55"></p>
    <p class="p3">¥<label>1500</label></p>
    <p class="p4">camplink.com</p>
    <p class="p5"><i></i>400-123-456</p>
</section>
<p>
返回码<%= result%>
</p>
<p>
返回描述<%= resultDesc%>
</p>
<p>
支付url，转为二维码链接：<%= payUrl%>
</p>
<p>
支付订单号：<%= logicOrderId%>
</p>
<p>
<%= tradeAmount%>
</p>
<section class="footer">
    <div class="div1_foot">
        <span class="span1">公司地址：北京市西城区裕民路18号北环中心801</span>
        <span class="span2">加入我们：hr@123123123.com</span>
        <span class="span1">客服电话：400-755-2255</span>
        <span class="span2">公司邮箱：gongsi@123123123.com</span>
    </div>
    <div class="div2_foot">
        <p class="p1_foot">青古留学 版权所有</p>
        <p class="p2_foot">copyright 2015-2016,Complink.com.ALL Rights Reserved.</p>
    </div>
</section>
<script src="/js/jquery-3.1.0.min.js"></script>
<script src="/js/jquery.easing.min.js"></script>
<script src="/js/public.js"></script>
<script src="/js/user.js"></script>

</body>
</html>