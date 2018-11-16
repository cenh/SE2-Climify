var rooms;

$(document).ready(function () {
    $('#table_id1').DataTable();

    var sUrl = "api/api-get-rooms.php";
    $.post(sUrl, function (data) {
        var jData = JSON.parse(data);
        rooms = jData;
        for(var i = 0; i<jData.length; i++) {
            // var html = '<a onclick=\'refreshTable(this)\'>' + jData[i].LocationName + '</a>';

            var x = document.getElementById("select_room");
            var option = document.createElement("option");
            option.text = jData[i].LocationName;
            option.value = jData[i].LocationID;
            x.add(option);

        }
    });
});

function refreshTable(roomID) {
    getTableData(roomID);
}

function getTableData(roomID) {


    var sUrl = "api/api-get-devices.php";
    $.post(sUrl, {
        roomID: roomID,
    }, function (data) {
        var jData = JSON.parse(data);
        console.table(jData);
        // var table = $('#roles_table').DataTable();
        // table.clear();
        //
        // for (var i = 0; i < jData.length; i++) {
        //     table.row.add([jData[i].RoleName, jData[i].PermDescription]).draw(false);
        // }
    });

}
