/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Partie{
    
    @Column(name="idPartie")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long idPartie;
    @Column(name="heurePartie")
    String heurePartie;
    @Column(name="nbPointsAAtteindrePartie")
    int nbPointsAAtteindrePartie;

    public Partie(Long idPartie, String heurePartie, int nbPointsAAtteindrePartie) {
        this.idPartie = idPartie;
        this.heurePartie = heurePartie;
        this.nbPointsAAtteindrePartie = nbPointsAAtteindrePartie;
    }

    public Partie(String heurePartie, int nbPointsAAtteindrePartie) {
        this.heurePartie = heurePartie;
        this.nbPointsAAtteindrePartie = nbPointsAAtteindrePartie;
    }

    public Partie() {
    }

    public Long getIdPartie() {
        return idPartie;
    }

    public void setIdPartie(Long idPartie) {
        this.idPartie = idPartie;
    }

    public String getHeurePartie() {
        return heurePartie;
    }

    public void setHeurePartie(String heurePartie) {
        this.heurePartie = heurePartie;
    }

    public int getNbPointsAAtteindrePartie() {
        return nbPointsAAtteindrePartie;
    }

    public void setNbPointsAAtteindrePartie(int nbPointsAAtteindrePartie) {
        this.nbPointsAAtteindrePartie = nbPointsAAtteindrePartie;
    }

    @Override
    public String toString() {
        return "Partie{" + "idPartie=" + idPartie + ", heurePartie=" + heurePartie + ", nbPointsAAtteindrePartie=" + nbPointsAAtteindrePartie + '}';
    }
    
}
