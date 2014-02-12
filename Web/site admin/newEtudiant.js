var lienUrl;
var socket = null;
var requeteReceived;
var numeroBadge = "";

$(function (){
ws = new ReconnectingWebSocket('ws://localhost:9000/admin');

ws.onopen = function(){
	alert("socket ouverte");
	var batimentSend = {"pageWeb" : "newEtudiant", "type" : "appelBdd", "optionChoisi" : "pasFait"};
	ws.send(JSON.stringify(batimentSend));	
};

ws.onmessage = function(message){
	requeteReceived = message.data;
	requeteReceived = JSON.parse(requeteReceived);
	if(requeteReceived.type == "pasFait"){

		promo_js();
		option_js();
	}
	else if(requeteReceived.type == "fait"){
	
		specialite_js();
	}
	else if(requeteReceived.type == "badge"){
	
		enseignant_js();
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

function enseignant_js(){
	
		numeroBadge = requeteReceived.badge;
		document.getElementById('id_enseignant_js').innerHTML = numeroBadge;
}

function promo_js(){

		document.getElementById('id_promo_js').innerHTML = '<label for = "promos">Promotions : </label><select name = "promos" id ="promos"/>' + addPromo() + '</select>';
}

function option_js(){

		document.getElementById('id_option_js').innerHTML = '<label for = "options">Options : </label><select name = "options" id ="options"/>' + addOption() + '</select><input type = "button" value = "Valider" onClick = "valideOption()"/> Valider pour choisir une spécialité';
}

function specialite_js(){

		document.getElementById('id_specialite_js').innerHTML = '<label for = "specialites">Specialites : </label><select name = "specialites" id ="specialites"/>'+ addSpecialite()+'</select>';
}

function valideOption(){

var optionCochee = document.getElementById("options");
optionCochee = optionCochee.value;

var JSONoptionCochee = {"pageWeb" : "newEtudiant", "type" : "appelBdd","optionChoisi" : "fait", "tableau" : [optionCochee]};

ws.send(JSON.stringify( JSONoptionCochee));
}

function addOption(){
var nbOption = "";
var i = 0;
while(i<requeteReceived.option.length){
nbOption = nbOption + "<option value = " + requeteReceived.option[i] + ">" + requeteReceived.option[i] + "</option>";
i = i + 1;
}
return nbOption;
}

function addPromo(){

var nbPromo = "";
var i = 0;
while(i<requeteReceived.promo.length){
nbPromo = nbPromo + "<option value = " + requeteReceived.promo[i] + ">" + requeteReceived.promo[i] + "</option>";
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


window.onbeforeunload = function() {
	ws.close();
};

function sendNewEtudiant(){

	var specialite = "";

	var nom = document.getElementById("nom");
	nom = nom.value;
	
	var prenom = document.getElementById("prenom");
	prenom = prenom.value;
	
	var id_enseignant = document.getElementById("id_enseignant");
	id_enseignant = id_enseignant.value;
	
	var promo = document.getElementById("promos");
	promo = promo.value;
	
	var option = document.getElementById("options");
	option = option.value;
	
	if(document.getElementById("specialites")!= null){
	var specialite = document.getElementById("specialites");
	specialite = specialite.value;
	}
	
	var mail = document.getElementById("mail");
	mail = mail.value;	
	
	var admin = document.getElementById("admin");
	admin = admin.value;

		if(admin == "NON"){
		admin = "0";
		}
		else if(admin == "OUI"){
		admin = "1"	;
		}
	
	var myJSONObject = { "pageWeb" : "newEtudiant", "type" : "modifBdd", "tableau" : [nom , prenom ,id_enseignant, numeroBadge , promo, option, specialite, mail, admin]};
	
		alert(myJSONObject.tableau[0]);
		alert(myJSONObject.tableau[1]);
		alert(myJSONObject.tableau[2]);
		alert(myJSONObject.tableau[3]);
		alert(myJSONObject.tableau[4]);
		alert(myJSONObject.tableau[5]);
		alert(myJSONObject.tableau[6]);
		alert(myJSONObject.tableau[7]);
		alert(myJSONObject.tableau[8]);

		
ws.send(JSON.stringify(myJSONObject));
window.location.reload();
	

}