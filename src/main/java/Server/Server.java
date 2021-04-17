/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import javax.websocket.DeploymentException;
/**
 *
 * @author arnaud
 */

public class Server {

    public static void main(String[] args) throws IOException {
        java.util.Map<String, Object> user_properties = new java.util.HashMap();
        user_properties.put("Author", "EribonLasvaladas");
        org.glassfish.tyrus.server.Server server = new org.glassfish.tyrus.server.Server("localhost", 8080, "/culDeChouette", user_properties, WebSocket.My_ServerEndpoint.class);
        System.out.println(server);
        try {
            server.start();
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
            System.out.println("Please press a key to stop the server...");
            reader.readLine();
        } catch (DeploymentException e) {
            System.out.println(e.getMessage());
        } finally {
            server.stop();
        }
    }
}