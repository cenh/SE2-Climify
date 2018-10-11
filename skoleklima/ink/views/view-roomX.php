

<div class="single-view view-roomX">
    <?php
    $returnFromIfx = file_get_contents("http://localhost:8086/query?u=admin&p=groupc&db=scadb&q=SELECT%20value%20FROM%20readBattery");
    $returnFromIfx = json_encode($returnFromIfx, true);
    $temperature = file_get_contents(   "http://localhost:8086/query?u=admin&p=groupc&db=scadb&q=SELECT%20value%20FROM%20readOutdoorTemperature");
    $temperature = json_encode($temperature, true)

    $humidity = file_get_contents("http://localhost:8086/query?u=admin&p=groupc&db=scadb&q=SELECT%20value%20FROM%20readHumidity")

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
                <span>
                    <i class="menu-link-ico nav-icon fa fa-thermometer-half" aria-hidden="true"></i>
                    <h4 style="float:left; padding-top:10px; padding-right:60px" id="temp_cur"></h4>
                </span>
            </div>
            <div class="view-roomX-data-box">
                <span>
                    <i class="menu-link-ico nav-icon fas fa-tint"></i>
                    <h4 style="float:left; padding-top:10px; padding-right:60px" id="humi_cur"></h4>
                </span>
            </div>
            <div class="view-roomX-data-box">
                <i class="menu-link-ico nav-icon fa fa-volume-down" aria-hidden="true"></i>
            </div>
            <div class="view-roomX-data-box">
                <span>
                    <i class="menu-link-ico nav-icon fa fa-battery-half"></i>
                    <h4 style="float:right; padding-top:10px; padding-right:60%" id="bat_lvl"></h4>
                </span>
            </div>
        </div>
    </div>


        <div class="view-roomX-data-box">
            <div class="temperature">
                <!-- TODO: -->
                <span>
                    <h4>
                        The current set temperature is: <label id="current_set_temp"></label>
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
    var json_batt = JSON.parse(obj);
    // get the latest battery reading
    var latest = json_batt.results[0].series[0].values.slice(-1)[0];
    document.getElementById("bat_lvl").innerHTML = latest[1].substring(0,6)+ "%";
    var t_obj = <?php echo $temperature; ?>;
    var json_t = JSON.parse(t_obj);
    var t_latest = json_t.results[0].series[0].values.slice(-1)[0];
    document.getElementById("temp_cur").innerHTML = t_latest[1];

    var h_obj = <?pho echo $humidity; ?>;
    var json_h = JSON.parse(h_obj);
    var h_latest = json_h.results[0].series[0].values.slice(-1)[0];
    document.getElementById("humi_cur").innerHTML = h_latest[1];

</script>
