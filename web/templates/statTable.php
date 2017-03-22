<div class="wrapper">
  <div class="table">
    <div class="row header">
      <div class="cell">
        Player
      </div>
      <div class="cell">
        <?php echo $ttitle; ?>
      </div>
    </div>

<?php  foreach($results as $row) { ?>
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
