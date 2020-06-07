var responseBoolean = "";
var responseError = "";
var responseErrorJson = "";
var invitedEmployeeList = new Array();
var modalEmployeeList = new Array();
var meeting;
var invitedEmployeeList = new Array();
var modalEmployeeList = new Array();
var employeeList;

function validateAndMakeCall(){

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
		errorDiv.style.display = "block";
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

			var postResponseBoolean = insertNewMeeting(requestObject);
			if(postResponseBoolean == true){
				var ownMeetingsTableBody = document.getElementById("table-body");

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
			} else {
				modal.style.display = "block";
			}
			meeting = "";
		} else {
			errorParagraph.innerHTML = "You must select at least 1 member other than you for the meeting";
			errorDiv.style.display = "block";
			modal.style.display = "block";
		}
	}
}

function insertNewMeeting(requestObject) {

	httpRequest = new XMLHttpRequest();
	var jsonRequest = JSON.stringify(requestObject);
	var modal = document.getElementById("myModal");
	var postReponseBoolean = false;

	if (!httpRequest) {
		console.log('Unable to create XMLHTTP instance');
		return false;
	}
	httpRequest.open('POST', 'saveNewMeeting', true);
	httpRequest.send(jsonRequest);
	httpRequest.onreadystatechange = function() {
		if (httpRequest.readyState === XMLHttpRequest.DONE) {
			if (httpRequest.status === 200) {
				meeting = JSON.parse(httpRequest.response);
				postReponseBoolean = true;
			} else {
				responseErrorJson = JSON.parse(httpRequest.response);
				responseError = responseErrorJson.errorMessage;
				errorParagraph.innerHTML = responseError;
				errorDiv.style.display = "block";
			}
		}
	}
	return postReponseBoolean;
}