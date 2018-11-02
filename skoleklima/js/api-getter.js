
function loadRoomDetails() {
    console.log("ROOM DETAILS....");
    $.ajax({
        type: "GET",
        url: "http://130.225.69.76/playground/skoleklima/api/api-get-current-temperature.php",
        dataType: "json",
    }).done(function (res) {
        var json_t = JSON.parse(res);
        var t_latest = json_t.results[0].series[0].values.slice(-1)[0];
        document.getElementById("temp_cur").innerHTML = t_latest[1].substring(0, 4) + "C";
    }).fail(function (jqXHR, textStatus, errorThrown) {
        alert("AJAX call failed: " + textStatus + ", " + errorThrown);
    });
    $.ajax({
        type: "GET",
        url: "http://130.225.69.76/playground/skoleklima/api/api-get-humidity.php",
        dataType: "json",
    }).done(function (res) {
        var json_h = JSON.parse(res);
        var h_latest = json_h.results[0].series[0].values.slice(-1)[0];
        document.getElementById("humi_cur").innerHTML = h_latest[1].substring(0, 4);
    })
}