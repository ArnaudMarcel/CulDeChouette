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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arnaud
 */
public class UserControler {

    private String id;
    private HashMap response = new HashMap();
    private GsonBuilder builder = new GsonBuilder();
    private Gson gson = builder.create();

    public UserControler(String id) {
        this.id = id;
    }

    public UserControler() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void creationJoueur(String message, javax.websocket.Session session) throws IOException {
        JoueurJPA dbJoueur = new JoueurJPA();
        Joueur j = this.gson.fromJson(message, Joueur.class);
        try {
            dbJoueur.create(j);
            WebSocket.listeJoueurs.put(j.getPseudoJoueur(), session);
            this.response.put("id", "creationJoueur_reussie");
        } catch (SpeudoAlreadyExistException e) {
            this.response.put("id", "creationJoueur_echec");
        }
        session.getBasicRemote().sendText(gson.toJson(this.response)); //pb
    }

    public void connexion(String message, javax.websocket.Session session) throws IOException {
        Joueur j = this.gson.fromJson(message, Joueur.class);
        JoueurJPA dbJoueur = new JoueurJPA();
        try {
            String tamp[] = message.split("\"");
            String mdp = tamp[tamp.length - 2];
            Joueur realJoueur = dbJoueur.find(j.getPseudoJoueur());
            if (mdp.equals(dbJoueur.find(realJoueur.getPseudoJoueur()).getMotDePasseJoueur())) {
                if (!WebSocket.listeJoueurs.containsKey(realJoueur.getPseudoJoueur())) {
                    WebSocket.listeJoueurs.put(realJoueur.getPseudoJoueur(), session);
                    this.response.put("id", "connexionJoueur_reussie");
                    this.response.put("pseudoJoueur", j.getPseudoJoueur());
                } else {
                    this.response.put("id", "connexionJoueur_echec");
                    this.response.put("raison", "Joueur déjà connecté");
                }

            } else {
                this.response.put("id", "connexionJoueur_echec");
                this.response.put("raison", "Mot de passe incorrect");
            }
        } catch (Exception e) {
            this.response.put("id", "connexionJoueur_echec");
        }
        session.getBasicRemote().sendText(gson.toJson(this.response));
    }

    public void creationPartie(String message, javax.websocket.Session session) throws IOException {
        JoueurJPA dbJoueur = new JoueurJPA();
        try {
            String tamp[] = message.split("\"");
            String hote = tamp[tamp.length - 2];
            Partie p = gson.fromJson(message, Partie.class);
            WebSocket.listeParties.put(hote, p);
            WebSocket.pm.add(p, dbJoueur.find(hote));
            this.response.put("id", "creationPartie_reussie");
        } catch (JsonSyntaxException e) {
            System.out.println("erreur survenue : " + e.getMessage());
            this.response.put("id", "creationPartie_echec");
        }
        session.getBasicRemote().sendText(gson.toJson(this.response));
    }

    public void Joueurliste(String message, javax.websocket.Session session) throws IOException {
        Joueur j = gson.fromJson(message, Joueur.class);
        ArrayList<String> respJ = new ArrayList();
        ArrayList<String> JoueursLobby = WebSocket.pm.getPseudoJoueurs(WebSocket.listeParties.get(j.getPseudoJoueur()));
        WebSocket.listeJoueurs.forEach((pseudo, s) -> {
            if (!JoueursLobby.contains(pseudo)) {
                respJ.add(pseudo);
            }
        });

        HashMap r = new HashMap();
        r.put("id", "listeDesJoueurs");
        r.put("joueursDisp", respJ);
        r.put("joueursLobby", JoueursLobby);
        session.getBasicRemote().sendText(gson.toJson(r));
    }

    public void disconnect(String message, javax.websocket.Session session) throws IOException, IOException, IOException {
        Joueur j = gson.fromJson(message, Joueur.class);
        try {
            Partie p = WebSocket.listeParties.get(j.getPseudoJoueur());
            if (p != null) {
                if (WebSocket.pm.getHotePartie(p).getPseudoJoueur().equals(j.getPseudoJoueur())) {
                    this.response.put("id", "PartieSupprimee");
                    WebSocket.pm.getJoueurs(p).forEach((Joueur joueurP) -> {
                        try {
                            WebSocket.listeParties.remove(joueurP.getPseudoJoueur());
                            WebSocket.listeJoueurs.get(joueurP.getPseudoJoueur()).getBasicRemote().sendText(gson.toJson(this.response));
                        } catch (IOException ex) {
                            Logger.getLogger(UserControler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    WebSocket.pm.delete(p);
                }
            }
        } catch (Exception e) {
            System.out.println("erreur survenue : " + e.getMessage());
            session.getBasicRemote().sendText("disconnect_failed");
        }
        WebSocket.listeJoueurs.remove(j.getPseudoJoueur());
    }

    public void invitation(String message, javax.websocket.Session session) throws IOException {
        try {
            String invJ = gson.fromJson(message, Joueur.class).getPseudoJoueur();
            message = message.replace("pseudoJoueur", "old");
            message = message.replace("hebergeur", "pseudoJoueur");
            String hostJ = gson.fromJson(message, Joueur.class).getPseudoJoueur();
            this.response.put("id", "invitationPartie");
            this.response.put("hote", hostJ);
            this.response.put("invite", invJ);
            WebSocket.listeJoueurs.get(invJ).getBasicRemote().sendText(gson.toJson(this.response));
        } catch (JsonSyntaxException e) {
            System.out.println(e.getMessage());
        }
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
        this.response.put("id", "Rejoindre_partie");
        session.getBasicRemote().sendText(gson.toJson(this.response));
    }

    public void quitterLobby(String message, javax.websocket.Session session) throws IOException {
        JoueurJPA dbJoueur = new JoueurJPA();
        Joueur j = dbJoueur.find(gson.fromJson(message, Joueur.class).getPseudoJoueur());
        try {
            Partie p = WebSocket.listeParties.get(j.getPseudoJoueur());
            if (WebSocket.pm.getHotePartie(p).getPseudoJoueur().equals(j.getPseudoJoueur())) {
                this.response.put("id", "PartieSupprimee");
                WebSocket.pm.getJoueurs(p).forEach((Joueur joueurP) -> {
                    try {
                        WebSocket.listeParties.remove(joueurP.getPseudoJoueur());
                        WebSocket.listeJoueurs.get(joueurP.getPseudoJoueur()).getBasicRemote().sendText(gson.toJson(this.response));
                    } catch (IOException ex) {
                        Logger.getLogger(UserControler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                WebSocket.pm.delete(p);
            } else {
                WebSocket.pm.delete(p, j);
            }
        } catch (Exception e) {
            System.out.println("Erreur survenue (quitterLobby) :" + e.getMessage());
        }

        WebSocket.listeParties.remove(j.getPseudoJoueur());
    }
}
