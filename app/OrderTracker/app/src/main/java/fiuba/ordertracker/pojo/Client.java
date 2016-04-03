
package fiuba.ordertracker.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

import fiuba.ordertracker.helpers.Constants;
import fiuba.ordertracker.helpers.DistanceCalculator;


public class Client {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("apenom")
    @Expose
    private String apenom;
    @SerializedName("direccion")
    @Expose
    private String direccion;
    @SerializedName("telefono")
    @Expose
    private String telefono;
    @SerializedName("observaciones")
    @Expose
    private String observaciones;
    @SerializedName("id_vendedor")
    @Expose
    private String idVendedor;
    @SerializedName("fecha_visita")
    @Expose
    private String fechaVisita;
    @SerializedName("latitud")
    @Expose
    private Double latitude;
    @SerializedName("longitud")
    @Expose
    private Double longitude;
    @SerializedName("cod_cliente")
    @Expose
    private String code;
    @SerializedName("razon_social")
    @Expose
    private String socialReason;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The apenom
     */
    public String getApenom() {
        return apenom;
    }

    /**
     * 
     * @param apenom
     *     The apenom
     */
    public void setApenom(String apenom) {
        this.apenom = apenom;
    }

    /**
     * 
     * @return
     *     The direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * 
     * @param direccion
     *     The direccion
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * 
     * @return
     *     The telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * 
     * @param telefono
     *     The telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * 
     * @return
     *     The observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * 
     * @param observaciones
     *     The observaciones
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * 
     * @return
     *     The idVendedor
     */
    public String getIdVendedor() {
        return idVendedor;
    }

    /**
     * 
     * @param idVendedor
     *     The id_vendedor
     */
    public void setIdVendedor(String idVendedor) {
        this.idVendedor = idVendedor;
    }

    /**
     * 
     * @return
     *     The fechaVisita
     */
    public String getFechaVisita() {
        return fechaVisita;
    }

    /**
     * 
     * @param fechaVisita
     *     The fecha_visita
     */
    public void setFechaVisita(String fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    @Override
    public String toString(){
        return this.getId();
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getDistance()
    {
        double currentLatitude = Constants.FIUBA_LATITUDE;
        double currentLongitude = Constants.FIUBA_LONGITUDE;
        double distance = DistanceCalculator.distance(currentLatitude,currentLongitude,this.getLatitude(),this.getLongitude(),Constants.UNIT);
        return Math.round(distance * 100.0) / 100.0;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSocialReason() {
        return socialReason;
    }

    public void setSocialReason(String socialReason) {
        this.socialReason = socialReason;
    }
}
