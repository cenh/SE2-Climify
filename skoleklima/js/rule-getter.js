function generateDivs(sensor, operator, value, action, ruleNo, ruleCount,ruleID){
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
        '<div class="card-body" id="'+ruleID+'">',
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

function generateRuleForms(){
    $('.modal-body').append('<select id="opSelect"></select>');
    $('.modal-body').append('<select id="actionSelect"></select>');

    $.get('api/api-get-operators.php')
        .done(function (res) {
            opArray = JSON.parse(res);
            $('#opSelect').append('<option value=""Select operator></option>');
            for (i=0; i < opArray.length; i++){
                $("#opSelect").append("<option value="+opArray[i].Type+">"+opArray[i].Type+"</option>");
            }
        });


}

function clearAccordion(size){
    $(".accordion").empty();
    $("#rule-count").text("Rules: "+size);
}

var rulelocationChosen = function() {
    return $("#sel_location").val();
};

var getrules = function(){

    $.get('api/api-get-rules.php', {LocationID: rule_Location})
        .done(function(res) {
            rules = JSON.parse(res);
        });
    return rules;

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
                generateDivs(rule.SensorID, rule.Operator, rule.Value, rule.Action,i,size, rule.RuleID);
            }
        });
});

$(".accordion").on("click","button.mybtn", function() {
    var thisRoom = rulelocationChosen();
    var ruleID = $(this).parent().parent().parent().attr('id');
    $.ajax({
        type: "POST",
        url: "api/api-rule-delete.php",
        data: {
            RuleID: ruleID,
            LocationID: thisRoom
        }
    }).done(function () {
        alert("Deleted ruleID "+ ruleID + " from room " + thisRoom);
        clearAccordion(getrules().length);
    });

});

$("#modalRule").on("click",function () {


    $.get('api/api-get-sensors-from-location.php', {LocationID: rulelocationChosen()})
        .done(function (res) {
            $("#sensorSelect").empty();
            results = JSON.parse(res);
            $("#sensorSelect").append('<option value="" >Select a sensor</option>');

            for (i=0;i < results.length; i++){
                $("#sensorSelect").append("<option value="+results[i].SensorID+">"+results[i].SensorID+"</option>");
            }
        });
});

$("#sensorSelect").change(function(){
    if($("#sensorSelect").val()){
        generateRuleForms();
    }
});


