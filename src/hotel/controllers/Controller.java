
package hotel.controllers;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author arnau.llopart
 */
public class Controller extends JFrame {
    public static boolean validDni(String dni){
        boolean valid = false;
        String lletres [] = {"T","R","W","A","G","M","Y","F","P","D","X","B","N","J","Z","S","Q","V","H","L","C","K","E"}; 
        if(dni.matches("[\\d]{8}[a-zA-z]") && valorLletraDni(dni)>=0 && valorLletraDni(dni)<lletres.length){
            if(String.valueOf(dni.charAt(8)).equalsIgnoreCase(lletres[valorLletraDni(dni)])){
                valid = true;
            } 
        } 
        return valid;
    }
    public static int valorLletraDni(String dni){
        return Integer.parseInt(dni.substring(0,8))%23;
    }
    public static boolean onlyLetters(String string){
        return string.matches("[a-zA-Z]{1,}");
    }
    
    public static boolean onlyNumbers(String string){
        return string.matches("[0-9]{1,}");
    }
    
}
