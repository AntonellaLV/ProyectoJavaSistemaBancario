package org.informatorio.Service;

import org.informatorio.Cliente.Cliente;
import org.informatorio.ClienteDAO.ClienteDAO;
import org.informatorio.ConexionDB.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteServicio {
    private ClienteDAO clienteDAO;

    public ClienteServicio(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    private void registrarCliente() {
        System.out.println("Registrar Nuevo Cliente");
        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la dirección: ");
        String direccion = scanner.nextLine();

        Cliente nuevoCliente = new Cliente(nombre, direccion);

        try (Connection conexion = ConexionDB.obtenerConexion()) {
            String sql = "INSERT INTO Clientes (Nombre, Direccion) VALUES (?, ?)";
            try (PreparedStatement statement = conexion.prepareStatement(sql)) {
                statement.setString(1, nuevoCliente.getNombre());
                statement.setString(2, nuevoCliente.getDireccion());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Cliente registrado con éxito.");
    }

}

