<?php
    $response = file_get_contents('http://localhost:8086/query?u=admin&p=groupc&db=scadb&q=SELECT%20value%20FROM%20readBattery');
    $response = json_decode($response);
    echo $response
?>
<div class="single-view view-roomX">

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

