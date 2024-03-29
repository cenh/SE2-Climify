<?php
//@author ciok
//get all channels for a given thing

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
LEFT JOIN Links ON Links.ChannelUID = Channels.UID
WHERE ThingsChannels.ThingUID='$thingID'
AND Channels.UID = ThingsChannels.ChannelUID";

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
