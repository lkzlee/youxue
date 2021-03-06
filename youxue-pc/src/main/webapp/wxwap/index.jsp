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
    <title>Camplink首页</title>
    <link rel="stylesheet" href="css/swiper.min.css"/>
    <link rel="stylesheet" href="css/cssReset.css"/>
    <link rel="stylesheet" href="css/camp.css"/>
</head>
<body>
<header>
    <div class="swiper-container">
        <div class="swiper-wrapper">
            <div class="swiper-slide"><img src="img/banner1.jpg" alt="" width="100%" ></div>
            <div class="swiper-slide"><img src="img/banner1.jpg" alt="" width="100%" ></div>
            <div class="swiper-slide"><img src="img/banner1.jpg" alt="" width="100%" ></div>
        </div>
        <div class="swiper-pagination"></div>
    </div>
</header>
<section>
    <ul>
        <li class="camp_common cf">
            <img class="fl" src="img/ic_located_normal.png" alt=""/><p class="fl">目的地</p> <img class="fr" src="img/ic_packup.png" alt=""/><span class="fr j_param">不限</span>
            <div class="camp_hidden_common">
                <h2>选择您要前往的目的地：</h2>
                <ul class="cf localeCategory"></ul>
                <button>确定</button>
            </div>
        </li>
        <li class="camp_common cf">
            <img class="fl" src="img/ic_located_normal.png" alt=""/><p class="fl">时间周期</p> <img class="fr" src="img/ic_packup.png" alt=""/><span class="fr j_param">不限</span>
            <div class="camp_hidden_common">
                <h2>选择您期望的营地时间周期：</h2>
                <ul class="cf timeDuration"></ul>
                <button>确定</button>
            </div>
        </li>
        <li class="camp_common cf">
            <img class="fl" src="img/ic_level_normal.png" alt=""/><p class="fl">主题类型</p> <img class="fr" src="img/ic_packup.png" alt=""/><span class="fr j_param">不限</span>
            <div class="camp_hidden_common">
                <h2>选择您期望的营地主题：</h2>
                <ul class="cf subjectCategory"></ul>
                <button>确定</button>
            </div>
        </li>
        <li class="camp_common cf">
            <img class="fl" src="img/ic_price_normal.png" alt=""/><p class="fl">价格档位</p> <img class="fr" src="img/ic_packup.png" alt=""/><span class="fr j_param">不限</span>
            <div class="camp_hidden_common">
                <h2>选择您期望的营地价格档位：</h2>
                <ul class="cf priceRange"></ul>
                <button>确定</button>
            </div>
        </li>
        <li class="camp_common cf">
            <img class="fl" src="img/ic_date_normal.png" alt=""/><p class="fl">出发时间</p> <img class="fr" src="img/ic_packup.png" alt=""/><span class="fr j_param">不限</span>
            <div class="camp_hidden_common">
                <h2>选择您期望的出发时间：</h2>
                <ul class="cf departureMonth"></ul>
                <button>确定</button>
            </div>
        </li>
    </ul>
    <button type="button">查看营地</button>
</section>
<script src="js/jquery-3.1.0.min.js"></script>
<script src="js/fastclick.js"></script>
<script src="js/swiper.min.js"></script>
<script src="js/common.js"></script>
<script>
$(function() {
    load_();
    FastClick.attach(document.body);
    $("section>ul").on("click",".camp_common",function(){
        $(this).find(".camp_hidden_common").fadeIn().find("button").on("click",function(e){
            e.stopPropagation();
            $(this).parent().fadeOut();
        });
    });
    $(".camp_hidden_common").on("click","li",function(){
        $(this).addClass("active").siblings(".active").removeClass("active");
        var _span=$(this).parents('.camp_hidden_common').siblings('.j_param');
        _span.html($(this).html()).attr('data-param',$(this).attr('data-param'));
        // if($(this).attr('data-value')){
            _span.attr('data-value',$(this).attr('data-value'))
        // }
    })
    var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        paginationClickable: true,
        autoplay: 3000
    });
    $("section>button").on("click",function(){
        var obj={'localeCategoryId':'','subjectCategoryId':'','timeDuration':'','categoryType':'','priceRange':''};
        $('.j_param').each(function(){
            if($(this).attr('data-value')){
                obj[$(this).attr('data-param')]=$(this).attr('data-value');
            }
        })
        auto_submit('camp_lists.jsp',$.param(obj),'get');
        // window.location.href="camp_lists.html";
    })
});
function load_(){
    //3 加载目的地
    load_local(3,function(arr){
        $('.localeCategory').append(publics(arr,'localeCategoryId'));
    });
    //4加载主题类型-想去哪里
    load_local(4,function(arr){
        $('.subjectCategory').append(publics(arr,'subjectCategoryId'));
    });
    // //5加载时间周期
    load_local(5,function(arr){
        $('.timeDuration').append(publics(arr,'timeDuration'));
    });
    // //6加载时间分类
    load_local(6,function(arr){
        $('.departureMonth').append(publics(arr,'departureMonth'));
    });
    // //7加载价格档位
    load_local(7,function(arr){
        $('.priceRange').append(publics(arr,'priceRange'));
    });
    function publics(con,param){
        var arr=[];
        for(var i=0,len=con.length;i<len;i++){
            arr.push('<li data-param="'+param+'" data-value="'+con[i]['categoryId']+'">'+con[i]['categoryName']+'</li>');
        }
        arr.push('<li data-param="'+param+'" data-value="">不限</li>');
        return arr.join('');
    }
}
</script>
</body>
</html>