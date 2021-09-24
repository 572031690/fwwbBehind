<%--
  Created by IntelliJ IDEA.
  User: Xgadmin
  Date: 2021/4/18
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<shiro:principal/>
<table width="500px">
    <th>
        <td>序号</td>
        <td>账号名称</td>
        <td>密码</td>
        <td>用户名</td>
    </th>
    <c:forEach items="${requestScope.userlist}" var="user" varStatus="st">
        <tr>
            <td>${st.count}</td>
            <td>${user.username}</td>
            <td>${user.password}</td>
            <td>${user.username}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
