module.exports.home = function (application, req, res) {

    var Recurso = new application.app.models.Recurso();
    Recurso.getRecursos(function (dadosReq) {
        var dados = JSON.parse(dadosReq.body);
        //console.log(dados);
        res.render('recursos', {dados : dados});
    });
}

module.exports.mapaRecursos = function (application, req, res) {

    function getDistanceFromLatLonInKm(lat1,lon1,lat2,lon2) {
        var R = 6371; // Radius of the earth in km
        var dLat = deg2rad(lat2-lat1);  // deg2rad below
        var dLon = deg2rad(lon2-lon1);
        var a =
        Math.sin(dLat/2) * Math.sin(dLat/2) +
        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
        Math.sin(dLon/2) * Math.sin(dLon/2)
        ;
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        var d = R * c; // Distance in km
        return d;
    }

    var Recurso = new application.app.models.Recurso();
    Recurso.getRecursos(function (dadosReq) {
        res.render('mapaRecursos', {data : dadosReq.body});
    });
}
