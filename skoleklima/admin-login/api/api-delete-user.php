<?php
/**
 * User: christianp
 * Date: 2018-12-20
 * Time: 13:07
 */


//************************************************
//	Delete user
//************************************************

require_once "../meta.php";

if( $currentUserID == ""){
    echo '{"status":"error1"}';
    exit;
}

// Validate API key
$apiPassword = API_PASSWORD;
$phase_api_key = clean($_GET['fAY2YfpdKvR']);

if( $apiPassword !== $phase_api_key){
    echo '{"status":"error2"}';
    exit;
}

$servername = DB_HOST;
$username = DB_USER;
$password = DB_PASSWORD;
$databasename = DB_NAME;

$userID = clean($_POST['id']);



//echo "tryingToDeleteUserID " .$tryingToDeleteUserID;


//If made it through validation -> delete user

$sql = "DELETE FROM InstUser WHERE UserID = '$userID' ";

$conn->query($sql);

$sql = "DELETE FROM Person WHERE UserID = '$userID' ";

if ($conn->query($sql)){

echo '{"status": "ok"}';

}

$sql = "DELETE FROM ProjectManager WHERE UserID= '$userID'";

$conn->query($sql);




$conn->close();

?>
