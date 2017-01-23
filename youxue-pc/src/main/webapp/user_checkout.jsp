<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>个人中心-结算订单_Camplink</title>
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
                    <a href="index.html"><i></i>首页</a>
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
            <div id="order_list">

            </div>
            <form id="outher_form">
            <div class="width_content prople_info contacts">
                <div class="head">联系人信息</div>
                <ul class="cont">
                    <li>
                        <span><i>*</i>联系人姓名：</span>
                        <input type="text" class="input1 require" name="contactName">
                    </li>
                    <li>
                        <span><i>*</i>联系人电话：</span>
                        <input type="text" class="input4 require" name="contactPhone">
                    </li>
                    <li>
                        <span><i>*</i>电子邮箱：</span>
                        <input type="text" class="input4 require" name="contactEmail">
                    </li>
                </ul>
            </div>
            <div class="width_content prople_info contacts">
                <div class="head">支付方式</div>
                <div class="cont_pay">
                    <label>
                        <input type="radio" name="payType" checked value="1"><span>支付宝</span>
                        <img src="img/alpay.jpg" alt="">
                    </label>
                    <label>
                        <input type="radio" name="payType" value="2"><span>微信支付</span>
                        <img src="img/weixin.jpg" alt="">
                    </label>
                </div>
                <div class="right_cont_pay">
                    <div class="div1">
                        <label>
                            <input type="radio" class="codeId_radio">
                            <span>使用优惠码：</span>
                        </label>
                        <input type="text" name="codeId">
                    </div>
                    <div class="div2">
                        实付款：<i class="moneyTotal">¥5000</i>元
                    </div>
                    <label class="div3">
                        <input type="checkbox" class="agree_check">
                        <span>同意<a href="#" id="viewProtocol">用户购买协议</a>，勾选后可提交订单</span>
                    </label>
                    <div class="div4">
                        <a href="user_shoppingCar.html"> <<返回购物车 </a>
                        <input type="button" value="提交订单" id="btn_order">
                    </div>
                </div>
            </div>
            </form>
        </div>
</section>
<div id="showImg">
    <i></i>
    <div>
        <h2>这里是协议的标题</h2>
        <p>这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容<br>
            这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容<br>
            这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容<br>
            这里是协议的内容。这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容<br>
            这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容<br>
            这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容<br>
            这里是协议的内容。<br/><br/><br/><br/>
            这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容<br>
            这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容<br>
            这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容<br>
            这里是协议的内容。<br/><br/><br/><br/>
            这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容<br>
            这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容<br>
            这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容，这里是协议的内容<br>
            这里是协议的内容。
        </p>
    </div>
