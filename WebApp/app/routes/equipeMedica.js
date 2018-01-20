module.exports = function (application) {
    application.get('/equipeMedica', function (req, res) {
        application.app.controllers.equipeMedica.home(application, req, res);
    });
}
