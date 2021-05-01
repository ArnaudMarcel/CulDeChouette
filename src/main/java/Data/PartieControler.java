/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import DB.LancerJPA;
import DB.PartieJPA;
import Server.UserControler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author darra
 */
public class PartieControler {

    private Partie p;
    private ArrayList<Joueur> joueurs;
    private HashMap<Joueur, Posseder> points = new HashMap();
    private UserControler uc;
    private int index;

    public PartieControler(Partie p, ArrayList<Joueur> joueurs) {
        Random rand = new Random();
        this.p = p;
        this.joueurs = joueurs;
        this.joueurs.forEach(j -> {
            Posseder poss = new Posseder();
            this.points.put(j, poss);
        });
        this.uc = new UserControler();
        this.index = 0;
        PartieJPA pj = new PartieJPA();
        p.setIdPartie(Long.valueOf(rand.nextInt(9999999)));
        pj.create(p);
    }

    public void start() throws IOException {
        this.tour(this.joueurs.get(this.index));
    }

    private boolean unGagnant() {
        boolean fini = false;
        for (Joueur j : this.joueurs) {
            if (this.points.get(j).getNbPoints() >= this.p.nbPointsAAtteindrePartie) {
                this.points.get(j).setEstGagnant(true);
                fini = true;
                break;
            }
        }
        return fini;
    }
    
    public void tourSuivant() throws IOException {
        this.joueurSuivant();
        this.tour(this.joueurs.get(this.index));
    }

    private void tour(Joueur j) throws IOException {
        this.uc.tourJoueur(j);
    }

    public Lancer lancer(Joueur j) {
        LancerJPA lj = new LancerJPA();
        Lancer l = null;
        if (j.equals(this.joueurs.get(this.index))) {
            l = new Lancer(j.getIdJoueur(), p.getIdPartie());
            this.addPoints(j, l);
        }
        lj.create(l);
        return l;
    }

    private void addPoints(Joueur j, Lancer l) {
        this.points.get(j).setNbPoints(this.nbPoints(l));
    }
    
    private int nbPoints(Lancer l) {
        int a = 0;
        if (l.isChouetteVelute()) {
            a = 40 + 10 * l.getValeurDes1();
        } else {
            if (l.isCulDeChouette()) {
                a = 40 + 10 * l.getValeurDes1();              
            } else {
                if (l.isChouette()) {
                    a = l.getValeurDes1() * l.getValeurDes2();
                } else {
                    if (l.isVelute()) {
                        a = (l.getValeurDes1() + l.getValeurDes2()) * (l.getValeurDes1() + l.getValeurDes2());
                    }
                }
            }
        }
        return a;
    }
    
    private void joueurSuivant() {
        this.index++;
        if (this.index == this.joueurs.size()) this.index = 0;
    }
}
