<div class="single-view view-roomX">

    <div class="roomX-top">
        <span>
            <h3>Release 1</h3>
            <p>On this page you can view details about room X and set a temperature for the thermostat.</p>
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
        <!--        <div class="temperature-graph-box">-->
        <!--            <canvas id="myChart"></canvas>-->
        <!--        </div>-->
        <div class="information-box">
            <h3>Information Panel</h3>
            <div class="view-roomX-data-box" style="display: flex; flex-direction: row; line-height: 30px; margin: 10px 0;">
                <span>
                    <i class="menu-link-ico nav-icon fa fa-thermometer-half" style="margin:0 10px 0 0;" aria-hidden="true"></i>
                    <h4 style="float: right;" id="temp_cur"></h4>
                </span>
            </div>
            <div class="view-roomX-data-box" style="display: flex; flex-direction: row; line-height: 30px; margin: 10px 0;">
                <span>
                    <i class="menu-link-ico nav-icon fa fa-tint" style="margin:0 10px 0 0;"></i>
                    <h4 style="float: right;" id="humi_cur"></h4>
                </span>
            </div>
            <!--            <div class="view-roomX-data-box">-->
            <!--                <i class="menu-link-ico nav-icon fa fa-volume-down" aria-hidden="true"></i>-->
            <!--            </div>-->
            <!--            <div class="view-roomX-data-box">-->
            <!--                <span>-->
            <!--                    <i class="menu-link-ico nav-icon fa fa-battery-half"></i>-->
            <!--                    <h4 style="float:right; padding-top:10px; padding-right:70%" id="bat_lvl"></h4>-->
            <!--                </span>-->
            <!--            </div>-->
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

        <div class="view-roomX-data-box" style="border:1px solid #92b2c7; padding:15px; display:flex; justify-content: space-between;">
            <div style="">
           <span>
                <form id="temp" class="my-center">
                    Set temperature:
                    <input type="number" name="value">
                </form>
           </span>
            </div>
            <div class="view-roomX-data-box" style="line-height: 30px;">
                             <span>
                                <i class="menu-link-ico nav-icon fa fa-battery-half" style="margin:0; float: right;"></i>
                                <h4 style="float: right; padding-right: 5px;" id="bat_lvl"></h4>
                            </span>
            </div>
        </div>
        <div class="button-float" style="float:right; padding:10px">
            <button id="my_button">
                Send request
            </button>
        </div>
    </div>

</div>
<script>
    setInterval(function(){
        loadRoomDetails()
    }, 10000) //every 10 sec
</script>
