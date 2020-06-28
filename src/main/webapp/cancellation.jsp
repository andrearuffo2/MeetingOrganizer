<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <title>Bad Attempts page</title>
</head>
<body>

<form id="homepageForm" action="homepage" method="POST"/>
<div class="container">
    <h1>EmployeeList</h1>
    <p>Select wich employee will be invited to your meeting.</p>
    <hr>

    <c:if test="${error!= null}">
        <p id="fieldEmptyErrorParagraph" style="text-align:center; color:#ff0000; font-weight: bold; font-size: x-large">${error}</p>
    </c:if>

</div>
<button type="submit" form="homepageForm" class="registerbtn">Go Back to the homepage</button>


</body>
</html>
