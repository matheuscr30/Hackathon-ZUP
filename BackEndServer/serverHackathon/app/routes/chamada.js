module.exports = function (application) {
    //POST
    application.post('/api/chamada', function(req, res){
        application.app.controllers.chamada.salvarOcorrencia(application, req, res);
    });

    //GET
    application.get('/api/chamada', function(req, res){
        application.app.controllers.chamada.getOcorrencias(application, req, res);
    });

    //GET BY Urgencia
    application.get('/api/chamada/urgencia', function(req, res){
        application.app.controllers.chamada.getOcorrenciasUrgentes(application, req, res);
    });

    //GET BY Urgencia
    application.get('/api/chamada/medica', function(req, res){
        application.app.controllers.chamada.getOcorrenciasMedicas(application, req, res);
    });

    //GET BY ID
    application.get('/api/chamada/:id', function(req, res){
        application.app.controllers.chamada.getOcorrenciaById(application, req, res);
    });

    //PUT
    application.put('/api/chamada/:id', function(req, res){
        application.app.controllers.chamada.putOcorrencia(application, req, res);
    });

    //DELETE
    application.delete('/api/chamada/:id', function(req, res){
        application.app.controllers.chamada.deleteOcorrencia(application, req, res);
    });
}
