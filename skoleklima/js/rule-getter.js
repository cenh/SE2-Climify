var isEdit = false;
var ruleID = -1;

function generateDivs(sensor, operator, value, action, ruleNo, ruleCount,ruleID,actuator){
    var message = "if " +sensor+" is "+operator + " than " +value + " then set " + actuator + " to: " + action;
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
        '<button class="mybtn" id="editRuleBtn" style="float:right">',
        '<i class="fa fa-pencil" aria-hidden="true"></i>',
        '</button>',
        '<button class="mybtn" id="deleteRuleBtn" style="float:right">',
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
    $('#opSelect').empty();
    $('#actuatorSelect').empty();
}

function generateRuleForms(){
    clearRuleForms();
    $.get('api/api-get-operators.php')
        .done(function (res) {
            $("#opSelect").append('<option value="" disabled selected>Select operator</option>');
            opArray = JSON.parse(res);
            for (i=0; i < opArray.length; i++){
                $("#opSelect").append("<option value="+opArray[i].Type+">"+opArray[i].Type+"</option>");
            }
        });
    $.get('api/api-get-actuators-from-location.php', {LocationID: rulelocationChosen()})
        .done(function(res) {
            $("#actuatorSelect").append('<option value="" disabled selected>Select Actuator</option>');
            actuatorArray = JSON.parse(res);
            console.log(actuatorArray);
            for (i=0;i<actuatorArray.length; i++){
                $("#actuatorSelect").append('<option value="'+actuatorArray[i].SensorID+'">'+actuatorArray[i].SensorID+'</option>');
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
    $.get('api/api-get-rules.php', {LocationID: rulelocationChosen()})
        .done(function(res) {
            rules = JSON.parse(res);
        });
    return rules;

};

function getRuleID(attr){
    return ruleID = $(attr).parent().parent().parent().attr('id');

}

$(".accordion").on("click","button#editRuleBtn", function () {
    ruleID = getRuleID(this);
    console.log(ruleID);
    isEdit = true;
    $('#modalCreateRule').modal('toggle');
    $.get('api/api-get-sensors-from-location.php', {LocationID: rulelocationChosen()})
        .done(function (res) {
            $("#sensorSelect").empty();
            results = JSON.parse(res);
            $("#sensorSelect").append('<option value="" disabled selected>Select a sensor</option>');
            for (i=0;i < results.length; i++){
                $("#sensorSelect").append("<option value="+results[i].SensorID+">"+results[i].SensorID+"</option>");
            }
        });
    clearRuleForms();
    generateRuleForms();
});

$("#fetch-sensors-for-loc").on("click",function() {
    var rule_Location = $("#sel_location").val();

    $.get('api/api-get-rules.php', {LocationID: rule_Location})
        .done(function(res) {
            var rules = JSON.parse(res);
            var size = rules.length;
            clearAccordion(size);
            for(i=0;i < rules.length;i++){
                var rule = rules[i];
                generateDivs(rule.SensorID, rule.Operator, rule.Value, rule.setTemp,i,size, rule.RuleID,rule.Actuator);
            }
        });
});



$(".accordion").on("click","button#deleteRuleBtn", function() {
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
    isEdit = false;
    $.get('api/api-get-sensors-from-location.php', {LocationID: rulelocationChosen()})
        .done(function (res) {
            $("#sensorSelect").empty();
            results = JSON.parse(res);
            $("#sensorSelect").append('<option value="" disabled selected>Select a sensor</option>');
            for (i=0;i < results.length; i++){
                $("#sensorSelect").append("<option value="+results[i].SensorID+">"+results[i].SensorID+"</option>");
            }
        });
    clearRuleForms();
    generateRuleForms();
});

$("#sensorSelect").change(function () {
    var sensorID = $('#sensorSelect').val();
    $.get('api/api-get-sensor-type.php', {SensorName: sensorID})
      .done(function (res) {
        results = JSON.parse(res);
        var type = results[0].Category;
        console.log(sensorID);
        console.log(type);
    if (sensorID === "readOutdoorTemperature" || sensorID === "readTemperature") {
        console.log("t");
        $('#unit').text('°C');
    }
    else if (sensorID === "readBattery" || sensorID === "readHumidity" ) {
        $('#unit').text('%');
    }
    else if (sensorID === "readCO2") {
        $('#unit').text('PPM');
    }
    else if (sensorID === "MainIndoorStation_Noise") {
        $('#unit').text('dB');
    }
 }
});

$("#submitRule").on("click",function () {
    var Location = rulelocationChosen();
    var sensorID = $('#sensorSelect').val();
    var op = $('#opSelect').val();
    var value =  $('#selectValue').val();
    var actID = $('#actuatorSelect').val();
    var setTemp =  $('[name=setTemp]').val();
    if(!(isEdit)) {
        $.ajax({
            type: "POST",
            url: "api/api-rule-save.php",
            data: {
                LocationID: Location,
                SensorID:  sensorID,
                Operator: op,
                Value: value,
                actuatorID: actID,
                Action: setTemp
            }
        }).done(function (res) {
            alert("Saved");
        });
    } else {
        if(!(isEdit === -1)) {
            $.ajax({
                type: "POST",
                url: "api/api-rule-update.php",
                data: {
                    RuleID: ruleID,
                    SensorID:  sensorID,
                    Operator: op,
                    Value: value,
                    Actuator: actID,
                    Action: setTemp
                }
            }).done(function (res) {

                alert(res);
                console.log("updated " +ruleID);
            });
        }

    }


});
$('#actuatorSelect').change(function () {

    if($('#actuatorSelect').val() === "ZWaveNode4LC13LivingConnectZThermostat_SetpointHeating"){
        $('#onActionSetTemp').show();
    }
    else {
        $('#onActionSetTemp').hide();
    }
});
