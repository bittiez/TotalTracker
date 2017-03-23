<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>TotalTracker Stats</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/custom.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
<div class="wrapper">

    <div class="row">
<?php $gClass = "col-lg-3 col-md-4 col-sm-6 col-xs-12"; ?>
      <div class="<?php echo $gClass; ?>">
        <h2>Blocks Broken</h2>
        <div id="bBroken">
            Loading stats..
        </div>
      </div>
      <div class="<?php echo $gClass; ?>">
        <h2>Blocks Placed</h2>
        <div id="bPlaced">
            Loading stats..
        </div>
      </div>

      <div class="<?php echo $gClass; ?>">
        <h2>Player Deaths</h2>
        <div id="pDeaths">
            Loading stats..
        </div>
      </div>
      <div class="<?php echo $gClass; ?>">
        <h2>Mob Kills</h2>
        <div id="mKills">
            Loading stats..
        </div>
      </div>

      <div class="<?php echo $gClass; ?>">
        <h2>Player Kills</h2>
        <div id="pKills">
            Loading stats..
        </div>
      </div>
      <div class="<?php echo $gClass; ?>">
        <h2>Player Logins</h2>
        <div id="pLogins">
            Loading stats..
        </div>
      </div>

    </div>


</div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>

    <script>
    function loadStat(id, type){
      $.get( "ajax.php?type=" + type, function( data ) {
        $("#" + id).html(data);
      });
    }

    loadStat("bBroken", "blocks_broken");
    loadStat("bPlaced", "blocks_placed");
    loadStat("pKills", "pvp_kills");
    loadStat("pDeaths", "deaths");
    loadStat("mKills", "mob_kills");
    loadStat("pLogins", "logins");
    </script>
  </body>
</html>
