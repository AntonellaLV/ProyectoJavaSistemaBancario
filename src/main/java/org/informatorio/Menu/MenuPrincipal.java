package org.informatorio.Menu;

import org.informatorio.ClienteServicio.ClienteServicio;
import org.informatorio.CuentaBancaria.CuentaBancaria;
import org.informatorio.CuentaBancariaServicio.CuentaBancariaServicio;
import org.informatorio.ExportadorServicio.Administracion;

import java.util.Scanner;

public class MenuPrincipal {
    private ClienteServicio clienteServicio;
    private CuentaBancariaServicio cuentaBancariaServicio;
    private Administracion administracion;
    private Scanner scanner;

    public MenuPrincipal(ClienteServicio clienteServicio,
                         CuentaBancariaServicio cuentaBancariaServicio,
                         Administracion administracion) {
        this.clienteServicio = clienteServicio;
        this.cuentaBancariaServicio = cuentaBancariaServicio;
        this.administracion = administracion;
        this.scanner = new Scanner(System.in);
    }

    public void mostrar() {
        int opcion;
        do {
            System.out.println("\nMenú Principal:");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Agregar Cuenta Bancaria");
            System.out.println("3. Eliminar Cuenta Bancaria");
            System.out.println("4. Depositar en Cuenta");
            System.out.println("5. Retirar de Cuenta");
            System.out.println("6. Mostrar Saldo Total de un Cliente");
            System.out.println("7. Agregar Intereses");
            System.out.println("8. Exportar Datos a CSV");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    clienteServicio.registrarCliente();
                    break;
                case 2:
                    cuentaBancariaServicio.agregarCuenta();
                    break;
                case 3:
                    cuentaBancariaServicio.eliminarCuenta();
                    break;
                case 4:
                    cuentaBancariaServicio.depositarEnCuenta();
                    break;
                case 5:
                    cuentaBancariaServicio.retirarDeCuenta();
                    break;
                case 6:
                    cuentaBancariaServicio.mostrarSaldoTotal();
                    break;
                case 7:
                    cuentaBancariaServicio.agregarIntereses();
                    break;
                case 8:
                    administracion.exportarReporteCuentas();
                    break;
                default:
                    if (opcion != 0) System.out.println("Opción no válida, por favor intenta de nuevo.");
            }
        } while (opcion != 0);
    }
}
