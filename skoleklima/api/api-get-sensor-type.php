<?php
require_once "../meta.php";

$servername = DB_HOST;
$username = DB_USER;
$password = DB_PASSWORD;
$databasename = DB_NAME;
$SensorName = $_GET['SensorName'];

$roomID = clean($_POST[roomID]);


$conn = new mysqli($servername, $username, $password, $databasename);
if ($conn->connect_error) {
    die("Connection error: " . $conn->connect_error);
}

$query = "SELECT items.*, Channels.ItemType FROM Items as items
INNER JOIN RaspberryPis as rp
INNER JOIN Things as t
INNER JOIN Channels
INNER JOIN ThingsChannels as tc
INNER JOIN Links as links
WHERE rp.LocationID = $roomID
AND t.RaspberryPiUID = rp.UID
AND tc.ThingUID = t.UID
AND links.ChannelUID = tc.ChannelUID
AND Channels.UID = links.ChannelUID
AND items.Name = links.ItemName AND Name = $SensorName";

$stmt = $conn->prepare($query);

$stmt->execute();

$result = $stmt->get_result();


$messages = json_encode( $result , JSON_UNESCAPED_UNICODE );
echo $messages;

$stmt->close();

$conn->close();

?>
