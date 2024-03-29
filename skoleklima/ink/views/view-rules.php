<!-- Author: Christian P -->

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
                            <div id="form-sensor" class="row">
                                <div class="col-sm-6">
                                    <b>If</b>
                                </div>
                                <div class="col-sm-6">
                                    <select id="sensorSelect">
                                    </select>
                                </div>
                            </div>
                            <div id="form-op" class="row">
                                <div class="col-sm-6">
                                    <b>is</b>
                                </div>
                                <div class="col-sm-6">
                                    <select id="opSelect">
                                    </select>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <b>to/than</b>
                                </div>
                                <div class="col-sm-6">
                                <input style="padding-right: 40px;text-align: right; width: 127px;" type="number" id="selectValue" name="value"><span id="unit" style="margin-left: -40px;"></span>
                                </div>
                            </div>
                            <div class="row">
                            <div class="col-sm-6">
                                <b>then</b>
                                <select id="actuatorSelect">
                                    <option value="" disabled selected>Select an actuator</option>
                                </select>
                            </div>
                            <div class="col-sm-6" id ="form-action">
                                <div id="onActionSetTemp"
                                    <b> to </b><input type="number" name="setTemp" min="4" max="35">
                                </div>
                            </div>

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <button type="button" id="submitRule" class="btn btn-primary">Save changes</button>
                            </div>


                            </div> <!-- modal body row -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
