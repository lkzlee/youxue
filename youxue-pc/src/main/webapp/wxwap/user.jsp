<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>个人中心_Camplink</title>
    <link rel="stylesheet" href="css/cssReset.css"/>
    <link rel="stylesheet" href="css/personal.css"/>
    <script src="js/isLogin.js"></script>
</head>
<body>
  <header>
      <img src="img/personal.png" style="width:100%" alt=""/>
      <input type="file" accept="image/*" id="xdaTanFileImg" style="opacity:0; background: red;"/>
      <div class="cf personal_head_part">
          <i class="fl"><img src="" alt="" id="photoUrl" width="100%" /></i>
          <div class="fl personal_head_part_message">
              <p id="nickName"></p>
              <p><span id="credit"></span> 积分</p>
          </div>
      </div>
  </header>
  <section>
     <div>
         <ul>
             <li><a href="personal_data.jsp"><i>个人资料</i></a></li>
             <li><a href="my_message.jsp" class="cf"><i class="fl">我的消息</i><b class="fr" id="unReads" style="display:none"></b></a></li>
         </ul>
     </div>
     <div>
         <ul>
             <li><a href="my_order.jsp"><i>我的订单</i></a></li>
             <li><a href="shopping_cart.jsp"><i>我的购物车</i></a></li>
         </ul>
     </div>
  </section>
<script src="js/jquery-3.1.0.min.js"></script>
<script src="js/fastclick.js"></script>
<script src="js/common.js"></script>
<script type="text/javascript">
$(function(){
  getUserData(function(data){
    renderUser(data);
  })
})
$('#photoUrl').on('click',function(){
  $('#xdaTanFileImg').click();
})
$('#xdaTanFileImg').on('change',function(){
  uploadImage($(this)[0])
})
function renderUser(data){
  // console.log(data); 
  $('#photoUrl').attr('src',handle_pic(data.photoUrl)[0]);
  $('#nickName').text(data.nickName);
  $('#credit').text(data.credit);
  data.unReads && $('#unReads').text(data.unReads).show();
}
</script>
</body>
</html>