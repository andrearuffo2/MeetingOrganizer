<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="css/style.css">
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

<form action="login" method="POST">
  <div class="container">
    <h1>You've been subscribed to the project!</h1>
    <hr>
	
	  <input type="hidden" value="refresh" name="refresh">
	  <input type="hidden" value=<%=userName%> name="userId">
	  <div class="container signin">
	      <p>Go back to your personal report page...  <button type="submit" class="projectbtn"> Back </button></p>
	  </div>
  </div>
  
</form>

	

</body>
</html>
