var lienUrl;
var ws = {};
var reponseReceived;
var numeroBadge = null;

$(function (){
ws = new ReconnectingWebSocket('ws://localhost:9000/admin');

ws.onopen = function(){
	alert("socket ouverte");
	var batimentSend = {"pageWeb" : "setPersonnel", "type" : "appelBdd"};
	ws.send(JSON.stringify(batimentSend));	
};

ws.onmessage = function(message){
	requeteReceived = message.data;

	requeteReceived = JSON.parse(requeteReceived);
	reponseReceived = requeteReceived;
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

		document.getElementById('id_personnel_js').innerHTML = '<label for = "personnels">Nom et prénom de la personnel : </label><select name = "personnels" id ="personnels"/>' + addPersonnel() + '</select>';
}

function idPersonnel_js(){

		document.getElementById('building').innerHTML = '<p><div>Veuillez donner l identifiant de remplacement</div><br /><label for = "id_per">ID : <input type = "text" name = "id_per" id ="id_per" maxlength = "8"/><br /><br /><input type="button" value="Envoyer" onClick = "sendSetPersonnel()"/>';
		numeroBadge = null;
}

function idBadge_js(){

		document.getElementById('building').innerHTML = '<p><div>Passer le badge avant de cliquer sur ok</div><br /><label for = "id_badge">Identifiant badge : ' + numeroBadge + '<input type="button" value ="ok" onClick = "recupIdBadge()"<br /><br /><input type="button" value="Envoyer" onClick = "sendSetPersonnel()"/>';
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

		document.getElementById('building').innerHTML = '<p><div>Veuillez donner le nouvel email</div><br /><label for = "mail">E-mail : </label><input type = "email" name = "mail" id ="mail"/><br /><br /><input type="button" value="Envoyer" onClick = "sendSetPersonnel()"/>';
		numeroBadge = null;
}

function admin_js(){

		document.getElementById('building').innerHTML = '<p><div>Changer son statut d administrateur</div><br /><label for = "admin">Administrateur : </label><select name = "admin" id ="admin"/><option value = "NON">NON</option><option value = "OUI">OUI</option></select><br /><br /><input type="button" value="Envoyer" onClick = "sendSetPersonnel()"/>';
		numeroBadge = null;
}

function addPersonnel(){
var nbPersonnel = "";


var i = 0;
while(i<reponseReceived.personnel.length){
nbPersonnel = nbPersonnel + "<option value = " + reponseReceived.personnel[i].nom + "/" + reponseReceived.personnel[i].prenom + ">" + reponseReceived.personnel[i].nom + " " + reponseReceived.personnel[i].prenom + "</option>";
i = i + 1;
}
return nbPersonnel;
}

window.onbeforeunload = function() {
	ws.close();
};

function sendSetPersonnel(){

var id_per = null;
var mail = null;
var admin = null;
var nb = "2";

var personnels = document.getElementById("personnels");
personnels = personnels.value;

var test = personnels.split("/");
var nom = test[0];
var prenom = test[1];


if(document.getElementById("id_per") != null){

id_per = document.getElementById("id_per");
id_per = id_per.value;
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

var myJSONObject = {"pageWeb" : "setPersonnel", "type" : "modifBdd", "modifier":nb, "tableau" :[nom, prenom, id_per, numeroBadge, mail, admin]};

ws.send(JSON.stringify( myJSONObject));
window.location.reload();
}