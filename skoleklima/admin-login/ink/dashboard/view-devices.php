<!-- View dahsboard -->

<div class="viewport">
    <div class="viewport-dashboard"> 
        <div class="top-btn-wrapper">
            <span>
                <input id="imp-search-company" type="text" placeholder="Search specific user">
                <button id="btn-search-company">Search</button>
            </span>
            <span>
            <select id="inp-serch-block">
                <option value="2">All users</option>
                <option value="1">Active users</option>
                <option value="0">Inactive users</option>
            </select>
            <button id="btn-update-company-list">Update list</button>
        </span>
        </div>

        <div class="header-text-wrapper">
            <h4>All users</h4>
        </div>
        <div class="user-list">
            <p><i class="update-spinner fa fa-spinner fa-spin fa-1x fa-fw"></i> Fetching data...</p>
            <!-- Content goes here -->
        </div>

        <div class="DTUManager-list">
            <!--<p><i class="update-spinner fa fa-spinner fa-spin fa-1x fa-fw"></i> Fetching data...</p>-->
            <!-- Content goes here -->



            <div class="user-list-single-header" style="display:flex;margin-left:10px;">
                <p>DTU Managers</p>
                <i class="ico-show-meta fa fa-plus link" style = "margin-left:17px"></i>
                <i class="ico-hide-meta fa fa-minus link" style="display:none;margin-left:17px"></i>
            </div>
            <div class="user-list-single-wrapper">
                <div class="user-list-single-meta">
                    <div class="user-meta-subusers">
                        <div class="create-subuser-wrapper">
                            <p>Create New DTU Manager</p>
                            <span>
                            <input type="text" class="inp-system-create-user inp-system-create-user-username" placeholder="Username (4-8 character)">
                            <input type="password" class="inp-system-password" placeholder="Password">
                            <button id="btn-create-manager">Create user</button>
                        </span>
                        </div>
                        <div class="user-meta-subusers-userlist">
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<div style="height: auto">
    <div style="width: 100%; text-align: center;
         font-weight: bold; font-size: 150%;"> Roles </div>

    <div style="width: 100%; height: auto; border: 1px solid #dddddd;
    text-align: left;
    padding: 8px; float:left;">
        <table id="roles_table" class="display">
            <thead>
            <tr>
                <th>Role Name</th>
            </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
    </div>
</div>

<!-- The Modal -->
<div id="myModal" class="modal">

    <!-- Modal content -->
    <div class="modal-content">
        <span class="close">&times;</span>
        <p>Some text in the Modal..</p>
    </div>

</div>

<style>
    /* The Modal (background) */
    .modal {
        display: none; /* Hidden by default */
        position: fixed; /* Stay in place */
        z-index: 1; /* Sit on top */
        padding-top: 100px; /* Location of the box */
        left: 0;
        top: 0;
        width: 100%; /* Full width */
        height: 100%; /* Full height */
        overflow: auto; /* Enable scroll if needed */
        background-color: rgb(0,0,0); /* Fallback color */
        background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
    }

    /* Modal Content */
    .modal-content {
        background-color: #fefefe;
        margin: auto;
        padding: 20px;
        border: 1px solid #888;
        width: 80%;
    }

    /* The Close Button */
    .close {
        color: #aaaaaa;
        float: right;
        font-size: 28px;
        font-weight: bold;
    }

    .close:hover,
    .close:focus {
        color: #000;
        text-decoration: none;
        cursor: pointer;
    }
</style>

