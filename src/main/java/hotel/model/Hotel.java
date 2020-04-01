/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author arnau1
 */
public class Hotel {

    String nomHotel;
    ArrayList<Habitacio> habitacions = new ArrayList<>();
    ArrayList<Client> llistaClients = new ArrayList<>();
    ArrayList<Reserva> llistaReservesPendents = new ArrayList<>();
    ArrayList<Reserva> llistaResservesConfirmades = new ArrayList<>();

    public Hotel() {

    }

    public String getNomHotel() {
        return nomHotel;
    }

    public void setNomHotel(String nomHotel) {
        this.nomHotel = nomHotel;
    }

    public ArrayList<Reserva> getLlistaReservesPendents() {
        return llistaReservesPendents;
    }

    public boolean clientExisteix(String dni) {
        for (Client client : this.llistaClients) {
            if (client.getDni().equalsIgnoreCase(dni)) {
                return true;
            }
        }
        return false;
    }

    public void afegirClient(Client cli) {
        this.llistaClients.add(cli);
    }

    public Client getObjectClient(String dni) {
        for (Client client : this.llistaClients) {
            if (client.getDni().equalsIgnoreCase(dni)) {
                return client;
            }
        }
        return null;
    }

    public Habitacio habitacioExisteix(int num) {
        for (Habitacio a : this.habitacions) {
            if (a.getNumHab() == num) {
                return a;
            }
        }
        return null;
    }

    public void afegirHabitacio(Habitacio habitacio) {
        this.habitacions.add(habitacio);
    }

    public void canviarMida(int num, int capacitat) {
        for (Habitacio a : this.habitacions) {
            if (a.getNumHab() == num) {
                int pos = habitacions.indexOf(a);
                habitacions.get(pos).setNumPers(capacitat);
                break;
            }
        }
    }

    public Habitacio numeroHabitacio(Reserva reserva) {
        for (int i = 0; i < 2; i++) {
            for (Habitacio a : this.habitacions) {
                if (a.getNumPers() == reserva.getNumPersones() + i) {
                    boolean ocupada = false;
                    for (Reserva b : totesLesReserves()) {
                        if (b.getHabitacio().getNumHab() == a.getNumHab()) {
                            if (overlap(reserva.getEntrada(), reserva.getSortida(), b.getEntrada(), b.getSortida())) {
                                ocupada = true;
                                break;
                            } 
                        }
                    }
                    if(!ocupada){
                        return a;
                    }
                }
            }
        }
        return null;
    }

    public ArrayList<Reserva> totesLesReserves() {
        ArrayList<Reserva> totesReserves = new ArrayList<>();
        totesReserves.addAll(this.llistaReservesPendents);
        totesReserves.addAll(this.llistaResservesConfirmades);
        return totesReserves;
    }

    public void afegirReservaPendent(Reserva reserva) {
        this.llistaReservesPendents.add(reserva);
    }

    public void afegirReservaConfirmada(Reserva reserva) {
        this.llistaResservesConfirmades.add(reserva);
    }

    public boolean overlap(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return start2.isBefore(end1) && end2.isAfter(start1);
    }
    
    public Reserva getReservaPendent(int pos){
        return this.llistaReservesPendents.get(pos);
    }
    
    public void novaReservaConfirmada(Reserva reserva){
        this.llistaResservesConfirmades.add(reserva);
    }
    
    public void eliminarRerservaPendent(int pos){
        this.llistaReservesPendents.remove(pos);
    }
    
    public void eliminarRerservaConfirmada(int pos){
        this.llistaResservesConfirmades.remove(pos);
    }
    
    public ArrayList<Reserva> mostrarSortides(LocalDate date){
        ArrayList<Reserva> sortides = new ArrayList<>();
        for(Reserva a: this.llistaResservesConfirmades){
            if(a.getSortida().equals(date)){
                sortides.add(a);
            }
        }
        return sortides;
    }
    
    public ArrayList<Reserva> mostrarEntrades(LocalDate date){
    ArrayList<Reserva> al = new ArrayList<>();
        for(Reserva a: this.llistaResservesConfirmades){
            if(a.getEntrada().equals(date)){
                al.add(a);
            }
        }
        return al;
    }
    public ArrayList<Client> mostraClients(String info){
        ArrayList<Client> al = new ArrayList<>();
        for(Client a : this.llistaClients){
            if(a.getNom().toUpperCase().contains(info)||a.getDni().toUpperCase().contains(info)||a.getCognoms().toUpperCase().contains(info)){
                al.add(a);
            }
        }
        return al;
    }
    
    public ArrayList<Reserva> reservesClient(Client client){
        ArrayList<Reserva> al = new ArrayList<>();
        for(Reserva a : totesLesReserves()){
            if(a.getClient().equals(client)){
                al.add(a);
            }
        }
        return al;
    }
    
    public int getPosicioReservaPendent(Reserva reserva){
        for(Reserva a : llistaReservesPendents){
            if(a.equals(reserva)){
                return llistaReservesPendents.indexOf(a);
            }
        }
        return -1;
    }
    
     public int getPosicioReservaConfirmada(Reserva reserva){
        for(Reserva a : llistaResservesConfirmades){
            if(a.equals(reserva)){
                return llistaResservesConfirmades.indexOf(a);
            }
        }
        return -1;
    }
    
}
