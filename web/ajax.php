<?php
require_once('config.php');
require_once('libs/meekrodb.2.3.class.php');

DB::$user = $mysql['username'];
DB::$password = $mysql['password'];
DB::$dbName = $mysql['database'];
DB::$host = $mysql['address'];
DB::$port = $mysql['port'];

if(isset($_GET['type'])){
  $type = $_GET['type'];

}
?>
