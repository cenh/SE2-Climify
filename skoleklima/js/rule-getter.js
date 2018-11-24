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

function clearRuleForms() {
    $('#opselect').empty();
    $('#actionSelect').empty();
}

function generateRuleForms(){
    $.get('api/api-get-operators.php')
        .done(function (res) {
            $("#opSelect").append('<option value="" disabled selected>Select operator</option>');
            opArray = JSON.parse(res);
            for (i=0; i < opArray.length; i++){
                $("#opSelect").append("<option value="+opArray[i].Type+">"+opArray[i].Type+"</option>");
            }
        });
    $.get('api/api-get-actions.php')
        .done(function(res) {
            $("#actionSelect").append('<option value="" disabled selected>Select Action</option>');
            actionsArray = JSON.parse(res);
            for (i=0;i<actionsArray.length; i++){
                $("#actionSelect").append('<option value="'+actionsArray[i].Action+'">'+actionsArray[i].Action+'</option>');
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
                generateDivs(rule.SensorType, rule.Operator, rule.Value, rule.Action,i,size, rule.RuleID);
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
            $("#sensorSelect").append('<option value="" disabled selected>Select a sensor</option>');
            for (i=0;i < results.length; i++){
                $("#sensorSelect").append("<option value="+results[i].SensorID+">"+results[i].SensorTypeName+"</option>");
            }
        });
    clearRuleForms();
    generateRuleForms();
});

/*$("#sensorSelect").change(function(){
    if($("#sensorSelect").val()){
        generateRuleForms();
    }
});*/

$("#submitRule").on("click",function () {
    var Location = rulelocationChosen();
    var sensorID = $('#sensorSelect').val();
    var op = $('#opSelect').val();
    var value =  $('#selectValue').val();
    var action = $('#actionSelect').val();
    var setTemp =  $('[name=setTemp]').val();
    $.ajax({
        type: "POST",
        url: "api/api-rule-save.php",
        data: {
            LocationID: Location,
            SensorID:  sensorID,
            Operator: op,
            Value: value,
            Action: action,
            setTemperature: setTemp
        }
    }).done(function (res) {
        alert(res);
    });

});
$('#actionSelect').change(function () {
    if($('#actionSelect').val() === "Set Temperature"){
        $('#form-action').append('<b> to </b><input type="number" name="setTemp" min="4" max="35">');
    }
});
