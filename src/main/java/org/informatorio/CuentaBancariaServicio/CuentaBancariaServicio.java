package org.informatorio.CuentaBancariaServicio;

import org.informatorio.CuentaBancaria.CuentaBancaria;
import org.informatorio.CuentaBancariaDAO.CuentaBancariaDAO;

public class CuentaBancariaServicio {
    private CuentaBancariaDAO cuentaBancariaDAO;

    public CuentaBancariaServicio(CuentaBancariaDAO cuentaBancariaDAO) {
        this.cuentaBancariaDAO = cuentaBancariaDAO;
    }

    public void agregarCuenta(CuentaBancaria cuenta) {
        cuentaBancariaDAO.agregarCuenta(cuenta);
        System.out.println("Cuenta agregada con éxito.");
    }

    // Otros métodos relacionados con las cuentas bancarias
}
