module.exports.home = function (application, req, res) {

    var Ocorrencia = new application.app.models.Ocorrencia();
    Ocorrencia.getOcorrencias(function (dadosReq) {
        var dados = JSON.parse(dadosReq.body);
        //console.log(dados);
        res.render('ocorrencias', {dados : dados});
    });
}

module.exports.getUmaOcorrencia = function (application, req, res) {

}
