package fiuba.ordertracker.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import fiuba.ordertracker.R;
import fiuba.ordertracker.pojo.TokenPush;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationIntentService extends IntentService {

    private static final String TAG ="RegistrationService";
    //private static final String["RegistrationIntentService"] TOPICS = {"global"};

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            synchronized (TAG) {
                // Initially a network call, to retrieve the token, subsequent calls are local.
                InstanceID instanceID = InstanceID.getInstance(this);
                String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                Log.i("GCM NOTIFICATIONS", "Token: " + token);

                // Send the token to the server
                SharedPreferences pref = getApplicationContext().getSharedPreferences("OrderTrackerPref", 0);
                int idVendedor = pref.getInt("id", 0);

                TokenPushNotificationService tokenService = TokenPushNotificationService.getInstance();

                Call<TokenPush> call = tokenService.registerToken.RegisterToken(token, Integer.toString(idVendedor));

                call.enqueue(new Callback<TokenPush>() {
                    @Override
                    public void onResponse(Call<TokenPush> call, Response<TokenPush> response) {
                        // Get result Repo from response.body()
                        Log.i("Token registred", "Token: ");
                        TokenPush token = response.body();
                    }

                    @Override
                    public void onFailure(Call<TokenPush> call, Throwable t) {
                        //Aca tenemos que agregar el msj de error a mostrar... puto el que lee
                        Log.d(TAG, "Failed to complete token registre", t);
                    }
                });

                sharedPreferences.edit().putBoolean(getString(R.string.pref_key_SENT_TOKEN_TO_SERVER), true).apply();

                // Save the GCM token
                pref = getApplicationContext().getSharedPreferences("OrderTrackerPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("gcmtoken", token).apply();
                editor.commit();
            }
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            sharedPreferences.edit().putBoolean(getString(R.string.pref_key_SENT_TOKEN_TO_SERVER), false).apply();
        }
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(getString(R.string.intent_name_REGISTRATION_COMPLETE)));
    }
}
