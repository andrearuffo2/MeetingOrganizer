var responseBoolean = "";
var responseError = "";
var invitedEmployeeList = new Array();
var modalEmployeeList = new Array();
var meeting;
var invitedEmployeeList = new Array();
var modalEmployeeList = new Array();
var employeeList;


function Validate()
{
	var name = document.vForm.name;
	var surname = document.vForm.surname;
	var email = document.vForm.email;
	var psw = document.vForm.psw;
	var confpsw = document.vForm.confpsw;

	var emailErr = document.getElementById('errorEmail');
	var errorName = document.getElementById('errorName');
	var errorSurname = document.getElementById('errorEmail');
	var errorPsw = document.getElementById('errorPsw');
	var errorConfirmPassword = document.getElementById('errorConfirmPassword');

	if(responseError != ""){
		alert(responseError);
		return;
	}

	if(responseBoolean == "true"){
		alert("User with this email already exists");
		return;
	}

	if(errorName.innerHTML != "" || errorSurname.innerHTML != "" || emailErr.innerHTML != "" || errorPsw.innerHTML != "" || errorConfirmPassword.innerHTML != ""){
		alert("Please fix registration form typo and retry submit!");
		return false;
	}

	if(name.value == "" || surname.value == "" || email.value == "" || psw.value == "" || confpsw.value == ""){
		alert("Please fill all registration requested fields");
		return false;
	}

}

