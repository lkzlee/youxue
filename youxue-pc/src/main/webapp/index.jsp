<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Camplink首页</title>
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <![endif]-->
    <script src="js/prefixfree.min.js"></script>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="css/calendar_1.0.css"/>
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
                <div class="index_icon" id="user_active">
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
<!--选择我的营地-->
<section class="select_camp">
    <div class="width_content">
        <p class="p1">选择我的营地</p>
        <div class="div_input"><input type="text" id="place" class="posElement1" placeholder="想去哪里" autocomplete="on"></div>
        <div class="div_input"><input type="text" id="wantDo" class="posElement2" placeholder="想做什么" autocomplete="on"></div>
        <div class="div_input"><input type="text" class="color_blur startTime" placeholder="出发时间" autocomplete="on"></div>
        <a href="javascript:void(0)" id="search"><i></i></a>
    </div>
</section>
<!--关于Camplink-->
<section class="about_camp">
    <span>关于Camplink</span>
</section>
<!--热门特价-->
<section class="hot_camp">
    <div class="hot_div">
        <div class="hot_title clear">
            <div class="border_hot"></div>
            <div class="div_p_hot">
                <p class="p1">热门&特价</p>
                <p>HOT</p>
            </div>
            <div class="border_hot"></div>
        </div>
        <ul class="hot_list">

        </ul>
    </div>
</section>
<!--主题分类-->
<section class="subjects_camp">
    <div class="subject_div">
        <div class="hot_title clear">
            <div class="border_hot"></div>
            <div class="div_p_hot">
                <p class="p1">主题分类</p>
                <p>subjects</p>
            </div>
            <div class="border_hot"></div>
        </div>
        <ul class="subject_list clear">

        </ul>
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
<script src="js/jquery-3.1.0.min.js"></script>
<script src="js/public.js"></script>
<script src="js/calendar_1.0.js"></script>
<script>
$(function(){
    //选择我的营地
    select_yingdi();
    //特价&热门分类营地列表、主题分类列表
    CampsDetail();
    $('#search').click(function(){
        var place=$('#place').val();
        var wantDo=$('#wantDo').val();
        var startTime=$('.startTime').val();
        var obj={
            'place':place,
            'wantDo':wantDo,
            'startTime':startTime
        };
        auto_submit(obj)
    })
})
function select_yingdi(){
    var con1=[{"categoryId":"","categoryName":"暂无数据"}];
    login_post('/getCategroyList.do?categoryType=3','','GET',function(data){
        data=JSON.parse(data);
        if(data.result==100){
            con1=data.categoryList;
        }
    });
    var con2=[{"categoryId":"","categoryName":"暂无数据"}];
    login_post('/getCategroyList.do?categoryType=5','','GET',function(data){
        data=JSON.parse(data);
        if(data.result==100){
            con2=data.categoryList;
        }
    });
    $('#place').focus(function(ev){
        index_select($(this),con1);
    })
    $('#place').blur(function(ev){
        index_blur($(this));
    })
    $('#wantDo').focus(function(ev){
        index_select($(this),con2);
    });
    $('#wantDo').blur(function(ev){
        index_blur($(this));
    });
}
function CampsDetail(){
    login_post('/getIndexCampsDetail.do','','',function(data){
        data=JSON.parse(data);
        if(data.result==100){
            var hot_list=$('.hot_list');
            var subject_list=$('.subject_list');
            if(data.hotCampsList){//热门
                var val=data.hotCampsList[0];
                str(val)
            }
            if(data.priceCampsList){//特价
                var val=data.priceCampsList[0];
                str(val)
            }
            if(data.subjectList){//主题分类
                var obj=data.subjectList;
    console.log(obj)
                var li=[];
                for(var i=0,len=obj.length;i<len;i++){
                    if(i==5){
                        li.push('<li class="li6"><a href="/info.jsp?categoryType='+val['categoryType']+'"><img src="'+handle_pic(obj[i]['categoryUrl'])[0]+'"><span>'+obj[i]['categoryName']+'</span><i></i></a><div class="div6_li"><a href="'+obj[i]['categoryId']+'">更多More</a></div></li>');
                    }else{
                        li.push('<li><a href="/info.jsp?categoryType='+val['categoryType']+'"><img src="'+handle_pic(obj[i]['categoryUrl'])[0]+'"><span>'+obj[i]['categoryName']+'</span><i></i></a></li>');
                    }
                }
                subject_list.append(li.join(''));
            }
            function str(val){
                var li=[];
                li.push('<li><a href="/info.jsp?campusId='+val['campsId']+'">');
                li.push('<img src="'+handle_pic(val['campsImages'])[0]+'" alt=""><div class="clear">');
                li.push('<span>'+val['campsTitle']+'</span><i>¥'+val['totalPrice']+'</i></div>');
                li.push('<p>'+val['campsDesc']+'</p></a></li>');
                hot_list.append(li.join(''));
            }
        }else{
            alert(data.resultDesc);
        }
    });
}
</script>
</body>
</html>