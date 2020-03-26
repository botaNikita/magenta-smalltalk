<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
  <head>
    <title>Index</title>
  </head>
  <body>
  <c:choose>
    <c:when test="${not empty sessionScope['userId']}">
      <h1>Hello, ${sessionScope['userName']}</h1>
      <a href="logout">Logout</a>
    </c:when>
    <c:otherwise>
      <a href="login">Login</a>
    </c:otherwise>
  </c:choose>
  </body>
</html>
