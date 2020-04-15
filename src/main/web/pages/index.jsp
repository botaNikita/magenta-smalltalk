<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
  <head>
    <title>Index</title>
    <script type="text/javascript" src="../scripts/index.js"></script>
  </head>
  <body onload="loadUsers()">
  <c:choose>
    <c:when test="${not empty username}">
      <h1>Hello, ${username}</h1>
      <a href="logout">Logout</a>
    </c:when>
    <c:otherwise>
      <a href="login">Login</a>
    </c:otherwise>
  </c:choose>

  <p>Users:</p>
  <p id="usersList"></p>

  </body>
</html>
