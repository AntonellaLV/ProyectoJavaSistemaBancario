package org.informatorio;

import org.informatorio.Cliente.Cliente;
import org.informatorio.Banco.Banco;
import org.informatorio.CuentaBancaria.CuentaBancaria;
import org.informatorio.CuentaAhorro.CuentaAhorro;
import org.informatorio.CuentaCorriente.CuentaCorriente;

public class App {
    public static void main(String[] args) {
        Banco banco = new Banco();

        // Crear algunos clientes y cuentas
        Cliente cliente1 = new Cliente("Juan Perez", "123 Calle Falsa");
        Cliente cliente2 = new Cliente("Ana Gonzalez", "456 Calle Verdadera");

        banco.registrarCliente(cliente1);
        banco.registrarCliente(cliente2);

        try {
            CuentaBancaria cuenta1 = banco.abrirCuenta(cliente1, "ahorro", 1000.0, 1.5);
            CuentaBancaria cuenta2 = banco.abrirCuenta(cliente2, "corriente", 2000.0, 500.0);

            // Realizar algunas operaciones
            cuenta1.depositar(500);
            cuenta2.retirar(1500);

            // Imprimir detalles de los clientes y sus cuentas
            banco.gestionarClientes();

            // Exportar información de cuentas a CSV
            banco.exportarCuentasCSV("cuentas.csv");

            System.out.println("Operaciones completadas con éxito. Revise el archivo 'cuentas.csv'.");

        } catch (Exception e) {
            System.err.println("Ocurrió un error: " + e.getMessage());
        }
    }
}
