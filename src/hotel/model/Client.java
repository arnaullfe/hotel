/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.model;

/**
 *
 * @author arnau1
 */
public class Client {
    String dni,nom,cognoms;

    public Client(String dni, String nom, String cognoms) {
        this.dni = dni;
        this.nom = nom;
        this.cognoms = cognoms;
    }
    public String infoClient(){
        return this.nom.substring(0, 1)+"."+this.cognoms+" - "+this.dni;
    }
}
