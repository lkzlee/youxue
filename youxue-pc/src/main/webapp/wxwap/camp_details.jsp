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
    <meta content="telephone=no" name="format-detection">
    <title>_Camplink</title>
    <link rel="stylesheet" href="css/swiper.min.css"/>
    <link rel="stylesheet" href="css/cssReset.css"/>
    <link rel="stylesheet" href="css/camp_details.css"/>
    <link rel="stylesheet" href="css/fsgallery.css" />
</head>
<body>
<div style="position:fixed;top:0;left:0;bottom:0;right:0;background:rgba(0,0,0,0.9);z-index: 999;display: none;" class="camp_details_hidden_Carousel swiper-container">
    <div class="camp_details_hidden_Carousel_content" style="">
        <div class="swiper-wrapper yingdi_list" style="height:auto;"></div>
        <div class="swiper-pagination swiper-p1" style="color:#fff;margin-bottom: 38px;"></div>
    </div>
</div>
<div style="position:relative;" class="g-box">
    <div class="camp_details_Carousel swiper-container" style="height:auto;">
        <div class="swiper-wrapper yingdi_list"></div>
        <div class="swiper-pagination swiper-p2" style="color:#fff;"></div>
    </div>
</div>
<section>
    <div class="camp_details_generalize">
        <h2 class="title"></h2>
        <ul class="travel_feature">
            <li><label style="line-height: 21px;">营地时间</label><span class="active startDate"></span></li>
            <li>行程周期<span class="durationTime"></span>天</li>
            <li>产品特色<span class="feature"></span></li>
        </ul>
        <ul class="cf serviceSupport">
        </ul>
    </div>
    <ul class="camp_details_items">
        <li>
            <p class="j_title">营地介绍</p>
            <div>
                <table style="width:100%;">
                    <caption style="text-align: left;padding-left:3px;">营地</caption>
                    <tr><td>时间 :</td><td><label class="startDate1"></label></td></tr>
                    <tr><td>地点 :</td><td><label class="campsLocale"></label></td></tr>
                    <tr><td>周期 :</td><td><label class="durationTime"></label>天</td></tr>
                    <tr><td>简介 :</td><td class="campsDesc"></td></tr>
                </table>
            </div>
        </li>
        <li>
            <p class="j_title">营地课程&活动</p>
            <div>
                <table style="width:100%;">
                    <caption style="text-align: left;padding-left:3px;">课程内容</caption>
                    <tr><td>概述 :</td><td class="courseDesc"></td></tr>
                </table>
                <table style="width:100%;">
                    <caption style="text-align: left;padding-left:3px;">活动内容</caption>
                    <tr><td>概述 :</td><td class="activityDesc"></td></tr>
                </table>
            </div>
        </li>
        <li>
            <p class="j_title">营地吃住行&安全</p>
            <div class="camp_eat_live_safe">
                <p class="campsFoodDesc"></p>
                <p class="campsFoodsPhotos"></p>
                <p style="padding-top:10px;"></p>
                <p class="campsHotelDesc"></p>
                <p class="campsHotelPhotos"></p>
            </div>
        </li>
        <li>
            <p class="no_arrow_down"><a href="javascript:void(0)" style="display: block;height:100%;" id="j_tripEvent">营地行程</a></p>
        </li>
        <li>
            <p class="j_title">费用</p>
            <div class="feeDesc"></div>
        </li>
        <li>
            <p class="j_title">常见问题</p>
            <div class="questions"></div>
        </li>
    </ul>
    <div class="fixed_button cf">
        <button class="fl j_customer">客服</button>
        <button class="fl j_addCar">加入购物车</button>
        <button class="fl j_immedAddCar">¥ <span class="j_price"></span>立即购买</button>
    </div>
</section>
<div id="camp_trip">
    <div class="icon_close"></div>
    <header>
        <p>此行程安排仅供参考 请以实际行程为准</p>
        <p>免费咨询电话：<a href="tel:4008537517">4008-537-517</a></p>
    </header>
    <section>
        <div class="traces"></div>
    </section>
</div>
<script src="js/jquery-3.1.0.min.js"></script>
<script src="js/imgBig.js"></script>
<script src="js/fastclick.js"></script>
<script src="js/swiper.min.js"></script>
<script src="js/common.js"></script>
<script>
var campusId= '<%=request.getParameter("campusId")%>';
var data_car={'campusId':campusId}
$(function() {
    var isLogin=false;
    getLogin(function(data){
        if(data.result==100){
            isLogin=true;
        }
    })
    load_render();
    FastClick.attach(document.body);
    //下拉显示
    $(".camp_details_items").on("click",".j_title",function(){
        $(this).siblings("div").slideToggle().prev().toggleClass("active_arrow_down");
    })
    $(".travel_feature>li:first-child").on("click","span",function(){
        $(this).addClass("active").siblings(".active").removeClass("active");
    });
    $('#j_tripEvent').click(function(){
        $('.g-box').hide();
        $('#camp_trip').fadeIn(300);
    })
    $('.icon_close').click(function(){
        $('.g-box').show();
        $('#camp_trip').hide();
    })
    $('.j_customer').click(function(){
        window.location.href='tel:4006666666';
    })
    $('.j_addCar').click(function(){
        if(!isLogin){
            window.location.href='/wxwap/login.jsp';
        }else{
            addCarFn($(this));
        }
    })
    $('.j_immedAddCar').click(function(){
        if(!isLogin){
            window.location.href='/wxwap/login.jsp';
        }else{
          addCarFn($(this),true);
        }
    })
    
});
window.onload=function(){
}
function addCarFn(element,location){
    data_car['detailId']=$('.startDate .selected').attr('data-id');
    if(!data_car['detailId']){
        alert('请选择营地时间');
        return false;
    }
    setBtnDisabled(element,false)
    data_car['num']=1;
    login_post('/addCartItem.do',data_car,'',function(data){
        data=JSON.parse(data);
        success(data,function(){
            alertMesageAndHide('加入购物车成功')
            if(location){
                window.location.href='/wxwap/shopping_cart.jsp';
            }
        },function(){
            setBtnDisabled(element,true)
        })
    });
}

