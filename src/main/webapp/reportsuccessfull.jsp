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

<!--  Check weather the session has ended -->
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
	   <h1>Report successfull</h1>
	   <hr>
	
		<div class="container signin">
			<p>Back to the personal page <button onclick="goBack()" class="registerbtn">Personal Page</button></p>
		</div>
	</div>

</body>
</html>
