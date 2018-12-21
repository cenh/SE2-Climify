// @author ciok
// managing roles


var roles = [];
var permissions = [];


// initialize the table
$(document).ready(function () {
    var table = $('#roles_table').DataTable({
        "searching": false,
        "paging": false,
        "info": false,
        "columns": [
            {
                "className": 'details-ctrl'
            }]
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


// get roles and fill the table
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

// format the view which appears after clicking a row
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


// gets permissions for a given role and buildes the modal to change them
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

// take selected permissions and save changes
function change_permissions(roleID) {
    var checks = document.getElementsByClassName('perm');
    perms = [];
    for(var i = 0; i < checks.length; i++) {
        if(checks[i].checked === true)
            perms.push(parseInt(checks[i].value));
    }

    $.post("api/api-update-role.php", {
       RoleID: roleID,
       Permissions: perms
    });

    var modal = document.getElementById('myModal');
    modal.style.display = "none";
    table = $('#roles_table').DataTable().draw();

    table.rows().every(function(){
        // If row has details expanded
        if(this.child.isShown()){
            // Collapse row details
            this.child.hide();
            $(this.node()).removeClass('shown');
        }
    });

    alert('Changes Saved');
}

// add new role with button
function add_role() {
    var modal = document.getElementById('myModal2');
    var span = document.getElementsByClassName("close")[1];

    modal.style.display = "block";
    span.onclick = function() {
        modal.style.display = "none";
    };
}

function createRole() {
    var name = document.getElementById("new_role_name").value;
    if(name === "") {
        alert("Name cannot be empty!");
        return;
    }

    $.post("api/api-check-role-name.php", {
        role_name: name,
        sessionToken: sessionToken
    }, function (data) {
        var jData = JSON.parse(data);
        if(jData.length > 0) {
            alert("A role with this name already exists!");
            return;
        }
        console.log("create new role");
    });
}

// refresh the roles table
function refreshRolesTableWithButton() {
    getTableData();
}
