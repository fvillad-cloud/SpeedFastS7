package dao;

import modelo.Pedido;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PedidoDAO {

    public void guardarPedido(Pedido pedido){
        String sql = "INSERT INTO pedido(direccion, tipo, estado) VALUES (?, ?, ?)";

        try(Connection conn = ConexionBD.obtenerConexion();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, pedido.getDireccionEntrega());
            stmt.setString(2, pedido.getTipoPedido());
            stmt.setString(3, pedido.getEstadoPedido());

            stmt.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar el producto en la base de datos.");
        }
    }
}
