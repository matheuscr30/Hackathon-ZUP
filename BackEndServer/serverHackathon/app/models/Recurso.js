var objectId = require('mongodb').ObjectId;

function Recurso(connection){
    this._connection = connection();
}

Recurso.prototype.salvarRecurso = function (recurso,res) {
    this._connection.open( function(err, mongoclient){
        mongoclient.collection('recursos', function(err, collection){
            collection.insert(recurso);
            res.status(200).json(recurso);
            mongoclient.close();
        });
    });
}

Recurso.prototype.getRecursos = function(application, req, res){
    this._connection.open( function(err, mongoclient){
        mongoclient.collection('recursos', function(err, collection){
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

Recurso.prototype.getRecursoById = function(application, req, res){
    this._connection.open( function(err, mongoclient){
        mongoclient.collection('recursos', function(err, collection){
            collection.find({'id':req.params.id})
                .toArray(function (err, result) {
                    res.json(result);
                });
            mongoclient.close();
        });
    });
}


Recurso.prototype.putRecurso = function(application, req, res){
    this._connection.open( function(err, mongoclient){
        mongoclient.collection('recursos', function(err, collection){
            req.body['id'] = req.params.id;
            collection.updateOne(
              {'id':req.params.id},
              {
                $set: req.body
              },
              {upsert:true},
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

Recurso.prototype.deleteRecurso = function(application, req, res){
  this._connection.open( function(err, mongoclient){
    mongoclient.collection('recursos', function(err,collection){
      collection.deleteOne(
          {'id':req.params.id},
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
    return Recurso;
}
