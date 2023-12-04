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

        // Verificar si el tipo de cuenta es válido
        if (!"Ahorro".equalsIgnoreCase(tipo) && !"Corriente".equalsIgnoreCase(tipo)) {
            System.out.println("Tipo de cuenta no válido. Debe ser 'Ahorro' o 'Corriente'.");
            return;
        }

        try (Connection conexion = ConexionDB.obtenerConexion()) {
            String sql = "INSERT INTO CuentasBancarias (NumeroCuenta, ClienteID, Saldo, Tipo) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = conexion.prepareStatement(sql)) {
                statement.setString(1, numeroCuenta);
                statement.setInt(2, clienteId);
                statement.setDouble(3, 0.0); // Saldo inicial
                statement.setString(4, tipo);
                statement.executeUpdate();

                System.out.println("Cuenta agregada con éxito:");
                System.out.println("Número de Cuenta: " + numeroCuenta);
                System.out.println("ID del Cliente: " + clienteId);
                System.out.println("Saldo Inicial: 0.0");
                System.out.println("Tipo de Cuenta: " + tipo);
            }
        } catch (SQLException e) {
            System.out.println("Error al agregar la cuenta:");
            e.printStackTrace();
        }
    }



    public void eliminarCuenta() {
        System.out.println("Eliminar Cuenta Bancaria");
        System.out.print("Ingrese el número de cuenta: ");
        String numeroCuenta = scanner.nextLine();

        try (Connection conexion = ConexionDB.obtenerConexion()) {
            // Primero, verificar si la cuenta existe
            String sqlConsulta = "SELECT COUNT(*) FROM CuentasBancarias WHERE NumeroCuenta = ?";
            try (PreparedStatement statementConsulta = conexion.prepareStatement(sqlConsulta)) {
                statementConsulta.setString(1, numeroCuenta);
                ResultSet rs = statementConsulta.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    System.out.println("No se encontró la cuenta con el número: " + numeroCuenta);
                    return;
                }
            }

            // Si la cuenta existe, proceder a eliminarla
            String sqlEliminar = "DELETE FROM CuentasBancarias WHERE NumeroCuenta = ?";
            try (PreparedStatement statementEliminar = conexion.prepareStatement(sqlEliminar)) {
                statementEliminar.setString(1, numeroCuenta);
                int filasAfectadas = statementEliminar.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Cuenta eliminada con éxito.");
                } else {
                    System.out.println("No se pudo eliminar la cuenta.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al intentar eliminar la cuenta:");
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
            // Primero, verificar si la cuenta existe
            String sqlConsulta = "SELECT COUNT(*) FROM CuentasBancarias WHERE NumeroCuenta = ?";
            try (PreparedStatement statementConsulta = conexion.prepareStatement(sqlConsulta)) {
                statementConsulta.setString(1, numeroCuenta);
                ResultSet rs = statementConsulta.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    System.out.println("No se encontró la cuenta con el número: " + numeroCuenta);
                    return;
                }
            }

            // Si la cuenta existe, proceder al depósito
            String sqlActualizar = "UPDATE CuentasBancarias SET Saldo = Saldo + ? WHERE NumeroCuenta = ?";
            try (PreparedStatement statementActualizar = conexion.prepareStatement(sqlActualizar)) {
                statementActualizar.setDouble(1, monto);
                statementActualizar.setString(2, numeroCuenta);
                int filasAfectadas = statementActualizar.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Depósito realizado con éxito.");

                    // Mostrar el nuevo saldo
                    String sqlSaldo = "SELECT Saldo FROM CuentasBancarias WHERE NumeroCuenta = ?";
                    try (PreparedStatement statementSaldo = conexion.prepareStatement(sqlSaldo)) {
                        statementSaldo.setString(1, numeroCuenta);
                        ResultSet rsSaldo = statementSaldo.executeQuery();
                        if (rsSaldo.next()) {
                            double nuevoSaldo = rsSaldo.getDouble("Saldo");
                            System.out.println("El nuevo saldo de la cuenta es: " + nuevoSaldo);
                        }
                    }
                } else {
                    System.out.println("Error al realizar el depósito.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al intentar realizar el depósito:");
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
            // Primero, verificar si la cuenta existe y tiene saldo suficiente
            String sqlConsulta = "SELECT Saldo FROM CuentasBancarias WHERE NumeroCuenta = ?";
            try (PreparedStatement statementConsulta = conexion.prepareStatement(sqlConsulta)) {
                statementConsulta.setString(1, numeroCuenta);
                ResultSet rs = statementConsulta.executeQuery();
                if (rs.next()) {
                    double saldoActual = rs.getDouble("Saldo");
                    if (saldoActual < monto) {
                        System.out.println("Fondos insuficientes para realizar el retiro.");
                        return;
                    }
                } else {
                    System.out.println("No se encontró la cuenta con el número: " + numeroCuenta);
                    return;
                }
            }

            // Proceder al retiro
            String sqlActualizar = "UPDATE CuentasBancarias SET Saldo = Saldo - ? WHERE NumeroCuenta = ?";
            try (PreparedStatement statementActualizar = conexion.prepareStatement(sqlActualizar)) {
                statementActualizar.setDouble(1, monto);
                statementActualizar.setString(2, numeroCuenta);
                int filasAfectadas = statementActualizar.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Retiro realizado con éxito.");

                    // Mostrar el nuevo saldo
                    try (PreparedStatement statementSaldo = conexion.prepareStatement(sqlConsulta)) {
                        statementSaldo.setString(1, numeroCuenta);
                        ResultSet rsSaldo = statementSaldo.executeQuery();
                        if (rsSaldo.next()) {
                            double nuevoSaldo = rsSaldo.getDouble("Saldo");
                            System.out.println("El nuevo saldo de la cuenta es: " + nuevoSaldo);
                        }
                    }
                } else {
                    System.out.println("Error al realizar el retiro.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al intentar realizar el retiro:");
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
                    System.out.println("Cliente no encontrado o no tiene cuentas.");
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
                    System.out.println("Intereses agregados con éxito a la cuenta: " + numeroCuenta);
                } else {
                    System.out.println("Cuenta no encontrada, no es de ahorro, o ya tiene interés aplicado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
