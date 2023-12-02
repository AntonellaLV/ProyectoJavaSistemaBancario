package org.informatorio.ClienteServicicio;

import org.informatorio.Cliente.Cliente;
import org.informatorio.ClienteDAO.ClienteDAO;

public class ClienteServicio {
    private ClienteDAO clienteDAO;

    public ClienteServicio(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public void registrarCliente(String nombre, String direccion) {
        Cliente nuevoCliente = new Cliente(nombre, direccion);
        clienteDAO.insertarCliente(nuevoCliente);
        System.out.println("Cliente registrado con éxito.");
    }

    // Otros métodos relacionados con los clientes
}
