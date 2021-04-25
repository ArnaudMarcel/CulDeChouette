/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author darra
 */
public class PartieManager {
    private Map<Partie, ArrayList<Joueur>> parties;
    
    public PartieManager() {
        this.parties = new HashMap<>();
    }
    
    public PartieManager(Partie p, Joueur j) {
        this.parties = new HashMap<>();
        ArrayList<Joueur> TampJ = new ArrayList((Collection) j);
        this.parties.put(p, TampJ);
    }
    
    public void add(Partie p, Joueur j) {
        ArrayList<Joueur> HashJ = this.parties.get(p);
        if (HashJ == null) HashJ = new ArrayList(); 
        HashJ.add(j);
        this.parties.put(p, HashJ);        
    }
    
    public void add(Partie p, ArrayList<Joueur> j) {
        this.parties.put(p, j);
    }

    @Override
    public String toString() {
        return "PartieManager{" + "parties=" + parties + '}';
    }
    
    public ArrayList<Joueur> getJoueurs(Partie p) {
        return this.parties.get(p);
    }
    
    public ArrayList getPseudoJoueurs(Partie p) {
        ArrayList pseudo = new ArrayList();
        this.parties.get(p).forEach(j -> {
            pseudo.add(j.getPseudoJoueur());
        });
        return pseudo;
    }
    
}
