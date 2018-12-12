<!-- Dashboard -->
<div class="view view-dashboard">
	<?php
		$perms = array_values($permissions);
		//if(in_array("devices", $perms)) {
			require_once "view-devices.php";
		//}
		//if(in_array("data-map", $perms)) {
			require_once "view-data-map.php";
		//}
		//if(in_array("data", $perms)) {
			require_once "view-data.php";
		//}
		//if(in_array("rules", $perms)) {
			require_once "view-rules.php";
		//}
		//if(in_array("communication", $perms)) {
			require_once "view-communication.php";
		//}
		//if(in_array("other-users", $perms)) {
			require_once "view-other-users.php";
		//}
		if(in_array("permissions", $perms)) {
			require_once "view-permissions.php";
		}
		if(in_array("own-users", $perms)) {
			require_once "view-own-user.php";
		}
		if(in_array("system-settings", $perms)) {
			require_once "view-system-settings.php";
		}
		if(in_array("roomX", $perms)) {
			require_once "view-roomX.php";
		}
		if(in_array("control-map", $perms)) {
			require_once "view-control-map.php";
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
