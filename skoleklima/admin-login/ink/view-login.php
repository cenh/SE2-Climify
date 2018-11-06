<!-- Login -->
<div class="viewport">
    <div class="viewport-login"> 
        <div class="login-background-image"></div>
        <div class="login-form-wrapper">
            <h1>Login</h1>
            <input type="text" id="inp-login-username" placeholder="Username">
            <input type="password" id="inp-login-password" placeholder="Password">
            <div class="g-recaptcha" data-sitekey="6LcTlEQUAAAAAC7P3MWLDoW7pM0bZPId8ZO2KK6y"></div>
            <span>
                <button id="btn-login-system">Sign in</button>
            </span>
        </div>
        <div class="logo-container">
            <p>Copyright © <?php echo date("Y") . " - " . $company_Name; ?></p>
            <img src="../img/logo/logo2.png">
        </div>
    </div>
</div>

<div style="width: 100%; text-align: center;
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