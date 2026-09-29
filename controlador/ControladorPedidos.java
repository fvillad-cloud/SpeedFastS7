package controlador;


import dao.ConexionBD;
import dao.EntregaDAO;
import dao.PedidoDAO;
import modelo.Entrega;
import modelo.Pedido;
import modelo.Repartidor;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ControladorPedidos {

    private PedidoDAO pedidoDAO = new PedidoDAO();
    private EntregaDAO entregaDAO = new EntregaDAO();

    public boolean registrarRepartidor(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return false;
        }
        String sql = "INSERT INTO repartidor (nombre) VALUES (?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registrarPedido(String direccion, String tipo, String estado) {
        if (direccion == null || direccion.isBlank()) {
            return false;
        }

        // Guardar el pedido usando el DAO correspondiente
        Pedido pedido = new Pedido(0, direccion, tipo, estado);
        pedidoDAO.guardarPedido(pedido);

        // Obtener los últimos IDs generados para registrar la entrega en paralelo
        int idPedido = obtenerUltimoId("pedido");
        int idRepartidor = obtenerUltimoId("repartidor");

        if (idPedido > 0 && idRepartidor > 0) {
            pedido.setId(idPedido);
            Repartidor repartidor = new Repartidor(idRepartidor, "");
            Entrega entrega = new Entrega(0, pedido, repartidor, LocalDate.now(), LocalTime.now());
            entregaDAO.guardarEntrega(entrega);
            return true;
        }

        return false;
    }

    private int obtenerUltimoId(String tabla) {
        String sql = "SELECT MAX(id) FROM " + tabla;
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }



}




