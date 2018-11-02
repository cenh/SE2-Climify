<!-- View dahsboard -->
<div style="width: 100%; height: 50%; text-align: center;
         font-weight: bold; font-size: 150%;"> Roles </div>

<div style="width: 100%; height: 50%; border: 1px solid #dddddd;
    text-align: left;
    padding: 8px; float:left;">
    <table id="roles_table" class="display">
        <thead>
        <tr>
            <th>Name</th>
        </tr>
        </thead>
        <tbody>

        </tbody>
    </table>
</div>

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
    </div>
</div>