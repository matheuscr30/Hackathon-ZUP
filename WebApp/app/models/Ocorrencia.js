var request = require('request');

function Ocorrencia(){
}

Ocorrencia.prototype.getOcorrencias = function(callback){
    var headers = {
        'Content-Type': 'application/json'
    }

    // Configure the request
    var options = {
        url: 'http://172.10.100.42/api/chamada',
        //url: 'http://104.236.94.131/api/chamada/urgencia',
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

Ocorrencia.prototype.salvarOcorrencia = function (ocorrencia, callback) {

    var headers = {
        'Content-Type': 'application/json'
    }

    ocorrencia.ch_status = 'Aberto';
    // Configure the request
    var options = {
        url: 'http://172.10.100.42/api/chamada',
        //url: 'http://104.236.94.131/api/chamada',
        method: 'POST',
        headers: headers,
        form: ocorrencia
    }

    // Start the request
    request(options, function (error, response, body) {
        if (!error && response.statusCode == 200) {
            // Print out the response body
            console.log("eoq");
            console.log(body);
        }
    });
}

module.exports = function () {
    return Ocorrencia;
}
