/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Data.Partie;
import Data.PartieManager;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.lang.reflect.Method;
import Data.PartieControler;

/**
 *
 * @author Arnaud
 */
public class WebSocket {
    
    static HashMap<String, javax.websocket.Session> listeJoueurs = new HashMap<>();    
    static HashMap<String, Partie> listeParties = new HashMap<>();
    static PartieManager pm = new PartieManager();
    static HashMap<Partie, PartieControler> pc = new HashMap<>();

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
        public void onMessage(javax.websocket.Session session, String message) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
            System.out.println("Message from JavaScript: " + message);
            UserControler controler = gson.fromJson(message, UserControler.class);
            Method method = controler.getClass().getMethod(controler.getId(), String.class, javax.websocket.Session.class);
            method.invoke(controler, message, session);
        }

        @javax.websocket.OnOpen
        public void onOpen(javax.websocket.Session session) throws java.io.IOException {
            HashMap r = new HashMap();
            r.put("id", "hello");
            session.getBasicRemote().sendText(gson.toJson(r));
        }
    }
}
