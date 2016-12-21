<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<% 
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
//String campusId = request.getParameter("campusId");//用request得到 
%> 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Camplink-英国亲子营地，5天3地，学习英国文化氛围</title>
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <![endif]-->
    <script src="js/prefixfree.min.js"></script>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/info.css">
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
            <img class="img_info" src="" alt="">
            <ul class="img_list">
            </ul>
        </div>
        <div class="left con1_right">
            <h1 class="title">英国亲子营地，5天3地，学习英国文化氛围</h1>
            <div class="con1_info">
                <div class="con1_info_d1">
                    <p class="p1">面向对象：<label class="orientedPeople"></label></p>
                    <p class="p1">行程时间：<labe class="durationTime"></labe>天</p>
                    <p class="p2">报名截止时间：<label class="deadlineDate"></label></p>
                </div>
                <div class="con1_info_d2 clear">
                    <div class="left">已有<span class="doneCount"></span>人报名</div>
                    <div class="right">
                        <i></i>
                        <div>
                            产品金额：<label class="totalPrice"></label>元
                        </div>
                    </div>
                </div>
                <p class="con1_info_p3">产品特色：<label class="feature"></label></p>
                <p class="con1_info_p4">服务保障：<label class="serviceSupport"></label></p>
                <p class="cont_info_p5">报名人数：<input type="number" value="1" class="signUp_number doneCount"></p>
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
            <li class="tese li2 li2_ash active"><a href="#">特色亮点</a></li>
            <li class="jieshao li2"><a href="#">营地介绍</a></li>
            <li class="chizhu li2 li2_ash"><a href="#">营地吃住行</a></li>
            <li class="huodong li2"><a href="#">营地课程&活动</a></li>
            <li class="xingcheng li2 li2_ash"><a href="#">营地行程</a></li>
            <li class="feiyong li2"><a href="#">费用详情</a></li>
            <li class="wenti li2 li2_ash"><a href="#">常见问题</a></li>
        </ul>
        <div class="con2_cont">
            <div class="div_con2_cont tese_div">
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
            <div class="div_con2_cont jieshao_div">
                <div class="left">
                    <h3>营地介绍</h3>
                </div>
                <div class="right">
                    <dl>
                        <dt class="campsName"></dt>
                        <dd>时间：<label class="departureDate"></label></dd>
                        <dd>地点：<label class="campsLocale"></label></dd>
                        <dd>周期：<label class="durationTime"></label>天</dd>
                        <dd><span>简介：</span><span class="span1 campsDesc"></span></dd>
                    </dl>
                </div>
            </div>
            <div class="div_con2_cont chizhu_div">
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
            <div class="div_con2_cont huodong_div">
                <div class="left">
                    <h3>营地吃住行<br/>安全</h3>
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
            <div class="div_con2_cont xingcheng_div">
                <div class="left">
                    <h3>营地行程</h3>
                </div>
                <div class="right">
                    <ul class="traces">

                    </ul>
                    <p class="color_blur p1_right">注：以上行程均为参考，具体行程根据航班实际情况有所调整，免费咨询电话：400-123-567</p>
                </div>
            </div>
            <div class="div_con2_cont feiyong_div">
                <div class="left">
                    <h3>费用详情</h3>
                </div>
                <div class="right feeDesc">
                </div>
            </div>
            <div class="div_con2_cont wenti_div">
                <div class="left">
                    <h3>常见问题</h3>
                </div>
                <div class="right">
                    <img src="img/wenti.jpg" alt="">
                </div>
            </div>
        </div>
    </div>
</section>
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
            <input type="submit" value="登录">
            <label></label>
        </div>
    </form>
    <div id="car_message" style="display:none;" class="cont1_car">
        <p class="message"><i class="i1"></i><b>商品已成功加入购物车</b></p>
        <a href="javascript:void(0)" id="gobank">返回商品详情</a>
        <a href="user_shoppingCar.html" class="right">去购物车结算</a>
    </div>
</div>
<div id="right_float" style="display:none;">
    <a href="javascript:void(0)" class="join_Car shoppingCar"><i></i><span>加入购物车</span></a>
    <a href="javascript:void(0)" class="now_bug buyImmediately"><i></i><span>立即购买</span></a>
    <a href="javascript:void(0)" onclick="onlineQQ()" class="online_advice"><i></i><span>在线咨询</span></a>
    <a href="javascript:void(0)" class="back_top" onclick="backTop()"><i></i><span>返回顶部</span></a>
