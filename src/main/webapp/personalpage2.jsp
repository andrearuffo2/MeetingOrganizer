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
    <% EmployeeBean employeeBean = (EmployeeBean) request.getAttribute("employeeData"); %>
    <h1>Welcome <%=employeeBean.getName()%> <%=employeeBean.getSurname()%></h1>
    <p>Here are displayed your next mettings</p>
    <div class="table-container">
        <table border ="1" align="center">
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
        <p>Completa all the information below to insert new meeting.</p>
        <hr>
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

    </div>
</form>
<div class="btn-container">
    <button id="myBtn" class="modal-button">Click to select members</button>
</div>

<!-- The Modal -->
<div id="myModal" class="modal">

    <!-- Modal content -->
    <div class="modal-content" id="employeeModal">
        <span class="close">&times;</span>
    </div>

</div>

<script>
    // Get the modal
    var modal = document.getElementById("myModal");

    // Get the button that opens the modal
    var btn = document.getElementById("myBtn");

    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];

    var invitedEmployeeList = new Array();
    var modalEmployeeList = new Array();

    // When the user clicks the button, open the modal
    btn.onclick = function() {
        var title = document.getElementById("title");
        var date = document.getElementById("date");
        var hour = document.getElementById("hour");
        var duration = document.getElementById("duration");
        var members = document.getElementById("members");

        if(title.value != "" &&
            date.value != "" &&
            hour.value != "" &&
            duration.value != "" &&
            members.value != " "){
            modal.style.display = "block";
            fillModal();
        }
    }

    // When the user clicks on <span> (x), close the modal
    span.onclick = function() {
        modal.style.display = "none";
    }

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    function fillModal(){
        var modalNode = document.getElementById("employeeModal");
        for(var i = 0; i < employeeList.length; i++) {
            var isEmployeeAlreadyAddedToTheList = checkEmployeeExistance(employeeList[i].email);
            if(isEmployeeAlreadyAddedToTheList == false){
                var inputElement = document.createElement("input");
                var labelElement = document.createElement("label");
                var breakElement = document.createElement("br");
                inputElement.type="checkbox";
                inputElement.className="checkbox-class";
                inputElement.id = "employee"+[i];
                inputElement.value=employeeList[i].email;
                inputElement.setAttribute("onchange", "handleChange(this)")
                // inputElement.onchange = function(){ handleChange(inputElement.id)}

                labelElement.innerHTML= employeeList[i].name + ' ' + employeeList[i].surname;
                modalNode.appendChild(inputElement);
                modalNode.appendChild(labelElement);
                modalNode.appendChild(breakElement);
                modalEmployeeList.push(employeeList[i].email);
            }
        }
        createSubmitButtonIfNotExists();
    }

    function checkEmployeeExistance(email){
        var isEmployeeExists = false;
        for(var i = 0; i < modalEmployeeList.length; i++){
            if (modalEmployeeList[i] == email) {
                isEmployeeExists = true;
            }
        }
        return isEmployeeExists;
    }

    function createSubmitButtonIfNotExists(){
        var submitButton = document.getElementById("submitButton");
        if(submitButton == null){
            var modalNode = document.getElementById("employeeModal");
            var divNode = document.createElement("div");
            var createdSubmitButton = document.createElement("button");
            createdSubmitButton.type="submit";
            createdSubmitButton.className="modal-button";
            createdSubmitButton.innerHTML="Create meeting";
            createdSubmitButton.id="submitButton";
            createdSubmitButton.setAttribute("onclick", "validateAndSubmit();")
            divNode.className="btn-modal-container";
            divNode.appendChild(createdSubmitButton);
            modalNode.appendChild(divNode);
        }
    }

</script>
</body>
</html>