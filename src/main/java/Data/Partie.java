/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Partie implements Serializable {
    
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
    public int hashCode() {
        int hash = 7;
        //hash = 59 * hash + Objects.hashCode(this.idPartie);
        hash = 59 * hash + Objects.hashCode(this.heurePartie);
        hash = 59 * hash + this.nbPointsAAtteindrePartie;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Partie other = (Partie) obj;
        if (this.nbPointsAAtteindrePartie != other.nbPointsAAtteindrePartie) {
            return false;
        }
        if (!Objects.equals(this.heurePartie, other.heurePartie)) {
            return false;
        }
        if (!Objects.equals(this.idPartie, other.idPartie)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Partie{" + "idPartie=" + idPartie + ", heurePartie=" + heurePartie + ", nbPointsAAtteindrePartie=" + nbPointsAAtteindrePartie + '}';
    }
    
}
