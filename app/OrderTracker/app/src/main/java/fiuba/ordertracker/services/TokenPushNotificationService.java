package fiuba.ordertracker.services;

import fiuba.ordertracker.helpers.Constants;
import fiuba.ordertracker.pojo.TokenPush;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by pablo on 26/3/2016.
 */

public final class TokenPushNotificationService {
    public RegisterToken registerToken;
    private static TokenPushNotificationService instance = null;

    private TokenPushNotificationService(){
        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        registerToken = retrofit.create(RegisterToken.class);
    }

    public static synchronized TokenPushNotificationService getInstance() {
        if (instance == null) {
            instance = new TokenPushNotificationService();
        }

        return instance;
    }

    //Store comment
    public interface RegisterToken {
        @FormUrlEncoded
        @POST("users/savetoken")
        Call<TokenPush> RegisterToken(@Field("token") String token, @Field("id_user") String id_vendedor);

        @FormUrlEncoded
        @POST("users/removetoken")
        Call<TokenPush> RemoveToken(@Field("token") String token, @Field("id_user") String id_vendedor);


    }

}