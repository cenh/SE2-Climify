
function loadRoomDetails() {
    //console.log("ROOM DETAILS....");


    $.ajax({
        type: "POST",
        url: "api/api-get-all-sensordata.php",
        data: {
            sensor: 'readOutdoorTemperature'
        }
    }).done(function (res) {
        res = JSON.parse(res);
        var t_latest = res.results[0].series[0].values.slice(-1)[0];
        document.getElementById("temp_cur").innerHTML = t_latest[1].substring(0, 4) + " ℃";
    }).fail(function (jqXHR, textStatus, errorThrown) {
        alert("AJAX call failed: " + textStatus + ", " + errorThrown);
    });
    $.ajax({
        type: "POST",
        url: "api/api-get-all-sensordata.php",
        data: {sensor: 'readHumidity'}
    }).done(function (res) {
        res = JSON.parse(res);
        console.log(res);
        var h_latest = res.results[0].series[0].values.slice(-1)[0];
        document.getElementById("humi_cur").innerHTML = h_latest[1].substring(0, 4);
    });
    $.ajax({
        type:"POST",
        url: "api/api-get-all-sensordata.php",
        data: {
            sensor: 'setTemperature'
        }
    }).done(function (res) {
        res = JSON.parse(res);
        var set_latest = res.results[0].series[0].values.slice(-1)[0];
        document.getElementById("current_set_temp").innerHTML = set_latest[1];
    })

}