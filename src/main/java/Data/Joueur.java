/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author arnaud
 */
@Entity
public class Joueur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idJoueur;
    @Column(name="pseudoJoueur")
    String pseudoJoueur;
    @Column(name="motDePasseJoueur")
    String motDePasseJoueur;
    @Column(name="ageJoueur")
    int ageJoueur;
    @Column(name="sexeJoueur")
    String sexeJoueur;
    @Column(name="villeJoueur")
    String villeJoueur;
    @Column(name="nbParties")
    int nbParties;
    @Column(name="nbVictoires")
    int nbVictoires;
    @Column(name="nbMoyVictoires")
    float nbMoyVictoires;
    @Column(name="scoreMoyen")
    float scoreMoyen;
    @Column(name="moySuitesGagnees")
    float moySuitesGagnees;
    @Column(name="moyChouettesVelutesPerdues")
    float moyChouettesVelutesPerdues;
    @Column(name="disponible")
    boolean disponible;

    public Joueur(Long idJoueur, String pseudoJoueur, String motDePasseJoueur, int ageJoueur, String sexeJoueur, String villeJoueur, int nbParties, int nbVictoires, float nbMoyVictoires, float scoreMoyen, float moySuitesGagnees, float moyChouettesVelutesPerdues, boolean disponible) {
        this.idJoueur = idJoueur;
        this.pseudoJoueur = pseudoJoueur;
        this.motDePasseJoueur = motDePasseJoueur;
        this.ageJoueur = ageJoueur;
        this.sexeJoueur = sexeJoueur;
        this.villeJoueur = villeJoueur;
        this.nbParties = nbParties;
        this.nbVictoires = nbVictoires;
        this.nbMoyVictoires = nbMoyVictoires;
        this.scoreMoyen = scoreMoyen;
        this.moySuitesGagnees = moySuitesGagnees;
        this.moyChouettesVelutesPerdues = moyChouettesVelutesPerdues;
        this.disponible = disponible;
    }
    
    public Joueur(String pseudoJoueur, String motDePasseJoueur, int ageJoueur, String sexeJoueur, String villeJoueur) {
        this.pseudoJoueur = pseudoJoueur;
        this.motDePasseJoueur = motDePasseJoueur;
        this.ageJoueur = ageJoueur;
        this.sexeJoueur = sexeJoueur;
        this.villeJoueur = villeJoueur;
        this.disponible = true;       
    }

    public Joueur(Long idJoueur, String pseudoJoueur, String motDePasseJoueur, int ageJoueur, String sexeJoueur, String villeJoueur) {
        this.idJoueur = idJoueur;
        this.pseudoJoueur = pseudoJoueur;
        this.motDePasseJoueur = motDePasseJoueur;
        this.ageJoueur = ageJoueur;
        this.sexeJoueur = sexeJoueur;
        this.villeJoueur = villeJoueur;
        this.disponible = true;       

    }

    public Joueur() {
    }

    public Joueur(Long idJoueur, String pseudoJoueur, String motDePasseJoueur, int ageJoueur, String sexeJoueur, String villeJoueur, int nbParties, int nbVictoires, float nbMoyVictoires, float scoreMoyen, float moySuitesGagnees, float moyChouettesVelutesPerdues) {
        this.idJoueur = idJoueur;
        this.pseudoJoueur = pseudoJoueur;
        this.motDePasseJoueur = motDePasseJoueur;
        this.ageJoueur = ageJoueur;
        this.sexeJoueur = sexeJoueur;
        this.villeJoueur = villeJoueur;
        this.nbParties = nbParties;
        this.nbVictoires = nbVictoires;
        this.nbMoyVictoires = nbMoyVictoires;
        this.scoreMoyen = scoreMoyen;
        this.moySuitesGagnees = moySuitesGagnees;
        this.moyChouettesVelutesPerdues = moyChouettesVelutesPerdues;
        this.disponible = true;
    }

    public Long getIdJoueur() {
        return idJoueur;
    }

    public void setIdJoueur(Long idJoueur) {
        this.idJoueur = idJoueur;
    }

    public String getPseudoJoueur() {
        return pseudoJoueur;
    }

    public void setPseudoJoueur(String pseudoJoueur) {
        this.pseudoJoueur = pseudoJoueur;
    }

    public String getMotDePasseJoueur() {
        return motDePasseJoueur;
    }

    public void setMotDePasseJoueur(String motDePasseJoueur) {
        this.motDePasseJoueur = motDePasseJoueur;
    }
    
    public int getAgeJoueur() {
        return ageJoueur;
    }

    public void setAgeJoueur(int ageJoueur) {
        this.ageJoueur = ageJoueur;
    }

    public String getSexeJoueur() {
        return sexeJoueur;
    }

    public void setSexeJoueur(String sexeJoueur) {
        this.sexeJoueur = sexeJoueur;
    }

    public String getVilleJoueur() {
        return villeJoueur;
    }

    public void setVilleJoueur(String villeJoueur) {
        this.villeJoueur = villeJoueur;
    }

    public int getNbParties() {
        return nbParties;
    }

    public void setNbParties(int nbParties) {
        this.nbParties = nbParties;
    }

    public int getNbVictoires() {
        return nbVictoires;
    }

    public void setNbVictoires(int nbVictoires) {
        this.nbVictoires = nbVictoires;
    }

    public float getNbMoyVictoires() {
        return nbMoyVictoires;
    }

    public void setNbMoyVictoires(float nbMoyVictoires) {
        this.nbMoyVictoires = nbMoyVictoires;
    }

    public float getScoreMoyen() {
        return scoreMoyen;
    }

    public void setScoreMoyen(float scoreMoyen) {
        this.scoreMoyen = scoreMoyen;
    }

    public float getMoySuitesGagnees() {
        return moySuitesGagnees;
    }

    public void setMoySuitesGagnees(float moySuitesGagnees) {
        this.moySuitesGagnees = moySuitesGagnees;
    }

    public float getMoyChouettesVelutesPerdues() {
        return moyChouettesVelutesPerdues;
    }

    public void setMoyChouettesVelutesPerdues(float moyChouettesVelutesPerdues) {
        this.moyChouettesVelutesPerdues = moyChouettesVelutesPerdues;
    }
    
    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Joueur{" + "idJoueur=" + idJoueur + ", pseudoJoueur=" + pseudoJoueur + ", motDePasseJoueur=" + motDePasseJoueur + ", ageJoueur=" + ageJoueur + ", sexeJoueur=" + sexeJoueur + ", villeJoueur=" + villeJoueur + ", nbParties=" + nbParties + ", nbVictoires=" + nbVictoires + ", nbMoyVictoires=" + nbMoyVictoires + ", scoreMoyen=" + scoreMoyen + ", moySuitesGagnees=" + moySuitesGagnees + ", moyChouettesVelutesPerdues=" + moyChouettesVelutesPerdues + '}';
    }
}

