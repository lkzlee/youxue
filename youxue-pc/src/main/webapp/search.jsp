<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% 
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
%> 
<!DOCTYPE html>
<html lang="zn">
<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=1180,inital-scale=1">
    <title>Camplink-搜索结果</title>
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <![endif]-->
    <script src="js/prefixfree.min.js"></script>
    <link rel="stylesheet" href="css/style.css?1">
    <link rel="stylesheet" href="css/search.css?0502">
    <link rel="stylesheet" href="css/calendar_1.0.css"/>
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
<section class="search_head">
    <div class="width_content">
        <a href="index.jsp">首页</a>
        <label id="position"><a href="javascript:void(0)"><i>></i></a></label>
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
                <a href="?timeDuration=" data-param="durationCategoryId" data-value="">不限<i></i></a>
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
                <a href="?priceCategoryId=" data-param="priceCategoryId" data-value="">不限<i></i></a>
            </div>
        </li>
        <li>
            <div class="left">出发时间<i></i></div>
            <div class="right departureMonth">
                <a href="?departureMonth=" data-param="departureCategoryId" data-value="">不限<i></i></a>
                
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
<script src="js/calendar_1.0.js"></script>
<script>
    var public_obj={};
    var searchContent= '<%=request.getParameter("searchContent")==null?"":request.getParameter("searchContent")%>';
    var campusId='<%=request.getParameter("campusId")==null?"":request.getParameter("campusId")%>';
    var localeCategoryId='<%=request.getParameter("localeCategoryId")==null?"":request.getParameter("localeCategoryId")%>';
    var subjectCategoryId='<%=request.getParameter("subjectCategoryId")==null?"":request.getParameter("subjectCategoryId")%>';
    var durationCategoryId='<%=request.getParameter("durationCategoryId")==null?"":request.getParameter("durationCategoryId")%>';
    var priceRange='<%=request.getParameter("priceCategoryId")==null?"":request.getParameter("priceCategoryId")%>';
    var departureCategoryId='<%=request.getParameter("departureCategoryId")==null?"":request.getParameter("departureCategoryId")%>';
    var startdate='<%=request.getParameter("startdate")==null?"":request.getParameter("startdate")%>';
    var enddate='<%=request.getParameter("enddate")==null?"":request.getParameter("enddate")%>';
    $(function(){
        if($.browser.msie && parseInt($.browser.version)<9){
            $('.select_sc').attr('id','radius');
        }
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
        if(durationCategoryId){
            public_obj['durationCategoryId']=durationCategoryId;
        }
        if(priceRange){
            public_obj['priceRange']=priceRange;
        }
        if(departureCategoryId){
            public_obj['departureCategoryId']=departureCategoryId;
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
            if(param=='departureCategoryId'){
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
            delete public_obj['departureCategoryId'];
            delete public_obj['searchContent'];
            search_sourch();
        });
        search_sourch();
    })
    function load_(){
        //3 加载目的地
        load_local(3,function(arr){
            $('.localeCategory').append(publics(arr,'localeCategoryId'));
        });
        //4加载主题类型-想去哪里
        load_local(4,function(arr){
            $('.subjectCategory').append(publics(arr,'subjectCategoryId'));
        });
        //5加载时间周期
        load_local(5,function(arr){
            // console.log(arr)
            $('.timeDuration').append(publics(arr,'durationCategoryId'));
        });
        //6加载时间分类
        load_local(6,function(arr){
            $('#custom_time').before(publics(arr,'departureCategoryId'));
        });
        //7加载价格档位
        load_local(7,function(arr){
            $('.priceRange').append(publics(arr,'priceCategoryId'));
        });
        function publics(con,str){
            param='categoryId';
            var as='';
            for(var i=0,len=con.length;i<len;i++){
                var class1='',i1='';
                if(!con[i]["categoryId"]){
                    class1="active";
                }
                if(i!=len-1){
                    i1='<i></i>';
                }
                as+='<a '+class1+' href="?'+str+'='+con[i][param]+'" data-param="'+str+'" data-value="'+con[i][param]+'">'+con[i]['categoryName']+i1+'</a>';
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
                public_obj['pageNo']=data.campsList.pageNo;
                public_obj['totalPage']=data.campsList.totalPage;
                public_obj['totalCount']=data.campsList.totalCount;
                var li=[];
                var obj=data.campsList.resultList;
                var len=obj.length;
                isSelect();
                if(len>0){
                    $('.source_count').text(public_obj['totalCount']);
                    for(var i=0;i<len;i++){
                        li.push('<li><div class="left_sc"><a href="/info.jsp?campusId='+obj[i]['campsId']+'" target="_blank"><img src="'+handle_pic(obj[i]['campsImages'])[0]+'"></a></div>')
                        li.push('<div class="center_sc"><h2><a href="/info.jsp?campusId='+obj[i]['campsId']+'" target="_blank" title="'+obj[i]['campsTitle']+'">'+obj[i]['campsTitle']+'</a></h2>')
                        li.push('<div class="tag">'+obj[i]['campsSubjectName']+'</div>')
                        li.push('<div class="cont">'+(obj[i]['campsDesc']).replace(/<img [^>]*src=['"]([^'"]+)[^>]*>/gi,"").replace(/<\/?[^>]*>/gim,"")+'</div></div>')
                        li.push('<div class="right_sc"><span>¥'+obj[i]['totalPrice']+'</span><a href="/info.jsp?campusId='+obj[i]['campsId']+'" target="_blank">点击查看</a></div></li>')
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