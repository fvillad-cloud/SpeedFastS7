package vista;

import controlador.ControladorPedidos;
import dao.ConexionBD;
import modelo.EstadoPedido;
import modelo.TipoPedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VentanaPrincipal extends JFrame {
    private JPanel panel1;
    private JTextField txtDireccion;
    private JComboBox cmbTipoPedido;
    private JComboBox cmbEstadoPedido;
    private JTextField txtNombre;
    private JButton btnGuardarRepartidor;
    private JButton btnGuardarPedido;
    private JTable tblPedidos;

    private DefaultTableModel model;

    private ControladorPedidos controlador;

    public VentanaPrincipal() {
        controlador = new ControladorPedidos();

        setTitle("SpeedFast - Gestión de Pedidos y Entregas");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 450);
        setLocationRelativeTo(null);

        // Carga comboBox
        cargarComboBoxes();

        // Configurar la tabla.
        configurarTabla();

        // Cargar los datos en la tabla
        cargarPedidosTabla();

        // Guarda repartidor
        btnGuardarRepartidor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText().trim();
                boolean exito = controlador.registrarRepartidor(nombre);

                if (exito) {
                    JOptionPane.showMessageDialog(null, "Repartidor guardado con éxito.");
                    txtNombre.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al guardar el repartidor. Verifique que el campo no esté vacío.");
                }
            }
        });

        // Guarda pedido y registra entrega en paralelo
        btnGuardarPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String direccion = txtDireccion.getText().trim();
                TipoPedido tipoEnum = (TipoPedido) cmbTipoPedido.getSelectedItem();
                EstadoPedido estadoEnum = (EstadoPedido) cmbEstadoPedido.getSelectedItem();

                if (direccion.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "La dirección no puede estar vacía.");
                    return;
                }

                boolean exito = controlador.registrarPedido(direccion, tipoEnum.toString(), estadoEnum.toString());

                if (exito) {
                    JOptionPane.showMessageDialog(null, "Pedido guardado y entrega registrada correctamente.");
                    txtDireccion.setText("");
                    cargarPedidosTabla();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al guardar el pedido. Asegúrese de haber registrado al menos un repartidor previamente.");
                }
            }
        });
    }

    private void cargarComboBoxes() {
        cmbTipoPedido.removeAllItems();
        for (TipoPedido tp : TipoPedido.values()) {
            cmbTipoPedido.addItem(tp);
        }

        cmbEstadoPedido.removeAllItems();
        for (EstadoPedido ep : EstadoPedido.values()) {
            cmbEstadoPedido.addItem(ep);
        }
    }

    private void configurarTabla() {
        model = new DefaultTableModel();
        model.addColumn("Id");
        model.addColumn("direccion");
        model.addColumn("tipo");
        model.addColumn("estado");
        tblPedidos.setModel(model);
    }

    private void cargarPedidosTabla() {
        model.setRowCount(0);
        String sql = "SELECT id, direccion, tipo, estado FROM pedido";
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] fila = {
                        rs.getInt("id"),
                        rs.getString("direccion"),
                        rs.getString("tipo"),
                        rs.getString("estado")
                };
                model.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
