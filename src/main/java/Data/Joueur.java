/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author darra
 */
@Entity
public class Joueur {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idJoueur;
    String pseudoJoueur;
    String motDePasseJoueur;
    int ageJoueur;
    String sexeJoueur;
    String villeJoueur;
    int nbParties;
    int nbVictoires;
    float nbMoyVictoires;
    float scoreMoyen;
    float moySuitesGagnees;
    float moyChouettesVelutesPerdues;
    
    
    public Joueur(String pseudoJoueur, String motDePasseJoueur, int ageJoueur, String sexeJoueur, String villeJoueur) {
        this.pseudoJoueur = pseudoJoueur;
        this.motDePasseJoueur = motDePasseJoueur;
        this.ageJoueur = ageJoueur;
        this.sexeJoueur = sexeJoueur;
        this.villeJoueur = villeJoueur;
    }

    public Joueur(Long idJoueur, String pseudoJoueur, String motDePasseJoueur, int ageJoueur, String sexeJoueur, String villeJoueur) {
        this.idJoueur = idJoueur;
        this.pseudoJoueur = pseudoJoueur;
        this.motDePasseJoueur = motDePasseJoueur;
        this.ageJoueur = ageJoueur;
        this.sexeJoueur = sexeJoueur;
        this.villeJoueur = villeJoueur;
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
    }
    
    

    public Long getId() {
        return idJoueur;
    }

    public void setId(Long idJoueur) {
        this.idJoueur = idJoueur;
    }

    public String getPseudo() {
        return pseudoJoueur;
    }

    public void setPseudo(String pseudoJoueur) {
        this.pseudoJoueur = pseudoJoueur;
    }

    public String getMdp() {
        return motDePasseJoueur;
    }

    public void setMdp(String motDePasseJoueur) {
        this.motDePasseJoueur = motDePasseJoueur;
    }

    public int getAge() {
        return ageJoueur;
    }

    public void setAge(int ageJoueur) {
        this.ageJoueur = ageJoueur;
    }

    public String getSexe() {
        return sexeJoueur;
    }

    public void setSexe(String sexeJoueur) {
        this.sexeJoueur = sexeJoueur;
    }

    public String getVille() {
        return villeJoueur;
    }

    public void setVille(String villeJoueur) {
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

    @Override
    public String toString() {
        return "Joueur{" + "idJoueur=" + idJoueur + ", pseudoJoueur=" + pseudoJoueur + ", motDePasseJoueur=" + motDePasseJoueur + ", ageJoueur=" + ageJoueur + ", sexeJoueur=" + sexeJoueur + ", villeJoueur=" + villeJoueur + ", nbParties=" + nbParties + ", nbVictoires=" + nbVictoires + ", nbMoyVictoires=" + nbMoyVictoires + ", scoreMoyen=" + scoreMoyen + ", moySuitesGagnees=" + moySuitesGagnees + ", moyChouettesVelutesPerdues=" + moyChouettesVelutesPerdues + '}';
    }

}
