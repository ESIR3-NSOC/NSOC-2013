var lienUrl;
var ws = {};
var optionReceived;

$(function (){
ws = new ReconnectingWebSocket('ws://localhost:9000/admin');

ws.onopen = function(){
	alert("socket ouverte");
	var batimentSend = {"pageWeb" : "option", "type" : "appelBdd"};
	ws.send(JSON.stringify(batimentSend));	
};

ws.onmessage = function(message){
	requeteReceived = message.data;

	requeteReceived = JSON.parse(requeteReceived);
	optionReceived = requeteReceived;
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

function new_js(){

		document.getElementById('building').innerHTML ='<p><div>Veuillez donner le nom de la nouvelle option</div><br /><label for = "new_option">Valeur : </label><input type = "text" name = "new_option" id ="new_option"/><br /><br /><input type="button" value="Envoyer" onClick = "sendOption()"/>';
}

function delete_js(){

		document.getElementById('building').innerHTML ='<p><div>Veuillez choisir l option à supprimer</div><br /><label for = "delete_option">Option de l étudiant: </label><select name = "delete_option" id ="delete_option"/>'+addOption()+'</select><br /><br /><input type="button" value="Envoyer" onClick = "sendOption()"/>';
}

function addOption(){
var nbOption = "";
var i = 0;
while(i<optionReceived.option.length){
nbOption = nbOption + "<option value = " + optionReceived.option[i] + ">" + optionReceived.option[i] + "</option>"
i = i + 1;
}
return nbOption;
}

window.onbeforeunload = function() {
	ws.close();
};

function sendOption(){

var new_option = null;
var delete_option = null;

if(document.getElementById("new_option") != null){
var new_option = document.getElementById("new_option");
new_option = new_option.value;
}

if(document.getElementById("delete_option") != null){
var delete_option = document.getElementById("delete_option");
delete_option = delete_option.value;
}

var myJSONObject = null;

if(new_option != null) {
myJSONObject = { "pageWeb" : "option", "type" : "modifBdd", "action" : "add" , "tableau" : [new_option]};
}
else if(delete_option != null){
myJSONObject = { "pageWeb" : "option", "type" : "modifBdd", "action" : "delete" , "tableau" : [delete_option]};
}

ws.send(JSON.stringify(myJSONObject));
window.location.reload();
}

