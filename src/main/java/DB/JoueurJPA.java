/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Data.Joueur;
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
public class JoueurJPA {

    EntityManagerFactory emf = null;
    EntityManager em = null;

    public JoueurJPA() {
       this.emf = Persistence.createEntityManagerFactory("$objectdb/db/joueur.odb");
       this.em = emf.createEntityManager();
    }
    
    public void create(Joueur j) {
        this.em.getTransaction().begin();
        em.persist(j);
        em.getTransaction().commit();
    }
    
    public void update(Joueur j) {
        this.em.getTransaction().begin();
        Joueur p = em.find(Joueur.class, j.getIdJoueur());
        p.setAgeJoueur(j.getAgeJoueur());
        p.setMotDePasseJoueur(j.getMotDePasseJoueur());
        p.setMoyChouettesVelutesPerdues(j.getMoyChouettesVelutesPerdues());
        p.setMoySuitesGagnees(j.getMoySuitesGagnees());
        p.setNbMoyVictoires(j.getNbMoyVictoires());
        p.setNbParties(j.getNbParties());
        p.setNbVictoires(j.getNbVictoires());
        p.setPseudoJoueur(j.getPseudoJoueur());
        p.setScoreMoyen(j.getScoreMoyen());
        p.setSexeJoueur(j.getSexeJoueur());
        p.setVilleJoueur(j.getVilleJoueur());
        em.getTransaction().commit();
    }
    
    public int delete(Joueur j) {
        this.em.getTransaction().begin();
        int count = em.createQuery("DELETE FROM Joueur where idJoueur = " + j.getIdJoueur()).executeUpdate();
        return count;
    }
    
    public Set<Joueur> findAll() {
        this.em.getTransaction().begin();
        Set s = null;
        try{
            Query q2 = em.createQuery("SELECT l FROM Lancer l", Joueur.class);
            s = new HashSet(q2.getResultList());
        } catch(Exception e) {
            System.out.println("Erreur survenue : " + e.getMessage());
        }
        return s;
    }
    
    
    public Joueur find(Long id) {
        this.em.getTransaction().begin();
        Joueur j = new Joueur();
        try {
            Query q2 = em.createQuery("SELECT j FROM Joueur j WHERE idJoueur = " + id, Joueur.class);
            j = (Joueur) q2.getSingleResult();
        } catch(Exception e) {
            System.out.println("Erreur survenue : " + e.getMessage());
        }
        return j;
    }
    
}
