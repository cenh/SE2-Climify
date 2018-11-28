
function refreshDevicesDropdown(roomID) {
    var sUrl = "api/api-get-things.php";
    $.post(sUrl, {
        roomID: roomID,
    }, function (data) {
        var jData = JSON.parse(data);
        console.table(jData);
    });
}