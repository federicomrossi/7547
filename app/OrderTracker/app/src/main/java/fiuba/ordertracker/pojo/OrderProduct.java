package fiuba.ordertracker.pojo;

/**
 * Created by pablo on 13/4/2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderProduct {

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

}
