module.exports.home = function (application, req, res) {
    res.render('chamadas');
}

module.exports.salvarChamada = function (application, req, res) {

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

    if(req.body.ch_emergencia == 'true'){
        req.body.ch_emergencia = 'NAO';
    } else {
        req.body.ch_emergencia = 'SIM';
    }

    console.log(erros);

    if(erros){
        res.render('dashboard', {'conteudo' : 'chamadas', erros : erros, dadosForm : req.body});
        return;
    }

    var Ocorrencia = new application.app.models.Ocorrencia();
    Ocorrencia.salvarOcorrencia(dadosForm);
    res.render('dashboard', {'conteudo': 'ocorrencias', erros : {}, dadosForm : {}});
}
