<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <title>Meeting Organizer</title>
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
        <input type="password" placeholder="Enter Password" name="psw" required>

    </div>
    <button type="submit" class="registerbtn">Login</button>

</form>

<div class="container signin">
    <p>Already have an account? <a href="registration.jsp">Sign up</a>.</p>
</div>

<c:if test="${error!= null}">
    <div class="container">
        <p style="color:red;">${error}</p>
    </div>
</c:if>

</body>
</html>
