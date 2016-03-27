
package com.example.ezequiel.ordertrack.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


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

}
