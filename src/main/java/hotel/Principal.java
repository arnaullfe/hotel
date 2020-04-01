/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel;

import hotel.controllers.Controller;
import hotel.model.Hotel;
import hotel.views.Finestra;

/**
 *
 * @author arnau.llopart
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Finestra vista = new Finestra();
        Hotel model = new Hotel();
        Controller ctrl = new Controller(vista,model);

        //ctrl.afegirDades();

    }
    
}
