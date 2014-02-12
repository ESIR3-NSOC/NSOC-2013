var lienUrl;
var ws = {};

$(function (){
ws = new ReconnectingWebSocket('ws://localhost:9000/admin');

ws.onopen = function(){
	alert("socket ouverte");
	
};

ws.onmessage = function(message){
	requeteReceived = message.data;
};
ws.onclose = function(){
	alert("socket fermé");
};
});

var include = function(url, callback){
 
    /* on crée une balise<script type="text/javascript"></script> */
    var script = document.createElement('script');
    script.type = 'text/javascript';
 
    /* On fait pointer la balise sur le script qu'on veut charger
       avec en prime un timestamp pour éviter les problèmes de cache
    */

    script.src = url + '?' + (new Date().getTime());
 
    /* On dit d'exécuter cette fonction une fois que le script est chargé */
    if (callback) {

        script.onreadystatechange = callback;
        script.onload = script.onreadystatechange;
    }

    /* On rajoute la balise script dans le head, ce qui démarre le téléchargement */
    document.getElementsByTagName('head')[0].appendChild(script);
   
}

include('setForm.js', function() {

 lienUrl = getUrl();
});

window.onbeforeunload = function() {
	ws.close();
};

function sendNewEquipement(){

var equipement = document.getElementById("equipement");
equipement = equipement.value;

var myJSONObject = { "pageWeb" : "newEquipement", "type" : "modifBdd", "tableau" : [equipement]};

ws.send(JSON.stringify(myJSONObject));
window.location.reload();
}