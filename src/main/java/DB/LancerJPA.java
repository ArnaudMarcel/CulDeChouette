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
    private static final long serialVersionUID = 1L;
    EntityManagerFactory emf = null;
    EntityManager em = null;

    public LancerJPA() {
       this.emf = Persistence.createEntityManagerFactory("$objectdb/db/lancer.odb");
       this.em = emf.createEntityManager();
    }
    
    public void create(Lancer l) {
        this.em.getTransaction().begin();
        em.persist(l);
        em.getTransaction().commit();
    }
    
    public void update(Lancer l) {
        this.em.getTransaction().begin();
        Lancer lancer = em.find(Lancer.class, l.getIdLancer());
        lancer.setValeurDes1(l.getValeurDes1());
        lancer.setValeurDes2(l.getValeurDes2());
        lancer.setValeurDes3(l.getValeurDes3());
        lancer.setInteraction(l.getInteraction());
        em.getTransaction().commit();
    }
    
    public int delete(Lancer l) {
        this.em.getTransaction().begin();
        int count = em.createQuery("DELETE FROM Lancer where idLancer = " + l.getIdLancer()).executeUpdate();
        return count;
    }
      
    public Set<Lancer> findAll() {
        this.em.getTransaction().begin();
        Set s = null;
        Lancer l = new Lancer();
        try{
            Query q2 = em.createQuery("SELECT l FROM Lancer l", Lancer.class);
            s = new HashSet(q2.getResultList());
        } catch(Exception e) {
            System.out.println("Erreur survenue : " + e.getMessage());
        }
        return s;
    }
    
    public Lancer find(long id) {
        this.em.getTransaction().begin();
        Lancer l = new Lancer();
        try {
            l = em.find(Lancer.class, id);
        } catch(Exception e) {
            System.out.println("Erreur survenue : " + e.getMessage());
        }
        return l;
    }
}
