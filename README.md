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

# Usage

Do stuff in game

# Permissions

None, may add them upon request.


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

**Updating**  
The plugin will automatically update your database, all you have to do is install the new jar file!


# Configuration


https://github.com/bittiez/TotalTracker/blob/master/src/config.yml

# To-do
- Add auto updater?
- Config option for block counting in creative mode
- Add a search function to the web portion
- Add pages(Currently only shows top 10) to the web portion
- Add distance walked
- Add fish caught
- Add food eaten
- Add items dropped
- Add buckets filled
- Add arrows shot
