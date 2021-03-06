<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zn">
<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=1180,inital-scale=1">
    <title>微信支付_Camplink</title>
    <script src="/js/isLogin.js"></script>
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <![endif]-->
    <script src="/js/prefixfree.min.js"></script>
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="/css/trip-calendar.css">
    <link rel="stylesheet" href="/css/user.css">
</head>
<body style="background:#eeeeee;">
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
<section class="weixin_head">
    <div class="logo">
        <img src="/img/weixin_logo.jpg" alt="">
    </div>
</section>
<section class="weixin_cont">
    <p id="qrcode" style="width:257px;height:257px;margin:0 auto;"></p>
    <p class="p2"><img src="/img/weixin1.jpg" alt="" width="262" height="55"></p>
    <p class="p3">¥<label><%= tradeAmount%></label></p>
    <p class="p4">camplink.com</p>
    <p class="p5"><i></i>400-123-456</p>
</section>
<section class="footer phoneWidth">
    <div class="div1_foot">
        <span class="span1">公司地址：北京市海淀区中关村南大街铸诚大厦B座</span>
        <span class="span2">加入我们：hr@chingoo.cn</span>
        <span class="span1">客服电话：010-59460305</span>
        <span class="span2">公司邮箱：chingoo@chingoo.cn</span>
    </div>
    <div class="div2_foot">
        <p class="p1_foot">营联世界 版权所有</p>
        <p class="p2_foot">copyright 2016-2017，camplink.cn. Powered by <a href="http://www.igalaxy.com.cn/" target="_blank" style="color:#fff;text-decoration:underline;">iGalaxy</a></p>
    </div>
</section>
<script src="/js/jquery-3.1.0.min.js"></script>
<script src="/js/qrcode.min.js"></script>
<script src="/js/public.js"></script>
<script src="/js/user.js"></script>
<script type="text/javascript">
//返回码 100 成功，其他状态未失败
var result= '<%=request.getAttribute("result")==null?"":request.getAttribute("result")%>';
//返回描述
var resultDesc= '<%=request.getAttribute("resultDesc")==null?"":request.getAttribute("resultDesc")%>';
//支付url，需要转换为二维码
var payUrl= '<%=request.getAttribute("payUrl")==null?"":request.getAttribute("payUrl")%>';
// 订单号
var logicOrderId= '<%=request.getAttribute("logicOrderId")==null?"":request.getAttribute("logicOrderId")%>';
//交易金额
var tradeAmount= '<%=request.getAttribute("tradeAmount")==null?"":request.getAttribute("tradeAmount")%>';
$(function(){
    if(result==100 && payUrl && logicOrderId){
        var qrcode = new QRCode('qrcode');
        qrcode.makeCode(payUrl);
        setInterval(getQueryState,5000)
    }else{
        resultDesc && alert(resultDesc);
    }
})
function getQueryState(){
    login_post('/pay/query.do','logicOrderId='+logicOrderId,'',function(data){
        data=JSON.parse(data);
        if(data.result==100){
            window.location.href='/user.html';
        }
    })
}
</script>
</body>
</html>