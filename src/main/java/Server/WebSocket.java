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
                response = "Creation ok";
                Joueur j = this.gson.fromJson(message, Joueur.class);
                try {
                    dbJoueur.create(j);
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
                        response = "Connexion ok";
                    }
                } catch (Exception e) {
                    response = "Creation joueur failed";
                }
            }
            
            if (message.contains("\"id\":\"creationPartie\"")) {
                response = "Creation game failed";
            }
            session.getBasicRemote().sendText(response);

        }

        @javax.websocket.OnOpen
        public void onOpen(javax.websocket.Session session) throws java.io.IOException {
            session.getBasicRemote().sendText("{Handshaking: \"Yes\"}");
        }
    }
}
