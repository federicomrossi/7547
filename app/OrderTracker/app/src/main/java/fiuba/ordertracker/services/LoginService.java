package fiuba.ordertracker.services;

import java.io.IOException;

import fiuba.ordertracker.helpers.Constants;
import fiuba.ordertracker.pojo.User;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by pablo on 26/3/2016.
 */
public final class LoginService {
    public Login  login;
    private static LoginService instance = null;

    private LoginService(){
        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        login = retrofit.create(Login.class);
    }

    public static synchronized LoginService getInstance() {
        if (instance == null) {
            instance = new LoginService();
        }

        return instance;
    }

    //categorias
    public interface Login {
        @FormUrlEncoded
        @POST("login")
        Call<User> login(@Field("email") String username, @Field("password") String password);
    }



    public static void main(String... args) throws IOException {
        LoginService loginService = LoginService.getInstance();

        // Create a call instance for looking up Retrofit contributors.
        Call<User> call = loginService.login.login("pabloqac87@gmail.com","12345");

        // Fetch and print a list of the contributors to the library.
        User user = call.execute().body();

        System.out.println(user);


    }
}