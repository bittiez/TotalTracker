<?php
require_once('config.php');
require_once('libs/meekrodb.2.3.class.php');

DB::$user = $mysql['username'];
DB::$password = $mysql['password'];
DB::$dbName = $mysql['database'];
DB::$host = $mysql['address'];
DB::$port = $mysql['port'];

// ?type=TYPE&page=1&limit=10&table_title=BlahBlah&prefix=test_

if(isset($_GET['type'])){
  $type = $_GET['type'];
  $page = 1;
  $limit = $config['rows_to_show'];
  $ttitle = ""; $reloadId = ""; $username = "";

  if(isset($_GET['page']))
    $page = $_GET['page'];
  if(isset($_GET['limit']))
    $limit = $_GET['limit'];
  if(isset($_GET['table_title']))
    $ttitle = $_GET['table_title'];
  if(isset($_GET['reload_id']))
    $reloadId = $_GET['reload_id'];
  if(isset($_GET['prefix']))
    $mysql['prefix'] = $_GET['prefix'];
  if(isset($_GET['username']))
    $username = $_GET['username'];

  if(isset($_GET['username']))
    $results = DB::query("SELECT %b, %b FROM `" . $mysql['database'] . "`.`" . $mysql['prefix'] . "TotalTracker` WHERE player_name=%s ORDER BY %b DESC LIMIT %i", "player_name", $type, $username, $type, $limit);
  else
    $results = DB::query("SELECT %b, %b FROM `" . $mysql['database'] . "`.`" . $mysql['prefix'] . "TotalTracker` ORDER BY %b DESC LIMIT %i", "player_name", $type, $type, $limit);

  if($type == "time_played"){
    foreach($results as $key => $row){
      $results[$key][$type] = formatTime($row[$type], $lang);
    }
  }
  include('templates/statTable.php');
}

function formatTime($minutes, $lang){
  return toDateInterval($minutes * 60)->format('%a '.$lang['day'].', %h '.$lang['hour'].', %i '.$lang['minute']);
}
function toDateInterval($seconds) {
  return date_create('@' . (($now = time()) + $seconds))->diff(date_create('@' . $now));
}
?>