function load_render(data){
    login_post('/campsDetail.do',data_car,'',function(data){
        data=JSON.parse(data);
        success(data,function(){
            //解决IOS下标题不能动态修改的bug
            var $body = $('body')
            document.title=data.campsTitle+'_Camplink';
            var $iframe = $('<iframe src="/favicon.ico"></iframe>').on('load', function() {
              setTimeout(function() {
                $iframe.off('load').remove()
              }, 0)
            }).appendTo($body);
            $('.title').text(data.campsTitle);
            $('.orientedPeople').text(data.orientedPeople);
            $('.deadlineDate').text(data.deadlineDate);
            $('.feature').text(data.feature);
            $('.shopCartCount').val(data.shopCartCount || 1);
            $('.doneCount').text(data.doneCount);
            $('.questions').html(data.questions);
            $('.campsLocale').text(data.campsLocale);
            $('.campsDesc').html(data.campsDesc);
            $('.courseDesc').html(data.courseDesc);
            $('.activityDesc').html(data.activityDesc);
            $('.campsFoodDesc').html(data.campsFoodDesc);
            $('.campsHotelDesc').html(data.campsHotelDesc);
            $('.traces').html(data.traceDesc);
            $('.feeDesc').html(data.feeDesc);
            if(data.campsImages){
                var arr=handle_pic(data.campsImages);
                var str='';
                for(var i=0;i<arr.length;i++){
                    str+='<div class="swiper-slide"><img src="'+arr[i]+'" style="width:100%;" alt=""/></div>';
                }
                $('.yingdi_list').html(str);
            }
            if(data.campsDetailList.length>0){
                var arr=[];
                for(var i=0,len=data.campsDetailList.length;i<len;i++){
                    arr.push('<label data-id="'+data.campsDetailList[i]['detailId']+'">'+data.campsDetailList[i]['detailStartTimeStr']+'至'+getDurationSetDate(data.campsDetailList[i])+'</label>');
                    if(i==0){
                        $('.startDate1').html(arr.join(''))
                    }
                }
                $('.startDate').html(arr.join(''));
                $('.totalPrice').text(data.campsDetailList[0]['detailPrice']);
                $('.durationTime').text(data.campsDetailList[0]['duration']);
                $('.j_price').text(data.campsDetailList[0]['detailPrice'])
            }
            if(data.serviceSupport){
                var arr=handle_pic(data.serviceSupport);
                var str='';
                for(var i=0;i<arr.length;i++){
                    var _class=i%2==0?'active_shit_yellow':'active_blue';
                    str+='<li class="fl '+_class+'">'+arr[i]+'</li>';
                }
                $('.serviceSupport').html(str);
            }
            if(data.campsFoodsPhotos){
                var arr=handle_pic(data.campsFoodsPhotos);
                var str='';
                for(var i=0;i<arr.length;i++){
                    str+='<a href="'+arr[i]+'"><img src="'+arr[i]+'"></a>';
                }
                $('.campsFoodsPhotos').html(str);
            }
            if(data.campsHotelPhotos){
                var arr=handle_pic(data.campsHotelPhotos);
                var str='';
                for(var i=0;i<arr.length;i++){
                    str+='<a href="'+arr[i]+'"><img src="'+arr[i]+'"></a>';
                }
                $('.campsHotelPhotos').html(str);
            }
            date_select(data.campsDetailList)
            load_carousel();
            $('.camp_details_items img').fsgallery();//点击图片放大
        })
    })
}
// 根据持续天数设置时间范围
function getDurationSetDate(detailList){
    var now = detailList['detailStartTime']?new Date(detailList['detailStartTime']):new Date();
    now = now.getFullYear() > 0 ? now : new Date(Date.parse(detailList['detailStartTime'].replace(/-/g, "/")));
    var nowTime = now.getTime() ;
    var duration = detailList['duration']*24*60*60*1000;
    return formatDate(nowTime+duration,0);
}
//时间范围选择时间
function date_select(detailList){
    $('.startDate label').click(function(){
        var index=$(this).index();
        $('.startDate1').html($(this).text());
        $('.totalPrice').text(detailList[index]['detailPrice']);
        $('.durationTime').text(detailList[index]['duration']);
        $('.j_price').text(detailList[index]['detailPrice'])
        $(this).addClass('selected').siblings().removeClass('selected');
    })
}
//加载轮播图-ajax获取页面信息后再调用此函数
function load_carousel(){
    var swiper1 = new Swiper('.camp_details_Carousel', {
        pagination: '.swiper-p2',
        paginationClickable: true,
        autoplay: 2500,
        paginationType : 'fraction',
        autoplayDisableOnInteraction : false
    });
    $(".camp_details_Carousel").on("click",".swiper-slide",function(){
        var screen_height=$(".camp_details_hidden_Carousel").css({display:"block"}).on("click",function(){
            $(this).hide();
        }).height();
        var img_height=$(".camp_details_hidden_Carousel img:first").height();
        var swiper2 = new Swiper('.camp_details_hidden_Carousel_content', {
            pagination: '.swiper-p1',
            paginationType: 'fraction'
        });
        $(".camp_details_hidden_Carousel_content").css({marginTop:Math.round((screen_height-img_height)/2)+"px"})
        swiper2.slideTo($(this).index(), 0, false);//切换
    });
}
</script>
</body>
</html>