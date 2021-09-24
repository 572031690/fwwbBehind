<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<body>
<h2>功能列表</h2>
<%--验证是否登录认证，已登录的认证--%>
<shiro:authenticated>
<%--  输出当前用户  --%>
    <shiro:principal></shiro:principal>
    <div style="float: left;">
        <a href="${pageContext.request.contextPath}/web/logout">注销</a>
    </div>

</shiro:authenticated>

<%--未登录时显示标签中包裹的内容--%>
<shiro:notAuthenticated>
    欢迎您<a href="${pageContext.request.contextPath}/web/gologin">请登录</a>
</shiro:notAuthenticated><br>
<shiro:guest>
    游客您好，您还未登录
</shiro:guest>


<hr>
<shiro:hasRole name="role1">
    <a href="#">查看用户</a>
</shiro:hasRole>
<shiro:hasRole name="admin">
    <a href="#">修改用户</a>
<%--    <a href="/adminUer/update">添加用户</a>--%>
    <a href="#">删除用户</a>
</shiro:hasRole>
<shiro:hasPermission name="user:create">
    <a href="#">添加用户</a>
</shiro:hasPermission><br>
<shiro:hasPermission name="user:delete">
    <a href="#">删除用户</a>
</shiro:hasPermission>
</body>
</html>
