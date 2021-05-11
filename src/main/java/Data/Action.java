/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 *
 * @author arnaud
 */
public class Action {
    private String pseudoJoueur;
    private String nom;
    private String reponse;
    private int valeurCul;

    public Action(String pseudoJoueur, String nom, String reponse, int valeurCul) {
        this.pseudoJoueur = pseudoJoueur;
        this.nom = nom;
        this.reponse = reponse;
        this.valeurCul = valeurCul;
    }

    public int getValeurCul() {
        return valeurCul;
    }

    public void setValeurCul(int valeurCul) {
        this.valeurCul = valeurCul;
    }

    public String getPseudoJoueur() {
        return pseudoJoueur;
    }

    public void setPseudoJoueur(String pseudoJoueur) {
        this.pseudoJoueur = pseudoJoueur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }
    
    
    
}
