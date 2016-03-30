package fiuba.ordertracker.services;

import java.io.IOException;
import java.util.List;

import fiuba.ordertracker.pojo.Client;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by pablo on 26/3/2016.
 */

public final class ClientService {
    public static final String API_URL = "http://ec2-54-207-103-117.sa-east-1.compute.amazonaws.com/";
    public ClientsFromTodayByVendIdService clientsFromTodayByVendIdService;
    public Clients  clients;
    private static ClientService instance = null;

    private ClientService(){
        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        clientsFromTodayByVendIdService = retrofit.create(ClientsFromTodayByVendIdService.class);
        clients = retrofit.create(Clients.class);
    }

    public static synchronized ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }

        return instance;
    }

    //clientes del dia por id de Vendedor
    public interface ClientsFromTodayByVendIdService {
        @GET("client/getFromToday/{id}")
        Call<List<Client>> ClientsFromTodayByVendIdService(@Path("id") int vendId);
    }

    //clientes
    public interface Clients {
        @GET("client")
        Call<List<Client>> Clients();
    }



    public static void main(String... args) throws IOException {
        ClientService cs = ClientService.getInstance();

        // Create a call instance for looking up Retrofit contributors.
        Call<List<Client>> call = cs.clientsFromTodayByVendIdService.ClientsFromTodayByVendIdService(1);

        // Fetch and print a list of the contributors to the library.
        List<Client> clientList = call.execute().body();
        for (Client client : clientList) {
            System.out.println(client.getApenom() + " (" + client.getDireccion() + ")");
        }

        System.out.println("||||||||||||||||||||||||||||||||||||||||");

        call = cs.clients.Clients();

        clientList = call.execute().body();
        for (Client client : clientList) {
            System.out.println(client.getApenom() + " (" + client.getDireccion() + ")");
        }
    }
}