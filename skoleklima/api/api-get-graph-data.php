
<?php
//ini_set('display_errors', 1);
//ini_set('display_startup_errors', 1);
//error_reporting(E_ALL);

require_once "../meta-influx.php";

$servername_influx = DB_HOST_INFLUX;
$serverport_influx = DB_PORT_INFLUX;
$dbname_influx = DB_NAME_INFLUX;
$dbuser_influx = DB_USER_INFLUX;
$dbpass_influx = DB_PASSWORD_INFLUX;

$database = InfluxDB\Client::fromDSN(sprintf('influxdb://%s:%s@%s:%s/%s',$dbuser_influx, $dbpass_influx, $servername_influx, $serverport_influx, $dbname_influx));

require_once "../meta.php";

if( $currentUserID == ""){
      echo '{"status":"no user"}';
    exit;
}

// Validate API key
$apiPassword = API_PASSWORD;
$phase_api_key = clean($_POST['fAY2YfpdKvR']);
$LocationID = intval($_POST['LocationID']);
$from = clean($_POST['from']);
$to = clean($_POST['to']);

$servername = DB_HOST;
$username = DB_USER;
$password = DB_PASSWORD;
$databasename = DB_NAME;

if( $apiPassword !== $phase_api_key){
  	echo '{"status":"no api key"}';
	exit;
}

$conn = new mysqli($servername,$username, $password, $databasename);

if($conn->connect_error){
    die("Connection error:" . $conn->connect_error);
    exit;
}

$sql = " SELECT * FROM Location NATURAL JOIN Map NATURAL JOIN Institution WHERE LocationID = '$LocationID' ";

$result = $conn->query($sql);
if(!$result) {
    die("Error: " . $conn->error);
}

$row = $result->fetch_assoc();
$InstIDForLocation = $row["InstID"];
$MunIDForLocation = $row["MunID"];

if ($currentUserRole == 1){
    if ($MunIDForLocation!=$currentUserCompanyID){
        echo '{"status":"no company"}';
        exit;
    }

}
else{
    if ($InstIDForLocation != $InstID){
        echo '{"status":"no instituion"}';
        exit;
    }
}

$q = "SELECT items.* FROM Items as items
      INNER JOIN RaspberryPis as rp
      INNER JOIN Things as t
      INNER JOIN ThingsChannels as tc
      INNER JOIN Links as links
      WHERE rp.LocationID = $LocationID
      AND t.RaspberryPiUID = rp.UID
      AND tc.ThingUID = t.UID
      AND links.ChannelUID = tc.ChannelUID
      AND items.Name = links.ItemName
      AND items.ReadOnly = 1";
error_log($q, 0);
$result = $conn->query($q);

$row_cnt = $result->num_rows;

if ($row_cnt==0) {
   echo '{"status":"nodata"}';
    exit;
}

$sensors=[];
$multi = 10**9;
$from = strtotime($from) * $multi;
$to = strtotime($to) * $multi;

while ($currentSensorIDArray = mysqli_fetch_assoc($result)) {
    //LAST() -> newest entry
  $q = 'SELECT * FROM "' . $currentSensorIDArray["Name"] . '"' . 'WHERE time >=' . $from . ' AND time <=' . $to . '';
  error_log($q, 0);
  $currentSensorRow = $database->query($q);
  $currentPoints = $currentSensorRow ->getPoints();
  array_push($sensors,$currentPoints);
}
$sensorData = json_encode( $sensors , JSON_UNESCAPED_UNICODE );

if (count($sensors[0][0])==0){
    echo '{"status":"no sensors retrived"}';
    exit;
}

else{
    echo ($sensorData);
}

$conn->close();
//$database->close();
?>
