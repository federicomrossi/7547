package fiuba.ordertracker.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by scampa on 28/5/2016.
 */
public class Agenda{

    @SerializedName("id_agenda")
    @Expose
    private String agendaID;

    @SerializedName("id_cliente")
    @Expose
    private String clientID;

    @SerializedName("fecha_visita_programada")
    @Expose
    private String fechaVisitaProgramada;

    @SerializedName("fecha_visita_concretada")
    @Expose
    private String fechaVisitaConcretada;

    @SerializedName("comentario")
    @Expose
    private String comment;

    @SerializedName("pedido_hecho")
    @Expose
    private Boolean isOrderDone;

    @SerializedName("id_orden")
    @Expose
    private String orderID;

    public String getAgendaID() {
        return agendaID;
    }

    public void setAgendaID(String agendaID) {
        this.agendaID = agendaID;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getFechaVisitaProgramada() {
        return fechaVisitaProgramada;
    }

    public void setFechaVisitaProgramada(String fechaVisitaProgramada) {
        this.fechaVisitaProgramada = fechaVisitaProgramada;
    }

    public String getFechaVisitaConcretada() {
        return fechaVisitaConcretada;
    }

    public void setFechaVisitaConcretada(String fechaVisitaConcretada) {
        this.fechaVisitaConcretada = fechaVisitaConcretada;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getIsOrderDone() {
        return isOrderDone;
    }

    public void setIsOrderDone(Boolean isOrderDone) {
        this.isOrderDone = isOrderDone;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
}
