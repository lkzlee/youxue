<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zn">
<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=1180,inital-scale=1">
    <title>Camplink资讯</title>
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <![endif]-->
    <script src="js/prefixfree.min.js"></script>
    <link rel="stylesheet" href="css/style.css?1">
    <link rel="stylesheet" href="css/news.css">
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
                <div class="news_icon " id="user_active">
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
<section class="phoneWidth">
<section class="content width_content clear">
    <section class="left">
        <p class="active"><a href="/news.html" style="letter-spacing:0;">Camaplink资讯<i></i></a></p>
    </section>
    <section class="right">
        <!-- <div class="top_img">
            <img src="img/news1.jpg" alt="">
        </div> -->
        <div class="bottom_content news_content">
            <h1></h1>
            <div class="j_content">
                
            </div>
        </div>
    </section>
</section>
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
<script type="text/javascript">
var newsId= '<%=request.getParameter("newsId")==null?"":request.getParameter("newsId")%>';
$(function(){
    load_newsList();
})
function load_newsList(){
    login_post('/newsContent.do','newsId='+newsId,'',function(data){
        data=JSON.parse(data);
        if(data.newsTitle){
            $('h1').text(data.newsTitle);
            $('.j_content').html(data.newsContent);
        }
    })
}
</script>
</body>
</html>