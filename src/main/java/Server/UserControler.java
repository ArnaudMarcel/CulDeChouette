/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import DB.JoueurJPA;
import DB.SpeudoAlreadyExistException;
import Data.Joueur;
import Data.Partie;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author darra
 */
public class UserControler {

    private String id;
    private String response;
    private GsonBuilder builder = new GsonBuilder();
    private Gson gson = builder.create();

    public UserControler(String id) {
        this.id = id;
    }

    public UserControler() {
    }

    public UserControler(String id, String response) {
        this.id = id;
        this.response = response;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void creationJoueur(String message, javax.websocket.Session session) throws SpeudoAlreadyExistException {
        JoueurJPA dbJoueur = new JoueurJPA();
        Joueur j = this.gson.fromJson(message, Joueur.class);
        dbJoueur.create(j);
        WebSocket.listeJoueurs.put(j.getPseudoJoueur(), session);
    }

    public void connexion(String message, javax.websocket.Session session) throws IOException {
        Joueur j = this.gson.fromJson(message, Joueur.class);
        JoueurJPA dbJoueur = new JoueurJPA();
        try {
            String tamp[] = message.split("\"");
            String mdp = tamp[tamp.length - 2];
            Joueur realJoueur = dbJoueur.find(j.getPseudoJoueur());
            if (mdp.equals(dbJoueur.find(realJoueur.getPseudoJoueur()).getMotDePasseJoueur())) {
                WebSocket.listeJoueurs.put(realJoueur.getPseudoJoueur(), session);
                this.response = j.getPseudoJoueur();
            }
        } catch (Exception e) {
            this.response = "Connexion joueur failed";
        }

        session.getBasicRemote().sendText(this.response);
    }

    public void creationPartie(String message, javax.websocket.Session session) throws IOException {
        JoueurJPA dbJoueur = new JoueurJPA();
        try {
            String tamp[] = message.split("\"");
            String hote = tamp[tamp.length - 2];
            Partie p = gson.fromJson(message, Partie.class);
            WebSocket.listeParties.put(hote, p);
            WebSocket.pm.add(p, dbJoueur.find(hote));
        } catch (JsonSyntaxException e) {
            System.out.println("erreur survenue : " + e.getMessage());
            session.getBasicRemote().sendText("Creation de partie erreur");
        }
    }

    public void Joueurliste(String message, javax.websocket.Session session) throws IOException {
        Joueur j = gson.fromJson(message, Joueur.class);
        ArrayList<String> respJ = new ArrayList();
        ArrayList<String> JoueursLobby = WebSocket.pm.getPseudoJoueurs(WebSocket.listeParties.get(j.getPseudoJoueur()));
        WebSocket.listeJoueurs.forEach((pseudo, s) -> {
            if (!JoueursLobby.contains(pseudo))
                respJ.add(pseudo);
        });
        
        HashMap<String, ArrayList> r = new HashMap();
        r.put("joueursDisp", respJ);        
        r.put("joueursLobby", JoueursLobby);
        System.out.println("ETAT PARTIE : " + r);
        session.getBasicRemote().sendText(gson.toJson(r));
    }
    
    public void disconnect(String message, javax.websocket.Session session) throws IOException, IOException, IOException {
        String tamp[] = message.split("\"");
        String nom = tamp[tamp.length - 2];
        try {
            WebSocket.listeJoueurs.remove(nom);
        } catch (Exception e) {
            session.getBasicRemote().sendText("disconnect failed");
        }
    }
    
    public void invitation(String message, javax.websocket.Session session) throws IOException {
        try {
            String invJ = gson.fromJson(message, Joueur.class).getPseudoJoueur();
            message = message.replace("pseudoJoueur", "old");
            message = message.replace("hebergeur", "pseudoJoueur");
            String hostJ = gson.fromJson(message, Joueur.class).getPseudoJoueur();
            WebSocket.listeJoueurs.get(invJ).getBasicRemote().sendText("rejoindre:" + hostJ);
            this.response = "invitation ok";
        } catch (JsonSyntaxException e) {
            System.out.println(e.getMessage());
            this.response = "invitation failed";
        }
        session.getBasicRemote().sendText(this.response);
    }
    
    public void rejoindre(String message, javax.websocket.Session session) throws IOException {
        JoueurJPA dbJoueur = new JoueurJPA();
        String invJ = gson.fromJson(message, Joueur.class).getPseudoJoueur();
        message = message.replace("pseudoJoueur", "old");
        message = message.replace("hebergeur", "pseudoJoueur");
        String hostJ = gson.fromJson(message, Joueur.class).getPseudoJoueur();
        Partie p = WebSocket.listeParties.get(hostJ);
        WebSocket.listeParties.put(invJ, p);
        Joueur invite = dbJoueur.find(invJ);
        WebSocket.pm.add(p, invite);
        session.getBasicRemote().sendText("{"
                + "id: Rejoindre partie,"
                + "joueurs: " + WebSocket.pm.getPseudoJoueurs(p).toString() + "}");
    }

}
