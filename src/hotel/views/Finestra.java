/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.views;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import hotel.Principal;
import hotel.controllers.Controller;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author arnau.llopart
 */
public class Finestra extends JFrame{
    int amplada;
    int altura;
    
    /*<-----------------FONTS------------------------->*/
    public Font titol = new Font("Dyuthi",Font.BOLD,40);
    public Font cos = new Font("Liberation Serif",Font.ITALIC,18);
    
    /*<-----------------PANELLS----------------------->*/
    public JPanel jpGestio, jpClients, jpBack;
    
     /*<-----------------GESTIÓ------------------------->*/
    public JLabel jlGestio,jlBack,jlResPend,jlResConf;
    public JTable jtResPend,jtResConf;
    public DefaultTableModel modelResPend,modelResConf;
    public JDateChooser jdcResConf;
    public JToggleButton jtbSwitch;
    /*<-----------------CLIENTS------------------------->*/
    public JLabel jlClients,jlDni,jlNom,jlCognoms,jlNumPers,jlNumNits,jlDataEntrada,jlNomIcon,jlDniIcon,jlCognomsIcon,jlNumPersIcon,jlNumNitsIcon;
    public ImageIcon iiValid,iiNoValid;
    public JTextField jtfDNI,jtfNom,jtfCognoms,jtfNumPers,jtfNumNits;
    public JCalendar jcDataEntrada;
    public JButton jbReserva;
    
    /*<----------------BACK------------------------------>*/
    public JLabel jlNomHotel,jlRegHab,jlNum,jlPers,jlConsRes,jlNomClient;
    public JTextField jtfNomHotel,jtfNum,jtfPers,jtfNomClient;
    public JButton jbGuardaHotel,jbGuardaReg,jbElimina;
    public JList jlistnomClient,jlistReservesClient;
    public DefaultListModel modelNomClient, modelReservesClient;
    
    
    public Finestra(){
    this.setVisible(true);
    this.setSize(1200,720);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    this.setResizable(true);
    this.getContentPane().setBackground(Color.BLACK);
    this.setLayout(null);
    iniciarComponents();
    dissenyGestio();
    dissenyClients();
    dissenyBack();
    omplirImageIcon();
    }
    
   public void iniciarComponents(){
       crearPanells();
   }
   
   public void crearPanells(){
    amplada = this.getWidth()/3;
    altura = this.getHeight();
        for(int i=0;i<3;i++){
            JPanel panell = new JPanel();
            panell.setBounds(amplada*i, 0, amplada-2, altura);
            panell.setLayout(null);
            this.getContentPane().add(panell);
        }
   }

