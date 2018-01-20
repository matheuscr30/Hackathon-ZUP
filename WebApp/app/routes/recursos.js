module.exports = function (application) {
    application.get('/recursos', function (req, res) {
        application.app.controllers.recursos.home(application, req, res);
    });

    application.get('/mapaRecursos', function (req, res) {
        application.app.controllers.recursos.mapaRecursos(application, req, res);
    });
}
