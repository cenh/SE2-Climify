<!-- System data map -->
<div class="single-view view-control-map">
    <div class="view-control-top">
        <span>
            <h3>Manage Devices</h3>
            <p>Configuration of devices</p>
        </span>

    </div>
    <hr>

    <!--    room dropdown-->
    <select id="select_room_manage_devices" onchange="refreshDevicesDropdown(value)">
        <option value="" selected>Choose Your Room</option>
    </select>

    <!--things dropdown-->
    <select id="select_thing_manage_devices" onchange="refreshTableChannel(value)">
        <option value="" disabled selected>Choose Your Device</option>
    </select>

    <!--    channel table-->
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
