<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% 
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/wxwap/"; 
%>
<!DOCTYPE html>
<html>
<head lang="en">
	<base href="<%=basePath%>"></base>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>_订单详情_Camplink</title>
    <link rel="stylesheet" href="css/cssReset.css"/>
    <link rel="stylesheet" href="css/goods_details.css"/>
    <script src="js/isLogin.js"></script>
</head>
<body>
<header>
<div class="div_head1"></div>
<div class="div_head2">
	<form class="padding20">
        <div class="form"><div class="section1"><p class="head_p">出行人信息</p><ul>
        <li><span>出行人姓名</span><label class="personName"></label></li>
        <li><span>联系电话</span><label class="personPhone"></label></li>
        <li><span>身份证号</span><label class="personIdno"></label></li>
        <li><span>联系地址</span><label class="personAddress"></label></li>
        </ul></div></div>
    </form>
    <div class="bg_height10"></div>
</div>
</header>
<section>
    <form id="outher_form">
    <div class="form">
        <div class="section2" style="margin-top:0;">
            <p>联系人信息</p>
            <ul>
                <li><span>联系人姓名</span><label class="contactName"></label></li>
                <li><span>联系电话</span><label class="contactPhone"></label></li>
                <li><span>邮箱地址</span><label class="contactEmail"></label></li>
            </ul>
        </div>
        <div class="section3">
            <p>付款方式</p>
            <ul>
	            <!-- <li><img src="img/ic_alipay.png" alt=""/><span>支付宝</span><label class="common_choice"  for="pay_zhifubao"><input id="pay_zhifubao" type="radio" name="payType" value="1" style="display:none"/><i></i></label></li> -->
                <li><img src="img/ic_weixinpay.png" alt=""/><span>微信支付</span><label class="common_choice " for="pay_weixin"><input id="pay_weixin" type="radio" name="payType" value="2" style="display:none"/><i class="j_wxPay"></i></label></li>
            </ul>
        </div>
        <div class="section4">
            <p>优惠编码</p>
            <ul>
                <li><label class="common_choice" for="no_youhui"><i class="active"></i></label><span>不使用/无优惠码</span><input type="radio" name="youkuima" value="1" checked id="no_youhui" style="display:none"/></li>
                <li><label class="common_choice" for="youhui"><i></i></label><span>使用优惠码</span><input type="radio" name="youkuima" value="2" id="youhui" style="display:none"/><input type="text" placeholder="点击输入优惠码" style="display: none" class="codeId" /></li>
            </ul>
        </div>
    </div>
    </form>
</section>
<footer class="cf">
    <p>合计：¥ <span class="j_price"></span></p>
    <button id="btn_order" onclick="location.href='my_order.jsp'">返回订单</button>
</footer>
<script src="js/jquery-3.1.0.min.js"></script>
<script src="js/fastclick.js"></script>
<script src="js/common.js"></script>
<script>
    // FastClick.attach(document.body);
    var orderId= '<%=request.getParameter("orderId")==null?"":request.getParameter("orderId")%>';
	$(function(){
	    getPayInfo();
	})
	function getPayInfo(){
	    login_post('/uc/orderdetail.do','orderId='+orderId,'',function(data){
	        data=JSON.parse(data);
	        // console.log(data)
	        renderOrderInfo(data);
	        renderPersonInfo(data.orderPersonList[0]);
	        renderContactInfoAndOther(data);
	    })
	}
    function renderOrderInfo(data){
    	$('title').prepend(data.campsTitle)
        var arr=[];
        arr.push('<div class="goods_message padding20"><div class="cf"><p class="fl head_p">订单信息</p></div>');
        arr.push('<ul><li class="cf" onclick=javascript:location.href="/wxwap/camp_details.jsp?campusId='+data['campsId']+'"><a href="/wxwap/camp_details.jsp?campusId='+data['campsId']+'"><img src="'+handle_pic(data['campsImages'])[0]+'" alt=""/><div><p>'+data['campsTitle']+'</p>');
        arr.push('<p class="price">价格 ¥ <span>'+data['payPrice']+'</span></p></div></a></li></ul></div><div class="bg_height10"></div>');
        $('.div_head1').append(arr.join(''));
    }
	function renderPersonInfo(data){//出行人信息
	    $('.personName').text(data.personName);
	    $('.personPhone').text(data.personPhone);
	    $('.personIdno').text(data.personIdno);
	    $('.personAddress').text(data.personAddress);
	}
    function renderContactInfoAndOther(data){//联系人及其他信息
	    $('.contactName').text(data.contactName);
	    $('.contactPhone').text(data.contactPhone);
	    $('.contactEmail').text(data.contactEmail);
	    data.payType==2 && $('.j_wxPay').addClass('active');
	    if(data.codeId){
	    	$('.section4 li').eq(1).find('i').addClass('active')
	    	$('.section4 li').eq(0).find('i').removeClass('active');
	        $('.codeId').val(data.codeId).show()
	    }
	    $('.j_price').text(data.payPrice);
	    // var arr=getState(data.payStatus);
	    // var btn_order=$('#btn_order');
	    // btn_order.text(arr[0]).addClass(arr[1]).attr('data-id',data.orderId).attr('data-value',data.logicOrderId)
	    // if(data.payStatus==1){
	    // 	btn_order.attr('disabled','disabled');
	    // }
	}
	// $('#btn_order').on('click',function(){
	//     login_post('/wxpay/addTradeOrderById.do','logicOrderId='+$(this).attr('data-id')+'','',function(data){
	//         setBtnDisabled($(this),false)
	//         data=JSON.parse(data);
	//         user_success(data,function(){
	//             if(data.wxPayParam){
	//                 window.location.href='wxpay.jsp?'+urlFormatObj(data.wxPayParam);
	//             }
	//         })
	//     });
	// })
	function getState(state){
	    switch(state){
	        case 0:
	            return ['去支付','pay_order'];
	        case 1:
	            return ['待审核','disabled'];
	        case 2:
	            return ['取消订单','j_BtnCancel'];
	        case 3:
	            return ['删除记录','del_order'];
	        case 4:
	            return ['删除记录','del_order'];
	    }
	}
</script>
</body>
</html>