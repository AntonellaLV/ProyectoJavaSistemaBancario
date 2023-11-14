package org.informatorio.CuentaDAO;

import org.informatorio.ConexionDB.ConexionDB;
import org.informatorio.CuentaAhorro.CuentaAhorro;
import org.informatorio.CuentaBancaria.CuentaBancaria;
import org.informatorio.CuentaCorriente.CuentaCorriente;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
        return null;
    }
}

