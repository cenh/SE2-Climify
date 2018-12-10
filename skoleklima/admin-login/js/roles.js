//roles table
var roles = [];
var permissions = [];

$(document).ready(function () {
    var table = $('#roles_table').DataTable({
        "searching": false,
        "paging": false,
        "info": false,
    });

    getTableData();

    // Add event listener for opening and closing details
    $('#roles_table tbody').on('click', 'td', function () {
        var tr = $(this).closest('tr');
        var row = table.row(tr);

        if (row.child.isShown()) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row

            format_roles(row.data(), function (rows) {
                row.child(format_roles(rows)).show();
            });
            tr.addClass('shown');

        }
    });
});

function getTableData() {
    var sUrl = "api/api-get-roles.php";
    // Do AJAX and phase link to api
    $.post(sUrl, {
        sessionToken: sessionToken,
    }, function (data) {
        var jData = JSON.parse(data);
        roles = jData;

        var table = $('#roles_table').DataTable();
        table.clear();

        for (var j = 0; j < roles.length; j++) {
            table.row.add([roles[j].RoleName]).draw(false);
        }
    });
}

function format_roles(d, callback) {
    // `d` is the original data object for the row
    var rows = '';
    var index = roles.findIndex(function (row) {
        return row.RoleName === d[0];
    });
    var roleID = roles[index].RoleID;

    var sUrl = "api/api-get-permissions.php";
    // Do AJAX and phase link to api
    $.post(sUrl, {
        roleID: roleID,
        sessionToken: sessionToken,
    }, function (data) {
        var jData = JSON.parse(data);
        console.table(jData);
        for(var i = 0; i < jData.length; i++) {
            rows += '<tr><td>'+ jData[i].PermDescription +'</td></tr>';
        }
    });
    callback(rows);
}
