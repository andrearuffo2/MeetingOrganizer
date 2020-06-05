<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page import="eu.polimi.tiw.bean.MeetingBean"%>
<%@page import="eu.polimi.tiw.bean.EmployeeBean"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Employee's personal page</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script src="js/script.js"></script>
</head>

<body onload="retrieveAllEmployee();">

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
    <% EmployeeBean employeeBean = (EmployeeBean) request.getAttribute("employeeData");%>
    <h1>Welcome <%=employeeBean.getName()%> <%=employeeBean.getSurname()%></h1>
    <p>Here are displayed your next mettings</p>
    <input type="text" id="loggedEmployeeEmail" style="visibility: hidden" value=<%=employeeBean.getEmail()%>>
    <div class="table-container">
        <table border ="1" align="center" id="ownMeetingsTable">
            <caption><h3>Employee OWN meetings list</h3></caption>
            <tr bgcolor="4CAF50">
                <th><b>Meeting title</b></th>
                <th><b>Meeting Data</b></th>
                <th><b>Meeting Hour</b></th>
                <th><b>Meeting Duration</b></th>
                <th><b>Number of partecipants</b></th>
            </tr>
            <%-- Fetching the attributes of the request object
                 which was previously set by the servlet
                  "StudentServlet.java"
            --%>
            <%ArrayList<MeetingBean> meetingBeanArrayList =
                    (ArrayList<MeetingBean>)request.getAttribute("meetingsOwnList");
                for(MeetingBean s:meetingBeanArrayList){%>
                <%-- Arranging data in tabular form
                --%>
                <tr>
                    <td><%=s.getMeetingTitle()%></td>
                    <td><%=s.getMeetingData()%></td>
                    <td><%=s.getMeetingHour()%></td>
                    <td><%=s.getMeetingsDuration()%></td>
                    <td><%=s.getInvolvedEmployeeNumber()%></td>
                </tr>
            <%}%>
        </table>

        <%--<h1>Displaying Employee invited meetings list</h1>--%>
        <table border ="1" align="center">
            <caption><h3>Employee Invited meetings list</h3></caption>
            <tr bgcolor="4CAF50">
                <th><b>Meeting title</b></th>
                <th><b>Meeting Data</b></th>
                <th><b>Meeting Hour</b></th>
                <th><b>Meeting Duration</b></th>
                <th><b>Number of partecipants</b></th>
            </tr>
            <%-- Fetching the attributes of the request object
                 which was previously set by the servlet
                  "StudentServlet.java"
            --%>
            <%ArrayList<MeetingBean> invitedMeetingBeanArrayList =
                    (ArrayList<MeetingBean>)request.getAttribute("meetingsInvitedList");
                for(MeetingBean s:invitedMeetingBeanArrayList){%>
            <%-- Arranging data in tabular form
            --%>
            <tr>
                <td><%=s.getMeetingTitle()%></td>
                <td><%=s.getMeetingData()%></td>
                <td><%=s.getMeetingHour()%></td>
                <td><%=s.getMeetingsDuration()%></td>
                <td><%=s.getInvolvedEmployeeNumber()%></td>
            </tr>
            <%}%>
        </table>
    </div>
<div/>

<%--FORM to insert new meeting--%>
        <form name="meetForm" action="login" method="POST">

            <div class="container">
                <h1 style="text-align: center">Plan a new meeting</h1>
                <p>Complete all the information below to insert new meeting.</p>
                <hr>
                <p id="fieldEmptyErrorParagraph" style="text-align:center; color:#ff0000; visibility:hidden; font-weight: bold;"></p>
                <label for="title"><b>Meeting title</b></label>
                <input id="title" type="text" placeholder="Enter meeting title" name="title" onfocus="hidetitleError()" onfocusout="validateTitle()" >
                <p id="meetingTitleError" class="errorFormParagraph" style="visibility: hidden; "></p>

                <label for="date"><b>Meeting date</b></label>
                <input id="date" type="date" placeholder="Enter meeting's date" name="date" onfocus="hideDateError()" onfocusout="validateDate()">
                <p id="meetingDateError" class="errorFormParagraph" style="visibility: hidden; "></p>

                <label for="hour"><b>Meeting hour</b></label>
                <input id="hour" type="time" placeholder="Enter meeting's" name="hour" onfocus="hideHourError()" onfocusout="validateHour()">
                <p id="meetingHourError" class="errorFormParagraph" style="visibility: hidden; "></p>

                <label for="duration"><b>Meeting duration</b></label>
                <input id="duration" type="number" placeholder="Enter meeting's duration" name="duration" onfocus="hideDurationError()" onfocusout="validateDuration()">
                <p id="meetingDurationError" class="errorFormParagraph" style="visibility: hidden; "></p>

                <label for="members"><b>Meeting members number</b></label>
                <input id="members" type="number" placeholder="Enter maximum number of members" name="members" onfocus="hideMembersError()" onfocusout="validateMembers()">
                <p id="meetingMembersError" class="errorFormParagraph" style="visibility: hidden; "></p>

            </div>
        </form>
        <div class="btn-container">
            <button id="myBtn" class="modal-button" onclick="showModal()">Click to select members</button>
        </div>

        <!-- The Modal -->
        <div id="myModal" class="modal">
            <%--javascript will populate the modal--%>
        </div>
</body>
</html>