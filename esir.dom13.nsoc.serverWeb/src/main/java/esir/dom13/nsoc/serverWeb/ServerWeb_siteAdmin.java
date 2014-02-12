package esir.dom13.nsoc.serverWeb;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 05/02/14
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 */


import esir.dom13.nsoc.adminDatabaseBuilding.IadminDatabaseBuilding;
import esir.dom13.nsoc.adminDatabaseEquipment.IadminDatabaseEquipment;
import esir.dom13.nsoc.adminDatabaseOption.IadminDatabaseOption;
import esir.dom13.nsoc.adminDatabasePeople.IadminDatabasePeople;
import esir.dom13.nsoc.adminDatabasePromo.IadminDatabasePromo;
import esir.dom13.nsoc.adminDatabaseRoom.IadminDatabaseRoom;
import esir.dom13.nsoc.databaseHistory.IDatabaseHistory;
import esir.dom13.nsoc.handler.HandlerWebSocket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;
import org.kevoree.log.Log;
import org.webbitserver.WebServer;
import org.webbitserver.WebServers;
import org.webbitserver.WebSocketConnection;
import org.webbitserver.WebSocketHandler;
import org.webbitserver.handler.StaticFileHandler;


/**
 * Created by Clement on 20/01/14.
 */


@Library(name = "NSOC2013")

@Provides({
        @ProvidedPort(name = "recupIdBadge", type = PortType.MESSAGE)
})

@Requires({
        @RequiredPort(name = "fakeConsole", type = PortType.MESSAGE),
        @RequiredPort(name = "setDatabaseEquipment", type =PortType.SERVICE, className = IadminDatabaseEquipment.class),
        @RequiredPort(name = "setDatabasePeople", type =PortType.SERVICE, className = IadminDatabasePeople.class),
        @RequiredPort(name = "setDatabaseBuilding", type =PortType.SERVICE, className = IadminDatabaseBuilding.class),
        @RequiredPort(name = "setDatabaseOption", type =PortType.SERVICE, className = IadminDatabaseOption.class),
        @RequiredPort(name = "setDatabasePromo", type =PortType.SERVICE, className = IadminDatabasePromo.class),
        @RequiredPort(name = "setDatabaseRoom", type =PortType.SERVICE, className = IadminDatabaseRoom.class),
        @RequiredPort(name = "putEntry", type =PortType.SERVICE, className = IDatabaseHistory.class),
})

@DictionaryType({
        @DictionaryAttribute(name = "port", defaultValue = "9000", optional = false)
})
@ComponentType
public class ServerWeb_siteAdmin extends AbstractComponentType implements WebSocketHandler {

    private WebServer webServer;
    private HandlerWebSocket handlerWebSocket;
    private Peers peers;
    private int port;

    @Start
    public void start() {

        createWebServer();
        webServer.start();
        Log.info("Server running at " + webServer.getUri());

    }

    @Stop
    public void stop() {
        webServer.stop();
    }


    @Update
    public void update() {
        webServer.stop();
        createWebServer();
        webServer.start();
        Log.info("UPDATE :: Server running at " + webServer.getUri());
    }


    private void createWebServer() {
        port = Integer.parseInt(getDictionary().get("port").toString());
        webServer = WebServers.createWebServer(port);
        webServer.add("/admin", this)
                .add(new StaticFileHandler("/web"));
    }

    public void onOpen(WebSocketConnection connection) {
        //connection.send("Other connection :: "+connexion );
        peers.addPeer(connection);
        Log.info("START SOCKET  :  ");
    }

    public void onClose(WebSocketConnection connection) {
        peers.removePeer(connection);
        Log.info("STOP SOCKET  :  ");
    }

