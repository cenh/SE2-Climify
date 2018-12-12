<?php

//************************************************
//	Login
//************************************************
session_start();
require_once "../meta.php";

// Validate API key
$apiPassword = SIGNIN_TOKEN;
$phase_api_key = clean($_GET['fAY2YfpdKvR']);

if ($apiPassword !== $phase_api_key) {
    echo '{"status":"error", "info":"token"}';
    exit;
}

$servername = DB_HOST;
$username = DB_USER;
$password = DB_PASSWORD;
$databasename = DB_NAME;

// Store user input (username, password) in variables
$phaseUsername = clean(strtolower($_GET['username']));
$phasePassword = clean($_GET['password']);
$phasePasswordRex=(string)preg_replace("/ /", "+", $phasePassword);
$phasePasswordDecrypt = decrypt($phasePasswordRex, ENCRYPTION_KEY);
$currentTime = date("d-m-Y, H:i");
$phaseUsername = clean($phaseUsername);
$phasePasswordDecrypt = clean($phasePasswordDecrypt);

$conn = new mysqli($servername, $username, $password, $databasename);
if ($conn->connect_error) {
    die("Connection error: " . $conn->connect_error);
    exit;
}

if ($stmt = $conn->prepare("SELECT * FROM Person WHERE UserName = ?")) {
    $stmt->bind_param("s", $phaseUsername);
    $stmt->execute();
    $stmt->bind_result($UserID, $phaseUsername, $FirstName, $LastName, $Email, $RoleName, $UserPassword, $Blocked, $LastLogin);
    $stmt->fetch();
    $stmt->close();
}
error_log("UserID1:".$UserID, 0);
if ($RoleName == 1 || $RoleName == 15) {
    if ($stmt = $conn->prepare("SELECT * FROM ProjectManager WHERE UserID = ?")) {
        $stmt->bind_param("i", $UserID);
        $stmt->execute();
        $stmt->bind_result($UserID, $MunID);
        $stmt->fetch();
        $stmt->close();
    }
    error_log("UserID2:".$UserID, 0);
} else {
    if ($stmt = $conn->prepare("SELECT * FROM InstUser WHERE UserID = ?")) {
        $stmt->bind_param("i", $UserID);
        $stmt->execute();
        $stmt->bind_result($UserID, $InstID);
        $stmt->fetch();
        $stmt->close();
    }
    error_log("UserID3:".$UserID, 0);
    if ($stmt = $conn->prepare("SELECT MunID, InstName FROM Institution WHERE InstID = ?")) {
        $stmt->bind_param("i", $InstID);
        $stmt->execute();
        $stmt->bind_result($MunID, $InstName);
        $stmt->fetch();
        $stmt->close();
    }
}

$q = "SELECT Permission.PermName FROM Permission INNER JOIN RolePermission ON Permission.PermID = RolePermission.PermID WHERE RolePermission.RoleID = $RoleName";
error_log("Query: $q", 0);
$result = $conn->query($q);
if($result->num_rows > 0) {
  $permissions=array();
  while($row = mysqli_fetch_assoc($result))
  {
    error_log("Pushed permission: ". $row['PermName'], 0);
    array_push($permissions, $row['PermName']);
  }
}

$sPasswordDBDecrypted = decrypt($UserPassword, ENCRYPTION_KEY);
error_log("DecryptedDB:".$sPasswordDBDecrypted, 0);
error_log("phaseDecrypted:".$phasePasswordDecrypt, 0);
if ($sPasswordDBDecrypted === $phasePasswordDecrypt) {
    error_log("Passwords are equal", 0);
    if ($Blocked == 1) {
        error_log("UserID4:".$UserID, 0);
        error_log("User is not blocked", 0);
        // Store user-info from variables in sessions
        $_SESSION['userID'] = $UserID;
        $_SESSION['userName'] = $phaseUsername;
        $_SESSION['userRole'] = $RoleName;
        $_SESSION['userFirstName'] = $FirstName;
        $_SESSION['userLastName'] = $LastName;
        $_SESSION['userEmail'] = $Email;
        $_SESSION['schoolAllowed'] = $InstID;
        $_SESSION['companyID'] = $MunID;
        $_SESSION['schoolAllowedName'] = $InstName;
        $_SESSION['permLogbook'] = $permLogBook;
        $_SESSION['permissions'] = $permissions;

        echo 	'{"status": "approve",
								"school":"'.$InstName.'"
							}';
    } else {
        echo '{"status": "error"}';
    }
} else {
    echo '{"status": "error"}';
}

$conn->close();
?>
