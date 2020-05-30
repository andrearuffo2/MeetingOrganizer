<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" type="text/css" href="css/style.css">
  <script src="js/script.js"></script>
</head>

<body onload="start()">

<form action="register" method="POST" onsubmit="return Validate()" name="vForm">
  <div class="container">
    <h1>Register </h1>
    <p>Please fill in this form to create an account.</p>
    <hr>
  
  	<label for="name"><b>Nome</b></label>
    <input type="text" placeholder="Name" name="name" required>
    
    <label for="surname"><b>Cognome</b></label>
    <input type="text" placeholder="Surname" name="surname" required>

    <label for="email"><b>Email</b></label>
    <input type="email" placeholder="Enter Email" name="email" required>
    
    <label for="Departments"><b>Departments</b></label>
    
    <div id="selectOptionDip">
      <div id="selectedDip" onclick="openSelectDip()">....</div>
      <div id="optionsDip" class="close">
	      <c:forEach items="${departmentList}" var="department">
	        <div class="optionDip">${department.idDipartimento} - ${department.nome}</div>
	      </c:forEach>
      </div>
      <input id="selectedValueDip" type="hidden" name="selectedValueDip" required/>
    </div>
    
    <label for="psw"><b>Password</b></label>
    <input type="password" placeholder="Enter Password" name="psw" maxlength="8" required>
    
    <label for="Projects"><b>Projects</b></label>
    
    <div id="selectOptionPrj">
      <div id="selectedPrj" onclick="openSelectPrj()">....</div>
      <div id="optionsPrj" class="close">
	      <c:forEach items="${projectList}" var="project">
	        <div class="optionPrj">${project.idProgetto} - ${project.nome}</div>
	      </c:forEach>
      </div>
      <input id="selectedValuePrj" type="hidden" name="selectedValuePrj" required/>
    </div>

    <hr>
  </div>
  <button type="submit" class="registerbtn">Register</button>
  <div class="container signin">
      <p>Already have an account? <a href="login.jsp">Sign in</a>.</p>
  </div>
</form>
  
</body>
</html>
