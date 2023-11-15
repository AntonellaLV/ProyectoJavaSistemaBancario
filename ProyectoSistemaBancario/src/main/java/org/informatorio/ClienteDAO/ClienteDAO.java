package org.informatorio.ClienteDAO;

import org.informatorio.Cliente.Cliente;
import org.informatorio.ConexionDB.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {

    public void agregarCliente(Cliente cliente) {
        String sql = "INSERT INTO Clientes (Nombre, Direccion) VALUES (?, ?)";
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getDireccion());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Cliente buscarClientePorId(int id) {
        String sql = "SELECT * FROM Clientes WHERE ID = ?";
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nombre = resultSet.getString("Nombre");
                String direccion = resultSet.getString("Direccion");
                return new Cliente(nombre, direccion);  // Asumiendo que Cliente tiene un constructor adecuado
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Otros métodos CRUD...
}

