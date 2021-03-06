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
    <!-- 设置 viewport -->
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>全部营地_Camplink</title>
    <link rel="stylesheet" href="css/cssReset.css"/>
    <link rel="stylesheet" href="css/camp_lists.css"/>
</head>
<body>
<header>
    <ul class="cf">
        <li class="active" data-load="true">全部营地</li>
        <li>特价营地</li>
        <li>热门营地</li>
    </ul>
</header>
<section></section>
<section></section>
<section></section>
<script src="js/jquery-3.1.0.min.js"></script>
<script src="js/fastclick.js"></script>
<script src="js/common.js"></script>
<script>
var public_obj={};
var is_trunPage = true,is_bottom=false,height = 0;
$('body').append('<div id="p-load" style="position:fixed;bottom:0;left:0;width:100%;display:none;text-align:center;line-height:2rem;z-index:99999;"><img src="img/load.gif"/><p>正在加载中…</p></div>')
var load_message = $('#p-load');
var load_height = load_message.height() * 2;
var localeCategoryId='<%=request.getParameter("localeCategoryId")==null?"":request.getParameter("localeCategoryId")%>';
var subjectCategoryId='<%=request.getParameter("subjectCategoryId")==null?"":request.getParameter("subjectCategoryId")%>';
var timeDuration='<%=request.getParameter("timeDuration")==null?"":request.getParameter("timeDuration")%>';
var priceRange='<%=request.getParameter("priceRange")==null?"":request.getParameter("priceRange")%>';
var departureMonth='<%=request.getParameter("departureMonth")==null?"":request.getParameter("departureMonth")%>';
if(localeCategoryId){
    public_obj['localeCategoryId']=localeCategoryId;
}
if(subjectCategoryId){
    public_obj['subjectCategoryId']=subjectCategoryId;
}
if(timeDuration){
    public_obj['durationCategoryId']=timeDuration;
}
if(priceRange){
    public_obj['priceCategoryId']=priceRange;
}
if(departureMonth){
    public_obj['departureCategoryId']=departureMonth;
}
$(function() {
    FastClick.attach(document.body);
    var section=$('section');
    $("header>ul").on("click","li",function(){
        $(this).addClass("active").show().siblings(".active").removeClass("active");
    	section.hide();
		section.eq($(this).index()).show();
    	if($(this).attr('data-load')!='true'){
    		$("header>ul li").attr('data-load','true');
    		load_message.show();
	        CampsDetail(section,$(this).index());
    	}
    });
    $("section").on("click",".camp_lists_common",function(){
        var campusId=$(this).attr('data-campusId');
        window.location.href='camp_details.jsp?campusId='+campusId;
    })
    search_sourch();
});
//滑动加载数据
$(window).on('scroll', function () {
    var bottom = $(this).scrollTop();
    if (bottom + load_height >= height && is_trunPage && !is_bottom) {//asyn start
        is_trunPage = false;
        public_obj['pageNo'] = public_obj['pageNo'] + 1;
        search_sourch();
    }
});
function search_sourch(){
	load_message.show();
    login_post('/getCampsList.do',public_obj,'',function(data){
        data=JSON.parse(data);
        success(data,function(){
			public_obj['pageNo']=data.campsList.pageNo;
            public_obj['totalPage']=data.campsList.totalPage;
            public_obj['totalCount']=data.campsList.totalCount;
            var li=[];
            var obj=data.campsList.resultList;
            var len=obj.length;
            if(len>0){
	            li.push('<ul>');
                for(var i=0;i<len;i++){
                    li.push('<li class="camp_lists_common" data-campusId="'+obj[i]['campsId']+'"><div class="cf"><div class="cl_img"><img class="fl" src="'+handle_pic(obj[i]['campsImages'])[0]+'"/></div>');
                	li.push('<div class="cl_listInfo"><p>'+obj[i]['campsTitle']+'</p><p>营地主题：<span>'+obj[i]['campsSubjectName']+'</span></p><p>出发时间：<span>'+formatDate(obj[i]['startDate'],0)+'</span></p>');
                	li.push('<p class="order_number"><span>'+obj[i]['totalPrice']+'</span>元/人起 </p></div></div></li>');
                }
	            li.push('</ul>');
	            load_message.fadeOut(600);
	            $('section:first').append(li.join(''));
	            height = $(document).height() - $(window).height();
            }else{
                is_bottom=true;
                load_message.html('没有了…').fadeOut(600);
                $('#mediaUl').animate({'padding-bottom':0},600);
            }
            is_trunPage = true;
            
        })
    });
}
function CampsDetail(section,index){
    login_post('/getIndexCampsDetail.do','','',function(data){
        data=JSON.parse(data);
        success(data,function(){
            var subject_list=$('.subject_list');
            if(data.hotCampsList.length>0){//热门
                var val=data.hotCampsList[0];
                str(val,section.eq(1))
            }
            if(data.priceCampsList.length>0){//特价
                var val=data.priceCampsList[0];
                str(val,section.eq(2))
            }
            function str(val,element){
                var li=[];
                li.push('<li class="camp_lists_common" data-campusId="'+val['campsId']+'"><div class="cf"><div class="cl_img"><img class="fl" src="'+handle_pic(val['campsImages'])[0]+'"/></div>');
            	li.push('<div class="cl_listInfo"><p>'+val['campsTitle']+'</p><p>营地主题：<span>'+val['campsSubjectName']+'</span></p><p>出发时间：<span>'+formatDate(val['startDate'],0)+'</span></p>');
            	li.push('<p class="order_number"><span>'+val['totalPrice']+'</span>元/人起 </p></div></div></li>');
                element.append(li.join(''));
            }
            load_message.fadeOut(600);
        })
    });
}
</script>
</body>
</html>