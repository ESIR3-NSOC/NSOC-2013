var lienUrl;
var ws = {};
var enseignantReceived;

$(function (){
ws = new ReconnectingWebSocket('ws://localhost:9000/admin');

ws.onopen = function(){
	alert("socket ouverte");
	var batimentSend = {"pageWeb" : "deleteEnseignant", "type" : "appelBdd"};
	ws.send(JSON.stringify(batimentSend));	
};

ws.onmessage = function(message){
	requeteReceived = message.data;

	requeteReceived = JSON.parse(requeteReceived);
	enseignantReceived = requeteReceived;
	enseignant_js();
	
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

function enseignant_js(){

		document.getElementById('id_enseignant_js').innerHTML = '<label for = "enseignants">Nom et prénom de l enseignant : </label><select name = "enseignants" id ="enseignants"/>' + addEnseignant() + '</select>';
}

function addEnseignant(){
var nbEnseignant = "";


var i = 0;
while(i<enseignantReceived.enseignant.length){
nbEnseignant = nbEnseignant + "<option value = " + enseignantReceived.enseignant[i].nom + "/" + enseignantReceived.enseignant[i].prenom + ">" + enseignantReceived.enseignant[i].nom + " " + enseignantReceived.enseignant[i].prenom + "</option>";
i = i + 1;
}
return nbEnseignant;
}

window.onbeforeunload = function() {
	ws.close();
};

function sendDeleteEnseignant(){

var enseignants = document.getElementById("enseignants");
enseignants = enseignants.value;

var test = enseignants.split("/");
var nom = test[0];
var prenom = test[1];

var myJSONObject = { "pageWeb" : "deleteEnseignant", "type" : "modifBdd", "tableau" :[nom, prenom]};

ws.send(JSON.stringify(myJSONObject));
window.location.reload();
}

