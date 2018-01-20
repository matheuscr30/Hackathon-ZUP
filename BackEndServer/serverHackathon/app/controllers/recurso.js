module.exports.salvarRecurso = function (application, req, res) {
    var connection = application.config.dbConnection;
    var Recurso = new application.app.models.Recurso(connection);

    Recurso.salvarRecurso(req.body, res);
}

module.exports.getRecursos= function (application, req, res) {
    var connection = application.config.dbConnection;
    var Recurso = new application.app.models.Recurso(connection);

    Recurso.getRecursos(application, req, res);
}

module.exports.getRecursoById = function (application, req, res) {
    var connection = application.config.dbConnection;
    var Recurso = new application.app.models.Recurso(connection);

    Recurso.getRecursoById(application, req, res);
}

module.exports.putRecurso = function (application, req, res) {
    var connection = application.config.dbConnection;
    var Recurso = new application.app.models.Recurso(connection);

    Recurso.putRecurso(application, req, res);
}

module.exports.deleteRecurso = function (application, req, res) {
    var connection = application.config.dbConnection;
    var Recurso = new application.app.models.Recurso(connection);

    Recurso.deleteRecurso(application, req, res);
}
