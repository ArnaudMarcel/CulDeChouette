/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;
import javax.persistence.*;
import java.util.*;
import DB.JoueurJPA;
import Data.Joueur;

/**
 *
 * @author darra
 */

public class Server {
    public static void main(String[] args) {
        JoueurJPA JoueurJPA = new JoueurJPA();
        Joueur j = new Joueur("Joe", "lezoo", 56, "masculin", "Peau");
        JoueurJPA.create(j);
        JoueurJPA.findAll();
        // Find the number of JoueurDAO objects in the database:
        //Query q1 = em.createQuery("SELECT COUNT(p) FROM JoueurDAO p");
        //System.out.println("Total JoueurDAOs: " + q1.getSingleResult());

        // Find the average X value:
        //Query q2 = em.createQuery("SELECT AVG(p.x) FROM JoueurDAO p");
        //System.out.println("Average X: " + q2.getSingleResult());

        // Retrieve all the JoueurDAO objects from the database:
        //TypedQuery<JoueurDAO> query =
        //em.createQuery("SELECT p FROM JoueurDAO p", JoueurDAO.class);
        //List<JoueurDAO> results = query.getResultList();
        
        //for (JoueurDAO p : results) {
        //    System.out.println(p);
        //}

        // Close the database connection:
        //em.close();
        //emf.close();
    }
}