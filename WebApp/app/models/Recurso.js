var request = require('request');

function Recurso(){
}

Recurso.prototype.getRecursos = function(callback){
    var headers = {
        'Content-Type': 'application/json'
    }

    // Configure the request
    var options = {
        url: 'http://172.10.100.42/api/recurso',
        //url: 'http://104.236.94.131/api/recurso',
        method: 'GET',
        headers: headers
    }

    // Start the request
    request(options, function (error, response, body) {
        if (!error && response.statusCode == 200) {
            //console.log(body)
            callback(response);
        }
    });
}

Recurso.prototype.salvarRecurso = function (recurso, callback) {

    var headers = {
        'Content-Type': 'application/json'
    }

    // Configure the request
    var options = {
        url: 'http://172.10.100.42/api/recurso',
        //url: 'http://104.236.94.131/api/recurso',
        method: 'POST',
        headers: headers,
        form: recurso
    }

    // Start the request
    request(options, function (error, response, body) {
        if (!error && response.statusCode == 200) {
            // Print out the response body
            console.log("eoq");
            console.log(body)
        }
    });
}

module.exports = function () {
    return Recurso;
}
