<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Employee's personal page</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>

<%--Devo capire se mi serve--%>
<div id="homePageDiv" style="text-align: center; display: none">
    <h1 id="homepageMessage"></h1>
</div>
<div class="container">

    <h1>Welcome <span id="employee_username">${homePageResponse.employeeEmail}</span></h1>
    <p>Here are displayed your next mettings</p>

    <div class="table-container">

        <table border ="1" align="center">
            <thead>
                <caption><h3>Employee OWN meetings list</h3></caption>
                <tr bgcolor="4CAF50">
                    <th><b>Meeting title</b></th>
                    <th><b>Meeting Data</b></th>
                    <th><b>Meeting Hour</b></th>
                    <th><b>Meeting Duration</b></th>
                    <th><b>Number of partecipants</b></th>
                </tr>
            </thead>
            <tbody id="ownMeetingsTableBody">
                <c:forEach items="${homePageResponse.employeeOwnActiveMeetings}" var="employeeOwnMeeting">
                    <tr>
                        <td>${employeeOwnMeeting.meetingTitle}</td>
                        <td>${employeeOwnMeeting.meetingData}</td>
                        <td>${employeeOwnMeeting.meetingHour}</td>
                        <td>${employeeOwnMeeting.meetingDuration}</td>
                        <td>${employeeOwnMeeting.involvedEmployeeNumber}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <table border ="1" align="center">
            <caption><h3>Employee Invited meetings list</h3></caption>
            <thead>
                <tr bgcolor="4CAF50">
                    <th><b>Meeting title</b></th>
                    <th><b>Meeting Data</b></th>
                    <th><b>Meeting Hour</b></th>
                    <th><b>Meeting Duration</b></th>
                    <th><b>Number of partecipants</b></th>
                </tr>
            </thead>
            <tbody id="invitedMeetingsTableBody">
                <c:forEach items="${homePageResponse.employeeInvitedActiveMeetings}" var="employeeInvitedMeeting">
                    <tr>
                        <td>${employeeInvitedMeeting.meetingTitle}</td>
                        <td>${employeeInvitedMeeting.meetingData}</td>
                        <td>${employeeInvitedMeeting.meetingHour}</td>
                        <td>${employeeInvitedMeeting.meetingDuration}</td>
                        <td>${employeeInvitedMeeting.involvedEmployeeNumber}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<form id="selectForm" action="selectMeetingEmployee" method="POST">

    <div class="container">
        <h1 style="text-align: center">Plan a new meeting</h1>
        <p>Complete all the information below to insert new meeting.</p>
        <hr>

        <c:if test="${error!= null}">
            <p id="fieldEmptyErrorParagraph" style="text-align:center; color:#ff0000; font-weight: bold;">${error}</p>
        </c:if>

        <label for="title"><b>Meeting title</b></label>
        <input id="title" type="text" placeholder="Enter meeting title" name="title" required>

        <label for="date"><b>Meeting date</b></label>
        <input id="date" type="date" placeholder="Enter meeting's date" name="date" required>

        <label for="hour"><b>Meeting hour</b></label>
        <input id="hour" type="time" placeholder="Enter meeting's" name="hour" required>

        <label for="duration"><b>Meeting duration</b></label>
        <input id="duration" type="number" placeholder="Enter meeting's duration" name="duration" required>

        <label for="members"><b>Meeting members number</b></label>
        <input id="members" type="number" placeholder="Enter maximum number of members" name="members" required>

        <hr>
    </div>

</form>

<form id="logoutForm" action="logout" method="POST"/>

<div class="btn-container">
    <button id="myBtn" type="submit" form="selectForm" class="modal-button">Click to select members</button>
    <button id="logoutBtn" type="submit" form="logoutForm" class="modal-button" style="float: right">Logout</button>
</div>

</body>
</html>