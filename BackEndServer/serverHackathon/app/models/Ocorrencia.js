var objectId = require('mongodb').ObjectId;

function Ocorrencia(connection){
    this._connection = connection();
}

Ocorrencia.prototype.salvarOcorrencia = function (ocorrencia,res) {
    this._connection.open( function(err, mongoclient){
        mongoclient.collection('ocorrencias', function(err, collection){

            ocorrencia['ch_create_time'] = Date.now();
            ocorrencia['ch_atendimento'] = 0;

            collection.insert(ocorrencia);
            res.status(200).json(ocorrencia);
            mongoclient.close();
        });
    });
}

Ocorrencia.prototype.getOcorrencias = function(application, req, res){
    this._connection.open( function(err, mongoclient){
        mongoclient.collection('ocorrencias', function(err, collection){
            collection.find().toArray(function(err, results){
                if(err){
                    res.json(err);
                } else {
                    res.json(results);
                }
                mongoclient.close();
            });
        });
    });
}

Ocorrencia.prototype.getOcorrenciasUrgentes = function(application, req, res){
    this._connection.open( function(err, mongoclient){
        mongoclient.collection('ocorrencias', function(err, collection){
            collection.find({},{"sort":[['ch_atendimento','asc'],['ch_create_time','desc']]}).toArray(function(err, results){
                if(err){
                    res.json(err);
                } else {
                    res.json(results);
                }
                mongoclient.close();
            });
        });
    });
}

Ocorrencia.prototype.getOcorrenciasMedicas = function(application, req, res){
    this._connection.open( function(err, mongoclient){
        mongoclient.collection('ocorrencias', function(err, collection){
            collection.find({"ch_emergencia": true}).toArray(function(err, results){
                if(err){
                    res.json(err);
                } else {
                    res.json(results);
                }
                mongoclient.close();
            });
        });
    });
}

Ocorrencia.prototype.getOcorrenciaById = function(application, req, res){
    this._connection.open( function(err, mongoclient){
        mongoclient.collection('ocorrencias', function(err, collection){
            collection.find(objectId(req.params.id))
                .toArray(function (err, result) {
                    res.json(result);
                });
            mongoclient.close();
        });
    });
}


Ocorrencia.prototype.putOcorrencia = function(application, req, res){
    this._connection.open( function(err, mongoclient){
        mongoclient.collection('ocorrencias', function(err, collection){
            collection.updateOne(
              { "_id": objectId(req.params.id)},
              {
                $set: req.body
              },
            function(err, results){
                if(err){
                    res.json(err);
                } else {
                    res.json(results);
                }
                mongoclient.close();
            });
        });
    });
}

Ocorrencia.prototype.deleteOcorrencia = function(application, req, res){
  this._connection.open( function(err, mongoclient){
    mongoclient.collection('ocorrencias', function(err,collection){
      collection.deleteOne(
          {"_id": objectId(req.params.id)},
        function(err, records){
          if(err){
            res.json(err);
          }else{
            res.json(records);
          }
          mongoclient.close();
        }
      );
    });
  });
}



module.exports = function () {
    return Ocorrencia;
}
