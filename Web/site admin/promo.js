var lienUrl;
var ws = {};
var promoReceived;

$(function (){
ws = new ReconnectingWebSocket('ws://localhost:9000/admin');

ws.onopen = function(){
	alert("socket ouverte");
	var batimentSend = {"pageWeb" : "promo", "type" : "appelBdd"};
	ws.send(JSON.stringify(batimentSend));	
};

ws.onmessage = function(message){
	requeteReceived = message.data;
	requeteReceived = JSON.parse(requeteReceived);
	promoReceived = requeteReceived;
	
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

function new_js(){

		document.getElementById('building').innerHTML ='<p><div>Veuillez donner le nom de la nouvelle promotion</div><br /><label for = "new_promo">Valeur : </label><input type = "text" name = "new_promo" id ="new_promo"/><br /><br /><input type="button" value="Envoyer" onClick = "sendPromo()"/>';
}

function delete_js(){

		document.getElementById('building').innerHTML ='<p><div>Veuillez choisir la promotion à supprimer</div><br /><label for = "delete_promo">Option de l étudiant: </label><select name = "delete_promo" id ="delete_promo"/>' + addPromo() + '</select><br /><br /><input type="button" value="Envoyer" onClick = "sendPromo()"/>';
}

function addPromo(){
var nbPromo = "";
var i = 0;
while(i<promoReceived.promo.length){
nbPromo = nbPromo + "<option value = " + promoReceived.promo[i] + ">" + promoReceived.promo[i] + "</option>";
i = i + 1;
}
return nbPromo;
}

window.onbeforeunload = function() {
	ws.close();
};

function sendPromo(){

var new_promo = null;
var delete_promo = null;

if(document.getElementById("new_promo") != null){
var new_promo = document.getElementById("new_promo");
new_promo = new_promo.value;
}

if(document.getElementById("delete_promo") != null){
var delete_promo = document.getElementById("delete_promo");
delete_promo = delete_promo.value;
}

var myJSONObject = null;

if(new_promo != null) {
myJSONObject = { "pageWeb" : "promo", "type" : "modifBdd", "action" : "add" , "tableau" : [new_promo]};
}
else if(delete_promo != null){
myJSONObject = { "pageWeb" : "promo", "type" : "modifBdd", "action" : "delete" , "tableau" : [delete_promo]};
}

ws.send(JSON.stringify(myJSONObject));
window.location.reload();
}

