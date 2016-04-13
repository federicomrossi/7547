package fiuba.ordertracker.services;

import java.io.IOException;
import java.util.List;

import fiuba.ordertracker.helpers.Constants;
import fiuba.ordertracker.pojo.Order;
import fiuba.ordertracker.pojo.OrderProduct;
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
public final class OrderService {
    public OrderI  order;
    private static OrderService instance = null;

    private OrderService(){
        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        order = retrofit.create(OrderI.class);
    }

    public static synchronized OrderService getInstance() {
        if (instance == null) {
            instance = new OrderService();
        }

        return instance;
    }

    //categorias
    public interface OrderI {
        @FormUrlEncoded
        @POST("orders")
        Call<Order> createOrder(@Field("id_cliente") String id_cliente, @Field("id_estado") String id_estado,  @Field("comentarios") String comentarios);

        @FormUrlEncoded
        @POST("orders/addProductToOrder")
        Call<Order> addProductToOrder (@Field("id_producto") String id_producto, @Field("id_orden") String id_orden,  @Field("cantidad") String cantidad);

        @GET("orders/{id}")
        Call<Order> orderById(@Path("id") int orderId );

        @GET("orders/getProducts")
        Call<List<OrderProduct>> orderProductsById(@Query("id") String id );
    }



    public static void main(String... args) throws IOException {
        OrderService loginService = OrderService.getInstance();

        // Create a call instance for looking up Retrofit contributors.
        Call<Order> call = loginService.order.createOrder("1","1","tuvieja");

        // Fetch and print a list of the contributors to the library.
        Order user = call.execute().body();

        System.out.println(user);

        call = loginService.order.addProductToOrder("1", "1", "3");

        // Fetch and print a list of the contributors to the library.
        user = call.execute().body();

        System.out.println(user);

        call = loginService.order.orderById(1);

        // Fetch and print a list of the contributors to the library.
        user = call.execute().body();

        System.out.println(user);

        Call<List<OrderProduct>> call2 = loginService.order.orderProductsById("1");

        // Fetch and print a list of the contributors to the library.
        List<OrderProduct> clientList = call2.execute().body();

        System.out.println(user);

        for (OrderProduct orderProduct : clientList) {
            System.out.println(orderProduct);
        }


    }
}