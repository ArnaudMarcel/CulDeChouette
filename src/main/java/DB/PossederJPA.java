/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Data.Posseder;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class PossederJPA {
    
    EntityManagerFactory emf = null;
    EntityManager em = null;

    public PossederJPA() {
        this.emf = Persistence.createEntityManagerFactory("$objectdb/db/posseder.odb");
        this.em = emf.createEntityManager();
    }

    public void create(Posseder p) {
        this.em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    public Set<Posseder> findAll() {
        Set s = null;
        Posseder p = new Posseder();
        try {
            Query q = em.createQuery("SELECT p FROM Posseder p", Posseder.class);
            s = new HashSet(q.getResultList());
        } catch (Exception e) {
            System.out.println("Erreur survenue : " + e.getMessage());
        }
        return s;
    }
    
    public Set<Posseder> findAllByPartie(long idP) {
        Set s = null;
        Posseder p = new Posseder();
        try {
            Query q = em.createQuery("SELECT p FROM Posseder p WHERE idPartie = " + idP, Posseder.class);
            s = new HashSet(q.getResultList());
        } catch (Exception e) {
            System.out.println("Erreur survenue : " + e.getMessage());
        }
        return s;
    }
    
    public Set<Posseder> findAllByJoueur(long idJ) {
        Set s = null;
        Posseder p = new Posseder();
        try {
            Query q = em.createQuery("SELECT p FROM Posseder p WHERE idJoueur = " + idJ, Posseder.class);
            s = new HashSet(q.getResultList());
        } catch (Exception e) {
            System.out.println("Erreur survenue : " + e.getMessage());
        }
        return s;
    }

    public Posseder find(Posseder posseder) {
        Posseder p = new Posseder();
        try {
            Query q = em.createQuery("SELECT p FROM Posseder p WHERE idJoueur = " + posseder.getIdJoueur() + " AND idPartie = " + posseder.getIdPartie(), Posseder.class);
            p = (Posseder) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("Erreur survenue : " + e.getMessage());
        }
        return p;
    }

    public void update(Posseder p) {
        this.em.getTransaction().begin();
        Query q = em.createQuery("SELECT p FROM Posseder p WHERE idJoueur = " + p.getIdJoueur() + " AND idPartie = " + p.getIdPartie(), Posseder.class);
        Posseder posseder = new Posseder();
        posseder = (Posseder) q.getSingleResult();
        posseder.setNbPoints(p.getNbPoints());
        posseder.setNbSuitesGagnees(p.getNbSuitesGagnees());
        posseder.setNbChouettesVelutesPerdues(p.getNbChouettesVelutesPerdues());
        posseder.setEstGagnant(p.isEstGagnant());
        this.em.getTransaction().commit();
    }

    public int delete(Posseder p) {
        this.em.getTransaction().begin();
        int count = em.createQuery("DELETE FROM Posseder where idPartie = " + p.getIdPartie() + " AND idJoueur = " + p.getIdJoueur()).executeUpdate();
        this.em.getTransaction().commit();
        return count;
    }

}
