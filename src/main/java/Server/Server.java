/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import DB.JoueurJPA;
import DB.LancerJPA;
import DB.PartieJPA;
import DB.PossederJPA;
import Data.Joueur;
import Data.Lancer;
import Data.Partie;
import Data.Posseder;


/**
 *
 * @author arnaud
 */

public class Server {
    public static void main(String[] args) {
        JoueurJPA jJPA = new JoueurJPA();       
        LancerJPA lJPA = new LancerJPA();
        PossederJPA pJPA = new PossederJPA();
        PartieJPA parJPA = new PartieJPA();
        
        Joueur j = new Joueur(new Long(1), "Jean", "test", 30, "Masculin", "Pau",2, 3, 3, 3, 3, 10);
        Lancer l = new Lancer(new Long(1), 3, 5, 6, true, new Long(1), new Long(1));
        Partie par = new Partie(new Long(1), "20/10/2021 20:30", 200);
        Posseder p = new Posseder(new Long(1), new Long(1), 200, 4, 3, true);
        
        jJPA.create(j);
        lJPA.create(l);
        pJPA.create(p);
        parJPA.create(par);
        
        System.out.println("JOUEUR : " + jJPA.find(j.getIdJoueur()));
        System.out.println("LANCER : " + lJPA.find(l.getIdLancer()));
        System.out.println("POSSEDER : " + pJPA.find(p));
        System.out.println("PARTIE : " + parJPA.find(par.getIdPartie()));
        
        j = new Joueur(new Long(1), "CHANGER", "BRUHHH", 30, "Masculin", "Pau",2, 3, 3, 3, 3, 10);
        l = new Lancer(new Long(1), 100, 100, 100, false, new Long(1), new Long(1));
        par = new Partie(new Long(1), "20/10/2029 22:30", 0);
        p = new Posseder(new Long(1), new Long(1), 0, 0, 0, false);
        
        jJPA.update(j);
        lJPA.update(l);
        pJPA.update(p);
        parJPA.update(par);

        System.out.println("JOUEURS : " + jJPA.findAll());
        System.out.println("LANCERS : " + lJPA.findAll());
        System.out.println("POSSEDERS : " + pJPA.findAll());
        System.out.println("PARTIES : " + parJPA.findAll());
        
        
        jJPA.delete(j);
        lJPA.delete(l);
        pJPA.delete(p);
        parJPA.delete(par);
    }
}