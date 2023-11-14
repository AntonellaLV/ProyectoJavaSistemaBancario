package org.informatorio.Archivo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.informatorio.Cliente.Cliente;
import org.informatorio.CuentaAhorro.CuentaAhorro;
import org.informatorio.CuentaBancaria.CuentaBancaria;

public class ArchivoImpl implements Archivo {

    @Override
    public void exportarClientesCSV(String filename, List<Cliente> clientes) {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            String header = "Número de Cuenta,Nombre Titular,Dirección Titular,Saldo,Tipo\n";
            fileWriter.write(header);

            for (Cliente cliente : clientes) {
                for (CuentaBancaria cuenta : cliente.getCuentas()) {
                    String tipoCuenta = cuenta instanceof CuentaAhorro ? "Ahorro" : "Corriente";
                    String line = String.format("%s,%s,%s,%.2f,%s\n",
                            cuenta.getNumeroCuenta(),
                            cliente.getNombre(),
                            cliente.getDirección(),
                            cuenta.consultarSaldo(),
                            tipoCuenta);
                    fileWriter.write(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}