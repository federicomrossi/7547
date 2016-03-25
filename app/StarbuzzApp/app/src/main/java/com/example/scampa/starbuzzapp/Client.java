package com.example.scampa.starbuzzapp;

/**
 * Created by scampa on 25/3/2016.
 */
public class Client {

    private String name;
    private String address;
    private int profilePicture;

    // TODO traer de la base!!
    // clients es un array de clientes (por el momento, sin discriminar por cartera de vendedor)
    public static final Client[] clients = {
            new Client("Bart", "Address 1",
                    R.drawable.bart),
            new Client("Homero", "Address 2",
                    R.drawable.homero),
            new Client("Lisa", "Address 3",
                    R.drawable.lisa),
            new Client("Flanders", "Address 4",
                    R.drawable.flanders),
            new Client("Burns", "Address 5",
                    R.drawable.burns),
            new Client("Tanya", "Address 6",
                    R.drawable.tanya)
    };

    // Cada cliente tiene nombre, direccion e imagen de perfil
    private Client(String name, String address, int profilePicture) {
        this.name = name;
        this.address = address;
        this.profilePicture = profilePicture;
    }
    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public int getProfilePicture() {
        return profilePicture;
    }

    public String toString() {
        return this.name;
    }

}
