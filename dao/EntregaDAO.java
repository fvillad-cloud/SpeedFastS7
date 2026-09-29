package dao;

import modelo.Entrega;
import modelo.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EntregaDAO {

    public void guardarEntrega(Entrega entrega){
        String sql = "INSERT INTO entrega(id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?,?)";

        try(Connection conn = ConexionBD.obtenerConexion();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, entrega.getPedido().getId());
            stmt.setInt(2, entrega.getRepartidor().getId());
            stmt.setObject(3, entrega.getFecha());
            stmt.setObject(4, entrega.getHora());


            stmt.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar el producto en la base de datos.");
        }
    }
}
