<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Case</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.1.min.js"></script>
    <style>
        header, footer {
            width: 100%;
            height: 40px;
            background: #145f96;
        }
        section {
            min-height: 600px;
            overflow: hidden;
        }
        table {
            width: 800px;
            margin: 100px auto 100px;
            background: #fff;
            border-collapse: collapse;
        }
        table tr td {
            height: 40px;
            border: 1px dashed #999;
        }
        table tr td:first-child {
            font-size: 12px;
        }
        table tr td:last-child {
            padding-left: 20px;
        }
        table tr td label {
            width: 200px;
            margin: 0 40px;
            display: inline-block;
            line-height: 40px;
            font-size: 12px;
            color: #666;
        }
        table tr td input {
            width: 480px;
            height: 40px;
            line-height: 40px;
            border-radius: 5px;
            padding-left: 20px;
        }
        textarea {
            width: 480px;
            height: 200px;
            margin-top: 10px;
            border-radius: 5px;
            padding: 10px;
            outline: none;
            border: 0;
        }
        .btn {
            display: inline-block;
            padding: 10px 50px;
            margin: 30px 10px;
            background: #ccc;
            border-radius: 5px;
            color: #666;
            cursor: pointer;
            border: 1px solid #d8d8d8;
            box-shadow: 0 2px 2px #666;
        }
        #back {
            background: #ccc;
        }
        .btn:hover {
            color: #fff;
            background: #145f96;
        }
        #back:hover {
            color: #fff;
            background: #145f96;
        }
        #problem {
            height: 150px;
        }
        .mask img {
            margin: auto;
            position: relative;
            top: 100px;
        }
        .mask {
            width: 100%;
            height: 100%;
            position: absolute;
            background: rgba(0, 0, 0, 0.5);
            top: 0;
            text-align: center;
            display: none;
        }
        .iconImg {
            width: 24px;
            height: 24px;
            position: relative;
        }
    </style>
</head>
<body>
<header>
</header>

<section>
    <input type="hidden" id="caseID" value="${caseDetail.caseID}"/>
    <input type="hidden" id="isAdmin" value="${idAdmin}"/>
    <table>
        <tr>
            <td colspan="2"><img class="iconImg" src="<%=request.getContextPath()%>/images/nail.jpg" alt=""/></td>
        </tr>
        <tr>
            <td align="right"><label>Company Name</label></td>
            <td>${caseDetail.companyName}</td>
        </tr>
        <tr>
            <td align="right"><label>Department</label></td>
            <td>${caseDetail.dept}</td>
        </tr>
        <tr>
            <td align="right"><label>Name</label></td>
            <td>${caseDetail.name}</td>
        </tr>
        <tr>
            <td align="right"><label>Mobile Num</label></td>
            <td>${caseDetail.mobile}</td>
        </tr>
        <tr>
            <td align="right"><label>Reply User</label></td>
            <td>${caseDetail.replyUser}</td>
        </tr>
        <tr>
            <td align="right"><label>Error Description</label></td>
            <td>
                <textarea id="problem" name="problem" readonly>${caseDetail.problem}</textarea>
            </td>
        </tr>
        <tr>
            <td align="right"><label>Reply</label></td>
            <td>
                <textarea id="reply" name="reply" placeholder="Please fill your reply..." <c:if test="${caseDetail.status == 1 || idAdmin == 0}">readonly</c:if>>${caseDetail.reply}</textarea>
            </td>
        </tr>
        <tr>
            <td align="right"><label>File</label></td>
            <td>
                <c:if test="${null != caseDetail.filePath && caseDetail.filePath != ''}">
                    <a href="javascript:void(0)" onclick="showImg()">${caseDetail.filePath}</a>
                </c:if>
                <c:if test="${null == caseDetail.filePath || !caseDetail.filePath == ''}">
                    -
                </c:if>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center" style="border: 0;"><c:if
                    test="${caseDetail.status == 0 && idAdmin == 1}"><span id="replyBtn" class="btn">Reply</span></c:if><span
                    id="back" class="btn">Back</span></td>
        </tr>
    </table>
</section>
<div class="mask">
    <img src="<%=request.getContextPath()%>/data/${caseDetail.filePath}"/>
</div>
<!--footer></footer-->
<script>
    $(function () {
        var height = $(document).height();
        $('.mask').height(height);
    });

    var basePath = '<%=request.getContextPath()%>';
    $('#back').bind('click', function () {
        var flag = $('#isAdmin').val();
        if (flag == 1) window.location.href = basePath + "/case/toList";
        else window.location.href = basePath + "/case/toMyCase";
    });
    $('#replyBtn').bind('click', function () {
        var reply = $('#reply').val();
        if (!reply) {
            alert('Please fill reply content!');
            return;
        }
        var caseID = $('#caseID').val();
        $.ajax({
            url: basePath + '/case/replyCase',
            type: 'post',
            data: {
                caseID: caseID,
                reply : reply
            },
            success: function (data) {
                if (data == 1) {
                    alert('Success!');
                    window.location.reload();
                } else {
                    window.location.href = basePath + "/common/toError";
                }
            }
        });
    });
    $('.mask').bind('click', function () {
        $('.mask').hide();
    });
    function showImg() {
        $('.mask').show();
    }
</script>
</body>
</html>
