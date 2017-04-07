<?php
include_once('lang/en.php'); //Include default language, will over ride with language set in config
include_once('config.php');
if($language != "en"){
  include_once('lang/'.$language.'.php'); //Include language in config if it is not set to english
}
?>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><?php echo $lang['page_title']; ?></title>
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/custom.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
<div class="wrapper">
  <!-- Un Comment out/delete this section to remove the multiple server buttons -->

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
  ["bBroken", $lang['block_broken']],
  ["bPlaced", $lang['block_placed']],
  ["pDeaths", $lang['player_death']],
  ["pKills", $lang['player_kill'],
  ["mKills", $lang['mob_kill']],
  ["arrowShot", $lang['arrow_shot']],
  ["pLogins", $lang['player_join']],
  ["dTaken", $lang['damage_taken']],
  ["dCaused", $lang['damage_caused']],
  ["iPickUp", $lang['item_pick_up']],
  ["iDropIt", $lang['item_dropped']],
  ["pChatMsg", $lang['chat_msg']],
  ["iCrafted", $lang['item_crafted']],
  ["iChanted", $lang['item_chanted']],
  ["iBrokeIt", $lang['tools_broken']],
  ["xpGained", $lang['xp_gain']],
  ["timePlayed", $lang['time_played']],
  ["foodEaten", $lang['food_eaten']]
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
      ["iBrokeIt", "tools_broken", "Tools Broken"],
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
