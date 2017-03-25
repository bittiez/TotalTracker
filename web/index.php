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
<?php $gClass = "col-lg-3 col-md-4 col-sm-6 col-xs-12";
$content = "<img src=\"img/cube.svg\">"; ?>
      <div class="<?php echo $gClass; ?>">
        <h2>Blocks Broken</h2>
        <div id="bBroken">
            <?php echo $content; ?>
        </div>
      </div>
      <div class="<?php echo $gClass; ?>">
        <h2>Blocks Placed</h2>
        <div id="bPlaced">
            <?php echo $content; ?>
        </div>
      </div>

      <div class="<?php echo $gClass; ?>">
        <h2>Player Deaths</h2>
        <div id="pDeaths">
            <?php echo $content; ?>
        </div>
      </div>
      <div class="<?php echo $gClass; ?>">
        <h2>Mob Kills</h2>
        <div id="mKills">
            <?php echo $content; ?>
        </div>
      </div>

      <div class="<?php echo $gClass; ?>">
        <h2>Player Kills</h2>
        <div id="pKills">
            <?php echo $content; ?>
        </div>
      </div>
      <div class="<?php echo $gClass; ?>">
        <h2>Player Logins</h2>
        <div id="pLogins">
            <?php echo $content; ?>
        </div>
      </div>
      <div class="<?php echo $gClass; ?>">
        <h2>Damage Taken</h2>
        <div id="dTaken">
            <?php echo $content; ?>
        </div>
      </div>
    <div class="<?php echo $gClass; ?>">
      <h2>Damage Caused</h2>
      <div id="dCaused">
          <?php echo $content; ?>
      </div>
    </div>
    <div class="<?php echo $gClass; ?>">
      <h2>Items Picked Up</h2>
      <div id="iPickUp">
          <?php echo $content; ?>
      </div>
    </div>
    <div class="<?php echo $gClass; ?>">
      <h2>Chat Messages</h2>
      <div id="pChatMsg">
          <?php echo $content; ?>
      </div>
    </div>
    <div class="<?php echo $gClass; ?>">
      <h2>Items Crafted</h2>
      <div id="iCrafted">
          <?php echo $content; ?>
      </div>
    </div>
    <div class="<?php echo $gClass; ?>">
      <h2>XP Gained</h2>
      <div id="xpGained">
          <?php echo $content; ?>
      </div>
    </div>

    </div>


</div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>

    <script>
    function loadStat(id, type, args){
      var url = "ajax.php?type=" + type;
      if(args.title != null)
        url = url + "&table_title="+args.title;
      $.get( url, function( data ) {
        $("#" + id).html(data);
      });
    }

    //Stats setup: [ID Of where to put the ajax content, database column name, title for the # column]
    var stats = [
      ["bBroken", "blocks_broken", "Blocks Broken"],
      ["bPlaced", "blocks_placed", "Blocks Placed"],
      ["pKills", "pvp_kills", "PvP Kills"],
      ["pDeaths", "deaths", "Deaths"],
      ["mKills", "mob_kills", "Mob Kills"],
      ["pLogins", "logins", "Login Count"],
      ["dTaken", "damage_taken", "Damage Taken"],
      ["dCaused", "damage_caused", "Damage Caused"],
      ["iPickUp", "items_picked_up", "Items Picked Up"],
      ["pChatMsg", "chat_messages", "Chat Messages"],
      ["iCrafted", "items_crafted", "Items Crafted"],
      ["xpGained", "xp_gained", "XP Gained"]
    ];

    for (var i = 0; i < stats.length; i++) {
       loadStat(stats[i][0], stats[i][1], {title:stats[i][2]});
    }
    </script>
  </body>
</html>
