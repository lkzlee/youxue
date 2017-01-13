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
    <div class="width_content">
        <a href="index.html">首页</a>
        <label id="position"><a href="javascript:void(0)"><i>></i>搜索</a></label>
    </div>
</section>
<section class="search_content width_content">
    <ul class="select_sc">
        <li>
            <div class="left">目&nbsp;&nbsp;的&nbsp;&nbsp;地<i></i></div>
            <div class="right localeCategory">
                <a href="?localeCategoryId=" data-param="localeCategoryId" data-value="">不限<i></i></a>
            </div>
        </li>
        <li>
            <div class="left">时间周期<i></i></div>
            <div class="right timeDuration">
                <a href="?timeDuration=" data-param="timeDuration" data-value="">不限<i></i></a>
                <a href="?timeDuration=7-14" data-param="timeDuration" data-value="7-14">7-14天<i></i></a>
                <a href="?timeDuration=14-21" data-param="timeDuration" data-value="14-21">14-21天<i></i></a>
                <a href="?timeDuration=21-30" data-param="timeDuration" data-value="21-30">21-30天<i></i></a>
                <a href="?timeDuration=30-60" data-param="timeDuration" data-value="30-60">1-2月</a>
            </div>
        </li>
        <li>
            <div class="left">主题类型<i></i></div>
            <div class="right subjectCategory">
                <a href="?subjectCategoryId=" data-param="subjectCategoryId" data-value="">不限<i></i></a>
            </div>
        </li>
        <li>
            <div class="left">价格档位<i></i></div>
            <div class="right priceRange">
                <a href="?priceRange=" data-param="priceRange" data-value="">不限<i></i></a>
                <a href="?priceRange=5000-8000" data-param="priceRange" data-value="5000-8000">5000-8000<i></i></a>
                <a href="?priceRange=8000-10000" data-param="priceRange" data-value="8000-10000">8000-10000<i></i></a>
                <a href="?priceRange=10000-15000" data-param="priceRange" data-value="10000-15000">10000-15000<i></i></a>
                <a href="?priceRange=15000-20000" data-param="priceRange" data-value="15000-20000">15000-20000<i></i></a>
                <a href="?priceRange=20000-1000000" data-param="priceRange" data-value="20000-1000000">20000以上</a>
            </div>
        </li>
        <li>
            <div class="left">出发时间<i></i></div>
            <div class="right departureMonth">
                <a href="?departureMonth=" data-param="departureMonth" data-value="">不限<i></i></a>
                <a href="?departureMonth=7" data-param="departureMonth" data-value="7">7月<i></i></a>
                <a href="?departureMonth=8" data-param="departureMonth" data-value="8">8月<i></i></a>
                <a href="?departureMonth=9" data-param="departureMonth" data-value="9">9月<i></i></a>
                <a href="?departureMonth=10" data-param="departureMonth" data-value="10">10月<i></i></a>
                <a href="?departureTime=" data-param="departureTime" data-value="" id="custom_time">自定义出发时间</a>
            </div>
        </li>
    </ul>
    <div class="selected_sc">
        <span>您已选择</span>
        <label  class="is_select"></label>
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
<script src="js/calendar_1.0.js"></script>
<script>
    var public_obj={};
    var searchContent= '<%=request.getParameter("searchContent")==null?"":request.getParameter("searchContent")%>';
    var campusId='<%=request.getParameter("campusId")==null?"":request.getParameter("campusId")%>';
    var localeCategoryId='<%=request.getParameter("localeCategoryId")==null?"":request.getParameter("localeCategoryId")%>';
    var subjectCategoryId='<%=request.getParameter("subjectCategoryId")==null?"":request.getParameter("subjectCategoryId")%>';
    var timeDuration='<%=request.getParameter("timeDuration")==null?"":request.getParameter("timeDuration")%>';
    var priceRange='<%=request.getParameter("priceRange")==null?"":request.getParameter("priceRange")%>';
    var departureMonth='<%=request.getParameter("departureMonth")==null?"":request.getParameter("departureMonth")%>';
    var startdate='<%=request.getParameter("startdate")==null?"":request.getParameter("startdate")%>';
    var enddate='<%=request.getParameter("enddate")==null?"":request.getParameter("enddate")%>';
    $(function(){
        load_();
        if(searchContent){
            public_obj['searchContent']=searchContent;
        }
        if(campusId){
            public_obj['campusId']=campusId;
        }
        if(localeCategoryId){
            public_obj['localeCategoryId']=localeCategoryId;
        }
        if(subjectCategoryId){
            public_obj['subjectCategoryId']=subjectCategoryId;
        }
        if(timeDuration){
            public_obj['timeDuration']=timeDuration;
        }
        if(priceRange){
            public_obj['priceRange']=priceRange;
        }
        if(departureMonth){
            public_obj['departureMonth']=departureMonth;
        }
        if(startdate && enddate){
            public_obj['departureTime']=startdate.split('-').join('')+'-'+enddate.split('-').join('');
        }
        change_page('.page',public_obj,function(data){
            public_obj['pageNo']=data.pageNo;
            public_obj['totalPage']=data.totalPage;
            public_obj['totalCount']=data.totalCount;
            search_sourch();
        });
        $('.is_select').on('click','a',function(){
            $(this).hide();
            var param=$(this).attr('data-param');
            delete public_obj[param];
            search_sourch();
            return false;
        })
        $('.select_sc').on('click','a',function(){
            var param=$(this).attr('data-param');
            var value=$(this).attr('data-value');
            public_obj[param]=value;
            if(param=='departureMonth'){
                delete public_obj['departureTime'];
            }
            delete public_obj['searchContent'];
            public_obj['pageNo']=1;
            search_sourch();
            return false;
        });
        changeEvent($('#custom_time'),function(data1,data2){
            startdate=data1;
            enddate=data2;
            public_obj['departureTime']=startdate.split('-').join('')+'-'+enddate.split('-').join('');
            public_obj['pageNo']=1;
            delete public_obj['departureMonth'];
            delete public_obj['searchContent'];
            search_sourch();
        });
        search_sourch();
    })
    function load_(){
        //加载下拉
        load_local(function(arr){
            $('.localeCategory').append(publics(arr,'localeCategoryId'));
        });
        load_subject(function(arr){
            $('.subjectCategory').append(publics(arr,'subjectCategoryId'));
        });
        function publics(con,str){
            var as='';
            for(var i=0,len=con.length;i<len;i++){
                var class1='',i1='';
                if(!con[i]["categoryId"]){
                    class1="active";
                }
                if(i!=len-1){
                    i1='<i></i>';
                }
                as+='<a '+class1+' href="?'+str+'='+con[i]['categoryId']+'" data-param="'+str+'" data-value="'+con[i]['categoryId']+'">'+con[i]['categoryName']+i1+'</a>';
            }
            return as;
        }
    }
    //根据public_obj对象，修改元素“您已选择”
    function isSelect(){
        var arr=[];
        for(var item in public_obj){
            if(item=='departureTime'){
                arr.push('<a href="?'+item+'='+public_obj[item]+'" data-param="'+item+'" data-value="'+public_obj[item]+'" class="selected_campusId">'+startdate+'至'+enddate+'<i>×</i></a>')
            }else if(public_obj[item] && item!='pageNo' && item!='totalPage' && item!='totalCount' && item!='searchContent'){
                var value=$('a[data-param="'+item+'"][data-value="'+public_obj[item]+'"]').html();
                arr.push('<a href="?'+item+'='+public_obj[item]+'" data-param="'+item+'" data-value="'+public_obj[item]+'" class="selected_campusId">'+value+'<i>×</i></a>')
            }
        }
        $('.is_select').html(arr.join(''));
    }
    function search_sourch(){
        $('.content_sc').html('<div style="margin:20px auto;text-align:center;"><img src="img/load.gif"/><p>正在加载中…</p></div>');
        $('#position').html(public_obj['searchContent']?'<a href="javascript:void(0)"><i>></i>'+public_obj['searchContent']+'</a>':'<a href="javascript:void(0)"><i>></i>搜索</a>');
        login_post('/getCampsList.do',public_obj,'',function(data){
            data=JSON.parse(data);
            success(data,function(){
//                data.campsList.pageNo=5;
//                data.campsList.totalPage=10;
//                data.campsList.totalCount=100;
//                data.campsList.resultList.campsImages='1,1,2';
                public_obj['pageNo']=data.campsList.pageNo;
                public_obj['totalPage']=data.campsList.totalPage;
                public_obj['totalCount']=data.campsList.totalCount;
                var li=[];
                var obj=data.campsList.resultList;
                var len=obj.length;
                isSelect();
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
                    li.push('<div class="no_result"><span>抱歉没有搜索结果</span><a href="/">随便逛逛</a></div>');
                }
                $('.page').html(pageSet(public_obj).join(''));
                $('.content_sc').html(li.join(''));
            });
        });
    }
</script>
</body>
</html>