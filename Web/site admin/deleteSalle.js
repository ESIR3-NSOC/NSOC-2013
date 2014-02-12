var lienUrl;
var ws = {};
var requeteReceived;
var salleReceived;

$(function (){
ws = new ReconnectingWebSocket('ws://localhost:9000/admin');

ws.onopen = function(){
	alert("socket ouverte");
	var batimentSend = {"pageWeb" : "deleteSalle", "type" : "appelBdd", "salleParBatiment" : "pasFait"};
	ws.send(JSON.stringify(batimentSend));	
};

ws.onmessage = function(message){
	requeteReceived = message.data;
	requeteReceived = JSON.parse(requeteReceived);
	
	if(requeteReceived.type == "pasFait"){
		
		batiment_js();
	}
	else if(requeteReceived.type == "fait"){
		
		salleReceived = requeteReceived;
		salle_js();
	}
	
	
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

		document.getElementById('id_batiment_js').innerHTML = '<label for = "batiments">Bâtiments : </label><select name = "batiments" id ="batiments"/>' + addBatiment() + '</select><input type = "button" value = "Valider" onClick = "valideBatiment()"/> Valider pour choisir une salle';
}

function salle_js(){

		document.getElementById('id_salle_js').innerHTML = '<label for = "salles">Nom de la salle à supprimer : </label><select name = "salles" id ="salles"/>' + addSalle() + '</select>';
}

function valideBatiment(){

var batimentCochee = document.getElementById("batiments");
batimentCochee = batimentCochee.value;

var JSONbatimentCochee = {"pageWeb" : "deleteSalle", "type" : "appelBdd","salleParBatiment" : "fait", "tableau" : [batimentCochee]};

ws.send(JSON.stringify( JSONbatimentCochee));
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

function addSalle(){
var nbSalle = "";



var i = 0;
while(i<salleReceived.salle.length){
nbSalle = nbSalle + "<option value = " + salleReceived.salle[i] + ">" + salleReceived.salle[i] + "</option>";
i = i + 1;
}
return nbSalle;
}

window.onbeforeunload = function() {
	ws.close();
};

function sendDeleteSalle(){

var batiments = document.getElementById("batiments");
batiments = batiments.value;

var salles = document.getElementById("salles");
salles = salles.value;


var myJSONObject = { "pageWeb" : "deleteSalle", "type" : "modifBdd", "tableau" : [ batiments ,salles]};
	
		alert(myJSONObject.tableau[0]);
		alert(myJSONObject.tableau[1]);


ws.send(JSON.stringify(myJSONObject));
window.location.reload();
}