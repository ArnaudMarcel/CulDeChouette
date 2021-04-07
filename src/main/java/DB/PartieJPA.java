/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Data.Partie;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class PartieJPA implements Serializable {

    EntityManagerFactory emf = null;
    EntityManager em = null;

    public PartieJPA() {
        this.emf = Persistence.createEntityManagerFactory("$objectdb/db/partie.odb");
        this.em = emf.createEntityManager();
    }

    public void create(Partie p) {
        this.em.getTransaction().begin();
        this.em.persist(p);
        this.em.getTransaction().commit();
    }

    public Set<Partie> findAll() {
        Set s = null;
        Partie p = new Partie();
        try {
            Query q = em.createQuery("SELECT p FROM Partie p", Partie.class);
            s = new HashSet(q.getResultList());
        } catch (Exception e) {
            System.out.println("Erreur survenue : " + e.getMessage());
        }
        return s;
    }

    public Partie find(Long id) {
        Partie p = new Partie();
        try {
            Query q = em.createQuery("SELECT p FROM Partie p WHERE idPartie = " + id, Partie.class);
            p = (Partie) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("Erreur survenue : " + e.getMessage());
        }
        return p;
    }

    public void update(Partie p) {
        this.em.getTransaction().begin();
        Partie partie = this.em.find(Partie.class, p.getIdPartie());
        partie.setHeurePartie(p.getHeurePartie());
        partie.setNbPointsAAtteindrePartie(p.getNbPointsAAtteindrePartie());
        this.em.getTransaction().commit();
    }

    public int delete(Partie p) {
        this.em.getTransaction().begin();
        int count = this.em.createQuery("DELETE FROM Partie where idPartie = " + p.getIdPartie()).executeUpdate();
        this.em.getTransaction().commit();
        return count;
    }
}
