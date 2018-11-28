<?php
require_once "../meta.php";

$servername = DB_HOST;
$username = DB_USER;
$password = DB_PASSWORD;
$databasename = DB_NAME;

$thingID = clean($_POST[thingID]);


$conn = new mysqli($servername, $username, $password, $databasename);
if ($conn->connect_error) {
    die("Connection error: " . $conn->connect_error);
}

$query = "SELECT * FROM Channels 
INNER JOIN ThingsChannels
INNER JOIN Things
WHERE Things.UID = ThingsChannels.ThingUID";
//JOIN ThingsChannels WHERE ThingUID = $thingID";

$stmt = $conn->prepare($query);

$stmt->execute();

$result = $stmt->get_result();

$emparray = array();
while($row = mysqli_fetch_assoc($result))
{
    $emparray[] = $row;
}

$messages = json_encode( $emparray , JSON_UNESCAPED_UNICODE );
echo $messages;

$stmt->close();

$conn->close();
