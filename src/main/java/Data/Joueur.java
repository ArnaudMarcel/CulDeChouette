/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import javax.persistence.Entity;

/**
 *
 * @author darra
 */
@Entity
public class Joueur {
    int idJoueur;
    String pseudo;
    String mdp;
    int age;
    String sexe;
    String ville;
    int nbParties;
    int nbVictoires;
    float nbMoyVictoires;
    float scoreMoyen;
    float moySuitesGagnees;
    float moyChouettesVelutesPerdues;
    
    
    public Joueur(String pseudo, String mdp, int age, String sexe, String ville) {
        this.pseudo = pseudo;
        this.mdp = mdp;
        this.age = age;
        this.sexe = sexe;
        this.ville = ville;
    }
    
    public String get_pseudo(int id) {
        return this.pseudo;
    }
}
