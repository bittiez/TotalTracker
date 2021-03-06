<?php
require_once('config.php');
?>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><?php echo $lang['page_title']; ?></title>
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/custom.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
<?php
  include("templates/menu.php");
  $mMarginTop="margin-top: 40px;";
?>

<div class="wrapper" style="<?php echo $mMarginTop; ?>">
<div class="row">
<?php $gClass = "col-lg-3 col-md-4 col-sm-6 col-xs-12";
$customStyle = "display: inline-block; min-height: 200px;";
$content = " <img src='img/cube.svg'>"; //Make sure not to use " in this variable

$tableArray = [
  ["bBroken", "blocks_broken", $lang['block_broken']],
  ["bPlaced", "blocks_placed", $lang['block_placed']],
  ["pDeaths", "deaths", $lang['player_death']],
  ["pKills", "pvp_kills", $lang['player_kill']],
  ["mKills", "mob_kills", $lang['mob_kill']],
  ["arrowShot", "arrows_shot", $lang['arrow_shot']],
  ["pLogins", "logins", $lang['player_join']],
  ["dTaken", "damage_taken", $lang['damage_taken']],
  ["dCaused", "damage_caused", $lang['damage_caused']],
  ["iPickUp", "items_picked_up", $lang['item_pick_up']],
  ["iDropIt", "items_dropped", $lang['item_dropped']],
  ["iFilledBucket", "buckets_filled", $lang['buckets_filled']],
  ["iEmptiedBucket", "buckets_emptied", $lang['buckets_emptied']],
  ["pChatMsg", "chat_messages", $lang['chat_msg']],
  ["iSaidWord", "words_spoken", $lang['words_said']],
  ["iCrafted", "items_crafted", $lang['item_crafted']],
  ["iChanted", "items_enchanted", $lang['item_chanted']],
  ["iBrokeIt", "tools_broken", $lang['tools_broken']],
  ["xpGained", "xp_gained",  $lang['xp_gain']],
  ["timePlayed", "time_played", $lang['time_played']],
  ["foodEaten", "food_eaten", $lang['food_eaten']],
  ["fishCaught", "fish_caught", $lang['fish_caught']],
  ["currentBal", "current_money", $lang['current_bal']]
];

foreach ($tableArray as $table) { ?>
  <div class="<?php echo $gClass; ?>" style="<?php echo $customStyle; ?>">
    &nbsp;
    <h2><?php echo $table[2]; ?></h2>
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

     function searchUser(){
       var user = $("#username").val();
       if(user.length > 0)
        reloadAllUser(user);
     }

    function loadStat(id, type, args){
      $("#"+id).html("<?php echo $content; ?>");

      var url = "ajax.php?type=" + type;
      if(args.title != null)
        url = url + "&table_title="+args.title;
      if(args.reloadId != null)
        url = url + "&reload_id="+args.reloadId;
      if(args.prefix != null)
        url = url + "&prefix=" + args.prefix;
      if(args.username != null)
        url = url + "&username=" + args.username;
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
    function delayTimerUser(time, i, user){
      setTimeout(function() {
        var args = {
          title:stats[i][2],
          reloadId:stats[i][0],
          username:user
         };
         if(gPrefix != null)
          args.prefix = gPrefix;

        loadStat(stats[i][0], stats[i][1], args);
     }, time);
    }

    var stats = <?php echo json_encode($tableArray); ?>;

    function reloadAllUser(user){
      for (var i = 0; i < stats.length; i++) {
         $("#" + stats[i][0]).html("<?php echo $content; ?>");
      }
      for (var i = 0; i < stats.length; i++) {
         delayTimerUser(500 * i, i, user);
      }
    }

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
