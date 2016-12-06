/**
 * Created by Administrator on 2016/11/1.
 */
$(function(){
    //鼠标经过导航，改变样式
    var nav_div=$("div",$("nav"));
    nav_div.hover(function(){
        $(this).addClass('active');
    },function(){
        $(this).removeClass('active');
    })
    $('#place').focus(function(){
        // $(document).unbind('click');
        var con=['美国','英国','澳大利亚','韩国','澳大利亚','韩国','日本','瑞士'];
        setElement($(this),con);
    })
    $('#wantDo').focus(function(){
        // $(document).unbind('click');
        var con=['语言学习','传统营地','全真插班','体育项目','艺术形式','科技探索','野生保护','志愿者项目','英国学年项目','日营'];
        setElement($(this),con);
    });
    //创建一个div,并定位
    function setElement(element,con){
        var pos_obj=element.offset();//left和top
        var width=element.width();
        var height=element.height();
        pos_obj.top+=height;
        pos_obj.width=width;

        var li='';
        for(var i=0,len=con.length;i<len;i++){
            li+='<li>'+con[i]+'</li>';
        }
        var nDiv=$("<ul class='posElement' style='position:absolute;top:"+pos_obj.top+"px;left:"+pos_obj.left+"px;width:"+pos_obj.width+"px'>"+li+"</ul>");
        $(document.body).append(nDiv);

        //当失去焦点，首先判断是不是点击了列表，然后判断是不是点击文档其他空白处
        element.blur(function(){
            var element_this=$(this);
            var posElement=$('.posElement');
            var li=$('li',posElement);
            li.click(function(ev){
                var value=$(this).html();
                element_this.val(value);
                posElement.hide();
                ev.stopPropagation();
            })
            $(document).click(function(ev){
                // console.log(1);
                posElement.hide();
                ev.stopPropagation();
                $(document).unbind('click');
            })
        })
    }
});
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
function login_post(address,data,method,successFn,errorFn){
    $.ajax({
        url:address,
        type:method,
        data:data,
        dataType:"json",
        success:successFn,
        error:errorFn
    })
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
