var timer=0;
$(function(){
    var phone=$('#phone');
    var code=$('#code');
    var codeInput=$('#codeInput');
    var btn_login=$('.btn_login');
    code.on('click',function(){
        var This=$(this);
        checkPhone(phone,function(){
            verifCode(This);
            sendCode(This,phone);
        })
        return false;
    })
    codeInput.on('input',function(){
        checkCode(codeInput,function(){
            setBtnDisabled(btn_login,true);
        },function(){
            setBtnDisabled(btn_login);
        })
    })
    btn_login.on("click",function(){
        var This=$(this);
        setBtnDisabled(This)
        function error(){
            setBtnDisabled(This);
        }
        if(checkPhone(phone,'',error)){
            var data={
                'mobile':phone.val(),
                'phoneCode':codeInput.val(),
                'autoLog':true
            };
            sendLogin(data,This);
        }
        return false;
    });
})
function sendLogin(data,This){
    login_post('/wx/bindphone.do',data,'',successFn);
    function successFn(json_data){
        var data=JSON.parse(json_data);
        success(data,function(){
            window.location.href='/wxwap/user.jsp';
        },error)
    }
    function error(){
        setBtnDisabled(This,true);
    }
}
function verifCode(This){
    var cur_time=120;
    setBtnDisabled(This)
    timer=setInterval(function(){
        cur_time--;
        if(cur_time>0){
            This.text('重新发送('+cur_time+')');
        }else{
            clearInterval(timer);
            setBtnDisabled(This,true).text('获取验证码');
        }
    },1000);
}
function sendCode(This,phone){
    login_post('/mobileCode.do','phone='+phone.val(),'',successFn,errorFn);
    function successFn(json_data){
        var data=JSON.parse(json_data);
        console.log(data)
        success(data,function(){
            alertMesageAndHide('验证码已成功发送')
        },function(){
            clearInterval(timer);
            setBtnDisabled(This,true).text('获取验证码');
        })
    }
    function errorFn(){
        clearInterval(timer);
        setBtnDisabled(This,true).text('获取验证码');
    }
}