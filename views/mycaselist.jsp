<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Case</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css"/>
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css"/>
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-table.min.css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap-table.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap-table-zh-CN.min.js"></script>
    <style>
        header,footer{width: 100%; height: 40px; background: #145f96;}
        section{width: 100%; height: auto; padding: 50px;}
        .bootstrap-table{width: 1000px; margin: 0 auto;}
        table tr th{text-align: center; background: #9ab0cc;}
        table tr td{text-align: center; background: #fff;}
        a{cursor: pointer;}
        .toSubCase{margin: 0 50px; float: right; font-size: 12px; display: inline-block; height: 40px; line-height: 40px; color: #ccc;
            text-decoration: none; font-weight: 800;
        }
        .top-btn{
            float: right; margin: 0 6px; text-decoration: none; font-weight: 800;
            font-size: 12px; display: inline-block; height: 40px; line-height: 40px; color: #fff;
            padding: 0 10px;
        }
        .top-btn:hover{background: #074370; color: #fff;}
    </style>
</head>
<body>
<header>
    <a class="top-btn"></a>
    <a class="top-btn" href="<%=request.getContextPath()%>/case/toCase">Back</a>
    <a class="top-btn" href="javascript:void(0);">Hello,&emsp;${young.loginName}</a>
</header>
<section>
    <input type="hidden" value="${userID}">
    <table id="caseTable"></table>
</section>
<!--footer></footer-->
</body>
<script>
    var basePath = '<%=request.getContextPath()%>';
    $('#caseTable').bootstrapTable({
        method: 'post',
        contentType: "application/x-www-form-urlencoded",//当请求方法为post的时候,必须要有！！！！
        url: basePath + "/case/getPageList",//请求路径
        striped: true, //是否显示行间隔色
        pageNumber: 1, //初始化加载第一页
        pagination: true, //是否分页
        sidePagination: 'server', //server:服务器端分页|client：前端分页
        pageSize: 10, //单页记录数
        pageList: [5, 10, 20], //可选择单页记录数
        showRefresh: false, //刷新按钮
        queryParams : function (params) { //上传服务器的参数
            var temp = { //如果是在服务器端实现分页，limit、offset这两个参数是必须的
                limit  : params.limit,  //每页显示数量
                offset : params.offset, //SQL语句起始索引
                page   : ( params.offset / params.limit ) + 1, //当前页码
                userID : $('#userID').val()
            };
            return temp;
        },
        columns:[
            {
                title:'No',
                field:'f0',
                formatter:function(value, row, index){
                    return "CASE"+(index+1);
                }
            },
            {
                title:'Company Name',
                field:'companyName'
            },
            {
                title:'Department',
                field:'dept'
            },
            {
                title:'Name',
                field:'name'
            },
            {
                title:'Mobile',
                field:'mobile'
            },
            {
                title:'Submit Time',
                field:'insertTime',
                formatter:function(value, row, index){
                    var date = new Date(value);
                    return formatterDate(date);
                }
            },
            {
                title:'Status',
                field:'status',
                formatter:function(value, row, index){
                    if(value == 0) return '未处理';
                    if(value == 1) return '已回复';
                }
            },
            {
                title:'replyUser',
                field:'replyUser'
            },
            {
                title:'Operation',
                field:'f1',
                formatter:function(value, row, index){
                    return "<a class='edit' href='"+basePath+"/case/toCaseDetail?caseID="+row.caseID+"' data-id='"+row.caseID +"'>详情</a> ";
                }
            }
        ]
    })

    function formatterDate(date){
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        var d = date.getDate();
        var h = date.getHours();
        var min = date.getMinutes();
        var s = date.getSeconds();
        m = m >= 10 ? m : "0"+m;
        d = d >= 10 ? d : "0"+d;
        h = h >= 10 ? h : "0"+h;
        min = min >= 10 ? min : "0"+min;
        s = s >= 10 ? s : "0"+s;
        return y+"-"+m+"-"+d +" "+ h+":"+min+":"+s;
    }

</script>
</html>
