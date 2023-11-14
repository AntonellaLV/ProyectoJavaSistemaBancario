package org.informatorio.Archivo;

import org.informatorio.Cliente.Cliente;

import java.util.List;

public interface Archivo {

    void exportarClientesCSV(String filename, List<Cliente> clientes);
}