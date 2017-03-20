var phoneReg=/^1[3|4|5|7|8][0-9]\d{4,8}$/;
var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
var strReg=/[`~!@#$%^&*()_+=<>?:"{},.\/;'[\]]/im;//验证特殊字符
var dateReg = /^\d{4}-(0[1-9]|1[012])(-\d{2})*$/;
var codeReg=/^\d{1,6}$/;
var pwdReg=/^[a-zA-Z0-9]{0,4}$/;
(function(){
    // var tmp='<footer class="foot_nav"><a href="">aa</a><a href="">bb</a><a href="">cc</a></footer>'
    // $('body').css('padding-bottom',35).append(tmp);
})()
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
    checkSpechars:function(input,isRequired){
        var value=input.val();
        return (!strReg.test(value)) && (value.length>1 || !isRequired);
    },
    checkPhone:function(input){
        var value=input.val();
        if(value.length==11 && phoneReg.test(value)){
            return true;
        }else{
            return false;
        }
    },
    checkEmail:function (input){
        var value=input.val();
        if(value.length>5){
            return true;
        }else{
            return false;
        }
    },
    checkCode:function(input){
        console.log(codeReg.test(input.val()))
        if(codeReg.test(input.val())){
            return true;
        }else{
            return false;
        }
    }
})
function uploadImage(obj) {
  if(validateImage(obj)) {
      var data = new FormData();
      data.append('uploadFile', obj.files[0]);
      file_post('/img/userphoto/uploadImg.do',data,'',function(data){
          data=JSON.parse(data);
          data=JSON.parse(data);
          imgUrl=data.url;
          saveImage(imgUrl,function(){
            // $('#photoUrl').attr('src',imgUrl)
            // $('#photoUrl').css('background-image','url("'+imgUrl+'")')
            imgCenter($('#photoUrl'),imgUrl)
          })
      });
  }
}
function imgCenter(element,src){
    imgWidthAndHeight(src,function(width,height){
        var obj={'background-image':'url("'+src+'")'};
        if(width>height){
            obj['background-size']="auto 100%";
        }else{
            obj['background-size']='100% auto';
        }
        element.css(obj);
    })
}
function imgAutoFull(element,src){
    imgWidthAndHeight(src,function(width,height){
        if(width<height){
            element.css({'width':'100%','height':'auto'});
        }
    })
}
function imgWidthAndHeight(src,callback){
    var img=new Image();
    img.src=src;
    img.onload=function(){
        var width=$(this)[0].width;
        var height=$(this)[0].height;
        callback && callback(width,height);
    }
}
function saveImage(url,callback){
    login_post('/uc/updatePhoto.do','userPhotoUrl='+url,'',function(data){
        data=JSON.parse(data);
        success(data,function(){
            callback && callback();
        })
    })
}
function checkPhone(phone,succBack,errback){
    if($.checkPhone(phone)){
        succBack && succBack();
        return phone;
    }else{
        errback && errback();
        alertMesageAndHide('账号或密码不正确',4);
        return false;
    }
}
function checkCode(codeInput,succBack,errback){
    if($.checkCode(codeInput)){
        succBack && succBack();
        return codeInput;
    }else{
        errback && errback();
        alertMesageAndHide('短信验证码输入错误',4)
        return false;
    }
}
//判断用户是否登录
function getLogin(callback){
    login_post('/uc/userinfo.do','','',function(data){
        data=JSON.parse(data);
        callback(data);
    })
}
//加载用户基本信息
function getUserData(callback){
  login_post('/uc/userinfo.do','','',function(data){
      data=JSON.parse(data);
      user_success(data,function(){
          callback && callback(data)
      });
  });
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
//根据传入的数值，获取男或女
function getSex(sex){
    return sex==0?'男':'女';
}
//相减，为避免小数浮点数精准度
function accSub(n1,n2){
    if(!isNaN(n1) && !isNaN(n2)){
        n1=n1*1000;
        n2=n2*1000;
        return (n1-n2)/1000;
    }
    return false;
}
//相加，为避免小数浮点数精准度
function accAdd(n1,n2){
    if(!isNaN(n1) && !isNaN(n2)){
        n1=n1*1000;
        n2=n2*1000;
        return (n1+n2)/1000;
    }
    return false;
}
function isDownPage(public_obj){//是否可以分页
    if(public_obj['pageNo']<=public_obj['totalPage']){
        return true;
    }
}
function isLastPage(public_obj){//是否最后一页 只有一页数据||没有了
    if(public_obj['totalPage']==1 || public_obj['pageNo']>public_obj['totalPage']){
        return true;
    }
}
function auto_submit(address,obj,method){
    method=method || 'POST';
    if(method.toLowerCase()=='get'){
        window.location.href=address+'?'+obj;
    }else{
        var frm=$('<form action='+address+' method="post">');
        for(var key in obj){
            frm.append('<input type="hidden" name="'+key+'" value="'+obj[key]+'">');
        }
        $('body').append(frm);
        
        frm.submit();
    }
}
//url格式化
function urlFormatObj(obj){
    var str='';
    for(var item in obj){
        str+=item+'='+obj[item]+'&';
    }
    return str;
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
            alertMesageAndHide('系统获取异常',4)
        }
    })
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
function success(data,callback,errback){
    if(data.result==100){
        callback();
    }else{
        errback && errback();
        alertMesageAndHide(data.resultDesc,4)
        return false;
    }
}
function user_success(data,callback){
    if(data.result==100){
        callback();
    }else if(data.result==-2){
        alertMesageAndHide(data.resultDesc,4)
        window.location.href='/wxwap/login.jsp';
    }else{
        alertMesageAndHide(data.resultDesc,4)
    }
}
//设置按钮可点或不可点样式,默认bool为空，禁止点击
function setBtnDisabled(btn,bool){
    if(bool){
        btn.removeAttr('disabled').removeClass('disabled');
    }else{
        btn.attr('disabled','disabled').addClass('disabled');
    }
    return btn;
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
 * 消息弹出框-并且自动隐藏
 * @returns
 */
function alertMesageAndHide(str,state){
    var style=state==4?'iconError':'iconSuccess';
    var elemnt=$('#alertMessage');
    if(elemnt.length>0){
        elemnt.show().find('p').text(str);
    }else{
        $('body').append('<div id="alertMessage"><div class="'+style+'"></div><p>'+str+'</p></div>');        
    }
    setTimeout(function(){
        $('#alertMessage').hide();
    },2000)
}
/**
 * 时间数组转换成正常时间格式
 * @param arrTime
 * @returns {string}
 */
function formatDate(time,style){
    var format='',str='';
    var dateTime=new Date(time);
    //兼容IOS高版本
    dateTime = dateTime.getFullYear() > 0 ? dateTime : new Date(Date.parse(time.replace(/-/g, "/")));
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
function touchSwiper(event,That,callback){
    var initX = event.targetTouches[0].pageX;
    var moveX=0;
    var X = 0;
    var objX = 0;
    // event.preventDefault();
    // var obj = event.target.parentNode;
    // if(obj.className == "list-li"){
    //     initX = event.targetTouches[0].pageX;
    //     objX =(obj.style.WebkitTransform.replace(/translateX\(/g,"").replace(/px\)/g,""))*1;
    // }
    // console.log(obj)
    That.on('touchmove',function(event) {
        event.preventDefault();
        moveX = event.targetTouches[0].pageX;
    })
    That.on('touchend',function(event) {
        That.unbind();
        X = moveX - initX;
        var str='';
        if(moveX==0){
            console.log('alert');
        }else{
            if (X > 0) {
                // console.log(moveX +'-'+ initX+'='+X+'右')
                str='right';
            }else{
                // console.log(moveX +'-'+ initX+'='+X+'左')
                str='left';
            }
        }
        callback && callback(str);
    })
}
/** 
 * js截取字符串，中英文都能用 
 * @param str：需要截取的字符串 
 * @param len: 需要截取的长度 
 */
function cutstr(str, len) {
    var str_length = 0;
    var str_len = 0;
    str_cut = new String();
    str_len = str.length;
    for (var i = 0; i < str_len; i++) {
        a = str.charAt(i);
        str_length++;
        if (escape(a).length > 4) {
            //中文字符的长度经编码之后大于4  
            str_length++;
        }
        str_cut = str_cut.concat(a);
        if (str_length >= len) {
            str_cut = str_cut.concat("...");
            return str_cut;
        }
    }
    //如果给定字符串小于指定长度，则返回源字符串；  
    if (str_length < len) {
        return str;
    }
}