<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<script src="js/script.js"></script>
</head>
<body>

<%	
	String userName = null;
	Cookie[] cookies = request.getCookies();
	if(cookies !=null){
		for(Cookie cookie : cookies){
			if(cookie.getName().equals("user")) userName = cookie.getValue();
		}
	}
	if(userName == null) response.sendRedirect("login.jsp");
%>

<form id="reportForm" action="report" method="POST"></form>
<form id="addProjectToUser" action="projectAddition" method="POST"></form>
<div class="container">
	  <h3>Login successful.</h3>
	  <p>Here is your personal report page.</p>
	  
	  <!-- Button to subscribe the user to a new project -->
	  <div id="selectOptionPersonalPrj">
	      <div id="selectedPersonalPrj" onclick="openSelectPersonalPrj()">....</div>
	      <div id="optionsPersonalPrj" class="close">
		      <c:forEach items="${projectList}" var="project">
		        <div class="optionPersonalPrj">${project.idProgetto} - ${project.nome}</div>
		      </c:forEach>
	      </div>
	      <input id="selectedValuePersonalPrj" form="addProjectToUser" type="hidden" name="selectedValuePersonalPrj" required/>
	      <input type="hidden" form="addProjectToUser" value=<%=userName%> name="userId">
      </div>
	  <button type="submit" form="addProjectToUser" class="projectbtn">Subscribe to this project</button>
	  
	  
	  <hr>
	  
	  <c:forEach items="${usersprojects}" var="project" varStatus="rowStatus">
	       <label><b>${project.nomeProgetto}</b></label>
	       <hr>
	       <select name="dayproject${rowStatus.index}" form="reportForm">
		        <c:forEach items="${project.daysOfTheMonth}" var="day" varStatus="rowCount">
					  <option value="${day}">${day}</option>
				</c:forEach>
			</select>
		<hr>
		<label for="hourNumber"><b>Hour number for the project</b></label>
		<hr>
	  		<input type="number" placeholder="Max hour per day is 8" name="hourNumber${rowStatus.index}" max="8" form="reportForm" required>
	  		<input type="hidden" value="${project.idProgetto}" name="projectName${rowStatus.index}" form="reportForm">
	  		
	  		
	  		
	  		
	  		<hr>
	  		
	  		<!-- bottone per report mensile del progetto in oggetto -->
	  		<form id="projectCalendar" action="projectCalendar" method="POST">
	  			<input type="hidden" value="${project.idProgetto}" name="projectCalendar">
	  			<input type="hidden" value=<%=userName%> name="userId">
	  			<button id="calendar${rowStatus.index}" type="submit" class="calendarbtn">Report mensile del progetto</button>
	  		</form>
	  		<hr>
	  		
	  		<c:set var="projectsNumber" scope ="session" value ="${rowStatus.index}"/>
	</c:forEach>
	
	<input type="hidden" value=<%=userName%> name="userId" form="reportForm">
	<input type="hidden" value="${projectsNumber}" name="projectsNumber" form="reportForm">
   
</div>
<button type="submit" form="reportForm" class="registerbtn">submit</button>

<form action="logout" method="POST">
	<div class="container signin">
    	<p>Do you want to logout? <button type="submit" class="loginbtn"> Logout </button></p>
 	</div>
</form>

</body>
</html>
