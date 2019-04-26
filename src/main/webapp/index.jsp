<%--
  Created by IntelliJ IDEA.
  User: 81008
  Date: 2019/4/25
  Time: 17:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Spring Boot+MySQL 构建简单登录系统</title>
</head>
<body>
    <h2>实例驱动学习之Spring框架</h2>
    <p th:text="Hello,'+${name}+'!'"/>
    <div class="add">
        <h2>注册新账号</h2>
        <form action="add"  method="post">
            用户名:<input type="text" name="name"><br>
            邮箱:<input type="text" name="email"><br>
            密码:<input type="password" name="password">
            <input type="submit" value="注册">
        </form>
    </div>
    <div class="login">
        <h2>登录已有账号</h2>
        <form action="/login" method="post">
            邮箱:<input type="text" name="email"><br>
            密码:<input type="password" name="password">
            <input type="submit" value="登录">
        </form>
    </div>
    <div class="all">
        <h2><a href="/all">查看所有账号信息</a></h2>
    </div>
</body>
</html>
