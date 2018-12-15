<?php


require_once "../admin-meta.php";
require_once "../session.php";

$phaseSessionToken = clean($_POST[sessionToken]);
$roleID = clean($_POST[roleID]);

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

//$query = "SELECT RoleName, PermDescription FROM Role r JOIN RolePermission rp ON r.RoleID = rp.RoleID
//        JOIN Permission p ON p.PermID = rp.PermID";
$query = "SELECT Permission.PermID, Permission.PermDescription, RolePermission.RoleID FROM 
Permission LEFT JOIN RolePermission ON RolePermission.PermID = Permission.PermID AND RolePermission.RoleID = $roleID";

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
