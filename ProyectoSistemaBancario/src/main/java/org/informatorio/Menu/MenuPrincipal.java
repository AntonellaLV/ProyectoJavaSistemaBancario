package org.informatorio.Menu;
import org.informatorio.Cliente.Cliente;
import org.informatorio.ConexionDB.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MenuPrincipal {
    private final Map<Integer, IOpcionMenu> opciones;
    private final Scanner scanner;

    public MenuPrincipal() {
        this.opciones = new HashMap<>();
        this.scanner = new Scanner(System.in);
        inicializarOpciones();
    }

    private void inicializarOpciones() {
        opciones.put(1, this::registrarCliente);
        opciones.put(2, this::agregarCuenta);
        opciones.put(3, this::eliminarCuenta);
        opciones.put(4, this::depositarEnCuenta);
        opciones.put(5, this::retirarDeCuenta);
        opciones.put(6, this::mostrarSaldoTotal);
        opciones.put(7, this::agregarIntereses);
        opciones.put(8, this::exportarDatosACSV);

    }

    public void mostrar() {
        int seleccion;
        do {
            System.out.println("\nMenú Principal:");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Agregar Cuenta Bancaria");
            System.out.println("3. Eliminar Cuenta Bancaria");
            System.out.println("4. Depositar en Cuenta");
            System.out.println("5. Retirar de Cuenta");
            System.out.println("6. Mostrar Saldo Total de un Cliente");
            System.out.println("7. Agregar Intereses a Cuenta de Ahorro");
            System.out.println("8. Exportar Datos de Cuentas a CSV");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            seleccion = scanner.nextInt();
            scanner.nextLine(); // Limpia el buffer del scanner

            IOpcionMenu opcionSeleccionada = opciones.get(seleccion);
            if (opcionSeleccionada != null) {
                opcionSeleccionada.ejecutar();
            } else if (seleccion != 0) {
                System.out.println("Opción no válida, por favor intenta de nuevo.");
            }
        } while (seleccion != 0);
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


    private void agregarCuenta() {
        System.out.println("Agregar Cuenta Bancaria");
        System.out.print("Ingrese el número de cuenta: ");
        String numeroCuenta = scanner.nextLine();
        System.out.print("Ingrese el ID del cliente: ");
        int clienteId = scanner.nextInt();
        scanner.nextLine(); // Limpia el buffer del scanner
        System.out.print("Ingrese el tipo de cuenta (Ahorro/Corriente): ");
        String tipo = scanner.nextLine();

        try (Connection conexion = ConexionDB.obtenerConexion()) {
            String sql = "INSERT INTO CuentasBancarias (NumeroCuenta, ClienteID, Saldo, Tipo) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = conexion.prepareStatement(sql)) {
                statement.setString(1, numeroCuenta);
                statement.setInt(2, clienteId);
                statement.setDouble(3, 0.0); // Suponiendo saldo inicial como 0
                statement.setString(4, tipo);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Cuenta agregada con éxito.");
    }


    private void eliminarCuenta() {
        System.out.println("Eliminar Cuenta Bancaria");
        System.out.print("Ingrese el número de cuenta: ");
        String numeroCuenta = scanner.nextLine();

        // Aquí debes agregar la lógica para eliminar la cuenta del sistema
        System.out.println("Cuenta eliminada con éxito.");
    }


    private void depositarEnCuenta() {
        System.out.println("Depositar en Cuenta");
        System.out.print("Ingrese el número de cuenta: ");
        String numeroCuenta = scanner.nextLine();
        System.out.print("Ingrese el monto a depositar: ");
        double monto = scanner.nextDouble();
        scanner.nextLine(); // Limpia el buffer del scanner

        // Aquí debes agregar la lógica para depositar en la cuenta
        System.out.println("Depósito realizado con éxito.");
    }


    private void retirarDeCuenta() {
        System.out.println("Retirar de Cuenta");
        System.out.print("Ingrese el número de cuenta: ");
        String numeroCuenta = scanner.nextLine();
        System.out.print("Ingrese el monto a retirar: ");
        double monto = scanner.nextDouble();
        scanner.nextLine(); // Limpia el buffer del scanner

        // Aquí debes agregar la lógica para retirar de la cuenta
        System.out.println("Retiro realizado con éxito.");
    }


    private void mostrarSaldoTotal() {
        System.out.println("Mostrar Saldo Total del Cliente");
        System.out.print("Ingrese el ID del cliente: ");
        int clienteId = scanner.nextInt();
        scanner.nextLine(); // Limpia el buffer del scanner

        // Aquí debes agregar la lógica para calcular y mostrar el saldo total
    }


    private void agregarIntereses() {
        System.out.println("Agregar Intereses a Cuenta de Ahorro");
        System.out.print("Ingrese el número de cuenta: ");
        String numeroCuenta = scanner.nextLine();

        // Aquí debes agregar la lógica para agregar intereses a la cuenta especificada
        System.out.println("Intereses agregados con éxito.");
    }


    private void exportarDatosACSV() {
        System.out.println("Exportar Datos de Cuentas a CSV");
        System.out.print("Ingrese el nombre del archivo CSV: ");
        String nombreArchivo = scanner.nextLine();

        // Aquí debes agregar la lógica para exportar los datos a un archivo CSV
        System.out.println("Datos exportados con éxito a " + nombreArchivo);
    }

}
