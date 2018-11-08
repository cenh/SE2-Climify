<div class="single-view view-rules">
    <div class="view-data-top">
	<span>
		<h3>Graphs</h3>
		<p>See statistics from one or more of the chosen locations.</p>
	</span>
        <span>
		<span>
			<?php if ( $currentUserRole == 1 || $currentUserRole == 15 ) { ?>
                <select class="list-schools-other-users" name="option" id="graphMainSelect">
					<option value="" selected>Choose Institution</option>
                    <!-- Content goes here -->
				</select>
            <?php } ?>

            <select class="chart-select-map" >
				<!-- Content goes here -->
				<option value="stand" selected>Choose Floor plan</option>
			</select>

			<button id="btn-toggle-chart-info" class="link"></button>
		</span>
		<span>
			<select class="chart-select-location" id="selectLocation">
			<option value="locStand" selected>Choose Location</option>
                <!-- Content goes here -->
			</select>



		</span>
	</span>
    </div>
</div>
<hr>