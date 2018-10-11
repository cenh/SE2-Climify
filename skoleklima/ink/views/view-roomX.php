

<div class="single-view view-roomX">
    <?php
    $returnFromIfx = file_get_contents("http://localhost:8086/query?u=admin&p=groupc&db=scadb&q=SELECT%20value%20FROM%20readBattery");
    $returnFromIfx = json_encode($returnFromIfx, true);
    $temperature = file_get_contents(   "http://localhost:8086/query?u=admin&p=groupc&db=scadb&q=SELECT%20value%20FROM%20readOutdoorTemperature");
    $temperature = json_encode($temperature, true)
    ?>
    <div class="roomX-top">
        <span>
            <h3>Release 1</h3>
            <p>View and set temperature for sensor on this page</p>
        </span>
    </div>
    <hr>

<!--    <div class="button-float">-->
<!--        <button id="button2">-->
<!--            REFRESHDATA-->
<!--        </button>-->
<!--    </div>-->

    <!-- TODO: view data -->
    <div class="data-display-box">
        <div class="temperature-graph-box">
            <canvas id="myChart"></canvas>
        </div>
        <div class="information-box">
            <h3>Information Panel</h3>
            <div class="view-roomX-data-box">
                <i class="menu-link-ico nav-icon fa fa-thermometer-half" aria-hidden="true"></i>
            </div>
            <div class="view-roomX-data-box">
                <i class="menu-link-ico nav-icon fa fa-cloud" aria-hidden="true"></i>
            </div>
            <div class="view-roomX-data-box">
                <i class="menu-link-ico nav-icon fa fa-volume-down" aria-hidden="true"></i>
            </div>
            <div class="view-roomX-data-box">
                <span>
                    <i class="menu-link-ico nav-icon fa fa-battery-half"></i>
                    <p id="bat_lvl"></p>
                </span>
            </div>
        </div>
    </div>


        <div class="view-roomX-data-box">
            <div class="temperature">
                <!-- TODO: -->
                <span>
                    <h4>
                        The current temperature is: <label id="current_set_temp"></label>
                    </h4>
                </span>
            </div>
        <div class="view-roomX-data-box" style="border:1px solid #92b2c7; padding:15px">
           <span>
                <form id="temp" class="my-center">
                    Set temperature:
                    <input type="number" name="value">
                </form>
           </span>
        </div>
        <div class="button-float" style="float:right; padding:10px">
            <button id="my_button">
                Send request
            </button>
        </div>
    </div>

</div>
<script>
    var obj = <?php echo $returnFromIfx; ?>;
    var json_batt = JSON.parse(obj)
    // get the latest battery reading
    var latest = json_batt.results[0].series[0].values.slice(-1)[0];
    document.getElementById("bat_lvl").innerHTML = latest[1]+ "%";
</script>