</div>
<script src="js/jquery-3.1.0.min.js"></script>
<script src="js/public.js"></script>
<script src="js/info.js"></script>
<script>
$(function(){
    var frm_logo=$('#frm_logo');//表单
    var notLogin=$('#notLogin');
    //isLogin：为ture代表用户登录；flase代表用户未登录
    var campusId= '<%=request.getParameter("campusId")%>';
    var shoppingCar=$('.shoppingCar'),buyImmediately=$('.buyImmediately'),isLogin=false;
    var add_Car='/addCartItem.do',data_car={'campusId':campusId};
    var car_message=$('#car_message');
    load_render(data_car);
    tab_pic();
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
    //当鼠标滚动，超过一屏，显示右侧浮动框
    var client_height=$(window).height();
    $(window).scroll(function(){
        var scrollTop=$(window).scrollTop();
        if(client_height/2 <= scrollTop){//开始添加样式
            $('#right_float').fadeIn(500);
        }else{
            $('#right_float').hide();
        }
    })
    function notLog_alert(element,location){
        if(!isLogin){
            frm_logo.show();
            notLogin.fadeIn(300);
            bg_showORhide();
        }else{//登录，那么显示加入购物车消息
            element && element.attr('disabled','disabled').addClass('disabled');
            data_car['num']=$('.signUp_number').val();
            var message=car_message.find('.message');
            var i=$('i',message);
            var b=$('b',message);
            login_post(add_Car,data_car,'',function(data){
                data=JSON.parse(data);
                success(data,function(){
                    if(location){
                        window.location.href='/user_checkout.html';
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
function load_render(data){
    login_post('/campsDetail.do',data,'',function(data){
        data=JSON.parse(data);
        <%--data.serviceSupport='a,b,c';--%>
        data.realCampsImages='a,b,c,d';
        console.log(data);
        success(data,function(){
            $('.title').text(data.campsName);
            $('.orientedPeople').text(data.orientedPeople);
            $('.durationTime').text(data.durationTime);
            $('.deadlineDate').text(data.deadlineDate);
            $('.totalPrice').text(data.totalPrice);
            $('.feature').text(data.feature);
            $('.doneCount').val(data.doneCount || 1);
            if(data.serviceSupport){
                var arr=handle_pic(data.serviceSupport);
                var str='';
                for(var i=0;i<arr.length;i++){
                    str+='<span><i></i>'+arr[i]+'</span>';
                }
                $('.serviceSupport').html(str);
            }
            if(data.realCampsImages){
                var arr=handle_pic(data.realCampsImages);
                var str='';
                for(var i=0;i<arr.length;i++){
                    str+='<li><a href="javascript:void(0)"><img src="'+arr[i]+'" data-src="'+arr[i]+'"></a></li>';
                }
                $('.img_info').attr('src',arr[0]);
                $('.img_list').html(str);
                $('#yingdi_list').html(str);
            }
            $('.campsLocale').val(data.campsLocale);
            $('.campsDesc').val(data.campsDesc);
            $('.courseDesc').val(data.courseDesc);
            $('.activityDesc').val(data.activityDesc);
            $('.campsFoodDesc').val(data.campsFoodDesc);
            if(data.campsFoodsPhotos){
                var arr=handle_pic(data.campsFoodsPhotos);
                var str='';
                for(var i=0;i<arr.length;i++){
                    str+='<img src="'+arr[i]+'">';
                }
                $('.campsFoodsPhotos').html(str);
            }
            $('.campsHotelDesc').val(data.campsHotelDesc);
            if(data.campsHotelPhotos){
                var arr=handle_pic(data.campsHotelPhotos);
                var str='';
                for(var i=0;i<arr.length;i++){
                str+='<img src="'+arr[i]+'">';
                }
                $('.campsHotelPhotos').html(str);
            }
            var traces=data.traces;
            if(traces.length>0){
                var arr=[];
                for(i=0,len=traces.length;i<len;i++){
                    arr.push('<li class="clear"><div class="li_left"><img src="'+handle_pic(traces[i]['tracePhotos'])[0]+'" alt=""></div>');
                    arr.push('<div class="li_right"><span class="color_blur">'+traces[i]['traceName']+'</span><p class="p1_li_right">'+traces[i]['traceDesc']+'</p></div></li>');
                }
                $('.traces').html(arr.join(''));
            }
            $('.feeDesc').val(data.feeDesc);
            yingdi_pic();
        })
    })
}
</script>
</body>
</html>