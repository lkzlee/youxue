/**
 * Created by Administrator on 2016/11/16.
 */
//页面加载后执行
function info_loding(){
    //列表-鼠标经过改变颜色
    var li2=$('.li2');
    li2.hover(function(){
        $(this).addClass('li2_hover');
    },function(){
        $(this).removeClass('li2_hover');
    })
    //内容横向导航,当鼠标滚动，给内容的横向导航添加定位样式
    var YDnav=$('#YDnav');
    var YDheight=YDnav.height();
    var pos_obj=YDnav.offset();//left和top
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
}
function load_render(data){
    login_post('/campsDetail.do',data,'',function(data){
        data=JSON.parse(data);
        data.realCampsImages='a,b,c,d';
        console.log(data);
        success(data,function(){
            $('.title').text(data.campsName);
            $('.orientedPeople').text(data.orientedPeople);
            $('.durationTime').text(data.durationTime);
            $('.deadlineDate').text(data.deadlineDate);
            $('.totalPrice').text(data.totalPrice);
            $('.feature').text(data.feature);
            $('.doneCount').val(data.doneCount || 1);
            if(data.serviceSupport){
                var arr=handle_pic(data.serviceSupport);
                var str='';
                for(var i=0;i<arr.length;i++){
                    str+='<span><i></i>'+arr[i]+'</span>';
                }
                $('.serviceSupport').html(str);
            }
            if(data.realCampsImages){
                var arr=handle_pic(data.realCampsImages);
                var str='';
                for(var i=0;i<arr.length;i++){
                    str+='<li><a href="javascript:void(0)"><img src="'+arr[i]+'" data-src="'+arr[i]+'"></a></li>';
                }
                $('.img_info').attr('src',arr[0]);
                $('.img_list').html(str);
                $('#yingdi_list').html(str);
            }
            $('.campsLocale').val(data.campsLocale);
            $('.campsDesc').val(data.campsDesc);
            $('.courseDesc').val(data.courseDesc);
            $('.activityDesc').val(data.activityDesc);
            $('.campsFoodDesc').val(data.campsFoodDesc);
            if(data.campsFoodsPhotos){
                var arr=handle_pic(data.campsFoodsPhotos);
                var str='';
                for(var i=0;i<arr.length;i++){
                    str+='<img src="'+arr[i]+'">';
                }
                $('.campsFoodsPhotos').html(str);
            }
            $('.campsHotelDesc').val(data.campsHotelDesc);
            if(data.campsHotelPhotos){
                var arr=handle_pic(data.campsHotelPhotos);
                var str='';
                for(var i=0;i<arr.length;i++){
                    str+='<img src="'+arr[i]+'">';
                }
                $('.campsHotelPhotos').html(str);
            }
            var traces=data.traces;
            if(traces.length>0){
                var arr=[];
                for(i=0,len=traces.length;i<len;i++){
                    arr.push('<li class="clear"><div class="li_left"><img src="'+handle_pic(traces[i]['tracePhotos'])[0]+'" alt=""></div>');
                    arr.push('<div class="li_right"><span class="color_blur">'+traces[i]['traceName']+'</span><p class="p1_li_right">'+traces[i]['traceDesc']+'</p></div></li>');
                }
                $('.traces').html(arr.join(''));
            }
            $('.feeDesc').val(data.feeDesc);
            yingdi_pic();
            tab_pic();
        })
    })
}
function yingdi_pic(){
    //营地图片
    var yingdi_list=$('#yingdi_list');
    var yingdi_li=$('li',yingdi_list);
    var yingdi_a=$('a',yingdi_list);
    var yingdi_len=yingdi_li.length;
    var index=0,pageNo=0,pageCount=5,disk=1,isClick=true;
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
}
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
    var qq='1252610341';
    var link = 'http://wpa.qq.com/msgrd?v=3&uin='+qq+'&site=qq&menu=yes';
    window.open(link,'_blank');
    return false;
}
//切换头部的三张图
function tab_pic(){
    $('.img_list').on('click','img',function(){
        var src=$(this).attr('src');
        $('.img_info').attr('src',src);
        return false;
    })
}