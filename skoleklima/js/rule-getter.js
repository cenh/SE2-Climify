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
        '<div class="card-body" id="'+ruleNo+'">',
        '<div class="row">',
        '<div class="col-sm-8">',
        message,
        '</div>',
        '<div class="col-sm-4">',
        '<button class="mybtn" style="float:right">',
        '<i class="fa fa-trash" aria-hidden="true"></i>',
        '</button>',
        '</div>',
        '</div>',
        '</div>',
        '</div>',
        '</div>'

    ].join('');
    $(".accordion").append(html);

}

function clearAccordion(size){
    $(".accordion").empty();
    $("#rule-count").text("Rules: "+size);
}

var rulelocationChosen = function() {
    return $("#sel_location").val();
};

$("#fetch-sensors-for-loc").on("click",function() {
    var rule_Location = $("#sel_location").val();

    $.get('api/api-get-rules.php', {LocationID: rule_Location})
        .done(function(res) {
            var rules = JSON.parse(res);
            var size = rules.length;
            clearAccordion(size);
            for(i=0;i < rules.length;i++){
                var rule = rules[i];
                generateDivs(rule.SensorID, rule.Operator, rule.Value, rule.Action,i,size);
            }
        });
});

$(".accordion").on("click","button.mybtn", function() {
    var thisRoom = rulelocationChosen();
    var ruleID = $(this).parent().parent().parent().attr('id');
    console.log("Delete rule " + ruleID + " from room: " + thisRoom);

});

