<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>_订单详情_Camplink</title>
    <script src="js/isLogin.js"></script>
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <![endif]-->
    <script src="js/prefixfree.min.js"></script>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/trip-calendar.css">
    <link rel="stylesheet" href="css/user.css">
</head>
<body>
<section class="header">
    <section class="head1 clear">
        <div class="left">
            <a href="#"> </a>
        </div>
        <div class="right">
            <div class="search">
                <input type="text"><div class="inco"><a href="search.html"></a></div>
            </div>
            <div class="car_inco"><a href="user_shoppingCar.html"></a></div>
            <div class="tel">
                <div class="inco"><a></a></div>
                <span>400-126-456</span>
            </div>
        </div>
    </section>
    <section class="head_nav">
        <div class="width_content">
            <nav>
                <div class="index_icon">
                    <a href="index.jsp"><i></i>首页</a>
                </div>
                <div class="about_icon ">
                    <a href="about.html"><i></i>关于我们</a>
                </div>
                <div class="news_icon ">
                    <a href="news.html"><i></i><span>Camplink</span>资讯</a>
                </div>
                <div class="private_icon ">
                    <a href="customized.html"><i></i>私人定制</a>
                </div>
                <div class="personal_icon" id="user_active">
                    <a href="login.html"><i></i>个人中心</a>
                </div>
            </nav>
        </div>
    </section>
</section>
<section class="user_checkout clear">
    <div class="order_info">
        <div class="head_width"></div>
        <div>
            <div class="width_content">
                <div class="head">订单信息</div>
            </div>
            <div class="width_content cont_order j_order_list">
                <div class="div_order clear">
                    <span class="span1">营地名称</span><span class="span2">单价（元）</span><span class="span3">数量</span><span class="span4">合计（元）</span>
                </div>
            </div>
            <form class="person10 j_person">
                <div class="width_content prople_info">
                <div class="head">出行人信息</div>
                    <ul class="cont">
                        <li>
                            <span><i>*</i>出行人信息：</span><label class="personName"></label>
                        </li>
                        <li>
                            <span><i>*</i>出行人性别：</span><label class="personSex"></label>
                            <span class="span3"><i>*</i>年龄：</span><label class="personAge"></label>
                        </li>
                        <li>
                            <span><i>*</i>联系电话：</span><label class="personPhone"></label>
                        </li>
                        <li>
                            <span><i>*</i>身份证号码：</span><label class="personIdno"></label>
                        </li>
                        <li>
                            <span><i>*</i>联系地址：</span><label class="personAddress"></label>
                        </li>
                    </ul>
                </div>
            </form>
        </div>
        <form id="outher_form" class="j_person">
            <div class="width_content prople_info contacts">
                <div class="head">联系人信息</div>
                <ul class="cont">
                    <li>
                        <span><i>*</i>联系人姓名：</span>
                        <label class="contactName">111</label>
                    </li>
                    <li>
                        <span><i>*</i>联系人电话：</span>
                        <label class="contactPhone">111</label>
                    </li>
                    <li>
                        <span><i>*</i>电子邮箱：</span>
                        <label class="contactEmail">111</label>
                    </li>
                </ul>
            </div>
            <div class="width_content prople_info contacts">
                <div class="head">支付方式</div>
                <div class="cont_pay">
                    <label>
                        <input type="radio" name="payType" checked value="1" disabled=""><span>支付宝</span>
                        <img src="img/alpay.jpg" alt="">
                    </label>
                    <label>
                        <input type="radio" name="payType" value="2" disabled=""><span>微信支付</span>
                        <img src="img/weixin.jpg" alt="">
                    </label>
                </div>
                <div class="right_cont_pay">
                    <div class="div1 j_codeDiv" style="display: none;">
                        <label>
                            <span>使用优惠码：</span>
                        </label>
                        <label class="codeId"></label>
                    </div>
                    <div class="div2">
                        实付款：<i class="moneyTotal">¥<label class="payPrice"></label></i>元
                    </div>
                    <div class="div4">
                        <input type="button" value="返回订单" onclick="history.go(-1)">
                    </div>
                </div>
            </div>
        </form>
    </div>
</section>
<section class="footer">
    <div class="div1_foot">
        <span class="span1">公司地址：北京市海淀区中关村南大街铸诚大厦B座</span>
        <span class="span2">加入我们：hr@chingoo.cn</span>
        <span class="span1">客服电话：010-59460305</span>
        <span class="span2">公司邮箱：chingoo@chingoo.cn</span>
    </div>
    <div class="div2_foot">
        <p class="p1_foot">营联天下 版权所有</p>
        <p class="p2_foot">copyright 2016-2017，camplink.cn. Powered by iGalaxy</p>
    </div>
</section>
<script src="js/jquery-3.1.0.min.js"></script>
<script src="js/public.js"></script>
<script>
var orderId= '<%=request.getParameter("orderId")==null?"":request.getParameter("orderId")%>';
$(function(){
    getPayInfo();
})
function getPayInfo(){
    login_post('/uc/orderdetail.do','orderId='+orderId,'',function(data){
        data=JSON.parse(data);
        renderOrderInfo(data);
        renderContactInfoAndOther(data);
        renderPersonInfo(data.orderPersonList[0]);
    })
}
function renderOrderInfo(data){
    $('title').prepend(data.campsTitle)
    var arr=[];
    arr.push('<div class="div_cont_order">');
    arr.push('<a href="/info.jsp?campusId='+data['campsId']+'" class="a1_order"><img src="'+handle_pic(data['campsImages'])[0]+'" alt=""><span class="span3">'+data['campsTitle']+'</span></a>');
    arr.push('<span class="span4">'+data['payPrice']+'</span><span class="span5">'+data['totalCount']+'</span><span class="span6">'+data['totalPrice']+'</span>');
    arr.push('</div>');
    $('.j_order_list').append(arr.join(''));
}
function renderContactInfoAndOther(data){//联系人及其他信息
    var arr=[];
    $('.contactName').text(data.contactName);
    $('.contactPhone').text(data.contactPhone);
    $('.contactEmail').text(data.contactEmail);

    $('input[name=payType][value='+data.payType+']').attr('checked','checked');
    if(data.codeId){
        $('.j_codeDiv').show();
        $('.codeId').text(data.codeId)
    }
    $('.payPrice').text(data.payPrice);
}
function renderPersonInfo(data){//出行人信息
    $('.personName').text(data.personName);
    $('.personSex').text(getSex(data.personSex));
    $('.personAge').text(data.personAge);
    $('.personPhone').text(data.personPhone);
    $('.personIdno').text(data.personIdno);
    $('.personAddress').text(data.personAddress);
}
</script>
</body>
</html>