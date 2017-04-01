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
  <!-- UnComment out/delete this section to remove the multiple server buttons -->

<!-- <div class="row">
  <div class="col-xs-12">
    <button type="button" class="btn btn-primary" onclick="reloadAllPrefix('CE_')">Main Server</button>
    <button type="button" class="btn btn-primary" onclick="reloadAllPrefix('te')">Test Server</button>
  </div>
</div> -->

<!-- End server button area -->


    <div class="row">
<?php $gClass = "col-lg-3 col-md-4 col-sm-6 col-xs-12";
$customStyle = "display: inline-block; min-height: 200px;";
$content = " <img src='img/cube.svg'>"; //Make sure not to use " in this variable

// [ID TO MATCH JS AT BOTTOM] [TABLE NAME ON LEFT SIDE OF TABLE]
$tableArray = [
  ["bBroken", "Blocks Broken"],
  ["bPlaced", "Blocks Placed"],
  ["pDeaths", "Player Deaths"],
  ["pKills", "Player Kills"],
  ["mKills", "Mob Kills"],
  ["pLogins", "Player Logins"],
  ["dTaken", "Damage Taken"],
  ["dCaused", "Damage Caused"],
  ["iPickUp", "Items Picked Up"],
  ["iDropIt", "Items Dropped"],
  ["pChatMsg", "Chat Messages"],
  ["iCrafted", "Items Crafted"],
  ["iChanted", "Items Enchanted"],
  ["xpGained", "XP Gained"],
  ["timePlayed", "Time Played"],
  ["foodEaten", "Food Eaten"],
  ["arrowShot", "Arrows Shot"]
];

foreach ($tableArray as $table) { ?>
  <div class="<?php echo $gClass; ?>" style="<?php echo $customStyle; ?>">
    &nbsp;
    <h2><?php echo $table[1]; ?></h2>
    <div id="<?php echo $table[0]; ?>">
        <?php echo $content; ?>
    </div>
  </div>
<?php } ?>


  </div>
</div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>

    <script>
    var gPrefix;
    <?php if(isset($_GET['prefix'])){ ?>
     gPrefix ='<?php echo $_GET['prefix']; ?>';
     <?php } ?>

    function loadStat(id, type, args){
      var url = "ajax.php?type=" + type;
      if(args.title != null)
        url = url + "&table_title="+args.title;
      if(args.reloadId != null)
        url = url + "&reload_id="+args.reloadId;
      if(args.prefix != null)
        url = url + "&prefix=" + args.prefix;
      $.get( url, function( data ) {
        $("#" + id).html(data);
      });
    }

    function delayTimer(time, i){
      setTimeout(function() {
        var args = {
          title:stats[i][2],
          reloadId:stats[i][0]
         };
         if(gPrefix != null)
          args.prefix = gPrefix;

        loadStat(stats[i][0], stats[i][1], args);
     }, time);
    }
    //Stats setup: [ID Of where to put the ajax content, database column name, title for the # column]
    var stats = [
      ["bBroken", "blocks_broken", "Blocks Broken"],
      ["bPlaced", "blocks_placed", "Blocks Placed"],
      ["pDeaths", "deaths", "Deaths"],
      ["pKills", "pvp_kills", "PvP Kills"],
      ["mKills", "mob_kills", "Mob Kills"],
      ["pLogins", "logins", "Login Count"],
      ["dTaken", "damage_taken", "Damage Taken"],
      ["dCaused", "damage_caused", "Damage Caused"],
      ["iPickUp", "items_picked_up", "Items Picked Up"],
      ["iDropIt", "items_dropped", "Items Dropped"],
      ["pChatMsg", "chat_messages", "Chat Messages"],
      ["iCrafted", "items_crafted", "Items Crafted"],
      ["iChanted", "items_enchanted", "Items Enchanted"],
      ["xpGained", "xp_gained", "XP Gained"],
      ["timePlayed", "time_played", "Time Played"],
      ["foodEaten", "food_eaten", "Food Eaten"],
      ["arrowShot", "arrows_shot", "Arrows Shot"]
    ];

    function reloadAllPrefix(prefix){
      gPrefix = prefix;
      for (var i = 0; i < stats.length; i++) {
         $("#" + stats[i][0]).html("<?php echo $content; ?>");
      }
      reloadAll();
    }
    function reloadAll(){
      for (var i = 0; i < stats.length; i++) {
         delayTimer(500 * i, i);
      }
    }
    reloadAll();
    </script>
  </body>
</html>
