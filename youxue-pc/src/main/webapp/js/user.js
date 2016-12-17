/**
 * Created by Administrator on 2016/11/9.
 */
var phoneReg=/^1[3|4|5|7|8][0-9]\d{4,8}$/;
var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
var strReg=/[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im;//验证特殊字符
var dateReg = /^\d{4}-(0[1-9]|1[012])(-\d{2})*$/;
$(function(){
    //弹出框
    // alert_element();
    //个人首页的左侧导航列表
    userIndexList();
    //首页基础信息编辑
    // user_edit();
    //我的消息_单选多选
    // message();
    //购物车
    // car();
});
//个人首页的左侧导航列表
function userIndexList(){
    var userList=$('#userList');
    var a_userList=$('.a_userList');
    var userUl=userList.find('ul');
    a_userList.click(function(){
        var This=$(this);
        var index=a_userList.index(this);
        var ulIndex=userUl.eq(index);
        var liList=$('li',ulIndex);
        var liLength=liList.length;
        var ulHeight=ulIndex.height()>0?0:(47*liLength);
        if(ulHeight==0){
            This.addClass('li_aActive');
        }else{
            This.removeClass('li_aActive');
        }
        ulIndex.animate({
            height:ulHeight},{
            easing: 'easeOutBounce',
            duration: 500,
            complete:function(){

            }
        });
        return false;
    })
}
//首页基础信息编辑
function user_edit(){
    var default_edit=$('#default_edit');
    var nickname=$('#nickname');
    var nspan=nickname.children('span');
    var ninput=nickname.children('input');

    var birthday=$('#birthday');
    var bspan=birthday.children('span');
    var binput=birthday.find("input");

    var loveCity=$('#loveCity');
    var lspan=loveCity.children('span');
    var linput=loveCity.children('input');

    var sex=$('#sex');
    var sspan=sex.children('span');
    default_edit.click(function(){
        if($(this).html()=="编辑"){
            this_value="保存";
            nspan.hide();
            ninput.attr("value",nspan.text()).show();
            bspan.hide();
            birthday.children('.calendar-input-wrap').css('display','inline-block').children("input").attr("value",bspan.text());
            lspan.hide();
            linput.attr("value",lspan.text()).show();
            sspan.hide();
            var num=sspan.text()=="女"?1:0;
            sex.children('label').css('display','inline-block').children('input:eq('+num+')').attr('checked','checked');
        }
        else{
            if(check_nickname(ninput) && check_birthday(binput,birthday) && check_loveCity(linput)){
                var nvalue=ninput.val();
                var bvalue=binput.val();
                var lvalue=linput.val();
                var svalue=sex.find('input:checked').val();
                var data={
                    // 'birthTime':bvalue,
                    'gender':svalue,
                    'nickName':nvalue,
                    'loveCity':lvalue
                }
                login_post('/uc/updateUserInfo.do',data,'',function (data) {
                    user_success(JSON.parse(data),function(){
                        nspan.text(nvalue).show();
                        ninput.hide();
                        bspan.text(bvalue).show();
                        birthday.children('.calendar-input-wrap').css('display','none');
                        lspan.text(lvalue).show();
                        linput.hide();
                        sspan.text(svalue==0?'男':'女').show();
                        sex.children('label').css('display','none');
                    })
                });
                this_value="编辑";
            }
        }
        $(this).html(this_value);
    })
    ninput.blur(function(){
        check_nickname($(this));
    })
    binput.blur(function(){
        check_birthday($(this),birthday);
    })
    linput.blur(function(){
        check_loveCity($(this))
    })
}
function user_success(data,callback){
    if(data.result==100){
        callback();
    }else if(data.result==-2){
        alert(data.resultDesc);
        window.location.href='/login.html';
    }else{
        alert(data.resultDesc);
    }
}
function check_nickname(This){
    var value=This.val();
    var message=This.siblings('i');
    if(value.length>1 && !(strReg.test(value))){
        message.html('');
    }else{
        // This.focus();
        message.html('请输入正确的昵称');
        return false;
    }
    return true;
}
function check_birthday(This,birthday){
    var value=This.val();
    var i=birthday.find('i');
    if(value!="" && dateReg.test(value)){
        i.html('');
    }else{
        // This.focus();
        i.html('请输入正确的生日');
        return false;
    }
    return true;
}
function check_loveCity(This){
    var value=This.val();
    var i=This.siblings('i');
    if(value!=""){
        i.html('');
    }else{
        // This.focus();
        i.html('请输入内容');
        return false;
    }
    return true;
}
//首页基础信息编辑
function user_edit_photo(url,successFn,errorFn){
    var edit_photo=$('#edit_photo');
    var photoDiv=$('.photoDiv');
    var file_photo=$('#file_photo');
    var btn_upload=$('#btn_upload');
    edit_photo.click(function(){
        if($(this).html()=="保存"){
            $(this).html("编辑");
            photoDiv.fadeOut(300);
        }else{
            $(this).html("保存");
            photoDiv.fadeIn(300);
            btn_upload.click(function(){
                file_photo.click();
            })
            file_photo.on('change',function(){
                uploadImage($(this).get(0))
            })
            function uploadImage(obj) {
                var tmpFilePath=obj.value;
                if(validateImage(obj)) {
                    var data={
                        'userPhotoUrl':tmpFilePath
                    }
                    // login_post(url,data,'',successFn,errorFn);
                    $.ajaxFileUpload({
                        url:url,             //需要链接到服务器地址
                        // data:data,
                        secureuri:false,
                        fileElementId:'file_photo',                         //文件选择框的id属性
                        dataType: 'json',                                     //服务器返回的格式，可以是json
                        success: successFn,
                        error: errorFn
                    });
                }
            }
        }
        return false;
    });
}
function message(){
    var all_checked=$('.all_checked');
    var cont_border=$('.cont_border');
    var radio_label=cont_border.children('label');
    var input=radio_label.children('input');
    var input_all=all_checked.children('input');
    var length=input.length;
    var count=0,checkedCount=0;
    var del=$('#delete');
    var signRead=$('#signRead');
    all_checked.click(function(){
        var isChecked=!input.prop('checked');
        //如果全部选中，那么值为length，else就是0--在单选时，也会改变此值
        if(isChecked){
            checkedCount=length;
            del.css('display','inline-block');
            signRead.css('display','inline-block');
        }else{
            checkedCount=0;
            del.css('display','none');
            signRead.css('display','none');
        }
        input.prop('checked',isChecked);
        input_all.prop('checked',isChecked);
        return false;
    })
    radio_label.click(function(){
        var this_input=$(this).children('input');
        var isChecked=this_input.prop('checked');
        this_input.prop('checked',!isChecked);
        checkedCount=isChecked?(checkedCount-1):(checkedCount+1);//如果当前没有选中，那么+1，下面就会选中了
        if(checkedCount==length){
            check_bool=true;
            del.css('display','inline-block');
            signRead.css('display','inline-block');
        }else{
            check_bool=false;
            del.css('display','none');
            signRead.css('display','none');
        }
        input_all.prop('checked',check_bool);
        return false;
    })
}
function car(){
    var car_ul=$('#car_ul');
    var li=$('li',car_ul);
    var all_car_check=$('.input_all_car');
    var li_car_check=$('input',car_ul);
    var money_car=$('.money_car'),moneyTotal=0;
    var number_car=$('.number_car'),numTotal=0;
    var checkedCount_car=0,len=li_car_check.length;
    //点击li下的input改变li样式
    li_car_check.click(function(){
        var money=Number($(this).siblings('.money_span_car').html());
        var numCar=Number($(this).siblings('.num_span_car').html());
        var isChecked=$(this).prop('checked');
        if(isChecked){
            checkedCount_car++;
            moneyTotal+=money;
            numTotal+=numCar;
            $(this).parents('li').addClass('active');
        }else{
            checkedCount_car--;
            moneyTotal-=money;
            numTotal-=numCar;
            $(this).parents('li').removeClass('active');
        }
        if(checkedCount_car==len){
            all_car_check.prop('checked',true);
        }else{
            all_car_check.prop('checked',false);
        }
        change_total();
    })
    //点击全选改变样式
    all_car_check.click(function(){
        var isChecked=$(this).prop('checked');
        checked_change_li(isChecked);
    });
    function checked_change_li(isChecked){
        if(isChecked){
            checkedCount_car=len;
            li.addClass('active');
            all_car_check.prop('checked',true);
            li_car_check.prop('checked',true);

            var num_span=$('.num_span_car');
            numTotal=0;
            num_span.each(function(){
                numTotal+=Number($(this).html());
            });
            var mon_span=$('.money_span_car');
            moneyTotal=0;
            mon_span.each(function(){
                moneyTotal+=Number($(this).html());
            });
            change_total();
        }else{
            checkedCount_car=0;
            li.removeClass('active');
            all_car_check.prop('checked',false);
            li_car_check.prop('checked',false);
            numTotal=0;
            moneyTotal=0;
            change_total()
        }
    }
    function change_total(){
        money_car.html('¥'+moneyTotal);
        number_car.html('¥'+numTotal);
    }
}
//校验图片格式及大小 Add Date 2012-6-14 LIUYI
function validateImage(obj) {
    var file = obj;
    var tmpFileValue = file.value;
    //校验图片格式
    if(/^.*?\.(gif|png|jpg|jpeg|bmp)$/.test(tmpFileValue.toLowerCase())){
        //校验图片大小,这段代码需调整浏览器安全级别(调到底级)和添加可信站点(将服务器站点添加到可信站点中)
        var maxSize = 1024 * 1024 * 2; //最大2MB
        if(file.value != ""){
            var size=obj.files[0].size;
            if(size<0 || size>maxSize){
                alert("当前文件大小" + (size/1024/1024).toFixed(2) + "MB, 超出最大限制"+(maxSize/1024/1024)+"MB");
                return false;
            }else{
                return true;
            }
        }else{
            alert("请选择上传的文件!");
            return false;
        }
    } else {
        alert("只能上传jpg、jpeg、png、bmp或gif格式的图片!");
        return false;
    }
}