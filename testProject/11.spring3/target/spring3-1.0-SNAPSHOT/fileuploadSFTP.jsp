
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>单文件上传 使用 ajaxfileupload-</title>

    <script src="${ctx}/static/js/jQuery/jquery-1.8.2.js"></script>
    <script src="${ctx}/static/js/ajaxfileupload.js"></script>

</head>
<body>
<input id="fileupload" type="file" name="file"><input type="button" value="上传" onclick="fileupload1()">
<p></p>
</body>


</html>
<script>

    function fileupload1(){

        $.ajaxFileUpload(
                {
                    url:'${ctx}/fileUploadTest',
                    secureuri:false,
                    fileElementId:"fileupload",
                    dataType: 'json',

                    success: function (data, status)
                    {
                        alert('提示',data);
                    },
                    error: function (data, status, e)
                    {
                        console.debug(data);
                    }

                });


    }
</script>