<?php
require_once "../meta.php";

$servername = DB_HOST;
$username = DB_USER;
$password = DB_PASSWORD;
$databasename = DB_NAME;

$roomID = clean($_POST[roomID]);


$conn = new mysqli($servername, $username, $password, $databasename);
if ($conn->connect_error) {
    die("Connection error: " . $conn->connect_error);
}

$query = "SELECT items.*, ItemType FROM Items as items
INNER JOIN RaspberryPis as rp
INNER JOIN Things as t
INNER JOIN ThingsChannels as tc
INNER JOIN Links as links
WHERE rp.LocationID = $roomID
AND t.RaspberryPiUID = rp.UID
AND tc.ThingUID = t.UID
AND links.ChannelUID = tc.ChannelUID
AND items.Name = links.ItemName";



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
