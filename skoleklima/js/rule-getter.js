
$("#fetch-sensors-for-loc").click(function () {
    var location = $("#selectLocation").val();

    // We
    $.get('api/api-get-rules.php', {LocationID: location})
        .done(function(res) {
            var rules = res;
            console.log(rules);
        });
});
