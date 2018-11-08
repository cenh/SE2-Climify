<?php
    $conn = mysqli_connect($DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
    $loc_query = "SELECT LocationID FROM Location";

    $result1 = mysqli_query($conn,$loc_query);
?>

<!-- show rules -->
<div class="single-view view-rules">
    <div class="view-data-top">
	<span>
		<h3>Rules</h3>
		<p>View rules and toggle them (on/off) for a chosen location</p>
        <select>
            <?php while ($row1 = mysqli_fetch_array($result1)):;?>
            <option value="<?php echo $row[1];?>">Select your value</option>
            <?php endwhile;?>
        </select>
    </div>
</div>
