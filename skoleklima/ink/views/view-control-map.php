<!-- System data map -->

<?php

$query = "SELECT LocationID, LocationName FROM Location";


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
    $options =$options."<option value=\"$row[0]\">$row[1]</option>";
}
?>



<div class="single-view view-control-map">
    <div class="view-control-top">
	<span>
		<h3>Control Map</h3>
		<p>An overview of installed devices</p>
	</span>

    </div>
    <hr>

    <div style="text-align: center">
        <h2>Your Sensors and Actuators</h2>
    </div>

    <div style="height: auto">
        <select id="select_room" onchange="refreshTableSensorsAndActuators(value)">
            <option value="" disabled selected>Choose Your Room</option>
            <?php echo $options; ?>
        </select>
    </div>

    <div style="overflow: auto">

        <div style="width: 50%; float:left;">
            <div style="width: 100%; height: auto; border: 1px solid #dddddd;
    text-align: left;
    padding: 8px; float:left;">
                <table id="table_id1" class="display" style="width: 100%">
                    <thead>
                    <tr>
                        <th></th>
                        <th>Things</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>

        <style>
            td.details-control {
                background: url('img/right.png') no-repeat center center;
                cursor: pointer;
            }

            tr.shown td.details-control {
                background: url('img/down.png') no-repeat center center;
            }
        </style>

    </div>

    <hr>

    <div style="text-align: center; padding-top: 30px">
        <h2>Manage Devices</h2>
    </div>

    <div style="height: auto">
        <select id="select_room_devices" onchange="refreshTableDevices(value)">
            <option value="" disabled selected>Choose Your Room</option>
        </select>
        <button id="listen_button" onclick="listen()" disabled>Discover new devices</button>
    </div>

    <div style="width: 100%; height: auto; border: 1px solid #dddddd;
    text-align: left;
    padding: 8px; float:left;">
        <table id="table_id3" class="display" style="width: 100%">
            <thead>
            <tr>
                <th></th>
                <th>Devices</th>
            </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
        <style>
            td.delete {
                background-color: #b70404;
                cursor: pointer;
            }
        </style>
    </div>

</div>
