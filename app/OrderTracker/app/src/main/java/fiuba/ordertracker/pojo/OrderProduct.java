package fiuba.ordertracker.pojo;

/**
 * Created by pablo on 13/4/2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderProduct{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_cliente")
    @Expose
    private String idCliente;
    @SerializedName("id_estado")
    @Expose
    private String idEstado;
    @SerializedName("comentarios")
    @Expose
    private String comentarios;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("id_orden")
    @Expose
    private String idOrden;
    @SerializedName("id_producto")
    @Expose
    private String idProducto;
    @SerializedName("cantidad")
    @Expose
    private String cantidad;
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
    @SerializedName("url_image_normal")
    @Expose
    private String urlImageNormal;
    @SerializedName("url_image_mini")
    @Expose
    private String urlImageMini;
    @SerializedName("subtotal_sin_descuento")
    @Expose
    private String subtotalWithoutDiscount;
    @SerializedName("descuento_realizado")
    @Expose
    private String appliedDiscount;
    @SerializedName("subtotal_con_descuento")
    @Expose
    private String subtotalWithDiscount;

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

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The idCliente
     */
    public String getIdCliente() {
        return idCliente;
    }

    /**
     *
     * @param idCliente
     * The id_cliente
     */
    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    /**
     *
     * @return
     * The idEstado
     */
    public String getIdEstado() {
        return idEstado;
    }

    /**
     *
     * @param idEstado
     * The id_estado
     */
    public void setIdEstado(String idEstado) {
        this.idEstado = idEstado;
    }

    /**
     *
     * @return
     * The comentarios
     */
    public String getComentarios() {
        return comentarios;
    }

    /**
     *
     * @param comentarios
     * The comentarios
     */
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     *
     * @return
     * The idOrden
     */
    public String getIdOrden() {
        return idOrden;
    }

    /**
     *
     * @param idOrden
     * The id_orden
     */
    public void setIdOrden(String idOrden) {
        this.idOrden = idOrden;
    }

    /**
     *
     * @return
     * The idProducto
     */
    public String getIdProducto() {
        return idProducto;
    }

    /**
     *
     * @param idProducto
     * The id_producto
     */
    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    /**
     *
     * @return
     * The cantidad
     */
    public String getCantidad() {
        return cantidad;
    }

    /**
     *
     * @param cantidad
     * The cantidad
     */
    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString(){
        return this.getIdCliente() + this.getIdProducto() + this.getIdOrden();
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getSubtotalWithoutDiscount() {
        return subtotalWithoutDiscount;
    }

    public void setSubtotalWithoutDiscount(String subtotalWithoutDiscount) {
        this.subtotalWithoutDiscount = subtotalWithoutDiscount;
    }

    public String getAppliedDiscount() {
        return appliedDiscount;
    }

    public void setAppliedDiscount(String appliedDiscount) {
        this.appliedDiscount = appliedDiscount;
    }

    public String getSubtotalWithDiscount() {
        return subtotalWithDiscount;
    }

    public void setSubtotalWithDiscount(String subtotalWithDiscount) {
        this.subtotalWithDiscount = subtotalWithDiscount;
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

    public Double getSubtotal()
    {
        try
        {
            return Double.parseDouble(this.precio) * Double.parseDouble(this.cantidad);
        }catch (Exception e)
        {
            return 0d;
        }
    }
}
