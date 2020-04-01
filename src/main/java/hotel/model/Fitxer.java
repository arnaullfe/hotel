/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.model;

import java.io.*;

/**
 *
 * @author arnau1
 */
public class Fitxer {
    File habitacions,reserves,clients,hotel,carpeta,fitxerTemp;
    FileWriter fileWriter;
    BufferedWriter bufferedWriter;
    FileReader fileReader;
    BufferedReader bufferedReader;

    public Fitxer(){
        crearDirectori();
        crearFitxers();
    }

    public File getHabitacions() {
        return habitacions;
    }

    public void setHabitacions(File habitacions) {
        this.habitacions = habitacions;
    }

    public File getReserves() {
        return reserves;
    }

    public void setReserves(File reserves) {
        this.reserves = reserves;
    }

    public File getClients() {
        return clients;
    }

    public void setClients(File clients) {
        this.clients = clients;
    }

    public File getHotel() {
        return hotel;
    }

    public void setHotel(File hotel) {
        this.hotel = hotel;
    }

    public File getCarpeta() {
        return carpeta;
    }

    public void setCarpeta(File carpeta) {
        this.carpeta = carpeta;
    }


    private void crearDirectori(){
        carpeta = new File("data");
        if(carpeta.mkdir()){
            System.out.println("Carpeta creada correctament");
        } else{
            System.err.println("Error al crear la carpeta, potser ja estava creada");
        }
    }

    private void crearFitxers(){
        habitacions = new File("data"+File.separator+"habitacions.txt");
        reserves = new File("data"+File.separator+"reserves.txt");
        clients = new File("data"+File.separator+"clients.txt");
        hotel = new File("data"+File.separator+"hotel.txt");
        try {
            statusCreacioFitxers(habitacions.createNewFile(),habitacions.getName());
            statusCreacioFitxers(reserves.createNewFile(),reserves.getName());
            statusCreacioFitxers(clients.createNewFile(),clients.getName());
            statusCreacioFitxers(hotel.createNewFile(),hotel.getName());
        } catch (IOException e) {
            System.err.println("Error al crear el fitxer: "+e);
        }
    }

    private void statusCreacioFitxers(Boolean status, String nom){
        if(status){
            System.out.println("Fitxer "+nom+" creat correctament");
        } else{
            System.err.println("Error al crear el fitxer "+nom);
        }
    }

    public void escriureFitxer(File fitxer, String linia){
            fitxerTemp = new File("data"+File.separator+"fitxerTemp.txt");
        try {
            fileWriter = new FileWriter(fitxer,false);
            bufferedWriter = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            System.err.println("Error al iniciar el fileWrtitter o BufferedWriter: "+e);
        }
        try {
            bufferedWriter.write(linia);
            System.out.println(linia);
        } catch (IOException e) {
            System.err.println("Error al escriure: "+e);
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String llegirFitxer(File fitxer){
        try {
            fileReader = new FileReader(fitxer);
            bufferedReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            System.err.println("Error al iniciar el fileReader o BufferedReader: "+e);
        }
        String currentLine = "";
        try {
            currentLine = bufferedReader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentLine;
    }
}
