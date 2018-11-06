<?php
//************************************************
//	Get influx database info
//************************************************

require_once "../meta.php";
require_once "../app/vendor/autoload.php";

$rb = new RuleBuilder;
$rule = $rb->create(
  $rb['temperature']->greaterThanOrEqualTo($rb['actualTemperature']),

  function() {
    error_log("RULE_EXECUTED!!!!!", 0);
  }
);

$context = new Context(array(
  'temperature' => 25,
  'actualTemperature' => function() {
      return 20;
    },
));

$rule->execute($context);
 ?>
