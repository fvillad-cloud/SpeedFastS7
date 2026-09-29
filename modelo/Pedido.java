package modelo;

public class Pedido {

    private int id;
    private String direccionEntrega;
    private String tipoPedido;
    private String estadoPedido;

    public Pedido(int id, String direccionEntrega, String tipoPedido, String estadoPedido) {
        this.id = id;
        this.direccionEntrega = direccionEntrega;
        this.tipoPedido = tipoPedido;
        this.estadoPedido = estadoPedido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", direccionEntrega='" + direccionEntrega + '\'' +
                ", tipoPedido='" + tipoPedido + '\'' +
                ", estadoPedido='" + estadoPedido + '\'' +
                '}';
    }
}
