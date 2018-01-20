module.exports.home = function (application, req, res) {
    res.render('dashboard',  {conteudo : 'ocorrencias'});
}

module.exports.getOcorrencias = function (application, req, res) {
    res.send('getOcorrencias');
}

module.exports.getRecursos = function (application, req, res) {
    res.send('getRecursos');
}
