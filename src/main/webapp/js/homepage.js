var meeting;
var invitedEmployeeList = new Array();
var invitedMeetingList = new Array();
var ownMeetingList = new Array();
var modalEmployeeList = new Array();
var employeeList = new Array();
var employeeEmail = sessionStorage.getItem('employeeEmail').trim();
var errorUserCounter = 0;



/**
 * homepage management
 */

//IIFE - Immediately-Invoked Function Expression - can be postponed by using defer attribute in the html page
//example: <script src="js/utils.js.js" defer></script>
(function() { // avoid variables ending up in the global scope

    document.getElementById("employee_username").textContent = employeeEmail;

    //Get call to populate homepage with userData. So formElement will be null
    window.addEventListener('load', (e) => {
        e.preventDefault();
        makeCall("GET", 'retrieveMeetingsData?email='+employeeEmail ,null,
            function(req) {
                if (req.readyState == XMLHttpRequest.DONE) {
                    var jsonResponse = JSON.parse(req.response);
                    switch (req.status) {
                        case 200:
                            populateHomePage(jsonResponse);
                            break;
                        case 500: // server error
                            document.getElementById("homePageDiv").style.display="block";
                            document.getElementById("homepageMessage").style.color="red";
                            document.getElementById("homepageMessage").innerHTML = message;
                            break;
                    }
                }
            }
        );
    });

})();

function populateHomePage(homePageResponse){

    employeeList = homePageResponse.employeeBeanList;
    invitedMeetingList = homePageResponse.employeeInvitedActiveMeetings;
    ownMeetingList = homePageResponse.employeeOwnActiveMeetings;

    fillMeetingTables();
}

function fillMeetingTables(){

    var invitedMeetingTableElement = document.getElementById("invitedMeetingsTableBody");
    var ownMeetingsTableElement = document.getElementById("ownMeetingsTableBody");

    //filling ownMeetingsTable
    for(var i = 0; i < ownMeetingList.length; i++){

        var newRow = document.createElement("tr");
        var newTitleColumn = document.createElement("td");
        var newDateColumn = document.createElement("td");
        var newHourColumn = document.createElement("td");
        var newDurationColumn = document.createElement("td");
        var newMembersColumn = document.createElement("td");

        newTitleColumn.innerHTML = ownMeetingList[i].meetingTitle;
        newDateColumn.innerHTML = ownMeetingList[i].meetingData;
        newHourColumn.innerHTML = ownMeetingList[i].meetingHour;
        newDurationColumn.innerHTML = ownMeetingList[i].meetingDuration;
        newMembersColumn.innerHTML = ownMeetingList[i].involvedEmployeeNumber;

        newRow.appendChild(newTitleColumn);
        newRow.appendChild(newDateColumn);
        newRow.appendChild(newHourColumn);
        newRow.appendChild(newDurationColumn);
        newRow.appendChild(newMembersColumn);

        ownMeetingsTableElement.appendChild(newRow);
    }

    //filling invitedMeetingTable
    for(var i = 0; i < invitedMeetingList.length; i++){

        var newRow = document.createElement("tr");
        var newTitleColumn = document.createElement("td");
        var newDateColumn = document.createElement("td");
        var newHourColumn = document.createElement("td");
        var newDurationColumn = document.createElement("td");
        var newMembersColumn = document.createElement("td");

        newTitleColumn.innerHTML = invitedMeetingList[i].meetingTitle;
        newDateColumn.innerHTML = invitedMeetingList[i].meetingData;
        newHourColumn.innerHTML = invitedMeetingList[i].meetingHour;
        newDurationColumn.innerHTML = invitedMeetingList[i].meetingDuration;
        newMembersColumn.innerHTML = invitedMeetingList[i].involvedEmployeeNumber;

        newRow.appendChild(newTitleColumn);
        newRow.appendChild(newDateColumn);
        newRow.appendChild(newHourColumn);
        newRow.appendChild(newDurationColumn);
        newRow.appendChild(newMembersColumn);

        invitedMeetingTableElement.appendChild(newRow);
    }
}

