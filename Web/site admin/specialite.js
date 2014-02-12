var lienUrl;
var socket = null;
var requeteReceived;
var specialiteReceived;

$(function (){
ws = new ReconnectingWebSocket('ws://localhost:9000/admin');

ws.onopen = function(){
	alert("socket ouverte");
	var batimentSend = {"pageWeb" : "specialite", "type" : "appelBdd", "optionChoisi" : "pasFait"};
	ws.send(JSON.stringify(batimentSend));	
};

ws.onmessage = function(message){
	requeteReceived = message.data;
	requeteReceived = JSON.parse(requeteReceived);
	
	if(requeteReceived.type == "pasFait"){
	
	option_js();
	}
	else if(requeteReceived.type == "fait"){
	
	specialiteReceived = requeteReceived;
	delete_js();
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

function option_js(){

		document.getElementById('id_option_js').innerHTML = '<label for = "options">Options : </label><select name = "options" id ="options"/>' + addOption() + '</select>';
}

function valideOption(){

var optionCochee = document.getElementById("options");
optionCochee = optionCochee.value;

var JSONoptionCochee = {"pageWeb" : "specialite", "type" : "appelBdd","optionChoisi" : "fait", "tableau" : [optionCochee]};
ws.send(JSON.stringify( JSONoptionCochee));
}

function new_js(){

		document.getElementById('building').innerHTML ='<p><div>Veuillez donner le nom de la nouvelle spécialité</div><br /><label for = "new_specialite">Valeur : </label><input type = "text" name = "new_specialite" id ="new_specialite"/><br /><br /><input type="button" value="Envoyer" onClick = "sendSpecialite()"/>';
}

function delete_js(){

		document.getElementById('building').innerHTML ='<p><div>Veuillez choisir la spécialité à supprimer</div><br /><label for = "delete_specialite">Option de l étudiant: </label><select name = "delete_specialite" id ="delete_specialite"/>' + addSpecialite() + '</select><input type = "button" value = "Actualiser specialite" onClick = "valideOption()"/><br /><br /><input type="button" value="Envoyer" onClick = "sendSpecialite()"/>';
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

function addSpecialite(){
var nbSpecialite = "";

var i = 0;
while(i<specialiteReceived.specialite.length){
nbSpecialite = nbSpecialite + "<option value = " + specialiteReceived.specialite[i] + ">" + specialiteReceived.specialite[i] + "</option>";
i = i + 1;
}
return nbSpecialite;
}

function webSpecialite(){

requeteReceived = { "option" : ["RES", "IB", "DOM","LSI"]};

var requeteSend = {"pageWeb" : "specialite", "type" : "appelBdd", "optionChoisi" : "pasFait"};
requeteSend = JSON.stringify(requeteSend);

/*socket = new WebSocket(lienUrl);

socket.onopen = function(){
alert("socket ouverte");
};
socket.send(requeteSend);	
socket.onmessage = function(message){
alert('message reçu : ' + message.data);
requeteReceived = message.data;
requeteReceived = JSON.parse(requeteReceived);*/
option_js();
/*};*/
}

window.onbeforeunload = function() {
	socket.close();
    return "socket fermée!";
};

function sendSpecialite(){

var new_specialite = null;
var delete_specialite = null;

var options = document.getElementById("options");
options = options.value;

if(document.getElementById("new_specialite") != null){
var new_specialite = document.getElementById("new_specialite");
new_specialite = new_specialite.value;
}

if(document.getElementById("delete_specialite") != null){
var delete_specialite = document.getElementById("delete_specialite");
delete_specialite = delete_specialite.value;
}
var myJSONObject = null;

if(new_specialite != null) {
myJSONObject = { "pageWeb" : "specialite", "type" : "modifBdd", "action" : "add" , "tableau" : [options, new_specialite]};
}
else if(delete_specialite != null){
myJSONObject = { "pageWeb" : "specialite", "type" : "modifBdd", "action" : "delete" , "tableau" : [delete_specialite]};
}


ws.send(JSON.stringify(myJSONObject));
window.location.reload();
}

