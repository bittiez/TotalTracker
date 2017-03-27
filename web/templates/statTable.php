<div class="wrapper">
  <div class="table">
    <div class="row header">
      <div class="cell">
        Player
      </div>
      <div class="cell">
        <?php echo $ttitle; ?>
        <span style="float: right;"><i class="fa fa-refresh" aria-hidden="true" onclick="loadStat('<?php echo $reloadId; ?>', '<?php echo $type; ?>', {title:'<?php echo $ttitle; ?>', reloadId:'<?php echo $reloadId; ?>', prefix:'<?php echo $mysql['prefix']; ?>'})"></i></span>
      </div>
    </div>

<?php  foreach($results as $key => $row) { ?>
    <div class="row">
      <div class="cell">
        <?php echo $row['player_name']; ?>
      </div>
      <div class="cell">
        <?php echo $row[$type]; ?>
      </div>
    </div>
<?php } ?>


  </div>
</div>
