package fiuba.ordertracker.services;

import java.io.IOException;
import java.util.List;

import fiuba.ordertracker.helpers.Constants;
import fiuba.ordertracker.pojo.Agenda;
import fiuba.ordertracker.pojo.Client;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by pablo on 26/3/2016.
 */

public final class ClientService {
    public ClientsFromTodayByVendIdService clientsFromTodayByVendIdService;
    public Clients  clients;
    public Comment comment;
    private static ClientService instance = null;

    private ClientService(){
        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        clientsFromTodayByVendIdService = retrofit.create(ClientsFromTodayByVendIdService.class);
        clients = retrofit.create(Clients.class);
        comment = retrofit.create(Comment.class);
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
        Call<List<Client>> ClientsFromTodayByVendIdService(@Path("id") int vendId, @Query("orderby") String orderby,
                                                           @Query("orientation") String orientation );
    }

    //clientes
    public interface Clients {
        @GET("client")
        //fecha_visita: YYYY-MM-DD Ej: '2016-04-22'
        Call<List<Client>> Clients(@Query("id") String id, @Query("id_vendedor") String id_vendedor, @Query("razon_social%") String razon_social, @Query("orderby") String orderby,
                                   @Query("orientation") String orientation, @Query("cod_cliente%") String codCliente, @Query("fecha_visita") String fecha_visita,
                                   @Query("fecha_visita_programada") String fecha_visita_programada, @Query("fecha_visita_concretada") String fecha_visita_concretada);
    }

    //Store comment
    public interface Comment {
        @FormUrlEncoded
        @POST("client/noOrderComment")
        Call<Agenda> AddComment(@Field("id_agenda") String id_agenda, @Field("comentario") String comentario);
    }


    public static void main(String... args) throws IOException {
        ClientService cs = ClientService.getInstance();

        // Create a call instance for looking up Retrofit contributors.
        Call<List<Client>> call = cs.clientsFromTodayByVendIdService.ClientsFromTodayByVendIdService(1,null,null);

        // Fetch and print a list of the contributors to the library.
        List<Client> clientList = call.execute().body();
        for (Client client : clientList) {
            //System.out.println(client.getApenom() + " (" + client.getDireccion() + ")");
        }



        call = cs.clients.Clients(null, null,null,null,null,null,null,null,null);

        clientList = call.execute().body();
        for (Client client : clientList) {
            //System.out.println(client.getApenom() + " (" + client.getDireccion() + ")");
        }
    }
}