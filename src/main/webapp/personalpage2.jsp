<%@page import="eu.polimi.tiw.bean.MeetingBean"%>
<%@page import="eu.polimi.tiw.bean.EmployeeBean"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Employee's personal page</title>
    <link rel="stylesheet" type="text/css" href="css/peronalpage.css">
    <script src="js/script.js"></script>
    <script src="js/bl-script.js"></script>
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
    <h1>Welcome <%employeeBean.getName();%> <%employeeBean.getSurname();%></h1>
    <p>Here are displayed your next mettings</p>
    <table border ="1" align="center" class="ownmeetingtable">
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
    <table border ="1" align="center" class="invitedmeetingstable">
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
<div/>

<%--FORM to insert new meeting--%>
<div class="container">

</div>

<!-- Trigger/Open The Modal -->
<button id="myBtn" class="w3-btn w3-dark-grey" style="font-size:17px;padding:12px 35px" onclick="fillModal();">Open Modal</button>

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
        modal.style.display = "block";
        fillModal();
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
    }

    function handleChange(element){
        var checkedEmployee = document.getElementById(element.id);
        if(checkedEmployee.checked == true){
            invitedEmployeeList.push(element.value);
        } else{
            for( var i = 0; i < invitedEmployeeList.length; i++){
                if(element.value == invitedEmployeeList[i]){
                    invitedEmployeeList.splice(i, 1);
                }
            }
        }
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

</script>
</body>
</html>