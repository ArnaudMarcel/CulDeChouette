/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import DB.JoueurJPA;
import DB.LancerJPA;
import DB.PartieJPA;
import DB.PossederJPA;
import Server.UserControler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Arnaud
 */
public class PartieControler {

    private Partie p;
    private ArrayList<Joueur> joueurs;
    private HashMap<Joueur, Posseder> points = new HashMap();
    private UserControler uc;
    private int index;
    private boolean action = false;
    private HashMap<Joueur, Boolean> suite = new HashMap();

    public PartieControler(Partie p, ArrayList<Joueur> joueurs) {
        Random rand = new Random();
        this.p = p;
        this.joueurs = joueurs;
        this.joueurs.forEach(j -> {
            this.points.put(j, new Posseder());
        });
        this.uc = new UserControler();
        this.index = 0;
        PartieJPA pj = new PartieJPA();
        this.joueurs.forEach((Joueur j) -> {
            this.suite.put(j, false);
        });
        p.setIdPartie(Long.valueOf(rand.nextInt(9999999)));
        pj.create(p);
    }

    public void start() throws IOException {
        this.tour(this.joueurs.get(this.index));
    }

    private boolean unGagnant() {
        boolean fini = false;
        for (Joueur j : this.joueurs) {
            if (this.points.get(j).getNbPoints() >= this.p.getNbPointsAAtteindrePartie()) {
                this.points.get(j).setEstGagnant(true);
                j.setNbMoyVictoires(1 + j.getNbVictoires());
                fini = true;
                break;
            }
        }
        return fini;
    }

    public void tourSuivant() throws IOException {
        this.action = false;
        if (!this.unGagnant()) {
            this.joueurSuivant();
            this.tour(this.joueurs.get(this.index));
        } else {
            this.saugarderPartie();
            this.uc.partieTerminee(this.gagnant(), this.joueurs, this.p);
        }

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

            if (l.getInteraction()) {
                this.action = true;
            }
        }

        lj.create(l);
        return l;
    }

    private void addPoints(Joueur j, Lancer l) {
        Posseder pts = this.points.get(j);
        pts.addPoints(this.nbPoints(l));
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
                        a = (l.getValeurDes3()) * (l.getValeurDes3());
                    }
                }
            }
        }
        return a;
    }

    private void joueurSuivant() {
        this.index++;
        if (this.index == this.joueurs.size()) {
            this.index = 0;
        }
    }

    public void interactionJoueur(Action a) throws IOException {
        switch (a.getNom()) {
            case "Suite":
                this.suite(a);
                break;

            case "ChouetteVelute":
                this.ChouetteVelute(a);
                break;
        }
    }

    private void suite(Action a) throws IOException {
        JoueurJPA jp = new JoueurJPA();
        if (this.action) {
            Joueur j = jp.find(a.getPseudoJoueur());
            this.points.get(j).ajoutSuiteGagnee();
            this.suite.put(j, true);
            if (this.estDernier()) {
                Posseder pts = this.points.get(j);
                pts.addPoints(-10);
                pts.SuitePerdue();
                this.points.put(j, pts);
                this.joueurs.forEach((Joueur jTamp) -> {
                    this.suite.put(jTamp, false);
                });
                this.tourSuivant();
            }
        }
    }

    private void ChouetteVelute(Action a) throws IOException {
        JoueurJPA jp = new JoueurJPA();
        if (this.action) {
            this.action = false;
            Joueur j = jp.find(a.getPseudoJoueur());
            Posseder pts = this.points.get(j);
            pts.addPoints(a.getValeurCul() * a.getValeurCul());
            this.updateNbChouettesVeluesPerdues(j);
            this.tourSuivant();
        }
    }

    private boolean estDernier() {
        return !(this.suite.values().contains(false));
    }

    private void saugarderPartie() {
        this.joueurs.forEach((Joueur j) -> {
            PossederJPA pj = new PossederJPA();
            JoueurJPA jp = new JoueurJPA();
            Posseder p1 = PartieControler.this.points.get(j);
            this.updateJoueur(j, p1);
            jp.update(j);
            p1.setIdJoueur(j.getIdJoueur());
            p1.setIdPartie(PartieControler.this.p.getIdPartie());
            pj.create(p1);
        });
    }

    private Joueur gagnant() {
        Joueur gagnant = null;
        for (Joueur j : this.joueurs) {
            if (this.points.get(j).isEstGagnant()) {
                gagnant = j;
            }
        }        
        return gagnant;
    }

    public ArrayList getPoints() {
        ArrayList pts = new ArrayList();
        this.joueurs.forEach((Joueur j) -> {
            ArrayList tamp = new ArrayList();
            tamp.add(j.getPseudoJoueur());
            tamp.add(this.points.get(j).getNbPoints());
            pts.add(tamp);
        });
        return pts;
    }
    
    private void updateJoueur(Joueur j, Posseder p) {
        if (j.equals(this.gagnant())) {
            j.setNbVictoires(1 + j.getNbVictoires());
        } 
        j.setNbParties(1 + j.getNbParties());
        j.setNbMoyVictoires(j.getNbVictoires() / j.getNbParties());
        j.setScoreMoyen((j.getScoreMoyen() * (j.getNbParties() - 1) + p.getNbPoints()) / j.getNbParties());
        j.setMoySuitesGagnees((j.getMoySuitesGagnees() * (j.getNbParties() - 1) + p.getNbSuitesGagnees()) / j.getNbParties());        
        j.setMoyChouettesVelutesPerdues((j.getMoyChouettesVelutesPerdues()* (j.getNbParties() - 1) + p.getNbChouettesVelutesPerdues()) / j.getNbParties());
    }
    
    private void updateNbChouettesVeluesPerdues(Joueur j) {
        this.joueurs.forEach((Joueur jTamp) -> {
            if (!jTamp.equals(j)) {
                Posseder pt = this.points.get(j);
                pt.setNbChouettesVelutesPerdues(1 + pt.getNbChouettesVelutesPerdues());
            }
        });
    }
}
