var lienUrl;
var ws = {};
var personnelReceived;

$(function (){
ws = new ReconnectingWebSocket('ws://localhost:9000/admin');

ws.onopen = function(){
	alert("socket ouverte");
	var batimentSend = {"pageWeb" : "deletePersonnel", "type" : "appelBdd"};
	ws.send(JSON.stringify(batimentSend));	
};

ws.onmessage = function(message){
	requeteReceived = message.data;

	requeteReceived = JSON.parse(requeteReceived);
	personnelReceived = requeteReceived;
	personnel_js();
	
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

function personnel_js(){

		document.getElementById('id_personnel_js').innerHTML = '<label for = "personnels">Nom et prénom de la personne : </label><select name = "personnels" id ="personnels"/>' + addPersonnel() + '</select>';
}

function addPersonnel(){
var nbPersonnel = "";


var i = 0;
while(i<personnelReceived.personnel.length){
nbPersonnel = nbPersonnel + "<option value = " + personnelReceived.personnel[i].nom + "/" + personnelReceived.personnel[i].prenom + ">" + personnelReceived.personnel[i].nom + " " + personnelReceived.personnel[i].prenom + "</option>";
i = i + 1;
}
return nbPersonnel;
}

window.onbeforeunload = function() {
	ws.close();
}


function sendDeletePersonnel(){

var personnels = document.getElementById("personnels");
personnels = personnels.value;

var test = personnels.split("/");
var nom = test[0];
var prenom = test[1];

var myJSONObject = { "pageWeb" : "deletePersonnel", "type" : "modifBdd", "tableau" :[nom, prenom]};

ws.send(JSON.stringify( myJSONObject));
window.location.reload();
}