package hotel.controllers;

import com.toedter.calendar.JCalendar;
import hotel.model.*;
import hotel.views.Finestra;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Controller {

    private Finestra view;
    private Hotel hotel;
    private int idReserva = 1;
    private Fitxer fitxer;

    public Controller(Finestra view, Hotel hotel) {
        this.view = view;
        this.hotel = hotel;
        listeners();
        dadesFitxers();

    }

    public  void listeners(){
        ComprovarCampsClient(view.jpClients.getComponents());
        listenerBotoReservar();
        listenerBotoNomHotel();
        listenerJTFHabitacio();
        listenerBotoAfegirHabitacio();
        listenerReservaPendent();
        listenerToggleButtonEntradesSortides();
        listenerJdcReservesConfirmades();
        listenerjtfNomClient();
        listenerJlistClients();
        listenerJlistReserves();
        listenerJbuttonElimina();
    }

    public void dadesFitxers(){
        fitxer = new Fitxer();
        fitxerNomHotel();
        fitxerHabitacions();
        fitxerClients();
        fitxerReservesPendents();
        fitxerReservesConfirmades();
    }

    public void afegirDades() {
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
                        }
                        break;

                    case "JTextFieldNom":
                        if (!onlyLetters(view.jtfNom.getText())) {
                            valid = false;
                        }
                        break;

                    case "JTextFieldCognom":
                        if (!onlyLetters(view.jtfCognoms.getText())) {
                            valid = false;
                        }
                        break;

                    case "JTextFieldNumPers":
                        if (!onlyNumbers(view.jtfNumPers.getText())) {
                            valid = false;
                        }
                        break;

                    case "JTextFieldNumNits":
                        if (!onlyNumbers(view.jtfNumNits.getText())) {
                            valid = false;
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
                        if (hotel.clientExisteix(view.jtfDNI.getText())) {
                            int opcio = JOptionPane.showConfirmDialog(view, "S'ha detactat que aquest client ja esta registrat, vols autocompletar els altres camps?");
                            switch (opcio) {
                                case 0:
                                    Client client = hotel.getObjectClient(view.jtfDNI.getText());
                                    view.jtfNom.setText(client.getNom());
                                    view.jtfCognoms.setText(client.getCognoms());
                                    break;
                                default:
                                    break;
                            }

                        }

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
                fitxer.escriureFitxer(fitxer.getHotel(),view.jtfNomHotel.getText());
                fitxerNomHotel();
                view.jtfNomHotel.setText("");
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
                    Client cli = new Client(view.jtfDNI.getText().toUpperCase(), view.jtfNom.getText(), view.jtfCognoms.getText());
                    hotel.afegirClient(cli);
                    String dadesClient= view.jtfDNI.getText().toUpperCase()+"-"+view.jtfNom.getText()+"-"+view.jtfCognoms.getText();
                    fitxer.updateFitxer(fitxer.getClients(),dadesClient,null);
                }
                reserva.setIdReserva(idReserva);
                reserva.setClient(hotel.getObjectClient(view.jtfDNI.getText()));
                reserva.setNumPersones(Integer.parseInt(view.jtfNumPers.getText()));
                reserva.setNumNits(Integer.parseInt(view.jtfNumNits.getText()));
                reserva.setEntrada(DataEntrada(view.jcDataEntrada.getDate().getTime()));
                reserva.setSortida(DataEntrada(view.jcDataEntrada.getDate().getTime()).plusDays(Integer.parseInt(view.jtfNumNits.getText())));
                Habitacio assignat = hotel.numeroHabitacio(reserva);
                if (assignat != null) {
                    reserva.setHabitacio(assignat);
                    hotel.afegirReservaPendent(reserva);
                    view.modelResPend.addRow(reserva.arrayReservaPendent());
                    view.clearJTFClient();
                    updateJListReserves();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String dadesReserva=reserva.getIdReserva()+"-"+reserva.getClient().getDni()+"-"+reserva.getNumPersones()+"-"+reserva.getNumNits()+"-"+assignat.getNumHab()+"-"+dtf.format(reserva.getEntrada())+"-"+dtf.format(reserva.getSortida());
                    fitxer.updateFitxer(fitxer.getReservesPendents(),dadesReserva,null);
                    JOptionPane.showMessageDialog(view, "La reserva ha estat feta correctament");
                    idReserva++;
                } else {
                    JOptionPane.showMessageDialog(view, " Error, no hi ha habitacions disponibles per aquests dies");
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

    public void listenerBotoAfegirHabitacio() {
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Habitacio nova = hotel.habitacioExisteix(Integer.parseInt(view.jtfNum.getText()));
                if (nova != null) {
                    int opcio = JOptionPane.showConfirmDialog(null, "El númerod'habitació ja existeix. -Capacitat actual: " + nova.getNumPers() + " persones    -Nova capacitat: " + view.jtfPers.getText() + " persones. \nEstàs segur que ho desitges canviar?");
                    switch (opcio) {
                        case 0:
                            String novaLinia = nova.getNumHab()+"-"+view.jtfPers.getText();
                            String liniaAnterior = nova.getNumHab()+"-"+nova.getNumPers();
                            fitxer.updateFitxer(fitxer.getHabitacions(),novaLinia,liniaAnterior);
                            hotel.canviarMida(nova.getNumHab(), Integer.parseInt(view.jtfPers.getText()));
                            break;
                        default:
                            break;
                    }
                } else {
                    Habitacio habitacio = new Habitacio(Integer.parseInt(view.jtfNum.getText()), Integer.parseInt(view.jtfPers.getText()));
                    hotel.afegirHabitacio(habitacio);
                    fitxer.updateFitxer(fitxer.getHabitacions(),habitacio.getNumHab()+"-"+habitacio.getNumPers(),null);
                    JOptionPane.showMessageDialog(null, "L'habitació número " + view.jtfNum.getText() + " ha estat afegida");
                }
                view.clearRegHab();
            }
        };
        view.jbGuardaReg.addActionListener(listener);
    }

    public void listenerReservaPendent() {
        String[] opcions = {"Confirmar la reserva", "Descartar la reserva", "Cancel·lar"};
        MouseListener confirmar = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String opcio = (String) JOptionPane.showInputDialog(view, "Selecciona el que vols fer amb la reserva:", opcions[0], JOptionPane.DEFAULT_OPTION, null, opcions, opcions[0]);
                    if (opcio != null) {
                        int posicio = view.jtResPend.rowAtPoint((e.getPoint()));
                        Reserva confirmada = hotel.getLlistaReservesPendents().get(posicio);
                        switch (opcio) {
                            case "Confirmar la reserva":
                                hotel.novaReservaConfirmada(confirmada);
                                hotel.eliminarRerservaPendent(posicio);
                                view.modelResPend.removeRow(posicio);
                                fitxer.updateFitxer(fitxer.getReservesConfirmades(),dadesFitxerReserva(confirmada),null);
                                fitxer.eliminarLinia(fitxer.getReservesPendents(),dadesFitxerReserva(confirmada));
                                updateTaulaReservesonfirmades();
                                JOptionPane.showMessageDialog(view, "La reserva s'ha confirmat correctament!");
                                break;
                            case "Descartar la reserva":
                                hotel.eliminarRerservaPendent(posicio);
                                view.modelResPend.removeRow(posicio);
                                fitxer.eliminarLinia(fitxer.getReservesPendents(),dadesFitxerReserva(confirmada));
                                updateJListReserves();
                                JOptionPane.showMessageDialog(view, "La reserva s'ha descartat correctament!");
                                break;
                            default:
                                break;
                        }

                    }
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

    public void listenerToggleButtonEntradesSortides() {
        ChangeListener listener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateTaulaReservesonfirmades();
            }
        };
        view.jtbSwitch.addChangeListener(listener);
    }

    public void afegirArrayListToTaula(ArrayList<Reserva> al) {
        for (Reserva a : al) {
            view.modelResConf.addRow(a.arrayReservaPendent());
        }
    }

    public void updateTaulaReservesonfirmades() {
        view.modelResConf.setRowCount(0);
        if (view.jtbSwitch.isSelected()) {
            view.jtbSwitch.setText("Sortides");
            if (view.jdcResConf.getDate() != null) {
                ArrayList<Reserva> al = hotel.mostrarSortides(DataEntrada(view.jdcResConf.getDate().getTime()));
                afegirArrayListToTaula(al);
            }
        } else {
            view.jtbSwitch.setText("Entrades");
            if (view.jdcResConf.getDate() != null) {
                ArrayList<Reserva> al = hotel.mostrarEntrades(DataEntrada(view.jdcResConf.getDate().getTime()));
                afegirArrayListToTaula(al);
            }
        }
    }

    public void listenerJdcReservesConfirmades() {
        view.jdcResConf.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                if (view.jdcResConf.getDate() != null) {
                    updateTaulaReservesonfirmades();
                }
            }
        });
    }

    public void listenerjtfNomClient() {
        KeyListener listener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                view.modelNomClient.clear();
                if (!view.jtfNomClient.getText().isEmpty()) {
                    afegirClientsJList(hotel.mostraClients(view.jtfNomClient.getText().toUpperCase()));
                }
            }
        };
        view.jtfNomClient.addKeyListener(listener);
    }

    public void afegirClientsJList(ArrayList<Client> al) {
        for (Client a : al) {
            view.modelNomClient.addElement(a);
        }
    }

    public void listenerJlistClients() {
        ListSelectionListener listener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateJListReserves();
            }
        };
        view.jlistnomClient.addListSelectionListener(listener);
    }

    public void afegirReservesClientJList(ArrayList<Reserva> al) {
        for (Reserva a : al) {
            view.modelReservesClient.addElement(a);
        }
    }

    public void listenerJlistReserves() {
        ListSelectionListener listener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                view.jbElimina.setEnabled(true);
            }
        };
        view.jlistReservesClient.addListSelectionListener(listener);
    }

    public void listenerJbuttonElimina() {
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcio = JOptionPane.showConfirmDialog(view, "Estàs segur de que vols eliminar la reserva?");
                switch (opcio) {
                    case 0:
                        Reserva reserva = (Reserva) view.jlistReservesClient.getSelectedValue();
                        int posicio = hotel.getPosicioReservaPendent(reserva);
                        File f;
                        if (posicio >= 0) {
                            hotel.eliminarRerservaPendent(posicio);
                            f= fitxer.getReservesPendents();
                            updateReservesPendents();
                        } else {
                            posicio = hotel.getPosicioReservaConfirmada(reserva);
                            hotel.eliminarRerservaConfirmada(posicio);
                            f = fitxer.getReservesConfirmades();
                            updateTaulaReservesonfirmades();
                        }
                        fitxer.eliminarLinia(f,dadesFitxerReserva(reserva));
                        updateJListReserves();
                        JOptionPane.showMessageDialog(view, "La reserva s'ha eliminat correctament");
                    default:
                        break;

                }
            }
        };
        view.jbElimina.addActionListener(listener);
    }

    public void updateReservesPendents() {
        view.modelResPend.setRowCount(0);
        for (Reserva a : hotel.getLlistaReservesPendents()) {
            view.modelResPend.addRow(a.arrayReservaPendent());
        }
    }

    public void updateJListReserves() {
        view.modelReservesClient.clear();
        Client client = (Client) view.jlistnomClient.getSelectedValue();
        if (client != null) {
            afegirReservesClientJList(hotel.reservesClient(client));
        }
    }

    public void fitxerNomHotel(){
        if(fitxer.llegirFitxer(fitxer.getHotel()).size()>0){
            canviaNomHotel(fitxer.llegirFitxer(fitxer.getHotel()).get(0));
        } else{
            canviaNomHotel("RuralCirv");
            fitxer.escriureFitxer(fitxer.getHotel(),hotel.getNomHotel());
        }
    }
    public void canviaNomHotel(String nom){
        hotel.setNomHotel(nom);
        view.setTitle(hotel.getNomHotel());
    }

    public void fitxerHabitacions(){
        for(String a : fitxer.llegirFitxer(fitxer.getHabitacions())){
            String [] array = a.split("-");
            hotel.afegirHabitacio(new Habitacio(Integer.parseInt(array[0]),Integer.parseInt(array[1])));
        }

    }
    public void fitxerClients(){
        for(String a : fitxer.llegirFitxer(fitxer.getClients())){
            String array [] = a.split("-");
            hotel.afegirClient(new Client(array[0],array[1],array[2]));
            }

    }

    public void fitxerReservesPendents(){
        for(String a : fitxer.llegirFitxer(fitxer.getReservesPendents())){
            String array [] = a.split("-");
            hotel.afegirReservaPendent(ferReservaAmbArray(array));
            view.modelResPend.addRow(ferReservaAmbArray(array).arrayReservaPendent());
        }
    }

    public void fitxerReservesConfirmades(){
        for(String a : fitxer.llegirFitxer(fitxer.getReservesConfirmades())){
            String array [] = a.split("-");
            hotel.afegirReservaConfirmada(ferReservaAmbArray(array));
        }
        updateTaulaReservesonfirmades();
    }

    public Reserva ferReservaAmbArray(String [] array){
        Reserva reserva = new Reserva();
        reserva.setIdReserva(Integer.parseInt(array[0]));
        reserva.setClient(hotel.getObjectClient(array[1]));
        reserva.setNumPersones(Integer.parseInt(array[2]));
        reserva.setNumNits(Integer.parseInt(array[3]));
        reserva.setHabitacio(hotel.habitacioExisteix(Integer.parseInt(array[4])));
        String data [] = array[5].split("/");
        reserva.setEntrada(LocalDate.parse(data[2]+"-"+data[1]+"-"+data[0]));
        data = array[6].split("/");
        reserva.setSortida(LocalDate.parse(data[2]+"-"+data[1]+"-"+data[0]));
        return reserva;
    }
    public String dadesFitxerReserva(Reserva confirmada){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String reservaDades = confirmada.getIdReserva()+"-"+confirmada.getClient().getDni()+"-"+confirmada.getNumPersones()+"-"+confirmada.getNumNits()+"-"+confirmada.getHabitacio().getNumHab()+"-"+dtf.format(confirmada.getEntrada())+"-"+dtf.format(confirmada.getSortida());
        return reservaDades;
    }
}
