<?php
//************************************************
//	Rule execution
//************************************************

require_once "../meta-influx.php";

$sensor_1 = $_POST['sensor1'];
$sensor_2 = $_POST['sensor2'];
$op_1 = $_POST['op1'];
$op_2 = $_POST['op2'];
$value_1 = $_POST['value1'];
$value_2 = $_POST['value2'];
$action_1 = $_POST['action1'];
$action_2 = $_POST['action2'];

error_log("Sensor: $sensor_1", 0);
error_log("Op: $op_1", 0);
error_log("Value: $value_1", 0);
error_log("Action: $action_1", 0);

if(empty($sensor_1)) {
  echo '{"status":"You have to enter a sensor!"}';
  error_log("Sensor_1 was not described.", 0);
  exit;
}
elseif (empty($sensor_2)) {
  // Means only sensor 1 exists
  if(validateRule($op_1, $value_1, $action_1)) {
    error_log("Sensor_1 was validated and will now be executed.", 0);
    executeRule($sensor_1, $op_1, $value_1, $action_1);
  }
  else {
    echo '{"status":"You need to specify the operator, value and action!"}';
    error_log("Sensor_1 was described, but could not be validated.", 0);
    exit;
  }
}
else {
  // We have both sensors described
  if(validateRule($op_1, $value_1, $action_1) && validateRule($op_2, $value_2, $action_2)) {
    // HOW DO WE USE THIS ON TWO SENSORS?
    error_log("Sensor_1 and sensor_2 was validated and will now be executed.", 0);
    executeRule($sensor_1, $op_1, $value_1, $action_1);
  }
  else {
    echo '{"status":"You need to specify the operator, value and action!"}';
    error_log("Sensor_1 and Sensor_2 was described, but could not be validated.", 0);
    exit;
  }
}

function validateRule($op, $value, $action) {
  if(empty($op) || empty($value) || empty($action)) {
    return False;
  }
  return True;
}

function executeRule($sensor, $op, $value, $action) {
  // if($op == "LESS") {
  //   $q = "SELECT * FROM $sensor WHERE temperature < $value ORDER BY time DESC LIMIT 1";
  // }
  // elseif ($op == "GREATER") {
  //   $q = "SELECT * FROM $sensor WHERE temperature > $value ORDER BY time DESC LIMIT 1";
  // }
  // elseif ($op == "EQUAL") {
  //   $q = "SELECT * FROM $sensor WHERE temperature = $value ORDER BY time DESC LIMIT 1";
  // }
  $q = "SELECT * FROM $sensor ORDER BY time DESC LIMIT 1";
  error_log("Query: $q", 0);

  $servername_influx = DB_HOST_INFLUX;
  $serverport_influx = DB_PORT_INFLUX;
  $dbname_influx = DB_NAME_INFLUX;
  $dbuser_influx = DB_USER_INFLUX;
  $dbpass_influx = DB_PASSWORD_INFLUX;
  error_log("$dbuser_influx, $dbpass_influx, $servername_influx, $serverport_influx, $dbname_influx", 0);
  $database = InfluxDB\Client::fromDSN(sprintf('influxdb://%s:%s@%s:%s/%s',$dbuser_influx, $dbpass_influx, $servername_influx, $serverport_influx, $dbname_influx));
  $returnFromIfx = $database->query($q);
  $points = $returnFromIfx->getPoints();
  $points_values = array_values($points);
  error_log(print_r($points_values[1], TRUE), 0);

  if(empty($returnFromIfx)) {
    // This is actually not bad and an error, just means that we should not execute anything
    echo '{"status":"Result from Influx was empty!"}';
    error_log("Result from Influx was empty", 0);
    exit;
  }
  else {
    // We should send an MQTT message here I think.
    echo $points_values[1];
    error_log("We did it! Action executed.", 0);
    exit;
  }
  return $returnFromIfx;
}

/*
require_once "../app/vendor/autoload.php";
require_once "../app/vendor/ruler/vendor/autoload.php";
require_once "../app/vendor/ruler/src/Ruler/RuleBuilder.php";


$rb = new RuleBuilder;
$rule = $rb->create(
  $rb['temperature']->greaterThanOrEqualTo($rb['actualTemperature']),

  function() {
    error_log("RULE_EXECUTED!!!!!", 0);
  }
);

$context = new Context(array(
  'temperature' => 25,
  'actualTemperature' => function() {
      return 20;
    },
));

$rule->execute($context);
*/
 ?>
