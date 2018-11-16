function generateDivs(sensor, operator, value, action, ruleNo){
    var message = "if " +sensor+" is "+operator + " than " +value + " then" + action.toUpperCase();
    var header_id = 'rule'+ruleNo;
    var body_id = 'collapse'+ruleNo;
    var html = [
        '<div class="card">',
            '<div class="card-header" id="'+header_id+'">',
                '<h5 class="mb-0">',
                '<button class="btn btn-link" type="button" data-toggle="collapse" data-target="#'+body_id + ' aria-expanded="true" aria-controls="'+body_id+'">',
                    'rule: '+ruleNo,
                '</button>',
                '</h5>',
            '</div>',
            '<div id="'+body_id+'" class="collapse show" aria-labelledby="'+body_id+'" data-parent="#accordionRules">',
                '<div class="card-body">',
                    message,
                '</div>',
             '</div>',
        '</div>'
    ].join('');
    $(".accordion").append(html);
    console.log("done");
}

$("#fetch-sensors-for-loc").on("click",function() {
    var rule_Location = $("#sel_location").val();

    $.get('api/api-get-rules.php', {LocationID: rule_Location})
        .done(function(res) {
            var rules = JSON.parse(res);
            for(i=0;i < rules.length;i++){
                var rule = rules[i]
                //TODO: finish the function
                generateDivs(rule.SensorID, rule.Operator, rule.Value, rule.Action,i)
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