</div>
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
<script src="js/jquery-3.1.0.min.js"></script>
<script src="js/public.js"></script>
<script src="js/user_pay.js"></script>
<script>
    var orderList= '<%=request.getParameter("orderList")==null?"":request.getParameter("orderList")%>';
    console.log(orderList);
    $(function(){
        var orderObj=[],htmlArr=[],num=0,len=0,moneyTotal=0;
        var discount=0;
        //输入优惠码.验证后单选选中，否则取消；提交时如果单选选中，那么提交优惠码
        $('input[name=codeId]').blur(function () {
            if(changeRadio() && changeRadio()!==1){
                login_post('/coupon/getCouponByCode.do','codeId='+$(this).val(),'',function(data){
                    data=JSON.parse(data);
                    console.log(data);
                    success(data,function(){
                        discount=data.codeAmount;
                        $('.moneyTotal').text(moneyTotal-discount);
                    },function(){
                        $('.codeId_radio').prop('checked',false);
                    })
                })
            }
        })
        if(orderList){
            var arr=orderList.split(',');
            moneyTotal=arr[0];
            $('.moneyTotal').text(moneyTotal)
            len=arr.length;
            for(var i=1;i<len;i++){
                var orderArr=arr[i].split('$$');
                orderObj[i]={
                    'value':orderArr[0],
                    'img':orderArr[1],
                    'name':orderArr[2],
                    'unitPrice':orderArr[3],
                    'numCar':orderArr[4],
                    'money':orderArr[5]
                }
                for(var j=0;j<orderObj[i]['numCar'];j++){
                    num++;
                    htmlArr.push(render_orderInfo(orderObj[i],i+''+j));
                    htmlArr.push(render_propleInfo(orderObj[i],i+''+j));
                }
            }
        }
        $('#order_list').html(htmlArr);
        setTimeout(function(){
            $('body').append("<script src='js/distpicker.data.js'><\/script><script src='js/distpicker.js'><\/script>");
        },300)
        $('#btn_order').click(function(){
            if(!auto_range()){
                return false;
            }
            if(!changeRadio()){
                return false;
            }
            if(!$('.agree_check').prop('checked')){
                alert('请勾选《用户购买协议》后提交')
                return false;
            }
            var AllObj={},infoArr=[];
            var outherObj=$('#outher_form').serializeObject();
            for(var i=1;i<len;i++){
                for(var j=0;j<orderObj[i]['numCar'];j++){
                    var orderListObj=$('.person'+i+''+j).serializeObject();
                    infoArr.push({
                        'campsId':orderListObj['campsId'],
                        'codeId':outherObj['codeId'],
                        'totalCount':1,
                        'contactName':outherObj['contactName'],
                        'contactEmail':outherObj['contactEmail'],
                        'contactPhone':outherObj['contactPhone'],
                        'outPersonList':[{
                            'personName':orderListObj['personName'],
                            'personSex':orderListObj['personSex'],
                            'personAge':orderListObj['personAge'],
                            'personPhone':orderListObj['personPhone'],
                            'personIdno':orderListObj['personIdno'],
                            'personAddress':orderListObj['province']+orderListObj['city']+orderListObj['district']+orderListObj['personAddress']
                        }]
                    })
                }
            }
            AllObj['orderList']=infoArr;
            AllObj['payType']=outherObj['payType'];
            login_post('/pay/addTradeOrder.do',JSON.stringify(AllObj),'',function(data){
                data=JSON.parse(data);
                console.log(data);
                success(data,function(){
                    if(data.payUrl){
                        console.log('window.location.href='+data.payUrl);
                        window.location.href=data.payUrl
                    }
                })
            },'','application/json; charset=utf-8')
            return false;
        })
    })
    function render_orderInfo(obj){
        var arr=[];
        arr.push('<div class="width_content"><div class="head">订单信息</div></div><div class="width_content cont_order"><div class="div_order clear">');
        arr.push('<span class="span1">营地名称</span><span class="span2">单价（元）</span><span class="span3">数量</span><span class="span4">合计（元）</span></div>');
        arr.push('<div class="div_cont_order"><a href="/info.jsp?campusId='+obj['value']+'" class="a1_order"><img src="'+obj['img']+'" alt=""><span class="span3">'+obj['name']+'</span></a>');
        arr.push('<span class="span4">'+obj['unitPrice']+'</span><span class="span5">1</span><span class="span6">'+obj['unitPrice']+'</span></div></div>');
        return arr.join('')
    }
    function render_propleInfo(obj,num){
        var arr=[];
        arr.push('<form class="person'+num+'">');
        arr.push('<div class="width_content prople_info"><div class="head">出行人信息</div><ul class="cont"><li><span><i>*</i>出行人信息：</span>');
        arr.push('<input type="text" class="input1 require" name="personName" required pattern="^\w.*$"></li><li><span><i>*</i>出行人性别：</span><label><input type="radio" name="personSex" value="0" checked>');
        arr.push('<span>男</span></label><label><input type="radio" name="personSex" value="1"><span>女</span></label><span class="span3"><i>*</i>年龄：</span>');
        arr.push('<input type="text" class="input3 require" name="personAge"></li><li><span><i>*</i>联系电话：</span><input type="text" class="input4 require" name="personPhone">');
        arr.push('</li><li><span><i>*</i>身份证号码：</span><input type="text" class="input4 require" name="personIdno"></li><li><span><i>*</i>联系地址：</span>');
        arr.push('<div data-toggle="distpicker" class="div_address"><div class="form-group"><select class="form-control" name="province"></select></div><div class="form-group">');
        arr.push('<select class="form-control" name="city"></select></div><div class="form-group"><select class="form-control" name="district"></select></div></div>');
        arr.push('<input type="text" class="input4 require" name="personAddress"></li></ul></div><input type="hidden" name="campsId" value="'+obj['value']+'"/> ');
        arr.push('</form>');
        return arr.join('')
    }
    //根据输入优惠码，改变单选状态
    function changeRadio(){
        var code=$('input[name=codeId]');
        var bool=1;
        if(code.val().length>0){
            if(code.val().length>2){
                bool=true;
            }else{
                bool=false;
            }
            $('.codeId_radio').prop('checked',bool);
            return range_input(code,bool);
        }
        return bool;
    }
    function range_input(input,bool){
        if(!bool){
            input.focus();
            input.css('border-color','red');
        }else{
            input.css('border-color','');
        }
        return bool;
    }
    function auto_range(){
        var input=$('input.require');
        var bool=true;
        input.each(function(){
            if($(this).val().length<2){
                bool=false;
                return range_input($(this),false);
            }else{
                bool=true;
                return range_input($(this),true);
            }
        })
        return bool;
    }
</script>
</body>
</html>