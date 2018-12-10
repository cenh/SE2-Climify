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
                row.child(rows).show();
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
        for (var i = 0; i < jData.length; i++) {
            if (jData[i].RoleID === roleID)
                rows += '<tr><td>' + jData[i].PermDescription + '</td></tr>';
        }
        if (rows === '')
            rows = '<tr><td>There is no permissions for this role</td></tr>';
        var table = '<table cellspacing="0" border="0" style="padding-left:10px; width: 100%;">' +
            rows + '<tr><td><button onclick="choose_permissions('+roleID+')">Edit</button></td></tr>' +
            '</table>';
        callback(table);
    });
}

function choose_permissions(roleID) {
    var modal = document.getElementById('myModal');
    var span = document.getElementsByClassName("close")[0];
    var perms = document.getElementById("form_wrapper");
    perms.innerHTML = "";

    var form = '<form id="permissions_form">';

    modal.style.display = "block";
    span.onclick = function() {
        modal.style.display = "none";
    };

    var sUrl = "api/api-get-permissions.php";
    // Do AJAX and phase link to api
    $.post(sUrl, {
        roleID: roleID,
        sessionToken: sessionToken,
    }, function (data) {
        var jData = JSON.parse(data);

        for(var i = 0; i < jData.length; i++) {
            var checked = '';
            if(jData[i].RoleID != null)
                checked = 'checked';
            form += '<input type="checkbox" class="perm" style="margin-right: 5px"' + checked + ' ' +
                'value="' + jData[i].PermID + '">' + jData[i].PermDescription + '<br>';
        }
        form += '<input type="button" value="Save Changes" onclick="change_permissions(' + roleID +')"></form>';
        perms.innerHTML = form;
    });



}

function change_permissions(roleID) {
    var checks = document.getElementsByClassName('perm');
    perms = [];
    for(var i = 0; i < checks.length; i++) {
        if(checks[i].checked === true)
            perms.push(checks[i].value);
    }
    console.log(perms);
    console.log(roleID);

    $.post("api/api-update-role.php", {
       RoleID: roleID,
       Permissions: perms
    });

    //here call api

    var modal = document.getElementById('myModal');
    modal.style.display = "none";
    $('#roles_table').DataTable().draw();
    alert('Changes Saved');
}
