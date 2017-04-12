<div class="wrapper">
  <div class="table">
    <div class="row <?php if(isset($config['color'])) echo $config['color']; ?> header">
      <div class="cell">
        <?php echo $lang['player_name']; ?>
      </div>
      <div class="cell">
        <?php echo $ttitle; ?>
        <span style="float: right;" class="reloadIconSmall"><i class="fa fa-refresh" aria-hidden="true" onclick="loadStat('<?php echo $reloadId; ?>', '<?php echo $type; ?>', {title:'<?php echo $ttitle; ?>', reloadId:'<?php echo $reloadId; ?>', prefix:'<?php echo $mysql['prefix']; ?>'})"></i></span>
      </div>
    </div>

<?php
if(count($results) < 1){ ?>
  <div class="row">
    <div class="cell">
      No data found
    </div>
    <div class="cell">
    </div>
  </div>
<?php } else {
foreach($results as $key => $row) { ?>
    <div class="row">
      <div class="cell">
        <?php echo $row['player_name']; ?>
      </div>
      <div class="cell">
        <?php echo $row[$type]; ?>
      </div>
    </div>
<?php }} ?>


  </div>
</div>
