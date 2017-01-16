/**
 * Created by Administrator on 2016/11/1.
 */
var phoneReg=/^1[3|4|5|7|8][0-9]\d{4,8}$/;
var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
var strReg=/[`~!@#$%^&*()_+=<>?:"{},.\/;'[\]]/im;//验证特殊字符
var dateReg = /^\d{4}-(0[1-9]|1[012])(-\d{2})*$/;
var pwdReg=/^\d{4,6}$/;
var codeReg=/^[a-z0-9]{0,4}$/;
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
$(function(){
    //鼠标经过导航，改变样式
    var nav_div=$("div",$("nav"));
    nav_div.hover(function(){
        $(this).addClass('active');
    },function(){
        $(this).removeClass('active');
    })
    new search();
});
//判断用户是否登录
function is_login(callback){
    login_post('/uc/userinfo.do','','',function(data){
        data=JSON.parse(data);
        callback(data);
    })
}
//加载目的地
function load_local(callback) {
    var con1=[{"categoryId":"","categoryName":"暂无数据"}];
    login_post('/getCategroyList.do?categoryType=3','','GET',function(data){
        data=JSON.parse(data);
        success(data,function(){
            if(data.categoryList.length>0){
                con1=data.categoryList;
            }
            callback(con1);
        })
    });
}
//加载主题类型
function load_wantDo(callback){
    var con1=[{"categoryId":"","categoryName":"暂无数据"}];
    login_post('/getCategroyList.do?categoryType=5','','GET',function(data){
        data=JSON.parse(data);
        success(data,function(){
            if(data.categoryList.length>0){
                con1=data.categoryList;
            }
            callback(con1);
        })
    });
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
    for(var i=0,len=con.length;i<len;i++){
        if(!con[i]['categoryId']){
            li+='<li class="disabled" data-value="'+con[i]['categoryId']+'">'+con[i]['categoryName']+'</li>';
        }else{
            li+='<li data-value="'+con[i]['categoryId']+'">'+con[i]['categoryName']+'</li>';
        }
    }
    var cName=element.attr('class');
    if(!document.getElementById(cName)){
        if(!li){
            li='<li>正在加载&nbsp;&nbsp;&nbsp;&nbsp;<img src="../img/load.gif" width="25"/> </li>';
        }
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
        error:errorFn ||function(data,error){
            alert('系统获取异常');
            console.log(data);
            console.log(error);
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
//登录
function logo_user(callback){
    var phone_input=$('.phone_input');
    var pwd_input=$('.pwd_input');
    var pwd_message=pwd_input.siblings('label');
    var code_input=$('.code_input');
    var code_message=code_input.siblings('label');
    $('.imgCode').click(function(){
        $("#imgCode").attr('src',"/verifyCode.do?"+(Math.random()*100));
    })
    $('.aCode').click(function(){
        var cur_time=120;
        var phone_value=phone_input.val();
        if(!veriPhone()){
            return false;
        }
        var data={
            phone:phone_value
        };
        var This=$(this);
        $(this).attr('disabled','disabled').addClass('disable');
        var timer=setInterval(function(){
            cur_time--;
            if(cur_time>0){
                This.val(cur_time+'秒后重新获取');
            }else{
                clearInterval(timer);
                This.removeAttr('disabled').val('获取验证码').removeClass('disable');
            }
        },1000);
        login_post('mobileCode.do',data,'',successFn);
        function successFn(json_data){
            var data=JSON.parse(json_data);
            if(data.result==100){
                pwd_message.show().html('验证码已成功发送');
            }else{
                pwd_message.show().html(data.resultDesc);
                clearInterval(timer);
                This.removeAttr('disabled').val('获取验证码').removeClass('disable');
            }
        }
    });
    $('#submit_login').click(function(){
        if(!veriPhone()){
            return false;
        }
        var pwd_value=pwd_input.val();
        if(pwd_value.length>3 && pwd_value.length<7 && pwdReg.test(pwd_value)){//验证没有问题
            pwd_message.hide().html('');
        }else{
            pwd_input.focus();
            pwd_message.show().html('请输入正确的动态密码');
            return false;
        }
        var code_value=code_input.val();
        if(code_value.length==4 && codeReg.test(code_value)){//验证没有问题
            code_message.hide().html('');
        }else{
            code_input.focus();
            code_message.show().html('请输入正确的验证码');
            return false;
        }
        var This=$(this);
        This.attr('disabled','disabled').addClass('disable');
        var phone_value=phone_input.val();
        var data={
            'mobile':phone_value,
            'phoneCode':pwd_value,
            'imgCode':code_value,
            'autoLog':$('.autoLog').is(':checked')
        };
        login_post('login.do',data,'',successFn);
        function successFn(json_data){
            var data=JSON.parse(json_data);
            if(data.result==100){
                callback && callback();
            }else{
                This.removeAttr('disabled','disabled').removeClass('disable');
                alert(data.resultDesc);
            }
        }
    })
    function veriPhone(){
        var phone_value=phone_input.val();
        var phone_message=phone_input.siblings('label');
        if(phone_value.length==11 && phoneReg.test(phone_value)){//验证没有问题
            phone_message.hide().html('');
        }else{
            phone_input.focus();
            phone_message.show().html('请输入正确的手机');
            return false;
        }
        return true;
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
/**
 * 图片地址处理，用用逗号把图片地址切割成数组，并返回
 * @returns {pic} 给定数组
 */
function handle_pic(pic){
    if(pic && pic.indexOf(',')!=-1){
        picArr=pic.split(',');
        return picArr;
    }
    return [];
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
        arr.push('<a class="firstPage" href=""><</a>');
    }
    for(var i=1;i<=totalPage;i++){
        if(i==pageNo){
            arr.push('<span class="current">'+i+'</span>');
        }else{
            arr.push('<a href="">'+i+'</a>');
        }
    }
    if(pageNo==totalPage){
        arr.push('<span class="endPage current">></span>');
    }else{
        arr.push('<a class="endPage" href="">></a>');
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
    if(style==0){
        format='-';
        str=dateTime.getFullYear()+format+toDb(dateTime.getMonth()+1)+format+toDb(dateTime.getDate());
    }else if(style==1){
        format=['年','月','日','时','分','秒'];
        str=dateTime.getFullYear()+format[0]+toDb(dateTime.getMonth()+1)+format[1]+toDb(dateTime.getDate())+format[2];
    }else{
        str=dateTime.getFullYear()+''+toDb(dateTime.getMonth()+1)+''+toDb(dateTime.getDate());
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
