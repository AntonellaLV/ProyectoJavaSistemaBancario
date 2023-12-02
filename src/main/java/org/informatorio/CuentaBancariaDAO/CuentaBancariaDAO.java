package org.informatorio.CuentaBancariaDAO;

import org.informatorio.Cliente.Cliente;
import org.informatorio.ConexionDB.ConexionDB;
import org.informatorio.CuentaBancaria.CuentaBancaria;
import org.informatorio.CuentaAhorro.CuentaAhorro;
import org.informatorio.CuentaCorriente.CuentaCorriente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CuentaBancariaDAO {

    public void agregarCuenta(CuentaBancaria cuenta) {
        String sql = "INSERT INTO CuentasBancarias (NumeroCuenta, ClienteID, Saldo, Tipo) VALUES (?, ?, ?, ?)";
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, cuenta.getNumeroCuenta());
            statement.setInt(2, cuenta.getTitular().getId());
            statement.setDouble(3, cuenta.getSaldo());
            statement.setString(4, cuenta.getTipo());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean eliminarCuenta(String numeroCuenta) {
        String sql = "DELETE FROM CuentasBancarias WHERE NumeroCuenta = ?";
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, numeroCuenta);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean retirarDeCuenta(String numeroCuenta, double monto) {
        String sql = "UPDATE CuentasBancarias SET Saldo = Saldo - ? WHERE NumeroCuenta = ? AND Saldo >= ?";
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setDouble(1, monto);
            statement.setString(2, numeroCuenta);
            statement.setDouble(3, monto);
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public double obtenerSaldoTotal(int clienteId) {
        String sql = "SELECT SUM(Saldo) AS SaldoTotal FROM CuentasBancarias WHERE ClienteID = ?";
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, clienteId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("SaldoTotal");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean agregarIntereses(String numeroCuenta, double tasaInteres) {
        String sql = "UPDATE CuentasBancarias SET Saldo = Saldo + (Saldo * ?) WHERE NumeroCuenta = ? AND Tipo = 'Ahorro'";
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setDouble(1, tasaInteres);
            statement.setString(2, numeroCuenta);
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean actualizarSaldo(String numeroCuenta, double nuevoSaldo) {
        String sql = "UPDATE CuentasBancarias SET Saldo = ? WHERE NumeroCuenta = ?";
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setDouble(1, nuevoSaldo);
            statement.setString(2, numeroCuenta);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<CuentaBancaria> obtenerTodasLasCuentas() {
        List<CuentaBancaria> cuentas = new ArrayList<>();
        String sql = "SELECT cb.*, c.Nombre, c.Direccion FROM CuentasBancarias cb INNER JOIN Clientes c ON cb.ClienteID = c.ID";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String tipo = rs.getString("Tipo");
                String numeroCuenta = rs.getString("NumeroCuenta");
                double saldo = rs.getDouble("Saldo");

                // Datos del cliente
                String nombreCliente = rs.getString("Nombre");
                String direccionCliente = rs.getString("Direccion");
                Cliente titular = new Cliente(nombreCliente, direccionCliente);

                CuentaBancaria cuenta;
                if ("Ahorro".equals(tipo)) {
                    double tasaInteres = rs.getDouble("TasaInteres");
                    cuenta = new CuentaAhorro(numeroCuenta, titular, saldo, tasaInteres);
                } else {
                    double limiteSobregiro = rs.getDouble("LimiteSobregiro");
                    cuenta = new CuentaCorriente(numeroCuenta, titular, saldo, limiteSobregiro);
                }
                cuentas.add(cuenta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cuentas;
    }
}