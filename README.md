[ ![Discord Support](https://www.mediafire.com/convkey/510b/iw2k26exg0qlf076g.jpg) ](https://discord.gg/p5DAvc6)
[ ![Bugs, Issues, Feature Requests](https://www.mediafire.com/convkey/2320/x80qtabf3auhhjr6g.jpg) ](../../issues)
[ ![Donate](https://www.mediafire.com/convkey/910d/z8160kkzvezi4km6g.jpg) ](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=THXHQ5287TBA8)


# TotalTracker

Spigot 1.11 plugin to keep track of all kinds of stats. See description for a current list.

**This requires MySQL**   
I do not plan to develop this for other databases for several reasons, sorry!

# Description
Note: This requires Java 1.8+

All stats are processed asynchronously so your server never experiences any lag.  
Stats will automatically be imported from bukkit/spigot statistics(This will occur on player login).

This will keep track of many stats, currently:
- PvP kills
- Deaths
- Mob kills
- Blocks broken
- Blocks placed
- Logins
- Damage Taken
- Damage Caused
- Items Picked Up
- Chat Messages
- Items Crafted
- XP Gained
- Time Played
- Food Eaten
- Items Dropped
- Items Enchanted
- Arrows Shot
- Tools Broken
- Buckets Filled
- Buckets Emptied

# Usage
Do stuff in game

# Permissions
[View permissions here](../../blob/master/src/plugin.yml)


# Installation
- Create a new MySQL Database, do not create any tables, the plugin will do that for you.
- Place the jar file in your plugins folder
- Restart your server
- Edit the configuration options
- Restart your server again

**Web Portion**

- Upload the included files
- Edit the config.php
- Done!
- (You may edit any files you want to suit your needs/preferences)
- For multiple servers, open index.php and look for the commented out section for server "Tabs" and change the button prefixes, add more buttons, remove, etc.

# Updating
- The plugin will automatically update your database, all you have to do is install the new jar file!

**Updating the web portion**  
- Just upload all files **except** the config.php file. Unless you have made custom changes, then it's up to you to update it with the new changes.

# Configuration
[View default configuration file here](../../blob/master/src/config.yml)

# To-do
- [WEB]Add a search function to the web portion
- [WEB]Add pages(Currently only shows top 10) to the web portion
- Add distance walked
- Add fish caught
- Money earned (Vault)
- Current money (Vault)
- Add words said
