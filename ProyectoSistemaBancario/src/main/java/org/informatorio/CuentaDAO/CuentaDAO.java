package org.informatorio.CuentaDAO;

import org.informatorio.Cliente.Cliente;
import org.informatorio.ConexionDB.ConexionDB;
import org.informatorio.CuentaAhorro.CuentaAhorro;
import org.informatorio.CuentaBancaria.CuentaBancaria;
import org.informatorio.CuentaCorriente.CuentaCorriente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CuentaDAO {

    public CuentaBancaria insertarCuenta(CuentaBancaria cuenta) {
        String sql = "INSERT INTO cuentas (cliente_id, numero_cuenta, saldo, tipo, tasa_interes, limite_sobregiro) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, cuenta.getTitular().getId());
            pstmt.setString(2, cuenta.getNumeroCuenta());
            pstmt.setDouble(3, cuenta.getSaldo());
            pstmt.setString(4, cuenta instanceof CuentaAhorro ? "ahorro" : "corriente");
            pstmt.setDouble(5, cuenta instanceof CuentaAhorro ? ((CuentaAhorro) cuenta).getTasaInteres() : null);
            pstmt.setDouble(6, cuenta instanceof CuentaCorriente ? ((CuentaCorriente) cuenta).getLimiteSobregiro() : null);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public CuentaBancaria buscarCuentaPorNumero(String numeroCuenta) {
        String sql = "SELECT c.*, cl.nombre, cl.dirección FROM cuentas c JOIN clientes cl ON c.cliente_id = cl.id WHERE c.numero_cuenta = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, numeroCuenta);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String tipo = rs.getString("tipo");
                int clienteId = rs.getInt("cliente_id");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("dirección");
                double saldo = rs.getDouble("saldo");

                Cliente titular = new Cliente(clienteId, nombre, direccion);
                CuentaBancaria cuenta = null;
                if ("ahorro".equals(tipo)) {
                    double tasaInteres = rs.getDouble("tasa_interes");
                    cuenta = new CuentaAhorro(numeroCuenta, titular, saldo);
                } else if ("corriente".equals(tipo)) {
                    double limiteSobregiro = rs.getDouble("limite_sobregiro");
                    cuenta = new CuentaCorriente(numeroCuenta, titular, saldo, limiteSobregiro);
                }
                return cuenta;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

