<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.1.min.js"></script>
    <style>
        section {
            min-height: 600px;
            overflow: hidden;
        }

        p {
            text-align: center;
            margin: 300px 0;
        }

        p span {
            display: inline-block;
            height: 40px;
            line-height: 40px;
            margin: 0 20px;
        }

        p .toIndex {
            border: 1px solid #999;
            padding: 0 20px;
            border-radius: 5px;
            background: #ccc;
            cursor: pointer;
        }
    </style>
</head>
<body>
<section>
    <p><span>Error</span><span class="toIndex">Back</span></p>
</section>
<script>
    var basePath = '<%=request.getContextPath()%>';
    $('.toIndex').bind('click', function () {
        window.location.href = basePath + "/case/toCase";
    });
</script>
</body>
</html>
