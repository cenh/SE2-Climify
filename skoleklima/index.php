    <?php
    require_once "meta.php";
    ?>

    <!DOCTYPE html>
    <html>
    <head>
        <title><?php echo $software_Name ?></title>
        <!--<meta charset="UTF-8">-->
        <meta http-equiv="Content-type" content="text/html;charset=UTF-8">
        <meta name="author" content="DTU Compute">
        <meta name="description"
              content="Se aktuel status på indendørsklima på folkeskoler i Danmark – Copyright © <?php echo date("Y") . " " . $company_Name; ?>">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="shortcut icon" type="image/png" href="img/logo/favicon.png?v=<?php echo $system_version ?>"/>
        <link rel="stylesheet" type="text/css" href="css/styles.css?v=<?php echo $system_version ?>">
        <link href="https://fonts.googleapis.com/css?family=Arimo:400,400i,700|Poppins:500|Raleway:500i" rel="stylesheet">
        <link rel="stylesheet" href="lib/font-awesome/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="lib/sweetalert/dist/sweetalert.css">
        <link rel="stylesheet" href="lib/checkbox/checkbox_style.css">
        <link rel="stylesheet" type="text/css" href="lib/daterangepicker/daterangepicker.css"/>
        <link rel="stylesheet" href="croppie/croppie.css"/>
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">

    </head>
    <body>

    <?php include_once("analyticstracking.php") ?>

    <div id="current-user-info">
        <input type="hidden" id="txt-software-name" value="<?php echo $software_Name ?>" readonly>
        <input type="hidden" id="txt-current-user-ID" value="<?php echo $currentUserID ?>" readonly>
        <input type="hidden" id="txt-current-user-name" value="<?php echo $currentUserName ?>" readonly>
        <input type="hidden" id="txt-current-user-role" value="<?php echo $currentUserRole ?>" readonly>
        <input type="hidden" id="txt-current-user-first-name" value="<?php echo $currentUserFirstName ?>" readonly>
        <input type="hidden" id="txt-current-user-last-name" value="<?php echo $currentUserLastName ?>" readonly>
        <input type="hidden" id="txt-current-user-email" value="<?php echo $currentUserEmail ?>" readonly>
        <input type="hidden" id="txt-current-user-school" value="<?php echo $currentUserSchoolAllowed ?>" readonly>
        <input type="hidden" id="txt-current-user-school-name" value="<?php echo $currentUserSchoolAllowedName ?>" readonly>
        <input type="hidden" id="txt-current-perm-logbook" value="<?php echo $currentPermLogbook ?>" readonly>
        <input type="hidden" id="7wpk6dQLcKKTPy4" value="<?php echo $api_key ?>" readonly>
        <input type="hidden" id="7wpk6dQLcKKTPy4" value="<?php echo $ç_key ?>" readonly>
        <input type="hidden" id="8wpk6diLcKKdPyq" value="<?php echo SIGNIN_TOKEN ?>" readonly>
    </div>

    <div id="loadCower">
        <img class="loader-gif" src="img/load/loading-animation.gif">
    </div>

    <?php

    require_once "ink/views/view-wrong-screen.php";

    if (!$currentUserID) {
        require_once "ink/views/view-login.php";
    }

    if ($currentUserID) {
        ?>
        <div class="viewport"> <?php
        require_once "ink/navigation.php";
        ?>
        <div class="dashboard"> <?php
            require_once "ink/header.php";
            require_once "ink/views/view-dashboard.php";
            ?> </div> </div><?php
    }

    ?>
    <script
            src="https://code.jquery.com/jquery-3.3.1.js"
            integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
            integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
            crossorigin="anonymous"></script>
    <script type="text/javascript" src="js/collapsible.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.bundle.min.js"></script>
    <script src='https://www.google.com/recaptcha/api.js'></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="lib/sammy/sammy.min.js" type="text/javascript"></script>
    <script src="lib/sweetalert/dist/sweetalert.min.js"></script>
    <script src="lib/chart-2.4.0/chart-min.js"></script>
    <script type="text/javascript" src="lib/daterangepicker/moment-with-locales.min.js"></script>
    <script type="text/javascript" src="lib/daterangepicker/daterangepicker.js"></script>
    <script type="text/javascript" src="lib/cropbox/cropbox-min.js"></script>
    <script src="js/main.js?v=<?php echo $system_version ?>"></script>
    <script type="text/javascript" src="js/api-getter.js"></script>
    <script type="text/javascript" src="js/rule-getter.js"></script>
    <script src="croppie/croppie.js"></script>
    <script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyCH3z0AYLLOwg0HhYvfXVWVVG1isTjyMUU&libraries=geometry"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
            integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
            crossorigin="anonymous"></script>
    <script src="lib/paho.javascript-1.0.3/paho-mqtt.js"></script>

    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
    <!--    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>-->


    <script type="text/javascript" src="js/devices.js"></script>
    <script type="text/javascript" src="js/manage-devices.js"></script>


    <?php
    if ($currentUserID) { ?>

        <script>
            [
                'js/main-al.js?v=<?php echo $system_version ?>',
                'js/nav.js?v=<?php echo $system_version ?>',
                'js/charts.js?v=<?php echo $system_version ?>'
            ].forEach(function (src) {
                var script = document.createElement('script');
                script.src = src;
                script.async = false;
                document.head.appendChild(script);
            });
        </script>

    <?php } else { ?>
        <script>
            ga('set', 'page', '/Login/');
            ga('send', 'pageview');
        </script>
    <?php } ?>

    </body>
    </html>
