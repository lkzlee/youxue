<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% 
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/wxwap/"; 
%>
<!DOCTYPE html>
<html>
<head lang="en">
	<base href="<%=basePath%>"></base>
    <title>我的消息_Camplink</title>
    <meta charset="UTF-8">
    <!-- 设置 viewport -->
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <!-- 兼容国产浏览器的高速模式 -->
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" href="css/cssReset.css"/>
    <link rel="stylesheet" href="css/my_message.css"/>
    <script src="js/isLogin.js"></script>
</head>
<body>
<section>
    <ul>
        
    </ul>
</section>
<script src="js/jquery-3.1.0.min.js"></script>
<script src="js/fastclick.js"></script>
<script src="js/common.js"></script>
<script type="text/javascript">
var public_obj={},isOneLoadEvent=0,lock=true,is_trunPage = true,is_bottom=false,height = 0;
$('body').after('<p id="p-load" style="position:fixed;bottom:0;left:0;width:100%;display:none;text-align:center;line-height:50px;z-index:99999;">消息加载中<img src="/wxwap/img/load.gif" style="position:relative;margin-top:0;"/></p>');
var load_message = $('#p-load');
var load_height = load_message.height() * 2;
$(function(){
    getMessage();
    $('ul').on('click','li',function(){
        var That=$(this);
        var style=That.attr('class');
        if(style=='weidu'){
            login_post('/uc/markmessage.do','messageId='+That.attr('data-id'),'',function(data){
                data=JSON.parse(data);
                console.log(data);
                user_success(data,function(){
                    That.attr('class','yidu').find('p').css('white-space','normal');
                    That.find('img').attr('src','img/ic_messages_yidu.png')
                })
            })
        }else{
            That.find('p').css('white-space','normal');
        }
    })
})
//滑动加载数据
$(window).on('scroll', function () {
    var bottom = $(this).scrollTop();
    if (bottom + load_height >= height && is_trunPage && !is_bottom) {//asyn start
        is_trunPage = false;
        public_obj['pageNo'] = public_obj['pageNo'] + 1;
        if(isDownPage(public_obj)){
            getMessage();
        }else if(isLastPage(public_obj)){
            alertMesageAndHide('没有了…');
        }
    }
});
function getMessage(){
    login_post('/uc/usermessage.do','','',function(data){
        data=JSON.parse(data);
        user_success(data,function(){
            renderMessage(data);
        })
    })
}
var temp=[];//测试数据
function renderMessage(data){
    load_message.show();
    public_obj['pageNo']=data.pageNo;
    public_obj['totalPage']=data.totalPage;
    public_obj['totalCount']=data.totalCount;
    // $('#message_num').text('(共'+data['totalCount']+'条，未读消息'+data['unReadCount']+'条)');
    var resultList=data['resultList'];
    var arr=[];
    var len=resultList.length;
    if(len>0){
        for(var i=0;i<len;i++){
            var cName=resultList[i].readStatus==0?'weidu':'yidu';
            arr.push('<li class="'+cName+'" data-id="'+resultList[i]['messageId']+'"><img src="img/ic_messages_'+cName+'.png" alt=""/>');
            arr.push('<div><h2>'+resultList[i]['messageTitle']+'</h2><p>'+resultList[i]['messageContent']+'</p></div></li>');
        }
        $('ul').append(arr.join(''));
        height = $(document).height() - $(window).height();
    }else{
        $('ul').html('<li style="margin:100px 0;text-align:center;">您目前没有任何消息</li>');
    }
    load_message.fadeOut(600);
    is_trunPage = true;
}
</script>
</body>
</html>