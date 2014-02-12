var lienUrl;
var socket = null;
var batimentReceived;

$(function (){
ws = new ReconnectingWebSocket('ws://localhost:9000/admin');

ws.onopen = function(){
	alert("socket ouverte");
	var batimentSend = {"pageWeb" : "setBatiment", "type" : "appelBdd"};
	ws.send(JSON.stringify(batimentSend));	
};

ws.onmessage = function(message){
	batimentReceived = message.data;
	batimentReceived = JSON.parse(batimentReceived);
	nom_js();

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

function batiment_js(){

		document.getElementById('building').innerHTML ='<p><div>Veuillez donner le nom de remplacement de l identifiant du bâtiment</div><br /><label for = "batiments">Valeur : </label><input type = "text" name = "batiments" id ="batiments" value="@import.calendar.google.com"/><br /><br /><input type="button" value="Envoyer" onClick = "sendSetBatiment()"/>';
}

function nbRoom_js(){

		document.getElementById('building').innerHTML ='<p><div>Choisissez le nombre de salle</div><br /><label for = "nbRooms">Nombre : </label><input type = "number" name = "nbRooms" id = "nbRooms" min = "1" max= "20"/><br /><br /><input type="button" value="Envoyer" onClick = "sendSetBatiment()"/>';
}

function nom_js(){

		document.getElementById('id_nom_js').innerHTML = '<label for = "nom">Nom du batiment : </label><select name = "nom" id ="nom"/>' + addBatiment() + '</select>';
}

function addBatiment(){
var nbBatiment = "";


var i = 0;
while(i<batimentReceived.batiment.length){
nbBatiment = nbBatiment + "<option value = " + batimentReceived.batiment[i] + ">" + batimentReceived.batiment[i] + "</option>";
i = i + 1;
}
return nbBatiment;
}

window.onbeforeunload = function() {
	socket.close();
    return "socket fermée!";
};

function sendSetBatiment(){

var batiment = null;
var nbRoom = null;
var nb = null;

var nom = document.getElementById("nom");
nom = nom.value;


if(document.getElementById("batiments") != null){
var batiment = document.getElementById("batiments");
batiment = batiment.value;
nb = "1";
}

if(document.getElementById("nbRooms") != null){
var nbRoom = document.getElementById("nbRooms");
nbRoom = nbRoom.value;
nb = "2";
}

var myJSONObject = { "pageWeb" : "setBatiment", "type" : "modifBdd", "modifier" : nb , "tableau" :[nom, batiment, nbRoom]};

alert(myJSONObject.tableau[0]);
alert(myJSONObject.tableau[1]);
alert(myJSONObject.tableau[2]);

ws.send(JSON.stringify( myJSONObject));
window.location.reload();
}

