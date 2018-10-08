
<div class="single-view view-roomX">

    <div class="roomX-top">
        <span>
            <h3>Release 1</h3>
            <p>View and set temperature for sensor on this page</p>
        </span>
    </div>
  <!-- TODO: view data -->
  <div class="view-roomx-data-box">
      <canvas id="myChart" width="400" height="400"></canvas>
  </div>
    <hr>
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
    var ctx = document.getElementById("myChart");
    var myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
            datasets: [{
                label: '# of Votes',
                data: [12, 19, 3, 5, 2, 3],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)'
                ],
                borderColor: [
                    'rgba(255,99,132,1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero:true
                    }
                }]
            }
        }
    });
</script>
