<?php
$mysql['username'] = "";
$mysql['password'] = "";
$mysql['address'] = "";
$mysql['port'] = 3306;
$mysql['database'] = "";
$mysql['prefix'] = "";

//See available languages in /lang/ folder, en.php would be 'en'
$config['language'] = "en";

//Set this to true if you want to show the server "Tabs" or "Menu" at the top
//                          when running multiple servers with seperate stats
$config['enable_server_tabs'] = false;
//Edit this to add/remove servers from the menu
// ["LINK TITLE", "DB_PREFIX"]
$config['menu'] = [
  ["Server 1", "S1_"],
  ["Server 2", "S2_"]
];

// Currently the only colors available are:
// "" = default red
// "green" = green
// "blue" = blue
$config['color'] = "";




//Do not edit below here
//
//
//
//
//
include_once('lang/en.php'); //Include default language, will over ride with language set in config
if(isset($config['language']))
  if($config['language'] != "en"){
    include_once('lang/'.$config['language'].'.php'); //Include language in config if it is not set to english
  }
?>
