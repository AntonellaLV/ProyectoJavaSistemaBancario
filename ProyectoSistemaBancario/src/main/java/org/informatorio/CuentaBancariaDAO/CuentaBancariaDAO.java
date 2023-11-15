package org.informatorio.CuentaBancariaDAO;

import org.informatorio.CuentaBancaria.CuentaBancaria;

public class CuentaBancariaDAO {

    public void agregarCuenta(CuentaBancaria cuenta) {
        String sql = "INSERT INTO CuentasBancarias (NumeroCuenta, Saldo, Tipo) VALUES (?, ?, ?)";
        // Implementación...
    }

    public void eliminarCuenta(String numeroCuenta) {
        String sql = "DELETE FROM CuentasBancarias WHERE NumeroCuenta = ?";
        // Implementación...
    }

    public void actualizarSaldo(String numeroCuenta, double nuevoSaldo) {
        String sql = "UPDATE CuentasBancarias SET Saldo = ? WHERE NumeroCuenta = ?";
        // Implementación...
    }

    // Otros métodos CRUD...
}

