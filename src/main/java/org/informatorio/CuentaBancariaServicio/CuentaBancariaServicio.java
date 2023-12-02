package org.informatorio.CuentaBancariaServicio;

import org.informatorio.Cliente.Cliente;
import org.informatorio.ConexionDB.ConexionDB;
import org.informatorio.CuentaAhorro.CuentaAhorro;
import org.informatorio.CuentaBancaria.CuentaBancaria;
import org.informatorio.CuentaBancariaDAO.CuentaBancariaDAO;
import org.informatorio.CuentaCorriente.CuentaCorriente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CuentaBancariaServicio {
    private CuentaBancariaDAO cuentaBancariaDAO;
    private Scanner scanner;

    public CuentaBancariaServicio(CuentaBancariaDAO cuentaBancariaDAO) {
        this.cuentaBancariaDAO = cuentaBancariaDAO;
        this.scanner = new Scanner(System.in);
    }

    public void agregarCuenta() {
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
                statement.setDouble(3, 0.0);
                statement.setString(4, tipo);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Cuenta agregada con éxito.");
    }


    public void eliminarCuenta() {
        System.out.println("Eliminar Cuenta Bancaria");
        System.out.print("Ingrese el número de cuenta: ");
        String numeroCuenta = scanner.nextLine();

        try (Connection conexion = ConexionDB.obtenerConexion()) {
            String sql = "DELETE FROM CuentasBancarias WHERE NumeroCuenta = ?";
            try (PreparedStatement statement = conexion.prepareStatement(sql)) {
                statement.setString(1, numeroCuenta);
                int filasAfectadas = statement.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Cuenta eliminada con éxito.");
                } else {
                    System.out.println("No se encontró la cuenta.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void depositarEnCuenta() {
        System.out.println("Depositar en Cuenta");
        System.out.print("Ingrese el número de cuenta: ");
        String numeroCuenta = scanner.nextLine();
        System.out.print("Ingrese el monto a depositar: ");
        double monto = scanner.nextDouble();
        scanner.nextLine(); // Limpia el buffer del scanner

        try (Connection conexion = ConexionDB.obtenerConexion()) {
            String sql = "UPDATE CuentasBancarias SET Saldo = Saldo + ? WHERE NumeroCuenta = ?";
            try (PreparedStatement statement = conexion.prepareStatement(sql)) {
                statement.setDouble(1, monto);
                statement.setString(2, numeroCuenta);
                int filasAfectadas = statement.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Depósito realizado con éxito.");
                } else {
                    System.out.println("No se encontró la cuenta.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void retirarDeCuenta() {
        System.out.println("Retirar de Cuenta");
        System.out.print("Ingrese el número de cuenta: ");
        String numeroCuenta = scanner.nextLine();
        System.out.print("Ingrese el monto a retirar: ");
        double monto = scanner.nextDouble();
        scanner.nextLine(); // Limpia el buffer del scanner

        try (Connection conexion = ConexionDB.obtenerConexion()) {
            String sql = "UPDATE CuentasBancarias SET Saldo = Saldo - ? WHERE NumeroCuenta = ? AND Saldo >= ?";
            try (PreparedStatement statement = conexion.prepareStatement(sql)) {
                statement.setDouble(1, monto);
                statement.setString(2, numeroCuenta);
                statement.setDouble(3, monto);
                int filasAfectadas = statement.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Retiro realizado con éxito.");
                } else {
                    System.out.println("Fondos insuficientes o cuenta no encontrada.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void mostrarSaldoTotal() {
        System.out.println("Mostrar Saldo Total del Cliente");
        System.out.print("Ingrese el ID del cliente: ");
        int clienteId = scanner.nextInt();
        scanner.nextLine(); // Limpia el buffer del scanner

        try (Connection conexion = ConexionDB.obtenerConexion()) {
            String sql = "SELECT SUM(Saldo) AS SaldoTotal FROM CuentasBancarias WHERE ClienteID = ?";
            try (PreparedStatement statement = conexion.prepareStatement(sql)) {
                statement.setInt(1, clienteId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    double saldoTotal = resultSet.getDouble("SaldoTotal");
                    System.out.println("El saldo total del cliente es: " + saldoTotal);
                } else {
                    System.out.println("Cliente no encontrado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void agregarIntereses() {
        System.out.println("Agregar Intereses a Cuenta de Ahorro");
        System.out.print("Ingrese el número de cuenta: ");
        String numeroCuenta = scanner.nextLine();

        // Necesitas conocer la tasa de interés para la cuenta de ahorro en este caso
        double tasaInteres = 0.05; // Ejemplo de tasa de interés del 5%

        try (Connection conexion = ConexionDB.obtenerConexion()) {
            String sql = "UPDATE CuentasBancarias SET Saldo = Saldo * (1 + ?) WHERE NumeroCuenta = ? AND Tipo = 'Ahorro'";
            try (PreparedStatement statement = conexion.prepareStatement(sql)) {
                statement.setDouble(1, tasaInteres);
                statement.setString(2, numeroCuenta);
                int filasAfectadas = statement.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Intereses agregados con éxito.");
                } else {
                    System.out.println("Cuenta no encontrada o no es de ahorro.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
