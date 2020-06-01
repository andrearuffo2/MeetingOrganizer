<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" type="text/css" href="css/style.css">
  <script src="js/script.js"></script>
</head>

<body>

<form action="register" method="POST" onsubmit="return Validate()" name="vForm">
  <div class="container">
    <h1>Register </h1>
    <p>Please fill in this form to create an account.</p>
    <hr>

    <label for="name"><b>Nome</b></label>
    <input type="text" placeholder="Name" name="name" onfocus="hideError('errorName')" onfocusout="validateName('errorName')">
    <div class="errorDiv" id="errorName"></div>

    <label for="surname"><b>Cognome</b></label>
    <input type="text" placeholder="Surname" name="surname" onfocus="hideError('errorSurname')" onfocusout="validateName('errorSurname')">
    <div class="errorDiv" id="errorSurname"></div>

    <label for="email"><b>Email</b></label>
    <input type="email" placeholder="Enter Email" name="email" onfocus="hideError('errorEmail')" onfocusout="validateEmail('errorEmail')">
    <div class="errorDiv" id="errorEmail"></div>

    <label for="psw"><b>Password</b></label>
    <input type="password" placeholder="Enter Password" name="psw" maxlength="15" onfocus="hideError('errorPsw')" onfocusout="validatePsw('errorPsw')">
    <div class="errorDiv" id="errorPsw"></div>

    <label for="confpsw"><b>Password</b></label>
    <input type="password" placeholder="Retype Password" name="confpsw" maxlength="15" onfocus="hideError('errorConfirmPassword')" onfocusout="validateConfPsw('errorConfirmPassword')">
    <div class="errorDiv" id="errorConfirmPassword"></div>

    <hr>
  </div>
  <button type="submit" class="registerbtn">Register</button>
  <div class="container signin">
    <p>Already have an account? <a href="login.jsp">Sign in</a>.</p>
  </div>
</form>

</body>
</html>