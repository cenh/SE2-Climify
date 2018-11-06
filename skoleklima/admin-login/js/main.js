// ***********  Main js *********** //

// Set session
var sessionToken = $("#session-token").val();

// Login
$(document).on("click", "#btn-login-system", function () {
    var username = $("#inp-login-username").val();
    var password = $("#inp-login-password").val();
    if (username != "" && password != "") {
        $.ajax({
            type: "POST",
            url: "api/api-admin-login.php",
            data: {
                sessionToken: sessionToken,
                username: username,
                password: password,
                captcha: grecaptcha.getResponse()
            },
            success: function (data) {
                var jData = JSON.parse(data);
                if (jData.status == "ok") {
                    location.reload();
                } else {
                    grecaptcha.reset();
                    $("#inp-login-username").val("");
                    $("#inp-login-password").val("");
                    $("#inp-login-username").addClass("wrong-login");
                    $("#inp-login-password").addClass("wrong-login");
                    setTimeout(() => {
                        $("#inp-login-username").removeClass("wrong-login");
                        $("#inp-login-password").removeClass("wrong-login");
                    }, 2000);
                }
            }
        })
    }
});

$("#inp-login-username, #inp-login-password").keyup(function (event) {
    if (event.keyCode == 13) {
        $("#btn-login-system").click();
    }
});

$("#btn-sign-out").click(function () {
    signOut();
});

// Sign out
function signOut() {
    // Store link to api in variable
    var sUrl = "../api/api-clear-all-sessions.php";
    // Do AJAX and phase link to api
    $.get(sUrl, function (sData) {
        var jData = JSON.parse(sData);
        if (jData.status == "signOut") {
            $.removeCookie("username");
            $.removeCookie("password");
            location.reload();
        }
    });
}


//roles table

$(document).ready(function () {
    var table = $('#roles_table').DataTable();
    table.draw();
    console.log(table);
    getTableData();
    refreshTable();
});

function refreshTable() {
    setInterval(function () {
        getTableData();
    }, 3000);
}

function getTableData() {
    // $.ajax({
    //     type: "GET",
    //     url: "api/api-get-sensor-info.php",
    //     dataType: "json",
    //
    // }).done(function (res) {
    //     var names = res.results[0].series[0].values;
    //     var table1 = $('#roles_table').DataTable();
    //     table1.clear();
    //     for(var i = 0; i < names.length; i++) {
    //         table1.row.add([names[i]]).draw(false);
    //     }
    //
    // }).fail(function (jqXHR, textStatus, errorThrown) {
    //     alert("AJAX call failed: " + textStatus + ", " + errorThrown);
    // });

    console.log('refreshed');
    var table = $('#roles_table').DataTable();
    //table1.clear();
    table.row.add(['Role']).draw(false);


}