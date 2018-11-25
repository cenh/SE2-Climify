// arrays for displayed items
var sensors = [];
var actuators = [];
var sensors_data = [];
var chosen_actuator;

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
    var table = $('#table_id3').DataTable({
        "searching": false,
        "paging": false,
        "info": false,
        "columns": [
            {
                "className": 'delete',
                "orderable": false
            },
            null
        ],
        "order": [[1, 'asc']]
    });

    // Add event listener for opening and closing details
    $('#table_id3 tbody').on('click', 'td.delete', function () {
        var tr = $(this).closest('tr');
        var row = table.row(tr);
        alert('Delete ' + row.data()[1]);
    });
});

// functions for collapsing
function format_sensors(d) {
    // `d` is the original data object for the row
    var index = sensors.findIndex(function (row) {
        return row.Name === d[1];
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
        return row.Name === d[1];
    });
    var action;
    if(actuators[index].ItemType === "Number"){
        action = '<td><form id="set_input">\n' +
            'Set value: <input type="number" value="10">\n' +
            '<button id="set_button" onclick="set_actuator() ">Set</button>\n' +
            '</form></td>';
        chosen_actuator = actuators[index].Name;
    } else {
        action = '<td>on/off</td>';
    }
    return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' +
        '<tr>' +
        '<td>Category:</td>' +
        '<td>' + actuators[index].Category + '</td>' +
        '</tr>' +
        '<tr>' +
        '<td>Action:</td>' +
        action +
        '</tr>' +
        '</table>';
}



function set_actuator () {
    var x = document.getElementById("set_input");
    var text = x.elements[0].value;
    // Create a client instance
    client = new Paho.MQTT.Client("iot.eclipse.org", Number(443), "/wss");
    client.startTrace();
    // set callback handlers
    client.onConnectionLost = onConnectionLost;
    //client.onMessageArrived = onMessageArrived;
    // connect the client
    client.connect({
        onSuccess: onConnect,
        useSSL: true
    });
    // console.log("attempting to connect...");

    // called when the client connects
    function onConnect() {
        // Once a connection has been made, make a subscription and send a message.
        // console.log("onConnect");
        //client.subscribe("testse2");
        msg = {
            name: chosen_actuator,
            value: text
        };
        msg_text = JSON.stringify(msg);
        message = new Paho.MQTT.Message(msg_text);
        message.destinationName = "commandse2/" + chosen_actuator;
        client.publish(message);

    }

    // called when the client loses its connection
    function onConnectionLost(responseObject) {
        if (responseObject.errorCode !== 0) {
            // console.log("onConnectionLost:" + responseObject.errorMessage);
        }
    }

    // called when a message arrives
    function onMessageArrived(message) {
        msg = JSON.parse(message.payloadString);
        // console.log("MessageArrived\n" + "Message id: " + msg['id'] + " message text: " + msg['text']);
    }
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
        sensors_data = [];
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

        var toPush = [];

        for (var j = 0; j < sensors.length; j++) {
            toPush.push(sensors[j].Name);
        }

        $.post("api/api-get-last-sensors-data.php", {
            sensors_names: toPush,
        }, function (data1) {
            var jData1 = JSON.parse(data1);
            for (j = 0; j < jData1.length; j++) {
                sensors_data.push(JSON.parse(jData1[j]).results[0].series[0].values[0][1]);
            }
        });
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
            table_devices.row.add(['DELETE', jData[i].Label]).draw(false);
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

$("#select_thing").change(function() {
    console.log(this.val());
    refreshTableSensorsAndActuators(this.val());
});