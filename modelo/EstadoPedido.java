package modelo;

public enum EstadoPedido {
    PENDIENTE,
    EN_REPARTO,
    ENTREGADO;

    @Override
    public String toString() {
        String texto =name().toLowerCase();
        return texto.substring(0,1).toUpperCase() + texto.substring(1);
    }
}
