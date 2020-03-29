/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.model;

/**
 *
 * @author arnau1
 */
public class Habitacio {
    int numHab,numPers;

    public Habitacio(int num, int pers){
        this.numHab = num;
        this.numPers = pers;
    }

    public int getNumHab() {
        return numHab;
    }

    public int getNumPers() {
        return numPers;
    }

    public void setNumPers(int numPers) {
        this.numPers = numPers;
    }
    
    
    
    
}
