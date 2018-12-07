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
                        <div class="modal fade" id="roleDropdown"  tabindex="-1" role="dialog" aria-labelledby="roleDropdownTitle" aria-hidden="true">>
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="roleDropdownTitle"</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <form id="newUserRole"></form>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                        <button type="button" id="SaveRole" class="btn btn-primary">Save changes</button>
                                    </div>
                                </div>
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
                <th>Name</th>
                <th>Permissions</th>
            </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
    </div>
</div>

