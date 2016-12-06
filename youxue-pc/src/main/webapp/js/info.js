/**
 * Created by Administrator on 2016/11/16.
 */
$(function(){
    //列表-鼠标经过改变颜色
    var li2=$('.li2');
    li2.hover(function(){
        $(this).addClass('li2_hover');
    },function(){
        $(this).removeClass('li2_hover');
    })
    //内容横向导航
    var YDnav=$('#YDnav');
    var YDheight=YDnav.height();
    var pos_obj=YDnav.offset();//left和top
    //当鼠标滚动，给内容的横向导航添加定位样式
    $(window).scroll(function(){
        var scrollTop=$(window).scrollTop();
        if(pos_obj.top <= scrollTop){//开始添加样式
            YDnav.addClass('navActive');
        }else{
            YDnav.removeClass('navActive');
        }
    })
    //点击内容横向导航，锚点指向到位置
    var li=$('li',YDnav);
    var anchor=$('.div_con2_cont');
    li.click(function(){
        $(this).addClass('active').siblings('li').removeClass('active');
        var index=li.index(this);
        var top=anchor.eq(index).offset().top;
        var sTop=top-YDheight-50;//减20是微调-因为内容横向导航条定位后，改变了元素位置
        $('body').animate({
            scrollTop:sTop
        },500);
        return false;
    })
    //营地图片
    var yingdi_list=$('#yingdi_list');
    var yingdi_li=$('li',yingdi_list);
    var yingdi_a=$('a',yingdi_list);
    var yingdi_len=yingdi_li.length;
    var index=0,pageNo=0,pageCount=5,disk=1,isClick=true;
//        var iWidth=document.documentElement.clientWidth;
//        var iHeight=document.documentElement.clientHeight;
    if(yingdi_len>5){
        var jt_left=$('#jt_left');
        var jt_right=$('#jt_right');
        jt_left.css('display','block');
        jt_right.css('display','block');
        jt_left.bind('click',function(){
            flipPic(-disk);
        })
        jt_right.bind('click',function(){
            flipPic(disk);
        })
    }
    //营地图片点击放大
    var div=$('#showImg');
    var close=div.find('i');
    yingdi_a.bind("click",function(){
        index=yingdi_a.index(this);
        var src=$(this).find('img').attr('data-src');
        //营地图片点击放大
        enlargePic(src,div);
        //弹出层翻页，上一张图下一张图
        var upPic=document.getElementById("showImg_left");
        var nextPic=document.getElementById("showImg_right");
        upPic.onclick=function(ev){
            if(isClick){
                flipAlert(-1,div,ev);
            }
            ev.stopPropagation();
            return false;
        }
        nextPic.onclick=function(ev){
            if(isClick){
                isClick=false;
                flipAlert(1,div,ev);
            }
            ev.stopPropagation();
            return false;
        }
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
        //禁止点击a跳转
        return false;
    });
    //营地图片翻页-列表翻页
    function flipPic(disk){
        pageNo+=disk;
        var startNum=pageNo*pageCount+1;
        var endNum=pageNo*pageCount+pageCount;
        if(yingdi_len<startNum) {
            pageNo--;
            return false;
        }
        if(pageNo<0) {
            pageNo++;
            return false;
        }
        var position=startNum==1?0:-(startNum-1)*191;
        yingdi_list.css("margin-left",position+'px');
    }
    //营地图片点击放大
    function enlargePic(src,div){
        var img=new Image();
        img.src=src;
        img.onload=function(){
            bg_showORhide();
            setTimeout(function(){
                //弹出层
                var width=img.width;
                var height=img.height;
                div.css({"width":width+'px',"height":height+'px',"margin-top":-height/2+'px',"margin-left":-width/2+'px'});
                div.find('img').attr('src',src);
                div.fadeIn(500);
                isClick=true;
            },300)
        }
    }
    //弹出层翻页，上一张图下一张图
    function flipAlert(disk,div){
        index+=disk;
        if(index<0){
            hideAlert(div);
            isClick=true;
            index++;
            return false;
        }
        if(index>=yingdi_len){
            hideAlert(div);
            isClick=true;
            index--;
            return false;
        }
        div.hide(200);
        var timer=setTimeout(function(){
            var src=yingdi_a.eq(index).find('img').attr('data-src');
            enlargePic(src,div);
            clearTimeout(timer);
        },500);
    }
})
//隐藏弹出层
function hideAlert(div,time,otherHide){
    div.hide(time||500);
    if(otherHide){
        for(var i=0,len=otherHide.length;i<len;i++){
            otherHide[i].hide();
        }
    }
    $('#divBG').hide();
}
//返回顶部
function backTop(){
    $('body,html').animate({scrollTop:"0"},500);
    return false;
}
//在线咨询-需要先申请qq在线客服：http://shang.qq.com/v3/widget.html
function onlineQQ(){
    var qq='757927051';
    var link = 'http://wpa.qq.com/msgrd?v=3&uin='+qq+'&site=qq&menu=yes';
    window.open(link,'_blank');
    return false;
}