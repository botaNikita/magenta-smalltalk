<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
  <head>
    <title>Index</title>
  </head>
  <body>
  <c:choose>
    <c:when test="${not empty sessionScope['login']}">
      <h1>Hello, ${sessionScope['login']}</h1>
    </c:when>
    <c:otherwise>
      <a href="login">Login</a>
    </c:otherwise>
  </c:choose>
  </body>
</html>
