$(document).ready(function () {
    $('#table_id1').DataTable();
    // getTableData();
    // refreshTable();


    var sUrl = "api/api-get-devices.php";
    $.post(sUrl, function (data) {
        var jData = JSON.parse(data);
        for(var i = 0; i<jData.length; i++) {
            var html = '<div onclick=\'refreshTable()\'>' + jData[i].LocationName + '</div>';
            $('#myDropdown').append(html);
        }
    });

});

function refreshTable() {
    console.log(this.text);
    // setInterval(function () {
    //     getTableData();
    // }, 10000);
}

function getTableData() {
    $.ajax({
        type: "GET",
        url: "api/api-get-sensor-info.php",
        dataType: "json",

    }).done(function (res) {
        var names = res.results[0].series[0].values;
        var table1 = $('#table_id1').DataTable();
        table1.clear();
        for(var i = 0; i < names.length; i++) {
            table1.row.add([names[i]]).draw(false);
        }

    }).fail(function (jqXHR, textStatus, errorThrown) {
        alert("AJAX call failed: " + textStatus + ", " + errorThrown);
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
