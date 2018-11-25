<!-- Navigation -->
<div class="element" id="side-navigation">
	<?php
		if(	$currentUserRole == 1 ) {
			$currentUserRights = "System administrator";
		} else if(	$currentUserRole == 15 ) {
			$currentUserRights = "System observer";
		}
		 else {
			$currentUserRights = $currentUserSchoolAllowed;
		}
	?>
	<h4 class="user-info-nav"><?php echo $currentUserFirstName. " " .$currentUserLastName ?></h4>
	<p class="user-info-nav"><?php echo $currentUserRights; ?></p>
	<hr class="user-info-nav">
	<nav>
		<ul>
			<?php
				$perms = array_values($permissions);
				if(in_array("devices", $perms))
				{ ?>
					<li class="menu-link menu-link-devices" data-go-to="devices">
						<a href="#/devices">
							<i class="menu-link-ico nav-icon fa fa-podcast" aria-hidden="true"></i>
							<p class="menu-link-text">Manage Building</p>
						</a>
					</li>
				<?php }
				if(in_array("data-map", $perms))
				{ ?>
					<li class="menu-link menu-link-data-map" data-go-to="data-map">
						<a href="#/sensors">
							<i class="menu-link-ico nav-icon fa fa-map" aria-hidden="true"></i>
							<p class="menu-link-text">Sensors</p>
						</a>
					</li>
				<?php }
				if(in_array("data", $perms))
				{ ?>
					<li class="menu-link menu-link-data" data-go-to="data">
						<a href="#/graphs">
							<i class="menu-link-ico nav-icon fa fa-area-chart" aria-hidden="true"></i>
							<p class="menu-link-text">Graphs</p>
						</a>
					</li>
				<?php }
				if(in_array("rules", $perms))
				{ ?>
					<li class="menu-link menu-link-rules" data-go-to="rules">
							<a href="#/rules">
									<i class="menu-link-ico nav-icon fas fa-pencil-ruler" aria-hidden="true"></i>
									<p class="menu-link-text">Rules</p>
							</a>
					</li>
				<?php }
				if(in_array("communication", $perms))
				{ ?>
					<li class="menu-link menu-link-communication" data-go-to="communication"></i>
						<a href="#/logbook">
							<i class="menu-link-ico nav-icon fa fa-comments" aria-hidden="true"></i>
							<p class="menu-link-text">Logbook</p>
						</a>
					</li>
				<?php }
				if(in_array("other-users", $perms))
				{ ?>
					<li class="menu-link menu-link-other-users" data-go-to="other-users">
						<a href="#/other-users">
							<i class="menu-link-ico nav-icon fa fa-users" aria-hidden="true"></i>
							<p class="menu-link-text" id="menu-link-text-other-users">Other Users</p>
						</a>
					</li>
				<?php }
				if(in_array("permissions", $perms))
				{ ?>
					<li class="menu-link menu-link-permissions" data-go-to="permissions">
					 <a href="#/Permissions">
						 <i class="menu-link-ico nav-icon fa fa-address-card" aria-hidden="true"></i>
						 <p class="menu-link-text">Permissions</p>
					 </a>
					</li>
				<?php }
				if(in_array("own-users", $perms))
				{ ?>
					<li class="menu-link menu-link-own-user" data-go-to="own-user">
						<a href="#/your-profile">
							<i class="menu-link-ico nav-icon fa fa-user" aria-hidden="true"></i>
							<p class="menu-link-text">Your profile</p>
						</a>
					</li>
				<?php }
				if(in_array("system-settings", $perms))
				{ ?>
					<li class="menu-link menu-link-system-settings" data-go-to="system-settings"></i>
						 <a href="#/settings">
								<i class="menu-link-ico nav-icon fa fa-cog" aria-hidden="true"></i>
								<p class="menu-link-text">Settings</p>
						 </a>
					</li>
				<?php }
				if(in_array("roomX", $perms))
				{ ?>
					<li class="menu-link menu-link-roomX" data-go-to="roomX"></i>
							<a href="#/roomX">
									<i class="menu-link-ico nav-icon fa fa-times-circle" aria-hidden="true"></i>
									<p class="menu-link-text">Room X</p>
							</a>
					</li>
				<?php }
				if(in_array("control-map", $perms))
				{ ?>
					<li class="menu-link menu-link-control-map" data-go-to="control-map"></i>
							<a href="#/control-map">
									<i class="menu-link-ico nav-icon fa fa-sitemap" aria-hidden="true"></i>
									<p class="menu-link-text">Control Map</p>
							</a>
					</li>
				<?php }
				if(in_array("education", $perms))
				{ ?>
					<hr>
					 <li class="menu-link menu-link-learning" data-go-to="learning"></i>
						 <a>
							 <i class="menu-link-ico nav-icon fa fa-book" aria-hidden="true"></i>
							 <p class="menu-link-text">Education</p>
						 </a>
					 </li>
				<?php }
			?>
			<span class="mobile-sign-out">
				<hr>
				<li class="menu-link menu-link-sign-out">
					<a href="#/">
						<i class="menu-link-ico nav-icon fa fa-sign-out" aria-hidden="true"></i>
						<p class="menu-link-text">Log out</p>
					</a>
				</li>
			</span>
		</ul>
	</nav>
	<div class="system-about-link"><i class="fa fa-info-circle" aria-hidden="true"></i>
</div>
	<div class="system-version-number"><p>v. <?php echo $system_version ?></p></div>
	<img src="img/nav/nav_bg.png">
</div>
