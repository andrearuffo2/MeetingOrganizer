<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

<form action="login" method="POST">
    <div class="container">
        <h1>Welcome to the Polimi report App</h1>
        <p>Please enter you account detail to login.</p>
        <hr>

        <label for="email"><b>Email</b></label>
        <input type="email" placeholder="Enter Email" name="email" required>

        <label for="psw"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="psw" maxlength="15" required>

    </div>
    <button type="submit" class="registerbtn">Login</button>

</form>

<form action="registrationData" method="GET">
    <div class="container signin">
        <p>You havent an account? Register! <button type="submit" class="loginbtn">Sign up</button></p>
    </div>
</form>

<c:if test="${error!= null}">
    <div class="container">
        <p><font color="red">${error}</font>
    </div>
</c:if>

</body>
</html>
