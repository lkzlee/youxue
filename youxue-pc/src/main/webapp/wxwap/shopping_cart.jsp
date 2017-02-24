<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>我的购物车_Camplink</title>
    <link rel="stylesheet" href="css/cssReset.css"/>
    <link rel="stylesheet" href="css/shopping_cart.css"/>
    <script src="js/isLogin.js"></script>
</head>
<body>
<header>
    <p>您购买的营地我们会尽快确认，确认无误后订单状态为“待出行”，若确认购买失败，将会全额退还。咨询热线：<a href="tel:4008-517-517">4008-517-517</a></p>
</header>
<section>
    <ul>
    </ul>
</section>
<footer class="cf">
    <div class="fl all_choice"><p class="common_choice"><i></i></p> 全选 </div>
    <button class="fr" id="j_btnCheck">结算</button>
    <div class="fr shopping_total">合计：￥ <span class="j_money_car">0</span></div>
</footer>
<script src="js/jquery-3.1.0.min.js"></script>
<script src="js/fastclick.js"></script>
<script src="js/common.js"></script>
<script>
FastClick.attach(document.body);
var dataObj=[];
var ul=$('ul'),all_choice=$('.all_choice');
$(function(){
    getCar();
})
ul.on('click','.J_check',function(){
    var index=$(this).index('.J_check')
    var checkElem=$(this).children('i');
    if(!elementIsCheck(checkElem)){
        ChangeOjbCheck(index,true)
    }else{
        ChangeOjbCheck(index,false)
    }
    checkElem.toggleClass('active');
    changeTotal();
})
//数量点击增加
ul.on('click','.sum_button',function(){
    var number=Number($(this).siblings('.j_number').val())+1;
    $(this).siblings('.j_number').val(number);
})
//数量点击减少
ul.on('click','.sub_button',function(){
    var number=Number($(this).siblings('.j_number').val())-1;
    $(this).siblings('.j_number').val(number || 1);
})
ul.on('click','.cart_edit_choice',function(){
    var index=$(this).index('.cart_edit_choice');
    var text=$(this).text();
    var parent=$(this).parents('li.cf');
    var sibligP=$(this).siblings('p');
    var siblingVal=$(this).siblings('.cart_edit_number');
    var check=parent.find('.J_check');
    $(this).text(text=="编辑"?"保存":"编辑");
    sibligP.toggleClass('hidden_active');
    siblingVal.toggleClass('hidden_active');
    if(text=="保存"){
        var id=$(this).attr('data-id');
        dataObj[index]['num']=siblingVal.find('.j_number').val();
        ajax_changeNum(id,dataObj[index]['num'],function(){
            sibligP.find('span').text(dataObj[index]['num']);
            changeTotal();
        });
        
    }
})
ul.on('touchstart','.choice_goods',function(event){
    var That=$(this);
    touchSwiper(event,That,function(swiper){
        var del=That.find('.child_del');
        if(swiper=='left'){
            del.css({'-webkit-transform':'translateX(0px)',"-moz-transform":"translateX(0px)",'transition':'0.5s'})
        }else if(swiper=='right'){
            del.css({'-webkit-transform':'translateX(50px)',"-moz-transform":"translateX(50px)",'transition':'0.3s'})
        }
    });
})
ul.on('click','.child_del',function(){
    var This=$(this);
    var value=$(this).attr('data-id');
    login_post('/deleteCartItem.do','campusId='+value,'',function(data){
        user_success(JSON.parse(data),function(){
            getCar();
        })
    })
})
all_choice.on('click',function(){
    var checkElem=$(this).find('i');
    var isCheck=elementIsCheck(checkElem);
    if(!isCheck){
        $('.J_check').find('i').addClass('active');
    }else{
        $('.J_check').find('i').removeClass('active');
    }
    for(var i=0;i<dataObj.length;i++){
        dataObj[i]['checked']=!isCheck;
    }
    changeTotal();
});
$('#j_btnCheck').on('click',function(){
    var checks=ul.find('i.active');
    if(checks.length>0){
        var arr=[];
        arr.push($('.j_money_car').text());
        checks.each(function(){
            var parent=$(this).parents('li.cf');
            var id=parent.find('.cart_edit_choice').attr('data-id');
            var src=parent.find('img').attr('src');
            var title=parent.find('.title').text();
            var price=Number(parent.find('.j_price').text());
            var num=Number(parent.find('.j_number').val());
            var xj_price=price*num;
            arr.push([id,src,title,price,num,xj_price].join('$$'));
        });
        auto_submit('/wxwap/check_order.jsp',{'orderList':arr},'post');
    }
    return false;
})
//删除购物车的列表后，改变元素
function ajax_changeNum(id,num,callback){
    login_post('/addCartItem.do','campusId='+id+'&num='+num,'',function(data){
        success(JSON.parse(data),function(){
            callback && callback();
        })
    })
}
function changeTotal(){
    var len=0,totalPrice=0;
    for(var i=0;i<dataObj.length;i++){
        if(dataObj[i]['checked']){
            len++;
            totalPrice=accAdd(dataObj[i]['price']*dataObj[i]['num'],totalPrice);
        }
    }
    $('.j_money_car').text(totalPrice);
    if(len==dataObj.length){
        $('.all_choice').find('i').addClass('active');
    }else{
        $('.all_choice').find('i').removeClass('active');
    }
}
function ChangeOjbCheck(index,bool){
    dataObj[index]['checked']=bool;
}
function addChangeObj(index,disk){
    dataObj[index]['num']=accAdd(dataObj[index]['num'],disk)
}
function subChangeObj(index,disk){
    dataObj[index]['num']=accSub(dataObj[index]['num'],disk)
}
function elementIsCheck(checkElem){
    if(checkElem.hasClass('active')){
        return true;
    }
    return false;
}
function getCar(){
    login_post('/shopCartDetailList.do','','',function(data){
        data=JSON.parse(data);
        user_success(data,function(){
            renderCar(data);
        })
    })
}
function renderCar(data){
    var arr=[];
    var source=data.shopCartList;
    if(source.length<=0){
        $('ul').html('<li class="noMessage_border">您的购物车还是空的，赶紧行动吧！</li>')
    }else{
        var price=0,count=0;
        for(var i=0;i<source.length;i++){
            dataObj.push({
                price:source[i].totalPrice,
                num:source[i].cartBuyCount,
                checked:false
            })
            arr.push('<li class="cf"><div class="cf choice_goods"><p class="common_choice J_check" data-price="'+source[i].totalPrice+'" data-count="'+source[i].cartBuyCount+'"><i></i></p>');
            arr.push('<div class="container"><div class="cf move_path">');
            // arr.push('<a href="camp_details.jsp?campusId='+source[i].campsId+'">');
            arr.push('<div class="lImg"><img src="'+handle_pic(source[i].campsImages)[0]+'"/></div><div class="js_calc_width"><p class="title">'+source[i].campsTitle+'</p><p class="price">价格 ¥ <span class="j_price">'+source[i].totalPrice+'</span></p></div>');
            // arr.push('</a>');
            arr.push('<button class="child_del" data-id="'+source[i].campsId+'">删除</button></div></div></div>');
            arr.push('<div class="cf cart_edit"><p>数量<span>'+source[i].cartBuyCount+'</span></p><div class="cart_edit_number hidden_active"><span class="sub_button"></span>');
            arr.push('<input class="j_number" type="number" value="'+source[i].cartBuyCount+'"><span class="sum_button"></span></div><div class="cart_edit_choice" data-id="'+source[i]['campsId']+'">编辑</div></div></li>');
        }
        $('ul').html(arr.join(''));
    }
}
</script>
</body>
</html>