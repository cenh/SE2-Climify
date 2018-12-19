/**
 * @author Ciok
 */



// arrays for displayed items
var sensors = [];
var actuators = [];
var sensors_data = [];
var actuator_data = [];
var chosen_actuator;
var things = [];

// initialize the tables
$(document).ready(function () {
    var table = $('#table_id1').DataTable({
        "searching": false,
        "paging": false,
        "info": false,
        "columns": [
            {
                "className": 'details-control'
            }]
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
                "className": 'details-control'
            }]
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

            table.rows().eq(0).each( function ( idx ) {
                var row = table.row( idx );
                row.child.hide();
            } );

            // Open this row
            row.child(format_actuators(row.data())).show();
            tr.addClass('shown');

        }
    });
});


// functions for collapsing
function format_sensors(d) {
    // `d` is the original data object for the row
    var index = sensors.findIndex(function (row) {
        return row.Name === d[0];
    });
    return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' +
        '<tr>' +
        '<td>Category:</td>' +
        '<td>' + sensors[index].Category + '</td>' +
        '</tr>' +
        '<tr>' +
        '<td>Last Measurement:</td>' +
        '<td>' + sensors_data[index] + '</td>' +
        '</tr>' +
        '</table>';
}

function format_actuators(d) {
    // `d` is the original data object for the row
    var index = actuators.findIndex(function (row) {
        return row.Name === d[0];
    });
    var action;
    if (actuators[index].ItemType === "Number") {
        action = '<td><form id="set_input_number">\n' +
            'Set value: <input type="number" value="10">\n' +
            '<button id="set_button" onclick="set_actuator_number()">Set</button>\n' +
            '</form></td>';
    } else {
        var is_checked = '';
        if(actuator_data[index] === 'ON')
            is_checked = 'checked';

        action = '<td>Turn on/off<label class="switch" style="margin-left: 10px">\n' +
            '  <input type="checkbox" id="set_on_off" onclick="set_actuator_on_off()"' + is_checked + '>\n' +
            '  <span class="slider round"></span>\n' +
            '</label></td>';
    }
    chosen_actuator = actuators[index].Name;
    return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' +
        '<tr>' +
        '<td>Category:</td>' +
        '<td>' + actuators[index].Category + '</td>' +
        '</tr>' +
        '<tr>' +
        '<td>Current Value:</td>' +
        '<td>' + actuator_data[index] + '</td>' +
        '</tr>' +
        '<tr>' +
        '<td>Action:</td>' +
        action +
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
            var x_devices_listen = document.getElementById("select_room_devices_listen");
            var x_manage_devices = document.getElementById("select_room_manage_devices");
            var option1 = document.createElement("option");
            var option2 = document.createElement("option");
            var option3 = document.createElement("option");
            var option4 = document.createElement("option");
            option1.text = jData[i].LocationName;
            option1.value = jData[i].LocationID;
            option2.text = jData[i].LocationName;
            option2.value = jData[i].LocationID;
            option3.text = jData[i].LocationName;
            option3.value = jData[i].LocationID;
            option4.text = jData[i].LocationName;
            option4.value = jData[i].LocationID;
            x.add(option1);
            x_devices.add(option2);
            x_manage_devices.add(option3);
            x_devices_listen.add(option4);
        }
    });
});

function refreshTableSensorsAndActuatorsWithButton() {
    var e = document.getElementById('select_room');
    var value = e.options[e.selectedIndex].value;

    if (e.selectedIndex !== 0) {
        refreshTableSensorsAndActuators(value);
    }

}

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
        sensors_data = [];
        actuator_data = [];
        for (var i = 0; i < jData.length; i++) {
            if (jData[i].ReadOnly === 1) {
                table_sensors.row.add([jData[i].Name]).draw(false);
                sensors.push(jData[i]);
            }
            else {
                table_actuators.row.add([jData[i].Name]).draw(false);
                actuators.push(jData[i]);
            }
        }
        table_sensors.draw(false);
        table_actuators.draw(false);
        table_sensors.columns.adjust().draw();
        table_actuators.columns.adjust().draw();

        var toPushSensors = [];

        for (var j = 0; j < sensors.length; j++) {
            toPushSensors.push(sensors[j].Name);
        }

        $.post("api/api-get-last-sensors-data.php", {
            sensors_names: toPushSensors,
        }, function (data1) {
            var jData1 = JSON.parse(data1);
            for (j = 0; j < jData1.length; j++) {
                var measurment = JSON.parse(jData1[j]);
                if (measurment.results[0].hasOwnProperty('series')) {
                    sensors_data.push(measurment.results[0].series[0].values[0][1]);
                } else {
                    sensors_data.push('No data recorded');
                }
            }
        });

        var toPushActuators = [];
        for (var k = 0; k < actuators.length; k++) {
            toPushActuators.push(actuators[k].Name);
        }

        $.post("api/api-get-last-actuator-data.php", {
            actuators_names: toPushActuators,
        }, function (data2) {
            var jData2 = JSON.parse(data2);
            for (k = 0; k < jData2.length; k++) {
                var measurment = JSON.parse(jData2[k]);
                if (measurment.results[0].hasOwnProperty('series')) {
                    actuator_data.push(measurment.results[0].series[0].values[0][1]);
                } else {
                    actuator_data.push('No data recorded');
                }
            }
        });


    });
}
