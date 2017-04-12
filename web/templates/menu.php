<nav class="navbar navbar-default navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#"><?php echo $lang['page_title']; ?></a>
    </div>
    <div id="navbar" class="navbar-collapse collapse">
      <ul class="nav navbar-nav">
        <?php
        if($config['enable_server_tabs']){
         foreach ($config['menu'] as $menu) {
          ?>
          <li><a href="#" onclick="reloadAllPrefix('<?php echo $menu[1]; ?>')"><?php echo $menu[0]; ?></a></li>
          <?php
          }
        }
        ?>
      </ul>
      <div class="navbar-form nabvar-right form-group">
        <input type="text" class="form-control" id="username" placeholder="Username">
        <button type="button" onclick="searchUser()" class="btn btn-primary">Search</button>
      </div>
    </div><!--/.nav-collapse -->
  </div>
</nav>
