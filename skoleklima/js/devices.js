function format(d) {
    // `d` is the original data object for the row
    return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' +
        '<tr>' +
        '<td>Full name:</td>' +
        '<td></td>' +
        '</tr>' +
        '<tr>' +
        '<td>Extension number:</td>' +
        '<td></td>' +
        '</tr>' +
        '<tr>' +
        '<td>Extra info:</td>' +
        '<td>And any further details here (images etc)...</td>' +
        '</tr>' +
        '</table>';
}

$(document).ready(function () {
    var table = $('#table_id1').DataTable({
        "searching": false,
        "paging": false,
        "info": false,
        "columns": [
            {
                "className": 'details-control'
            }
        ]
    });

    // Add event listener for opening and closing details
    $('#table_id1 tbody').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = table.row(tr);

        if (row.child.isShown()) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            row.child(format(row.data())).show();
            tr.addClass('shown');
        }
    });
});

$(document).ready(function () {
    $('#table_id2').DataTable({
        "searching": false,
        "paging": false,
        "info": false
    });

    // Add event listener for opening and closing details
    $('#table_id1 tbody').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = table.row(tr);

        if (row.child.isShown()) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            row.child(format(row.data())).show();
            tr.addClass('shown');
        }
    });
});

$(document).ready(function () {
    var sUrl = "api/api-get-rooms.php";
    $.post(sUrl, function (data) {
        var jData = JSON.parse(data);
        for (var i = 0; i < jData.length; i++) {
            var x = document.getElementById("select_room");
            var option = document.createElement("option");
            option.text = jData[i].LocationName;
            option.value = jData[i].LocationID;
            x.add(option);
        }
    });
});

function refreshTable(roomID) {
    var sUrl = "api/api-get-devices.php";
    $.post(sUrl, {
        roomID: roomID,
    }, function (data) {
        var jData = JSON.parse(data);
        console.table(jData);
        var table_sensors = $('#table_id1').DataTable();
        var table_actuators = $('#table_id2').DataTable();
        table_sensors.clear();
        table_actuators.clear();
        for (var i = 0; i < jData.length; i++) {
            if(jData[i].ReadOnly == 1)
                table_sensors.row.add([jData[i].Name]).draw(false);
            else
                table_actuators.row.add([jData[i].Name]).draw(false);
        }
        table_sensors.draw(false);
        table_actuators.draw(false);
    });
}
