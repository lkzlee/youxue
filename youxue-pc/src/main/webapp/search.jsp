<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% 
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
%> 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Camplink-搜索结果</title>
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <![endif]-->
    <script src="js/prefixfree.min.js"></script>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/search.css">
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
<section class="search_head">
    <div class="width_content" id="position">
        <a href="index.html">首页</a>
        <%--<a href="#"><i>></i>美国</a>--%>
        <%--<a href="#"><i>></i>所有产品</a>--%>
    </div>
</section>
<section class="search_content width_content">
    <ul class="select_sc">
        <li>
            <div class="left">目&nbsp;&nbsp;的&nbsp;&nbsp;地<i></i></div>
            <div class="right">
                <a href="?campusId=1">不限<i></i></a>
                <a href="?campusId=2">美国<i></i></a>
                <a href="?campusId=3">韩国<i></i></a>
                <a href="#">澳大利亚<i></i></a>
                <a href="#">英国<i></i></a>
                <a href="#">瑞士<i></i></a>
                <a href="#">德国<i></i></a>
                <a href="#">法国</a>
            </div>
        </li>
        <li>
            <div class="left">时间周期<i></i></div>
            <div class="right">
                <a href="#">不限<i></i></a>
                <a href="#">7-14天<i></i></a>
                <a href="#">14-21天<i></i></a>
                <a href="#">21-30天<i></i></a>
                <a href="#">1-2月</a>
            </div>
        </li>
        <li>
            <div class="left">主题类型<i></i></div>
            <div class="right">
                <a href="#">不限<i></i></a>
                <a href="#">文化艺术<i></i></a>
                <a href="#">语言学习<i></i></a>
                <a href="#">野生动物保护<i></i></a>
                <a href="#">体育项目<i></i></a>
                <a href="#">传统营地<i></i></a>
                <a href="#">全真插班</a>
            </div>
        </li>
        <li>
            <div class="left">价格档位<i></i></div>
            <div class="right">
                <a href="#">不限<i></i></a>
                <a href="#">5000-8000<i></i></a>
                <a href="#">8000-10000<i></i></a>
                <a href="#">10000-15000<i></i></a>
                <a href="#">15000-20000<i></i></a>
                <a href="#">20000以上</a>
            </div>
        </li>
        <li>
            <div class="left">出发时间<i></i></div>
            <div class="right">
                <a href="#">不限<i></i></a>
                <a href="#">5月<i></i></a>
                <a href="#">6月<i></i></a>
                <a href="#">7月<i></i></a>
                <a href="#">8月<i></i></a>
                <a href="#">9月<i></i></a>
                <a href="#">10月<i></i></a>
                <a href="#">11月<i></i></a>
                <a href="#">12月<i></i></a>
                <a href="#">1月<i></i></a>
                <a href="#" id="custom_time">自定义出发时间</a>
            </div>
        </li>
    </ul>
    <div class="selected_sc">
        <span class="is_select">您已选择</span>
        <%--<a href="#">美国<i>×</i></a>--%>
        <%--<a href="#">21-30天<i>×</i></a>--%>
        <span class="right">共有<label class="source_count">0</label>个符合选项</span>
    </div>
    <ul class="content_sc">

    </ul>
    <div class="page">

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
<script>
    var public_obj={};
    $(function(){
        var searchContent= '<%=request.getParameter("searchContent")==null?"":request.getParameter("searchContent")%>';
        var campusId='<%=request.getParameter("campusId")==null?"":request.getParameter("campusId")%>';
        if(searchContent){
            <%--alert('searchContent='+searchContent);--%>
            public_obj['searchContent']=searchContent;
        }
        if(campusId){
            <%--alert('campusId='+campusId);--%>
            public_obj['campusId']=campusId;
        }
        $('.selected_sc').on('click','.selected_campusId',function(){
            var id=$(this).attr('data-id');
            $(this).hide()
            delete public_obj['campusId'];
            search_sourch(public_obj);
        })
        change_page('.page',public_obj,function(data){
            public_obj['pageNo']=data.pageNo;
            public_obj['totalPage']=data.totalPage;
            public_obj['totalCount']=data.totalCount;
            search_sourch(public_obj);
        });
        search_sourch(public_obj);
    })
    function search_sourch(){
        public_obj['campusId'] && $('.is_select').after('<a href="javascript:void(0)" data-id="'+public_obj['campusId']+'" class="selected_campusId">'+public_obj['campusId']+'<i>×</i></a>');
        public_obj['searchContent'] && $('#position').append('<a href="javascript:void(0)"><i>></i>'+public_obj['searchContent']+'</a>');
        login_post('/getCampsList.do',public_obj,'',function(data){
            data=JSON.parse(data);
            <%--data.campsList.pageNo=5;--%>
            <%--data.campsList.totalPage=10;--%>
            <%--data.campsList.totalCount=100;--%>
            public_obj['pageNo']=data.campsList.pageNo;
            public_obj['totalPage']=data.campsList.totalPage;
            public_obj['totalCount']=data.campsList.totalCount;
            <%--data.campsList.resultList.campsImages='1,1,2';--%>
            console.log(data);
            var li=[];
            var obj=data.campsList.resultList;
            var len=obj.length;
            success(data,function(){
                if(len>0){
                    $('.source_count').text(len);
                    for(var i=0;i<len;i++){
                        li.push('<li><div class="left_sc"><a href="/info.jsp?campusId='+obj[i]['campsId']+'"><img src="'+handle_pic(obj[i]['campsImages'])[0]+'"></a></div>')
                        li.push('<div class="center_sc"><h2><a href="/info.jsp?campusId='+obj[i]['campsId']+'">'+obj[i]['campsTitle']+'</a></h2>')
                        li.push('<div><a href="#">产品分类<i>></i></a><a href="#">语言学习<i>></i></a><a href="#">10月</a></div>')
                        li.push('<p>'+obj[i]['campsDesc']+'</p></div>')
                        li.push('<div class="right_sc"><span>¥'+obj[i]['totalPrice']+'</span><a href="/info.jsp?campusId='+obj[i]['campsId']+'">点击查看</a></div></li>')
                    }
                }else{
                    li.push('<div class="no_result"><span>抱歉没有搜索结果</span><a href="search.html">随便逛逛</a></div>');
                }
                $('.page').html(pageSet(public_obj).join(''));
                $('.content_sc').html(li.join(''));
            });
        });
    }
</script>
</body>
</html>