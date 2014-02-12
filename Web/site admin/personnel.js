var lienUrl;
var ws = {};
var reponseReceived;
var etudiantReceived;

$(function (){
ws = new ReconnectingWebSocket('ws://localhost:9000/admin');

ws.onopen = function(){
	alert("socket ouverte");
	var batimentSend = {"pageWeb" : "personnel", "type" : "pasFait"};;
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
	
		etudiantReceived = requeteReceived;
		recupPeople();
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

		document.getElementById('id_etudiant_js').innerHTML = '<label for = "etudiants">Nom et prénom de la personne : </label><select name = "etudiants" id ="etudiants"/>' + addEtudiant() + '</select><input type = "button" value ="Valider" onClick = "envoieNom()"/>';
}

function addEtudiant(){
var nbEtudiant = "";


var i = 0;
while(i<reponseReceived.enseignant.length){
nbEtudiant = nbEtudiant + "<option value = " + reponseReceived.enseignant[i].nom + "/" + reponseReceived.enseignant[i].prenom + ">" + reponseReceived.enseignant[i].nom + " " + reponseReceived.enseignant[i].prenom + "</option>";
i = i + 1;
}
return nbEtudiant;
}

function envoieNom(){
var etudiants = document.getElementById("etudiants");
etudiants = etudiants.value;

var test = etudiants.split("/");
var nom = test[0];
var prenom = test[1];

var requeteSend2 = {"pageWeb" : "personnel", "type" : "fait", "tableau" : [nom,prenom]};
ws.send(JSON.stringify(requeteSend2));	

}

function recupPeople(){

		document.getElementById('id_people').innerHTML = 'Identifiant : ' + etudiantReceived.valeur[0];
		document.getElementById('id_badge').innerHTML = 'Identifiant badge : ' + etudiantReceived.valeur[1];
		document.getElementById('promo').innerHTML = 'Promotion : ' + etudiantReceived.valeur[2];
		document.getElementById('option').innerHTML = 'Option : ' + etudiantReceived.valeur[3];
		document.getElementById('specialite').innerHTML = 'Spécialité : ' + etudiantReceived.valeur[4];
		document.getElementById('mail').innerHTML = 'E-mail : ' + etudiantReceived.valeur[5];
		document.getElementById('admin').innerHTML = 'Admin : ' + etudiantReceived.valeur[6]
}