function validateName(errorId)
{
	var name = document.vForm.name.value;
	var err = document.getElementById(errorId);
	var nameRGEX = /^[a-z ,.'-]+$/i;
	if(!nameRGEX.test(name)){
		err.innerHTML = "invalid name format";
	} else {
		err.innerHTML = "";
	}
}

function validateSurname(errorId)
{
	var surname = document.vForm.surname.value;
	var surnameRGEX = /^[a-z ,.'-]+$/i;
	var err = document.getElementById(errorId);
	if(!surnameRGEX.test(surname)){
		err.innerHTML = "invalid surname format";
	}else {
		err.innerHTML = "";
	}
}

function validateEmail(errorId)
{
	var email = document.vForm.email.value;
	var emailRGEX = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	var err = document.getElementById(errorId);
	if(!emailRGEX.test(email)){
		err.innerHTML = "invalid email format";
	} else {
		err.innerHTML = "";
		checkUserExists(email);
	}
}


function showModal(){
	var title = document.getElementById("title");
	var date = document.getElementById("date");
	var hour = document.getElementById("hour");
	var duration = document.getElementById("duration");
	var members = document.getElementById("members");
	var modalInternalDiv = document.getElementById("employeeModal");
	var modalExternalDiv= document.getElementById("myModal");

	if(responseError != ""){
		document.getElementById("fieldEmptyErrorParagraph").innerHTML = responseError;
		document.getElementById("fieldEmptyErrorParagraph").style.visibility="visible";
	}

	if(modalInternalDiv == null){
		modalInternalDiv = document.createElement("div");
		var closeModalSpan = document.createElement("span");
		closeModalSpan.className="close";
		closeModalSpan.innerHTML="&times;";
		modalInternalDiv.className="modal-content";
		modalInternalDiv.id="employeeModal";
		modalInternalDiv.appendChild(closeModalSpan);
		modalExternalDiv.appendChild(modalInternalDiv);
	}

	if (title.value != "" &&
		date.value != "" &&
		hour.value != "" &&
		duration.value != "" &&
		members.value != "") {
		if(checkFormErrors() == true){
			document.getElementById("fieldEmptyErrorParagraph").innerHTML = "Please fix all form errors";
			document.getElementById("fieldEmptyErrorParagraph").style.visibility="visible";
		} else {
			modalExternalDiv.style.display = "block";
			fillModal();
		}
	} else {
		document.getElementById("fieldEmptyErrorParagraph").innerHTML = "Please fill all the empty form fields";
		document.getElementById("fieldEmptyErrorParagraph").style.visibility="visible";
	}
}

function checkFormErrors(){
	var title = document.getElementById("meetingTitleError");
	var date = document.getElementById("meetingDateError");
	var hour = document.getElementById("meetingHourError");
	var duration = document.getElementById("meetingDurationError");
	var members = document.getElementById("meetingMembersError");
	if(window.getComputedStyle(title).visibility === "visible"||
		window.getComputedStyle(date).visibility === "visible" ||
		window.getComputedStyle(hour).visibility === "visible" ||
		window.getComputedStyle(duration).visibility === "visible" ||
		window.getComputedStyle(members).visibility === "visible") {
		return true;
	} else {
		return false;
	}

}

function hidetitleError(){
	document.getElementById("fieldEmptyErrorParagraph").style.visibility="hidden";
	document.getElementById("meetingTitleError").style.visibility="hidden";
}
function hideDateError(){
	document.getElementById("fieldEmptyErrorParagraph").style.visibility="hidden";
	document.getElementById("meetingDateError").style.visibility="hidden";
}
function hideHourError(){
	document.getElementById("fieldEmptyErrorParagraph").style.visibility="hidden";
	document.getElementById("meetingHourError").style.visibility="hidden";
}
function hideDurationError(){
	document.getElementById("fieldEmptyErrorParagraph").style.visibility="hidden";
	document.getElementById("meetingDurationError").style.visibility="hidden";
}
function hideMembersError(){
	document.getElementById("fieldEmptyErrorParagraph").style.visibility="hidden";
	document.getElementById("meetingMembersError").style.visibility="hidden";
}

function fillModal() {
	retrieveAllEmployee();
	var modalNode = document.getElementById("employeeModal");
	var errorDiv = document.createElement("div");
	var errorParagraph = document.createElement("p");
	const loggedEmployeeEmail = document.getElementById("loggedEmployeeEmail").value;
	errorParagraph.id = "errorParagraph";
	errorParagraph.style.color = "red";
	errorDiv.style.textAlign = "center";
	errorDiv.style.visibility = "hidden";
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
	createSubmitButtonIfNotExists();
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


function validatePsw(errorId)
{
	var psw = document.vForm.psw.value;
	var pswRGEX = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$/;
	var err = document.getElementById(errorId);
	if(!pswRGEX.test(psw)){
		err.innerHTML = "Password must be min 6 chars, at least one letter and one number";
	}else {
		err.innerHTML = "";
	}
}


function validateConfPsw(errorId)
{
	var psw = document.vForm.psw.value;
	var confPsw = document.vForm.confpsw.value;
	var err = document.getElementById(errorId);
	if(confPsw == "" || confPsw != psw){
		err.innerHTML = "confirm psw does not match the psw or is empty";
	}else {
		err.innerHTML = "";
	}
}

function checkUserExists(email) {

	httpRequest = new XMLHttpRequest();
	var parameter = "email="+email;

	if (!httpRequest) {
		console.log('Unable to create XMLHTTP instance');
		return false;
	}
	httpRequest.open('GET', 'register?email='+email);
	httpRequest.send();
	httpRequest.onreadystatechange = function() {
		if (httpRequest.readyState === XMLHttpRequest.DONE) {
			if (httpRequest.status === 200) {
				responseBoolean = httpRequest.response;
			} else {
				responseError = 'Something went wrong..!! Please refresh the page and retry!';
			}
		}
	}
	return responseBoolean;
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

function validateAndSubmit(){

	var modal = document.getElementById("myModal");
	var meetForm = document.meetForm;
	var errorDiv = document.getElementById("errorDiv");
	var errorParagraph = document.getElementById("errorParagraph");
	const loggedEmployeeEmail = document.getElementById("loggedEmployeeEmail").value;

	//Close the modal
	modal.style.display = "none";

	//Check if members of meeting are more then expected
	var membersNumber = meetForm.members.value;
	if(membersNumber < invitedEmployeeList.length){
		var surplusEmployess = invitedEmployeeList.length - membersNumber;

		//Display modal again to display error
		//TODO - Ricorda che nella chiamata devi passare anche l'utente che sta creando la riunione
		errorParagraph.innerHTML = "Too many employees selected. Please deselect at least " + surplusEmployess + " of them"
		errorDiv.style.visibility = "visible";
		modal.style.display = "block";
	} else {

		if (invitedEmployeeList.length > 0) {
			//Filling request object
			var requestObject = {};
			requestObject['title'] = meetForm.title.value;
			requestObject['data'] = meetForm.date.value;
			requestObject['hour'] = meetForm.hour.value;
			requestObject['duration'] = meetForm.duration.value;
			requestObject['members'] = meetForm.members.value;
			requestObject['invitedEmployeeList'] = invitedEmployeeList;
			requestObject['meetingOrganizator'] = loggedEmployeeEmail;

			insertNewMeeting(requestObject);
			var ownMeetingsTable = document.getElementById("ownMeetingsTable");

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
			ownMeetingsTable.appendChild(newMeetingRow);
		} else {
			errorParagraph.innerHTML = "You must select at least 1 member other than you for the meeting";
			errorDiv.style.visibility = "visible";
			modal.style.display = "block";
		}
	}
}

function insertNewMeeting(requestObject) {

	httpRequest = new XMLHttpRequest();
	var jsonRequest = JSON.stringify(requestObject);
	var modal = document.getElementById("myModal");

	if (!httpRequest) {
		console.log('Unable to create XMLHTTP instance');
		return false;
	}
	httpRequest.open('POST', 'saveNewMeeting');
	httpRequest.send(jsonRequest);
	httpRequest.onreadystatechange = function() {
		if (httpRequest.readyState === XMLHttpRequest.DONE) {
			if (httpRequest.status === 200) {
				meeting = JSON.parse(httpRequest.response);
			} else {
				responseError = JSON.parse(httpRequest.response);
				errorParagraph.innerHTML = responseError.detailMessage;
				errorDiv.style.visibility = "visible";
				modal.style.display = "block";
			}
		}
	}
	return responseBoolean;
}

function retrieveAllEmployee() {

	httpRequest = new XMLHttpRequest();

	if (!httpRequest) {
		console.log('Unable to create XMLHTTP instance');
		return false;
	}
	httpRequest.open('GET', 'retrieveEmployee');
	httpRequest.send();
	httpRequest.onreadystatechange = function() {
		if (httpRequest.readyState === XMLHttpRequest.DONE) {
			if (httpRequest.status === 200) {
				employeeList = JSON.parse(httpRequest.response);
			} else {
				responseError = 'Something went wrong..!! Please contact the support team!';
			}
		}
	}
	return responseBoolean;
}

function createSubmitButtonIfNotExists() {
	var submitButton = document.getElementById("submitButton");
	if (submitButton == null) {
		var modalNode = document.getElementById("employeeModal");
		var divNode = document.createElement("div");
		var createdSubmitButton = document.createElement("button");
		createdSubmitButton.type = "submit";
		createdSubmitButton.className = "modal-button";
		createdSubmitButton.innerHTML = "Create meeting";
		createdSubmitButton.id = "submitButton";
		createdSubmitButton.setAttribute("onclick", "validateAndSubmit();")
		divNode.className = "btn-modal-container";
		divNode.appendChild(createdSubmitButton);
		modalNode.appendChild(divNode);
	}
}

function validateMembers(){
	var membersError = document.getElementById("meetingMembersError");
	var meetingMembers = document.getElementById("members").value;
	if(meetingMembers < 1){
		membersError.innerHTML = "Meeting members must be at least 1 other than you";
		membersError.style.visibility = "visible";
	}
}

function validateDuration(){

	var durationError = document.getElementById("meetingDurationError");
	var meetingDuration = document.getElementById("duration").value;
	if(meetingDuration < 1){
		durationError.innerHTML = "Meeting duration must be at least 1 hour";
		durationError.style.visibility = "visible";
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
			hourError.style.visibility = "visible";
		}
	}
}

function validateDate(){
	var dateError = document.getElementById("meetingDateError");
	var today = new Date();
	var startDate = new Date(document.getElementById("date").value);
	startDate.setHours(0,0,0,0);
	today.setHours(0,0,0,0);
	if (startDate.getTime() < today.getTime()) {
		dateError.innerHTML = "Meeting date must be at least today's date ";
		dateError.style.visibility = "visible";
	}
}

function validateTitle(){
	var meetingTitle = document.getElementById("title");
	var titleError = document.getElementById("meetingTitleError");
	if(meetingTitle.value == ""){
		titleError.innerHTML = "Set a title to the meeting";
		titleError.style.visibility = "visible";
	}
}
function goBack() {
	window.history.back();
}