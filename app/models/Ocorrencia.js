var request = require('request');

function Ocorrencia(){
}

Ocorrencia.prototype.getOcorrencias = function(callback){
    //get
}

Ocorrencia.prototype.salvarOcorrencia = function (ocorrencia, callback) {
    var halo = JSON.stringify(ocorrencia);

    // Set the headers
    var headers = {
        'Content-Type': 'application/json'
    }

    // Configure the request
    var options = {
        url: 'http://104.236.94.131/chamada/ ',
        method: 'POST',
        headers: headers,
        form: halo
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
    return Ocorrencia;
}
