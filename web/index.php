<?php
require_once('config.php');

?>

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
    <link href="css/sb-admin.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    <div class="container-fluid">
      <div id="page-wrapper">

      <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">
                TotalTracker Stats
            </h1>
        </div>
    </div>


    <div class="row">
      <div class="col-sm-3">
        <h2>Blocks Broken</h2>
          <div class="table-responsive">
              <table class="table table-bordered table-hover">
                  <thead>
                      <tr>
                          <th>Player</th>
                          <th>Blocks Broken</th>
                      </tr>
                  </thead>
                  <tbody>
                      <tr>
                          <td>/index.html</td>
                          <td>1265</td>
                      </tr>
                      <tr>
                          <td>/about.html</td>
                          <td>261</td>
                      </tr>
                  </tbody>
              </table>
          </div>
      </div>
      <div class="col-sm-3"></div>
      <div class="col-sm-3"></div>
    </div>

</div>
</div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </body>
</html>
