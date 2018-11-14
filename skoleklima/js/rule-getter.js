
$("#fetch-sensors-for-loc").click(function () {
    var rule_Location = $("#selectLocation").val();

    // We
    $.get('api/api-get-rules.php', {LocationID: rule_Location})
        .done(function(res) {
            var rules = res;
            console.log(rules);
        });
});
