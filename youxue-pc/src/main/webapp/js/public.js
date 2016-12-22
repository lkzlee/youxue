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

});
//搜索

//创建一个div,并定位
function index_select(element,con){
    var pos_obj=element.offset();//left和top
    var width=element.width();
    var height=element.height();
    pos_obj.top+=height;
    pos_obj.width=width;

    var li='';
    for(var i=0,len=con.length;i<len;i++){
        li+='<li data-value="'+con[i]['categoryId']+'">'+con[i]['categoryName']+'</li>';
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
            posElement.hide();
            ev.stopPropagation();
            $(document).unbind('click');
        })
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
function login_post(address,data,method,successFn,errorFn){
    $.ajax({
        url:address,
        type:method || 'post',
        data:data,
        dataType:"json",
        success:successFn,
        error:errorFn ||function(){
            alert('系统获取异常');
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
/**
 * 图片地址处理，用用逗号把图片地址切割成数组，并返回
 * @returns {pic} 给定数组
 */
function handle_pic(pic){
    picArr=pic.split(',');
    return picArr;
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
function pageSet(element,data){
    element.html('');
    var pageNo=data.pageNo;
    var totalPage=data.totalPage;
    if(totalPage==0 && data.totalCount==0){
        return false;
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
    element.html(arr.join(''))
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
