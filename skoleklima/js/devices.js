var rooms;


$(document).ready(function () {
    $('#table_id1').DataTable();
    // getTableData();
    // refreshTable();
    $('select').formSelect();

    var sUrl = "api/api-get-rooms.php";
    $.post(sUrl, function (data) {
        var jData = JSON.parse(data);
        rooms = jData;
        for(var i = 0; i<jData.length; i++) {
            // var html = '<a onclick=\'refreshTable(this)\'>' + jData[i].LocationName + '</a>';

            var x = document.getElementById("select_room");
            console.log(x);
            var option = document.createElement("option");
            option.text = jData[i].LocationName;
            option.value = jData[i].LocationID;
            x.add(option);


        }
    });

});

function refreshTable(element) {
    var room = element.innerHTML;
    getTableData(room);
}

function getTableData(room) {
    // $.ajax({
    //     type: "GET",
    //     url: "api/api-get-sensor-info.php",
    //     dataType: "json",
    //
    // }).done(function (res) {
    //     var names = res.results[0].series[0].values;
    //     var table1 = $('#table_id1').DataTable();
    //     table1.clear();
    //     for(var i = 0; i < names.length; i++) {
    //         table1.row.add([names[i]]).draw(false);
    //     }
    //
    // }).fail(function (jqXHR, textStatus, errorThrown) {
    //     alert("AJAX call failed: " + textStatus + ", " + errorThrown);
    // });

    var sUrl = "api/api-get-devices.php";
    // Do AJAX and phase link to api
    $.post(sUrl, {
        roomID: room,
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

function dropdown() {
    document.getElementById("myDropdown").classList.toggle("show");
}

function filterFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myInput");
    filter = input.value.toUpperCase();
    div = document.getElementById("myDropdown");
    a = div.getElementsByTagName("a");
    for (i = 0; i < a.length; i++) {
        if (a[i].innerHTML.toUpperCase().indexOf(filter) > -1) {
            a[i].style.display = "";
        } else {
            a[i].style.display = "none";
        }
    }
}
