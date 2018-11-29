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

    <!-- Button trigger modal -->
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter">
        Launch demo modal
    </button>

    <!-- Modal -->
    <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalCenterTitle">Modal title</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    ...
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Save changes</button>
                </div>
            </div>
        </div>
    </div>

</div>
