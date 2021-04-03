/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Data.Joueur;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.Query;


/**
 *
 * @author darra
 */
@Entity
public class JoueurDAO implements Serializable {

    private static final long serialVersionUID = 1L;
    EntityManagerFactory emf = null;
    EntityManager em = null;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String pseudo;
    private String mdp;
    private int age;
    private String sexe;
    private String ville;
    private int nbParties;
    private int nbVictoires;
    private float nbMoyVictoires;
    private float scoreMoyen;
    private float moySuitesGagnees;
    private float moyChouettesVelutesPerdues;

    public JoueurDAO() {
       this.emf = Persistence.createEntityManagerFactory("$objectdb/db/joueur.odb");
       this.em = emf.createEntityManager();
       this.em.getTransaction().begin();
    }
    
    public void create(Joueur j) {
        em.persist(j);
        em.getTransaction().commit();
    }
    
    public void find() {
        em.getTransaction().begin();
        Query q2 = em.createQuery("SELECT j FROM Joueur j", JoueurDAO.class);
        System.out.println("TEST: " + q2.getResultList());
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    
    public String getMdp() {
        return this.mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
    
    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    public String getSexe() {
        return this.sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }
    
    public String getVille() {
        return this.ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
    
    public int getNbParties() {
        return this.nbParties;
    }

    public void setNbParties(int nbParties) {
        this.nbParties = nbParties;
    }
    
    public int getNbVictoires() {
        return this.nbVictoires;
    }

    public void setNbVictoires(int nbVictoires) {
        this.nbVictoires = nbVictoires;
    }
   
    public float getnbMoyVictoires() {
        return this.nbMoyVictoires;
    }

    public void setNbMoyVictoires(float nbMoyVictoires) {
        this.nbMoyVictoires = nbMoyVictoires;
    }
    
    public float getScoreMoyen() {
        return this.scoreMoyen;
    }

    public void setScoreMoyen(float scoreMoyen) {
        this.scoreMoyen = scoreMoyen;
    }
    
    public float getMoySuitesGagnees() {
        return this.moySuitesGagnees;
    }

    public void setMoySuitesGagnees(float moySuitesGagnees) {
        this.moySuitesGagnees = moySuitesGagnees;
    }
    
    public float getMoyChouettesVelutesPerdues() {
        return this.moyChouettesVelutesPerdues;
    }

    public void setMoyChouettesVelutesPerdues(float moyChouettesVelutesPerdues) {
        this.moyChouettesVelutesPerdues = moyChouettesVelutesPerdues;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JoueurDAO)) {
            return false;
        }
        JoueurDAO other = (JoueurDAO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", this.id, this.pseudo);
    }
    
}
