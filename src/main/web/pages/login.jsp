<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <form method="post" action="login" enctype="application/x-www-form-urlencoded">
        <p>
            <label>
                Login:
                <input type="text" name="login" value="${param['login']}" />
            </label>
        </p>
        <p>
            <label>
                Password:
                <input type="password" name="password" />
            </label>
        </p>
        <input type="submit" value="Login" />
    </form>
    <a href="register" >Register</a>
</body>
</html>
