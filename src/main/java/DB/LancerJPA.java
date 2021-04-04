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
       this.em.getTransaction().begin();
    }
    
    public void create(Lancer l) {
        em.persist(l);
        em.getTransaction().commit();
    }
    
    public void update(Lancer l) {
        Lancer lancer = em.find(Lancer.class, l.getIdLancer());
        lancer.setValeurDes1(l.getValeurDes1());
        lancer.setValeurDes2(l.getValeurDes2());
        lancer.setValeurDes3(l.getValeurDes3());
        lancer.setCombinaison(l.getCombinaison());
        em.getTransaction().commit();
    }
    
    public int delete(Lancer l) {
        int count = em.createQuery("DELETE FROM Lancer where idLancer = " + l.getIdLancer()).executeUpdate();
        return count;
    }
      
    public Set<Lancer> findAll() {
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
        Lancer l = new Lancer();
        try {
            Query q2 = em.createQuery("SELECT l FROM Lancer l WHERE idLancer = " + id , Lancer.class);
            l = (Lancer) q2.getSingleResult();
        } catch(Exception e) {
            System.out.println("Erreur survenue : " + e.getMessage());
        }
        return l;
    }
}
