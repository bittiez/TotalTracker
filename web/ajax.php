<?php
require_once('config.php');
require_once('libs/meekrodb.2.3.class.php');

DB::$user = $mysql['username'];
DB::$password = $mysql['password'];
DB::$dbName = $mysql['database'];
DB::$host = $mysql['address'];
DB::$port = $mysql['port'];

// ?type=TYPE&page=1&limit=10&table_title=BlahBlah

if(isset($_GET['type'])){
  $type = $_GET['type'];
  $page = 1;
  $limit = 10;
  $ttitle = "";

  if(isset($_GET['page']))
    $page = $_GET['page'];
  if(isset($_GET['limit']))
    $limit = $_GET['limit'];
  if(isset($_GET['table_title']))
    $ttitle = $_GET['table_title'];

  $results = DB::query("SELECT player_name, " . mysql_escape_string($type) . " FROM `" . $mysql['database'] . "`.`" . $mysql['prefix'] . "TotalTracker` ORDER BY ".mysql_escape_string($type)." DESC LIMIT " . mysql_escape_string($limit));
  if($type == "time_played"){
    foreach($results as $key => $row){
      $results[$key][$type] = formatTime($row[$type]);
    }
  }
  include('templates/statTable.php');


}

function formatTime($minutes){
  return toDateInterval($minutes * 60)->format('%a days, %h hours, %i minutes');
}
function toDateInterval($seconds) {
  return date_create('@' . (($now = time()) + $seconds))->diff(date_create('@' . $now));
}
?>
