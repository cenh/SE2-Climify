//roles table
var roles = [];

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

        var index = roles.findIndex(function (r) {
            return r.RoleName === row.data()[0];
        });
        var roleID = roles[index].RoleID;

        var sUrl = "api/api-get-permissions.php";
        // Do AJAX and phase link to api
        var permissions = [];
        $.post(sUrl, {
            roleID: roleID,
            sessionToken: sessionToken,
        }, function (data) {
            var jData = JSON.parse(data);
            permissions = jData;
        });

        if (row.child.isShown()) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            row.child(format_roles(row.data(), permissions)).show();
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

function format_roles(d, permissions) {
    // `d` is the original data object for the row
    var rows = '';
    console.log(permissions);


    for (var i = 0; i < permissions.length; i++) {
        rows += '<tr><td>' + permissions[i].PermDescription + '</td></tr>';
    }

    return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' +
        rows +
        '</table>';
}
