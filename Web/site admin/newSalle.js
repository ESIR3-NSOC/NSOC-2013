var lienUrl;
var ws = {};
var requeteReceived;


$(function (){
ws = new ReconnectingWebSocket('ws://localhost:9000/admin');

ws.onopen = function(){
	alert("socket ouverte");
	var batimentSend = {"pageWeb" : "newSalle", "type" : "appelBdd"};
	ws.send(JSON.stringify(batimentSend));	
};

ws.onmessage = function(message){
	requeteReceived = message.data;
	requeteReceived = JSON.parse(requeteReceived);
	
	batiment_js();
	equipement_js();
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

function batiment_js(){

		document.getElementById('id_batiment_js').innerHTML = '<label for = "batiment">Nom du batiment : </label><select name = "batiment" id ="batiment"/>' + addBatiment() + '</select>';
}

function equipement_js(){

		document.getElementById('id_equipement_js').innerHTML = '<label for = "equipement">Nom de l équipment : </label><select name = "equipement" id ="equipement"/>' + addEquipement() + '</select>';
}

function addBatiment(){
var nbBatiment = "";

var i = 0;
while(i<requeteReceived.batiment.length){
nbBatiment = nbBatiment + "<option value = " + requeteReceived.batiment[i] + ">" + requeteReceived.batiment[i] + "</option>";
i = i + 1;
}
return nbBatiment;
}

function addEquipement(){
var nbEquipement = "";

var i = 0;
while(i<requeteReceived.equipement.length){
nbEquipement = nbEquipement + "<option value = " + requeteReceived.equipement[i] + ">" + requeteReceived.equipement[i] + "</option>";
i = i + 1;
}
return nbEquipement;
}

window.onbeforeunload = function() {
	ws.close();
};

function sendNewSalle(){

var batiment = document.getElementById("batiment");
batiment = batiment.value;

var nomSalle = document.getElementById("nomSalle");
nomSalle = nomSalle.value;

var equipement = document.getElementById("equipement");
equipement = equipement.value;

var myJSONObject = { "pageWeb" : "newSalle", "type" : "modifBdd", "tableau" : [batiment ,nomSalle ,equipement] };

ws.send(JSON.stringify( myJSONObject));
window.location.reload();
}