<?php

$query = "SELECT LocationID FROM Location";


$servername = DB_HOST;
$username = DB_USER;
$password = DB_PASSWORD;
$databasename = DB_NAME;
$pepper = HASH_PEPPER;

$conn = new mysqli($servername, $username, $password, $databasename);
if ($conn->connect_error) {
    die("Connection error: " . $conn->connect_error);
}

//RolePermission;
//Permission;

$stmt = $conn->prepare($query);

$stmt->execute();

$result = $stmt->get_result();

$emparray = array();
$options = "";
while($row = mysqli_fetch_array($result))
{
    $options =$options."<option>$row[0]</option>";
}
?>


<!-- show rules -->
<div class="single-view view-rules">
    <div class="view-data-top">
	<span>
		<h3>Rules</h3>
		<p>View rules and toggle them (on/off) for a chosen location</p>
        <select id="sel_location" style="min-width: 80px;">
            <?php echo $options;?>
        </select>
        <button id="fetch-sensors-for-loc" class="link">Search rules</button>
    </div>
    <hr>

    <button style="display:none;" class="collapsible">
        <span class="ui-button-text"></span>
    </button>
    <div class="accordion" id="accordionExample">
        <div class="container-fluid">
        <div class="row">
            <div class="col-md-8">
                <p id="rule-count"></p>
            </div>
            <div class="col-md-4">
                <button class="mybtn" style="float-right">
                    <i class="fa fa-plus-circle"></i>
                </button>
            </div>
    </div>



</div>