//Modal logic
function showModal(){
    var title = document.getElementById("title");
    var date = document.getElementById("date");
    var hour = document.getElementById("hour");
    var duration = document.getElementById("duration");
    var members = document.getElementById("members");
    var modalInternalDiv = document.getElementById("employeeModal");
    var modalExternalDiv= document.getElementById("myModal");

    if(modalInternalDiv == null){
        modalInternalDiv = document.createElement("div");
        modalInternalDiv.className="modal-content";
        modalInternalDiv.id="employeeModal";
        modalExternalDiv.appendChild(modalInternalDiv);
    }

    if (title.value != "" &&
        date.value != "" &&
        hour.value != "" &&
        duration.value != "" &&
        members.value != "") {
        if(checkFormErrors() == true){
            document.getElementById("fieldEmptyErrorParagraph").innerHTML = "Please fix all form errors";
            document.getElementById("fieldEmptyErrorParagraph").style.display="block";
        } else {
            modalExternalDiv.style.display = "block";
            fillModal();
        }
    } else {
        document.getElementById("fieldEmptyErrorParagraph").innerHTML = "Please fill all the empty form fields";
        document.getElementById("fieldEmptyErrorParagraph").style.display="block";
    }

}

function checkFormErrors(){
    var title = document.getElementById("meetingTitleError");
    var date = document.getElementById("meetingDateError");
    var hour = document.getElementById("meetingHourError");
    var duration = document.getElementById("meetingDurationError");
    var members = document.getElementById("meetingMembersError");
    if(window.getComputedStyle(title).display === "block"||
        window.getComputedStyle(date).display === "block" ||
        window.getComputedStyle(hour).display === "block" ||
        window.getComputedStyle(duration).display === "block" ||
        window.getComputedStyle(members).display === "block") {
        return true;
    } else {
        return false;
    }
}

function fillModal() {
    var modalNode = document.getElementById("employeeModal");
    var errorDiv = document.createElement("div");
    var errorParagraph = document.createElement("p");
    const loggedEmployeeEmail = employeeEmail;
    errorParagraph.id = "errorParagraph";
    errorParagraph.style.color = "red";
    errorDiv.style.textAlign = "center";
    errorDiv.style.display = "none";
    errorDiv.id = "errorDiv";
    errorDiv.appendChild(errorParagraph);
    modalNode.appendChild(errorDiv);

    for (var i = 0; i < employeeList.length; i++) {
        var isEmployeeAlreadyAddedToTheList = checkEmployeeExistance(employeeList[i].email);
        if (isEmployeeAlreadyAddedToTheList == false) {

            //Check if the user to add to the modal is not the one who's logged in.
            if (employeeList[i].email != loggedEmployeeEmail) {
                var inputElement = document.createElement("input");
                var labelElement = document.createElement("label");
                var breakElement = document.createElement("br");
                inputElement.type = "checkbox";
                inputElement.className = "checkbox-class";
                inputElement.id = "employee" + [i];
                inputElement.value = employeeList[i].email;
                //Da modificare secondo nuova logica
                inputElement.setAttribute("onchange", "handleChange(this)")
                // inputElement.onchange = function(){ handleChange(inputElement.id)}

                labelElement.innerHTML = employeeList[i].name + ' ' + employeeList[i].surname;
                modalNode.appendChild(inputElement);
                modalNode.appendChild(labelElement);
                modalNode.appendChild(breakElement);
                modalEmployeeList.push(employeeList[i].email);
            }
        }
    }
    createButtonsIfThemNotExists();
}

function createButtonsIfThemNotExists() {
    var submitButton = document.getElementById("submitButton");
    var closeButton = document.getElementById("closeButton");
    if (submitButton == null && closeButton == null) {
        var modalNode = document.getElementById("employeeModal");
        var divNode = document.createElement("div");
        var createdSubmitButton = document.createElement("button");
        var createdCloseButton = document.createElement("button");
        createdSubmitButton.type = "button";
        createdSubmitButton.className = "modal-button";
        createdSubmitButton.innerHTML = "Create meeting";
        createdSubmitButton.id = "submitButton";
        createdSubmitButton.setAttribute("onclick", "validateAndMakeCall();")
        createdCloseButton.type = "button";
        createdCloseButton.className = "modal-button";
        createdCloseButton.innerHTML = "Close employees view";
        createdCloseButton.id = "closeButton";
        createdCloseButton.setAttribute("onclick", "closeModal()");
        divNode.className = "btn-modal-container";
        divNode.appendChild(createdSubmitButton);
        divNode.appendChild(createdCloseButton)
        modalNode.appendChild(divNode);
    }
}

