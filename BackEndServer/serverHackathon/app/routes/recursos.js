module.exports = function (application) {
    //POST
    application.post('/api/recurso', function(req, res){
        application.app.controllers.recurso.salvarRecurso(application, req, res);
    });

    //GET
    application.get('/api/recurso', function(req, res){
        application.app.controllers.recurso.getRecursos(application, req, res);
    });

    //GET BY ID
    application.get('/api/recurso/:id', function(req, res){
        application.app.controllers.recurso.getRecursoById(application, req, res);
    });

    //PUT
    application.put('/api/recurso/:id', function(req, res){
        application.app.controllers.recurso.putRecurso(application, req, res);
    });

    //DELETE
    application.delete('/api/recurso/:id', function(req, res){
        application.app.controllers.recurso.deleteRecurso(application, req, res);
    });
}
