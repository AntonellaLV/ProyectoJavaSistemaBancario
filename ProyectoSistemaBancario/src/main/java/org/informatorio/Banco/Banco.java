package org.informatorio.Banco;

import org.informatorio.Cliente.Cliente;
import org.informatorio.CuentaAhorro.CuentaAhorro;
import org.informatorio.CuentaBancaria.CuentaBancaria;
import org.informatorio.CuentaCorriente.CuentaCorriente;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Banco {
    private List<Cliente> clientes;

    public Banco() {
        this.clientes = new ArrayList<>();
    }

    public void registrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public CuentaBancaria abrirCuenta(Cliente cliente, String tipoCuenta, double montoInicial, double tasaOlimite) throws Exception {
        CuentaBancaria cuenta;
        switch (tipoCuenta.toLowerCase()) {
            case "ahorro":
                cuenta = new CuentaAhorro(generarNumeroCuenta(), cliente, tasaOlimite);
                break;
            case "corriente":
                cuenta = new CuentaCorriente(generarNumeroCuenta(), cliente, tasaOlimite);
                break;
            default:
                throw new Exception("Tipo de cuenta no válido.");
        }
        cuenta.depositar(montoInicial);
        cliente.agregarCuenta(cuenta);
        return cuenta;
    }

    public void gestionarClientes() {
        // Ejemplo de lógica para imprimir la información de todos los clientes
        for (Cliente cliente : clientes) {
            System.out.println("Cliente: " + cliente.getNombre() + " - Dirección: " + cliente.getDirección());
            System.out.println("Cuentas:");
            for (CuentaBancaria cuenta : cliente.getCuentas()) {
                System.out.println("\tNúmero de Cuenta: " + cuenta.getNumeroCuenta() + " - Saldo: " + cuenta.consultarSaldo());
            }
            System.out.println();
        }
    }


    public void exportarCuentasCSV(String filename) {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            String header = "Número de Cuenta,Nombre Titular,Dirección Titular,Saldo,Tipo\n";
            fileWriter.write(header);

            for (Cliente cliente : clientes) {
                for (CuentaBancaria cuenta : cliente.getCuentas()) {
                    String tipoCuenta = "Corriente";
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


    // Genera un número de cuenta aleatorio para el ejemplo
    private String generarNumeroCuenta() {
        return "BANCO" + (int) (Math.random() * 1000000);
    }

    // Getters y Setters
    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
}

