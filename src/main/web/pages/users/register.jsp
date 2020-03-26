<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="form" type="ru.magentasmalltalk.web.viewmodels.RegistrationFormViewModel" scope="request" />

<html>
<head>
    <title>Register</title>
</head>
<body>
    <form method="post" action="register" enctype="application/x-www-form-urlencoded">
        <p>
            <label>
                Login:
                <input type="text" name="login" value="${form.login}" />
            </label>
        </p>
        <p>
            <label>
                Password:
                <input type="password" name="password" value="${form.password}" />
            </label>
        </p>
        <p>
            <label>
                Name:
                <input type="text" name="name" value="${form.name}" />
            </label>
        </p>
        <p>
            <label>
                Role:
                <select name="role">
                    <c:forEach items="${form.userRoles}" var="userRole">
                        <option value="${userRole}" selected="${userRole == form.selectedUserRole ? "selected" : ""}">${userRole}</option>
                    </c:forEach>
                </select>
            </label>
        </p>
        <input type="submit" value="Register" />
    </form>
</body>
</html>
