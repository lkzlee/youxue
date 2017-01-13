/**
 * Created by Administrator on 2016/11/16.
 */
$(function(){
    xieyi();
});
function xieyi(){
    //结算订单-查看协议
    var viewProtocol=$('#viewProtocol');
    var div=$('#showImg');
    var close=div.find('i');
    viewProtocol.click(function(){
        protocol(div);
        //禁止点击a跳转
        return false;
    });
    //点击关闭按钮，隐藏弹出层
    //隐藏弹出层
    close[0].onclick=function(ev){
        hideAlert(div);
        ev.stopPropagation();
    }
    div.bind('click',function(ev){
        ev.stopPropagation();
    });
    document.body.onclick=function(ev){
        hideAlert(div);
        ev.stopPropagation();
    }
}
function protocol(div){
    //body背景色变透明黑
    bg_showORhide();
    setTimeout(function(){
        //弹出层
        div.show(500);
    },300)
}
//隐藏弹出层
function hideAlert(div){
    div.hide(500);
    $('#divBG').hide();
}