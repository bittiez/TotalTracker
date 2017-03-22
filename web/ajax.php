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

  $results = DB::query("SELECT player_name, " . $type . " FROM `" . $mysql['database'] . "`.`" . $mysql['prefix'] . "TotalTracker` ORDER BY ".$type." DESC LIMIT " . $limit);

  ?>
  <table class="table table-bordered table-hover">
      <thead>
          <tr>
              <th>Player</th>
              <th><?php echo $ttitle; ?></th>
          </tr>
      </thead>
      <tbody>
  <?php

  foreach ($results as $row) {
    ?>
        <tr>
            <td><?php echo $row['player_name']; ?></td>
            <td><?php echo $row[$type]; ?></td>
        </tr>
    <?php
  }
  ?>
</tbody>
</table>
  <?php


}
?>
