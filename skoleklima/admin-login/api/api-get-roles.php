<?php


require_once "../admin-meta.php";
require_once "../session.php";


$servername = DB_HOST;
$username = DB_USER;
$password = DB_PASSWORD;
$databasename = DB_NAME;


$conn = new mysqli($servername, $username, $password, $databasename);
if ($conn->connect_error) {
    die("Connection error: " . $conn->connect_error);
}

$query = "SELECT * FROM Role;";
$stmt = $mysqli->stmt_init();

if(!$stmt->prepare($query))
{
    print "Failed to prepare statement\n";
}
else {
    $stmt->execute();
    $result = $stmt->get_result();
    $emparray = array();
    while($row = mysqli_fetch_assoc($result))
    {
        $emparray[] = $row;
    }

    $messages = json_encode( $emparray , JSON_UNESCAPED_UNICODE );
    echo $messages;
}


$stmt->close();
$conn->close();

?>
