/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Data.Lancer;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author arnaud
 */
public class LancerJPA {
    
    EntityManagerFactory emf = null;
    EntityManager em = null;

    public LancerJPA() {
       this.emf = Persistence.createEntityManagerFactory("$objectdb/db/lancer.odb");
       this.em = emf.createEntityManager();
    }
    
    public void create(Lancer l) {
        this.em.getTransaction().begin();
        this.em.persist(l);
        this.em.getTransaction().commit();
    }
    
    public void update(Lancer l) {
        this.em.getTransaction().begin();
        Query q = this.em.createQuery("SELECT l FROM Lancer l WHERE idLancer = " + l.getIdLancer(), Lancer.class);
        Lancer lancer = new Lancer();
        lancer = (Lancer) q.getSingleResult();
        lancer.setValeurDes1(l.getValeurDes1());
        lancer.setValeurDes2(l.getValeurDes2());
        lancer.setValeurDes3(l.getValeurDes3());
        lancer.setInteraction(l.getInteraction());
        this.em.getTransaction().commit();
    }
    
    public int delete(Lancer l) {
        this.em.getTransaction().begin();
        int count = em.createQuery("DELETE FROM Lancer where idLancer = " + l.getIdLancer()).executeUpdate();
        this.em.getTransaction().commit();
        return count;
    }
      
    public Set<Lancer> findAll() {
        Set s = null;
        try{
            Query q2 = this.em.createQuery("SELECT l FROM Lancer l", Lancer.class);
            s = new HashSet(q2.getResultList());
        } catch(Exception e) {
            System.out.println("Erreur survenue : " + e.getMessage());
        }
        return s;
    }
    
    public Set<Lancer> findAllByPartie(long idP) {
        Set s = null;
        try{
            Query q2 = em.createQuery("SELECT l FROM Lancer l WHERE idPartie = " + idP, Lancer.class);
            s = new HashSet(q2.getResultList());
        } catch(Exception e) {
            System.out.println("Erreur survenue : " + e.getMessage());
        }
        return s;
    }
    
    public Set<Lancer> findAllByJoueur(long idJ) {
        Set s = null;
        try{
            Query q2 = em.createQuery("SELECT l FROM Lancer l WHERE idJoueur =" + idJ, Lancer.class);
            s = new HashSet(q2.getResultList());
        } catch(Exception e) {
            System.out.println("Erreur survenue : " + e.getMessage());
        }
        return s;
    }
    
    public Set<Lancer> findAll(long idJ, long idP) {
        Set s = null;
        Lancer l = new Lancer();
        try{
            Query q2 = em.createQuery("SELECT l FROM Lancer l WHERE idJoueur = "
                    + idJ + " AND idPartie = " + idP, Lancer.class);
            s = new HashSet(q2.getResultList());
        } catch(Exception e) {
            System.out.println("Erreur survenue : " + e.getMessage());
        }
        return s;
    }
    
    public Lancer find(long id) {
        Lancer l = new Lancer();
        try {
            l = em.find(Lancer.class, id);
        } catch(Exception e) {
            System.out.println("Erreur survenue : " + e.getMessage());
        }
        return l;
    }
}
