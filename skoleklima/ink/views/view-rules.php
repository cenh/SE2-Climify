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

    </div>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-8">
                <p id="rule-count"></p>
            </div>
            <div class="col-md-4">
                <button class="link" style="float: right" data-toggle="modal" data-target="#modalCreateRule" id="modalRule">
                    Create new rule
                </button>
            </div>
            <!-- MODAL -->
            <div class="modal fade" id="modalCreateRule" tabindex="-1" role="dialog" aria-labelledby="modalCreateRuleTitle" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalCreateRuleTitle">Add a rule</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div id="form-sensor">
                                <b>If</b>
                                <select id="sensorSelect">
                                </select>
                            </div>
                            <div id="form-op">
                                <b>is</b>
                                <select id="opSelect">
                                </select>
                            </div>
                            <div id="form-value">
                                <b>to/than</b>
                                <input type="number" id="selectValue" name="value">
                            </div>
                            <div class="row">
                            <div class="col">
                            <div id="form-action">
                                <b>then</b>
                                <select id="actionSelect">
                                    <option value="" disabled selected>Select an action</option>
                                </select>
                            </div>
                            </div>
                            <div class="col" id ="selectValue">

                            </div>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="button" id="submitRule" class="btn btn-primary">Save changes</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>