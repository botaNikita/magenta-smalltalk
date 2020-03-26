<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <form method="post" action="register" enctype="application/x-www-form-urlencoded">
        <p>
            <label>
                Login:
                <input type="text" name="login" />
            </label>
        </p>
        <p>
            <label>
                Password:
                <input type="password" name="password" />
            </label>
        </p>
        <p>
            <label>
                Name:
                <input type="text" name="name" />
            </label>
        </p>
        <p>
            Role:
            <label>
                Admin
                <input type="radio" name="role" value="ADMIN" />
            </label>
            <label>
                User
                <input type="radio" name="role" value="USER" checked="checked" />
            </label>
        </p>
        <input type="submit" value="Register" />
    </form>
</body>
</html>
