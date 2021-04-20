/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import DB.JoueurJPA;
import DB.SpeudoAlreadyExistException;
import Data.Joueur;
import java.io.IOException;
import java.util.ArrayList;
import javax.websocket.RemoteEndpoint.Basic;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author Arnaud
 */
public class WebSocket {
    
    private static ArrayList<Joueur> listeJoueurs = new ArrayList<>();
    @javax.websocket.server.ServerEndpoint(value = "/WebSocket")
    
    public static class My_ServerEndpoint {
        private GsonBuilder builder = new GsonBuilder();
        private Gson gson = builder.create();

        @javax.websocket.OnClose
        public void onClose(javax.websocket.Session session, javax.websocket.CloseReason close_reason) {
            System.out.println("onClose: " + close_reason.getReasonPhrase());
        }

        @javax.websocket.OnError
        public void onError(javax.websocket.Session session, Throwable throwable) throws IOException {
            System.out.println("onError: " + throwable.getMessage()); 
        }

        @javax.websocket.OnMessage
        public void onMessage(javax.websocket.Session session, String message) throws IOException {
            System.out.println("Message from JavaScript: " + message);
            String response = "";
            JoueurJPA dbJoueur = new JoueurJPA();

            if (message.contains("\"id\":\"creationJoueur\"")) {
                Joueur j = this.gson.fromJson(message, Joueur.class);
                try {
                    dbJoueur.create(j);
                    WebSocket.listeJoueurs.add(j);
                    response = j.getPseudoJoueur();
                } catch (SpeudoAlreadyExistException e) {
                    response = "Creation failed";
                }
            }
            
            if (message.contains("\"id\":\"connexion\"")) {
                
                Joueur j = this.gson.fromJson(message, Joueur.class);
                try {
                    String tamp[] = message.split("\"");
                    String mdp = tamp[tamp.length-2];
                    Joueur realJoueur = dbJoueur.find(j.getPseudoJoueur());
                    if (mdp.equals(dbJoueur.find(realJoueur.getPseudoJoueur()).getMotDePasseJoueur())) {
                        WebSocket.listeJoueurs.add(realJoueur);
                        response = j.getPseudoJoueur();
                    }
                } catch (Exception e) {
                    response = "Creation joueur failed";
                }
            }
            
            if (message.contains("\"id\":\"creationPartie\"")) {
                response = "Creation game ok";
            }
            
            if (message.contains("\"id\":\"Joueurliste\"")) {
                ArrayList<String> respJ = new ArrayList();
                WebSocket.listeJoueurs.forEach(coJ -> {
                    respJ.add(coJ.getPseudoJoueur());
                });
                response = respJ.toString();
            }
            
            if (message.contains("\"id\":\"disconnect\"")) {
                String tamp[] = message.split("\"");                
                String nom = tamp[tamp.length-2];
                try {
                    boolean found = false;
                    int i = 0;
                    while (i < WebSocket.listeJoueurs.size() && !found) {
                        if (WebSocket.listeJoueurs.get(i).getPseudoJoueur().equals(nom)) {
                            found = !found;
                        }
                        i++;
                    }
                    
                    if (found) {
                        WebSocket.listeJoueurs.remove(i-1);
                        response = "disconnect ok";
                    }
                } catch (Exception e) {
                    response = "disconnect failed";
                }
            }
            System.out.println(WebSocket.listeJoueurs);
            session.getBasicRemote().sendText(response);

        }

        @javax.websocket.OnOpen
        public void onOpen(javax.websocket.Session session) throws java.io.IOException {
            session.getBasicRemote().sendText("{Handshaking: \"Yes\"}");
        }
    }
}
