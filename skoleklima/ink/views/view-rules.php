<?php

$servername = DB_HOST;
$username = DB_USER;
$password = DB_PASSWORD;
$databasename = DB_NAME;

$conn = new mysqli($servername, $username, $password, $databasename);

$loc_query = "SELECT LocationID FROM Location";

$result2 = mysqli_query($conn,$loc_query);


$options = "";

while($row2 = mysqli_fetch_array($result2))
{
    $options = $options."<option>$row2[1]</option>";
}

?>
?>

<!-- show rules -->
<div class="single-view view-rules">
    <div class="view-data-top">
	<span>
		<h3>Rules</h3>
		<p>View rules and toggle them (on/off) for a chosen location</p>
        <select style="min-width: 80px;">
            <?php echo $options;?>
        </select>
    </div>
</div>