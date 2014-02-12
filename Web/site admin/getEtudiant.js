var lienUrl;
var ws = {};
var etudiantReceived;
var hisReceived;

$(function (){
ws = new ReconnectingWebSocket('ws://localhost:9000/admin');

ws.onopen = function(){
	alert("socket ouverte");
	var batimentSend = {"pageWeb" : "getEtudiant", "type" : "pasFait"};
	ws.send(JSON.stringify(batimentSend));	
};

ws.onmessage = function(message){
	requeteReceived = message.data;
	alert(requeteReceived);
	requeteReceived = JSON.parse(requeteReceived);
	
	if(requeteReceived.type == "pasFait"){
	
		etudiantReceived = requeteReceived;
		etudiant_js();
	}
	else if(requeteReceived.type == "fait"){
	
		hisReceived = requeteReceived;
		afficherValeur();
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

		document.getElementById('id_etudiant_js').innerHTML = '<label for = "etudiants">Nom et prénom de l étudiant : </label><select name = "etudiants" id ="etudiants"/>' + addEtudiant() + '</select><input type = "button" value = "Valider" onClick = "historique()"/>';
}

function addEtudiant(){
var nbEtudiant = "";

var i = 0;
while(i<etudiantReceived.people.length){
nbEtudiant = nbEtudiant + "<option value = " + etudiantReceived.people[i].nom + "/" + etudiantReceived.people[i].prenom + ">" + etudiantReceived.people[i].nom + " " + etudiantReceived.people[i].prenom + "</option>";
i = i + 1;
}
return nbEtudiant;
}

function historique(){

var etudiants = document.getElementById("etudiants");
etudiants = etudiants.value;

var test = etudiants.split("/");
var nom = test[0];
var prenom = test[1];

	var etudiantHis = {"pageWeb" : "getEtudiant", "type" : "fait", "tableau" : [nom,prenom]};
	ws.send(JSON.stringify(etudiantHis));	

}

function addHistory(){

var nbHistory = "";	
var i = 0;

while(i<hisReceived.valeur.length){
	var jsonArrayParent = hisReceived.valeur[i];
	var j = 0;
	
	while(j<jsonArrayParent.length){
	nbHistory = nbHistory + "<label>" + jsonArrayParent[j] + " &nbsp</label>";
	j= j+1;
	}
	
	nbHistory = nbHistory + "<br/>";
	i = i+1;
}

return nbHistory;
}

function afficherValeur(){

			document.getElementById('id_history_js').innerHTML = '<p>' + addHistory() + '</p>';
}

window.onbeforeunload = function() {
	ws.close();
};

