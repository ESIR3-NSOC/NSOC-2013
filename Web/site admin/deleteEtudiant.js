var lienUrl;
var ws = {};
var etudiantReceived;

$(function (){
ws = new ReconnectingWebSocket('ws://localhost:9000/admin');

ws.onopen = function(){
	alert("socket ouverte");
	var batimentSend = {"pageWeb" : "deleteEtudiant", "type" : "appelBdd"};
	ws.send(JSON.stringify(batimentSend));	
};

ws.onmessage = function(message){
	requeteReceived = message.data;
	requeteReceived = JSON.parse(requeteReceived);
	etudiantReceived = requeteReceived;
	etudiant_js();
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

function etudiant_js(){

		document.getElementById('id_etudiant_js').innerHTML = '<label for = "etudiants">Nom et prénom de l étudiant : </label><select name = "etudiants" id ="etudiants"/>' + addEtudiant() + '</select>';
}

function addEtudiant(){
var nbEtudiant = "";

var i = 0;
while(i<etudiantReceived.etudiant.length){
nbEtudiant = nbEtudiant + "<option value = " + etudiantReceived.etudiant[i].nom + "/" + etudiantReceived.etudiant[i].prenom + ">" + etudiantReceived.etudiant[i].nom + " " + etudiantReceived.etudiant[i].prenom + "</option>";
i = i + 1;
}
return nbEtudiant;
}


window.onbeforeunload = function() {
	ws.close();
};

function sendDeleteEtudiant(){

var etudiants = document.getElementById("etudiants");
etudiants = etudiants.value;

var test = etudiants.split("/");
var nom = test[0];
var prenom = test[1];


var myJSONObject = {"pageWeb" : "deleteEtudiant", "type" : "modifBdd", "tableau" : [nom,prenom]};

ws.send(JSON.stringify( myJSONObject));
window.location.reload();
}