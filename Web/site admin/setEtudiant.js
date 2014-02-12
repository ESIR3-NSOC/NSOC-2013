var lienUrl;
var ws = {};
var reponseReceived;
var specialiteReceived;
var numeroBadge = null;

$(function (){
ws = new ReconnectingWebSocket('ws://localhost:9000/admin');

ws.onopen = function(){
	alert("socket ouverte");
	var batimentSend = {"pageWeb" : "setEtudiant", "type" : "appelBdd", "optionChoisi" : "pasFait"};
	ws.send(JSON.stringify(batimentSend));	
};

ws.onmessage = function(message){
	requeteReceived = message.data;

	requeteReceived = JSON.parse(requeteReceived);
	
	
	if(requeteReceived.type == "pasFait"){
	reponseReceived = requeteReceived;
	etudiant_js();
	}
	else if(requeteReceived.type == "fait"){
	specialite_js();
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

function etudiant_js(){

		document.getElementById('id_etudiant_js').innerHTML = '<label for = "etudiants">Nom et prénom de l étudiant : </label><select name = "etudiants" id ="etudiants"/>' + addEtudiant() + '</select>';
}

function idEtudiant_js(){

		document.getElementById('building').innerHTML = '<p><div>Veuillez donner le nouvel identifiant</div><br /><label for = "id_et">ID : </label><input type = "email" name = "id_et" id ="id_et"/><br /><br /><input type="button" value="Envoyer" onClick = "sendSetEtudiant()"/></p>';
		document.getElementById('id_specialite_js').innerHTML ='';
		numeroBadge = null;
}

function idBadge_js(){

		document.getElementById('building').innerHTML = '<p><div>Passer le badge avant de cliquer sur ok</div><br /><label for = "id_badge">Identifiant badge : ' + numeroBadge + '<input type="button" value ="ok" onClick = "recupIdBadge()"<br /><br /><input type="button" value="Envoyer" onClick = "sendSetEtudiant()"/>';
}

function recupIdBadge(){

		/*socket.onmessage = function(message){
		alert('message reçu : ' + message.data);
		numeroBadge = message.data;
		*/
		numeroBadge = "1234567890";
		idBadge_js();
}

function promo_js(){

		document.getElementById('building').innerHTML = '<p><div>Veuillez choisir la promo de remplacement</div><br /><label for = "promos">Promotions : </label><select name = "promos" id ="promos"/>' + addPromo() + '</select><br /><br /><input type="button" value="Envoyer" onClick = "sendSetEtudiant()"/></p>';
		document.getElementById('id_specialite_js').innerHTML ='';
		numeroBadge = null;
}

function option_js(){

		document.getElementById('building').innerHTML = '<p><div>Veuillez choisir l option de remplacement</div><br /><label for = "options">Options : </label><select name = "options" id ="options"/>' + addOption() + '</select><br /><br /><input type="button" value="Envoyer" onClick = "sendSetEtudiant()"/></p>';
		document.getElementById('id_specialite_js').innerHTML ='';
		numeroBadge = null;
}

function choixOption_js(){

		document.getElementById('building').innerHTML = '<p><div>Veuillez choisir l option pour afficher les spécialités correspondantes</div><br /><label for = "optionsChoix">Options : </label><select name = "optionsChoix" id ="optionsChoix"/>' + addOption() + '</select><input type = "button" value = "Valider" onClick = "valideOption()" /></p>';
		numeroBadge = null;
}

function specialite_js(){

		document.getElementById('id_specialite_js').innerHTML = '<p><div>Veuillez choisir la spécialité de remplacement</div><br /><label for = "specialites">Specialites : </label><select name = "specialites" id ="specialites"/>' + addSpecialite() + '</select><br /><br /><input type="button" value="Envoyer" onClick = "sendSetEtudiant()"/></p>';
		numeroBadge = null;
}

function valideOption(){

var optionCochee = document.getElementById("optionsChoix");
optionCochee = optionCochee.value;

var JSONoptionCochee = {"pageWeb" : "setEtudiant", "type" : "appelBdd","optionChoisi" : "fait", "tableau" : [optionCochee]};

ws.send(JSON.stringify( JSONoptionCochee));
}

function email_js(){

		document.getElementById('building').innerHTML = '<p><div>Veuillez donner le nouvel email</div><br /><label for = "mail">E-mail : </label><input type = "email" name = "mail" id ="mail"/><br /><br /><input type="button" value="Envoyer" onClick = "sendSetEtudiant()"/></p>';
		document.getElementById('id_specialite_js').innerHTML ='';
		numeroBadge = null;
}

function admin_js(){

		document.getElementById('building').innerHTML = '<p><div>Changer son statut d administrateur</div><br /><label for = "admin">Administrateur : </label><select name = "admin" id ="admin"/><option value = "NON">NON</option><option value = "OUI">OUI</option></select><br /><br /><input type="button" value="Envoyer" onClick = "sendSetEtudiant()"/>';
		document.getElementById('id_specialite_js').innerHTML ='';
		numeroBadge = null;
}

function addOption(){
var nbOption = "";
var i = 0;
while(i<reponseReceived.option.length){
nbOption = nbOption + "<option value = " + reponseReceived.option[i] + ">" + reponseReceived.option[i] + "</option>";
i = i + 1;
}
return nbOption;
}

function addPromo(){
var nbPromo = "";
var i = 0;
while(i<reponseReceived.promo.length){
nbPromo = nbPromo + "<option value = " + reponseReceived.promo[i] + ">" + reponseReceived.promo[i] + "</option>";
i = i + 1;
}
return nbPromo;
}

function addSpecialite(){
var nbSpecialite = "";

var i = 0;
while(i<specialiteReceived.specialite.length){
nbSpecialite = nbSpecialite + "<option value = " + specialiteReceived.specialite[i] + ">" + specialiteReceived.specialite[i] + "</option>";
i = i + 1;
}
return nbSpecialite;
}

function addEtudiant(){
var nbEtudiant = "";


var i = 0;
while(i<reponseReceived.etudiant.length){
nbEtudiant = nbEtudiant + "<option value = " + reponseReceived.etudiant[i].nom + "/" + reponseReceived.etudiant[i].prenom + ">" + reponseReceived.etudiant[i].nom + " " + reponseReceived.etudiant[i].prenom + "</option>";
i = i + 1;
}
return nbEtudiant;
}

window.onbeforeunload = function() {
	ws.close();
};

function sendSetEtudiant(){

var id_et = null;
var promos = null;
var options = null;
var specialites = null;
var mail = null;
var admin = null;
var nb = "2";

var etudiants = document.getElementById("etudiants");
etudiants = etudiants.value;

var test = etudiants.split("/");
var nom = test[0];
var prenom = test[1];

if(document.getElementById("id_et") != null){
var id_et = document.getElementById("id_et");
id_et = id_et.value;
nb = "1";
}

if(document.getElementById("promos") != null){
var promos = document.getElementById("promos");
promos = promos.value;
nb = "3";
}

if(document.getElementById("options") != null){
var options = document.getElementById("options");
options = options.value;
nb = "4";
}

if(document.getElementById("specialites") != null){
var specialites = document.getElementById("specialites");
specialites = specialites.value;
nb = "5";
}

if(document.getElementById("mail") != null){
var mail = document.getElementById("mail");
mail = mail.value;
nb = "6";
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
nb = "7";
}

var myJSONObject = { "pageWeb" : "setEtudiant", "type" : "modifBdd", "modifier" : nb, "tableau" : [nom , prenom ,id_et, numeroBadge ,promos ,options ,specialites, mail, admin]};

ws.send(JSON.stringify( myJSONObject));
window.location.reload();
}