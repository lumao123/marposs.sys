<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> IT SERVICE REGISTRATION PORTAL</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.1.min.js"></script>
    <style>
        html,body{height: 100%; margin: 0; padding: 0;}
        section{
            width: 100%; height: 100%;
        }
        ul{
            list-style: none; width: 400px; height: auto;
            float: right; background: #fff; border-radius: 6px;
            padding: 30px 0 0 0; position: absolute; top: 240px; left: 50px;
        }
        ul li{width: 100%; height: 60px; text-align: center;}
        ul li p{
            height: 60px; line-height: 60px; text-align: left;
            padding-left: 50px; font-size: 24px; font-weight: bold;
            border-bottom: 1px solid #d8d8d8; color: #666;
        }
        ul li label{
            width: 100px; text-align: center; color: #666;
            height: 40px; line-height: 40px; margin: 0 0;
            display: inline-block; font-size: 14px; border-bottom: 1px solid #145f96;
        }
        ul li input{
            width: 290px; color: #333; height: 40px; line-height: 40px;
            margin: 0 0; display: inline-block; padding-left: 10px; font-size: 14px;
            outline: none; border: 0; border-bottom: 1px solid #145f96;
            padding: 0;
        }
        ul li span{
            width: 404px; height: 44px; line-height: 44px; color: #fff;
            margin: 0 0; border-radius: 2px; font-size: 14px;
            background: #145f96; display: inline-block;
        }
        ul li i{height: 20px; line-height: 20px; color: #f00; margin: 20px 0; display: inline-block;}
        .left{width: 60%; background: #1976D2; background: url("../images/bg.jpg") no-repeat; background-size: 100% 100%;}
        .right{width: 40%;}
        .left,.right{
            display: flex; height: 100%; position: relative; float: left;
        }
        .sys-title{
            margin: 400px auto 0; color: #fff; font-size: 60px;
        }
        #lgBtn{margin-top: 20px;}
        .toLogin{float: right; margin: 20px 30px;}
        .iconImg{width: 100px; height: 100px; margin: 0 40px;}
    </style>
</head>
<body>
<section>
    <div class="left">
        <p class="sys-title"><img class="iconImg" src="<%=request.getContextPath()%>/images/icon.jpg">IT Service Registration</p>
    </div>
    <div class="right">
        <ul>
            <li><p>Login</p></li>
            <li><i class="msg"></i></li>
            <li><label for="userName">User Name</label><input type="text" id="userName" name="userName"/></li>
            <li><label for="password">Password</label><input type="password" id="password" name="password"/></li>
            <li><span id="lgBtn">Login</span></li>
            <!--<li><a class="toLogin" href="<%=request.getContextPath()%>/user/toRegister">Register</a></li>-->
        </ul>
    </div>
</section>
</body>
<script>
    var basePath = '<%=request.getContextPath()%>';
    $('#lgBtn').bind('click', function(){
        var userName = $('#userName').val();
        var password = $('#password').val();
        if(!userName) {setMsg('User name is empty!'); return;}
        if(!password) {setMsg('Password is empty！'); return;}
        $.ajax({
            url: basePath + '/user/login',
            type: 'post',
            data : {
                userName : userName,
                password : password
            },
            success: function (data) {
                if (data.code == 1){
                    window.location.href = basePath + data.jumpUrl;
                }else{
                    setMsg('User name or password is not correct!');
                }
            }
        });
    });
    $('input').on('focus', function(){
        setMsg('');
        window.onkeydown = function(e) {
            if(e.keyCode == 13) {
                $('#lgBtn').click();
            }
        }
    });
    function setMsg(msg){
        $('.msg').empty().text(msg);
    }
</script>
</html>