    public void onMessage(WebSocketConnection connection, String message) throws JSONException {
        Log.debug("Message from peer " + message);


        /*
        message reçu par le serveur doit être de la forme:
        {"pageWeb" : "nom_de_la_page_web" , "type" : ("appelBdd" ou "modifBdd"), tableau : ["valeur_a_transmettre"]}

        message a transmettre au client selon la page web
            */

        String result = null;
        JSONArray jsonArray = null;
        JSONObject jsonObject = new JSONObject(message);
        String pageWeb = jsonObject.getString("pageWeb");
        String type = jsonObject.getString("type");

        if(pageWeb.equals("newEtudiant")) {

             if(type.equals("appelBdd")){

                 String optionChoisi = jsonObject.getString("optionChoisi");

                 if(optionChoisi.equals("pasFait")){

                     String nameOption = getPortByName("setDatabaseOption", IadminDatabaseOption.class).getOption();
                     String namePromo = getPortByName("setDatabasePromo", IadminDatabasePromo.class).getPromo();

                     JSONArray JSONoption = new JSONArray(nameOption);
                     JSONArray JSONpromo = new JSONArray(namePromo);
                     JSONObject reponse = new JSONObject();
                     reponse.put("type" , "pasFait");
                     reponse.put("option",JSONoption);
                     reponse.put("promo", JSONpromo);
                     connection.send(reponse.toString());
                 }

                 else if(optionChoisi.equals("fait")){

                     jsonArray = jsonObject.getJSONArray("tableau");
                     String nameOption = jsonArray.getString(0);
                     String nameSpecialite = getPortByName("setDatabaseOption", IadminDatabaseOption.class).getSpecialite(nameOption);
                     JSONArray JSONspecialite = new JSONArray(nameSpecialite);
                     JSONObject reponse = new JSONObject();
                     reponse.put("type" , "fait");
                     reponse.put("specialite",JSONspecialite);
                     connection.send(reponse.toString());
                 }
             }

            else if(type.equals("modifBdd")) {

                jsonArray = jsonObject.getJSONArray("tableau");//voir si çà marche envoie du client = { "pageWeb" : "newEtudiant", "type" : "modifBdd", tableau : [nom ,prenom ,id_etudiant ,id_badge ,promo ,option ,specialite ,mail ,admin]};
                String nom = jsonArray.getString(0);
                String prenom = jsonArray.getString(1);
                String id_people = jsonArray.getString(2);
                String id_badge = jsonArray.getString(3);
                String promo = jsonArray.getString(4);
                String option = jsonArray.getString(5);
                String specialite = jsonArray.getString(6);
                String mail = jsonArray.getString(7);
                String admin = jsonArray.getString(8);

                getPortByName("setDatabasePeople", IadminDatabasePeople.class).addPeople(nom, prenom, id_people, id_badge, promo, option, specialite, mail, "Etudiant", admin);
            }
        }
        else if(pageWeb.equals("setEtudiant")){

            if(type.equals("appelBdd")){

                String optionChoisi = jsonObject.getString("optionChoisi");

                if(optionChoisi.equals("pasFait")){

                    String nom = getPortByName("setDatabasePeople", IadminDatabasePeople.class).getFirstname("Etudiant");
                    String prenom = getPortByName("setDatabasePeople", IadminDatabasePeople.class).getSurname("Etudiant");

                    JSONArray JSONnom = new JSONArray(nom);
                    JSONArray JSONprenom = new JSONArray(prenom);
                    JSONArray tableau = new JSONArray();
                    JSONObject valeur = null;

                    for(int i = 0; i< JSONnom.length(); i++){   //pour mettre au format : {etudiant : [{"nom" : nomValeur1, "prenom" : prenomValeur1},{"nom" : nomValeur2, "prenom" : prenomValeur2} etc...]}
                        valeur = new JSONObject();
                        valeur.put("nom",JSONnom.getString(i));
                        valeur.put("prenom", JSONprenom.getString(i));
                        tableau.put(valeur);
                    }

                    String nameOption = getPortByName("setDatabaseOption", IadminDatabaseOption.class).getOption();
                    String namePromo = getPortByName("setDatabasePromo", IadminDatabasePromo.class).getPromo();

                    JSONArray JSONoption = new JSONArray(nameOption);
                    JSONArray JSONpromo = new JSONArray(namePromo);
                    JSONObject reponse = new JSONObject();
                    reponse.put("type" , "pasFait");
                    reponse.put("etudiant", tableau);
                    reponse.put("option",JSONoption);
                    reponse.put("promo", JSONpromo);
                    connection.send(reponse.toString());
                }

                else if(optionChoisi.equals("fait")){

                    jsonArray = jsonObject.getJSONArray("tableau");
                    String nameOption = jsonArray.getString(0);
                    String nameSpecialite = getPortByName("setDatabaseOption", IadminDatabaseOption.class).getSpecialite(nameOption);
                    JSONArray JSONspecialite = new JSONArray(nameSpecialite);
                    JSONObject reponse = new JSONObject();
                    reponse.put("type" , "fait");
                    reponse.put("specialite",JSONspecialite);
                    connection.send(reponse.toString());
                }
            }

            else if(type.equals("modifBdd")) {

                jsonArray = jsonObject.getJSONArray("tableau");
                String nom = jsonArray.getString(0);
                String prenom = jsonArray.getString(1);
                String valeur;
                for(int i = 2; i<jsonArray.length(); i++){

                    valeur = jsonArray.getString(i);

                    if(!valeur.equals("null")){

                    String modifier = jsonObject.getString("modifier");

                    if(modifier.equals("1")){
                        Log.debug("modif : 1 " + valeur + " " + prenom + " " + nom);
                        getPortByName("setDatabasePeople", IadminDatabasePeople.class).setId_people(valeur,nom,prenom);
                    }
                    else if(modifier.equals("2")){
                        Log.debug("modif : 2 " + valeur + " " + prenom + " " + nom);
                        getPortByName("setDatabasePeople", IadminDatabasePeople.class).setId_badge(valeur,nom,prenom);
                    }
                    else if(modifier.equals("3")){
                        Log.debug("modif : 3 " + valeur + " " + prenom + " " + nom);
                        getPortByName("setDatabasePeople", IadminDatabasePeople.class).setPromo(valeur,nom,prenom);
                    }
                    else if(modifier.equals("4")){
                        Log.debug("modif : 4 " + valeur + " " + prenom + " " + nom);
                        getPortByName("setDatabasePeople", IadminDatabasePeople.class).setOptions(valeur,nom,prenom);
                    }
                    else if(modifier.equals("5")){
                        Log.debug("modif : 5 " + valeur + " " + prenom + " " + nom);
                        getPortByName("setDatabasePeople", IadminDatabasePeople.class).setSpecialite(valeur,nom,prenom);
                    }
                    else if(modifier.equals("6")){
                        Log.debug("modif : 6 " + valeur + " " + prenom + " " + nom);
                        getPortByName("setDatabasePeople", IadminDatabasePeople.class).setEmailAddress(valeur,nom,prenom);
                    }
                    else if(modifier.equals("7")){
                        Log.debug("modif : 7 " + valeur + " " + prenom + " " + nom);
                        getPortByName("setDatabasePeople", IadminDatabasePeople.class).setAdmin(valeur,nom,prenom);
                    }
                    }

                }

            }
        }
        else if(pageWeb.equals("deleteEtudiant")){

            if(type.equals("appelBdd")){

                String nom = getPortByName("setDatabasePeople", IadminDatabasePeople.class).getSurname("Etudiant");
                String prenom = getPortByName("setDatabasePeople", IadminDatabasePeople.class).getFirstname("Etudiant");

                JSONArray JSONnom = new JSONArray(nom);
                JSONArray JSONprenom = new JSONArray(prenom);
                JSONArray tableau = new JSONArray();
                JSONObject valeur = null;

                for(int i = 0; i< JSONnom.length(); i++){   //pour mettre au format : {etudiant : [{"nom" : nomValeur1, "prenom" : prenomValeur1},{"nom" : nomValeur2, "prenom" : prenomValeur2} etc...]}
                    valeur = new JSONObject();
                    valeur.put("nom",JSONnom.getString(i));
                    valeur.put("prenom", JSONprenom.getString(i));
                    tableau.put(valeur);
                }
                JSONObject reponse = new JSONObject();
                reponse.put("etudiant", tableau);
                connection.send(reponse.toString());
            }

            else if(type.equals("modifBdd")) {

                jsonArray = jsonObject.getJSONArray("tableau");
                String nom = jsonArray.getString(0);
                String prenom = jsonArray.getString(1);
                getPortByName("setDatabasePeople", IadminDatabasePeople.class).deletePeople(prenom,nom);
            }
        }
        else if(pageWeb.equals("newPersonnel")){

            if(type.equals("appelBdd")){

            }

            else if(type.equals("modifBdd")) {

                jsonArray = jsonObject.getJSONArray("tableau");
                String nom = jsonArray.getString(0);
                String prenom = jsonArray.getString(1);
                String id_people = jsonArray.getString(2);
                String id_badge = jsonArray.getString(3);
                String mail = jsonArray.getString(4);
                String admin = jsonArray.getString(5);

                getPortByName("setDatabasePeople", IadminDatabasePeople.class).addPeople(nom, prenom, id_people, id_badge, "", "", "", mail, "Personnel", admin);
            }
        }
        else if(pageWeb.equals("setPersonnel")){

            if(type.equals("appelBdd")){

                String nom = getPortByName("setDatabasePeople", IadminDatabasePeople.class).getFirstname("Personnel");
                String prenom = getPortByName("setDatabasePeople", IadminDatabasePeople.class).getSurname("Personnel");

                JSONArray JSONnom = new JSONArray(nom);
                JSONArray JSONprenom = new JSONArray(prenom);
                JSONArray tableau = new JSONArray();
                JSONObject valeur = null;

                for(int i = 0; i< JSONnom.length(); i++){   //pour mettre au format : {etudiant : [{"nom" : nomValeur1, "prenom" : prenomValeur1},{"nom" : nomValeur2, "prenom" : prenomValeur2} etc...]}
                    valeur = new JSONObject();
                    valeur.put("nom",JSONnom.getString(i));
                    valeur.put("prenom", JSONprenom.getString(i));
                    tableau.put(valeur);
                }

                JSONObject reponse = new JSONObject();
                reponse.put("personnel", tableau);
                connection.send(reponse.toString());

            }

            else if(type.equals("modifBdd")) {

                jsonArray = jsonObject.getJSONArray("tableau");
                String nom = jsonArray.getString(0);
                String prenom = jsonArray.getString(1);
                String valeur;
                for(int i = 2; i<jsonArray.length(); i++){

                    valeur = jsonArray.getString(i);
                    if(!valeur.equals("null")){

                    String modifier = jsonObject.getString("modifier");
                    if(modifier.equals("1")){
                        getPortByName("setDatabasePeople", IadminDatabasePeople.class).setId_people(valeur,prenom,nom);
                    }
                    else if(modifier.equals("2")){
                        getPortByName("setDatabasePeople", IadminDatabasePeople.class).setId_badge(valeur,prenom,nom);
                    }
                    else if(modifier.equals("3")){
                        getPortByName("setDatabasePeople", IadminDatabasePeople.class).setEmailAddress(valeur,prenom,nom);
                    }
                    else if(modifier.equals("4")){
                        getPortByName("setDatabasePeople", IadminDatabasePeople.class).setAdmin(valeur,prenom,nom);
                    }

                    }
                }
            }
        }
        else if(pageWeb.equals("deletePersonnel")){

            if(type.equals("appelBdd")){

                String nom = getPortByName("setDatabasePeople", IadminDatabasePeople.class).getSurname("Personnel");
                String prenom = getPortByName("setDatabasePeople", IadminDatabasePeople.class).getFirstname("Personnel");

                JSONArray JSONnom = new JSONArray(nom);
                JSONArray JSONprenom = new JSONArray(prenom);
                JSONArray tableau = new JSONArray();
                JSONObject valeur = null;

                for(int i = 0; i< JSONnom.length(); i++){   //pour mettre au format : {etudiant : [{"nom" : nomValeur1, "prenom" : prenomValeur1},{"nom" : nomValeur2, "prenom" : prenomValeur2} etc...]}
                    valeur = new JSONObject();
                    valeur.put("nom",JSONnom.getString(i));
                    valeur.put("prenom", JSONprenom.getString(i));
                    tableau.put(valeur);
                }
                JSONObject reponse = new JSONObject();
                reponse.put("personnel", tableau);
                connection.send(reponse.toString());
            }
            else if(type.equals("modifBdd")) {

                jsonArray = jsonObject.getJSONArray("tableau");
                String nom = jsonArray.getString(0);
                String prenom = jsonArray.getString(1);

                getPortByName("setDatabasePeople", IadminDatabasePeople.class).deletePeople(prenom,nom);
            }
        }
        else if(pageWeb.equals("newEnseignant")){

            if(type.equals("appelBdd")){

            }

            else if(type.equals("modifBdd")) {

                jsonArray = jsonObject.getJSONArray("tableau");
                String nom = jsonArray.getString(0);
                String prenom = jsonArray.getString(1);
                String id_people = jsonArray.getString(2);
                String id_badge = jsonArray.getString(3);
                String mail = jsonArray.getString(4);
                String admin = jsonArray.getString(5);

                getPortByName("setDatabasePeople", IadminDatabasePeople.class).addPeople(nom, prenom, id_people, id_badge, "", "", "", mail, "Enseignant", admin);
            }
        }
        else if(pageWeb.equals("setEnseignant")){

            if(type.equals("appelBdd")){

                String nom = getPortByName("setDatabasePeople", IadminDatabasePeople.class).getFirstname("Enseignant");
                String prenom = getPortByName("setDatabasePeople", IadminDatabasePeople.class).getSurname("Enseignant");

                JSONArray JSONnom = new JSONArray(nom);
                JSONArray JSONprenom = new JSONArray(prenom);
                JSONArray tableau = new JSONArray();
                JSONObject valeur = null;

                for(int i = 0; i< JSONnom.length(); i++){   //pour mettre au format : {etudiant : [{"nom" : nomValeur1, "prenom" : prenomValeur1},{"nom" : nomValeur2, "prenom" : prenomValeur2} etc...]}
                    valeur = new JSONObject();
                    valeur.put("nom",JSONnom.getString(i));
                    valeur.put("prenom", JSONprenom.getString(i));
                    tableau.put(valeur);
                }

                JSONObject reponse = new JSONObject();
                reponse.put("enseignant", tableau);
                connection.send(reponse.toString());

            }
            else if(type.equals("modifBdd")) {

                jsonArray = jsonObject.getJSONArray("tableau");
                String nom = jsonArray.getString(0);
                String prenom = jsonArray.getString(1);
                String valeur;
                for(int i = 2; i<jsonArray.length(); i++){

                    valeur = jsonArray.getString(i);
                    if(!valeur.equals("null")){

                    String modifier = jsonObject.getString("modifier");

                    if(modifier.equals("1")){
                       getPortByName("setDatabasePeople", IadminDatabasePeople.class).setId_people(valeur,prenom,nom);
                    }
                    else if(modifier.equals("2")){
                       getPortByName("setDatabasePeople", IadminDatabasePeople.class).setId_badge(valeur,prenom,nom);
                    }
                    else if(modifier.equals("3")){
                        getPortByName("setDatabasePeople", IadminDatabasePeople.class).setEmailAddress(valeur,prenom,nom);
                    }
                    else if(modifier.equals("4")){
                        getPortByName("setDatabasePeople", IadminDatabasePeople.class).setAdmin(valeur,prenom,nom);
                    }
                    }
                }
            }
        }
        else if(pageWeb.equals("deleteEnseignant")){
            if(type.equals("appelBdd")){

                String nom = getPortByName("setDatabasePeople", IadminDatabasePeople.class).getSurname("Enseignant");
                String prenom = getPortByName("setDatabasePeople", IadminDatabasePeople.class).getFirstname("Enseignant");

                JSONArray JSONnom = new JSONArray(nom);
                JSONArray JSONprenom = new JSONArray(prenom);
                JSONArray tableau = new JSONArray();
                JSONObject valeur = null;

                for(int i = 0; i< JSONnom.length(); i++){   //pour mettre au format : {etudiant : [{"nom" : nomValeur1, "prenom" : prenomValeur1},{"nom" : nomValeur2, "prenom" : prenomValeur2} etc...]}
                    valeur = new JSONObject();
                    valeur.put("nom",JSONnom.getString(i));
                    valeur.put("prenom", JSONprenom.getString(i));
                    tableau.put(valeur);
                }
                JSONObject reponse = new JSONObject();
                reponse.put("enseignant", tableau);
                connection.send(reponse.toString());
            }

            else if(type.equals("modifBdd")) {

                jsonArray = jsonObject.getJSONArray("tableau");
                String nom = jsonArray.getString(0);
                String prenom = jsonArray.getString(1);
                getPortByName("setDatabasePeople", IadminDatabasePeople.class).deletePeople(prenom,nom);
            }
        }
        else if(pageWeb.equals("newBatiment")){
            if(type.equals("appelBdd")){

            }
            else if(type.equals("modifBdd")) {

                jsonArray = jsonObject.getJSONArray("tableau");
                String nom = jsonArray.getString(0);
                String id_building = jsonArray.getString(1);
                String nbSalle = jsonArray.getString(2);

               getPortByName("setDatabaseBuilding", IadminDatabaseBuilding.class).addBuilding(id_building, nom, nbSalle);
            }
        }
        else if(pageWeb.equals("setBatiment")){
            if(type.equals("appelBdd")){

                String nomBatiment = getPortByName("setDatabaseBuilding", IadminDatabaseBuilding.class).getNameBuilding();
                JSONArray JSONnom = new JSONArray(nomBatiment);
                JSONObject reponse = new JSONObject();
                reponse.put("batiment", JSONnom);
                connection.send(reponse.toString());

            }
            else if(type.equals("modifBdd")) {

                jsonArray = jsonObject.getJSONArray("tableau");
                String nameBatiment = jsonArray.getString(0);
                String valeur;
                for(int i = 1; i<jsonArray.length(); i++){

                    valeur = jsonArray.getString(i);
                    if(!valeur.equals("null")){
                    String modifier = jsonObject.getString("modifier");

                    if(modifier.equals("1")){
                             getPortByName("setDatabaseBuilding", IadminDatabaseBuilding.class).setId_building(nameBatiment,valeur); break;
                    }
                    else if(modifier.equals("2")){
                             getPortByName("setDatabaseBuilding", IadminDatabaseBuilding.class).setNumberOfRoom(nameBatiment, valeur); break;

                    }
                    }

                }
            }
        }
        else if(pageWeb.equals("deleteBatiment")){

            if(type.equals("appelBdd")){

                String nomBatiment = getPortByName("setDatabaseBuilding", IadminDatabaseBuilding.class).getNameBuilding();
                JSONArray JSONnom = new JSONArray(nomBatiment);
                JSONObject reponse = new JSONObject();
                reponse.put("batiment", JSONnom);
                connection.send(reponse.toString());
            }
            else if(type.equals("modifBdd")) {

                jsonArray = jsonObject.getJSONArray("tableau");
                String nameBatiment = jsonArray.getString(0);

                getPortByName("setDatabaseBuilding", IadminDatabaseBuilding.class).deleteBuilding(nameBatiment);
            }
        }

        else if(pageWeb.equals("newSalle")){
            if(type.equals("appelBdd")){

                String nomEquipement = getPortByName("setDatabaseEquipment", IadminDatabaseEquipment.class).getNameEquipment();
                String nomBatiment = getPortByName("setDatabaseBuilding", IadminDatabaseBuilding.class).getNameBuilding();
                JSONArray JSONequipement = new JSONArray(nomEquipement);
                JSONArray JSONbatiment = new JSONArray(nomBatiment);
                JSONObject reponse = new JSONObject();
                reponse.put("equipement", JSONequipement);
                reponse.put("batiment", JSONbatiment);
                connection.send(reponse.toString());

            }
            else if(type.equals("modifBdd")) {     //voir avec Yvan si on utilise l'identifiant du batiment ou son nom (mieux nom)

                jsonArray = jsonObject.getJSONArray("tableau");
                String nameBatiment = jsonArray.getString(0);
                String nameSalle = jsonArray.getString(1);
                String nameEquipement = jsonArray.getString(2);
                String id_building = getPortByName("setDatabaseBuilding", IadminDatabaseBuilding.class).getIdBuilding(nameBatiment) ;
                JSONArray id = new JSONArray(id_building);
                id_building = id.getString(0);
                getPortByName("setDatabaseRoom", IadminDatabaseRoom.class).addRoom(nameSalle, nameBatiment,id_building,nameEquipement);
            }
        }
        else if(pageWeb.equals("setSalle")){
            if(type.equals("appelBdd")){

                String salleParBatiment = jsonObject.getString("salleParBatiment");

                if(salleParBatiment.equals("pasFait")){

                    String nameBatiment = getPortByName("setDatabaseBuilding", IadminDatabaseBuilding.class).getNameBuilding();
                    JSONArray JSONbatiment = new JSONArray(nameBatiment);
                    JSONObject reponse = new JSONObject();
                    reponse.put("type", "pasFait");
                    reponse.put("batiment", JSONbatiment);
                    connection.send(reponse.toString());

                }

                else if(salleParBatiment.equals("salle")){

                    jsonArray = jsonObject.getJSONArray("tableau");
                    String nameBatiment = jsonArray.getString(0);
                    String nameSalle = getPortByName("setDatabaseRoom", IadminDatabaseRoom.class).getName(nameBatiment);
                    JSONArray JSONsalle = new JSONArray(nameSalle);

                    JSONObject reponse = new JSONObject();
                    reponse.put("type", "salle");
                    reponse.put("salle", JSONsalle);
                    connection.send(reponse.toString());
                }
                else if(salleParBatiment.equals("fait")){

                    jsonArray = jsonObject.getJSONArray("tableau");
                    String nameBatiment = jsonArray.getString(0);
                    String nameSalle = jsonArray.getString(1);
                    String nameEquipement = getPortByName("setDatabaseEquipment", IadminDatabaseEquipment.class).getNameEquipment();
                    JSONArray JSONsalle = new JSONArray(nameEquipement);

                    JSONObject reponse = new JSONObject();
                    reponse.put("type", "fait");
                    reponse.put("equipement", JSONsalle);
                    connection.send(reponse.toString());
                }
            }
            else if(type.equals("modifBdd")) {

                jsonArray = jsonObject.getJSONArray("tableau");
                String nameBatiment = jsonArray.getString(0);
                String nameSalle = jsonArray.getString(1);
                String valeur =  jsonArray.getString(2);

                getPortByName("setDatabaseRoom", IadminDatabaseRoom.class).setNameEquipment(nameBatiment,nameSalle,valeur);

            }
        }
        else if(pageWeb.equals("deleteSalle")){

            if(type.equals("appelBdd")){

                String salleParBatiment = jsonObject.getString("salleParBatiment");

                if(salleParBatiment.equals("pasFait")){

                    String nameBatiment = getPortByName("setDatabaseBuilding", IadminDatabaseBuilding.class).getNameBuilding();
                    JSONArray JSONbatiment = new JSONArray(nameBatiment);
                    JSONObject reponse = new JSONObject();
                    reponse.put("type" , "pasFait");
                    reponse.put("batiment", JSONbatiment);
                    connection.send(reponse.toString());

                }

                else if(salleParBatiment.equals("fait")){

                    jsonArray = jsonObject.getJSONArray("tableau");
                    String nameBatiment = jsonArray.getString(0);
                    String nameSalle = getPortByName("setDatabaseRoom", IadminDatabaseRoom.class).getName(nameBatiment);
                    JSONArray JSONsalle = new JSONArray(nameSalle);
                    JSONObject reponse = new JSONObject();
                    reponse.put("type" , "fait");
                    reponse.put("salle", JSONsalle);
                    connection.send(reponse.toString());
                }

            }
            else if(type.equals("modifBdd")) {

                jsonArray = jsonObject.getJSONArray("tableau");
                String nameBatiment = jsonArray.getString(0);
                String nameSalle = jsonArray.getString(1);

                getPortByName("setDatabaseRoom", IadminDatabaseRoom.class).deleteRoom(nameSalle, nameBatiment);
            }
        }
        else if(pageWeb.equals("newEquipement")){
            if(type.equals("appelBdd")){

            }
            else if(type.equals("modifBdd")) {

                jsonArray = jsonObject.getJSONArray("tableau");
                String nameEquipement = jsonArray.getString(0);

                getPortByName("setDatabaseEquipment", IadminDatabaseEquipment.class).addEquipment(nameEquipement);
            }
        }
        else if(pageWeb.equals("deleteEquipement")){
            if(type.equals("appelBdd")){

                String nameEquipement = getPortByName("setDatabaseEquipment", IadminDatabaseEquipment.class) .getNameEquipment();
                JSONArray JSONequipement = new JSONArray(nameEquipement);
                JSONObject reponse = new JSONObject();
                reponse.put("equipement", JSONequipement);
                connection.send(reponse.toString());
            }
            else if(type.equals("modifBdd")) {

                jsonArray = jsonObject.getJSONArray("tableau");
                String nameEquipement = jsonArray.getString(0);

                getPortByName("setDatabaseEquipment", IadminDatabaseEquipment.class).deleteEquipment(nameEquipement);
            }
        }
        else if(pageWeb.equals("specialite")){
            if(type.equals("appelBdd")){

                String optionChoisi = jsonObject.getString("optionChoisi");

                if(optionChoisi.equals("pasFait")){

                    String nameOption = getPortByName("setDatabaseOption", IadminDatabaseOption.class).getOption();
                    JSONArray JSONoption = new JSONArray(nameOption);
                    JSONObject reponse = new JSONObject();
                    reponse.put("type","pasFait");
                    reponse.put("option" , JSONoption);
                    connection.send(reponse.toString());
                }

                else if(optionChoisi.equals("fait")){

                    jsonArray = jsonObject.getJSONArray("tableau");
                    String nameOption = jsonArray.getString(0);
                    String nameSpecialite = getPortByName("setDatabaseOption", IadminDatabaseOption.class).getSpecialite(nameOption);
                    JSONArray JSONspecialite = new JSONArray(nameSpecialite);
                    JSONObject reponse = new JSONObject();
                    reponse.put("type","fait");
                    reponse.put("specialite", JSONspecialite);
                    connection.send(reponse.toString());
                }
            }

            else if(type.equals("modifBdd")) {

                String action = jsonObject.getString("action");

                if(action.equals("add")){
                    jsonArray = jsonObject.getJSONArray("tableau");
                    String nameOption = jsonArray.getString(0);
                    String nameSpecialite = jsonArray.getString(1);
                    getPortByName("setDatabaseOption", IadminDatabaseOption.class).addSpecialite(nameOption,nameSpecialite);
                }

                else if(action.equals("delete")){
                    jsonArray = jsonObject.getJSONArray("tableau");
                    String nameSpecialite = jsonArray.getString(0);
                    getPortByName("setDatabaseOption", IadminDatabaseOption.class).deleteSpecialite(nameSpecialite);

                }

            }
        }
        else if(pageWeb.equals("option")){
            if(type.equals("appelBdd")){

                String nameOption = getPortByName("setDatabaseOption", IadminDatabaseOption.class).getOption();
                JSONArray JSONoption = new JSONArray(nameOption);
                JSONObject reponse = new JSONObject();
                reponse.put("option", JSONoption);
                connection.send(reponse.toString());
            }
            else if(type.equals("modifBdd")) {

                String action = jsonObject.getString("action");

                if(action.equals("add")){
                    jsonArray = jsonObject.getJSONArray("tableau");
                    String nameOption = jsonArray.getString(0);
                    getPortByName("setDatabaseOption", IadminDatabaseOption.class).addOption(nameOption);
                }

                else if(action.equals("delete")){
                    jsonArray = jsonObject.getJSONArray("tableau");
                    String nameOption = jsonArray.getString(0);
                    getPortByName("setDatabaseOption", IadminDatabaseOption.class).deleteOption(nameOption);

                }
            }
        }
        else if(pageWeb.equals("promo")){
            if(type.equals("appelBdd")){

                String namePromo = getPortByName("setDatabasePromo", IadminDatabasePromo.class).getPromo();
                JSONArray JSONpromo = new JSONArray(namePromo);
                JSONObject reponse = new JSONObject();
                reponse.put("promo", JSONpromo);
                connection.send(reponse.toString());
            }
            else if(type.equals("modifBdd")) {

                String action = jsonObject.getString("action");

                if(action.equals("add")){
                    jsonArray = jsonObject.getJSONArray("tableau");
                    String namePromo = jsonArray.getString(0);
                    getPortByName("setDatabasePromo", IadminDatabasePromo.class).addPromo(namePromo);
                }

                else if(action.equals("delete")){
                    jsonArray = jsonObject.getJSONArray("tableau");
                    String namePromo = jsonArray.getString(0);
                    getPortByName("setDatabasePromo", IadminDatabasePromo.class).deletePromo(namePromo);

                }
            }
        }
       else if(pageWeb.equals("batiment")) {

            if(type.equals("pasFait")){

                String nomBatiment = getPortByName("setDatabaseBuilding", IadminDatabaseBuilding.class).getNameBuilding();
                JSONArray JSONnom = new JSONArray(nomBatiment);
                JSONObject reponse = new JSONObject();
                reponse.put("type", "pasFait");
                reponse.put("batiment", JSONnom);
                connection.send(reponse.toString());
            }
            else if(type.equals("fait")) {

                jsonArray = jsonObject.getJSONArray("tableau");
                String nameBuilding = jsonArray.getString(0);
                String buildingValue  =  getPortByName("setDatabaseBuilding", IadminDatabaseBuilding.class).getBuilding(nameBuilding);
                JSONArray building = new JSONArray(buildingValue);
                JSONObject reponse = new JSONObject();
                reponse.put("type", "fait");
                reponse.put("valeur", building);
                connection.send(reponse.toString());
            }
        }
        else if(pageWeb.equals("salle")) {

            if(type.equals("pasFait")){

                        String nameBatiment = getPortByName("setDatabaseBuilding", IadminDatabaseBuilding.class).getNameBuilding();
                        JSONArray JSONbatiment = new JSONArray(nameBatiment);
                        JSONObject reponse = new JSONObject();
                        reponse.put("type", "pasFait");
                        reponse.put("batiment", JSONbatiment);
                        connection.send(reponse.toString());
            }
            else if(type.equals("salle"))   {


                        jsonArray = jsonObject.getJSONArray("tableau");
                        String nameBuilding = jsonArray.getString(0);
                        String nameSalle = getPortByName("setDatabaseRoom", IadminDatabaseRoom.class).getName(nameBuilding);
                        JSONArray JSONsalle = new JSONArray(nameSalle);
                        JSONObject reponse = new JSONObject();
                        reponse.put("type", "salle");
                        reponse.put("salle", JSONsalle);
                        connection.send(reponse.toString());
            }

            else if(type.equals("fait")){

                jsonArray = jsonObject.getJSONArray("tableau");
                String nameSalle = jsonArray.getString(1);
                String nameBuilding = jsonArray.getString(0);
                String salleValue  =  getPortByName("setDatabaseRoom", IadminDatabaseRoom.class).getSalle(nameBuilding,nameSalle);
                JSONArray salle = new JSONArray(salleValue);
                JSONObject reponse = new JSONObject();
                reponse.put("type", "fait");
                reponse.put("valeur", salle);
                connection.send(reponse.toString());
            }
        }
        else if(pageWeb.equals("equipement")){

            String equipementValue  =  getPortByName("setDatabaseEquipment", IadminDatabaseEquipment.class).getNameEquipment();
            JSONArray equipement = new JSONArray(equipementValue);
            JSONObject reponse = new JSONObject();
            reponse.put("equipement", equipement);
            connection.send(reponse.toString());
        }
        else if(pageWeb.equals("etudiant")){


            if(type.equals("pasFait")){

                String prenom  =  getPortByName("setDatabasePeople", IadminDatabasePeople.class).getFirstname("Etudiant");
                String nom  =  getPortByName("setDatabasePeople", IadminDatabasePeople.class).getSurname("Etudiant");
                JSONArray JSONnom = new JSONArray(nom);
                JSONArray JSONprenom = new JSONArray(prenom);
                JSONArray tableau = new JSONArray();
                JSONObject valeur = null;

                for(int i = 0; i< JSONnom.length(); i++){   //pour mettre au format : {etudiant : [{"nom" : nomValeur1, "prenom" : prenomValeur1},{"nom" : nomValeur2, "prenom" : prenomValeur2} etc...]}
                    valeur = new JSONObject();
                    valeur.put("nom",JSONnom.getString(i));
                    valeur.put("prenom", JSONprenom.getString(i));
                    tableau.put(valeur);
                }
                JSONObject reponse = new JSONObject();
                reponse.put("type" , "pasFait");
                reponse.put("etudiant", tableau);
                connection.send(reponse.toString());
            }

            else if(type.equals("fait")){

                jsonArray = jsonObject.getJSONArray("tableau");
                String nom = jsonArray.getString(0);
                String prenom = jsonArray.getString(1);
                String etudiantValue  =  getPortByName("setDatabasePeople", IadminDatabasePeople.class).getPeople(nom,prenom);
                JSONArray etudiant = new JSONArray(etudiantValue);
                JSONObject reponse = new JSONObject();
                reponse.put("type" , "fait");
                reponse.put("valeur", etudiant);
                connection.send(reponse.toString());
            }
        }
        else if(pageWeb.equals("personnel")){


            if(type.equals("pasFait")){

                String prenom  =  getPortByName("setDatabasePeople", IadminDatabasePeople.class).getFirstname("Personnel");
                String nom  =  getPortByName("setDatabasePeople", IadminDatabasePeople.class).getSurname("Personnel");
                JSONArray JSONnom = new JSONArray(nom);
                JSONArray JSONprenom = new JSONArray(prenom);
                JSONArray tableau = new JSONArray();
                JSONObject valeur = null;

                for(int i = 0; i< JSONnom.length(); i++){   //pour mettre au format : {etudiant : [{"nom" : nomValeur1, "prenom" : prenomValeur1},{"nom" : nomValeur2, "prenom" : prenomValeur2} etc...]}
                    valeur = new JSONObject();
                    valeur.put("nom",JSONnom.getString(i));
                    valeur.put("prenom", JSONprenom.getString(i));
                    tableau.put(valeur);
                }
                JSONObject reponse = new JSONObject();
                reponse.put("type", "pasFait");
                reponse.put("enseignant", tableau);
                connection.send(reponse.toString());
            }

            else if(type.equals("fait")){

                jsonArray = jsonObject.getJSONArray("tableau");
                String nom = jsonArray.getString(0);
                String prenom = jsonArray.getString(1);
                String personnelValue  =  getPortByName("setDatabasePeople", IadminDatabasePeople.class).getPeople(nom,prenom);
                JSONArray personnel = new JSONArray(personnelValue);
                JSONObject reponse = new JSONObject();
                reponse.put("type" , "fait");
                reponse.put("valeur", personnel);
                connection.send(reponse.toString());
            }
        }
        else if(pageWeb.equals("enseignant")){

            if(type.equals("pasFait")){

                String prenom  =  getPortByName("setDatabasePeople", IadminDatabasePeople.class).getFirstname("Enseignant");
                String nom  =  getPortByName("setDatabasePeople", IadminDatabasePeople.class).getSurname("Enseignant");
                JSONArray JSONnom = new JSONArray(nom);
                JSONArray JSONprenom = new JSONArray(prenom);
                JSONArray tableau = new JSONArray();
                JSONObject valeur = null;

                for(int i = 0; i< JSONnom.length(); i++){   //pour mettre au format : {etudiant : [{"nom" : nomValeur1, "prenom" : prenomValeur1},{"nom" : nomValeur2, "prenom" : prenomValeur2} etc...]}
                    valeur = new JSONObject();

                    valeur.put("nom",JSONnom.getString(i));
                    valeur.put("prenom", JSONprenom.getString(i));
                    tableau.put(valeur);
                }
                JSONObject reponse = new JSONObject();
                reponse.put("type" , "pasFait");
                reponse.put("enseignant", tableau);
                connection.send(reponse.toString());
            }

            else if(type.equals("fait")){

                jsonArray = jsonObject.getJSONArray("tableau");
                String nom = jsonArray.getString(0);
                String prenom = jsonArray.getString(1);
                String enseignantValue  =  getPortByName("setDatabasePeople", IadminDatabasePeople.class).getPeople(nom,prenom);
                JSONArray enseignant = new JSONArray(enseignantValue);
                JSONObject reponse = new JSONObject();
                reponse.put("type" , "fait");
                reponse.put("valeur", enseignant);
                connection.send(reponse.toString());
            }
        }
        else if(pageWeb.equals("getEtudiant")){

            if(type.equals("pasFait")){

                String prenom  =  getPortByName("setDatabasePeople", IadminDatabasePeople.class).getFirstname("Etudiant");
                String nom  =  getPortByName("setDatabasePeople", IadminDatabasePeople.class).getSurname("Etudiant");
                JSONArray JSONnom = new JSONArray(nom);
                JSONArray JSONprenom = new JSONArray(prenom);
                JSONArray tableau = new JSONArray();
                JSONObject valeur = null;

                for(int i = 0; i< JSONnom.length(); i++){   //pour mettre au format : {etudiant : [{"nom" : nomValeur1, "prenom" : prenomValeur1},{"nom" : nomValeur2, "prenom" : prenomValeur2} etc...]}
                    valeur = new JSONObject();
                    valeur.put("nom",JSONnom.getString(i));
                    valeur.put("prenom", JSONprenom.getString(i));
                    tableau.put(valeur);
                }
                JSONObject reponse = new JSONObject();
                reponse.put("type" , "pasFait");
                reponse.put("people", tableau);
                connection.send(reponse.toString());
                Log.debug("envoie :" + reponse.toString());
            }

            else if(type.equals("fait")){

                jsonArray = jsonObject.getJSONArray("tableau");
                String nom = jsonArray.getString(0);
                String prenom = jsonArray.getString(1);
                String id_people = getPortByName("setDatabasePeople", IadminDatabasePeople.class).getID(nom,prenom);
                JSONArray id = new JSONArray(id_people);
                id_people = id.getString(0);
                String historyValue  =  getPortByName("putEntry", IDatabaseHistory.class).getHistory(id_people);
                JSONArray history = new JSONArray(historyValue);
                JSONObject reponse = new JSONObject();
                reponse.put("type", "fait");
                reponse.put("valeur", history);
                connection.send(reponse.toString());
            }
        }

        getPortByName("fakeConsole", MessagePort.class).process(message);
    }

    @Override
    public void onMessage(WebSocketConnection connection, byte[] msg) throws Throwable {
        Log.debug("Message from peer " + msg);

    }

    @Override
    public void onPing(WebSocketConnection connection, byte[] msg) throws Throwable {
        Log.info("START SOCKET  :  ");
    }

    @Override
    public void onPong(WebSocketConnection connection, byte[] msg) throws Throwable {
        Log.info("STOP SOCKET  :  ");
    }

    @Port(name = "recupIdBadge")
    public void recupIdBadge(Object object){

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type","badge");
            jsonObject.put("badge",object.toString());
            peers.broadcast(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}

