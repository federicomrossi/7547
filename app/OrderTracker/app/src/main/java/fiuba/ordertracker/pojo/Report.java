package fiuba.ordertracker.pojo;

/**
 * Created by pablo on 25/5/2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Report {

    @SerializedName("clientsOnRoute")
    @Expose
    private Integer clientsOnRoute;
    @SerializedName("clientsNotOnRoute")
    @Expose
    private Integer clientsNotOnRoute;
    @SerializedName("totalProductos")
    @Expose
    private String totalProductos;
    @SerializedName("totalMoney")
    @Expose
    private String totalMoney;

    /**
     *
     * @return
     * The clientsOnRoute
     */
    public Integer getClientsOnRoute() {
        return clientsOnRoute;
    }

    /**
     *
     * @param clientsOnRoute
     * The clientsOnRoute
     */
    public void setClientsOnRoute(Integer clientsOnRoute) {
        this.clientsOnRoute = clientsOnRoute;
    }

    /**
     *
     * @return
     * The clientsNotOnRoute
     */
    public Integer getClientsNotOnRoute() {
        return clientsNotOnRoute;
    }

    /**
     *
     * @param clientsNotOnRoute
     * The clientsNotOnRoute
     */
    public void setClientsNotOnRoute(Integer clientsNotOnRoute) {
        this.clientsNotOnRoute = clientsNotOnRoute;
    }

    /**
     *
     * @return
     * The totalProductos
     */
    public String getTotalProductos() {
        return totalProductos;
    }

    /**
     *
     * @param totalProductos
     * The totalProductos
     */
    public void setTotalProductos(String totalProductos) {
        this.totalProductos = totalProductos;
    }

    /**
     *
     * @return
     * The totalMoney
     */
    public String getTotalMoney() {
        return totalMoney;
    }

    /**
     *
     * @param totalMoney
     * The totalMoney
     */
    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

}