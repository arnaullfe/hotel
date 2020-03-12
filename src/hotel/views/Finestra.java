/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.views;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import static java.awt.font.TextAttribute.FONT;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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
    Font titol = new Font("Dyuthi",Font.BOLD,40);
    Font cos = new Font("Liberation Serif",Font.ITALIC,18);
    
    /*<-----------------PANELLS----------------------->*/
    JPanel jpGestio, jpClients, jpBack;
    
     /*<-----------------GESTIÓ------------------------->*/
    JLabel jlGestio,jlBack,jlResPend,jlResConf;
    JTable jtResPend,jtResConf;
    DefaultTableModel modelResPend,modelResConf;
    JDateChooser jdcResConf;
   
    /*<-----------------CLIENTS------------------------->*/
    JLabel jlClients,jlDni,jlNom,jlCognoms,jlNumPers,jlNumNits,jlDataEntrada,jlNomIcon,jlDniIcon,jlCognomsIcon,jlNumPersIcon,jlNumNitsIcon;
    ImageIcon iiValid,iiNoValid;
    JTextField jtfDNI,jtfNom,jtfCognoms,jtfNumPers,jtfNumNits;
    JCalendar jcDataEntrada;
    JButton jbReserva;
    
    /*<----------------BACK------------------------------>*/
    JLabel jlNomHotel,jlRegHab,jlNum,jlPers,jlConsRes,jlNomClient;
    JTextField jtfNomHotel,jtfNum,jtfPers,jtfNomClient;
    JButton jbGuardaHotel,jbGuardaReg,jbElimina;
    JList client1,client2;
    
    public Finestra(){
    this.setVisible(true);
    this.setSize(1200,720);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setTitle("RuralCirv");
    this.setLocationRelativeTo(null);
    this.setResizable(true);
    this.getContentPane().setBackground(Color.BLACK);
    this.setLayout(null);
    iniciarComponents();
    dissenyGestio();
    dissenyClients();
    dissenyBack();
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
        modelResPend.addColumn("#Reserva");
        modelResPend.addColumn("Dia");
        modelResPend.addColumn("Persones");
        modelResPend.addColumn("Habitació");
        jtResPend = new JTable(modelResPend);
        jtResPend.setBounds(20,140,jpGestio.getWidth()-40,200);
        jpGestio.add(jtResPend);
        JScrollPane scroll = new JScrollPane(jtResPend,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(20,140,jtResPend.getWidth(),jtResPend.getHeight());
        jpGestio.add(scroll);
        
        jlResConf = new JLabel("Reserves confirmades:");
        jlResConf.setBounds(20, 360, 200, 30);
        jlResConf.setFont(cos);
        jpGestio.add(jlResConf);
        
        jdcResConf = new JDateChooser();
        jdcResConf.setBounds(210,360,168,30);
        jpGestio.add(jdcResConf);
        
        modelResConf = new DefaultTableModel();
        modelResConf.addColumn("Nom");
        modelResConf.addColumn("Date in");
        modelResConf.addColumn("Date out");
        modelResConf.addColumn("Habitació");
        jtResConf = new JTable(modelResConf);
        jtResConf.setBounds(20,400,jpGestio.getWidth()-40,200);
        jpGestio.add(jtResConf);
        JScrollPane scrollResConf = new JScrollPane(jtResConf,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollResConf.setBounds(20,400,jtResConf.getWidth(),jtResConf.getHeight());
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
        jpClients.add(jtfDNI);
        
        jlNomIcon = new JLabel();
        jlNomIcon.setBounds(330,105,30,20);
        omplirImageIcon();
        jpClients.add(jlNomIcon);
        
        //NOM
        jlNom = new JLabel("Nom: ");
        jlNom.setBounds(20,150,200,30);
        jlNom.setFont(cos);
        jpClients.add(jlNom);

        jtfNom = new JTextField();
        jtfNom.setBounds(150,150,170,30);
        jpClients.add(jtfNom);
        
        //Cognoms
        jlCognoms = new JLabel("Cognoms: ");
        jlCognoms.setBounds(20,200,200,30);
        jlCognoms.setFont(cos);
        jpClients.add(jlCognoms);

        jtfCognoms = new JTextField();
        jtfCognoms.setBounds(150,200,170,30);
        jpClients.add(jtfCognoms);
        
        //número Persones
        jlNumPers = new JLabel("Num. Persones: ");
        jlNumPers.setBounds(20,250,200,30);
        jlNumPers.setFont(cos);
        jpClients.add(jlNumPers);

        jtfNumPers = new JTextField();
        jtfNumPers.setBounds(150,250,75,30);
        jpClients.add(jtfNumPers);
        
        //Número nits
        jlNumNits = new JLabel("Num. Nits: ");
        jlNumNits.setBounds(20,300,200,30);
        jlNumNits.setFont(cos);
        jpClients.add(jlNumNits);

        jtfNumNits = new JTextField();
        jtfNumNits.setBounds(150,300,75,30);
        jpClients.add(jtfNumNits);
        
        //data entrada
        jlDataEntrada = new JLabel("Data d'entrada: ");
        jlDataEntrada.setBounds(20,380,200,30);
        jlDataEntrada.setFont(cos);
        jpClients.add(jlDataEntrada);
        
        jcDataEntrada = new JCalendar();
        jcDataEntrada.setBounds(20,430,jpClients.getWidth()-40,180);
        jpClients.add(jcDataEntrada);
        
        jbReserva = new JButton();
        jbReserva.setBounds(160,630,100,30);
        jbReserva.setText("Reserva!");
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
        jpBack.add(jtfNum);
        
        jlPers = new JLabel("# Pers:");
        jlPers.setBounds(200,240,75,30);
        jlPers.setFont(cos);
        jpBack.add(jlPers);
        
        jtfPers = new JTextField();
        jtfPers.setBounds(270,240,75,30);
        jpBack.add(jtfPers);
        
        jbGuardaReg = new JButton();
        jbGuardaReg.setBounds(160,310,100,30);
        jbGuardaReg.setText("Guarda!");
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
        
        client1 = new JList();
        client1.setBounds(20,480,160,130);
        jpBack.add(client1);
        
        JScrollPane scrollClient1 = new JScrollPane(client1,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollClient1.setBounds(20,480,client1.getWidth(),client1.getHeight());
        jpBack.add(scrollClient1);
        
        client2 = new JList();
        client2.setBounds(220,480,160,130);
        jpBack.add(client2);
        
        JScrollPane scrollClient2 = new JScrollPane(client2,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollClient2.setBounds(220,480,client2.getWidth(),client2.getHeight());
        jpBack.add(scrollClient2);
        
        jbElimina = new JButton();
        jbElimina.setBounds(160,630,100,30);
        jbElimina.setText("Elimina!");
        jpBack.add(jbElimina);
    }
    
    private void omplirImageIcon() {
        ImageIcon valid = new ImageIcon("valid.png");
        iiValid = new ImageIcon(valid.getImage().getScaledInstance(jlNomIcon.getWidth(), jlNomIcon.getHeight(), Image.SCALE_SMOOTH));
        ImageIcon noValid = new ImageIcon("/img/no_valid.png");
        iiNoValid = new ImageIcon(noValid.getImage().getScaledInstance(jlNomIcon.getWidth(), jlNomIcon.getHeight(), Image.SCALE_SMOOTH));
    }
}