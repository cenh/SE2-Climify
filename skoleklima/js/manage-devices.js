var channels;

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


function refreshDevicesDropdown(roomID) {
    $('#table_channels').DataTable().clear().draw(false);
    var sUrl = "api/api-get-things.php";
    $.post(sUrl, {
        roomID: roomID,
    }, function (data) {
        var jData = JSON.parse(data);
        var x = document.getElementById("select_thing_manage_devices");
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
        for(var i = 0; i < jData.length; i++) {
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


    return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' +
        '<tr>' +
        '<td>Description:</td>' +
        '<td>' + channel.Description + '</td>' +
        '</tr>' +
        '<tr>' +
        '<td>Item Type</td>' + channel.ItemType +
        '</tr>' +
        '</table>';
}