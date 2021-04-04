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

    private static final long serialVersionUID = 1L;
    EntityManagerFactory emf = null;
    EntityManager em = null;

    public PartieJPA() {
        this.emf = Persistence.createEntityManagerFactory("$objectdb/db/partie.odb");
        this.em = emf.createEntityManager();
        this.em.getTransaction().begin();
    }

    public void create(Partie p) {
        em.persist(p);
        em.getTransaction().commit();
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
        Partie partie = em.find(Partie.class, p.getIdPartie());
        partie.setHeurePartie(p.getHeurePartie());
        partie.setNbPointsAAtteindrePartie(p.getNbPointsAAtteindrePartie());
        em.getTransaction().commit();
    }

    public int delete(Partie p) {
        int count = em.createQuery("DELETE FROM Partie where idLancer = " + p.getIdPartie()).executeUpdate();
        return count;
    }
}
