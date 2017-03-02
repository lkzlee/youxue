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
    <title>æçè®¢å_Camplink</title>
    <link rel="stylesheet" href="css/cssReset.css"/>
    <link rel="stylesheet" href="css/my_order.css"/>
    <script src="js/isLogin.js"></script>
</head>
<body>
<header style="overflow:hidden;" class="j_head">
    <ul class="cf">
        <li data-state="-1" class="active">å¨é¨è®¢å</li>
        <li data-state="0">å¾ä»æ¬¾</li>
        <li data-state="1">å¾å®¡æ ¸</li>
        <li data-state="2">å¾åºè¡</li>
        <li data-state="3">å·²å®æ</li>
        <li data-state="4">å·²åæ¶</li>
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
$('body').after('<p id="p-load" style="position:fixed;bottom:0;left:0;width:100%;display:none;text-align:center;line-height:50px;z-index:99999;">è®¢åå è½½ä¸­<img src="/wxwap/img/load.gif" style="position:relative;margin-top:0;"/></p>');
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
    if(window.confirm('æ¯å¦ç¡®è®¤å é¤è®¢åï¼')){
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
    if(window.confirm('æ¯å¦ç¡®è®¤åæ¶è®¢åï¼')){
        setBtnDisabled($(this),false);
        var That=$(this);
        var orderId=That.attr('data-value');
        login_post('/uc/cancelorder.do','orderId='+orderId,'',function(data){
            data=JSON.parse(data);
            success(data,function(){
                alert('åæ¶è®¢åè¯·èç³»å®¢æï¼400-517-517ï¼å¾ä¸å®¢æç¡®è®¤æ è¯¯åï¼å¯å¨âå·²åæ¶âè®¢åä¸­æ¥çã');
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
//æ»å¨å è½½æ°æ®
$(window).on('scroll', function () {
    var bottom = $(this).scrollTop();
    if (bottom + load_height >= height && is_trunPage && !is_bottom) {//asyn start
        is_trunPage = false;
        public_obj['pageNo'] = public_obj['pageNo'] + 1;
        if(isDownPage(public_obj)){
            get_orderList();
        }else if(isLastPage(public_obj)){
            alertMesageAndHide('æ²¡æäºâ¦');
        }
    }
});
//è·åè®¢ååè¡¨
function get_orderList(){
    load_message.show();
    login_post('/uc/userorder.do',public_obj,'',function(data){
        data=JSON.parse(data);
        user_success(data,function(){
            render_list(data);
        })
    });
}
//æ¸²ææ¶æ¯åè¡¨
function render_list(data){
    console.log(data)
    public_obj['pageNo']=data.pageNo;
    public_obj['totalPage']=data.totalPage;
    public_obj['totalCount']=data.totalCount;
    var arr=[];
    var result=data['resultList'];
    var len=result.length;
    if(len<=0){
        arr.push('<li class="noMessage_border">æ²¡æè®°å½</li>');
    }else{
        for(var i=0;i<len;i++){
            arr.push('<li class="cf order_item_style_cancel">');
            var orderList=result[i]['orderList'];
            for(var j=0,jLen=orderList.length;j<jLen;j++){
                arr.push('<a href="/wxwap/order_info.jsp?orderId='+orderList[j]['orderId']+'"><div class="order_state cf"><p>è®¢åç¼å·ï¼<span>'+orderList[j]['orderId']+'</span></p>');
                arr.push('<p class="non_payment" style="costatuslor:'+getState(orderList[j]['payStatus'])[1]+' !important;">'+getState(orderList[j]['payStatus'])[0]+'</p></div>');
                arr.push('<div class="cf order_intr"><div class="lImg"><img src="'+handle_pic(orderList[j].campsImages)[0]+'"/></div>');
                arr.push('<div class="r_orderInfo"><p class="title">'+orderList[j]['campsTitle']+'</p><p class="order_number">æ°é <span>1</span></p></div></div></a>');
                arr.push('<div class="order_pay_module cf"><i>è´¹ç¨: Â¥<span>'+orderList[j]['totalPrice']+'</span></i><div class="order_pay_button cf">');
                if(orderList[j]['payStatus']==0){
                    arr.push('<button class="cancel_order_button del_order" data-id="'+orderList[j]['orderId']+'">å é¤è®°å½</button>');
                    arr.push('<button class="pay_order_button pay_order" data-id="'+orderList[j]['logicOrderId']+'">å»æ¯ä»</button>');
                }else if(orderList[j]['payStatus']==2){
                    arr.push('<button class="pay_order_button j_BtnCancel" data-id="'+orderList[j]['orderId']+'">åæ¶è®¢å</button>');
                }else if(orderList[j]['payStatus']==3){
                    arr.push('<button class="cancel_order_button del_order" data-id="'+orderList[j]['orderId']+'">å é¤è®°å½</button>');
                    arr.push('<button class="pay_order_button again_pay" data-id="'+orderList[j]['campsId']+'">åæ¬¡è´­ä¹°</button>');
                }else if(orderList[j]['payStatus']==4){
                    arr.push('<button class="cancel_order_button del_order" data-id="'+orderList[j]['orderId']+'">å é¤è®°å½</button>');
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
            return ['å¾ä»æ¬¾','red'];
        case 1:
            return ['å¾å®¡æ ¸','red'];
        case 2:
            return ['å¾åºè¡','blue'];
        case 3:
            return ['å·²å®æ','gray'];
        case 4:
            return ['å·²åæ¶','gray'];
    }
}
</script>
</body>
</html>