function checkEmployeeExistance(email) {
    var isEmployeeExists = false;
    for (var i = 0; i < modalEmployeeList.length; i++) {
        if (modalEmployeeList[i] == email) {
            isEmployeeExists = true;
        }
    }
    return isEmployeeExists;
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

//Creating table logic
function validateAndMakeCall(){

    var form = document.getElementById("meetForm");
    var modal = document.getElementById("myModal");
    var errorDiv = document.getElementById("errorDiv");
    var errorParagraph = document.getElementById("errorParagraph");

    //Close the modal
    modal.style.display = "none";

    //Check if members of meeting are more then expected
    var membersNumber = form.members.value;
    if(membersNumber < invitedEmployeeList.length || errorUserCounter > 2){

        if(errorUserCounter > 2) {
            form.reset();
            uncheckInvitedEmployee();
            document.getElementById("cancelErrorParagraph").innerHTML = "Three attempts to set up a meeting with too many participants. The meeting will not be created.";
            document.getElementById("errorModal").style.display = "block";

            //Flush invited user modal errors
            errorParagraph.innerHTML="";
            errorDiv.style.display = "none";

        } else {
            var surplusEmployess = invitedEmployeeList.length - membersNumber;

            //Display modal again to display error
            errorParagraph.innerHTML = "Too many employees selected. Please deselect at least " + surplusEmployess + " of them"
            errorDiv.style.display = "block";
            modal.style.display = "block";

            errorUserCounter++;
        }
    } else {

        if (invitedEmployeeList.length > 0) {
            //Filling request object
            var requestObject = {};
            requestObject['title'] = form.title.value;
            requestObject['data'] = form.date.value;
            requestObject['hour'] = form.hour.value;
            requestObject['duration'] = form.duration.value;
            requestObject['members'] = form.members.value;
            requestObject['invitedEmployeeList'] = invitedEmployeeList;
            requestObject['meetingOrganizator'] = employeeEmail;

            insertNewMeeting(requestObject, form);

            //Flush request parameters
            form.reset();
            meeting = "";
        } else {
            errorParagraph.innerHTML = "You must select at least 1 member other than you for the meeting";
            errorDiv.style.display = "block";
            modal.style.display = "block";
        }
    }
}

function insertNewMeeting(requestObject, form) {

    var modal = document.getElementById("myModal");
    var jsonRequest = JSON.stringify(requestObject);
    makeCustomCall("POST", 'saveNewMeeting', jsonRequest,
        function(req) {
            if (req.readyState == XMLHttpRequest.DONE) {
                var jsonResponse = JSON.parse(req.response);
                switch (req.status) {
                    case 200:
                        //Logica insertNewMeeting
                        meeting = JSON.parse(req.response);
                        addNewMeetingToTable();
                        uncheckInvitedEmployee();
                        window.scrollTo(0, 0);
                        break;
                    case 400: // bad request
                        document.getElementById("homePageDiv").style.display="block";
                        document.getElementById("homepageMessage").innerHTML=jsonResponse.errorMessage
                        document.getElementById("homepageMessage").style.color="red";
                        form.clear();
                        uncheckInvitedEmployee();
                        window.scrollTo(0, 0);
                        break;
                    case 401: // unauthorized
                        document.getElementById("homePageDiv").style.display="block";
                        document.getElementById("homepageMessage").innerHTML=jsonResponse.errorMessage;
                        document.getElementById("homepageMessage").style.color="red";

                        form.clear();
                        uncheckInvitedEmployee();
                        //flush the session if is not.
                        sessionStorage.clear();
                        sleep(1000);
                        window.location.href = "login.html";
                        break;
                    case 406: // not_acceptable
                        document.getElementById("homePageDiv").style.display="block";
                        document.getElementById("homepageMessage").innerHTML=jsonResponse.errorMessage;
                        document.getElementById("homepageMessage").style.color="red";

                        //Flush form field and decheck invited employees
                        form.reset();
                        uncheckInvitedEmployee();
                        window.scrollTo(0, 0);
                        break;
                    case 500: // server error
                        document.getElementById("homePageDiv").style.display="block";
                        document.getElementById("homepageMessage").innerHTML=jsonResponse.errorMessage;
                        document.getElementById("homepageMessage").style.color="red";
                        form.reset();
                        uncheckInvitedEmployee();
                        window.scrollTo(0, 0);
                        break;
                }
            }
        }
    )
}

function addNewMeetingToTable(){

    var ownMeetingsTableBody = document.getElementById("ownMeetingsTableBody");

    //creating the row to add to the table
    var newMeetingRow = document.createElement("tr");

    //Filling new row columns
    var titleColumn = document.createElement("td");
    titleColumn.innerHTML = meeting.meetingTitle;
    var dataColumn = document.createElement("td");
    dataColumn.innerHTML = meeting.meetingData;
    var hourColumn = document.createElement("td");
    hourColumn.innerHTML = meeting.meetingHour;
    var durationColumn = document.createElement("td");
    durationColumn.innerHTML = meeting.meetingDuration;
    var membersColumn = document.createElement("td");
    membersColumn.innerHTML = meeting.involvedEmployeeNumber;

    //adding columns to the row
    newMeetingRow.appendChild(titleColumn);
    newMeetingRow.appendChild(dataColumn);
    newMeetingRow.appendChild(hourColumn);
    newMeetingRow.appendChild(durationColumn);
    newMeetingRow.appendChild(membersColumn);

    //finally add new row to the table
    ownMeetingsTableBody.appendChild(newMeetingRow);

    //Display success message
    document.getElementById("homePageDiv").style.display="block";
    document.getElementById("homepageMessage").style.color="green";
    document.getElementById("homepageMessage").innerHTML = "The new meeting was correcly inserted";
}

// Form validation
function validateMembers(){
    var membersError = document.getElementById("meetingMembersError");
    var meetingMembers = document.getElementById("members").value;
    if(meetingMembers < 1){
        membersError.innerHTML = "Meeting members must be at least 1 other than you";
        membersError.style.display = "block";
    }
}

function validateDuration(){
    var durationError = document.getElementById("meetingDurationError");
    var meetingDuration = document.getElementById("duration").value;
    if(meetingDuration < 1){
        durationError.innerHTML = "Meeting duration must be at least 1 hour";
        durationError.style.display = "block";
    }
}

function validateDate(){
    var dateError = document.getElementById("meetingDateError");
    var today = new Date();
    var startDate = new Date(document.getElementById("date").value);
    startDate.setHours(0, 0, 0, 0);
    today.setHours(0, 0, 0, 0);
    if (startDate.getTime() < today.getTime()) {
        dateError.innerHTML = "Meeting date must be at least today's date ";
        dateError.style.display = "block";
    }
}

function validateTitle(){
    var meetingTitle = document.getElementById("title");
    var titleError = document.getElementById("meetingTitleError");
    if(meetingTitle.value == ""){
        titleError.innerHTML = "Set a title to the meeting";
        titleError.style.display = "block";
    }
}


function validateHour(){
    var hourError = document.getElementById("meetingHourError");
    var startHour = document.getElementById("hour").value;
    var startDate = new Date(document.getElementById("date").value);
    var todaysCompareDate = new Date();
    var today = new Date();
    startDate.setHours(0,0,0,0);
    today.setHours(0,0,0,0);

    if (startDate.getTime() == today.getTime()) {
        //Logic to compare input time with current time
        var m = todaysCompareDate.getMinutes();
        var h = todaysCompareDate.getHours();

        // get input time
        var time = startHour.split(":");
        var hour = time[0];
        if (hour == '00') {
            hour = 24
        }
        var min = time[1];

        var inputTime = hour + "." + min;
        var currentTime = h + "." + m;
        if (h == '00') {
            h = 24
        }

        var totalTime = currentTime - inputTime;
        if (totalTime > -1) {
            hourError.innerHTML = "Meeting date must be at least 1 hours forward ";
            hourError.style.display = "block";
        }
    }
}

//hiding errors
function hidetitleError(){
    document.getElementById("fieldEmptyErrorParagraph").style.display="none";
    document.getElementById("meetingTitleError").style.display="none";
}

function hideDateError(){
    document.getElementById("fieldEmptyErrorParagraph").style.display="none";
    document.getElementById("meetingDateError").style.display="none";
}

function hideHourError(){
    document.getElementById("fieldEmptyErrorParagraph").style.display="none";
    document.getElementById("meetingHourError").style.display="none";
}

function hideDurationError(){
    document.getElementById("fieldEmptyErrorParagraph").style.display="none";
    document.getElementById("meetingDurationError").style.display="none";
}

function hideMembersError() {
    document.getElementById("fieldEmptyErrorParagraph").style.display="none";
    document.getElementById("meetingMembersError").style.display="none";
}

function closeModal(){
    var modalExternalDiv= document.getElementById("myModal");
    modalExternalDiv.style.display="none";

    //Flush the errorDiv content
    document.getElementById("errorParagraph").innerHTML="";
    document.getElementById("errorDiv").style.display="none";
}

function backHomePage(){
    //Flush error counter and error message
    errorUserCounter = 0;
    document.getElementById("cancelErrorParagraph").innerHTML = "";
    //Empty checked invited user
    invitedEmployeeList = [];
    document.getElementById("errorModal").style.display = "none";
}

function sleep(milliseconds) {
    var start = new Date().getTime();
    for (var i = 0; i < 1e7; i++) {
        if ((new Date().getTime() - start) > milliseconds){
            break;
        }
    }
}

function uncheckInvitedEmployee(){
    for (var i = 0; i < employeeList.length; i++) {
        var checkedEmployee = document.getElementById("employee" + i);
        if(checkedEmployee != null && checkedEmployee.checked == true){
            checkedEmployee.checked = false;
        }
    }
    invitedEmployeeList = [];
}