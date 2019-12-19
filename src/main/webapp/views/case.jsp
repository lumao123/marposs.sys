<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>IT Service Form</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.1.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
    <style>
        header, footer {
            width: 100%;
            height: 40px;
        }

        header {
            background: #145f96;
        }

        section {
            min-height: 600px; /*background: rgba(25, 118, 210, 0.8);*/
            overflow: hidden;
        }

        table {
            width: 800px;
            margin: 100px auto 100px;
        }

        table tr td {
            height: 60px;
        }

        table tr td label {
            width: 200px;
            color: #666;
            margin: 10px 40px;
            display: inline-block;
            line-height: 40px;
            font-size: 16px;
        }

        table tr td input {
            width: 480px;
            height: 40px;
            line-height: 40px;
            margin: 10px 0;
            border-radius: 4px;
            padding-left: 20px;
        }

        table tr td input[type=radio] {
            width: 15px;
            height: 15px;
            line-height: 40px;
            margin: 10px 10px;
            padding-left: 20px;
        }

        textarea {
            width: 480px;
            height: 300px;
            margin-top: 10px;
            border-radius: 4px;
            padding: 10px;
        }

        input, textarea {
            outline: none;
            border: 1px solid #d7d7d7;
        }

        input:focus, textarea:focus {
            border-bottom: 2px solid #145f96;
        }

        .btn {
            display: inline-block;
            padding: 10px 50px;
            margin: 30px 0;
            background: #ccc;
            border-radius: 5px;
            color: #666;
            cursor: pointer;
            border: 1px solid #d8d8d8;
            box-shadow: 0 2px 2px #666;
        }

        .btn:hover {
            color: #fff;
            background: #145f96;
        }

        .error {
            border: 1px solid #f00;
        }

        .upBtn {
            display: inline-block;
            background: #ccc;
            padding: 5px 10px;
            border-radius: 4px;
            color: #666;
            cursor: pointer;
            border: 1px solid #d8d8d8;
            box-shadow: 0 1px 1px #666;
        }

        .upBtn:hover {
            color: #fff;
            background: #145f96;
        }

        .tip {
            margin: 0 10px;
            color: #666;
        }

        .top-btn {
            float: right;
            margin: 0 6px;
            text-decoration: none;
            font-weight: 800;
            font-size: 12px;
            display: inline-block;
            height: 40px;
            line-height: 40px;
            color: #fff;
            padding: 0 10px;
        }

        .top-btn:hover {
            background: #074370
        }

        #dept {
            width: 200px;
            height: 40px;
            line-height: 40px;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<header>
    <c:if test="${null != young}">
        <a class="top-btn"></a>
        <a class="top-btn" href="<%=request.getContextPath()%>/user/loginOut">LoginOut</a>
        <a class="top-btn toMyCase" href="<%=request.getContextPath()%>/case/toMyCase">My Cases</a>
        <a class="top-btn" href="javascript:void(0)">Hello,&emsp;${young.loginName}</a>
    </c:if>
    <c:if test="${null == young}">
        <a class="top-btn"></a>
        <a class="top-btn" href="<%=request.getContextPath()%>/user/toLogin">Home</a>
        <a class="top-btn" href="javascript:void(0)">Hello,&emsp;Visitor!</a>
    </c:if>
</header>

