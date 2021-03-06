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
    <title>登录_Camplink</title>
    <link rel="stylesheet" href="css/cssReset.css"/>
    <link rel="stylesheet" href="css/login.css"/>
    <script src="js/isLogin.js"></script>
</head>
<body>
<header class="ctf">
    <p>欢迎进入 Camplink 官网！</p>
</header>
<section>
    <form id="user_input" action="" method="">
        <button class="get_check_number" id="code">获得验证码</button>
        <input type="text" value="18810497384" placeholder="输入手机号" maxlength="11" id="phone" class="input" />
        <input type="number" value="" placeholder="输入验证码" id="codeInput" class="input codeInput" />
        <div><button  disabled="" class="btn_login disabled">登陆</button></div>
    </form>
</section>

<script src="js/jquery-3.1.0.min.js"></script>
<script src="js/fastclick.js"></script>
<script src="js/common.js"></script>
<script src="js/login.js"></script>
</body>
</html>