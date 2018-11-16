function generateDivs(sensor, operator, value, action){
    console.log("if " +sensor+" is "+operator + " than" +value + " then" + action );

}

$("#fetch-sensors-for-loc").on("click",function() {
    var rule_Location = $("#sel_location").val();

    $.get('api/api-get-rules.php', {LocationID: rule_Location})
        .done(function(res) {
            var rules = JSON.parse(rules);
            for(i=0;i < rules.length;i++){
                var rule = rules[i]
                //TODO: finish the function
                generateDivs(rule.SensorID, rule.Operator, rule.Value, rule.Action)
            }
        });

    if ($("#sel_location").val()==="1") {
        var room_selected = $("#sel_location").val();
        console.log(room_selected);
        $.post('api/api-rules-query.php', {
            room: room_selected
        }).done(function (res) {
            $(".ui-button-text").text("Room: " + res);
            $("#rules-title").text("Rules for room " + res);
        });
        $(".collapsible").show();

    }
    else if ($("#sel_location").val()==="2") {
        console.log("2");
        $(".collapsible").hide();
        alert("No rules specefied for selected room");
    }
});