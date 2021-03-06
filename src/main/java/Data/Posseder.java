/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Posseder implements Serializable {
    
    @Column(name="idJoueur")
    @Id
    @OneToOne
    Long idJoueur;
    @Column(name="idPartie")
    @Id
    @OneToOne
    Long idPartie;
    @Column(name="nbPoints")
    int nbPoints;
    @Column(name="nbSuitesGagnees")
    int nbSuitesGagnees;
    @Column(name="nbChouettesVelutesGagnees")
    int nbChouettesVelutesPerdues;
    @Column(name="estGagnant")
    boolean estGagnant = false; 

    public Posseder(Long idJoueur, Long idPartie, int nbPoints, int nbSuitesGagnees, int nbChouettesVelutesPerdues, boolean estGagnant) {
        this.idJoueur = idJoueur;
        this.idPartie = idPartie;
        this.nbPoints = nbPoints;
        this.nbSuitesGagnees = nbSuitesGagnees;
        this.nbChouettesVelutesPerdues = nbChouettesVelutesPerdues;
        this.estGagnant = estGagnant;
    }

    public Posseder() {
    }
    
    public void ajoutSuiteGagnee() {
        this.nbSuitesGagnees++;
    }
    
    public void SuitePerdue() {
        this.nbSuitesGagnees--;
    }
    
    public void addPoints(int pts) {
        this.nbPoints += pts;
    }

    public Long getIdJoueur() {
        return idJoueur;
    }

    public void setIdJoueur(Long idJoueur) {
        this.idJoueur = idJoueur;
    }

    public Long getIdPartie() {
        return idPartie;
    }

    public void setIdPartie(Long idPartie) {
        this.idPartie = idPartie;
    }

    public int getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }

    public int getNbSuitesGagnees() {
        return nbSuitesGagnees;
    }

    public void setNbSuitesGagnees(int nbSuitesGagnees) {
        this.nbSuitesGagnees = nbSuitesGagnees;
    }

    public int getNbChouettesVelutesPerdues() {
        return nbChouettesVelutesPerdues;
    }

    public void setNbChouettesVelutesPerdues(int nbChouettesVelutesPerdues) {
        this.nbChouettesVelutesPerdues = nbChouettesVelutesPerdues;
    }

    public boolean isEstGagnant() {
        return estGagnant;
    }

    public void setEstGagnant(boolean estGagnant) {
        this.estGagnant = estGagnant;
    }

    @Override
    public String toString() {
        return "Posseder{" + "idJoueur=" + idJoueur + ", idPartie=" + idPartie + ", nbPoints=" + nbPoints + ", nbSuitesGagnees=" + nbSuitesGagnees + ", nbChouettesVelutesGagnees=" + nbChouettesVelutesPerdues + ", estGagnant=" + estGagnant + '}';
    }
    
}
