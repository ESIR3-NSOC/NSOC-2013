var lienUrl;
var ws = {};
var requeteReceived;
var salleReceived;
var batimentCochee;

$(function (){
ws = new ReconnectingWebSocket('ws://localhost:9000/admin');

ws.onopen = function(){
	alert("socket ouverte");
	var batimentSend = {"pageWeb" : "setSalle", "type" : "appelBdd", "salleParBatiment" : "pasFait"};
	ws.send(JSON.stringify(batimentSend));	
};

ws.onmessage = function(message){
	requeteReceived = message.data;
	requeteReceived = JSON.parse(requeteReceived);
	
	if(requeteReceived.type == "pasFait"){
		
		batiment_js();
	}
	else if(requeteReceived.type == "salle"){
		
		salleReceived = requeteReceived;
		nom_js();
	}
	else if(requeteReceived.type == "fait"){

		equipement_js();
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

function nom_js(){

		document.getElementById('id_nom_js').innerHTML = '<div>Veuillez donner le nom de la salle à changer</div><br /><label for = "nom">Nom de la salle : </label><select name = "nom" id ="nom"/>' + addSalle() + '</select>';
}

function valideBatiment(){

batimentCochee = document.getElementById("batiments");
batimentCochee = batimentCochee.value;

var JSONbatimentCochee = {"pageWeb" : "setSalle", "type" : "appelBdd","salleParBatiment" : "salle", "tableau" : [batimentCochee]};

ws.send(JSON.stringify( JSONbatimentCochee));
}

function callEquipement_js(){
salleCochee = document.getElementById("nom");
salleCochee = salleCochee.value;
var JSONsalleCochee = {"pageWeb" : "setSalle", "type" : "appelBdd","salleParBatiment" : "fait", "tableau" : [batimentCochee,salleCochee]};
ws.send(JSON.stringify( JSONsalleCochee));
}

function equipement_js(){

		document.getElementById('building').innerHTML = '<p><div>Veuillez donner le nom de remplacement de l équipement</div><br /><label for = "equipements">Nom de l équipment : </label><select name = "equipements" id ="equipements"/>' + addEquipement() + '</select><br /><br /><input type="button" value="Envoyer" onClick = "sendSetSalle()" />';
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

function sendSetSalle(){

var equipement = null;

var nom = document.getElementById("nom");
nom = nom.value;

var batiment = document.getElementById("batiments");
batiments = batiments.value;

if(document.getElementById("equipements") != null){
var equipement = document.getElementById("equipements");
equipement = equipement.value;
}

var myJSONObject = { "pageWeb" : "setSalle", "type" : "modifBdd", "tableau" : [ batiments ,nom ,equipement]};

ws.send(JSON.stringify(myJSONObject));
window.location.reload();
}