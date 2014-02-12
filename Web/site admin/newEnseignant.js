var lienUrl;
var ws = {};
var numeroBadge = "";

$(function (){
ws = new ReconnectingWebSocket('ws://localhost:9000/admin');

ws.onopen = function(){
	alert("socket ouverte");
	
};

ws.onmessage = function(message){
	requeteReceived = message.data;

	requeteReceived = JSON.parse(requeteReceived);
	
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

		/*socket.onmessage = function(message){
		alert('message reçu : ' + message.data);
		numeroBadge = message.data;
		*/
		numeroBadge = "1234567890";
		document.getElementById('id_enseignant_js').innerHTML = numeroBadge;
		/*}*/
}


window.onbeforeunload = function() {
	ws.close();
};

function sendNewEnseignant(){

var nom = document.getElementById("nom");
nom = nom.value;

var prenom = document.getElementById("prenom");
prenom = prenom.value;

var id = document.getElementById("id_enseignant");
id = id.value;

var mail = document.getElementById("mail");
mail = mail.value;

var admin = document.getElementById("admin");
admin = admin.value;
if(admin =="OUI"){
admin = "1";
}
else if(admin == "NON"){
admin = "0";
}

var myJSONObject = { "pageWeb" : "newEnseignant", "type" : "modifBdd", "tableau" : [nom ,prenom ,id ,numeroBadge , mail, admin]};

ws.send(JSON.stringify( myJSONObject));
window.location.reload();
}
