/**
 * Created by Administrator on 2016/11/1.
 */
var phoneReg=/^1[3|4|5|7|8][0-9]\d{4,8}$/;
var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
var strReg=/[`~!@#$%^&*()_+=<>?:"{},.\/;'[\]]/im;//验证特殊字符
var dateReg = /^\d{4}-(0[1-9]|1[012])(-\d{2})*$/;
var pwdReg=/^\d{4,6}$/;
var codeReg=/^[a-zA-Z0-9]{0,4}$/;
$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [ o[this.name] ];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
$.extend({
    checkSpechars:function(str,isRequired){
        var value=str.val();
        return (!strReg.test(value)) && (value.length>1 || !isRequired);
    },
    checkPhone:function(str,isRequired){
        var value=str.val();
        if(value.length==11 && phoneReg.test(value)){
            return true;
        }else{
            return false;
        }
    },
    checkEmail:function (str,isRequired){
        var value=str.val();
        if(value.length>5 && emailReg.test(value)){
            return true;
        }else{
            return false;
        }
    }
})
$(function(){
    nav_loginDown();
    hoverShowDown();
    hovChangeStyle();
    new search();
    floatRight();
});
//内容横向导航,滚动定位
function navScrollPosition(obj){
    var nav=obj.element;
    var css=obj.css || {'width':nav.width()};
    var YDheight=nav.height();
    var pos_obj=nav.offset();//left和top
    $(window).scroll(function(){
        var scrollTop=$(window).scrollTop();
        if(pos_obj.top <= scrollTop){//开始添加样式
            nav.addClass('navActive').css(css);
        }else{
            nav.removeClass('navActive');
        }
    })
}
//个人中心的导航下拉
function nav_loginDown(){
    var str='<ul style="display:none;" class="login_down"><li><a href="/user.html">个人信息</a></li><li><a href="/user_payment.html">我的订单</a></li>';
    str+='<li><a href="/user_shoppingCar.html">我的购物车</a></li>';
    str+='</ul>';
    $('.personal_icon').append(str);
    is_login(function(data){
        if(data.result==100){
            $('.login_down').append('<li class="loginDown"><a href="javscript:void(0)" onclick="loginOut()">退出登录</a></li>');
        }
    })
}
//经过导航，改变样式
function hovChangeStyle(){
    $('#user_active').addClass('active');
    var nav_div=$("div:not('.active')",$("nav"));
    nav_div.hover(function(){
        $(this).addClass('active');
    },function(){
        $(this).removeClass('active');
    })
}
//鼠标经过，下拉显示
function hoverShowDown(){
    $('.personal_icon').hover(function(){
        $('.login_down').toggle();
    })
}
//退出登录事件
function loginOut(){
    login_post('/loginOut.do','','GET',function(data){
        data=JSON.parse(data);
        success(data,function(){
            window.location.href='/login.html';
        })
    },1)
}
//定位居中
function posMiddle(element){
    element.css('margin-top',-element.height()/2);
}
//右侧浮动
function floatRight(info){
    createFloatRight();
    EVscrollTop();
    function createFloatRight(){
        var tmp='<a href="javascript:void(0)" class="join_Car shoppingCar"><i></i><span>加入购物车</span></a><a href="javascript:void(0)" class="now_bug buyImmediately"><i></i><span>立即购买</span></a>';
        var element='',right_float=$('#right_float');
        if(right_float.length<=0){
            element=$('<div id="right_float" style="display:none;"><a href="javascript:void(0)" onclick="onlineQQ()" class="online_advice" target="_self"><i></i><span>在线咨询</span></a><a href="javascript:void(0)" class="back_top" onclick="backTop()" target="_self"><i></i><span>返回顶部</span></a></div>');
            $("body").append(element);
        }else{
            // right_float.show()
        }
        if(info){
            $('#right_float').prepend(tmp);
        }
        posMiddle($("#right_float"));
    }
    function EVscrollTop(){
        //当鼠标滚动，超过一屏，显示右侧浮动框
        var client_height=$(window).height();
        $(window).scroll(function(){
            var scrollTop=$(window).scrollTop();
            if(client_height/2 <= scrollTop){//开始添加样式
                $('#right_float').fadeIn(500);
            }else{
                $('#right_float').hide();
            }
        })
    }
}
//返回顶部
function backTop(){
    $('body,html').animate({scrollTop:"0"},500);
    return false;
}
//在线咨询-需要先申请qq在线客服：http://shang.qq.com/v3/widget.html
function onlineQQ(){
    var qq='781581897';
    var link = 'http://wpa.qq.com/msgrd?v=3&uin='+qq+'&site=qq&menu=yes';
    window.open(link,'_blank');
    return false;
}
//判断用户是否登录
function is_login(callback){
    login_post('/uc/userinfo.do','','GET',function(data){
        data=JSON.parse(data);
        callback(data);
    },1)
}
//加载目的地 主题分类 时间周期 等
function load_local(id,callback) {
    var con1=[];
    login_post('/getCategroyList.do?categoryType='+id,'','GET',function(data){
        data=JSON.parse(data);
        success(data,function(){
            if(data.categoryList.length>0){
                con1=data.categoryList;
            }
            callback(con1);
        })
    });
}
//按钮点击后，不可再点击
function btn_disable(That,isDisabled){
    if(isDisabled){
        That.attr('disabled','disabled').addClass('disabled');
    }else{
        That.removeAttr('disabled').removeClass('disabled');
    }
}
//搜索
function search(){
    var search=$('.search');
    var This=this;
    this.input=search.children('input');
    this.btn=search.find('a');
    this.btn.click(function(){
        btn_search();
        return false;
    })
    $(window).keydown(function(ev){
        if(ev.keyCode==13){
            btn_search();
            return false;
        }
    })
    function btn_search(){
        var val=This.input.prop('value');
        if(!val || strReg.test(val)){
            alert('请输入正确的搜索内容');
            return false;
        }
        var obj={
            'searchContent':val
        };
        auto_submit('/search.jsp',$.param(obj),'get');
    }
}
function auto_submit(address,obj,method){
    method=method || 'POST';
    if(method.toLowerCase()=='get'){
        window.location.href=address+'?'+obj;
    }else{
        var frm=$('<form action='+address+' method="post">');
        for(var key in obj){
            frm.append('<input type="text" name="'+key+'" value="'+obj[key]+'">');
        }
        $('body').append(frm);
        frm.submit();
    }
}
//创建一个div,并定位
function index_select(element,con){
    var pos_obj=element.offset();//left和top
    var width=element.width();
    var height=element.height();
    pos_obj.top+=height;
    pos_obj.width=width;

    var li='';
    if(con.length>0){
        for(var i=0,len=con.length;i<len;i++){
            if(!con[i]['categoryId']){
                li+='<li class="disabled" data-value="'+con[i]['categoryId']+'">'+con[i]['categoryName']+'</li>';
            }else{
                li+='<li data-value="'+con[i]['categoryId']+'">'+con[i]['categoryName']+'</li>';
            }
        }
    }else{
        li='<li>正在加载&nbsp;&nbsp;&nbsp;&nbsp;<img src="../img/load.gif" width="25"/> </li>';
    }
    
    var cName=element.attr('class');
    if(!document.getElementById(cName)){
        var nDiv=$("<ul class='posElement' id='"+cName+"' style='position:absolute;top:"+pos_obj.top+"px;left:"+pos_obj.left+"px;width:"+pos_obj.width+"px'>"+li+"</ul>");
        $(document.body).append(nDiv);
    }else{
        $("#"+cName).css({
            top:pos_obj.top,
            left:pos_obj.left,
            width:pos_obj.width
        }).html(li).fadeIn(300);
    }
}
function index_blur(element){
    var cName=element.attr('class');
    var posElement=$("#"+cName);
    posElement.fadeOut(300);
    $('li',posElement).click(function(){
        if($(this).attr('data-value')){
            element.val($(this).html()).attr('data-value',$(this).attr('data-value'));
        }
    })
}
//背景层显示隐藏
function bg_showORhide(){
    //body背景色变透明黑
    if(!document.getElementById('divBG')){
        var divBG=document.createElement("div");
        divBG.id="divBG";
        divBG.style.position="fixed";
        divBG.style.transition="1s";
        divBG.style.zIndex="999";
        divBG.style.top="0";
        divBG.style.left="0";
        divBG.style.width='100%';
        divBG.style.height='100%';
        divBG.style.background='rgba(0,0,0,.5)';
        document.body.appendChild(divBG);
    }else{
        $('#divBG').css('display','block');
    }
}
//提交表单
function login_post(address,data,method,successFn,errorFn,contentType){
    $.ajax({
        url:address,
        type:method || 'post',
        data:data,
        dataType:"json",
        contentType:contentType || 'application/x-www-form-urlencoded; charset=UTF-8',
        success:successFn,
        error:errorFn ||function(XMLHttpRequest, textStatus, errorThrown){
            alert('login_post系统获取异常');
            console.log(XMLHttpRequest.status);
            console.log(XMLHttpRequest.readyState);
            console.log(textStatus);
            return false;
        }
    })
}
//验证对象是否为空
function isEmptyObject(obj){
    for(var item in obj){
        return true;
    }
    return false;
}
function success(data,callback,errback){
    if(data.result==100){
        callback();
    }else{
        errback && errback();
        alert(data.resultDesc);
        return false;
    }
}
//提交图片
function file_post(address,data,method,successFn,errorFn){
    $.ajax({
        url:address,
        type:method || 'post',
        data:data,
        contentType: false,    //不可缺
        processData: false,    //不可缺
        success:successFn,
        error:errorFn ||function(){
            alert('系统获取异常');
        }
    })
}
//根据传入的数值，获取男或女
function getSex(sex){
    return sex==0?'男':'女';
}
/**
 * 图片地址处理，用用逗号把图片地址切割成数组，并返回
 * @returns {pic} 给定数组
 */
function handle_pic(pic){
    if(pic && pic.indexOf(',')!=-1){
        picArr=pic.split(',');
        return picArr;
    }
    return [pic];
}
/**
 * 获取给定日期的周一到周日某一天的时间戳
 * @returns {number} 1-7的数字，默认为1
 * @param {dateTime} 给定日期，默认当天
 */
function getWeek(num,dateTime){
    num=num || 1;
    var now = dateTime?new Date(dateTime):new Date();
    var nowTime = now.getTime() ;
    var day = now.getDay()==0?7:now.getDay();
    var oneDayLong = 24*60*60*1000 ;
    var MondayTime = nowTime - (day-num)*oneDayLong  ;
    return formatDate(MondayTime);
}
/**
 * 获取给定日期的当月或者上个月或上上月的第一天的时间戳
 * @returns {number} 1-7的数字，默认为1
 * @param {dateTime} 给定日期，默认当天
 */
function getMonth(num,dateTime){
    num=num || 1;
    var now = dateTime?new Date(dateTime):new Date();
    var nowTime = now.getTime() ;
    var year = now.getFullYear();
    var month = now.getMonth()+1-num+1;
    if(month<=0){
        month+=12;
        year-=1;
    }
    return formatDate(year+'-'+month+'-'+1);
}
//对象转换字符串
function ObjTrans(obj){
    var str='',i=0;
    for(var item in obj){
        if(i==0){
            str+=item+'='+obj[item];
        }else{
            str+='&'+item+'='+obj[item];
        }
        i++;
    }
    return str;
}
/**
 * 分页
 * @param element 元素
 * @returns {object}
 */
function pageSet(data){
    var pageNo=data.pageNo;
    var totalPage=data.totalPage;
    if(totalPage==0 && data.totalCount==0){
        return [];
    }
    var arr=[];
    if(pageNo==1){
        arr.push('<span class="firstPage current"><</span>');
    }else{
        arr.push('<a class="firstPage" href="#1"><</a>');
    }
    for(var i=1;i<=totalPage;i++){
        if(i==pageNo){
            arr.push('<span class="current">'+i+'</span>');
        }else{
            arr.push('<a href="#'+i+'">'+i+'</a>');
        }
    }
    if(pageNo==totalPage){
        arr.push('<span class="endPage current">></span>');
    }else{
        arr.push('<a class="endPage" href="#'+totalPage+'">></a>');
    }
    return arr;
}
/*
* 分页点击触发，改变数据
* */
function change_page(element,data,callback){
    $(element).on('click','a',function(){
        if($(this).is('.firstPage')){
            data['pageNo']=1;
        }else if($(this).is('.endPage')){
            data['pageNo']=data['totalPage'];
        }else{
            data['pageNo']=Number($(this).text());
        }
        callback && callback(data);
        return false;
    })
}
/**
 * 时间数组转换成正常时间格式
 * @param arrTime
 * @returns {string}
 */
function formatDate(time,style){
    var format='',str='';
    var dateTime=new Date(time);
    switch(style){
        case 0:
            format='-';
            str=dateTime.getFullYear()+format+toDb(dateTime.getMonth()+1)+format+toDb(dateTime.getDate());
            break;
        case 1:
            format=['年','月','日','时','分','秒'];
            str=dateTime.getFullYear()+format[0]+toDb(dateTime.getMonth()+1)+format[1]+toDb(dateTime.getDate())+format[2];
            break;
        case 2:
            str=toDb(dateTime.getDate())+'/'+toDb(dateTime.getMonth()+1)+'/'+dateTime.getFullYear();
            break;
        case 3:
            str=dateTime.getFullYear()+'-'+toDb(dateTime.getMonth()+1)+'-'+toDb(dateTime.getDate())+' '+dateTime.getHours()+':'+dateTime.getMinutes()+':'+dateTime.getSeconds();
            break;
        default:
            str=dateTime.getFullYear()+''+toDb(dateTime.getMonth()+1)+''+toDb(dateTime.getDate());
            break;
    }
    return str;
}
/**
 * 给日期的个位补0
 * @param date
 * @returns 如果10以内,补一个0,10及以上的数字,改成字符串格式输出
 */
function toDb(date){
    return date<10?'0'+date:''+date;
}