    private void dissenyGestio() {
        jpGestio = (JPanel) this.getContentPane().getComponent(0);
        jlGestio = new JLabel("Gestió");
        jlGestio.setBounds(0, 30, jpGestio.getWidth(),40);
        jlGestio.setHorizontalAlignment(SwingConstants.CENTER);
        jlGestio.setFont(titol);
        jpGestio.add(jlGestio);
        
        jlResPend = new JLabel("Reserves pendents:");
        jlResPend.setBounds(20,100,200,30);
        jlResPend.setFont(cos);
        jpGestio.add(jlResPend);
       
        modelResPend = new DefaultTableModel();
        modelResPend.addColumn("DNI");
        modelResPend.addColumn("Entrada");
        modelResPend.addColumn("Sortida");
        modelResPend.addColumn("Persones");
        modelResPend.addColumn("Habitació");
        jtResPend = new JTable(modelResPend);
        jtResPend.setBounds(20,140,jpGestio.getWidth()-40,200);
        jtResPend.setEnabled(false);
        jpGestio.add(jtResPend);
        JScrollPane scroll = new JScrollPane(jtResPend,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(20,140,jtResPend.getWidth(),jtResPend.getHeight());
        jpGestio.add(scroll);
        
        jlResConf = new JLabel("Reserves confirmades:");
        jlResConf.setBounds(20, 360, 200, 30);
        jlResConf.setFont(cos);
        jpGestio.add(jlResConf);
        
        jtbSwitch = new JToggleButton();
        jtbSwitch.setBounds(20, 400, 100, 30);
        jtbSwitch.setText("Entrades");
        jpGestio.add(jtbSwitch);
        
        jdcResConf = new JDateChooser();
        jdcResConf.setBounds(210,400,168,30);
        jpGestio.add(jdcResConf);
        
        modelResConf = new DefaultTableModel();
        modelResConf.addColumn("DNI");
        modelResConf.addColumn("Entrada");
        modelResConf.addColumn("Sortida");
        modelResConf.addColumn("Persones");
        modelResConf.addColumn("Habitació");
        jtResConf = new JTable(modelResConf);
        jtResConf.setBounds(20,440,jpGestio.getWidth()-40,200);
        jtResConf.setEnabled(false);
        jpGestio.add(jtResConf);
        JScrollPane scrollResConf = new JScrollPane(jtResConf,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollResConf.setBounds(20,440,jtResConf.getWidth(),jtResConf.getHeight());
        jpGestio.add(scrollResConf);
        
    }

    private void dissenyClients() {
        jpClients = (JPanel) this.getContentPane().getComponent(1);
        jlClients = new JLabel("Clients");
        jlClients.setBounds(0, 20, jpClients.getWidth(), 40);
        jlClients.setHorizontalAlignment(SwingConstants.CENTER);
        jlClients.setFont(titol);
        jpClients.add(jlClients);
        
        //DNI
        jlDni = new JLabel("DNI: ");
        jlDni.setBounds(20,100,200,30);
        jlDni.setFont(cos);
        jpClients.add(jlDni);
        
        jtfDNI = new JTextField();
        jtfDNI.setBounds(150,100,170,30);
        jtfDNI.setName("JTextFieldDni");
        jpClients.add(jtfDNI);
        
        jlDniIcon = new JLabel();
        jlDniIcon.setBounds(330,100,30,30);
        jpClients.add(jlDniIcon);
        
        //NOM
        jlNom = new JLabel("Nom: ");
        jlNom.setBounds(20,150,200,30);
        jlNom.setFont(cos);
        jpClients.add(jlNom);

        jtfNom = new JTextField();
        jtfNom.setBounds(150,150,170,30);
        jtfNom.setName("JTextFieldNom");
        jpClients.add(jtfNom);
        
        jlNomIcon = new JLabel();
        jlNomIcon.setBounds(330,150,30,30);
        jpClients.add(jlNomIcon);
        
        //Cognoms
        jlCognoms = new JLabel("Cognoms: ");
        jlCognoms.setBounds(20,200,200,30);
        jlCognoms.setFont(cos);
        jpClients.add(jlCognoms);

        jtfCognoms = new JTextField();
        jtfCognoms.setBounds(150,200,170,30);
        jtfCognoms.setName("JTextFieldCognom");
        jpClients.add(jtfCognoms);
        
        jlCognomsIcon = new JLabel();
        jlCognomsIcon.setBounds(330,200,30,30);
        jpClients.add(jlCognomsIcon);
        
        
        //número Persones
        jlNumPers = new JLabel("Num. Persones: ");
        jlNumPers.setBounds(20,250,200,30);
        jlNumPers.setFont(cos);
        jpClients.add(jlNumPers);

        jtfNumPers = new JTextField();
        jtfNumPers.setBounds(150,250,75,30);
        jtfNumPers.setName("JTextFieldNumPers");
        jpClients.add(jtfNumPers);
        
        jlNumPersIcon = new JLabel();
        jlNumPersIcon.setBounds(330,250,30,30);
        jpClients.add(jlNumPersIcon);
        
        //Número nits
        jlNumNits = new JLabel("Num. Nits: ");
        jlNumNits.setBounds(20,300,200,30);
        jlNumNits.setFont(cos);
        jpClients.add(jlNumNits);

        jtfNumNits = new JTextField();
        jtfNumNits.setBounds(150,300,75,30);
        jtfNumNits.setName("JTextFieldNumNits");
        jpClients.add(jtfNumNits);
        
        jlNumNitsIcon = new JLabel();
        jlNumNitsIcon.setBounds(330,300,30,30);
        jpClients.add(jlNumNitsIcon);
        
        //data entrada
        jlDataEntrada = new JLabel("Data d'entrada: ");
        jlDataEntrada.setBounds(20,380,200,30);
        jlDataEntrada.setFont(cos);
        jpClients.add(jlDataEntrada);
        
        jcDataEntrada = new JCalendar();
        jcDataEntrada.setBounds(20,430,jpClients.getWidth()-40,180);
        jcDataEntrada.setName("JCalendarDataEntrada");
        jpClients.add(jcDataEntrada);
        
        jbReserva = new JButton();
        jbReserva.setBounds(160,630,100,30);
        jbReserva.setText("Reserva!");
        jbReserva.setEnabled(false);
        jpClients.add(jbReserva);   
    }

    private void dissenyBack() {
        jpBack = (JPanel) this.getContentPane().getComponent(2);
        jlBack = new JLabel("Back");
        jlBack.setBounds(0, 20, jpBack.getWidth(), 40);
        jlBack.setFont(titol);
        jlBack.setHorizontalAlignment(SwingConstants.CENTER);
        jpBack.add(jlBack);
        
        //Nom Hotel
        jlNomHotel = new JLabel("Nom Hotel: ");
        jlNomHotel.setBounds(20,100,200,30);
        jlNomHotel.setFont(cos);
        jpBack.add(jlNomHotel);
        
        jtfNomHotel = new JTextField();
        jtfNomHotel.setBounds(150,100,220,30);
        jpBack.add(jtfNomHotel);
        
        jbGuardaHotel = new JButton();
        jbGuardaHotel.setBounds(160,150,100,30);
        jbGuardaHotel.setText("Guarda!");
        jpBack.add(jbGuardaHotel);
        
        //Registre habitació
        
        jlRegHab = new JLabel("Registre nova habitació: ");
        jlRegHab.setBounds(20,200,200,30);
        jlRegHab.setFont(cos);
        jpBack.add(jlRegHab);
        
        jlNum = new JLabel("Num:");
        jlNum.setBounds(20,240,75,30);
        jlNum.setFont(cos);
        jpBack.add(jlNum);
        
        jtfNum = new JTextField();
        jtfNum.setBounds(75,240,75,30);
        jtfNum.setName("jtfNumHab");
        jpBack.add(jtfNum);
        
        jlPers = new JLabel("# Pers:");
        jlPers.setBounds(200,240,75,30);
        jlPers.setFont(cos);
        jpBack.add(jlPers);
        
        jtfPers = new JTextField();
        jtfPers.setBounds(270,240,75,30);
        jtfPers.setName("jtfNumPers");
        jpBack.add(jtfPers);
        
        jbGuardaReg = new JButton();
        jbGuardaReg.setBounds(160,310,100,30);
        jbGuardaReg.setText("Guarda!");
        jbGuardaReg.setEnabled(false);
        jpBack.add(jbGuardaReg);
        
        /*<----------------------CONSULTA RESERVA--------------------->*/
        jlConsRes = new JLabel("Consulta Reserva ");
        jlConsRes.setBounds(20,370,200,30);
        jlConsRes.setFont(cos);
        jpBack.add(jlConsRes);
        
        jlNomClient = new JLabel("Nom client: ");
        jlNomClient.setBounds(20,425,200,30);
        jlNomClient.setFont(cos);
        jpBack.add(jlNomClient);
        
        jtfNomClient = new JTextField();
        jtfNomClient.setBounds(140,425,200,30);
        jpBack.add(jtfNomClient);
        
        modelNomClient = new DefaultListModel();
        
        jlistnomClient = new JList(modelNomClient);
        jlistnomClient.setBounds(20,480,160,130);
        jpBack.add(jlistnomClient);
        
        JScrollPane scrollClient1 = new JScrollPane(jlistnomClient,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollClient1.setBounds(20,480,jlistnomClient.getWidth(),jlistnomClient.getHeight());
        jpBack.add(scrollClient1);
        
        modelReservesClient = new DefaultListModel();
        
        jlistReservesClient = new JList();
        jlistReservesClient.setBounds(220,480,160,130);
        jpBack.add(jlistReservesClient);
        
        JScrollPane scrollClient2 = new JScrollPane(jlistReservesClient,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollClient2.setBounds(220,480,jlistReservesClient.getWidth(),jlistReservesClient.getHeight());
        jpBack.add(scrollClient2);
        
        jbElimina = new JButton();
        jbElimina.setBounds(160,630,100,30);
        jbElimina.setText("Elimina!");
        jpBack.add(jbElimina);
    }
    
    private void omplirImageIcon() {
        ImageIcon valid = new ImageIcon("img/icons/valid.png");
        iiValid = new ImageIcon(valid.getImage().getScaledInstance(jlDniIcon.getWidth(), jlDniIcon.getHeight(), Image.SCALE_SMOOTH));
        ImageIcon noValid = new ImageIcon("img/icons/no_valid.png");
        iiNoValid = new ImageIcon(noValid.getImage().getScaledInstance(jlDniIcon.getWidth(), jlDniIcon.getHeight(), Image.SCALE_SMOOTH));
    }
    
    public void clearJTFClient(){
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
    
   public void CanviarTitolFinestra(String nom){
        this.setTitle(nom);
   }
   
   public void clearRegHab(){
       jtfNum.setText("");
       jtfPers.setText("");
       jbGuardaReg.setEnabled(false);
   }
   
}