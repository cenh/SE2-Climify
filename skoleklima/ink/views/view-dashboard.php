<!-- Dashboard -->
<!--
 Author: Christian Hansen & Christian Petersen
-->
<div class="view view-dashboard">
	<?php
	foreach ($permissions as $permission){
	    require_once "view-".$permission.".php";
    }
    require_once "view-control-map.php";

    if($currentPermLogbook == 1 ){
        require_once "view-communication.php";
    }
    ?>
	<div class="go-to-top">
		<a href="<?php echo $company_Website ?>#top-header" target="_self"><i class="ico-go-to-top fa fa-arrow-up link" aria-hidden="true"></i></a>
	</div>
	<div class="cookie-info-outer-wrapper">
		<div class="cookie-info-wrapper">
			<div>
				<img src="img/logo/cook_ico.png" alt="Cookie">
				<p>This site uses cookies to remember your settings.</br>
				By continuing on the site, you accept the use of cookies.
				</p>
		</div>
			<button id="btn-accept-cookie">OK</button>
		</div>
	</div>
</div>
