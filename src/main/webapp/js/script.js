var responseBoolean = "";
var responseError = "";

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

function hideError(eid){
	var x = document.getElementById(eid);
	x.innerHTML="";
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

function goBack() {
	window.history.back();
}