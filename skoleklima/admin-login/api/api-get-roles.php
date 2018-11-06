<?php

echo '<script>console.log("api works here!")</script>';

require_once "../admin-meta.php";
require_once "../session.php";

$phaseSessionToken = clean($_POST[sessionToken]);

if( $phaseSessionToken != $adminSessionToken ){
    echo '{"status":"error"}';
    exit;
}

if (!$systemAccess) {
    echo '{"status":"error"}';
    exit;
}

$phaseStatus=clean($_POST["status"]); //Blocked
$phaseSearch=clean($_POST["search"]);


$servername = DB_HOST;
$username = DB_USER;
$password = DB_PASSWORD;
$databasename = DB_NAME;
$pepper = HASH_PEPPER;

$conn = new mysqli($servername, $username, $password, $databasename);
if ($conn->connect_error) {
    die("Connection error: " . $conn->connect_error);
    exit;
}


$stmt = $conn->prepare("(SELECT * FROM Role);");


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

?>