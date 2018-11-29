
$(document).ready(function () {
    var table = $('#table_channels').DataTable({
        "searching": false,
        "paging": false,
        "info": false,
    });
});


function refreshDevicesDropdown(roomID) {
    var sUrl = "api/api-get-things.php";
    $.post(sUrl, {
        roomID: roomID,
    }, function (data) {
        var jData = JSON.parse(data);
        console.table(jData);
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
    console.log(thingID);
    var sUrl = "api/api-get-channels.php";
    $.post(sUrl, {
        thingID: thingID,
    }, function (data) {
        var jData = JSON.parse(data);
        console.table(jData);
    });
}