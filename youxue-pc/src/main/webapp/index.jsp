<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zn">
<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=1180,inital-scale=1">
    <title>Camplink首页</title>
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <![endif]-->
    <script src="js/prefixfree.min.js"></script>
    <link rel="stylesheet" href="css/style.css?1">
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="css/calendar_1.0.css"/>
    <base target='_blank'>
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
                <div class="index_icon" id="user_active">
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
<!--选择我的营地-->
<section class="select_camp phoneWidth">
    <div class="width_content">
        <p class="p1">选择我的营地</p>
        <div class="div_input"><input type="text" id="place" class="posElement1" placeholder="想去哪里" autocomplete="off"></div>
        <div class="div_input"><input type="text" id="wantDo" class="posElement2" placeholder="想做什么" autocomplete="off"></div>
        <div class="div_input"><input type="text" class="startTime" placeholder="出发时间" autocomplete="off"></div>
        <a href="javascipt:void(0)" id="search"><i></i></a>
    </div>
</section>
<!--关于Camplink-->
<section class="about_camp phoneWidth" onclick="window.open('/about.html')">
    <span>关于Camplink</span>
</section>
<!--热门特价-->
<section class="hot_camp phoneWidth">
    <div class="hot_div">
        <div class="hot_title clear">
            <div class="border_hot"></div>
            <div class="div_p_hot">
                <p class="p1">热门&特价</p>
                <p>HOT</p>
            </div>
            <div class="border_hot"></div>
        </div>
        <ul class="hot_list clear">

        </ul>
    </div>
</section>
<!--主题分类-->
<section class="subjects_camp phoneWidth">
    <div class="subject_div">
        <div class="hot_title clear">
            <div class="border_hot"></div>
            <div class="div_p_hot">
                <p class="p1">主题分类</p>
                <p>SUBJECTRS</p>
            </div>
            <div class="border_hot"></div>
        </div>
        <ul class="subject_list clear">

        </ul>
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
<ul class='posElement' id='posElement1' style='position:absolute;display:none;'></ul>
<ul class='posElement' id='posElement2' style='position:absolute;display:none;'></ul>
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
<script src="js/calendar_1.0.js"></script>
<script>
$(function(){
    navScrollPosition({ 'element':$('.head_nav')});
    changeEvent($('.startTime'));
    //选择我的营地
    event_yingdi();
    //特价&热门分类营地列表、主题分类列表
    CampsDetail();
})
function event_yingdi(){
    var con1={},con2={};
    //3 加载目的地
    load_local(3,function(arr){
        con1=arr;
    });
    //4加载主题类型-想去哪里
    load_local(4,function(arr){
        con2=arr;
    });
    $('#place').focus(function(){
        index_select($(this),con1);
    }).blur(function(){
        index_blur($(this));
    })
    $('#wantDo').focus(function(){
        index_select($(this),con2);
    }).blur(function(){
        index_blur($(this));
    });
    $('#search').click(function(){
        var place=$('#place').attr('data-value');
        var wantDo=$('#wantDo').attr('data-value');
        var startdate=$('.startTime').attr('startdate');
        var enddate=$('.startTime').attr('enddate');
        var obj={
            'localeCategoryId':place,
            'subjectCategoryId':wantDo,
            'startdate':startdate,
            'enddate':enddate
        };
        auto_submit('/search.jsp',$.param(obj),'get')
        return false;
    })
}
function CampsDetail(){
    login_post('/getIndexCampsDetail.do','','',function(data){
        data=JSON.parse(data);
        // console.log(data)
        success(data,function(){
            var hot_list=$('.hot_list');
            var subject_list=$('.subject_list');
            if(data.hotCampsList.length>0){//热门
                var arr=[];
                for(var i=0,len=data.hotCampsList.length;i<len;i++){
                    arr.push(str(data.hotCampsList[i]));
                }
                hot_list.append(arr.join(''))
            }
            if(data.priceCampsList.length>0){//特价
                var arr=[];
                for(var i=0,len=data.priceCampsList.length;i<len;i++){
                    arr.push(str(data.priceCampsList[i]));
                }
                hot_list.append(arr.join(''))
            }
            if(data.subjectList.length>0){//主题分类
                var obj=data.subjectList;
                var li=[];
                for(var i=0,len=obj.length;i<len;i++){
                    var liNo=(i+1)%3==0?'li3':'';
                    if(i==len-1){
                        li.push('<li class="li6"><a href="/search.jsp?subjectCategoryId='+obj[i]['categoryId']+'"><div><img src="'+handle_pic(obj[i]['categoryUrl'])[0]+'"></div><span>'+obj[i]['categoryName']+'</span><i></i></a><div class="div6_li"><a href="/search.jsp">更多More</a></div></li>');
                    }else{
                        li.push('<li class="'+liNo+'"><a href="/search.jsp?subjectCategoryId='+obj[i]['categoryId']+'"><div><img src="'+handle_pic(obj[i]['categoryUrl'])[0]+'"></div><span>'+obj[i]['categoryName']+'</span><i></i></a></li>');
                    }
                }
                subject_list.append(li.join(''));
            }
            function str(val){
                var arr=[];
                arr.push('<li><a href="info.jsp?campusId='+val['campsId']+'">');
                arr.push('<div class="hotImgs"><img src="'+handle_pic(val['campsImages'])[0]+'" onclick=show("info.jsp?campusId='+val['campsId']+'")></div><div class="clear">');
                arr.push('<span>'+val['campsName']+'</span><i>¥'+val['totalPrice']+'</i></div>');
                arr.push('<p>'+cutstr(setContent(val['campsDesc']),100)+'</p></a></li>');
                return arr.join('');
                // hot_list.append(li.join(''));
            }
            loadSlide($('.hot_list'));
            if($.browser.msie && parseInt($.browser.version) <=8){//解决ie8\ie7不支持nth-child的bug
                $('.hot_list li:nth-child(3n+1)').css("margin-left",0)
            }
        })
    });
}
function show(url){
    window.location.href=url;
}
function loadSlide(slideElement,num){
    num=num||3;
    var biZhiDelayLoadImg = slideElement.children('li');
    var biZhiDelayLoadImgLength = biZhiDelayLoadImg.length;
    // var _focus_num = $(".smallUl > li").length;
    if(biZhiDelayLoadImgLength<=num){
        return false;
    }
    var _focus_num = Math.ceil(biZhiDelayLoadImgLength/num);
    var _focus_li_length = slideElement.width();
    slideElement.css('width',_focus_num+'00%')
    var _focus_direction = true;
    var _focus_pos = 0;
    var _focus_max_length = _focus_num * _focus_li_length;
    var _focus_dsq = null;
    var _focus_lock = true;
    autoExecAnimate();
    function autoExecAnimate() {
        // console.log(new Date().getSeconds())
        var moveLen = _focus_pos * _focus_li_length;
        slideElement.animate({
                'margin-left': "-" + moveLen + "px"
            },
            3000);
        if (_focus_pos == (_focus_num - 1)) {
            _focus_direction = false
        }
        if (_focus_pos == 0) {
            _focus_direction = true
        }
        if (_focus_direction) {
            _focus_pos++
        } else {
            _focus_pos--
        }
    }
    _focus_dsq = setInterval(autoExecAnimate, 6000);
    slideElement.hover(function(){
        clearInterval(_focus_dsq)
    },function(){
        _focus_dsq = setInterval(autoExecAnimate, 6000);
    })    
}
</script>
</body>
</html>