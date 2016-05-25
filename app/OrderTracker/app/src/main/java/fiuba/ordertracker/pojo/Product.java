
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
    @SerializedName("descuento_1")
    @Expose
    private String descuento1;
    @SerializedName("descuento_1_min")
    @Expose
    private String descuento1Min;
    @SerializedName("descuento_2")
    @Expose
    private String descuento2;
    @SerializedName("descuento_2_min")
    @Expose
    private String descuento2Min;
    @SerializedName("descuento_3")
    @Expose
    private String descuento3;
    @SerializedName("descuento_3_min")
    @Expose
    private String descuento3Min;
    @SerializedName("descuento_4")
    @Expose
    private String descuento4;
    @SerializedName("descuento_4_min")
    @Expose
    private String descuento4Min;
    @SerializedName("descuento_5")
    @Expose
    private String descuento5;
    @SerializedName("descuento_5_min")
    @Expose
    private String descuento5Min;

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

    public String getDescuento1() {
        return descuento1;
    }

    public void setDescuento1(String descuento1) {
        this.descuento1 = descuento1;
    }

    public String getDescuento1Min() {
        return descuento1Min;
    }

    public void setDescuento1Min(String descuento1Min) {
        this.descuento1Min = descuento1Min;
    }

    public String getDescuento2() {
        return descuento2;
    }

    public void setDescuento2(String descuento2) {
        this.descuento2 = descuento2;
    }

    public String getDescuento2Min() {
        return descuento2Min;
    }

    public void setDescuento2Min(String descuento2Min) {
        this.descuento2Min = descuento2Min;
    }

    public String getDescuento3() {
        return descuento3;
    }

    public void setDescuento3(String descuento3) {
        this.descuento3 = descuento3;
    }

    public String getDescuento3Min() {
        return descuento3Min;
    }

    public void setDescuento3Min(String descuento3Min) {
        this.descuento3Min = descuento3Min;
    }

    public String getDescuento4() {
        return descuento4;
    }

    public void setDescuento4(String descuento4) {
        this.descuento4 = descuento4;
    }

    public String getDescuento4Min() {
        return descuento4Min;
    }

    public void setDescuento4Min(String descuento4Min) {
        this.descuento4Min = descuento4Min;
    }

    public String getDescuento5() {
        return descuento5;
    }

    public void setDescuento5(String descuento5) {
        this.descuento5 = descuento5;
    }

    public String getDescuento5Min() {
        return descuento5Min;
    }

    public void setDescuento5Min(String descuento5Min) {
        this.descuento5Min = descuento5Min;
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

    public String stockState(){
        String res;
        try{
            Integer stock = new Integer(this.stock);
            if(stock.intValue() == 0){
                res = "Agotado";
            }else if(stock.intValue() > 5){
                res = "Disponible";
            }else{
                res = "Por Agotarse";
            }
        }catch(Exception e){
            res = "Agotado";
        }
        return res;
    }
}
