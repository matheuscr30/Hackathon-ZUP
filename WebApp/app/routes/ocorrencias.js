module.exports = function (application) {
    application.get('/ocorrencias', function (req, res) {
        application.app.controllers.ocorrencias.home(application, req, res);
    });

    application.get('/ocorrencia', function (req, res) {
        application.app.controllers.ocorrencias.getUmaOcorrencia(application, req, res);
    });
}
