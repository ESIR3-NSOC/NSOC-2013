<!doctype html>
<html class="no-js" lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Foundation | Welcome</title>
    <link rel="stylesheet" href="css/foundation.css" />
    <script src="js/modernizr.js"></script>
  </head>
  <body>
    
    <div class="row">
      <div class="large-12 columns">
        <h1>Welcome sur le projet NSOC</h1>
      </div>
    </div>
    
    <div class="row">
      <div class="large-12 columns">
      	<div class="panel">
	        <h3>Mode Scénario</h3>
	        <p>Vous pouvez interagir avec les actionneurs, enjoy !</p>

      	</div>
      </div>
    </div>

    <div class="row">
      <div class="large-12 medium-8 columns">
        
        <!-- Grid Example -->

        <div class="row">
          
          <h5>ElectricStrike :</h5>
          <div class="switch large-4 small-6 ">

            <input id="x" name="switch-x" type="radio" checked>
            <label for="x" onclick>CloseLock</label>
            <input id="x1" name="switch-x" type="radio">
            <label for="x1" onclick>OpenClock</label>
            <span></span>

          </div>


          <h5>Light :</h5>
          <div class="row">
            <div class="large-3 small-6 columns">
              <div class="switch"> 
              <input id="y" name="switch-y" type="radio" checked>
              <label for="y" onclick="">Off</label>
              <input id="y1" name="switch-y" type="radio">
              <label for="y1" onclick="">On</label>
              <span></span>
              </div>
            </div>
            <a class="small secondary button large-1 ">Toogle</a>
          </div> 

          <h5>Projector :</h5>
          <div class="switch large-4 small-6 ">

            <input id="z" name="switch-z" type="radio" checked>
            <label for="z" onclick>TurnOff</label>
            <input id="z1" name="switch-z" type="radio">
            <label for="z1" onclick>TurnOn</label>
            <span></span>

          </div>   

          <h5>Screen :</h5>
          <div class="switch large-4 small-6 ">

            <input id="w" name="switch-w" type="radio" checked>
            <label for="w" onclick>SetDown</label>
            <input id="w1" name="switch-w" type="radio">
            <label for="w1" onclick>SetUp</label>
            <span></span>

          </div>   

        

        </div>


        <h5>Autres commandes:</h5>
        <a href="index.html" class="medium secondary button">Retour au menu</a></p> 
        <hr />
              
    
    <script src="js/jquery.js"></script>
    <script src="js/foundation.min.js"></script>
    <script>
      $(document).foundation();
    </script>
  </body>
</html>
