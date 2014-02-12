var lienUrl;
var ws = {};
var reponseReceived;
var numeroBadge;

$(function (){
ws = new ReconnectingWebSocket('ws://localhost:9000/admin');

ws.onopen = function(){
	alert("socket ouverte");
	var batimentSend = {"pageWeb" : "setEnseignant", "type" : "appelBdd"};
	ws.send(JSON.stringify(batimentSend));	
};

ws.onmessage = function(message){
	requeteReceived = message.data;

	requeteReceived = JSON.parse(requeteReceived);
	reponseReceived = requeteReceived;
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

function idEnseignant_js(){

		document.getElementById('building').innerHTML = '<p><div>Veuillez donner l identifiant de remplacement</div><br /><label for = "id_en">ID : <input type = "text" name = "id_en" id ="id_en" maxlength = "8"/><br /><br /><input type="button" value="Envoyer" onClick = "sendSetEnseignant()"/>';
		numeroBadge = null;
}

function idBadge_js(){

		document.getElementById('building').innerHTML = '<p><div>Passer le badge avant de cliquer sur ok</div><br /><label for = "id_badge">Identifiant badge : ' + numeroBadge + '<input type="button" value ="ok" onClick = "recupIdBadge()"<br /><br /><input type="button" value="Envoyer" onClick = "sendSetEnseignant()"/>';
}

function recupIdBadge(){

		/*socket.onmessage = function(message){
		alert('message reçu : ' + message.data);
		numeroBadge = message.data;
		*/
		numeroBadge = "1234567890";
		idBadge_js();
}

function email_js(){

		document.getElementById('building').innerHTML = '<p><div>Veuillez donner le nouvel email</div><br /><label for = "mail">E-mail : </label><input type = "email" name = "mail" id ="mail"/><br /><br /><input type="button" value="Envoyer" onClick = "sendSetEnseignant()"/>';
		numeroBadge = null;
}

function admin_js(){

		document.getElementById('building').innerHTML = '<p><div>Changer son statut d administrateur</div><br /><label for = "admin">Administrateur : </label><select name = "admin" id ="admin"/><option value = "NON">NON</option><option value = "OUI">OUI</option></select><br /><br /><input type="button" value="Envoyer" onClick = "sendSetEnseignant()"/>';
		numeroBadge = null;
}

function addEnseignant(){
var nbEnseignant = "";

var i = 0;
while(i<reponseReceived.enseignant.length){
nbEnseignant = nbEnseignant + "<option value = " + reponseReceived.enseignant[i].nom + "/" + reponseReceived.enseignant[i].prenom + ">" + reponseReceived.enseignant[i].nom + " " + reponseReceived.enseignant[i].prenom + "</option>";
i = i + 1;
}
return nbEnseignant;
}

window.onbeforeunload = function() {
	ws.close();
};

function sendSetEnseignant(){

var id_en = null;
var mail = null;
var admin = null;
var nb = "2";

var enseignants = document.getElementById("enseignants");
enseignants = enseignants.value;

var test = enseignants.split("/");
var nom = test[0];
var prenom = test[1];

if(document.getElementById("id_en") != null){
var id_en = document.getElementById("id_en");
id_en = id_en.value;
nb = "1";
}

if(document.getElementById("mail") != null){
var mail = document.getElementById("mail");
mail = mail.value;
nb = "3";
}

if(document.getElementById("admin") != null){
var admin = document.getElementById("admin");
admin = admin.value;
if(admin =="OUI"){
admin = "1";
}
else if(admin == "NON"){
admin = "0";
}
nb = "4";
}

var myJSONObject = { "pageWeb" : "setEnseignant", "type" : "modifBdd", "modifier" : nb, "tableau" :[nom, prenom, id_en, numeroBadge, mail, admin]};

ws.send(JSON.stringify( myJSONObject));
window.location.reload();
}
