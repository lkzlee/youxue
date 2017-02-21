<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="js/jquery-3.1.0.min.js"></script>
<script src="js/common.js"></script>
<script>
    var appId =  '<%=request.getParameter("appId")==null?"":request.getParameter("appId")%>';
    var timeStamp = '<%=request.getParameter("timeStamp")==null?"":request.getParameter("timeStamp")%>';
    var nonceStr = '<%=request.getParameter("nonceStr")==null?"":request.getParameter("nonceStr")%>';
    var pg = '<%=request.getParameter("pg")==null?"":request.getParameter("pg")%>';
    var signType = '<%=request.getParameter("signType")==null?"":request.getParameter("signType")%>';
    var paySign = '<%=request.getParameter("paySign")==null?"":request.getParameter("paySign")%>';
    function onBridgeReady(){
        WeixinJSBridge.invoke(
            'getBrandWCPayRequest', {
                "appId" : appId, //公众号名称，由商户传入
                "timeStamp": timeStamp, //时间戳，自1970年以来的秒数
                "nonceStr" : nonceStr, //随机串
                "package" : "prepay_id=" + pg,
                "signType" : signType, //微信签名方式:
                "paySign" : paySign //微信签名
            },
            function(res){
                // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
                alert(res.err_msg)
                if(res.err_msg == "get_brand_wcpay_request:ok" ) {

                }
            }
        );
    }
    if (typeof WeixinJSBridge == "undefined"){
        if( document.addEventListener ){
            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
        }else if (document.attachEvent){
            document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
        }
    }else{
        onBridgeReady();
    }
</script>
</body>
</html>