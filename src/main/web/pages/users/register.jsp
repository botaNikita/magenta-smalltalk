<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<jsp:useBean id="data" type="ru.magentasmalltalk.web.viewmodels.RegistrationFormData" scope="request" />

<html>
<head>
    <title>Register</title>
</head>
<body>
<%--@elvariable id="form" type="ru.magentasmalltalk.web.viewmodels.RegistrationFormViewModel"--%>
    <form:form modelAttribute="form" method="post" action="register" enctype="application/x-www-form-urlencoded">
        <security:csrfInput/>
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
        <p>
            <label>
                Name:
                <form:input type="text" path="name" />
            </label>
            <form:errors path="name" cssStyle="color: red;" />
        </p>
        <p>
            <label>
                Role:
                <form:select path="selectedUserRole">
                    <c:forEach items="${data.userRoles}" var="userRole">
                        <option value="${userRole}" selected="${userRole == form.selectedUserRole ? "selected" : ""}">${userRole}</option>
                    </c:forEach>
                </form:select>
            </label>
            <form:errors path="selectedUserRole" cssStyle="color: red;" />
        </p>
        <input type="submit" value="Register" />
    </form:form>
</body>
</html>
