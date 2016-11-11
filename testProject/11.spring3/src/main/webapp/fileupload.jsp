<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>用户登录</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
    <meta http-equiv="X-UA-Compatible" content="IE=9" />

    <script src="${ctx}/static/js/jQuery/jquery-1.8.2.js"></script>
    <script src="${ctx}/static/js/jQuery/jquery.ui.widget.js"></script>

    <link href="${ctx}/static/js/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <script src="${ctx}/static/js/bootstrap/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="${ctx}/static/js/jQuery-File-Upload-9.13.1/css/jquery.fileupload.css">
    <%--<link rel="stylesheet" href="${ctx}/static/js/jQuery-File-Upload-9.13.1/css/jquery.fileupload-ui.css">--%>


    <script src="${ctx}/static/js/jQuery-File-Upload-9.13.1/js/jquery.fileupload.js"></script>
    <script src="${ctx}/static/js/jQuery-File-Upload-9.13.1/js/jquery.iframe-transport.js"></script>
<style>

    .bar {
        height: 18px;
        background: green;
    }
</style>
</head>
<body>
<input id="fileupload" type="file" name="files[]" data-url="${ctx}/fileUploadMore" multiple>
<div id="progress">
    <div class="bar" style="width: 0%;"></div>
</div>
</body>


</html>
<script>
    $(function () {
        $('#fileupload').fileupload({
            dataType: 'json',
            add: function (e, data) {
                data.context = $('<button/>').text('Upload')
                        .appendTo(document.body)
                        .click(function () {
                            $(this).replaceWith($('<p/>').text('Uploading...'));
                            data.submit();
                        });
            },
            progressall: function (e, data) {
                //进度条
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#progress .bar').css(
                        'width',
                        progress + '%'
                );
            },
            done: function (e, data) {
                alert("1111111111");
                data.context.text('Upload finished.');
            }
        });
    });
</script>