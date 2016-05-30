package fiuba.ordertracker.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TokenPush {

    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("token")
    @Expose
    private String token;

    /**
     *
     * @return
     * The idUser
     */
    public String getIdUser() {
        return idUser;
    }

    /**
     *
     * @param idUser
     * The id_user
     */
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    /**
     *
     * @return
     * The token
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     * The token
     */
    public void setToken(String token) {
        this.token = token;
    }

}