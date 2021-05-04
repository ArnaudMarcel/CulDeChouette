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
import Data.Action;
import Data.Joueur;
import Data.Lancer;
import Data.Partie;
import Data.PartieControler;
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

    public void lancerPartie(String message, javax.websocket.Session session) throws IOException {
        Joueur j = gson.fromJson(message, Joueur.class);
        Partie p = WebSocket.listeParties.get(j.getPseudoJoueur());
        ArrayList<String> jPartie = WebSocket.pm.getPseudoJoueurs(p);
        this.response.put("id", "culDeChouette");
        this.response.put("listeJoueurs", jPartie);
        this.response.put("points", p.getNbPointsAAtteindrePartie());
        PartieControler tamp = new PartieControler(p, WebSocket.pm.getJoueurs(p));
        WebSocket.pc.put(p, tamp);
        jPartie.forEach((String jp) -> {
            try {
                WebSocket.listeJoueurs.get(jp).getBasicRemote()
                        .sendText(gson.toJson(this.response));
            } catch (IOException ex) {
                Logger.getLogger(UserControler.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        WebSocket.pc.get(p).start();
    }

    public void tourJoueur(Joueur j) throws IOException {
        this.response.put("id", "tour");
        WebSocket.listeJoueurs.get(j.getPseudoJoueur()).getBasicRemote().sendText(
                gson.toJson(this.response));
    }

    public void lancerDes(String message, javax.websocket.Session session) {
        JoueurJPA jp = new JoueurJPA();
        Joueur j = jp.find(gson.fromJson(message, Joueur.class).getPseudoJoueur());
        Partie p = WebSocket.listeParties.get(j.getPseudoJoueur());
        Lancer l = WebSocket.pc.get(p).lancer(j);
        this.response.put("id", "resultatDes");
        this.response.put("lancer", l);
        this.response.put("action", l.getNomInteraction());
        this.response.put("reponse", l.getReponseInteraction());
        this.response.put("combinaison", l.getCombinaisonName());
        this.response.put("score", WebSocket.pc.get(p).getPoints());
        WebSocket.pm.getPseudoJoueurs(p).forEach(pseudo -> {
            if (pseudo.equals(j.getPseudoJoueur())) {
                this.response.put("id", "resultatDesLanceur");
            } else {
                this.response.put("id", "resultatDes");
            }
            try {
                WebSocket.listeJoueurs.get(pseudo).getBasicRemote().sendText(
                        gson.toJson(this.response));
            } catch (IOException ex) {
                Logger.getLogger(UserControler.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void joueurSuivant(String message, javax.websocket.Session session) throws IOException {
        Joueur j = gson.fromJson(message, Joueur.class);
        Partie p = WebSocket.listeParties.get(j.getPseudoJoueur());
        WebSocket.pc.get(p).tourSuivant();
    }

    public void actionJoueur(String message, javax.websocket.Session session) throws IOException {
        Action a = gson.fromJson(message, Action.class);
        Partie p = WebSocket.listeParties.get(a.getPseudoJoueur());
        WebSocket.pc.get(p).interactionJoueur(a);
    }

    public void partieTerminee(Joueur j, ArrayList<Joueur> jPartie, Partie p) {
        this.response.put("id", "PartieTerminee");
        this.response.put("gagnant", j.getPseudoJoueur());
        jPartie.forEach((Joueur jTamp) -> {
            try {
                WebSocket.listeJoueurs.get(jTamp.getPseudoJoueur()).getBasicRemote().sendText(
                        gson.toJson(this.response));
            } catch (IOException ex) {
                Logger.getLogger(UserControler.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
