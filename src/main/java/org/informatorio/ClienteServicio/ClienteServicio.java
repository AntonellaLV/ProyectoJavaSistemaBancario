package org.informatorio.ClienteServicio;

import org.informatorio.Cliente.Cliente;
import org.informatorio.ClienteDAO.ClienteDAO;
import org.informatorio.ConexionDB.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ClienteServicio {
    private ClienteDAO clienteDAO;
    private Scanner scanner;

    public ClienteServicio(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
        this.scanner = new Scanner(System.in);
    }

    public void registrarCliente() {
        System.out.println("Registrar Nuevo Cliente");
        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la dirección: ");
        String direccion = scanner.nextLine();

        Cliente nuevoCliente = new Cliente(nombre, direccion);
        clienteDAO.insertarCliente(nuevoCliente);
        System.out.println("Cliente registrado con éxito.");
    }

}
