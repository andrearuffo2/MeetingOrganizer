<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <title>Select Employees page</title>
</head>
<body>

<form action="saveNewMeeting" method="POST">
    <div class="container">
        <h1>EmployeeList</h1>
        <p>Select wich employee will be invited to your meeting.</p>
        <hr>

        <c:if test="${error!= null}">
            <p id="fieldEmptyErrorParagraph" style="text-align:center; color:#ff0000; font-weight: bold;">${error}</p>
        </c:if>

        <%--If there's an error i will be redirect baack to the selectemployee.jsp where i have to fill the already checked invited employee list--%>
        <c:choose>
            <c:when test="${invitedEmployeeList!= null}">
                <c:forEach items="${invitedEmployeeList}" var="invitedEmployee">
                    <input class="checkbox-class" type="checkbox" name="employee" value="${invitedEmployee.email}" checked>
                    <label for="invEmployee">${invitedEmployee.name} ${invitedEmployee.surname}</label><br>
                </c:forEach>
                <c:if test="${othersEmployeeList!= null}">
                    <c:forEach items="${othersEmployeeList}" var="othersEmployee">
                        <input class="checkbox-class" type="checkbox" name="employee" value="${othersEmployee.email}">
                        <label for="otherEmployee">${othersEmployee.name} ${othersEmployee.surname}</label><br>
                    </c:forEach>
                </c:if>
            </c:when>
            <c:otherwise>
                <c:forEach items="${selectEmployeeResponse}" var="singleEmployee">
                    <input class="checkbox-class" type="checkbox" name="employee" value="${singleEmployee.email}">
                    <label for="sinEmployee">${singleEmployee.name} ${singleEmployee.surname}</label><br>
                </c:forEach>
            </c:otherwise>
        </c:choose>

    </div>
    <button type="submit" class="registerbtn">Save Meeting</button>
</form>

</body>

<script>

    function manageCounter(){

        ${checkUserCount}
    }
</script>
</html>
