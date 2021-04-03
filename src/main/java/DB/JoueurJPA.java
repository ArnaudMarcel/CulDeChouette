/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Data.Joueur;
import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


/**
 *
 * @author darra
 */
public class JoueurJPA implements Serializable {

    private static final long serialVersionUID = 1L;
    EntityManagerFactory emf = null;
    EntityManager em = null;

    public JoueurJPA() {
       this.emf = Persistence.createEntityManagerFactory("$objectdb/db/joueur.odb");
       this.em = emf.createEntityManager();
       this.em.getTransaction().begin();
    }
    
    public void create(Joueur j) {
        em.persist(j);
        em.getTransaction().commit();
    }
    
    public void findAll() {
        em.getTransaction().begin();
        Query q2 = em.createQuery("SELECT j FROM Joueur j", Joueur.class);
        ArrayList r = new ArrayList(q2.getResultList());
        Joueur test = new Joueur();
        test = (Joueur) r.get(0);
        System.out.println(test.toString());
    }
    
    public void find(Long id) {
        em.getTransaction().begin();
        Query q2 = em.createQuery("SELECT j FROM Joueur j WHERE idJoueur = " + id , Joueur.class);
        Joueur test = new Joueur();
        //test = q2.getFirstResult();
        //0System.out.println(test.toString());
    }
    
}
