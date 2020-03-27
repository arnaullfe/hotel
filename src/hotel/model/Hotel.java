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

    public Hotel(String nomHotel) {
        this.nomHotel = nomHotel;
    }

    public String getNomHotel() {
        return nomHotel;
    }

    public void setNomHotel(String nomHotel) {
        this.nomHotel = nomHotel;
    }

    public ArrayList<Habitacio> getLlistaHab() {
        return habitacions;
    }

    public void setLlistaHab(ArrayList<Habitacio> llistaHab) {
        this.habitacions = llistaHab;
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

    public boolean clientExisteix(String dni) {
        for (Client client : this.llistaClients) {
            if (dni.equalsIgnoreCase(client.dni)) {
                return true;
            }
        }
        return false;
    }

    public void afegirClient(String dni, String nom, String cognom) {
        this.llistaClients.add(new Client(dni, nom, cognom));
    }

    public Client getObjectClient(String dni) {
        for (Client client : this.llistaClients) {
            if (dni.equalsIgnoreCase(dni)) {
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
                        System.out.println("DINS");
                        if (b.getHabitacio().getNumHab() == a.getNumHab()) {
                            if (overlap(reserva.entrada, reserva.getSortida(), b.getEntrada(), b.getSortida())) {
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
    
    public void novaReservaConrifmada(Reserva reserva){
        this.llistaResservesConfirmades.add(reserva);
    }
    
    public void eliminarRerservaPendent(int pos){
        this.llistaReservesPendents.remove(pos);
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
}
