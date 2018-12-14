var channels;
var in_inbox;

$(document).ready(function () {
    var table = $('#table_channels').DataTable({
        "searching": false,
        "paging": false,
        "info": false,
    });

    // Add event listener for opening and closing details
    $('#table_channels tbody').on('click', 'td', function () {
        var tr = $(this).closest('tr');
        var row = table.row(tr);

        if (row.child.isShown()) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            row.child(format_channels(row.data())).show();
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
        var thing_uid = things[row.index()].UID;
        var rp_uid = things[row.index()].RaspberryPiUID;
        delete_thing(thing_uid, rp_uid);

    });
});


$(document).ready(function () {
    var table = $('#table_listening').DataTable({
        "searching": false,
        "paging": false,
        "info": false,
        "columns": [
            {
                "className": 'approve',
                "orderable": false
            },
            null
        ],
        "order": [[1, 'asc']]
    });

    // Add event listener for opening and closing details
    $('#table_listening tbody').on('click', 'td.approve', function () {
        var tr = $(this).closest('tr');
        var row = table.row(tr);
        console.log('approve');
        var thing_uid = in_inbox[row.index()].ThingUID;
        var rp_uid = in_inbox[row.index()].RaspberryPiUID;
        console.log(thing_uid, rp_uid);
        approve_thing(thing_uid, rp_uid);
    });
});



function refreshDevicesDropdown(roomID) {
    $('#table_channels').DataTable().clear().draw(false);
    var sUrl = "api/api-get-things.php";
    $.post(sUrl, {
        roomID: roomID,
    }, function (data) {
        var jData = JSON.parse(data);
        var x = document.getElementById("select_thing_manage_devices");
        x.selectedIndex = 0;
        x.options.length = 1;
        // var option1 = document.createElement("option");
        for (var i = 0; i < jData.length; i++) {
            var option1 = document.createElement("option");
            option1.text = jData[i].Label;
            option1.value = jData[i].UID;
            x.add(option1);
        }
    });
}

function refreshTableChannel(thingID) {
    var table_channels = $('#table_channels').DataTable().clear().draw(false);
    var sUrl = "api/api-get-channels.php";
    $.post(sUrl, {
        thingID: thingID,
    }, function (data) {
        var jData = JSON.parse(data);
        channels = jData;
        for (var i = 0; i < jData.length; i++) {
            table_channels.row.add([jData[i].Label]).draw(false);
        }
    });
}

function format_channels(d) {
    // `d` is the original data object for the row
    var index = channels.findIndex(function (row) {
        return row.Label === d[0];
    });
    var channel = channels[index];

    var action;

    if (channel.ItemName != null) {
        action = '<tr>' +
            '<td>Already linked with: ' + channel.ItemName + '</td>' + '<td><button onclick="unlink()">Unlink</button></td>' +
            '</tr>';


    } else {
        action = '<tr>' +
            '<td>Currently not linked</td>' + '<td></td>' +
            '</tr>' +
            '<tr>' +
            '<td>Create a new item:<br><form>' +
            'New Name:<br>' +
            '<input type="text" name="name">' +
            '</form><br>' +
            '    <select id="select_category">\n' +
            '        <option value="" disabled selected>Choose Category</option>\n' +
            '    </select>' +
            '    <select id="select_type">\n' +
            '        <option value="" disabled selected>Choose Type</option>\n' +
            '    </select>' +
            '</td>' + '<td><button onclick="link()">Create and Link</button></td>' + '<td></td>' +
            '</tr>';
    }


    return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px; width: 100%;">' +
        '<tr>' +
        '<td>Description:</td>' +
        '<td>' + channel.Description + '</td>' +
        '</tr>' +
        '<tr>' +
        '<td>Item Type: </td>' +
        '<td>' + channel.ItemType + '</td>' +
        '</tr>' +
        action +
        '</table>';
}

function link() {
    alert('link');
}

function unlink() {
    alert('unlink');
}


function refreshTableDevices(roomID) {
    things = [];
    var sUrl = "api/api-get-things.php";
    $.post(sUrl, {
        roomID: roomID,
    }, function (data) {
        var jData = JSON.parse(data);
        var table_devices = $('#table_id3').DataTable();
        table_devices.clear();
        things = jData;
        for (var i = 0; i < jData.length; i++) {
            table_devices.row.add(['DELETE', jData[i].Label]).draw(false);
        }
        table_devices.draw(false);
    });
}

function refreshDevicesTableWithButton() {
    e = document.getElementById('select_room_devices');
    var value = e.options[e.selectedIndex].value;
    if(value !== "")
        refreshTableDevices(value);
}

function unlock_listening() {
    document.getElementById("listen_button").disabled = false;
    document.getElementById("listen_button").style.opacity = "1";
    var e = document.getElementById("select_room_devices_listen");
    var roomID = e.options[e.selectedIndex].value;
    fill_table_listening(roomID);
}

function refresh_table_listening() {
    document.getElementById("p_countdown").innerHTML = "";
    document.getElementById("listen_button").disabled = false;
    document.getElementById("listen_button").style.opacity = "1";

    var e = document.getElementById("select_room_devices_listen");
    var roomID = e.options[e.selectedIndex].value;
    fill_table_listening(roomID);
}

function fill_table_listening(roomID) {
    var sUrl = "api/api-get-inbox.php";
    in_inbox = [];
    $.post(sUrl, {
        roomID: roomID
    }, function (data) {
        var jData = JSON.parse(data);
        in_inbox = jData;
        var table_listening = $('#table_listening').DataTable();
        table_listening.clear();
        things = jData;
        for (var i = 0; i < jData.length; i++) {
            table_listening.row.add(['APPROVE', jData[i].Label]).draw(false);
        }
        table_listening.draw(false);
    });
}