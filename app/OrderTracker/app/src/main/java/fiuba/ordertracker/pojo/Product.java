
package fiuba.ordertracker.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Product {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("marca")
    @Expose
    private String marca;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("categoria")
    @Expose
    private String categoria;
    @SerializedName("precio")
    @Expose
    private String precio;
    @SerializedName("stock")
    @Expose
    private String stock;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("url_image_normal")
    @Expose
    private String urlImageNormal;
    @SerializedName("url_image_mini")
    @Expose
    private String urlImageMini;

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
     *     The nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * 
     * @param nombre
     *     The nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * 
     * @return
     *     The marca
     */
    public String getMarca() {
        return marca;
    }

    /**
     * 
     * @param marca
     *     The marca
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * 
     * @return
     *     The descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * 
     * @param descripcion
     *     The descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * 
     * @return
     *     The categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * 
     * @param categoria
     *     The categoria
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * 
     * @return
     *     The precio
     */
    public String getPrecio() {
        return precio;
    }

    /**
     * 
     * @param precio
     *     The precio
     */
    public void setPrecio(String precio) {
        this.precio = precio;
    }

    /**
     * 
     * @return
     *     The stock
     */
    public String getStock() {
        return stock;
    }

    /**
     * 
     * @param stock
     *     The stock
     */
    public void setStock(String stock) {
        this.stock = stock;
    }

    /**
     * 
     * @return
     *     The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     * @param createdAt
     *     The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 
     * @return
     *     The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 
     * @param updatedAt
     *     The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString(){
        return this.getId();
    }

    public String getUrlImageNormal() {
        return urlImageNormal;
    }

    public void setUrlImageNormal(String urlImageNormal) {
        this.urlImageNormal = urlImageNormal;
    }

    public String getUrlImageMini() {
        return urlImageMini;
    }

    public void setUrlImageMini(String urlImageMini) {
        this.urlImageMini = urlImageMini;
    }
}
