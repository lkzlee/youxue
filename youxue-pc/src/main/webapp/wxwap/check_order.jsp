<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% 
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/wxwap/"; 
%> 
<!DOCTYPE html>
<html>
<head lang="en">
	<base href="<%=basePath%>"></base>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>个人中心-结算订单_Camplink</title>
    <link rel="stylesheet" href="css/cssReset.css"/>
    <link rel="stylesheet" href="css/goods_details.css"/>
    <script src="js/isLogin.js"></script>
</head>
<body>
    <header>
        
    </header>
    <section>
        <form id="outher_form">
        <div class="form">
            <div class="section2" style="margin-top:0;">
                <p>联系人信息</p>
                <ul>
                    <li><span>联系人姓名</span><input type="text" name="contactName" placeholder="真实姓名" class="require"/></li>
                    <li><span>联系电话</span><input type="text" name="contactPhone" placeholder="输入手机号" class="require"/></li>
                    <li><span>邮箱地址</span><input type="text" name="contactEmail" placeholder="点击输入" class="require"/></li>
                </ul>
            </div>
            <div class="section3">
                <p>付款方式</p>
                <ul>
                    <li><img src="img/ic_weixinpay.png" alt=""/><span>微信支付</span><label class="common_choice " for="pay_weixin"><input id="pay_weixin" type="radio" name="payType" checked value="3" style="display:none"/><i class="active"></i></label></li>
                    <!-- <li><img src="img/ic_alipay.png" alt=""/><span>支付宝</span><label class="common_choice"  for="pay_zhifubao"><input id="pay_zhifubao" type="radio" name="payType" value="2" style="display:none"/><i></i></label></li> -->
                </ul>
            </div>
            <div class="section4">
                <p>优惠编码</p>
                <ul>
                    <li><label class="common_choice" for="no_youhui"><i class="active"></i></label><span>不使用/无优惠码</span><input type="radio" name="youkuima" value="1" checked id="no_youhui" style="display:none"/></li>
                    <li><label class="common_choice" for="youhui"><i></i></label><span>使用优惠码</span><input type="radio" name="youkuima" value="2" id="youhui" style="display:none"/><input type="text" placeholder="点击输入优惠码" style="display: none" name="codeId" /></li>
                </ul>
            </div>
        </div>
        </form>
    </section>
    <footer class="cf">
        <p>合计：¥ <span class="j_price"></span></p>
        <button id="btn_order">立即支付</button>
    </footer>
    <script src="js/jquery-3.1.0.min.js"></script>
    <script src="js/fastclick.js"></script>
    <script src="js/common.js"></script>
    <script>
        var orderList= '<%=request.getParameter("orderList")==null?"":request.getParameter("orderList")%>';
        // console.log(orderList);
        // FastClick.attach(document.body);
        $(function() {
            var btn_order=$('#btn_order');
            var moneyTotal=0,len=0,orderObj=[],campsIds=[],totalPersons=[];
            if(orderList){
                var htmlArr=[],num=0;
                var arr=orderList.split(',');
                moneyTotal=arr[0];
                $('.j_price').text(moneyTotal)
                len=arr.length;
                for(var i=1;i<len;i++){
                    var orderArr=arr[i].split('$$');
                    orderObj[i]={
                        'value':orderArr[0],
                        'img':orderArr[1],
                        'name':orderArr[2],
                        'unitPrice':orderArr[3],
                        'numCar':orderArr[4],
                        'money':orderArr[5]
                    }
                    campsIds.push(orderArr[0])
                    totalPersons.push(orderArr[4])
                    for(var j=0;j<orderObj[i]['numCar'];j++){
                        num++;
                        htmlArr.push(render_orderInfo(orderObj[i],i+''+j));
                        htmlArr.push(render_propleInfo(orderObj[i],i+''+j));
                    }
                }
                $('header').html(htmlArr);
            }
            $(".section3>ul,.section4>ul").on("click",".common_choice",function(){
                $(this).children("i").addClass("active").parent().parent().siblings("li").find("i").removeClass("active");
            });
            $(".section4>ul").on("click",".common_choice",function(){
                if($(this).prop("for")=="youhui"){
                    setBtnDisabled(btn_order,false);
                    $(this).parent().find("[type=text]").show();
                }else{
                    $('input[name=codeId]').val('');
                    $('.j_price').text(moneyTotal)
                    setBtnDisabled(btn_order,true);
                    $(this).parent().next().find("[type=text]").hide();
                }
            })
            //输入优惠码.验证后单选选中，否则取消；提交时如果单选选中，那么提交优惠码
            // $('input[name=codeId]').blur(function () {
            $('input[name=codeId]').bind('input propertychange',function(){
                var That=$(this);
                if(changeRadio($(this)) && changeRadio($(this))!==1){
                    var obj={
                        'codeId':$(this).val(),
                        'campsIds':campsIds.join(','),
                        'totalPersons':totalPersons.join(',')
                    }
                    login_post('/coupon/getCouponByCode.do',obj,'',function(data){
                        data=JSON.parse(data);
                        if(data.result==100){
                            setBtnDisabled(btn_order,true);
                            discount=Number(data.codeAmount);//优惠金额
                            if(discount<moneyTotal){
                                // $('.j_price').text(accSub(moneyTotal,discount));
                                $('.j_price').text(data.payAmount);
                            }
                        }else{
                            setBtnDisabled(btn_order,false);
                            $('.j_price').text(moneyTotal)
                        }
                    })
                }else{
                    setBtnDisabled(btn_order,false);
                    $('.j_price').text(moneyTotal)
                }
            })
            $("#btn_order").on("click",function(){
                if(!auto_range()){
                    return false;
                }
                if(!changeRadio($('input[name=codeId]'))){
                    return false;
                }
                var AllObj={},infoArr=[];
                var outherObj=$('#outher_form').serializeObject();
                for(var i=1;i<len;i++){
                    for(var j=0;j<orderObj[i]['numCar'];j++){
                        var orderListObj=$('.person'+i+''+j).serializeObject();
                        infoArr.push({
                            'campsId':orderListObj['campsId'],
                            'codeId':outherObj['codeId'],
                            'totalCount':1,
                            'contactName':outherObj['contactName'],
                            'contactEmail':outherObj['contactEmail'],
                            'contactPhone':outherObj['contactPhone'],
                            'outPersonList':[{
                                'personName':orderListObj['personName'],
                                // 'personSex':orderListObj['personSex'],
                                'personSex':0,
                                // 'personAge':orderListObj['personAge'],
                                'personAge':0,
                                'personPhone':orderListObj['personPhone'],
                                'personIdno':orderListObj['personIdno'],
                                'personAddress':orderListObj['personAddress']
                            }]
                        })
                    }
                }
                AllObj['orderList']=infoArr;
                AllObj['payType']=outherObj['payType'];
                login_post('/wxpay/addTradeOrder.do',JSON.stringify(AllObj),'',function(data){
                    data=JSON.parse(data);
                    console.log(data)
                    success(data,function(){
                        if(data.wxPayParam){
                            window.location.href='wxpay.jsp?'+urlFormatObj(data.wxPayParam);
                        }
                    })
                },'','application/json; charset=utf-8')
            });
        });
        function render_orderInfo(obj){
            var arr=[];
            arr.push('<div class="goods_message padding20"><div class="cf"><p class="fl head_p">订单信息</p></div>');
            arr.push('<ul><li class="cf"><a href="/wxwap/camp_details.jsp?campusId='+obj['value']+'"><img src="'+obj['img']+'" alt=""/><div><p>'+obj['name']+'</p>');
            arr.push('<p class="price">价格 ¥ <span>'+obj['unitPrice']+'</span></p></div></a></li></ul></div><div class="bg_height10"></div>');
            return arr.join('')
        }
        function render_propleInfo(obj,num){
            var arr=[];
            arr.push('<form class="person'+num+' padding20">');
            arr.push('<div class="form"><div class="section1"><p class="head_p">出行人信息</p><ul>');
            arr.push('<li><span>出行人姓名</span><input type="text" class="require" name="personName" placeholder="真实姓名"/></li>');
            arr.push('<li><span>联系电话</span><input type="text" class="require" name="personPhone" placeholder="输入手机号"/></li>');
            arr.push('<li><span>身份证号</span><input type="text" class="require" name="personIdno" placeholder="点击输入"/></li>');
            arr.push('<li><span>联系地址</span><input type="text" class="require" name="personAddress" placeholder="点击输入"/></li>');
            arr.push('</ul></div></div><input type="hidden" name="campsId" value="'+obj['value']+'"/>');
            arr.push('</form><div class="bg_height10"></div>');
            return arr.join('')
        }
        //根据输入优惠码，改变单选状态
        function changeRadio(code){
            var bool=1;
            if(code.val().length>0){
                if(code.val().length>2){
                    bool=true;
                }else{
                    bool=false;
                }
                return bool;
            }
            return bool;
        }
        function range_input(input,bool,msg){
            if(!bool){
                input.focus();
                alertMesageAndHide(msg,4)
            }
            return bool;
        }
        function auto_range(){
            var input=$('input.require');
            var bool=true;
            input.each(function(){
                if($(this).val().length<2){
                    bool=false;
                    return range_input($(this),false,'信息输入有误');
                }else{
                    bool=true;
                    return range_input($(this),true);
                }
            })
            return bool;
        }
    </script>
</body>
</html>