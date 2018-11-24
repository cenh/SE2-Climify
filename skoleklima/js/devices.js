// arrays for displayed items
var sensors = [];
var actuators = [];

// initialize the tables
$(document).ready(function () {
    var table = $('#table_id1').DataTable({
        "searching": false,
        "paging": false,
        "info": false,
        "columns": [
            {
                "className": 'details-control',
                "orderable": false,
                "data": null,
                "defaultContent": '',
            },
            null
        ],
        "order": [[1, 'asc']]
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
            row.child(format_sensors(row.data())).show();
            tr.addClass('shown');
        }
    });
});

$(document).ready(function () {
    var table = $('#table_id2').DataTable({
        "searching": false,
        "paging": false,
        "info": false,
        "columns": [
            {
                "className": 'details-control',
                "orderable": false,
                "data": null,
                "defaultContent": '',
            },
            null
        ],
        "order": [[1, 'asc']]
    });

    // Add event listener for opening and closing details
    $('#table_id2 tbody').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = table.row(tr);

        if (row.child.isShown()) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            row.child(format_actuators(row.data())).show();
            tr.addClass('shown');
        }
    });
});

$(document).ready(function () {
    $('#table_id3').DataTable({
        "searching": false,
        "paging": false,
        "info": false,
    });
});

// functions for collapsing
function format_sensors(d) {
    // `d` is the original data object for the row
    return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' +
        '<tr>' +
        '<td>Full name:</td>' +
        '<td>' + d[1] + '</td>' +
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

function format_actuators(d) {
    // `d` is the original data object for the row
    return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' +
        '<tr>' +
        '<td>Full name:</td>' +
        '<td>' + d[1] + '</td>' +
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


// dropdown with rooms
$(document).ready(function () {
    var sUrl = "api/api-get-rooms.php";
    $.post(sUrl, function (data) {
        var jData = JSON.parse(data);
        for (var i = 0; i < jData.length; i++) {
            var x = document.getElementById("select_room");
            var x_devices = document.getElementById("select_room_devices");
            var option1 = document.createElement("option");
            var option2 = document.createElement("option");
            option1.text = jData[i].LocationName;
            option1.value = jData[i].LocationID;
            option2.text = jData[i].LocationName;
            option2.value = jData[i].LocationID;
            x.add(option1);
            x_devices.add(option2);
        }
    });
});

// put rows into tables
function refreshTableSensorsAndActuators(roomID) {
    var sUrl = "api/api-get-items.php";
    $.post(sUrl, {
        roomID: roomID,
    }, function (data) {
        var jData = JSON.parse(data);
        var table_sensors = $('#table_id1').DataTable();
        var table_actuators = $('#table_id2').DataTable();
        table_sensors.clear();
        table_actuators.clear();
        sensors = [];
        actuators = [];
        for (var i = 0; i < jData.length; i++) {
            if (jData[i].ReadOnly === 1) {
                table_sensors.row.add(['', jData[i].Name]).draw(false);
                sensors.push(jData[i]);
            }
            else {
                table_actuators.row.add(['', jData[i].Name]).draw(false);
                actuators.push(jData[i]);

            }
        }
        table_sensors.draw(false);
        table_actuators.draw(false);
        console.table(sensors);
        console.table(actuators);
    });
}

function refreshTableDevices(roomID) {
    var sUrl = "api/api-get-things.php";
    $.post(sUrl, {
        roomID: roomID,
    }, function (data) {
        var jData = JSON.parse(data);
        var table_devices = $('#table_id3').DataTable();
        table_devices.clear();
        for (var i = 0; i < jData.length; i++) {
            table_devices.row.add([jData[i].Label]).draw(false);
        }
        table_devices.draw(false);
    });
    document.getElementById("listen_button").disabled = false;
}

// listen for new devices in a chosen room
function listen() {
    var drop = document.getElementById("select_room_devices");
    var roomID = drop.options[drop.selectedIndex].value;
    alert('listen for new devices in room ' + roomID);
}
