<?php
/*
 *	Author: Christian Hansen & Kacper Zyla
 */
require_once "../meta.php";

if( $currentUserID == ""){
    echo '{"status":"No UserID"}';
    exit;
}

$servername = DB_HOST;
$username = DB_USER;
$password = DB_PASSWORD;
$databasename = DB_NAME;

$conn = new mysqli($servername, $username, $password, $databasename);
if($conn->connect_error) {
    echo '{"status":"Connection died"}';
    die("Connection error: " . $conn->connect_error);
    exit;
}

$q = "SELECT * FROM Actions ";
$result = mysqli_query($conn, $q) or die("Error in Selecting " . mysqli_error($conn));

$emparray = array();
while($row = mysqli_fetch_assoc($result))
{
    $temparray = [];
    array_push($temparray[ActionID] = $row["id"]);
    array_push($temparray[Action] = $row["Description"]);

    array_push($emparray, $temparray);
}
echo(json_encode($emparray));

$conn->close();
?>
