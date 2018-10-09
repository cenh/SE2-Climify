
<div class="single-view view-roomX">

    <div class="roomX-top">
        <span>
            <h3>Release 1</h3>
            <p>View and set temperature for sensor on this page</p>
        </span>
    </div>
    <hr>
    <!-- TODO: view data -->
  <div class="view-roomx-data-box" style="width:600px; height:600px;">
      <canvas id="myChart" style="width:100%"></canvas>
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

