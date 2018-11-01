<?php

//************************************************
//	Admin login
//************************************************

require_once "../admin-meta.php";
require_once "../session.php";

$phaseSessionToken = clean($_POST[sessionToken]);

if( $phaseSessionToken != $adminSessionToken ){
    echo '{"status":"errortoken"}';
    exit;
}

$phaseUsername = clean(strtolower($_POST['username']));
$phasePassword = clean($_POST['password']);
$phasePasswordRex=(string)preg_replace("/ /", "+", $phasePassword);
$phasePasswordDecrypt = decrypt($phasePasswordRex, ENCRYPTION_KEY);
$currentTime = date("d-m-Y, H:i");
$phaseUsername = clean($phaseUsername);
$phasePasswordDecrypt = clean($phasePasswordDecrypt);

$myResponse = "error";

if ( $phaseUsername == "" ) {
    echo '{"status":"error"}';
    exit;
}

if ( $phasePassword == "" ) {
    echo '{"status":"error"}';
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
    exit;
}

error_log("Test" ,0);

$stmt = $conn->prepare("SELECT UserID,UserPassword,Blocked FROM DTUManager WHERE UserName = ? LIMIT 1");

$stmt->bind_param("s", $phaseUsername);

if (!$stmt->execute()) {
    error_log("Test1" ,0);
    echo '{"status":"errorexc"}';
    $conn->close();
    exit;
}

$result = $stmt->get_result();
error_log("Test2" ,0);

if ($result->num_rows==1){
    error_log("Test3" ,0);
    while($row = $result->fetch_assoc()) {
        $DBUserID = $row["UserID"];
        $DBUserPass = $row["UserPassword"];
        $DBUserUserBlocked = $row["Blocked"];
    }
}
error_log("Test4" ,0);

$stmt->close();

$sPasswordDBDecrypted = decrypt($DBUserPass, ENCRYPTION_KEY);
error_log("DBUserName: " . $DBUserID, 0);
error_log("DBUserPass: " . $DBUserPass, 0);
error_log("Pass: " . $phasePasswordDecrypt, 0);
error_log("DBPass: " . $sPasswordDBDecrypted, 0);
if ($sPasswordDBDecrypted === $phasePasswordDecrypt) {
    if ($DBUserUserBlocked == 1 ) {
        session_start();
        $_SESSION['adminAccess'] = true;
        $_SESSION['session-time-admin'] = time();

        $stmt = $conn->prepare("UPDATE Person SET LastLogin = ? WHERE UserID = ?");

        $stmt->bind_param("si", date("Y-m-d, H:i"), $DBUserID);

        if ($stmt->execute()) {
            session_start();
            $_SESSION['adminAccess'] = true;
            $_SESSION['session-time-admin'] = time();
            echo '{"status":"ok"}';
            $stmt->close();
            $conn->close();
            exit;
        } else {
            echo '{"status":"error"}';
            $stmt->close();
            $conn->close();
            exit;
        }


    } else {
        echo '{"status":"errornotverif"}';
        $conn->close();
        exit;
    }
} else {
    echo '{"status":"errornorow"}';
    $conn->close();
    exit;
};

?>
