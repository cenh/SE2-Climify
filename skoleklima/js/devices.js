var mysql = require('mysql');

var con = mysql.createConnection({
    host: "localhost:3306",
    user: "root",
    password: "totallysecurepassword"
});

con.connect(function(err) {
    if (err) throw err;
    console.log("Connected!");
});




$(document).ready(function () {
    $('#table_id1').DataTable();
    getTableData();
    refreshTable();
});

function refreshTable() {
    setInterval(function () {
        getTableData();
    }, 10000);
}

function getTableData() {
    $.ajax({
        type: "GET",
        url: "api/api-get-sensor-info.php",
        dataType: "json",

    }).done(function (res) {
        var names = res.results[0].series[0].values;
        var table1 = $('#table_id1').DataTable();
        table1.clear();
        for(var i = 0; i < names.length; i++) {
            table1.row.add([names[i]]).draw(false);
        }

    }).fail(function (jqXHR, textStatus, errorThrown) {
        alert("AJAX call failed: " + textStatus + ", " + errorThrown);
    });

}