package hotel.controllers;

import com.toedter.calendar.JCalendar;
import hotel.model.Client;
import hotel.model.Habitacio;
import hotel.model.Hotel;
import hotel.model.Reserva;
import hotel.views.Finestra;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Controller {

    private Finestra view;
    private Hotel hotel;

    public Controller(Finestra view, Hotel hotel) {
        this.view = view;
        this.hotel = hotel;
    }

    public void afegirDades(){
        Client client = new Client("48042164v", "arnau", "llopart");
        Client client2 = new Client("00000000v", "Berta", "Torras");
        Habitacio hab1 = new Habitacio(3, 2);
        Habitacio hab2 = new Habitacio(1, 3);
        hotel.afegirHabitacio(hab1);
        hotel.afegirHabitacio(hab2);
        hotel.afegirReservaPendent(new Reserva(client, 3, 2, LocalDate.now(), LocalDate.now().plusDays(2), hab1));
        hotel.afegirReservaConfirmada(new Reserva(client2, 1, 1, LocalDate.now(), LocalDate.now().plusDays(1), hab2));
        
    }

    public boolean validDni(String dni) {
        boolean valid = false;
        String lletres[] = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};
        if (dni.matches("[\\d]{8}[a-zA-z]") && valorLletraDni(dni) >= 0 && valorLletraDni(dni) < lletres.length) {
            if (String.valueOf(dni.charAt(8)).equalsIgnoreCase(lletres[valorLletraDni(dni)])) {
                valid = true;
            }
        }
        return valid;
    }

    public int valorLletraDni(String dni) {
        return Integer.parseInt(dni.substring(0, 8)) % 23;
    }

    public boolean onlyLetters(String string) {
        return string.matches("[a-zA-ZÀ-ú ]{2,}");
    }

    public boolean onlyNumbers(String string) {
        return string.matches("[0-9]{1,2}");
    }

    public boolean totsCampsValids(Component[] components) {
        boolean valid = true;
        for (Component component : components) {
            if (component instanceof JTextField || component instanceof JCalendar) {
                switch (component.getName()) {
                    case "JTextFieldDni":
                        if (!validDni(view.jtfDNI.getText())) {
                            valid = false;
                            break;
                        }
                        break;

                    case "JTextFieldNom":
                        if (!onlyLetters(view.jtfNom.getText())) {
                            valid = false;
                            break;
                        }
                        break;

                    case "JTextFieldCognom":
                        if (!onlyLetters(view.jtfCognoms.getText())) {
                            valid = false;
                            break;
                        }
                        break;

                    case "JTextFieldNumPers":
                        if (!onlyNumbers(view.jtfNumPers.getText())) {
                            valid = false;
                            break;
                        }
                        break;

                    case "JTextFieldNumNits":
                        if (!onlyNumbers(view.jtfNumNits.getText())) {
                            valid = false;
                            break;
                        }
                        break;

                }
            }
        }
        return valid;
    }

    public LocalDate DataEntrada(long ms) {
        LocalDate entrada = Instant.ofEpochMilli(ms).atZone(ZoneId.systemDefault()).toLocalDate();
        return entrada;
    }

    public void ComprovarCampsClient(Component[] components) {
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
                view.jbReserva.setEnabled(totsCampsValids(components));
                if (e.getComponent().equals(view.jtfDNI)) {
                    if (validDni(view.jtfDNI.getText())) {
                        view.jlDniIcon.setIcon(view.iiValid);
                    } else {
                        view.jlDniIcon.setIcon(view.iiNoValid);
                    }
                } else if (e.getComponent().equals(view.jtfNom)) {
                    if (onlyLetters(view.jtfNom.getText())) {
                        view.jlNomIcon.setIcon(view.iiValid);
                    } else {
                        view.jlNomIcon.setIcon(view.iiNoValid);
                    }
                } else if (e.getComponent().equals(view.jtfCognoms)) {
                    if (onlyLetters(view.jtfCognoms.getText())) {
                        view.jlCognomsIcon.setIcon(view.iiValid);
                    } else {
                        view.jlCognomsIcon.setIcon(view.iiNoValid);
                    }
                } else if (e.getComponent().equals(view.jtfNumPers)) {
                    if (onlyNumbers(view.jtfNumPers.getText())) {
                        view.jlNumPersIcon.setIcon(view.iiValid);
                    } else {
                        view.jlNumPersIcon.setIcon(view.iiNoValid);
                    }
                } else if (e.getComponent().equals(view.jtfNumNits)) {
                    if (onlyNumbers(view.jtfNumNits.getText())) {
                        view.jlNumNitsIcon.setIcon(view.iiValid);
                    } else {
                        view.jlNumNitsIcon.setIcon(view.iiNoValid);
                    }
                }
            }
        };
        view.jtfDNI.addKeyListener(listener);
        view.jtfNom.addKeyListener(listener);
        view.jtfCognoms.addKeyListener(listener);
        view.jtfNumPers.addKeyListener(listener);
        view.jtfNumNits.addKeyListener(listener);
    }

    public void listenerBotoNomHotel() {
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hotel.setNomHotel(view.jtfNomHotel.getText());
                view.CanviarTitolFinestra(hotel.getNomHotel());
                view.jtfNomHotel.setText(" ");
                JOptionPane.showMessageDialog(null, "El nom de l'hotel ha estat canviat");
            }
        };
        view.jbGuardaHotel.addActionListener(listener);
    }

    public void listenerBotoReservar() {
        ActionListener reserva = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reserva reserva = new Reserva();
                if (!hotel.clientExisteix(view.jtfDNI.getText())) {
                    hotel.afegirClient(view.jtfDNI.getText().toUpperCase(), view.jtfNomClient.getText(), view.jtfCognoms.getText());
                }
                reserva.setClient(hotel.getObjectClient(view.jtfDNI.getText()));
                reserva.setNumPersones(Integer.parseInt(view.jtfNumPers.getText()));
                reserva.setNumNits(Integer.parseInt(view.jtfNumNits.getText()));
                reserva.setEntrada(DataEntrada(view.jcDataEntrada.getDate().getTime()));
                reserva.setSortida(DataEntrada(view.jcDataEntrada.getDate().getTime()).plusDays(Integer.parseInt(view.jtfNumNits.getText())));
                Habitacio assignat = hotel.numeroHabitacio(reserva);    
                if(assignat!=null){
                reserva.setHabitacio(assignat);
                hotel.afegirReservaPendent(reserva);
                view.modelResPend.addRow(reserva.arrayReservaPendent());
                view.clearJTFClient();
                JOptionPane.showMessageDialog(null, "La reserva ha estat feta correctament");
                } else{
                JOptionPane.showMessageDialog(null," Error, no hi ha habitacions disponibles per aquests dies");
                }
                view.clearJTFClient();
                
            }
        };
        view.jbReserva.addActionListener(reserva);
    }

    public void listenerJTFHabitacio() {
        KeyListener habitacio = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                view.jbGuardaReg.setEnabled(false);
                if (onlyNumbers(view.jtfNum.getText()) && onlyNumbers(view.jtfPers.getText())) {
                    view.jbGuardaReg.setEnabled(true);
                }
            }
        };
        view.jtfNum.addKeyListener(habitacio);
        view.jtfPers.addKeyListener(habitacio);
    }

    public void listenerBotoReservarHabitacio() {
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Habitacio nova = hotel.habitacioExisteix(Integer.parseInt(view.jtfNum.getText()));
                if (nova != null) {
                    int novaCapacitat = nova.getNumPers()+(Integer.parseInt(view.jtfPers.getText()));
                    int opcio = JOptionPane.showConfirmDialog(null, "El númeor d'habitació ja existeix. -Capacitat actual: " + nova.getNumPers() + " persones    -Nova capacitat: "+novaCapacitat+" persones. \nEstàs segur que ho desitges canviar?" );
                    switch (opcio){
                        case 0:
                            hotel.canviarMida(nova.getNumHab(), novaCapacitat);
                            break;
                        case 1:
                             break;
                        case 2: 
                             break;
                    }
                } else {
                    Habitacio habitacio = new Habitacio(Integer.parseInt(view.jtfNum.getText()), Integer.parseInt(view.jtfPers.getText()));
                    hotel.afegirHabitacio(habitacio);
                    JOptionPane.showMessageDialog(null, "L'habitació número " + view.jtfNum.getText() + " ha estat afegida");
                }
                view.clearRegHab();
            }
        };
        view.jbGuardaReg.addActionListener(listener);
    }
    
    public void listenerReservaPendent(){
        MouseListener confirmar= new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                    System.out.println(view.jtResPend.rowAtPoint(e.getPoint()));
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };
        view.jtResPend.addMouseListener(confirmar);
    }
}
