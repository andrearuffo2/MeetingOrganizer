/**
 * Login management
 */

//IIFE - Immediately-Invoked Function Expression - can be postponed by using defer attribute in the html page
//example: <script src="js/utils.js.js" defer></script>
(function() { // avoid variables ending up in the global scope

    document.getElementById("loginbutton").addEventListener('click', (e) => {
        e.preventDefault();
        var form = e.target.closest("form");
        if (form.checkValidity()) {
            makeCall("POST", 'login', e.target.closest("form"),
                function(req) {
                    if (req.readyState == XMLHttpRequest.DONE) {
                        var message = req.responseText;
                        switch (req.status) {
                            case 200:
                                sessionStorage.setItem('employeeEmail', message);
                                window.location.href = "personalpage.html";
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