/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.Serializable;
import java.util.Random;
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
public class Lancer implements Serializable {

    @Column(name = "IdLancer")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long idLancer;
    @Column(name = "valeurDes1")
    int valeurDes1;
    @Column(name = "valeurDes2")
    int valeurDes2;
    @Column(name = "valeurDes3")
    int valeurDes3;
    @Column(name = "interaction")
    boolean interaction;
    @Column(name = "idJoueur")
    long idJoueur;
    @Column(name = "idPartie")
    long idPartie;

    public Lancer(long idLancer, int valeurDes1, int valeurDes2, int valeurDes3, boolean interaction, long idJoueur, long idPartie) {
        this.idLancer = idLancer;
        this.valeurDes1 = valeurDes1;
        this.valeurDes2 = valeurDes2;
        this.valeurDes3 = valeurDes3;
        this.interaction = interaction;
        this.idJoueur = idJoueur;
        this.idPartie = idPartie;
    }

    public Lancer(int valeurDes1, int valeurDes2, int valeurDes3, boolean interaction, long idJoueur, long idPartie) {
        this.valeurDes1 = valeurDes1;
        this.valeurDes2 = valeurDes2;
        this.valeurDes3 = valeurDes3;
        this.interaction = interaction;
        this.idJoueur = idJoueur;
        this.idPartie = idPartie;
    }

    public Lancer(long idJoueur, long idPartie) {
        this.idPartie = idPartie;
        this.idJoueur = idJoueur;
        Random rand = new Random();
        this.valeurDes1 = rand.nextInt(6) + 1;
        this.valeurDes2 = rand.nextInt(6) + 1;
        this.valeurDes3 = rand.nextInt(6) + 1;
        this.isInteraction();
    }

    public Lancer() {

    }

    private void isInteraction() {
        if (this.valeurDes1 + 1 == this.valeurDes2 && this.valeurDes1 + 2 == this.valeurDes3) {
            this.interaction = true;
        }
        if (this.valeurDes1 + this.valeurDes2 == this.valeurDes3 && this.valeurDes1 == this.valeurDes2) {
            this.interaction = true;
        }
    }

    public boolean isVelute() {
        return (this.valeurDes1 + this.valeurDes2 == this.valeurDes3);
    }

    public boolean isChouette() {
        return (this.valeurDes1 == this.valeurDes2 && this.valeurDes1 * 2 != this.valeurDes3);
    }

    public boolean isCulDeChouette() {
        return (this.valeurDes1 == this.valeurDes2 && this.valeurDes2 == this.valeurDes3);
    }

    public boolean isSuite() {
        return (this.valeurDes1 + 1 == this.valeurDes2 && this.valeurDes1 + 2 == this.valeurDes3);
    }

    public boolean isChouetteVelute() {
        return (this.isChouette() && this.isVelute());
    }

    public String getNomInteraction() {
        String nom = null;
        if (this.interaction == true) {
            if (this.isChouetteVelute()) {
                nom = "ChouetteVelute";
            } else {
                nom = "Suite";
            }
        }
        return nom;
    }

    public String getReponseInteraction() {
        String s = "";
        if (this.interaction) {
            switch (this.getNomInteraction()) {
                case "ChouetteVelute":
                    s = "Pas mou le caillou !";
                    break;

                case "Suite":
                    s = "Grelotte ça picote !";
                    break;

                default:
                    break;
            }
        }
        return s;
    }

    public long getIdLancer() {
        return idLancer;
    }

    public void setIdLancer(long idLancer) {
        this.idLancer = idLancer;
    }

    public int getValeurDes1() {
        return valeurDes1;
    }

    public void setValeurDes1(int valeurDes1) {
        this.valeurDes1 = valeurDes1;
    }

    public int getValeurDes2() {
        return valeurDes2;
    }

    public void setValeurDes2(int valeurDes2) {
        this.valeurDes2 = valeurDes2;
    }

    public int getValeurDes3() {
        return valeurDes3;
    }

    public void setValeurDes3(int valeurDes3) {
        this.valeurDes3 = valeurDes3;
    }

    public boolean getInteraction() {
        return interaction;
    }

    public void setInteraction(boolean interaction) {
        this.interaction = interaction;
    }

    public long getIdJoueur() {
        return idJoueur;
    }

    public void setIdJoueur(long idJoueur) {
        this.idJoueur = idJoueur;
    }

    public long getIdPartie() {
        return idPartie;
    }

    public void setIdPartie(long idPartie) {
        this.idPartie = idPartie;
    }

    @Override
    public String toString() {
        return "Lancer{" + "idLancer=" + idLancer + ", valeurDes1=" + valeurDes1 + ", valeurDes2=" + valeurDes2 + ", valeurDes3=" + valeurDes3 + ", interaction=" + interaction + ", idJoueur=" + idJoueur + ", idPartie=" + idPartie + '}';
    }

}
