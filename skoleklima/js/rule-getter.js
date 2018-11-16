function generateDivs(sensor, operator, value, action, ruleNo){
    var message = "if " +sensor+" is "+operator + " than " +value + " then " + action.toUpperCase();
    var header_id = 'rule'+ruleNo;
    var body_id = 'collapse'+ruleNo;
    var html = [
        '<div class="container-fluid">',
            '<h2 id="rules-title">Rules for room '+ruleNo+'</h2>',
                '<div class="container">',
                    '<div class="row">',
                        '<div class="col-lg-8">Rule '+ruleNo,
                            '<b>Rule: </b>' +message,
                        '</div>',
                        '<div class="col-lg-3">',
                            '<label class="switch">',
                                '<input id="rulecheck" type="checkbox">',
                                '<span class="slider round"></span>',
                            '</label>',
                        '</div>',
                    '</div>',
                '</div>',
        '</div>'
    ].join('');
    $(".content").append(html);
}

$("#fetch-sensors-for-loc").on("click",function() {
    var rule_Location = $("#sel_location").val();

    $.get('api/api-get-rules.php', {LocationID: rule_Location})
        .done(function(res) {
            var rules = JSON.parse(res);
            for(i=0;i < rules.length;i++){
                var rule = rules[i];
                generateDivs(rule.SensorID, rule.Operator, rule.Value, rule.Action,i)
            }
        });
    var room_selected = $("#sel_location").val();
    $.post('api/api-rules-query.php', {
        room: room_selected
    }).done(function (res) {
        $(".ui-button-text").text("Room: " + res);
        $("#rules-title").text("Rules for room " + res);
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