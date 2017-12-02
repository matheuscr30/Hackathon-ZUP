module.exports = function (application) {
    application.get('/', function (req, res) {
        application.app.controllers.index.home(application, req, res);
    });

    application.post('/ocorrencias', function (req, res) {
        application.app.controllers.index.getOcorrencias(application, req, res);
    });

    application.post('/recursos', function (req, res) {
        application.app.controllers.index.getRecursos(application, req, res);
    });

    application.get('/chamada', function (req, res) {
        res.send('');    
    });
}
