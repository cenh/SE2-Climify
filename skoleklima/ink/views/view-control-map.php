<!-- System data map -->
<div class="single-view view-control-map">
    <div class="view-control-top">
        <span>
            <h3>Manage Devices</h3>
            <p>Configuration of devices</p>
        </span>

    </div>
    <hr>

    <div style="text-align: center; padding: 30px">
        <h2>Manage Items</h2>
    </div>

    <!--    room dropdown-->
    <select id="select_room_manage_devices" onchange="refreshDevicesDropdown(value)">
        <option value="" selected>Choose Your Room</option>
    </select>

    <!--things dropdown-->
    <select id="select_thing_manage_devices" onchange="refreshTableChannel(value)">
        <option value="" disabled selected>Choose Your Device</option>
    </select>

    <!--    channel table-->
    <div style="overflow: auto">
        <div style="width: 100%; height: auto; border: 1px solid #dddddd;
        text-align: left;
        padding: 8px; float:left;">
            <table id="table_channels" class="display" style="width: 100%">
                <thead>
                <tr>
                    <th>Channels</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>
    </div>


    <div style="text-align: center; padding: 30px">
        <h2>Manage Things</h2>
    </div>

    <div style="height: auto">
        <select id="select_room_devices" onchange="refreshTableDevices(value)">
            <option value="" disabled selected>Choose Your Room</option>
        </select>
        <button id="listen_button" onclick="listen()" disabled>Discover new devices</button>
        <button id="refresh_things_table_button" onclick="refreshThingsTableWithButton()">Refresh</button>
    </div>

    <div style="width: 100%; height: auto; border: 1px solid #dddddd;
    text-align: left;
    padding: 8px; float:left;">
        <table id="table_id3" class="display" style="width: 100%">
            <thead>
            <tr>
                <th></th>
                <th>Devices</th>
            </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
        <style>
            td.delete {
                /*background-color: #b70404;*/
                cursor: pointer;
            }
        </style>
    </div>

</div>
