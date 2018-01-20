module.exports = function (application) {
    application.get('/chamadas', function (req, res) {
        application.app.controllers.chamadas.home(application, req, res);
    });

    application.post('/salvarChamada', function (req, res) {
        application.app.controllers.chamadas.salvarChamada(application, req, res);
    });
}
