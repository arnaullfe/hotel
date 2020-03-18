/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author arnau1
 */
public class Hotel {
    String nomHotel;
    ArrayList<Habitacio> llistaHab = new ArrayList<>();
    ArrayList<Client>llistaClients = new ArrayList<>();
    ArrayList<Reserva>llistaReservesPendents = new ArrayList<>();
    ArrayList<Reserva>llistaResservesConfirmades = new ArrayList<>();

    public Hotel() {
    }

    public String getNomHotel() {
        return nomHotel;
    }

    public void setNomHotel(String nomHotel) {
        this.nomHotel = nomHotel;
    }

    public ArrayList<Habitacio> getLlistaHab() {
        return llistaHab;
    }

    public void setLlistaHab(ArrayList<Habitacio> llistaHab) {
        this.llistaHab = llistaHab;
    }

    public ArrayList<Client> getLlistaClients() {
        return llistaClients;
    }

    public void setLlistaClients(ArrayList<Client> llistaClients) {
        this.llistaClients = llistaClients;
    }

    public ArrayList<Reserva> getLlistaReservesPendents() {
        return llistaReservesPendents;
    }

    public void setLlistaReservesPendents(ArrayList<Reserva> llistaReservesPendents) {
        this.llistaReservesPendents = llistaReservesPendents;
    }

    public ArrayList<Reserva> getLlistaResservesConfirmades() {
        return llistaResservesConfirmades;
    }

    public void setLlistaResservesConfirmades(ArrayList<Reserva> llistaResservesConfirmades) {
        this.llistaResservesConfirmades = llistaResservesConfirmades;
    }
    
    public boolean clientExisteix(String dni){
        for(Client client : this.llistaClients){
            if(dni.equalsIgnoreCase(client.dni)){
                return true;
            }
        }
        return false;
    }
    
    public void afegirClient(String dni, String nom,String cognom){
        this.llistaClients.add(new Client(dni,nom,cognom));
    }
    
    public Client getObjectClient(String dni){
        for(Client client: this.llistaClients){
            if(dni.equalsIgnoreCase(dni)){
                return client;
            }
        }
        return null;
    }
    
    
}
