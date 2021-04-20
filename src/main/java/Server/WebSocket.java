/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.util.ArrayList;
import javax.websocket.RemoteEndpoint.Basic;

/**
 *
 * @author Arnaud
 */
public class WebSocket {
    
    private static ArrayList<Basic> listeWS = new ArrayList<>();
    @javax.websocket.server.ServerEndpoint(value = "/WebSocket")
    
    public static class My_ServerEndpoint {

        @javax.websocket.OnClose
        public void onClose(javax.websocket.Session session, javax.websocket.CloseReason close_reason) {
            System.out.println("onClose: " + close_reason.getReasonPhrase());
        }

        @javax.websocket.OnError
        public void onError(javax.websocket.Session session, Throwable throwable) throws IOException {
            System.out.println("onError: " + throwable.getMessage()); 
        }

        @javax.websocket.OnMessage
        public void onMessage(javax.websocket.Session session, String message) {
            System.out.println("Message from JavaScript: " + message);
        }

        @javax.websocket.OnOpen
        public void onOpen(javax.websocket.Session session) throws java.io.IOException {
            session.getBasicRemote().sendText("{Handshaking: \"Yes\"}");
            WebSocket.listeWS.add(session.getBasicRemote());
        }
        
        private void itemRequest(javax.websocket.Session session, String request){
            System.out.println("Seeking this item..." + request);
        }
    }
}
