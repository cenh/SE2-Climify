<?php
//@author ciok
//check if some users have this role

require_once "../admin-meta.php";
require_once "../session.php";

$phaseSessionToken = clean($_POST[sessionToken]);
$role_id = clean($_POST[role_id]);

if (!$systemAccess) {
    echo '{"status":"systemAccess error"}';
    exit;
}


if( $phaseSessionToken != $adminSessionToken ){
    echo '{"status":"phaseSessionToken error"}';
    exit;
}


$servername = DB_HOST;
$username = DB_USER;
$password = DB_PASSWORD;
$databasename = DB_NAME;
$pepper = HASH_PEPPER;

$conn = new mysqli($servername, $username, $password, $databasename);
if ($conn->connect_error) {
    die("Connection error: " . $conn->connect_error);
}

$query = "SELECT * FROM Person WHERE Person.RoleName = '$role_id'";


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

