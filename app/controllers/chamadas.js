module.exports.home = function (application, req, res) {
    res.render('chamadas');
}

module.exports.getChamadas = function (application, req, res) {

    var dadosForm = req.body;

    req.checkBody('ch_solicitante', "Nome é obrigatório").notEmpty();
    req.checkBody('ch_telefone', "Telefone é obrigatório").notEmpty();
    req.checkBody('ch_endereco', "Endereço é obrigatório").notEmpty();
    req.checkBody('ch_numero', "Numero é obrigatório").notEmpty();
    req.checkBody('ch_bairro', "Bairro é obrigatório").notEmpty();
    req.checkBody('ch_paciente', "Nome do paciente é obrigatório").notEmpty();
    req.checkBody('ch_idade', "Idade é obrigatório").notEmpty();
    req.checkBody('ch_queixa', "Queixa é obrigatório").notEmpty();
    req.checkBody('ch_municipio', "Municipio é obrigatório").notEmpty();
    //console.log(dadosForm);
    //Arrumar a idade

    var erros = req.validationErrors();

    console.log(erros);

    if(erros){
        return;
    }

    var Ocorrencia = new application.app.models.Ocorrencia();
    Ocorrencia.salvarOcorrencia(dadosForm);
}
