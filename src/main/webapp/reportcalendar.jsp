<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<script src="js/script.js"></script>
</head>
<body>

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
<div class="container">
    <h3>${projectName}</h3>

	<div class="month">      
	  <ul>
	    <li>
	      ${reportMonth}<br>
	      <span style="font-size:18px">${reportYear}</span>
	    </li>
	  </ul>
	</div>
	
	<ul class="days">
		<c:forEach items="${daysList}" var="day">
		       <li><h1>${day.dayOfMonth}</h1><hr><h2>${day.hourNumber}</h2><hr></li>
		</c:forEach>
	</ul>
</div>
  
<div class="container signin">
	<p>Back to the personal page <button onclick="goBack()" class="registerbtn">Personal Page</button></p>
</div>

</body>
</html>
