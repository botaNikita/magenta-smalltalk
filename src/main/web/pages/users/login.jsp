<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Login</title>
</head>
<body>
    <%--@elvariable id="form" type="ru.magentasmalltalk.web.viewmodels.LoginFormViewModel"--%>
    <form:form modelAttribute="form" method="post" action="login" enctype="application/x-www-form-urlencoded">
        <p>
            <label>
                Login:
                <form:input type="text" path="login" />
            </label>
            <form:errors path="login" cssStyle="color: red;" />
        </p>
        <p>
            <label>
                Password:
                <form:input type="password" path="password" />
            </label>
            <form:errors path="password" cssStyle="color: red;" />
        </p>
        <input type="submit" value="Login" />
    </form:form>
    <a href="register" >Register</a>
</body>
</html>
