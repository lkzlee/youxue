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
    <title>我的订单_Camplink</title>
    <link rel="stylesheet" href="css/cssReset.css"/>
    <link rel="stylesheet" href="css/my_order.css"/>
    <script src="js/isLogin.js"></script>
</head>
<body>
<header style="overflow:hidden;" class="j_head">
    <ul class="cf">
        <li data-state="-1" class="active">全部订单</li>
        <li data-state="0">待付款</li>
        <li data-state="1">待审核</li>
        <li data-state="2">待出行</li>
        <li data-state="3">已完成</li>
        <li data-state="4">已取消</li>
    </ul>
    <i class="j_list iconOrder"></i>
</header>
<section>
    <ul>
        
    </ul>
</section>
<script src="js/jquery-3.1.0.min.js"></script>
<script src="js/fastclick.js"></script>
<script src="js/common.js"></script>
<script>
var public_obj={ 'orderType':-1,'pageNo':1},is_trunPage = true,is_bottom=false,height = 0;
$('body').after('<p id="p-load" style="position:fixed;bottom:0;left:0;width:100%;display:none;text-align:center;line-height:50px;z-index:99999;">订单加载中<img src="/wxwap/img/load.gif" style="position:relative;margin-top:0;"/></p>');
var load_message = $('#p-load');
var load_height = load_message.height() * 2;
$(function() {
    FastClick.attach(document.body);
    get_orderList();
    $("header>ul").on("click","li:not('.active')",function(){
        $('section ul').html('');
        $(this).addClass("active").siblings(".active").removeClass("active");
        var state=$(this).attr('data-state') || 0;
        public_obj['pageNo']=1;
        public_obj['orderType']=state;
        get_orderList();
    })
});
$('ul').on('click','.del_order',function(){
    if(window.confirm('是否确认删除订单？')){
        setBtnDisabled($(this),false)
        login_post('/uc/deleteorder.do','orderId='+$(this).attr('data-id')+'','',function(data){
            data=JSON.parse(data);
            user_success(data,function(){
                $('section ul').html('');
                get_orderList()
            })
        });
    }
})
$('ul').on('click','.pay_order',function(){
    login_post('/pay/addTradeOrderById.do','logicOrderId='+$(this).attr('data-id')+'','',function(data){
        setBtnDisabled($(this),false)
        data=JSON.parse(data);
        user_success(data,function(){
            if(data.wxPayParam){
                window.location.href='wxpay.jsp?'+urlFormatObj(data.wxPayParam);
                // window.location.href=data.payUrl
            }
        })
    });
})
$('ul').on('click','.j_BtnCancel',function(){
    if(window.confirm('是否确认取消订单？')){
        setBtnDisabled($(this),false);
        var That=$(this);
        var orderId=That.attr('data-id');
        login_post('/uc/cancelorder.do','orderId='+orderId,'',function(data){
            data=JSON.parse(data);
            success(data,function(){
                alert('取消订单请联系客服：400-517-517，待与客服确认无误后，可在“已取消”订单中查看。');
            })
        })
    }
})
$('ul').on('click','.again_pay',function(){
    window.location.href='/wxwap/camp_details.jsp?campusId='+$(this).attr('data-id');
})
$('.j_list').on('click',function(){
    var j_head=$('.j_head');
    var height=Number(j_head.height())>=80?45:88;
    if(height==45){
        $(this).css('background-image','url(img/ic_pulldown.png)');
    }
    if(height==88){
        $(this).css('background-image','url(img/ic_up.png)');
    }
    j_head.animate({'height':height})
})
//滑动加载数据
$(window).on('scroll', function () {
    var bottom = $(this).scrollTop();
    if (bottom + load_height >= height && is_trunPage && !is_bottom) {//asyn start
        is_trunPage = false;
        public_obj['pageNo'] = public_obj['pageNo'] + 1;
        if(isDownPage(public_obj)){
            get_orderList();
        }else if(isLastPage(public_obj)){
            alertMesageAndHide('没有了…');
        }
    }
});
//获取订单列表
function get_orderList(){
    load_message.show();
    login_post('/uc/userorder.do',public_obj,'',function(data){
        data=JSON.parse(data);
        user_success(data,function(){
            render_list(data);
        })
    });
}
//渲染消息列表
function render_list(data){
    console.log(data)
    public_obj['pageNo']=data.pageNo;
    public_obj['totalPage']=data.totalPage;
    public_obj['totalCount']=data.totalCount;
    var arr=[];
    var result=data['resultList'];
    var len=result.length;
    if(len<=0){
        arr.push('<li class="noMessage_border">没有记录</li>');
    }else{
        for(var i=0;i<len;i++){
            arr.push('<li class="cf order_item_style_cancel">');
            var orderList=result[i]['orderList'];
            for(var j=0,jLen=orderList.length;j<jLen;j++){
                console.log(orderList[j]['status'])
                arr.push('<a href="/wxwap/order_info.jsp?orderId='+orderList[j]['orderId']+'"><div class="order_state cf"><p>订单编号：<span>'+orderList[j]['orderId']+'</span></p>');
                arr.push('<p class="non_payment" style="costatuslor:'+getState(orderList[j]['status'])[1]+' !important;">'+getState(orderList[j]['status'])[0]+'</p></div>');
                arr.push('<div class="cf order_intr"><div class="lImg"><img src="'+handle_pic(orderList[j].campsImages)[0]+'"/></div>');
                arr.push('<div class="r_orderInfo"><p class="title">'+orderList[j]['campsTitle']+'</p><p class="order_number">数量 <span>1</span></p></div></div></a>');
                arr.push('<div class="order_pay_module cf"><i>费用: ¥<span>'+orderList[j]['totalPrice']+'</span></i><div class="order_pay_button cf">');
                if(orderList[j]['status']==0){
                    arr.push('<button class="cancel_order_button del_order" data-id="'+orderList[j]['orderId']+'">删除记录</button>');
                    arr.push('<button class="pay_order_button pay_order" data-id="'+orderList[j]['logicOrderId']+'">去支付</button>');
                }else if(orderList[j]['status']==2||orderList[j]['status']==6||orderList[j]['status']==7){
                    if(orderList[j]['status']==6||orderList[j]['status']==7){
                        arr.push('<button class="disabled pay_order_button j_BtnCancel" data-id="'+orderList[j]['orderId']+'" disabled="disabled">取消订单</button>');
                    }else{
                        arr.push('<button class="pay_order_button j_BtnCancel" data-id="'+orderList[j]['orderId']+'">取消订单</button>');
                    }                    
                }else if(orderList[j]['status']==3){
                    arr.push('<button class="cancel_order_button del_order" data-id="'+orderList[j]['orderId']+'">删除记录</button>');
                    arr.push('<button class="pay_order_button again_pay" data-id="'+orderList[j]['campsId']+'">再次购买</button>');
                }else if(orderList[j]['status']==4||orderList[j]['status']==5){
                    arr.push('<button class="cancel_order_button del_order" data-id="'+orderList[j]['orderId']+'">删除记录</button>');
                }
                arr.push('</div></div>');
            }
            arr.push('</li>');
        }
        height = $(document).height() - $(window).height();
    }
    load_message.fadeOut(600);
    $('section ul').append(arr.join(''));
    is_trunPage = true;
}
function getState(state){
    switch(state){
        case 0:
            return ['待付款','red'];
        case 1:
            return ['待审核','red'];
        case 2:
        case 6:
        case 7:
            return ['待出行','red'];
        case 3:
            return ['交易成功','red'];
        default:
            return ['已取消','red'];
    }
}
</script>
</body>
</html>