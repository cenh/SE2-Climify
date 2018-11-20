<?php
//************************************************
//	Rule execution
//************************************************

require_once "../meta-influx.php";

$sensor = $_POST['SensorID'];
$op = $_POST['Operator'];
$value = $_POST['Value'];
$action = $_POST['Action'];

error_log("Sensor: $sensor", 0);
error_log("Op: $op", 0);
error_log("Value: $value", 0);
error_log("Action: $action", 0);

if(empty($sensor)) {
  echo '{"status":"You have to enter a sensor!"}';
  error_log("Sensor was not described.", 0);
  exit;
}
if(validateRule($op, $value, $action)) {
  error_log("Sensor was validated and will now be executed.", 0);
  executeRule($sensor, $op, $value, $action);
}

function validateRule($op, $value, $action) {
  if(empty($op) || empty($value) || empty($action)) {
    return False;
  }
  return True;
}

function executeRule($sensor, $op, $value, $action) {
  // if($op == "LESS") {
  //   $q = "SELECT * FROM $sensor WHERE temperature < $value ORDER BY time DESC LIMIT ";
  // }
  // elseif ($op == "GREATER") {
  //   $q = "SELECT * FROM $sensor WHERE temperature > $value ORDER BY time DESC LIMIT ";
  // }
  // elseif ($op == "EQUAL") {
  //   $q = "SELECT * FROM $sensor WHERE temperature = $value ORDER BY time DESC LIMIT ";
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
  $sensor_value = array_values($points[0]);

  if(empty($returnFromIfx)) {
    // This is actually not bad and an error, just means that we should not execute anything
    echo '{"status":"Result from Influx was empty!"}';
    error_log("Result from Influx was empty", 0);
    exit;
  }
  else {
    // We should send an MQTT message here I think.
    $result = "Rule requirements haven't been met";
    if($op == "LESS" && $sensor_value < $value) {
      $result = $action;
    }
    elseif ($op == "GREATER" && $sensor_value > $value) {
      $result = $action;
    }
    elseif ($op == "EQUAL" && $sensor_value == $value) {
      $result = $action;
    }
    error_log("Result: " . $result, 0);
    echo "{\"Result\": \"$result\"}";
    exit;
  }
  return $returnFromIfx;
}
$conn->close();
 ?>
