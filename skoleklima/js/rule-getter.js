function generateDivs(sensor, operator, value, action, ruleNo, ruleCount){
    var message = "if " +sensor+" is "+operator + " than " +value + " then " + action.toUpperCase();
    var header_id = 'heading'+ruleNo;
    var body_id = 'collapse'+ruleNo;
    var html = [
        '<div class="card">',
            '<div class="card-header" id="'+header_id+'">',
                '<h5 class="mb-0">',
                '<button class="btn btn-link" type="button" data-toggle="collapse" data-target="#'+body_id+'" aria-expanded="true" aria-controls="'+body_id+'">',
                    'Rule '+(Number(ruleNo)+1).toString(),
                '</button>',
                '</h5>',
            '</div>',
            '<div id="'+body_id+'" class="collapse" aria-labelledby="'+header_id+'" data-parent="#accordionExample">',
                '<div class="card-body">',
                    '<div class="row">',
                        '<div class="col-sm-8">',
                            message,
                        '</div>',
                        '<div class="col-sm-4">',
                            '<label class="switch">',
                                '<input id="rulecheck" type="checkbox">',
                                '<span class="slider round"></span>',
                            '</label>',
                        '</div>',
                    '</div>',
                '</div>',
            '</div>',
        '</div>'

    ].join('');
    $(".accordion").append(html);
    $("#rule-count").append("Rules: "+ruleCount);

}

$("#fetch-sensors-for-loc").on("click",function() {
    var rule_Location = $("#sel_location").val();

    $.get('api/api-get-rules.php', {LocationID: rule_Location})
        .done(function(res) {
            var rules = JSON.parse(res);
            var size = rules.length;
            for(i=0;i < rules.length;i++){
                var rule = rules[i];
                generateDivs(rule.SensorID, rule.Operator, rule.Value, rule.Action,i,size);
            }
        });
});