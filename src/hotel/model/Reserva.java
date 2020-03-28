/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author arnau1
 */
public class Reserva {

    Client client;
    int numPersones, numNits, idReserva;
    LocalDate entrada, sortida;
    Habitacio habitacio;

    public Reserva(Client client, int numPersones, int numNits, LocalDate entrada, LocalDate sortida, Habitacio habitacio) {
        this.client = client;
        this.numPersones = numPersones;
        this.numNits = numNits;
        this.entrada = entrada;
        this.sortida = sortida;
        this.habitacio = habitacio;
    }

    public Reserva() {
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getNumPersones() {
        return numPersones;
    }

    public void setNumPersones(int numPersones) {
        this.numPersones = numPersones;
    }

    public int getNumNits() {
        return numNits;
    }

    public void setNumNits(int numNits) {
        this.numNits = numNits;
    }

    public LocalDate getEntrada() {
        return entrada;
    }

    public void setEntrada(LocalDate entrada) {
        this.entrada = entrada;
    }

    public LocalDate getSortida() {
        return sortida;
    }

    public void setSortida(LocalDate sortida) {
        this.sortida = sortida;
    }

    public Habitacio getHabitacio() {
        return habitacio;
    }

    public void setHabitacio(Habitacio habitacio) {
        this.habitacio = habitacio;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public String[] arrayReservaPendent() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String[] array = new String[5];
        array[0] = this.client.dni;
        array[1] = this.entrada.format(formatter);
        array[2] = this.sortida.format(formatter);
        array[3] = Integer.toString(this.numPersones);
        array[4] = Integer.toString(this.habitacio.getNumHab());
        return array;
    }

    @Override
    public String toString() {
         DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
         return "ID: " + this.idReserva + " - " + dtf.format(this.entrada) + " - " + this.numPersones + " pax";
    }

}
