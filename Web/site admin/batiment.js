var lienUrl;
var ws = {};
var batimentReceived;


$(function (){
ws = new ReconnectingWebSocket('ws://localhost:9000/admin');

ws.onopen = function(){
	alert("socket ouverte");
	var batimentSend = {'pageWeb' : 'batiment', 'type' : 'pasFait'};
	ws.send(JSON.stringify(batimentSend));	
};

ws.onmessage = function(message){
	batimentReceived = message.data;
	batimentReceived = JSON.parse(batimentReceived);

	if(batimentReceived.type == "pasFait"){
		batiment_js();
	}
	else if(batimentReceived.type == "fait"){
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





function batiment_js(){

		document.getElementById('id_batiment_js').innerHTML = '<label for = "batiments">Nom du batiment : </label><select name = "batiments" id ="batiments"/>' + addBatiment() + '</select><input type = "button" value ="Valider" onClick = "envoieNom()"/>';
		
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

function envoieNom(){

var batiments = document.getElementById("batiments");
batiments = batiments.value;
var requeteSend2 = {'pageWeb' : 'batiment', 'type' : 'fait', 'tableau' : [batiments]};
ws.send(JSON.stringify(requeteSend2));	

}

function recupPeople(){

		document.getElementById('id_people').innerHTML = 'Identifiant : ' + batimentReceived.valeur[0];
		document.getElementById('id_badge').innerHTML = 'Nombre de salle : ' + batimentReceived.valeur[1];

}




