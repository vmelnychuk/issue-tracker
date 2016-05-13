function logOut() {
    httpRequest = new XMLHttpRequest();
    httpRequest.onreadystatechange = function() {
        if(httpRequest.readyState === XMLHttpRequest.DONE && httpRequest.status === 200) {
            console.log("log out");
        }
        if(httpRequest.readyState === XMLHttpRequest.DONE && httpRequest.status === 401) {
            console.log("was not logined");
        }
    };
    httpRequest.open("DELETE", "/login", true);
    httpRequest.send(null);
}