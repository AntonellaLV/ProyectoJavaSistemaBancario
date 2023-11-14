package org.informatorio.ClienteDAO;

import org.informatorio.Cliente.Cliente;
import org.informatorio.ConexionDB.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ClienteDAO {

    //private List<Cliente> cliente;

    public void insertarCliente(Cliente cliente) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexionDB.obtenerConexion();
            conn.setAutoCommit(false); // Desactivar auto-commit para manejar la transacción manualmente

            String sql = "INSERT INTO clientes (nombre, dirección) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getDirección());
            pstmt.executeUpdate();

            conn.commit(); // Confirmar la transacción si todo ha salido bien
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Revertir la transacción en caso de error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Reestablecer auto-commit
                    conn.close(); // Asegurarse de cerrar la conexión
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Cliente consultarCliente(int idCliente) {
        return null;
    }
}

