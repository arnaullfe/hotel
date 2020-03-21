
package hotel.controllers;

import com.toedter.calendar.JCalendar;
import hotel.Principal;
import hotel.views.Finestra;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JTextField;
import static hotel.views.Finestra.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import model.Reserva;

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
        return string.matches("[a-zA-ZÀ-ú ]{2,}");
    }
    
    public static boolean onlyNumbers(String string){
        return string.matches("[0-9]{1,2}");
    }
    
   public static boolean totsCampsValids(Component [] components){
       boolean valid = true;
       for(Component component : components){
            if(component instanceof JTextField || component instanceof JCalendar){
                switch(component.getName()){
                    case "JTextFieldDni":
                        if(!validDni(jtfDNI.getText())){
                            valid = false;
                            break;
                        }
                    break;
                    
                    case "JTextFieldNom":
                        if(!onlyLetters(jtfNom.getText())){
                            valid = false;
                            break;
                        }
                    break;
                    
                    case "JTextFieldCognom":
                        if(!onlyLetters(jtfCognoms.getText())){
                            valid = false;
                            break;
                        }        
                     break;
                     
                    case "JTextFieldNumPers":
                        if(!onlyNumbers(jtfNumPers.getText())){
                            valid=false;
                            break;
                        }
                    break;
                        
                    case "JTextFieldNumNits":
                        if(!onlyNumbers(jtfNumNits.getText())){
                            valid=false;
                            break;
                        }
                    break;
                    
                }
           }           
        }
       return valid;
   }
 
   public static LocalDate DataEntrada(long ms){
       LocalDate entrada = Instant.ofEpochMilli(ms).atZone(ZoneId.systemDefault()).toLocalDate();
       return entrada;
   }
   
   public static void ComprovarCampsClient(Component [] components){
    KeyListener listener;
        listener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                jbReserva.setEnabled(totsCampsValids(components));
                if(e.getComponent().equals(Finestra.jtfDNI)){
                    if(Controller.validDni(Finestra.jtfDNI.getText())){
                        jlDniIcon.setIcon(Finestra.iiValid);
                    }else{
                       jlDniIcon.setIcon(Finestra.iiNoValid);
                    }
                } else if(e.getComponent().equals(Finestra.jtfNom)){
                    if(Controller.onlyLetters(Finestra.jtfNom.getText())){
                        jlNomIcon.setIcon(Finestra.iiValid);
                    } else{
                        jlNomIcon.setIcon(Finestra.iiNoValid);
                    }
                } else if(e.getComponent().equals(Finestra.jtfCognoms)){
                    if(Controller.onlyLetters(jtfCognoms.getText())){
                        jlCognomsIcon.setIcon(Finestra.iiValid);
                    } else{
                        jlCognomsIcon.setIcon(Finestra.iiNoValid);
                    }
                } else if(e.getComponent().equals(Finestra.jtfNumPers)){
                    if(Controller.onlyNumbers(Finestra.jtfNumPers.getText())){
                        jlNumPersIcon.setIcon(Finestra.iiValid);
                    } else{
                        Finestra.jlNumPersIcon.setIcon(Finestra.iiNoValid);
                    }
                } else if(e.getComponent().equals(Finestra.jtfNumNits)){
                    if(Controller.onlyNumbers(Finestra.jtfNumNits.getText())){
                        jlNumNitsIcon.setIcon(Finestra.iiValid);
                    } else{
                        jlNumNitsIcon.setIcon(Finestra.iiNoValid);
                    }
                }
            }
        };
        jtfDNI.addKeyListener(listener);
        jtfNom.addKeyListener(listener);
        jtfCognoms.addKeyListener(listener);
        jtfNumPers.addKeyListener(listener);
        jtfNumNits.addKeyListener(listener);
    }

   public static void ListenerBotoNomHotel(){
       ActionListener listener = new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
              Principal.hotel.setNomHotel(jtfNomHotel.getText());
              Principal.f.setTitle(jtfNomHotel.getText());
              jtfNomHotel.setText(" ");
           }
       };
   jbGuardaHotel.addActionListener(listener);
   
   }
   
   public static void ListenerBotoReservar(){
       ActionListener reserva = new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               Reserva reserva = new Reserva();
               if(!Principal.hotel.clientExisteix(jtfDNI.getText())){
                   Principal.hotel.afegirClient(jtfDNI.getText(), jtfNomClient.getText(), jtfCognoms.getText());
               }
               reserva.setClient(Principal.hotel.getObjectClient(jtfDNI.getText()));
               reserva.setNumPersones(Integer.parseInt(jtfNumPers.getText()));
               reserva.setNumNits(Integer.parseInt(jtfNumNits.getText()));
               reserva.setEntrada(DataEntrada(jcDataEntrada.getDate().getTime()));
               reserva.setSortida(DataEntrada(jcDataEntrada.getDate().getTime()).plusDays(Integer.parseInt(jtfNumNits.getText())));
               modelResPend.addRow(reserva.arrayReservaPendent());
               clearJTFClient();
           }
       };
       jbReserva.addActionListener(reserva);
   }
   
   public static void clearJTFClient(){
       jtfDNI.setText("");
       jtfNom.setText("");
       jtfCognoms.setText("");
       jtfNumNits.setText("");
       jtfNumPers.setText("");
       jlDniIcon.setIcon(null);
       jlNomIcon.setIcon(null);
       jlCognomsIcon.setIcon(null);
       jlNumNitsIcon.setIcon(null);
       jlNumPersIcon.setIcon(null);
   }
   
   
  
                    
                  
                   
                
                    
                
                    
                    
               
}