<section>
    <table>
        <form id="fileUploadForm" name="fileUploadForm" action="/common/uploadFile" method="post"
              enctype="multipart/form-data"></form>
        <tr>
            <td align="right"><label>Company Name</label></td>
            <td>
                <input type="radio" name="companyName" checked value="MNA"/>MNA
                <input type="radio" name="companyName" value="MST"/>MST
                <input type="radio" name="companyName" value="MGAsia"/>MGAsia
                <input type="radio" name="companyName" value="ZHONGYUAN"/>ZHONGYUAN
                <input type="radio" name="companyName" value="MTW"/>MTW
            </td>
        </tr>
        <!--tr><td align="right"><label for="subUser">员工号</label></td><td><input type="text" id="subUser" name="subUser"/></td></tr-->
        <tr>
            <td align="right"><label for="name">Name</label></td>
            <td><input type="text" id="name" name="name"/></td>
        </tr>
        <c:if test="${null != young}">
            <tr>
                <td align="right"><label for="name">User Num</label></td>
                <td><input style="width: 100px" type="text" value="NO.0000${young.userID}" readonly/></td>
            </tr>
        </c:if>
        <tr>
            <td align="right"><label for="dept">Depertment</label></td>
            <td>
                <select id="dept" name="dept">
                    <option value="">==Select==</option>
                    <option value="MNA_CPP">MNA_CPP</option>
                    <option value="MNA_D&E">MNA_D&E</option>
                    <option value="MNA_Proposal">MNA_Proposal</option>
                    <option value="MNA_Facility">MNA_Facility</option>
                    <option value="MNA_Finance">MNA_Finance</option>
                    <option value="MNA_G&M">MNA_G&M</option>
                    <option value="MNA_HR&Admin">MNA_HR&Admin</option>
                    <option value="MNA_LeakTest">MNA_LeakTest</option>
                    <option value="MNA_MG">MNA_MG</option>
                    <option value="MNA_Production">MNA_Production</option>
                    <option value="MNA_Supply Chain">MNA_Supply Chain</option>
                    <option value="MNA_QC">MNA_QC</option>
                    <option value="MST_A.S.S">MST_A.S.S</option>
                    <option value="MST_G&A">MST_G&A</option>
                    <option value="MST_P&E">MST_P&E</option>
                    <option value="MST_S&M">MST_S&M</option>
                    <option value="ZHONGYUAN">ZHONGYUAN</option>
                    <option value="MTW">MTW</option>
                    <option value="MGAsia">MGAsia</option>
                </select>
            </td>
        </tr>
        <tr>
            <td align="right"><label for="mobile">Phone Num</label></td>
            <td><input type="text" id="mobile" name="mobile"/></td>
        </tr>
        <tr>
            <td align="right"><label for="problem">Error Description</label></td>
            <td>
                <textarea id="problem" name="problem"></textarea>
            </td>
        </tr>
        <tr>
            <td align="right"><label for="fileUpload">File</label></td>
            <td>
                <input type="file" id="fileUpload" name="fileUpload" form="fileUploadForm" value="" style="display: none;"/>
                <span class="upBtn">Upload</span>
                <span class="tip"><em style="color: #f00; padding: 0 8px;">*</em>Only support .JPG、.PNG images.</span>
                <input type="hidden" id="extra" name="extra">
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center"><span id="submit" class="btn">Submit</span></td>
        </tr>
    </table>
</section>

<!--footer></footer-->

<script>
    var basePath = '<%=request.getContextPath()%>';
    $('.btn').on('click', function () {
        $('input,textarea').removeClass('error');
        var companyName = $('input[name=companyName]').val();
        var dept = $('#dept').val();
        var name = $('#name').val();
        var mobile = $('#mobile').val();
        var problem = $('#problem').val();
        var extra = $('#extra').val();
        if (!companyName) {
            alert('Please fill the company name.');
            $('#companyName').addClass('error');
            return;
        }
        if (!dept) {
            alert('Please fill the dept.');
            $('#dept').addClass('error');
            return;
        }
        if (!name) {
            alert('Please fill the name.');
            $('#name').addClass('error');
            return;
        }
        if (!mobile) {
            alert('Please fill the mobile.');
            $('#mobile').addClass('error');
            return;
        }
        if (!problem) {
            alert('Please fill the problem.');
            $('#problem').addClass('error');
            return;
        }
        if (problem.length > 150) {
            alert('Content length can not over 150.');
            $('#problem').addClass('error');
            return;
        }
        $.ajax({
            url: basePath + '/case/subCase',
            type: 'post',
            data: {
                dept: dept,
                name: name,
                mobile: mobile,
                problem: problem,
                companyName: companyName,
                extra: extra
            },
            success: function (data) {
                if (data == 1) {
                    window.location.href = basePath + "/common/toSuccess";
                } else {
                    window.location.href = basePath + "/common/toError";
                }
            }
        });
    });
    var fileName = '';
    $('.upBtn').bind('click', function () {
        $('#fileUpload').click();
        $('#fileUpload').reset();
    });

    $(function() {
        $('#fileUpload').on('change', function () {
            var value = $(this).val();
            if (fileName == value) {
                return;
            };
            fileName = value;
            if (value && value.indexOf('.') > 0) {
                let suffix = value.substring(value.indexOf('.') + 1).toLowerCase();
                if (suffix == 'jpg' || suffix == 'png') {
                    $('#fileUploadForm').ajaxSubmit({
                        url: basePath + '/common/uploadFile',
                        type: 'post',
                        success: function (data) {
                            data = JSON.parse(data)
                            if (data.code == 1) {
                                $('.tip').empty().html(data.fileName);
                                alert('success!');
                                $('#extra').val(data.fileName);
                                $('#fileUpload').empty();
                            } else {
                                alert('Error!');
                            }
                        },
                        error: function (error) {
                            console.log(error)
                        }
                    });
                } else {
                    alert('Image form is incorrect!');
                }
            } else {
                alert('Please select file!');
            }
        });
    })
</script>

</body>
</html>
