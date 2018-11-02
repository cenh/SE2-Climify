
function loadRoomDetails() {
    console.log("ROOM DETAILS....");
    $.ajax({
        type: "GET",
        url: "api/api-get-current-temperature.php",
        dataType: "json",
    }).done(function (res) {
        console.log(res);
        var t_latest = res.results[0].series[0].values.slice(-1)[0];
        document.getElementById("temp_cur").innerHTML = t_latest[1].substring(0, 4) + " ℃";
    }).fail(function (jqXHR, textStatus, errorThrown) {
        alert("AJAX call failed: " + textStatus + ", " + errorThrown);
    });
    $.ajax({
        type: "GET",
        url: "api/api-get-humidity.php",
        dataType: "json",
    }).done(function (res) {
        console.log(res);
        var h_latest = res.results[0].series[0].values.slice(-1)[0];
        document.getElementById("humi_cur").innerHTML = h_latest[1].substring(0, 4);
    });
}