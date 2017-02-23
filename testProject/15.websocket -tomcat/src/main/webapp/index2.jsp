<%--
  Created by IntelliJ IDEA.
  User: Administra
  Date: 2016/12/27
  Time: 17:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="${ctx}/static/js/sockjs-0.3.min.js"></script>
</head>
<body>
<input type="test">
</body>
</html>
<script>
    var websocket;
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:8080/websocket/websocket");
    } else if ('MozWebSocket' in window) {
        websocket = new MozWebSocket("ws://localhost:8080/websocket/websocket");
    } else {
        websocket = new SockJS("http://localhost:8080/Origami/sockjs/webSocketServer");
    }
    websocket.onopen = function (evnt) {
    };
    websocket.onmessage = function (evnt) {
        $("#msgcount").html("(<font color='red'>"+evnt.data+"</font>)")
    };
    websocket.onerror = function (evnt) {
    };
    websocket.onclose = function (evnt) {
    }

</script>
