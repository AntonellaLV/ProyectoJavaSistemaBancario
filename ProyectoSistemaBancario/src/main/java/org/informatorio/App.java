package org.informatorio;

import org.informatorio.Cliente.Cliente;
import org.informatorio.ClienteDAO.ClienteDAO;
import org.informatorio.CuentaAhorro.CuentaAhorro;
import org.informatorio.CuentaBancaria.CuentaBancaria;

import org.informatorio.Archivo.Archivo;
import org.informatorio.Archivo.ArchivoImpl;
import org.informatorio.CuentaCorriente.CuentaCorriente;
import org.informatorio.CuentaDAO.CuentaDAO;

import java.util.Scanner;


public class App {
    private static Scanner scanner = new Scanner(System.in);
    private static Archivo archivo = new ArchivoImpl() {
        @Override
        public void exportarClientesCSV(String filename, Class<? extends String[]> clientes) {

        }
    };

    public static void main(String[] args) throws Exception {
        boolean salir = false;
        while (!salir) {
            System.out.println("Bienvenido al Sistema Bancario Informatorio");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Abrir Cuenta");
            System.out.println("3. Depositar Dinero");
            System.out.println("4. Retirar Dinero");
            System.out.println("5. Exportar Cuentas a CSV");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    registrarCliente();
                    break;
                case 2:
                    abrirCuenta();
                    break;
                case 3:
                    depositarDinero();
                    break;
                case 4:
                    retirarDinero();
                    break;
                case 5:
                    archivo.exportarClientesCSV("cuentas.csv", args.getClass());
                    System.out.println("Las cuentas han sido exportadas a 'cuentas.csv' exitosamente.");
                    break;
                case 6:
                    salir = true;
                    System.out.println("Gracias por usar el sistema.");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor intente de nuevo.");
                    break;
            }
        }
        scanner.close();
    }


    private static void registrarCliente() {
        scanner.nextLine(); // Limpiar el buffer del escáner
        System.out.println("Ingrese el nombre del cliente:");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese la dirección del cliente:");
        String direccion = scanner.nextLine();

        Cliente nuevoCliente = new Cliente(0, nombre, direccion);

        ClienteDAO clienteDao = new ClienteDAO();
        clienteDao.insertarCliente(nuevoCliente);
        System.out.println("Cliente registrado con éxito.");
    }


    private static void abrirCuenta() {
        System.out.println("Ingrese el ID del cliente para la nueva cuenta:");
        int idCliente = scanner.nextInt();

        ClienteDAO clienteDao = new ClienteDAO();
        Cliente cliente = clienteDao.consultarCliente(idCliente);

        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        scanner.nextLine(); // Limpia el buffer del scanner después de leer un número.

        System.out.println("Ingrese el tipo de cuenta (ahorro/corriente):");
        String tipo = scanner.nextLine();
        System.out.println("Ingrese el número de cuenta:");
        String numeroCuenta = scanner.nextLine();
        System.out.println("Ingrese el saldo inicial de la cuenta:");
        double saldoInicial = scanner.nextDouble();

        CuentaBancaria nuevaCuenta;
        CuentaDAO cuentaDao = new CuentaDAO();

        if ("ahorro".equalsIgnoreCase(tipo)) {
            System.out.println("Ingrese la tasa de interés para la cuenta de ahorro:");
            double tasaInteres = scanner.nextDouble();
            nuevaCuenta = new CuentaAhorro(numeroCuenta, cliente, tasaInteres);
            cuentaDao.insertarCuenta(nuevaCuenta);
            System.out.println("Cuenta de ahorro creada exitosamente.");
        } else if ("corriente".equalsIgnoreCase(tipo)) {
            System.out.println("Ingrese el límite de sobregiro para la cuenta corriente:");
            double limiteSobregiro = scanner.nextDouble();
            nuevaCuenta = new CuentaCorriente(numeroCuenta, cliente, saldoInicial, limiteSobregiro);
            cuentaDao.insertarCuenta(nuevaCuenta);
            System.out.println("Cuenta corriente creada exitosamente.");
        } else {
            System.out.println("Tipo de cuenta no válido.");
        }
    }


    private static void depositarDinero() {
        System.out.println("Ingrese el número de cuenta:");
        String numeroCuenta = scanner.next();
        System.out.println("Ingrese la cantidad a depositar:");
        double monto = scanner.nextDouble();

        CuentaBancaria cuenta = buscarCuentaPorNumero(numeroCuenta);
        if (cuenta != null) {
            cuenta.depositar(monto);
            System.out.println("Depósito realizado con éxito. Nuevo saldo: " + cuenta.consultarSaldo());
        } else {
            System.out.println("Número de cuenta no encontrado.");
        }
    }


    private static void retirarDinero() throws Exception {
        System.out.println("Ingrese el número de cuenta:");
        String numeroCuenta = scanner.next();
        System.out.println("Ingrese la cantidad a retirar:");
        double monto = scanner.nextDouble();

        CuentaBancaria cuenta = buscarCuentaPorNumero(numeroCuenta);
        if (cuenta != null) {
            cuenta.retirar(monto);
            System.out.println("Retiro realizado con éxito. Nuevo saldo: " + cuenta.consultarSaldo());
        } else {
            System.out.println("Número de cuenta no encontrado.");
        }
    }


    private static CuentaBancaria buscarCuentaPorNumero(String numeroCuenta) {
        CuentaDAO cuentaDao = new CuentaDAO();
        return cuentaDao.buscarCuentaPorNumero(numeroCuenta);
    }

}


