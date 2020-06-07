/**
 * Registration management
 */

//IIFE - Immediately-Invoked Function Expression - can be postponed by using defer attribute in the html page
//example: <script src="js/utils.js.js" defer></script>
(function() { // avoid variables ending up in the global scope

    document.getElementById("registrationbutton").addEventListener('click', (e) => {
        e.preventDefault();
        var form = e.target.closest("form");
        if (form.checkValidity()) {
            makeCall("POST", 'register', e.target.closest("form"),
                function(req) {
                    if (req.readyState == XMLHttpRequest.DONE) {
                        var message = req.responseText;
                        switch (req.status) {
                            case 200:
                                window.location.href = "registrationsuccessfull.html";
                                break;
                            case 400: // bad request
                                document.getElementById("errorDiv").style.display="block";
                                document.getElementById("errormessage").innerHTML = message;
                                break;
                            case 401: // unauthorized
                                document.getElementById("errorDiv").style.display="block";
                                document.getElementById("errormessage").innerHTML = message;
                                break;
                            case 500: // server error
                                document.getElementById("errorDiv").style.display="block";
                                document.getElementById("errormessage").innerHTML = message;
                                break;
                        }
                    }
                }
            );
        } else {
            form.reportValidity();
        }
    });

})();


function validateName(errorId)
{
    var name = document.vForm.name.value;
    var err = document.getElementById(errorId);
    var nameRGEX = /^[a-z ,.'-]+$/i;
    if(!nameRGEX.test(name)){
        err.style.display="block"
        err.innerHTML = "invalid name format";
    }
}

function validateSurname(errorId)
{
    var surname = document.vForm.surname.value;
    var surnameRGEX = /^[a-z ,.'-]+$/i;
    var err = document.getElementById(errorId);
    if(!surnameRGEX.test(surname)){
        err.style.display="block"
        err.innerHTML = "invalid surname format";
    }
}

function validateEmail(errorId)
{
    var email = document.vForm.email.value;
    var emailRGEX = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    var err = document.getElementById(errorId);
    if(!emailRGEX.test(email)){
        err.style.display="block"
        err.innerHTML = "invalid email format";
    }
}

function validatePsw(errorId)
{
    var psw = document.vForm.psw.value;
    var pswRGEX = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$/;
    var err = document.getElementById(errorId);
    if(!pswRGEX.test(psw)){
        err.style.display="block"
        err.innerHTML = "Password must be min 6 chars, at least one letter and one number";
    }
}


function validateConfPsw(errorId)
{
    var psw = document.vForm.psw.value;
    var confPsw = document.vForm.confpsw.value;
    var err = document.getElementById(errorId);
    if(confPsw == "" || confPsw != psw){
        err.style.display="block"
        err.innerHTML = "confirm psw does not match the psw or is empty";
    }
}

function hideError(errorId){
    var err = document.getElementById(errorId);
    err.style.display="none"
}