<%--
  Created by IntelliJ IDEA.
  User: mek
  Date: 2021/8/16
  Time: 1:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>登录页面</title>
</head>
<style>
    body {
        font-size:14px;
    }
    label {
        display: block;
    }
</style>
<body>
    <form action="${pageContext.request.contextPath}/web/shirologin" method="post">
        <label>用户名:<input name="username" type="text"></label>
        <label>密码:<input type="password" name="password"></label>
        <input type="submit" value="登录">
    </form>
</body>
</html>
