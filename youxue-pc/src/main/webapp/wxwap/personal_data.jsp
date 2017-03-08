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
    <title>个人资料_Camplink</title>
    <link rel="stylesheet" href="css/cssReset.css"/>
    <link rel="stylesheet" href="css/personal_data.css"/>
    <script src="js/isLogin.js"></script>
</head>
<body>
<section>
    <div>
        <input type="file" accept="image/*" id="xdaTanFileImg" style="opacity:0; background: red;"/>
        <ul>
            <li><i>头像</i><b><img src="" style="width:46px;vertical-align: top;" id="photoUrl"/></b></li>
            <li><i>昵称</i><b id="nickName"></b></li>
            <li><i>性别</i><b id="gender"></b></li>
            <li><i>生日</i><b id="birthTime"></b></li>
            <li><i>喜爱城市</i><b id="loveCity"></b></li>
        </ul>
    </div>
    <div>
        <ul>
            <li><i>手机</i><b id="mobile"></b></li>
            <li><i>邮箱</i><b id="email"></b></li>
        </ul>
    </div>
</section>
<footer>
    <strong>注：修改个人信息，请前往Camplink官网！</strong>
</footer>
  <script src="js/jquery-3.1.0.min.js"></script>
<script src="js/fastclick.js"></script>
<script src="js/common.js"></script>
<script type="text/javascript">
$(function(){
  getUserData(function(data){
    renderUser(data);
  })
})
// $('#photoUrl').on('click',function(){
//   $('#xdaTanFileImg').click();
// })
// $('#xdaTanFileImg').on('change',function(){
//   uploadImage($(this)[0])
// })
function renderUser(data){
  $('#photoUrl').attr('src',handle_pic(data.photoUrl)[0]);
  $('#nickName').text(data.nickName);
  $('#loveCity').text(data.loveCity);
  $('#mobile').text(data.mobile);
  $('#email').text(data.email);
  if(data.birthTime){
    $('#birthTime').text(formatDate(data.birthTime,0));
  }
  $('#gender').text(getSex(data.gender));
}
</script>
</body>
</html>