/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.model;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author arnau1
 */
public class Fitxer {
    File habitacions,reservesPendents,reservesConfirmades,clients,hotel,carpeta,fitxerTemp;
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

    public File getReservesPendents() {
        return reservesPendents;
    }

    public File getReservesConfirmades() {
        return reservesConfirmades;
    }

    public File getClients() {
        return clients;
    }

    public File getHotel() {
        return hotel;
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
        reservesPendents = new File("data"+File.separator+"reservesPendents.txt");
        reservesConfirmades = new File("data"+File.separator+"reservesConfirmades.txt");
        clients = new File("data"+File.separator+"clients.txt");
        hotel = new File("data"+File.separator+"hotel.txt");
        fitxerTemp = new File("data"+File.separator+"fitxerTemp.txt");
        try {
            statusCreacioFitxers(habitacions.createNewFile(),habitacions.getName());
            statusCreacioFitxers(reservesPendents.createNewFile(),reservesPendents.getName());
            statusCreacioFitxers(reservesConfirmades.createNewFile(),reservesConfirmades.getName());
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
        try {
            fileWriter = new FileWriter(fitxer,false);
            bufferedWriter = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            System.err.println("Error al iniciar el fileWrtitter o BufferedWriter: "+e);
        }
        try {
            bufferedWriter.write(linia);
        } catch (IOException e) {
            System.err.println("Error al escriure: "+e);
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> llegirFitxer(File fitxer){
        try {
            fileReader = new FileReader(fitxer);
            bufferedReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            System.err.println("Error al iniciar el fileReader o BufferedReader: "+e);
        }
        String currentLine = "";
        ArrayList<String> al = new ArrayList<>();
        while(true) {
            try {
                if (!((currentLine = bufferedReader.readLine())!=null)) break;
                al.add(currentLine);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return al;
    }

    public void updateFitxer(File fitxer, String novaLinia, String liniaEditar){
        try {
            fileReader = new FileReader(fitxer);
            fileWriter = new FileWriter(fitxerTemp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bufferedReader = new BufferedReader(fileReader);
        bufferedWriter = new BufferedWriter(fileWriter);
        String currentLine = "";
        while(true){
            try {
                if (!((currentLine = bufferedReader.readLine())!=null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(liniaEditar!=null && currentLine.contains(liniaEditar)){
                currentLine = novaLinia;
            }
            try {
                bufferedWriter.write(currentLine+System.getProperty("line.separator"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        try {
            if(liniaEditar==null){
                bufferedWriter.write(novaLinia+System.getProperty("line.separator"));
            }
            bufferedWriter.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fitxer.delete();
        fitxerTemp.renameTo(fitxer);
    }

    public void eliminarLinia(File fitxer, String liniaEliminar){
        try {
            fileReader = new FileReader(fitxer);
            fileWriter = new FileWriter(fitxerTemp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bufferedReader = new BufferedReader(fileReader);
        bufferedWriter = new BufferedWriter(fileWriter);
        String currentLine = "";
        while(true){
            try {
                if (!((currentLine = bufferedReader.readLine())!=null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(!currentLine.contains(liniaEliminar)){
                try {
                    bufferedWriter.write(currentLine+System.getProperty("line.separator"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            bufferedWriter.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fitxer.delete();
        fitxerTemp.renameTo(fitxer);
    }

}
