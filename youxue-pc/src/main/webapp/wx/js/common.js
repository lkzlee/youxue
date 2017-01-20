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
//判断用户是否登录
function is_login(callback){
    login_post('/uc/userinfo.do','','',function(data){
        data=JSON.parse(data);
        callback(data);
    })
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
function success(data,callback,errback){
    if(data.result==100){
        callback();
    }else{
        errback && errback();
        alert(data.resultDesc);
        return false;
    }
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