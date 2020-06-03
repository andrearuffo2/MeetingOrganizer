var employeeList;

// Da fare all'onload della pagina'
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
                responseError = 'Something went wrong..!! Please refresh the page and retry!';
            }
        }
    }
    return responseBoolean;
}