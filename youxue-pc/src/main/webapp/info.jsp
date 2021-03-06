<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<% 
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
//String campusId = request.getParameter("campusId");//用request得到 
%> 
<!DOCTYPE html>
<html lang="zn">
<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=1180,inital-scale=1">
    <title></title>
    <link rel="stylesheet" href="css/style.css?1">
    <link rel="stylesheet" href="css/info.css">
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <style type="text/css">
    #divBG{filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#aa000000,endColorstr=#aa000000);}
    #frm_logo span{overflow:hidden;}
    #frm_logo span i{right:-46px!important;top:7px!important;width:19px!important;border-width:19px;border-style:dotted;background:#21a8e2!important;}
    .con2 .con2_head .li2.tese .before{background:none;}
    .con2 .con2_head .li2 .before{background:url(/img/index.png);background-position:-165px -59px;}
    .con2 .con2_head .li2_ash .before{background-position:-191px -59px;background-color: #fff;}
    .con2 .con2_head .active.li2+.li2 .before{background-color:#29abe2;}
    .con2 .con2_head .wenti.active .after{background:url(/img/index.png);background-position:-191px -59px;}
    </style>
    <![endif]-->
    <script src="js/prefixfree.min.js"></script>
</head>
<body>
<section class="header phoneWidth">
    <section class="head1 clear">
        <div class="left">
            <a href="/"> </a>
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
                <div class="personal_icon ">
                    <a href="login.html"><i></i>个人中心</a>
                </div>
            </nav>
        </div>
    </section>
</section>
<section class="content width_content">
    <div class="con1 clear">
        <div class="left con1_left">
            <div class="img_info_div">
                <img class="img_info" src="" alt="">
            </div>
            <ul class="img_list">
            </ul>
        </div>
        <div class="left con1_right">
            <h1 class="title"><i></i></h1>
            <div class="con1_info">
                <div class="con1_info_d1">
                    <p class="p1">面向对象：<label class="orientedPeople"></label></p>
                    <!-- <p class="p1">行程时间：<labe class="durationTime"></labe>天</p> -->
                    <p class="p2">报名截止时间：<label class="deadlineDate"></label></p>
                </div>
                <p class="con1_info_p3">营地时间：<label class="startTime"></label></p>
                <div class="con1_info_d2 clear">
                    <div class="left">已有<span class="doneCount"></span>人报名</div>
                    <div class="right">
                        <i></i>
                        <div>
                            产品金额：<label class="totalPrice"></label>元
                        </div>
                    </div>
                </div>
                <p class="con1_info_p3"><span>产品特色：</span><label class="feature"></label></p>
                <p class="con1_info_p4"><span>服务保障：</span><label class="serviceSupport"></label></p>
                <p class="cont_info_p5"><span>报名人数：</span><input type="number" value="1" class="signUp_number shopCartCount"></p>
                <div class="con1_info_d3">
                    <div class="left">
                        <input type="button" class="leftA1 shoppingCar" value="加入购物车"/>
                        <input type="button" class="" value="免费咨询" onclick="onlineQQ()"/>
                    </div>
                    <div class="right">
                        <a href="javascript:void(0)" class="buyImmediately">立即购买</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="con2">
        <ul class="con2_head" id="YDnav">
            <li class="tese li2 li2_ash active"><i class="before"></i><a href="#tese">特色亮点</a></li>
            <li class="jieshao li2"><i class="before"></i><a href="#jieshao">营地介绍</a></li>
            <li class="chizhu li2 li2_ash"><i class="before"></i><a href="#chizhu">营地吃住行</a></li>
            <li class="huodong li2"><i class="before"></i><a href="#huodong_div">营地课程&活动</a></li>
            <li class="xingcheng li2 li2_ash"><i class="before"></i><a href="#xingcheng">营地行程</a></li>
            <li class="feiyong li2"><i class="before"></i><a href="#xiangqing">费用详情</a></li>
            <li class="wenti li2 li2_ash"><i class="before"></i><a href="#wenti">常见问题</a><i class="after"></i></li>
        </ul>
        <div class="con2_cont">
            <div class="div_con2_cont tese_div" id="tese">
                <div class="left">
                    <h3>营地图片</h3>
                </div>
                <div class="right">
                    <ul id="yingdi_list">
                    </ul>
                    <a href="javascript:void(0)" class="jiantou_left" id="jt_left">
                        <i></i>
                    </a>
                    <a href="javascript:void(0)" class="jiantou_right" id="jt_right">
                        <i></i>
                    </a>
                </div>
            </div>
            <div class="div_con2_cont jieshao_div" id="jieshao">
                <div class="left">
                    <h3>营地介绍</h3>
                </div>
                <div class="right">
                    <dl>
                        <dt class="title"></dt>
                        <dd>时间：<label class="createTime"></label></dd>
                        <dd>地点：<label class="campsLocale"></label></dd>
                        <dd>周期：<label class="durationTime"></label>天</dd>
                        <dd><span>简介：</span><div class="span1 campsDesc"></div></dd>
                    </dl>
                </div>
            </div>
            <div class="div_con2_cont chizhu_div" id="chizhu">
                <div class="left">
                    <h3>营地课程&活动</h3>
                </div>
                <div class="right">
                    <dl>
                        <dt>课程内容</dt>
                        <dd><span>概&nbsp;&nbsp;&nbsp;&nbsp;述：</span><span class="span1 courseDesc"></span></dd>
                        <dt class="dt1">活动内容</dt>
                        <dd><span>概&nbsp;&nbsp;&nbsp;&nbsp;述:</span><span class="span1 activityDesc"></span></dd>
                    </dl>
                </div>
            </div>
            <div class="div_con2_cont huodong_div" id="huodong_div">
                <div class="left">
                    <h3>营地吃住行<p>安全</p></h3>
                </div>
                <div class="right">
                    <dl>
                        <dt>营地伙食</dt>
                        <dd class="campsFoodDesc"></dd>
                        <dd class="dd_img campsFoodsPhotos">
                        </dd>
                        <dt>营地住宿</dt>
                        <dd class="campsHotelDesc"></dd>
                        <dd class="dd_img campsHotelPhotos">
                        </dd>
                    </dl>
                </div>
            </div>
            <div class="div_con2_cont xingcheng_div" id="xingcheng">
                <div class="left">
                    <h3>营地行程</h3>
                </div>
                <div class="right">
                    <ul class="traces">

                    </ul>
                    <p class="color_blur p1_right">注：以上行程均为参考，具体行程根据航班实际情况有所调整，免费咨询电话：400-123-567</p>
                </div>
            </div>
            <div class="div_con2_cont feiyong_div" id="xiangqing">
                <div class="left">
                    <h3>费用详情</h3>
                </div>
                <div class="right feeDesc">
                </div>
            </div>
            <div class="div_con2_cont wenti_div" id="wenti">
                <div class="left">
                    <h3>常见问题</h3>
                </div>
                <div class="right questions">
                </div>
            </div>
        </div>
    </div>
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
<div id="showImg">
    <i></i>
    <img src="uploads/images/item_list_b.jpg" alt="">
    <a href="#" id="showImg_left">
        <i></i>
    </a>
    <a href="#" id="showImg_right">
        <i></i>
    </a>
</div>
<div id="notLogin">
    <i id="close_car"></i>
    <p><i></i></p>
    <form action="" method="post" id="frm_logo" style="display:none;">
        <div class="phone clear">
            <span>手机号<i></i></span>
            <input type="text" placeholder="请输入手机号码" class="phone_input" name="mobile">
            <label></label>
        </div>
        <div class="password clear">
            <span>密码<i></i></span>
            <input type="password" placeholder="请输入动态密码" class="pwd_input" name="phoneCode">
            <input type="button" class="aCode" value="获取动态密码">
            <label></label>
        </div>
        <div class="code clear">
            <span>验证码<i></i></span>
            <input type="text" placeholder="请输入验证码" class="code_input" name="imgCode">
            <a href="javascript:void(0)" class="imgCode">
                <img src="verifyCode.do" alt="" id="imgCode">
            </a>
            <label></label>
        </div>
        <div class="bth_log clear">
            <input type="button" value="登录" id="submit_login">
            <label></label>
        </div>
    </form>
    <div id="car_message" style="display:none;" class="cont1_car">
        <p class="message"><i class="i1"></i><b>商品已成功加入购物车</b></p>
        <a href="javascript:void(0)" id="gobank">返回商品详情</a>
        <a href="user_shoppingCar.html" class="right">去购物车结算</a>
    </div>
</div>

<script type="text/javascript">
    var userAgent = navigator.userAgent.toLowerCase();
    var obj = {
        version: (userAgent.match(/.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/) || [])[1],
        msie: (/msie/.test(userAgent)||/rv:/.test(userAgent)) && !/opera/.test(userAgent)
    };
    if(!obj.msie || (obj.msie && parseInt(obj.version)>8)){
        document.write('<script src="js/jquery-3.1.0.min.js"><\/script>');
    }
</script>
<!--[if lte IE 8]> 
<script src="https://cdn.bootcss.com/jquery/1.9.1/jquery.min.js"></script>
<![endif]-->
<script src="js/public.js"></script>
<script src="js/json2.js"></script>
<script src="js/user.js"></script>
<script src="js/info.js"></script>
<script>
floatRight('info');
$(function(){
    info_loding();
    var frm_logo=$('#frm_logo');//表单
    var notLogin=$('#notLogin');
    //isLogin：为ture代表用户登录；flase代表用户未登录
    var campusId= '<%=request.getParameter("campusId")%>';
    var shoppingCar=$('.shoppingCar'),buyImmediately=$('.buyImmediately'),isLogin=false;
    var add_Car='/addCartItem.do',data_car={'campusId':campusId};
    var car_message=$('#car_message');
    load_render(data_car);
    is_login(function(data){
        if(data.result==100){
            isLogin=true;
        }else{
            isLogin=false;
        }
    })
    //登录-如果未登录，点击按钮会触发
    logo_user(function(){
        isLogin=true;
        frm_logo.hide();
        notLog_alert();
    });
    if($.browser.msie && parseInt($.browser.version) <=9){
        $('.title').addClass('ie78');
        $('.con1_info_d2 i').attr('id','ie78');
    }
    shoppingCar.click(function(ev){
        notLog_alert($(this));
        ev.stopPropagation();
    })
    buyImmediately.click(function(ev){
        notLog_alert($(this),true);
        ev.stopPropagation();
    });
    $('#gobank').click(function (ev) {
        hideAlert(notLogin);
        ev.stopPropagation();
    })
    $('#close_car').click(function(ev){
        hideAlert(notLogin);
        ev.stopPropagation();
    })
    notLogin.click(function(ev){
     ev.stopPropagation();
    })
    $(document).click(function(ev){
        hideAlert(notLogin);
        ev.stopPropagation();
    })    
    function notLog_alert(element,location){
        data_car['detailId']=$('.startTime .selected').attr('data-id');
        if(!data_car['detailId']){
            alert('请选择营地时间');
            return false;
        }
        data_car['num']=$('.signUp_number').val();
        if(!(/^[1-9]+\d*$/.test(data_car['num']))){
            alert('您输入的报名人数不正确')
            return false;
        }
        if(!isLogin){
            frm_logo.show();
            notLogin.fadeIn(300);
            bg_showORhide();
        }else{//登录，那么显示加入购物车消息
            element && element.attr('disabled','disabled').addClass('disabled');
            var message=car_message.find('.message');
            var i=$('i',message);
            var b=$('b',message);
            login_post(add_Car,data_car,'',function(data){
                data=JSON.parse(data);
                success(data,function(){
                    if(location){
                        window.location.href='/user_shoppingCar.html';
                    }else{
                        i.attr('class','i1');
                        b.html('商品已成功加入购物车');
                        car_message.show();
                        notLogin.fadeIn(300);
                        bg_showORhide();
                        element && element.removeAttr('disabled').removeClass('disabled');
                    }
                },function(){
                        element && element.removeAttr('disabled').removeClass('disabled');
                })
            });
        }
    }
})
</script>
</body>
</html>