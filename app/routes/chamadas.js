module.exports = function (application) {
    application.get('/chamadas', function (req, res) {
        application.app.controllers.chamadas.home(application, req, res);
    });

    application.post('/getChamadas', function (req, res) {
        application.app.controllers.chamadas.getChamadas(application, req, res);
    });

    application.get('/teste', function (req, res) {
        res.render('teste');
    });
}
