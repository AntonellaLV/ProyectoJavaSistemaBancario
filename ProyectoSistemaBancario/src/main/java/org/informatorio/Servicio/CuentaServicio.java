package org.informatorio.Service;

import org.informatorio.CuentaBancaria.CuentaBancaria;
import org.informatorio.CuentaBancariaDAO.CuentaBancariaDAO;

public class CuentaServicio {
    private CuentaBancariaDAO cuentaBancariaDAO;

    public CuentaServicio(CuentaBancariaDAO cuentaBancariaDAO) {
        this.cuentaBancariaDAO = cuentaBancariaDAO;
    }

    public void agregarCuenta(CuentaBancaria cuenta) {
        cuentaBancariaDAO.agregarCuenta(cuenta);
        // Otras operaciones o lógica adicional si es necesario
    }

    // otros métodos relacionados con la gestión de cuentas
    // Por ejemplo: eliminarCuenta, actualizarCuenta, buscarCuentaPorNumero, etc.
}
